package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class DanseuseScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Texture textureGame;
    private Texture textureJoueur;
    private Texture textureDanceuse;

    private Animator[] tabDanseuse;

    private int[] sequenceDanses;
    private int indexDanseActuelle = 0;
    private int nombreDanses = 4;

    private float timerDanse = 0f;
    private float DUREE_DANSE = 2.0f;
    private float DUREE_PAUSE = 1.0f;

    private enum Etat { PAUSE, DANSE, TERMINE }
    private Etat etatActuel = Etat.PAUSE;

    private Random rand = new Random();

    public DanseuseScreen(Main game) {
        this.game = game;
        this.tabDanseuse = new Animator[4];
        this.sequenceDanses = new int[nombreDanses];

        genererSequenceAleatoire();
        importDance();

        System.out.println("=== SÉQUENCE GÉNÉRÉE ===");
        afficherSequence();
    }

    private void genererSequenceAleatoire() {
        for (int i = 0; i < nombreDanses; i++) {
            sequenceDanses[i] = rand.nextInt(4);
        }
    }

    private void afficherSequence() {
        String[] noms = {"Floss", "Gangnam", "Macarena", "Robot"};
        System.out.print("Séquence : ");
        for (int i = 0; i < sequenceDanses.length; i++) {
            System.out.print(noms[sequenceDanses[i]]);
            if (i < sequenceDanses.length - 1) System.out.print(" → ");
        }
        System.out.println();
    }

    private void importDance() {
        tabDanseuse[0] = new Animator("danseuse/floss.png");
        tabDanseuse[1] = new Animator("danseuse/gangnamStyle.png");
        tabDanseuse[2] = new Animator("danseuse/macarena.png");
        tabDanseuse[3] = new Animator("danseuse/robot.png");
    }

    public int[] getSequenceDanses() {
        return sequenceDanses;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        textureGame = new Texture("maps/Map.png");
        textureDanceuse = new Texture("dance/danseuse/defaut.png");
        textureJoueur = new Texture("dance/joueur/defaut.png");

        AudioManager.getInstance().jouerDanseusMusic();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timerDanse += delta;

        switch (etatActuel) {
            case PAUSE:
                if (timerDanse >= DUREE_PAUSE) {
                    etatActuel = Etat.DANSE;
                    timerDanse = 0f;

                    int danse = sequenceDanses[indexDanseActuelle];
                    tabDanseuse[danse].reset();

                    String[] noms = {"Floss", "Gangnam", "Macarena", "Robot"};
                    System.out.println("▶ Danse " + (indexDanseActuelle + 1) + "/" + nombreDanses + " : " + noms[danse]);
                }
                break;

            case DANSE:
                int danse = sequenceDanses[indexDanseActuelle];
                tabDanseuse[danse].update(delta);

                if (timerDanse >= DUREE_DANSE) {
                    indexDanseActuelle++;
                    timerDanse = 0f;

                    if (indexDanseActuelle >= nombreDanses) {
                        etatActuel = Etat.TERMINE;
                        System.out.println("✓ Séquence terminée ! À toi de jouer !");
                    } else {
                        etatActuel = Etat.PAUSE;
                    }
                }
                break;

            case TERMINE:
                if (timerDanse >= 1.0f) {
                    game.setScreen(new JoueurScreen(game, sequenceDanses));
                }
                break;
        }

        batch.begin();

        batch.draw(textureGame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (etatActuel == Etat.DANSE) {
            int danse = sequenceDanses[indexDanseActuelle];
            tabDanseuse[danse].draw(batch, 750, 450, 300, 675);
        } else {
            batch.draw(textureDanceuse, 750, 675, 390, 273);
        }

        batch.draw(textureJoueur, 660, 180, 600, 440);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        textureGame.dispose();
        textureDanceuse.dispose();
        textureJoueur.dispose();

        for (Animator anim : tabDanseuse) {
            if (anim != null) anim.dispose();
        }
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
