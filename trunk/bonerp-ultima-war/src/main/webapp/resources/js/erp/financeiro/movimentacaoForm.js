function triggerListener(prefixo, campo) {
	prefixo = "#frmMovimentacao_" + prefixo + "\\:";
	var jCampo = prefixo + campo + "_input";
	return ($(jCampo).val() == '__/__/____') || ($(jCampo).val() == '');
}

function atualizaDatas(tipoLancto, campo) {
	prefixo = "#frmMovimentacao_" + tipoLancto + "\\:";
	var iDtMovimentId = prefixo + "iDtMoviment_input";
	var iDtVenctoId = prefixo + "iDtVencto_input";
	var iDtVenctoEfetivaId = prefixo + "iDtVenctoEfetiva_input";
	var iDtPagtoId = prefixo + "iDtPagto_input";

	if (tipoLancto == 'REALIZADA') {
		if ($(iDtVenctoId).val() == '') {
			$(iDtVenctoId).val($(iDtMovimentId).val());
		}
		if ($(iDtVenctoEfetivaId).val() == '') {
			$(iDtVenctoEfetivaId).val($(iDtMovimentId).val());
		}
		if ($(iDtPagtoId).val() == '') {
			$(iDtPagtoId).val($(iDtMovimentId).val());
		}
		// frmMovimentacao_A_PAGAR_RECEBER:iPessoa_input
		// $(prefixo + "iPessoa_input").focus();
	} else {

		// se está entrando no campo iDtVencto
		if (campo == 'iDtVencto') {
			if ($(iDtVenctoId).val() == '') {
				if ($(iDtMovimentId).val() != '__/__/____') {
					$(iDtVenctoId).val($(iDtMovimentId).val());
					$(iDtVenctoId).select();
				}
			}
		}

		// se está entrando no campo iDtPagto
		if (campo == 'iDtPagto') {
			if ($(iDtPagtoId) && $(iDtPagtoId).val() == '') {
				if ($(iDtPagtoId).val() != '__/__/____') {
					$(iDtPagtoId).val($(iDtVenctoId).val());
					$(iDtPagtoId).select();
				}
			}
		}
		
	}
	
}

function somaValores(prefixo) {
	prefixo = "#frmMovimentacao_" + prefixo + "\\:";
	var iValorId = prefixo + "iValor";
	var iDescontosId = prefixo + "iDescontos";
	var iAcrescimosId = prefixo + "iAcrescimos";
	var iValorTotalId = prefixo + "iValorTotal";

	var valor = currencyStringToFloat($(iValorId).val());
	var descontos = currencyStringToFloat($(iDescontosId).val());
	var acrescimos = currencyStringToFloat($(iAcrescimosId).val());
	var total = (valor + descontos + acrescimos).toFixed(2);
	var totalStr = ("" + total).replace("\.", ",");
	$(iValorTotalId).val(totalStr);
}



function ajustarAlturas() {
	// obter o tamanho do spMovimentacao (será o tamanho máximo que ele terá)
	spMovimentacao_maxheight =  _$("spMovimentacao").height();
	var bodyheight = $(window).height();	
	_$("spMovimentacao").height(bodyheight-300);
	_$("spMovimentacao").css("max-height", spMovimentacao_maxheight);
}
			
