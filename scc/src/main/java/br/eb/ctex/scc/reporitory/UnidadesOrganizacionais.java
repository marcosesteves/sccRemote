package br.eb.ctex.scc.reporitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eb.ctex.scc.model.UnidadeOrganizacional;

public interface UnidadesOrganizacionais extends JpaRepository<UnidadeOrganizacional, Long> {
	
	public List<UnidadeOrganizacional> findBySiglaUnidadeContaining(String siglaUnidade);

}
