package br.com.procempa.modus.ui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import br.com.procempa.modus.utils.SwingUtils;

public class AboutView extends JFrame {

	private static final long serialVersionUID = -7875742792673842607L;
	private static AboutView view;
	
    public AboutView() {
    	setTitle("Sobre o Modus...");
    	setIconImage(IconFactory.createModus().getImage());
    	setResizable(false);
        ImageIcon aboutImage = IconFactory.createSplash();

        int imgWidth = aboutImage.getImage().getWidth(this)+5;
        int imgHeight = aboutImage.getImage().getHeight(this)+25;  
        
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(aboutImage);
        imageLabel.setBackground(Color.WHITE);
        
        add(imageLabel);
        setSize(imgWidth, imgHeight);
        SwingUtils.center(this);
    }
    
    public static AboutView getInstance() {
    	if (view == null) {
    		view = new AboutView();
    	}

        view.setVisible(true);
        view.toFront();

        return view;
    }
}