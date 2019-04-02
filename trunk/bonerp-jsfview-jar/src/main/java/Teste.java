import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.ocabit.utils.strings.StringUtils;


public class Teste {

	static String[] campos_PROD = {
			"Empresa                                        Código da Empresa",
			"NrNfe                                          Número da NF-e",
			"SrNfe                                          Série da NF-e",
			"Prod_nitem                                     Número de seqüência do Item na nota",
			"Prod_cprod                                     Código do Produto/Serviço",
			"Prod_cean                                      EAN",
			"Prod_xprod                                     Descrição Produto",
			"Prod_ncm                                       NCM",
			"Prod_EX_Tipi                                   EX Tipi",
			"Prod_Genero                                    Gênero do produto (capítulos NCM)",
			"Prod_cfop                                      CFOP",
			"Prod_ucom                                      Unidade Comercial",
			"Prod_qcom                                      Quantidade Comercial",
			"Prod_vuncom                                    Valor unitário Comercial",
			"Prod_vprod                                     Valor do Item (qcom * vuncom)",
			"Prod_vfrete                                    Valor Total do frete",
			"Prod_vseguro                                   Valor Total do Seguro",
			"Prod_vdesc                                     Valor do Desconto",
			"Prod_voutra                                    Valor Outras Despesas",
			"indTot                                         Este campo deverá ser preenchido com:",
			"Prod_di_nDi                                    Nr. Documento de Importação",
			"Prod_di_ddi                                    Data de Registro",
			"Prod_di_xLocDesemb                             Local de desembaraço",
			"Prod_di_ufDesemb                               UF Desembaraço",
			"Prod_di_ddesemb                                Data Desembaraço",
			"Prod_di_cexportador                            Código do Exportador",
			"Prod_di_adi_nAdicao                            Número da Adição",
			"Prod_di_nseqadic                               Número seq. Do item dentro da adição",
			"Prod_di_cfabricante                            Código do fabricante",
			"Prod_di_vdescdi                                Valor do desconto do item da DI – adição",
			"xPed                                           numero pedido",
			"nItemPed                                       Informação de interesse do emissor para controle do B2B",
			"Prod_veicprod_tpop                             1-Venda Conces. 2-Fat. Direto 3-Venda  direta 0-Outros",
			"Prod_veicprod_chassi                           Chassi",
			"Prod_veicprod_ccor                             Código da cor",
			"Prod_veicprod_xcor                             Nome da cor",
			"Prod_veicprod_pot                              Potencia do motor",
			"Prod_veicprod_cm3                              CM3(Potencia)",
			"Prod_veicprod_pesol                            Peso Liquido",
			"Prod_veicprod_pesob                            Peso Bruto",
			"Prod_veicprod_nserie                           Número de série",
			"Prod_veicprod_tpcomb                           Tipo Combustível",
			"Prod_veicprod_nMotor                           Número Motor",
			"Prod_veicprod_cmkg                             CMKG",
			"Prod_veicprod_dist                             Distancia entre eixos",
			"Prod_veicprod_renavam                          RENAVAM",
			"Prod_veicprod_anomod                           Ano Modelo",
			"Prod_veicprod_anofab                           Ano Fabricação",
			"Prod_veicprod_tppint                           Tipo pintura",
			"Prod_veicprod_tpveic                           Usar Tabela RENAVAM",
			"Prod_veicprod_espveic                          Usar Tabela RENAVAM",
			"Prod_veicprod_vin                              VIN (Vehicle identification number)",
			"Prod_veicprod_condveic                         1-acabado, 2-inacabado, 3-semi-acabado",
			"Prod_veicprod_cmod                             Usar Tabela RENAVAM",
			"Prod_med_nlote                                 Número do lote de medicamento",
			"Prod_med_qlote                                 Quantidade produto no lote",
			"Prod_med_dfab                                  Data Fabricação",
			"Prod_med_dval                                  Data Validade",
			"Prod_med_vpmc                                  Preço máximo consumidor",
			"Prod_arma_tparma                               1-Uso Permitido, 2-Uso Restrito",
			"Prod_arma_nserie                               Número de série",
			"Prod_arma_ncano                                Número de série do cano",
			"Prod_arma_desc                                 Descrição Arma",
			"Prod_impostos_icms_ori                         Origem da Mercadoria",
			"Prod_impostos_icms_cst                         CST Mercadoria",
			"Prod_impostos_icms_modbc                       Modalidade Base de Calculo",
			"Prod_impostos_icms_pRedBc                      %Redução Base de Calculo",
			"Prod_impostos_icms_vbc                         Valor da Base de Calculo",
			"Prod_impostos_icms_picms                       %ICMS (Alíquota)",
			"Prod_impostos_icms_vicms                       Valor do ICMS",
			"Prod_impostos_icms_modbcst                     Modalidade Base Calculo Icms ST",
			"Prod_impostos_icms_pmvast                      Perc. Margem do Valor Adic. ICMS ST",
			"Prod_impostos_icms_predbcst                    % Redução Base ICMS ST",
			"Prod_Impostos_icms_vbcst                       Valor Base Calc. ICMS ST",
			"Prod_Impostos_icms_picmsst                     %ICMS ST",
			"Prod_Impostos_icms_vicmsst                     Valor Icms ST",
			"Prod_impostos_ipi_clenq                        Classe enq. Do IPI Cigarros e Bebidas",
			"Prod_impostos_ipi_cnpjprod                     CNPJ do Produto",
			"Prod_impostos_ipi_cselo                        Codigo do selo de controle IPI",
			"Prod_impostos_ipi_qselo                        Quantidade de selo de controle",
			"Prod_impostos_ipi_cenq                         Código enquadramento legal ipi",
			"Prod_impostos_ipi_ipitrib_cst                  CST IPI Tributável",
			"Prod_impostos_ipi_ipitrib_vbc                  valor base de calculo",
			"Prod_impostos_ipi_ipitrib_qUnid                Quantidade Tributável Prod.trib.por unid",
			"Prod_impostos_ipi_ipitrib_vunid                Valor Unidade Tributável",
			"Prod_impostos_ipi_ipitrib_pipi                 Aliquota do IPI",
			"Prod_impostos_ipi_ipitrib_vipi                 Valor do IPI",
			"Prod_impostos_ipi_IPINt_cst                    CST Ipi Não tributável",
			"Prod_Impostos_ii_vbc                           Base de Calculo Imposto Importação",
			"Prod_Impostos_ii_vdespadu                      Despesas Aduaneiras",
			"Prod_Impostos_ii_vii                           Valor do Imposto Importação",
			"Prod_Impostos_ii_viof                          Valor IOF Importação",
			"Prod_Impostos_PIS_CST                          CST Pis",
			"Prod_Impostos_PIS_vbc                          Base de Calculo PIS",
			"Prod_Impostos_PIS_ppis                         Percentual PIS",
			"Prod_Impostos_PIS_qbcProd                      Quantidade Base de Calculo",
			"Prod_Impostos_PIS_vAliqProd                    Aliquota Por unidade produto",
			"Prod_Impostos_PIS_vpis                         Valor do PIS",
			"Prod_Impostos_COFINS_CST                       CST COFINS",
			"Prod_Impostos_COFINS_vbc                       Base de Calculo COFINS",
			"Prod_Impostos_COFINS_pCOFINS                   Percentual COFINS",
			"Prod_Impostos_COFINS_qbcProd                   Quantidade Base de Calculo",
			"Prod_Impostos_COFINS_vAliqProd                 Aliquota Por unidade produto",
			"Prod_Impostos_COFINS_vCOFINS                   Valor do COFINS",
			"Prod_Impostos_issqn_vbc                        Base de Calculo Serviços",
			"Prod_Impostos_issqn_valiq                      Aliquota ISS",
			"Prod_Impostos_issqn_vissqn                     Valor do Iss",
			"Prod_Impostos_issqn_cMunfg                     Código Município IBGE",
			"Prod_Impostos_issqn_clistserv                  Código da lista de serviços",
			"Prod_informacoes_infadprod                     Informações Adicionais",
			"Prod_anp                                       Código ANP - Obrigatorio",
			"Prod_codif                                     CODIF – Não obrigatório",
			"Prod_qTemp                                     Quantidade em temperatura ambiente",
			"Prod_ufcons                                    Código do Estado",
			"Prod_qBCprod	                               Base calculo CIDE",
			"Prod_vAliqProd                                 Valor da alíquota da CIDE",
			"Prod_vcide                                     Valor da CIDE",
			"Infv_xped                                      Pedido e item do pedido de compra" };

	static int[] index_PROD = { 1, 5, 14, 17, 20, 40, 60, 120, 128, 138, 140, 144, 150, 165, 180, 195, 210, 225, 240,
			255, 256, 266, 276, 336, 338, 348, 358, 368, 378, 398, 412, 427, 433, 434, 451, 455, 495, 499, 503, 513,
			523, 532, 540, 561, 570, 574, 583, 587, 591, 592, 594, 595, 596, 606, 612, 632, 647, 657, 667, 682, 683,
			692, 701, 801, 802, 804, 805, 810, 825, 830, 845, 846, 861, 866, 881, 886, 901, 906, 920, 1020, 1030, 1033,
			1036, 1051, 1066, 1081, 1086, 1101, 1104, 1119, 1134, 1149, 1164, 1167, 1182, 1187, 1202, 1217, 1232, 1235,
			1250, 1255, 1270, 1285, 1300, 1315, 1320, 1335, 1342, 1347, 1597, 1606, 1627, 1643, 1645, 1661, 1676,
			1691 };

	static String[] campos_CABECALHO = { "exEmpresa                       Código da Empresa",
			"Versao                          3.1",
			"Id                              Identificação da Nfe precedido de NF-e",
			"Ide_cuf                         Código UF Tabela IBGE",
			"Ide_cnf                         Número único que identifica a NF-e",
			"Ide_natop                       Descrição do CFOP",
			"Ide_indpag                      0 a vista, 1 a prazo 2 outros",
			"Ide_mod_                        55",
			"Ide_serie                       ex: 001   900 Scan Nacional",
			"Ide_nnf                         Número da NF-e",
			"Ide_demi                        Data de Emissão Formato AAAA-MM-DD",
			"Ide_DSaiEnt                     Data Emissão/Entrada Formato AAAA-MM-DD",
			"hSaiEnt                         Formato “HH:MM:SS”",
			"Ide_tpnf                        0 Entrada 1 Saída",
			"Ide_cmunfg                      Cód.Municípi IBGE",
			"Ide_NFref                       NF-e Referenciada",
			"Ide_NFref_cUf                   Cód.UF.IBGE NF Referenciada",
			"Ide_NFref_AAMM                  Ano e Mês de Emissão NF Referenciada",
			"Ide_NFref_CNPJ                  CNPJ Emissor NF Referenciada",
			"Ide_NFRef_Mod                   Modelo NF Referenciada",
			"Ide_NFRef_Serie                 Série NF Referenciada",
			"Ide_NFRef_Nota                  Número NF Referenciada",
			"Ide_tpimp                       1 Retrato",
			"Ide_tpEmis                      1 Normal 2 Contingência 3 Scan Nacional",
			"Ide_cdv                         Digito Verificador da Nf-e",
			"Ide_tpAmb                       1 Produção 2 Homologação",
			"Ide_finnfe                      1 Normal, 2 Complementar, 3 de Ajuste, 4 de Devolução",
			"Ide_procemi                     0 Emissão Aplicativo Contribuinte",
			"Ide_verproc                     Versão do Aplicativo",
			"dhCont                          AAAA-MM-DD",
			"xJust                           Justificativa Contingencia",
			"Emi_Cnpj                        CNPJ Do Emitente",
			"Emi_cpf                         00000000000",
			"Emi_xNome                       Nome do Emitente",
			"Emi_xFant                       Nome Fantasia do Emitente",
			"Emi_EnderEmit_xLgr              Logradouro Emitente",
			"Emi_EnderEmit_Nro               Número Logradouro",
			"Emi_EnderEmit_xCpl              Complemento",
			"Emi_EnderEmit_xBairro           Bairro",
			"Emi_EnderEmit_cMun              Cód. Município IBGE do Emitente",
			"Emi_EnderEmit_xMun              Nome do Município do Emitente",
			"Emi_EnderEmit_uf                UF Do Emitente",
			"Emi_EnderEmit_Cep               CPE do Emitente",
			"Emi_EnderEmit_Cpais             País do Emitente = 1058",
			"Emi_EnderEmit_xPais             BRASIL",
			"Emi_EnderEmit_Fone              Telefone do Emitente",
			"Emi_EnderEmit_Ie                Inscrição Estadual do Emitente",
			"Emi_EnderEmit_Iest              Inscrição Estadual Subs.Tributário",
			"Emi_EnderEmit_Im                Inscrição Municipal Emitente",
			"Emi_EnderEmit_Cnae              CNAE do Emitente",
			"CRT                             Este campo será obrigatoriamente preenchido com: 1 – Simples Nacional;",
			"Des_Cnpj                        CNPJ Do Destinatário",
			"Dês_Cpf                         CPF Do Destinatário",
			"Des_xNome                       Nome/Razão Social Destinatário",
			"Des_EnderDest_xLgr              Logradouro Destinatário",
			"Des_EnderDest_Nro               Número Logradouro Destinatário",
			"Des_EnderDest_xCpl              Complemento Logradouro Destinatário",
			"Des_EnderDest_xBairro           Bairro Destinatário",
			"Des_EnderDest_cmun              Código Município Destinatário IBGE",
			"Des_EnderDest_xMun              Nome Município Destinatário",
			"Des_EnderDest_Uf                UF Destinatário",
			"Des_EnderDest_Cep               CEP Do Destinatário",
			"Des_EnderDest_Cpais             Código País do Destinatário",
			"Des_EnderDest_xPais             Nome País do Destinatário",
			"Des_EnderDest_Fone              Telefone do Destinatário",
			"Des_EnderDest_Ie                Inscrição Estadual Destinatário",
			"Des_EnderDest_Isuf              Suframa Destinatário",
			"Email                           email destinatário",
			"Ret_Cnpj                        CNPJ Do local de retirada",
			"Ret_xLgr                        Logradouro Local de Retirada",
			"Ret_nro                         Número Logradouro Local de Retirada",
			"Ret_xCpl                        Complemento Logradouro Local de Retirada",
			"Ret_xBairro                     Bairro Local de Retirada",
			"Ret_cmun                        Código Município Local de Retirada",
			"Ret_xmun                        Nome Município Local de Retirada",
			"Ret_Uf                          UF Local de Retirada",
			"Ent_Cnpj                        Cnpj Local de Entrega",
			"Ent_nro                         Número Logradouro Local de Entrega",
			"Ent_xLgr                        Logradouro Local de Entrega",
			"Ent_xCpl                        Complemento Local de Entrega",
			"Ent_xBairro                     Bairro Local de Entrega",
			"Ent_Cmun                        Código Município Local de Entrega",
			"Ent_xMun                        Nome Município Local de Entrega",
			"Ent_Uf                          UF  Local de Entrega",
			"IcmsTot_vbc                     Total Base Calculo Icms",
			"IcmsTot_vicms                   Total ICMS",
			"IcmsTot_vbcst                   Total Base Calculo Icms ST",
			"IcmsTot_vst                     Total do ICMS ST",
			"IcmsTot_vprod                   Valor Total dos Produtos",
			"IcmsTot_vfrete                  Valor Total do Frete",
			"IcmsTot_vseg                    Valor Total do Seguro",
			"IcmsTot_vdesc                   Valor Total do Desconto",
			"IcmsTot_vii                     Valor Total Imposto Importação",
			"IcmsTot_vipi                    Valor Total do IPI",
			"IcmsTot_vpis                    Valor Total do PIS",
			"IcmsTot_vcofins                 Valor Total do COFINS",
			"IcmsTot_voutro                  Valor Total Outras Despesas",
			"IcmsTot_vnf                     Valor Total da Nota Fiscal",
			"Issqnto_vserv                   Valor Total dos Serviços",
			"Issqnto_vbc                     Valor Base de Calculo ISS",
			"Issqnto_viss                    Valor do ISS",
			"Issqnto_vpis                    Valor do PIS Serviços",
			"Issqnto_vcofins                 Valor do COFINS Serviços",
			"Issqnto_vretpis                 Valor do PIS Retido",
			"Issqnto_vretcofins              Valor do COFINS Retido",
			"Issqnto_vretcsll                Valor CSLL Retido",
			"Issqnto_vbcirrf                 Base de Calculo do IRRF",
			"Issqnto_virrf                   Valor do IRRF",
			"Issqnto_vbcretprev              Base de Calculo INSS",
			"Issqnto_vretPrev                INSS Retido",
			"Transp_ModFrete                 Frete O Emitente 1 Destinatario",
			"Transp_Transporta_Cnpj          CNPJ Transportador",
			"Transp_Transporta_Cpf           CPF Transportador",
			"Transp_Transporta_xNome         Nome Transportador",
			"Transp_Transporta_ie            Inscrição Estadual",
			"Transp_Transporta_xEnder        Endereço Transportador",
			"Transp_Transporta_xMun          Nome Município Transportador",
			"Transp_Transporta_uf            UF Transportador",
			"Transp_T_RetTransp_vServ        Valor do Serviço de Frete",
			"Transp_T_RetTransp_vbcret       Valor Base de Calculo Frete Retido",
			"Transp_T_RetTransp_picmsRet     Alíquota ICMS Retido",
			"Transp_T_RetTransp_vicmsret     Valor do ICMS Retido",
			"Transp_T_RetTransp_cfop         CFOP Icms Retido Frete",
			"Transp_T_RetTransp_CMunFG       Código Município IBGE",
			"Transp_T_RetTransp_TVeiculo_Pl  Placa do Veículo",
			"Transp_T_RetTransp_TVeiculo_UF  UF Veículo",
			"Transp_T_RetTransp_TVeiculos_rn RNTC (Reg. Nac. de Transp.de Carga)",
			"Transp_T_RetTransp_Reboque_pla  Placa Do Reboque",
			"Transp_T_RetTransp_Reboque_uf   UF do Reboque",
			"Transp_T_RetTransp_Reboque_rnt  RNTC Reboque",
			"Transp_T_RetTransp_Vol_Qvol     Quantidade Volumes",
			"Transp_T_RetTransp_Vol_Esp      Espécie",
			"Transp_T_RetTransp_Vol_Marca    Marca",
			"Transp_T_RetTransp_Vol_nvol     Numeração dos Volumes Transportados",
			"Transp_T_RetTransp_Vol_pesol    Peso Liquido",
			"Transp_T_RetTransp_Vol_pesob    Peso Bruto",
			"Transp_T_RetTransp_Lacres_nLac  Número dos Lacres",
			"Cobranca_nFat                   Número da Fatura",
			"Cobranca_vOrig                  Valor Original",
			"Cobranca_vDesc                  Valor Descontos",
			"Cobranca_vLiqu                  Valor Liquido",
			"InfAdic_infCpl                  Informações Adicionais",
			"InfAdic_InfAdFisco              Informações adicionais Fisco",
			"Exporta_UFEmbarq                UF Embarque quando exportação",
			"Exporta_xLocEmbarq              Local do Embarque",
			"Compra_xNemp                    Número Empenho Órgão Publico",
			"Compra_xPed                     Número do Pedido Órgão Publico",
			"Compra_xCont                    Número do Contrato Órgão Publico",
			"Cliente                         Código do Cliente",
			"Observacoes                     " };

	static int[] index_CABECALHO = { 1, 5, 9, 59, 61, 70, 130, 131, 133, 136, 145, 155, 165, 173, 174, 181, 225, 227,
			231, 245, 247, 250, 259, 260, 261, 262, 263, 264, 265, 285, 295, 551, 565, 576, 636, 696, 756, 766, 786,
			846, 853, 913, 915, 923, 927, 987, 1001, 1015, 1029, 1044, 1051, 1052, 1066, 1077, 1137, 1197, 1207, 1227,
			1287, 1294, 1354, 1356, 1364, 1368, 1428, 1442, 1456, 1465, 1525, 1539, 1599, 1609, 1629, 1689, 1696, 1756,
			1758, 1772, 1782, 1842, 1862, 1922, 1929, 1989, 1991, 2005, 2019, 2033, 2047, 2061, 2075, 2089, 2103, 2117,
			2131, 2145, 2159, 2173, 2187, 2201, 2215, 2229, 2243, 2257, 2271, 2285, 2299, 2313, 2327, 2341, 2355, 2356,
			2370, 2381, 2441, 2455, 2515, 2575, 2577, 2591, 2605, 2610, 2624, 2628, 2635, 2642, 2644, 2664, 2671, 2673,
			2693, 2708, 2768, 2828, 2888, 2902, 2916, 2976, 2996, 3010, 3024, 3038, 3288, 3538, 3540, 3600, 3617, 3677,
			3737, 3743 };

	public static void main(String[] args) {
		
		
		
//		
//		String cabec = "00013.10                                                  41000000000VENDA PRAZO PROD.FORA ESTADO                                1550010001249772016-11-162016-11-1614117701                                            00000000000000000000     000000000110110                    0585597400017000000000000BASTON DO BRASIL PRODUTOS QUIMICOS LTDA                     BASTON DO BRASIL                                            AVENIDA DAS PALMEIRAS                                       1705                          COLONIA FRANCESA                                            4117701PALMEIRA                                                    PR841300001058BRASIL                                                      42325217059029696672                                 00000001068900600017100000000000BC PARALELA COMERCIAL LTDA                                  AVLUIS VIANA 8544 SHOP.PARALELA SAL.COM.                    .                             PARALELA                                                    2927408SALVADOR                                                    BA        1058BRASIL                                                      7133606428080005539              00000000000000                                                                                                                                                      0000000                                                              00000000000000                                                                                                                                                      0000000                                                              000000002620400000000001834400000000000000000000000000000000000029083200000000000000000000000000000000000000000000000000000000000000000632360000000000639600000000029957000000000000000000000035406800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000018831784700308000000000000RAPIDO TRANSPAULO LTDA                                      336890773115  AV.PAPA JOAO PAULO I - 687, GLP 1 VILA AEROPORTO            GUARULHOS                                                   SP0000000000000000000000000000000000000000000000000000000000                                                          56             CAIXAS                                                      NEEZ                                                                                                                    0000000020720000000000222800                                                            124977              000000003540680000000000000000000000000000                                                                                                                                                                                                                                                              N.ONU:1950/CLASSE:2.1/N.RISCO:23-AEROSSOIS BASE CALCULO COM DEDUCAO PIS/COFINS, CONF. CONVENIO N. 34/06 OC.1066/NEEZ PAGARA O FRETE                                                                                                                                                                                                  1066                                                                                                                    019022central@belsalvador.com.br;sneez@neez.com.br                                                        Observacoes: Volumes contendo produtos perigosos em qtde. limitadas p/ embalagens internas, isentos do porte do selo de Identificacao da Conformidade nos termos da Res. ANTT n. 420/04.";
//		
//		String linha1 = "";
//		String linha2 = "";
//		String linha3 = "";
//		String linha4 = "";
//		
//		for (int i=1 ; i<=cabec.length() ; i++) {
//			linha1 += ("" + i).substring(0, 1);
//			if (i>9)
//				linha2 += ("" + i).substring(1, 2);
//			else linha2 += " ";
//			if (i > 99) 
//				linha3 += ("" + i).substring(2, 3);
//			else linha3 += " ";
//			if (i > 999) 
//				linha4 += ("" + i).substring(3, 4);
//			else linha4 += " ";		
//			
//		}
//		
//		System.out.println(cabec);
//		System.out.println(linha1);
//		System.out.println(linha2);
//		System.out.println(linha3);
//		System.out.println(linha4);
//		
		

		 process_CABECALHO();
		// process_PROD();

	}

	public static void process_CABECALHO() {
		try {
			List<String> linhas = Files
					.readLines(new File("C:\\Users\\Carlos\\Desktop\\Integração Spartacus\\NOTA_bonerp.txt"), Charset
							.defaultCharset());

			for (String linha : linhas) {

				for (int i = 0; i < index_CABECALHO.length; i++) {

					int ini = index_CABECALHO[i] - 1;
					int fim = i < index_CABECALHO.length - 1 ? index_CABECALHO[i+1] - 1 : -1;
					
					System.out.print(Strings.padEnd(campos_CABECALHO[i], 120, ' '));
					System.out.print(" >>>>> ");
					System.out.print(" (" + (ini+1) + "/" + fim + ") ");
					if (i == index_CABECALHO.length - 1) {
						System.out.println("[" + linha.substring(ini) + "]");
					} else {
						System.out.println("[" + linha.substring(ini, fim) + "]");
					}
				}

				System.out.println("--------------------------------------------");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void process_PROD() {
		try {
			List<String> linhas = Files
					.readLines(new File("C:\\Users\\Carlos\\Desktop\\Integração Spartacus\\NOTAPROD_bonerp.txt"), Charset
							.defaultCharset());
			
			for (String linha : linhas) {
				
				System.out.println(linha);
				System.out.println("...........................");
				
				for (int i = 0; i < index_PROD.length; i++) {
					
					int ini = index_PROD[i] - 1;
					int fim = i < index_PROD.length - 1 ? index_PROD[i+1] - 1 : -1;
					
					System.out.print(Strings.padEnd(campos_PROD[i], 120, ' '));
					System.out.print(" >>>>> ");
					System.out.print(" (" + (ini+1) + "/" + fim + ") ");
					if (i == index_PROD.length - 1) {
						System.out.println("[" + linha.substring(ini) + "]");
					} else {
						System.out.println("[" + linha.substring(ini, fim) + "]");
					}
				}
				
				System.out.println("--------------------------------------------");
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
