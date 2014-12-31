
package kiloboltgame;

/*
 * AS A GENERAL RULE:
 * =================
 * Whenever we add a new object to our game, we have to do 
 * the following:
 * 1. Create a class for it so that we have a blueprint 
 * for it. (We did this for our background).
 * 2. Within the StartingClass, we must create objects 
 * using this class (first by declaring them as variables 
 * below the class declaration and second by 
 * assigning them values in the start() method).
 * 3. Within the run() method, we must call the object's 
 * update() method.
 * 4. We must paint the new object in the paint() method.
 */

public class Background {
	
	//	creating variables
	private int bgX, bgY, speedX;
	
	//	defining a constructor
	public Background(int x, int y){
		bgX = x;
		bgY = y;
		speedX = 0;
	}
	
	//	update method
	public void update(){
		bgX += speedX;
		
		if(bgX <= -2160){
			bgX += 4320;
		}
	}

	public int getBgX() {
		return bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	

}
