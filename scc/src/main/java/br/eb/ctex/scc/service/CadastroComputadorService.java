package br.eb.ctex.scc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.eb.ctex.scc.model.Computador;
import br.eb.ctex.scc.reporitory.Computadores;
import br.eb.ctex.scc.reporitory.filter.ComputadorFilter;

@Service
public class CadastroComputadorService {
	
	@Autowired
	Computadores computadores;
	
	@Autowired
	CadastroUnidadeService unidadeService;
	
	public void salvar(Computador computador) {
		try {
			computadores.save(computador);			
		}
		catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}
	
	public void excluir (Long idComp) {
		computadores.delete(idComp);
	}
	public List<Computador> filtrar(ComputadorFilter filtro) {
		String nomeCodigo = filtro.getNomeCodigo() == null ? "%" : filtro.getNomeCodigo();
		return computadores.findByNomeCodigoContaining(nomeCodigo);
	}
	
	public List<Computador> buscarTodosComputadores() {
		return computadores.findAll();
	}

}
