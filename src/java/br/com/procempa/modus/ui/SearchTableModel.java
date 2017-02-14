package br.com.procempa.modus.ui;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.beanutils.BeanUtils;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import br.com.procempa.modus.entity.Persistent;

/**
 * Classe abstrata que descreve as funcionalidades básicas de um TableModel para
 * SearchPanel
 * 
 * @author petrillo
 * 
 */
public abstract class SearchTableModel<T> extends DefaultTableModel {

	private DefaultTableColumnModelExt columnModel = new DefaultTableColumnModelExt();

	protected List<Persistent> list;

	public SearchTableModel() {
		super();
	}

	public SearchTableModel(DefaultTableColumnModelExt model) {
		this.columnModel = model;
	}

	public abstract List<Persistent> getList();

	public void setList(List<Persistent> list) {
		this.list = list;
	}

	public int getRowCount() {
		if (null != getList()) {
			return getList().size();
		} else {
			return 0;
		}
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public Object getValueAt(int row, int col) {
		Object value = null;

		if (col == -1) {
			return getList().get(row);
		}

		SearchTableColumn column = (SearchTableColumn) this.columnModel
				.getColumn(col);

		try {
			value = BeanUtils.getProperty(getList().get(row), column
					.getFieldName());
		} catch (Exception e) {
			return "";
		}

		return value;
	}

	public void setValueAt(Object aValue, int row, int column) {
		if (getColumnModel().getColumn(column) instanceof SearchTableColumn) {
			String field = ((SearchTableColumn) getColumnModel().getColumn(column)).getFieldName();
			try {
				BeanUtils.setProperty(getList().get(row), field, aValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			super.setValueAt(aValue, row, column);
		}
	}

	public String getColumnName(int column) {
		return ((SearchTableColumn) this.columnModel.getColumn(column))
				.getTitle();
	}

	public int getColumnCount() {
		return this.columnModel != null ? this.columnModel.getColumnCount() : 0;
	}

	@SuppressWarnings("unchecked")
	public Class getColumnClass(int c) {
		Object o = getValueAt(0, c);
		if (null == o) {
			o = new Object();
		}
		return o.getClass();
	}

	public void refresh() {
		try {
			setList(null);
			getList();
			fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DefaultTableColumnModelExt getColumnModel() {
		return columnModel;
	}

	public void setColumnModel(DefaultTableColumnModelExt colModel) {
		this.columnModel = colModel;
	}

	public void addItem(Persistent item) {
		list.add(item);
	}

	public void replaceItem(Persistent p) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId().equals(p.getId())) {
				list.set(i,p);
				break;
			}
		}
	}

	public void removeItem(Persistent i) {
		for (Persistent item : list) {
			if (item.getId().equals(i.getId())) {
				list.remove(item);
				fireTableDataChanged();
				break;
			}
		}
	}
}