package Projet;

import java.util.ArrayList;

public class FondMarin
{
    private Point[] Points;
    private ArrayList<Point[]> PointSols;
    private int taille;
    private ArrayList<Float> couleur;
    
    public FondMarin(final int taille) {
        this.taille = taille;
        this.Points = new Point[8];
        this.PointSols = new ArrayList<>();
        this.couleur = new ArrayList<>();
        CreationMonde();
    }
    
    public void CreationMonde() {
        this.Points[0] = new Point((float) this.taille, (float) this.taille, (float) this.taille);
        this.Points[1] = new Point((float) -this.taille, (float) this.taille, (float) this.taille);
        this.Points[2] = new Point((float) -this.taille, (float) -this.taille, (float) this.taille);
        this.Points[3] = new Point((float) this.taille, (float) -this.taille, (float) this.taille);
        this.Points[4] = new Point((float) this.taille, (float) this.taille, (float) -this.taille);
        this.Points[5] = new Point((float) -this.taille, (float) this.taille, (float) -this.taille);
        this.Points[6] = new Point((float) -this.taille, (float) -this.taille, (float) -this.taille);
        this.Points[7] = new Point((float) this.taille, (float) -this.taille, (float) -this.taille);
        CreationSol();
    }

    private void CreationSol(){
        for (int j = 0; j < this.taille + 1; j++) {
            final Point[] p = new Point[this.taille + 1];
            for (int i = 0; i < this.taille + 1; i++) {
                p[i] = new Point(-this.taille + i * 2.0f, -this.taille + (float)Math.random() * 0.24f * this.taille, -this.taille + j * 2.0f);
                this.couleur.add((float)(Math.random() * 0.5) + 0.3f);
            }
            this.PointSols.add(p);
        }
    }
    
    public Point[] getPoints() {
        return this.Points;
    }
    
    public int getTaille() {
        return this.taille;
    }
    
    public ArrayList<Point[]> getPointSols() {
        return this.PointSols;
    }
    
    public ArrayList<Float> getCouleur() {
        return this.couleur;
    }
}
