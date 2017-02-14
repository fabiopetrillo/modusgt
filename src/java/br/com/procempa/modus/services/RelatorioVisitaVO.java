package br.com.procempa.modus.services;

import br.com.procempa.modus.utils.TimeUtils;


public class RelatorioVisitaVO {
		
	private String nome;
	
	private Integer numeroVisitas;
	
	private Long totalHoras;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNumeroVisitas() {
		return numeroVisitas;
	}

	public void setNumeroVisitas(Integer numeroVisitas) {
		this.numeroVisitas = numeroVisitas;
	}

	public Long getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(Long totalHoras) {
		this.totalHoras = totalHoras;
	}
	
	public String getFormatedTime(){
		return TimeUtils.formatTime(getTotalHoras());
	}
}
