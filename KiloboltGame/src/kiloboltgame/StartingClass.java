package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartingClass extends Applet implements Runnable, KeyListener {

	@Override
	public void init() {
		// 'super' refers to the Super Class - in this case "Applet"
		super.init();
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

			repaint(); // this calls paint
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
			System.out.println("Move down");
			break;

		case KeyEvent.VK_LEFT:
			System.out.println("Move left");
			break;

		case KeyEvent.VK_RIGHT:
			System.out.println("Move right");
			break;

		case KeyEvent.VK_SPACE:
			System.out.println("Jump");
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
			System.out.println("Stop moving down");
			break;

		case KeyEvent.VK_LEFT:
			System.out.println("Stop moving left");
			break;

		case KeyEvent.VK_RIGHT:
			System.out.println("Stop moving right");
			break;

		case KeyEvent.VK_SPACE:
			System.out.println("Stop jumping");
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
