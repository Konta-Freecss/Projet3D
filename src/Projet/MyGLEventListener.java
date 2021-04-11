package Projet;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;

import java.util.ArrayList;


//Applications implement the GLEventListener interface to perform OpenGL drawing via callbacks.
public class MyGLEventListener implements GLEventListener {


	/**
	 * helRot : Comporte l'angle de l'hélice
	 * rotX : Comporte l'angle du sous marin par rapport à l'axe X
	 * transx : Comporte la position du sous marin sur X
	 * transz : Comporte la position du sous marin sur Z
	 */

	GLUT glut;
	GLU glu;
	float helRot=0.0f;
	private float rotX = 0;
	private float transx = 0;
	private float transz = 0;

	//About the camera and the visualization
	SceneMouseAdapter objectMouse;
	SceneKeyAdapter objectKeys;
	private float camera [] = {0.0f, 0.0f, 9.0f};
	private float view_rotx = 0.0f, view_roty = 0.0f;
	private float scale = 1.0f;
	private float aspect;
	private FondMarin fondMarin;
	private SousMarin sousMarin;

	//Predefined colors
	float red[] = { 0.8f, 0.1f, 0.0f, 0.7f };
	float green[] = { 0.0f, 0.8f, 0.2f, 0.7f };
	float blue[] = { 0.2f, 0.2f, 1.0f, 0.7f };

	//Light properties
	float light_ambient[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	float light_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float light_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f};
	float light_position[] = { 10.0f, 10.0f, 10.0f, 0.0f };
	float light_position2[] = { -10.0f, -10.0f, 10.0f, 0.0f };
	float light_position3[] = { 0.0f, 0.0f, -10.0f, 0.0f };


	public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		//For the light and the material
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);

		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light_ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light_diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light_specular, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);

		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, light_ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, light_diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, light_specular, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, light_position2, 0);

		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT, light_ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, light_diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, light_specular, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, light_position3, 0);

		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_LIGHT1);
		gl.glEnable(GL2.GL_LIGHT2);

		//Various tests
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glShadeModel(GL2.GL_SMOOTH);

		objectMouse = new SceneMouseAdapter(this);
		objectKeys = new SceneKeyAdapter(this);

		if (drawable instanceof Window) {
			Window window = (Window) drawable;
			window.addMouseListener(objectMouse);
			window.addKeyListener(objectKeys);
		}

		else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
			java.awt.Component comp = (java.awt.Component) drawable;
			new AWTMouseAdapter(objectMouse, drawable).addTo(comp);
			new AWTKeyAdapter(objectKeys, drawable).addTo(comp);
		}

		gl.glEnable(GL2.GL_NORMALIZE);

		glut =  new GLUT();
		glu =  new GLU();

		/**
		 * Initialisation des deux variables "fondMarin" et "sousMarin"
		 * FondMarin prend en parametre la taille du monde
		 */
		this.fondMarin = new FondMarin(10);
		this.sousMarin = new SousMarin();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2();

		aspect = (float)width / (float)height;
		gl.glViewport(x, y, width, height);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		glu.gluPerspective(60.0f, (float) aspect, 0.1f, 100.0f);


		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		glu.gluLookAt(camera[0], camera[1], camera[2]+scale,
				0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f);

		gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);

		/**
		 * A l'intérieur d'une matrice :
		 * Création du monde avec la distinction du haut ( sans le bas )
		 * Création du fond marin ( le bas du cube )
		 */
		gl.glPushMatrix();
		CreationMonde(gl);
		gl.glPopMatrix();

		/**
		 * A l'intérieur d'une matrice :
		 * Création du sous-marin.
		 * Prise en compte du mouvement et de la rotation du sous-marin
		 */
		gl.glPushMatrix();
		gl.glTranslatef(transx,0.0f,-transz); // Avancer et reculer
		gl.glRotatef(rotX,0,1,0);	 // Rotation du sous marin
		CreationSousMarin(gl);
		gl.glPopMatrix();

	}

	public void CreationMonde(GL2 gl){
		Point[] points = this.fondMarin.getPoints();

		/**
		 * Affichage des parois du cube
		 */
		gl.glColor3d(0.2, 0.5, 1.0);
		gl.glBegin(GL2.GL_QUADS);
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

		/**
		 * Affichage du haut du cube
		 */
		gl.glColor3d(0.1, 0.1, 1.0);
		gl.glVertex3f(points[1].getX(), points[1].getY(), points[1].getZ());
		gl.glVertex3f(points[5].getX(), points[5].getY(), points[5].getZ());
		gl.glVertex3f(points[4].getX(), points[4].getY(), points[4].getZ());
		gl.glVertex3f(points[0].getX(), points[0].getY(), points[0].getZ());
		gl.glEnd();

		/**
		 * Récupération des données et affichage des différent points que comport le fond marin
		 */
		ArrayList<Point[]> sol = this.fondMarin.getPointSols();
		ArrayList<Float> colors = this.fondMarin.getCouleur();
		for (int i = 0; i < this.fondMarin.getTaille() ; i++) {
			for (int j = 0; j < this.fondMarin.getTaille() ; j++) {

				if(i==0 || j==0){
					gl.glColor3d(colors.get(i + j), colors.get(i +j), colors.get(i +j));
				}else {
					gl.glColor3d(colors.get(i * j), colors.get(i * j), colors.get(i * j));
				}

				gl.glBegin(GL2.GL_TRIANGLES);
				gl.glVertex3f(sol.get(i + 1)[j].getX(), sol.get(i + 1)[j].getY(), sol.get(i + 1)[j].getZ());
				gl.glVertex3f(sol.get(i + 1)[j + 1].getX(), sol.get(i + 1)[j + 1].getY(), sol.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(sol.get(i)[j].getX(), sol.get(i)[j].getY(), sol.get(i)[j].getZ());
				gl.glEnd();
				gl.glBegin(GL2.GL_TRIANGLES);

				if(i==0 || j==0){
					gl.glColor3d(colors.get(i + j), colors.get(i +j), colors.get(i +j));
				}else {
					gl.glColor3d(colors.get(i * j), colors.get(i * j), colors.get(i * j));
				}

				gl.glVertex3f(sol.get(i + 1)[j + 1].getX(), sol.get(i + 1)[j + 1].getY(), sol.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(sol.get(i)[j + 1].getX(), sol.get(i)[j + 1].getY(), sol.get(i)[j + 1].getZ());
				gl.glVertex3f(sol.get(i)[j].getX(), sol.get(i)[j].getY(), sol.get(i)[j].getZ());
				gl.glEnd();
			}
		}
	}

	public void CreationSousMarin(GL2 gl) {
		/**
		 * Affichage du cylindre du sous-marin
		 */
		gl.glColor3d(0.2, 0.2, 0.2);
		for (int i = 0; i < this.sousMarin.getMeridiens(); i++) {

			gl.glBegin(GL2.GL_QUADS);
			gl.glVertex3f(this.sousMarin.getPointsC2()[i + 1].getX(), this.sousMarin.getPointsC2()[i + 1].getY(), this.sousMarin.getPointsC2()[i + 1].getZ());
			gl.glVertex3f(this.sousMarin.getPointsC2()[i + 2].getX(), this.sousMarin.getPointsC2()[i + 2].getY(), this.sousMarin.getPointsC2()[i + 2].getZ());
			gl.glVertex3f(this.sousMarin.getPointsC1()[i + 2].getX(), this.sousMarin.getPointsC1()[i + 2].getY(), this.sousMarin.getPointsC1()[i + 2].getZ());
			gl.glVertex3f(this.sousMarin.getPointsC1()[i + 1].getX(), this.sousMarin.getPointsC2()[i + 1].getY(), this.sousMarin.getPointsC1()[i + 1].getZ());
			gl.glEnd();
			gl.glBegin(GL2.GL_QUADS);
			gl.glVertex3f(this.sousMarin.getPointsC1()[i + 1].getX(), this.sousMarin.getPointsC2()[i + 1].getY(), this.sousMarin.getPointsC1()[i + 1].getZ());
			gl.glVertex3f(this.sousMarin.getPointsC1()[i + 2].getX(), this.sousMarin.getPointsC1()[i + 2].getY(), this.sousMarin.getPointsC1()[i + 2].getZ());
			gl.glVertex3f(this.sousMarin.getPointsC2()[i + 2].getX(), this.sousMarin.getPointsC2()[i + 2].getY(), this.sousMarin.getPointsC2()[i + 2].getZ());
			gl.glVertex3f(this.sousMarin.getPointsC2()[i + 1].getX(), this.sousMarin.getPointsC2()[i + 1].getY(), this.sousMarin.getPointsC2()[i + 1].getZ());
			gl.glEnd();
		}

		/**
		 * Affichage des spheres avant et arriere du sous-marin
		 */
		ArrayList<Point[]> faceAvant = this.sousMarin.getFaceAvant();
		ArrayList<Point[]> faceArriere = this.sousMarin.getFaceArriere();
		for (int i = 0; i < this.sousMarin.getMeridiens() - 1; i++) {
			for (int j = 0; j < this.sousMarin.getMeridiens(); j++) {
				// SPHERE AVANT
				gl.glColor3d(0.2, 0.2, 0.2);
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3f(faceAvant.get(i)[j].getX(), faceAvant.get(i)[j].getY(), faceAvant.get(i)[j].getZ());
				gl.glVertex3f(faceAvant.get(i)[j + 1].getX(), faceAvant.get(i)[j + 1].getY(), faceAvant.get(i)[j + 1].getZ());
				gl.glVertex3f(faceAvant.get(i + 1)[j + 1].getX(), faceAvant.get(i + 1)[j + 1].getY(), faceAvant.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(faceAvant.get(i + 1)[j].getX(), faceAvant.get(i + 1)[j].getY(), faceAvant.get(i + 1)[j].getZ());
				gl.glEnd();
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3f(faceAvant.get(i + 1)[j].getX(), faceAvant.get(i + 1)[j].getY(), faceAvant.get(i + 1)[j].getZ());
				gl.glVertex3f(faceAvant.get(i + 1)[j + 1].getX(), faceAvant.get(i + 1)[j + 1].getY(), faceAvant.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(faceAvant.get(i)[j + 1].getX(), faceAvant.get(i)[j + 1].getY(), faceAvant.get(i)[j + 1].getZ());
				gl.glVertex3f(faceAvant.get(i)[j].getX(), faceAvant.get(i)[j].getY(), faceAvant.get(i)[j].getZ());
				gl.glEnd();

				// SPHERE ARRIERE
				gl.glColor3d(0.2, 0.2, 0.2);
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3f(faceArriere.get(i)[j].getX(), faceArriere.get(i)[j].getY(), faceArriere.get(i)[j].getZ());
				gl.glVertex3f(faceArriere.get(i)[j + 1].getX(), faceArriere.get(i)[j + 1].getY(), faceArriere.get(i)[j + 1].getZ());
				gl.glVertex3f(faceArriere.get(i + 1)[j + 1].getX(), faceArriere.get(i + 1)[j + 1].getY(), faceArriere.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(faceArriere.get(i + 1)[j].getX(), faceArriere.get(i + 1)[j].getY(), faceArriere.get(i + 1)[j].getZ());
				gl.glEnd();
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3f(faceArriere.get(i + 1)[j].getX(), faceArriere.get(i + 1)[j].getY(), faceArriere.get(i + 1)[j].getZ());
				gl.glVertex3f(faceArriere.get(i + 1)[j + 1].getX(), faceArriere.get(i + 1)[j + 1].getY(), faceArriere.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(faceArriere.get(i)[j + 1].getX(), faceArriere.get(i)[j + 1].getY(), faceArriere.get(i)[j + 1].getZ());
				gl.glVertex3f(faceArriere.get(i)[j].getX(), faceArriere.get(i)[j].getY(), faceArriere.get(i)[j].getZ());
				gl.glEnd();
			}
		}
		/**
		 * Affichage de la sphere qui dessigne le toit du sous-marin
		 */
		gl.glColor3d(0.3, 0.3, 0.3);
		ArrayList<Point[]> toit = this.sousMarin.getHaut();
		for (int i = 0; i < this.sousMarin.getMeridiens() * 2 - 1; i++) {
			for (int j = 0; j < this.sousMarin.getMeridiens(); j++) {
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3f(toit.get(i)[j].getX(), toit.get(i)[j].getY(), toit.get(i)[j].getZ());
				gl.glVertex3f(toit.get(i)[j + 1].getX(), toit.get(i)[j + 1].getY(), toit.get(i)[j + 1].getZ());
				gl.glVertex3f(toit.get(i + 1)[j + 1].getX(), toit.get(i + 1)[j + 1].getY(), toit.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(toit.get(i + 1)[j].getX(), toit.get(i + 1)[j].getY(), toit.get(i + 1)[j].getZ());
				gl.glEnd();
			}
		}

		/**
		 * Création d'une nouvelle matrice pour l'implémentation de l'helice et de sa rotation
		 */
		gl.glPushMatrix();
		gl.glTranslatef(0.0f,0.0f,2.07f); //Positionnement de l'hélice sur le sous marin
		gl.glRotatef(helRot,0,0,1);		//Gestion de la rotation de l'hélice
		CreationHelice(gl);
		gl.glPopMatrix();

	}

	/**
	 * Affichage de l'helice
	 */
	public void CreationHelice(GL2 gl){
		ArrayList<Point[]> helice1 = this.sousMarin.getHelice1();
		ArrayList<Point[]> helice2 = this.sousMarin.getHelice2();
		gl.glColor3d(0.3, 0.3, 0.3);

		for (int i = 0; i < this.sousMarin.getMeridiens() * 2 - 1; i++) {
			for (int j = 0; j < this.sousMarin.getMeridiens(); j++) {
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3f(helice1.get(i)[j].getX(), helice1.get(i)[j].getY(), helice1.get(i)[j].getZ());
				gl.glVertex3f(helice1.get(i)[j + 1].getX(), helice1.get(i)[j + 1].getY(), helice1.get(i)[j + 1].getZ());
				gl.glVertex3f(helice1.get(i + 1)[j + 1].getX(), helice1.get(i + 1)[j + 1].getY(), helice1.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(helice1.get(i + 1)[j].getX(), helice1.get(i + 1)[j].getY(), helice1.get(i + 1)[j].getZ());
				gl.glEnd();
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3f(helice2.get(i)[j].getX(), helice2.get(i)[j].getY(), helice2.get(i)[j].getZ());
				gl.glVertex3f(helice2.get(i)[j + 1].getX(), helice2.get(i)[j + 1].getY(), helice2.get(i)[j + 1].getZ());
				gl.glVertex3f(helice2.get(i + 1)[j + 1].getX(), helice2.get(i + 1)[j + 1].getY(), helice2.get(i + 1)[j + 1].getZ());
				gl.glVertex3f(helice2.get(i + 1)[j].getX(), helice2.get(i + 1)[j].getY(), helice2.get(i + 1)[j].getZ());
				gl.glEnd();
			}
		}
	}

	public float getView_rotx() {
		return view_rotx;
	}
	public void setView_rotx(float view_rotx) {
		this.view_rotx = view_rotx;
	}
	public float getView_roty() {
		return view_roty;
	}
	public void setView_roty(float view_roty) {
		this.view_roty = view_roty;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale2) {
		this.scale = scale2;
	}

	public void setRotX(float add) {
		if (rotX+add == 360 || rotX+add == -360) {
			rotX = 0;
		}
		else {rotX+=add;}
	}
	public float getRotX() {
		return rotX;
	}

	public void setTranslx(float add) {
		this.transx = transx+add;
	} 	// Met à jour la position sur X
	public void setTranslz(float add) {
		this.transz = transz+add;
	}	// Met à jour la position sur Z
	public void setHelRot(float add) {
		this.helRot = helRot+add;
	}	//Met à jour la rotation de l'hélice

}