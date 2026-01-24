package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
    private static AudioManager instance;
    private Music musicMenu;
    private Music musicJeu;
    private Music musicActuelle;
    private float volumeActuel = 0.7f;
    private boolean estMute = false;

    private AudioManager() {
        chargerMusiques();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    private void chargerMusiques() {
        // Charger la musique du jeu
        try {
            musicJeu = Gdx.audio.newMusic(Gdx.files.internal("vdef.wav"));
            musicJeu.setLooping(true);
            musicJeu.setVolume(volumeActuel);
            System.out.println("✓ Musique jeu chargée (vdef.wav)");
        } catch (Exception e) {
            System.out.println("✗ ERREUR MUSIQUE JEU : " + e.getMessage());
            e.printStackTrace();
        }

        // Charger la musique du menu
        try {
            musicMenu = Gdx.audio.newMusic(Gdx.files.internal("menu.wav"));
            musicMenu.setLooping(true);
            musicMenu.setVolume(volumeActuel);
            System.out.println("✓ Musique menu chargée (menu.wav)");
        } catch (Exception e) {
            System.out.println("✗ ERREUR MUSIQUE MENU : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void jouerMenuMusic() {
        System.out.println("=== LANCEMENT MUSIQUE MENU ===");

        // Arrêter la musique actuelle si elle joue
        if (musicActuelle != null && musicActuelle.isPlaying()) {
            System.out.println("Arrêt musique actuelle");
            musicActuelle.stop();
        }

        // Lancer la musique du menu
        if (musicMenu != null) {
            musicMenu.setVolume(estMute ? 0f : volumeActuel);
            musicMenu.play();
            musicActuelle = musicMenu;
            System.out.println("✓ Menu music en cours : " + musicMenu.isPlaying());
        } else {
            System.out.println("✗ musicMenu est NULL - fichier menu.wav manquant ?");
        }
    }

    public void jouerDanseusMusic() {
        System.out.println("=== LANCEMENT MUSIQUE DANSEUSE ===");
        jouerMusiqueJeu();
    }

    public void jouerJoueurMusic() {
        System.out.println("=== LANCEMENT MUSIQUE JOUEUR ===");
        jouerMusiqueJeu();
    }

    private void jouerMusiqueJeu() {
        // Arrêter la musique du menu si elle joue
        if (musicMenu != null && musicMenu.isPlaying()) {
            System.out.println("Arrêt musique menu");
            musicMenu.stop();
        }

        // Lancer la musique du jeu
        if (musicJeu != null) {
            musicJeu.setVolume(estMute ? 0f : volumeActuel);
            musicJeu.play();
            musicActuelle = musicJeu;
            System.out.println("✓ Musique jeu en cours : " + musicJeu.isPlaying());
        } else {
            System.out.println("✗ musicJeu est NULL - fichier vdef.wav manquant ?");
        }
    }

    public void arreterMusique() {
        if (musicActuelle != null && musicActuelle.isPlaying()) {
            musicActuelle.stop();
            System.out.println("Musique arrêtée");
        }
    }

    public void setVolume(float volume) {
        volumeActuel = Math.max(0f, Math.min(1f, volume)); // Entre 0 et 1

        if (musicActuelle != null) {
            musicActuelle.setVolume(estMute ? 0f : volumeActuel);
        }

        System.out.println("Volume réglé à " + (int)(volumeActuel * 100) + "%");
    }

    public void setMute(boolean mute) {
        estMute = mute;

        if (musicActuelle != null) {
            musicActuelle.setVolume(mute ? 0f : volumeActuel);
        }

        System.out.println(mute ? "Son coupé" : "Son activé");
    }

    public boolean isMute() {
        return estMute;
    }

    public float getVolume() {
        return volumeActuel;
    }

    public void dispose() {
        if (musicMenu != null) {
            musicMenu.dispose();
            System.out.println("Musique menu libérée");
        }
        if (musicJeu != null) {
            musicJeu.dispose();
            System.out.println("Musique jeu libérée");
        }
    }
}
