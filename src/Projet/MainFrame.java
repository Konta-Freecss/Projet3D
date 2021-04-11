package Projet;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import javax.swing.JFrame;

public class MainFrame extends JFrame
{
    public MainFrame() {
        this.setTitle("Simulation Projet3D");
        this.setSize(1200, 900);
        final GLCapabilities glCapabilities = new GLCapabilities(GLProfile.getDefault());
        final GLCanvas glCanvas = new GLCanvas(glCapabilities);
        final MyGLEventListener glListener = new MyGLEventListener();
        glCanvas.addGLEventListener(glListener);
        this.add(glCanvas);
        final FPSAnimator fpsAnimator = new FPSAnimator(glCanvas, 60);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                fpsAnimator.stop();
                System.exit(0);
            }
        });
        fpsAnimator.start();
    }
}
