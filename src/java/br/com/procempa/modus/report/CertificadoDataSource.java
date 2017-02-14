package br.com.procempa.modus.report;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.com.procempa.modus.entity.Inscricao;

public class CertificadoDataSource implements JRDataSource{

	private Inscricao inscricao;
	
	private Iterator iterator;
	
	public CertificadoDataSource(List<Inscricao> inscricao) {
		iterator = inscricao.iterator();
	}
	
	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		if(field.getName().equals("nomeUsuario"))
				value = inscricao.getUsuario().getNome();
		if(field.getName().equals("rg"))
				value = inscricao.getUsuario().getRg();
		if(field.getName().equals("nomeCurso")) 
				value = inscricao.getTurma().getCurso().getNome();
		if(field.getName().equals("quantHoras")) 
				value = inscricao.getTurma().getCurso().getCargaHorario().toString();
		if(field.getName().equals("ementa")) 
				value = inscricao.getTurma().getCurso().getEmenta();
		if(field.getName().equals("local")) 
				value = inscricao.getTurma().getCurso().getLocal();
		if(field.getName().equals("periodo")) 
			value = inscricao.getTurma().getPeriodo();
		if(field.getName().equals("assiduidade")) 
			value = inscricao.getTurma().getCurso().getAssiduidade().toString();
		return value;
	}

	public boolean next() throws JRException {
		boolean hasNext = iterator.hasNext();
		if(hasNext)
			inscricao = (Inscricao)iterator.next();
		return hasNext;
	}

}
