package br.com.procempa.modus.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Classe persistente que agruapa Usuários que realizaram um Curso em um
 * determinado período.
 * 
 * @author petrillo
 * 
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TURMA")
public class Turma extends BasePersistent {

	private static final long serialVersionUID = 148961392413061896L;

	private String nome = new String();

	private Integer vagas = new Integer(0);

	private String periodo = new String();

	private String horario = new String();
	
	private Boolean aberta = new Boolean(true);

	private Curso curso;
	
	private Integer status;

	@Id 
    public String getId() {
            return super.getId();
    }

	@Version
	public Timestamp getTimestamp() {
		return super.getTimestamp();
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

    @Column(nullable = false)	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

    @Column(nullable = false)	
	public Integer getVagas() {
		return vagas;
	}

	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}

	@ManyToOne	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Boolean getAberta() {
		return aberta;
	}

	public void setAberta(Boolean aberta) {
		this.aberta = aberta;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
