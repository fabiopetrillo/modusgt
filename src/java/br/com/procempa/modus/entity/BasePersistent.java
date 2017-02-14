package br.com.procempa.modus.entity;

import java.sql.Timestamp;

public abstract class BasePersistent implements Persistent {
	private Timestamp timestamp = null;
	private String id = null;
	
	public String getId() {
		return this.id;
	}

	public void setId(String i) {
		this.id = i;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp date) {
		timestamp = date;
	}	
}