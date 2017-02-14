package br.com.procempa.modus.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public interface Persistent extends Serializable {
	public String getId();
	public void setId(String id);
	public Timestamp getTimestamp();
	public void setTimestamp(Timestamp timestamp);
	public Integer getStatus();
	public void setStatus(Integer status);
}
