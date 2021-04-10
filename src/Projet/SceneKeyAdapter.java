package Projet;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import java.lang.Math;


class SceneKeyAdapter extends KeyAdapter {

    private float view_rotx, view_roty;
    private float zoom = 1;

    private MyGLEventListener myGLEventListener;


    public SceneKeyAdapter (MyGLEventListener _myGLEventListener) {
        myGLEventListener = _myGLEventListener;
        view_rotx = _myGLEventListener.getView_rotx();
        view_roty = _myGLEventListener.getView_roty();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int kc = e.getKeyCode();

        view_rotx = myGLEventListener.getView_rotx();
        view_roty = myGLEventListener.getView_roty();

        if(140 == kc) {
            zoom += 0.1;
        }

        if(139 == kc) {
            zoom -= 0.1;
        }

        else if (kc == 149) { // flèche de gauche
            myGLEventListener.setRotX(2);   //rotation du sous marin entier vers la gauche (2 est l'angle entre chaque appuie)
            Avancer(true,0.2f);
        }
        else if (kc == 151) { // flèche de droite
            myGLEventListener.setRotX(-2);
            Avancer(true,0.2f);
        }


        else if (kc == 150) { // flèche du haut (avancer)
            Avancer(true,0.2f);
        }
        else if (kc == 152) { // flèche du bas (reculer)
            Avancer(false,0.2f);
        }

        myGLEventListener.setView_rotx(view_rotx);
        myGLEventListener.setView_roty(view_roty);

    }

    public void Avancer(boolean sens, float vitesse) { // Fonction qui gère la translation avant/arrière du sous marin : true pour avancer, false pour reculer
        if (sens==true) {
            myGLEventListener.setTranslx(-vitesse * (float) Math.sin(myGLEventListener.getRotX() * Math.PI / 180)); //On modifie simplement les coordonnée sur X et Z
            myGLEventListener.setTranslz(vitesse * (float) Math.cos(myGLEventListener.getRotX() * Math.PI / 180));
            myGLEventListener.setHelRot(30);    //Et on met la rotation de l'hélice
        }
        else {
            myGLEventListener.setTranslx(vitesse* (float)Math.sin(myGLEventListener.getRotX()*Math.PI/180) );
            myGLEventListener.setTranslz(-vitesse* (float)Math.cos(myGLEventListener.getRotX()*Math.PI/180) );
            myGLEventListener.setHelRot(-30);
        }
    }

    public float getScale() {
        return zoom;
    }

    public void setScale(float scale) {
        this.zoom = scale;
    }

}