SELECT
    orc.id as id,
    orc.cabecalho,
    orc.desconto,
    orc.dt_preenchimento,
    orc.dt_validade,
    orc.obs,
    orc.exibir_totais,
    orc.preenchido_por,
    orc.status as status_orcamento,
    orc_grupo.ordem as grupo_ordem,
    orc_grupo.titulo,
    orc_item.descricao as item_descricao,
    orc_item.ordem,
    orc_item.valor_unit,
    orc_item.obs as item_obs,
    cortina.id as cortina_id,
    cortina.altura,
    cortina.largura,
    cortina.altura_janela,
    cortina.largura_janela,
    cortina.com_instalacao,
    cortina.qtde_camadas,
    cortina_item.altura_barra,
    cortina_item.camada,
    cortina_item.`tecido_fator`,
    cortina_item.obs as cortinaItemObs,
    cortina_item.preco_prazo,
    cortina_item.preco_promo,
    cortina_item.preco_vista,
    cortina_item.qtde,
    artigo_cortina.tipo_artigo_cortina,
    tecido.largura tecido_largura,
    produto.reduzido as produto_reduzido,
    produto.descricao as produto_descricao,
    unidade.`label` as unidade,
    cortina_item.qtde * cortina_item.preco_prazo AS total_item,
    (
    SELECT round(truncate(( SUM(i_.qtde * i_.valor_unit ) ), 3) + 0.0001, 2)
    FROM
        orc_orcamento o_,
        orc_orcamento_grupo_item g_,
        orc_orcamento_item i_
    WHERE
        o_.id = orc.id AND
        i_.orcamento_grupo_id = g_.id AND g_.orcamento_id = o_.id
        ) as sub_total,
    (
    SELECT round(truncate(( SUM(i_.qtde * i_.valor_unit * ((100-o_.desconto)/100) ) ), 3) + 0.0001, 2)
    FROM
        orc_orcamento o_,
        orc_orcamento_grupo_item g_,
        orc_orcamento_item i_
    WHERE
        o_.id = orc.id AND
        i_.orcamento_grupo_id = g_.id AND g_.orcamento_id = o_.id
        ) as valor_total
FROM
    orc_orcamento orc
    JOIN orc_orcamento_grupo_item orc_grupo ON orc_grupo.orcamento_id = orc.id
    JOIN orc_orcamento_item orc_item ON orc_item.`orcamento_grupo_id` = orc_grupo.id
    JOIN crtn_cortina cortina ON cortina.`orcamento_item_id` = orc_item.id
    JOIN crtn_cortina_item cortina_item ON cortina_item.`cortina_id` = cortina.id
    JOIN crtn_artigo_cortina artigo_cortina ON cortina_item.`artigoCortina_id` = artigo_cortina.id
    LEFT JOIN crtn_tecido tecido ON artigo_cortina.`tecido_id` = tecido.id
    JOIN est_produto produto ON artigo_cortina.`produto_id` = produto.id
    JOIN `est_unidade_produto` unidade ON produto.`unidade_produto_id` = `unidade`.id

WHERE

    orc.id = 16
ORDER BY
    orc_grupo.`ordem`,
    orc_item.`ordem`,
    cortina_item.`camada`,
    produto.descricao
	
	
	
	
	
	
	
	
	
	
	
	
	
	
-- UNION PARA ESCONDER MÃO-DE-OBRA

SELECT * FROM ((SELECT
    orc.id as id,
    orc.cabecalho,
    orc.desconto,
    orc.dt_preenchimento,
    orc.dt_validade,
    orc.obs,
    orc.exibir_totais,
    orc.preenchido_por,
    orc.status as status_orcamento,
    orc_grupo.ordem as grupo_ordem,
    orc_grupo.titulo,
    orc_item.descricao as item_descricao,
    orc_item.ordem as item_ordem,
    orc_item.valor_unit,
    orc_item.obs as item_obs,
    cortina.id as cortina_id,
    cortina.altura,
    cortina.largura,
    cortina.altura_janela,
    cortina.largura_janela,
    cortina.com_instalacao,
    cortina.qtde_camadas,
    cortina_item.altura_barra,
    cortina_item.camada,
    cortina_item.`tecido_fator`,
    cortina_item.obs as cortinaItemObs,
    cortina_item.preco_prazo,
    cortina_item.preco_promo,
    cortina_item.preco_vista,
    cortina_item.qtde,
    artigo_cortina.tipo_artigo_cortina,
    tecido.largura tecido_largura,
    produto.reduzido as produto_reduzido,
    produto.descricao as produto_descricao,
    unidade.`label` as unidade,
    cortina_item.qtde * cortina_item.preco_prazo AS total_item,
    (
    SELECT round(truncate(( SUM(i_.qtde * i_.valor_unit ) ), 3) + 0.0001, 2)
    FROM
        orc_orcamento o_,
        orc_orcamento_grupo_item g_,
        orc_orcamento_item i_
    WHERE
        o_.id = orc.id AND
        i_.orcamento_grupo_id = g_.id AND g_.orcamento_id = o_.id
        ) as sub_total,
    (
    SELECT round(truncate(( SUM(i_.qtde * i_.valor_unit * ((100-o_.desconto)/100) ) ), 3) + 0.0001, 2)
    FROM
        orc_orcamento o_,
        orc_orcamento_grupo_item g_,
        orc_orcamento_item i_
    WHERE
        o_.id = orc.id AND
        i_.orcamento_grupo_id = g_.id AND g_.orcamento_id = o_.id
        ) as valor_total
FROM
    orc_orcamento orc
    JOIN orc_orcamento_grupo_item orc_grupo ON orc_grupo.orcamento_id = orc.id
    JOIN orc_orcamento_item orc_item ON orc_item.`orcamento_grupo_id` = orc_grupo.id
    JOIN crtn_cortina cortina ON cortina.`orcamento_item_id` = orc_item.id
    JOIN crtn_cortina_item cortina_item ON cortina_item.`cortina_id` = cortina.id
    JOIN crtn_artigo_cortina artigo_cortina ON cortina_item.`artigoCortina_id` = artigo_cortina.id
    LEFT JOIN crtn_tecido tecido ON artigo_cortina.`tecido_id` = tecido.id
    JOIN est_produto produto ON artigo_cortina.`produto_id` = produto.id
    JOIN `est_unidade_produto` unidade ON produto.`unidade_produto_id` = `unidade`.id

WHERE
    orc.id = 16
    AND artigo_cortina.`tipo_artigo_cortina` NOT IN ('MAO_DE_OBRA_COSTUREIRA_DETALHES','MAO_DE_OBRA_COSTUREIRA_TECIDO','MAO_DE_OBRA_ILHOS')

ORDER BY
    orc_grupo.`ordem`,
    orc_item.`ordem`,
    cortina_item.`camada`,
    produto.descricao)
UNION ALL
(SELECT
    orc.id as id,
    orc.cabecalho,
    orc.desconto,
    orc.dt_preenchimento,
    orc.dt_validade,
    orc.obs,
    orc.exibir_totais,
    orc.preenchido_por,
    orc.status as status_orcamento,
    orc_grupo.ordem as grupo_ordem,
    orc_grupo.titulo,
    orc_item.descricao as item_descricao,
    orc_item.ordem as item_ordem,
    orc_item.valor_unit,
    orc_item.obs as item_obs,
    cortina.id as cortina_id,
    cortina.altura,
    cortina.largura,
    cortina.altura_janela,
    cortina.largura_janela,
    cortina.com_instalacao,
    cortina.qtde_camadas,
    cortina_item.altura_barra,
    999 as camada, -- cortina_item.camada,
    cortina_item.`tecido_fator`,
    null as cortinaItemObs, -- cortina_item.obs as cortinaItemObs,
    SUM(cortina_item.qtde * cortina_item.preco_prazo) as preco_prazo, -- cortina_item.preco_prazo,
    cortina_item.preco_promo,
    cortina_item.preco_vista,
    1.00 as qtde, -- cortina_item.qtde,
    artigo_cortina.tipo_artigo_cortina,
    tecido.largura tecido_largura,
    null as produto_reduzido, -- produto.reduzido as produto_reduzido,
    'MÃO-DE-OBRA CONFECÇÃO' as produto_descricao, -- produto.descricao as produto_descricao,
    'UN' as unidade, -- unidade.`label` as unidade,
    SUM(cortina_item.qtde * cortina_item.preco_prazo) AS total_item,
    (
    SELECT round(truncate(( SUM(i_.qtde * i_.valor_unit ) ), 3) + 0.0001, 2)
    FROM
        orc_orcamento o_,
        orc_orcamento_grupo_item g_,
        orc_orcamento_item i_
    WHERE
        o_.id = orc.id AND
        i_.orcamento_grupo_id = g_.id AND g_.orcamento_id = o_.id
        ) as sub_total,
    (
    SELECT round(truncate(( SUM(i_.qtde * i_.valor_unit * ((100-o_.desconto)/100) ) ), 3) + 0.0001, 2)
    FROM
        orc_orcamento o_,
        orc_orcamento_grupo_item g_,
        orc_orcamento_item i_
    WHERE
        o_.id = orc.id AND
        i_.orcamento_grupo_id = g_.id AND g_.orcamento_id = o_.id
        ) as valor_total
FROM
    orc_orcamento orc
    JOIN orc_orcamento_grupo_item orc_grupo ON orc_grupo.orcamento_id = orc.id
    JOIN orc_orcamento_item orc_item ON orc_item.`orcamento_grupo_id` = orc_grupo.id
    JOIN crtn_cortina cortina ON cortina.`orcamento_item_id` = orc_item.id
    JOIN crtn_cortina_item cortina_item ON cortina_item.`cortina_id` = cortina.id
    JOIN crtn_artigo_cortina artigo_cortina ON cortina_item.`artigoCortina_id` = artigo_cortina.id
    LEFT JOIN crtn_tecido tecido ON artigo_cortina.`tecido_id` = tecido.id
    JOIN est_produto produto ON artigo_cortina.`produto_id` = produto.id
    JOIN `est_unidade_produto` unidade ON produto.`unidade_produto_id` = `unidade`.id

WHERE
    orc.id = 16
     AND    artigo_cortina.`tipo_artigo_cortina` IN ('MAO_DE_OBRA_COSTUREIRA_DETALHES','MAO_DE_OBRA_COSTUREIRA_TECIDO','MAO_DE_OBRA_ILHOS')
GROUP BY
	orc_item.id
)) orcamento_cortina
ORDER BY
   grupo_ordem,
item_ordem,
camada,
produto_descricao