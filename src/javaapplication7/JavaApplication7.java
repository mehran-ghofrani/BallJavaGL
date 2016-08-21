package javaapplication7;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jogamp.opengl.glu.GLUquadricImpl;


public class JavaApplication7 {
    public static Frame f;
    public static GLCanvas c;


    public static void main( String [] args ) {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        final GLCanvas glcanvas = new GLCanvas( glcapabilities );

        glcanvas.addGLEventListener( new GLEventListener() {

            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
            }

            @Override
            public void init( GLAutoDrawable glautodrawable ) {
            }

            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
            }

            @Override
            public void display( GLAutoDrawable glautodrawable ) {
                OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight() );
                OneTriangle.updatePositions();
            }
        });

        final Frame frame = new Frame( "One Triangle AWT" );
        frame.add( glcanvas );
        frame.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent windowevent ) {
                frame.remove( glcanvas );
                frame.dispose();
                System.exit( 0 );
            }
        });
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        frame.setSize( 500, 500 );
        frame.setVisible( true );

        c=glcanvas;
        f=frame;
    }
}


class OneTriangle {

    public static int listName;
    public static GLUT glut=new GLUT();
    public static GLU glu=new GLU();
    public static GLUquadric quad;
    public static double deg;
    public static FloatBuffer indexbuf;
    public static Texture texture;
    public static double x1,x2,y1,y2;
    public static double xx1=2,xx2=2,yy1=1,yy2=1;
    protected static void setup( GL2 gl2, int width, int height ) {


        listName=gl2.glGenLists(1);
        gl2.glNewList(listName, gl2.GL_COMPILE);
                glut.glutSolidSphere(55, 200 , 200);
        gl2.glEndList();

        gl2.glClearColor(0, 0, 0, 0);
        gl2.glClearDepth(1);
        gl2.glEnable(GL.GL_DEPTH_TEST);
//        gl2.glEnable(GL2.GL_NORMALIZE);
        gl2.glShadeModel(gl2.GL_SMOOTH);

        quad=new GLUquadricImpl(gl2, true, null, 0);
//        float[] mat_specular = { 1.0f, 1.0f, 1.0f, 1.0f };
//        float[] mat_shininess = { 50.0f };
//        float[] light_position = { 1.0f, 1.0f, 1.0f, 0.0f };
//
//
//        gl2.glMaterialfv(gl2.GL_FRONT, gl2.GL_SPECULAR, mat_specular);
//        gl2.glMaterialfv(gl2.GL_FRONT, gl2.GL_SHININESS, mat_shininess);

        float[] indices = { 0, 0, 50f, 0 };
        ByteBuffer ifb=ByteBuffer.allocateDirect(indices.length*4);
        ifb.order(ByteOrder.nativeOrder());
        indexbuf=ifb.asFloatBuffer();
        indexbuf.put(indices);
        indexbuf.position(0);
        FloatBuffer testing;
        float[] ver = { 1, 0, 0, 0 };
        ByteBuffer vfb=ByteBuffer.allocateDirect(ver.length*4);
        vfb.order(ByteOrder.nativeOrder());
        testing=vfb.asFloatBuffer();
        testing.put(ver);
        testing.position(0);

        gl2.glLightfv(gl2.GL_LIGHT0, gl2.GL_POSITION, indexbuf);
//        gl2.glLightfv(gl2.GL_LIGHT0, gl2.GL_AMBIENT,testing);
//        gl2.glLightfv(gl2.GL_LIGHT0, gl2.GL_DIFFUSE,testing);
//        gl2.glLightfv(gl2.GL_LIGHT0, gl2.GL_SPECULAR,testing);

        gl2.glColorMaterial(GL.GL_FRONT_AND_BACK, gl2.GL_SPECULAR);
        gl2.glEnable(gl2.GL_COLOR_MATERIAL);


        gl2.glMaterialf(gl2.GL_FRONT, gl2.GL_SHININESS, 100);
//        gl2.glMaterialf(gl2.GL_FRONT, gl2.GL_SPECULAR, 1);
//
//        gl2.glEnable(gl2.GL_LIGHTING);
//        gl2.glEnable(gl2.GL_LIGHT0);
        gl2.glEnable(gl2.GL_DEPTH_TEST);





        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                    deg+=0.1;
                    if(deg>=360)
                        deg=0;
                    JavaApplication7.c.repaint();

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OneTriangle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();


//        File img=new File("C:\\Users\\mgh\\Desktop\\1.bmp");
//
//        try {
//            texture = TextureIO.newTexture(img, false);
//            texture.enable(gl2);
//
//
//            texture.bind(gl2);
//
//        }
//        catch (IOException ex) {
//            Logger.getLogger(OneTriangle.class.getName()).log(Level.SEVERE, null, ex);
//        }

        gl2.glMatrixMode(gl2.GL_PROJECTION);
        gl2.glLoadIdentity();
        glu.gluPerspective(45,1, 1f,444f);
        gl2.glMatrixMode(gl2.GL_MODELVIEW);




    }
    static void updatePositions() {
        final int a=75;
        if(x1<-a||x1>a)
            xx1=-xx1;
        if(y1<-a||y1>a)
            yy1=-yy1;
        if(x2<-a||x2>a)
            xx2=-xx2;
        if(y2<-a||y2>a)
            yy2=-yy2;
        x1+=xx1;
        y1+=yy1;
        x2+=xx2;
        y2+=yy2;
        
        
        
        
            
    
    
    
    
    
    
    }

    protected static void render(GL2 gl2, int width, int height ) {

        gl2.glClear( GL.GL_COLOR_BUFFER_BIT );


        gl2.glClear( GL.GL_DEPTH_BUFFER_BIT );




       

        gl2.glLoadIdentity();
        
gl2.glColor3f(1f, 1f, 1f);

        gl2.glTranslated(x1, y1, -322);

        gl2.glCallList(listName);
                

        
//        
//        gl2.glLoadIdentity();
//        glu.gluLookAt(0, 0, 0, 0, 0, -1, 0, 1, 0);
//        
//        gl2.glTranslated(x2, y2, -322);
//        gl2.glRotatef((float)deg, 0, 1, 0);
//
//        gl2.glCallList(listName);
    }


}