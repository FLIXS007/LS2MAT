package io.github.ls2mat.Dance;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dance {
    private int id;
    private String nom;
    private Animation<TextureRegion> animation;

    public Dance(int id, String nom, Animation<TextureRegion> animation) {
        this.id = id;
        this.nom = nom;
        this.animation = animation;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public String getNom() {
        return nom;
    }
}
