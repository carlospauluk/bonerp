package com.bonsucesso.erp.orcamentos.data;



import java.io.File;
import java.io.InputStream;

import com.bonsucesso.erp.orcamentos.model.OrcamentoArquivo;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade OrcamentoArquivo.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface OrcamentoArquivoDataMapper extends DataMapper<OrcamentoArquivo> {

	public void upload(OrcamentoArquivo orcamentoArquivo, InputStream arquivo) throws ViewException;

	public File getFile(OrcamentoArquivo orcamentoArquivo) throws ViewException;

	public void deleleAndDelete(OrcamentoArquivo orcamentoArquivo) throws ViewException;

}
