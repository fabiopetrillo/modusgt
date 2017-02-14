package br.com.procempa.modus.report;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.com.procempa.modus.services.RelatorioVisitaVO;

public class RelatorioVisitaAUDataSource implements JRDataSource{
	
	private Iterator iterator;
	
	private RelatorioVisitaVO relatorio;
	
	public RelatorioVisitaAUDataSource(List<RelatorioVisitaVO> relatorio) {
		iterator = relatorio.iterator();
	}
	
	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		if(field.getName().equals("nomeUsuario"))
				value = relatorio.getNome();
		if(field.getName().equals("numVisitas"))
				value = relatorio.getNumeroVisitas();
		if(field.getName().equals("totalHoras"))
			value = relatorio.getTotalHoras(); 
		if(field.getName().equals("formatedTime"))
			value = relatorio.getFormatedTime();
		return value;
	}

	public boolean next() throws JRException {
		boolean hasNext = iterator.hasNext();
		if(hasNext)
			relatorio = (RelatorioVisitaVO)iterator.next();
		return hasNext;
	}

}
