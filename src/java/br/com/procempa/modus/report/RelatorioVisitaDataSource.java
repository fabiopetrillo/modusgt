package br.com.procempa.modus.report;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.utils.TimeUtils;


public class RelatorioVisitaDataSource implements JRDataSource {

	private Iterator iterator;

	private Visita relatorio;

	public RelatorioVisitaDataSource(List<Visita> relatorio) {
		iterator = relatorio.iterator();
	}

	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		
		if (field.getName().equals("nomeUsuario"))
			value = relatorio.getUsuario().getNome();

		if (field.getName().equals("dataVisita"))
			value = relatorio.getDataInicio();
		
		if (field.getName().equals("totalHoras"))
			value = relatorio.getDataFim().getTime()
					- relatorio.getDataInicio().getTime();
		
		if (field.getName().equals("formatedTime"))
			value = TimeUtils.formatTime(relatorio.getDataFim().getTime()
					- relatorio.getDataInicio().getTime());
		return value;
	}

	public boolean next() throws JRException {
		boolean hasNext = iterator.hasNext();
		if (hasNext)
			relatorio = (Visita) iterator.next();
		return hasNext;
	}

}
