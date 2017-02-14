package br.com.procempa.modus.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;

/**
 * Classe persistente que contém os dados de um equipamento do telecentro.
 * 
 * @author petrillo
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "EQUIPAMENTO")
public class Equipamento extends BasePersistent {

	private static final long serialVersionUID = 2804274019946380676L;

	private String rotulo = new String();

	private String marca = new String();

	private String processador = new String();

	private String discoRigido = new String();

	private String memoria = new String();

	private String ipAddress = new String();
	
	private Integer port;
	
	private Telecentro telecentro;

	private Integer situacao;
	
	private Boolean disponivel = new Boolean(true);

	private Integer status;

	@Id 
    public String getId() {
            return super.getId();
    }

	@Version
	public Timestamp getTimestamp() {
		return super.getTimestamp();
	}

	@Column(nullable = false, unique = true)
	public String getRotulo() {
		return this.rotulo;
	}

	public void setRotulo(String r) {
		this.rotulo = r;
	}

	public String getDiscoRigido() {
		return discoRigido;
	}

	public void setDiscoRigido(String d) {
		this.discoRigido = d;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String m) {
		this.marca = m;
	}

	public String getProcessador() {
		return processador;
	}

	public void setProcessador(String p) {
		this.processador = p;
	}

	public void setMemoria(String m) {
		this.memoria = m;
	}

	public String getMemoria() {
		return memoria;
	}

	@ManyToOne
	public Telecentro getTelecentro() {
		return this.telecentro;
	}

	public void setTelecentro(Telecentro telecentro) {
		this.telecentro = telecentro;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	@Transient
	public String getStatusDesc() {
		if (this.situacao == null) {
			return SituacaoEquipamento.getLabel(SituacaoEquipamento.NAO_CONTROLADO);
		} else {
			return SituacaoEquipamento.getLabel(this.situacao);
		}
	}

	@Transient
	public boolean isOpened() {
		if (getSituacao() != null) {
			return !StringUtils.isEmpty(getIpAddress())
					&& getSituacao().equals(SituacaoEquipamento.ABERTO);
		} else {
			return false;
		}

	}

	@Transient
	public boolean isClosed() {
		if (getSituacao() != null) {
			return !StringUtils.isEmpty(getIpAddress())
					&& (getSituacao().equals(SituacaoEquipamento.FECHADO) || getSituacao()
							.equals(SituacaoEquipamento.PRONTO));
		} else {
			return false;
		}
	}

	@Transient
	public boolean isDown() {
		if (getSituacao() != null) {
			return !StringUtils.isEmpty(getIpAddress())
					&& getSituacao().equals(SituacaoEquipamento.DERRUBADO);
		} else {
			return false;
		}
	}

	@Transient
	public boolean isControled() {
		return isClosed() || isOpened() || isReady();
	}

	@Transient
	public boolean isReady() {
		return !StringUtils.isEmpty(getIpAddress())
				&& getSituacao().equals(SituacaoEquipamento.PRONTO);
	}

	public String toString() {
		return getRotulo();
	}
	
	public Boolean getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Boolean disponivel) {
		this.disponivel = disponivel;
	}

	public Integer getStatus() {
		return this.status ;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}