package br.com.procempa.modus.entity;

public class Status {

	public static final int NOVO = 0;
	public static final int PRONTO = 1;
	public static final int ATUALIZADO = 2;
	public static final int EXCLUIDO = 10;
	
	public static String getLabel(int value) {
		String label = "";
		switch (value) {
		case NOVO:
			label = "novo";
		case PRONTO:
			label = "pronto";
		case ATUALIZADO:
			label = "atualizado";
		case EXCLUIDO:
			label = "excluído";
		}
		return label;
	}	
}
