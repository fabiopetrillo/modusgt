package br.com.procempa.modus.utils;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;

public class SwingUtils {
	/**
	 * Centraliza a janela na tela
	 * @param w janela a ser centralizada
	 */
	public static void center(Window w) {
		Dimension us = w.getSize(), them = Toolkit.getDefaultToolkit()
				.getScreenSize();
		int newX = (them.width - us.width) / 2;
		int newY = (them.height - us.height) / 2;
		w.setLocation(newX, newY);
	}

	public static Dimension getScreenDimension() {
		int width = GraphicsEnvironment.getLocalGraphicsEnvironment()
		.getDefaultScreenDevice().getDisplayMode().getWidth();
		int height = GraphicsEnvironment.getLocalGraphicsEnvironment()
		.getDefaultScreenDevice().getDisplayMode().getHeight();
		
		Dimension d = new Dimension(width,height);
		return d;
	}
}
