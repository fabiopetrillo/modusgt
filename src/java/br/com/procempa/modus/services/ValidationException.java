package br.com.procempa.modus.services;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 3634454083274369254L;
	private ValidationList validationList;

	ValidationException(ValidationList list) {
		this.validationList = list;
	}

	ValidationException(Validation validation) {
		ValidationList list = new ValidationList();
		list.add(validation);
		this.validationList = list;
	}

	public ValidationList getValidationList() {
		return validationList;
	}

	public void setValidationList(ValidationList validationList) {
		this.validationList = validationList;
	}
}
