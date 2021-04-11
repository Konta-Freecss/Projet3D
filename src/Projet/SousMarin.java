package Projet;

import java.util.ArrayList;

public class SousMarin {
    /**
     * Création de variable
     */
    private double pi = Math.PI;
    private double demi = 90*(Math.PI/180);

    private int meridiens;

    private Point[] pointsC1;
    private Point[] pointsC2;

    private ArrayList<Point[]> faceAvant;
    private ArrayList<Point[]> faceArriere;
    private ArrayList<Point[]> Haut;

    private ArrayList<Point[]> helice1;
    private ArrayList<Point[]> helice2;

    private float rayon;
    private float hauteur;


    /**
     * Constructeur du sous-marin
     */
    public SousMarin() {
        this.rayon = 0.5f;
        this.hauteur = 1.5f;
        this.meridiens = 100;
        this.pointsC1 = new Point[this.meridiens + 2];
        this.pointsC2 = new Point[this.meridiens + 2];

        this.faceAvant = new ArrayList<>();
        this.faceArriere = new ArrayList<>();
        this.Haut = new ArrayList<>();
        this.helice1 = new ArrayList<>();
        this.helice2 = new ArrayList<>();

        CreateCylindre();
        CreateSphere();
        CreateHaut();
        CreateHelice();
    }

    public void CreateCylindre(){
        this.pointsC1[0] = new Point(0.0f, 0.0f, this.hauteur );
        this.pointsC2[0] = new Point(0.0f, 0.0f, -this.hauteur);
        for (int i = 0; i <= this.meridiens; i++) {
            this.pointsC1[i + 1] = new Point((float)(this.rayon * Math.cos(pi*2 / this.meridiens * i)), (float)(this.rayon * Math.sin(pi*2 / this.meridiens * i)), this.hauteur );
            this.pointsC2[i + 1] = new Point((float)(this.rayon * Math.cos(pi*2 / this.meridiens * i)), (float)(this.rayon * Math.sin(pi*2 / this.meridiens * i)), -this.hauteur);
        }
    }

    public void CreateSphere(){
        int cpt; // compteur
        // Sphere avant
        for (float i = 0.0f; i <= demi; i += (float)(demi / this.meridiens)) {
            cpt = 0;
            Point[] p = new Point[this.meridiens + 1];
            for (float j = 0.0f; j <= pi*2; j += (float)(pi*2 / this.meridiens)) {
                p[cpt] = new Point((float)(this.rayon * Math.cos(i) * Math.cos(j)), (float)(this.rayon * Math.cos(i) * Math.sin(j)), (float)(this.rayon * Math.sin(i) + this.hauteur));
                cpt++;
            }
            p[cpt] = p[0];
            this.faceAvant.add(p);
        }

        // Sphere faceArriere
        for (float a = 0.0f; a >= -demi; a -= (float)(demi / this.meridiens)) {
            cpt = 0;
            final Point[] p = new Point[this.meridiens + 1];
            for (float b = 0.0f; b <= pi*2; b += (float)(pi*2 / this.meridiens)) {
                p[cpt] = new Point((float)(this.rayon * Math.cos(a) * Math.cos(b)), (float)(this.rayon * Math.cos(a) * Math.sin(b)), (float)(this.rayon * Math.sin(a) - this.hauteur));
                cpt++;
            }
            p[cpt] = p[0];
            this.faceArriere.add(p);
        }
    }

    // Sphere haut
    public void CreateHaut(){
        int cpt;
        for (float i = (float) (-demi); i <= demi; i += (float)(demi / this.meridiens)) {
            cpt = 0;
            Point[] p = new Point[this.meridiens + 1];
            for (float j = 0.0f; j <= pi; j += (float)(Math.PI / this.meridiens)) {
                p[cpt] = new Point((float)(0.5 * this.rayon * Math.cos(i) * Math.cos(j)), (float)(0.8 * this.rayon * Math.cos(i) * Math.sin(j)) + (float)(this.rayon * 0.7), (float)(this.rayon * Math.sin(i)));
                cpt++;
            }
            p[cpt] = p[0];
            this.Haut.add(p);
        }
    }

    public void CreateHelice(){
        int cpt;
        for (float i = (float) (-demi); i <= demi; i += (float)(demi / this.meridiens)) {
            cpt = 0;
            Point[] p1 = new Point[this.meridiens + 1];
            Point[] p2 = new Point[this.meridiens + 1];
            for (float j = 0.0f; j <= pi*2; j += (float)(pi*2 / this.meridiens)) {
                p1[cpt] = new Point((float)(0.1 * this.rayon * Math.cos(i) * Math.cos(j)), (float)(this.rayon * Math.cos(i) * Math.sin(j)), 0.1f * (float)(this.rayon * Math.sin(i)));
                p2[cpt] = new Point((float)(this.rayon * Math.cos(i) * Math.cos(j)), (float)(0.1 * this.rayon * Math.cos(i) * Math.sin(j)), 0.1f * (float)(this.rayon * Math.sin(i)));
                cpt++;
            }
            p1[cpt] = p1[0];
            p2[cpt] = p2[0];

            this.helice1.add(p1);
            this.helice2.add(p2);
        }
    }

    public Point[] getPointsC1() {
        return this.pointsC1;
    }

    public Point[] getPointsC2() {
        return this.pointsC2;
    }

    public int getMeridiens() {
        return this.meridiens;
    }

    public ArrayList<Point[]> getFaceAvant() {
        return this.faceAvant;
    }

    public ArrayList<Point[]> getFaceArriere() {
        return this.faceArriere;
    }

    public ArrayList<Point[]> getHaut() {
        return this.Haut;
    }

    public ArrayList<Point[]> getHelice1() {
        return this.helice1;
    }

    public ArrayList<Point[]> getHelice2() {
        return this.helice2;
    }
}