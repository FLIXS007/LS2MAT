package io.github.ls2mat.Dance;

public enum DanceListe {
    Floss(0,"Floss", 1f),
    Macarena(0,"Macarena", 1f),
    Robot(0,"Robot", 1f),
    GangnamStyle(0,"Le Robot", 1f);

//    Dab,
//    Moonwalk(),
//    FunK,
//    DiscoPoint,
//    T_pose.

    private final int id;
    private final String nom;
    private final float frameDuration; // Temps entre chaque image (vitesse)

    DanceListe(int id, String nom, float frameDuration) {
        this.id = id;
        this.nom = nom;
        this.frameDuration = frameDuration;
    }


    // ---Getters---
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public float getFrameDuration() {
        return frameDuration;
    }


}
