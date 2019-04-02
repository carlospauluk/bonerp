package com.bonsucesso.erp.base.model;

import java.util.List;

public interface IPessoa {
	
	public Pessoa getPessoa();
	
	public String getFone1();
	public String getFone2();
	public String getFone3();
	public String getFone4();
	
	public String getEmail();
	
	public List<Endereco> getEnderecos();
	
	public Endereco getEndereco();

}
