package br.com.procempa.modus.session.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class SearchException extends Exception {

	private static final long serialVersionUID = 4793623137708020875L;

	public SearchException(Exception e) {
		super(e);
	}
}
