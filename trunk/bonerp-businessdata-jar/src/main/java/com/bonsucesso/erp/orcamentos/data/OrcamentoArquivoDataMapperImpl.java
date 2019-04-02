package com.bonsucesso.erp.orcamentos.data;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.orcamentos.model.OrcamentoArquivo;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * Implementação Data Mapper para a entidade OrcamentoArquivo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("orcamentoArquivoDataMapper")
public class OrcamentoArquivoDataMapperImpl extends DataMapperImpl<OrcamentoArquivo> implements
		OrcamentoArquivoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -2979968564493398019L;

	protected static Logger logger = Logger.getLogger(OrcamentoArquivoDataMapperImpl.class);

	@Autowired
	private ConfigFinder configFinder;

	private final static String CHAVE_DIR_ARQUIVOS = "config.orcamento.arquivosdir";

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public void upload(OrcamentoArquivo orcamentoArquivo, InputStream arquivo) throws ViewException {
		FileOutputStream fos = null;
		try {

			orcamentoArquivo = save(orcamentoArquivo);

			String dir = getConfigFinder().findConfigByChave(OrcamentoArquivoDataMapperImpl.CHAVE_DIR_ARQUIVOS)
					.getValor();

			File file = new File(dir + orcamentoArquivo.getNomeArquivo());

			if (file.exists()) {
				if (!file.delete()) {
					logger
							.error("Erro ao salvar o arquivo. Arquivo já existente (erro ao substituir).");
					throw new IllegalStateException("Erro ao salvar o arquivo. Arquivo já existente (erro ao substituir)");
				}
			}

			fos = new FileOutputStream(file);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = arquivo.read(bytes)) != -1) {
				fos.write(bytes, 0, read);
			}

		} catch (DataException e) {
			logger.error("Erro ao salvar o arquivo", e);
			throw new IllegalStateException("Erro ao salvar o arquivo", e);
		} catch (FileNotFoundException e) {
			logger.error("Erro ao salvar o arquivo", e);
			throw new IllegalStateException("Erro ao salvar o arquivo", e);
		} catch (IOException e) {
			logger.error("Erro ao salvar o arquivo", e);
			throw new IllegalStateException("Erro ao salvar o arquivo", e);
		} finally {
			if (arquivo != null) {
				try {
					arquivo.close();
				} catch (IOException e) {
					logger.error("Erro ao salvar o arquivo", e);
					throw new IllegalStateException("Erro ao salvar o arquivo", e);
				}
			}
			if (fos != null) {
				try {
					// outputStream.flush();
					fos.close();
				} catch (IOException e) {
					logger.error("Erro ao salvar o arquivo", e);
					throw new IllegalStateException("Erro ao salvar o arquivo", e);
				}

			}
		}

	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleleAndDelete(OrcamentoArquivo orcamentoArquivo) throws ViewException {
		try {
			File file = getFile(orcamentoArquivo);

			delete(orcamentoArquivo);

			if (file.exists()) {
				if (!file.delete()) {
					logger.error("Erro ao deletar o arquivo.");
					throw new IllegalStateException("Erro ao deletar o arquivo.");
				}
			} else {
				logger.info("Arquivo já não existe");
			}
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao deletar registro ou arquivo", e);
		}

	}

	@Override
	public File getFile(OrcamentoArquivo orcamentoArquivo) {
		try {
			String dir = getConfigFinder().findConfigByChave(OrcamentoArquivoDataMapperImpl.CHAVE_DIR_ARQUIVOS)
					.getValor();

			File file = new File(dir + orcamentoArquivo.getNomeArquivo());

			return file;
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
			return null;
		}

	}

}
