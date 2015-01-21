package kiloboltgame;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {

	/*	'type' indicates type of tile
	 * 	'speed' indicates speed of tile
	 */
	private int tileX, tileY, speedX, type;
	public Image tileImage;
	
	private Robot robot = StartingClass.getRobot();
	private Background bg = StartingClass.getBg1();
	private Rectangle r;
	
	public Tile(int x,int y,int typeInt) {
		// constructor stub
		tileX = x * 40;
		tileY = y * 40;
		
		type = typeInt;
		
		r = new Rectangle();
		
		/*
		 * The reason for choosing the seemingly arbitrary 
		 * typeInt values
		 * In a typical numpad, there are arrows below 
		 * the numbers 8, 4, 6, and 2. I used these numbers 
		 * to represent dirt tiles with grass on the side 
		 * that the arrows point towards. 5, of course, 
		 * has no arrows and represents a dirt tile with 
		 * no grass on any side.
		 */
		if(type == 5){
			tileImage = StartingClass.tiledirt;
		} else if(type == 8){
			tileImage = StartingClass.tilegrassTop;
		} else if(type == 4){
			tileImage = StartingClass.tilegrassLeft;
		} else if(type == 6){
			tileImage = StartingClass.tilegrassRight;
		} else if(type == 2){
			tileImage = StartingClass.tilegrassBot;
		} else{
			type = 0;
		}
	}
	
	public void update(){
		/*	
		 * As we will not be handling layers of 
		 * background with tiles, we simply remove 
		 * unneeded if statements.
		 */
		speedX = bg.getSpeedX() * 5;
		tileX += speedX;
		r.setBounds(tileX, tileY, 40, 40);
		
		if(type != 0){
			checkVerticalCollision(Robot.rect, Robot.rect2);
		}
	}

	public int getTileX() {
		return tileX;
	}
	
	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

	public void checkVerticalCollision(Rectangle rtop, Rectangle rbot){
		if(rtop.intersects(r)){
			System.out.println("upper collision");
		}
		
		if(rbot.intersects(r)){
			System.out.println("lower collision");
		}
	}
}
