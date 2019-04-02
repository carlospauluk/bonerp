/**
 * ServerWsrcsPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public interface ServerWsrcsPortType extends java.rmi.Remote {

    /**
     * FunÃ§Ã£o usada para fazer a integraÃ§Ã£o com o Safety Cred.
     */
    public java.lang.String fncIntegraCrediario(java.lang.String paramApp, java.lang.String guid, int etapa, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String cdConAnt, float valorVenda, float valorEntrada, int qtdePrest, java.lang.String dataVcto, java.lang.String usuarioAval, java.lang.String senhaAval, int cdProduto, int qtdeVia, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * FunÃ§Ã£o usada para realizar uma venda no SafetyCred
     */
    public java.lang.String fncEfetuaVenda(java.lang.String paramApp, java.lang.String guid, int etapa, int instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String cdConAnt, float valorVenda, float valorEntrada, java.lang.String dataVcto, java.lang.String usuarioAval, java.lang.String senhaAval, int cdProduto, int qtdeVia, int autorizado, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o irÃ¡ retornar todas as prestaÃ§Ãµes abertas do
     * cliente
     */
    public com.bonsucesso.servipa.ws.TRetContrato fncConsultaPrestacao(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, int dtPgto, int tipoContrato, com.bonsucesso.servipa.ws.TContratoParam listContrato, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o irÃ¡ retornar todas as prestaÃ§Ãµes abertas do
     * cliente
     */
    public com.bonsucesso.servipa.ws.TRetEfetuaPgto fncEfetuaPagamento(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, int dtPgto, int formaPgto, int tipoPagamento, int tipoContrato, int lojaContrato, java.lang.String nrContra, int nrPrestacao, float vlrPgto, int nrBanco, int nrAgencia, java.lang.String nrCheque, int qtdeVia, java.lang.String usuarioAval, java.lang.String senhaAval, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o irÃ¡ cadastrar um cliente no sistema Tidas SoluÃ§Ãµes,
     * mas se o cliente jÃ¡ existir irÃ¡ fazer uma atualizaÃ§Ã£o.
     */
    public com.bonsucesso.servipa.ws.TRetCustom fncCadastraCliente(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String senhaWS, com.bonsucesso.servipa.ws.TDadosPessoaisCliente dadosPessoaisCliente, com.bonsucesso.servipa.ws.TEnderecoCliente enderecoCliente, com.bonsucesso.servipa.ws.TDadosTrabalhoCliente dadosTrabalhoCliente, com.bonsucesso.servipa.ws.TDadosConjugeCliente dadosConjugeCliente, com.bonsucesso.servipa.ws.TDadosReferenciaCliente dadosReferenciaCliente, com.bonsucesso.servipa.ws.TDadosReferenciaClienteItem[] dadosReferenciaLista, com.bonsucesso.servipa.ws.TDadosRendaFamiliar[] dadosRendaFamiliar, com.bonsucesso.servipa.ws.TDadosReferenciaComercial[] dadosReferenciaComercial, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Altera cadastro de cliente no sistema Safety Cred.
     */
    public com.bonsucesso.servipa.ws.TRetCustom fncAlteraCadastroCliente(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String senhaWS, com.bonsucesso.servipa.ws.TDadosPessoaisCliente dadosPessoaisCliente, com.bonsucesso.servipa.ws.TEnderecoCliente enderecoCliente, com.bonsucesso.servipa.ws.TDadosTrabalhoCliente dadosTrabalhoCliente, com.bonsucesso.servipa.ws.TDadosConjugeCliente dadosConjugeCliente, com.bonsucesso.servipa.ws.TDadosReferenciaCliente dadosReferenciaCliente, com.bonsucesso.servipa.ws.TDadosReferenciaClienteItem[] dadosReferenciaLista, com.bonsucesso.servipa.ws.TDadosRendaFamiliar[] dadosRendaFamiliar, com.bonsucesso.servipa.ws.TDadosReferenciaComercial[] dadosReferenciaComercial, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o irÃ¡ consultar o NOME do cliente na Tidas SoluÃ§Ãµes
     */
    public com.bonsucesso.servipa.ws.TDadosNomeCliente fncConsultaNomeCliente(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o irÃ¡ consultar um cliente do cliente na Tidas
     * SoluÃ§Ãµes
     */
    public com.bonsucesso.servipa.ws.TRetDadosCliente fncConsultaCliente(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o irÃ¡ cancelar uma venda no sistema Tidas SoluÃ§Ãµes.
     */
    public com.bonsucesso.servipa.ws.TRetCustom fncCancelaVenda(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, int tipoContrato, java.lang.String nrContrato, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o irÃ¡ estornar a parcela da venda.
     */
    public com.bonsucesso.servipa.ws.TRetCustom fncEstornaPrestacao(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, int tipoContrato, java.lang.String nrContrato, int lojaContrato, int nrPrestacao, java.lang.String dtPgto, float vlrPgto, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o irÃ¡ gravar uma observaÃ§Ã£o no contrato.
     */
    public com.bonsucesso.servipa.ws.TRetCustom fncGravaObservacao(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, int tipoContrato, java.lang.String nrContrato, java.lang.String dsObs, int idPerm, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Retorna a lista de ocupaÃ§Ãµes
     */
    public com.bonsucesso.servipa.ws.TRetCustomCombo fncListaOcupacao(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Retorna a lista de moradias
     */
    public com.bonsucesso.servipa.ws.TRetCustomCombo fncListaMoradia(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Retorna a lista de meios de transportes
     */
    public com.bonsucesso.servipa.ws.TRetCustomCombo fncListaMeioTransporte(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Retorna a lista de Estado Civil
     */
    public com.bonsucesso.servipa.ws.TRetCustomCombo fncListaEstadoCivil(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Retorna a lista de Tipos de EndereÃ§o
     */
    public com.bonsucesso.servipa.ws.TRetCustomCombo fncListaTipoEndereco(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Retorna a lista de Quantidade de Dependentes
     */
    public com.bonsucesso.servipa.ws.TRetCustomCombo fncListaDependente(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Retorna a lista de Tipo de Parentesco
     */
    public com.bonsucesso.servipa.ws.TRetCustomCombo fncListaParentesco(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Retorna a lista de Nacionalidade
     */
    public com.bonsucesso.servipa.ws.TRetCustomCombo fncListaNacionalidade(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Retorna a lista de tipos de arquivos
     */
    public com.bonsucesso.servipa.ws.TRetCustomCombo fncListaTipoArquivo(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Consulta de SPC
     */
    public com.bonsucesso.servipa.ws.TRetConsultaSPC fncConsultaSPC(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Consulta SituaÃ§Ã£o do Cliente
     */
    public com.bonsucesso.servipa.ws.TRetConsultaSituacao fncConsultaSituacaoCliente(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Consulta os Arquivos Cadastrados do Cliente
     */
    public com.bonsucesso.servipa.ws.TRetConsultaArquivo fncConsultaArquivosCliente(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Envia arquivo digitalizado do cliente
     */
    public com.bonsucesso.servipa.ws.TRetCustom fncEnviaArquivoCliente(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, int nrTipoDoc, java.lang.String biArqDoc, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Calcula acordo para prestaÃ§Ãµes de um cliente
     */
    public com.bonsucesso.servipa.ws.TRetCalculaAcordo fncCalculaAcordo(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, com.bonsucesso.servipa.ws.TContratoParam[] listaContratos, float vlEntrada, float vlCapital, float vlJuro, float vlMulta, float vlCapitalOri, float vlJuroOri, float vlMultaOri, int dtPriVencto, int qtdParcelas, float vlJurosPMT, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Grava acordo do cliente
     */
    public com.bonsucesso.servipa.ws.TRetGravaAcordo fncGravaAcordo(java.lang.String paramApp, int instituicao, int loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, com.bonsucesso.servipa.ws.TContratoParam[] listaContratos, float vlEntrada, float vlCapital, float vlJuro, float vlMulta, float vlCapitalOri, float vlJuroOri, float vlMultaOri, int dtPriVencto, int qtdParcelas, float vlJurosPMT, float percCapital, float percJuro, float percMulta, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o irÃ¡ cancelar o acordo ativo do .
     */
    public com.bonsucesso.servipa.ws.TRetCustom fncCancelaAcordo(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, int codigoAcordo, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o retorna uma lista de todos os clientes alterados
     * em um determinado dia.
     */
    public com.bonsucesso.servipa.ws.TRetornoClienteAlterado fncListaClientesAlterados(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, int dataAlteracao, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o retorna uma lista de todas as vendas feitas no
     * dia.
     */
    public com.bonsucesso.servipa.ws.TRetornoVendaDia fncListaVendasDia(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, int dataAlteracao, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o retorna uma lista dos recebimentos do dia.
     */
    public com.bonsucesso.servipa.ws.TRetornoRecebimentoDia fncListaRecebimentosDia(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, int dataAlteracao, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o retorna uma lista dos recebimentos para atualizaÃ§Ã£o
     * do bd da CobranÃ§a Terceirizada.
     */
    public com.bonsucesso.servipa.ws.TRetornoRecebimentoCobradora fncListaRecebimentosCobradora(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, int dataRecebimento, java.lang.String senhaWS, java.lang.String IP, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o retorna uma lista dos recebimentos para atualizaÃ§Ã£o
     * do bd da CobranÃ§a Terceirizada, valores que nÃ£o foram cobrados pela
     * mesma e pagos pelo cliente diretamente na loja.
     */
    public com.bonsucesso.servipa.ws.TRetornoRecebimentoCobradoraSemComissao fncListaRecebimentosCobradoraSemComissao(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, int dataRecebimento, java.lang.String senhaWS, java.lang.String IP, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o retorna os dados do limite do cliente.
     */
    public com.bonsucesso.servipa.ws.TRetornoLimiteCliente fncConsultaLimiteCliente(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o retorna as cidade do Estado.
     */
    public com.bonsucesso.servipa.ws.TRetornoConsultaListaCidade fncConsultaListaCidade(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String UF, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o retorna os dados da consulta de CEP.
     */
    public com.bonsucesso.servipa.ws.TRetornoConsultaCEP fncConsultaCEP(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String pesquisaCEP, int codigoCidade, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * FunÃ§Ã£o usada para receber arquivos Offlines para serem processados
     */
    public java.lang.String enviaArquivoOffline(com.bonsucesso.servipa.ws.EnviaArquivoOfflineParam param) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o retorna uma lista dos autorizado do cliente.
     */
    public com.bonsucesso.servipa.ws.TRetornoListaAutorizado fncListaAutorizado(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;

    /**
     * Esta funÃ§Ã£o cadastra e/ou altera um autorizado do cliente.
     */
    public com.bonsucesso.servipa.ws.TRetCustom fncCadastraAutorizado(java.lang.String paramApp, java.lang.String instituicao, java.lang.String loja, java.lang.String usuario, java.lang.String senha, java.lang.String CPFCNPJ, java.lang.String dsNome, java.lang.String dsDDD, java.lang.String dsTelefone, java.lang.String dsCPF, java.lang.String dsRG, int dtLimite, float vlLimite, int nrAtivo, java.lang.String senhaWS, java.lang.String IP, java.lang.String dadosCliente, java.lang.String nomeURL) throws java.rmi.RemoteException;
}
