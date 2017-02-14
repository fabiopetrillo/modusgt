package br.com.procempa.modus.stationmonitor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.utils.SwingUtils;

public class BlockScreen extends Window {

	private static final long serialVersionUID = 3939012130030024757L;

	private Image blockImage;
	private boolean paintCalled = false;
	private static BlockScreen screen;
	
	private BlockScreen() {
		super(null);
		blockImage = IconFactory.createBlock().getImage();
		setSize(SwingUtils.getScreenDimension());
		setBackground(Color.BLACK);
		SwingUtils.center(this);
	}

	public void paint(Graphics g) {
		g.drawImage(blockImage, (getWidth() - blockImage.getWidth(this)) / 2,
				(getHeight() - blockImage.getHeight(this)) / 2, this);

		if (!paintCalled) {
			paintCalled = true;
			synchronized (this) {
				notifyAll();
			}
		}
	}

	public static BlockScreen splash() {
		if (screen == null) {
			screen = new BlockScreen();

			screen.addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1) {
						screen.setVisible(false);
						UnblockScreen.splash();
					}
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
	
			});
			
			screen.setVisible(true);
			screen.setAlwaysOnTop(true);
			screen.toFront();

			if (!EventQueue.isDispatchThread()) {
				synchronized (screen) {
					if (!screen.paintCalled) {
						try {
							screen.wait(5000);
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}
		screen.setVisible(true);
		return screen;
	}
}