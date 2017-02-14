package br.com.procempa.modus.tests.utils;

import java.awt.Dimension;

import br.com.procempa.modus.utils.SwingUtils;
import junit.framework.TestCase;

public class SwingUtilsTest extends TestCase {
	
	public void testGetScreenSize() {
		Dimension d = SwingUtils.getScreenDimension();
		assertNotNull(d);
	}
}
