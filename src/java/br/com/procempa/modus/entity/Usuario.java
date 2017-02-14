package br.com.procempa.modus.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USUARIO")
public class Usuario extends BasePersistent {
    
    private static final long serialVersionUID = -8137866887249595459L;

    private String nome = new String();
    private String rg = new String();
    private String emissor = new String();
    private Integer perfil = Perfil.USUARIO;
    private Integer sexo = Sexo.MASCULINO;
    private Integer raca = Raca.BRANCA;
    private Integer estadoCivil = EstadoCivil.SOLTEIRO;
    private Date dataNascimento = new Date();
    private Endereco endereco = new Endereco();
    private Boolean estudante = false;
    private Boolean trabalha = false;
    private String ocupacao = new String();
    private Escolaridade escolaridade = new Escolaridade();
    private Date dataCadastro = new Date();
    private String email = null;
    private String senha = new String();
    private String telefone = new String();
    private List<Visita> visitaList = new ArrayList<Visita>();
    private String observacao = new String();
    private Integer status;
    private Integer deficiencia = Deficiencia.NENHUMA;

	@Id 
    public String getId() {
            return super.getId();
    }

    @Version
    public Timestamp getTimestamp() {
            return super.getTimestamp();
    }

    @Column(nullable = false)
    public String getNome() {
            return nome;
    }

    public void setNome(String nome) {
            this.nome = nome;
    }

    @Column(nullable = false, length = 11, unique = true)
    public String getRg() {
            return rg;
    }

    public void setRg(String rg) {
            this.rg = rg;
    }

    @Column(nullable = false)
    public String getEmissor() {
            return this.emissor;
    }

    public void setEmissor(String emissor) {
            this.emissor = emissor;
    }

    public void setDataNascimento(Date date) {
            this.dataNascimento = date;
    }

    @Column(nullable = false)
    public Date getDataNascimento() {
            return dataNascimento;
    }

    public Endereco getEndereco() {
            return endereco;
    }

    public void setEndereco(Endereco endereco) {
            this.endereco = endereco;
    }

    @Column(nullable = false)
    public Boolean getEstudante() {
            return estudante;
    }

    public void setEstudante(Boolean estudante) {
            this.estudante = estudante;
    }

    public String getOcupacao() {
            return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
            this.ocupacao = ocupacao;
    }

    public Boolean getTrabalha() {
            return trabalha;
    }

    public void setTrabalha(Boolean trabalha) {
            this.trabalha = trabalha;
    }

    @Column(nullable = false)
    public Integer getSexo() {
            return this.sexo;
    }

    public void setSexo(Integer sexo) {
            this.sexo = sexo;
    }

    @Column(nullable = false)
    public Integer getEstadoCivil() {
            return estadoCivil;
    }

    public void setEstadoCivil(Integer estadoCivil) {
            this.estadoCivil = estadoCivil;
    }

    @Column(nullable = false)
    public Integer getRaca() {
            return raca;
    }

    public void setRaca(Integer raca) {
            this.raca = raca;
    }

    public Escolaridade getEscolaridade() {
            return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
            this.escolaridade = escolaridade;
    }

    @Column(unique = true)
    public String getEmail() {
            return email;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    @Column(updatable = false, nullable = false)
    public Date getDataCadastro() {
            return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
    }

    public void setSenha(String senha) {
            this.senha = senha;
    }

    @Column(nullable = false)
    public String getSenha() {
            return this.senha;
    }

    public void setTelefone(String telefone) {
            this.telefone = telefone;
    }

    public String getTelefone() {
            return this.telefone;
    }

    @Column(nullable = false)
    public Integer getPerfil() {
            return this.perfil;
    }

    public void setPerfil(Integer perfil) {
            this.perfil = perfil;
    }

    @OneToMany(targetEntity=Visita.class,cascade=CascadeType.ALL, mappedBy="usuario")        
    public List<Visita> getVisitaList() {
        return this.visitaList;
    }
    
    public void setVisitaList(List<Visita> v) {
        this.visitaList = v;
    }
    
	@Lob
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}	
    
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDeficiencia() {
		return deficiencia;
	}

	public void setDeficiencia(Integer deficiencia) {
		this.deficiencia = deficiencia;
	}
}