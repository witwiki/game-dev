package kiloboltgame;

import java.awt.Rectangle;

public class Enemy {

	// declare variables
	private int power, speedX, centerX, centerY;

	// create a reference to bg object
	private Background bg = StartingClass.getBg1();
	
	//	grabbing robot from startingClass for developing AI
	private Robot robot = StartingClass.getRobot();
	
	//	outlining the collision area
	public Rectangle r = new Rectangle(0, 0, 0, 0);

	// for health system
	public int health = 5;
	
	//	for implementing the AI
	private int movementSpeed;

	// behavioural methods
	public void update() {
		
		//	path-finding AI
		follow();
		
		centerX += speedX;
		speedX = bg.getSpeedX() * 5 + movementSpeed;
		r.setBounds(centerX - 25, centerY - 25, 50, 60);
		
		if(r.intersects(Robot.yellowRed)){
			checkCollision();
		}
	}
	
	private void checkCollision(){
		if(r.intersects(Robot.rect) || r.intersects(Robot.rect2) || r.intersects(Robot.rect3) || r.intersects(Robot.rect4)){
			System.out.println("collision");
		}
	}
	
	public void follow(){
		
		if(centerX < -95 || centerX > 810){
			movementSpeed = 0;
		} else if(Math.abs(robot.getCenterX() - centerX) < 5){
			movementSpeed = 0;			
		} else {
			
			if(robot.getCenterX() >= centerX){
				movementSpeed = 1;
			} else{
				movementSpeed = -1;
			}
		}
	}

	public void die() {

	}

	public void attack() {

	}

	public int getPower() {
		return power;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public Background getBg() {
		return bg;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

}
