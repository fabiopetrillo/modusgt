package br.com.procempa.modus.tests.ui;

import junit.framework.TestCase;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.MessageView;

public class MessageViewTest extends TestCase{
	
	@Override
	protected void setUp() throws Exception {
		MessageView.getInstance(IconFactory.createCertificado64(),"Teste da mensagem de aviso").setVisible(true);
	}

	public void testMainShow() {
		assertNotNull(MessageView.getInstance(IconFactory.createCertificado64(),"Teste da mensagem de aviso"));
	}
	
	
	public static void main(String[] args) {
		MessageView.getInstance(IconFactory.createCertificado64(),"Teste da mensagem de aviso").setVisible(true);
		
	}
}
