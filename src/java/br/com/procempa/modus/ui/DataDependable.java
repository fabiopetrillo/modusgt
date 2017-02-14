package br.com.procempa.modus.ui;

import br.com.procempa.modus.entity.Persistent;

public interface DataDependable {
	public Persistent getData();
	public void setData(Persistent persistent);
}
