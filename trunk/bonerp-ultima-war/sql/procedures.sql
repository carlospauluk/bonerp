CREATE PROCEDURE `prod_planilha_corte`(
        IN `loteItensIds` VARCHAR(999),
        IN `tiposInsumosIds` VARCHAR(255)
    )
    NOT DETERMINISTIC
    READS SQL DATA
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN

    DECLARE p_lote_id BIGINT(20);
	DECLARE p_lote_codigo INT(11);
    DECLARE p_lote_dt_lote DATE;
    DECLARE p_lote_descricao VARCHAR(200);
    DECLARE p_loteitem_id BIGINT(20);
    DECLARE p_confeccao_id BIGINT(20);
    DECLARE p_confeccao_descricao VARCHAR(200);
    DECLARE p_confeccaoitem_id BIGINT(20);
    DECLARE p_tipoinsumo VARCHAR(100);
    DECLARE p_insumo VARCHAR(200);
    DECLARE p_preco_custo DOUBLE;
    
    DECLARE p_qtdelote_02 INT(11) DEFAULT 0;
	DECLARE p_qtdelote_04 INT(11) DEFAULT 0;
	DECLARE p_qtdelote_06 INT(11) DEFAULT 0;
	DECLARE p_qtdelote_08 INT(11) DEFAULT 0;
	DECLARE p_qtdelote_10 INT(11) DEFAULT 0;
	DECLARE p_qtdelote_12 INT(11) DEFAULT 0;
	DECLARE p_qtdelote_14 INT(11) DEFAULT 0;
	DECLARE p_qtdelote_16 INT(11) DEFAULT 0;
	DECLARE p_qtdelote_P INT(11) DEFAULT 0;
	DECLARE p_qtdelote_M INT(11) DEFAULT 0;
	DECLARE p_qtdelote_G INT(11) DEFAULT 0;
	DECLARE p_qtdelote_XG INT(11) DEFAULT 0;
	DECLARE p_qtdelote_SG INT(11) DEFAULT 0;
	DECLARE p_qtdelote_SS INT(11) DEFAULT 0;
    DECLARE p_totallote INT(11) DEFAULT 0;
    
    DECLARE p_qtdeitem_02 DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_04 DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_06 DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_08 DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_10 DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_12 DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_14 DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_16 DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_P DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_M DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_G DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_XG DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_SG DOUBLE DEFAULT 0;
    DECLARE p_qtdeitem_SS DOUBLE DEFAULT 0;
    DECLARE p_totalitem DOUBLE DEFAULT 0;
    
    DECLARE p_qtdetotal_02 DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_04 DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_06 DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_08 DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_10 DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_12 DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_14 DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_16 DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_P DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_M DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_G DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_XG DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_SG DOUBLE DEFAULT 0;
    DECLARE p_qtdetotal_SS DOUBLE DEFAULT 0;
    DECLARE p_totalinsumo DOUBLE DEFAULT 0;

	DECLARE no_more_rows BOOLEAN DEFAULT FALSE;
    DECLARE loop_cntr INT DEFAULT 0;
	DECLARE num_rows INT DEFAULT 0;
    
    

	DECLARE cur_itenslote CURSOR FOR
    	SELECT
          lote.id as lote_id, 
          lote.codigo as lote_codigo,
          lote.dt_lote,
          lote.descricao as lote_descricao,
          loteitem.id as loteitem_id,
          confeccao.id as confeccao_id,
          confeccao.descricao as confeccao_descricao,
          ci.id as confeccaoitem_id,          
          tipoinsumo.descricao as tipoinsumo,
          insumo.descricao as insumo,
          insumopreco.preco_custo,
          sum(liq.`qtde`) as totallote
          from 
              prod_lote_confeccao lote,
              prod_lote_confeccao_item loteitem,
              prod_lote_confeccao_item_qtde liq,
              prod_confeccao confeccao,
              prod_confeccao_item ci,
              prod_insumo insumo, 
              prod_tipo_insumo tipoinsumo,
              prod_insumo_preco insumopreco
          where 
              is_id_in_ids(loteItensIds,loteitem.id) AND
              is_id_in_ids(tiposInsumosIds,tipoinsumo.id) AND
              lote.id = loteitem.lote_confeccao_id AND
              loteitem.confeccao_id = confeccao.id AND
              liq.lote_confeccao_item_id = loteitem.id AND
              confeccao.id = ci.confeccao_id AND
              ci.insumo_id = insumo.id AND
              insumo.tipo_insumo_id = tipoinsumo.id AND
              insumo.id = insumopreco.insumo_id
          GROUP BY insumo.id, confeccao.id
          ORDER BY confeccao_descricao, tipoinsumo.descricao, insumo.descricao;
          
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_rows = TRUE;
    
    DROP TEMPORARY TABLE IF EXISTS _prod_planilha_corte;
    CREATE TEMPORARY TABLE IF NOT EXISTS _prod_planilha_corte (
      lote_id BIGINT(20),
      lote_codigo INT(11),
      lote_dt_lote DATE,
      lote_descricao VARCHAR(200),
      loteitem_id BIGINT(20),
      confeccao_id BIGINT(20),
      confeccao_descricao VARCHAR(200),
      confeccaoitem_id BIGINT(20),
      tipoinsumo VARCHAR(100),
      insumo VARCHAR(200),
      preco_custo DOUBLE,
      qtdeitem_02 DOUBLE,
      qtdeitem_04 DOUBLE,
      qtdeitem_06 DOUBLE,
      qtdeitem_08 DOUBLE,
      qtdeitem_10 DOUBLE,
      qtdeitem_12 DOUBLE,
      qtdeitem_14 DOUBLE,
      qtdeitem_16 DOUBLE,
      qtdeitem_P  DOUBLE,
      qtdeitem_M  DOUBLE,
      qtdeitem_G  DOUBLE,
      qtdeitem_XG DOUBLE,
      qtdeitem_SG DOUBLE,
      qtdeitem_SS INT(11),
      qtdelote_02 INT(11),
      qtdelote_04 INT(11),
      qtdelote_06 INT(11),
      qtdelote_08 INT(11),
      qtdelote_10 INT(11),
      qtdelote_12 INT(11),
      qtdelote_14 INT(11),
      qtdelote_16 INT(11),
      qtdelote_P  INT(11),
      qtdelote_M  INT(11),
      qtdelote_G  INT(11),
      qtdelote_XG INT(11),
      qtdelote_SG INT(11),
      qtdelote_SS INT(11),      
      qtdetotal_02 DOUBLE,
      qtdetotal_04 DOUBLE,
      qtdetotal_06 DOUBLE,
      qtdetotal_08 DOUBLE,
      qtdetotal_10 DOUBLE,
      qtdetotal_12 DOUBLE,
      qtdetotal_14 DOUBLE,
      qtdetotal_16 DOUBLE,
      qtdetotal_P DOUBLE,
      qtdetotal_M DOUBLE,
      qtdetotal_G DOUBLE,
      qtdetotal_XG DOUBLE,
      qtdetotal_SG DOUBLE,
      qtdetotal_SS DOUBLE,
      totalitem DOUBLE,
      totallote INT(11),
      totalinsumo DOUBLE      
      )DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ENGINE=MEMORY; 
    
    OPEN cur_itenslote;
    
    select FOUND_ROWS() into num_rows;
    
    -- select 'rows: ' + num_rows;

	-- LOOP PRINCIPAL
	the_loop: 
    LOOP 
      FETCH cur_itenslote INTO
          p_lote_id,
          p_lote_codigo,
          p_lote_dt_lote,
          p_lote_descricao,
          p_loteitem_id,
          p_confeccao_id,
          p_confeccao_descricao,
          p_confeccaoitem_id,
          p_tipoinsumo,
          p_insumo,
          p_preco_custo,
          p_totallote
          ;
          
        IF no_more_rows THEN
            CLOSE cur_itenslote;
            LEAVE the_loop;
        END IF;
      
      -- PROCURA AS QTDES PARA TAMANHO 02
      
      -- outro bloco para que os NOT FOUNDs das procedures abaixo não passem pro bloco do FETCH
      BLOCK2: begin
      
        DECLARE CONTINUE HANDLER FOR NOT FOUND BEGIN END;
        
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 3, '02', p_qtdelote_02, p_qtdetotal_02, p_qtdeitem_02);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 3, '04', p_qtdelote_04, p_qtdetotal_04, p_qtdeitem_04);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 3, '06', p_qtdelote_06, p_qtdetotal_06, p_qtdeitem_06);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 3, '08', p_qtdelote_08, p_qtdetotal_08, p_qtdeitem_08);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 3, '10', p_qtdelote_10, p_qtdetotal_10, p_qtdeitem_10);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 3, '12', p_qtdelote_12, p_qtdetotal_12, p_qtdeitem_12);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 3, '14', p_qtdelote_14, p_qtdetotal_14, p_qtdeitem_14);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 3, '16', p_qtdelote_16, p_qtdetotal_16, p_qtdeitem_16);
        
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 1, 'P', p_qtdelote_p, p_qtdetotal_p, p_qtdeitem_p);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 1, 'M', p_qtdelote_m, p_qtdetotal_m, p_qtdeitem_m);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 1, 'G', p_qtdelote_g, p_qtdetotal_g, p_qtdeitem_g);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 1, 'XG', p_qtdelote_xg, p_qtdetotal_xg, p_qtdeitem_xg);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 1, 'SG', p_qtdelote_sg, p_qtdetotal_sg, p_qtdeitem_sg);
        CALL `prod_planilha_corte_totais_grade`(p_confeccaoitem_id, p_loteitem_id, 1, 'SS', p_qtdelote_ss, p_qtdetotal_ss, p_qtdeitem_ss);
      
      end;
      
      BLOCK3: begin
      
        DECLARE CONTINUE HANDLER FOR NOT FOUND BEGIN END;
        
          SELECT
            sum(ciq.qtde) as totalitem, 
          	sum((lciq.qtde * ciq.qtde)) as qtde INTO p_totalitem, p_totalinsumo  
          FROM 
            prod_lote_confeccao_item_qtde lciq, 
            prod_confeccao_item_qtde ciq
          WHERE 
            lciq.grade_tamanho_id = ciq.grade_tamanho_id AND 
            lciq.lote_confeccao_item_id = p_loteitem_id AND 
            ciq.confeccao_item_id = p_confeccaoitem_id;
        
      
      end;
    
        
      INSERT INTO _prod_planilha_corte
      	( lote_id,
          lote_codigo,
          lote_dt_lote,
          lote_descricao,
          loteitem_id,
          confeccao_id,
          confeccao_descricao,
          confeccaoitem_id,
          tipoinsumo,
          insumo,
          preco_custo,
          qtdeitem_02,
          qtdeitem_04,
          qtdeitem_06,
          qtdeitem_08,
          qtdeitem_10,
          qtdeitem_12,
          qtdeitem_14,
          qtdeitem_16,
          qtdeitem_P ,
          qtdeitem_M ,
          qtdeitem_G ,
          qtdeitem_XG,
          qtdeitem_SG,
          qtdeitem_SS,
          qtdelote_02,
          qtdelote_04,
          qtdelote_06,
          qtdelote_08,
          qtdelote_10,
          qtdelote_12,
          qtdelote_14,
          qtdelote_16,
          qtdelote_P,
          qtdelote_M,
          qtdelote_G,
          qtdelote_XG,
          qtdelote_SG,
          qtdelote_SS,
          qtdetotal_02,
          qtdetotal_04,
          qtdetotal_06,
          qtdetotal_08,
          qtdetotal_10,
          qtdetotal_12,
          qtdetotal_14,
          qtdetotal_16,
          qtdetotal_P,
          qtdetotal_M,
          qtdetotal_G,
          qtdetotal_XG,
          qtdetotal_SG,
          qtdetotal_SS,
          totalitem,
          totallote,
          totalinsumo)
      	VALUES
        	(p_lote_id,
                p_lote_codigo,
                p_lote_dt_lote,
                p_lote_descricao,
                p_loteitem_id,
                p_confeccao_id,
                p_confeccao_descricao,
                p_confeccaoitem_id,
                p_tipoinsumo,
                p_insumo,
                p_preco_custo,
                p_qtdeitem_02,
                p_qtdeitem_04,
                p_qtdeitem_06,
                p_qtdeitem_08,
                p_qtdeitem_10,
                p_qtdeitem_12,
                p_qtdeitem_14,
                p_qtdeitem_16,
                p_qtdeitem_p,
                p_qtdeitem_m,
                p_qtdeitem_g,
                p_qtdeitem_xg,
                p_qtdeitem_sg,
                p_qtdeitem_ss,
                p_qtdelote_02,
                p_qtdelote_04,
                p_qtdelote_06,
                p_qtdelote_08,
                p_qtdelote_10,
                p_qtdelote_12,
                p_qtdelote_14,
                p_qtdelote_16,
                p_qtdelote_p,
                p_qtdelote_m,
                p_qtdelote_g,
                p_qtdelote_xg,
                p_qtdelote_sg,
                p_qtdelote_ss,
                p_qtdetotal_02,
                p_qtdetotal_04,
                p_qtdetotal_06,
                p_qtdetotal_08,
                p_qtdetotal_10,
                p_qtdetotal_12,
                p_qtdetotal_14,
                p_qtdetotal_16,
                p_qtdetotal_p,
                p_qtdetotal_m,
                p_qtdetotal_g,
                p_qtdetotal_xg,
                p_qtdetotal_sg,
                p_qtdetotal_ss,
                p_totalitem,
                p_totallote,
                p_totalinsumo
                );  
      

      -- count the number of times looped
      SET loop_cntr = loop_cntr + 1;

	END LOOP the_loop;
    
    SELECT * FROM _prod_planilha_corte ORDER BY p_confeccao_descricao, p_tipoinsumo, p_insumo;


END;














CREATE PROCEDURE `prod_planilha_corte_totais_grade`(
        IN `p_confeccaoitem_id` BIGINT(20),
        IN `p_loteitem_id` BIGINT(20),
        IN `gradecodigo` BIGINT(20),
        IN `tamanho` VARCHAR(100),
        OUT `qtdelote` INTEGER(11),
        OUT `qtdetotal` DOUBLE,
        OUT `qtdeitem` DOUBLE
    )
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN
	
      SELECT 
      	lciq.qtde,
        ciq.qtde as qtdeitem,
        (lciq.qtde * ciq.qtde) as qtde 
        INTO qtdelote, qtdeitem, qtdetotal 
      FROM 
      	prod_lote_confeccao_item_qtde lciq, 
        prod_confeccao_item_qtde ciq, 
        est_grade grade, 
        est_grade_tamanho gradetamanho 
      WHERE 
      	lciq.grade_tamanho_id = ciq.grade_tamanho_id AND 
        lciq.grade_tamanho_id = gradetamanho.id AND 
        gradetamanho.grade_id = grade.id AND 
        lciq.lote_confeccao_item_id = p_loteitem_id AND 
        ciq.confeccao_item_id = p_confeccaoitem_id AND 
        gradetamanho.tamanho = tamanho AND grade.codigo = gradecodigo;

END;


















CREATE PROCEDURE `split_ids`(
        IN `strIDs` VARCHAR(255)
    )
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN

  DECLARE strLen    INT DEFAULT 0;
  DECLARE subStrLen INT DEFAULT 0;
  DECLARE subs		VARCHAR(255);

  IF strIDs IS NULL THEN
    SET strIDs = '';
  END IF;
  
  DROP TEMPORARY TABLE IF EXISTS _ids;
  CREATE TEMPORARY TABLE IF NOT EXISTS _ids (
  	_id BIGINT(20)
  ) DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ENGINE=MEMORY;

  do_this:
    LOOP
      SET strLen = LENGTH(strIDs);
      
      -- descobre o tamanho do primeiro id encontrado
      
      SET subs = SUBSTRING_INDEX(strIDs, ',', 1);
      
      INSERT INTO _ids VALUES(CAST(subs AS UNSIGNED));
      
      SET subStrLen = LENGTH(SUBSTRING_INDEX(strIDs, ',', 1));
      SET strIDs = MID(strIDs, subStrLen+2, strLen);
      
      
      
      IF strIDs = NULL or trim(strIds) = '' THEN
        LEAVE do_this;
      END IF;
      
  END LOOP do_this;
  
  select * from _ids;

END;












CREATE FUNCTION `is_id_in_ids`(
        `strIDs` VARCHAR(255),
        `_id` BIGINT
    )
    RETURNS BIT(1)
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN

  DECLARE strLen    INT DEFAULT 0;
  DECLARE subStrLen INT DEFAULT 0;
  DECLARE subs		VARCHAR(255);

  IF strIDs IS NULL THEN
    SET strIDs = '';
  END IF;
  
  

  do_this:
    LOOP
      SET strLen = LENGTH(strIDs);
      SET subs = SUBSTRING_INDEX(strIDs, ',', 1);
      
      if ( CAST(subs AS UNSIGNED) = _id ) THEN
        -- founded
      	return(1);
      END IF;
      
      SET subStrLen = LENGTH(SUBSTRING_INDEX(strIDs, ',', 1));
      SET strIDs = MID(strIDs, subStrLen+2, strLen);
      
      
      
      IF strIDs = NULL or trim(strIds) = '' THEN
        LEAVE do_this;
      END IF;
      
  END LOOP do_this;
  
   -- not founded
  return(0);
  
END;