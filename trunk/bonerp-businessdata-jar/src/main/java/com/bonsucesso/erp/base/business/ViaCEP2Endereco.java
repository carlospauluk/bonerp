package com.bonsucesso.erp.base.business;



import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bonsucesso.erp.base.model.Endereco;
import com.ocabit.base.model.ViaCEP;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * Componente que executa uma consulta ao serviço ViaCep e converte o resultado para uma entidade Endereco;
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("viaCEP2Endereco")
public class ViaCEP2Endereco implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4896463687535899957L;

	protected static Logger logger = Logger.getLogger(ViaCEP2Endereco.class);

	public Endereco convert(String cep, Endereco endereco) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = String.format("http://viacep.com.br/ws/%s/json", cep);
			ViaCEP viaCEP = restTemplate.getForObject(url, ViaCEP.class);
			if (viaCEP != null) {
				viaCEP2Endereco(viaCEP, endereco);
			}
		} catch (RestClientException e) {
			JSFUtils.addErrorMsg("Erro ao consultar CEP (rest).");
			logger.error(e);
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao consultar CEP.");
			logger.error(e);
		}

		return endereco;
	}

	public Endereco convert(String cep) {
		Endereco endereco = new Endereco();
		return convert(cep, endereco);
	}

	public Endereco convert(Endereco endereco) {
		return convert(endereco.getCep(), endereco);
	}

	private void viaCEP2Endereco(ViaCEP viaCEP, Endereco endereco) {
		endereco.setLogradouro(viaCEP.getLogradouro());
		endereco.setBairro(viaCEP.getBairro());
		endereco.setCep(viaCEP.getCep());
		endereco.setCidade(viaCEP.getLocalidade());
		endereco.setEstado(viaCEP.getUf());
	}

	public static void main(String... args) throws ViewException {
		System.out.println(new ViaCEP2Endereco().convert("12345-000"));
	}

}
