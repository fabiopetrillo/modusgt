package br.com.procempa.modus.report;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.com.procempa.modus.services.RelatorioExcecaoVO;

public class ExceptionLogDataSource implements JRDataSource{
	
	private RelatorioExcecaoVO exceptionLog;
	
	private Iterator iterator;
	
	public ExceptionLogDataSource(List<RelatorioExcecaoVO> exceptionLog) {
		iterator = exceptionLog.iterator();
	}
	
	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		if(field.getName().equals("id"))
				value = exceptionLog.getId();
		if(field.getName().equals("excecao"))
			value = exceptionLog.getExcecao();
		if(field.getName().equals("usuario"))
			value = exceptionLog.getUsuario();
		if(field.getName().equals("telecentro"))
			value = exceptionLog.getTelecentro();
		if(field.getName().equals("data"))
			value = exceptionLog.getData();
		if(field.getName().equals("message"))
			value = exceptionLog.getMensagem();
		return value;
	}

	public boolean next() throws JRException {
		boolean hasNext = iterator.hasNext();
		if(hasNext)
			exceptionLog = (RelatorioExcecaoVO)iterator.next();
		return hasNext;
	}

}

