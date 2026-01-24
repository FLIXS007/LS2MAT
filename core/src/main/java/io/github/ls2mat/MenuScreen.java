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
    private float volume = 0.7f; // Volume par défaut (70%)

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

        // Lancer la musique du menu
        System.out.println("MenuScreen.show() - Lancement musique menu...");
        AudioManager.getInstance().jouerMenuMusic();
    }

    @Override
    public void render(float delta) {

        // Récupérer la résolution actuelle
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // --- POSITIONS RELATIVES BASÉES SUR 1920x1080 ---

        // Taille des boutons de volume
        float volumeBtnSize = screenWidth * 0.08f; // 8% de l'écran
        float volumeBtnSpacing = screenWidth * 0.02f; // 2% d'espacement

        // Centrage horizontal des boutons de volume
        float centerX = screenWidth * 0.5f;
        float totalWidth = (volumeBtnSize * 3) + (volumeBtnSpacing * 2);
        float startX = centerX - (totalWidth / 2);

        // Boutons de volume (centrés au milieu de l'écran)
        float btnMoinsX = startX;
        float btnMoinsY = screenHeight * 0.45f;
        float btnMoinsW = volumeBtnSize;
        float btnMoinsH = volumeBtnSize;

        float btnSonX = startX + volumeBtnSize + volumeBtnSpacing;
        float btnSonY = screenHeight * 0.45f;
        float btnSonW = volumeBtnSize;
        float btnSonH = volumeBtnSize;

        float btnPlusX = startX + (volumeBtnSize * 2) + (volumeBtnSpacing * 2);
        float btnPlusY = screenHeight * 0.45f;
        float btnPlusW = volumeBtnSize;
        float btnPlusH = volumeBtnSize;

        // Bouton BACK (en bas à gauche)
        float btnBackX = screenWidth * 0.05f;
        float btnBackY = screenHeight * 0.05f;
        float btnBackW = screenWidth * 0.08f;
        float btnBackH = screenWidth * 0.08f;

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

        // Menu Settings (panneau de fond)
        float menuSettingsX = screenWidth * 0.3f;
        float menuSettingsY = screenHeight * 0.25f;
        float menuSettingsW = screenWidth * 0.4f;
        float menuSettingsH = screenHeight * 0.5f;

        // --- GESTION DES ENTRÉES ---

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = screenHeight - Gdx.input.getY();

            if (showSettings) {
                // Clic sur le bouton Mute/Son
                if (isClicked(btnSonX, btnSonY, btnSonW, btnSonH, mouseX, mouseY)) {
                    isMuted = !isMuted;
                    if (isMuted) {
                        AudioManager.getInstance().setVolume(0f);
                        System.out.println("Son coupé");
                    } else {
                        AudioManager.getInstance().setVolume(volume);
                        System.out.println("Son activé à " + (int)(volume * 100) + "%");
                    }
                }

                // Clic sur le bouton PLUS (augmenter volume)
                if (!isMuted && isClicked(btnPlusX, btnPlusY, btnPlusW, btnPlusH, mouseX, mouseY)) {
                    volume = Math.min(1.0f, volume + 0.1f); // Max 100%
                    AudioManager.getInstance().setVolume(volume);
                    System.out.println("Volume: " + (int)(volume * 100) + "%");
                }

                // Clic sur le bouton MOINS (diminuer volume)
                if (!isMuted && isClicked(btnMoinsX, btnMoinsY, btnMoinsW, btnMoinsH, mouseX, mouseY)) {
                    volume = Math.max(0.0f, volume - 0.1f); // Min 0%
                    AudioManager.getInstance().setVolume(volume);
                    System.out.println("Volume: " + (int)(volume * 100) + "%");
                }

                // Clic pour FERMER les settings (bouton BACK)
                if (isClicked(btnBackX, btnBackY, btnBackW, btnBackH, mouseX, mouseY)) {
                    showSettings = false;
                }
            }
            else {
                // Bouton PLAY
                if (isClicked(btnPlayX, btnPlayY, btnPlayW, btnPlayH, mouseX, mouseY)) {
                    game.setScreen(new DanseuseScreen(game));
                }

                // Ouvrir les SETTINGS
                if (isClicked(btnSettingsX, btnSettingsY, btnSettingsW, btnSettingsH, mouseX, mouseY)) {
                    showSettings = true;
                }

                // Bouton EXIT
                if (isClicked(btnExitMenuX, btnExitMenuY, btnExitMenuW, btnExitMenuH, mouseX, mouseY)) {
                    Gdx.app.exit();
                }
            }
        }

        // --- DESSIN (AFFICHAGE) ---

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Fond (toujours affiché)
        batch.draw(textureMapFlou, 0, 0, screenWidth, screenHeight);

        if (showSettings) {
            // --- AFFICHER LE MENU SETTINGS ---

            // Panneau de fond
            batch.draw(textureMenuSettings, menuSettingsX, menuSettingsY, menuSettingsW, menuSettingsH);

            // Bouton BACK en bas à gauche
            batch.draw(textureBoutonBack, btnBackX, btnBackY, btnBackW, btnBackH);

            // Boutons de volume centrés
            if (isMuted) {
                batch.draw(textureBoutonMute, btnSonX, btnSonY, btnSonW, btnSonH);
            }
            else {
                batch.draw(textureBoutonMoins, btnMoinsX, btnMoinsY, btnMoinsW, btnMoinsH);
                batch.draw(textureBoutonSon, btnSonX, btnSonY, btnSonW, btnSonH);
                batch.draw(textureBoutonPlus, btnPlusX, btnPlusY, btnPlusW, btnPlusH);
            }
        }
        else {
            // --- AFFICHER LE MENU PRINCIPAL ---
            // Dessiner chaque bouton séparément
            batch.draw(btnPlayRegion, btnPlayX, btnPlayY, btnPlayW, btnPlayH);
            batch.draw(btnSettingsRegion, btnSettingsX, btnSettingsY, btnSettingsW, btnSettingsH);
            batch.draw(btnExitRegion, btnExitMenuX, btnExitMenuY, btnExitMenuW, btnExitMenuH);
            batch.draw(textureLogo, logoX, logoY, logoW, logoH);
        }
        batch.end();
    }

    /**
     * Vérifie si un clic est dans un rectangle
     */
    private boolean isClicked(float x, float y, float w, float h, float mouseX, float mouseY) {
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
