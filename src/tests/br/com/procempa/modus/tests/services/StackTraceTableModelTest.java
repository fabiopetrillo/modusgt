package br.com.procempa.modus.tests.services;

import junit.framework.TestCase;
import br.com.procempa.modus.exceptionlogviewer.StackTraceTableModel;

public class StackTraceTableModelTest extends TestCase {

	public void testBuildMatrix() throws Exception {
		StackTraceTableModel tm = new StackTraceTableModel("pacote.terter\nmetodo\nclasse\n321\npacote2.terter\nmetodo2\nclasse2\n312321");
		System.out.println(tm.getValueAt(0, 0));
		System.out.println(tm.getValueAt(0, 1));
		System.out.println(tm.getValueAt(0, 2));
		System.out.println(tm.getValueAt(0, 3));
		System.out.println(tm.getValueAt(1, 0));
		System.out.println(tm.getValueAt(1, 1));
		System.out.println(tm.getValueAt(1, 2));
		System.out.println(tm.getValueAt(1, 3));
		
	}
}
