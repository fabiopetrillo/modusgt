package br.com.procempa.modus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = -5969756081951391917L;

	private String logradouro;

	private String numero;

	private String complemento;

	private String cep;

	private String bairro;

	private String cidade = "Porto Alegre";

	private String uf = "RS";

	private String pais = "Brasil";

	@Column(nullable = false)
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(nullable = false)
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Column(nullable = false)
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Column(nullable = false)
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(nullable = false)
	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Column(nullable = false)
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * Concate os dados para fornecer o endereço com todas as informações
	 * 
	 * @return String com o endereço completo
	 */
	@Transient
	public String getEnderecoPostal() {
		return getLogradouro() + " " + getNumero() + " "
				+ StringUtils.defaultIfEmpty(getComplemento(), "") + " "
				+ getBairro() + "\n" + getCidade() + " " + getPais() + " "
				+ "CEP " + getCep();
	}
	
	/**
	 * Sobrescreve o método toString, apresentado somente os endereço básico
	 */
	public String toString() {
		return getLogradouro() + " " + getNumero() + " "
				+ StringUtils.defaultIfEmpty(getComplemento(), "") + " "
				+ StringUtils.defaultIfEmpty(getBairro(), "");
	}
}