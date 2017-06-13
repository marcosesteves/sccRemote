package br.eb.ctex.scc.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class UnidadeOrganizacional {
	
	@NotEmpty(message = "O Nome da Unidade é obrigatório")
	private String nomeUnidade;
	
	@NotEmpty(message = "A Sigla da Unidade é obrigatória")
	private String siglaUnidade;
	
	@NotEmpty(message = "O Ramal da Unidade é obrigatório")
	private String ramalUnidade;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUnidade;
	
	public Long getIdUnidade() {
		return idUnidade;
	}
	public void setIdUnidade(Long idUnidade) {
		this.idUnidade = idUnidade;
	}
	public String getNomeUnidade() {
		return nomeUnidade;
	}
	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}
	public String getSiglaUnidade() {
		return siglaUnidade;
	}
	public void setSiglaUnidade(String siglaUnidade) {
		this.siglaUnidade = siglaUnidade;
	}
	public String getRamalUnidade() {
		return ramalUnidade;
	}
	public void setRamalUnidade(String ramalUnidade) {
		this.ramalUnidade = ramalUnidade;
	}
	
	@OneToMany(mappedBy="unidade")
	private Set<Computador> computadores;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeUnidade == null) ? 0 : nomeUnidade.hashCode());
		result = prime * result + ((siglaUnidade == null) ? 0 : siglaUnidade.hashCode());
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
		UnidadeOrganizacional other = (UnidadeOrganizacional) obj;
		if (nomeUnidade == null) {
			if (other.nomeUnidade != null)
				return false;
		} else if (!nomeUnidade.equals(other.nomeUnidade))
			return false;
		if (siglaUnidade == null) {
			if (other.siglaUnidade != null)
				return false;
		} else if (!siglaUnidade.equals(other.siglaUnidade))
			return false;
		return true;
	}
	public Set<Computador> getComputadores() {
		return computadores;
	}
	public void setComputadores(Set<Computador> computadores) {
		this.computadores = computadores;
	} 
}
