package br.com.procempa.modus.synchronizer;

public class SaveReferenceTimeException extends ReferenceException {

	private static final long serialVersionUID = -5403554868790695594L;
	
	public SaveReferenceTimeException() {
		super("Falha ao salvar o reference time.");
	}
}
