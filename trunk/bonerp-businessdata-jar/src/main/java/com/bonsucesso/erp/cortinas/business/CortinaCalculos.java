package com.bonsucesso.erp.cortinas.business;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.cortinas.data.ArtigoCortinaFinder;
import com.bonsucesso.erp.cortinas.model.Cortina;
import com.bonsucesso.erp.cortinas.model.CortinaItem;
import com.bonsucesso.erp.cortinas.model.CortinaLado;
import com.bonsucesso.erp.cortinas.model.OrientacaoTecido;
import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * Classe responsável por executar cálculos de itens de cortinas.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("cortinaCalculos")
public class CortinaCalculos {

	protected static Logger logger = Logger.getLogger(CortinaCalculos.class);

	@Autowired
	private ArtigoCortinaFinder artigoCortinaFinder;

	public static void main(String[] args) throws ViewException {
		//CortinaCalculos calcCortinas = new CortinaCalculos();
		//calcCortinas.doIt();
	}

	/**
	 * Executa os cálculos sobre todos os itens da cortina.
	 *
	 * @param cortina
	 * @throws ViewException
	 */
	public void executar(Cortina cortina) throws ViewException {

		if ((cortina.getLargurasLados() == null) || (cortina.getLargurasLados().size() == 0)) {
			throw new ViewException("Faltam informações sobre os lados.");
		}

		// Primeiro calcula as quantidades para os tecidos
		for (CortinaItem item : cortina.getItens()) {
			logger.debug("Calculando para o item: " + item.getArtigoCortina().getProduto().getDescricao());
			if (item.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)
					&& item.getDrapeado() == false) {
				calcularQtdeTecidos(item);
			}
		}
		// Depois calcula entretelas
		for (CortinaItem item : cortina.getItens()) {
			if (item.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.ENTRETELA)) {
				calcularQtdeEntretela(item);
			}
		}

		// Depois calcula drapeados
		for (CortinaItem item : cortina.getItens()) {
			if (item.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)
					&& item.getDrapeado() == true) {
				calcularQtdeDrapeado(item);
			}
		}

		boolean temIlhos = false;
		boolean temDetalhes = false;

		for (CortinaItem item : cortina.getItens()) {
			switch (item.getArtigoCortina().getTipoArtigoCortina()) {
				case TECIDO:
					if (item.getDrapeado() || item.getBando())
						temDetalhes = true;
					break;
				case RODIZIO:
				case ARGOLA:
				case GANCHO:
					calcularQtdeRodizioArgolaGancho(item, 0.10);
					break;
				case TRILHO:
				case VARAO:
					calcularQtdeVaraoTrilho(item, 0.10);
					break;
				case ILHOS:
					temIlhos = true;
					calcularQtdeIlhos(item, 0.15);
					break;
				case SUPORTE_DUPLO:
				case SUPORTE_SIMPLES:
				case SUPORTE_TRIPLO:
					calcularQtdeSuporte(item);
					break;
				case FRANJA:
					calcularQtdeFranja(item);
					break;
				default:
					break;
			}
		}

		calcularQtdeMaoDeObraCostureira(cortina);
		calcularQtdeMaoDeObraInstalacao(cortina);

		if (temIlhos) {
			calcularQtdeMaoDeObraIlhos(cortina);
		}
		if (temDetalhes) {
			calcularQtdeMaoDeObraDetalhes(cortina);
		}

	}

	/**
	 * O cálculo da mão-de-obra da costureira é feito a partir da quantidade
	 * total de tecido.
	 *
	 * @param item
	 *            O item MAO_DE_OBRA_COSTUREIRA.
	 * @throws ViewException
	 */
	private void calcularQtdeMaoDeObraCostureira(Cortina cortina) throws ViewException {
		BigDecimal bdQtdeTecido = BigDecimal.ZERO;
		CortinaItem ciMaoDeObraCostureira = null;
		for (CortinaItem i : cortina.getItens()) {
			if (i.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)) {
				bdQtdeTecido = bdQtdeTecido.add(i.getQtde());
			} else if (i.getArtigoCortina().getTipoArtigoCortina()
					.equals(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_TECIDO)) {
				ciMaoDeObraCostureira = i;
			}
		}

		if (ciMaoDeObraCostureira != null) {
			if (ciMaoDeObraCostureira.getQtdeNaoAlterar() == Boolean.TRUE) {
				return;
			}
			ciMaoDeObraCostureira.setQtde(bdQtdeTecido);
		} else {
			if (cortina.isCortinaCompleta()) {
				throw new ViewException("Item 'mão-de-obra costureira' não encontrado");
			}
		}
	}

	/**
	 * Calcula a qtde de mão-de-obra pra detalhes: drapeado e bandô.
	 * 
	 * @param cortina
	 * @throws ViewException
	 */
	private void calcularQtdeMaoDeObraDetalhes(Cortina cortina) throws ViewException {
		BigDecimal bdQtde = BigDecimal.ZERO;
		CortinaItem ciMaoDeObraDetalhes = null;
		for (CortinaItem i : cortina.getItens()) {
			if (i.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)) {
				if (i.getDrapeado() == true) {
					// o cálculo da M.O. do drapeado é feito somente sobre a largura do mesmo, e não sobre o total de tecidos de drapeado
					// E também não é sobre a largura da cortina, pois existem casos onde o drapeado vai até a metade da janela, com a queda no meio.
					bdQtde = bdQtde.add(i.getDrapeadoLargura());
				} else if (i.getBando() == true) {
					bdQtde = bdQtde.add(cortina.getLargura());
				}
			} else if (i.getArtigoCortina().getTipoArtigoCortina()
					.equals(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_DETALHES)) {
				ciMaoDeObraDetalhes = i;
			}
		}
		if (ciMaoDeObraDetalhes != null) {
			if (ciMaoDeObraDetalhes.getQtdeNaoAlterar() == Boolean.TRUE) {
				return;
			}
			ciMaoDeObraDetalhes.setQtde(bdQtde);
		} else {
			if (cortina.isCortinaCompleta()) {
				throw new ViewException("Item 'mão-de-obra detalhes' não encontrado");
			}
		}
	}

	private void calcularQtdeMaoDeObraIlhos(Cortina cortina) throws ViewException {
		BigDecimal bdQtdeIlhos = BigDecimal.ZERO;
		CortinaItem ciMaoDeObraIlhos = null;
		for (CortinaItem i : cortina.getItens()) {
			if (i.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.ILHOS)) {
				bdQtdeIlhos = bdQtdeIlhos.add(i.getQtde());
			} else if (i.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.MAO_DE_OBRA_ILHOS)) {
				ciMaoDeObraIlhos = i;
			}
		}
		if (ciMaoDeObraIlhos != null) {
			if (ciMaoDeObraIlhos.getQtdeNaoAlterar() == Boolean.TRUE) {
				return;
			}
			ciMaoDeObraIlhos.setQtde(bdQtdeIlhos);
		} else {
			if (cortina.isCortinaCompleta()) {
				throw new ViewException("Item 'mão-de-obra ilhós' não encontrado");
			}
		}
	}

	private void calcularQtdeMaoDeObraInstalacao(Cortina cortina) throws ViewException {
		CortinaItem ciMaoDeObraInstalacao = null;
		for (CortinaItem i : cortina.getItens()) {
			if (i.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.MAO_DE_OBRA_INSTALACAO)) {
				ciMaoDeObraInstalacao = i;
				break;
			}
		}
		if (ciMaoDeObraInstalacao != null) {
			if (ciMaoDeObraInstalacao.getQtdeNaoAlterar() == Boolean.TRUE) {
				return;
			}
			ciMaoDeObraInstalacao.setQtde(BigDecimal.ONE);
		} else {
			if (cortina.isComInstalacao()) {
				throw new ViewException("Item 'mão-de-obra instalação' não encontrado");
			}
		}
	}

	public void calcularQtdeTecidos(CortinaItem itemCortinaTecido) throws ViewException {

		if (itemCortinaTecido.getOrientacao() == null) {
			throw new ViewException("A orientação do tecido deve ser informada.");
		}

		if (itemCortinaTecido.getQtdeNaoAlterar() == Boolean.TRUE) {
			return;
		}

		OrientacaoTecido orientacaoAUtilizar = itemCortinaTecido.getOrientacao();

		// verifica se o corte o tecido será usado horizontalmente ou verticalmente
		// horizontalmente, se cortina.altura + tecido.alturaBarra <= tecido.largura
		// 	ex.: 	cortina.altura = 		2.50
		//			tecido.alturaBarra = 	0.30
		//			-----------------    	2.80
		//			tecido.largura = 		3.00
		// verticalmente, o oposto.
		// TODO: * Porém, verificar como considerar quando a diferença for pequena,
		// como por exemplo 2.80 + 0.30 = 3.10 (fazer barra de 0.20?)

		Cortina cortina = itemCortinaTecido.getCortina();

		BigDecimal larguraCortina = itemCortinaTecido.getCortina().getLargura();
		BigDecimal fatorTecido = itemCortinaTecido.getFatorReal();
		BigDecimal qtdeTecido = BigDecimal.ZERO;

		BigDecimal larguraTecido = itemCortinaTecido.getArtigoCortina().getTecido().getLargura();

		// considerando a barra
		BigDecimal alturaComBarra = itemCortinaTecido.getAlturaBarra().add(itemCortinaTecido.getCortina().getAltura());

		if (orientacaoAUtilizar.equals(OrientacaoTecido.VERTICAL)) {

			if (itemCortinaTecido.getLarguras() == null) {

				BigDecimal bdTecidoLargura = itemCortinaTecido.getArtigoCortina().getTecido().getLargura();
				if (larguraTecido.doubleValue() >= larguraCortina.multiply(fatorTecido).doubleValue()) {
					itemCortinaTecido.setLarguras(1);
				} else {
					// Se o fator do tecido não for número inteiro, é porque já foi selecionado na tabela de eficiência largura/fator
					itemCortinaTecido.setLarguras(cortina.getLargura().multiply(fatorTecido)
							.divide(bdTecidoLargura, 0, RoundingMode.HALF_DOWN)
							.intValue());
				}
			}

			qtdeTecido = qtdeTecido
					.add(alturaComBarra.multiply(CurrencyUtils
							.getBigDecimalCurrency(Double.valueOf("" + itemCortinaTecido.getLarguras()))));
		} else if (orientacaoAUtilizar.equals(OrientacaoTecido.HORIZONTAL)) {
			if (itemCortinaTecido.getBando()) {
				qtdeTecido = larguraCortina.multiply(fatorTecido);
			} else {
				if (alturaComBarra.doubleValue() <= itemCortinaTecido.getArtigoCortina().getTecido().getLargura()
						.doubleValue()) {
					qtdeTecido = larguraCortina.multiply(fatorTecido);
				} else {
					throw new ViewException("Tecido muito curto para ser usado horizontalmente nesta altura de janela (Largura Tecido: "
							+ itemCortinaTecido.getArtigoCortina().getTecido().getLargura()
							+ ", Altura Cortina (c/ barra): "
							+ alturaComBarra);
				}
			}
		}
		itemCortinaTecido.setQtde(qtdeTecido);
	}

	/**
	 * 
	 * @param tecidoCortina
	 * @throws ViewException
	 */
	private void calcularQtdeDrapeado(CortinaItem tecidoCortina) throws ViewException {
		if (tecidoCortina.getQtdeNaoAlterar() == Boolean.TRUE) {
			return;
		}
		if (tecidoCortina.getDrapeadoLances() == 0) {
			throw new ViewException("Deve ser informada a qtde de lances do drapeado (acima de 0).");
		}

		if (tecidoCortina.getDrapeadoAltura1() == null && tecidoCortina.getDrapeadoAltura2() == null) {
			throw new ViewException("Deve ser informada a(s) altura(s) do drapeado.");
		} else {
			double altura1 = 0;
			double altura2 = 0;
			if (tecidoCortina.getDrapeadoAltura1() != null) {
				altura1 = tecidoCortina.getDrapeadoAltura1().doubleValue();
			}
			if (tecidoCortina.getDrapeadoAltura2() != null) {
				altura2 = tecidoCortina.getDrapeadoAltura2().doubleValue();
			}
			if (altura1 + altura2 <= 0.0) {
				throw new ViewException("Deve ser informada a(s) altura(s) do drapeado.");
			}
		}

		if (tecidoCortina.getDrapeadoLargura() == null) {
			throw new ViewException("Deve ser informada a largura do drapeado.");
		}

		BigDecimal drapeadoAltura = tecidoCortina.getDrapeadoAltura1().add(tecidoCortina.getDrapeadoAltura2());
		BigDecimal drapeadoLargura = tecidoCortina.getDrapeadoLargura();
		BigDecimal drapeadoLances = CurrencyUtils
				.getBigDecimalScaled(Double.valueOf("" + tecidoCortina.getDrapeadoLances()), 2);

		// LARGURA DA CORTINA + 1m A CADA LANCE + (ALTURA DO DRAPEADO * LADOS DO DRAPEADO)
		tecidoCortina.setQtde(drapeadoLargura.add(drapeadoAltura).add(drapeadoLances));
	}

	private void calcularQtdeFranja(CortinaItem franja) throws ViewException {

		if (franja.getQtdeNaoAlterar() == Boolean.TRUE) {
			return;
		}

		CortinaItem itemTecido = null;
		for (CortinaItem item : franja.getCortina().getItens()) {
			if (item.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)
					&& item.getCamada().equals(franja.getCamada())) {
				itemTecido = item;
				franja.setQtde(itemTecido.getQtde());
				break;
			}
		}

		if (itemTecido == null) {
			throw new ViewException("Nenhum tecido encontrado para a colocação de franja nesta camada.");
		}
	}

	private void calcularQtdeEntretela(CortinaItem entretela) throws ViewException {

		if (entretela.getQtdeNaoAlterar() == Boolean.TRUE) {
			return;
		}

		CortinaItem itemTecido = null;
		for (CortinaItem item : entretela.getCortina().getItens()) {
			if (item.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)
					&& item.getCamada().equals(entretela.getCamada())) {
				itemTecido = item;
				if (itemTecido.getOrientacao().equals(OrientacaoTecido.HORIZONTAL)) {
					entretela.setQtde(itemTecido.getQtde());
				} else {
					BigDecimal bdLarguras = CurrencyUtils.getBigDecimalScaled(itemTecido.getLarguras().doubleValue(), 3);
					entretela.setQtde(itemTecido.getArtigoCortina().getTecido().getLargura().multiply(bdLarguras));
				}
			}
		}

		if (itemTecido == null) {
			throw new ViewException("Nenhum tecido encontrado para a colocação de entretela nesta camada");
		}
	}

	/**
	 * O cálculo para colocação de argolas/ganchos/rodízio é: (larguraCortina /
	 * 0,10cm) + 2. Ou seja, a cada 10cm é colocado uma argola e/ou um gancho, e
	 * adiciona-se 2 a mais. Se o resultado for ímpar, diminui 1 para arredondar
	 * para número par.
	 *
	 * @param item
	 * @param espacamento
	 * @throws ViewException
	 */
	private void calcularQtdeRodizioArgolaGancho(CortinaItem item, Double espacamento) throws ViewException {
		
		logger.debug("calcularQtdeRodizioArgolaGancho()");
		logger.debug(item.getArtigoCortina().getProduto().getDescricao());

		if (item.getQtdeNaoAlterar() == Boolean.TRUE) {
			return;
		}

		boolean encontrouTecidoComEstaFixacao = false;

		// PROCURA TECIDOS DESTA CAMADA
		for (CortinaItem i : item.getCortina().getItens()) {
			if (i.getCamada().equals(item.getCamada())
					&& i.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)) {
				if (item.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.ARGOLA)) {
					// ARGOLA - ARGOLA
					if (i.getTipoFixacao().equals(TipoArtigoCortina.ARGOLA)) {
						encontrouTecidoComEstaFixacao = true;
						break;
					}
				} else if (item.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.GANCHO)) {
					// GANCHO - (ARGOLA ou NULLED)
					if (i.getTipoFixacao().equals(TipoArtigoCortina.ARGOLA)
							|| i.getTipoFixacao().equals(TipoArtigoCortina.NULLED)) {
						encontrouTecidoComEstaFixacao = true;
						break;
					}
				} else if (item.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.RODIZIO)) {
					// RODIZIO - RODIZIO
					if (i.getTipoFixacao().equals(TipoArtigoCortina.RODIZIO)) {
						encontrouTecidoComEstaFixacao = true;
						break;
					}
				} else if (item.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.ILHOS)) {
					// ILHOS - ILHOS
					if (i.getTipoFixacao().equals(TipoArtigoCortina.ILHOS)) {
						encontrouTecidoComEstaFixacao = true;
						break;
					}
				}
			}
		}

		if (!encontrouTecidoComEstaFixacao) {
			throw new ViewException("Nenhum tecido selecionado com o tipo de fixação: "
					+ item.getArtigoCortina().getTipoArtigoCortina().getLabel() + ".");
		}

		Cortina cortina = item.getCortina();

		// Divide a largura da cortina pelo espaçamento (0,10cm)

		BigDecimal qtdeTotal = BigDecimal.ZERO;

		BigDecimal bdArred = new BigDecimal(CurrencyUtils.divide(cortina.getLargura().doubleValue(), espacamento));
		bdArred = bdArred.setScale(0, RoundingMode.UP);

		// Adiciona mais 2 unidades
		BigDecimal qtde = bdArred.add(new BigDecimal("2.0"));

		// Se for impar, reduz 1 unidade
		if ((qtde.doubleValue() % 2) != 0) {
			qtde = qtde.add(new BigDecimal("-1.0"));
		}

		qtdeTotal = qtdeTotal.add(qtde);

		item.setQtde(qtdeTotal);

	}

	public void calcularQtdeGanchos(CortinaItem item) {

		if (item.getQtdeNaoAlterar() == Boolean.TRUE) {
			return;
		}

		for (CortinaItem i : item.getCortina().getItens()) {
			if (i.getCamada().equals(item.getCamada())
					&& i.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.GANCHO)) {
				i.setQtde(item.getQtde());
			}
		}
	}

	/**
	 *
	 * @param varaoTrilho
	 * @param sobra
	 *            Geralmente são adicionados 15cm.
	 */
	private void calcularQtdeVaraoTrilho(CortinaItem varaoTrilho, Double sobra) {

		if (varaoTrilho.getQtdeNaoAlterar() == Boolean.TRUE) {
			return;
		}

		// sempre 15cm a mais por varão
		varaoTrilho.setQtde(varaoTrilho.getCortina().getLargura().add(CurrencyUtils.getBigDecimalCurrency(sobra)));
	}

	/**
	 * Calcula a quantidade de ilhós que será utilizado na cortina (baseia-se na qtde de entretela).
	 *
	 * @param ilhos
	 * @param itemTecido
	 * @param fatorIlhos
	 * @throws ViewException
	 */
	private void calcularQtdeIlhos(CortinaItem ilhos, double fatorIlhos) throws ViewException {

		if (ilhos.getQtdeNaoAlterar() == Boolean.TRUE) {
			return;
		}

		BigDecimal bdFator = CurrencyUtils.getBigDecimalScaled(fatorIlhos, 2);
		double totalIlhos = 0;
		CortinaItem entretela = null;
		for (CortinaItem item : ilhos.getCortina().getItens()) {
			// Pega o item ENTRETELA desta camada
			if (item.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.ENTRETELA)
					&& item.getCamada().equals(ilhos.getCamada())) {
				entretela = item;

				// Para cada lado da cortina, descobre quanto de ENTRETELA estará usando
				for (CortinaLado lado : ilhos.getCortina().getLargurasLados()) {
					// regra de três pra descobrir quanto de entretela vai neste lado
					BigDecimal qtdeEntretelaNesteLado = entretela.getQtde()
							.multiply(lado.getLarguraLado())
							.divide(ilhos.getCortina().getLargura(), RoundingMode.HALF_DOWN);

					int totalIlhosNesteLado = qtdeEntretelaNesteLado.divide(bdFator, RoundingMode.HALF_DOWN).intValue();

					if ((totalIlhosNesteLado % 2) != 0) {
						totalIlhosNesteLado += 1;
					}

					totalIlhos += totalIlhosNesteLado;

				}

				ilhos.setQtde(CurrencyUtils.getBigDecimalCurrency(totalIlhos));
			}
		}

		if (entretela == null) {
			throw new ViewException("Nenhuma entretela encontrada para a colocação de ilhós na camada "
					+ ilhos.getCamada() + ".");
		}

	}

	/**
	 * Verifica o tipo do suporte selecionado e adiciona conforme a qtde.
	 * 
	 * @param suporte
	 */
	private void calcularQtdeSuporte(CortinaItem suporte) {

		if (suporte.getQtdeNaoAlterar() == Boolean.TRUE) {
			return;
		}

		// Suporte pode ser somente com unidade 'UN' ou 'PAR'

		Integer qtdeUnidades;

		if (suporte.getArtigoCortina().getProduto().getUnidadeProduto().getLabel().equals("PAR")) {
			qtdeUnidades = 1;
		} else if (suporte.getArtigoCortina().getProduto().getUnidadeProduto().getLabel().equals("UN")) {
			qtdeUnidades = 2;
		} else {
			throw new IllegalStateException("Item suporte deve ser com unidade 'PAR' ou 'UN' ("
					+ suporte.getArtigoCortina().getProduto().getReduzido() + " - "
					+ suporte.getArtigoCortina().getProduto().getDescricao() + ": "
					+ suporte.getArtigoCortina().getProduto().getUnidadeProduto().getLabel());
		}

		suporte.setQtde(CurrencyUtils.getBigDecimalCurrency(qtdeUnidades.doubleValue()));
	}

	/**
	 * Verifica quais itens de mão-de-obra são necessários e os adiciona.
	 * 
	 * @param cortina
	 * @throws ViewException
	 */
	public List<CortinaItem> adicionarItensMaoDeObra(Cortina cortina) throws ViewException {
		// Percorre todos os itens para ver quais mão-de-obra é necessário adicionar
		boolean encontrouInstalacao = false;
		boolean encontrouMOTecidos = false;
		boolean temIlhos = false;
		boolean encontrouMOIlhos = false;

		boolean temDrapeadoOuBando = false;
		boolean encontrouMODetalhes = false;
		for (CortinaItem ci : cortina.getItens()) {
			if (ci.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.ILHOS)) {
				temIlhos = true;
			} else if (ci.getArtigoCortina().getTipoArtigoCortina()
					.equals(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_TECIDO)) {
				encontrouMOTecidos = true;
			} else if (ci.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.MAO_DE_OBRA_INSTALACAO)) {
				encontrouInstalacao = true;
			} else if (ci.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.MAO_DE_OBRA_ILHOS)) {
				encontrouMOIlhos = true;
			} else if (ci.getArtigoCortina().getTipoArtigoCortina()
					.equals(TipoArtigoCortina.TECIDO) && (ci.getDrapeado() == true || ci.getBando() == true)) {
				temDrapeadoOuBando = true;
			} else if (ci.getArtigoCortina().getTipoArtigoCortina()
					.equals(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_DETALHES)) {
				encontrouMODetalhes = true;
			}
		}

		List<CortinaItem> itensAdicionados = new ArrayList<CortinaItem>();

		if (cortina.isComInstalacao() && !encontrouInstalacao) {
			CortinaItem ci = new CortinaItem();
			ci.setArtigoCortina(getArtigoCortinaFinder().findBy(TipoArtigoCortina.MAO_DE_OBRA_INSTALACAO));
			ci.setCamada(999);
			ci.setCortina(cortina);
			cortina.getItens().add(ci);
			itensAdicionados.add(ci);
		}

		if (temIlhos && !encontrouMOIlhos) {
			CortinaItem ci = new CortinaItem();
			ci.setArtigoCortina(getArtigoCortinaFinder().findBy(TipoArtigoCortina.MAO_DE_OBRA_ILHOS));
			ci.setCamada(999);
			ci.setCortina(cortina);
			cortina.getItens().add(ci);
			itensAdicionados.add(ci);
		}

		if (!encontrouMOTecidos) {
			CortinaItem ci = new CortinaItem();
			ci.setArtigoCortina(getArtigoCortinaFinder().findBy(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_TECIDO));
			ci.setCamada(999);
			ci.setCortina(cortina);
			cortina.getItens().add(ci);
			itensAdicionados.add(ci);
		}

		if (temDrapeadoOuBando && !encontrouMODetalhes) {
			CortinaItem ci = new CortinaItem();
			ci.setArtigoCortina(getArtigoCortinaFinder().findBy(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_DETALHES));
			ci.setCamada(999);
			ci.setCortina(cortina);
			cortina.getItens().add(ci);
			itensAdicionados.add(ci);
		}

		return itensAdicionados;

	}

	/**
	 * Monta o List com os itens para o comparativo de larguras.
	 * 
	 * @param itemTecido
	 * @throws ViewException
	 */
	public List<CortinaLargurasCompar> buildCortinaLargurasCompar(CortinaItem itemTecido) throws ViewException {
		List<CortinaLargurasCompar> r = new ArrayList<>();

		if (!itemTecido.getOrientacao().equals(OrientacaoTecido.VERTICAL)) {
			throw new ViewException("Comparativo de larguras só deve ser montado em tecidos com orientação VERTICAL.");
		}

		Integer largurasInicial = 2;

		// Verifica se dá pra começar com 1 largura.
		if (itemTecido.getArtigoCortina().getTecido().getLargura().doubleValue() > itemTecido.getCortina().getLargura()
				.doubleValue()) {
			largurasInicial = 1;
		}

		BigDecimal qtdeTecidoIdeal = itemTecido.getFator().multiply(itemTecido.getCortina().getLargura());

		BigDecimal fatorReal = BigDecimal.ZERO;

		while (fatorReal.doubleValue() < 5) {
			BigDecimal qtdeTecidoLarguras = new BigDecimal(largurasInicial)
					.multiply(itemTecido.getArtigoCortina().getTecido().getLargura());
			BigDecimal diferenca = qtdeTecidoLarguras.subtract(qtdeTecidoIdeal);
			BigDecimal eficiencia = BigDecimal.ONE
					.subtract(diferenca.abs().divide(qtdeTecidoIdeal, 3, RoundingMode.HALF_DOWN))
					.multiply(new BigDecimal("100.0"));
			fatorReal = qtdeTecidoLarguras.divide(itemTecido.getCortina().getLargura(), RoundingMode.HALF_DOWN);

			CortinaLargurasCompar clc = new CortinaLargurasCompar();
			clc.setDiferenca(diferenca);
			clc.setEficiencia(eficiencia);
			clc.setFatorReal(fatorReal);
			clc.setLarguras(largurasInicial);
			clc.setQtdeTecido(qtdeTecidoLarguras);
			clc.setUid(clc.hashCode());

			largurasInicial++;

			r.add(clc);
		}

		CortinaLargurasCompar maiorDiferencaNeg = null;
		CortinaLargurasCompar maiorDiferencaPos = null;

		/**
		 * Pego somente os mais eficientes, tanto com diferença positiva (com tecido a mais do que o do fator 'bruto' selecionado), quando
		 * com diferença negativa (com tecido a menos do que o do fator 'bruto' selecionado)
		 */
		for (CortinaLargurasCompar clc : r) {
			if (clc.getDiferenca().doubleValue() < 0.0) {
				if (maiorDiferencaNeg == null
						|| clc.getDiferenca().doubleValue() > maiorDiferencaNeg.getDiferenca().doubleValue()) {
					maiorDiferencaNeg = clc;
				}
			} else {
				if (maiorDiferencaPos == null
						|| clc.getDiferenca().doubleValue() < maiorDiferencaPos.getDiferenca().doubleValue()) {
					maiorDiferencaPos = clc;
				}
			}
		}

		r.clear();
		if (maiorDiferencaPos != null) {
			r.add(maiorDiferencaPos);
		}
		if (maiorDiferencaNeg != null) {
			r.add(maiorDiferencaNeg);
		}

		return r;

	}

	public ArtigoCortinaFinder getArtigoCortinaFinder() {
		return artigoCortinaFinder;
	}

	public void setArtigoCortinaFinder(ArtigoCortinaFinder artigoCortinaFinder) {
		this.artigoCortinaFinder = artigoCortinaFinder;
	}

}
