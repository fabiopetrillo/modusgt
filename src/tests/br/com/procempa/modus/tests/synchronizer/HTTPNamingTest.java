package br.com.procempa.modus.tests.synchronizer;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.session.PersistentAccess;

public class HTTPNamingTest extends TestCase {

	PersistentAccess mainPersistentAccess;

	Properties props;

	@Override
	protected void setUp() {
		props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.HttpNamingContextFactory");
		props.put(Context.PROVIDER_URL,
				"http://10.110.115.231:8080/invoker/JNDIFactory");
	}

	public void testHTTPNaming() throws Exception {
		Context mainCtx = new InitialContext(props);
		mainPersistentAccess = (PersistentAccess) mainCtx
				.lookup("modus/PersistentAccessBean/remote");

		List<Persistent> list = mainPersistentAccess.search("FROM Usuario");
		for (Persistent persist : list) {
			Usuario u = (Usuario) persist;
			System.out.println("Nome: " + u.getNome());
		}

		assertNotNull(list);
	}
}
