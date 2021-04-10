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
            //System.out.println("Key pressed: zoom in="+zoom);
        }

        if(139 == kc) {
            zoom -= 0.1;
            //System.out.println("Key pressed: zoom out");
        }

        else if (kc == 149) {
            myGLEventListener.setRotX(2);
            myGLEventListener.setTranslx(-0.2f* (float)Math.sin(myGLEventListener.getRotX()*Math.PI/180) );
            myGLEventListener.setTranslz(0.2f* (float)Math.cos(myGLEventListener.getRotX()*Math.PI/180) );
            System.out.println("gauche");
        }
        else if (kc == 151) {
            myGLEventListener.setRotX(-2);
            myGLEventListener.setTranslx(-0.2f* (float)Math.sin(myGLEventListener.getRotX()*Math.PI/180) );
            myGLEventListener.setTranslz(0.2f* (float)Math.cos(myGLEventListener.getRotX()*Math.PI/180) );
            System.out.println("droite");
        }


        else if (kc == 150) {
            myGLEventListener.setTranslx(-0.2f* (float)Math.sin(myGLEventListener.getRotX()*Math.PI/180) );
            myGLEventListener.setTranslz(0.2f* (float)Math.cos(myGLEventListener.getRotX()*Math.PI/180) );
            System.out.println("haut");
        }
        else if (kc == 152) {
            myGLEventListener.setTranslx(0.2f* (float)Math.sin(myGLEventListener.getRotX()*Math.PI/180) );
            myGLEventListener.setTranslz(-0.2f* (float)Math.cos(myGLEventListener.getRotX()*Math.PI/180) );
            System.out.println("bas");
        }



        myGLEventListener.setView_rotx(view_rotx);
        myGLEventListener.setView_roty(view_roty);

    }

    public float getScale() {
        return zoom;
    }

    public void setScale(float scale) {
        this.zoom = scale;
    }

}