package br.com.procempa.modus.entity;

public class EstadoCivil {
	public static final int SOLTEIRO = 0;
	public static final int CASADO = 1;
	public static final int SEPARADO = 2;
	public static final int DIVORCIADO = 3;
	public static final int VIUVO = 4;
	public static final String[] items  = new String[] {"solteiro","casado","separado","divorciado","viúvo"};
	
	public static String getLabel(int value) {
		return items[value];
	}
}
