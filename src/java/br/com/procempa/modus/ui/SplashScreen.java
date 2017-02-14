package br.com.procempa.modus.ui;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;

import br.com.procempa.modus.utils.SwingUtils;

public class SplashScreen extends Window {

	private static final long serialVersionUID = -3159743172722820299L;

	private Image splashImage;
    private boolean paintCalled = false;
    
    public SplashScreen(Frame owner) {
        super(owner);
        splashImage = IconFactory.createSplash().getImage();

        int imgWidth = splashImage.getWidth(this);
        int imgHeight = splashImage.getHeight(this);  

        setSize(imgWidth, imgHeight);
        SwingUtils.center(this);
    }
    
    public void paint(Graphics g) {
        g.drawImage(splashImage, 0, 0, this);

        // Notify method splash that the window
        // has been painted.
        if (! paintCalled) {
            paintCalled = true;
            synchronized (this) { notifyAll(); }
        }
    }
    
    public static Frame splash() {
        Frame f = new Frame();
        SplashScreen w = new SplashScreen(f);

        w.setVisible(true);
        w.toFront();

        if (! EventQueue.isDispatchThread()) {
            synchronized (w) {
                if (! w.paintCalled) {
                    try { 
                        w.wait(5000);
                    } catch (InterruptedException e) {}
                }
            }
        }
        return f;
    }
}