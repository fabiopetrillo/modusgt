package br.com.procempa.modus.entity;

/**
 * Situacao do Equipamento 
 * @author petrillo
 *
 */
public class SituacaoEquipamento {
	public static final int NAO_CONTROLADO = 0;
	public static final int PRONTO = 1;
	public static final int ABERTO = 2;
	public static final int FECHADO = 3;
	public static final int DERRUBADO = 4; //StationMonitor derrubado pelo usuario
	
	public static final String[] items = new String[] {"Não Controlado","Pronto","Aberto","Fechado","Derrubado"};
	
	public static String getLabel(int value) {
		return items[value];
	}	
}
