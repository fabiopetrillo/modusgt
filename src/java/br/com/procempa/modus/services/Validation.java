package br.com.procempa.modus.services;

public class Validation {

	private String message;
	private String field;
	
	public Validation(String message, String field) {
		this.message = message;
		this.field = field;
	}
	
	public Validation(String message) {
		this.message = message;
	}	
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
