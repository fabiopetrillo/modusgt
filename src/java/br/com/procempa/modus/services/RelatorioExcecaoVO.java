package br.com.procempa.modus.services;


public class RelatorioExcecaoVO {

	private String id;

	private String usuario;

	private String telecentro;

	private String excecao;
	
	private String mensagem;

	private Long data;

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

	public String getExcecao() {
		return excecao;
	}

	public void setExcecao(String excecao) {
		this.excecao = excecao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTelecentro() {
		return telecentro;
	}

	public void setTelecentro(String telecentro) {
		this.telecentro = telecentro;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
