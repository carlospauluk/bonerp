package com.bonsucesso.erp.financeiro.business;



import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.faces.security.FaceletsAuthorizeTagUtils;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.Status;
import com.ocabit.base.view.exception.ViewException;


/**
 * Classe de negócios a respeito da entidade Movimentacao e suas correlações.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("movimentacaoSecurityCheck")
public class MovimentacaoSecurityCheck implements Serializable {

	@Autowired
	private MovimentacaoBusiness movimentacaoBusiness;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1076190581982709712L;

	/**
	 * Método que trata o campo Movimentacao.descricaoMontada de acordo com as permissões do usuário logado.
	 * 
	 * Passo o User como parâmetro pela questão do cache.
	 * 
	 * @param m
	 * @throws ViewException 
	 */
	public String rebuildDescricaoMontadaTratProp(Movimentacao m) throws ViewException {
		try {
			if (m == null || m.getDescricao() == null || m.getDescricaoMontada() == null) {
				throw new IllegalStateException("m - null value(s)");
			}

			String descricaoMontada = m.getDescricaoMontada();

			if (m.getCategoria() != null && m.getCategoria().getRolesAcess() != null
					&& !"".equals(m.getCategoria().getRolesAcess().trim())) {
				// Se o usuário logado não tem permissão para ver a descrição da movimentação
				if (!FaceletsAuthorizeTagUtils.areAnyGranted(m.getCategoria().getRolesAcess().trim())) {
					// lógica: remove tudo o que não for letra e espaço, e pega somente as iniciais.
					String descricao = m.getDescricao().replaceAll("[^A-Z ]", "");
					String r = "";
					for (String s : descricao.split(" ")) {
						if (s.length() > 1)
							r += s.charAt(0);
					}
					// adiciona o centro de custo
					r += " - CC" + m.getCentroCusto().getCodigo();

					// troca a parte da 'descricao' na 'descricaoMontada'
					String sufixo = m.getDescricaoMontada().replace(m.getDescricao(), "");
					descricaoMontada = r + sufixo;
				}
			}

			return descricaoMontada;
		} catch (Exception e) {
			throw new ViewException("Erro ao gerar descrição montada");
		}
	}

	/**
	 * Verifica se o usuário logado pode deletar a movimentação em questão.
	 * 
	 * @param m
	 * @return
	 */
	public boolean podeDeletar(Movimentacao m) {
		return true;
	}

	/**
	 * Verifica se o usuário logado pode editar a movimentação em questão.
	 * 
	 * @param m
	 * @return
	 */
	public boolean podeEditar(Movimentacao m) {
		return true;
	}

	/**
	 * Verifica se o usuário logado pode editar a movimentação em questão.
	 * 
	 * @param m
	 * @return
	 * @throws ViewException 
	 */
	public boolean podePagar(Movimentacao m) throws ViewException {
		try {
			return (m.getStatus().equals(Status.ABERTA) || m.getStatus().equals(Status.A_COMPENSAR))
					&& FaceletsAuthorizeTagUtils.areAnyGranted("ROLE_FINANCEIRO_PAGTOS");
		} catch (Exception e) {
			throw new ViewException("Erro ao verificar permissão 'podePagar'");
		}
	}

	public MovimentacaoBusiness getMovimentacaoBusiness() {
		return movimentacaoBusiness;
	}

	public void setMovimentacaoBusiness(MovimentacaoBusiness movimentacaoBusiness) {
		this.movimentacaoBusiness = movimentacaoBusiness;
	}

}
