package br.com.procempa.modus.synchronizer;

public class ReferenceNotFoundException extends ReferenceException {

	private static final long serialVersionUID = -821464205516897995L;
	
	public ReferenceNotFoundException() {
		super("N�o foi poss�vel obter time reference. Revise o seu sistema de arquivos.");
	}

}
