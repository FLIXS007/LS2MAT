package io.github.ls2mat.Dance;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dance {
        private int id;
        private String nom;
        private TextureRegion imageStatique;      // Image fixe (pour icône)
        private Animation<TextureRegion> animation; // L'animation
        private float stateTime;                  // Temps écoulé (compteur)

        public Dance(int id, String nom, TextureRegion imageStatique, Animation<TextureRegion> animation) {
            this.id = id;
            this.nom = nom;
            this.imageStatique = imageStatique;
            this.animation = animation;
            this.stateTime = 0f;
        }

        // À appeler dans le render() pour faire avancer l'animation
        public void update(float delta) {
            stateTime += delta;
        }

        // Récupère la frame exacte à afficher selon le temps écoulé
        public TextureRegion getFrameCourante() {
            // true = l'animation boucle (loop)
            return animation.getKeyFrame(stateTime, true);
        }


    // ---Getters---
    public TextureRegion getImageStatique() { return this.imageStatique; }
    public String getNom() { return this.nom; }
}
