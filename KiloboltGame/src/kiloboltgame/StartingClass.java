package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import kiloboltgame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {

	private Robot robot;
	private Heliboy hb, hb2;
	private Image image, currentSprite, character, character2,
			character3, characterDown, characterJumped, 
			background, heliboy, heliboy2, heliboy3, heliboy4,
			heliboy5;
	private URL base;
	private Graphics second;
	private static Background bg1, bg2;
	private Animation anim, hanim;

	@Override
	public void init() {

		setSize(800, 480); // 800 by 480 pixels
		setBackground(Color.BLACK);
		setFocusable(true); // Makes sure that when
							// the game starts, the
		// applet takes focus and that our input goes
		// directly into it

		// Initiates the KeyListener
		addKeyListener(this);

		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");

		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		character = getImage(base, "data/character.png");
		character2 = getImage(base, "data/character2.png");
		character3 = getImage(base, "data/character3.png");
		
		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");
		
		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");

		background = getImage(base, "data/background.png");
		
		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);
		
		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);
		
		currentSprite = character;
		
		//	INITIALIZATIONS
		// background objects
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		//	Heliboy variables
		hb = new Heliboy(340,360);
		hb2 = new Heliboy(700,360);
		//	new robot
		robot = new Robot();
	}

	@Override
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		while (true) {
			robot.update();
			if (robot.isJumped()) {
				currentSprite = characterJumped;
			} else if (robot.isJumped() == false && robot.isDucked() == false) {
				currentSprite = anim.getImage();
			}
			
			//	Bullets
			ArrayList projectiles = robot.getProjectiles();
			for(int i = 0; i < projectiles.size(); i++){
				Projectile p = (Projectile) projectiles.get(i);
				if (p.isVisible() == true){
					p.update();
				} else {
					projectiles.remove(i);
				}
			}
			
			//	Update Objects
			hb.update();
			hb2.update();
			bg1.update();
			bg2.update();
			
			animate();
			repaint(); // this calls paint
			
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {
		// draw the background image
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		
		ArrayList projectiles = robot.getProjectiles();
		for(int i = 0; i < projectiles.size(); i++){
			Projectile p = (Projectile) projectiles.get(i);
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		
		// draw the character image
		g.drawImage(character, robot.getCenterX() - 61,
				robot.getCenterY() - 63, this);
		//	draw the current state of the character's image
		g.drawImage(currentSprite, robot.getCenterX() - 61,
				robot.getCenterY() - 63, this);
		//	draw the heliboy objects
		/*	If we paint 48 pixels lower in both X and Y 
		 * 	(by subtracting 48), then whatever numbers 
		 * 	that we input in the constructor (e.g. 
		 * 	hb = new Heliboy(340, 360);) will represent 
		 * 	the centerX and centerY coordinates of the 
		 * 	newly drawn images.
		 * 	updated: retrieves the current frame's image for heliboy
		 */
		g.drawImage(hanim.getImage(), hb.getCenterX() - 48, hb.getCenterY() - 48, this);
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48, hb2.getCenterY() - 48, this);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		/*
		 * Use a switch to carry out the appropriate action depending on which
		 * button is pressed
		 */
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
			System.out.println("Move up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			if (robot.isJumped() == false) {
				robot.setDucked(true);
				robot.setSpeedX(0);
			}
			break;

		case KeyEvent.VK_LEFT:
			robot.moveLeft();
			robot.setMovingLeft(true);
			break;

		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			robot.setMovingRight(true);
			break;

		case KeyEvent.VK_SPACE:
			robot.jump();
			break;
			
		case KeyEvent.VK_CONTROL:
			if(robot.isDucked() == false && robot.isJumped() == false){
				robot.shoot();
			}
			break;

		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		/*
		 * Use a switch to carry out the appropriate action depending on which
		 * button is released
		 */
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
			System.out.println("Stop moving up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = anim.getImage();
			robot.setDucked(false);
			break;

		case KeyEvent.VK_LEFT:
			robot.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			robot.stopRight();
			break;

		case KeyEvent.VK_SPACE:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	/*	They will let us retrieve the variables bg1 
	 * 	and bg2 for use in other classes.
	 */
	public static Background getBg1(){
		return bg1;
	}

	public static Background getBg2(){
		return bg2;
	}
	
	public void animate(){
		anim.update(10);
		hanim.update(50);
	}

}
