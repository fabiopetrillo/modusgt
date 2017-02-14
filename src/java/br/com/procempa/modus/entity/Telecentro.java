package br.com.procempa.modus.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Inheritance
@Table(name = "TELECENTRO")
public class Telecentro extends BasePersistent {

    private static final long serialVersionUID = -8568470880093599583L;

    private String nome = new String();

    private Endereco endereco = new Endereco();

    private String email  = new String();

    private String telefone  = new String();

    private String horarioInicio = new String();

    private String horarioFim = new String();

    private Usuario coordenador = new Usuario();

    private Usuario monitor1 = new Usuario();
    private Usuario monitor2 = new Usuario();
    private Usuario monitor3 = new Usuario();
    
    private String turno1 =  new String();
    private String turno2 =  new String();
    private String turno3 =  new String();

    private Integer tempo;
    
    private Boolean encerramentoAutomatico = new Boolean(false);
    
    private Integer status;

	private Boolean umUsuarioPorEquipamento;

	@Id 
    public String getId() {
            return super.getId();
    }

    @Version
    public Timestamp getTimestamp() {
            return super.getTimestamp();
    }

    public String getNome() {
            return this.nome;
    }

    public void setNome(String nome) {
            this.nome = nome;
    }

    public void setEndereco(Endereco e) {
            this.endereco = e;
    }

    public void setTelefone(String telefone) {
            this.telefone = telefone;
    }

    public String getHorarioInicio() {
            return this.horarioInicio;
    }

    public void setHorarioInicio(String inicio) {
            this.horarioInicio = inicio;
    }

    public String getHorarioFim() {
            return this.horarioFim;
    }

    public void setHorarioFim(String fim) {
            this.horarioFim = fim;
    }

    public Endereco getEndereco() {
            return this.endereco;
    }

    public String getTelefone() {
            return this.telefone;
    }
    
    @OneToOne
    @JoinColumn(name="coordenador_fk")
    public Usuario getCoordenador() {
            return this.coordenador;
    }

    public void setCoordenador(Usuario coordenador) {
            this.coordenador = coordenador;
    }

    public String getEmail() {
            return email;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    @OneToOne
    @JoinColumn(name="monitor1_fk")
    public Usuario getMonitor1() {
        return monitor1;
    }

    public void setMonitor1(Usuario monitor1) {
        this.monitor1 = monitor1;
    }

    @OneToOne
    @JoinColumn(name="monitor2_fk")
    public Usuario getMonitor2() {
        return monitor2;
    }

    public void setMonitor2(Usuario monitor2) {
        this.monitor2 = monitor2;
    }

    @OneToOne
    @JoinColumn(name="monitor3_fk")
    public Usuario getMonitor3() {
        return monitor3;
    }

    public void setMonitor3(Usuario monitor3) {
        this.monitor3 = monitor3;
    }

    public String getTurno1() {
        return turno1;
    }

    public void setTurno1(String turno1) {
        this.turno1 = turno1;
    }

    public String getTurno2() {
        return turno2;
    }

    public void setTurno2(String turno2) {
        this.turno2 = turno2;
    }

    public String getTurno3() {
        return turno3;
    }

    public void setTurno3(String turno3) {
        this.turno3 = turno3;
    }
    
    /**
     * Tempo máximo uma visita em minutos 
     * @return o valor do tempo máximo
     */
	public Integer getTempo() {
		return this.tempo;
	}

	public void setTempo(Integer t){
        this.tempo = t;
    }
	
    public Boolean getEncerramentoAutomatico() {
		return encerramentoAutomatico;
	}

	public void setEncerramentoAutomatico(Boolean encerramentoAutomatico) {
		this.encerramentoAutomatico = encerramentoAutomatico;
	}	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getUmUsuarioPorEquipamento() {
		return this.umUsuarioPorEquipamento;
	}

	public void setUmUsuarioPorEquipamento(boolean b) {
		this.umUsuarioPorEquipamento = b;
	}
}