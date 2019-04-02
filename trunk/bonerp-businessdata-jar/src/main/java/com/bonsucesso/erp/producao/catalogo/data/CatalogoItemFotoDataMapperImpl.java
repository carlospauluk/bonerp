package com.bonsucesso.erp.producao.catalogo.data;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.producao.catalogo.model.CatalogoItem;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemArtigo;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemFoto;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.ArquivoUploadVO;


/**
 * Implementação de DataMapper para a entidade CatalogoFoto.
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("catalogoItemFotoDataMapper")
public class CatalogoItemFotoDataMapperImpl extends DataMapperImpl<CatalogoItemFoto>
		implements CatalogoItemFotoDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8057071691935323119L;

	@Autowired
	private ConfigFinder configFinder;

	public final static String CHAVE_DIR_ARQUIVOS = "config.produto.catalogo.arquivosdir";

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	@Override
	public void uploadArquivos(CatalogoItem catalogoItem, List<ArquivoUploadVO> arquivos)
			throws ViewException {		
		FileOutputStream fos = null;

		String dir = getConfigFinder().findConfigByChave(CatalogoItemFotoDataMapperImpl.CHAVE_DIR_ARQUIVOS)
				.getValor();
		
		logger.info("Iniciando o upload de " + arquivos.size() + " arquivo(s)");
		int j = 1;
		for (ArquivoUploadVO arquivo : arquivos) {
			
			logger.info("Arquivo " + j++);

			InputStream i = arquivo.getInputStream();
			try {

				CatalogoItemFoto foto = new CatalogoItemFoto();
				foto.setCatalogoItem(catalogoItem);
				String nomeArquivo = catalogoItem.getId() + "_" + arquivo.getUploadedFile().getFileName();
				
				String extensao = FilenameUtils.getExtension(nomeArquivo);
				extensao = extensao.toUpperCase();
				String nomeArquivoSemExtensao = FilenameUtils.removeExtension(nomeArquivo);
				nomeArquivoSemExtensao = nomeArquivoSemExtensao.replaceAll("\\W+", "_");
				nomeArquivo = nomeArquivoSemExtensao + "." + extensao;
				foto.setNomeArquivo(nomeArquivo);
				String nomeArquivoComPath = dir + nomeArquivo; 
				
				
				File file = new File(nomeArquivoComPath);

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

				while ((read = i.read(bytes)) != -1) {
					fos.write(bytes, 0, read);
				}

				getThiz().save(foto);
				
				logger.info("SUCESSO!");

			} catch (FileNotFoundException e) {
				logger.error(e);
				throw new ViewException("Arquivo não encontrado", e);
			} catch (IOException e) {
				logger.error(e);
				throw new ViewException("Erro de I/O", e);
			}

		}

	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	@Override
	public void deleleFileAndDelete(CatalogoItemFoto catalogoFoto) throws ViewException {
		try {
			String dir = getConfigFinder().findConfigByChave(CatalogoItemFotoDataMapperImpl.CHAVE_DIR_ARQUIVOS)
					.getValor();

			File file = new File(dir + catalogoFoto.getNomeArquivo());

			getThiz().delete(catalogoFoto);

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

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	@Override
	public CatalogoItemFoto saveArtigosFoto(CatalogoItemFoto foto, List<CatalogoItemArtigo> artigos) throws ViewException {
		try {
			foto.setArtigos(null);
			foto = save(foto);
			foto.setArtigos(new ArrayList<CatalogoItemArtigo>());
			foto.getArtigos().addAll(artigos);
			foto = save(foto);
			return foto;
		} catch (Exception e) {
			throw new ViewException("Erro ao salvar os artigos da foto", e);
		}
	}

}
