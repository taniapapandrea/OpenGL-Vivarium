/*
 * Author: Tania Papandrea taniap@bu.edu
 * template by Stan Sclaroff (BU CS480)
 * 
 * creates an Spider for the vivarium using displaylists
 * 
 */

import javax.media.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT;

import java.util.*;

public class Spider
{
  //display lists
  private int thorax;
  private int head;
  private int abdomen;
  private int leg_distal;
  private int leg_middle;
  private int leg_body;
  
  //dimensions
  public double Spider_radius;
  public double thorax_length;
  public double head_length;
  public double abdomen_length;
  public double leg_length;
  public double body_length;
  public double middle_length;
  public double distal_length;
  public double leg_radius;
  public double height;
  public double length;
  public double width;
  public double diameter;
  
  //leg angles
  private float leg1_body_x;
  private float leg1_body_y;
  private float leg1_middle_x;
  private float leg1_middle_y;
  private float leg1_distal_x;
  private float leg1_distal_y;
  private float leg2_body_x;
  private float leg2_body_y;
  private float leg2_middle_x;
  private float leg2_middle_y;
  private float leg2_distal_x;
  private float leg2_distal_y;
  private float leg3_body_x;
  private float leg3_body_y;
  private float leg3_middle_x;
  private float leg3_middle_y;
  private float leg3_distal_x;
  private float leg3_distal_y;
  private float leg4_body_x;
  private float leg4_body_y;
  private float leg4_middle_x;
  private float leg4_middle_y;
  private float leg4_distal_x;
  private float leg4_distal_y;
  private float leg5_body_x;
  private float leg5_body_y;
  private float leg5_middle_x;
  private float leg5_middle_y;
  private float leg5_distal_x;
  private float leg5_distal_y;
  private float leg6_body_x;
  private float leg6_body_y;
  private float leg6_middle_x;
  private float leg6_middle_y;
  private float leg6_distal_x;
  private float leg6_distal_y;
  private float legDirection;
  public int legSpeed;
  
  //head and abdomen angles
  private float abdomen_y;
  private float head_x;
  private int headDir;
  public double headSpeed;
  
  //creature movement
  public static double x_location;
  private float x_direction;
  public float xdir_not_normalized;
  public static double z_location;
  private float z_direction;
  public float zdir_not_normalized;
  public double moveSpeed;

  public Spider(double Spider_radius_, double abdomen_length_, double thorax_length_, double head_length_, double leg_length_, double xpos, double zpos, float xdir, float zdir, double speed)
  {
	  this.moveSpeed = speed;
	  this.thorax_length = thorax_length_;
	  this.Spider_radius = Spider_radius_;
	  this.abdomen_length = abdomen_length_;
	  this.head_length = head_length_;
	  this.leg_length = leg_length_;
	  this.body_length = leg_length*4/9;
	  this.middle_length = leg_length*3/9;
	  this.distal_length = leg_length*2/9;
	  this.leg_radius = leg_length*.043;
	  this.height = Math.max(Spider_radius, leg_length*.4);
	  this.length = abdomen_length + thorax_length + head_length;
	  this.width = Spider_radius_ + 2*body_length + 2*middle_length + 2*distal_length;
	  this.diameter = Math.max(length, width);
	  
	  leg1_body_x=50;
	  leg1_body_y=-160;
	  leg1_middle_x=62;
	  leg1_middle_y=0;
	  leg1_distal_x=-20;
	  leg1_distal_y=-10;
	  leg2_body_x=40;
	  leg2_body_y=-80;
	  leg2_middle_x=-30;
	  leg2_middle_y=60;
	  leg2_distal_x=18;
	  leg2_distal_y=-20;
	  leg3_body_x=-30;
	  leg3_body_y=-30;
	  leg3_middle_x=58;
	  leg3_middle_y=-16;
	  leg3_distal_x=10;
	  leg3_distal_y=10;
	  leg4_body_x=-70;
	  leg4_body_y=34;
	  leg4_middle_x=40;
	  leg4_middle_y=0;
	  leg4_distal_x=20;
	  leg4_distal_y=42;
	  leg5_body_x=60;
	  leg5_body_y=130;
	  leg5_middle_x=28;
	  leg5_middle_y=-60;
	  leg5_distal_x=-30;
	  leg5_distal_y=20;
	  leg6_body_x=32;
	  leg6_body_y=130;
	  leg6_middle_x=60;
	  leg6_middle_y=-2;
	  leg6_distal_x=-20;
	  leg6_distal_y=-10;
	  legDirection = 1;
	  legSpeed=30;
	  
	  abdomen_y = -10;
	  head_x = 0;
	  headDir = 1;
	  headSpeed=30;
	  
	  x_location=xpos;
	  z_location=zpos;
	  xdir_not_normalized = xdir;
	  zdir_not_normalized = zdir;
	  x_direction=(float)(xdir_not_normalized/Math.sqrt(xdir_not_normalized*xdir_not_normalized + zdir_not_normalized*zdir_not_normalized));
	  z_direction=(float)(zdir_not_normalized/Math.sqrt(xdir_not_normalized*xdir_not_normalized + zdir_not_normalized*zdir_not_normalized));
  }

  public void init( GL2 gl )
  {

	GLUT glut = new GLUT();
	
	//displaylist to draw a thorax at the origin
    thorax = gl.glGenLists(1);
    gl.glNewList(thorax, GL2.GL_COMPILE);
    	gl.glPushMatrix();
    		//gl.glTranslated(0, 0, 0);
    		gl.glScalef((float)(Spider_radius/thorax_length), (float)(Spider_radius/thorax_length), 1);
    		glut.glutSolidSphere(thorax_length/2, 36, 18);
	    gl.glPopMatrix();
    gl.glEndList();
    
    //displaylist to draw the body joint of a leg at the origin
    leg_body = gl.glGenLists(1);
    gl.glNewList(leg_body, GL2.GL_COMPILE);
		gl.glPushMatrix();
			gl.glTranslated(0, 0, body_length*.34);
			gl.glRotated(180,0,0,0);
			glut.glutSolidCone(leg_radius, body_length*.34, 36, 18);
		gl.glPopMatrix();
		gl.glPushMatrix();
			gl.glTranslated(0, 0, body_length*.34);
			glut.glutSolidCylinder(leg_radius, body_length*.66, 36, 18);
		gl.glPopMatrix();
	gl.glEndList();
	
	//displaylist to draw the middle joint of a leg at the origin
    leg_middle = gl.glGenLists(1);
    gl.glNewList(leg_middle, GL2.GL_COMPILE);
		gl.glPushMatrix();
			gl.glTranslated(0, 0, middle_length*.34);
			gl.glRotated(180,0,0,0);
			glut.glutSolidCone(leg_radius*.8, middle_length*.34, 36, 18);
		gl.glPopMatrix();
		gl.glPushMatrix();
			gl.glTranslated(0, 0, middle_length*.34);
			glut.glutSolidCylinder(leg_radius*.8, middle_length*.66, 36, 18);
		gl.glPopMatrix();
	gl.glEndList();
	
	//displaylist to draw the distal joint of a leg at the origin
    leg_distal = gl.glGenLists(1);
    gl.glNewList(leg_distal,  GL2.GL_COMPILE);
		gl.glPushMatrix();
			gl.glTranslated(0,0,distal_length*.34);
			gl.glRotated(180,0,0,0);
			glut.glutSolidCone(leg_radius*.6, distal_length*.34, 36, 18);
		gl.glPopMatrix();
		gl.glPushMatrix();
			gl.glTranslated(0,0,distal_length*.34);
			glut.glutSolidCylinder(leg_radius*.6, distal_length*.66, 36, 18);
		gl.glPopMatrix();
	gl.glEndList();
	
	//displaylist to draw a head at the origin
    head = gl.glGenLists(1);
    gl.glNewList(head, GL2.GL_COMPILE);
    	gl.glPushMatrix();
    		gl.glTranslated(0,0, -thorax_length/2);
			double u = head_length*.125;
			gl.glPushMatrix();
				gl.glTranslated(0,0,-u);
				glut.glutSolidSphere(u, 36, 18);
			gl.glPopMatrix();
			gl.glPushMatrix();
				gl.glTranslated(0,0,-4.5*u);
				gl.glScalef((float)(Spider_radius/(7*u)), ((float)(Spider_radius/(7*u))), 1);
				glut.glutSolidSphere(3.5*u, 36, 18);
			gl.glPopMatrix();
			gl.glPushMatrix();
				gl.glTranslated(Spider_radius/2, 0, -4.5*u);
				glut.glutSolidSphere(u, 36, 18);
			gl.glPopMatrix();
			gl.glPushMatrix();
				gl.glTranslated(-Spider_radius/2, 0, -4.5*u);
				glut.glutSolidSphere(u, 36, 18);
			gl.glPopMatrix();
	    gl.glPopMatrix();
    gl.glEndList();

    //displaylist to draw an abdomen at the origin
    abdomen = gl.glGenLists(1);
    gl.glNewList(abdomen,  GL2.GL_COMPILE);
    	gl.glPushMatrix();
    		gl.glTranslated(0,0, thorax_length/2);
			double b = abdomen_length*.1; //unit of measurement
			gl.glPushMatrix();
				gl.glTranslated(0,0,b/2);
				glut.glutSolidSphere(b/2, 36, 18);
			gl.glPopMatrix();
			gl.glPushMatrix();
				gl.glTranslated(0,0,4*b);
				gl.glScalef((float)(Spider_radius/(3*b)), (float)(Spider_radius/(3*b)), 1);
				glut.glutSolidSphere(3*b, 36, 18);
			gl.glPopMatrix();
			gl.glPushMatrix();
				gl.glTranslated(0,0,4*b);
				glut.glutSolidCone(Spider_radius, 6*b, 36, 18);
			gl.glPopMatrix();
	    gl.glPopMatrix();
    gl.glEndList();
    
  }

  public void update( GL2 gl )
  {
	  //leg movement
	  if (leg1_body_x>50 || leg1_body_x<10){
		  legDirection=-legDirection;
	  }
	  leg1_body_x+=legDirection*(-30/legSpeed);
	  leg1_body_y+=legDirection*(30/legSpeed);
	  leg1_middle_x+=legDirection*(20/legSpeed);
	  leg1_middle_y+=legDirection*(20/legSpeed);
	  leg1_distal_x+=legDirection*(10/legSpeed);
	  leg1_distal_y+=legDirection*(30/legSpeed);
	  leg2_body_x+=legDirection*(0/legSpeed);
	  leg2_body_y+=legDirection*(-40/legSpeed);
	  leg2_middle_x+=legDirection*(60/legSpeed);
	  leg2_middle_y+=legDirection*(0/legSpeed);
	  leg2_distal_x+=legDirection*(-20/legSpeed);
	  leg2_distal_y+=legDirection*(30/legSpeed);
	  leg3_body_x+=legDirection*(-30/legSpeed);
	  leg3_body_y+=legDirection*(-20/legSpeed);
	  leg3_middle_x+=legDirection*(16/legSpeed);
	  leg3_middle_y+=legDirection*(0/legSpeed);
	  leg3_distal_x+=legDirection*(20/legSpeed);
	  leg3_distal_y+=legDirection*(0/legSpeed);
	  leg4_body_x+=legDirection*(34/legSpeed);
	  leg4_body_y+=legDirection*(14/legSpeed);
	  leg4_middle_x+=legDirection*(32/legSpeed);
	  leg4_middle_y+=legDirection*(0/legSpeed);
	  leg4_distal_x+=legDirection*(-50/legSpeed);
	  leg4_distal_y+=legDirection*(-26/legSpeed);
	  leg5_body_x+=legDirection*(60/legSpeed);
	  leg5_body_y+=legDirection*(-18/legSpeed);
	  leg5_middle_x+=legDirection*(-60/legSpeed);
	  leg5_middle_y+=legDirection*(0/legSpeed);
	  leg5_distal_x+=legDirection*(20/legSpeed);
	  leg5_distal_y+=legDirection*(20/legSpeed);
	  leg6_body_x+=legDirection*(-50/legSpeed);
	  leg6_body_y+=legDirection*(20/legSpeed);
	  leg6_middle_x+=legDirection*(-30/legSpeed);
	  leg6_middle_y+=legDirection*(-32/legSpeed);
	  leg6_distal_x+=legDirection*(-10/legSpeed);
	  leg6_distal_y+=legDirection*(-30/legSpeed);
	  
	  //head and abdomen movement
	  if (head_x>10 || head_x<-10){
		  headDir=-headDir;
	  }
	  abdomen_y+=headDir*(10/headSpeed);
	  head_x+=headDir*(10/headSpeed);
	  
	  //creature movement
	  z_location+=moveSpeed*z_direction;
	  if (z_location>(2-diameter/2) || z_location<-(2-diameter/2)){
		  z_direction=-z_direction;
	  }
	  x_location+=moveSpeed*x_direction;
	  if (x_location>(2-diameter/2) || x_location<-(2-diameter/2)){
		  x_direction=-x_direction;
	  }
  }

  public void draw( GL2 gl )
  {
	/* draw Spider object with updates */
    gl.glPushMatrix();
    gl.glPushAttrib( GL2.GL_CURRENT_BIT );
    	gl.glRotated(180, 0, 1, 0);
    	//translation for whole creature
    	gl.glTranslated(x_location,-(2-height),z_location);
	    gl.glColor3f( 0.6f, 0.6f, 0.6f); // Orange
		gl.glPushMatrix();
    		gl.glRotated((Math.atan(x_direction/z_direction))*57, 0, 1, 0);
    		if (z_direction>0)
    			gl.glRotated(180, 0, 1, 0);
			gl.glCallList(thorax);

	    	//leg 1 (front left)
	    	gl.glPushMatrix();
	    		gl.glTranslated(-Spider_radius/4,0,-thorax_length/4);
	    		//rotate body
	    		gl.glRotated(leg1_body_x, 1, 0, 0);
	    		gl.glRotated(leg1_body_y, 0, 1, 0);
				gl.glCallList(leg_body);
				//rotate middle
	    		gl.glTranslated(0,0,body_length);
				gl.glRotated(leg1_middle_x, 1, 0, 0);
				gl.glRotated(leg1_middle_y, 0, 1, 0);
				gl.glCallList(leg_middle);
				//rotate distal
	    		gl.glTranslated(0,0, middle_length);
				gl.glRotated(leg1_distal_x, 1, 0, 0);
				gl.glRotated(leg1_distal_y, 0, 1, 0);
				gl.glCallList(leg_distal);
    		gl.glPopMatrix();

    		//leg 2 (middle left)
    		gl.glPushMatrix();
				gl.glTranslated(-Spider_radius/2,0,0);
	    		//rotate body
	    		gl.glRotated(leg2_body_x, 1, 0, 0);
	    		gl.glRotated(leg2_body_y, 0, 1, 0);
				gl.glCallList(leg_body);
				//rotate middle
	    		gl.glTranslated(0,0,body_length);
				gl.glRotated(leg2_middle_x, 1, 0, 0);
				gl.glRotated(leg2_middle_y, 0, 1, 0);
				gl.glCallList(leg_middle);
				//rotate distal
	    		gl.glTranslated(0,0, middle_length);
				gl.glRotated(leg2_distal_x, 1, 0, 0);
				gl.glRotated(leg2_distal_y, 0, 1, 0);
				gl.glCallList(leg_distal);
    		gl.glPopMatrix();
	    
    		//leg 3 (back left)
    		gl.glPushMatrix();
				gl.glTranslated(-Spider_radius/4,0,thorax_length/4);
	    		//rotate body
	    		gl.glRotated(leg3_body_x, 1, 0, 0);
	    		gl.glRotated(leg3_body_y, 0, 1, 0);
				gl.glCallList(leg_body);
				//rotate middle
	    		gl.glTranslated(0,0,body_length);
				gl.glRotated(leg3_middle_x, 1, 0, 0);
				gl.glRotated(leg3_middle_y, 0, 1, 0);
				gl.glCallList(leg_middle);
				//rotate distal
	    		gl.glTranslated(0,0, middle_length);
				gl.glRotated(leg3_distal_x, 1, 0, 0);
				gl.glRotated(leg3_distal_y, 0, 1, 0);
				gl.glCallList(leg_distal);
    		gl.glPopMatrix();
    		
	    	//leg 4 (back right)
	    	gl.glPushMatrix();
				gl.glTranslated(Spider_radius/4,0,thorax_length/4);
	    		//rotate body
	    		gl.glRotated(leg4_body_x, 1, 0, 0);
	    		gl.glRotated(leg4_body_y, 0, 1, 0);
				gl.glCallList(leg_body);
				//rotate middle
	    		gl.glTranslated(0,0,body_length);
				gl.glRotated(leg4_middle_x, 1, 0, 0);
				gl.glRotated(leg4_middle_y, 0, 1, 0);
				gl.glCallList(leg_middle);
				//rotate distal
	    		gl.glTranslated(0,0, middle_length);
				gl.glRotated(leg4_distal_x, 1, 0, 0);
				gl.glRotated(leg4_distal_y, 0, 1, 0);
				gl.glCallList(leg_distal);
	    	gl.glPopMatrix();
	    	
			//leg 5 (middle right)
	    	gl.glPushMatrix();
	    		gl.glTranslated(Spider_radius/2,0,0);
	    		//rotate body
	    		gl.glRotated(leg5_body_x, 1, 0, 0);
	    		gl.glRotated(leg5_body_y, 0, 1, 0);
				gl.glCallList(leg_body);
				//rotate middle
	    		gl.glTranslated(0,0,body_length);
				gl.glRotated(leg5_middle_x, 1, 0, 0);
				gl.glRotated(leg5_middle_y, 0, 1, 0);
				gl.glCallList(leg_middle);
				//rotate distal
	    		gl.glTranslated(0,0, middle_length);
				gl.glRotated(leg5_distal_x, 1, 0, 0);
				gl.glRotated(leg5_distal_y, 0, 1, 0);
				gl.glCallList(leg_distal);
	    	gl.glPopMatrix();
	    	
			//leg 6 (front right)
			gl.glPushMatrix();
	    		gl.glTranslated(Spider_radius/4,0,-thorax_length/4);
	    		//rotate body
	    		gl.glRotated(leg6_body_x, 1, 0, 0);
	    		gl.glRotated(leg6_body_y, 0, 1, 0);
				gl.glCallList(leg_body);
				//rotate middle
	    		gl.glTranslated(0,0,body_length);
				gl.glRotated(leg6_middle_x, 1, 0, 0);
				gl.glRotated(leg6_middle_y, 0, 1, 0);
				gl.glCallList(leg_middle);
				//rotate distal
	    		gl.glTranslated(0,0, middle_length);
				gl.glRotated(leg6_distal_x, 1, 0, 0);
				gl.glRotated(leg6_distal_y, 0, 1, 0);
				gl.glCallList(leg_distal);
			gl.glPopMatrix();
	    
	    	gl.glPushMatrix();
	    		gl.glRotated(head_x, 0, 1, 0);
	    		gl.glCallList(head);
	    	gl.glPopMatrix();
	    
	    	gl.glPushMatrix();
	    		gl.glRotated(abdomen_y, 1, 0, 0);
	    		gl.glCallList(abdomen);
	    	gl.glPopMatrix();
	    
		gl.glPopMatrix();
	    	    
    gl.glPopAttrib();
    gl.glPopMatrix();
  }
}
