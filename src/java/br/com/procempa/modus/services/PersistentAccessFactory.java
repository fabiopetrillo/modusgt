package br.com.procempa.modus.services;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.procempa.modus.session.PersistentAccess;

public class PersistentAccessFactory {
	
	public static final String LOOKUP_NAME = "modus/PersistentAccessBean/remote";
	
	private static PersistentAccess pa;
	
	private static PersistentAccess mainPA;
	
	private PersistentAccessFactory() {}; 

	public static PersistentAccess getInstance() throws NamingException {
		if(pa == null) {
			InitialContext ctx = new InitialContext();
			
			pa = (PersistentAccess) ctx.lookup(LOOKUP_NAME);
		}
		return pa;
	}
	
	public static PersistentAccess getInstanceMain() throws NamingException {
		if(mainPA == null) {
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.jboss.naming.HttpNamingContextFactory");
			props.put(Context.PROVIDER_URL, "http://:8080/invoker/JNDIFactory");
			Context mainCtx = new InitialContext(props);

			mainPA = (PersistentAccess) mainCtx.lookup(LOOKUP_NAME);
		}
		return mainPA;
	}
}
