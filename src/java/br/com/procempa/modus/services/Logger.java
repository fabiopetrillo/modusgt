package br.com.procempa.modus.services;

import java.net.URL;

import org.apache.log4j.PropertyConfigurator;

public class Logger extends Object {

	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getRootLogger();
	
	static {
	    URL url = Object.class.getResource("/log4j.properties");
	    PropertyConfigurator.configure(url);
	}
	
	public static void info(String message) {
	    logger.info(message);
	}

	public static void debug(String message) {
	    logger.debug(message);
	}
}