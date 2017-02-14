package br.com.procempa.modus.tests.ui;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.ExceptionLog;
import br.com.procempa.modus.ui.ErrorView;

public class ErrorViewTest extends TestCase{
	ExceptionLog log = new ExceptionLog();

	@Override
	protected void setUp() throws Exception {
		log.setException("TestException");
		ErrorView.getInstance(log).setVisible(true);
	}

	public void testMainShow() {
		ErrorView v = (ErrorView) ErrorView.getInstance(log);
		assertNotNull(v);
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}