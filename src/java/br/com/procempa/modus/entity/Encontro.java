package br.com.procempa.modus.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ENCONTRO")
public class Encontro extends BasePersistent {

	private static final long serialVersionUID = 4195385723975359302L;

	private String data = new String();
	
	private Turma turma;
	
	private Conteudo conteudo;
	
	private Integer status;

	@Id 
    public String getId() {
            return super.getId();
    }

	@Version
	public Timestamp getTimestamp() {
		return super.getTimestamp();
	}
	
	@ManyToOne	
	public Conteudo getConteudo() {
		return conteudo;
	}

	public void setConteudo(Conteudo conteudo) {
		this.conteudo = conteudo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@ManyToOne	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
