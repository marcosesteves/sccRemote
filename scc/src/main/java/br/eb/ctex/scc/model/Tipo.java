package br.eb.ctex.scc.model;

public enum Tipo {
	DESKTOP("Desktop"),
	LEPTOP("Leptop"),
	SERVIDOR("Servidor");
	
	private String descricao;
	
	Tipo(String descricao ) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
