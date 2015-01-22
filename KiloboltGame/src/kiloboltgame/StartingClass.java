package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import kiloboltgame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {

	private static Robot robot;
	
	public static Heliboy hb, hb2;
	public static int score = 0;
	private Font font = new Font(null,Font.BOLD,30);
	
	private Image image, currentSprite, character, character2,
			character3, characterDown, characterJumped, 
			background, heliboy, heliboy2, heliboy3, heliboy4,
			heliboy5;
	public static Image tilegrassTop, tilegrassBot, tilegrassLeft, tilegrassRight, tiledirt;
	private URL base;
	private Graphics second;
	private static Background bg1, bg2;
	private Animation anim, hanim;
	private ArrayList<Tile>tilearray = new ArrayList<Tile>();
	
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
		
		tiledirt = getImage(base, "data/tiledirt.png");
		tilegrassTop = getImage(base, "data/tilegrasstop.png");
		tilegrassBot = getImage(base, "data/tilegrassbot.png");
		tilegrassLeft = getImage(base, "data/tilegrassleft.png");
		tilegrassRight = getImage(base, "data/tilegrassright.png");
		
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
	}

	@Override
	public void start() {
		//	INITIALIZATIONS
		// background objects
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);

		//	new robot
		robot = new Robot();

		//	Initialise Tiles
		try{
			loadMap("data/map1.txt");
		} catch(IOException e){
			e.printStackTrace();
		}
		
		//	Heliboy variables
		hb = new Heliboy(340,360);
		hb2 = new Heliboy(700,360);

		Thread thread = new Thread(this);
		thread.start();
	}

	private void loadMap(String filename) throws IOException{
		ArrayList lines = new ArrayList();
		int width = 0;
		int height = 0;
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while(true){
			String line = reader.readLine();
			//	no more lines to read
			if(line == null){
				reader.close();
				break;
			}
			
			if(!line.startsWith("!")){
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}
		height = lines.size();
		
		/*
		 * We check what the character at the index i of 
		 * the current line is (which has index j). 
		 * We then create a new Tile with the parameters 
		 * x position, y position, and type. Since the 
		 * characters we read from the text file are 
		 * characters, not integers (there's a distinction 
		 * between '1' and 1 much like there's a distinction 
		 * between "a" and a), we use a built-in method: 
		 * Character.getNumericValue(ch) to convert it 
		 * to a number.
		 * The purpose of the if(i < line.length()){... statement 
		 * is there to ensure that our i index never searches 
		 * for a character that does not exist. If this is 
		 * not there, we can have all kinds of problems with our 
		 * map.txt so this is very important!
		 */
		for(int j = 0; j < 12; j++){
			String line = (String) lines.get(j);
			for(int i = 0; i < width; i++){
				System.out.println(i + "is i ");
				
				if(i < line.length()){
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}
			}
		}
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
			updateTiles();
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
		paintTiles(g);
		
		ArrayList projectiles = robot.getProjectiles();
		for(int i = 0; i < projectiles.size(); i++){
			Projectile p = (Projectile) projectiles.get(i);
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		
		// draw the character image
		g.drawImage(character, robot.getCenterX() - 61,
				robot.getCenterY() - 63, this);

		//	rectangles for collision detection
		//g.drawRect((int)robot.rect.getX(), (int)robot.rect.getY(), (int)robot.rect.getWidth(), (int)robot.rect.getHeight());
		//g.drawRect((int)robot.rect2.getX(), (int)robot.rect2.getY(), (int)robot.rect2.getWidth(), (int)robot.rect2.getHeight());

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
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(score), 740, 30);
	}
	
	private void updateTiles(){
		
		for(int i = 0; i < tilearray.size(); i++){
			Tile t = (Tile)tilearray.get(i);
			t.update();
		}
	}
	
	private void paintTiles(Graphics g){
		for(int i = 0; i < tilearray.size(); i++){
			Tile t = (Tile) tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(),t.getTileY(),this);
		}
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
				robot.setReadyToFire(false);
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
			
		case KeyEvent.VK_CONTROL:
			robot.setReadyToFire(true);
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
	
	public static Robot getRobot(){
		return robot;
	}
	
	public void animate(){
		anim.update(10);
		hanim.update(50);
	}

}
