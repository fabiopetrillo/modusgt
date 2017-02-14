package br.com.procempa.modus.entity;

public class Raca {
	public static final int BRANCA = 0;
	public static final int NEGRA = 1;
	public static final int AMARELA = 2;
	public static final int INDIGENA = 3;
	public static final int OUTRA = 4;
	
	public static final String[] items = new String[] {"branca","negra","amarela","indígena","outra"};
	
	public static String getLabel(int value) {
		return items[value];
	}
}
