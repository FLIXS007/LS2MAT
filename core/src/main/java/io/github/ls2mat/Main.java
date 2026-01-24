package io.github.ls2mat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {

    @Override
    public void create() {
        System.out.println("=== MAIN CRÉÉ ===");
        System.out.println("Files internes disponibles :");

        try {
            var handle = Gdx.files.internal("vdef.wav");
            System.out.println("vdef.wav trouvé : " + handle.exists());
        } catch (Exception e) {
            System.out.println("ERREUR accès vdef.wav : " + e.getMessage());
        }

        AudioManager.getInstance();
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        AudioManager.getInstance().dispose();
        super.dispose();
    }
}
