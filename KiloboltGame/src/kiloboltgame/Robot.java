package kiloboltgame;

import java.awt.Rectangle;
import java.util.ArrayList;

/*
 * AS A GENERAL RULE:
 * ==================
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

public class Robot {

	// Constants
	final int JUMPSPEED = -15;
	final int MOVESPEED = 5;

	/*
	 * In Java, Class Variables should be private so that only its methods can
	 * change them.
	 */
	private int centerX = 100; // x, y coordinates of robot character's center
	private int centerY = 377;
	private boolean jumped = false; // true if character is in the air and false
									// is on the ground
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean ducked = false;
	private boolean readyToFire = true;

	private static Background bg1 = StartingClass.getBg1();
	private static Background bg2 = StartingClass.getBg2();

	private int speedX = 0; // the rate at which these x and y positions change
	private int speedY = 0;
	
	public static Rectangle rect = new Rectangle(0,0,0,0);	// upper body
	public static Rectangle rect2 = new Rectangle(0,0,0,0); // lower body
	public static Rectangle rect3 = new Rectangle(0,0,0,0);	// left hand
	public static Rectangle rect4 = new Rectangle(0,0,0,0); // right hand
	public static Rectangle yellowRed = new Rectangle(0,0,0,0);	//  yellowRed is the "region of 25" tiles that will be checked for collision
	public static Rectangle footleft = new Rectangle(0,0,0,0);
	public static Rectangle footright = new Rectangle(0,0,0,0);
	
	private ArrayList<Projectile>projectiles = new ArrayList<Projectile>();
	
	public void update() {

		// Moves Character or Scrolls Background accordingly.
		/*
		 * For our game to draw the character in the proper location, centerX
		 * must constantly be updated; however, if the character is not moving
		 * (speedX == 0), then there is no need to update centerX by adding
		 * speed to it. Like in Metal Slug, the character freely moves in the
		 * left half of the screen, but once he starts moving to the right, the
		 * character stays at the same location while the background scrolls.
		 * This is what we are doing here. If speed is zero, then we will not
		 * scroll the background. If the character's centerX coordinate is less
		 * than 150, he can move freely. Else, we will scroll the background and
		 * stop moving the character.
		 */
		if (speedX < 0) {
			centerX += speedX;
		}
		if (speedX == 0 || speedX < 0) {
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);

		}
		if (centerX <= 200 && speedX > 0) {
			centerX += speedX;
		}
		if (speedX > 0 && centerX > 200) {
			//	background will move at 1/5th character's speed
			bg1.setSpeedX(-MOVESPEED/5);
			bg2.setSpeedX(-MOVESPEED/5);
		}

		// Updates Y Position
		/*
		 * Since gravity is always present, the character is constantly being
		 * pushed to the ground. We assume that the ground is located at about
		 * 440 pixels down from the top, and if the character's Y position plus
		 * his Y speed will bring him below the ground, we use the statement:
		 * centerY = 382/GROUND; to manually set the character at a height that will
		 * stop him from moving.
		 */
		centerY += speedY;

		// Handles Jumping
		/*
		 * As we handle falling elsewhere, we can remove 
		 * the first if statement. The if statement that 
		 * I have created prevents small fluctuations in 
		 * speedY from registering as jumps.
		 */
		speedY += 1;
		
		if(speedY > 3){
			jumped = true;
		}
		
		
		// Prevents going beyond X coordinate of 0
		/*
		 * This section just checks if the character is moving beyond the left
		 * edge of the screen and fixes his centerX coordinate at 61 if he tries
		 * to move off the screen.
		 */
		if (centerX + speedX <= 60) { // this represents the robot limit which
										// makes go outside the screen
			centerX = 61; // fixes the robot's centerX at 60 px
		}
		
		rect.setRect(centerX - 34, centerY - 63, 68, 63);
		rect2.setRect(rect.getX(), rect.getY() + 63, 68, 64);
		rect3.setRect(rect.getX() - 26, rect.getY() + 32, 26, 20);
		rect4.setRect(rect.getX() + 68, rect.getY() + 32, 26, 20);
		yellowRed.setRect(centerX - 110, centerY - 110, 180, 180);
		footleft.setRect(centerX - 50, centerY + 20, 50, 15);
		footright.setRect(centerX, centerY + 20, 50, 15);
	}

	public void moveRight() {
		if (ducked == false) {
			speedX = MOVESPEED;
		}
	}

	public void moveLeft() {
		if (ducked == false) {
			speedX = -MOVESPEED;
		}
	}

	public void stopRight() {
		setMovingRight(false);
		stop();
	}

	public void stopLeft() {
		setMovingLeft(false);
		stop();
	}
	
    private void stop() {
        if (isMovingRight() == false && isMovingLeft() == false) {
            speedX = 0;
        }

        if (isMovingRight() == false && isMovingLeft() == true) {
            moveLeft();
        }

        if (isMovingRight() == true && isMovingLeft() == false) {
            moveRight();
        }

    }

	public void jump() {
		if (jumped == false) {
			speedY = JUMPSPEED;
			jumped = true;
		}

	}
	
	public void shoot(){
		if(readyToFire){
			Projectile p = new Projectile(centerX + 50, centerY - 25);	//	50 and 25 are x,y coordinates of p
			projectiles.add(p);
		}
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	
	public boolean isDucked() {
        return ducked;
    }

    public void setDucked(boolean ducked) {
        this.ducked = ducked;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

	public ArrayList getProjectiles() {
		return projectiles;
	}

	public boolean isReadyToFire() {
		return readyToFire;
	}

	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}
}
