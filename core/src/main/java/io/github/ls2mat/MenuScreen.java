package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MenuScreen implements Screen {

    private Main game;
    private SpriteBatch batch;

    // --- TEXTURES ---
    private Texture textureMapFlou;
    private Texture textureBoutons4;
    private Texture textureLogo;
    private Texture textureMenuSettings;
    private Texture textureBoutonMute;
    private Texture textureBoutonSon;
    private Texture textureBoutonBack;
    private Texture textureBoutonPlus;
    private Texture textureBoutonMoins;

    // Régions de texture pour découper les boutons
    private TextureRegion btnPlayRegion;
    private TextureRegion btnScoreRegion;
    private TextureRegion btnSettingsRegion;
    private TextureRegion btnExitRegion;

    // --- ÉTATS D'AFFICHAGE ---
    private boolean showSettings = false;
    private boolean isMuted = false;

    // --- POSITIONS / HITBOXES ---

    // Position du bouton SON (Mute/unMute) dans Settings
    float btnSonX = 600;
    float btnSonY = 600;
    float btnSonW = 400;
    float btnSonH = 400;

    // Position du bouton PLUS (volume)
    float btnPlusX = 800;
    float btnPlusY = 600;
    float btnPlusW = 400;
    float btnPlusH = 400;

    // Position du bouton MOINS (volume)
    float btnMoinsX = 1000;
    float btnMoinsY = 600;
    float btnMoinsW = 400;
    float btnMoinsH = 400;

    // Position du bouton SETTINGS dans le menu principal
    float btnOuvrirSettingsX = 775;
    float btnOuvrirSettingsY = 25;
    float btnOuvrirSettingsW = 400;
    float btnOuvrirSettingsH = 400;

    // Position du bouton BACK (retour depuis Settings)
    float btnBackX = 300;
    float btnBackY = 600;
    float btnBackW = 400;
    float btnBackH = 400;

    // Position du bouton EXIT (quitter le jeu) du Menu principal
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

        // Découper l'image des 4 boutons (ordre: PLAY, SCORE, SETTINGS, EXIT)
        int btnHeight = textureBoutons4.getHeight() / 4;
        int btnWidth = textureBoutons4.getWidth();

        btnPlayRegion = new TextureRegion(textureBoutons4, 0, 0, btnWidth, btnHeight);
        btnScoreRegion = new TextureRegion(textureBoutons4, 0, btnHeight, btnWidth, btnHeight);
        btnSettingsRegion = new TextureRegion(textureBoutons4, 0, btnHeight * 2, btnWidth, btnHeight);
        btnExitRegion = new TextureRegion(textureBoutons4, 0, btnHeight * 3, btnWidth, btnHeight);

        AudioManager.getInstance().jouerMenuMusic();
    }

    @Override
    public void render(float delta) {

        // Récupérer la résolution actuelle
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // --- POSITIONS RELATIVES BASÉES SUR 1920x1080 ---

        // Boutons dans le menu Settings
        float btnSonX = screenWidth * (600f / 1920f);
        float btnSonY = screenHeight * (600f / 1080f);
        float btnSonW = screenWidth * (400f / 1920f);
        float btnSonH = screenHeight * (400f / 1080f);

        float btnPlusX = screenWidth * (800f / 1920f);
        float btnPlusY = screenHeight * (600f / 1080f);
        float btnPlusW = screenWidth * (400f / 1920f);
        float btnPlusH = screenHeight * (400f / 1080f);

        float btnMoinsX = screenWidth * (1000f / 1920f);
        float btnMoinsY = screenHeight * (600f / 1080f);
        float btnMoinsW = screenWidth * (400f / 1920f);
        float btnMoinsH = screenHeight * (400f / 1080f);

        float btnBackX = screenWidth * (300f / 1920f);
        float btnBackY = screenHeight * (600f / 1080f);
        float btnBackW = screenWidth * (400f / 1920f);
        float btnBackH = screenHeight * (400f / 1080f);

        // Boutons du menu principal (espacés verticalement)
        float btnWidth = screenWidth * (400f / 1920f);
        float btnHeight = screenHeight * (100f / 1080f);
        float btnX = screenWidth * (760f / 1920f);

        float btnPlayX = btnX;
        float btnPlayY = screenHeight * (300f / 1080f);
        float btnPlayW = btnWidth;
        float btnPlayH = btnHeight;

        float btnSettingsX = btnX;
        float btnSettingsY = screenHeight * (180f / 1080f);
        float btnSettingsW = btnWidth;
        float btnSettingsH = btnHeight;

        float btnExitMenuX = btnX;
        float btnExitMenuY = screenHeight * (60f / 1080f);
        float btnExitMenuW = btnWidth;
        float btnExitMenuH = btnHeight;

        // Logo
        float logoX = screenWidth * (510f / 1920f);
        float logoY = screenHeight * (450f / 1080f);
        float logoW = screenWidth * (900f / 1920f);
        float logoH = screenHeight * (600f / 1080f);

        // Menu Settings
        float menuSettingsX = screenWidth * (710f / 1920f);
        float menuSettingsY = screenHeight * (340f / 1080f);
        float menuSettingsW = screenWidth * (500f / 1920f);
        float menuSettingsH = screenHeight * (400f / 1080f);

        // --- GESTION DES ENTRÉES ---

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // --- Si le menu SETTINGS est ouvert ---
            if (showSettings) {

                // Clic sur le bouton Mute/Son
                if (isTextureClicked(btnSonX, btnSonY, btnSonW, btnSonH, mouseX, mouseY)) {
                    isMuted = !isMuted;
                }

                // Clic sur le bouton PLUS (augmenter volume)
                if (!isMuted && isTextureClicked(btnPlusX, btnPlusY, btnPlusW, btnPlusH, mouseX, mouseY)) {
                }

                // Clic sur le bouton MOINS (diminuer volume)
                if (!isMuted && isTextureClicked(btnMoinsX, btnMoinsY, btnMoinsW, btnMoinsH, mouseX, mouseY)) {
                }

                // Clic pour FERMER les settings (bouton BACK)
                if (isTextureClicked(btnBackX, btnBackY, btnBackW, btnBackH, mouseX, mouseY)) {
                    showSettings = false;
                }
            }
            // --- MENU PRINCIPAL ---
            else {
                // Bouton PLAY
                if (isClicked(btnPlayX, btnPlayY, btnPlayW, btnPlayH, mouseX, mouseY)) {
                    game.setScreen(new DanseuseScreen(game));
                }

                // Ouvrir les SETTINGS
                if (isTextureClicked(btnOuvrirSettingsX, btnOuvrirSettingsY, btnOuvrirSettingsW, btnOuvrirSettingsH, mouseX, mouseY)) {
                    showSettings = true;
                }

                // Bouton EXIT (quitter le jeu)
                if (isTextureClicked(btnExitMenuX, btnExitMenuY, btnExitMenuW, btnExitMenuH, mouseX, mouseY)) {
                    Gdx.app.exit();
                }

                if (isTextureClicked(btnPlayX, btnPlayY, btnPlayW, btnPlayH, mouseX, mouseY)) {
                    game.setScreen(new DanseuseScreen(game));
                }
            }
        }

        // --- DESSIN (AFFICHAGE) ---

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Fond (toujours affiché)
        batch.draw(textureMapFlou, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (showSettings) {
            // --- AFFICHER LE MENU SETTINGS ---
            batch.draw(textureMenuSettings, 810, 390, 500, 400);
            batch.draw(textureBoutonBack, btnBackX, btnBackY, btnBackW, btnBackH);

            // Bouton Son
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
            // --- AFFICHER LE MENU PRINCIPAL ---
            batch.draw(textureBoutons4, 775, 100, 400, 400);
            batch.draw(textureLogo, 520, 475, 900, 650);
        }
        batch.end();
    }

    /**
     * Méthode utilitaire pour vérifier si un clic (mouseX, mouseY) est dans un rectangle (x, y, w, h)
     */
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
