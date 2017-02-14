package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.List;

import br.com.procempa.modus.entity.Inscricao;
import br.com.procempa.modus.entity.Presenca;

public class PresencaVO {
	
	private Inscricao inscricao;
	private List<Presenca> presencaList = new ArrayList<Presenca>();
	
	public Inscricao getInscricao() {
		return inscricao;
	}
	public void setInscricao(Inscricao inscricao) {
		this.inscricao = inscricao;
	}
	public List<Presenca> getPresencaList() {
		return presencaList;
	}
	public void setPresencaList(List<Presenca> presencaList) {
		this.presencaList = presencaList;
	}

}
