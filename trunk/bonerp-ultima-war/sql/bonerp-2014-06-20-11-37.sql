-- MySQL dump 10.13  Distrib 5.5.37, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: bonerp
-- ------------------------------------------------------
-- Server version	5.5.37-0ubuntu0.13.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `_child`
--

DROP TABLE IF EXISTS `_child`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_child` (
  `id` int(11) NOT NULL,
  `id_parent` int(11) NOT NULL,
  PRIMARY KEY (`id`,`id_parent`) USING BTREE,
  KEY `id_parent` (`id_parent`) USING BTREE,
  CONSTRAINT `_child_fk1` FOREIGN KEY (`id_parent`) REFERENCES `_parent` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `_child`
--
-- ORDER BY:  `id`,`id_parent`

LOCK TABLES `_child` WRITE;
/*!40000 ALTER TABLE `_child` DISABLE KEYS */;
/*!40000 ALTER TABLE `_child` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `_parent`
--

DROP TABLE IF EXISTS `_parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `_parent` (
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `_parent`
--
-- ORDER BY:  `id`

LOCK TABLES `_parent` WRITE;
/*!40000 ALTER TABLE `_parent` DISABLE KEYS */;
/*!40000 ALTER TABLE `_parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_cliente`
--

DROP TABLE IF EXISTS `bon_cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_cliente` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rg` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `codigo_cliente` int(11) NOT NULL,
  `data_emissao_rg` datetime DEFAULT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `documento` varchar(14) COLLATE utf8_swedish_ci NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  `email` varchar(50) COLLATE utf8_swedish_ci DEFAULT NULL,
  `estado_civil` varchar(13) COLLATE utf8_swedish_ci DEFAULT NULL,
  `estado_rg` varchar(2) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone1` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone2` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone3` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone4` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `inscricao_estadual` varchar(20) COLLATE utf8_swedish_ci DEFAULT NULL,
  `inscricao_municipal` varchar(40) COLLATE utf8_swedish_ci DEFAULT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `naturalidade` varchar(50) COLLATE utf8_swedish_ci DEFAULT NULL,
  `nome` varchar(200) COLLATE utf8_swedish_ci NOT NULL,
  `nome_fantasia` varchar(90) COLLATE utf8_swedish_ci DEFAULT NULL,
  `obs` longtext COLLATE utf8_swedish_ci,
  `orgao_emissor_rg` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `sexo` varchar(9) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  `website` varchar(120) COLLATE utf8_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `documento` (`documento`),
  UNIQUE KEY `UK_g0hw2ef9m0ee8r1qluegve5fi` (`documento`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_cliente`
--
-- ORDER BY:  `id`

LOCK TABLES `bon_cliente` WRITE;
/*!40000 ALTER TABLE `bon_cliente` DISABLE KEYS */;
INSERT INTO `bon_cliente` (`id`, `rg`, `codigo_cliente`, `data_emissao_rg`, `data_nascimento`, `documento`, `dt_cadastro`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `inserted`, `updated`, `naturalidade`, `nome`, `nome_fantasia`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `website`) VALUES (1,'',99900001,NULL,NULL,'05242105941','2014-03-28 17:18:04','',NULL,NULL,'','','','',NULL,NULL,'2014-03-28 17:18:12','2014-03-28 17:18:12','','CARLOS EDUARDO PAULUK',NULL,'','',NULL,0,NULL);
/*!40000 ALTER TABLE `bon_cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_cliente_bon_endereco`
--

DROP TABLE IF EXISTS `bon_cliente_bon_endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_cliente_bon_endereco` (
  `bon_cliente_id` bigint(20) NOT NULL,
  `enderecos_endereco_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_k789o835dbnne19xbk2upt9le` (`enderecos_endereco_id`),
  KEY `FK_mvk8g5be8f6t2rurl62dd8wem` (`bon_cliente_id`),
  CONSTRAINT `FK_mvk8g5be8f6t2rurl62dd8wem` FOREIGN KEY (`bon_cliente_id`) REFERENCES `bon_cliente` (`id`),
  CONSTRAINT `FK_k789o835dbnne19xbk2upt9le` FOREIGN KEY (`enderecos_endereco_id`) REFERENCES `bon_endereco` (`endereco_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_cliente_bon_endereco`
--
-- ORDER BY:  `enderecos_endereco_id`

LOCK TABLES `bon_cliente_bon_endereco` WRITE;
/*!40000 ALTER TABLE `bon_cliente_bon_endereco` DISABLE KEYS */;
/*!40000 ALTER TABLE `bon_cliente_bon_endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_cliente_enderecos`
--

DROP TABLE IF EXISTS `bon_cliente_enderecos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_cliente_enderecos` (
  `bon_cliente_id` bigint(20) NOT NULL,
  `enderecos_endereco_id` bigint(20) NOT NULL,
  UNIQUE KEY `enderecos_endereco_id` (`enderecos_endereco_id`),
  UNIQUE KEY `UK_k789o835dbnne19xbk2upt9le` (`enderecos_endereco_id`),
  UNIQUE KEY `UK_h9c5efijmivgw03t6dg8xae7t` (`enderecos_endereco_id`),
  KEY `FK112A5E6AAF152D25` (`enderecos_endereco_id`),
  KEY `FK112A5E6AD0B9D1B8` (`bon_cliente_id`),
  CONSTRAINT `FK112A5E6AAF152D25` FOREIGN KEY (`enderecos_endereco_id`) REFERENCES `bon_endereco` (`endereco_id`),
  CONSTRAINT `FK112A5E6AD0B9D1B8` FOREIGN KEY (`bon_cliente_id`) REFERENCES `bon_cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_cliente_enderecos`
--
-- ORDER BY:  `enderecos_endereco_id`

LOCK TABLES `bon_cliente_enderecos` WRITE;
/*!40000 ALTER TABLE `bon_cliente_enderecos` DISABLE KEYS */;
/*!40000 ALTER TABLE `bon_cliente_enderecos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_dia_util`
--

DROP TABLE IF EXISTS `bon_dia_util`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_dia_util` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dia` datetime NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4ro6fcx2elte87no71mg1dskd` (`dia`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_dia_util`
--
-- ORDER BY:  `id`

LOCK TABLES `bon_dia_util` WRITE;
/*!40000 ALTER TABLE `bon_dia_util` DISABLE KEYS */;
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (1,'2014-05-02 00:00:00','2014-05-15 17:23:57','2014-05-15 17:23:59',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (2,'2014-05-03 00:00:00','2014-05-15 17:26:00','2014-05-15 17:26:00',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (3,'2014-05-05 00:00:00','2014-05-15 17:26:11','2014-05-15 17:26:11',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (4,'2014-05-06 00:00:00','2014-05-15 17:26:14','2014-05-15 17:26:14',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (5,'2014-05-07 00:00:00','2014-05-15 17:26:16','2014-05-15 17:26:16',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (6,'2014-05-08 00:00:00','2014-05-15 17:26:18','2014-05-15 17:26:18',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (7,'2014-05-09 00:00:00','2014-05-15 17:26:20','2014-05-15 17:26:20',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (8,'2014-05-10 00:00:00','2014-05-15 17:26:25','2014-05-15 17:26:25',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (9,'2014-05-12 00:00:00','2014-05-15 17:26:31','2014-05-15 17:26:31',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (10,'2014-05-13 00:00:00','2014-05-15 17:26:33','2014-05-15 17:26:33',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (11,'2014-05-14 00:00:00','2014-05-15 17:26:35','2014-05-15 17:26:35',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (12,'2014-05-15 00:00:00','2014-05-15 17:26:38','2014-05-15 17:26:38',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (13,'2014-05-16 00:00:00','2014-05-15 17:26:39','2014-05-15 17:26:39',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (14,'2014-05-17 00:00:00','2014-05-15 17:26:42','2014-05-15 17:26:42',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (15,'2014-05-19 00:00:00','2014-05-15 17:26:50','2014-05-15 17:26:50',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (16,'2014-05-20 00:00:00','2014-05-15 17:26:53','2014-05-15 17:26:53',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (17,'2014-05-21 00:00:00','2014-05-15 17:26:55','2014-05-15 17:26:55',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (18,'2014-05-22 00:00:00','2014-05-15 17:26:57','2014-05-15 17:26:57',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (19,'2014-05-23 00:00:00','2014-05-15 17:26:59','2014-05-15 17:26:59',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (20,'2014-05-24 00:00:00','2014-05-15 17:27:01','2014-05-15 17:27:01',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (21,'2014-05-26 00:00:00','2014-05-15 17:27:06','2014-05-15 17:27:06',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (22,'2014-05-27 00:00:00','2014-05-15 17:27:08','2014-05-15 17:27:08',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (23,'2014-05-28 00:00:00','2014-05-15 17:27:10','2014-05-15 17:27:10',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (24,'2014-05-29 00:00:00','2014-05-15 17:27:12','2014-05-15 17:27:12',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (25,'2014-05-30 00:00:00','2014-05-15 17:27:16','2014-05-15 17:27:16',0);
INSERT INTO `bon_dia_util` (`id`, `dia`, `inserted`, `updated`, `version`) VALUES (26,'2014-05-31 00:00:00','2014-05-15 17:27:18','2014-05-15 17:27:18',0);
/*!40000 ALTER TABLE `bon_dia_util` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_endereco`
--

DROP TABLE IF EXISTS `bon_endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_endereco` (
  `endereco_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(120) COLLATE utf8_swedish_ci DEFAULT NULL,
  `cep` varchar(9) COLLATE utf8_swedish_ci DEFAULT NULL,
  `cidade` varchar(80) COLLATE utf8_swedish_ci DEFAULT NULL,
  `complemento` varchar(120) COLLATE utf8_swedish_ci DEFAULT NULL,
  `estado` varchar(2) COLLATE utf8_swedish_ci DEFAULT NULL,
  `logradouro` varchar(120) COLLATE utf8_swedish_ci DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `tipoEndereco` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`endereco_id`)
) ENGINE=InnoDB AUTO_INCREMENT=477 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_endereco`
--
-- ORDER BY:  `endereco_id`

LOCK TABLES `bon_endereco` WRITE;
/*!40000 ALTER TABLE `bon_endereco` DISABLE KEYS */;
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (1,'ITAIM PAULISTA','08115100','SAO PAULO',NULL,'SP','AV MARECHAL TITO 6829',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (2,'LIBERDADE','93334290','NOVO HAMBURGO',NULL,'RS','R. VER. ADAO RODRIGUES DE OLIVEIRA',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (3,'VILA ROSA','09850300','S.BERNARDO CAMPO',NULL,'SP','AV. HUMBERTO A.C. BRANCO,900',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (7,'ITAIM PAULISTA','08115100','SAO PAULO',NULL,'SP','AV MARECHAL TITO',6829,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (8,'LIBERDADE','93334290','NOVO HAMBURGO',NULL,'RS','R. VER. ADAO RODRIGUES DE OLIVEIRA',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (9,'VILA ROSA','09850300','S.BERNARDO CAMPO',NULL,'SP','AV. HUMBERTO A.C. BRANCO,',900,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (10,'','35950000','ALVINOPOLIS',NULL,'MG','PC.CEL. ARISTIDES MASCARENHAS,',169,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (11,'DAS ROSAS','93890000','NOVA HARTZ',NULL,'RS','ANGRA DOS REIS',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (12,'ITAIM PAULISTA','08115100','SAO PAULO',NULL,'SP','AV MARECHAL TITO',6829,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (13,'LIBERDADE','93334290','NOVO HAMBURGO',NULL,'RS','R. VER. ADAO RODRIGUES DE OLIVEIRA',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (14,'VILA ROSA','09850300','S.BERNARDO CAMPO',NULL,'SP','AV. HUMBERTO A.C. BRANCO,',900,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (15,'','35950000','ALVINOPOLIS',NULL,'MG','PC.CEL. ARISTIDES MASCARENHAS,',169,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (16,'DAS ROSAS','93890000','NOVA HARTZ',NULL,'RS','ANGRA DOS REIS',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (17,'CENTRO','86026720','LONDRINA',NULL,'PR','AV. ARC. DO M GERALDO FERNANDES',1170,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (18,'ITAIM PAULISTA','08115100','SAO PAULO',NULL,'SP','AV MARECHAL TITO',6829,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (19,'LIBERDADE','93334290','NOVO HAMBURGO',NULL,'RS','R. VER. ADAO RODRIGUES DE OLIVEIRA',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (20,'VILA ROSA','09850300','S.BERNARDO CAMPO',NULL,'SP','AV. HUMBERTO A.C. BRANCO,',900,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (21,'','35950000','ALVINOPOLIS',NULL,'MG','PC.CEL. ARISTIDES MASCARENHAS,',169,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (22,'DAS ROSAS','93890000','NOVA HARTZ',NULL,'RS','ANGRA DOS REIS',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (23,'CENTRO','86026720','LONDRINA',NULL,'PR','AV. ARC. DO M GERALDO FERNANDES',1170,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (24,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (25,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (26,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (27,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (28,'PENHA CIRCULAR','21020120','RIO DE JANEIRO',NULL,'RJ','AV LOBO JUNIOR,',1672,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (29,'','','',NULL,'SC','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (30,'NOVO MUNDO','','CURITIBA',NULL,'PR','ADALBERTO SCHERER',335,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (31,'IRIRIU','89227310','JOINVILLE',NULL,'SC','R JORGE AUGUSTO E.MULLER,',110,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (32,'','','SAO PAULO',NULL,'SP','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (33,'CENTRO','88360000','GUABIRUBA',NULL,'SC','RUA BRUSQUE,',2631,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (34,'JARDIM ALVORADA','87033110','MARINGA',NULL,'PR','RUA MATO GROSSO,',445,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (35,'','','',NULL,'SC','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (36,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (37,'CITY PETROPOLIS','14409601','FRANCA',NULL,'SP','R ELIO ANTONIO OLIVEIRA, 1090 FUNDOS',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (38,'N.SRA.DO CARMO','3551900','NOVA SERRANA',NULL,'MG','PRESIDENTE COSTA E SILVA',718,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (39,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (40,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (41,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (42,'.','35519000','NOVA SERRANA-MG',NULL,'MG','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (43,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (44,'TESTO SALTO','89074700','BLUMENAU',NULL,'SC','R:JOHANN KARSTEN,',260,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (45,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (46,'LIMOEIRO','88318640','ITAJAI',NULL,'SC','RUA JOAO HAMILTON MERISIMO',126,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (47,'DOM BOSCO','89115000','LUIZ ALVES',NULL,'SC','AV MARIA MARANGONI',391,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (48,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (49,'CENTRO','86701270','ARAPONGAS',NULL,'PR','RUA IBIS,',644,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (50,'ESTIVA','18520000','CERQUILHO',NULL,'SP','R: DO VELHO RAMAL,',490,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (51,'JD. LAVINIA','','S.BERNARDO DO CAMPO',NULL,'SP','R ROLANDO GAMBINI, 55 LOJA',1,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (52,'','','',NULL,'RS','EST.RS 240-CENTRO-PORTAO-RS',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (53,'GUARANI','95630000','PAROBE',NULL,'RS','R ARMINDO SCHMIDT,',676,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (54,'FORTALEZA','89053400','BLUMENAL',NULL,'SC','R ALMIRANTE BARROSO,',351,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (55,'BONSUCESSO','07170350','GUARULHOS',NULL,'SP','AV. PAPA JOAO PAULO I,',5235,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (56,'JD N.S.LOURDES','18520000','CERQUILHO',NULL,'SP','AV. DR. VINICIO GAGLIARDI,1311  CX.P',1,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (57,'','','FRANCA',NULL,'SP','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (58,'JD.PLANALTO','58301645','SANTA RITA',NULL,'PB','ROD BR 230 -LIG. STA RITA S/N',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (59,'PAU SECO','14801905','ARARAQUARA',NULL,'SP','ROD. WASHINTON LUIZ KM 276,',5,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (60,'','89120000','TIMBO',NULL,'SC','R BLUMENAU,',615,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (61,'','18900000','STA CRUZ RIO PARDO',NULL,'SP','RUA MATHIAS BAN,',49,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (62,'STO.AMARO','04754080','SAO PAULO',NULL,'SP','R PLACIDO VIEIRA,',405,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (63,'BOM RETIRO','89219030','JOINVILLE',NULL,'SC','R: ARNO W. DOLHER ,',145,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (64,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (65,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (66,'POLO CALCADISTA','63800000','QUIXERAMOBIM',NULL,'CE','GERALDO BIZARRIA DE CARVALHO',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (67,'','62040050','SOBRAL',NULL,'CE','R. PIMENTEL GOMES,',214,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (68,'SANTA RITA','89160000','RIO DO SUL',NULL,'SC','BR 470  KM 137,',2150,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (69,'BATEAS','88355350','BRUSQUE',NULL,'SC','R: EDGAR VON BUETTNER',941,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (70,'BADENFURT','89010971','BLUMENAU',NULL,'SC','R: BR 470 KM 61,',7235,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (71,'DISTR. INDUSTRIAL','17034300','BAURU',NULL,'SC','R RICARDO GABAS, 1-45   CX.P.',497,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (72,'PQ.SAO DOMINGOS','05121000','SAO PAULO',NULL,'SP','R DR. ODON CARLOS DE F. FERRAZ,',915,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (149,'ITAIM PAULISTA','08115100','SAO PAULO',NULL,'SP','AV MARECHAL TITO',6829,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (150,'LIBERDADE','93334290','NOVO HAMBURGO',NULL,'RS','R. VER. ADAO RODRIGUES DE OLIVEIRA',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (151,'VILA ROSA','09850300','S.BERNARDO CAMPO',NULL,'SP','AV. HUMBERTO A.C. BRANCO,',900,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (152,'','35950000','ALVINOPOLIS',NULL,'MG','PC.CEL. ARISTIDES MASCARENHAS,',169,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (153,'DAS ROSAS','93890000','NOVA HARTZ',NULL,'RS','ANGRA DOS REIS',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (154,'CENTRO','86026720','LONDRINA',NULL,'PR','AV. ARC. DO M GERALDO FERNANDES',1170,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (155,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (156,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (157,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (158,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (159,'PENHA CIRCULAR','21020120','RIO DE JANEIRO',NULL,'RJ','AV LOBO JUNIOR,',1672,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (160,'','','',NULL,'SC','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (161,'NOVO MUNDO','','CURITIBA',NULL,'PR','ADALBERTO SCHERER',335,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (162,'IRIRIU','89227310','JOINVILLE',NULL,'SC','R JORGE AUGUSTO E.MULLER,',110,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (163,'','','SAO PAULO',NULL,'SP','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (164,'CENTRO','88360000','GUABIRUBA',NULL,'SC','RUA BRUSQUE,',2631,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (165,'JARDIM ALVORADA','87033110','MARINGA',NULL,'PR','RUA MATO GROSSO,',445,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (166,'','','',NULL,'SC','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (167,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (168,'CITY PETROPOLIS','14409601','FRANCA',NULL,'SP','R ELIO ANTONIO OLIVEIRA, 1090 FUNDOS',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (169,'N.SRA.DO CARMO','3551900','NOVA SERRANA',NULL,'MG','PRESIDENTE COSTA E SILVA',718,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (170,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (171,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (172,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (173,'.','35519000','NOVA SERRANA-MG',NULL,'MG','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (174,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (175,'TESTO SALTO','89074700','BLUMENAU',NULL,'SC','R:JOHANN KARSTEN,',260,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (176,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (177,'LIMOEIRO','88318640','ITAJAI',NULL,'SC','RUA JOAO HAMILTON MERISIMO',126,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (178,'DOM BOSCO','89115000','LUIZ ALVES',NULL,'SC','AV MARIA MARANGONI',391,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (179,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (180,'CENTRO','86701270','ARAPONGAS',NULL,'PR','RUA IBIS,',644,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (181,'ESTIVA','18520000','CERQUILHO',NULL,'SP','R: DO VELHO RAMAL,',490,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (182,'JD. LAVINIA','','S.BERNARDO DO CAMPO',NULL,'SP','R ROLANDO GAMBINI, 55 LOJA',1,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (183,'','','',NULL,'RS','EST.RS 240-CENTRO-PORTAO-RS',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (184,'GUARANI','95630000','PAROBE',NULL,'RS','R ARMINDO SCHMIDT,',676,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (185,'FORTALEZA','89053400','BLUMENAL',NULL,'SC','R ALMIRANTE BARROSO,',351,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (186,'BONSUCESSO','07170350','GUARULHOS',NULL,'SP','AV. PAPA JOAO PAULO I,',5235,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (187,'JD N.S.LOURDES','18520000','CERQUILHO',NULL,'SP','AV. DR. VINICIO GAGLIARDI,1311  CX.P',1,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (188,'','','FRANCA',NULL,'SP','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (189,'JD.PLANALTO','58301645','SANTA RITA',NULL,'PB','ROD BR 230 -LIG. STA RITA S/N',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (190,'PAU SECO','14801905','ARARAQUARA',NULL,'SP','ROD. WASHINTON LUIZ KM 276,',5,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (191,'','89120000','TIMBO',NULL,'SC','R BLUMENAU,',615,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (192,'','18900000','STA CRUZ RIO PARDO',NULL,'SP','RUA MATHIAS BAN,',49,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (193,'STO.AMARO','04754080','SAO PAULO',NULL,'SP','R PLACIDO VIEIRA,',405,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (194,'BOM RETIRO','89219030','JOINVILLE',NULL,'SC','R: ARNO W. DOLHER ,',145,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (195,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (196,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (197,'POLO CALCADISTA','63800000','QUIXERAMOBIM',NULL,'CE','GERALDO BIZARRIA DE CARVALHO',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (198,'','62040050','SOBRAL',NULL,'CE','R. PIMENTEL GOMES,',214,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (199,'SANTA RITA','89160000','RIO DO SUL',NULL,'SC','BR 470  KM 137,',2150,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (200,'BATEAS','88355350','BRUSQUE',NULL,'SC','R: EDGAR VON BUETTNER',941,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (201,'BADENFURT','89010971','BLUMENAU',NULL,'SC','R: BR 470 KM 61,',7235,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (202,'DISTR. INDUSTRIAL','17034300','BAURU',NULL,'SC','R RICARDO GABAS, 1-45   CX.P.',497,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (203,'PQ.SAO DOMINGOS','05121000','SAO PAULO',NULL,'SP','R DR. ODON CARLOS DE F. FERRAZ,',915,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (204,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (205,'SAO GERALDO','35680448','ITAUNA',NULL,'MG','RUA CANDIDO BERNARDES',545,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (206,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (207,'IMIGRANTE','93890000','NOVA HARTZ',NULL,'RS','RUA HENRIQUE HOFFMAN',3095,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (208,'VILA APARECIDA','14401234','FRANCA',NULL,'SP','AVENIDA BRASIL',2013,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (209,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (210,'PARI','03022000','SAO PAULO',NULL,'SP','RUA PAULO ANDRIGUETTI,',290,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (211,'LACE','29703060','COLATINA',NULL,'ES','PINTOR LUDOVICO 118 GP',2,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (212,'CENTRO','95650000','IGREJINHA',NULL,'RS','TRAVESSA DA LIBERDADE,',64,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (213,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (214,'JD PEROLA','13454010','STA.BARBARA D\'OESTE',NULL,'SP','RUA DO CAFE, 360 AO',400,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (215,'CENTRO','89620000','CAMPOS NOVOS',NULL,'SC','RUA DOM DANIEL HOSTIN, 434 SL',2,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (216,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (217,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (218,'BRAS','03052040','SAO PAULO',NULL,'SP','R BELO HORIZONTE, 40 SALA',2,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (219,'CENTRO','16200010','BIRIGUI',NULL,'SP','TRAV. MAL. DEODORO, 56    CX.P.',301,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (220,'','','FRANCA',NULL,'SP','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (221,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (222,'','','',NULL,'SP','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (223,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (224,'CANABARRO','95890000','TEUTONIA',NULL,'RS','RUA 31 DE MARCO N',1060,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (225,'BOM JARDIM','14406126','FRANCA',NULL,'SP','AV WILSON SABIO DE MELO,',4100,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (226,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (227,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (228,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (229,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (230,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (231,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (232,'INDUSTRIAL','93800000','SAPIRANGA',NULL,'RS','RUA CRUZEIRO DO SUL N.',856,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (233,'MOOCA','03164120','SAO PAULO',NULL,'SP','R ALMIRANTE BRASIL,',243,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (234,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (235,'LIMOEIRO','88316000','ITAJAI',NULL,'SC','RUA SOROCABA',347,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (236,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (237,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (238,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (239,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (240,'BARRA FUNDA','01141010','SAO PAULO',NULL,'SP','R: ROBERT BOSCH,',568,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (241,'CENTRO','16200026','BIRIGUI',NULL,'SP','GETULIO VARGAS',5,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (242,'BRACO ELZA','89115000','LUIZ ALVES',NULL,'SC','AVENIDA EMILIA RECH 141 GAPAO',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (243,'CENTRO','86200000','IBIPORA',NULL,'PR','AV. PREF MARIO DE MENEZES 1324 FUNDOS',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (244,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (245,'VORSTADT','89015900','BLUMENAU',NULL,'SC','R. ITAJAI,',948,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (246,'CENTRO','95650000','IGREJINHA',NULL,'RS','R JOAO PAULO I,',77,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (247,'PQ INDUSTRIAL','86084000','LONDRINA',NULL,'PR','RUA BERTOLUCCI,',527,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (248,'IBITINGA','14940000','MONTE ALEGRE',NULL,'SP','AV.DAS BORDADEIRAS',823,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (249,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (250,'DISTRITO INDUSTRIAL','79170000','SIDROLANDIA',NULL,'MS','RODOVIA BR 60 KM 41,',5,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (251,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (252,'VILA ANDRE DE FREITA','35519000','NOVA SERRANA',NULL,'MG','RUA JOSE DO PATROCINIO,',6,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (253,'CENTRO','13450280','STA BARBARA D\'OESTE',NULL,'SP','AV. MONTE CASTELO,',492,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (254,'SANTO AMARO','04730000','SAO PAULO',NULL,'SP','RUA DR. RUBENS GOMES BUENO,',158,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (255,'PQ INDUSTRIAL ROLAND','86600000','ROLANDIA',NULL,'PR','JOSE NICOLA CALIENTO,',380,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (256,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (257,'PARQUE DAS NACOES','07243590','GUARULHOS',NULL,'SP','EDUARDO FRONER',165,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (258,'RIO COTIA','06714150','COTIA',NULL,'SP','R DR LADISLAU RETI,',43,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (259,'','','JARAGUA DO SUL',NULL,'SC','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (260,'PQE INDUSTR. II','86703070','ARAPONGAS',NULL,'PR','R JURUTAU,',1545,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (261,'ZONA RURAL','79601970','TRES LAGOAS',NULL,'MS','BR 18 AV SAMIR THOME KM 277',5,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (262,'MARGEM ESQUERDA','89110000','GASPAR',NULL,'SC','RUA LUIZ FRANZOI',2375,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (263,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (264,'','87640000','UNIFLOR',NULL,'PR','RUA ORQUIDEA,',899,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (265,'JARDIM GRANADA','87160000','MANDAGUACU',NULL,'PR','RUA JOSE EMANUEL DE MOURA,',222,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (266,'MINEIRA VELHA','88806300','CRICIUMA',NULL,'SC','AV ASSEMBLEIA DE DEUS/RUA',1603,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (267,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (268,'','89260900','JARAGUA DO SUL',NULL,'SC','R. BERTHA WEEGE,',200,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (269,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (270,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (271,'PARI','03013030','SAO PAULO',NULL,'SP','RUA PARAIBA,',229,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (272,'JD REDENTOR','14409291','FRANCA',NULL,'SP','RUA DR JOAO MESSIAS,',159,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (273,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (274,'CHACARA','85900000','TOLETO',NULL,'PR','ESTRADA TOLEDO OURO VERDE SN',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (275,'JD PEROLA','13454100','STA BARBARA D\'OESTE',NULL,'SP','R DO ACUCAR',459,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (276,'CENTRO','95150000','NOVA PETROPOLIS',NULL,'RS','RODOVIA PRESIDENTE GETULIO VARGAS',353,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (277,'PIO X','95034170','CAXIA DO SUL',NULL,'RS','RUA FLORIANO PREZZI,',1165,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (278,'','85601000','FRANCISCO BELTRAO',NULL,'PR','R JULIO ASSIS CAVALHEIRO,',222,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (279,'AGUA VERDE','89042300','CURITIBA',NULL,'PR','RUA JOHANN OHF,',1630,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (280,'********************','','********************',NULL,'**','****************************************',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (281,'SANTA LUZIA','88357101','BRUSQUE',NULL,'SC','RUA AUGUSTO KLAPOTH, S/N',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (282,'MOINHOS','95900000','LAJEADO',NULL,'RS','RUA CARLOS SPOHR FILHO SALA B,',1409,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (283,'LIMOEIRO','88350000','BRUSQUE',NULL,'SC','ROD. ANTONIO HEIL (SC 48) KM',21,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (284,'PIPEIRO','13360000','CAPIVARI',NULL,'SP','CHACARA BELA VISTA,SN   CX.P.',1814,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (285,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (286,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (287,'JD PAULISTA','14402406','FRANCA',NULL,'SP','R: JOAO DOS SANTOS FERREIRA',1141,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (288,'CENTRO','80020010','CURITIBA',NULL,'PR','PC GEN. OSORIO, 333 LOJA',6,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (289,'BRAS','03016000','SAO PAULO',NULL,'SP','R ORIENTE,',296,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (290,'AVAI','89270000','GUARAMIRIM',NULL,'SC','RODOVIA BR 280  KM',55,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (291,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (292,'VILA SIDERIA','18900000','SANTA CRUZ DO RIO PA',NULL,'SP','RUA JOSE ROCHA SILOS,',295,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (293,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (294,'','95650000','IGREJINHA',NULL,'RS','RUA BERTHALINA KIRSCH,',267,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (295,'VILA MARIA','03021030','SAO PAULO',NULL,'SP','AV. GUILHERME COTCHING,',85,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (296,'ATUBA','83408280','CURITIBA',NULL,'PR','AV. ABEL SCUISSIATO,',2832,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (297,'CENTRO','44191000','SANTO ESTEVAO',NULL,'BA','RUA PROF. MARIA LIA CELIA VELLAME,SN',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (298,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (299,'MADALENA','62500000','ITAPIPOCA',NULL,'CE','RUA DA UNIVERSIDADE',406,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (300,'CX.P. 30','89205010','JOIVILLE',NULL,'SC','RUA OTTO EDUARDO LEPPER,',1,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (301,'ZONA 04','87015570','MARINGA',NULL,'PR','R CARAMURU,',318,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (302,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (303,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (304,'DISTR.INDUSTRIAL','13456108','STA.BARBARA D\'OESTE',NULL,'SP','R POTIGUARES,',810,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (305,'SE','01021100','SAO PAULO',NULL,'SP','RUA 25 DE MARCO',1260,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (306,'FANY','81030300','CURITIBA',NULL,'PR','CAPITAO JOSE MARINHO SOBRINHO',1245,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (307,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (308,'CENTRO','','S.MIGUEL DOS CAMPOS',NULL,'AL','SITIO VERA CRUZ SN',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (309,'SANTA TEREZINHA','14409267','FRANCA',NULL,'SP','JOSE MARIA JACINTHO REBELO  N',5821,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (310,'CENTRO','62900000','RUSSAS',NULL,'CE','AV CEL. ANTONIO CORDEIRO,',1001,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (311,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (312,'CENTRO','35519000','NOVA SERRANA',NULL,'MG','RUA ESTER COELHO RIBEIRO',244,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (313,'VILA NOVA','95660000','TRES COROAS',NULL,'RS','SAO LEOPOLDO',186,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (314,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (315,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (316,'MARISTER','16204250','BIRIGUI',NULL,'SP','RUA ANGELO BORIN,',715,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (317,'PINHEIRINHO','81150070','CURITIBA',NULL,'PR','R ATILIO BRUNETTI,',2136,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (318,'CENTRO','89107000','POMERODE',NULL,'SC','RUA TIRADENTES,',230,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (319,'KM 04','89170000','LAURENTINO',NULL,'SC','RUA NARCISO FAQUINI,',3799,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (320,'FLORESTA','89211460','JOINVILE',NULL,'SC','AV.ANTONIO RAMOS ALVIN',1365,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (321,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (322,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (323,'CENTRO','','MORENO',NULL,'PE','AV. CLETO CAMPELO',3178,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (324,'','89370000','PADANDUVA',NULL,'SC','RUA JOAQUIM MENDES, 191    CX.P.',1,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (325,'CENTRO','83880000','RIO NEGRO',NULL,'PR','RUA CAP. JOAO BLEY,',30,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (326,'CENTRO','89110000','GASPAR',NULL,'SC','R FREI SOLANO, 339 GALPAO',11,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (327,'','','',NULL,'','EDSONJF@ALPARGATAS.COM.BR',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (328,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (329,'MIRA SUL','99560000','SARANDI',NULL,'RS','BR 386  KM',134,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (330,'JD. CENTENARIO','14403555','FRANCA',NULL,'SP','RUA ANTONIO ELIAS BORGES,',2441,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (331,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (332,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (333,'VIEIRAS','89257000','JARAGUA DO SUL',NULL,'SC','MANOEL FRANCISCO DA COSTA',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (334,'PARQUE FRANVILLE','14403574','FRANCA',NULL,'SP','JOAO URIAS PIMENTA',4460,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (335,'SALTO WEISSBACH','89032001','BLUMENAU',NULL,'SC','R BAHIA,',5129,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (336,'CIDADE INDUSTRIAL','32210260','CONTAGEM',NULL,'MG','RUA JONAS BARCELOS CORREIA,',215,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (337,'PRAIA DE FORA','88138090','PALHOCA',NULL,'SC','RUA MANOEL JOAO MARTINS S/N',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (338,'CENTRO','01021000','SAO PAULO',NULL,'SP','RUA 25 DE MARCO',241,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (339,'CONTORNO NORTE','86817500','APUCARANA',NULL,'PR','ESTRADA DA SERRINHA S/N KM',8,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (340,'CORVETA','89245000','ARAQUARI',NULL,'SC','RODOVIA BR 101 KM',59,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (341,'TANGUEIRA','61940000','MARANGUAPE',NULL,'CE','RUA CON. HEITOR VIEIRA CAVALCANTE,',300,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (342,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (343,'NEVA','85802160','CASCAVEL',NULL,'PR','OSVALDO CRUZ',803,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (344,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (345,'','','BRUSQUE',NULL,'SC','STOP SHOP',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (346,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (347,'ARUJA','83020712','SAO JOSE DOS PINHAIS',NULL,'PR','RODOVIA CONTORNO LESTE BR 116',1710,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (348,'CENTRO','','',NULL,'','VICENTE MACHADO 7 SALA',4,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (349,'PINHAL','13315971','CABREUVA',NULL,'SP','RUA LAURO PINTO TOLEDO',410,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (350,'VILA JARAGUA-OSASCO','06278000','SAO PAULO',NULL,'SP','VIA ANHANGUERA,S/N�KM 19-LD.ESQ.CAP.INT',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (351,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (352,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (353,'NOVA RUSSIA','','PONTA GROSSA',NULL,'PR','D.PEDRO II-',337,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (354,'BRAS','03013001','SAO PAULO',NULL,'SP','CASEMIRO DE ABREU,',502,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (355,'BRAS','03013001','SAO PAULO',NULL,'SP','R CASEMIRO DE ABREU,',518,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (356,'BRAS OU BOM RETIRO','','SP',NULL,'SP','MILLER 281 OU PF.CESARE LOMBROSO',140,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (357,'BRAS','03026001','SAO PAULO',NULL,'SP','SILVA TELES',113,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (358,'BRAS','','SAO PAULO',NULL,'SP','MEGA POLO',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (359,'BRAS','03009000','SAO PAULO',NULL,'SP','JOAO TEODORO',1673,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (360,'BRAS','03011010','SAO PAULO',NULL,'SP','RUA MILLER,',240,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (361,'BRAS','03013001','SAO PAULO',NULL,'SP','CASEMIRO DE ABREU',311,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (362,'BRAS','03013011','SAO PAULO',NULL,'SP','R MENDES JUNIOR,',555,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (363,'','','SAO PAULO',NULL,'SP','DANYMAR-NEUZA 011-2292-',7441,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (364,'BRAS','','SAO PAULO',NULL,'SP','RUA CASEMIRO DE ABREU',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (365,'PARI','03026001','SAO PAULO',NULL,'SP','R SILVA TELES,',235,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (366,'BRAS','03026001','SAO PAULO',NULL,'SP','SILVA TELES',191,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (367,'BRAS','03012060','SAO PAULO',NULL,'SP','RUA RUBINO DE OLIVEIRA,',145,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (368,'JD REALCE','08674000','SUZANO',NULL,'SP','RUA GENERAL FRANCISCO GLICERIO',2310,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (369,'PARI','03026001','SAO PAULO',NULL,'SP','RUA SILVA TELES,',251,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (370,'PARI','','SAO PAULO',NULL,'SP','RUA SILVA TELES',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (371,'BRAS','','SAO PAULO',NULL,'SP','MENDES GONCALVES 184-186/SILVA TELES-',435,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (372,'BRAS','','SAO PAULO',NULL,'','SILVA TELES',247,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (373,'BRAS','03013011','SAO PAULO',NULL,'SP','R. MENDES JUNIOR,',452,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (374,'BRAS','03026001','SAO PAULO',NULL,'SP','SILVA TELES 73/',77,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (375,'BRAS','03016020','SAO PAULO',NULL,'','RUA JOLI',626,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (376,'','','BRUSQUE',NULL,'SC','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (377,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (378,'PARI','03018010','SAO PAULO',NULL,'SP','R BRESSER,',10,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (379,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (380,'BRAS','03027000','SAO PAULO',NULL,'SP','RUA XAVANTES,',800,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (381,'BRAS','03016020','SAO PAULO',NULL,'SP','RUA JOLI,',568,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (382,'BRAS','03016001','SAO PAULO',NULL,'SP','ORIENTE',343,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (383,'SE','01021200','SAO PAULO',NULL,'SP','R 25 DE MARCO,',999,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (384,'BRAS','','SAO PAULO',NULL,'','XAVANTES',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (385,'CENTRO','','SAO PAULO',NULL,'SP','R FLORENCIO DE ABREU,',418,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (386,'BRAS','03017000','SAO PAULO',NULL,'SP','BRESSER 330/',334,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (387,'BRAS','','SAO PAULO',NULL,'SP','SILVA TELES',692,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (388,'STA TEREZINHA','88352502','BRUSQUE',NULL,'SC','RODOVIA ANTONIO HEIL SL 50 TERREO -',635,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (389,'CENTRO','37580000','MONTE SIAO',NULL,'MG','PREFEITO JOSE CARLOS FRANCISCO',181,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (390,'','','MONTE SIAO',NULL,'MG','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (391,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (392,'','','MONTE SIAO',NULL,'MG','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (393,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (394,'','','SAO PAULO',NULL,'SP','R. MIGUEL MENTEN,',207,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (395,'','','SAO PAULO',NULL,'SP','RUA 25 DE MARCO',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (396,'','03027010','SAO PAULO',NULL,'SP','R MENDES GONCALVES, 193/',195,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (397,'PARI','03011000','SAO PAULO',NULL,'SP','R MARIA MARCOLINA, 748/',764,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (398,'ITOUPAVA SECA','89010971','BLUMENAU',NULL,'SC','R IGUACU, 291/',363,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (399,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (400,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (401,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (402,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (403,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (404,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (405,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (406,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (407,'SAO JOSE','','PONTA GROSSA',NULL,'PR','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (408,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (409,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (410,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (411,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (412,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (413,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (414,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (415,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (416,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (417,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (418,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (419,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (420,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (421,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (422,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (423,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (424,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (425,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (426,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (427,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (428,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (429,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (430,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (431,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (432,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (433,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (434,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (435,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (436,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (437,'ALTO AMPARO','','PONTA GROSSA',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (438,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (439,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (440,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (441,'SANTA TERESINHA','','PONTA GROSSA',NULL,'PR','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (442,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (443,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (444,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (445,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (446,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (447,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (448,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (449,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (450,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (451,'STA MONICA','','PONTA GROSSA',NULL,'PR','R CADES S N',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (452,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (453,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (454,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (455,'BERTO CIRIO','92480000','NOVA SANTA RITA',NULL,'RS','BR 386 /ROD.TABAI-CANOAS/ KM 436,',8779,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (456,'RIO CERRO I','89251970','JARAGUA DO SUL',NULL,'SC','ROD.SC 416, 2727     CX.P.',1102,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (457,'BAIRRO ALTO','82840210','CURITIBA',NULL,'PR','R. RIO JARI,',40,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (458,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (459,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (460,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (461,'VILA DAINESE','13469413','AMERICANA',NULL,'SP','RUA SANTAREM, 158/',240,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (462,'PINHEIROS','05423040','SAO PAULO',NULL,'SP','R. DR FERNANDES COELHO,64 9.ANDAR SL',91,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (463,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (464,'VILA HAUER','','CURITIBA',NULL,'PR','RUA CARLOS DE LAET,',1211,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (465,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (466,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (467,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (468,'BOM RETIRO','01126001','SAO PAULO',NULL,'SP','RUA JULIO CONCEI�AO,',455,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (469,'SANTA QUIT�RIA','80310420','CURITIBA',NULL,'PR','RUA JOS� ALENCAR GUIMAR�ES,',561,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (470,'PARQUE INDUSTRIAL','86800765','APUCARANA',NULL,'PR','RUA HENRI HERMANN ROBERT STORM,',375,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (471,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (472,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (473,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (474,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (475,'','','',NULL,'','',NULL,'COMERCIAL',0);
INSERT INTO `bon_endereco` (`endereco_id`, `bairro`, `cep`, `cidade`, `complemento`, `estado`, `logradouro`, `numero`, `tipoEndereco`, `version`) VALUES (476,'','','',NULL,'','',NULL,'COMERCIAL',0);
/*!40000 ALTER TABLE `bon_endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_fornecedor`
--

DROP TABLE IF EXISTS `bon_fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_fornecedor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` int(11) NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `nomeFantasia` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `razaoSocial` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `representante` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `representanteContatoInfo` varchar(100) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  `documento` varchar(50) COLLATE utf8_swedish_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone1` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone2` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone3` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone4` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `inscricao_estadual` varchar(20) COLLATE utf8_swedish_ci DEFAULT NULL,
  `inscricao_municipal` varchar(40) COLLATE utf8_swedish_ci DEFAULT NULL,
  `dt_cadastro` datetime DEFAULT NULL,
  `contato` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kirewe7evn7ajbxkknr1fertk` (`nomeFantasia`),
  UNIQUE KEY `UK_muckg45c0ef679eqxhe905pci` (`nomeFantasia`)
) ENGINE=InnoDB AUTO_INCREMENT=478 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_fornecedor`
--
-- ORDER BY:  `id`

LOCK TABLES `bon_fornecedor` WRITE;
/*!40000 ALTER TABLE `bon_fornecedor` DISABLE KEYS */;
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (150,1,'2014-06-02 17:54:37','2014-06-02 17:54:37','MASH','MASH','JULIO','4130882885',0,'115423330111',NULL,'1125719180','',NULL,NULL,'03125730000107',NULL,'2011-09-13 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (151,2,'2014-06-02 17:54:37','2014-06-02 17:54:37','OLIMPIKUS','CALCADOS AZALEIA S/A','NUNES  41-9102-8282','4133424041',0,'98408073001606',NULL,'515431000','4584113130',NULL,NULL,'0860226670',NULL,'2005-08-02 00:00:00','DIEGO ATACADO');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (152,3,'2014-06-02 17:54:37','2014-06-02 17:54:37','JOLITEX','IND. E COMERCIO JOLITEX LTDA','WALDIR 9964-4393 WAG 9969-1701','0412732778',0,'61808531000280',NULL,'01143937000','0117514488',NULL,NULL,'635146372113',NULL,'1989-11-13 00:00:00','4066-7767(DIADEMA)');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (153,4,'2014-06-02 17:54:37','2014-06-02 17:54:37','MASCARENHAS','CIA. FABRIL MASCARENHAS','NELSON TRENTIM 9972-6959','32249302',0,'16718231000175',NULL,'03138851011','03138551449',NULL,NULL,'0230738310030',NULL,'1989-11-13 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (154,5,'2014-06-02 17:54:37','2014-06-02 17:54:37','RAMARIN','RAMARIN','GILSON','4499723135',0,'88104328000107',NULL,'5135658100','',NULL,NULL,'2940000640',NULL,'2009-03-04 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (155,6,'2014-06-02 17:54:37','2014-06-02 17:54:37','DUPALIE','DUPALIE','NILCEIA','',0,'6012020854',NULL,'0033234593','',NULL,NULL,'8126289100156',NULL,'2012-03-30 00:00:00','NILCEIA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (156,7,'2014-06-02 17:54:37','2014-06-02 17:54:37','COMFORT FLEX','COMFORT FLEX','CARLOS 44-9992-8979 TIM','4430347290',0,'',NULL,'','',NULL,NULL,'',NULL,'2011-09-13 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (157,8,'2014-06-02 17:54:37','2014-06-02 17:54:37','DIXIE','DIXIE','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-08-27 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (158,9,'2014-06-02 17:54:37','2014-06-02 17:54:37','POLEGAR','POLEGAR','VALTER','4198556526',0,'',NULL,'4132281574','',NULL,NULL,'',NULL,'2012-09-13 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (159,10,'2014-06-02 17:54:37','2014-06-02 17:54:37','BOX 130','BOX 130','CARLOS','',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-01-31 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (160,11,'2014-06-02 17:54:37','2014-06-02 17:54:37','DEMILLUS','DEMILLUS S.A. IND E COM','VILMAR','4699751479',0,'33115817000164',NULL,'2125988100','2125988101',NULL,NULL,'81862656',NULL,'2003-03-12 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (161,12,'2014-06-02 17:54:37','2014-06-02 17:54:37','ALVER KLEIN','ALVER KLEIN','ALBERTO 4132240972 4988015376','4191378408',0,'',NULL,'','',NULL,NULL,'',NULL,'2009-10-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (162,13,'2014-06-02 17:54:37','2014-06-02 17:54:37','THAMY','BULEK & ARAUJO LTDA ME','PEDRO','0413273266',0,'95432167000138',NULL,'0413273266','',NULL,NULL,'6410092881',NULL,'2014-06-02 17:54:37','PEDRO');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (163,14,'2014-06-02 17:54:37','2014-06-02 17:54:37','IZALTEX','IZALTEX CONFECCOES LTDA','WILSON  47-9648-9615','04734554673',0,'00787388000112',NULL,'0474370884','',NULL,NULL,'253166497',NULL,'1997-06-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (164,15,'2014-06-02 17:54:37','2014-06-02 17:54:37','SESTINI','SESTINI MERCANTIL LTDA','FONSECA 41-9154-5995','4132572768',0,'',NULL,'1131231600','',NULL,NULL,'',NULL,'2005-11-17 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (165,16,'2014-06-02 17:54:37','2014-06-02 17:54:37','FORE','WICETEX IND. E COM. CONFEC. LTDA','VITOR VISCONTI','4799834285',0,'',NULL,'473540325','473540585',NULL,NULL,'',NULL,'2005-02-18 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (166,17,'2014-06-02 17:54:37','2014-06-02 17:54:37','HOTFIELD','HOTFIELD-J&C ALMEIDA-VESTUARIO LTDA','FABIO','04499108237',0,'09036414000145',NULL,'4432262605','',NULL,NULL,'9041533560',NULL,'2012-09-25 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (167,18,'2014-06-02 17:54:37','2014-06-02 17:54:37','KARDIE','MALHARIA SILVA','RONALDO','4232356979',0,'',NULL,'','',NULL,NULL,'',NULL,'2009-02-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (168,19,'2014-06-02 17:54:37','2014-06-02 17:54:37','OASIS','OA`SIS TAPETES','VILMAR','99720996',0,'',NULL,'01124313018','',NULL,NULL,'',NULL,'2012-04-26 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (169,20,'2014-06-02 17:54:38','2014-06-02 17:54:38','MARKIN','CALCADO AMOMAR LTDA ME','FLAVIO    41-248-7651','4199755117',0,'02510259000108',NULL,'1637037570','',NULL,NULL,'310252636119',NULL,'2003-08-21 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (170,21,'2014-06-02 17:54:38','2014-06-02 17:54:38','NAYANE','NAYANE CALCADOS','LOURENCO','',0,'70965165000183',NULL,'3732267222','',NULL,NULL,'4528785290009',NULL,'2010-08-25 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (171,22,'2014-06-02 17:54:38','2014-06-02 17:54:38','DELLUS','DELLUS','VARELLA','4799461538',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-03-10 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (172,23,'2014-06-02 17:54:38','2014-06-02 17:54:38','GAB','GAB','MOACIR 41 9902-2003','4133676917',0,'',NULL,'4733344747','',NULL,NULL,'',NULL,'2010-04-06 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (173,24,'2014-06-02 17:54:38','2014-06-02 17:54:38','MAMAE EU QUERO','MAMAE EU KERO','KETHELIN','41411464',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (174,25,'2014-06-02 17:54:38','2014-06-02 17:54:38','SANDILI','IND.CALCADOS SAMUEL','LOURENCO  41-9157-1577','4130352998',0,'09133506000143',NULL,'3732263291','',NULL,NULL,'',NULL,'2010-08-27 00:00:00','ARIANE');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (175,26,'2014-06-02 17:54:38','2014-06-02 17:54:38','NOVOPE','NOVOPE','CARLOS','4196957334',0,'',NULL,'','',NULL,NULL,'',NULL,'2009-06-05 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (176,27,'2014-06-02 17:54:38','2014-06-02 17:54:38','KARSTEN','KARSTEN S.A.','AROLDO 459914-1551','04632250050',0,'82640558000104',NULL,'0473314000','0473314063',NULL,NULL,'250026368',NULL,'1989-11-13 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (177,28,'2014-06-02 17:54:38','2014-06-02 17:54:38','SULTAN','SULTAN IND E COM LTDA','IVO  (41) 9965-0170','4136421621',0,'60869468000300',NULL,'1121958300','',NULL,NULL,'',NULL,'2008-05-08 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (178,29,'2014-06-02 17:54:38','2014-06-02 17:54:38','DANKA','DANKA','JUNIOR     04299646744','0473553870',0,'04475928000183',NULL,'04732470746','',NULL,NULL,'254244521',NULL,'2000-01-01 00:00:00','ROSANE');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (179,30,'2014-06-02 17:54:38','2014-06-02 17:54:38','ROVITEX','ROVITEX','ANTONIO 8805-9706/9995-3030','30253010',0,'252199782',NULL,'4733778000','',NULL,NULL,'79233672000105',NULL,'2004-07-30 00:00:00','LILIAN-SULFORT@HOTMA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (180,31,'2014-06-02 17:54:38','2014-06-02 17:54:38','WINIPEG','WINIPEG','REGINALDO','1433722162',0,'',NULL,'','',NULL,NULL,'',NULL,'2011-10-21 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (181,32,'2014-06-02 17:54:38','2014-06-02 17:54:38','SENILHA','SENILHA CONFECCOES DE ROUPAS LTDA','EUCLIDES 9974-2220','0432521339',0,'77343739000120',NULL,'0432521339','',NULL,NULL,'6280246776',NULL,'1999-08-24 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (182,33,'2014-06-02 17:54:38','2014-06-02 17:54:38','SELENE','SELENE IND. TEXTIL S.A.','ODIR 9972-7593','32236993',0,'47254545000198',NULL,'01533848888','01533848892',NULL,NULL,'265002222117',NULL,'1989-11-13 00:00:00','FLAVIO');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (183,34,'2014-06-02 17:54:38','2014-06-02 17:54:38','CATARINENSE','RICARDO DIAS NEGRI','WALDIR  41-9976-8743','04132298163',0,'00580693000139',NULL,'01141095844','01143522420',NULL,NULL,'635288725113',NULL,'1989-11-13 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (184,35,'2014-06-02 17:54:38','2014-06-02 17:54:38','KIUTIL','KIUTIL ACOLCHOADOS','TRENTIM','5135621182',0,'89089213000145',NULL,'','',NULL,NULL,'213003669',NULL,'2010-06-17 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (185,36,'2014-06-02 17:54:38','2014-06-02 17:54:38','BOTTERO','CALCADOS BOTTERO LTDA','EMILIO    42-9902-0156','4130789797',0,'90312133000196',NULL,'515233333','515231666',NULL,NULL,'2410003855',NULL,'2003-04-04 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (186,37,'2014-06-02 17:54:38','2014-06-02 17:54:38','SMILE/PUFF','SMILE IND.E COM.DE BORDADOS LTDA ME','JULIO 41-3088-2885  DIHOFFMANN','4199042885',0,'02773081000198',NULL,'0473402266','',NULL,NULL,'253764912',NULL,'1997-07-29 00:00:00','JULIO1040@BRTURBO.CO');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (187,38,'2014-06-02 17:54:38','2014-06-02 17:54:38','TRIFIL','INDUSTRIA DE MEIAS SCALINA LTDA','LEANDRO   9986-5018','4130792802',0,'61149886000124',NULL,'413392714','1164333211',NULL,NULL,'336128348110',NULL,'2003-04-25 00:00:00','11-3598-2000');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (188,39,'2014-06-02 17:54:38','2014-06-02 17:54:38','CLICK','RAUL ALBINO E CIA. LTDA','ODIR 972-7593  CELSO 224-2080','2236993',0,'47254586000184',NULL,'01532841411','0800120411',NULL,NULL,'265000034115',NULL,'1989-11-14 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (189,40,'2014-06-02 17:54:38','2014-06-02 17:54:38','TOPAZIA','J F DO NASCIMENTO ME','ARI','',0,'',NULL,'1637052446','',NULL,NULL,'',NULL,'2011-07-07 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (190,41,'2014-06-02 17:54:38','2014-06-02 17:54:38','RAINHA','ALPARGATAS RAINHA','REJANE   041-9161-8090','4191286070',0,'61079117014580',NULL,'08007070533','001934715905',NULL,NULL,'160347416',NULL,'1997-08-08 00:00:00','ALVARO GER REG');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (191,42,'2014-06-02 17:54:38','2014-06-02 17:54:38','LUPO','LUPO S.A.','VALDEVINO 043-9971-1671','0432551701',0,'43948405000169',NULL,'08007078100','0162353005',NULL,NULL,'181040356111',NULL,'1989-11-14 00:00:00','016-235-3000');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (192,43,'2014-06-02 17:54:38','2014-06-02 17:54:38','DIANA','MALHARIA DIANA S.A.','PEDRO   41-8852-1584','0412469511',0,'86375789000180',NULL,'0472812600','',NULL,NULL,'250044838',NULL,'1989-11-14 00:00:00','MARCIA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (193,44,'2014-06-02 17:54:38','2014-06-02 17:54:38','ADIDAS','REPRESENT. CALCADOS FRANCISCON','REGINALDO','',0,'',NULL,'01433722162','1433722162',NULL,NULL,'',NULL,'2012-05-11 00:00:00','REGINALDO');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (194,45,'2014-06-02 17:54:38','2014-06-02 17:54:38','TANUS GASTIN','TANUS GASTIN IND TEXTIL LTDA','MARCOS-43-99388060','',0,'61205944000190',NULL,'1127968699','',NULL,NULL,'103633724117',NULL,'1996-07-29 00:00:00','NEVES  43-33385207');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (195,46,'2014-06-02 17:54:38','2014-06-02 17:54:38','DOHLER','DOHLER S.A.','LUCIMARA 41-9600-8257','4132721973',0,'84683408000103',NULL,'04734411666','04734411777',NULL,NULL,'250055260',NULL,'1989-11-14 00:00:00','LUCIMARA 4196008257');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (196,47,'2014-06-02 17:54:38','2014-06-02 17:54:38','IONE','IONE ENXOVAIS','VILMAR     9972-0996','',0,'',NULL,'1633423293','',NULL,NULL,'',NULL,'2010-09-09 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (197,48,'2014-06-02 17:54:38','2014-06-02 17:54:38','BEIJA FLOR','BEIJA FLOR','ADIR PAZ','04196039646',0,'',NULL,'','04132767492',NULL,NULL,'',NULL,'2012-10-02 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (198,49,'2014-06-02 17:54:38','2014-06-02 17:54:38','OKEAN','CALCADOS ANIGER NORDESTE LTDA','PAULO RICARDO 41 99209986','4130828332',0,'01506990000440',NULL,'5121259900','5121259930',NULL,NULL,'066954410',NULL,'2009-10-26 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (199,50,'2014-06-02 17:54:38','2014-06-02 17:54:38','GRENDENE','GRENDENE CALCADOS SA','AURELIO  ALBSI@GRENDENE.COM.BR','4199763775',0,'72273196000107',NULL,'0854851033','0854851033',NULL,NULL,'069161135',NULL,'1999-03-08 00:00:00','ESC CTBA 41 32243332');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (200,51,'2014-06-02 17:54:38','2014-06-02 17:54:38','TOK-SUL','TOK-SUL CONFECCOES LTDA','EMERSON 041-7812-2026','4734111141',0,'00563028000137',NULL,'475251141','',NULL,NULL,'253068185',NULL,'1999-11-22 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (201,52,'2014-06-02 17:54:38','2014-06-02 17:54:38','BUETTNER','BUETTNER S.A. INDUSTRIA E COMERCIO','OSNI MAFRA         9975-4123','0413243315',0,'82981812000120',NULL,'08000473033','',NULL,NULL,'250176777',NULL,'1989-11-14 00:00:00','LURDETI/ 47-355-4000');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (202,53,'2014-06-02 17:54:38','2014-06-02 17:54:38','ALTENBURG','FAB.DE ACOLC. ALTENBURG LTDA.','WILSON 9954-0222','2261349',0,'75293662000104',NULL,'0473311500','0473340647',NULL,NULL,'251141020',NULL,'1989-11-14 00:00:00','DEP.VENDAS:334-1351');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (203,54,'2014-06-02 17:54:38','2014-06-02 17:54:38','AMERICA','J. SHAYEB & CIA. LTDA.','CAIO  9983-7099','0412726287',0,'44996072000106',NULL,'01431035000','01432032999',NULL,NULL,'209003858117',NULL,'1989-11-14 00:00:00','CAIO  043-3325-8754');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (204,55,'2014-06-02 17:54:38','2014-06-02 17:54:38','TAMPINHA','KAKAO CONF. E COM. LTDA','ROBSON    41-9966-6307','0412363201',0,'55608202000102',NULL,'01138313533','01136418493',NULL,NULL,'111455102112',NULL,'2000-02-15 00:00:00','SAC 0800-1604-15');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (205,56,'2014-06-02 17:54:38','2014-06-02 17:54:38','CIA BASIC','CIA BASIC','VITOR','',0,'',NULL,'','',NULL,NULL,'',NULL,'2011-06-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (206,57,'2014-06-02 17:54:38','2014-06-02 17:54:38','ROLIDAN','ROLIDAN','NELSON TRENTIN 9972-6959','32249302',0,'3388860100018',NULL,'3732424352','',NULL,NULL,'86150792000180',NULL,'2004-02-17 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (207,58,'2014-06-02 17:54:38','2014-06-02 17:54:38','REALCE','REALCE','','04199768743',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-06-05 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (208,59,'2014-06-02 17:54:38','2014-06-02 17:54:38','VIA MARTE','CALCADOS MARTE LTDA','JOAO CARLOS','4499726709',0,'2940000241',NULL,'5135658000','',NULL,NULL,'88887021000111',NULL,'2009-10-27 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (209,60,'2014-06-02 17:54:38','2014-06-02 17:54:38','LE SPORTIFF','LE SPORTIFF CALCADOS','ARI 9971-0953','4232264312',0,'04306178000116',NULL,'1637253511','',NULL,NULL,'310350248114',NULL,'2006-05-16 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (210,61,'2014-06-02 17:54:38','2014-06-02 17:54:38','TRAVENA','TN BOLSA','NEI','422273894',0,'',NULL,'4432461773','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (211,62,'2014-06-02 17:54:38','2014-06-02 17:54:38','DERMIWIL','DERMIWIL INDUSTRIA PLASTICA LTDA','ASHER       41-8412-1775','4132963195',0,'60643988000139',NULL,'1127979797','1126925572',NULL,NULL,'104661340118',NULL,'2005-12-21 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (212,63,'2014-06-02 17:54:38','2014-06-02 17:54:38','VILEJACK','VILEJACK JEANS','DAVI ROMER   43-9955-6688','08002836699',0,'12927088000170',NULL,'08002836699','',NULL,NULL,'082762228',NULL,'2006-07-13 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (213,64,'2014-06-02 17:54:38','2014-06-02 17:54:38','BEIRA RIO','CALCADOS BEIRA RIO LTDA','ANA PAULA 41-9116-0997','',0,'88379771000506',NULL,'0515948888','0515842221',NULL,NULL,'1610020593',NULL,'1993-11-09 00:00:00','43-337-3884 145');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (214,65,'2014-06-02 17:54:38','2014-06-02 17:54:38','BILU NEW CONFECCOES','BILU NEW CONFECCOES','','4291095098',0,'',NULL,'1120819053','',NULL,NULL,'',NULL,'2011-07-14 00:00:00','FORNECEDOR');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (215,66,'2014-06-02 17:54:38','2014-06-02 17:54:38','RANER','RANER IND. TEXTIL LTDA','GABRIEL/TITO 9660-6213','041132579386',0,'52431426000101',NULL,'01934579600','',NULL,NULL,'606028055112',NULL,'2000-05-07 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (216,67,'2014-06-02 17:54:38','2014-06-02 17:54:38','MARATTI','BHYA MARATTI CONFECCOES','VITOR FORE','',0,'09096998000144',NULL,'4935443452','',NULL,NULL,'255496460',NULL,'2010-05-10 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (217,68,'2014-06-02 17:54:38','2014-06-02 17:54:38','ELITE','CONFECCOES ELITE','JUNIOR','1692536170',0,'',NULL,'1633831166','',NULL,NULL,'',NULL,'2011-03-17 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (218,69,'2014-06-02 17:54:38','2014-06-02 17:54:38','KINCCAL','KINCCAL','LOURENCO','4191571577',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-08-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (219,70,'2014-06-02 17:54:38','2014-06-02 17:54:38','MACADAMIA','MACADAMIA','FONSECA','4191545995',0,'12861587000102',NULL,'001132511582','',NULL,NULL,'147644191112',NULL,'2011-11-01 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (220,71,'2014-06-02 17:54:38','2014-06-02 17:54:38','BICAL','BIRIGUI CALCADOS IND E COM LTDA','LOURENCO 41-9157-1577','4130352998',0,'45377272000143',NULL,'0186432121','0186424130',NULL,NULL,'214000923118',NULL,'1996-07-15 00:00:00','EMAIL LOURECOF@GMAIL');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (221,72,'2014-06-02 17:54:38','2014-06-02 17:54:38','ALVIN','EDNALDO BALDUINO DE MIRANDA','FONSECA','4191545995',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-04-08 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (222,73,'2014-06-02 17:54:38','2014-06-02 17:54:38','ANA AGUIAR','ANA AGUIAR','NEY','4499613628',0,'',NULL,'4430347290','',NULL,NULL,'',NULL,'2014-02-20 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (223,74,'2014-06-02 17:54:38','2014-06-02 17:54:38','ALL STAR','ALL STAR','RINALDO  43-9101-1128','04384370205',0,'',NULL,'0515941566','05945852',NULL,NULL,'',NULL,'1993-02-10 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (224,75,'2014-06-02 17:54:38','2014-06-02 17:54:38','PINOKIO','PINOKIO','ARI  99710953','32264312',0,'',NULL,'','',NULL,NULL,'',NULL,'2008-05-21 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (225,76,'2014-06-02 17:54:38','2014-06-02 17:54:38','PICCADILLY','PICCADILLY','SIDNEY','4491449745',0,'97755177001020',NULL,'4432552126','5135499600',NULL,NULL,'2440035089',NULL,'2007-08-02 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (226,77,'2014-06-02 17:54:38','2014-06-02 17:54:38','MARINER','MARINER IND COM ART COURO','? NUNES REPRESENTACOES','4532528066',0,'545881400000151',NULL,'1637122222','',NULL,NULL,'310077764110',NULL,'2010-09-27 00:00:00','45-8811-0500 ROBERTO');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (227,78,'2014-06-02 17:54:38','2014-06-02 17:54:38','PERLATTO CALCADOS','PERLATTO','RONALDO   45-9945-1477','4499623453',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-08-04 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (228,79,'2014-06-02 17:54:38','2014-06-02 17:54:38','ORTOPASSO','ORTOPASSO','RONALDO     45-9945-1477','4499623453',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-04-08 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (229,80,'2014-06-02 17:54:38','2014-06-02 17:54:38','KIRTEENS','KIRTEENS','RONALDO   45-9945-1477','4499623453',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-04-08 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (230,81,'2014-06-02 17:54:38','2014-06-02 17:54:38','ITAPUA','CALCADOS ITAPUA','MARCELO/MARCIO','4188659898',0,'',NULL,'2820019007','',NULL,NULL,'',NULL,'2007-05-28 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (231,82,'2014-06-02 17:54:38','2014-06-02 17:54:38','LE FLEX','LE FLEX','ARI 9971-0953','4232264312',0,'',NULL,'','',NULL,NULL,'',NULL,'2008-02-08 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (232,83,'2014-06-02 17:54:38','2014-06-02 17:54:38','MOSAICO','MOSAICO','BETO','',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-10-23 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (233,84,'2014-06-02 17:54:38','2014-06-02 17:54:38','MOLECA','CALCADOS BEIRA RIO S.A.','ROGERIO   42-9997-0044','4299970044',0,'88379771003017',NULL,'4132321788','35842207',NULL,NULL,'1310088737',NULL,'2006-08-25 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (234,85,'2014-06-02 17:54:38','2014-06-02 17:54:38','DAKAR/PLANETA BRASIL','LANG FORD IMP. E COM. INTERN.LTD','AUGUSTINHO/RIBAS 41-3277-21449','04132721828',0,'',NULL,'1166941855','',NULL,NULL,'',NULL,'1999-06-28 00:00:00','CEL AGOST.8408-7791');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (235,86,'2014-06-02 17:54:38','2014-06-02 17:54:38','GANGSTER','GANGSTER','BETO','04299885759',0,'',NULL,'','',NULL,NULL,'',NULL,'2011-08-09 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (236,87,'2014-06-02 17:54:38','2014-06-02 17:54:38','GILZER','GILZER CONFECCOES LTDA','JUNIOR   42-9964-6744','4733553870',0,'00588427000152',NULL,'4733500234','4733553870',NULL,NULL,'253103487',NULL,'2014-06-02 17:54:37','JUNIOR');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (237,88,'2014-06-02 17:54:38','2014-06-02 17:54:38','DEL RIO','DEL RIO','EUCLIDES','',0,'',NULL,'','',NULL,NULL,'',NULL,'2008-05-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (238,89,'2014-06-02 17:54:38','2014-06-02 17:54:38','DEE MARKA','DEE MARKA','JAIR','4499656393',0,'',NULL,'4434462218','',NULL,NULL,'',NULL,'2011-08-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (239,90,'2014-06-02 17:54:38','2014-06-02 17:54:38','MOCA BONITA','MOCA BONITA','REGINALDO','4391090470',0,'',NULL,'1497834492','',NULL,NULL,'',NULL,'2011-08-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (240,91,'2014-06-02 17:54:38','2014-06-02 17:54:38','OBER','OBER','VAGNER  PIMENTEL','4196565050',0,'',NULL,'','',NULL,NULL,'',NULL,'2008-04-08 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (241,92,'2014-06-02 17:54:38','2014-06-02 17:54:38','ZORBA','ZORBA TEXTIL S.A.','ALEXANDRE 41-9973-3077','4133329285',0,'60393824000109',NULL,'01136118199','01136117773',NULL,NULL,'101164736118',NULL,'1989-11-16 00:00:00','WALTER 0800-55-66-69');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (242,93,'2014-06-02 17:54:38','2014-06-02 17:54:38','WINSTON','WINSTON INDUSTRIA DE MEIAS','JONAS  44-88045823','5433761734',0,'45383981000131',NULL,'1836422176','1836422176',NULL,NULL,'214001006117',NULL,'2007-08-02 00:00:00','KANAWATE 30280139');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (243,94,'2014-06-02 17:54:38','2014-06-02 17:54:38','ABRANGE','ABRANGE IND E COM CONF LTDA','ANTONIO','99953030',0,'07807234000194',NULL,'004733778500','',NULL,NULL,'255119925',NULL,'2013-03-08 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (244,95,'2014-06-02 17:54:38','2014-06-02 17:54:38','GOOF','GOOF','ANTONIO    3025-3010','99953030',0,'10631023000158',NULL,'4331582117','',NULL,NULL,'9048542988',NULL,'2009-06-25 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (245,96,'2014-06-02 17:54:38','2014-06-02 17:54:38','VELINHO','(VLH) VELHINHO','PAULO SERGIO 16 9999-4555','4499652342',0,'',NULL,'','',NULL,NULL,'',NULL,'2011-08-18 00:00:00','ESC 16 3720-4555');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (246,97,'2014-06-02 17:54:38','2014-06-02 17:54:38','SULFABRIL','SULFABRIL  S.A.','HILDO      225-4350/3028-4350','4299714793',0,'82636911000174',NULL,'0473311000','0473311010',NULL,NULL,'250010828',NULL,'1989-11-16 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (247,98,'2014-06-02 17:54:38','2014-06-02 17:54:38','VIZZANO','CALCADOS BEIRA RIO SA','GLAUCIO 41-9976-0170','4135273728',0,'88379771000263',NULL,'','',NULL,NULL,'1610036317',NULL,'2003-04-16 00:00:00','41-232-1788 ANA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (248,99,'2014-06-02 17:54:38','2014-06-02 17:54:38','CORPO ACAO','CORPO E ACAO','ABNER CAVALINI 43-8829-7380','4333365217',0,'',NULL,'4333365217','',NULL,NULL,'',NULL,'2003-05-08 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (249,100,'2014-06-02 17:54:38','2014-06-02 17:54:38','OLIVEIRA','ENXOVAIS OLIVEIRA','GABRIEL41-9660-6213','4196606213',0,'01145562000196',NULL,'1633416256','',NULL,NULL,'344049080110',NULL,'2009-09-21 00:00:00','GABRIEL');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (250,101,'2014-06-02 17:54:38','2014-06-02 17:54:38','TERE','TERE','TERE          42-9931-0970','4234362517',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-02-17 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (251,102,'2014-06-02 17:54:38','2014-06-02 17:54:38','PHAEL','ALVES & RIBEIRO LTDA','SILVESTRE   43-9138-0312','4330335944',0,'04465681000114',NULL,'0672723360','',NULL,NULL,'283181818',NULL,'1999-05-21 00:00:00','0800-55-5955');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (252,103,'2014-06-02 17:54:38','2014-06-02 17:54:38','AMAROK','AMAROK','ARI   JULIANO','4299283318',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-02-29 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (253,104,'2014-06-02 17:54:39','2014-06-02 17:54:39','PLANET','D\'JEAN IND CALCADOS LTDA','PAULO SERGIO','',0,'975311970000127',NULL,'','',NULL,NULL,'0018030420005',NULL,'2011-08-18 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (254,105,'2014-06-02 17:54:39','2014-06-02 17:54:39','CAUTION','VIRONDA CONFECCOES LTDA','JAIME      45-9972-1793','00452235490',0,'58517798000123',NULL,'01934598100','',NULL,NULL,'606037579114',NULL,'2001-06-04 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (255,106,'2014-06-02 17:54:39','2014-06-02 17:54:39','INCOMETAL','INCOMETAL S/A INDUSTRIA E COMERCIO','MAURICIO 9971-7280','4232273300',0,'60851656000140',NULL,'1156421234','1156412639',NULL,NULL,'105954262114',NULL,'2005-10-31 00:00:00','08007021233 VIVIANE');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (256,107,'2014-06-02 17:54:39','2014-06-02 17:54:39','CORTINARO','CORTINARO ACESSORIOS P CORTINAS LTD','MARLI','04332554233',0,'03591434000100',NULL,'04332554233','',NULL,NULL,'9020369970',NULL,'1989-11-16 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (257,108,'2014-06-02 17:54:39','2014-06-02 17:54:39','MARIA EMILIA','MARIA EMILIA','PAULO RICARDO','04299705131',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-03-09 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (258,109,'2014-06-02 17:54:39','2014-06-02 17:54:39','JERSEY BRAS','JERSEY BRAS','GERALDO RONALDO 4498402301','4499645044',0,'60773041000142',NULL,'1155942440','',NULL,NULL,'336886465112',NULL,'2007-08-16 00:00:00','ULAN 01934581169');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (259,110,'2014-06-02 17:54:39','2014-06-02 17:54:39','CORTI LESTER','TEXTIL CORTI LESTER SA','GILBERTO04130497771','04198545266',0,'61730669000123',NULL,'','01146163210',NULL,NULL,'278058969112',NULL,'2001-08-14 00:00:00','11-39568400');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (260,111,'2014-06-02 17:54:39','2014-06-02 17:54:39','GUMZ','GUMZ','DARIO','0473700788',0,'',NULL,'0473707088','0473707088',NULL,NULL,'',NULL,'2014-06-02 17:54:37','JANE');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (261,112,'2014-06-02 17:54:39','2014-06-02 17:54:39','RITCHELLY','RITCHELLY IND E COM DE CORTINAS LTD','WILSON 43-99231207','4330153619',0,'79345161000177',NULL,'0432763266','',NULL,NULL,'6280020611',NULL,'2002-03-28 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (262,113,'2014-06-02 17:54:39','2014-06-02 17:54:39','CORTTEX','CORTTEX INDUSTRIA TEXTIL LTDA','TANAKA 44-84090617','04432282046',0,'48606503000565',NULL,'0800551710','',NULL,NULL,'283112581',NULL,'2002-09-13 00:00:00','19-21097700');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (263,114,'2014-06-02 17:54:39','2014-06-02 17:54:39','JAKI MALHAS','JAKI','SILVAL LOSS    45-9107-3697','452245814',0,'00911366000112',NULL,'4733320834','4733322736',NULL,NULL,'253217970',NULL,'2000-08-22 00:00:00','JAQUELINE');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (264,115,'2014-06-02 17:54:39','2014-06-02 17:54:39','CRUZ DE MALTA','CRUZ DE MALTA','ELOI   99662540','4232235245',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (265,116,'2014-06-02 17:54:39','2014-06-02 17:54:39','DE LOURDES','DE LOURDES CONFECCOES LTDA','LAUDEMIR 43-99857691','4334222733',0,'04467670000173',NULL,'4432701158','',NULL,NULL,'9023614305',NULL,'2008-06-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (266,117,'2014-06-02 17:54:39','2014-06-02 17:54:39','MURYS','M. PERROTISSE E CIA LTDA','ANDRE','04599714872',0,'04256196000130',NULL,'04432451371','',NULL,NULL,'9023083180',NULL,'2012-03-16 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (267,118,'2014-06-02 17:54:39','2014-06-02 17:54:39','TOPLAY','TOPLAY SPORT ROUPAS LTDA','ALEXANDRE (41)99723115','4137797576',0,'78880788000165',NULL,'04834617244','',NULL,NULL,'251199754',NULL,'1998-10-02 00:00:00','0800-7027244');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (268,119,'2014-06-02 17:54:39','2014-06-02 17:54:39','MALAS CRUZEIRO','MALAS CRUZEIRO','MARCOS DALPONTE','4299619776',0,'',NULL,'1124360177','',NULL,NULL,'',NULL,'2008-07-21 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (269,120,'2014-06-02 17:54:39','2014-06-02 17:54:39','MALWEE MALHAS','MALHAS MALWEE S.A.','ALCIDES  041-99876990','0412326844',0,'84429737000114',NULL,'0473727200','0473727300',NULL,NULL,'250264722',NULL,'1989-11-17 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (270,121,'2014-06-02 17:54:39','2014-06-02 17:54:39','KAIWAA','KAIWAA','EDUARDO 14-99632-3970 VIVO','1437115725',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-06-19 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (271,122,'2014-06-02 17:54:39','2014-06-02 17:54:39','EMPORIO','EMPORIO JEANS','TUNICO','',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-03-15 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (272,123,'2014-06-02 17:54:39','2014-06-02 17:54:39','MELTEX','FIVE BROS','DONATO -RODRIGO -LEANDRO','4199737963',0,'',NULL,'1122029800','4184166796',NULL,NULL,'',NULL,'2005-10-28 00:00:00','RENATO  4130159383 E');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (273,124,'2014-06-02 17:54:39','2014-06-02 17:54:39','FREE WILLY','BENIVALDO FRANCISCO SOUZA FRANCA ME','JULIANO','',0,'60230570000108',NULL,'01637030367','',NULL,NULL,'310117349112',NULL,'2013-03-08 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (274,125,'2014-06-02 17:54:39','2014-06-02 17:54:39','RED NOSE PE','RED NOSE','ROBERTO','4199697423',0,'',NULL,'','',NULL,NULL,'',NULL,'2008-09-19 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (275,126,'2014-06-02 17:54:39','2014-06-02 17:54:39','BLAZIUS','BLAZIUS','ANDRE','04599714872',0,'02141751000153',NULL,'04530553523','452527580',NULL,NULL,'9014317101',NULL,'1997-05-15 00:00:00','COD 5302 EVORI');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (276,127,'2014-06-02 17:54:39','2014-06-02 17:54:39','ULAM','TEXTIL ULAM LTDA','GERALDO449964-5044','04432227657',0,'49061526000170',NULL,'01934581169','',NULL,NULL,'606034348117',NULL,'2000-10-25 00:00:00','3458-1715');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (277,128,'2014-06-02 17:54:39','2014-06-02 17:54:39','TRASSUS','TRASSUS TRICOT','PEDRO   (54) 9935-4518','5432232365',0,'04275816000189',NULL,'5432813604','',NULL,NULL,'0840024169',NULL,'2011-02-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (278,129,'2014-06-02 17:54:39','2014-06-02 17:54:39','SAMVILLE','SAMVILLE','PEDRO        54-9935-4518','5432232365',0,'90077595000176',NULL,'54354518','',NULL,NULL,'0290136512',NULL,'2008-02-08 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (279,130,'2014-06-02 17:54:39','2014-06-02 17:54:39','HELTER','CONFECCOES HELTER LTDA','VILMAR   046-224-6018','04699751479',0,'80783160000193',NULL,'0465244864','',NULL,NULL,'3210079962',NULL,'1996-12-10 00:00:00','BANY-45-3265252 CINM');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (280,131,'2014-06-02 17:54:39','2014-06-02 17:54:39','TOQUE INTIMO','TOQUE INTIMO','VILMAR','',0,'02162420000108',NULL,'04133291432','',NULL,NULL,'253544181',NULL,'2013-03-08 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (281,132,'2014-06-02 17:54:39','2014-06-02 17:54:39','TOPICO','TOPICO','EMERSON','',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-03-08 00:00:00','********************');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (282,133,'2014-06-02 17:54:39','2014-06-02 17:54:39','BRUTEXTIL','BRUTEXTIL IND COM LTDA','OSNI MAFRA 419975-4123','4133424886',0,'82156290000121',NULL,'4733518327','',NULL,NULL,'252078330',NULL,'2011-03-16 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (283,134,'2014-06-02 17:54:39','2014-06-02 17:54:39','MICROFLOCK TEXTIL','MICROFLOCK TEXTIL BRAZIL','IVAN','4199043307',0,'09376533000147',NULL,'5137481350','',NULL,NULL,'0720124565',NULL,'2013-06-07 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (284,135,'2014-06-02 17:54:39','2014-06-02 17:54:39','APPEL','INDUSTRIAL APPEL LTDA.','MAURO 91346753-041','41',0,'83100743000160',NULL,'0473500555','0473501324',NULL,NULL,'250067161',NULL,'1989-11-17 00:00:00','VENDAS 0800-47-0555');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (285,136,'2014-06-02 17:54:39','2014-06-02 17:54:39','BRANYL','BRANYL COM E IND TEXTIL LTDA','MARCOS-NEVES REPR.43-99388060','04333385207',0,'43631191000100',NULL,'01934928400','01934915002',NULL,NULL,'253010554119',NULL,'1990-03-26 00:00:00','RICARDO 4396981456');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (286,137,'2014-06-02 17:54:39','2014-06-02 17:54:39','CORTTEX CONF.','FATEX IND.COM. IMP E EXP LTDA','EDINO  4199915550','4132724719',0,'07280722000196',NULL,'6735210180','',NULL,NULL,'2833587690',NULL,'2013-06-07 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (287,138,'2014-06-02 17:54:39','2014-06-02 17:54:39','FIORELA','FIORELA','PC','1699994555',0,'',NULL,'','',NULL,NULL,'',NULL,'2009-07-06 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (288,139,'2014-06-02 17:54:39','2014-06-02 17:54:39','FREE POWER/BAD BOY','FREE POWER/BAD BOY','PC/CAIO','01699994555',0,'310355987119',NULL,'4399837099','',NULL,NULL,'01998213000117',NULL,'2009-07-06 00:00:00','BAD BOY CAIO');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (289,140,'2014-06-02 17:54:39','2014-06-02 17:54:39','IDEAL','RUDIMAR ART P/CHUVA,SOL E PRAIA LTD','RODOLFO','0412324529',0,'78333291000127',NULL,'0412324529','',NULL,NULL,'1014593060',NULL,'1994-11-19 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (290,141,'2014-06-02 17:54:39','2014-06-02 17:54:39','BREDA','ALICIO BREDA & CIA LTDA','JOSE FALCAO 9187-8308','0412571339',0,'60840956000395',NULL,'01126721699','01166717440',NULL,NULL,'108267805117',NULL,'1997-05-13 00:00:00','011-227-9606');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (291,142,'2014-06-02 17:54:39','2014-06-02 17:54:39','LUNENDER','LUNENDER S.A.','JAIME 42-9964-7702 TIM','4433543048',0,'75552133000170',NULL,'04733737000','',NULL,NULL,'250826755',NULL,'1995-11-10 00:00:00','EMERSON 43-8807-8050');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (292,143,'2014-06-02 17:54:39','2014-06-02 17:54:39','TRIBAL','TRIBAL','EMERSON','',0,'',NULL,'','',NULL,NULL,'',NULL,'1013-06-12 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (293,144,'2014-06-02 17:54:39','2014-06-02 17:54:39','VIA SUL','CRIACOES MAUBER IND E COM DE CALCAD','RAFAEL','4291321171',0,'48364806000195',NULL,'1433721044','',NULL,NULL,'612009323115',NULL,'2013-03-19 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (294,145,'2014-06-02 17:54:39','2014-06-02 17:54:39','DANZER','DANZER','ARI LE SPORTIF','',0,'',NULL,'','',NULL,NULL,'',NULL,'2008-10-06 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (295,146,'2014-06-02 17:54:39','2014-06-02 17:54:39','USAFLEX','STEBRAS CALCADOS LTDA','VALDECIR 43 9976-9631','4330292102',0,'869009258000104',NULL,'5135498100','4391919900',NULL,NULL,'1610036686',NULL,'2008-09-16 00:00:00','CP 28');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (296,147,'2014-06-02 17:54:39','2014-06-02 17:54:39','GUARATINGUETA','CIA FIACAO E TEC.GUARATINGUETA','MAURO RIBAS 9134-6753','004133276628',0,'48540447000503',NULL,'001160970111','001160966733',NULL,NULL,'332000045113',NULL,'1993-03-25 00:00:00','VENDAS@GUARATINGUETA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (297,148,'2014-06-02 17:54:39','2014-06-02 17:54:39','TOPPER','VILA RICA CALCADOS','RODRIGO   14-8148-2376','',0,'',NULL,'4136756446','',NULL,NULL,'',NULL,'1990-08-20 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (298,149,'2014-06-02 17:54:39','2014-06-02 17:54:39','FILA','DASS NORDESTE CALCADOS - FILA','GEAN FLORES       41-3387-4513','04196995007',0,'01287588000330',NULL,'4933343479','4130244513',NULL,NULL,'54313897',NULL,'2013-05-09 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (299,150,'2014-06-02 17:54:39','2014-06-02 17:54:39','UMBRO','UMBRO','FONTOURA     41 97016400','',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-12-05 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (300,151,'2014-06-02 17:54:39','2014-06-02 17:54:39','TRYON','TRYON','FONTOURA-EJFONTOURA@HOTMAIL.CO','4197016400',0,'01287588000250',NULL,'4130828332','4933343473',NULL,NULL,'062805967',NULL,'2008-06-02 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (301,152,'2014-06-02 17:54:39','2014-06-02 17:54:39','LEPPER','COMPANHIA FABRIL LEPPER','DIOGO 44-9925-8584','4432228456',0,'84683887000150',NULL,'04734413111','04734334781',NULL,NULL,'250005131',NULL,'1992-08-25 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (302,153,'2014-06-02 17:54:39','2014-06-02 17:54:39','BETON','FLAVILINE CONFECCOES LTDA','DANILO 44-99720373','04432335716',0,'80365521000181',NULL,'04432329595','04432325467',NULL,NULL,'7010284222',NULL,'1994-12-16 00:00:00','VENDAS@CUECASBETON.');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (303,154,'2014-06-02 17:54:39','2014-06-02 17:54:39','ASIATEX','G.C. TEXTIL IND. COM. DE TAPETES','ZACARIAS','4132240972',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-06-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (304,155,'2014-06-02 17:54:39','2014-06-02 17:54:39','MICHEL BENFIO TEXTIL','MICHEL BENFIO TEXTIL','NELSON','32240251',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-06-12 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (305,156,'2014-06-02 17:54:39','2014-06-02 17:54:39','LALITEX','A.L.S.A. TEXTIL LTDA','WALDIR REGADAS 9976-8743','04132298163',0,'02012319000162',NULL,'01138193077','',NULL,NULL,'606084354113',NULL,'1997-09-10 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (306,157,'2014-06-02 17:54:39','2014-06-02 17:54:39','CENTRAL','CENTRAL INDL. E COML. TEXTIL LTDA','KAMMEL','',0,'04460240000120',NULL,'01132294344','32294344',NULL,NULL,'116140980117',NULL,'1994-09-26 00:00:00','KAMMEL');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (307,158,'2014-06-02 17:54:39','2014-06-02 17:54:39','DI HOFFMANN','DI HOFFMANN','JULIO','0415691969',0,'',NULL,'04332321490','',NULL,NULL,'77498442000134',NULL,'2002-09-18 00:00:00','ANGELICA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (308,159,'2014-06-02 17:54:39','2014-06-02 17:54:39','KOHMAR','KOHMAR','MARCELO','',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-06-28 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (309,160,'2014-06-02 17:54:39','2014-06-02 17:54:39','JOAO NOGUEIRA','COTONIFICIO JOAO NOGUEIRA SA','VOLNEI   (CIATEX=35-3622-0677)','0412429293',0,'12268389000210',NULL,'0822711337','0822711311',NULL,NULL,'240060989',NULL,'1999-01-13 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (310,161,'2014-06-02 17:54:39','2014-06-02 17:54:39','LETICIA CAROLINA','CLAUDIO ROBERTO MONTEIRO ME','ROBSON DAKOTA','4299199723',0,'',NULL,'1637032505','',NULL,NULL,'310129913110',NULL,'2009-08-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (311,162,'2014-06-02 17:54:39','2014-06-02 17:54:39','DAKOTA','DAKOTA NORDESTE SA','ROBERSON  42-9919-9723','2232959',0,'00465442000134',NULL,'884110350','884110026',NULL,NULL,'2010302423',NULL,'2003-08-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (312,163,'2014-06-02 17:54:39','2014-06-02 17:54:39','MOLEQUINHA','CALCADOS BEIRA RIO S/A','ROGERIO    42-9971-7041 TIM','',0,'',NULL,'04132321788','',NULL,NULL,'',NULL,'2012-11-27 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (313,164,'2014-06-02 17:54:39','2014-06-02 17:54:39','GARDENIA','GARDENIA','ARI','4299710953',0,'',NULL,'3732262949','',NULL,NULL,'',NULL,'2012-10-24 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (314,165,'2014-06-02 17:54:39','2014-06-02 17:54:39','ANA VITORIA','ANA VITORIA CALCADOS','ARI','4232264312',0,'',NULL,'5135466300','',NULL,NULL,'',NULL,'2012-12-04 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (315,167,'2014-06-02 17:54:39','2014-06-02 17:54:39','REEBOK','REEBOK','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-12-01 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (316,168,'2014-06-02 17:54:39','2014-06-02 17:54:39','VELUT','VELUT','PAULO RICARDO','4299705131',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-08-12 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (317,169,'2014-06-02 17:54:39','2014-06-02 17:54:39','ROODOK','MARC ELLSSE IND COM CALCADOS LTDA','ARI','',0,'67582676000101',NULL,'1836424001','',NULL,NULL,'214044381110',NULL,'2013-04-19 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (318,170,'2014-06-02 17:54:39','2014-06-02 17:54:39','ROMER','IND.DE CONF.ROMER SPORT LTDA','DAVI 43-3016-1103','4399556688',0,'79182416000128',NULL,'4132486907','41',NULL,NULL,'1010222504',NULL,'1992-10-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (319,171,'2014-06-02 17:54:39','2014-06-02 17:54:39','ZEE RUCCI','ICONE SC COM IMP EXP LTDA','ROBERTO      41-9601-0200','4733873236',0,'10212986000117',NULL,'','',NULL,NULL,'255659687',NULL,'2013-05-20 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (320,172,'2014-06-02 17:54:39','2014-06-02 17:54:39','CIA DO MILENIO','LUCIANO CARLOS BONEZZI E CIA LTDA','ISRAEL','4199869162',0,'03106308000104',NULL,'4735462002','',NULL,NULL,'253769884',NULL,'2013-09-20 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (321,174,'2014-06-02 17:54:39','2014-06-02 17:54:39','CAJADAN','CAJADAN','ELDEMAR','4299488508',0,'72170392000147',NULL,'4734360355','',NULL,NULL,'252671740',NULL,'2013-03-07 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (322,175,'2014-06-02 17:54:39','2014-06-02 17:54:39','FABIANA','FABIANA','ANTONIO','99768055',0,'',NULL,'32279018','',NULL,NULL,'',NULL,'1999-04-01 00:00:00','ANTONIO');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (323,176,'2014-06-02 17:54:39','2014-06-02 17:54:39','TRENZINHO','TRENZINHO','KANAWATE','',0,'',NULL,'','',NULL,NULL,'',NULL,'2004-04-05 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (324,177,'2014-06-02 17:54:39','2014-06-02 17:54:39','PARAHYBA','TECELAGEM PARAHYBA S.A.','VILMAR 9972-0996','0112298808',0,'01032552000144',NULL,'0815351090','0815351011',NULL,NULL,'18165002197110',NULL,'1989-11-18 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (325,179,'2014-06-02 17:54:39','2014-06-02 17:54:39','LYNEL','LYNEL INDUSTRIA TEXTIL LTDA','NELSON','2249302',0,'75797118000191',NULL,'0476532159','',NULL,NULL,'250827778',NULL,'1999-03-24 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (326,180,'2014-06-02 17:54:39','2014-06-02 17:54:39','DALLA ROSA','GUTTI`S INDUSTRIA E COM.CONFCCAO','TRENTIN       9972-6959','2249302',0,'77152007000153',NULL,'476422891','',NULL,NULL,'1260061875',NULL,'2001-11-21 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (327,181,'2014-06-02 17:54:39','2014-06-02 17:54:39','DIG DOG','GISELKE MALHAS LTDA ME','ISRAEL 41 9991-1417 3345-6635-','4188462128',0,'03572855000134',NULL,'473971489','',NULL,NULL,'253981883',NULL,'2004-03-23 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (328,182,'2014-06-02 17:54:39','2014-06-02 17:54:39','HAVAIANAS','SAO PAULO ALPARGATAS','VINICIUS  42-9975=8674','4232295283',0,'',NULL,'4299292583','4230252142',NULL,NULL,'',NULL,'2002-10-03 00:00:00','CAMILO 8403-8250');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (329,183,'2014-06-02 17:54:39','2014-06-02 17:54:39','LPM MALHAS','LPM MALHAS','LAUDEMIR','',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-07-15 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (330,184,'2014-06-02 17:54:39','2014-06-02 17:54:39','MIRASUL','MIRA SUL INDUSTRIA TEXTIL LTDA','ALCIR 41 9915-6433','',0,'89856512000168',NULL,'543612185','',NULL,NULL,'1330037526',NULL,'2002-10-21 00:00:00','54-3361-1183');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (331,185,'2014-06-02 17:54:39','2014-06-02 17:54:39','FEGALLI','FEGALLI INDUSTRIA DE CALCADOS LTDA','FLAVIO','4199755117',0,'07307967000160',NULL,'1637032900','',NULL,NULL,'310396220113',NULL,'2003-08-21 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (332,187,'2014-06-02 17:54:39','2014-06-02 17:54:39','TEAM WORK','TEAM WORK','TONINHO (43) 9904-6461','',0,'',NULL,'','',NULL,NULL,'',NULL,'2002-02-05 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (333,191,'2014-06-02 17:54:39','2014-06-02 17:54:39','LILIAN CALCADOS','LILIAN CALCADOS','REGINALDO','1497834492',0,'',NULL,'4391090470','',NULL,NULL,'',NULL,'2008-07-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (334,192,'2014-06-02 17:54:39','2014-06-02 17:54:39','ELIAN','ELIAN','WALMIQUE4391528124','4331589900',0,'82698085000198',NULL,'04732759000','',NULL,NULL,'252072782',NULL,'2003-02-18 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (335,194,'2014-06-02 17:54:39','2014-06-02 17:54:39','BICARELLO','LUIS HENRIQUE GALVANI','FLAVIO','4199755117',0,'04625769000156',NULL,'1637051392','',NULL,NULL,'310357837113',NULL,'2005-03-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (336,195,'2014-06-02 17:54:39','2014-06-02 17:54:39','MANGA CURTA','VILA NOVA IND E COM LTDA','VILMAR     46-9975-1479','462246018',0,'79294039000119',NULL,'4733227800','',NULL,NULL,'251316327',NULL,'2003-09-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (337,197,'2014-06-02 17:54:39','2014-06-02 17:54:39','ESTAMPARIA','ESTAMPARIA S.A.','PIMENTEL (41) 9976-5954','4132245742',0,'19791987000138',NULL,'3133622621','3133622620',NULL,NULL,'1860084650039',NULL,'2003-10-10 00:00:00','ELENICE');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (338,198,'2014-06-02 17:54:39','2014-06-02 17:54:39','BELA CASA - KOHLOG','KOMLOG IMPORTACAO LTDA','MARCOOS','4199464000',0,'06114935001580',NULL,'4830274600','',NULL,NULL,'255649339',NULL,'2013-09-19 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (339,199,'2014-06-02 17:54:39','2014-06-02 17:54:39','ADONAI DECORACOES','HUMBERTO BURATTO DEC. LTDA','GILBERTO','01131153812',0,'',NULL,'1131014721','1131014721',NULL,NULL,'',NULL,'2013-11-14 00:00:00','GILBERTO');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (340,203,'2014-06-02 17:54:39','2014-06-02 17:54:39','PAIVA','LAVANDERIA INDUSTRIAL TAYANA LTDA','JOAOZINHO 43-91185676','',0,'02648732000118',NULL,'4334247903','',NULL,NULL,'9026456590',NULL,'2003-10-17 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (341,204,'2014-06-02 17:54:39','2014-06-02 17:54:39','CAEDU','CONFECCOES CAEDU LTDA','RAFAEL','4599533196',0,'46377727001670',NULL,'04730016810','',NULL,NULL,'255988656',NULL,'2013-11-07 00:00:00','RAFAEL BLAZIUS');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (342,207,'2014-06-02 17:54:39','2014-06-02 17:54:39','KOLOSH','DAKOTA NORDESTE SA','RAFAEL      42-9132-1171','4299175400',0,'00465813000319',NULL,'853413311','853413012',NULL,NULL,'069486689',NULL,'2004-02-02 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (343,208,'2014-06-02 17:54:39','2014-06-02 17:54:39','ISABELA FERRARI','ISABELA FERRARI','RAFAEL','',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-10-03 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (344,211,'2014-06-02 17:54:39','2014-06-02 17:54:39','MULLER','PILONETO & MULLER LTDA','SIPRIANO 9923-1508','4532242155',0,'04798756000189',NULL,'4530357110','',NULL,NULL,'9024967937',NULL,'2004-02-17 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (345,212,'2014-06-02 17:54:39','2014-06-02 17:54:39','ALMIX','ALMIX','LAUDEMIR','4399857691',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-10-28 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (346,220,'2014-06-02 17:54:39','2014-06-02 17:54:39','VICIO FATAL','VICIO FATAL','','',0,'',NULL,'4732557000','32557044',NULL,NULL,'',NULL,'2013-12-13 00:00:00','SALETE/BEL');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (347,249,'2014-06-02 17:54:39','2014-06-02 17:54:39','EMBALAGENS','EMBALAGENS PERFUME','PERFUMARIA','',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-01-31 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (348,250,'2014-06-02 17:54:39','2014-06-02 17:54:39','NATURA','NATURA','NILCEIA','4232225254',0,'905530877',NULL,'0800115566','',NULL,NULL,'71673990003869',NULL,'2014-06-02 17:54:37','0800-762-8872');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (349,251,'2014-06-02 17:54:39','2014-06-02 17:54:39','BOTICARIO','BOTICARIO','','4299189906',0,'',NULL,'4230259078','',NULL,NULL,'',NULL,'2012-03-10 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (350,252,'2014-06-02 17:54:39','2014-06-02 17:54:39','AVON','AVON COSMETICOS','ALINE 9913-0133','4232274603',0,'233085985117',NULL,'1145292000','',NULL,NULL,'56991441000823',NULL,'2007-07-17 00:00:00','LOIDE');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (351,253,'2014-06-02 17:54:39','2014-06-02 17:54:39','JEQUITI','JEQUITI','LUCIANE      9973-4000','4230277872',0,'07278350000163',NULL,'1121926000','1121926010',NULL,NULL,'492515600113',NULL,'2009-03-18 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (352,254,'2014-06-02 17:54:39','2014-06-02 17:54:39','EUDORA','EUDORA','LUCIANA','4288127004',0,'',NULL,'','',NULL,NULL,'',NULL,'2011-12-06 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (353,255,'2014-06-02 17:54:39','2014-06-02 17:54:39','PERFUMES IMP0RTADOS','PERFUMES IMPORTADOS','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-02-13 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (354,296,'2014-06-02 17:54:39','2014-06-02 17:54:39','BONSUCESSO','COMERCIAL TECIDOS PAULUK LTDA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'1997-05-29 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (355,300,'2014-06-02 17:54:39','2014-06-02 17:54:39','MONOCOLORE','MONOCOLORE','','',0,'',NULL,'1122914222','',NULL,NULL,'',NULL,'2013-11-20 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (356,301,'2014-06-02 17:54:39','2014-06-02 17:54:39','MODIT','CONFECCOES AUDIOVISUAL LTDA','','',0,'51954196000194',NULL,'0112917214','01166946627',NULL,NULL,'110206952113',NULL,'2014-06-02 17:54:37','6693-4958 6693-2284');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (357,302,'2014-06-02 17:54:39','2014-06-02 17:54:39','LE FIX','LE FIX','BOM RETIRO','1133629979',0,'',NULL,'1122924425','1122925921',NULL,NULL,'',NULL,'2012-11-29 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (358,303,'2014-06-02 17:54:39','2014-06-02 17:54:39','LILIAN','LILIAN','','',0,'',NULL,'1126938899','1126942385',NULL,NULL,'',NULL,'2010-08-25 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (359,304,'2014-06-02 17:54:40','2014-06-02 17:54:40','SUNNY DAYS','SUNNY DAYS','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-10-27 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (360,305,'2014-06-02 17:54:40','2014-06-02 17:54:40','OSSE','OSSE','','',0,'',NULL,'1133133577','1133134280',NULL,NULL,'',NULL,'2012-06-29 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (361,306,'2014-06-02 17:54:40','2014-06-02 17:54:40','SWANY','IND COM DE CONFECCOES LEETEX LTDA','','',0,'46379558000473',NULL,'1126927088','',NULL,NULL,'145560159116',NULL,'2013-11-26 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (362,307,'2014-06-02 17:54:40','2014-06-02 17:54:40','KI BELA','KI BELA','','1126930216',0,'',NULL,'1127967877','11',NULL,NULL,'',NULL,'2011-03-23 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (363,308,'2014-06-02 17:54:40','2014-06-02 17:54:40','JOYALY','JOYALY CONFECCOES E COMERCIO LTDA','JOYALY@JOYALY.COM.BR','',0,'64756141000111',NULL,'1166948624','112928323',NULL,NULL,'112949260116',NULL,'2002-12-04 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (364,309,'2014-06-02 17:54:40','2014-06-02 17:54:40','P.E. SAO PAULO','PRONTA ENTREGA S.PAULO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'1994-09-22 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (365,310,'2014-06-02 17:54:40','2014-06-02 17:54:40','MIKASA','MIKASA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'1993-12-04 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (366,311,'2014-06-02 17:54:40','2014-06-02 17:54:40','EDELVAIS','MODAS EDELVAIS','','',0,'51200954000189',NULL,'01166943003','',NULL,NULL,'110115780111',NULL,'1994-10-31 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (367,312,'2014-06-02 17:54:40','2014-06-02 17:54:40','MAZARIN','MAZARIN','','',0,'035986050001',NULL,'1122929411','1126920664',NULL,NULL,'115598496117',NULL,'2009-07-29 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (368,314,'2014-06-02 17:54:40','2014-06-02 17:54:40','GILAS','GILAS MODAS CONFECCOES LTDA','PANINI','',0,'05907695000103',NULL,'1126941761','',NULL,NULL,'116706594112',NULL,'2013-06-05 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (369,315,'2014-06-02 17:54:40','2014-06-02 17:54:40','PEPTUCHY','FABIO ALEXANDRE BATISTA ROUPAS','PANINI  41-9838-0210 TIM','4132724719',0,'09005928000133',NULL,'1147476787','',NULL,NULL,'672192356110',NULL,'2013-05-06 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (370,318,'2014-06-02 17:54:40','2014-06-02 17:54:40','RIK WIL','WILSON NARCHI & CIA LTDA','','00000000000',0,'62677604000205',NULL,'1166953185','00000000',NULL,NULL,'110007523111',NULL,'2003-12-18 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (371,320,'2014-06-02 17:54:40','2014-06-02 17:54:40','SHIC','SHIC','','',0,'',NULL,'1126941024','',NULL,NULL,'',NULL,'2006-04-28 00:00:00','MILENA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (372,321,'2014-06-02 17:54:40','2014-06-02 17:54:40','PIT STOP','PIT STOP','','1166182234',0,'',NULL,'1126939385','',NULL,NULL,'',NULL,'2006-11-28 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (373,322,'2014-06-02 17:54:40','2014-06-02 17:54:40','DEVLIN','DEVLIN MODA','','',0,'',NULL,'1166931014','1166932934',NULL,NULL,'',NULL,'2007-11-27 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (374,323,'2014-06-02 17:54:40','2014-06-02 17:54:40','NITTOH CONF','CONFECCOES NITTOH LTDA','','',0,'51032381000121',NULL,'001126922543','001126941913',NULL,NULL,'110726510111',NULL,'1999-12-08 00:00:00','JAIME');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (375,324,'2014-06-02 17:54:40','2014-06-02 17:54:40','POCOLOCO','POCOLOCO','','1133299162',0,'',NULL,'1126935282','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (376,325,'2014-06-02 17:54:40','2014-06-02 17:54:40','SAWARY','SAWARY','','',0,'00422351000190',NULL,'1160999999','',NULL,NULL,'114331325112',NULL,'2008-05-05 00:00:00','REGINALDO/GE');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (377,327,'2014-06-02 17:54:40','2014-06-02 17:54:40','DENUNCIA JEANS','DENUNCIA JEANS','','',0,'',NULL,'4732557333','',NULL,NULL,'',NULL,'2010-04-06 00:00:00','NORMA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (378,328,'2014-06-02 17:54:40','2014-06-02 17:54:40','COKILAGE','COKILAGE','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (379,329,'2014-06-02 17:54:40','2014-06-02 17:54:40','PROPOS','PROPOS CONFECCOES LTDA','','',0,'05604108000107',NULL,'001122922171','',NULL,NULL,'116574554116',NULL,'2001-12-10 00:00:00','VITORIA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (380,330,'2014-06-02 17:54:40','2014-06-02 17:54:40','LINDA Z','LINDA Z JEANS','','',0,'',NULL,'4732557333','',NULL,NULL,'',NULL,'2010-04-06 00:00:00','NORMA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (381,331,'2014-06-02 17:54:40','2014-06-02 17:54:40','FBK','USINA JEANS CONF COM IMP E ESP LTDA','','',0,'05692054000260',NULL,'001136928819','',NULL,NULL,'116631414112',NULL,'2004-05-17 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (382,333,'2014-06-02 17:54:40','2014-06-02 17:54:40','KAIRON','KAIRON MODAS LTDA','','',0,'01076221000297',NULL,'1122926071','',NULL,NULL,'115414172115',NULL,'2009-07-31 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (383,334,'2014-06-02 17:54:40','2014-06-02 17:54:40','LIPI','LIPI COUROS','','',0,'',NULL,'1166951333','',NULL,NULL,'',NULL,'2006-04-26 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (384,336,'2014-06-02 17:54:40','2014-06-02 17:54:40','PEIXOTO','PEIXOTO ARTIGOS DE COURO LTDA','','',0,'53469656000203',NULL,'01120813390','0112298808',NULL,NULL,'111689957110',NULL,'1995-01-14 00:00:00','IVAN/HEBE');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (385,339,'2014-06-02 17:54:40','2014-06-02 17:54:40','GUITTA RIO','GUITTA RIO','DONATO','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (386,340,'2014-06-02 17:54:40','2014-06-02 17:54:40','WENTI','WENTI - CHANG CHAN TI','','',0,'00546957000219',NULL,'','',NULL,NULL,'114565707116',NULL,'1997-09-05 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (387,342,'2014-06-02 17:54:40','2014-06-02 17:54:40','KILIZ','KILIZ LINGERIE','JAQUELINE','',0,'',NULL,'551162916884','',NULL,NULL,'',NULL,'2007-07-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (388,343,'2014-06-02 17:54:40','2014-06-02 17:54:40','LU BY LU','LU BY LU','','',0,'04503344000174',NULL,'11','',NULL,NULL,'116215020119',NULL,'2007-09-04 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (389,344,'2014-06-02 17:54:40','2014-06-02 17:54:40','WAZZUP','WAZZUP','NORMA','4732557000',0,'01586240000182',NULL,'4732557050','',NULL,NULL,'253404983',NULL,'2007-09-10 00:00:00','NORMA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (390,348,'2014-06-02 17:54:40','2014-06-02 17:54:40','MDM','MDM','','',0,'',NULL,'3534652213','3534652826',NULL,NULL,'',NULL,'2009-04-28 00:00:00','HANA OU LE');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (391,349,'2014-06-02 17:54:40','2014-06-02 17:54:40','JOINHA','JOINHA','REBECA','3534652349',0,'',NULL,'','',NULL,NULL,'',NULL,'2009-04-28 00:00:00','REBECA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (392,350,'2014-06-02 17:54:40','2014-06-02 17:54:40','MONTE SIAO','MONTE SIAO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2009-05-04 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (393,351,'2014-06-02 17:54:40','2014-06-02 17:54:40','EDNA','EDNA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2009-04-28 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (394,352,'2014-06-02 17:54:40','2014-06-02 17:54:40','JACUTINGA','JACUTINGA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-04-25 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (395,353,'2014-06-02 17:54:40','2014-06-02 17:54:40','PRESIDENTE','LENCOS PRESIDENTE S.A.','ELOI  99662540','422235245',0,'',NULL,'01169054141','01169054137',NULL,NULL,'',NULL,'1989-11-18 00:00:00','SCALABRIM5130288666');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (396,356,'2014-06-02 17:54:40','2014-06-02 17:54:40','DU CARECA','DU CARECA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-01-01 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (397,357,'2014-06-02 17:54:40','2014-06-02 17:54:40','TANGO','IND E COM DE ROUPAS NOVA TANGO LTDA','','',0,'04696240000123',NULL,'01166951013','',NULL,NULL,'116244874115',NULL,'1989-11-18 00:00:00','(011)6695-2424');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (398,358,'2014-06-02 17:54:40','2014-06-02 17:54:40','BRASCOL','BRASCOL IND.COM. ROUPAS NN 7264','TELE VENDAS 011-3328-1818','080077300000',0,'59356790000276',NULL,'01133281818','01133268132',NULL,NULL,'114386714114',NULL,'1993-04-30 00:00:00','011  3311-8483');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (399,359,'2014-06-02 17:54:40','2014-06-02 17:54:40','CREMER','CREMER S.A.','ISAIAS RUIZ','2243988',0,'82641325000118',NULL,'0473218000','0473218100',NULL,NULL,'250010992',NULL,'1989-11-16 00:00:00','0800-7013080');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (400,501,'2014-06-02 17:54:40','2014-06-02 17:54:40','SAGRADA FAMILIA','COLEGIO SAGRADA FAMILIA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (401,502,'2014-06-02 17:54:40','2014-06-02 17:54:40','CIESC','SAGRADO CORACAO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (402,503,'2014-06-02 17:54:40','2014-06-02 17:54:40','SANT\'ANA','SANT\'ANA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (403,504,'2014-06-02 17:54:40','2014-06-02 17:54:40','SESI','SESI','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2008-12-20 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (404,505,'2014-06-02 17:54:40','2014-06-02 17:54:40','SANTA TERESINHA','SANTA TERESINHA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (405,506,'2014-06-02 17:54:40','2014-06-02 17:54:40','SESC','SESC','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-12-15 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (406,507,'2014-06-02 17:54:40','2014-06-02 17:54:40','DESAFIO','ESCOLA DESAFIO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-11-27 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (407,508,'2014-06-02 17:54:40','2014-06-02 17:54:40','GENESIS','GENESIS','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2008-12-13 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (408,509,'2014-06-02 17:54:40','2014-06-02 17:54:40','SESPEE','SESPEE','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2011-05-19 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (409,510,'2014-06-02 17:54:40','2014-06-02 17:54:40','MASTER','MASTER','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2000-12-01 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (410,511,'2014-06-02 17:54:40','2014-06-02 17:54:40','BOM PASTOR','ESCOLA BOM PASTOR','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-05-29 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (411,512,'2014-06-02 17:54:40','2014-06-02 17:54:40','ROSAZUL','ROSAZUL','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2000-02-25 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (412,514,'2014-06-02 17:54:40','2014-06-02 17:54:40','SEPAM','SEPAM','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2001-01-13 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (413,515,'2014-06-02 17:54:40','2014-06-02 17:54:40','GIRASSOL','GIRASSOL','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-12-16 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (414,516,'2014-06-02 17:54:40','2014-06-02 17:54:40','ASSARTE','ASSARTE','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2001-06-29 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (415,517,'2014-06-02 17:54:40','2014-06-02 17:54:40','SAO JORGE PRE-ESCOLA','SAO JORGE PRE-ESCOLA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (416,518,'2014-06-02 17:54:40','2014-06-02 17:54:40','APAE','APAE','','',0,'',NULL,'32382455','',NULL,NULL,'',NULL,'2009-12-18 00:00:00','LINDAMIR');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (417,519,'2014-06-02 17:54:40','2014-06-02 17:54:40','SANTA MARIA GORETTI','SANTA MARIA GORETTI','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2002-02-21 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (418,520,'2014-06-02 17:54:40','2014-06-02 17:54:40','MEU FUTURO','MEU FUTURO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2011-01-01 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (419,551,'2014-06-02 17:54:40','2014-06-02 17:54:40','BECKER E SILVA','BECKER E SILVA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (420,552,'2014-06-02 17:54:40','2014-06-02 17:54:40','ELZIRA C. SA','ELZIRA C. SA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (421,553,'2014-06-02 17:54:40','2014-06-02 17:54:40','EPAMINONDAS','EPAMINONDAS','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (422,554,'2014-06-02 17:54:40','2014-06-02 17:54:40','FREI DOROTEU','FREI DOROTEU','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (423,555,'2014-06-02 17:54:40','2014-06-02 17:54:40','JULIO TEODORICO','JULIO TEODORICO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (424,556,'2014-06-02 17:54:40','2014-06-02 17:54:40','KENNEDY','KENNEDY','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (425,557,'2014-06-02 17:54:40','2014-06-02 17:54:40','LINDA BACILA','LINDA BACILA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (426,558,'2014-06-02 17:54:40','2014-06-02 17:54:40','MEDALHA MILAGROSA','MEDALHA MILAGROSA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (427,559,'2014-06-02 17:54:40','2014-06-02 17:54:40','MENELEU','MENELEU','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (428,560,'2014-06-02 17:54:40','2014-06-02 17:54:40','PADRE CARLOS','PADRE CARLOS','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (429,561,'2014-06-02 17:54:40','2014-06-02 17:54:40','REGENTE FEIJO','REGENTE FEIJO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (430,562,'2014-06-02 17:54:40','2014-06-02 17:54:40','CEEPPG','CENTRO ESTADUAL DE EDUCACAO PROFIS','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2010-12-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (431,563,'2014-06-02 17:54:40','2014-06-02 17:54:40','SIRLEY JAGAS','SIRLEY JAGAS','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (432,564,'2014-06-02 17:54:40','2014-06-02 17:54:40','AMALIO PINHEIRO','AMALIO PINHEIRO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (433,565,'2014-06-02 17:54:40','2014-06-02 17:54:40','JULIO TURISMO','JULIO TEODORICO TURISMO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2007-12-29 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (434,566,'2014-06-02 17:54:40','2014-06-02 17:54:40','INSTITUTO','INSTITUTO DE EDUCACAO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'1998-10-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (435,567,'2014-06-02 17:54:40','2014-06-02 17:54:40','J.D.OPERARIO','J.D.OPERARIO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'1998-12-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (436,568,'2014-06-02 17:54:40','2014-06-02 17:54:40','SANTA MARIA','COLEGIO SANTA MARIA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2009-12-17 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (437,569,'2014-06-02 17:54:40','2014-06-02 17:54:40','POLIVALENTE','POLIVALENTE','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (438,570,'2014-06-02 17:54:40','2014-06-02 17:54:40','BALDOMERO','COLEGIO ESTADUAL BALDOMERO B TAQUES','','',0,'',NULL,'4232531156','',NULL,NULL,'',NULL,'2006-05-22 00:00:00','MARCIO DIRETOR');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (439,571,'2014-06-02 17:54:40','2014-06-02 17:54:40','31 DE MARCO','31 DE MARCO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2000-12-18 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (440,572,'2014-06-02 17:54:40','2014-06-02 17:54:40','EDISON PIETROBELLI','EDISON PIETROBELLI','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2001-01-11 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (441,573,'2014-06-02 17:54:40','2014-06-02 17:54:40','CAIC','CAIC','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2001-01-20 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (442,574,'2014-06-02 17:54:40','2014-06-02 17:54:40','JOSE GOMES DO AMARAL','ESC ESTADUAL JOSE GOMES DO AMARAL','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2011-12-18 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (443,575,'2014-06-02 17:54:40','2014-06-02 17:54:40','N.SRA. GLORIA','NOSSA SENHORA DA GLORIA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2012-11-23 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (444,576,'2014-06-02 17:54:40','2014-06-02 17:54:40','SENADOR CORREIA','SENADOR CORREIA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2001-04-10 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (445,577,'2014-06-02 17:54:40','2014-06-02 17:54:40','COLONIA DONA LUISA','COLEGIO COLONIA DONA LUISA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2013-12-16 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (446,580,'2014-06-02 17:54:40','2014-06-02 17:54:40','IOLANDO T. FONSECA','IOLANDO TAQUES FONSECA','','',0,'',NULL,'32369281','',NULL,NULL,'',NULL,'2002-11-19 00:00:00','PROF SAMUEL 99261125');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (447,581,'2014-06-02 17:54:40','2014-06-02 17:54:40','NOSSA SRA.DAS GRACAS','NOSSA SRA.DAS GRACAS','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2002-12-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (448,584,'2014-06-02 17:54:40','2014-06-02 17:54:40','ANA DIVANIR BORATO','ANA DIVANIR BORATO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2003-02-15 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (449,585,'2014-06-02 17:54:40','2014-06-02 17:54:40','MONTEIRO LOBATO','MONTEIRO LOBATO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2004-01-20 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (450,586,'2014-06-02 17:54:40','2014-06-02 17:54:40','GENERAL OSORIO','GENERAL OSORIO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2004-01-31 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (451,587,'2014-06-02 17:54:40','2014-06-02 17:54:40','BORELL','BORELL','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2004-01-31 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (452,588,'2014-06-02 17:54:40','2014-06-02 17:54:40','DORAH DAITSCHMAN','COLEGIO ESTADUAL DORAH DAITSCHMAN','','',0,'',NULL,'00422382932','00422244430',NULL,NULL,'',NULL,'2004-07-20 00:00:00','PROF LEONILDA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (453,608,'2014-06-02 17:54:40','2014-06-02 17:54:40','PREFEITURA','PREFEITURA MUNICIPAL PONTA GROSSA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2005-12-30 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (454,699,'2014-06-02 17:54:40','2014-06-02 17:54:40','MAO DE OBRA','MAO DE OBRA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2011-09-27 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (455,700,'2014-06-02 17:54:40','2014-06-02 17:54:40','AZUL MARINHO','AZUL MARINHO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (456,900,'2014-06-02 17:54:40','2014-06-02 17:54:40','GAUCHA','FIACAO E TECELAGEM GAUCHA LTDA','','',0,'88332119000102',NULL,'05134795555','0514795550',NULL,NULL,'3820000218',NULL,'1994-01-01 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (457,901,'2014-06-02 17:54:40','2014-06-02 17:54:40','NANETE','NANETE TEXTIL LTDA    7834','NILSON PELIZER 43-9972-1380','4333425522',0,'',NULL,'473760000','473761844',NULL,NULL,'',NULL,'2001-01-01 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (458,902,'2014-06-02 17:54:40','2014-06-02 17:54:40','H.MILAGRE','I.T.M. IND. TEXTEIS H.MILAGRE S.A.','','',0,'',NULL,'413563180','',NULL,NULL,'',NULL,'2001-11-01 00:00:00','41-356-6029 99730433');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (459,903,'2014-06-02 17:54:40','2014-06-02 17:54:40','ELIZABETH/VICUNHA','ELIZABETH/VICUNHA TEXTIL','IGOR/LUCIANA','4232244158',0,'',NULL,'','',NULL,NULL,'',NULL,'2005-09-26 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (460,904,'2014-06-02 17:54:40','2014-06-02 17:54:40','FARROUPILHA','TEXTIL FARROUPILHA LTDA','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2003-10-23 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (461,905,'2014-06-02 17:54:40','2014-06-02 17:54:40','COMPRAS SAO PAULO','COMPRAS SAO PAULO','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (462,906,'2014-06-02 17:54:40','2014-06-02 17:54:40','JURJENSEN','IND TEXTIL IRMAOS JURGENSEN LTDA','ROBERTO 41 9962-9179','',0,'43240944000149',NULL,'1934063552','',NULL,NULL,'165003343119',NULL,'2010-11-12 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (463,907,'2014-06-02 17:54:40','2014-06-02 17:54:40','NEOTEXTIL','NEOTEXTL IND. COM. IMP. EXP. LTDA','EDUARDO','44',0,'05578339000193',NULL,'1138166200','1130',NULL,NULL,'',NULL,'2007-09-14 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (464,908,'2014-06-02 17:54:40','2014-06-02 17:54:40','JETFIO','JETFIO','DENIS  41 8837-9608 9808-1285','4130263016',0,'',NULL,'1934768535','1934768536',NULL,NULL,'',NULL,'2009-10-31 00:00:00','SUZANA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (465,909,'2014-06-02 17:54:40','2014-06-02 17:54:40','TRAPAO','TRATEXTIL','','',0,'',NULL,'4132785892','4132867422',NULL,NULL,'',NULL,'2010-01-01 00:00:00','LEANDRO');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (466,910,'2014-06-02 17:54:40','2014-06-02 17:54:40','TEKLA','TEKLA','','08007748200',0,'',NULL,'1163338235','',NULL,NULL,'',NULL,'2005-09-26 00:00:00','LAISE RAMAL 8233');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (467,911,'2014-06-02 17:54:40','2014-06-02 17:54:40','ARMAR. VOLUNTARIOS','VITOR','','',0,'',NULL,'4132245692','4132245304',NULL,NULL,'',NULL,'2005-09-26 00:00:00','GUIOMAR');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (468,912,'2014-06-02 17:54:40','2014-06-02 17:54:40','BICOLOR','TEXTIL BICOLOR','','',0,'',NULL,'1166183999','',NULL,NULL,'',NULL,'2005-09-26 00:00:00','ROSA');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (469,913,'2014-06-02 17:54:40','2014-06-02 17:54:40','YKK','PAULINIA IMP. E COM.','WWW.PAULINIA-IMPORT.COM.BR','',0,'60904836000142',NULL,'1133669022','1133376523',NULL,NULL,'105471536116',NULL,'2002-11-07 00:00:00','WILTON');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (470,914,'2014-06-02 17:54:40','2014-06-02 17:54:40','HACO','MRC DISTRIBUIDORA - RICHTER','','',0,'',NULL,'4132745179','',NULL,NULL,'',NULL,'2002-09-28 00:00:00','ODANIR/JUNIOR');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (471,915,'2014-06-02 17:54:40','2014-06-02 17:54:40','MARQUES ETIQUETAS','CRUZ ETIQUETAS','','',0,'',NULL,'4334203300','',NULL,NULL,'',NULL,'2009-10-20 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (472,916,'2014-06-02 17:54:40','2014-06-02 17:54:40','FREMATEX','FREMATEX','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (473,917,'2014-06-02 17:54:40','2014-06-02 17:54:40','TECELART','TECELART','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (474,918,'2014-06-02 17:54:40','2014-06-02 17:54:40','ACR','ACR','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (475,919,'2014-06-02 17:54:40','2014-06-02 17:54:40','TRITAN','TRITAN','','',0,'',NULL,'','',NULL,NULL,'',NULL,'2014-06-02 17:54:37','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (476,930,'2014-06-02 17:54:40','2014-06-02 17:54:40','CORRENTE','CORRENTE','','',0,'',NULL,'','',NULL,NULL,'',NULL,'1989-01-01 00:00:00','');
INSERT INTO `bon_fornecedor` (`id`, `codigo`, `inserted`, `updated`, `nomeFantasia`, `razaoSocial`, `representante`, `representanteContatoInfo`, `version`, `documento`, `email`, `fone1`, `fone2`, `fone3`, `fone4`, `inscricao_estadual`, `inscricao_municipal`, `dt_cadastro`, `contato`) VALUES (477,999,'2014-06-02 17:54:40','2014-06-02 17:54:40','PONTA DE ESTOQUE','PONTA DE ESTOQUE','','',0,'',NULL,'','',NULL,NULL,'',NULL,'1987-11-16 00:00:00','');
/*!40000 ALTER TABLE `bon_fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_fornecedor_enderecos`
--

DROP TABLE IF EXISTS `bon_fornecedor_enderecos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_fornecedor_enderecos` (
  `bon_fornecedor_id` bigint(20) NOT NULL,
  `enderecos_endereco_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_mbvij7i05mqinlc2uevso4q3e` (`enderecos_endereco_id`),
  KEY `FK_2mie2hvjvmtkgtm9r0ab307ol` (`bon_fornecedor_id`),
  CONSTRAINT `FK_2mie2hvjvmtkgtm9r0ab307ol` FOREIGN KEY (`bon_fornecedor_id`) REFERENCES `bon_fornecedor` (`id`),
  CONSTRAINT `FK_mbvij7i05mqinlc2uevso4q3e` FOREIGN KEY (`enderecos_endereco_id`) REFERENCES `bon_endereco` (`endereco_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_fornecedor_enderecos`
--
-- ORDER BY:  `enderecos_endereco_id`

LOCK TABLES `bon_fornecedor_enderecos` WRITE;
/*!40000 ALTER TABLE `bon_fornecedor_enderecos` DISABLE KEYS */;
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (150,149);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (151,150);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (152,151);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (153,152);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (154,153);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (155,154);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (156,155);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (157,156);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (158,157);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (159,158);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (160,159);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (161,160);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (162,161);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (163,162);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (164,163);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (165,164);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (166,165);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (167,166);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (168,167);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (169,168);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (170,169);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (171,170);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (172,171);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (173,172);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (174,173);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (175,174);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (176,175);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (177,176);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (178,177);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (179,178);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (180,179);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (181,180);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (182,181);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (183,182);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (184,183);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (185,184);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (186,185);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (187,186);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (188,187);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (189,188);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (190,189);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (191,190);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (192,191);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (193,192);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (194,193);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (195,194);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (196,195);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (197,196);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (198,197);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (199,198);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (200,199);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (201,200);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (202,201);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (203,202);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (204,203);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (205,204);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (206,205);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (207,206);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (208,207);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (209,208);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (210,209);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (211,210);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (212,211);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (213,212);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (214,213);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (215,214);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (216,215);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (217,216);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (218,217);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (219,218);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (220,219);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (221,220);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (222,221);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (223,222);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (224,223);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (225,224);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (226,225);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (227,226);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (228,227);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (229,228);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (230,229);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (231,230);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (232,231);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (233,232);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (234,233);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (235,234);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (236,235);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (237,236);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (238,237);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (239,238);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (240,239);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (241,240);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (242,241);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (243,242);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (244,243);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (245,244);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (246,245);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (247,246);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (248,247);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (249,248);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (250,249);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (251,250);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (252,251);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (253,252);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (254,253);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (255,254);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (256,255);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (257,256);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (258,257);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (259,258);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (260,259);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (261,260);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (262,261);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (263,262);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (264,263);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (265,264);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (266,265);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (267,266);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (268,267);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (269,268);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (270,269);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (271,270);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (272,271);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (273,272);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (274,273);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (275,274);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (276,275);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (277,276);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (278,277);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (279,278);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (280,279);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (281,280);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (282,281);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (283,282);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (284,283);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (285,284);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (286,285);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (287,286);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (288,287);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (289,288);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (290,289);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (291,290);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (292,291);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (293,292);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (294,293);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (295,294);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (296,295);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (297,296);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (298,297);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (299,298);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (300,299);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (301,300);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (302,301);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (303,302);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (304,303);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (305,304);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (306,305);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (307,306);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (308,307);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (309,308);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (310,309);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (311,310);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (312,311);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (313,312);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (314,313);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (315,314);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (316,315);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (317,316);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (318,317);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (319,318);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (320,319);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (321,320);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (322,321);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (323,322);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (324,323);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (325,324);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (326,325);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (327,326);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (328,327);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (329,328);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (330,329);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (331,330);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (332,331);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (333,332);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (334,333);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (335,334);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (336,335);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (337,336);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (338,337);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (339,338);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (340,339);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (341,340);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (342,341);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (343,342);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (344,343);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (345,344);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (346,345);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (347,346);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (348,347);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (349,348);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (350,349);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (351,350);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (352,351);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (353,352);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (354,353);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (355,354);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (356,355);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (357,356);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (358,357);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (359,358);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (360,359);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (361,360);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (362,361);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (363,362);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (364,363);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (365,364);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (366,365);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (367,366);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (368,367);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (369,368);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (370,369);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (371,370);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (372,371);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (373,372);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (374,373);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (375,374);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (376,375);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (377,376);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (378,377);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (379,378);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (380,379);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (381,380);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (382,381);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (383,382);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (384,383);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (385,384);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (386,385);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (387,386);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (388,387);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (389,388);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (390,389);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (391,390);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (392,391);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (393,392);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (394,393);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (395,394);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (396,395);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (397,396);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (398,397);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (399,398);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (400,399);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (401,400);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (402,401);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (403,402);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (404,403);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (405,404);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (406,405);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (407,406);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (408,407);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (409,408);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (410,409);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (411,410);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (412,411);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (413,412);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (414,413);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (415,414);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (416,415);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (417,416);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (418,417);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (419,418);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (420,419);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (421,420);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (422,421);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (423,422);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (424,423);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (425,424);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (426,425);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (427,426);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (428,427);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (429,428);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (430,429);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (431,430);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (432,431);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (433,432);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (434,433);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (435,434);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (436,435);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (437,436);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (438,437);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (439,438);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (440,439);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (441,440);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (442,441);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (443,442);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (444,443);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (445,444);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (446,445);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (447,446);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (448,447);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (449,448);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (450,449);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (451,450);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (452,451);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (453,452);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (454,453);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (455,454);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (456,455);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (457,456);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (458,457);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (459,458);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (460,459);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (461,460);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (462,461);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (463,462);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (464,463);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (465,464);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (466,465);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (467,466);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (468,467);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (469,468);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (470,469);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (471,470);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (472,471);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (473,472);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (474,473);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (475,474);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (476,475);
INSERT INTO `bon_fornecedor_enderecos` (`bon_fornecedor_id`, `enderecos_endereco_id`) VALUES (477,476);
/*!40000 ALTER TABLE `bon_fornecedor_enderecos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_funcionario`
--

DROP TABLE IF EXISTS `bon_funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_funcionario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rg` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `codigo` int(11) NOT NULL,
  `cpf` varchar(14) COLLATE utf8_swedish_ci NOT NULL,
  `data_emissao_rg` datetime DEFAULT NULL,
  `data_nascimento` datetime DEFAULT NULL,
  `dt_admissao` datetime NOT NULL,
  `dt_demissao` datetime DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_swedish_ci DEFAULT NULL,
  `estado_civil` varchar(13) COLLATE utf8_swedish_ci DEFAULT NULL,
  `estado_rg` varchar(2) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone1` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone2` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone3` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `fone4` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `naturalidade` varchar(50) COLLATE utf8_swedish_ci DEFAULT NULL,
  `nome` varchar(200) COLLATE utf8_swedish_ci NOT NULL,
  `obs` varchar(3000) COLLATE utf8_swedish_ci DEFAULT NULL,
  `orgao_emissor_rg` varchar(15) COLLATE utf8_swedish_ci DEFAULT NULL,
  `sexo` varchar(9) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  `nome_ekt` varchar(200) COLLATE utf8_swedish_ci DEFAULT NULL,
  `senha` varchar(200) COLLATE utf8_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_68xiwo0kfnrjxm5qj8u1hx3a8` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_funcionario`
--
-- ORDER BY:  `id`

LOCK TABLES `bon_funcionario` WRITE;
/*!40000 ALTER TABLE `bon_funcionario` DISABLE KEYS */;
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (1,'',99,'99999999999',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 11:56:52','2014-04-17 18:21:51','','FUNCIONÁRIOS','','',NULL,1,'FUNCIONARIOS','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (9,'',1,'21512582972',NULL,'1951-02-20 00:00:00','1987-10-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:23:15','2014-04-29 12:27:06','','VALMIR FERREIRA DA SILVA','','',NULL,6,'VALMIR','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (10,'',2,'00000000002',NULL,'1996-07-27 00:00:00','2013-01-02 00:00:00','2013-08-13 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:24:14','2014-04-29 11:47:06','','BRUNA ANGELITA TLOMATZKI','','',NULL,6,'BRUNA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (11,'',2,'00000000022',NULL,'1900-01-01 00:00:00','2012-03-01 00:00:00','2012-12-31 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:24:54','2014-04-17 18:24:54','','NOELI','','',NULL,0,'NOELI','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (12,'',3,'00000000003',NULL,'1900-01-01 00:00:00','2012-01-01 00:00:00','2013-03-31 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:25:18','2014-04-17 18:25:18','','ALINE','','',NULL,0,'ALINE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (13,'',3,'10063918943',NULL,'1994-09-02 00:00:00','2014-01-07 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:25:47','2014-05-20 11:38:38','','BIANCA STADLER GONÇALVES','','',NULL,8,'BIANCA','31B9B9A70EE17624BA0520E270ED70D141E8ADCF535D6C7FEEDA0D5F3630682EFDCFAA4C4C9A8635');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (14,'',3,'00396379990',NULL,'1977-11-16 00:00:00','2013-04-18 00:00:00','2013-10-07 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:26:13','2014-04-29 12:02:02','','ELAINE CRISTINA FERREIRA ROSA','','',NULL,4,'ELAINE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (15,'',4,'09886014946',NULL,'1995-01-01 00:00:00','2013-03-11 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:26:55','2014-05-20 11:38:49','','MIRIÃ SANTOS MEDEIROS','','',NULL,11,'MIRIA','80D8F935F9DDB7B99D17EAE8730A66B552F9EC1DE0538C5F6FE36259DC79052B73FF1B1B34A2AC3A');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (16,'',5,'00000000005',NULL,NULL,'2012-01-01 00:00:00','2012-08-30 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:27:26','2014-04-17 18:27:26','','ANDRESSA','','',NULL,0,'ANDRESSA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (17,'',5,'07977992906',NULL,'1991-02-21 00:00:00','2013-03-11 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:27:48','2014-05-20 11:42:03','','KEVINI MENON','','',NULL,5,'KEVINI','E30382A7D6E8D158511E70DEB20BB32AE6566F97C3F3628A652423EBD87944ED32D1E64D2A030D27');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (18,'',6,'56157126949',NULL,'1963-04-11 00:00:00','1980-10-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:28:06','2014-04-28 14:25:51','','ANA SENIUK','','',NULL,4,'ANA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (19,'',7,'09131684912',NULL,'1995-02-17 00:00:00','2013-01-02 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:28:25','2014-05-20 11:42:13','','CHRISTIAN ALESSANDRO ZUBACZ','','',NULL,5,'CRISTIAN','5CC1926601055D3B050E0D06E72DCC115EA79133E63D7E7938D941BBBA375CDFF66EA04C7FC53607');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (22,'',7,'00000000077',NULL,NULL,'2012-11-01 00:00:00','2012-12-31 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:28:56','2014-04-17 18:28:56','','WANESSA','','',NULL,0,'WANESSA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (23,'',8,'72202637915',NULL,'1967-09-28 00:00:00','1987-06-08 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:29:15','2014-04-28 14:24:26','','JANE DA SILVA','','',NULL,7,'JANE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (24,'',9,'00000000009',NULL,NULL,'2012-01-01 00:00:00','2014-02-28 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:29:38','2014-04-22 11:52:03','','ROSE','','',NULL,1,'ROSE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (25,'',10,'00000000010',NULL,NULL,'1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:29:57','2014-04-17 18:29:57','','TONINHO','','',NULL,0,'TONINHO','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (26,'',11,'00413662985',NULL,'1976-05-31 00:00:00','2013-08-16 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:30:19','2014-05-20 11:42:21','','ROSICLEIA NIZER PEREIRA','','',NULL,5,'ROSICLEIA','30075804BCA1F5377989C9529981449F543E3AEC3871F302EBF9737E8618DD366588B3EA584F3286');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (27,'',11,'00000000111',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00','2013-03-31 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:37:24','2014-04-17 18:37:24','','ZILDA','','',NULL,0,'ZILDA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (28,'',12,'00000000012',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:37:38','2014-04-17 18:37:38','','MARIZA','','',NULL,0,'MARIZA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (29,'',13,'07083780994',NULL,'1988-11-20 00:00:00','2013-10-01 00:00:00','2014-04-26 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:38:03','2014-04-29 12:07:54','','VIVIANE OPATA','','',NULL,6,'VIVIANE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (30,'',14,'07715839906',NULL,'1989-09-14 00:00:00','2013-02-01 00:00:00','2013-11-30 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:38:33','2014-04-29 11:50:51','','ELIANE PAUK KOSSAR','','',NULL,4,'ELIANE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (31,'',15,'00000000015',NULL,'1900-01-01 00:00:00','2012-01-01 00:00:00','2012-03-31 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:51:25','2014-04-28 09:01:19','','ADRIANE','','',NULL,1,'ADRIANE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (32,'',15,'10894963970',NULL,'1996-09-22 00:00:00','2014-03-20 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-22 11:51:42','2014-05-20 11:42:28','','KELVIN KIELT FRANCO','','',NULL,4,'KELVIN','66DF999B759A9DB757CFCB6DF0877E04CCEABED0149653368C5F477FBC8296A7339F979CBB34FC05');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (33,'',15,'00000001115',NULL,'1993-10-20 00:00:00','2012-08-01 00:00:00','2014-03-11 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:51:59','2014-04-29 11:41:52','','LORIANI RAFAELA GUIMARÃES','','',NULL,6,'LORIANI','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (34,'',16,'04709595925',NULL,'1987-04-30 00:00:00','2012-08-10 00:00:00','2013-03-14 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:52:17','2014-04-28 18:16:41','','ANA MARIA CANTERI','','',NULL,2,'ANA MARIA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (35,'',16,'00000000116',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00','2012-06-30 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:52:29','2014-04-28 09:03:16','','ANINHA','','',NULL,1,'ANINHA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (36,'',16,'10426036930',NULL,'1996-10-23 00:00:00','2013-10-09 00:00:00','2014-01-06 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:52:41','2014-04-29 12:06:43','','ISABELA BUENO DE CAMARGO','','',NULL,4,'ISABELA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (37,'',17,'00000000017',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00','2013-10-30 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:52:53','2014-04-28 09:04:01','','SELMA','','',NULL,1,'SELMA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (38,'',18,'03869889942',NULL,'1981-05-25 00:00:00','2011-04-09 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-22 11:53:06','2014-05-20 11:43:02','','DANIELE ALVES BANNACH','','',NULL,8,'DANIELE','CA34A7CEEC553BFF9F8CEE749363BCC1F4FBFB91E0D28733EA21B73A1D6FD9B97366441D7B997ABF');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (39,'',19,'10261615920',NULL,'1996-11-18 00:00:00','2013-12-09 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-22 11:53:43','2014-05-20 11:43:13','','CARLOS HENRIQUE CARNEIRO DOS SANTOS','','',NULL,6,'CARLOS HENRIQUE','DE5793153C6CDAF583181960BADC42FD4FD5EE5D7B13AD124EF53E0C1006AF85C7D3F67860D1FA98');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (42,'',22,'04975779927',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-22 11:54:13','2014-05-02 09:40:08','','ANNE CRISTINE DE ALMEIRA PRADO','','',NULL,8,'ANNE CRISTINE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (44,'',17,'09990056935',NULL,'1996-12-09 00:00:00','2014-01-07 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-26 16:25:53','2014-05-20 11:42:35','','RAFAELE DUARTE DE CAMARGO','','',NULL,6,'RAFAELE','61F4B506B8A1D93DC9AAEEB19386EA81CC6F994B9DF7556A53E347C798C99665FF049E076381626A');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (45,'',901,'08917154956',NULL,'1994-04-16 00:00:00','2010-12-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-28 14:29:47','2014-04-28 14:32:32','','MAKELLY KAONE DOS SANTOS','','',NULL,5,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (46,'',902,'02722825961',NULL,'1973-04-21 00:00:00','2011-10-03 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-28 18:10:04','2014-04-28 18:11:29','','JOSEMERI DE FATIMA COMINEZE','','',NULL,3,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (47,'',903,'75586282968',NULL,'1989-07-13 00:00:00','2012-04-04 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-29 11:41:25','2014-04-29 11:41:25','','RENATO DE OLIVEIRA','','',NULL,0,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (48,'',904,'05215397902',NULL,'1967-06-20 00:00:00','2013-02-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-29 11:51:20','2014-04-29 11:51:48','','SILVANI TEREZINHA RIBEIRO','','',NULL,3,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (49,'',905,'08928179920',NULL,'1993-06-04 00:00:00','2013-04-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-29 11:57:15','2014-04-29 11:58:08','','OSNIM GIOVANNI FARIAS CORDOVA','','',NULL,4,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (50,'',906,'10282649921',NULL,'1994-10-05 00:00:00','2013-10-09 00:00:00','2014-01-06 00:00:00','',NULL,NULL,'','','','','2014-04-29 12:05:20','2014-04-29 12:05:43','','PAULA DAIANE FERREIRA DE PAIVA MAIOR','','',NULL,2,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (51,'',98,'98989898989',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-29 12:15:21','2014-04-29 12:15:37','','LICITAÇÕES','','',NULL,1,'LICITACOES','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (52,'',907,'10192952951',NULL,'1995-10-29 00:00:00','2014-04-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-29 12:16:07','2014-04-29 12:17:57','','RULIANE APARECIDA CAMPOS RAMOS','','',NULL,2,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
INSERT INTO `bon_funcionario` (`id`, `rg`, `codigo`, `cpf`, `data_emissao_rg`, `data_nascimento`, `dt_admissao`, `dt_demissao`, `email`, `estado_civil`, `estado_rg`, `fone1`, `fone2`, `fone3`, `fone4`, `inserted`, `updated`, `naturalidade`, `nome`, `obs`, `orgao_emissor_rg`, `sexo`, `version`, `nome_ekt`, `senha`) VALUES (53,'',50,'00000000050',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-05-02 09:40:32','2014-05-02 09:40:32','','PAULUK','','',NULL,0,'PAULUK','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
/*!40000 ALTER TABLE `bon_funcionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_funcionario_bon_endereco`
--

DROP TABLE IF EXISTS `bon_funcionario_bon_endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_funcionario_bon_endereco` (
  `bon_funcionario_id` bigint(20) NOT NULL,
  `enderecos_endereco_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_9rho31mktown43jkmae0okmuf` (`enderecos_endereco_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_funcionario_bon_endereco`
--
-- ORDER BY:  `enderecos_endereco_id`

LOCK TABLES `bon_funcionario_bon_endereco` WRITE;
/*!40000 ALTER TABLE `bon_funcionario_bon_endereco` DISABLE KEYS */;
/*!40000 ALTER TABLE `bon_funcionario_bon_endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_funcionario_cargo`
--

DROP TABLE IF EXISTS `bon_funcionario_cargo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_funcionario_cargo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cargo` varchar(30) COLLATE utf8_swedish_ci DEFAULT NULL,
  `dt_inicio` datetime NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `salario` double NOT NULL,
  `version` int(11) NOT NULL,
  `funcionario_id` bigint(20) NOT NULL,
  `comissao` double NOT NULL,
  `dt_fim` datetime DEFAULT NULL,
  `salario_piso` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r0fp7vw37sn1dpkxx3h8ctqm1` (`funcionario_id`,`dt_inicio`),
  CONSTRAINT `FK_mpy76qy7r6ekd0miyewleo7li` FOREIGN KEY (`funcionario_id`) REFERENCES `bon_funcionario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_funcionario_cargo`
--
-- ORDER BY:  `id`

LOCK TABLES `bon_funcionario_cargo` WRITE;
/*!40000 ALTER TABLE `bon_funcionario_cargo` DISABLE KEYS */;
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (2,'VENDEDOR','2013-03-01 00:00:00','2014-04-24 14:37:18','2014-04-24 14:37:18',820,2,15,1,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (6,'VENDEDOR','2013-10-01 00:00:00','2014-04-24 15:26:59','2014-04-24 15:26:59',920,2,29,1,'2013-10-31 00:00:00',722);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (7,'AUXILIAR_VENDAS','2014-01-07 00:00:00','2014-04-28 09:07:03','2014-04-28 09:07:03',920,2,13,1,'2014-03-31 00:00:00',722);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (8,'GERENTE','2012-05-01 00:00:00','2014-04-28 12:15:51','2014-04-28 12:15:51',1059.48,1,9,0,'2013-04-30 00:00:00',1059.48);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (9,'GERENTE','2013-05-01 00:00:00','2014-04-28 12:26:32','2014-04-28 12:26:32',1154.83,0,9,0,NULL,1154.83);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (10,'AUXILIAR_SERVICOS_GERAIS','2012-01-01 00:00:00','2014-04-28 14:23:08','2014-04-28 14:23:08',800,1,23,0,'2012-04-30 00:00:00',800);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (11,'AUXILIAR_SERVICOS_GERAIS','2012-05-01 00:00:00','2014-04-28 14:23:26','2014-04-28 14:23:26',850,1,23,0,'2012-08-31 00:00:00',850);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (12,'AUXILIAR_SERVICOS_GERAIS','2012-09-01 00:00:00','2014-04-28 14:23:38','2014-04-28 14:23:38',860,1,23,0,'2013-04-30 00:00:00',860);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (13,'AUXILIAR_SERVICOS_GERAIS','2013-05-01 00:00:00','2014-04-28 14:23:53','2014-04-28 14:23:53',950,1,23,0,'2013-10-31 00:00:00',950);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (14,'AUXILIAR_SERVICOS_GERAIS','2013-11-01 00:00:00','2014-04-28 14:24:14','2014-04-28 14:24:14',1000,0,23,0,NULL,1000);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (15,'CAIXA','2012-05-01 00:00:00','2014-04-28 14:25:15','2014-04-28 14:25:15',960.12,1,18,0,'2013-04-30 00:00:00',960.12);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (16,'CAIXA','2013-05-01 00:00:00','2014-04-28 14:25:34','2014-04-28 14:25:34',1046.53,0,18,0,NULL,1046.53);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (17,'CREDIARISTA','2012-01-01 00:00:00','2014-04-28 14:29:55','2014-04-28 14:29:55',670,2,45,0,'2012-04-30 00:00:00',670);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (18,'CREDIARISTA','2012-05-01 00:00:00','2014-04-28 14:30:14','2014-04-28 14:30:14',723.6,1,45,0,'2012-10-31 00:00:00',723.6);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (19,'CREDIARISTA','2012-11-01 00:00:00','2014-04-28 14:30:41','2014-04-28 14:30:41',750,1,45,0,'2013-04-30 00:00:00',750);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (20,'CREDIARISTA','2013-05-01 00:00:00','2014-04-28 14:30:52','2014-04-28 14:30:52',817.5,0,45,0,NULL,817.5);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (21,'VENDEDOR','2012-05-01 00:00:00','2014-04-28 14:33:21','2014-04-28 14:33:21',820,3,38,1,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (22,'VENDEDOR','2013-05-01 00:00:00','2014-04-28 14:40:01','2014-04-28 14:40:01',920,1,38,1,NULL,777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (23,'CORTE','2012-05-01 00:00:00','2014-04-28 18:10:10','2014-04-28 18:10:10',713.66,1,46,0,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (24,'CORTE','2013-05-01 00:00:00','2014-04-28 18:10:33','2014-04-28 18:10:33',777.89,0,46,0,NULL,777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (25,'VENDEDOR','2012-08-01 00:00:00','2014-04-28 18:13:32','2014-04-28 18:13:32',820,2,33,1,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (26,'VENDEDOR','2013-05-01 00:00:00','2014-04-28 18:15:26','2014-04-28 18:15:26',920,0,33,1,'2014-03-11 00:00:00',777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (27,'VENDEDOR','2012-10-01 00:00:00','2014-04-29 11:43:28','2014-04-29 11:43:28',820,1,42,1,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (28,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:44:03','2014-04-29 11:44:03',920,1,42,1,'2013-08-31 00:00:00',777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (29,'CREDIARISTA','2013-09-01 00:00:00','2014-04-29 11:44:19','2014-04-29 11:44:19',817.5,0,42,0,NULL,817.5);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (30,'VENDEDOR','2013-01-01 00:00:00','2014-04-29 11:45:23','2014-04-29 11:45:23',820,1,10,1,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (31,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:45:37','2014-04-29 11:45:37',920,1,10,1,'2013-08-13 00:00:00',777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (32,'VENDEDOR','2013-01-01 00:00:00','2014-04-29 11:47:58','2014-04-29 11:47:58',820,1,19,1,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (33,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:48:50','2014-04-29 11:48:50',920,0,19,1,NULL,777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (34,'VENDEDOR','2013-02-01 00:00:00','2014-04-29 11:50:21','2014-04-29 11:50:21',820,1,30,1,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (35,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:50:40','2014-04-29 11:50:40',920,0,30,1,'2013-11-30 00:00:00',777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (36,'AUXILIAR_SERVICOS_GERAIS','2013-02-01 00:00:00','2014-04-29 11:51:25','2014-04-29 11:51:25',828,1,48,0,'2013-04-30 00:00:00',828);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (37,'AUXILIAR_SERVICOS_GERAIS','2013-05-01 00:00:00','2014-04-29 11:51:37','2014-04-29 11:51:37',920,0,48,0,NULL,920);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (38,'VENDEDOR','2013-03-01 00:00:00','2014-04-29 11:52:20','2014-04-29 11:52:20',820,1,17,1,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (39,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:52:37','2014-04-29 11:52:37',920,0,17,1,NULL,777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (40,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:53:48','2014-04-29 11:53:48',920,0,15,1,NULL,777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (41,'ESTOQUISTA','2013-04-01 00:00:00','2014-04-29 11:57:23','2014-04-29 11:57:23',713.66,1,49,0,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (42,'ESTOQUISTA','2013-05-01 00:00:00','2014-04-29 11:57:46','2014-04-29 11:57:46',920,1,49,0,'2013-10-31 00:00:00',920);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (43,'ESTOQUISTA','2013-11-01 00:00:00','2014-04-29 11:57:56','2014-04-29 11:57:56',966,0,49,0,NULL,966);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (44,'VENDEDOR','2013-04-18 00:00:00','2014-04-29 12:01:27','2014-04-29 12:01:27',820,1,14,1,'2013-04-30 00:00:00',713.66);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (45,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 12:01:51','2014-04-29 12:01:51',920,0,14,1,'2013-10-07 00:00:00',777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (46,'VENDEDOR','2013-08-16 00:00:00','2014-04-29 12:03:03','2014-04-29 12:03:03',920,2,26,1,'2013-10-31 00:00:00',720);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (47,'VENDEDOR','2013-11-01 00:00:00','2014-04-29 12:03:14','2014-04-29 12:03:14',920,0,26,1,NULL,777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (48,'AUXILIAR_VENDAS','2013-10-09 00:00:00','2014-04-29 12:05:25','2014-04-29 12:05:25',722,0,50,0,'2014-01-06 00:00:00',722);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (49,'VENDEDOR','2013-10-09 00:00:00','2014-04-29 12:06:27','2014-04-29 12:06:27',920,0,36,1,'2014-01-06 00:00:00',722);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (50,'VENDEDOR','2013-11-01 00:00:00','2014-04-29 12:07:41','2014-04-29 12:07:41',920,0,29,1,'2014-04-26 00:00:00',777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (51,'VENDEDOR','2013-12-01 00:00:00','2014-04-29 12:09:10','2014-04-29 12:09:10',920,1,39,1,'2014-02-28 00:00:00',722);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (52,'VENDEDOR','2014-03-01 00:00:00','2014-04-29 12:09:32','2014-04-29 12:09:32',920,0,39,1,NULL,777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (53,'AUXILIAR_VENDAS','2014-01-07 00:00:00','2014-04-29 12:10:36','2014-04-29 12:10:36',920,1,44,1,'2014-03-31 00:00:00',722);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (54,'AUXILIAR_VENDAS','2014-04-01 00:00:00','2014-04-29 12:10:58','2014-04-29 12:10:58',920,0,44,1,NULL,777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (55,'AUXILIAR_VENDAS','2014-04-01 00:00:00','2014-04-29 12:12:13','2014-04-29 12:12:13',920,0,13,1,NULL,777.89);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (56,'AUXILIAR_SERVICOS_GERAIS','2014-03-20 00:00:00','2014-04-29 12:13:26','2014-04-29 12:13:26',920,0,32,1,NULL,722);
INSERT INTO `bon_funcionario_cargo` (`id`, `cargo`, `dt_inicio`, `inserted`, `updated`, `salario`, `version`, `funcionario_id`, `comissao`, `dt_fim`, `salario_piso`) VALUES (57,'AUXILIAR_VENDAS','2014-04-01 00:00:00','2014-04-29 12:16:19','2014-04-29 12:16:19',920,0,52,0,NULL,722);
/*!40000 ALTER TABLE `bon_funcionario_cargo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bon_funcionario_enderecos`
--

DROP TABLE IF EXISTS `bon_funcionario_enderecos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bon_funcionario_enderecos` (
  `bon_funcionario_id` bigint(20) NOT NULL,
  `enderecos_endereco_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_9rho31mktown43jkmae0okmuf` (`enderecos_endereco_id`),
  UNIQUE KEY `UK_qo9ddkam1dygysbe8uqr51ng1` (`enderecos_endereco_id`),
  KEY `FK_fxbje6nieot9tn2ol3owus5ov` (`bon_funcionario_id`),
  CONSTRAINT `FK_9rho31mktown43jkmae0okmuf` FOREIGN KEY (`enderecos_endereco_id`) REFERENCES `bon_endereco` (`endereco_id`),
  CONSTRAINT `FK_fxbje6nieot9tn2ol3owus5ov` FOREIGN KEY (`bon_funcionario_id`) REFERENCES `bon_funcionario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_funcionario_enderecos`
--
-- ORDER BY:  `enderecos_endereco_id`

LOCK TABLES `bon_funcionario_enderecos` WRITE;
/*!40000 ALTER TABLE `bon_funcionario_enderecos` DISABLE KEYS */;
/*!40000 ALTER TABLE `bon_funcionario_enderecos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cfg_config`
--

DROP TABLE IF EXISTS `cfg_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cfg_config` (
  `config_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chave` varchar(50) COLLATE utf8_swedish_ci NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `valor` varchar(20000) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `chave` (`chave`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cfg_config`
--
-- ORDER BY:  `config_id`

LOCK TABLES `cfg_config` WRITE;
/*!40000 ALTER TABLE `cfg_config` DISABLE KEYS */;
INSERT INTO `cfg_config` (`config_id`, `chave`, `inserted`, `updated`, `valor`, `version`) VALUES (1,'bonsucesso.vendas.custoOperacional','2014-03-28 14:54:38','2014-03-28 14:54:40','0.35',0);
INSERT INTO `cfg_config` (`config_id`, `chave`, `inserted`, `updated`, `valor`, `version`) VALUES (2,'bonsucesso.vendas.custoFinanceiro','2014-03-28 14:55:40','2014-03-28 14:55:43','0.15',0);
INSERT INTO `cfg_config` (`config_id`, `chave`, `inserted`, `updated`, `valor`, `version`) VALUES (3,'bonsucesso.cortinas.prazoValidadeOrcamento','2014-03-28 14:56:22','2014-03-28 14:56:25','30',0);
INSERT INTO `cfg_config` (`config_id`, `chave`, `inserted`, `updated`, `valor`, `version`) VALUES (5,'bonsucesso.vendas.descontoAVista','2014-03-28 15:55:55','2014-03-28 15:55:57','0.10',0);
INSERT INTO `cfg_config` (`config_id`, `chave`, `inserted`, `updated`, `valor`, `version`) VALUES (6,'bonsucesso.cortinas.alturaBarraPadrao','2014-03-28 16:12:55','2014-03-28 16:12:58','0.30',0);
INSERT INTO `cfg_config` (`config_id`, `chave`, `inserted`, `updated`, `valor`, `version`) VALUES (7,'bonsucesso.cortinas.alturaMaxHorizontal','2014-03-28 16:13:36','2014-03-28 16:13:38','1.50',0);
INSERT INTO `cfg_config` (`config_id`, `chave`, `inserted`, `updated`, `valor`, `version`) VALUES (8,'bonsucesso.rh.porcentMetaMinima','2014-05-15 16:32:54','2014-05-15 16:32:54','0.05',0);
/*!40000 ALTER TABLE `cfg_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crtn_artigo_cortina`
--

DROP TABLE IF EXISTS `crtn_artigo_cortina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crtn_artigo_cortina` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `estado_civil` varchar(100) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  `produto_id` bigint(20) NOT NULL,
  `tecido_id` bigint(20) DEFAULT NULL,
  `descricao` varchar(200) COLLATE utf8_swedish_ci NOT NULL,
  `reduzido` int(11) NOT NULL,
  `tipo` varchar(30) COLLATE utf8_swedish_ci NOT NULL,
  `fornecedor_id` bigint(20) NOT NULL,
  `tecido_marca_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7qde6qnjcp2cd09aa5lm0otyf` (`produto_id`),
  UNIQUE KEY `UK_mpymo372juwpeg41ocxo6i7xg` (`tipo`,`descricao`,`fornecedor_id`),
  UNIQUE KEY `UK_lgqt1fsgvojljoihn383ujihk` (`reduzido`),
  KEY `FK_ifbof7r75drdl1um2aey4itwq` (`tecido_id`),
  KEY `FK_b7a6sgv4tngdfeisxtd0iyqc7` (`fornecedor_id`),
  KEY `FK_4sh0cjeb1qkxwrd7fw9vevrxa` (`tecido_marca_id`),
  CONSTRAINT `FK_4sh0cjeb1qkxwrd7fw9vevrxa` FOREIGN KEY (`tecido_marca_id`) REFERENCES `crtn_artigo_cortina_marca` (`id`),
  CONSTRAINT `FK_7qde6qnjcp2cd09aa5lm0otyf` FOREIGN KEY (`produto_id`) REFERENCES `est_produto` (`id`),
  CONSTRAINT `FK_b7a6sgv4tngdfeisxtd0iyqc7` FOREIGN KEY (`fornecedor_id`) REFERENCES `crtn_fornecedor` (`id`),
  CONSTRAINT `FK_ifbof7r75drdl1um2aey4itwq` FOREIGN KEY (`tecido_id`) REFERENCES `crtn_tecido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_artigo_cortina`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_artigo_cortina` WRITE;
/*!40000 ALTER TABLE `crtn_artigo_cortina` DISABLE KEYS */;
/*!40000 ALTER TABLE `crtn_artigo_cortina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crtn_artigo_cortina_marca`
--

DROP TABLE IF EXISTS `crtn_artigo_cortina_marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crtn_artigo_cortina_marca` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `marca` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_itavv5ym9eun5ftju6nf9sgjk` (`marca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_artigo_cortina_marca`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_artigo_cortina_marca` WRITE;
/*!40000 ALTER TABLE `crtn_artigo_cortina_marca` DISABLE KEYS */;
/*!40000 ALTER TABLE `crtn_artigo_cortina_marca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crtn_artigo_cortina_preco`
--

DROP TABLE IF EXISTS `crtn_artigo_cortina_preco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crtn_artigo_cortina_preco` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coeficiente` double NOT NULL,
  `custo_operacional` double NOT NULL,
  `dt_custo` date NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `margem` double NOT NULL,
  `prazo` int(11) NOT NULL,
  `preco_custo` double NOT NULL,
  `preco_prazo` double NOT NULL,
  `preco_promo` double DEFAULT NULL,
  `preco_vista` double NOT NULL,
  `version` int(11) NOT NULL,
  `artigo_cortina_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2ta1gausgq7kyoh10klui8i8x` (`artigo_cortina_id`,`dt_custo`),
  CONSTRAINT `FK_2m4g814e36dmra4aodsbonkyb` FOREIGN KEY (`artigo_cortina_id`) REFERENCES `crtn_artigo_cortina` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_artigo_cortina_preco`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_artigo_cortina_preco` WRITE;
/*!40000 ALTER TABLE `crtn_artigo_cortina_preco` DISABLE KEYS */;
/*!40000 ALTER TABLE `crtn_artigo_cortina_preco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crtn_cortina`
--

DROP TABLE IF EXISTS `crtn_cortina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crtn_cortina` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `altura` double NOT NULL,
  `altura_janela` double NOT NULL,
  `com_instalacao` bit(1) NOT NULL,
  `completa` bit(1) NOT NULL,
  `descricao` varchar(300) COLLATE utf8_swedish_ci DEFAULT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `largura` double NOT NULL,
  `largura_janela` double NOT NULL,
  `ordem` int(11) NOT NULL,
  `qtde_camadas` int(11) NOT NULL,
  `varao_trilho` varchar(1) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  `orcamento_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k3aw2ps8ybffxpotpd6wkq966` (`orcamento_id`,`ordem`),
  CONSTRAINT `FK_57w7rdg3h6ixw93alpgelytxm` FOREIGN KEY (`orcamento_id`) REFERENCES `crtn_orcamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_cortina`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_cortina` WRITE;
/*!40000 ALTER TABLE `crtn_cortina` DISABLE KEYS */;
/*!40000 ALTER TABLE `crtn_cortina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crtn_cortina_item`
--

DROP TABLE IF EXISTS `crtn_cortina_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crtn_cortina_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `altura_barra` double DEFAULT NULL,
  `camada` int(11) NOT NULL,
  `tecido_fator` double DEFAULT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `obs` varchar(3000) COLLATE utf8_swedish_ci DEFAULT NULL,
  `tecido_varao_trilho` varchar(30) COLLATE utf8_swedish_ci DEFAULT NULL,
  `preco` double NOT NULL,
  `qtde` double NOT NULL,
  `tecido_tipo_fixacao` varchar(1) COLLATE utf8_swedish_ci DEFAULT NULL,
  `tipoPrega` varchar(100) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  `cortina_id` bigint(20) NOT NULL,
  `produto_id` bigint(20) NOT NULL,
  `artigoCortina_id` bigint(20) NOT NULL,
  `artigo_cortina_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5vnd28scp6qu7ec331kfnkp9g` (`cortina_id`,`produto_id`,`qtde`,`camada`),
  UNIQUE KEY `UK_gh28ihpv9o50sd2p8pq2b54qs` (`cortina_id`,`artigoCortina_id`,`qtde`,`camada`),
  UNIQUE KEY `UK_rpwujhept2cwwm1mfx4ih3lcc` (`cortina_id`,`artigo_cortina_id`,`qtde`,`camada`),
  KEY `FK_5l9anef809jt9dpd99shd99be` (`produto_id`),
  KEY `FK_2axjy75g8cpvm8s0a75ausw29` (`artigoCortina_id`),
  KEY `FK_ekjswdilu2w64y3p2ixlm1oal` (`artigo_cortina_id`),
  CONSTRAINT `FK_ekjswdilu2w64y3p2ixlm1oal` FOREIGN KEY (`artigo_cortina_id`) REFERENCES `crtn_artigo_cortina` (`id`),
  CONSTRAINT `FK_1f6eom4e7tljveepqcr3j6to0` FOREIGN KEY (`cortina_id`) REFERENCES `crtn_cortina` (`id`),
  CONSTRAINT `FK_2axjy75g8cpvm8s0a75ausw29` FOREIGN KEY (`artigoCortina_id`) REFERENCES `crtn_artigo_cortina` (`id`),
  CONSTRAINT `FK_5l9anef809jt9dpd99shd99be` FOREIGN KEY (`produto_id`) REFERENCES `est_produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_cortina_item`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_cortina_item` WRITE;
/*!40000 ALTER TABLE `crtn_cortina_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `crtn_cortina_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crtn_cortina_lado`
--

DROP TABLE IF EXISTS `crtn_cortina_lado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crtn_cortina_lado` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `largura_lado` double NOT NULL,
  `version` int(11) NOT NULL,
  `cortina_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ncbtrmwm4ljtm3wbd6a864msp` (`cortina_id`),
  CONSTRAINT `FK_ncbtrmwm4ljtm3wbd6a864msp` FOREIGN KEY (`cortina_id`) REFERENCES `crtn_cortina` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_cortina_lado`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_cortina_lado` WRITE;
/*!40000 ALTER TABLE `crtn_cortina_lado` DISABLE KEYS */;
/*!40000 ALTER TABLE `crtn_cortina_lado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crtn_depreciacao_preco`
--

DROP TABLE IF EXISTS `crtn_depreciacao_preco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crtn_depreciacao_preco` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `porcentagem` double NOT NULL,
  `prazo_fim` int(11) NOT NULL,
  `prazo_ini` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_56b4d19h8sg8q3s7b7tr00fxm` (`prazo_ini`),
  UNIQUE KEY `UK_bgsaagmrnnpp17bw8ylxf257a` (`prazo_fim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_depreciacao_preco`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_depreciacao_preco` WRITE;
/*!40000 ALTER TABLE `crtn_depreciacao_preco` DISABLE KEYS */;
/*!40000 ALTER TABLE `crtn_depreciacao_preco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crtn_fornecedor`
--

DROP TABLE IF EXISTS `crtn_fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crtn_fornecedor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` int(11) NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `nomeFantasia` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `razaoSocial` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `representante` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `representanteContatoInfo` varchar(100) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_l6u7i4t3ip4yhshsg7w2pyu8t` (`nomeFantasia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_fornecedor`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_fornecedor` WRITE;
/*!40000 ALTER TABLE `crtn_fornecedor` DISABLE KEYS */;
/*!40000 ALTER TABLE `crtn_fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crtn_orcamento`
--

DROP TABLE IF EXISTS `crtn_orcamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crtn_orcamento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_preenchimento` datetime DEFAULT NULL,
  `dt_validade` datetime DEFAULT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `obs` varchar(3000) COLLATE utf8_swedish_ci DEFAULT NULL,
  `preenchido_por` varchar(80) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  `cliente_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4kbyro8gsqnpdq0idf2bd7dn8` (`dt_preenchimento`,`cliente_id`,`dt_validade`),
  KEY `FK_d3eaydlyjtkhh8bdr8b1b8wjd` (`cliente_id`),
  CONSTRAINT `FK_d3eaydlyjtkhh8bdr8b1b8wjd` FOREIGN KEY (`cliente_id`) REFERENCES `bon_cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_orcamento`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_orcamento` WRITE;
/*!40000 ALTER TABLE `crtn_orcamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `crtn_orcamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crtn_tecido`
--

DROP TABLE IF EXISTS `crtn_tecido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crtn_tecido` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `altura_barra_padrao` double NOT NULL,
  `altura_max_horizontal` double NOT NULL,
  `fator_padrao` double DEFAULT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `largura` double NOT NULL,
  `orientacao_padrao` varchar(30) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  `produto_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_st3exfd0i7ke7ashk0h090nd7` (`produto_id`),
  CONSTRAINT `FK_st3exfd0i7ke7ashk0h090nd7` FOREIGN KEY (`produto_id`) REFERENCES `est_produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_tecido`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_tecido` WRITE;
/*!40000 ALTER TABLE `crtn_tecido` DISABLE KEYS */;
/*!40000 ALTER TABLE `crtn_tecido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_depreciacao_preco`
--

DROP TABLE IF EXISTS `est_depreciacao_preco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_depreciacao_preco` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `porcentagem` double NOT NULL,
  `prazo_fim` int(11) NOT NULL,
  `prazo_ini` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `prazo_ini` (`prazo_ini`),
  UNIQUE KEY `prazo_fim` (`prazo_fim`),
  UNIQUE KEY `UK_56b4d19h8sg8q3s7b7tr00fxm` (`prazo_ini`),
  UNIQUE KEY `UK_bgsaagmrnnpp17bw8ylxf257a` (`prazo_fim`),
  UNIQUE KEY `UK_dfq6yegwm77b1nbhvn4jvrkbl` (`prazo_ini`),
  UNIQUE KEY `UK_qnxjul5tc3sk55mwug4dq869r` (`prazo_fim`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_depreciacao_preco`
--
-- ORDER BY:  `id`

LOCK TABLES `est_depreciacao_preco` WRITE;
/*!40000 ALTER TABLE `est_depreciacao_preco` DISABLE KEYS */;
INSERT INTO `est_depreciacao_preco` (`id`, `inserted`, `updated`, `porcentagem`, `prazo_fim`, `prazo_ini`, `version`) VALUES (1,'2014-03-28 15:47:34','2014-03-28 15:47:34',1,15,0,0);
INSERT INTO `est_depreciacao_preco` (`id`, `inserted`, `updated`, `porcentagem`, `prazo_fim`, `prazo_ini`, `version`) VALUES (2,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.981,30,16,0);
INSERT INTO `est_depreciacao_preco` (`id`, `inserted`, `updated`, `porcentagem`, `prazo_fim`, `prazo_ini`, `version`) VALUES (3,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.969,45,31,0);
INSERT INTO `est_depreciacao_preco` (`id`, `inserted`, `updated`, `porcentagem`, `prazo_fim`, `prazo_ini`, `version`) VALUES (4,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.958,60,46,0);
INSERT INTO `est_depreciacao_preco` (`id`, `inserted`, `updated`, `porcentagem`, `prazo_fim`, `prazo_ini`, `version`) VALUES (5,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.946,75,61,0);
INSERT INTO `est_depreciacao_preco` (`id`, `inserted`, `updated`, `porcentagem`, `prazo_fim`, `prazo_ini`, `version`) VALUES (6,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.935,90,76,0);
INSERT INTO `est_depreciacao_preco` (`id`, `inserted`, `updated`, `porcentagem`, `prazo_fim`, `prazo_ini`, `version`) VALUES (7,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.924,105,91,0);
INSERT INTO `est_depreciacao_preco` (`id`, `inserted`, `updated`, `porcentagem`, `prazo_fim`, `prazo_ini`, `version`) VALUES (8,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.914,120,106,0);
INSERT INTO `est_depreciacao_preco` (`id`, `inserted`, `updated`, `porcentagem`, `prazo_fim`, `prazo_ini`, `version`) VALUES (9,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.904,135,121,0);
INSERT INTO `est_depreciacao_preco` (`id`, `inserted`, `updated`, `porcentagem`, `prazo_fim`, `prazo_ini`, `version`) VALUES (10,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.894,999999,136,0);
/*!40000 ALTER TABLE `est_depreciacao_preco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_depto`
--

DROP TABLE IF EXISTS `est_depto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_depto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` int(11) NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `nome` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cuo06f8l4bgfuio6ww38s7g9a` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_depto`
--
-- ORDER BY:  `id`

LOCK TABLES `est_depto` WRITE;
/*!40000 ALTER TABLE `est_depto` DISABLE KEYS */;
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (1,1,'2014-05-29 09:58:55','2014-05-29 09:58:55','UNIFORMES ESCOLARES',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (2,2,'2014-05-29 09:59:04','2014-05-29 09:59:04','MASCULINO',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (3,3,'2014-05-29 09:59:09','2014-05-29 09:59:09','FEMININO',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (4,4,'2014-05-29 09:59:18','2014-05-29 09:59:18','ART. PARA BEBE',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (5,5,'2014-05-29 09:59:33','2014-05-29 09:59:33','INFANTO JUVENIL',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (6,6,'2014-05-29 09:59:42','2014-05-29 09:59:42','CAMA, MESA E BANHO',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (7,7,'2014-05-29 09:59:47','2014-05-29 09:59:47','MALHAS',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (8,8,'2014-05-29 09:59:58','2014-05-29 09:59:58','ART. DE COURO/CALCADOS',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (9,9,'2014-05-29 10:00:04','2014-05-29 10:00:04','BRINQUEDOS',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (10,10,'2014-05-29 10:00:08','2014-05-29 10:00:08','LINHA PRAIA',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (11,11,'2014-05-29 10:00:12','2014-05-29 10:00:12','TECIDOS',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (12,12,'2014-05-29 10:00:18','2014-05-29 10:00:18','ARMARINHO',0);
INSERT INTO `est_depto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`) VALUES (13,13,'2014-05-29 10:00:22','2014-05-29 10:00:22','DIVERSOS',0);
/*!40000 ALTER TABLE `est_depto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_grade`
--

DROP TABLE IF EXISTS `est_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_grade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` int(11) NOT NULL,
  `decimais` bit(1) NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `obs` varchar(100) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5qaq95ncfbnmeufmvhnhxahx5` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_grade`
--
-- ORDER BY:  `id`

LOCK TABLES `est_grade` WRITE;
/*!40000 ALTER TABLE `est_grade` DISABLE KEYS */;
/*!40000 ALTER TABLE `est_grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_grade_tamanho`
--

DROP TABLE IF EXISTS `est_grade_tamanho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_grade_tamanho` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `ordem` int(11) NOT NULL,
  `tamanho` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  `grade_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8e1cawvnw7wpepot6qer4evg0` (`tamanho`,`grade_id`),
  KEY `FK_gr36epn1urreqt771ecl0ruap` (`grade_id`),
  CONSTRAINT `FK_gr36epn1urreqt771ecl0ruap` FOREIGN KEY (`grade_id`) REFERENCES `est_grade` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_grade_tamanho`
--
-- ORDER BY:  `id`

LOCK TABLES `est_grade_tamanho` WRITE;
/*!40000 ALTER TABLE `est_grade_tamanho` DISABLE KEYS */;
/*!40000 ALTER TABLE `est_grade_tamanho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_produto`
--

DROP TABLE IF EXISTS `est_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_produto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(200) COLLATE utf8_swedish_ci NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `reduzido` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `fornecedor_id` bigint(20) NOT NULL,
  `subdepto_id` bigint(20) NOT NULL,
  `tipo_produto_id` bigint(20) NOT NULL,
  `unidade_produto_id` bigint(20) NOT NULL,
  `unidadeproduto_id` bigint(20) NOT NULL,
  `dt_ult_venda` date NOT NULL,
  `referencia` int(11) NOT NULL,
  `grade_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_m55in0gnol4rxhp7wn8umsqqg` (`tipo_produto_id`,`subdepto_id`,`descricao`,`fornecedor_id`),
  UNIQUE KEY `UK_iemfjd6yv57uxrf1u9304vibw` (`reduzido`),
  UNIQUE KEY `UK_tnek7bv4s6q6pxrlmlvyo1p33` (`subdepto_id`,`descricao`,`fornecedor_id`),
  KEY `FK_i1n7epvbxvg459rwfxhjpmq9t` (`fornecedor_id`),
  KEY `FK_esk91ymmq20i265lwmoagho4v` (`unidade_produto_id`),
  KEY `FK_8638ghhiq3567sk22hie2xmh` (`unidadeproduto_id`),
  KEY `FK_f08svdb6nivyhoo1vqob0513g` (`grade_id`),
  CONSTRAINT `FK_f08svdb6nivyhoo1vqob0513g` FOREIGN KEY (`grade_id`) REFERENCES `est_grade` (`id`),
  CONSTRAINT `FK_56s91dd7o96b1ax729dfcta9v` FOREIGN KEY (`tipo_produto_id`) REFERENCES `est_tipo_produto` (`id`),
  CONSTRAINT `FK_8638ghhiq3567sk22hie2xmh` FOREIGN KEY (`unidadeproduto_id`) REFERENCES `lc_unidade_produto` (`id`),
  CONSTRAINT `FK_esk91ymmq20i265lwmoagho4v` FOREIGN KEY (`unidade_produto_id`) REFERENCES `lc_unidade_produto` (`id`),
  CONSTRAINT `FK_i1n7epvbxvg459rwfxhjpmq9t` FOREIGN KEY (`fornecedor_id`) REFERENCES `bon_fornecedor` (`id`),
  CONSTRAINT `FK_k63xwg7p2kag0gt73l56a1ds5` FOREIGN KEY (`subdepto_id`) REFERENCES `est_subdepto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_produto`
--
-- ORDER BY:  `id`

LOCK TABLES `est_produto` WRITE;
/*!40000 ALTER TABLE `est_produto` DISABLE KEYS */;
/*!40000 ALTER TABLE `est_produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_produto_preco`
--

DROP TABLE IF EXISTS `est_produto_preco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_produto_preco` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coeficiente` double NOT NULL,
  `custo_operacional` double NOT NULL,
  `dt_custo` date NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `margem` double NOT NULL,
  `prazo` int(11) NOT NULL,
  `preco_custo` double NOT NULL,
  `preco_prazo` double NOT NULL,
  `preco_promo` double DEFAULT NULL,
  `preco_vista` double NOT NULL,
  `version` int(11) NOT NULL,
  `produto_id` bigint(20) NOT NULL,
  `dt_preco_venda` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c0hjqccye8rck9khwydgmvxki` (`produto_id`,`dt_custo`),
  CONSTRAINT `FK_jagug4tptf55exl7rg4wosnyn` FOREIGN KEY (`produto_id`) REFERENCES `est_produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_produto_preco`
--
-- ORDER BY:  `id`

LOCK TABLES `est_produto_preco` WRITE;
/*!40000 ALTER TABLE `est_produto_preco` DISABLE KEYS */;
/*!40000 ALTER TABLE `est_produto_preco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_produto_saldo`
--

DROP TABLE IF EXISTS `est_produto_saldo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_produto_saldo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_tamanho_id` tinyblob NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `qtde` double NOT NULL,
  `version` int(11) NOT NULL,
  `produto_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lael55gsw41recv8h2pslfqto` (`produto_id`),
  CONSTRAINT `FK_lael55gsw41recv8h2pslfqto` FOREIGN KEY (`produto_id`) REFERENCES `est_produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_produto_saldo`
--
-- ORDER BY:  `id`

LOCK TABLES `est_produto_saldo` WRITE;
/*!40000 ALTER TABLE `est_produto_saldo` DISABLE KEYS */;
/*!40000 ALTER TABLE `est_produto_saldo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_subdepto`
--

DROP TABLE IF EXISTS `est_subdepto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_subdepto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` int(11) NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `nome` varchar(100) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  `depto_id` bigint(20) NOT NULL,
  `margem` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3ap7cu9pwkt8xl9o7ms45idlg` (`nome`,`depto_id`),
  KEY `FK_fsv5pmht0815p4phpy31tbg6j` (`depto_id`),
  CONSTRAINT `FK_fsv5pmht0815p4phpy31tbg6j` FOREIGN KEY (`depto_id`) REFERENCES `est_depto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_subdepto`
--
-- ORDER BY:  `id`

LOCK TABLES `est_subdepto` WRITE;
/*!40000 ALTER TABLE `est_subdepto` DISABLE KEYS */;
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (8,8,'2014-05-29 14:20:38','2014-05-29 14:20:38','CALCA   ESCOLAR ACTION',0,1,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (9,9,'2014-05-29 14:20:38','2014-05-29 14:20:38','JAQUETA ESCOLAR ACTION',0,1,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (10,10,'2014-05-29 14:20:38','2014-05-29 14:20:38','BERMUDA ESCOLAR ACTION',0,1,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (11,11,'2014-05-29 14:20:38','2014-05-29 14:20:38','CALCA   ESCOLAR MALHA',0,1,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (12,12,'2014-05-29 14:20:38','2014-05-29 14:20:38','JAQUETA ESCOLAR MALHA',0,1,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (13,13,'2014-05-29 14:20:38','2014-05-29 14:20:38','BERMUDA ESCOLAR MALHA',0,1,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (15,15,'2014-05-29 14:20:38','2014-05-29 14:20:38','CAMISETA ESCOLAR MC PA',0,1,20);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (16,16,'2014-05-29 14:20:38','2014-05-29 14:20:38','CAMISETA ESCOLAR ML PA',0,1,20);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (17,17,'2014-05-29 14:20:38','2014-05-29 14:20:38','CAMISETA ESCOLAR MC PV',0,1,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (18,18,'2014-05-29 14:20:38','2014-05-29 14:20:38','CAMISETA ESCOLAR ML PV',0,1,16);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (21,21,'2014-05-29 14:20:38','2014-05-29 14:20:38','BLUSA MOLETON INFANTIL',0,1,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (22,22,'2014-05-29 14:20:38','2014-05-29 14:20:38','BLUSA MOLETON ADULTO',0,1,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (30,30,'2014-05-29 14:20:38','2014-05-29 14:20:38','JALECO',0,1,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (101,101,'2014-05-29 14:20:38','2014-05-29 14:20:38','CAMISA ESPORTE MC',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (102,102,'2014-05-29 14:20:38','2014-05-29 14:20:38','CAMISA ESPORTE ML',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (103,103,'2014-05-29 14:20:38','2014-05-29 14:20:38','POLO MC AD',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (104,104,'2014-05-29 14:20:38','2014-05-29 14:20:38','CALCA SOCIAL MASC',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (105,105,'2014-05-29 14:20:38','2014-05-29 14:20:38','CALCA JEANS MASC',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (106,106,'2014-05-29 14:20:38','2014-05-29 14:20:38','CALCA COLOR MASC',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (107,107,'2014-05-29 14:20:38','2014-05-29 14:20:38','CUECA AD',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (108,108,'2014-05-29 14:20:38','2014-05-29 14:20:38','MEIA AD MASC',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (109,109,'2014-05-29 14:20:39','2014-05-29 14:20:39','LENCO DE BOLSO',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (110,110,'2014-05-29 14:20:39','2014-05-29 14:20:39','BLUSA LA MASC',0,2,13);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (111,111,'2014-05-29 14:20:39','2014-05-29 14:20:39','POLO ML MASC',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (112,112,'2014-05-29 14:20:39','2014-05-29 14:20:39','BERMUDA AGUA AD',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (113,113,'2014-05-29 14:20:39','2014-05-29 14:20:39','BERMUDA JEANS/SARJA AD',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (114,114,'2014-05-29 14:20:39','2014-05-29 14:20:39','JAQUETA MASC',0,2,13);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (115,115,'2014-05-29 14:20:39','2014-05-29 14:20:39','SHORTS COLOR AD',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (116,116,'2014-05-29 14:20:39','2014-05-29 14:20:39','MEIA ESPORTIVA',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (117,117,'2014-05-29 14:20:39','2014-05-29 14:20:39','CAMISA FLANELA',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (118,118,'2014-05-29 14:20:39','2014-05-29 14:20:39','GRAVATA AD',0,2,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (119,119,'2014-05-29 14:20:39','2014-05-29 14:20:39','JAQUETA JEANS MASC',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (120,120,'2014-05-29 14:20:39','2014-05-29 14:20:39','JAQUETA LA MASC',0,2,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (121,121,'2014-05-29 14:20:39','2014-05-29 14:20:39',NULL,0,2,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (122,122,'2014-05-29 14:20:39','2014-05-29 14:20:39','AGASALHO ESPORTIVO',0,2,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (123,123,'2014-05-29 14:20:39','2014-05-29 14:20:39','CALCA TACTEL AD',0,2,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (124,124,'2014-05-29 14:20:39','2014-05-29 14:20:39','ROUPAO MASCULINO',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (125,125,'2014-05-29 14:20:39','2014-05-29 14:20:39','BONE',0,2,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (126,126,'2014-05-29 14:20:39','2014-05-29 14:20:39','SUNGA AD',0,2,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (127,127,'2014-05-29 14:20:39','2014-05-29 14:20:39','ACES. INVERNO MASC',0,2,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (201,201,'2014-05-29 14:20:39','2014-05-29 14:20:39','BLUSA INVERNO AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (202,202,'2014-05-29 14:20:39','2014-05-29 14:20:39','CONJ INVERNO AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (203,203,'2014-05-29 14:20:39','2014-05-29 14:20:39','BLUSA LA FEM',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (204,204,'2014-05-29 14:20:39','2014-05-29 14:20:39','BLUSA FIO VERAO',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (205,205,'2014-05-29 14:20:39','2014-05-29 14:20:39','BLUSA VERAO AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (206,206,'2014-05-29 14:20:39','2014-05-29 14:20:39','CONJ VERAO',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (207,207,'2014-05-29 14:20:39','2014-05-29 14:20:39','CALCA SOCIAL FEM',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (208,208,'2014-05-29 14:20:39','2014-05-29 14:20:39','CALCA JEANS FEM',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (209,209,'2014-05-29 14:20:39','2014-05-29 14:20:39','CALCA COLOR FEM',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (210,210,'2014-05-29 14:20:39','2014-05-29 14:20:39','MEIA FEM',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (211,211,'2014-05-29 14:20:39','2014-05-29 14:20:39','CALCINHA AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (212,212,'2014-05-29 14:20:40','2014-05-29 14:20:40','BIQUINE AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (213,213,'2014-05-29 14:20:40','2014-05-29 14:20:40','SOUTIEN',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (214,214,'2014-05-29 14:20:40','2014-05-29 14:20:40','CINTA E MODELADOR',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (215,215,'2014-05-29 14:20:40','2014-05-29 14:20:40','CAMISOLA',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (216,216,'2014-05-29 14:20:40','2014-05-29 14:20:40','ANAGUA/COMBINACAO',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (217,217,'2014-05-29 14:20:40','2014-05-29 14:20:40','JAQUETA AD FEM',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (218,218,'2014-05-29 14:20:40','2014-05-29 14:20:40','SAIA JEANS AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (219,219,'2014-05-29 14:20:40','2014-05-29 14:20:40','SAIA SOCIAL AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (220,220,'2014-05-29 14:20:40','2014-05-29 14:20:40','SAIA COLOR AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (221,221,'2014-05-29 14:20:40','2014-05-29 14:20:40','VESTIDO AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (222,222,'2014-05-29 14:20:40','2014-05-29 14:20:40','JAQUETA JEANS FEM',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (223,223,'2014-05-29 14:20:40','2014-05-29 14:20:40','CASACO FEM AD',0,3,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (225,225,'2014-05-29 14:20:40','2014-05-29 14:20:40',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (226,226,'2014-05-29 14:20:40','2014-05-29 14:20:40','PENHOAR E ROUPAO',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (227,227,'2014-05-29 14:20:40','2014-05-29 14:20:40','BERMUDA FEM AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (229,229,'2014-05-29 14:20:40','2014-05-29 14:20:40','CALCA LA AD',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (230,230,'2014-05-29 14:20:40','2014-05-29 14:20:40',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (231,231,'2014-05-29 14:20:40','2014-05-29 14:20:40','MEIA SOQUETE',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (232,232,'2014-05-29 14:20:40','2014-05-29 14:20:40','CALCINHA BANCA AD',0,3,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (235,235,'2014-05-29 14:20:40','2014-05-29 14:20:40',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (245,245,'2014-05-29 14:20:40','2014-05-29 14:20:40','ACESSORIOS FEMININOS',0,3,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (250,250,'2014-05-29 14:20:40','2014-05-29 14:20:40',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (251,251,'2014-05-29 14:20:40','2014-05-29 14:20:40',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (254,254,'2014-05-29 14:20:40','2014-05-29 14:20:40',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (255,255,'2014-05-29 14:20:40','2014-05-29 14:20:40',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (256,256,'2014-05-29 14:20:40','2014-05-29 14:20:40',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (257,257,'2014-05-29 14:20:40','2014-05-29 14:20:40',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (258,258,'2014-05-29 14:20:40','2014-05-29 14:20:40',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (259,259,'2014-05-29 14:20:41','2014-05-29 14:20:41',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (260,260,'2014-05-29 14:20:41','2014-05-29 14:20:41',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (261,261,'2014-05-29 14:20:41','2014-05-29 14:20:41',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (262,262,'2014-05-29 14:20:41','2014-05-29 14:20:41',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (263,263,'2014-05-29 14:20:41','2014-05-29 14:20:41',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (264,264,'2014-05-29 14:20:41','2014-05-29 14:20:41',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (265,265,'2014-05-29 14:20:41','2014-05-29 14:20:41',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (266,266,'2014-05-29 14:20:41','2014-05-29 14:20:41',NULL,0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (270,270,'2014-05-29 14:20:41','2014-05-29 14:20:41','MM.BB',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (271,271,'2014-05-29 14:20:41','2014-05-29 14:20:41','EKOS',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (272,272,'2014-05-29 14:20:41','2014-05-29 14:20:41','NATURE',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (273,273,'2014-05-29 14:20:41','2014-05-29 14:20:41','FACES',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (274,274,'2014-05-29 14:20:41','2014-05-29 14:20:41','UNICA',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (275,275,'2014-05-29 14:20:41','2014-05-29 14:20:41','AQUARELA',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (276,276,'2014-05-29 14:20:41','2014-05-29 14:20:41','CHRONOS',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (277,277,'2014-05-29 14:20:41','2014-05-29 14:20:41','PLANTS',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (278,278,'2014-05-29 14:20:41','2014-05-29 14:20:41','ERVA DOCE',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (279,279,'2014-05-29 14:20:41','2014-05-29 14:20:41','TODODIA',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (280,280,'2014-05-29 14:20:41','2014-05-29 14:20:41','FOTOEQUILIBRIO',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (281,281,'2014-05-29 14:20:41','2014-05-29 14:20:41','PERFUMARIA FEM',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (282,282,'2014-05-29 14:20:41','2014-05-29 14:20:41','PERFUMARIA MASC',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (283,283,'2014-05-29 14:20:41','2014-05-29 14:20:41','CRER PARA VER',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (284,284,'2014-05-29 14:20:41','2014-05-29 14:20:41','KIT',0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (285,285,'2014-05-29 14:20:41','2014-05-29 14:20:41','DIVERSA',0,3,-1.4);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (286,286,'2014-05-29 14:20:41','2014-05-29 14:20:41','UNA',0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (287,287,'2014-05-29 14:20:41','2014-05-29 14:20:41','EMBALAGENS',0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (288,288,'2014-05-29 14:20:41','2014-05-29 14:20:41','NATURA HOMEN',0,3,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (301,301,'2014-05-29 14:20:41','2014-05-29 14:20:41','FRALDA',0,4,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (302,302,'2014-05-29 14:20:42','2014-05-29 14:20:42',NULL,0,4,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (303,303,'2014-05-29 14:20:42','2014-05-29 14:20:42','JOGO BATIZADO',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (304,304,'2014-05-29 14:20:42','2014-05-29 14:20:42','MANTA BEBE',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (305,305,'2014-05-29 14:20:42','2014-05-29 14:20:42','COBERTOR BEBE',0,4,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (306,306,'2014-05-29 14:20:42','2014-05-29 14:20:42','PAGAO',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (307,307,'2014-05-29 14:20:42','2014-05-29 14:20:42','MIJAO',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (308,308,'2014-05-29 14:20:42','2014-05-29 14:20:42','ACESSORIOS BEBE',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (309,309,'2014-05-29 14:20:42','2014-05-29 14:20:42','SAPATINHO E SANDALIA BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (310,310,'2014-05-29 14:20:42','2014-05-29 14:20:42','EDREDON BERCO',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (311,311,'2014-05-29 14:20:42','2014-05-29 14:20:42','JOGO LENCOL BERCO',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (312,312,'2014-05-29 14:20:42','2014-05-29 14:20:42','MOSQUITEIRO',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (313,313,'2014-05-29 14:20:42','2014-05-29 14:20:42','SACOLA E FRASQUEIRA BEBE',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (314,314,'2014-05-29 14:20:42','2014-05-29 14:20:42','BABEIRO',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (315,315,'2014-05-29 14:20:42','2014-05-29 14:20:42',NULL,0,4,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (316,316,'2014-05-29 14:20:42','2014-05-29 14:20:42','PROTETOR DE BERCO',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (317,317,'2014-05-29 14:20:42','2014-05-29 14:20:42','MACACAO CURTO MALHA',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (318,318,'2014-05-29 14:20:42','2014-05-29 14:20:42','MACACAO LONGO PLUSH',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (319,319,'2014-05-29 14:20:42','2014-05-29 14:20:42','MACACAO LONGO ESPONJA',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (320,320,'2014-05-29 14:20:42','2014-05-29 14:20:42','REGATA BEBE',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (321,321,'2014-05-29 14:20:42','2014-05-29 14:20:42','MACACAO LONGO MALHA',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (322,322,'2014-05-29 14:20:42','2014-05-29 14:20:42','BANHEIRA',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (323,323,'2014-05-29 14:20:42','2014-05-29 14:20:42',NULL,0,4,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (324,324,'2014-05-29 14:20:42','2014-05-29 14:20:42','MEIA BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (326,326,'2014-05-29 14:20:42','2014-05-29 14:20:42','SACO DORMIR',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (328,328,'2014-05-29 14:20:42','2014-05-29 14:20:42','TRAVESSEIRO BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (329,329,'2014-05-29 14:20:42','2014-05-29 14:20:42','TOALHA BANHO INF',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (330,330,'2014-05-29 14:20:42','2014-05-29 14:20:42','KIT BERCO',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (332,332,'2014-05-29 14:20:42','2014-05-29 14:20:42','BLUSA INVERNO BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (333,333,'2014-05-29 14:20:42','2014-05-29 14:20:42','CALCA JEANS BB FEM',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (334,334,'2014-05-29 14:20:43','2014-05-29 14:20:43','CALCA JEANS BB MASC',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (335,335,'2014-05-29 14:20:43','2014-05-29 14:20:43','BERMUDA BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (336,336,'2014-05-29 14:20:43','2014-05-29 14:20:43','CONJ MOLETON BEBE MASC',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (337,337,'2014-05-29 14:20:43','2014-05-29 14:20:43','JAQUETA BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (338,338,'2014-05-29 14:20:43','2014-05-29 14:20:43','CONJ VERAO BEBE MASC',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (340,340,'2014-05-29 14:20:43','2014-05-29 14:20:43','CAMISETA MM BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (341,341,'2014-05-29 14:20:43','2014-05-29 14:20:43','CONJ VERAO BEBE FEM',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (342,342,'2014-05-29 14:20:43','2014-05-29 14:20:43','CAMISETA MF ML BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (343,343,'2014-05-29 14:20:43','2014-05-29 14:20:43','BLUSA MOLETON BEBE',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (344,344,'2014-05-29 14:20:43','2014-05-29 14:20:43','CALCA MOLETON BEBE',0,4,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (345,345,'2014-05-29 14:20:43','2014-05-29 14:20:43','CONJ MOLETON BEBE FEM',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (346,346,'2014-05-29 14:20:43','2014-05-29 14:20:43','PIJAMA LONGO BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (347,347,'2014-05-29 14:20:43','2014-05-29 14:20:43','BLUSA LA BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (348,348,'2014-05-29 14:20:43','2014-05-29 14:20:43','JARDINEIRA BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (349,349,'2014-05-29 14:20:43','2014-05-29 14:20:43','BLUSA VERAO BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (350,350,'2014-05-29 14:20:43','2014-05-29 14:20:43','VESTIDO BEBE',0,4,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (351,351,'2014-05-29 14:20:43','2014-05-29 14:20:43','CAMISA INF MC',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (352,352,'2014-05-29 14:20:43','2014-05-29 14:20:43','CAMISA INF ML',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (353,353,'2014-05-29 14:20:43','2014-05-29 14:20:43','POLO INF MC',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (354,354,'2014-05-29 14:20:43','2014-05-29 14:20:43','CALCA SOCIAL INF MASC',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (355,355,'2014-05-29 14:20:43','2014-05-29 14:20:43','CALCA JEANS INF MASC',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (356,356,'2014-05-29 14:20:43','2014-05-29 14:20:43','CALCA COLOR INF MASC',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (357,357,'2014-05-29 14:20:43','2014-05-29 14:20:43','CUECA INF',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (358,358,'2014-05-29 14:20:43','2014-05-29 14:20:43','CALCINHA INF',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (359,359,'2014-05-29 14:20:43','2014-05-29 14:20:43','MEIA INF',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (360,360,'2014-05-29 14:20:43','2014-05-29 14:20:43','BLUSA LA INF MASC',0,5,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (361,361,'2014-05-29 14:20:43','2014-05-29 14:20:43','JAQUETA FORRADA INF MASC',0,5,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (362,362,'2014-05-29 14:20:43','2014-05-29 14:20:43','CONJ VERAO INF FEM',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (363,363,'2014-05-29 14:20:43','2014-05-29 14:20:43','CONJ INVERNO INF FEM',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (364,364,'2014-05-29 14:20:43','2014-05-29 14:20:43','BERMUDA INF MASC',0,5,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (365,365,'2014-05-29 14:20:44','2014-05-29 14:20:44','SAIA INF',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (366,366,'2014-05-29 14:20:44','2014-05-29 14:20:44','VESTIDO VERAO INF',0,5,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (367,367,'2014-05-29 14:20:44','2014-05-29 14:20:44','VESTIDO INVERNO INF',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (368,368,'2014-05-29 14:20:44','2014-05-29 14:20:44','BLUSA VERAO INF',0,5,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (369,369,'2014-05-29 14:20:44','2014-05-29 14:20:44','BLUSA INVERNO INF',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (370,370,'2014-05-29 14:20:44','2014-05-29 14:20:44',NULL,0,5,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (371,371,'2014-05-29 14:20:44','2014-05-29 14:20:44',NULL,0,5,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (372,372,'2014-05-29 14:20:44','2014-05-29 14:20:44','CONJ VERAO INF MASC',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (373,373,'2014-05-29 14:20:44','2014-05-29 14:20:44','CONJUNTO INF MASC',0,5,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (374,374,'2014-05-29 14:20:44','2014-05-29 14:20:44','BERMUDA INF FEM',0,5,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (376,376,'2014-05-29 14:20:44','2014-05-29 14:20:44',NULL,0,5,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (377,377,'2014-05-29 14:20:44','2014-05-29 14:20:44','ACESSORIOS JUNINOS',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (378,378,'2014-05-29 14:20:44','2014-05-29 14:20:44','VESTIDO JUNINO',0,5,18);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (379,379,'2014-05-29 14:20:44','2014-05-29 14:20:44',NULL,0,5,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (380,380,'2014-05-29 14:20:44','2014-05-29 14:20:44','CALCAO ESPORTIVO INF',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (381,381,'2014-05-29 14:20:44','2014-05-29 14:20:44','ROUPAO INF',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (382,382,'2014-05-29 14:20:44','2014-05-29 14:20:44','POLO ML INF',0,5,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (383,383,'2014-05-29 14:20:44','2014-05-29 14:20:44','CALCA JEANS INF FEM',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (384,384,'2014-05-29 14:20:44','2014-05-29 14:20:44','CALCA COLOR INF FEM',0,5,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (385,385,'2014-05-29 14:20:44','2014-05-29 14:20:44',NULL,0,5,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (386,386,'2014-05-29 14:20:44','2014-05-29 14:20:44','BLUSA LA INF FEM',0,5,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (387,387,'2014-05-29 14:20:44','2014-05-29 14:20:44','JAQUETA FORRADA INF FEM',0,5,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (389,389,'2014-05-29 14:20:44','2014-05-29 14:20:44',NULL,0,5,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (401,401,'2014-05-29 14:20:44','2014-05-29 14:20:44','JOGO LENCOL CASAL',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (402,402,'2014-05-29 14:20:44','2014-05-29 14:20:44','COBERTOR E MANTA CASAL',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (403,403,'2014-05-29 14:20:44','2014-05-29 14:20:44','COLCHA CASAL',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (404,404,'2014-05-29 14:20:44','2014-05-29 14:20:44','EDREDON CASAL',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (405,405,'2014-05-29 14:20:44','2014-05-29 14:20:44','TRAVESSEIRO',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (406,406,'2014-05-29 14:20:44','2014-05-29 14:20:44','FRONHA',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (407,407,'2014-05-29 14:20:45','2014-05-29 14:20:45','TOALHA MESA',0,6,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (408,408,'2014-05-29 14:20:45','2014-05-29 14:20:45','COPA E COZINHA',0,6,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (409,409,'2014-05-29 14:20:45','2014-05-29 14:20:45','TOALHA ROSTO',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (410,410,'2014-05-29 14:20:45','2014-05-29 14:20:45','TOALHA BANHO',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (411,411,'2014-05-29 14:20:45','2014-05-29 14:20:45','JOGO BANHO',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (412,412,'2014-05-29 14:20:45','2014-05-29 14:20:45','JOGO BANHEIRO',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (413,413,'2014-05-29 14:20:45','2014-05-29 14:20:45','CAPA COLCHAO',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (414,414,'2014-05-29 14:20:45','2014-05-29 14:20:45','JOGO LENCOL SOLT',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (415,415,'2014-05-29 14:20:45','2014-05-29 14:20:45','COBERTOR E MANTA SOLT',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (416,416,'2014-05-29 14:20:45','2014-05-29 14:20:45','COLCHA SOLT',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (417,417,'2014-05-29 14:20:45','2014-05-29 14:20:45','EDREDON SOLT',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (418,418,'2014-05-29 14:20:45','2014-05-29 14:20:45','TOALHA SOCIAL',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (419,419,'2014-05-29 14:20:45','2014-05-29 14:20:45','CORTINA',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (420,420,'2014-05-29 14:20:45','2014-05-29 14:20:45','TAPETE',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (421,421,'2014-05-29 14:20:45','2014-05-29 14:20:45','ACESSORIOS C-M-B',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (422,422,'2014-05-29 14:20:45','2014-05-29 14:20:45','CAPA ESTOFADO',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (423,423,'2014-05-29 14:20:45','2014-05-29 14:20:45','CAPA ACOLCHOADO',0,6,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (501,501,'2014-05-29 14:20:45','2014-05-29 14:20:45',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (502,502,'2014-05-29 14:20:45','2014-05-29 14:20:45',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (503,503,'2014-05-29 14:20:45','2014-05-29 14:20:45',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (504,504,'2014-05-29 14:20:45','2014-05-29 14:20:45',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (505,505,'2014-05-29 14:20:45','2014-05-29 14:20:45',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (506,506,'2014-05-29 14:20:45','2014-05-29 14:20:45',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (507,507,'2014-05-29 14:20:45','2014-05-29 14:20:45','CAMISETA MM AD',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (508,508,'2014-05-29 14:20:45','2014-05-29 14:20:45','CAMISETA MM INF',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (509,509,'2014-05-29 14:20:45','2014-05-29 14:20:45','REGATA AD',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (510,510,'2014-05-29 14:20:45','2014-05-29 14:20:45','REGATA INF',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (513,513,'2014-05-29 14:20:45','2014-05-29 14:20:45',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (514,514,'2014-05-29 14:20:45','2014-05-29 14:20:45',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (515,515,'2014-05-29 14:20:45','2014-05-29 14:20:45',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (516,516,'2014-05-29 14:20:46','2014-05-29 14:20:46','CONJ VERAO FEM INF',0,7,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (517,517,'2014-05-29 14:20:46','2014-05-29 14:20:46','CONJ VERAO MASC INF',0,7,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (518,518,'2014-05-29 14:20:46','2014-05-29 14:20:46',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (519,519,'2014-05-29 14:20:46','2014-05-29 14:20:46','BERMUDA MALHA FEM',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (520,520,'2014-05-29 14:20:46','2014-05-29 14:20:46','BERMUDA MALHA INF',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (521,521,'2014-05-29 14:20:46','2014-05-29 14:20:46','BLUSA MOLETON AD MASC',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (522,522,'2014-05-29 14:20:46','2014-05-29 14:20:46','BLUSA MOLETON INF MASC',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (523,523,'2014-05-29 14:20:46','2014-05-29 14:20:46','CONJ MOLETON AD MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (524,524,'2014-05-29 14:20:46','2014-05-29 14:20:46','CONJ MOLETON INF MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (525,525,'2014-05-29 14:20:46','2014-05-29 14:20:46','CALCA MOLETON AD MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (526,526,'2014-05-29 14:20:46','2014-05-29 14:20:46','CALCA MOLETON INF MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (527,527,'2014-05-29 14:20:46','2014-05-29 14:20:46','CALCA COTTON AD',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (528,528,'2014-05-29 14:20:46','2014-05-29 14:20:46','CALCA COTTON INF',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (529,529,'2014-05-29 14:20:46','2014-05-29 14:20:46','JAQUETA MOLETON MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (530,530,'2014-05-29 14:20:46','2014-05-29 14:20:46','JAQUETA MOLETON FEM',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (531,531,'2014-05-29 14:20:46','2014-05-29 14:20:46','JAQUETA MOLETON INF MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (532,532,'2014-05-29 14:20:46','2014-05-29 14:20:46',NULL,0,7,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (536,536,'2014-05-29 14:20:46','2014-05-29 14:20:46','CAMISETA MF ML AD',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (537,537,'2014-05-29 14:20:46','2014-05-29 14:20:46','CAMISETA MF ML INF',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (538,538,'2014-05-29 14:20:46','2014-05-29 14:20:46','BLUSA MOLETON AD FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (539,539,'2014-05-29 14:20:46','2014-05-29 14:20:46','BLUSA MOLETON INF FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (540,540,'2014-05-29 14:20:46','2014-05-29 14:20:46','CONJ MOLETON AD FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (541,541,'2014-05-29 14:20:46','2014-05-29 14:20:46','CONJ MOLETON INF FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (542,542,'2014-05-29 14:20:46','2014-05-29 14:20:46','CALCA MOLETON AD FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (543,543,'2014-05-29 14:20:46','2014-05-29 14:20:46','CALCA MOLETON INF FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (544,544,'2014-05-29 14:20:46','2014-05-29 14:20:46','JAQUETA MOLETON INF FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (551,551,'2014-05-29 14:20:46','2014-05-29 14:20:46','PIJAMA CURTO MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (552,552,'2014-05-29 14:20:46','2014-05-29 14:20:46','PIJAMA CURTO FEM',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (553,553,'2014-05-29 14:20:47','2014-05-29 14:20:47','PIJAMA CURTO INF MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (554,554,'2014-05-29 14:20:47','2014-05-29 14:20:47','PIJAMA LONGO MF MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (555,555,'2014-05-29 14:20:47','2014-05-29 14:20:47','PIJAMA LONGO MF FEM',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (556,556,'2014-05-29 14:20:47','2014-05-29 14:20:47','PIJAMA LONGO MF INF MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (557,557,'2014-05-29 14:20:47','2014-05-29 14:20:47','PIJAMA MG MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (558,558,'2014-05-29 14:20:47','2014-05-29 14:20:47','PIJAMA MG FEM',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (559,559,'2014-05-29 14:20:47','2014-05-29 14:20:47','PIJAMA MG INF MASC',0,7,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (560,560,'2014-05-29 14:20:47','2014-05-29 14:20:47','MINHOCAO MASCULINO',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (561,561,'2014-05-29 14:20:47','2014-05-29 14:20:47','MINHOCAO FEMININO',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (562,562,'2014-05-29 14:20:47','2014-05-29 14:20:47','MINHOCAO INFANTIL',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (563,563,'2014-05-29 14:20:47','2014-05-29 14:20:47','CAMISETA DORMIR MASC',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (564,564,'2014-05-29 14:20:47','2014-05-29 14:20:47','CAMISETA DORMIR FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (565,565,'2014-05-29 14:20:47','2014-05-29 14:20:47','CAMISETA DORMIR INF',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (566,566,'2014-05-29 14:20:47','2014-05-29 14:20:47','PIJAMA CURTO INF FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (567,567,'2014-05-29 14:20:47','2014-05-29 14:20:47','PIJAMA LONGO MF INF FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (568,568,'2014-05-29 14:20:47','2014-05-29 14:20:47','PIJAMA MG INF FEM',0,7,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (601,601,'2014-05-29 14:20:47','2014-05-29 14:20:47','BOLSA FEM',0,8,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (602,602,'2014-05-29 14:20:47','2014-05-29 14:20:47','CARTEIRA MASC',0,8,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (603,603,'2014-05-29 14:20:47','2014-05-29 14:20:47','BOLSA P/VIAGEM',0,8,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (604,604,'2014-05-29 14:20:47','2014-05-29 14:20:47','CINTO INF MASC',0,8,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (605,605,'2014-05-29 14:20:47','2014-05-29 14:20:47','CINTO MASC',0,8,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (606,606,'2014-05-29 14:20:47','2014-05-29 14:20:47','CINTO FEM',0,8,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (607,607,'2014-05-29 14:20:47','2014-05-29 14:20:47','CINTO INF FEM',0,8,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (608,608,'2014-05-29 14:20:47','2014-05-29 14:20:47','CARTEIRA FEM',0,8,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (610,610,'2014-05-29 14:20:47','2014-05-29 14:20:47','GUARDA-CHUVA',0,8,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (611,611,'2014-05-29 14:20:47','2014-05-29 14:20:47','SOMBRINHA',0,8,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (612,612,'2014-05-29 14:20:47','2014-05-29 14:20:47','PASTA E MOCHILA',0,8,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (613,613,'2014-05-29 14:20:47','2014-05-29 14:20:47','LANCHEIRA',0,8,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (614,614,'2014-05-29 14:20:47','2014-05-29 14:20:47','ESTOJO ESCOLAR',0,8,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (620,620,'2014-05-29 14:20:48','2014-05-29 14:20:48','SAPATO BEBE MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (621,621,'2014-05-29 14:20:48','2014-05-29 14:20:48','SANDALIA BEBE MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (622,622,'2014-05-29 14:20:48','2014-05-29 14:20:48','TENIS BEBE MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (625,625,'2014-05-29 14:20:48','2014-05-29 14:20:48','SAPATO BEBE FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (626,626,'2014-05-29 14:20:48','2014-05-29 14:20:48','SANDALIA BEBE FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (627,627,'2014-05-29 14:20:48','2014-05-29 14:20:48','TENIS BEBE FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (630,630,'2014-05-29 14:20:48','2014-05-29 14:20:48',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (631,631,'2014-05-29 14:20:48','2014-05-29 14:20:48','BOTA INF MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (632,632,'2014-05-29 14:20:48','2014-05-29 14:20:48','SANDALIA INF MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (633,633,'2014-05-29 14:20:48','2014-05-29 14:20:48','CHINELO INF MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (634,634,'2014-05-29 14:20:48','2014-05-29 14:20:48','TENIS INF MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (640,640,'2014-05-29 14:20:49','2014-05-29 14:20:49','SAPATO INF FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (641,641,'2014-05-29 14:20:49','2014-05-29 14:20:49','BOTA INF FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (642,642,'2014-05-29 14:20:49','2014-05-29 14:20:49','SANDALIA INF FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (643,643,'2014-05-29 14:20:49','2014-05-29 14:20:49','CHINELO INF FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (644,644,'2014-05-29 14:20:49','2014-05-29 14:20:49','TENIS INF FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (645,645,'2014-05-29 14:20:49','2014-05-29 14:20:49','SAPATILHA INF FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (650,650,'2014-05-29 14:20:49','2014-05-29 14:20:49','SAPATO AD MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (651,651,'2014-05-29 14:20:49','2014-05-29 14:20:49','BOTA AD MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (652,652,'2014-05-29 14:20:49','2014-05-29 14:20:49','SANDALIA AD MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (653,653,'2014-05-29 14:20:49','2014-05-29 14:20:49','CHINELO AD MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (654,654,'2014-05-29 14:20:49','2014-05-29 14:20:49','TENIS AD MASC',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (655,655,'2014-05-29 14:20:49','2014-05-29 14:20:49','SAPATENIS MASCULINO',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (660,660,'2014-05-29 14:20:49','2014-05-29 14:20:49','SAPATO AD FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (661,661,'2014-05-29 14:20:49','2014-05-29 14:20:49','BOTA AD FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (662,662,'2014-05-29 14:20:49','2014-05-29 14:20:49','SANDALIA AD FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (663,663,'2014-05-29 14:20:49','2014-05-29 14:20:49','CHINELO AD FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (664,664,'2014-05-29 14:20:49','2014-05-29 14:20:49','SAPATILHA AD FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (665,665,'2014-05-29 14:20:49','2014-05-29 14:20:49','TENIS AD FEM',0,9,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (667,667,'2014-05-29 14:20:49','2014-05-29 14:20:49',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (701,701,'2014-05-29 14:20:49','2014-05-29 14:20:49',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (702,702,'2014-05-29 14:20:49','2014-05-29 14:20:49',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (705,705,'2014-05-29 14:20:49','2014-05-29 14:20:49',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (706,706,'2014-05-29 14:20:49','2014-05-29 14:20:49',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (707,707,'2014-05-29 14:20:49','2014-05-29 14:20:49',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (708,708,'2014-05-29 14:20:49','2014-05-29 14:20:49','BICHINHO PELUCIA',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (709,709,'2014-05-29 14:20:49','2014-05-29 14:20:49',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (710,710,'2014-05-29 14:20:49','2014-05-29 14:20:49',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (711,711,'2014-05-29 14:20:49','2014-05-29 14:20:49','PERFUME FEM',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (712,712,'2014-05-29 14:20:50','2014-05-29 14:20:50','DES.FEM',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (713,713,'2014-05-29 14:20:50','2014-05-29 14:20:50',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (714,714,'2014-05-29 14:20:50','2014-05-29 14:20:50',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (715,715,'2014-05-29 14:20:50','2014-05-29 14:20:50',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (716,716,'2014-05-29 14:20:50','2014-05-29 14:20:50','BARBIE',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (721,721,'2014-05-29 14:20:50','2014-05-29 14:20:50','SKIN SO SOFT',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (722,722,'2014-05-29 14:20:50','2014-05-29 14:20:50','MODA/CASA',0,9,30);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (723,723,'2014-05-29 14:20:50','2014-05-29 14:20:50','SUN',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (724,724,'2014-05-29 14:20:50','2014-05-29 14:20:50','FOOT WORKS',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (725,725,'2014-05-29 14:20:50','2014-05-29 14:20:50','CLEAR SKIN',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (726,726,'2014-05-29 14:20:50','2014-05-29 14:20:50','CARE',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (727,727,'2014-05-29 14:20:50','2014-05-29 14:20:50','ACCOLADE',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (728,728,'2014-05-29 14:20:50','2014-05-29 14:20:50','ERVA DOCE',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (729,729,'2014-05-29 14:20:50','2014-05-29 14:20:50','LIV BOTANICALS',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (730,730,'2014-05-29 14:20:50','2014-05-29 14:20:50','NATURALS',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (731,731,'2014-05-29 14:20:50','2014-05-29 14:20:50','RENEW',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (732,732,'2014-05-29 14:20:50','2014-05-29 14:20:50','SOLUTIONS',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (733,733,'2014-05-29 14:20:50','2014-05-29 14:20:50','BATOM/BRILHO/GLOSS',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (734,734,'2014-05-29 14:20:50','2014-05-29 14:20:50','SOMBRA',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (735,735,'2014-05-29 14:20:50','2014-05-29 14:20:50',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (740,740,'2014-05-29 14:20:50','2014-05-29 14:20:50',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (741,741,'2014-05-29 14:20:50','2014-05-29 14:20:50','MASCARA/LAPIS/DELINEADOR',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (742,742,'2014-05-29 14:20:50','2014-05-29 14:20:50','BASE/PO/CORRETIVO/BLUSH',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (743,743,'2014-05-29 14:20:50','2014-05-29 14:20:50',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (747,747,'2014-05-29 14:20:50','2014-05-29 14:20:50',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (748,748,'2014-05-29 14:20:50','2014-05-29 14:20:50','BABY GENTLE',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (750,750,'2014-05-29 14:20:50','2014-05-29 14:20:50','PERFUME MASC',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (751,751,'2014-05-29 14:20:51','2014-05-29 14:20:51','DES.MASC',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (752,752,'2014-05-29 14:20:51','2014-05-29 14:20:51','AT',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (753,753,'2014-05-29 14:20:51','2014-05-29 14:20:51','EU',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (754,754,'2014-05-29 14:20:51','2014-05-29 14:20:51','STAME STIGMA',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (755,755,'2014-05-29 14:20:51','2014-05-29 14:20:51','PERFUME',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (756,756,'2014-05-29 14:20:51','2014-05-29 14:20:51','SABORES',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (757,757,'2014-05-29 14:20:51','2014-05-29 14:20:51','FYO',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (758,758,'2014-05-29 14:20:51','2014-05-29 14:20:51','CAMINHO DAS AGUAS',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (759,759,'2014-05-29 14:20:51','2014-05-29 14:20:51','BEM ME QUER',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (760,760,'2014-05-29 14:20:51','2014-05-29 14:20:51','ROSA DE ESPARTA',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (761,761,'2014-05-29 14:20:51','2014-05-29 14:20:51','LAVANDA DE GRASSE',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (762,762,'2014-05-29 14:20:51','2014-05-29 14:20:51','MARIA MANHA',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (763,763,'2014-05-29 14:20:51','2014-05-29 14:20:51','ABYSSAL',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (764,764,'2014-05-29 14:20:51','2014-05-29 14:20:51','AIRE',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (765,765,'2014-05-29 14:20:51','2014-05-29 14:20:51','DASOS',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (766,766,'2014-05-29 14:20:51','2014-05-29 14:20:51','UZON FRESH',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (767,767,'2014-05-29 14:20:51','2014-05-29 14:20:51','SOMBRA/LAPIS/MASCARA/DELI',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (768,768,'2014-05-29 14:20:51','2014-05-29 14:20:51','BATON/BRILHO/GLOSS/LAPIS',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (769,769,'2014-05-29 14:20:51','2014-05-29 14:20:51','BASE/PO/CORRETIVO/BLUSH2',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (770,770,'2014-05-29 14:20:51','2014-05-29 14:20:51','ELAS',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (771,771,'2014-05-29 14:20:51','2014-05-29 14:20:51','INFANTIL',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (772,772,'2014-05-29 14:20:51','2014-05-29 14:20:51','JEQVIDA',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (773,773,'2014-05-29 14:20:51','2014-05-29 14:20:51','PROTECAO',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (774,774,'2014-05-29 14:20:51','2014-05-29 14:20:51','ERVA DOCE 2',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (775,775,'2014-05-29 14:20:51','2014-05-29 14:20:51','FOTOSIM',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (776,776,'2014-05-29 14:20:51','2014-05-29 14:20:51','FASES',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (777,777,'2014-05-29 14:20:51','2014-05-29 14:20:51','CRESCER/SUAVE',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (778,778,'2014-05-29 14:20:52','2014-05-29 14:20:52','EMBALAGENS',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (779,779,'2014-05-29 14:20:52','2014-05-29 14:20:52','KIT',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (781,781,'2014-05-29 14:20:52','2014-05-29 14:20:52',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (783,783,'2014-05-29 14:20:52','2014-05-29 14:20:52','EMBALAGENS CAIXA',0,9,30);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (788,788,'2014-05-29 14:20:52','2014-05-29 14:20:52','EMBALAGENS SACOS',0,9,30);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (789,789,'2014-05-29 14:20:52','2014-05-29 14:20:52','ESTOJO DIA D',0,9,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (790,790,'2014-05-29 14:20:52','2014-05-29 14:20:52','EMBALAGENS 2',0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (791,791,'2014-05-29 14:20:52','2014-05-29 14:20:52',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (795,795,'2014-05-29 14:20:52','2014-05-29 14:20:52',NULL,0,9,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (801,801,'2014-05-29 14:20:52','2014-05-29 14:20:52','MAIO INF',0,10,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (802,802,'2014-05-29 14:20:52','2014-05-29 14:20:52','BIQUINI INF',0,10,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (803,803,'2014-05-29 14:20:52','2014-05-29 14:20:52','SUNGA INF',0,10,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (804,804,'2014-05-29 14:20:52','2014-05-29 14:20:52','MAIO AD',0,10,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (805,805,'2014-05-29 14:20:52','2014-05-29 14:20:52','BIQUINI AD',0,10,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (806,806,'2014-05-29 14:20:52','2014-05-29 14:20:52','SUNGA AD',0,10,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (807,807,'2014-05-29 14:20:52','2014-05-29 14:20:52','ACESSORIOS PRAIA',0,10,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (808,808,'2014-05-29 14:20:52','2014-05-29 14:20:52','MAIO BEBE',0,10,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (809,809,'2014-05-29 14:20:52','2014-05-29 14:20:52',NULL,0,10,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (810,810,'2014-05-29 14:20:52','2014-05-29 14:20:52','AFLORA',0,10,-5);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (811,811,'2014-05-29 14:20:52','2014-05-29 14:20:52','PERFUME FEM',0,10,-5);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (812,812,'2014-05-29 14:20:52','2014-05-29 14:20:52','PERFUME MASC',0,10,-5);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (813,813,'2014-05-29 14:20:53','2014-05-29 14:20:53','NEO',0,10,-5);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (814,814,'2014-05-29 14:20:53','2014-05-29 14:20:53','SOUL',0,10,-5);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (815,815,'2014-05-29 14:20:53','2014-05-29 14:20:53','PODEROSA',0,10,-5);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (816,816,'2014-05-29 14:20:53','2014-05-29 14:20:53','ENTRE 4 PAREDES',0,10,-5);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (817,817,'2014-05-29 14:20:53','2014-05-29 14:20:53','FLORATTA',0,10,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (818,818,'2014-05-29 14:20:53','2014-05-29 14:20:53','TEENS',0,10,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (819,819,'2014-05-29 14:20:53','2014-05-29 14:20:53','CAPRICHO',0,10,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (820,820,'2014-05-29 14:20:53','2014-05-29 14:20:53','MAKE B',0,10,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (821,821,'2014-05-29 14:20:53','2014-05-29 14:20:53','INTENSE',0,10,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (822,822,'2014-05-29 14:20:53','2014-05-29 14:20:53','ACTIVE',0,10,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (823,823,'2014-05-29 14:20:53','2014-05-29 14:20:53','CUIDE-SE',0,10,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (824,824,'2014-05-29 14:20:53','2014-05-29 14:20:53','GOLDEN PLUS',0,10,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (825,825,'2014-05-29 14:20:53','2014-05-29 14:20:53','NATIVA SPA',0,10,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (901,901,'2014-05-29 14:20:53','2014-05-29 14:20:53','TECIDO 220',0,11,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (902,902,'2014-05-29 14:20:53','2014-05-29 14:20:53','ATOALHADO',0,11,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (903,903,'2014-05-29 14:20:53','2014-05-29 14:20:53','CORTINADO',0,11,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (904,904,'2014-05-29 14:20:53','2014-05-29 14:20:53','TECIDO FORRO',0,11,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (905,905,'2014-05-29 14:20:53','2014-05-29 14:20:53','MORIN',0,11,10);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (908,908,'2014-05-29 14:20:53','2014-05-29 14:20:53','RENDA CORTINA',0,11,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (910,910,'2014-05-29 14:20:53','2014-05-29 14:20:53','BRIM',0,11,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (911,911,'2014-05-29 14:20:53','2014-05-29 14:20:53',NULL,0,11,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (912,912,'2014-05-29 14:20:53','2014-05-29 14:20:53',NULL,0,11,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (913,913,'2014-05-29 14:20:53','2014-05-29 14:20:53',NULL,0,11,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (914,914,'2014-05-29 14:20:53','2014-05-29 14:20:53','FLANELA COMUM',0,11,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (915,915,'2014-05-29 14:20:53','2014-05-29 14:20:53','POPELINE 140 ESTAMPADA',0,11,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (916,916,'2014-05-29 14:20:53','2014-05-29 14:20:53','TECIDO FRALDA',0,11,12);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (917,917,'2014-05-29 14:20:54','2014-05-29 14:20:54','TECIDO CAMA MENOS 220',0,11,15);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (920,920,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,11,20);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (921,921,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,11,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (922,922,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,11,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (930,930,'2014-05-29 14:20:54','2014-05-29 14:20:54','RETALHOS',0,11,5);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (951,951,'2014-05-29 14:20:54','2014-05-29 14:20:54','ACESSORIOS P/CORTINA',0,12,20);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (955,955,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,12,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (960,960,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,12,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (961,961,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,12,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (962,962,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,12,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (963,963,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,12,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (964,964,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,12,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (965,965,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,12,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (966,966,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,12,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (967,967,'2014-05-29 14:20:54','2014-05-29 14:20:54',NULL,0,12,0);
INSERT INTO `est_subdepto` (`id`, `codigo`, `inserted`, `updated`, `nome`, `version`, `depto_id`, `margem`) VALUES (999,999,'2014-05-29 14:20:54','2014-05-29 14:20:54','DIVERSOS',0,13,0);
/*!40000 ALTER TABLE `est_subdepto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_tipo_produto`
--

DROP TABLE IF EXISTS `est_tipo_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_tipo_produto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chave` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `tipo` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  `subdepto_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_duik7b8tijvxee6ioc3ykvgws` (`chave`),
  KEY `FK_ipnmoq08h2494k63vbjqi8fav` (`subdepto_id`),
  CONSTRAINT `FK_ipnmoq08h2494k63vbjqi8fav` FOREIGN KEY (`subdepto_id`) REFERENCES `est_subdepto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_tipo_produto`
--
-- ORDER BY:  `id`

LOCK TABLES `est_tipo_produto` WRITE;
/*!40000 ALTER TABLE `est_tipo_produto` DISABLE KEYS */;
/*!40000 ALTER TABLE `est_tipo_produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `est_unidade_produto`
--

DROP TABLE IF EXISTS `est_unidade_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `est_unidade_produto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `fator` int(11) NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `label` varchar(5) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_29tobej4sjmll8cceivtu0qwc` (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `est_unidade_produto`
--
-- ORDER BY:  `id`

LOCK TABLES `est_unidade_produto` WRITE;
/*!40000 ALTER TABLE `est_unidade_produto` DISABLE KEYS */;
/*!40000 ALTER TABLE `est_unidade_produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lc_unidade_produto`
--

DROP TABLE IF EXISTS `lc_unidade_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lc_unidade_produto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `fator` int(11) NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `label` varchar(5) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c1ojucb97ycug5o7v66963dwr` (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lc_unidade_produto`
--
-- ORDER BY:  `id`

LOCK TABLES `lc_unidade_produto` WRITE;
/*!40000 ALTER TABLE `lc_unidade_produto` DISABLE KEYS */;
/*!40000 ALTER TABLE `lc_unidade_produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rh_total_dia_vendedor`
--

DROP TABLE IF EXISTS `rh_total_dia_vendedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rh_total_dia_vendedor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dia` datetime NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `total` double NOT NULL,
  `version` int(11) NOT NULL,
  `funcionario_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cr890yevf0wo743qdvipswn49` (`funcionario_id`,`dia`),
  CONSTRAINT `FK_h7hfiiap8krywgat5ebbwcyo7` FOREIGN KEY (`funcionario_id`) REFERENCES `bon_funcionario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1363 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rh_total_dia_vendedor`
--
-- ORDER BY:  `id`

LOCK TABLES `rh_total_dia_vendedor` WRITE;
/*!40000 ALTER TABLE `rh_total_dia_vendedor` DISABLE KEYS */;
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (17,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',208.8,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (18,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',1210.08,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (19,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',1103.36,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (20,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',966.81,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (21,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',230.15,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (22,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',567.38,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (23,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',1554.91,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (24,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',93.51,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (25,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',1225.63,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (26,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',1258.62,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (27,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',257.75,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (28,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',129.2,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (29,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',929.62,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (30,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',961.34,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (31,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',1036.27,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (32,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',96.93,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (33,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',995.82,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (34,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',877.6,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (35,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',13.59,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (36,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',1627.22,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (37,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',1255.01,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (38,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',808.5,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (39,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',31.86,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (40,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',640.93,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (41,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',1824.34,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (42,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',227.34,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (43,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',1315.2,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (44,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',23.04,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (45,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',371.09,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (46,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',20.97,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (47,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',839.16,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (48,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',1020.99,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (49,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',621.36,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (50,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',668.45,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (51,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',84,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (52,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',70.65,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (53,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',1185.3,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (54,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',868.62,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (55,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',476.59,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (56,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',88.2,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (57,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',701.59,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (58,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',884.62,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (59,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',37.98,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (60,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',229.99,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (61,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',706.44,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (62,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',367.47,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (63,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',1168.02,0,42);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (64,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',86.42,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (65,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',87.33,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (66,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',1122.86,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (67,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',848.58,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (68,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',1438.37,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (69,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',13.86,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (70,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',1075.24,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (71,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',10.8,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (72,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',644.66,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (73,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',159.3,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (74,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',1900.86,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (75,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',690.76,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (76,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',347.29,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (77,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',675.39,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (78,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',999.96,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (79,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',2335.59,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (80,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',114.39,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (81,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',648.19,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (82,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',767,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (83,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',41.22,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (84,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',700.5,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (85,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',958.37,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (86,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',1161.24,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (87,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',310.97,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (88,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',1037.53,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (89,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',760.67,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (90,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',943.4,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (91,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',404.54,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (92,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',735.04,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (93,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',152.6,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (94,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',737.57,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (95,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',107.28,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (96,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',1057.26,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (97,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',364.97,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (98,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',179.01,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (99,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',3381.57,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (100,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',247.03,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (101,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',135.09,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (102,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',763.34,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (103,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',205.84,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (104,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',1086.51,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (105,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',364.58,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (106,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',544.03,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (107,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',30.06,0,42);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (108,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',136.72,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (109,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',356.38,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (110,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',1258.33,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (111,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',519.3,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (112,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',130,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (113,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',560.42,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (114,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',975.3,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (115,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',239.13,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (116,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',1196.07,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (117,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',701.21,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (118,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',12.6,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (119,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',733.5,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (120,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',672.4,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (121,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',96.62,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (122,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',161.48,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (123,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',54.9,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (124,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',242.91,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (125,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',816.4,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (126,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',477.7,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (127,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',410.95,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (128,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',158.6,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (129,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',394.91,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (130,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',240.05,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (131,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',148.46,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (132,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',140.93,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (133,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',378.23,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (134,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',308.95,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (135,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',182.8,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (136,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',1136.95,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (137,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',62.37,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (138,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',781.31,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (139,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',295.66,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (140,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',192.6,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (141,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',240.68,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (142,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',64.53,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (143,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',15.75,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (144,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',134,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (145,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',889.55,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (146,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (147,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',652.13,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (148,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',179.4,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (149,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',1289.13,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (150,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',2083.06,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (151,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',582.18,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (152,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',205.4,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (153,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',1658.43,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (154,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',499.86,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (155,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',245.55,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (156,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',274.05,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (157,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',27.45,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (158,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',311.4,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (159,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',427.03,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (160,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (161,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',452.86,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (162,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',147.25,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (163,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',504.87,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (164,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',154.71,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (165,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',293.72,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (166,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',308.41,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (167,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',917.03,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (168,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',1397.94,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (169,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',579.92,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (170,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',8.91,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (171,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',1279.4,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (172,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',532.98,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (173,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',14.76,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (174,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',1321.96,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (175,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',1752.29,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (176,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',453.19,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (177,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',436.3,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (178,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',854.04,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (179,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',1000.4,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (180,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',418.32,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (181,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',10.71,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (182,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',614.39,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (183,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',173.09,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (184,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',295.12,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (185,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',927.68,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (186,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',423.02,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (187,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',144.6,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (188,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',1125.6,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (189,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',1361.41,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (190,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',1021.72,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (191,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',181.2,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (192,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',110.16,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (193,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',658.37,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (194,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',322.24,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (195,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',76.23,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (196,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',812.14,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (197,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',840.05,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (198,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',75.52,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (206,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',751.51,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (207,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',980.42,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (208,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',713.63,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (209,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',789.75,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (210,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',117.7,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (211,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',566.39,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (212,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',384.96,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (213,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',47.88,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (214,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',892.86,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (215,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',327.51,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (216,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',479.74,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (217,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',356.78,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (218,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',728.99,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (219,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',41.67,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (220,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',689.37,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (221,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',421.47,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (222,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (223,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',474.35,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (224,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',687.68,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (225,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',718.53,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (226,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',620.83,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (227,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',354.52,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (228,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',613.92,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (229,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',799.02,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (230,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',1326.86,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (231,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',63.31,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (232,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',284.11,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (233,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',259.11,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (234,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (235,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',325.59,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (236,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',236.92,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (237,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',987.17,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (238,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',472.77,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (239,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',157.47,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (240,'2014-04-11 00:00:00','2014-05-09 09:34:43','2014-05-09 09:34:43',759.49,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (241,'2014-04-11 00:00:00','2014-05-09 09:34:43','2014-05-09 09:34:43',1219.03,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (242,'2014-04-11 00:00:00','2014-05-09 09:34:43','2014-05-09 09:34:43',727.68,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (243,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',578.1,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (244,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',186.7,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (245,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',0,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (246,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',776.64,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (247,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (248,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',627.44,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (249,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',146.09,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (250,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',1090.81,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (251,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',100,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (252,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',0,0,53);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (253,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',135.76,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (254,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',138.2,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (255,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',884.67,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (256,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',565.4,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (257,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',145.53,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (258,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',459.81,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (259,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',1020.05,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (260,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',366.75,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (261,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (262,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',616.8,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (263,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',170.77,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (264,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',1402.1,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (265,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',243.04,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (266,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',87.34,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (267,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',248.39,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (268,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',510.18,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (269,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',359.64,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (270,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',20,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (271,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',471.36,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (272,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',886.15,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (273,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',188.59,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (274,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',673.04,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (275,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',537.51,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (276,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',567.44,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (277,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',308.98,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (278,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',1055.68,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (279,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',408.34,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (280,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',731.59,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (281,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',109.98,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (282,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',1069.51,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (283,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',315.03,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (284,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',150.58,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (285,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',595.43,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (286,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',819.12,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (287,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',382.67,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (288,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',1387.85,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (289,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',213.84,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (290,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',849.2,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (291,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',113.13,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (292,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',467.37,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (293,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',1070.19,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (294,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',243.85,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (295,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',281.17,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (296,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',477.91,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (297,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',482.91,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (298,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',140.08,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (299,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',1195.61,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (300,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',539.9,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (301,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',666.57,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (302,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',1518.1,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (303,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',109.86,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (304,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',936.34,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (305,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',242.01,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (306,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',467.82,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (307,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',1133.98,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (308,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',109.38,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (309,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',105.17,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (310,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',124.87,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (311,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',825.6,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (312,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',660.76,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (313,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',704.84,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (314,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',392.73,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (315,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',948.33,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (316,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',873.33,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (317,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',1004.52,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (318,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',415.8,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (319,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',45.66,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (320,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',0,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (321,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',327.57,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (322,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',1006.14,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (323,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',613.23,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (324,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',6.3,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (325,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',733.72,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (326,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',1265.11,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (327,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',68.85,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (328,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',373.19,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (329,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',673.24,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (330,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',167.02,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (331,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',131.45,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (332,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',348.75,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (333,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',616.73,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (334,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',438.16,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (335,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',98.82,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (336,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',429.84,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (337,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',992.32,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (338,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',558.46,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (339,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',653.49,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (340,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',236.37,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (341,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',406.22,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (342,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',628.64,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (343,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',154.86,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (344,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',150.14,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (345,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',0,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (346,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',382.82,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (347,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',46.7,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (348,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',19.98,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (349,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',1113.15,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (350,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',148.93,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (351,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',1226.27,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (352,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',744.62,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (353,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',86.19,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (354,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',158.22,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (355,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',1217.45,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (356,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',728.9,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (357,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',608.12,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (358,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',539.57,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (359,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',757.44,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (360,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',1037.64,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (361,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',1742.18,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (362,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',376.38,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (363,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',492.94,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (364,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',52.47,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (365,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',685.66,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (366,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',1100.97,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (367,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',911.09,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (368,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',27.27,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (369,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',787.48,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (370,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',126.45,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (371,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',670.83,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (372,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',83.07,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (373,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',1469.6,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (374,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',813.03,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (375,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',969.4,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (376,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',229.97,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (377,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',149.82,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (378,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',2206.51,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (379,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',1043.41,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (380,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',2821.29,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (381,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',54.72,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (382,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',2357.14,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (383,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',977.43,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (384,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',2711.95,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (385,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',892.58,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (386,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',2192.38,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (387,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',3546.42,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (388,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',1587.01,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (389,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',118.91,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (390,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',98.4,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (391,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',1042.79,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (392,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',746.07,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (393,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',844.34,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (394,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',52.88,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (395,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',841.09,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (396,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',68.85,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (397,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',212.13,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (398,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',1512.88,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (399,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',610.93,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (400,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',968.75,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (401,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',11.43,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (402,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',35.01,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (403,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',520.72,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (404,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',296.98,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (405,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',923.45,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (406,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',4.5,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (407,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',402.05,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (408,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',828.03,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (409,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',219.9,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (410,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',971.91,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (411,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',646.21,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (412,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',578.91,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (413,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',269.85,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (414,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',872.11,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (415,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',805.2,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (416,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',347.45,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (417,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',0,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (418,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',159.68,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (419,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',1152.65,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (420,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',290.19,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (421,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',721.27,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (422,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',1051.56,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (423,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',313.42,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (424,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',545.31,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (425,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',9,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (426,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',802.71,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (427,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',1201.43,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (428,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',777.54,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (429,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',892.54,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (430,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',156.96,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (431,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',990.4,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (432,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',502.05,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (433,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',404.95,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (434,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',344.05,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (435,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',226.74,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (436,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',923.69,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (437,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',336.41,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (438,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',480.42,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (439,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',79.83,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (440,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',622.58,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (441,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',781.53,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (442,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',1021.98,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (443,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',9.45,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (444,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',613.48,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (445,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',85.8,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (446,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',26.37,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (447,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',1857.16,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (448,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',1035.56,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (449,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',1284.28,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (450,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',9.81,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (451,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',605.2,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (452,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',630.4,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (453,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',233.84,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (454,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',1586.33,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (455,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',632.49,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (456,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',596.05,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (457,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',351.21,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (458,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',484.69,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (459,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',266.87,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (460,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',417.83,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (461,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',561.16,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (462,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',752.01,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (463,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',1277.26,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (464,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',228.87,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (465,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',165.77,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (466,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',1423.16,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (467,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',165.51,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (468,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',89.28,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (469,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',396.43,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (470,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',146.65,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (471,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',300,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (472,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',438.86,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (473,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',49.5,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (474,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',522.17,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (475,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',307.67,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (476,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',371.47,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (477,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',91.95,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (478,'2014-03-31 00:00:00','2014-05-23 16:31:29','2014-05-23 16:31:29',1014.87,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (479,'2014-03-31 00:00:00','2014-05-23 16:31:29','2014-05-23 16:31:29',274.73,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (480,'2014-03-31 00:00:00','2014-05-23 16:31:29','2014-05-23 16:31:29',745.28,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (481,'2014-03-31 00:00:00','2014-05-23 16:31:29','2014-05-23 16:31:29',701.83,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (482,'2014-03-31 00:00:00','2014-05-23 16:31:29','2014-05-23 16:31:29',149.94,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (483,'2014-03-31 00:00:00','2014-05-23 16:31:29','2014-05-23 16:31:29',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (484,'2014-03-31 00:00:00','2014-05-23 16:31:29','2014-05-23 16:31:29',39.95,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (485,'2014-03-31 00:00:00','2014-05-23 16:31:29','2014-05-23 16:31:29',362.75,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (486,'2014-03-31 00:00:00','2014-05-23 16:31:29','2014-05-23 16:31:29',184.06,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (487,'2014-03-29 00:00:00','2014-05-23 16:31:39','2014-05-23 16:31:39',935.33,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (488,'2014-03-29 00:00:00','2014-05-23 16:31:39','2014-05-23 16:31:39',217.09,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (489,'2014-03-29 00:00:00','2014-05-23 16:31:39','2014-05-23 16:31:39',563.44,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (490,'2014-03-29 00:00:00','2014-05-23 16:31:39','2014-05-23 16:31:39',330.66,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (491,'2014-03-29 00:00:00','2014-05-23 16:31:39','2014-05-23 16:31:39',792.07,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (492,'2014-03-29 00:00:00','2014-05-23 16:31:39','2014-05-23 16:31:39',17.91,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (493,'2014-03-29 00:00:00','2014-05-23 16:31:39','2014-05-23 16:31:39',505.82,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (494,'2014-03-29 00:00:00','2014-05-23 16:31:39','2014-05-23 16:31:39',1031.19,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (495,'2014-03-29 00:00:00','2014-05-23 16:31:39','2014-05-23 16:31:39',492.66,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (496,'2014-03-29 00:00:00','2014-05-23 16:31:39','2014-05-23 16:31:39',151.72,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (497,'2014-03-28 00:00:00','2014-05-23 16:31:49','2014-05-23 16:31:49',431.73,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (498,'2014-03-28 00:00:00','2014-05-23 16:31:49','2014-05-23 16:31:49',560.68,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (499,'2014-03-28 00:00:00','2014-05-23 16:31:49','2014-05-23 16:31:49',247.52,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (500,'2014-03-28 00:00:00','2014-05-23 16:31:49','2014-05-23 16:31:49',813.89,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (501,'2014-03-28 00:00:00','2014-05-23 16:31:49','2014-05-23 16:31:49',911.43,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (502,'2014-03-28 00:00:00','2014-05-23 16:31:49','2014-05-23 16:31:49',561.85,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (503,'2014-03-28 00:00:00','2014-05-23 16:31:49','2014-05-23 16:31:49',191.78,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (504,'2014-03-28 00:00:00','2014-05-23 16:31:49','2014-05-23 16:31:49',175.3,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (505,'2014-03-28 00:00:00','2014-05-23 16:31:49','2014-05-23 16:31:49',62.98,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (506,'2014-03-28 00:00:00','2014-05-23 16:31:49','2014-05-23 16:31:49',34.31,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (507,'2014-03-27 00:00:00','2014-05-23 16:31:58','2014-05-23 16:31:58',806,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (508,'2014-03-27 00:00:00','2014-05-23 16:31:58','2014-05-23 16:31:58',281.7,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (509,'2014-03-27 00:00:00','2014-05-23 16:31:58','2014-05-23 16:31:58',311.4,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (510,'2014-03-27 00:00:00','2014-05-23 16:31:58','2014-05-23 16:31:58',688.37,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (511,'2014-03-27 00:00:00','2014-05-23 16:31:58','2014-05-23 16:31:58',2315.06,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (512,'2014-03-27 00:00:00','2014-05-23 16:31:58','2014-05-23 16:31:58',358.22,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (513,'2014-03-27 00:00:00','2014-05-23 16:31:58','2014-05-23 16:31:58',316.57,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (514,'2014-03-27 00:00:00','2014-05-23 16:31:58','2014-05-23 16:31:58',396,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (515,'2014-03-26 00:00:00','2014-05-23 16:32:08','2014-05-23 16:32:08',49.86,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (516,'2014-03-26 00:00:00','2014-05-23 16:32:08','2014-05-23 16:32:08',535.82,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (517,'2014-03-26 00:00:00','2014-05-23 16:32:08','2014-05-23 16:32:08',723.47,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (518,'2014-03-26 00:00:00','2014-05-23 16:32:08','2014-05-23 16:32:08',514.35,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (519,'2014-03-26 00:00:00','2014-05-23 16:32:08','2014-05-23 16:32:08',319.23,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (520,'2014-03-26 00:00:00','2014-05-23 16:32:08','2014-05-23 16:32:08',1011.83,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (521,'2014-03-26 00:00:00','2014-05-23 16:32:08','2014-05-23 16:32:08',70.2,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (522,'2014-03-26 00:00:00','2014-05-23 16:32:08','2014-05-23 16:32:08',128.58,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (523,'2014-03-26 00:00:00','2014-05-23 16:32:08','2014-05-23 16:32:08',205.23,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (524,'2014-03-25 00:00:00','2014-05-23 16:32:17','2014-05-23 16:32:17',17.91,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (525,'2014-03-25 00:00:00','2014-05-23 16:32:17','2014-05-23 16:32:17',990.72,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (526,'2014-03-25 00:00:00','2014-05-23 16:32:17','2014-05-23 16:32:17',984.2,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (527,'2014-03-25 00:00:00','2014-05-23 16:32:17','2014-05-23 16:32:17',776.94,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (528,'2014-03-25 00:00:00','2014-05-23 16:32:18','2014-05-23 16:32:18',392.52,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (529,'2014-03-25 00:00:00','2014-05-23 16:32:18','2014-05-23 16:32:18',412.94,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (530,'2014-03-25 00:00:00','2014-05-23 16:32:18','2014-05-23 16:32:18',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (531,'2014-03-25 00:00:00','2014-05-23 16:32:18','2014-05-23 16:32:18',210,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (532,'2014-03-25 00:00:00','2014-05-23 16:32:18','2014-05-23 16:32:18',695.34,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (533,'2014-03-25 00:00:00','2014-05-23 16:32:18','2014-05-23 16:32:18',583.03,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (534,'2014-03-25 00:00:00','2014-05-23 16:32:18','2014-05-23 16:32:18',47.2,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (535,'2014-03-24 00:00:00','2014-05-23 16:32:27','2014-05-23 16:32:27',232.88,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (536,'2014-03-24 00:00:00','2014-05-23 16:32:27','2014-05-23 16:32:27',805.38,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (537,'2014-03-24 00:00:00','2014-05-23 16:32:27','2014-05-23 16:32:27',521.64,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (538,'2014-03-24 00:00:00','2014-05-23 16:32:27','2014-05-23 16:32:27',895.57,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (539,'2014-03-24 00:00:00','2014-05-23 16:32:27','2014-05-23 16:32:27',783.99,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (540,'2014-03-24 00:00:00','2014-05-23 16:32:27','2014-05-23 16:32:27',833.94,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (541,'2014-03-24 00:00:00','2014-05-23 16:32:27','2014-05-23 16:32:27',262.6,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (542,'2014-03-24 00:00:00','2014-05-23 16:32:27','2014-05-23 16:32:27',985.28,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (543,'2014-03-24 00:00:00','2014-05-23 16:32:27','2014-05-23 16:32:27',320.58,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (544,'2014-03-24 00:00:00','2014-05-23 16:32:27','2014-05-23 16:32:27',3.91,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (545,'2014-03-22 00:00:00','2014-05-23 16:32:36','2014-05-23 16:32:36',1011.82,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (546,'2014-03-22 00:00:00','2014-05-23 16:32:36','2014-05-23 16:32:36',1038.51,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (547,'2014-03-22 00:00:00','2014-05-23 16:32:36','2014-05-23 16:32:36',675.34,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (548,'2014-03-22 00:00:00','2014-05-23 16:32:36','2014-05-23 16:32:36',1116.94,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (549,'2014-03-22 00:00:00','2014-05-23 16:32:37','2014-05-23 16:32:37',27.45,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (550,'2014-03-22 00:00:00','2014-05-23 16:32:37','2014-05-23 16:32:37',1096.38,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (551,'2014-03-22 00:00:00','2014-05-23 16:32:37','2014-05-23 16:32:37',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (552,'2014-03-22 00:00:00','2014-05-23 16:32:37','2014-05-23 16:32:37',719.12,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (553,'2014-03-22 00:00:00','2014-05-23 16:32:37','2014-05-23 16:32:37',872.33,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (554,'2014-03-22 00:00:00','2014-05-23 16:32:37','2014-05-23 16:32:37',663.11,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (555,'2014-03-22 00:00:00','2014-05-23 16:32:37','2014-05-23 16:32:37',282.13,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (556,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',6.03,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (557,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',890.17,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (558,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',416.25,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (559,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',661.38,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (560,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',359.43,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (561,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',44.01,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (562,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',601.06,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (563,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',210.1,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (564,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',1286.16,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (565,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',833.31,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (566,'2014-03-21 00:00:00','2014-05-23 16:32:46','2014-05-23 16:32:46',71.55,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (567,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',41.04,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (568,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',1204.21,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (569,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',1172.8,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (570,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',332.87,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (571,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',130,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (572,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',842.7,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (573,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',812.85,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (574,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',410.71,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (575,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',538.74,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (576,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',64.53,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (577,'2014-03-20 00:00:00','2014-05-23 16:32:56','2014-05-23 16:32:56',231.12,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (578,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',329.02,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (579,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',391.64,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (580,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',597.16,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (581,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',14.85,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (582,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',530.59,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (583,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',49.9,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (584,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',655.56,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (585,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',249.42,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (586,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',611.93,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (587,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',1207.11,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (588,'2014-03-19 00:00:00','2014-05-23 16:33:06','2014-05-23 16:33:06',680.9,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (589,'2014-03-18 00:00:00','2014-05-23 16:33:15','2014-05-23 16:33:15',49.5,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (590,'2014-03-18 00:00:00','2014-05-23 16:33:15','2014-05-23 16:33:15',263.72,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (591,'2014-03-18 00:00:00','2014-05-23 16:33:15','2014-05-23 16:33:15',997.29,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (592,'2014-03-18 00:00:00','2014-05-23 16:33:15','2014-05-23 16:33:15',153.9,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (593,'2014-03-18 00:00:00','2014-05-23 16:33:15','2014-05-23 16:33:15',99.63,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (594,'2014-03-18 00:00:00','2014-05-23 16:33:15','2014-05-23 16:33:15',350.65,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (595,'2014-03-18 00:00:00','2014-05-23 16:33:15','2014-05-23 16:33:15',919.44,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (596,'2014-03-18 00:00:00','2014-05-23 16:33:15','2014-05-23 16:33:15',67,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (597,'2014-03-18 00:00:00','2014-05-23 16:33:16','2014-05-23 16:33:16',1150.24,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (598,'2014-03-18 00:00:00','2014-05-23 16:33:16','2014-05-23 16:33:16',231.11,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (599,'2014-03-18 00:00:00','2014-05-23 16:33:16','2014-05-23 16:33:16',240.31,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (600,'2014-03-17 00:00:00','2014-05-23 16:33:26','2014-05-23 16:33:26',620.89,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (601,'2014-03-17 00:00:00','2014-05-23 16:33:26','2014-05-23 16:33:26',760.61,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (602,'2014-03-17 00:00:00','2014-05-23 16:33:26','2014-05-23 16:33:26',1097.56,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (603,'2014-03-17 00:00:00','2014-05-23 16:33:26','2014-05-23 16:33:26',127.35,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (604,'2014-03-17 00:00:00','2014-05-23 16:33:26','2014-05-23 16:33:26',130.95,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (605,'2014-03-17 00:00:00','2014-05-23 16:33:26','2014-05-23 16:33:26',33.39,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (606,'2014-03-17 00:00:00','2014-05-23 16:33:26','2014-05-23 16:33:26',1465.9,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (607,'2014-03-17 00:00:00','2014-05-23 16:33:26','2014-05-23 16:33:26',542.61,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (608,'2014-03-17 00:00:00','2014-05-23 16:33:26','2014-05-23 16:33:26',359.75,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (609,'2014-03-17 00:00:00','2014-05-23 16:33:26','2014-05-23 16:33:26',358.59,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (610,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',0,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (611,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',1282.61,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (612,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',585.14,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (613,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',550.67,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (614,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',145.35,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (615,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',108.9,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (616,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',459.82,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (617,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',229.27,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (618,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',441.15,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (619,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',405.09,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (620,'2014-03-15 00:00:00','2014-05-23 16:33:35','2014-05-23 16:33:35',273.61,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (621,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',120.58,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (622,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',813.54,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (623,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',915.42,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (624,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',654.05,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (625,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',15.03,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (626,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',709.65,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (627,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',533.47,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (628,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (629,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',197.75,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (630,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',1034.13,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (631,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',174.61,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (632,'2014-03-14 00:00:00','2014-05-23 16:33:45','2014-05-23 16:33:45',66.6,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (633,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',30.42,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (634,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',685.99,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (635,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',813.19,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (636,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',513.04,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (637,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',165.25,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (638,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',561.88,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (639,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',1403.28,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (640,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',293.31,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (641,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',862.72,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (642,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',837.69,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (643,'2014-03-13 00:00:00','2014-05-23 16:33:54','2014-05-23 16:33:54',480.99,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (644,'2014-03-12 00:00:00','2014-05-23 16:34:04','2014-05-23 16:34:04',498.79,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (645,'2014-03-12 00:00:00','2014-05-23 16:34:04','2014-05-23 16:34:04',492.26,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (646,'2014-03-12 00:00:00','2014-05-23 16:34:04','2014-05-23 16:34:04',481.05,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (647,'2014-03-12 00:00:00','2014-05-23 16:34:04','2014-05-23 16:34:04',71.46,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (648,'2014-03-12 00:00:00','2014-05-23 16:34:04','2014-05-23 16:34:04',469.51,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (649,'2014-03-12 00:00:00','2014-05-23 16:34:04','2014-05-23 16:34:04',803.96,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (650,'2014-03-12 00:00:00','2014-05-23 16:34:04','2014-05-23 16:34:04',197.01,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (651,'2014-03-12 00:00:00','2014-05-23 16:34:04','2014-05-23 16:34:04',863.1,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (652,'2014-03-12 00:00:00','2014-05-23 16:34:04','2014-05-23 16:34:04',936.83,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (653,'2014-03-12 00:00:00','2014-05-23 16:34:04','2014-05-23 16:34:04',174.6,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (654,'2014-03-11 00:00:00','2014-05-23 16:34:12','2014-05-23 16:34:12',47.52,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (655,'2014-03-11 00:00:00','2014-05-23 16:34:12','2014-05-23 16:34:12',1119.35,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (656,'2014-03-11 00:00:00','2014-05-23 16:34:12','2014-05-23 16:34:12',372.23,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (657,'2014-03-11 00:00:00','2014-05-23 16:34:12','2014-05-23 16:34:12',358.92,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (658,'2014-03-11 00:00:00','2014-05-23 16:34:12','2014-05-23 16:34:12',28.71,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (659,'2014-03-11 00:00:00','2014-05-23 16:34:12','2014-05-23 16:34:12',610.65,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (660,'2014-03-11 00:00:00','2014-05-23 16:34:12','2014-05-23 16:34:12',1485.98,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (661,'2014-03-11 00:00:00','2014-05-23 16:34:13','2014-05-23 16:34:13',373.78,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (662,'2014-03-11 00:00:00','2014-05-23 16:34:13','2014-05-23 16:34:13',252.05,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (663,'2014-03-11 00:00:00','2014-05-23 16:34:13','2014-05-23 16:34:13',2126.21,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (664,'2014-03-11 00:00:00','2014-05-23 16:34:13','2014-05-23 16:34:13',136.89,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (665,'2014-03-10 00:00:00','2014-05-23 16:34:22','2014-05-23 16:34:22',219.5,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (666,'2014-03-10 00:00:00','2014-05-23 16:34:22','2014-05-23 16:34:22',582.99,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (667,'2014-03-10 00:00:00','2014-05-23 16:34:22','2014-05-23 16:34:22',522.16,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (668,'2014-03-10 00:00:00','2014-05-23 16:34:22','2014-05-23 16:34:22',16.65,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (669,'2014-03-10 00:00:00','2014-05-23 16:34:22','2014-05-23 16:34:22',402.21,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (670,'2014-03-10 00:00:00','2014-05-23 16:34:22','2014-05-23 16:34:22',503.51,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (671,'2014-03-10 00:00:00','2014-05-23 16:34:22','2014-05-23 16:34:22',296.63,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (672,'2014-03-10 00:00:00','2014-05-23 16:34:22','2014-05-23 16:34:22',727.66,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (673,'2014-03-10 00:00:00','2014-05-23 16:34:22','2014-05-23 16:34:22',221.95,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (674,'2014-03-10 00:00:00','2014-05-23 16:34:22','2014-05-23 16:34:22',19.8,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (675,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',118.26,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (676,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',1256.23,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (677,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',1906.16,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (678,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',1003.25,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (679,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',25.11,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (680,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',989.51,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (681,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',1317.39,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (682,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',948.58,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (683,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',48.6,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (684,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',129.53,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (685,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',358.34,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (686,'2014-03-08 00:00:00','2014-05-23 16:34:31','2014-05-23 16:34:31',129.33,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (687,'2014-03-07 00:00:00','2014-05-23 16:34:41','2014-05-23 16:34:41',3818.6,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (688,'2014-03-07 00:00:00','2014-05-23 16:34:41','2014-05-23 16:34:41',306.41,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (689,'2014-03-07 00:00:00','2014-05-23 16:34:41','2014-05-23 16:34:41',705.43,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (690,'2014-03-07 00:00:00','2014-05-23 16:34:41','2014-05-23 16:34:41',521.24,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (691,'2014-03-07 00:00:00','2014-05-23 16:34:41','2014-05-23 16:34:41',19.99,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (692,'2014-03-07 00:00:00','2014-05-23 16:34:41','2014-05-23 16:34:41',677.25,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (693,'2014-03-07 00:00:00','2014-05-23 16:34:41','2014-05-23 16:34:41',599.63,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (694,'2014-03-07 00:00:00','2014-05-23 16:34:41','2014-05-23 16:34:41',386.35,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (695,'2014-03-07 00:00:00','2014-05-23 16:34:41','2014-05-23 16:34:41',1250.1,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (696,'2014-03-07 00:00:00','2014-05-23 16:34:41','2014-05-23 16:34:41',258.3,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (697,'2014-03-07 00:00:00','2014-05-23 16:34:42','2014-05-23 16:34:42',756.94,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (698,'2014-03-06 00:00:00','2014-05-23 16:34:52','2014-05-23 16:34:52',728.97,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (699,'2014-03-06 00:00:00','2014-05-23 16:34:52','2014-05-23 16:34:52',549.41,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (700,'2014-03-06 00:00:00','2014-05-23 16:34:52','2014-05-23 16:34:52',1159.38,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (701,'2014-03-06 00:00:00','2014-05-23 16:34:52','2014-05-23 16:34:52',328.13,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (702,'2014-03-06 00:00:00','2014-05-23 16:34:52','2014-05-23 16:34:52',1019.11,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (703,'2014-03-06 00:00:00','2014-05-23 16:34:52','2014-05-23 16:34:52',833.33,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (704,'2014-03-06 00:00:00','2014-05-23 16:34:52','2014-05-23 16:34:52',534.56,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (705,'2014-03-06 00:00:00','2014-05-23 16:34:52','2014-05-23 16:34:52',694.35,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (706,'2014-03-06 00:00:00','2014-05-23 16:34:52','2014-05-23 16:34:52',443.15,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (707,'2014-03-05 00:00:00','2014-05-23 16:35:02','2014-05-23 16:35:02',13.95,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (708,'2014-03-05 00:00:00','2014-05-23 16:35:02','2014-05-23 16:35:02',507.1,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (709,'2014-03-05 00:00:00','2014-05-23 16:35:02','2014-05-23 16:35:02',397.43,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (710,'2014-03-05 00:00:00','2014-05-23 16:35:02','2014-05-23 16:35:02',627.26,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (711,'2014-03-05 00:00:00','2014-05-23 16:35:02','2014-05-23 16:35:02',15.39,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (712,'2014-03-05 00:00:00','2014-05-23 16:35:02','2014-05-23 16:35:02',425.8,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (713,'2014-03-05 00:00:00','2014-05-23 16:35:02','2014-05-23 16:35:02',346.39,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (714,'2014-03-05 00:00:00','2014-05-23 16:35:02','2014-05-23 16:35:02',262.95,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (715,'2014-03-05 00:00:00','2014-05-23 16:35:02','2014-05-23 16:35:02',321.34,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (716,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',569.16,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (717,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',2077.87,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (718,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',158.68,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (719,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',380.79,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (720,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',19.8,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (721,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',487.97,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (722,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',154.1,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (723,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',584.72,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (724,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (725,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',585.13,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (726,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',37.62,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (727,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',1114.95,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (728,'2014-03-03 00:00:00','2014-05-23 16:35:13','2014-05-23 16:35:13',666.93,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (729,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',29.56,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (730,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',952.28,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (731,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',1270.86,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (732,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',867.82,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (733,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',54.9,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (734,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',1185.73,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (735,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',110.07,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (736,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',569.68,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (737,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (738,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',736.45,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (739,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',790.6,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (740,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',890.39,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (741,'2014-03-01 00:00:00','2014-05-23 16:36:37','2014-05-23 16:36:37',121.38,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (742,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',986.95,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (743,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',631.43,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (744,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',759.8,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (745,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',0,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (746,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',370.25,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (747,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',1315.31,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (748,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',233.95,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (749,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',91.44,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (750,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',794.01,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (751,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',271.36,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (752,'2014-02-28 00:00:00','2014-05-23 16:52:48','2014-05-23 16:52:48',594.99,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (753,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',48.69,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (754,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',1035.33,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (755,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',19.89,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (756,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',1030.04,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (757,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',123.03,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (758,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',1539.19,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (759,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',10.8,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (760,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',824.14,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (762,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',229.22,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (763,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',853.05,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (764,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',287.55,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (765,'2014-02-27 00:00:00','2014-05-23 16:53:12','2014-05-23 16:53:12',331.31,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (766,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',914.76,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (767,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',413.75,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (768,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',642.4,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (769,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',10.08,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (770,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',833.51,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (771,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',102,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (772,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',636.17,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (773,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (774,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',289.93,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (775,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',603.29,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (776,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',688.8,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (777,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',150.76,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (778,'2014-02-26 00:00:00','2014-05-23 16:53:24','2014-05-23 16:53:24',27.85,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (779,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',19.71,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (780,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',516.47,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (781,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',419.05,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (782,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',264.25,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (783,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',51.84,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (784,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',681.17,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (785,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',44.64,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (786,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',378.36,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (787,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',497.48,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (788,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',600.59,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (789,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',355.91,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (790,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',533.68,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (791,'2014-02-25 00:00:00','2014-05-23 16:53:34','2014-05-23 16:53:34',166.91,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (792,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',939.81,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (793,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',170.95,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (794,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',1212.26,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (795,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',75.8,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (796,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',404.69,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (797,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',20.25,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (798,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',590.3,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (799,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',153.9,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (800,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',489.4,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (801,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',82.5,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (802,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',589.5,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (803,'2014-02-24 00:00:00','2014-05-23 16:53:55','2014-05-23 16:53:55',39.51,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (804,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',17.01,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (805,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',1695.72,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (806,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',605.7,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (807,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',568.8,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (808,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',40.3,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (809,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',804.63,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (810,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',511.83,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (811,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',978.03,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (812,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (813,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',557.21,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (814,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',559.62,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (815,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',1732.56,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (816,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',228.6,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (818,'2014-02-22 00:00:00','2014-05-23 16:54:15','2014-05-23 16:54:15',637.43,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (819,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',0,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (820,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',1215.13,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (821,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',903.15,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (822,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',1097.3,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (823,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',70.83,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (824,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',372.86,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (825,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',37.8,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (826,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',182.94,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (827,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',255.52,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (828,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',138.69,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (829,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',393.71,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (830,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',827.22,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (831,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',577.89,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (832,'2014-02-21 00:00:00','2014-05-23 16:54:27','2014-05-23 16:54:27',40.39,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (833,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',434.61,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (834,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',801.38,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (835,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',239.63,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (836,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',255.92,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (837,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',0.9,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (838,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',922.85,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (839,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',392.67,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (840,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',818.98,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (841,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',368.47,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (842,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',262.89,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (843,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',484.83,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (844,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',503.11,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (845,'2014-02-20 00:00:00','2014-05-23 16:54:36','2014-05-23 16:54:36',232.86,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (846,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',0,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (847,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',717.48,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (848,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',644.23,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (849,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',270.18,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (850,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',93.15,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (851,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',375.84,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (852,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',525.52,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (853,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',1093.4,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (854,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',160.24,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (855,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',470.69,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (856,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',1543.78,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (857,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',256.77,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (858,'2014-02-19 00:00:00','2014-05-23 16:54:47','2014-05-23 16:54:47',22.99,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (859,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',318.31,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (860,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',1466.86,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (861,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',736.32,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (862,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',837.77,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (863,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',138.06,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (864,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',664.38,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (865,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',113.49,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (866,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',637.25,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (867,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (868,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',718.73,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (869,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',531.33,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (870,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',567.17,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (871,'2014-02-18 00:00:00','2014-05-23 16:54:57','2014-05-23 16:54:57',128.7,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (872,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',1944.58,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (873,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',1985.65,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (874,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',1523.76,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (875,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',0,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (876,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',1304.07,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (877,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',28.53,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (878,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',1022.93,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (879,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',1925.89,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (880,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (881,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',1417.72,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (882,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',1274.55,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (883,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',1834.57,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (884,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',734.34,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (885,'2014-02-17 00:00:00','2014-05-23 16:55:05','2014-05-23 16:55:05',69.28,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (886,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',1314.53,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (887,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',1114,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (888,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',1092.9,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (889,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',167.84,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (890,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',1446.82,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (891,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',159.48,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (892,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',1208.35,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (893,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',2443.43,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (894,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',1356.64,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (895,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',910.27,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (896,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',287.14,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (897,'2014-02-15 00:00:00','2014-05-23 16:55:15','2014-05-23 16:55:15',1970.03,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (898,'2014-02-15 00:00:00','2014-05-23 16:55:16','2014-05-23 16:55:16',152.51,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (899,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',732.34,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (900,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',345.02,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (901,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',182.32,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (902,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',53.8,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (903,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',961.04,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (904,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',384.66,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (905,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',568.97,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (906,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (907,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',243.89,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (908,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',522.8,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (909,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',511.53,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (910,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',258.24,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (911,'2014-02-14 00:00:00','2014-05-23 16:55:26','2014-05-23 16:55:26',66.15,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (912,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',1671.44,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (913,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',315.69,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (914,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',170.8,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (915,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',41.85,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (916,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',1022.21,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (917,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',51.66,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (918,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',577.22,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (919,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',717.69,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (920,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',325.25,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (921,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',330.19,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (922,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',1059.43,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (923,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',508.19,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (924,'2014-02-13 00:00:00','2014-05-23 16:55:36','2014-05-23 16:55:36',258.6,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (925,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',292.47,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (926,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',1832.34,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (927,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',684.98,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (928,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',904.82,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (929,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',95.85,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (930,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',878.45,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (931,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',31.41,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (932,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',481.59,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (933,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',1403.51,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (934,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',4.68,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (935,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',527.11,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (936,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',440.73,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (937,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',309.87,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (938,'2014-02-12 00:00:00','2014-05-23 16:55:46','2014-05-23 16:55:46',362.38,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (939,'2014-02-11 00:00:00','2014-05-23 16:55:55','2014-05-23 16:55:55',23.76,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (940,'2014-02-11 00:00:00','2014-05-23 16:55:55','2014-05-23 16:55:55',1355.92,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (941,'2014-02-11 00:00:00','2014-05-23 16:55:55','2014-05-23 16:55:55',727.29,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (942,'2014-02-11 00:00:00','2014-05-23 16:55:55','2014-05-23 16:55:55',616.24,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (943,'2014-02-11 00:00:00','2014-05-23 16:55:55','2014-05-23 16:55:55',31.68,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (944,'2014-02-11 00:00:00','2014-05-23 16:55:55','2014-05-23 16:55:55',2053.22,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (945,'2014-02-11 00:00:00','2014-05-23 16:55:55','2014-05-23 16:55:55',44.1,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (946,'2014-02-11 00:00:00','2014-05-23 16:55:55','2014-05-23 16:55:55',817.85,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (947,'2014-02-11 00:00:00','2014-05-23 16:55:56','2014-05-23 16:55:56',1745.76,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (948,'2014-02-11 00:00:00','2014-05-23 16:55:56','2014-05-23 16:55:56',804.24,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (949,'2014-02-11 00:00:00','2014-05-23 16:55:56','2014-05-23 16:55:56',747.04,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (950,'2014-02-11 00:00:00','2014-05-23 16:55:56','2014-05-23 16:55:56',1641.68,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (951,'2014-02-11 00:00:00','2014-05-23 16:55:56','2014-05-23 16:55:56',1042.5,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (952,'2014-02-11 00:00:00','2014-05-23 16:55:56','2014-05-23 16:55:56',75.06,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (953,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',346.06,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (954,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',1368.42,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (955,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',1417.17,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (956,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',1325.46,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (957,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',145.81,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (958,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',1666.18,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (959,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',764.53,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (960,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',2033.86,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (961,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',2166.65,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (962,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',29.52,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (963,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',1117.98,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (964,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',706.23,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (965,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',1463.5,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (966,'2014-02-10 00:00:00','2014-05-23 16:56:04','2014-05-23 16:56:04',1563.55,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (967,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',649.46,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (968,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',2210.97,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (969,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',2624.26,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (970,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',1804.07,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (971,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',205.47,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (972,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',2127.83,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (973,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',2283.54,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (974,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',4033.69,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (975,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',108.61,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (976,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',1125.22,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (977,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',247.68,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (978,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',2928.94,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (979,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',2147.76,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (980,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',2591.32,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (981,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',2996.52,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (982,'2014-02-08 00:00:00','2014-05-23 16:56:13','2014-05-23 16:56:13',1161.63,0,42);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (983,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',307.37,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (984,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',1983.41,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (985,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',2264.48,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (986,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',1392.09,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (987,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',94.77,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (988,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',1597.66,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (989,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',240.9,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (990,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',2858.34,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (991,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',3240.3,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (992,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',84.06,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (993,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',1668.07,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (994,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',1380.43,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (995,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',2190.72,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (996,'2014-02-07 00:00:00','2014-05-23 16:56:21','2014-05-23 16:56:21',2329.93,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (997,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',249.63,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (998,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',2757.08,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (999,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',1858.36,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1000,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',1394.66,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1001,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',98.76,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1002,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',1694.15,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1003,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',68.13,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1004,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',1805.17,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1005,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',24.03,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1006,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',2565.66,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1007,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',269.55,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1008,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',2532.32,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1009,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',1720.12,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1010,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',2834,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1011,'2014-02-06 00:00:00','2014-05-23 16:56:31','2014-05-23 16:56:31',1950.4,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1012,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',118.44,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1013,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',2464.4,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1014,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',1993.95,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1015,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',1550.9,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1016,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',206.19,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1017,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',1366.8,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1018,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',73.43,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1019,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',1498.55,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1020,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',689.98,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1021,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1022,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',1185.53,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1023,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',1805.4,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1024,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',2732.86,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1025,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',2159.51,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1026,'2014-02-05 00:00:00','2014-05-23 16:56:39','2014-05-23 16:56:39',4.59,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1027,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',159.66,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1028,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',2258.82,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1029,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',2309.2,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1030,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',1498.38,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1031,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',76.05,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1032,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',2300.51,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1033,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',529.9,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1034,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',2137.62,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1035,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',2514.72,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1036,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',1412.23,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1037,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',852.89,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1038,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',2614.46,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1039,'2014-02-04 00:00:00','2014-05-23 16:56:47','2014-05-23 16:56:47',1825.47,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1040,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',47.34,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1041,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',2108.22,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1042,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',1553.09,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1043,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',2295.03,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1044,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',44.91,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1045,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',2597.29,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1046,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',304.71,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1047,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',2941.9,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1048,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',1496.83,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1049,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',2878.42,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1050,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',1485.48,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1051,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',4421.05,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1052,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',2969.35,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1053,'2014-02-03 00:00:00','2014-05-23 16:56:56','2014-05-23 16:56:56',47.79,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1054,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',3.69,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1055,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',1977.39,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1056,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',2719.1,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1057,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',1789.22,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1058,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',38.61,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1059,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',1362.18,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1060,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',394.44,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1061,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',2195,0,24);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1062,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',23.4,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1063,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',2760.82,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1064,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',1374.51,0,29);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1065,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',1792.8,0,33);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1066,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',1492.01,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1067,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',1950.39,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1068,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',59.04,0,42);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1069,'2014-02-01 00:00:00','2014-05-23 16:57:05','2014-05-23 16:57:05',162.11,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1070,'2014-05-21 00:00:00','2014-05-26 10:52:24','2014-05-26 10:52:24',793.13,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1071,'2014-05-21 00:00:00','2014-05-26 10:52:24','2014-05-26 10:52:24',1227.94,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1072,'2014-05-21 00:00:00','2014-05-26 10:52:24','2014-05-26 10:52:24',662.24,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1073,'2014-05-21 00:00:00','2014-05-26 10:52:24','2014-05-26 10:52:24',31.77,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1074,'2014-05-21 00:00:00','2014-05-26 10:52:24','2014-05-26 10:52:24',479.17,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1075,'2014-05-21 00:00:00','2014-05-26 10:52:24','2014-05-26 10:52:24',989.83,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1076,'2014-05-21 00:00:00','2014-05-26 10:52:24','2014-05-26 10:52:24',966.98,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1077,'2014-05-21 00:00:00','2014-05-26 10:52:24','2014-05-26 10:52:24',351.43,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1078,'2014-05-21 00:00:00','2014-05-26 10:52:24','2014-05-26 10:52:24',-0.18,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1079,'2014-05-21 00:00:00','2014-05-26 10:52:24','2014-05-26 10:52:24',619.23,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1080,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',8.98,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1081,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',455.31,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1082,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',325.68,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1083,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',102.99,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1084,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',2.97,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1085,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',225.61,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1086,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',284.85,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1087,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',197.91,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1088,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',1021.37,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1089,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',213.63,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1090,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',543.73,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1091,'2014-05-22 00:00:00','2014-05-26 10:52:43','2014-05-26 10:52:43',115.78,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1092,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',1372.05,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1093,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',419.04,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1094,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',413.7,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1095,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',243.26,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1096,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',35,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1097,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',394.38,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1098,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',2930.5,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1099,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',817.69,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1100,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',91.8,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1101,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',1708.08,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1102,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',377.88,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1103,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',493.13,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1104,'2014-05-23 00:00:00','2014-05-26 10:53:13','2014-05-26 10:53:13',27.75,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1105,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',36.54,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1106,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',1477.45,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1107,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',1337.15,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1108,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',1479,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1109,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',87.34,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1110,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',1344.01,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1111,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',75.33,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1112,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',1090.45,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1113,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',40.05,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1114,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',2016.98,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1115,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',58.86,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1116,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',1232.8,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1117,'2014-05-24 00:00:00','2014-05-26 10:53:26','2014-05-26 10:53:26',91.97,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1118,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',1204.73,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1119,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',1197.07,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1120,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',85.41,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1121,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',336.08,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1122,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',1699.55,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1123,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',103.82,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1124,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',1360,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1125,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',1730.51,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1126,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1127,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',750.7,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1128,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',1415.93,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1129,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',1826.66,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1130,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',1255.8,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1131,'2014-05-26 00:00:00','2014-06-02 18:09:48','2014-06-02 18:09:48',416.08,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1132,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',1332.43,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1133,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',1361.41,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1134,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',8.55,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1135,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',74.33,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1136,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',1059.04,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1137,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',138.8,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1138,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',1087.14,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1139,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',433.5,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1140,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',1317.06,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1141,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',290.69,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1142,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',853.5,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1143,'2014-05-27 00:00:00','2014-06-02 18:09:59','2014-06-02 18:09:59',431.28,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1144,'2014-05-28 00:00:00','2014-06-02 18:10:09','2014-06-02 18:10:09',777.32,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1145,'2014-05-28 00:00:00','2014-06-02 18:10:09','2014-06-02 18:10:09',1020.87,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1146,'2014-05-28 00:00:00','2014-06-02 18:10:09','2014-06-02 18:10:09',1363.37,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1147,'2014-05-28 00:00:00','2014-06-02 18:10:09','2014-06-02 18:10:09',2930.5,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1148,'2014-05-28 00:00:00','2014-06-02 18:10:09','2014-06-02 18:10:09',396.5,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1149,'2014-05-28 00:00:00','2014-06-02 18:10:09','2014-06-02 18:10:09',185.67,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1150,'2014-05-28 00:00:00','2014-06-02 18:10:09','2014-06-02 18:10:09',1840.32,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1151,'2014-05-28 00:00:00','2014-06-02 18:10:09','2014-06-02 18:10:09',1217.48,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1152,'2014-05-28 00:00:00','2014-06-02 18:10:09','2014-06-02 18:10:09',1027.5,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1153,'2014-05-28 00:00:00','2014-06-02 18:10:09','2014-06-02 18:10:09',238.38,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1154,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',1.35,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1155,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',257.04,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1156,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',586.68,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1157,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',66.78,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1158,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',68.31,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1159,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',1219.8,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1160,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',644.78,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1161,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',214.08,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1162,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',1048.13,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1163,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',1481.76,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1164,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',388.8,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1165,'2014-05-29 00:00:00','2014-06-02 18:10:18','2014-06-02 18:10:18',86.76,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1166,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',915.47,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1167,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',921.16,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1168,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',569.23,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1169,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',79.02,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1170,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',1037.58,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1171,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',4.05,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1172,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',1960,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1173,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',830.38,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1174,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',68.76,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1175,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',1255.75,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1176,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',903.15,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1177,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',974.77,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1178,'2014-05-30 00:00:00','2014-06-02 18:10:30','2014-06-02 18:10:30',7.9,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1179,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',55.81,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1180,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',1425.6,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1181,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',1270.38,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1182,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',1296.88,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1183,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',163.62,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1184,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',810.56,0,19);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1185,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',370.82,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1186,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',1944.75,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1187,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',410.12,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1188,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',2721.42,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1189,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',1649.82,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1190,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',1803.47,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1191,'2014-05-31 00:00:00','2014-06-02 18:10:40','2014-06-02 18:10:40',36.5,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1192,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',49.23,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1193,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',541.13,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1194,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',430.53,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1195,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',561.94,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1196,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',23.31,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1197,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',173.7,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1198,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',1288.8,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1199,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',2236.67,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1200,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',320.94,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1201,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',2131.66,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1202,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',1185.52,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1203,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',926.98,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1204,'2014-06-03 00:00:00','2014-06-03 18:41:24','2014-06-03 18:41:24',120,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1205,'2014-06-02 00:00:00','2014-06-03 18:41:32','2014-06-03 18:41:32',9.9,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1206,'2014-06-02 00:00:00','2014-06-03 18:41:32','2014-06-03 18:41:32',1123.07,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1207,'2014-06-02 00:00:00','2014-06-03 18:41:32','2014-06-03 18:41:32',1281.42,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1208,'2014-06-02 00:00:00','2014-06-03 18:41:32','2014-06-03 18:41:32',419.15,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1209,'2014-06-02 00:00:00','2014-06-03 18:41:32','2014-06-03 18:41:32',31.77,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1210,'2014-06-02 00:00:00','2014-06-03 18:41:32','2014-06-03 18:41:32',577.53,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1211,'2014-06-02 00:00:00','2014-06-03 18:41:32','2014-06-03 18:41:32',852.12,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1212,'2014-06-02 00:00:00','2014-06-03 18:41:32','2014-06-03 18:41:32',1336.54,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1213,'2014-06-02 00:00:00','2014-06-03 18:41:32','2014-06-03 18:41:32',990.27,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1214,'2014-06-02 00:00:00','2014-06-03 18:41:33','2014-06-03 18:41:33',659.3,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1215,'2014-06-02 00:00:00','2014-06-03 18:41:33','2014-06-03 18:41:33',50.76,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1216,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',79.8,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1217,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',1208.39,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1218,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',1238.32,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1219,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',807.65,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1220,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',84.42,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1221,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',277.02,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1222,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',953.33,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1223,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',16.92,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1224,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',1088.36,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1225,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',1851.33,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1226,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',492.61,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1227,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',1164.18,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1228,'2014-06-04 00:00:00','2014-06-04 18:36:02','2014-06-04 18:36:02',381.31,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1229,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',435.82,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1230,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',441.15,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1231,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',1168.88,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1232,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',966.91,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1233,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',128.7,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1234,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',18,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1235,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',615.63,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1236,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',534.52,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1237,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',703.57,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1238,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',991.55,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1239,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',986.02,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1240,'2014-06-05 00:00:00','2014-06-06 11:06:57','2014-06-06 11:06:57',98.42,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1241,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',18,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1242,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',1251.9,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1243,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',655.46,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1244,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',636.28,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1245,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',744,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1246,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',564.06,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1247,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',615.5,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1248,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',1438.71,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1249,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',1317.29,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1250,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',1183.07,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1251,'2014-06-10 00:00:00','2014-06-11 17:45:13','2014-06-11 17:45:13',663.86,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1252,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',130.68,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1253,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',1161.38,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1254,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',783.18,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1255,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',966.25,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1256,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',1699.85,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1257,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',92.1,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1258,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',916.07,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1259,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',249.86,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1260,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',1514.18,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1261,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',749.41,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1262,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',399.2,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1263,'2014-06-09 00:00:00','2014-06-11 17:45:25','2014-06-11 17:45:25',202.13,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1264,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',50.04,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1265,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',1487.68,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1266,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',1729.61,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1267,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',1058.7,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1268,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',146.54,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1269,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',127.78,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1270,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',461.78,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1271,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',1049.81,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1272,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',2335.73,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1273,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',1702.38,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1274,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',729.65,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1275,'2014-06-07 00:00:00','2014-06-11 17:45:34','2014-06-11 17:45:34',107,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1276,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',0.27,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1277,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',1115.62,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1278,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',662.17,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1279,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',957.43,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1280,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',20.79,0,18);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1281,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',489.2,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1282,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',0,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1283,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',839.12,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1284,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1285,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',402.66,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1286,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',904.88,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1287,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',1455.86,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1288,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',964.58,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1289,'2014-06-06 00:00:00','2014-06-11 17:45:45','2014-06-11 17:45:45',320.46,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1290,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',145.17,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1291,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',1246.04,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1292,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',1038.89,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1293,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',300.67,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1294,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',29.43,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1295,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',1178.06,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1296,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',489.08,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1297,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',1600.75,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1298,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',1133.71,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1299,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',849.42,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1300,'2014-06-11 00:00:00','2014-06-11 18:37:37','2014-06-11 18:37:37',156.9,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1301,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',7.56,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1302,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',181.8,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1303,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',258.73,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1304,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',217.05,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1305,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',114.8,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1306,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',2044,0,25);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1307,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',554.32,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1308,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',376.56,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1309,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',660.03,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1310,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',708.12,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1311,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',792.66,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1312,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',8.55,0,42);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1313,'2014-06-12 00:00:00','2014-06-12 15:45:17','2014-06-12 15:45:17',2.52,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1314,'2014-06-14 00:00:00','2014-06-16 14:18:17','2014-06-16 14:18:17',115.11,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1315,'2014-06-14 00:00:00','2014-06-16 14:18:17','2014-06-16 14:18:17',1713.01,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1316,'2014-06-14 00:00:00','2014-06-16 14:18:17','2014-06-16 14:18:17',276,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1317,'2014-06-14 00:00:00','2014-06-16 14:18:17','2014-06-16 14:18:17',585.05,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1318,'2014-06-14 00:00:00','2014-06-16 14:18:17','2014-06-16 14:18:17',147.78,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1319,'2014-06-14 00:00:00','2014-06-16 14:18:17','2014-06-16 14:18:17',1063.95,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1320,'2014-06-14 00:00:00','2014-06-16 14:18:17','2014-06-16 14:18:17',351.64,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1321,'2014-06-14 00:00:00','2014-06-16 14:18:17','2014-06-16 14:18:17',1362.63,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1322,'2014-06-14 00:00:00','2014-06-16 14:18:17','2014-06-16 14:18:17',814.54,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1323,'2014-06-14 00:00:00','2014-06-16 14:18:17','2014-06-16 14:18:17',1545.17,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1324,'2014-06-13 00:00:00','2014-06-16 14:18:27','2014-06-16 14:18:27',116.1,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1325,'2014-06-13 00:00:00','2014-06-16 14:18:27','2014-06-16 14:18:27',321.41,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1326,'2014-06-13 00:00:00','2014-06-16 14:18:27','2014-06-16 14:18:27',580.25,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1327,'2014-06-13 00:00:00','2014-06-16 14:18:27','2014-06-16 14:18:27',204.22,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1328,'2014-06-13 00:00:00','2014-06-16 14:18:27','2014-06-16 14:18:27',944.8,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1329,'2014-06-13 00:00:00','2014-06-16 14:18:27','2014-06-16 14:18:27',145.17,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1330,'2014-06-13 00:00:00','2014-06-16 14:18:27','2014-06-16 14:18:27',981.63,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1331,'2014-06-13 00:00:00','2014-06-16 14:18:27','2014-06-16 14:18:27',183.51,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1332,'2014-06-13 00:00:00','2014-06-16 14:18:27','2014-06-16 14:18:27',697.36,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1333,'2014-06-13 00:00:00','2014-06-16 14:18:27','2014-06-16 14:18:27',7.79,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1334,'2014-06-18 00:00:00','2014-06-20 11:34:11','2014-06-20 11:34:11',25,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1335,'2014-06-18 00:00:00','2014-06-20 11:34:11','2014-06-20 11:34:11',566.69,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1336,'2014-06-18 00:00:00','2014-06-20 11:34:11','2014-06-20 11:34:11',370.98,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1337,'2014-06-18 00:00:00','2014-06-20 11:34:11','2014-06-20 11:34:11',236.8,0,23);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1338,'2014-06-18 00:00:00','2014-06-20 11:34:11','2014-06-20 11:34:11',858.06,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1339,'2014-06-18 00:00:00','2014-06-20 11:34:11','2014-06-20 11:34:11',308.61,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1340,'2014-06-18 00:00:00','2014-06-20 11:34:11','2014-06-20 11:34:11',754.28,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1341,'2014-06-18 00:00:00','2014-06-20 11:34:11','2014-06-20 11:34:11',567.45,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1342,'2014-06-18 00:00:00','2014-06-20 11:34:11','2014-06-20 11:34:11',612.55,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1343,'2014-06-18 00:00:00','2014-06-20 11:34:11','2014-06-20 11:34:11',12,0,1);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1344,'2014-06-17 00:00:00','2014-06-20 11:34:22','2014-06-20 11:34:22',10.08,0,9);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1345,'2014-06-17 00:00:00','2014-06-20 11:34:22','2014-06-20 11:34:22',276.21,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1346,'2014-06-17 00:00:00','2014-06-20 11:34:22','2014-06-20 11:34:22',253.35,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1347,'2014-06-17 00:00:00','2014-06-20 11:34:22','2014-06-20 11:34:22',236.07,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1348,'2014-06-17 00:00:00','2014-06-20 11:34:22','2014-06-20 11:34:22',301.2,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1349,'2014-06-17 00:00:00','2014-06-20 11:34:22','2014-06-20 11:34:22',83.43,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1350,'2014-06-17 00:00:00','2014-06-20 11:34:22','2014-06-20 11:34:22',464.26,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1351,'2014-06-17 00:00:00','2014-06-20 11:34:22','2014-06-20 11:34:22',199.53,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1352,'2014-06-17 00:00:00','2014-06-20 11:34:22','2014-06-20 11:34:22',65.97,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1353,'2014-06-16 00:00:00','2014-06-20 11:34:32','2014-06-20 11:34:32',545.72,0,13);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1354,'2014-06-16 00:00:00','2014-06-20 11:34:32','2014-06-20 11:34:32',326.98,0,15);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1355,'2014-06-16 00:00:00','2014-06-20 11:34:32','2014-06-20 11:34:32',222.82,0,17);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1356,'2014-06-16 00:00:00','2014-06-20 11:34:32','2014-06-20 11:34:32',457.7,0,26);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1357,'2014-06-16 00:00:00','2014-06-20 11:34:32','2014-06-20 11:34:32',0,0,28);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1358,'2014-06-16 00:00:00','2014-06-20 11:34:32','2014-06-20 11:34:32',82.59,0,32);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1359,'2014-06-16 00:00:00','2014-06-20 11:34:32','2014-06-20 11:34:32',575.52,0,44);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1360,'2014-06-16 00:00:00','2014-06-20 11:34:32','2014-06-20 11:34:32',338.51,0,38);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1361,'2014-06-16 00:00:00','2014-06-20 11:34:32','2014-06-20 11:34:32',312.03,0,39);
INSERT INTO `rh_total_dia_vendedor` (`id`, `dia`, `inserted`, `updated`, `total`, `version`, `funcionario_id`) VALUES (1362,'2014-06-16 00:00:00','2014-06-20 11:34:32','2014-06-20 11:34:32',155.01,0,1);
/*!40000 ALTER TABLE `rh_total_dia_vendedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rh_total_mes`
--

DROP TABLE IF EXISTS `rh_total_mes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rh_total_mes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `mes_ano` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `total` double DEFAULT NULL,
  `qtde_vendedores` int(11) DEFAULT NULL,
  `fator_meta` double DEFAULT NULL,
  `total_historico` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2snvo8hohmurakyct7l5oe2gy` (`mes_ano`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rh_total_mes`
--
-- ORDER BY:  `id`

LOCK TABLES `rh_total_mes` WRITE;
/*!40000 ALTER TABLE `rh_total_mes` DISABLE KEYS */;
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (2,'2014-04-22 09:28:50','2014-05-24 09:49:19','2012-01-01 00:00:00',8,331516.43,10,0.05,270755.71);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (3,'2014-04-22 09:28:50','2014-05-24 09:49:16','2012-02-01 00:00:00',5,231383.13,10,0.05,255146.98);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (4,'2014-04-22 09:28:50','2014-05-24 09:49:13','2012-03-01 00:00:00',5,144018.39,10,0.05,159851.13);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (5,'2014-04-22 09:28:50','2014-05-24 09:49:11','2012-04-01 00:00:00',5,146552.93,9,0.05,147977.65);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (6,'2014-04-22 09:28:50','2014-05-24 09:49:08','2012-05-01 00:00:00',5,196560.39,9,0.05,193602.09);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (7,'2014-04-22 09:28:50','2014-05-24 09:49:05','2012-06-01 00:00:00',5,157870.53,9,0.05,180067.89);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (8,'2014-04-22 09:28:50','2014-05-24 09:49:02','2012-07-01 00:00:00',5,138357.66,9,0.05,153063.07);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (9,'2014-04-22 09:28:51','2014-05-24 09:48:59','2012-08-01 00:00:00',5,130635.59,9,0.05,138703.01);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (10,'2014-04-22 09:28:51','2014-05-24 09:48:57','2012-09-01 00:00:00',5,127064.15,9,0.05,147973.75);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (11,'2014-04-22 09:28:51','2014-05-24 09:48:53','2012-10-01 00:00:00',5,130424.87,8,0.05,140312.49);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (12,'2014-04-22 09:28:51','2014-05-24 09:48:48','2012-11-01 00:00:00',5,128968.42,8,0.05,151021.57);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (13,'2014-04-22 09:28:51','2014-05-24 09:48:38','2012-12-01 00:00:00',5,248764.81,10,0.05,310159.17);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (14,'2014-04-22 09:28:51','2014-05-24 09:48:36','2013-01-01 00:00:00',5,307239.35,11,0.05,331516.43);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (15,'2014-04-22 09:28:51','2014-05-24 09:48:34','2013-02-01 00:00:00',5,288086.12,11,0.05,231383.13);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (16,'2014-04-22 09:28:51','2014-05-24 09:48:32','2013-03-01 00:00:00',5,151468.19,10,0.05,144018.39);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (17,'2014-04-22 09:28:51','2014-05-24 09:48:29','2013-04-01 00:00:00',5,142105.16,9,0.05,146552.93);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (18,'2014-04-22 09:28:51','2014-05-24 09:48:25','2013-05-01 00:00:00',5,178208.71,10,0.05,196560.39);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (19,'2014-04-22 09:28:51','2014-05-24 09:48:23','2013-06-01 00:00:00',5,160676.06,10,0.05,157870.53);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (20,'2014-04-22 09:28:51','2014-05-24 09:48:20','2013-07-01 00:00:00',5,175614.66,8,0.05,138357.66);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (21,'2014-04-22 09:28:51','2014-05-24 09:48:17','2013-08-01 00:00:00',5,160944.95,9,0.05,130635.59);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (22,'2014-04-22 09:28:51','2014-05-24 09:48:14','2013-09-01 00:00:00',5,133665.76,9,0.05,127064.15);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (23,'2014-04-22 09:28:51','2014-05-24 09:48:12','2013-10-01 00:00:00',6,138198.09,9,0.05,130424.87);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (24,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-11-01 00:00:00',4,137697.19,9,0.05,128968.42);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (25,'2014-04-22 09:28:51','2014-05-24 09:43:35','2013-12-01 00:00:00',5,271555.86,10,0.05,248764.81);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (26,'2014-04-22 09:28:51','2014-05-24 09:43:12','2014-01-01 00:00:00',5,389319.1,10,0.05,307239.35);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (27,'2014-04-22 09:28:51','2014-05-23 17:04:25','2014-02-01 00:00:00',17,293786.8899999998,10,0.05,288086.12);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (28,'2014-04-22 09:28:51','2014-05-23 16:39:27','2014-03-01 00:00:00',16,139227.94000000006,8,0.05,151468.19);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (29,'2014-04-29 15:30:47','2014-05-24 09:49:23','2011-12-01 00:00:00',6,310159.17,10,0.05,273135.99);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (30,'2014-05-02 09:38:59','2014-05-23 16:12:59','2014-04-01 00:00:00',11,140357.74000000005,9,0.05,142105.16);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (32,'2014-05-02 11:55:12','2014-05-24 09:49:26','2011-11-01 00:00:00',6,151021.57,9,0.05,135991.79);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (33,'2014-05-08 12:26:50','2014-06-02 18:10:58','2014-05-01 00:00:00',25,216397.72999999998,8,0.05,178208.71);
INSERT INTO `rh_total_mes` (`id`, `inserted`, `updated`, `mes_ano`, `version`, `total`, `qtde_vendedores`, `fator_meta`, `total_historico`) VALUES (34,'2014-06-04 10:12:50','2014-06-20 11:34:39','2014-06-01 00:00:00',19,106598.54999999997,8,0.05,160676.06);
/*!40000 ALTER TABLE `rh_total_mes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rh_total_mes_vendedor`
--

DROP TABLE IF EXISTS `rh_total_mes_vendedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rh_total_mes_vendedor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `total_mes_id` bigint(20) NOT NULL,
  `funcionario_id` bigint(20) NOT NULL,
  `total` double NOT NULL,
  `considera_mes` bit(1) NOT NULL,
  `posicao` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_d2nxl68la129gwdlgggl4d20l` (`funcionario_id`,`total_mes_id`),
  KEY `FK_m1yaa90vnbktftfubt172ucfu` (`total_mes_id`),
  CONSTRAINT `FK_m1yaa90vnbktftfubt172ucfu` FOREIGN KEY (`total_mes_id`) REFERENCES `rh_total_mes` (`id`),
  CONSTRAINT `FK_says02j4mrv0uong5m9942b93` FOREIGN KEY (`funcionario_id`) REFERENCES `bon_funcionario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1054 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rh_total_mes_vendedor`
--
-- ORDER BY:  `id`

LOCK TABLES `rh_total_mes_vendedor` WRITE;
/*!40000 ALTER TABLE `rh_total_mes_vendedor` DISABLE KEYS */;
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (271,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,30,41856.79,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (272,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,37,41316.44,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (273,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,24,34877.66,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (274,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,38,33225.68,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (275,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,42,32546.3,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (276,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,12,32150.34,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (277,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,35,28128.52,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (278,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,16,27528.74,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (279,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,31,26919.02,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (280,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,27,25812.89,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (281,'2014-04-22 12:08:58','2014-04-22 12:08:58',3,2,18,2798.99,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (282,'2014-04-22 12:08:58','2014-04-22 12:08:58',3,2,9,1641.84,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (283,'2014-04-22 12:08:58','2014-04-22 12:08:58',3,2,23,1314.93,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (284,'2014-04-22 12:08:58','2014-04-22 12:08:58',3,2,1,1255.64,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (285,'2014-04-22 12:08:58','2014-04-22 12:08:58',3,2,28,99.5,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (286,'2014-04-22 12:08:58','2014-04-22 12:08:58',3,2,25,43.15,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (287,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,12,27604.54,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (288,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,30,27570.06,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (289,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,24,26348.52,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (290,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,38,24114.01,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (291,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,16,23271.9,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (292,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,37,23237.34,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (293,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,35,21588.18,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (294,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,31,17103.74,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (295,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,27,16993.29,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (296,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,42,16726,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (297,'2014-04-22 12:08:58','2014-04-22 12:08:58',3,3,23,1983.4,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (298,'2014-04-22 12:08:59','2014-04-22 12:08:59',3,3,1,1950.33,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (299,'2014-04-22 12:08:59','2014-04-22 12:08:59',3,3,18,1527.63,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (300,'2014-04-22 12:08:59','2014-04-22 12:08:59',3,3,9,1236.29,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (301,'2014-04-22 12:08:59','2014-04-22 12:08:59',3,3,25,127.9,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (302,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,37,19840.11,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (303,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,24,17851.04,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (304,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,30,17752.35,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (305,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,16,17352.43,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (306,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,38,17235.8,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (307,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,35,15854.09,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (308,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,42,12571.38,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (309,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,31,7671.6,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (310,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,12,5712.38,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (311,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,11,3062.21,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (312,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,18,2841.59,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (313,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,9,2362.5,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (314,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,1,1842.64,'',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (315,'2014-04-22 12:08:59','2014-04-22 12:08:59',3,4,23,1237.12,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (316,'2014-04-22 12:09:00','2014-04-22 12:09:00',3,4,27,831.15,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (317,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,37,21727.95,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (318,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,16,19227.72,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (319,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,24,17876.58,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (320,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,27,17157.58,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (321,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,38,15607.77,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (322,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,12,15597.15,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (323,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,35,14627.38,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (324,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,11,10935.45,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (325,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,30,7539.87,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (326,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,18,2128.69,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (327,'2014-04-22 12:09:00','2014-04-22 12:09:00',3,5,23,1652.22,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (328,'2014-04-22 12:09:00','2014-04-22 12:09:00',3,5,1,1450.24,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (329,'2014-04-22 12:09:00','2014-04-22 12:09:00',3,5,9,570.19,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (330,'2014-04-22 12:09:00','2014-04-22 12:09:00',3,5,25,348.4,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (331,'2014-04-22 12:09:00','2014-04-22 12:09:00',3,5,28,105.74,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (332,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,6,24,26026.18,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (333,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,6,37,24541.62,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (334,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,6,16,23433.24,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (335,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,27,22405.93,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (336,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,12,21647.93,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (337,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,30,21497.06,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (338,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,35,18244.8,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (339,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,38,17006.27,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (340,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,11,11350.76,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (341,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,1,4176.19,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (342,'2014-04-22 12:09:01','2014-04-22 12:09:01',3,6,9,1871.95,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (343,'2014-04-22 12:09:01','2014-04-22 12:09:01',3,6,18,1606.07,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (344,'2014-04-22 12:09:01','2014-04-22 12:09:01',3,6,23,1528.92,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (345,'2014-04-22 12:09:01','2014-04-22 12:09:01',3,6,42,1223.47,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (346,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,37,20099.94,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (347,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,16,19343.07,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (348,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,11,18844.66,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (349,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,27,16028.4,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (350,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,35,16019.25,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (351,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,12,15874.97,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (352,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,30,15480.98,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (353,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,24,15260.75,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (354,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,38,12316.22,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (355,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,1,4123.87,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (356,'2014-04-22 12:09:02','2014-04-22 12:09:02',3,7,23,1667.74,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (357,'2014-04-22 12:09:02','2014-04-22 12:09:02',3,7,9,1497.82,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (358,'2014-04-22 12:09:02','2014-04-22 12:09:02',3,7,18,1041.49,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (359,'2014-04-22 12:09:02','2014-04-22 12:09:02',3,7,25,231.38,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (360,'2014-04-22 12:09:02','2014-04-22 12:09:02',3,7,28,39.99,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (361,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,27,19465.21,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (362,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,24,18974.83,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (363,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,12,18818.12,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (364,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,38,17433.59,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (365,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,11,16460.77,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (366,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,16,16393.14,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (367,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,30,14271.94,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (368,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,37,6293.4,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (369,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,23,5124.77,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (370,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,8,1,2400.84,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (371,'2014-04-22 12:09:03','2014-04-22 12:09:03',3,8,9,905.85,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (372,'2014-04-22 12:09:03','2014-04-22 12:09:03',3,8,28,701.02,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (373,'2014-04-22 12:09:03','2014-04-22 12:09:03',3,8,33,649.46,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (374,'2014-04-22 12:09:03','2014-04-22 12:09:03',3,8,18,252.65,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (375,'2014-04-22 12:09:03','2014-04-22 12:09:03',3,8,25,212.07,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (376,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,37,18235.02,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (377,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,24,17766.67,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (378,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,12,17569.27,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (379,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,33,16023.53,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (380,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,16,14057.29,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (381,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,11,13133.11,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (382,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,27,12745.57,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (383,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,9,9841.18,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (384,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,34,6957.29,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (385,'2014-04-22 12:09:03','2014-04-22 12:09:03',3,9,1,1547.73,'\0',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (386,'2014-04-22 12:09:03','2014-04-22 12:09:03',3,9,23,1371.95,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (387,'2014-04-22 12:09:04','2014-04-22 12:09:04',3,9,18,1072.47,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (388,'2014-04-22 12:09:04','2014-04-22 12:09:04',3,9,38,269.02,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (389,'2014-04-22 12:09:04','2014-04-22 12:09:04',3,9,28,29,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (390,'2014-04-22 12:09:04','2014-04-22 12:09:04',3,9,25,16.49,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (391,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,37,16504.59,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (392,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,33,14868.52,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (393,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,34,14023.58,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (394,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,11,13831.42,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (395,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,12,12987.88,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (396,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,42,12883.55,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (397,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,38,11305.27,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (398,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,27,10905.84,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (399,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,24,10048.82,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (400,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,1,4195.73,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (401,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,18,2921.26,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (402,'2014-04-22 12:09:04','2014-04-22 12:09:04',3,10,9,1529.4,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (403,'2014-04-22 12:09:04','2014-04-22 12:09:04',3,10,23,917.99,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (404,'2014-04-22 12:09:05','2014-04-22 12:09:05',3,10,28,140.3,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (405,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,42,18788.5,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (406,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,37,17754.44,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (407,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,33,17721.09,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (408,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,34,16427.62,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (409,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,27,15378.11,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (410,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,12,14217.26,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (411,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,38,12638.22,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (412,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,11,12196.44,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (413,'2014-04-22 12:09:05','2014-04-22 12:09:05',3,11,23,1832.55,'\0',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (414,'2014-04-22 12:09:05','2014-04-22 12:09:05',3,11,1,1748.11,'\0',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (415,'2014-04-22 12:09:05','2014-04-22 12:09:05',3,11,9,706.83,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (416,'2014-04-22 12:09:05','2014-04-22 12:09:05',3,11,18,623.87,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (417,'2014-04-22 12:09:05','2014-04-22 12:09:05',3,11,24,326.98,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (418,'2014-04-22 12:09:05','2014-04-22 12:09:05',3,11,25,64.85,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (419,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,12,42,27170.01,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (420,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,12,37,17722.28,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (421,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,12,33,16706.66,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (422,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,34,14754.35,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (423,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,27,14598.57,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (424,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,12,11700.36,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (425,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,38,11513.93,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (426,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,24,10526.65,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (427,'2014-04-22 12:09:06','2014-04-22 12:09:06',3,12,22,1514.04,'\0',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (428,'2014-04-22 12:09:06','2014-04-22 12:09:06',3,12,1,1478.35,'\0',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (429,'2014-04-22 12:09:06','2014-04-22 12:09:06',3,12,23,690.58,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (430,'2014-04-22 12:09:06','2014-04-22 12:09:06',3,12,18,385.87,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (431,'2014-04-22 12:09:06','2014-04-22 12:09:06',3,12,9,206.77,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (432,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,37,32611.73,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (433,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,42,29892.13,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (434,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,24,26340.11,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (435,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,12,24459.02,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (436,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,34,24439.64,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (437,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,38,23669.91,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (438,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,27,23475.13,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (439,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,30,20804.83,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (440,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,33,15252.25,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (441,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,22,14601.56,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (442,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,1,4991.14,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (443,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,9,3906.59,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (444,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,23,3647.46,'',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (445,'2014-04-22 12:09:07','2014-04-22 12:09:07',3,13,18,458.52,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (446,'2014-04-22 12:09:07','2014-04-22 12:09:07',3,13,25,115.19,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (447,'2014-04-22 12:09:07','2014-04-22 12:09:07',3,13,11,99.6,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (448,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,12,33128.72,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (449,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,24,32883.53,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (450,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,42,32272.97,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (451,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,30,31220.77,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (452,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,34,29802.24,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (453,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,38,27556.39,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (454,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,10,27252.12,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (455,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,37,26231.03,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (456,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,19,25081.61,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (457,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,14,27,25075.01,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (458,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,14,33,9864.03,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (459,'2014-04-22 12:09:08','2014-04-22 12:09:08',3,14,1,2990.91,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (460,'2014-04-22 12:09:08','2014-04-22 12:09:08',3,14,18,1824.39,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (461,'2014-04-22 12:09:08','2014-04-22 12:09:08',3,14,23,1645.67,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (462,'2014-04-22 12:09:08','2014-04-22 12:09:08',3,14,9,409.96,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (463,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,37,35565.23,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (464,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,19,32565.82,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (465,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,24,31144.04,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (466,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,30,29784.21,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (467,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,34,28942.21,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (468,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,10,28710.62,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (469,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,38,23400.22,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (470,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,12,21215.1,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (471,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,27,18411.86,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (472,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,33,12126.88,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (473,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,42,11565.18,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (474,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,28,7169,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (475,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,15,23,4121.44,'',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (476,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,15,1,2224.67,'',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (477,'2014-04-22 12:09:09','2014-04-22 12:09:09',3,15,9,595.88,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (478,'2014-04-22 12:09:09','2014-04-22 12:09:09',3,15,18,476.95,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (479,'2014-04-22 12:09:09','2014-04-22 12:09:09',3,15,25,66.81,'\0',17);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (480,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,24,26472.98,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (481,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,38,25701.06,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (482,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,19,19611.31,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (483,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,37,18614.44,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (484,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,10,17144.16,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (485,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,30,13416.01,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (486,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,34,8684.73,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (487,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,42,5122.16,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (488,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,27,3454.32,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (489,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,33,3386.17,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (490,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,1,2655.21,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (491,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,23,1894.11,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (492,'2014-04-22 12:09:09','2014-04-22 12:09:09',3,16,28,1508.71,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (493,'2014-04-22 12:09:09','2014-04-22 12:09:09',3,16,12,1288.16,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (494,'2014-04-22 12:09:09','2014-04-22 12:09:09',3,16,9,1260.33,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (495,'2014-04-22 12:09:10','2014-04-22 12:09:10',3,16,18,1246.68,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (496,'2014-04-22 12:09:10','2014-04-22 12:09:10',3,16,25,7.65,'\0',17);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (497,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,37,19073.31,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (498,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,19,18970.14,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (499,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,15,17685.24,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (500,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,17,16375.18,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (501,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,10,14648.47,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (502,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,38,14395.53,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (503,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,24,12827.28,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (504,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,30,12299.58,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (505,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,14,5750.83,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (506,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,1,3117.66,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (507,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,33,1871.24,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (508,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,42,1735.96,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (509,'2014-04-22 12:09:10','2014-04-22 12:09:10',3,17,9,1225.52,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (510,'2014-04-22 12:09:10','2014-04-22 12:09:10',3,17,28,1006.21,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (511,'2014-04-22 12:09:10','2014-04-22 12:09:10',3,17,23,755.98,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (512,'2014-04-22 12:09:10','2014-04-22 12:09:10',3,17,18,367.03,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (513,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,37,27352.25,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (514,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,15,19157.18,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (515,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,14,19017.45,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (516,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,17,17584.16,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (517,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,19,17002.06,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (518,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,24,16515.49,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (519,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,30,15359.22,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (520,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,38,13434.82,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (521,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,10,12787.24,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (522,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,42,11572.74,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (523,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,1,3796.26,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (524,'2014-04-22 12:09:11','2014-04-22 12:09:11',3,18,33,1204.46,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (525,'2014-04-22 12:09:11','2014-04-22 12:09:11',3,18,25,1185,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (526,'2014-04-22 12:09:11','2014-04-22 12:09:11',3,18,23,801.2,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (527,'2014-04-22 12:09:11','2014-04-22 12:09:11',3,18,28,711.69,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (528,'2014-04-22 12:09:11','2014-04-22 12:09:11',3,18,18,444.85,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (529,'2014-04-22 12:09:11','2014-04-22 12:09:11',3,18,9,282.64,'\0',17);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (530,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,19,37,25428.15,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (531,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,42,17076.6,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (532,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,15,17001.28,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (533,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,14,16233.99,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (534,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,19,14067.06,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (535,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,10,13693.73,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (536,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,30,12976.12,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (537,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,17,12794.68,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (538,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,24,12248.62,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (539,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,38,10878.03,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (540,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,9,3731.02,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (541,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,1,3351.41,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (542,'2014-04-22 12:09:12','2014-04-22 12:09:12',3,19,28,408.42,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (543,'2014-04-22 12:09:12','2014-04-22 12:09:12',3,19,33,378.29,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (544,'2014-04-22 12:09:12','2014-04-22 12:09:12',3,19,23,373.44,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (545,'2014-04-22 12:09:12','2014-04-22 12:09:12',3,19,18,23.22,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (546,'2014-04-22 12:09:12','2014-04-22 12:09:12',3,19,25,12,'\0',17);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (547,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,20,17,25688.23,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (548,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,42,23465.84,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (549,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,15,22352.82,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (550,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,14,21673.49,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (551,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,10,18120.84,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (552,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,24,16912.82,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (553,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,30,16694.68,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (554,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,19,16613.05,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (555,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,9,5988.87,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (556,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,1,4902.2,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (557,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,23,1923.27,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (558,'2014-04-22 12:09:13','2014-04-22 12:09:13',3,20,37,470.77,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (559,'2014-04-22 12:09:13','2014-04-22 12:09:13',3,20,18,323.5,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (560,'2014-04-22 12:09:13','2014-04-22 12:09:13',3,20,33,315.2,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (561,'2014-04-22 12:09:13','2014-04-22 12:09:13',3,20,38,123.34,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (562,'2014-04-22 12:09:13','2014-04-22 12:09:13',3,20,25,29.9,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (563,'2014-04-22 12:09:13','2014-04-22 12:09:13',3,20,28,15.84,'\0',17);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (564,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,21,37,19827.97,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (565,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,21,14,18472.3,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (566,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,30,17498.18,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (567,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,19,15959.59,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (568,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,38,15692.42,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (569,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,24,15226.36,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (570,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,17,15154.94,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (571,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,15,14661.18,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (572,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,10,12155.78,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (573,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,9,9700.31,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (574,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,1,2994.7,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (575,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,18,2609.52,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (576,'2014-04-22 12:09:14','2014-04-22 12:09:14',3,21,26,567.19,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (577,'2014-04-22 12:09:14','2014-04-22 12:09:14',3,21,23,292.12,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (578,'2014-04-22 12:09:14','2014-04-22 12:09:14',3,21,42,132.39,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (579,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,22,37,18396.77,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (580,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,22,17,16961.5,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (581,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,22,26,15587.53,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (582,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,22,15,15534.05,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (583,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,24,13324.1,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (584,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,38,12576.68,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (585,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,14,11716.75,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (586,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,30,11337.59,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (587,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,19,9173.92,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (588,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,18,3847.22,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (589,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,1,2871.44,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (590,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,9,1857.98,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (591,'2014-04-22 12:09:15','2014-04-22 12:09:15',3,22,23,384.83,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (592,'2014-04-22 12:09:15','2014-04-22 12:09:15',3,22,28,95.4,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (593,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,38,19078.95,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (594,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,17,15265.89,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (595,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,30,14627.72,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (596,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,19,14026.92,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (597,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,26,13551.03,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (598,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,15,13026.13,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (599,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,25,11937.42,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (600,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,36,9457.9,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (601,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,18,8109.04,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (602,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,29,7988.23,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (603,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,9,3189.06,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (604,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,14,2904.87,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (605,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,37,2280.74,'',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (606,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,1,1895.2,'',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (607,'2014-04-22 12:09:16','2014-04-22 12:09:16',3,23,23,858.99,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (608,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,38,20919.79,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (609,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,24,18303.65,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (610,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,26,15085.15,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (611,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,36,14171.62,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (612,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,15,13342.87,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (613,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,19,12303.15,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (614,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,30,11363.88,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (615,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,17,10834.98,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (616,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,29,8279.85,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (617,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,25,6113.07,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (618,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,9,3877.14,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (619,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,24,1,1674.38,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (620,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,24,18,777.67,'',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (621,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,24,23,649.99,'',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (622,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,24,40092.77,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (623,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,38,37938.47,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (624,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,26,34863.9,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (625,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,15,31090.16,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (626,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,29,26165.09,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (627,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,17,24527.4,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (628,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,36,23961.84,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (629,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,19,21887.29,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (630,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,33,10294.71,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (631,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,39,7714.86,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (632,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,1,4191.49,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (633,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,23,3283.75,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (634,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,9,3131.38,'',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (635,'2014-04-22 12:09:17','2014-04-22 12:09:17',3,25,18,2348.09,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (636,'2014-04-22 12:09:17','2014-04-22 12:09:17',3,25,28,42.62,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (637,'2014-04-22 12:09:18','2014-04-22 12:09:18',3,25,25,17,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (638,'2014-04-22 12:09:18','2014-04-22 12:09:18',3,25,42,5.04,'\0',17);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (639,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,26,48648.06,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (640,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,24,47124.82,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (641,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,38,43674.46,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (642,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,17,41376.74,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (643,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,19,37244.97,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (644,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,39,36950.05,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (645,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,15,35148.13,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (646,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,29,31323,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (647,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,13,28252.31,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (648,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,33,20665.62,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (649,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,9,14170.89,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (650,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,36,4740.05,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (702,'2014-05-02 11:01:35','2014-05-02 11:01:35',3,29,9,1798.01,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (703,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,12,26806.07,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (704,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,16,23862.14,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (705,'2014-05-02 11:01:35','2014-05-02 11:01:35',3,29,18,2132.04,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (706,'2014-05-02 11:01:35','2014-05-02 11:01:35',3,29,23,1300.58,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (707,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,24,37878.93,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (708,'2014-05-02 11:01:35','2014-05-02 11:01:35',3,29,25,16.22,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (709,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,27,16974.72,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (710,'2014-05-02 11:01:35','2014-05-02 11:01:35',3,29,28,157.72,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (711,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,30,37979.82,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (712,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,31,23890.54,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (713,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,35,26006.3,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (714,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,37,36147.37,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (715,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,38,33625.38,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (716,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,42,30361.09,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (717,'2014-05-02 11:01:35','2014-05-02 11:01:35',3,29,53,0,'\0',17);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (718,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,1,4241.91,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (719,'2014-05-02 11:55:37','2014-05-02 11:55:37',3,32,9,941.75,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (720,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,12,16314.36,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (721,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,16,15565.02,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (722,'2014-05-02 11:55:37','2014-05-02 11:55:37',3,32,18,1438.4,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (723,'2014-05-02 11:55:37','2014-05-02 11:55:37',3,32,23,996.66,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (724,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,24,16755.96,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (725,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,27,14669.53,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (726,'2014-05-02 11:55:37','2014-05-02 11:55:37',3,32,28,0,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (727,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,30,18381.27,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (728,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,31,13868.81,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (729,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,35,15509.94,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (730,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,38,16943.3,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (731,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,42,17327.73,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (732,'2014-05-02 11:55:37','2014-05-02 11:55:37',3,32,53,0,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (733,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,1,2308.84,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (859,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,38,19094,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (860,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,15,17723.43,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (861,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,13,16983.8,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (862,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,26,16696.33,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (863,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,19,12716.54,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (864,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,39,12554.97,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (865,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,17,12240.15,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (866,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,29,9020.2,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (867,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,44,7468,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (868,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,18,4796.18,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (869,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,1,4474.17,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (870,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,32,4001.57,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (871,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,9,1921.5,'',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (872,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,23,533.25,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (873,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,28,103.59,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (874,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,42,30.06,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (875,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,53,0,'\0',17);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (876,'2014-05-23 16:12:59','2014-05-23 16:12:59',0,30,25,0,'\0',18);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (877,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,26,20759.43,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (878,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,13,20417.27,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (879,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,15,16776.65,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (880,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,38,16461.09,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (881,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,17,15778.71,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (882,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,19,14307.58,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (883,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,39,13414.9,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (884,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,29,9261.51,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (885,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,9,5577,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (886,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,1,4742.44,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (887,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,18,949.47,'\0',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (888,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,23,677.76,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (889,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,33,86.22,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (890,'2014-05-23 16:39:27','2014-05-23 16:39:27',0,28,28,17.91,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (891,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,13,36269.75,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (892,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,38,33913.97,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (893,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,26,32892.89,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (894,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,19,29347.78,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (895,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,15,26696.34,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (896,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,39,26148.72,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (897,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,24,25788.07,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (898,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,17,24479.37,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (899,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,29,23120.67,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (900,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,33,20119.65,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (901,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,23,5023.36,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (902,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,9,3036.21,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (903,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,1,2930.33,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (904,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,18,1905.58,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (905,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,42,1220.67,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (906,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,28,635.49,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (907,'2014-05-23 17:04:25','2014-05-23 17:04:25',0,27,25,258.04,'\0',17);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (925,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,44,34620.53,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (926,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,13,26108.72,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (927,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,26,24302.92,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (928,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,38,22994.41,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (929,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,15,21322.36,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (930,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,19,20366.79,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (931,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,17,19559.82,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (932,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,39,17968.37,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (933,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,25,9481,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (934,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,1,7043.08,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (935,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,32,4693.84,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (936,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,9,3238.47,'',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (937,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,23,1828.47,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (938,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,18,1559.63,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (939,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,42,1168.02,'\0',15);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (940,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,28,91.8,'\0',16);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (941,'2014-06-02 18:10:58','2014-06-02 18:10:58',0,33,29,49.5,'\0',17);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1039,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,44,18615.7,'',1);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1040,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,13,13181.2,'',2);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1041,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,38,12830.26,'',3);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1042,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,26,12522.28,'',4);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1043,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,39,11888.14,'',5);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1044,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,15,10683.77,'',6);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1045,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,17,8511.17,'',7);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1046,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,32,6950.85,'',8);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1047,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,25,3332.8,'',9);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1048,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,18,2847.61,'',10);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1049,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,1,2278.16,'',11);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1050,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,23,1738.38,'\0',12);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1051,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,9,1192.76,'\0',13);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1052,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,28,16.92,'\0',14);
INSERT INTO `rh_total_mes_vendedor` (`id`, `inserted`, `updated`, `version`, `total_mes_id`, `funcionario_id`, `total`, `considera_mes`, `posicao`) VALUES (1053,'2014-06-20 11:34:39','2014-06-20 11:34:39',0,34,42,8.55,'\0',15);
/*!40000 ALTER TABLE `rh_total_mes_vendedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_group`
--

DROP TABLE IF EXISTS `sec_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sec_group` (
  `group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `groupname` varchar(90) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `groupname` (`groupname`),
  UNIQUE KEY `UK_9fr1lrbpsxlyb9rl3syf74nbh` (`groupname`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_group`
--
-- ORDER BY:  `group_id`

LOCK TABLES `sec_group` WRITE;
/*!40000 ALTER TABLE `sec_group` DISABLE KEYS */;
INSERT INTO `sec_group` (`group_id`, `inserted`, `updated`, `groupname`, `version`) VALUES (1,'2012-08-03 00:16:44','2012-08-03 00:18:15','ADMINISTRADORES',0);
/*!40000 ALTER TABLE `sec_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_group_role`
--

DROP TABLE IF EXISTS `sec_group_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sec_group_role` (
  `group_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FK1754E6E4790C7FAC` (`role_id`),
  KEY `FK1754E6E4D5F739E8` (`group_id`),
  CONSTRAINT `FK1754E6E4790C7FAC` FOREIGN KEY (`role_id`) REFERENCES `sec_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK1754E6E4D5F739E8` FOREIGN KEY (`group_id`) REFERENCES `sec_group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_group_role`
--

LOCK TABLES `sec_group_role` WRITE;
/*!40000 ALTER TABLE `sec_group_role` DISABLE KEYS */;
INSERT INTO `sec_group_role` (`group_id`, `role_id`) VALUES (1,1);
/*!40000 ALTER TABLE `sec_group_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_role`
--

DROP TABLE IF EXISTS `sec_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sec_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(90) COLLATE utf8_swedish_ci NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `role` varchar(90) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role` (`role`),
  UNIQUE KEY `UK_2n3qbrc5tu07q0xyi94caepcp` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_role`
--
-- ORDER BY:  `role_id`

LOCK TABLES `sec_role` WRITE;
/*!40000 ALTER TABLE `sec_role` DISABLE KEYS */;
INSERT INTO `sec_role` (`role_id`, `descricao`, `inserted`, `updated`, `role`, `version`) VALUES (1,'Administrador do Sistema','2012-02-13 00:51:42','2012-02-13 00:51:45','ROLE_ADMIN',0);
/*!40000 ALTER TABLE `sec_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_user`
--

DROP TABLE IF EXISTS `sec_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sec_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ativo` tinyint(1) NOT NULL,
  `email` varchar(90) COLLATE utf8_swedish_ci NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `nome` varchar(90) COLLATE utf8_swedish_ci NOT NULL,
  `senha` varchar(90) COLLATE utf8_swedish_ci NOT NULL,
  `username` varchar(90) COLLATE utf8_swedish_ci NOT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `UK_5ctbdrlf3eismye20vsdtk8w8` (`username`),
  KEY `FK375DF2F9D5F739E8` (`group_id`),
  CONSTRAINT `FK375DF2F9D5F739E8` FOREIGN KEY (`group_id`) REFERENCES `sec_group` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_user`
--
-- ORDER BY:  `user_id`

LOCK TABLES `sec_user` WRITE;
/*!40000 ALTER TABLE `sec_user` DISABLE KEYS */;
INSERT INTO `sec_user` (`user_id`, `ativo`, `email`, `inserted`, `updated`, `nome`, `senha`, `username`, `group_id`, `version`) VALUES (1,1,'carlospauluk@gmail.com','2012-02-13 00:50:39','2012-08-03 00:18:53','CARLOS EDUARDO PAULUK','754116f9f2d2144a7acafbff146e08bf44708db368248e0e66277c9a4f1dbc0f5a6fe367957e36f8','carlos',1,0);
/*!40000 ALTER TABLE `sec_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_user_role`
--

DROP TABLE IF EXISTS `sec_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sec_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FK7DE039FC790C7FAC` (`role_id`),
  KEY `FK7DE039FC1E37438C` (`user_id`),
  CONSTRAINT `FK7DE039FC1E37438C` FOREIGN KEY (`user_id`) REFERENCES `sec_user` (`user_id`),
  CONSTRAINT `FK7DE039FC790C7FAC` FOREIGN KEY (`role_id`) REFERENCES `sec_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_user_role`
--

LOCK TABLES `sec_user_role` WRITE;
/*!40000 ALTER TABLE `sec_user_role` DISABLE KEYS */;
INSERT INTO `sec_user_role` (`user_id`, `role_id`) VALUES (1,1);
/*!40000 ALTER TABLE `sec_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-20 11:37:34
