package br.com.procempa.modus.services;

/**
 * Classe de exceção para tratamento de regras de negócio,
 * que devem ser tratadas explicitamente.
 * @author petrillo
 *
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = -5117952179764302954L;

	public BusinessException(String message) {
		super(message);
	}
}
