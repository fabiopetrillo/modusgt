package br.com.procempa.modus.tests.services;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;
import br.com.procempa.modus.session.HelloException;
import br.com.procempa.modus.session.SimpleEJB;

public class SimpleEJBTest extends TestCase {
	
	public void testPrintHello() throws NamingException {
		InitialContext ctx = new InitialContext();
		SimpleEJB bean  = (SimpleEJB) ctx.lookup("modus/SimpleEJBImp/remote");
		System.out.println(bean.getHello());
	}
	
	public void testHelloException() throws NamingException, HelloException {
		InitialContext ctx = new InitialContext();
		SimpleEJB bean  = (SimpleEJB) ctx.lookup("modus/SimpleEJBImp/remote");
		bean.getHelloException();
	}	
}
