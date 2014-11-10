package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;

public class StartingClass extends Applet implements Runnable {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		// 'super' refers to the Super Class - in this case "Applet"
		super.init();
		setSize(800, 480); // 800 by 480 pixels
		setBackground(Color.BLACK);
		setFocusable(true); // Makes sure that when
							// the game starts, the
		// applet takes focus and that our input goes
		// directly into it

		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		super.start();
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
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
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

}
