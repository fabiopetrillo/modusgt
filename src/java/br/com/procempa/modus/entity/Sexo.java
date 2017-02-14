package br.com.procempa.modus.entity;

public class Sexo {

	public static final int MASCULINO = 0;
	public static final int FEMININO = 1;
	
	public static String getLabel(int value) {
		String label = "";
		switch (value) {
		case MASCULINO:
			label = "masculino";
		case FEMININO:
			label = "feminino";
		}
		return label;
	}
}
