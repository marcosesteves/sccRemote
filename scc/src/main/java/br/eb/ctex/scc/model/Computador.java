package br.eb.ctex.scc.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

// Classe Computador
@Entity()
public class Computador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idComp;

	@NotEmpty(message = "O Nome/Código do computador são obrigatórios")
	private String nomeCodigo;
	
	@NotEmpty(message = "O usuário é origatório")
	private String usuario;
	
	@NotEmpty(message = "O numero IP é obrigatório")
	@Size(min = 8, max = 16, message = "O número IP tem que ter no mínimo 8 e no máximo 16 caracteres")
	private String numIp;
	
	@NotEmpty(message = "A placa mãe é obrigatória obrigatórios")
	private String placaMae;
	
	@NotEmpty(message = "O processador é obrigatório")
	private String processador;
	
	@NotEmpty(message = "A memória é obrigatória")
	private String memoriaRam;
	
	@NotEmpty(message = "O HD é obrigatório")
	private String hd;
	
	@Enumerated(EnumType.STRING)
	private Tipo tipoComp;
	
	@NotEmpty(message = "O Sistema Operacional é obrigatório")
	private String sistOper;
	
	@NotEmpty(message = "O Técnico é obrigatório")
	private String tecnico;
	
	@NotNull(message = "Data de cadastro é obrigatória")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@ManyToOne
	@JoinColumn(name = "id_unidade")
	private UnidadeOrganizacional unidade;

	public UnidadeOrganizacional getUnidade() {
		return unidade;
	}
	public void setUnidade(UnidadeOrganizacional unidade) {
		this.unidade = unidade;
	}
	
	public Long getIdComp() {
		return idComp;
	}
	public void setIdComp(Long idComp) {
		this.idComp = idComp;
	}
	
	public String getNomeCodigo() {
		return nomeCodigo;
	}
	public void setNomeCodigo(String nomeCodigo) {
		this.nomeCodigo = nomeCodigo;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNumIp() {
		return numIp;
	}
	public void setNumIp(String numIp) {
		this.numIp = numIp;
	}
	public String getPlacaMae() {
		return placaMae;
	}
	public void setPlacaMae(String placaMae) {
		this.placaMae = placaMae;
	}
	public String getProcessador() {
		return processador;
	}
	public void setProcessador(String processador) {
		this.processador = processador;
	}
	public String getMemoriaRam() {
		return memoriaRam;
	}
	public void setMemoriaRam(String memoriaRam) {
		this.memoriaRam = memoriaRam;
	}
	public String getHd() {
		return hd;
	}
	public void setHd(String hd) {
		this.hd = hd;
	}
	
	public Tipo getTipoComp() {
		return tipoComp;
	}
	public void setTipoComp(Tipo tipoComp) {
		this.tipoComp = tipoComp;
	}
	public String getSistOper() {
		return sistOper;
	}
	public void setSistOper(String sistOper) {
		this.sistOper = sistOper;
	}
	public String getTecnico() {
		return tecnico;
	}
	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeCodigo == null) ? 0 : nomeCodigo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computador other = (Computador) obj;
		if (nomeCodigo == null) {
			if (other.nomeCodigo != null)
				return false;
		} else if (!nomeCodigo.equals(other.nomeCodigo))
			return false;
		return true;
	}

}
