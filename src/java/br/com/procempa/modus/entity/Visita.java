package br.com.procempa.modus.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name = "VISITA")
public class Visita extends BasePersistent {

	private static final long serialVersionUID = -533597678200305212L;
	
	private Usuario usuario;
	private Telecentro telecentro;
	private Equipamento equipamento;	
	private Motivo motivo = new Motivo(); 	
	private Date dataInicio = new Date();
	private Date dataFim = null;	
	private String observacao;	
	private Integer status;
	
	@Id 
    public String getId() {
            return super.getId();
    }

    @Version
    public Timestamp getTimestamp() {
            return super.getTimestamp();
    }	

    @Column(nullable = false)
    public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
    
    public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public Motivo getMotivo() {
		return this.motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	@Lob
	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

    @ManyToOne    
    public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @ManyToOne    
    public Telecentro getTelecentro() {
        return this.telecentro;
    }

    public void setTelecentro(Telecentro telecentro) {
        this.telecentro = telecentro;
    }

    @OneToOne    
	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	
	@Transient
	public boolean isListaEspera() {
		return equipamento == null ? true : false;
	}
	
	@Transient
	public boolean isAtiva() {
		return dataFim == null ? true : false;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
