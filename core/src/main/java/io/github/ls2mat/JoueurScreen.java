package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class JoueurScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture textureGame;
    private Texture textureDanceuse;
    private Texture textureJoueur;

    private Animator[] tabJoueur;

    // Textures des boutons
    private Texture btnFloss;
    private Texture btnGangnam;
    private Texture btnMacarena;
    private Texture btnRobot;

    // Zones cliquables des boutons
    private Rectangle btnFlossRect;
    private Rectangle btnGangnamRect;
    private Rectangle btnMacarenaRect;
    private Rectangle btnRobotRect;

    // Séquence à reproduire
    private int[] sequenceAReproduire;
    private int indexReponse = 0;

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
        System.out.println("Clique sur les boutons pour reproduire la séquence !");
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
        font.getData().setScale(2);

        textureGame = new Texture("maps/Map.png");
        textureDanceuse = new Texture("dance/danseuse/defaut.png");
        textureJoueur = new Texture("dance/joueur/defaut.png");

        // Charger les boutons (images des danses)
        btnFloss = new Texture("dance/joueur/floss.png");
        btnGangnam = new Texture("dance/joueur/gangnamStyle.png");
        btnMacarena = new Texture("dance/joueur/macarena.png");
        btnRobot = new Texture("dance/joueur/robot.png");

        // Positionner les boutons en bas de l'écran
        float btnWidth = 120;
        float btnHeight = 120;
        float btnY = 20;
        float spacing = 20;
        float totalWidth = (btnWidth * 4) + (spacing * 3);
        float startX = (Gdx.graphics.getWidth() - totalWidth) / 2;

        btnFlossRect = new Rectangle(startX, btnY, btnWidth, btnHeight);
        btnGangnamRect = new Rectangle(startX + btnWidth + spacing, btnY, btnWidth, btnHeight);
        btnMacarenaRect = new Rectangle(startX + (btnWidth + spacing) * 2, btnY, btnWidth, btnHeight);
        btnRobotRect = new Rectangle(startX + (btnWidth + spacing) * 3, btnY, btnWidth, btnHeight);
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
                    game.setScreen(new DanseuseScreen(game));
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    game.setScreen(new MenuScreen(game));
                }
                // Détection clic souris pour les boutons de fin
                if (Gdx.input.justTouched()) {
                    int mouseX = Gdx.input.getX();
                    int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

                    // Zone "Rejouer" (approximatif)
                    if (mouseY > 180 && mouseY < 220 && mouseX > 250 && mouseX < 450) {
                        game.setScreen(new DanseuseScreen(game));
                    }
                    // Zone "Menu" (approximatif)
                    if (mouseY > 140 && mouseY < 180 && mouseX > 240 && mouseX < 440) {
                        game.setScreen(new MenuScreen(game));
                    }
                }
                break;

            case VICTOIRE:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    game.setScreen(new DanseuseScreen(game));
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    game.setScreen(new MenuScreen(game));
                }
                // Détection clic pour victoire
                if (Gdx.input.justTouched()) {
                    int mouseX = Gdx.input.getX();
                    int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

                    if (mouseY > 180 && mouseY < 220 && mouseX > 200 && mouseX < 500) {
                        game.setScreen(new DanseuseScreen(game));
                    }
                    if (mouseY > 140 && mouseY < 180 && mouseX > 240 && mouseX < 440) {
                        game.setScreen(new MenuScreen(game));
                    }
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

        // Boutons de danse (seulement si on attend l'input)
        if (etatActuel == Etat.ATTENTE_INPUT) {
            dessinerBoutons();
        }

        // Interface texte
        afficherUI();

        batch.end();
    }

    private void dessinerBoutons() {
        // Dessiner les 4 boutons
        batch.draw(btnFloss, btnFlossRect.x, btnFlossRect.y, btnFlossRect.width, btnFlossRect.height);
        batch.draw(btnGangnam, btnGangnamRect.x, btnGangnamRect.y, btnGangnamRect.width, btnGangnamRect.height);
        batch.draw(btnMacarena, btnMacarenaRect.x, btnMacarenaRect.y, btnMacarenaRect.width, btnMacarenaRect.height);
        batch.draw(btnRobot, btnRobotRect.x, btnRobotRect.y, btnRobotRect.width, btnRobotRect.height);

        // Labels des boutons
        font.getData().setScale(1);
        font.draw(batch, "Floss", btnFlossRect.x + 30, btnFlossRect.y - 5);
        font.draw(batch, "Gangnam", btnGangnamRect.x + 10, btnGangnamRect.y - 5);
        font.draw(batch, "Macarena", btnMacarenaRect.x + 5, btnMacarenaRect.y - 5);
        font.draw(batch, "Robot", btnRobotRect.x + 30, btnRobotRect.y - 5);
        font.getData().setScale(2);
    }

    private void gererInput() {
        // Détection du clic
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inverser Y

            int tentative = -1;

            if (btnFlossRect.contains(mouseX, mouseY)) {
                tentative = 0;
            } else if (btnGangnamRect.contains(mouseX, mouseY)) {
                tentative = 1;
            } else if (btnMacarenaRect.contains(mouseX, mouseY)) {
                tentative = 2;
            } else if (btnRobotRect.contains(mouseX, mouseY)) {
                tentative = 3;
            }

            if (tentative != -1) {
                verifierReponse(tentative);
            }
        }
    }

    private void verifierReponse(int tentative) {
        int bonneReponse = sequenceAReproduire[indexReponse];
        String[] noms = {"Floss", "Gangnam", "Macarena", "Robot"};

        if (tentative == bonneReponse) {
            System.out.println("✓ Correct ! " + noms[tentative] + " (" + (indexReponse + 1) + "/" + sequenceAReproduire.length + ")");
            score++;
            indexReponse++;

            tabJoueur[tentative].reset();
            etatActuel = Etat.JOUE_ANIMATION;

            if (indexReponse >= sequenceAReproduire.length) {
                etatActuel = Etat.VICTOIRE;
                System.out.println("🎉 BRAVO ! Séquence réussie !");
            }
        } else {
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
                font.draw(batch, "ESCAPE = Menu", 250, 160);
                break;

            case VICTOIRE:
                font.draw(batch, "BRAVO !", 300, 400);
                font.draw(batch, "Séquence réussie !", 220, 350);
                font.draw(batch, "Score: " + score + "/" + sequenceAReproduire.length, 280, 300);
                font.draw(batch, "ENTER = Niveau suivant", 200, 200);
                font.draw(batch, "ESCAPE = Menu", 240, 160);
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
        btnFloss.dispose();
        btnGangnam.dispose();
        btnMacarena.dispose();
        btnRobot.dispose();

        for (Animator anim : tabJoueur) {
            if (anim != null) anim.dispose();
        }
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
