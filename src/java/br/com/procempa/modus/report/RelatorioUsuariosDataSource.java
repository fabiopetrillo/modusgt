package br.com.procempa.modus.report;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.com.procempa.modus.entity.Usuario;

public class RelatorioUsuariosDataSource implements JRDataSource {
	private Iterator iterator;

	private Usuario relatorio;

	public RelatorioUsuariosDataSource(List<Usuario> relatorio) {
		iterator = relatorio.iterator();
	}

	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		
		if (field.getName().equals("rg"))
			value = relatorio.getRg();
		
		if (field.getName().equals("nome"))
			value = relatorio.getNome();

		if (field.getName().equals("dataNascimento"))
			value = relatorio.getDataNascimento();
		
		if (field.getName().equals("email"))
			value = relatorio.getEmail();
		
		if (field.getName().equals("telefone"))
			value = relatorio.getTelefone();
		
		return value;
	}

	public boolean next() throws JRException {
		boolean hasNext = iterator.hasNext();
		if (hasNext)
			relatorio = (Usuario) iterator.next();
		return hasNext;
	}

}
