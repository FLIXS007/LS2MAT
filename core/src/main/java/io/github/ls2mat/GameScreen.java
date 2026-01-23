package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.ls2mat.Dance.Dance;
import io.github.ls2mat.Dance.TypeDanse;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
    private Main game ;
    private SpriteBatch batch ;
    private Texture textureGame;
    private Texture textureJoueur ;
    private Texture textureDanceuse ;
    private Dance dance;
    private Animator[] tabDanseuse;
    private Animator[] tabJoueur;

    public GameScreen(Main game){
        this.game = game ;
        this.tabDanseuse = new Animator[4];
        this.tabJoueur = new  Animator[4];
        importDance();
    }

    private void importDance(){
        tabDanseuse[0] = new Animator("danseuse/floss.png");
        tabDanseuse[1] = new Animator("danseuse/gangnamStyle.png");
        tabDanseuse[2] = new Animator("danseuse/macarena.png");
        tabDanseuse[3] = new Animator("danseuse/robot.png");

        tabJoueur[0] = new Animator("joueur/floss.png");
        tabJoueur[1] = new Animator("joueur/gangnamStyle.png");
        tabJoueur[2] = new Animator("joueur/macarena.png");
        tabJoueur[3] = new Animator("joueur/robot.png");
    }



    @Override
    public void show() {
        // Prepare your screen here
        batch = new SpriteBatch();

        textureGame = new Texture("maps/Map.png");
        textureDanceuse = new Texture("dance/danseuse/defaut.png");
        textureJoueur = new Texture("dance/joueur/defaut.png");


    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        // Effacer l'écran (couleur noire)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tabDanseuse[1].update(delta);
        tabJoueur[1].update(delta);
        // Dessiner
        batch.begin();

        // Afficher la map en arrière-plan (remplit tout l'écran)
        batch.draw(textureGame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //batch.draw(textureDanceuse,250,300,130,130);
       // batch.draw(textureJoueur,220,80,200,200);
        tabDanseuse[1].draw(batch,250,200,100,300);
        tabJoueur[1].draw(batch,220,10,100,300);
        // Afficher les autres textures quand tu les auras


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
        // Destroy screen's assets here
        batch.dispose();
        textureGame.dispose();
        // Dispose toutes les animations
        for (Animator anim : tabDanseuse) {
            if (anim != null) anim.dispose();
        }
        for (Animator anim : tabJoueur) {
            if (anim != null) anim.dispose();
        }
    }
}
