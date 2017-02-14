package br.com.procempa.modus.session;

import javax.ejb.Remote;

import br.com.procempa.modus.entity.ExceptionLog;

@Remote
public interface ExceptionLogger {
	public ExceptionLog log(ExceptionLog exceptionLog);
}
