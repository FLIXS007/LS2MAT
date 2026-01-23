package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** First screen of the application. Displayed after the application is created. */
public class MenuScreen implements Screen {
    private Main game ;
    private SpriteBatch batch ;
    private Texture textureSheet;
    private Texture textureBoutonPlay;
    private Texture textureBoutonScore;
    private Texture textureBoutonSettings;
    private Texture textureBoutonExit;
    private Texture textureBoutonMute;
    private Texture textureBoutonSon;
    private Texture textureBarreSelect;
    public MenuScreen(Main game){
        this.game = game;
    }


    @Override
    public void show() {
        // Prepare your screen here
        batch = new SpriteBatch();

        textureSheet = new Texture("MenuFond.png");
        textureBoutonPlay = new Texture("BoutonPlay.png");
        textureBoutonScore = new Texture("BoutonScore.png");
        textureBoutonSettings = new Texture("BoutonSettings.png");
        textureBoutonExit = new Texture("BoutonExit.png");
        textureBoutonMute = new Texture("BoutonMute.png");
        textureBoutonSon = new Texture("BoutonSon.png");
        textureBarreSelect = new Texture("BarreSelect.png");

    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        // Effacer l'écran (couleur noire)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dessiner
        batch.begin();

        // Afficher la map en arrière-plan (remplit tout l'écran)
        batch.draw(textureSheet, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(textureBoutonPlay, 200, 200, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        /**batch.draw(textureBoutonScore, 300, 300, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(textureBoutonSettings, 300, 300, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(textureBoutonSon, 300, 300, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(textureBoutonMute, 300, 300, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(textureBarreSelect, 300, 300, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(textureBoutonExit, 300, 300, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());**/

        if(Gdx.input.isKeyPressed(65)){
            Gdx.app.exit();
            batch.draw(textureBoutonScore, 500, 300, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }




        // Afficher les autres textures quand tu les auras
        // batch.draw(textureDance, 100, 100);
        // batch.draw(textureDanceuse, 200, 200);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        batch.dispose();
        textureSheet.dispose();
    }
}
