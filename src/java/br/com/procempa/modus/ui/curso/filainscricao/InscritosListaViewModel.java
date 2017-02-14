package br.com.procempa.modus.ui.curso.filainscricao;

import java.util.List;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Deficiencia;
import br.com.procempa.modus.entity.FilaInscricao;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.FilaInscricaoDataServices;
import br.com.procempa.modus.ui.SearchTableModel;

public class InscritosListaViewModel extends SearchTableModel {

	private static final long serialVersionUID = -8441073660191148175L;
	private Curso curso;

	public InscritosListaViewModel(Curso curso) {
		super();
		this.curso = curso;
	}

	@SuppressWarnings("unchecked")	
	@Override
	public List getList() {
        try {
            if (list == null) {
				list = FilaInscricaoDataServices.getList(curso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return col == 5 ? true : false;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Object getValueAt(int row, int col) {
		Object value = super.getValueAt(row, col);
		if(col == 5) { 
			value = "";
			try {
				FilaInscricao listaEspera = FilaInscricaoDataServices.getList(curso).get(row);
				
				if(null != listaEspera.getTurma()){
					Turma t = listaEspera.getTurma();
					value = t.getNome();
					if(t.getHorario() != null && !t.getHorario().equals("")){
						value = value + " (" + t.getHorario() + ")"; 
					}					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		} 
		
		if(col == 4) { 
			value = "Nenhuma";
			try {
				FilaInscricao listaEspera = FilaInscricaoDataServices.getList(curso).get(row);
				Integer def = listaEspera.getUsuario().getDeficiencia();
				value = Deficiencia.getLabel(def);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		} 
		return value;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		super.setValueAt(aValue, row, column);
		
		if (column == 5) {
			try {
				FilaInscricao listaEspera = FilaInscricaoDataServices.getList(curso).get(row);
				listaEspera.setTurma((Turma)aValue);
				listaEspera = (FilaInscricao) FilaInscricaoDataServices.persist(listaEspera);
				getList().set(row, listaEspera); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
