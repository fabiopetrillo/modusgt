package br.com.procempa.modus.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "CONTEUDO")
public class Conteudo extends BasePersistent{	

	private static final long serialVersionUID = 8250547517154465382L;

	private String nome =  new String();
	private String descricao = new String();
	private Integer horasAula = new Integer(0);
	private Integer status;
	
	private Curso curso;
	
	@Id 
    public String getId() {
            return super.getId();
    }

    @Version
    public Timestamp getTimestamp() {
            return super.getTimestamp();
    }
	
    @Lob
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Integer getHorasAula() {
		return horasAula;
	}
	
	public void setHorasAula(Integer horasAula) {
		this.horasAula = horasAula;
	}
	
	@Column(nullable = false)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@ManyToOne
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
