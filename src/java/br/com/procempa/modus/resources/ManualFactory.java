package br.com.procempa.modus.resources;

import java.net.URL;

/**
 * F�brica das URLs dos arquivos HTML dos manuais
 * do sistema. 
 * @author petrillo
 *
 */
public class ManualFactory {
	/**
	 * O caminho b�sico dos recursos de manual
	 */
	static final String PATH = "/resources/manual/"; 

	static URL getURL(String resource) {
		return 	Object.class.getResource(PATH+resource);
	}
	
	public static URL createIndex() {
		return getURL("index.html");
	}
}
