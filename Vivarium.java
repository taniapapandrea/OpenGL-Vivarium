/*author: Tania Papandrea taniap@bu.edu
 * template provided by: Stan Sclaroff sclaroff@bu.edu
 * This is a vivarium made to contain a spider (predator) and ants (prey).
 */

import javax.media.opengl.*;
import com.jogamp.opengl.util.*;
import java.util.*;

public class Vivarium
{
  private Tank tank;
  private static Spider spider;
  private Ant ant;
  private Ant ant2;
  private Ant ant3;
  private static Food food;
  public static boolean isFood=false;

  public Vivarium()
  {
	food = new Food();
    tank = new Tank( 4.0f, 4.0f, 4.0f );
    //arguments: radius, abdomen length, thorax length, head length, leg length, x position, z position, x direction, z direction, speed
    ant = new Ant(.15, .8, .5, .4, .5, -1.5, 1.5, (float)Math.random(), (float)Math.random(), .02);
    spider = new Spider(.2, .6, .2, .3, .9, -1, -1, (float) Math.random(), (float)Math.random(), .01);
    ant2 = new Ant(.15, .8, .5, .4, .5, 1.5, -1.2, (float)Math.random(), (float)Math.random(), .02);
    ant3 = new Ant(.15, .8, .5, .4, .5, 1, 1, (float)Math.random(), (float)Math.random(), .02);
  }

  //initialize all objects
  public void init( GL2 gl )
  {
    tank.init(gl);
    spider.init(gl);
    ant.init(gl);
    ant2.init(gl);
    ant3.init(gl);
    food.init(gl);
  }
  
  public static void addFood(){
	  Vivarium.isFood=true;
  }

  public void update( GL2 gl )
  {
    tank.update(gl);
    spider.update(gl);
    ant.update(gl);
    ant2.update(gl);
    ant3.update(gl);
    //check if ants are colliding with the food
    if (!ant.isDead){
    	if (( Math.abs(Food.x_location - ant.x_location) < (ant.diameter/4 )) &&
    			(Math.abs(Food.z_location - ant.z_location) < (ant.diameter/4 )) ) {
    		isFood=false;
    	}
    }
    if (!ant2.isDead){
    	if (( Math.abs(Food.x_location - ant2.x_location) < (ant2.diameter/4 )) &&
    			(Math.abs(Food.z_location - ant2.z_location) < (ant2.diameter/4)) ) {
    		isFood=false;
    	}
    }
    if (!ant3.isDead){
    	if (( Math.abs(Food.x_location - ant3.x_location) < (ant3.diameter/4 )) &&
    			(Math.abs(Food.z_location - ant3.z_location) < (ant3.diameter/4)) ) {
    		isFood=false;
    	}
    }
    if (( Math.abs(Food.x_location - Spider.x_location) < (spider.diameter/2 )) &&
    		(Math.abs(Food.z_location - Spider.z_location) < (spider.diameter/2)) ) {
    	isFood=false;
    }
    
    //check if ants are eaten
    if (!ant.isDead){
    	if (( Math.abs(Spider.x_location - ant.x_location) < (ant.diameter/8 + spider.diameter/4)) &&
    			(Math.abs(Spider.z_location - ant.z_location) < (ant.diameter/8 + spider.diameter/4)) ) {
    		ant.isDead=true;
    	}
    }
    if (!ant2.isDead){
    	if (( Math.abs(Spider.x_location - ant2.x_location) < (ant2.diameter/8 + spider.diameter/4)) &&
    			(Math.abs(Spider.z_location - ant2.z_location) < (ant2.diameter/8 + spider.diameter/4)) ) {
    		ant2.isDead=true;
    	}
    }
    if (!ant3.isDead){
    	if (( Math.abs(Spider.x_location - ant3.x_location) < (ant3.diameter/8 + spider.diameter/4)) &&
    			(Math.abs(Spider.z_location - ant3.z_location) < (ant3.diameter/8 + spider.diameter/4)) ) {
    		ant3.isDead=true;
    	}
    }
    //check if ants are colliding with each other
    if ( (Math.abs(ant2.x_location - ant.x_location) < (ant.diameter/4 + ant2.diameter/4)) &&
    		(Math.abs(ant2.z_location - ant.z_location) < (ant.diameter/4 + ant2.diameter/4)) ) {
    	ant.changeDir();
    	ant2.changeDir();
    }
    if ( (Math.abs(ant3.x_location - ant.x_location) < (ant.diameter/4 + ant3.diameter/4)) &&
    		(Math.abs(ant3.z_location - ant.z_location) < (ant.diameter/4 + ant3.diameter/4)) ) {
    	ant.changeDir();
    	ant2.changeDir();
    }
    if ( (Math.abs(ant3.x_location - ant2.x_location) < (ant2.diameter/4 + ant3.diameter/4)) &&
    		(Math.abs(ant3.z_location - ant2.z_location) < (ant2.diameter/4 + ant3.diameter/4)) ) {
    	ant.changeDir();
    	ant2.changeDir();
    }
  }

  //draw the vivarium
  public void draw( GL2 gl )
  {
    tank.draw(gl);
    spider.draw(gl);
    if (Vivarium.isFood){
    	food.draw(gl);
    }
    if (!ant.isDead){
    	ant.draw(gl);
    }
    if (!ant2.isDead){
    	ant2.draw(gl);
    }
    if (!ant3.isDead){
    	ant3.draw(gl);
    }
  }
  
  //methods for the ant to obtain the spider's coordinates
  public static double getSpiderX() {
	  return Spider.x_location;
  }
  public static double getSpiderZ() {
	  return Spider.z_location;
  }
  
  //methods for the ant to obtain the food's coordinates
  public static double getFoodX() {
	  if (isFood){
		  return Food.x_location;
	  } else {
		  return -3;
	  }
  }
  public static double getFoodZ() {
	  if (isFood){
		  return Food.z_location;
	  } else {
		  return -3;
	  }
  }
}