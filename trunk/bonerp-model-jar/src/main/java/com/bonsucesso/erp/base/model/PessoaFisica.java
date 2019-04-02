package com.bonsucesso.erp.base.model;



import java.util.Date;


public interface PessoaFisica extends IPessoa {

	public EstadoCivil getEstadoCivil();

	public Date getDtNascimento();

	public Sexo getSexo();

	public String getRg();

	public Date getDtEmissaoRG();

	public String getOrgaoEmissorRG();

	public UF getEstadoRG();

	public String getNaturalidade();

}
