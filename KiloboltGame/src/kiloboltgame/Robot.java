package kiloboltgame;

import java.awt.Graphics;

public class Robot {
	// In Java, Class Variables should be private so that only its methods can
	// change them.
	private int centerX = 100; // x, y coordinates of robot character's center
	private int centerY = 382;
	private boolean jumped = false; // true if character is in the air and false
									// is on the ground

	private int speedX = 0; // the rate at which these x and y positions change
	private int speedY = 1;

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
		} else if (speedX == 0) {
			System.out.println("Do not scroll the background.");

		} else {
			if (centerX <= 150) { // if robot's centerX is in the left 150 px
				centerX += speedX; // change centerX by adding speedX
			} else {
				System.out.println("Scroll Background Here");
			}
		}

		// Updates Y Position
		/*
		 * Since gravity is always present, the character is constantly being
		 * pushed to the ground. We assume that the ground is located at about
		 * 440 pixels down from the top, and if the character's Y position plus
		 * his Y speed will bring him below the ground, we use the statement:
		 * centerY = 382; to manually set the character at a height that will
		 * stop him from moving.
		 */
		if (centerY + speedY >= 382) { // 382 is when robot is standing on
										// ground
			centerY = 382;
		} else {
			centerY += speedY; // add speedY to determine its new position
		}

		// Handles Jumping
		/*
		 * The Handles jumping section will check the current speed and position
		 * to test whether the character is in mid-jump or on the ground.
		 */
		if (jumped == true) {
			speedY += 1; // while robot is in air, add 1 to its speedY
							// NOTE: this brings robot downwards
			if (centerY + speedY >= 382) {
				centerY = 382;
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
		speedX = 6;
	}

	public void moveLeft() {
		speedX = -6;
	}

	public void stop() {
		speedX = 0;
	}

	public void jump() {
		if (jumped == false) {
			speedY = -15;
			jumped = true;
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
}
