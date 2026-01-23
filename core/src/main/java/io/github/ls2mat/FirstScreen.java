package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.ls2mat.Dance.Dance;
import io.github.ls2mat.Dance.TypeDanse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FirstScreen implements Screen {

    SpriteBatch batch;
    private Texture textureSheet;
    // Pour nettoyer la mémoire à la fin
    List<Texture> texturesPourNettoyage = new ArrayList<>();

    // DEUX bibliothèques séparées (Prof vs Joueur)
    Map<TypeDanse, Dance> danseuse = new HashMap<>();
    Map<TypeDanse, Dance> dansesJoueur = new HashMap<>();

    Personnage prof;
    Personnage joueur;

    // Logique du jeu
    enum EtatJeu { TOUR_PROF, TOUR_JOUEUR, GAME_OVER }
    EtatJeu etatActuel;

    List<TypeDanse> sequenceAImiter = new ArrayList<>();
    int indexLectureProf = 0;
    int indexReponseJoueur = 0;
    float timerAttente = 0;

    public FirstScreen() {
        batch = new SpriteBatch();

        //chargement de la map
        textureSheet = new Texture("maps/Map.png");
        texturesPourNettoyage.add(textureSheet);

        // 1. CHARGEMENT DES RESSOURCES
        // Charge dossier "assets/dance/danseuse/"
        chargerBibliotheque(danseuse, "danseuse");

        // Charge dossier "assets/dance/joueur/"
        chargerBibliotheque(dansesJoueur, "joueur");

        // 2. RECUPERATION DES POSITIONS D'ATTENTE (IDLE)
        Dance idleProf = danseuse.get(TypeDanse.Defaut);
        Dance idleJoueur = dansesJoueur.get(TypeDanse.Defaut);

        // 3. CREATION DES PERSONNAGES
        prof = new Personnage(50, 200, idleProf);
        joueur = new Personnage(400, 200, idleJoueur);

    }

    /**
     * Charge toutes les danses de l'Enum depuis un sous-dossier spécifique.
     */
    private void chargerBibliotheque(Map<TypeDanse, Dance> mapARemplir, String sousDossier) {
        for (TypeDanse type : TypeDanse.values()) {
            // Construction du chemin : assets/dance/[dossier]/[fichier]
            String chemin = "dance/" + sousDossier + "/" + type.getFichierImage();

            try {
                Texture tex = new Texture(Gdx.files.internal(chemin));
                texturesPourNettoyage.add(tex);

                // Découpe (64x64 pixels - Change ici si tes images sont plus grandes !)
                TextureRegion[][] tmp = TextureRegion.split(tex, 64, 64);

                // Création animation (Ligne 0)
                Animation<TextureRegion> anim = new Animation<>(type.getVitesse(), tmp[0]);

                mapARemplir.put(type, new Dance(type.getId(), type.getNom(), anim));

            } catch (Exception e) {
                System.err.println("ERREUR : Impossible de charger " + chemin);
            }
        }
    }

    private void ajouterDanseEtRelancer() {
        TypeDanse[] choix = TypeDanse.values();
        TypeDanse random = null;

        // IMPORTANT : On ne choisit jamais IDLE pour le jeu de mémoire
        while (random == null || random == TypeDanse.Defaut) {
            random = choix[new Random().nextInt(choix.length)];
        }

        sequenceAImiter.add(random);

        // Configuration Tour Prof
        etatActuel = EtatJeu.TOUR_PROF;
        indexLectureProf = 0;
        timerAttente = 1.0f; // Pause 1 seconde avant de commencer
        System.out.println("Niveau " + sequenceAImiter.size() + " : Regarde bien !");
    }


    private void gererTourProf(float delta) {
        if (!prof.aFiniDeDanser()) return;

        if (timerAttente > 0) {
            timerAttente -= delta;
            return;
        }

        if (indexLectureProf < sequenceAImiter.size()) {
            TypeDanse aJouer = sequenceAImiter.get(indexLectureProf);
            // La prof utilise SES images (dansesProf)
            prof.jouerDanse(danseuse.get(aJouer));

            indexLectureProf++;
            timerAttente = 0.5f; // Pause entre deux mouvements
        } else {
            etatActuel = EtatJeu.TOUR_JOUEUR;
            indexReponseJoueur = 0;
            System.out.println("A TOI ! (Gauche = Robot, Droite = Salsa)");
        }
    }

    private void gererEntreeJoueur() {
        if (!joueur.aFiniDeDanser()) return;

        TypeDanse tentative = null;

        // CONTROLES
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) tentative = TypeDanse.Floss;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) tentative = TypeDanse.Macarena;

        if (tentative != null) {
            // Le joueur utilise SES images (dansesJoueur)
            joueur.jouerDanse(dansesJoueur.get(tentative));

            TypeDanse bonneReponse = sequenceAImiter.get(indexReponseJoueur);

            if (tentative == bonneReponse) {
                indexReponseJoueur++;
                if (indexReponseJoueur >= sequenceAImiter.size()) {
                    System.out.println("BRAVO !");
                    ajouterDanseEtRelancer();
                }
            } else {
                System.out.println("PERDU ! Appuie sur ENTREE pour recommencer.");
                etatActuel = EtatJeu.GAME_OVER;
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dessiner
        batch.begin();

        // Afficher la map en arrière-plan (remplit tout l'écran)
        batch.draw(textureSheet, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();

        // Mises à jour
        prof.update(delta);
        joueur.update(delta);

        // Logique
        switch (etatActuel) {
            case TOUR_PROF:
                gererTourProf(delta);
                break;
            case TOUR_JOUEUR:
                gererEntreeJoueur();
                break;
            case GAME_OVER:
                if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    sequenceAImiter.clear();
                    ajouterDanseEtRelancer();
                }
                break;
        }

        // Dessin
        batch.begin();
        prof.draw(batch);
        joueur.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Texture t : texturesPourNettoyage) {
            t.dispose();
        }
    }

    @Override public void show() {
    }
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
