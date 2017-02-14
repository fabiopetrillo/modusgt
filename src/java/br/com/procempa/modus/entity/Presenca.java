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
@Table(name = "PRESENCA")
public class Presenca extends BasePersistent {

	private static final long serialVersionUID = -1717394668764113228L;
	
	private Inscricao inscricao;
	private Encontro encontro;
	private Boolean presente = new Boolean(false);
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
	public Encontro getEncontro() {
		return encontro;
	}

	public void setEncontro(Encontro encontro) {
		this.encontro = encontro;
	}

	@OneToOne
	public Inscricao getInscricao() {
		return inscricao;
	}

	public void setInscricao(Inscricao inscricao) {
		this.inscricao = inscricao;
	}

	public Boolean getPresente() {
		return presente;
	}

	public void setPresente(Boolean presente) {
		this.presente = presente;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
