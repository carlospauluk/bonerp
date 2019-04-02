package com.bonsucesso.erp.vendas.view;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.vendas.spartacus.business.ISpartacusHandler;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para gerar arquivos ZIP com as notas do mês.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("downloadZipFiscalView")
@Scope("view")
public final class DownloadZipFiscalView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5065615671006566185L;

	protected static Logger logger = Logger.getLogger(DownloadZipFiscalView.class);

	@Autowired
	private ISpartacusHandler spartacusHandler;

	private Date mesAno = new Date();
	
	private StreamedContent file;

	static final int BUFFER = 2048;

	@PostConstruct
	public void init() {
		// processar();
	}

	public void processar() {

		try {
			BufferedInputStream origin = null;
			ByteArrayOutputStream dest = new ByteArrayOutputStream();
			CheckedOutputStream checksum = new CheckedOutputStream(dest, new Adler32());
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(checksum));
			//out.setMethod(ZipOutputStream.DEFLATED);
			byte data[] = new byte[BUFFER];
			List<Map<String, Object>> l = getSpartacusHandler().getXMLs(getMesAno());

			Random random = new Random(1000);
			for (Map<String, Object> r : l) {
				if (!r.containsKey("a03_id") || r.get("a03_id") == null) continue;
				// Integer statusNfe = (Integer) r.get("status_nfe");
				Integer statusCanc = (Integer) r.get("status_canc");
				// Adicionei um random porque não estava aceitando os nomes duplicados (nos casos de refaturamento)
				String nomeArquivo = r.get("a03_id") + (statusCanc == 101 ? "-CANCELADA" : "") + "-r" + random.nextInt() + ".xml";
				String conteudo = (String) r.get("xml_nfe");
				if (conteudo == null) continue;
				logger.debug("Adding: " + nomeArquivo);
				InputStream is = new ByteArrayInputStream(conteudo.getBytes());
				origin = new BufferedInputStream(is, BUFFER);
				ZipEntry entry = new ZipEntry(nomeArquivo);				
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
			logger.debug("checksum: " + checksum.getChecksum().getValue());
			
			InputStream stream = new ByteArrayInputStream(dest.toByteArray());
			file = new DefaultStreamedContent(stream, "application/zip", "nfes_" + new SimpleDateFormat("yyyy-MM").format(getMesAno()) + ".zip");
			logger.debug("Done");


		} catch (Exception e) {
			logger.error("Erro ao gerar arquivos XML", e);
			JSFUtils.addErrorMsg("Erro ao gerar arquivos XML");
		}
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public ISpartacusHandler getSpartacusHandler() {
		return spartacusHandler;
	}

	public void setSpartacusHandler(ISpartacusHandler spartacusHandler) {
		this.spartacusHandler = spartacusHandler;
	}

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

}
