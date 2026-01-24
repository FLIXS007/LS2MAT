package io.github.ls2mat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {
    private static final int FRAME_COLS = 4, FRAME_ROWS = 1;

    private Animation<TextureRegion> walkAnimation;
    private Texture walkSheet;
    private float stateTime;

    public Animator(String fichier) {
        // Charger la texture
        this.walkSheet = new Texture(Gdx.files.internal("dance/" + fichier));
        this.stateTime = 0f;

        // Découper en frames
        TextureRegion[][] tmp = TextureRegion.split(
            walkSheet,
            walkSheet.getWidth() / FRAME_COLS,
            walkSheet.getHeight() / FRAME_ROWS
        );

        // Créer le tableau 1D
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // Créer l'animation (0.15f = vitesse)
        walkAnimation = new Animation<>(0.25f, walkFrames);
    }

    /**
     * Met à jour le temps de l'animation
     */
    public void update(float delta) {
        stateTime += delta;
    }

    /**
     * Dessine l'animation à une position donnée
     */
    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y, width, height);
    }

    /**
     * Réinitialise l'animation au début
     */
    public void reset() {
        stateTime = 0f;
    }

    /**
     * Vérifie si l'animation est terminée (pour animations non-loop)
     */
    public boolean isFinished() {
        return walkAnimation.isAnimationFinished(stateTime);
    }

    public void dispose() {
        walkSheet.dispose();
    }
}
