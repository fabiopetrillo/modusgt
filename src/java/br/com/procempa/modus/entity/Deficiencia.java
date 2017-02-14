package br.com.procempa.modus.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Deficiencia implements Serializable{

	private static final long serialVersionUID = -1498246945252574991L;

	public static final int NENHUMA = 0;
	public static final int VISUAL = 1;
	public static final int CADEIRANTE = 2;
	
	public static final String[] items = new String[] {"Nenhuma","Visual","Cadeirante"};

	public static String getLabel(int value) {
		return items[value];
	}
	
	private Integer def = NENHUMA;

	public Integer getDef() {
		return def;
	}

	public void setDef(Integer def) {
		this.def = def;
	}
}
