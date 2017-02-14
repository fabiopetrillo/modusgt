package br.com.procempa.modus.entity;

public class Perfil {

    public static final int USUARIO = 0;
	public static final int MONITOR = 1;
	public static final int COORDENADOR = 2;
	public static final int CIDAT = 3;

	public static final String[] items = new String[] {"usuário","monitor","coordenador","cidat"};
	
	public static String getLabel(int value) {
		return items[value];
	}
}