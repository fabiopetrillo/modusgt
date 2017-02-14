package br.com.procempa.modus.services;

public class UsuarioException extends Exception {
	
	private static final long serialVersionUID = -280059690337729803L;

	public UsuarioException(String message) {
		super("Falha na busca de usuário: " + message);
	}

}
