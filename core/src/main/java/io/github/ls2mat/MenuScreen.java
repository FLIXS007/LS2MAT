package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input; // Import important pour les touches
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen {

    private Main game;
    private SpriteBatch batch;

    // Textures

    private Texture textureMapFlou;
    private Texture textureBoutons4;
    private Texture textureLogo;
    private Texture textureMenuSettings;
    private Texture textureBoutonMute;
    private Texture textureBoutonSon;
    private Texture textureBarreSelect;
    private Texture textureBoutonSelect;

    // Conditions d'affichage

    private boolean showSettings = false;
    private boolean showScore = false;

    public MenuScreen(Main game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        textureBoutons4 = new Texture("Boutons4.png");
        textureMapFlou= new Texture("MenuFond.png");
        textureBoutonMute = new Texture("BoutonMute.png");
        textureBoutonSon = new Texture("BoutonSon.png");
        textureBarreSelect = new Texture("BarreSelect.png");
        textureBoutonSelect = new Texture("BoutonSelect.png");
        textureLogo = new Texture("LogoMOVE'IT.png");
        textureMenuSettings = new Texture("MenuSettings.png");
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            showSettings = !showSettings;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(textureMapFlou, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // -- GESTION DE L'AFFICHAGE DES SETTINGS --

        if (showSettings) {

            batch.draw(textureMenuSettings, 725, 300, 1000, 1000);

            // Ajout des prochains boutons.

        } else {
            batch.draw(textureBoutons4, 950, 100, 650, 650);
            batch.draw(textureLogo, 725, 800, 1100, 650);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        textureMapFlou.dispose();
        textureBoutons4.dispose();
        textureLogo.dispose();
        textureMenuSettings.dispose();
        // N'oublie pas de dispose toutes les textures utilisées
    }
}
