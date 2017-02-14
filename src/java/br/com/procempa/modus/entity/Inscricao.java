package br.com.procempa.modus.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "INSCRICAO")
public class Inscricao extends BasePersistent {

	private static final long serialVersionUID = -3393416491588867934L;
	
	private Usuario usuario; 
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
	 
  
	@OneToOne
	public Turma getTurma() {
		return turma;
	}
	
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	@OneToOne
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	} 
	

}
