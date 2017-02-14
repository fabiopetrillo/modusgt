package br.com.procempa.modus.ui.visita;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.services.EquipamentoDataServices;

class EquipamentoLivreComboBoxModel implements ComboBoxModel {

	Telecentro telecentro;
	boolean isInsert;

	Object item;

	List<Equipamento> items;

	public EquipamentoLivreComboBoxModel(Telecentro t, boolean isInsert) {
		this.telecentro = t;
		this.isInsert = isInsert;
		populate();
	}

	private void populate() {
		try {
			if (isInsert) {
				if (null == telecentro.getUmUsuarioPorEquipamento() || telecentro.getUmUsuarioPorEquipamento()) {
					items = EquipamentoDataServices.getLivres(telecentro);
				} else {
					items = EquipamentoDataServices.getList(telecentro);
				}
				Equipamento e = new Equipamento();
				e.setRotulo("Lista de Espera");
				e.setId("");
				items.add(e);				
			} else {
				items = EquipamentoDataServices.getList(telecentro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getSelectedItem() {
		return item;
	}

	public void setSelectedItem(Object anItem) {
		item = anItem;
	}

	public Object getElementAt(int index) {
		return items.get(index);
	}

	public int getSize() {
		return items.size();
	}

	public void removeListDataListener(ListDataListener l) {
	}

	public int indexOf(Equipamento equipamento) {
		int index = -1;

		if (equipamento != null) {
			for (int i = 0; i < items.size(); i++) {
				if (equipamento.getId().equals(items.get(i).getId())) {
					index = i;
					break;
				}
			}
		}

		return index;
	}

	public void addListDataListener(ListDataListener l) {
	}
}
