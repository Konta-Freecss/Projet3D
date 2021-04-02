package Projet;
import java.util.ArrayList;
import com.jogamp.opengl.GL2;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;
import java.awt.Component;
import com.jogamp.opengl.GLProfile;
import com.jogamp.newt.Window;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GLEventListener;

public class MyGLEventListener implements GLEventListener
{
	private float view_rotx;
	private float view_roty;
	private float scale;
	private boolean zoomModified;
	private float aspect;
	SceneMouseAdapter objectMouse;
	SceneKeyAdapter objectKeys;
	GLUT glut;
	FondMarin fondMarin;

	public MyGLEventListener() {
		this.view_rotx = 20.0f;
		this.view_roty = 30.0f;
		this.scale = 7.0f;
		this.zoomModified = false;
	}

	@Override
	public void init(final GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		final float[] pos = { 5.0f, 5.0f, 5.0f, 0.0f };
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glLightfv(16384, 4611, pos, 0);
		gl.glEnable(2884);
		gl.glEnable(2929);
		this.objectMouse = new SceneMouseAdapter(this);
		this.objectKeys = new SceneKeyAdapter(this);
		if (drawable instanceof Window) {
			final Window window = (Window)drawable;
			window.addMouseListener(this.objectMouse);
			window.addKeyListener(this.objectKeys);
		}
		else if (GLProfile.isAWTAvailable() && drawable instanceof Component) {
			final Component comp = (Component)drawable;
			new AWTMouseAdapter(this.objectMouse, drawable).addTo(comp);
			new AWTKeyAdapter(this.objectKeys, drawable).addTo(comp);
		}
		gl.glEnable(2977);
		gl.glLineWidth(2.0f);
		gl.glEnable(2903);
		this.fondMarin = new FondMarin(50);
		gl.glPolygonMode(1032, 32823);
	}

	@Override
	public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) {
		final GL2 gl = drawable.getGL().getGL2();
		this.aspect = height / (float)width;
		gl.glMatrixMode(5889);
		gl.glLoadIdentity();
		gl.glFrustum(-this.scale, this.scale, -this.scale * this.aspect, this.scale * this.aspect, 5.0, 6000.0);
		gl.glMatrixMode(5888);
	}

	@Override
	public void dispose(final GLAutoDrawable drawable) {
	}

	@Override
	public void display(final GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(16640);
		gl.glPushMatrix();
		this.updateScaleAndRotation(gl, this.aspect, this.view_rotx, this.view_roty);
		this.glut = new GLUT();
		final Point[] points = this.fondMarin.getPoints();

		//Mur
		gl.glColor3d(0.0, 0.4, 1.0);
		gl.glBegin(7);
		gl.glVertex3f(points[3].getX(), points[3].getY(), points[3].getZ());
		gl.glVertex3f(points[2].getX(), points[2].getY(), points[2].getZ());
		gl.glVertex3f(points[1].getX(), points[1].getY(), points[1].getZ());
		gl.glVertex3f(points[0].getX(), points[0].getY(), points[0].getZ());
		gl.glVertex3f(points[7].getX(), points[7].getY(), points[7].getZ());
		gl.glVertex3f(points[3].getX(), points[3].getY(), points[3].getZ());
		gl.glVertex3f(points[0].getX(), points[0].getY(), points[0].getZ());
		gl.glVertex3f(points[4].getX(), points[4].getY(), points[4].getZ());
		gl.glVertex3f(points[2].getX(), points[2].getY(), points[2].getZ());
		gl.glVertex3f(points[6].getX(), points[6].getY(), points[6].getZ());
		gl.glVertex3f(points[5].getX(), points[5].getY(), points[5].getZ());
		gl.glVertex3f(points[1].getX(), points[1].getY(), points[1].getZ());
		gl.glVertex3f(points[6].getX(), points[6].getY(), points[6].getZ());
		gl.glVertex3f(points[7].getX(), points[7].getY(), points[7].getZ());
		gl.glVertex3f(points[4].getX(), points[4].getY(), points[4].getZ());
		gl.glVertex3f(points[5].getX(), points[5].getY(), points[5].getZ());

		//Toit
		gl.glColor3d(0.0, 0.75, 1.0);
		gl.glVertex3f(points[1].getX(), points[1].getY(), points[1].getZ());
		gl.glVertex3f(points[5].getX(), points[5].getY(), points[5].getZ());
		gl.glVertex3f(points[4].getX(), points[4].getY(), points[4].getZ());
		gl.glVertex3f(points[0].getX(), points[0].getY(), points[0].getZ());

		//Sol
		gl.glColor3d(0.0, 0.2, 0.3);
		gl.glVertex3f(points[7].getX(), points[7].getY(), points[7].getZ());
		gl.glVertex3f(points[6].getX(), points[6].getY(), points[6].getZ());
		gl.glVertex3f(points[2].getX(), points[2].getY(), points[2].getZ());
		gl.glVertex3f(points[3].getX(), points[3].getY(), points[3].getZ());
		gl.glEnd();

		//Sol Marin
		final ArrayList<Point[]> sol = this.fondMarin.getPointsSol();
		final ArrayList<Float> couleurs = this.fondMarin.getCouleurSol();
		for (int i = 0; i < this.fondMarin.getTaille() - 1; ++i) {
			for (int j = 0; j < this.fondMarin.getTaille() - 1; ++j) {
				gl.glBegin(4);
				gl.glColor3d(0.0, couleurs.get(i * j), couleurs.get(i * j));
				gl.glVertex3f(sol.get(i + 1)[j].getX(), sol.get(i + 1)[j].getY(), sol.get(i + 1)[j].getZ());
				gl.glVertex3f(sol.get(i + 1)[j + 1].getX(), sol.get(i + 1)[j + 1].getY(), sol.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(sol.get(i)[j].getX(), sol.get(i)[j].getY(), sol.get(i)[j].getZ());
				gl.glEnd();
				gl.glBegin(4);
				gl.glColor3d(0.0, couleurs.get(i * j), couleurs.get(i * j));
				gl.glVertex3f(sol.get(i + 1)[j + 1].getX(), sol.get(i + 1)[j + 1].getY(), sol.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(sol.get(i)[j + 1].getX(), sol.get(i)[j + 1].getY(), sol.get(i)[j + 1].getZ());
				gl.glVertex3f(sol.get(i)[j].getX(), sol.get(i)[j].getY(), sol.get(i)[j].getZ());
				gl.glEnd();
			}
		}
	}


	public void updateScaleAndRotation(final GL2 gl, final float aspect, final float view_rotx, final float view_roty) {
		if (this.zoomModified) {
			gl.glMatrixMode(5889);
			gl.glLoadIdentity();
			gl.glFrustum(-this.scale, this.scale, -this.scale * aspect, this.scale * aspect, 5.0, 6000.0);
			this.zoomModified = false;
			gl.glMatrixMode(5888);
		}
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -50.0f);
		gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
	}

	public float getView_rotx() {
		return this.view_rotx;
	}

	public void setView_rotx(final float view_rotx) {
		this.view_rotx = view_rotx;
	}

	public float getView_roty() {
		return this.view_roty;
	}

	public void setView_roty(final float view_roty) {
		this.view_roty = view_roty;
	}

	public void setScale(final float scale2) {
		this.scale = scale2;
		this.zoomModified = true;
	}

}
