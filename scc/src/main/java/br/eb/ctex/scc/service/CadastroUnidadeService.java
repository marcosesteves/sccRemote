package br.eb.ctex.scc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.eb.ctex.scc.model.UnidadeOrganizacional;
import br.eb.ctex.scc.reporitory.UnidadesOrganizacionais;
import br.eb.ctex.scc.reporitory.filter.UnidadeOrganizacionalFilter;

@Service
public class CadastroUnidadeService {

	@Autowired
	UnidadesOrganizacionais unidades;

	public void salvar(UnidadeOrganizacional unidade) {
		unidades.save(unidade);
	}

	public void excluir(Long idUnidade) {
		unidades.delete(idUnidade);
	}

	public List<UnidadeOrganizacional> filtrar(UnidadeOrganizacionalFilter filtro) {
		String siglaUnidade = filtro.getSiglaUnidade() == null ? "%" : filtro.getSiglaUnidade();
		return unidades.findBySiglaUnidadeContaining(siglaUnidade);
	}

	public List<UnidadeOrganizacional> buscarTodasUnidades() {
		return unidades.findAll();
	}
	
}
