package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
    private static AudioManager instance;
    private Music musicMenu;
    private Music musicJeu;
    private Music musicActuelle;

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
        try {
            musicJeu = Gdx.audio.newMusic(Gdx.files.internal("vdef.wav"));
            musicJeu.setLooping(true);
            musicJeu.setVolume(0.7f);
            System.out.println("Musique jeu chargée");
        } catch (Exception e) {
            System.out.println("ERREUR MUSIQUE JEU : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void chargerMenuMusic() {
        try {
            if (musicMenu == null) {
                musicMenu = Gdx.audio.newMusic(Gdx.files.internal("menu.wav"));
                musicMenu.setLooping(true);
                musicMenu.setVolume(0.7f);
                System.out.println("Menu music chargée");
            }
        } catch (Exception e) {
            System.out.println("ERREUR MENU MUSIC : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void jouerMenuMusic() {
        System.out.println("=== PLAY MENU MUSIC ===");
        chargerMenuMusic();

        if (musicActuelle != null && musicActuelle.isPlaying()) {
            System.out.println("Arrêt musique actuelle");
            musicActuelle.stop();
        }

        if (musicMenu != null) {
            System.out.println("Lancement menu.wav");
            musicMenu.play();
            musicActuelle = musicMenu;
            System.out.println("Menu music en cours : " + musicMenu.isPlaying());
        } else {
            System.out.println("ERROR : musicMenu est NULL");
        }
    }

    public void jouerDanseusMusic() {
        System.out.println("=== PLAY DANSEUSE MUSIC ===");

        if (musicMenu != null && musicMenu.isPlaying()) {
            System.out.println("Arrêt menu music");
            musicMenu.stop();
        }

        if (musicJeu != null) {
            System.out.println("Lancement vdef.wav");
            musicJeu.play();
            musicActuelle = musicJeu;
            System.out.println("Jeu music en cours : " + musicJeu.isPlaying());
        }
    }

    public void jouerJoueurMusic() {
        System.out.println("=== PLAY JOUEUR MUSIC ===");

        if (musicMenu != null && musicMenu.isPlaying()) {
            System.out.println("Arrêt menu music");
            musicMenu.stop();
        }

        if (musicJeu != null) {
            System.out.println("Lancement vdef.wav");
            musicJeu.play();
            musicActuelle = musicJeu;
            System.out.println("Jeu music en cours : " + musicJeu.isPlaying());
        }
    }

    public void arreterMusique() {
        if (musicActuelle != null && musicActuelle.isPlaying()) {
            musicActuelle.stop();
        }
    }

    public void setVolume(float volume) {
        if (musicActuelle != null) {
            musicActuelle.setVolume(volume);
        }
    }

    public void dispose() {
        if (musicMenu != null) musicMenu.dispose();
        if (musicJeu != null) musicJeu.dispose();
    }
}
