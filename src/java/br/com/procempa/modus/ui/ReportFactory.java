package br.com.procempa.modus.ui;

import java.awt.Component;
import java.net.URL;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


public class ReportFactory {

	static final String PATH = "resources/reports/";

	static ReportFactory factory;

	private ReportFactory() {
		//Singleton Pattern
	}

	public static ReportFactory getInstance() {
			factory = new ReportFactory();
		return factory;
	}

	URL getURL(String resource) {
		//Deve-se utilizar o ClassLoader de uma instancia de objeto,
		//por causa do Java Web Start.
		return this.getClass().getClassLoader().getResource(PATH + resource);
	}
	
	public Component getComponent(String name, HashMap params, JRDataSource dataSource){
		JasperPrint jasperPrint = null;
		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(getURL(name).getPath());
			jasperPrint = JasperFillManager.fillReport( 
			          jasperReport, params, dataSource);
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		JasperViewer jrviewer = new JasperViewer(jasperPrint);
		jrviewer.setZoomRatio(new Float (0.50));
		return jrviewer.getComponent(0);
	}
}
