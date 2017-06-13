package br.eb.ctex.scc.reporitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eb.ctex.scc.model.Computador;

public interface Computadores extends JpaRepository<Computador, Long> {
	
	public List<Computador> findByNomeCodigoContaining(String nomeCodigo);

}
