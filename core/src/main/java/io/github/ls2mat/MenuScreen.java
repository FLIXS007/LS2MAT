package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen {

    private Main game;
    private SpriteBatch batch;

    private Texture textureMapFlou;
    private Texture textureBoutons4;
    private Texture textureLogo;
    private Texture textureMenuSettings;
    private Texture textureBoutonMute;
    private Texture textureBoutonSon;
    private Texture textureBoutonBack;
    private Texture textureBoutonPlus;
    private Texture textureBoutonMoins;

    private boolean showSettings = false;
    private boolean isMuted = false;

    float btnSonX = 600;
    float btnSonY = 600;
    float btnSonW = 400;
    float btnSonH = 400;

    float btnPlusX = 800;
    float btnPlusY = 600;
    float btnPlusW = 400;
    float btnPlusH = 400;

    float btnMoinsX = 1000;
    float btnMoinsY = 600;
    float btnMoinsW = 400;
    float btnMoinsH = 400;

    float btnOuvrirSettingsX = 775;
    float btnOuvrirSettingsY = 25;
    float btnOuvrirSettingsW = 400;
    float btnOuvrirSettingsH = 400;

    float btnBackX = 300;
    float btnBackY = 600;
    float btnBackW = 400;
    float btnBackH = 400;

    float btnExitMenuX = 775;
    float btnExitMenuY = 100;
    float btnExitMenuW = 400;
    float btnExitMenuH = 100;

    float btnPlayX = 775;
    float btnPlayY = 100;
    float btnPlayW = 400;
    float btnPlayH = 400;

    public MenuScreen(Main game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        textureBoutons4 = new Texture("Boutons4.png");
        textureMapFlou = new Texture("MenuFond.png");
        textureBoutonMute = new Texture("BoutonMute.png");
        textureBoutonSon = new Texture("BoutonSon.png");
        textureLogo = new Texture("LogoMOVE'IT.png");
        textureMenuSettings = new Texture("MenuSettings.png");
        textureBoutonBack = new Texture("BoutonBack.png");
        textureBoutonPlus = new Texture("BoutonPlus.png");
        textureBoutonMoins = new Texture("BoutonMoins.png");

        AudioManager.getInstance().jouerMenuMusic();
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (showSettings) {

                if (isTextureClicked(btnSonX, btnSonY, btnSonW, btnSonH, mouseX, mouseY)) {
                    isMuted = !isMuted;
                }

                if (!isMuted && isTextureClicked(btnPlusX, btnPlusY, btnPlusW, btnPlusH, mouseX, mouseY)) {
                }

                if (!isMuted && isTextureClicked(btnMoinsX, btnMoinsY, btnMoinsW, btnMoinsH, mouseX, mouseY)) {
                }

                if (isTextureClicked(btnBackX, btnBackY, btnBackW, btnBackH, mouseX, mouseY)) {
                    showSettings = false;
                }
            }
            else {

                if (isTextureClicked(btnOuvrirSettingsX, btnOuvrirSettingsY, btnOuvrirSettingsW, btnOuvrirSettingsH, mouseX, mouseY)) {
                    showSettings = true;
                }

                if (isTextureClicked(btnExitMenuX, btnExitMenuY, btnExitMenuW, btnExitMenuH, mouseX, mouseY)) {
                    Gdx.app.exit();
                }

                if (isTextureClicked(btnPlayX, btnPlayY, btnPlayW, btnPlayH, mouseX, mouseY)) {
                    game.setScreen(new DanseuseScreen(game));
                }
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(textureMapFlou, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (showSettings) {
            batch.draw(textureMenuSettings, 810, 390, 500, 400);
            batch.draw(textureBoutonBack, btnBackX, btnBackY, btnBackW, btnBackH);

            if (isMuted) {
                batch.draw(textureBoutonMute, btnSonX, btnSonY, btnSonW, btnSonH);
            }
            else {
                batch.draw(textureBoutonSon, btnSonX, btnSonY, btnSonW, btnSonH);
                batch.draw(textureBoutonPlus, btnPlusX, btnPlusY, btnPlusW, btnPlusH);
                batch.draw(textureBoutonMoins, btnMoinsX, btnMoinsY, btnMoinsW, btnMoinsH);
            }
        }
        else {
            batch.draw(textureBoutons4, 775, 100, 400, 400);
            batch.draw(textureLogo, 520, 475, 900, 650);
        }
        batch.end();
    }

    private boolean isTextureClicked(float x, float y, float w, float h, float mouseX, float mouseY) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
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
        textureBoutonMute.dispose();
        textureBoutonSon.dispose();
        textureBoutonBack.dispose();
        textureBoutonPlus.dispose();
        textureBoutonMoins.dispose();
    }
}
