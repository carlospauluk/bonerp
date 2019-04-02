package com.bonsucesso.erp.fiscal.view;



import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.fiscal.business.ParseNFe;
import com.bonsucesso.erp.fiscal.data.NotaFiscalDataMapper;
import com.bonsucesso.erp.fiscal.data.NotaFiscalFinder;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.NotaFiscalItem;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * View para a entidade NotaFiscal.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("notaFiscalEntradaFormView")
@Scope("view")
public class NotaFiscalEntradaFormView extends AbstractEntityFormView<NotaFiscal, NotaFiscalDataMapper> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5429297557152603688L;

	protected static Logger logger = Logger.getLogger(NotaFiscalEntradaFormView.class);

	private UploadedFile arquivoOrcamento;

	@Autowired
	private ParseNFe parseNFe;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private NotaFiscalFinder finder;

	private boolean cadastroManual = false;

	private NotaFiscalItem item;

	@Override
	public void afterSetE(NotaFiscal nfe) {
		if (nfe != null) {
			if (nfe.getPessoaEmitente() == null) {
				nfe.setPessoaEmitente(new Pessoa());
			}
			// se o xmlNota estiver no NotaFiscal, então não pode ser preenchimento manual
			if ((nfe.getXmlNota() != null && !"".equals(nfe.getXmlNota().trim())) || nfe.getId() == null) {
				setCadastroManual(false);
			} else {
				setCadastroManual(true);
			}
		}
		nfe.setEntrada(true);

	}

	public void updateFornecedorSelec(Fornecedor fornecedor) {
		try {
			Fornecedor f = getFornecedorFinder().refresh(fornecedor);
			f.getEnderecos().size(); // touch
			getE().setPessoaEmitente(f.getPessoa());
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao selecionar o fornecedor.");
		}
	}

	public void uploadArquivoXML(FileUploadEvent event) {
		try {
			setE(new NotaFiscal());
			UploadedFile arquivo = event.getFile();
			NotaFiscal notaFiscal = getParseNFe().parseXML(arquivo.getInputstream());
			NotaFiscal nfExistente = getFinder().findBy(notaFiscal.getNumero(), notaFiscal.getSerie(), notaFiscal.getPessoaEmitente());
			if (nfExistente != null) {
				JSFUtils.addInfoMsg("Nota Fiscal já existente.");
				setE(nfExistente);
				openDialogRedirectToId();
			} else {
				setE(notaFiscal);
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao ler o conteúdo do arquivo.");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao ler o arquivo.");
		}
	}

	public void updateDoc() {
		try {
			// Se o documento do setId for diferente do inputado (mudou o CPF/CNPJ)
			String doc = getE().getPessoaEmitente().getDocumento();
			if (doc != null && !"".equals(doc.trim())) {
				Fornecedor fornecedor = getFornecedorFinder().findByDoc(doc);
				if (fornecedor != null) { // não achou Fornecedor...
					getE().setPessoaEmitente(fornecedor.getPessoa());
					JSFUtils.addWarnMsg("Fornecedor já existente.");
				}
			} else {
				if (getE().getPessoaEmitente() != null && getE().getPessoaEmitente().getId() != null) {
					getE().setPessoaEmitente(new Pessoa());
				}
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void novoItem() {
		setItem(new NotaFiscalItem());
	}

	public void deleteItem(NotaFiscalItem item) {
		try {
			setItem(item);
			getE().getItens().remove(getItem());
			JSFUtils.addInfoMsg("Item removido.");
			JSFUtils.addInfoMsg("É necessário pressionar em 'Salvar' para efetivar a remoção.");
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao remover item.");
		}
	}

	public void saveItem() {
		try {
			if (!getE().getItens().contains(getItem())) {
				getE().getItens().add(getItem());
				getItem().setNotaFiscal(getE());
			}
			save();
			RequestContext.getCurrentInstance().addCallbackParam("saved", "true");
			RequestContext.getCurrentInstance().addCallbackParam("dlgId", "dlgItem");
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao salvar o item");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void updateValorTotalItem() {
		try {
			getItem().setValorTotal(CurrencyUtils
					.getBigDecimalCurrency(CurrencyUtils.multiplica(getItem().getValorUnit(), getItem().getQtde())));
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao calcular o valor total.");
			logger.error(e);
		}
	}

	public UploadedFile getArquivoOrcamento() {
		return arquivoOrcamento;
	}

	public void setArquivoOrcamento(UploadedFile arquivoOrcamento) {
		this.arquivoOrcamento = arquivoOrcamento;
	}

	public ParseNFe getParseNFe() {
		return parseNFe;
	}

	public void setParseNFe(ParseNFe parseNFe) {
		this.parseNFe = parseNFe;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	public boolean getCadastroManual() {
		return cadastroManual;
	}

	public void setCadastroManual(boolean cadastroManual) {
		this.cadastroManual = cadastroManual;
	}

	public NotaFiscalItem getItem() {
		return item;
	}

	public void setItem(NotaFiscalItem item) {
		this.item = item;
	}

	public NotaFiscalFinder getFinder() {
		return finder;
	}

	public void setFinder(NotaFiscalFinder finder) {
		this.finder = finder;
	}

}
