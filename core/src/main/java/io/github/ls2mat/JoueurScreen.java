package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class JoueurScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture textureGame;
    private Texture textureDanceuse;
    private Texture textureJoueur;

    private Animator[] tabJoueur;

    // Séquence à reproduire
    private int[] sequenceAReproduire;
    private int indexReponse = 0; // Où on en est dans la reproduction

    // Timer pour l'animation
    private float timerAnimation = 0f;
    private float DUREE_ANIMATION = 2.0f;

    // États
    private enum Etat { ATTENTE_INPUT, JOUE_ANIMATION, GAME_OVER, VICTOIRE }
    private Etat etatActuel = Etat.ATTENTE_INPUT;

    // Score
    private int score = 0;
    private String messageErreur = "";

    public JoueurScreen(Main game, int[] sequence) {
        this.game = game;
        this.sequenceAReproduire = sequence;

        this.tabJoueur = new Animator[4];
        importAnimations();

        System.out.println("=== TOUR DU JOUEUR ===");
        afficherSequence();
        System.out.println("Utilise les touches : 1=Floss, 2=Gangnam, 3=Macarena, 4=Robot");
    }

    private void afficherSequence() {
        String[] noms = {"Floss", "Gangnam", "Macarena", "Robot"};
        System.out.print("À reproduire : ");
        for (int i = 0; i < sequenceAReproduire.length; i++) {
            System.out.print(noms[sequenceAReproduire[i]]);
            if (i < sequenceAReproduire.length - 1) System.out.print(" → ");
        }
        System.out.println();
    }

    private void importAnimations() {
        tabJoueur[0] = new Animator("joueur/floss.png");
        tabJoueur[1] = new Animator("joueur/gangnamStyle.png");
        tabJoueur[2] = new Animator("joueur/macarena.png");
        tabJoueur[3] = new Animator("joueur/robot.png");
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2); // Texte plus gros

        textureGame = new Texture("maps/Map.png");
        textureDanceuse = new Texture("dance/danseuse/defaut.png");
        textureJoueur = new Texture("dance/joueur/defaut.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Gestion des états
        switch (etatActuel) {
            case ATTENTE_INPUT:
                gererInput();
                break;

            case JOUE_ANIMATION:
                timerAnimation += delta;
                int danseActuelle = sequenceAReproduire[indexReponse - 1];
                tabJoueur[danseActuelle].update(delta);

                if (timerAnimation >= DUREE_ANIMATION) {
                    etatActuel = Etat.ATTENTE_INPUT;
                    timerAnimation = 0f;
                }
                break;

            case GAME_OVER:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    // Recommencer
                    game.setScreen(new DanseuseScreen(game));
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    Gdx.app.exit();
                }
                break;

            case VICTOIRE:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    // Niveau suivant (séquence plus longue)
                    game.setScreen(new DanseuseScreen(game));
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    Gdx.app.exit();
                }
                break;
        }

        // Dessin
        batch.begin();

        // Map
        batch.draw(textureGame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Danseuse (immobile)
        batch.draw(textureDanceuse, 750, 675, 390, 273);

        // Joueur
        if (etatActuel == Etat.JOUE_ANIMATION) {
            int danseActuelle = sequenceAReproduire[indexReponse - 1];
            tabJoueur[danseActuelle].draw(batch, 760, 130, 260, 520);
        } else {
            batch.draw(textureJoueur, 660, 180, 600, 440);
        }

        // Interface texte
        afficherUI();

        batch.end();
    }

    private void gererInput() {
        int tentative = -1;

        // Touches 1, 2, 3, 4
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) tentative = 0; // Floss
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) tentative = 1; // Gangnam
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) tentative = 2; // Macarena
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) tentative = 3; // Robot

        if (tentative != -1) {
            verifierReponse(tentative);
        }
    }

    private void verifierReponse(int tentative) {
        int bonneReponse = sequenceAReproduire[indexReponse];
        String[] noms = {"Floss", "Gangnam", "Macarena", "Robot"};

        if (tentative == bonneReponse) {
            // Bonne réponse !
            System.out.println("✓ Correct ! " + noms[tentative] + " (" + (indexReponse + 1) + "/" + sequenceAReproduire.length + ")");
            score++;
            indexReponse++;

            // Jouer l'animation
            tabJoueur[tentative].reset();
            etatActuel = Etat.JOUE_ANIMATION;

            // Vérifier si séquence complète
            if (indexReponse >= sequenceAReproduire.length) {
                etatActuel = Etat.VICTOIRE;
                System.out.println("🎉 BRAVO ! Séquence réussie !");
            }
        } else {
            // Mauvaise réponse
            System.out.println("✗ ERREUR ! Tu as fait " + noms[tentative] + " au lieu de " + noms[bonneReponse]);
            messageErreur = "ERREUR ! C'était " + noms[bonneReponse] + " !";
            etatActuel = Etat.GAME_OVER;
        }
    }

    private void afficherUI() {
        String[] noms = {"Floss", "Gangnam", "Macarena", "Robot"};

        switch (etatActuel) {
            case ATTENTE_INPUT:
                font.draw(batch, "Mouvement " + (indexReponse + 1) + "/" + sequenceAReproduire.length, 10, 580);
                font.draw(batch, "Score: " + score, 10, 540);
                font.draw(batch, "1=Floss  2=Gangnam  3=Macarena  4=Robot", 10, 30);

                // Afficher la séquence
//                String seq = "Séquence: ";
//                for (int i = 0; i < sequenceAReproduire.length; i++) {
//                    if (i < indexReponse) {
//                        seq += "✓ "; // Déjà fait
//                    } else if (i == indexReponse) {
//                        seq += "[" + noms[sequenceAReproduire[i]] + "] "; // En cours
//                    } else {
//                        seq += "? "; // À venir
//                    }
//                }
//                font.draw(batch, seq, 10, 500);
                break;

            case JOUE_ANIMATION:
                font.draw(batch, "Mouvement " + indexReponse + "/" + sequenceAReproduire.length, 10, 580);
                font.draw(batch, "Score: " + score, 10, 540);
                break;

            case GAME_OVER:
                font.draw(batch, "PERDU !", 300, 400);
                font.draw(batch, messageErreur, 200, 350);
                font.draw(batch, "Score: " + score + "/" + sequenceAReproduire.length, 280, 300);
                font.draw(batch, "ENTER = Rejouer", 250, 200);
                font.draw(batch, "ESCAPE = Quitter", 240, 160);
                break;

            case VICTOIRE:
                font.draw(batch, "BRAVO !", 300, 400);
                font.draw(batch, "Séquence réussie !", 220, 350);
                font.draw(batch, "Score: " + score + "/" + sequenceAReproduire.length, 280, 300);
                font.draw(batch, "ENTER = Niveau suivant", 200, 200);
                font.draw(batch, "ESCAPE = Quitter", 240, 160);
                break;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        textureGame.dispose();
        textureDanceuse.dispose();
        textureJoueur.dispose();

        for (Animator anim : tabJoueur) {
            if (anim != null) anim.dispose();
        }
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
