package io.github.ls2mat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.ls2mat.Dance.Dance;

public class Personnage {
    private float x, y;
    private Dance defaut;
    private Dance currentDance;
    private float stateTime;
    private boolean enTrainDeDanser;

    public Personnage(float x, float y, Dance defaut) {
        this.x = x;
        this.y = y;
        this.defaut = defaut;
        this.enTrainDeDanser = false;
        this.stateTime = 0f;
    }

    public void jouerDanse(Dance nouvelleDanse) {
        this.currentDance = nouvelleDanse;
        this.stateTime = 0f;
        this.enTrainDeDanser = true;
    }

    public void update(float delta) {
        if (enTrainDeDanser && currentDance != null) {
            stateTime += delta;

            // Si l'animation est finie (mode non-looping)
            if (currentDance.getAnimation().isAnimationFinished(stateTime)) {
                enTrainDeDanser = false;
                stateTime = 0;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        TextureRegion frameToDraw;

        if (enTrainDeDanser && currentDance != null) {
            // CAS 1 : On danse -> On récupère l'image animée au temps T
            frameToDraw = currentDance.getAnimation().getKeyFrame(stateTime, false);
        } else {
            // CAS 2 : On ne fait rien -> On prend l'image statique
            // getKeyFrame(0) renvoie toujours la 1ère (et unique) image
            frameToDraw = this.defaut.getAnimation().getKeyFrame(0);
        }

        if (frameToDraw != null) {
            batch.draw(frameToDraw, x, y);
        }
    }

    public boolean aFiniDeDanser() {
        return !enTrainDeDanser;
    }
}
