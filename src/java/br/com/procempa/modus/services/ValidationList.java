package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.List;

public class ValidationList extends ArrayList<Validation> {

	private static final long serialVersionUID = 3634454083274369254L;

	
	public List<String> getMessages() {
		ArrayList<String> messages = new ArrayList<String>();
		for (Validation validation : this) {
			messages.add(validation.getMessage());
		}
		return messages;
	}
	
	public boolean isValid() {
		return this.size() == 0;
	}
}
