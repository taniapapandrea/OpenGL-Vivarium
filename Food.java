/*
 * Author: Tania Papandrea taniap@bu.edu

 * template by Stan Sclaroff (BU CS480)
 * 
 * creates a donut for the vivarium using displaylists
 * 
 */

import javax.media.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT;

import java.util.*;

public class Food
{
  public int food;
  public static double x_location;
  public static double z_location;

  public Food()
  {
	  x_location = (Math.random())%2;
	  z_location = (Math.random())%2;
  }

  public void init( GL2 gl )
  {
	GLUT glut = new GLUT();
	//display list for a simple cylinder
    food = gl.glGenLists(1);
    gl.glNewList(food, GL2.GL_COMPILE);
    gl.glRotatef(90, 1, 0, 0);
    gl.glTranslated(x_location,z_location,1.95);
    gl.glColor3f( 0.45f, 0.35f, 0.45f);
    gl.glScalef(.5f, .5f, .5f);
    glut.glutSolidTorus(.1, .2, 18, 9);
    gl.glEndList();
    
  }

  public void draw( GL2 gl )
  {
	//draw food
    gl.glPushMatrix();
    gl.glPushAttrib( GL2.GL_CURRENT_BIT );
    gl.glCallList(food);
    gl.glPopAttrib();
    gl.glPopMatrix();
  }
}
