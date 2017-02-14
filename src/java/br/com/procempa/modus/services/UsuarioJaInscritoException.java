package br.com.procempa.modus.services;

public class UsuarioJaInscritoException extends Exception {

	private static final long serialVersionUID = -3036446825742054405L;

	public UsuarioJaInscritoException() {
		super("Usuário já inscrito");
	}
}