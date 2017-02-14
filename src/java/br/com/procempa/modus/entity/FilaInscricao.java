package br.com.procempa.modus.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "FILAINSCRICAO")
public class FilaInscricao extends BasePersistent {

	private static final long serialVersionUID = -8210857051831049568L;

	private Integer ordem;
	
	private Usuario usuario;
	
	private Curso curso;
	
	private Turma turma;
	
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
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@OneToOne
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

}
