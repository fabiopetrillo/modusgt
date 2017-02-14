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
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "CURSO")
public class Curso extends BasePersistent {
	
	private static final long serialVersionUID = -7798096392891894665L;
	
	private String nome = new String();
	private String local = new String();
	private Integer cargaHorario = new Integer(0);
	private String ementa = new String();
	private Integer assiduidade = new Integer(50);
	
	private Integer vagasDisponiveis = new Integer(0);
	
    private Telecentro telecentro;
    
    private Integer status;

	@ManyToOne
    public Telecentro getTelecentro() {
		return telecentro;
	}

	public void setTelecentro(Telecentro telecentro) {
		this.telecentro = telecentro;
	}

	@Id
    public String getId() {
            return super.getId();
    }

    @Version
    public Timestamp getTimestamp() {
            return super.getTimestamp();
    }	
	
    @Column(nullable = false)
    public Integer getAssiduidade() {
		return assiduidade;
	}
	
	public void setAssiduidade(Integer assiduidade) {
		this.assiduidade = assiduidade;
	}
	
	public Integer getCargaHorario() {
		return cargaHorario;
	}
	
	public void setCargaHorario(Integer cargaHorario) {
		this.cargaHorario = cargaHorario;
	}
	
	@Lob
	public String getEmenta() {
		return ementa;
	}
	public void setEmenta(String ementa) {
		this.ementa = ementa;
	}
	
	public String getLocal() {
		return local;
	}
	
	public void setLocal(String local) {
		this.local = local;
	}
	
    @Column(nullable = false)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna as vagas diponíveis nas turma abertas.
	 * Por ser transiente, deve ser atualizado antes do uso.
	 */
	@Transient
	public Integer getVagasDisponiveis() {
		return vagasDisponiveis;
	}

	public void setVagasDisponiveis(Integer vagasDisponiveis) {
		this.vagasDisponiveis = vagasDisponiveis;
	}
	
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}