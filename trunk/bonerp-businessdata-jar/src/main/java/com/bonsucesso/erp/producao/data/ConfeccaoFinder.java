package com.bonsucesso.erp.producao.data;



import java.util.List;

import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.ConfeccaoItem;
import com.bonsucesso.erp.producao.model.ConfeccaoPreco;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.bonsucesso.erp.producao.model.Insumo;
import com.bonsucesso.erp.producao.model.LoteConfeccao;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Confeccao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface ConfeccaoFinder extends BasicEntityFinder<Confeccao> {

	public ConfeccaoItem findItemBy(Instituicao instituicao, TipoArtigo tipoArtigo, Insumo insumo,
			GradeTamanho gradeTamanho)
					throws ViewException;

	public Confeccao findBy(Instituicao instituicao, TipoArtigo tipoArtigo, String descricao) throws ViewException;

	public List<Confeccao> findBy(Instituicao instituicao, TipoArtigo tipoArtigo) throws ViewException;

	public List<Confeccao> findBy(String str) throws ViewException;

	public List<ConfeccaoItem> findConfeccaoItemBy(Confeccao confeccao, TipoInsumo tipoInsumo) throws ViewException;

	public ConfeccaoItem findConfeccaoItemBy(Integer instituicaoCodigo, Integer tipoArtigoCodigo, Integer insumoCodigo)
			throws ViewException;

	public List<ConfeccaoPreco> findConfeccaoPrecosByStr(String str) throws ViewException;

	public List<Confeccao> findBy(Instituicao instituicao) throws ViewException;

	public List<Confeccao> findBy(Insumo insumo) throws ViewException;

	public List<Confeccao> findBy(Instituicao instituicao, TipoArtigo tipoArtigo, Boolean ocultas) throws ViewException;

	public List<LoteConfeccao> findLotesConfeccaoBy(Confeccao confeccao) throws ViewException;

}
