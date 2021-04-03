package Projet;
import java.util.ArrayList;

public class FondMarin
{
    private Point[] points;
    private ArrayList<Point[]> pointsSol;
    private int taille;
    private ArrayList<Float> couleurSol;
    
    public FondMarin(final int taille) {
        this.taille = taille;
        this.points = new Point[8];
        this.pointsSol = new ArrayList<Point[]>();
        this.couleurSol = new ArrayList<Float>();
        this.init();
    }
    
    public void init() {
        //avant
        this.points[0] = new Point(Float.valueOf(this.taille), Float.valueOf(this.taille), Float.valueOf(this.taille));
        this.points[1] = new Point(Float.valueOf(-this.taille), Float.valueOf(this.taille), Float.valueOf(this.taille));
        this.points[2] = new Point(Float.valueOf(-this.taille), Float.valueOf(-this.taille), Float.valueOf(this.taille));
        this.points[3] = new Point(Float.valueOf(this.taille), Float.valueOf(-this.taille), Float.valueOf(this.taille));
        //arriere
        this.points[4] = new Point(Float.valueOf(this.taille), Float.valueOf(this.taille), Float.valueOf(-this.taille));
        this.points[5] = new Point(Float.valueOf(-this.taille), Float.valueOf(this.taille), Float.valueOf(-this.taille));
        this.points[6] = new Point(Float.valueOf(-this.taille), Float.valueOf(-this.taille), Float.valueOf(-this.taille));
        this.points[7] = new Point(Float.valueOf(this.taille), Float.valueOf(-this.taille), Float.valueOf(-this.taille));

        for (int j = 0; j < this.taille + 1; ++j) {
            final Point[] tab = new Point[this.taille + 1];
            for (int i = 0; i < this.taille + 1; ++i) {
                tab[i] = new Point(-this.taille + i * 2.05f, -this.taille + (float)Math.random() * 0.05f * this.taille, -this.taille + j * 2.05f);
                this.couleurSol.add((float)(Math.random() * 0.5) + 0.3f);
            }
            this.pointsSol.add(tab);
        }
    }
    
    public Point[] getPoints() {
        return this.points;
    }
    
    public int getTaille() {
        return this.taille;
    }
    
    public ArrayList<Point[]> getPointsSol() {
        return this.pointsSol;
    }
    
    public ArrayList<Float> getCouleurSol() {
        return this.couleurSol;
    }
}
