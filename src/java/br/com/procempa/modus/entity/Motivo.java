package br.com.procempa.modus.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Motivo implements Serializable {
	
	private static final long serialVersionUID = 12772363756735907L;
	
	private Boolean email = Boolean.FALSE;
	private Boolean jogo = Boolean.FALSE;
	private Boolean chat = Boolean.FALSE;
	private Boolean web = Boolean.FALSE;
	private Boolean escolar = Boolean.FALSE;
	private Boolean curso = Boolean.FALSE;
	private Boolean oficina = Boolean.FALSE;
	private Boolean outro = Boolean.FALSE;
	
	public Boolean getChat() {
		return this.chat;
	}
	public void setChat(Boolean chat) {
		this.chat = chat;
	}
	public Boolean getCurso() {
		return this.curso;
	}
	public void setCurso(Boolean curso) {
		this.curso = curso;
	}
	public Boolean getEmail() {
		return this.email;
	}
	public void setEmail(Boolean email) {
		this.email = email;
	}
	public Boolean getEscolar() {
		return this.escolar;
	}
	public void setEscolar(Boolean escolar) {
		this.escolar = escolar;
	}
	public Boolean getJogo() {
		return this.jogo;
	}
	public void setJogo(Boolean jogo) {
		this.jogo = jogo;
	}
	public Boolean getOficina() {
		return this.oficina;
	}
	public void setOficina(Boolean oficina) {
		this.oficina = oficina;
	}
	public Boolean getOutro() {
		return this.outro;
	}
	public void setOutro(Boolean outro) {
		this.outro = outro;
	}
	public Boolean getWeb() {
		return this.web;
	}
	public void setWeb(Boolean web) {
		this.web = web;
	}
	

}
