package kiloboltgame;

import java.awt.Graphics;
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
	final int GROUND = 382;

	/*
	 * In Java, Class Variables should be private so that only its methods can
	 * change them.
	 */
	private int centerX = 100; // x, y coordinates of robot character's center
	private int centerY = GROUND;
	private boolean jumped = false; // true if character is in the air and false
									// is on the ground
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean ducked = false;

	private static Background bg1 = StartingClass.getBg1();
	private static Background bg2 = StartingClass.getBg2();

	private int speedX = 0; // the rate at which these x and y positions change
	private int speedY = 1;
	
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
			bg1.setSpeedX(-MOVESPEED);
			bg2.setSpeedX(-MOVESPEED);
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
		if (centerY + speedY >= GROUND) {
			centerY = GROUND;
		}

		// Handles Jumping
		/*
		 * The Handles jumping section will check the current speed and position
		 * to test whether the character is in mid-jump or on the ground.
		 */
		if (jumped == true) {
			speedY += 1; // while robot is in air, add 1 to its speedY
							// NOTE: this brings robot downwards
			if (centerY + speedY >= GROUND) {
				centerY = GROUND;
				speedY = 0;
				jumped = false;
			}

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
		Projectile p = new Projectile(centerX + 50, centerY - 25);	//	50 and 25 are x,y coordinates of p
		projectiles.add(p);
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
}
