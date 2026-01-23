
package io.github.ls2mat.Dance;

    public enum TypeDanse {
        // Liste des danses : (ID, Nom, Fichier Image, Vitesse)
        Defaut(0,"Attente","defaut.png",0f),
        Floss(0,"Floss", "floss.png", 0.1f),
        Macarena(1,"Macarena", "macarena.png",0.2f),
        Robot(2, "Robot", "robot.png", 0.2f),
        GangnamStyle(3,"GangnamStyle","gangnamStyle.png" ,0.1f);

        private final int id;
        private final String nom;
        private final String fichierImage;
        private final float vitesse;

        TypeDanse(int id, String nom, String fichierImage, float vitesse) {
            this.id = id;
            this.nom = nom;
            this.fichierImage = fichierImage;
            this.vitesse = vitesse;
        }

        public int getId() { return id; }
        public String getNom() { return nom; }
        public String getFichierImage() { return fichierImage; }
        public float getVitesse() { return vitesse; }
    }
