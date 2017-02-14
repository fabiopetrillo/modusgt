package br.com.procempa.modus.session.exceptions;

import br.com.procempa.modus.entity.ExceptionLog;

public class PersistException extends RuntimeException {

	private static final long serialVersionUID = 9027983887725325336L;
	private ExceptionLog exceptionLog;

	public PersistException(ExceptionLog log ) {
		this.exceptionLog = log;
	}
	
	public ExceptionLog getExceptionLog() {
		return exceptionLog;
	}

	public void setExceptionLog(ExceptionLog exceptionLog) {
		this.exceptionLog = exceptionLog;
	}
}
