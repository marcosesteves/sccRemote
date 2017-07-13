package br.eb.ctex.scc.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Usuario {
	
	@NotEmpty(message = "O login é origatório")
	private String login;
	
	@NotEmpty(message = "A senha é obrigatória")
	private String senha;
	
	private String nome;
	
	private String posto;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPosto() {
		return posto;
	}
	public void setPosto(String posto) {
		this.posto = posto;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

}
