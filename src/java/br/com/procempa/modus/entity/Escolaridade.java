package br.com.procempa.modus.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Escolaridade implements Serializable {
	
	private static final long serialVersionUID = -8138938231273728155L;

	public static final int FUNDAMENTAL = 0;
	public static final int MEDIO = 1;
	public static final int SUPERIOR = 2;
	public static final int EJA_FUNDAMENTAL = 3;
	public static final int EJA_MEDIO = 4;

	public static final String[] items = new String[] {"Ensino Fundamental","Ensino Médio","Ensino Superior","EJA Fundamental","EJA Médio"};
	
	private Integer nivel = FUNDAMENTAL;
	private Boolean completo = true;
	private String serie = new String();
	
	public static String getLabel(int value) {
		return items[value];
	}

	public Boolean getCompleto() {
		return completo;
	}

	public void setCompleto(Boolean completo) {
		this.completo = completo;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}
}