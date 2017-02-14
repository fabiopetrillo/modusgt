package br.com.procempa.modus.services;

public class SemVagasException extends Exception {

	private static final long serialVersionUID = -4347486596110116119L;
	
	public SemVagasException() {
		super("Excedido o número de vagas da turma");
	}
}
