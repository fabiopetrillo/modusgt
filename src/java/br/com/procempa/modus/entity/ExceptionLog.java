package br.com.procempa.modus.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "EXCEPTIONLOG")
public class ExceptionLog extends BasePersistent {

	private static final long serialVersionUID = -2985388613198665704L;
	
	private String exception;
	private String message;
	private String stackTrace;
	private String userDescription;
	private String user;
	private String telecentro;
	private String contextMessage;	
	private Integer status;

	@Id 
    public String getId() {
            return super.getId();
    }

    @Version
    public Timestamp getTimestamp() {
            return super.getTimestamp();
    }

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Lob	
	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getTelecentro() {
		return telecentro;
	}

	public void setTelecentro(String telecentro) {
		this.telecentro = telecentro;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Lob
	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public void setContextMessage(String message) {
		this.contextMessage = message;
	}

	public String getContextMessage() {
		return contextMessage;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
