package br.com.procempa.modus.tests.ui;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.ValidationView;

public class ValidationViewTest extends TestCase{
	
	@Override
	protected void setUp() throws Exception {
		List<String> list = new ArrayList<String>();
		list.add("Nome");
		list.add("Idade");
		ValidationView.getInstance(IconFactory.createCertificado64(),"Teste da mensagem de aviso", list).setVisible(true);

	}

	public void testMainShow() {
		List<String> list = new ArrayList<String>();
		list.add("Nome");
		list.add("Idade");
		assertNotNull(ValidationView.getInstance(IconFactory.createCertificado64(),"Teste da mensagem de aviso", list));
	}
	
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("Nome");
		list.add("Idade");
		ValidationView.getInstance(IconFactory.createCertificado64(),"Teste da mensagem de aviso", list).setVisible(true);
	}
}
