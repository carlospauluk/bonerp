-- MySQL dump 10.13  Distrib 5.5.36, for Win64 (x86)
--
-- Host: localhost    Database: bonerp_dev
-- ------------------------------------------------------
-- Server version	5.5.36

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
INSERT INTO `bon_cliente` VALUES (1,'',99900001,NULL,NULL,'05242105941','2014-03-28 17:18:04','',NULL,NULL,'','','','',NULL,NULL,'2014-03-28 17:18:12','2014-03-28 17:18:12','','CARLOS EDUARDO PAULUK',NULL,'','',NULL,0,NULL);
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
  UNIQUE KEY `enderecos_endereco_id` (`enderecos_endereco_id`),
  UNIQUE KEY `UK_k789o835dbnne19xbk2upt9le` (`enderecos_endereco_id`),
  KEY `FK112A5E6AAF152D25` (`enderecos_endereco_id`),
  KEY `FK112A5E6AD0B9D1B8` (`bon_cliente_id`),
  CONSTRAINT `FK112A5E6AAF152D25` FOREIGN KEY (`enderecos_endereco_id`) REFERENCES `bon_endereco` (`endereco_id`),
  CONSTRAINT `FK112A5E6AD0B9D1B8` FOREIGN KEY (`bon_cliente_id`) REFERENCES `bon_cliente` (`id`)
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
INSERT INTO `bon_dia_util` VALUES (1,'2014-05-02 00:00:00','2014-05-15 17:23:57','2014-05-15 17:23:59',0),(2,'2014-05-03 00:00:00','2014-05-15 17:26:00','2014-05-15 17:26:00',0),(3,'2014-05-05 00:00:00','2014-05-15 17:26:11','2014-05-15 17:26:11',0),(4,'2014-05-06 00:00:00','2014-05-15 17:26:14','2014-05-15 17:26:14',0),(5,'2014-05-07 00:00:00','2014-05-15 17:26:16','2014-05-15 17:26:16',0),(6,'2014-05-08 00:00:00','2014-05-15 17:26:18','2014-05-15 17:26:18',0),(7,'2014-05-09 00:00:00','2014-05-15 17:26:20','2014-05-15 17:26:20',0),(8,'2014-05-10 00:00:00','2014-05-15 17:26:25','2014-05-15 17:26:25',0),(9,'2014-05-12 00:00:00','2014-05-15 17:26:31','2014-05-15 17:26:31',0),(10,'2014-05-13 00:00:00','2014-05-15 17:26:33','2014-05-15 17:26:33',0),(11,'2014-05-14 00:00:00','2014-05-15 17:26:35','2014-05-15 17:26:35',0),(12,'2014-05-15 00:00:00','2014-05-15 17:26:38','2014-05-15 17:26:38',0),(13,'2014-05-16 00:00:00','2014-05-15 17:26:39','2014-05-15 17:26:39',0),(14,'2014-05-17 00:00:00','2014-05-15 17:26:42','2014-05-15 17:26:42',0),(15,'2014-05-19 00:00:00','2014-05-15 17:26:50','2014-05-15 17:26:50',0),(16,'2014-05-20 00:00:00','2014-05-15 17:26:53','2014-05-15 17:26:53',0),(17,'2014-05-21 00:00:00','2014-05-15 17:26:55','2014-05-15 17:26:55',0),(18,'2014-05-22 00:00:00','2014-05-15 17:26:57','2014-05-15 17:26:57',0),(19,'2014-05-23 00:00:00','2014-05-15 17:26:59','2014-05-15 17:26:59',0),(20,'2014-05-24 00:00:00','2014-05-15 17:27:01','2014-05-15 17:27:01',0),(21,'2014-05-26 00:00:00','2014-05-15 17:27:06','2014-05-15 17:27:06',0),(22,'2014-05-27 00:00:00','2014-05-15 17:27:08','2014-05-15 17:27:08',0),(23,'2014-05-28 00:00:00','2014-05-15 17:27:10','2014-05-15 17:27:10',0),(24,'2014-05-29 00:00:00','2014-05-15 17:27:12','2014-05-15 17:27:12',0),(25,'2014-05-30 00:00:00','2014-05-15 17:27:16','2014-05-15 17:27:16',0),(26,'2014-05-31 00:00:00','2014-05-15 17:27:18','2014-05-15 17:27:18',0);
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
  `bairro` varchar(120) COLLATE utf8_swedish_ci NOT NULL,
  `cep` varchar(9) COLLATE utf8_swedish_ci NOT NULL,
  `cidade` varchar(80) COLLATE utf8_swedish_ci NOT NULL,
  `complemento` varchar(120) COLLATE utf8_swedish_ci DEFAULT NULL,
  `estado` varchar(2) COLLATE utf8_swedish_ci NOT NULL,
  `logradouro` varchar(120) COLLATE utf8_swedish_ci NOT NULL,
  `numero` int(11) NOT NULL,
  `tipoEndereco` varchar(100) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`endereco_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bon_endereco`
--
-- ORDER BY:  `endereco_id`

LOCK TABLES `bon_endereco` WRITE;
/*!40000 ALTER TABLE `bon_endereco` DISABLE KEYS */;
/*!40000 ALTER TABLE `bon_endereco` ENABLE KEYS */;
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
INSERT INTO `bon_funcionario` VALUES (1,'',99,'99999999999',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 11:56:52','2014-04-17 18:21:51','','FUNCIONÁRIOS','','',NULL,1,'FUNCIONARIOS','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(9,'',1,'21512582972',NULL,'1951-02-20 00:00:00','1987-10-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:23:15','2014-04-29 12:27:06','','VALMIR FERREIRA DA SILVA','','',NULL,6,'VALMIR','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(10,'',2,'00000000002',NULL,'1996-07-27 00:00:00','2013-01-02 00:00:00','2013-08-13 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:24:14','2014-04-29 11:47:06','','BRUNA ANGELITA TLOMATZKI','','',NULL,6,'BRUNA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(11,'',2,'00000000022',NULL,'1900-01-01 00:00:00','2012-03-01 00:00:00','2012-12-31 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:24:54','2014-04-17 18:24:54','','NOELI','','',NULL,0,'NOELI','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(12,'',3,'00000000003',NULL,'1900-01-01 00:00:00','2012-01-01 00:00:00','2013-03-31 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:25:18','2014-04-17 18:25:18','','ALINE','','',NULL,0,'ALINE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(13,'',3,'10063918943',NULL,'1994-09-02 00:00:00','2014-01-07 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:25:47','2014-05-20 11:38:38','','BIANCA STADLER GONÇALVES','','',NULL,8,'BIANCA','31B9B9A70EE17624BA0520E270ED70D141E8ADCF535D6C7FEEDA0D5F3630682EFDCFAA4C4C9A8635'),(14,'',3,'00396379990',NULL,'1977-11-16 00:00:00','2013-04-18 00:00:00','2013-10-07 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:26:13','2014-04-29 12:02:02','','ELAINE CRISTINA FERREIRA ROSA','','',NULL,4,'ELAINE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(15,'',4,'09886014946',NULL,'1995-01-01 00:00:00','2013-03-11 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:26:55','2014-05-20 11:38:49','','MIRIÃ SANTOS MEDEIROS','','',NULL,11,'MIRIA','80D8F935F9DDB7B99D17EAE8730A66B552F9EC1DE0538C5F6FE36259DC79052B73FF1B1B34A2AC3A'),(16,'',5,'00000000005',NULL,NULL,'2012-01-01 00:00:00','2012-08-30 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:27:26','2014-04-17 18:27:26','','ANDRESSA','','',NULL,0,'ANDRESSA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(17,'',5,'07977992906',NULL,'1991-02-21 00:00:00','2013-03-11 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:27:48','2014-05-20 11:42:03','','KEVINI MENON','','',NULL,5,'KEVINI','E30382A7D6E8D158511E70DEB20BB32AE6566F97C3F3628A652423EBD87944ED32D1E64D2A030D27'),(18,'',6,'56157126949',NULL,'1963-04-11 00:00:00','1980-10-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:28:06','2014-04-28 14:25:51','','ANA SENIUK','','',NULL,4,'ANA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(19,'',7,'09131684912',NULL,'1995-02-17 00:00:00','2013-01-02 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:28:25','2014-05-20 11:42:13','','CHRISTIAN ALESSANDRO ZUBACZ','','',NULL,5,'CRISTIAN','5CC1926601055D3B050E0D06E72DCC115EA79133E63D7E7938D941BBBA375CDFF66EA04C7FC53607'),(22,'',7,'00000000077',NULL,NULL,'2012-11-01 00:00:00','2012-12-31 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:28:56','2014-04-17 18:28:56','','WANESSA','','',NULL,0,'WANESSA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(23,'',8,'72202637915',NULL,'1967-09-28 00:00:00','1987-06-08 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:29:15','2014-04-28 14:24:26','','JANE DA SILVA','','',NULL,7,'JANE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(24,'',9,'00000000009',NULL,NULL,'2012-01-01 00:00:00','2014-02-28 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:29:38','2014-04-22 11:52:03','','ROSE','','',NULL,1,'ROSE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(25,'',10,'00000000010',NULL,NULL,'1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:29:57','2014-04-17 18:29:57','','TONINHO','','',NULL,0,'TONINHO','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(26,'',11,'00413662985',NULL,'1976-05-31 00:00:00','2013-08-16 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:30:19','2014-05-20 11:42:21','','ROSICLEIA NIZER PEREIRA','','',NULL,5,'ROSICLEIA','30075804BCA1F5377989C9529981449F543E3AEC3871F302EBF9737E8618DD366588B3EA584F3286'),(27,'',11,'00000000111',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00','2013-03-31 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:37:24','2014-04-17 18:37:24','','ZILDA','','',NULL,0,'ZILDA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(28,'',12,'00000000012',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-17 18:37:38','2014-04-17 18:37:38','','MARIZA','','',NULL,0,'MARIZA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(29,'',13,'07083780994',NULL,'1988-11-20 00:00:00','2013-10-01 00:00:00','2014-04-26 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:38:03','2014-04-29 12:07:54','','VIVIANE OPATA','','',NULL,6,'VIVIANE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(30,'',14,'07715839906',NULL,'1989-09-14 00:00:00','2013-02-01 00:00:00','2013-11-30 00:00:00','',NULL,NULL,'','','','','2014-04-17 18:38:33','2014-04-29 11:50:51','','ELIANE PAUK KOSSAR','','',NULL,4,'ELIANE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(31,'',15,'00000000015',NULL,'1900-01-01 00:00:00','2012-01-01 00:00:00','2012-03-31 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:51:25','2014-04-28 09:01:19','','ADRIANE','','',NULL,1,'ADRIANE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(32,'',15,'10894963970',NULL,'1996-09-22 00:00:00','2014-03-20 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-22 11:51:42','2014-05-20 11:42:28','','KELVIN KIELT FRANCO','','',NULL,4,'KELVIN','66DF999B759A9DB757CFCB6DF0877E04CCEABED0149653368C5F477FBC8296A7339F979CBB34FC05'),(33,'',15,'00000001115',NULL,'1993-10-20 00:00:00','2012-08-01 00:00:00','2014-03-11 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:51:59','2014-04-29 11:41:52','','LORIANI RAFAELA GUIMARÃES','','',NULL,6,'LORIANI','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(34,'',16,'04709595925',NULL,'1987-04-30 00:00:00','2012-08-10 00:00:00','2013-03-14 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:52:17','2014-04-28 18:16:41','','ANA MARIA CANTERI','','',NULL,2,'ANA MARIA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(35,'',16,'00000000116',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00','2012-06-30 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:52:29','2014-04-28 09:03:16','','ANINHA','','',NULL,1,'ANINHA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(36,'',16,'10426036930',NULL,'1996-10-23 00:00:00','2013-10-09 00:00:00','2014-01-06 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:52:41','2014-04-29 12:06:43','','ISABELA BUENO DE CAMARGO','','',NULL,4,'ISABELA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(37,'',17,'00000000017',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00','2013-10-30 00:00:00','',NULL,NULL,'','','','','2014-04-22 11:52:53','2014-04-28 09:04:01','','SELMA','','',NULL,1,'SELMA','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(38,'',18,'03869889942',NULL,'1981-05-25 00:00:00','2011-04-09 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-22 11:53:06','2014-05-20 11:43:02','','DANIELE ALVES BANNACH','','',NULL,8,'DANIELE','CA34A7CEEC553BFF9F8CEE749363BCC1F4FBFB91E0D28733EA21B73A1D6FD9B97366441D7B997ABF'),(39,'',19,'10261615920',NULL,'1996-11-18 00:00:00','2013-12-09 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-22 11:53:43','2014-05-20 11:43:13','','CARLOS HENRIQUE CARNEIRO DOS SANTOS','','',NULL,6,'CARLOS HENRIQUE','DE5793153C6CDAF583181960BADC42FD4FD5EE5D7B13AD124EF53E0C1006AF85C7D3F67860D1FA98'),(42,'',22,'04975779927',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-22 11:54:13','2014-05-02 09:40:08','','ANNE CRISTINE DE ALMEIRA PRADO','','',NULL,8,'ANNE CRISTINE','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(44,'',17,'09990056935',NULL,'1996-12-09 00:00:00','2014-01-07 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-26 16:25:53','2014-05-20 11:42:35','','RAFAELE DUARTE DE CAMARGO','','',NULL,6,'RAFAELE','61F4B506B8A1D93DC9AAEEB19386EA81CC6F994B9DF7556A53E347C798C99665FF049E076381626A'),(45,'',901,'08917154956',NULL,'1994-04-16 00:00:00','2010-12-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-28 14:29:47','2014-04-28 14:32:32','','MAKELLY KAONE DOS SANTOS','','',NULL,5,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(46,'',902,'02722825961',NULL,'1973-04-21 00:00:00','2011-10-03 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-28 18:10:04','2014-04-28 18:11:29','','JOSEMERI DE FATIMA COMINEZE','','',NULL,3,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(47,'',903,'75586282968',NULL,'1989-07-13 00:00:00','2012-04-04 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-29 11:41:25','2014-04-29 11:41:25','','RENATO DE OLIVEIRA','','',NULL,0,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(48,'',904,'05215397902',NULL,'1967-06-20 00:00:00','2013-02-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-29 11:51:20','2014-04-29 11:51:48','','SILVANI TEREZINHA RIBEIRO','','',NULL,3,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(49,'',905,'08928179920',NULL,'1993-06-04 00:00:00','2013-04-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-29 11:57:15','2014-04-29 11:58:08','','OSNIM GIOVANNI FARIAS CORDOVA','','',NULL,4,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(50,'',906,'10282649921',NULL,'1994-10-05 00:00:00','2013-10-09 00:00:00','2014-01-06 00:00:00','',NULL,NULL,'','','','','2014-04-29 12:05:20','2014-04-29 12:05:43','','PAULA DAIANE FERREIRA DE PAIVA MAIOR','','',NULL,2,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(51,'',98,'98989898989',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-29 12:15:21','2014-04-29 12:15:37','','LICITAÇÕES','','',NULL,1,'LICITACOES','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(52,'',907,'10192952951',NULL,'1995-10-29 00:00:00','2014-04-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-04-29 12:16:07','2014-04-29 12:17:57','','RULIANE APARECIDA CAMPOS RAMOS','','',NULL,2,NULL,'de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a'),(53,'',50,'00000000050',NULL,'1900-01-01 00:00:00','1900-01-01 00:00:00',NULL,'',NULL,NULL,'','','','','2014-05-02 09:40:32','2014-05-02 09:40:32','','PAULUK','','',NULL,0,'PAULUK','de80276b4224f77c6df24a0564e2f61669eba24380f2b5d38ffde3da095e9942c72dd1790d9a632a');
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
  UNIQUE KEY `UK_9rho31mktown43jkmae0okmuf` (`enderecos_endereco_id`),
  KEY `FK_fxbje6nieot9tn2ol3owus5ov` (`bon_funcionario_id`),
  CONSTRAINT `FK_9rho31mktown43jkmae0okmuf` FOREIGN KEY (`enderecos_endereco_id`) REFERENCES `bon_endereco` (`endereco_id`),
  CONSTRAINT `FK_fxbje6nieot9tn2ol3owus5ov` FOREIGN KEY (`bon_funcionario_id`) REFERENCES `bon_funcionario` (`id`)
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
INSERT INTO `bon_funcionario_cargo` VALUES (2,'VENDEDOR','2013-03-01 00:00:00','2014-04-24 14:37:18','2014-04-24 14:37:18',820,2,15,1,'2013-04-30 00:00:00',713.66),(6,'VENDEDOR','2013-10-01 00:00:00','2014-04-24 15:26:59','2014-04-24 15:26:59',920,2,29,1,'2013-10-31 00:00:00',722),(7,'AUXILIAR_VENDAS','2014-01-07 00:00:00','2014-04-28 09:07:03','2014-04-28 09:07:03',920,2,13,1,'2014-03-31 00:00:00',722),(8,'GERENTE','2012-05-01 00:00:00','2014-04-28 12:15:51','2014-04-28 12:15:51',1059.48,1,9,0,'2013-04-30 00:00:00',1059.48),(9,'GERENTE','2013-05-01 00:00:00','2014-04-28 12:26:32','2014-04-28 12:26:32',1154.83,0,9,0,NULL,1154.83),(10,'AUXILIAR_SERVICOS_GERAIS','2012-01-01 00:00:00','2014-04-28 14:23:08','2014-04-28 14:23:08',800,1,23,0,'2012-04-30 00:00:00',800),(11,'AUXILIAR_SERVICOS_GERAIS','2012-05-01 00:00:00','2014-04-28 14:23:26','2014-04-28 14:23:26',850,1,23,0,'2012-08-31 00:00:00',850),(12,'AUXILIAR_SERVICOS_GERAIS','2012-09-01 00:00:00','2014-04-28 14:23:38','2014-04-28 14:23:38',860,1,23,0,'2013-04-30 00:00:00',860),(13,'AUXILIAR_SERVICOS_GERAIS','2013-05-01 00:00:00','2014-04-28 14:23:53','2014-04-28 14:23:53',950,1,23,0,'2013-10-31 00:00:00',950),(14,'AUXILIAR_SERVICOS_GERAIS','2013-11-01 00:00:00','2014-04-28 14:24:14','2014-04-28 14:24:14',1000,0,23,0,NULL,1000),(15,'CAIXA','2012-05-01 00:00:00','2014-04-28 14:25:15','2014-04-28 14:25:15',960.12,1,18,0,'2013-04-30 00:00:00',960.12),(16,'CAIXA','2013-05-01 00:00:00','2014-04-28 14:25:34','2014-04-28 14:25:34',1046.53,0,18,0,NULL,1046.53),(17,'CREDIARISTA','2012-01-01 00:00:00','2014-04-28 14:29:55','2014-04-28 14:29:55',670,2,45,0,'2012-04-30 00:00:00',670),(18,'CREDIARISTA','2012-05-01 00:00:00','2014-04-28 14:30:14','2014-04-28 14:30:14',723.6,1,45,0,'2012-10-31 00:00:00',723.6),(19,'CREDIARISTA','2012-11-01 00:00:00','2014-04-28 14:30:41','2014-04-28 14:30:41',750,1,45,0,'2013-04-30 00:00:00',750),(20,'CREDIARISTA','2013-05-01 00:00:00','2014-04-28 14:30:52','2014-04-28 14:30:52',817.5,0,45,0,NULL,817.5),(21,'VENDEDOR','2012-05-01 00:00:00','2014-04-28 14:33:21','2014-04-28 14:33:21',820,3,38,1,'2013-04-30 00:00:00',713.66),(22,'VENDEDOR','2013-05-01 00:00:00','2014-04-28 14:40:01','2014-04-28 14:40:01',920,1,38,1,NULL,777.89),(23,'CORTE','2012-05-01 00:00:00','2014-04-28 18:10:10','2014-04-28 18:10:10',713.66,1,46,0,'2013-04-30 00:00:00',713.66),(24,'CORTE','2013-05-01 00:00:00','2014-04-28 18:10:33','2014-04-28 18:10:33',777.89,0,46,0,NULL,777.89),(25,'VENDEDOR','2012-08-01 00:00:00','2014-04-28 18:13:32','2014-04-28 18:13:32',820,2,33,1,'2013-04-30 00:00:00',713.66),(26,'VENDEDOR','2013-05-01 00:00:00','2014-04-28 18:15:26','2014-04-28 18:15:26',920,0,33,1,'2014-03-11 00:00:00',777.89),(27,'VENDEDOR','2012-10-01 00:00:00','2014-04-29 11:43:28','2014-04-29 11:43:28',820,1,42,1,'2013-04-30 00:00:00',713.66),(28,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:44:03','2014-04-29 11:44:03',920,1,42,1,'2013-08-31 00:00:00',777.89),(29,'CREDIARISTA','2013-09-01 00:00:00','2014-04-29 11:44:19','2014-04-29 11:44:19',817.5,0,42,0,NULL,817.5),(30,'VENDEDOR','2013-01-01 00:00:00','2014-04-29 11:45:23','2014-04-29 11:45:23',820,1,10,1,'2013-04-30 00:00:00',713.66),(31,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:45:37','2014-04-29 11:45:37',920,1,10,1,'2013-08-13 00:00:00',777.89),(32,'VENDEDOR','2013-01-01 00:00:00','2014-04-29 11:47:58','2014-04-29 11:47:58',820,1,19,1,'2013-04-30 00:00:00',713.66),(33,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:48:50','2014-04-29 11:48:50',920,0,19,1,NULL,777.89),(34,'VENDEDOR','2013-02-01 00:00:00','2014-04-29 11:50:21','2014-04-29 11:50:21',820,1,30,1,'2013-04-30 00:00:00',713.66),(35,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:50:40','2014-04-29 11:50:40',920,0,30,1,'2013-11-30 00:00:00',777.89),(36,'AUXILIAR_SERVICOS_GERAIS','2013-02-01 00:00:00','2014-04-29 11:51:25','2014-04-29 11:51:25',828,1,48,0,'2013-04-30 00:00:00',828),(37,'AUXILIAR_SERVICOS_GERAIS','2013-05-01 00:00:00','2014-04-29 11:51:37','2014-04-29 11:51:37',920,0,48,0,NULL,920),(38,'VENDEDOR','2013-03-01 00:00:00','2014-04-29 11:52:20','2014-04-29 11:52:20',820,1,17,1,'2013-04-30 00:00:00',713.66),(39,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:52:37','2014-04-29 11:52:37',920,0,17,1,NULL,777.89),(40,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 11:53:48','2014-04-29 11:53:48',920,0,15,1,NULL,777.89),(41,'ESTOQUISTA','2013-04-01 00:00:00','2014-04-29 11:57:23','2014-04-29 11:57:23',713.66,1,49,0,'2013-04-30 00:00:00',713.66),(42,'ESTOQUISTA','2013-05-01 00:00:00','2014-04-29 11:57:46','2014-04-29 11:57:46',920,1,49,0,'2013-10-31 00:00:00',920),(43,'ESTOQUISTA','2013-11-01 00:00:00','2014-04-29 11:57:56','2014-04-29 11:57:56',966,0,49,0,NULL,966),(44,'VENDEDOR','2013-04-18 00:00:00','2014-04-29 12:01:27','2014-04-29 12:01:27',820,1,14,1,'2013-04-30 00:00:00',713.66),(45,'VENDEDOR','2013-05-01 00:00:00','2014-04-29 12:01:51','2014-04-29 12:01:51',920,0,14,1,'2013-10-07 00:00:00',777.89),(46,'VENDEDOR','2013-08-16 00:00:00','2014-04-29 12:03:03','2014-04-29 12:03:03',920,2,26,1,'2013-10-31 00:00:00',720),(47,'VENDEDOR','2013-11-01 00:00:00','2014-04-29 12:03:14','2014-04-29 12:03:14',920,0,26,1,NULL,777.89),(48,'AUXILIAR_VENDAS','2013-10-09 00:00:00','2014-04-29 12:05:25','2014-04-29 12:05:25',722,0,50,0,'2014-01-06 00:00:00',722),(49,'VENDEDOR','2013-10-09 00:00:00','2014-04-29 12:06:27','2014-04-29 12:06:27',920,0,36,1,'2014-01-06 00:00:00',722),(50,'VENDEDOR','2013-11-01 00:00:00','2014-04-29 12:07:41','2014-04-29 12:07:41',920,0,29,1,'2014-04-26 00:00:00',777.89),(51,'VENDEDOR','2013-12-01 00:00:00','2014-04-29 12:09:10','2014-04-29 12:09:10',920,1,39,1,'2014-02-28 00:00:00',722),(52,'VENDEDOR','2014-03-01 00:00:00','2014-04-29 12:09:32','2014-04-29 12:09:32',920,0,39,1,NULL,777.89),(53,'AUXILIAR_VENDAS','2014-01-07 00:00:00','2014-04-29 12:10:36','2014-04-29 12:10:36',920,1,44,1,'2014-03-31 00:00:00',722),(54,'AUXILIAR_VENDAS','2014-04-01 00:00:00','2014-04-29 12:10:58','2014-04-29 12:10:58',920,0,44,1,NULL,777.89),(55,'AUXILIAR_VENDAS','2014-04-01 00:00:00','2014-04-29 12:12:13','2014-04-29 12:12:13',920,0,13,1,NULL,777.89),(56,'AUXILIAR_SERVICOS_GERAIS','2014-03-20 00:00:00','2014-04-29 12:13:26','2014-04-29 12:13:26',920,0,32,1,NULL,722),(57,'AUXILIAR_VENDAS','2014-04-01 00:00:00','2014-04-29 12:16:19','2014-04-29 12:16:19',920,0,52,0,NULL,722);
/*!40000 ALTER TABLE `bon_funcionario_cargo` ENABLE KEYS */;
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
INSERT INTO `cfg_config` VALUES (1,'bonsucesso.vendas.custoOperacional','2014-03-28 14:54:38','2014-03-28 14:54:40','0.35',0),(2,'bonsucesso.vendas.custoFinanceiro','2014-03-28 14:55:40','2014-03-28 14:55:43','0.15',0),(3,'bonsucesso.cortinas.prazoValidadeOrcamento','2014-03-28 14:56:22','2014-03-28 14:56:25','30',0),(5,'bonsucesso.vendas.descontoAVista','2014-03-28 15:55:55','2014-03-28 15:55:57','0.10',0),(6,'bonsucesso.cortinas.alturaBarraPadrao','2014-03-28 16:12:55','2014-03-28 16:12:58','0.30',0),(7,'bonsucesso.cortinas.alturaMaxHorizontal','2014-03-28 16:13:36','2014-03-28 16:13:38','1.50',0),(8,'bonsucesso.rh.porcentMetaMinima','2014-05-15 16:32:54','2014-05-15 16:32:54','0.05',0);
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
  `descricao` varchar(200) COLLATE utf8_swedish_ci NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `reduzido` int(11) NOT NULL,
  `tipo` varchar(30) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  `fornecedor_id` bigint(20) NOT NULL,
  `tecido_marca_id` bigint(20) NOT NULL,
  `tecido_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mpymo372juwpeg41ocxo6i7xg` (`tipo`,`descricao`,`fornecedor_id`),
  UNIQUE KEY `UK_lgqt1fsgvojljoihn383ujihk` (`reduzido`),
  KEY `FK_b7a6sgv4tngdfeisxtd0iyqc7` (`fornecedor_id`),
  KEY `FK_4sh0cjeb1qkxwrd7fw9vevrxa` (`tecido_marca_id`),
  KEY `FK_ifbof7r75drdl1um2aey4itwq` (`tecido_id`),
  CONSTRAINT `FK_4sh0cjeb1qkxwrd7fw9vevrxa` FOREIGN KEY (`tecido_marca_id`) REFERENCES `crtn_artigo_cortina_marca` (`id`),
  CONSTRAINT `FK_b7a6sgv4tngdfeisxtd0iyqc7` FOREIGN KEY (`fornecedor_id`) REFERENCES `crtn_fornecedor` (`id`),
  CONSTRAINT `FK_ifbof7r75drdl1um2aey4itwq` FOREIGN KEY (`tecido_id`) REFERENCES `crtn_tecido` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_artigo_cortina`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_artigo_cortina` WRITE;
/*!40000 ALTER TABLE `crtn_artigo_cortina` DISABLE KEYS */;
INSERT INTO `crtn_artigo_cortina` VALUES (1,'VOIL 3.00M','2014-04-05 09:48:09','2014-04-14 11:02:12',999001,'TECIDO',4,1,1,1),(2,'BLACKOUT 2,80M','2014-04-05 09:49:00','2014-04-14 11:07:56',999002,'TECIDO',3,1,1,2),(3,'ACESSÓRIO UM','2014-04-05 10:01:59','2014-04-05 10:01:59',999003,'ACESSORIO',0,1,1,NULL),(4,'ARGOLA UM','2014-04-05 10:02:29','2014-04-14 09:05:06',999004,'ARGOLA',1,1,1,NULL),(5,'ENTRETELA UM','2014-04-05 10:03:00','2014-04-14 15:19:32',999005,'ENTRETELA',2,1,1,NULL),(6,'GANCHO UM','2014-04-05 10:03:23','2014-04-14 09:05:27',999006,'GANCHO',1,1,1,NULL),(7,'ILHÓS UM','2014-04-05 10:04:11','2014-04-14 09:08:14',999007,'ILHOS',1,1,1,NULL),(8,'MÃO-DE-OBRA COSTUREIRA','2014-04-05 10:04:59','2014-04-14 15:00:14',999008,'MAO_DE_OBRA_COSTUREIRA',2,1,1,NULL),(9,'MÃO-DE-OBRA ILHÓS','2014-04-05 10:05:34','2014-04-14 15:49:35',999009,'MAO_DE_OBRA_ILHOS',2,1,1,NULL),(10,'MÃO-DE-OBRA INSTALAÇÃO','2014-04-05 10:06:14','2014-04-05 10:06:14',999010,'MAO_DE_OBRA_INSTALACAO',0,1,1,NULL),(11,'PONTEIRA UM','2014-04-05 10:06:46','2014-04-05 10:06:46',999011,'PONTEIRA',0,1,1,NULL),(12,'RODÍZIO UM','2014-04-05 10:07:18','2014-04-05 10:07:18',999012,'RODIZIO',0,1,1,NULL),(13,'SUPORTE DUPLO UM','2014-04-05 11:31:26','2014-04-14 14:54:15',999013,'SUPORTE_DUPLO',2,1,1,NULL),(14,'SUPORTE TRIPLO UM','2014-04-05 11:44:00','2014-04-14 10:18:38',999014,'SUPORTE_TRIPLO',1,1,1,NULL),(15,'SUPORTE SIMPLES UM','2014-04-05 11:44:33','2014-04-05 11:44:33',999015,'SUPORTE_SIMPLES',0,1,1,NULL),(16,'TERMINAL UM','2014-04-05 11:45:07','2014-04-05 11:45:07',999016,'TERMINAL',0,1,1,NULL),(17,'TRILHO UM','2014-04-05 11:45:34','2014-04-05 11:45:34',999017,'TRILHO',0,1,1,NULL),(18,'VARÃO UM','2014-04-05 11:46:27','2014-04-14 09:06:06',999018,'VARAO',1,1,1,NULL),(19,'BLACKOUT 280LAR 100POL','2014-05-06 09:25:18','2014-05-06 09:25:18',536,'ACESSORIO',0,2,2,NULL),(20,'FILO 100% POLIAMIDA','2014-05-06 09:25:59','2014-05-06 09:25:59',1502,'ACESSORIO',0,3,3,NULL),(21,'TULE 100%POLIAMIDA','2014-05-06 09:26:00','2014-05-06 09:26:00',3241,'ACESSORIO',0,3,3,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_artigo_cortina_marca`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_artigo_cortina_marca` WRITE;
/*!40000 ALTER TABLE `crtn_artigo_cortina_marca` DISABLE KEYS */;
INSERT INTO `crtn_artigo_cortina_marca` VALUES (1,'2014-04-05 09:47:02','2014-04-05 09:47:02','MARCA UM',0),(2,'2014-05-06 09:08:47','2014-05-06 09:08:47','REALCE',0),(3,'2014-05-06 09:08:51','2014-05-06 09:08:51','BRANYL',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_artigo_cortina_preco`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_artigo_cortina_preco` WRITE;
/*!40000 ALTER TABLE `crtn_artigo_cortina_preco` DISABLE KEYS */;
INSERT INTO `crtn_artigo_cortina_preco` VALUES (1,1.764,35,'2014-04-05','2014-04-05 09:48:09','2014-04-05 09:48:09',12,90,5.3,11,0,9.9,2,1),(2,1.938,35,'2014-04-05','2014-04-05 09:49:00','2014-04-05 09:49:00',15,45,16.3,37.2,0,33.48,2,2),(3,2.019,35,'2014-04-05','2014-04-05 10:01:59','2014-04-05 10:01:59',17,40,22,52.3,0,47.07,0,3),(4,1.652,35,'2014-04-05','2014-04-05 10:02:29','2014-04-05 10:02:29',7,60,0.1,0.2,0,0.18,1,4),(5,1.916,35,'2014-04-05','2014-04-05 10:03:00','2014-04-05 10:03:00',15,60,0.8,1.8,0,1.62,2,5),(6,1.916,35,'2014-04-05','2014-04-05 10:03:23','2014-04-05 10:03:23',15,60,0.05,0.1,0,0.09,1,6),(7,1.7,35,'2014-04-05','2014-04-05 10:04:11','2014-04-05 10:04:11',10,80,0.35,0.7,0,0.63,1,7),(8,1.538,35,'2014-04-05','2014-04-05 10:04:59','2014-04-05 10:04:59',0,0,4.5,4.5,0,4.5,2,8),(9,1.538,35,'2014-04-05','2014-04-05 10:05:34','2014-04-05 10:05:34',0,0,0.55,0.55,0,0.55,2,9),(10,1.538,35,'2014-04-05','2014-04-05 10:06:14','2014-04-05 10:06:14',0,0,25,25,0,25,0,10),(11,1.828,35,'2014-04-05','2014-04-05 10:06:46','2014-04-05 10:06:46',12,40,12.5,26.9,0,24.21,0,11),(12,1.711,35,'2014-04-05','2014-04-05 10:07:18','2014-04-05 10:07:18',9,60,0.07,0.1,0,0.09,0,12),(13,2,35,'2014-04-05','2014-04-05 11:31:26','2014-04-05 11:31:26',15,10,3.4,8,0,7.2,2,13),(14,1.924,35,'2014-04-05','2014-04-05 11:44:00','2014-04-05 11:44:00',14,30,7.37,16.7,0,15.03,1,14),(15,2.174,35,'2014-04-05','2014-04-05 11:44:33','2014-04-05 11:44:33',22,90,5,12.8,0,11.52,0,15),(16,1.828,35,'2014-04-05','2014-04-05 11:45:07','2014-04-05 11:45:07',12,45,0.15,0.3,0,0.27,0,16),(17,1.742,35,'2014-04-05','2014-04-05 11:45:34','2014-04-05 11:45:34',10,60,3.3,6.8,0,6.12,0,17),(18,1.962,35,'2014-04-05','2014-04-05 11:46:27','2014-04-05 11:46:27',15,30,2.8,6.5,0,5.85,1,18),(19,1.916,35,'2013-07-03','2014-05-06 09:25:18','2014-05-06 09:25:18',15,60,16.51,37.2,0,33.48,0,19),(20,2.044,35,'2012-09-05','2014-05-06 09:25:59','2014-05-06 09:25:59',17,30,1.85,4.4,0,3.96,0,20),(21,2.002,35,'2012-09-05','2014-05-06 09:26:00','2014-05-06 09:26:00',16,30,1.65,3.9,0,3.51,0,21);
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
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `largura` double NOT NULL,
  `largura_janela` double NOT NULL,
  `ordem` int(11) NOT NULL,
  `qtde_camadas` int(11) NOT NULL,
  `varao_trilho` varchar(30) COLLATE utf8_swedish_ci NOT NULL,
  `version` int(11) NOT NULL,
  `orcamento_id` bigint(20) NOT NULL,
  `descricao` varchar(300) COLLATE utf8_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k3aw2ps8ybffxpotpd6wkq966` (`orcamento_id`,`ordem`),
  CONSTRAINT `FK_57w7rdg3h6ixw93alpgelytxm` FOREIGN KEY (`orcamento_id`) REFERENCES `crtn_orcamento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_cortina`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_cortina` WRITE;
/*!40000 ALTER TABLE `crtn_cortina` DISABLE KEYS */;
INSERT INTO `crtn_cortina` VALUES (1,2.3,2.3,'','','2014-04-08 17:45:20','2014-04-14 15:12:41',3.9,3.9,1,2,'VARAO',27,1,'BLACKOUT E VOIL EM ARGOLAS'),(2,2.3,3.9,'','','2014-04-14 15:15:11','2014-04-14 15:43:26',3.9,3.9,2,2,'VARAO',9,1,'BLACKOUT E VOIL COM ILHOS'),(3,2.55,2.55,'','','2014-04-15 10:34:32','2014-04-15 10:38:58',1.2,1.2,3,2,'VARAO',2,1,'CORTINA DA PORTA DO QUARTO');
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
  `qtde` double NOT NULL,
  `tecido_tipo_fixacao` varchar(30) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  `artigo_cortina_id` bigint(20) NOT NULL,
  `cortina_id` bigint(20) NOT NULL,
  `tipoPrega` varchar(100) COLLATE utf8_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ag4tfjjdseikfh5fq2g8bui68` (`cortina_id`,`artigo_cortina_id`,`qtde`,`camada`) USING BTREE,
  UNIQUE KEY `UK_rpwujhept2cwwm1mfx4ih3lcc` (`cortina_id`,`artigo_cortina_id`,`qtde`,`camada`),
  KEY `FK_ekjswdilu2w64y3p2ixlm1oal` (`artigo_cortina_id`),
  CONSTRAINT `FK_1f6eom4e7tljveepqcr3j6to0` FOREIGN KEY (`cortina_id`) REFERENCES `crtn_cortina` (`id`),
  CONSTRAINT `FK_ekjswdilu2w64y3p2ixlm1oal` FOREIGN KEY (`artigo_cortina_id`) REFERENCES `crtn_artigo_cortina` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_cortina_item`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_cortina_item` WRITE;
/*!40000 ALTER TABLE `crtn_cortina_item` DISABLE KEYS */;
INSERT INTO `crtn_cortina_item` VALUES (1,NULL,999,NULL,'2014-04-12 12:14:58','2014-04-14 12:16:06',NULL,NULL,23.4,NULL,11,8,1,NULL),(2,0.3,2,4,'2014-04-12 12:47:58','2014-04-14 12:33:18',NULL,'HORIZONTAL',15.6,'ARGOLA',17,1,1,NULL),(3,NULL,1,NULL,'2014-04-14 10:23:35','2014-04-14 10:23:35',NULL,NULL,40,NULL,1,4,1,NULL),(7,NULL,2,NULL,'2014-04-14 10:26:24','2014-04-14 10:26:24',NULL,NULL,40,NULL,1,4,1,NULL),(8,NULL,1,NULL,'2014-04-14 10:43:53','2014-04-14 10:43:53',NULL,NULL,40,NULL,1,6,1,NULL),(9,NULL,1,NULL,'2014-04-14 10:52:35','2014-04-14 10:52:35',NULL,NULL,4.05,NULL,1,18,1,NULL),(10,NULL,2,NULL,'2014-04-14 10:52:43','2014-04-14 10:52:43',NULL,NULL,4.05,NULL,1,18,1,NULL),(11,0.3,1,2,'2014-04-14 10:53:18','2014-04-14 10:58:20',NULL,'HORIZONTAL',7.8,NULL,7,2,1,NULL),(12,NULL,2,NULL,'2014-04-14 10:54:25','2014-04-14 10:54:25',NULL,NULL,40,NULL,1,6,1,NULL),(14,NULL,999,NULL,'2014-04-14 12:26:50','2014-04-14 15:09:28',NULL,NULL,2,NULL,3,13,1,NULL),(15,NULL,999,NULL,'2014-04-14 14:54:48','2014-04-14 14:54:48',NULL,NULL,1,NULL,2,10,1,NULL),(16,0.3,1,2,'2014-04-14 15:16:24','2014-04-14 15:16:24',NULL,'HORIZONTAL',7.8,'ARGOLA',1,2,2,NULL),(17,0.3,2,3,'2014-04-14 15:16:50','2014-04-14 15:16:50',NULL,'HORIZONTAL',11.7,'ILHOS',1,1,2,NULL),(18,NULL,1,NULL,'2014-04-14 15:17:08','2014-04-14 15:17:08',NULL,NULL,40,NULL,1,4,2,NULL),(19,NULL,1,NULL,'2014-04-14 15:17:19','2014-04-14 15:17:19',NULL,NULL,40,NULL,1,6,2,NULL),(20,NULL,1,NULL,'2014-04-14 15:17:34','2014-04-14 15:17:34',NULL,NULL,4.05,NULL,1,18,2,NULL),(21,NULL,2,NULL,'2014-04-14 15:17:44','2014-04-14 15:17:44',NULL,NULL,4.05,NULL,1,18,2,NULL),(22,NULL,999,NULL,'2014-04-14 15:17:59','2014-04-14 15:17:59',NULL,NULL,2,NULL,1,14,2,NULL),(23,NULL,2,NULL,'2014-04-14 15:18:12','2014-04-14 15:18:12',NULL,NULL,11.7,NULL,1,5,2,NULL),(24,NULL,2,NULL,'2014-04-14 15:18:25','2014-04-14 15:18:25',NULL,NULL,80,NULL,2,7,2,NULL),(25,NULL,999,NULL,'2014-04-14 15:18:37','2014-04-14 15:18:37',NULL,NULL,19.5,NULL,1,8,2,NULL),(26,NULL,999,NULL,'2014-04-14 15:18:47','2014-04-14 15:18:47',NULL,NULL,80,NULL,2,9,2,NULL),(27,NULL,999,NULL,'2014-04-14 15:18:58','2014-04-14 15:18:58',NULL,NULL,1,NULL,1,10,2,NULL),(28,NULL,1,NULL,'2014-04-15 10:35:08','2014-04-15 10:35:08',NULL,NULL,1.3499999999999999,NULL,1,18,3,NULL),(29,NULL,999,NULL,'2014-04-15 10:35:19','2014-04-15 10:35:19',NULL,NULL,1,NULL,1,13,3,NULL),(30,NULL,1,NULL,'2014-04-15 10:35:33','2014-04-15 10:35:33',NULL,NULL,14,NULL,1,4,3,NULL),(31,NULL,1,NULL,'2014-04-15 10:35:45','2014-04-15 10:35:45',NULL,NULL,14,NULL,1,6,3,NULL),(32,NULL,1,NULL,'2014-04-15 10:35:54','2014-04-15 10:35:54',NULL,NULL,0,NULL,0,11,3,NULL),(33,0.25,1,1,'2014-04-15 10:37:00','2014-04-15 12:14:32',NULL,'HORIZONTAL',2.8,'ARGOLA',3,2,3,NULL),(34,NULL,2,NULL,'2014-04-15 10:37:17','2014-04-15 10:37:17',NULL,NULL,1.3499999999999999,NULL,1,18,3,NULL),(35,NULL,2,NULL,'2014-04-15 10:37:33','2014-04-15 10:37:33',NULL,NULL,0,NULL,0,11,3,NULL),(36,NULL,2,NULL,'2014-04-15 10:37:42','2014-04-15 10:37:42',NULL,NULL,14,NULL,1,6,3,NULL),(37,NULL,2,NULL,'2014-04-15 10:37:54','2014-04-15 10:37:54',NULL,NULL,14,NULL,1,4,3,NULL),(38,0.3,2,4,'2014-04-15 10:38:21','2014-04-15 10:38:21',NULL,'HORIZONTAL',4.8,'ARGOLA',1,1,3,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_cortina_lado`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_cortina_lado` WRITE;
/*!40000 ALTER TABLE `crtn_cortina_lado` DISABLE KEYS */;
INSERT INTO `crtn_cortina_lado` VALUES (7,'2014-04-14 10:16:39','2014-04-14 10:16:39',1.95,0,1),(8,'2014-04-14 10:16:39','2014-04-14 10:16:39',1.95,0,1),(11,'2014-04-14 15:15:41','2014-04-14 15:15:41',1.95,0,2),(12,'2014-04-14 15:15:41','2014-04-14 15:15:41',1.95,0,2),(13,'2014-04-15 10:34:48','2014-04-15 10:34:48',1.2,0,3);
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
  UNIQUE KEY `prazo_ini` (`prazo_ini`),
  UNIQUE KEY `prazo_fim` (`prazo_fim`),
  UNIQUE KEY `UK_56b4d19h8sg8q3s7b7tr00fxm` (`prazo_ini`),
  UNIQUE KEY `UK_bgsaagmrnnpp17bw8ylxf257a` (`prazo_fim`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_depreciacao_preco`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_depreciacao_preco` WRITE;
/*!40000 ALTER TABLE `crtn_depreciacao_preco` DISABLE KEYS */;
INSERT INTO `crtn_depreciacao_preco` VALUES (1,'2014-03-28 15:47:34','2014-03-28 15:47:34',1,15,0,0),(2,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.981,30,16,0),(3,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.969,45,31,0),(4,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.958,60,46,0),(5,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.946,75,61,0),(6,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.935,90,76,0),(7,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.924,105,91,0),(8,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.914,120,106,0),(9,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.904,135,121,0),(10,'2014-03-28 15:47:41','2014-03-28 15:47:41',0.894,999999,136,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_fornecedor`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_fornecedor` WRITE;
/*!40000 ALTER TABLE `crtn_fornecedor` DISABLE KEYS */;
INSERT INTO `crtn_fornecedor` VALUES (1,9999999,'2014-04-05 09:15:22','2014-04-05 09:15:22','FORNECEDOR UM','FORNECEDOR UM','??????','???????????',0),(2,58,'2014-05-06 09:05:39','2014-05-06 09:05:39','REALCE','REALCE','???????????','?????????????',0),(3,136,'2014-05-06 09:05:52','2014-05-06 09:05:52','BRANYL','BRANYL','????????????','??????????????????',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_orcamento`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_orcamento` WRITE;
/*!40000 ALTER TABLE `crtn_orcamento` DISABLE KEYS */;
INSERT INTO `crtn_orcamento` VALUES (1,'2014-04-08 00:00:00','2014-05-08 00:00:00','2014-04-08 16:44:23','2014-04-14 15:09:39','','CARLOS',2,1);
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
  `fator_padrao` double NOT NULL,
  `inserted` datetime NOT NULL,
  `updated` datetime NOT NULL,
  `largura` double NOT NULL,
  `orientacao_padrao` varchar(30) COLLATE utf8_swedish_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crtn_tecido`
--
-- ORDER BY:  `id`

LOCK TABLES `crtn_tecido` WRITE;
/*!40000 ALTER TABLE `crtn_tecido` DISABLE KEYS */;
INSERT INTO `crtn_tecido` VALUES (1,0.3,1.5,4,'2014-04-05 09:48:09','2014-04-05 09:48:09',3,NULL,1),(2,0.3,1.5,1,'2014-04-05 09:49:00','2014-04-05 09:49:00',2.8,NULL,1);
/*!40000 ALTER TABLE `crtn_tecido` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=478 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rh_total_dia_vendedor`
--
-- ORDER BY:  `id`

LOCK TABLES `rh_total_dia_vendedor` WRITE;
/*!40000 ALTER TABLE `rh_total_dia_vendedor` DISABLE KEYS */;
INSERT INTO `rh_total_dia_vendedor` VALUES (17,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',208.8,0,9),(18,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',1210.08,0,13),(19,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',1103.36,0,15),(20,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',966.81,0,17),(21,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',230.15,0,18),(22,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',567.38,0,19),(23,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',1554.91,0,26),(24,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',93.51,0,32),(25,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',1225.63,0,44),(26,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',1258.62,0,38),(27,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',257.75,0,39),(28,'2014-05-02 00:00:00','2014-05-08 10:35:01','2014-05-08 10:35:01',129.2,0,1),(29,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',929.62,0,13),(30,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',961.34,0,15),(31,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',1036.27,0,17),(32,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',96.93,0,18),(33,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',995.82,0,19),(34,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',877.6,0,26),(35,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',13.59,0,32),(36,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',1627.22,0,44),(37,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',1255.01,0,38),(38,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',808.5,0,39),(39,'2014-05-03 00:00:00','2014-05-08 12:07:43','2014-05-08 12:07:43',31.86,0,1),(40,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',640.93,0,9),(41,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',1824.34,0,13),(42,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',227.34,0,15),(43,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',1315.2,0,17),(44,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',23.04,0,18),(45,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',371.09,0,19),(46,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',20.97,0,23),(47,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',839.16,0,26),(48,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',1020.99,0,44),(49,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',621.36,0,38),(50,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',668.45,0,39),(51,'2014-05-05 00:00:00','2014-05-08 12:08:17','2014-05-08 12:08:17',84,0,1),(52,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',70.65,0,9),(53,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',1185.3,0,13),(54,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',868.62,0,15),(55,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',476.59,0,17),(56,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',88.2,0,18),(57,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',701.59,0,19),(58,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',884.62,0,26),(59,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',37.98,0,32),(60,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',229.99,0,44),(61,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',706.44,0,38),(62,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',367.47,0,39),(63,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',1168.02,0,42),(64,'2014-05-06 00:00:00','2014-05-08 12:08:54','2014-05-08 12:08:54',86.42,0,1),(65,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',87.33,0,9),(66,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',1122.86,0,13),(67,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',848.58,0,15),(68,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',1438.37,0,17),(69,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',13.86,0,18),(70,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',1075.24,0,19),(71,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',10.8,0,23),(72,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',644.66,0,26),(73,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',159.3,0,32),(74,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',1900.86,0,44),(75,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',690.76,0,38),(76,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',347.29,0,39),(77,'2014-05-07 00:00:00','2014-05-08 12:24:07','2014-05-08 12:24:07',675.39,0,1),(78,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',999.96,0,13),(79,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',2335.59,0,15),(80,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',114.39,0,17),(81,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',648.19,0,19),(82,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',767,0,26),(83,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',41.22,0,32),(84,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',700.5,0,44),(85,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',958.37,0,38),(86,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',1161.24,0,39),(87,'2014-04-30 00:00:00','2014-05-09 09:31:32','2014-05-09 09:31:32',310.97,0,1),(88,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',1037.53,0,13),(89,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',760.67,0,15),(90,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',943.4,0,17),(91,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',404.54,0,19),(92,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',735.04,0,26),(93,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',152.6,0,32),(94,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',737.57,0,44),(95,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',107.28,0,38),(96,'2014-04-29 00:00:00','2014-05-09 09:31:44','2014-05-09 09:31:44',1057.26,0,39),(97,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',364.97,0,13),(98,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',179.01,0,15),(99,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',3381.57,0,18),(100,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',247.03,0,19),(101,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',135.09,0,23),(102,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',763.34,0,26),(103,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',205.84,0,32),(104,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',1086.51,0,44),(105,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',364.58,0,38),(106,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',544.03,0,39),(107,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',30.06,0,42),(108,'2014-04-28 00:00:00','2014-05-09 09:31:55','2014-05-09 09:31:55',136.72,0,1),(109,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',356.38,0,13),(110,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',1258.33,0,15),(111,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',519.3,0,17),(112,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',130,0,18),(113,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',560.42,0,19),(114,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',975.3,0,26),(115,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',239.13,0,29),(116,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',1196.07,0,44),(117,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',701.21,0,39),(118,'2014-04-26 00:00:00','2014-05-09 09:32:06','2014-05-09 09:32:06',12.6,0,1),(119,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',733.5,0,13),(120,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',672.4,0,15),(121,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',96.62,0,18),(122,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',161.48,0,19),(123,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',54.9,0,23),(124,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',242.91,0,26),(125,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',816.4,0,44),(126,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',477.7,0,38),(127,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',410.95,0,39),(128,'2014-04-25 00:00:00','2014-05-09 09:32:15','2014-05-09 09:32:15',158.6,0,1),(129,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',394.91,0,13),(130,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',240.05,0,15),(131,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',148.46,0,17),(132,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',140.93,0,18),(133,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',378.23,0,19),(134,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',308.95,0,26),(135,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',182.8,0,29),(136,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',1136.95,0,44),(137,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',62.37,0,38),(138,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',781.31,0,39),(139,'2014-04-24 00:00:00','2014-05-09 09:32:25','2014-05-09 09:32:25',295.66,0,1),(140,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',192.6,0,13),(141,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',240.68,0,15),(142,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',64.53,0,17),(143,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',15.75,0,18),(144,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',134,0,19),(145,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',889.55,0,26),(146,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',0,0,28),(147,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',652.13,0,29),(148,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',179.4,0,32),(149,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',1289.13,0,44),(150,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',2083.06,0,38),(151,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',582.18,0,39),(152,'2014-04-23 00:00:00','2014-05-09 09:32:37','2014-05-09 09:32:37',205.4,0,1),(153,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',1658.43,0,9),(154,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',499.86,0,13),(155,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',245.55,0,15),(156,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',274.05,0,17),(157,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',27.45,0,18),(158,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',311.4,0,19),(159,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',427.03,0,26),(160,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',0,0,28),(161,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',452.86,0,29),(162,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',147.25,0,32),(163,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',504.87,0,44),(164,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',154.71,0,38),(165,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',293.72,0,39),(166,'2014-04-22 00:00:00','2014-05-09 09:32:49','2014-05-09 09:32:49',308.41,0,1),(167,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',917.03,0,13),(168,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',1397.94,0,15),(169,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',579.92,0,17),(170,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',8.91,0,18),(171,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',1279.4,0,19),(172,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',532.98,0,26),(173,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',14.76,0,28),(174,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',1321.96,0,29),(175,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',1752.29,0,38),(176,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',453.19,0,39),(177,'2014-04-19 00:00:00','2014-05-09 09:32:59','2014-05-09 09:32:59',436.3,0,1),(178,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',854.04,0,13),(179,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',1000.4,0,15),(180,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',418.32,0,17),(181,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',10.71,0,18),(182,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',614.39,0,19),(183,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',173.09,0,26),(184,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',295.12,0,29),(185,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',927.68,0,38),(186,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',423.02,0,39),(187,'2014-04-17 00:00:00','2014-05-09 09:33:11','2014-05-09 09:33:11',144.6,0,1),(188,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',1125.6,0,13),(189,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',1361.41,0,15),(190,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',1021.72,0,17),(191,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',181.2,0,18),(192,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',110.16,0,19),(193,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',658.37,0,26),(194,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',322.24,0,29),(195,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',76.23,0,32),(196,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',812.14,0,38),(197,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',840.05,0,39),(198,'2014-04-16 00:00:00','2014-05-09 09:33:23','2014-05-09 09:33:23',75.52,0,1),(206,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',751.51,0,13),(207,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',980.42,0,15),(208,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',713.63,0,17),(209,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',789.75,0,19),(210,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',117.7,0,26),(211,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',566.39,0,29),(212,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',384.96,0,32),(213,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',47.88,0,1),(214,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',892.86,0,38),(215,'2014-04-15 00:00:00','2014-05-09 09:34:05','2014-05-09 09:34:05',327.51,0,39),(216,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',479.74,0,13),(217,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',356.78,0,15),(218,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',728.99,0,17),(219,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',41.67,0,18),(220,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',689.37,0,19),(221,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',421.47,0,26),(222,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',0,0,28),(223,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',474.35,0,29),(224,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',687.68,0,32),(225,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',718.53,0,38),(226,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',620.83,0,39),(227,'2014-04-14 00:00:00','2014-05-09 09:34:16','2014-05-09 09:34:16',354.52,0,1),(228,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',613.92,0,13),(229,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',799.02,0,15),(230,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',1326.86,0,17),(231,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',63.31,0,18),(232,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',284.11,0,19),(233,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',259.11,0,26),(234,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',0,0,28),(235,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',325.59,0,29),(236,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',236.92,0,32),(237,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',987.17,0,38),(238,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',472.77,0,39),(239,'2014-04-12 00:00:00','2014-05-09 09:34:30','2014-05-09 09:34:30',157.47,0,1),(240,'2014-04-11 00:00:00','2014-05-09 09:34:43','2014-05-09 09:34:43',759.49,0,13),(241,'2014-04-11 00:00:00','2014-05-09 09:34:43','2014-05-09 09:34:43',1219.03,0,15),(242,'2014-04-11 00:00:00','2014-05-09 09:34:43','2014-05-09 09:34:43',727.68,0,17),(243,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',578.1,0,19),(244,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',186.7,0,23),(245,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',0,0,25),(246,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',776.64,0,26),(247,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',0,0,28),(248,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',627.44,0,29),(249,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',146.09,0,32),(250,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',1090.81,0,38),(251,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',100,0,39),(252,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',0,0,53),(253,'2014-04-11 00:00:00','2014-05-09 09:34:44','2014-05-09 09:34:44',135.76,0,1),(254,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',138.2,0,9),(255,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',884.67,0,13),(256,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',565.4,0,15),(257,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',145.53,0,17),(258,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',459.81,0,18),(259,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',1020.05,0,19),(260,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',366.75,0,26),(261,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',0,0,28),(262,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',616.8,0,29),(263,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',170.77,0,32),(264,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',1402.1,0,38),(265,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',243.04,0,39),(266,'2014-04-10 00:00:00','2014-05-09 09:34:53','2014-05-09 09:34:53',87.34,0,1),(267,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',248.39,0,13),(268,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',510.18,0,15),(269,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',359.64,0,17),(270,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',20,0,18),(271,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',471.36,0,19),(272,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',886.15,0,26),(273,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',188.59,0,29),(274,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',673.04,0,32),(275,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',537.51,0,38),(276,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',567.44,0,39),(277,'2014-04-09 00:00:00','2014-05-09 09:35:12','2014-05-09 09:35:12',308.98,0,1),(278,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',1055.68,0,13),(279,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',408.34,0,15),(280,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',731.59,0,17),(281,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',109.98,0,19),(282,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',1069.51,0,26),(283,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',315.03,0,29),(284,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',150.58,0,32),(285,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',595.43,0,38),(286,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',819.12,0,39),(287,'2014-04-08 00:00:00','2014-05-09 09:35:31','2014-05-09 09:35:31',382.67,0,1),(288,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',1387.85,0,13),(289,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',213.84,0,15),(290,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',849.2,0,17),(291,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',113.13,0,18),(292,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',467.37,0,19),(293,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',1070.19,0,26),(294,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',243.85,0,29),(295,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',281.17,0,32),(296,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',477.91,0,38),(297,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',482.91,0,39),(298,'2014-04-07 00:00:00','2014-05-09 09:35:41','2014-05-09 09:35:41',140.08,0,1),(299,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',1195.61,0,13),(300,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',539.9,0,15),(301,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',666.57,0,17),(302,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',1518.1,0,19),(303,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',109.86,0,23),(304,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',936.34,0,26),(305,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',242.01,0,29),(306,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',467.82,0,32),(307,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',1133.98,0,38),(308,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',109.38,0,39),(309,'2014-04-05 00:00:00','2014-05-09 09:35:52','2014-05-09 09:35:52',105.17,0,1),(310,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',124.87,0,9),(311,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',825.6,0,13),(312,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',660.76,0,15),(313,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',704.84,0,17),(314,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',392.73,0,19),(315,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',948.33,0,26),(316,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',873.33,0,29),(317,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',1004.52,0,38),(318,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',415.8,0,39),(319,'2014-04-04 00:00:00','2014-05-09 09:36:00','2014-05-09 09:36:00',45.66,0,1),(320,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',0,0,9),(321,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',327.57,0,13),(322,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',1006.14,0,15),(323,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',613.23,0,17),(324,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',6.3,0,18),(325,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',733.72,0,19),(326,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',1265.11,0,26),(327,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',68.85,0,28),(328,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',373.19,0,29),(329,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',673.24,0,38),(330,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',167.02,0,39),(331,'2014-04-03 00:00:00','2014-05-09 09:36:14','2014-05-09 09:36:14',131.45,0,1),(332,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',348.75,0,13),(333,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',616.73,0,15),(334,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',438.16,0,17),(335,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',98.82,0,18),(336,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',429.84,0,19),(337,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',992.32,0,26),(338,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',558.46,0,29),(339,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',653.49,0,38),(340,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',236.37,0,39),(341,'2014-04-02 00:00:00','2014-05-09 09:36:23','2014-05-09 09:36:23',406.22,0,1),(342,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',628.64,0,13),(343,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',154.86,0,15),(344,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',150.14,0,17),(345,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',0,0,18),(346,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',382.82,0,19),(347,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',46.7,0,23),(348,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',19.98,0,28),(349,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',1113.15,0,26),(350,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',148.93,0,29),(351,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',1226.27,0,38),(352,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',744.62,0,39),(353,'2014-04-01 00:00:00','2014-05-09 09:36:50','2014-05-09 09:36:50',86.19,0,1),(354,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',158.22,0,9),(355,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',1217.45,0,13),(356,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',728.9,0,15),(357,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',608.12,0,17),(358,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',539.57,0,19),(359,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',757.44,0,26),(360,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',1037.64,0,44),(361,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',1742.18,0,38),(362,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',376.38,0,39),(363,'2014-05-08 00:00:00','2014-05-13 15:02:17','2014-05-13 15:02:17',492.94,0,1),(364,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',52.47,0,9),(365,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',685.66,0,13),(366,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',1100.97,0,15),(367,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',911.09,0,17),(368,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',27.27,0,18),(369,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',787.48,0,19),(370,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',126.45,0,23),(371,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',670.83,0,26),(372,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',83.07,0,32),(373,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',1469.6,0,44),(374,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',813.03,0,38),(375,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',969.4,0,39),(376,'2014-05-09 00:00:00','2014-05-13 15:02:27','2014-05-13 15:02:27',229.97,0,1),(377,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',149.82,0,9),(378,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',2206.51,0,13),(379,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',1043.41,0,15),(380,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',2821.29,0,17),(381,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',54.72,0,18),(382,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',2357.14,0,19),(383,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',977.43,0,23),(384,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',2711.95,0,26),(385,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',892.58,0,32),(386,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',2192.38,0,44),(387,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',3546.42,0,38),(388,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',1587.01,0,39),(389,'2014-05-10 00:00:00','2014-05-13 15:02:40','2014-05-13 15:02:40',118.91,0,1),(390,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',98.4,0,9),(391,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',1042.79,0,13),(392,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',746.07,0,15),(393,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',844.34,0,17),(394,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',52.88,0,18),(395,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',841.09,0,19),(396,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',68.85,0,26),(397,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',212.13,0,32),(398,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',1512.88,0,44),(399,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',610.93,0,38),(400,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',968.75,0,39),(401,'2014-05-12 00:00:00','2014-05-13 15:02:50','2014-05-13 15:02:50',11.43,0,1),(402,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',35.01,0,9),(403,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',520.72,0,13),(404,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',296.98,0,15),(405,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',923.45,0,17),(406,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',4.5,0,18),(407,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',402.05,0,19),(408,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',828.03,0,26),(409,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',219.9,0,32),(410,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',971.91,0,44),(411,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',646.21,0,38),(412,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',578.91,0,39),(413,'2014-05-13 00:00:00','2014-05-19 17:21:24','2014-05-19 17:21:24',269.85,0,1),(414,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',872.11,0,13),(415,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',805.2,0,15),(416,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',347.45,0,17),(417,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',0,0,18),(418,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',159.68,0,19),(419,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',1152.65,0,26),(420,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',290.19,0,32),(421,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',721.27,0,44),(422,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',1051.56,0,38),(423,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',313.42,0,39),(424,'2014-05-14 00:00:00','2014-05-19 17:22:29','2014-05-19 17:22:29',545.31,0,1),(425,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',9,0,9),(426,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',802.71,0,13),(427,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',1201.43,0,15),(428,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',777.54,0,17),(429,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',892.54,0,26),(430,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',156.96,0,32),(431,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',990.4,0,44),(432,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',502.05,0,38),(433,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',404.95,0,39),(434,'2014-05-15 00:00:00','2014-05-19 17:22:47','2014-05-19 17:22:47',344.05,0,1),(435,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',226.74,0,9),(436,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',923.69,0,13),(437,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',336.41,0,15),(438,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',480.42,0,17),(439,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',79.83,0,18),(440,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',622.58,0,19),(441,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',781.53,0,26),(442,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',1021.98,0,44),(443,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',9.45,0,38),(444,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',613.48,0,39),(445,'2014-05-16 00:00:00','2014-05-19 17:23:30','2014-05-19 17:23:30',85.8,0,1),(446,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',26.37,0,9),(447,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',1857.16,0,13),(448,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',1035.56,0,15),(449,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',1284.28,0,17),(450,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',9.81,0,18),(451,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',605.2,0,19),(452,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',630.4,0,26),(453,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',233.84,0,32),(454,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',1586.33,0,44),(455,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',632.49,0,38),(456,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',596.05,0,39),(457,'2014-05-17 00:00:00','2014-05-19 17:23:47','2014-05-19 17:23:47',351.21,0,1),(458,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',484.69,0,13),(459,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',266.87,0,15),(460,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',417.83,0,17),(461,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',561.16,0,19),(462,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',752.01,0,26),(463,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',1277.26,0,44),(464,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',228.87,0,38),(465,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',165.77,0,39),(466,'2014-05-20 00:00:00','2014-05-21 16:41:04','2014-05-21 16:41:04',1423.16,0,1),(467,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',165.51,0,13),(468,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',89.28,0,15),(469,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',396.43,0,17),(470,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',146.65,0,19),(471,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',300,0,25),(472,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',438.86,0,26),(473,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',49.5,0,29),(474,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',522.17,0,44),(475,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',307.67,0,38),(476,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',371.47,0,39),(477,'2014-05-19 00:00:00','2014-05-21 16:41:13','2014-05-21 16:41:13',91.95,0,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rh_total_mes`
--
-- ORDER BY:  `id`

LOCK TABLES `rh_total_mes` WRITE;
/*!40000 ALTER TABLE `rh_total_mes` DISABLE KEYS */;
INSERT INTO `rh_total_mes` VALUES (2,'2014-04-22 09:28:50','2014-05-21 15:48:26','2012-01-01 00:00:00',7,331516.43,10,0.05,270755.71),(3,'2014-04-22 09:28:50','2014-05-21 15:48:26','2012-02-01 00:00:00',4,231383.13,10,0.05,255146.98),(4,'2014-04-22 09:28:50','2014-05-21 15:48:26','2012-03-01 00:00:00',4,144018.39,10,0.05,159851.13),(5,'2014-04-22 09:28:50','2014-05-21 15:48:26','2012-04-01 00:00:00',4,146552.93,9,0.05,147977.65),(6,'2014-04-22 09:28:50','2014-05-21 15:48:26','2012-05-01 00:00:00',4,196560.39,9,0.05,193602.09),(7,'2014-04-22 09:28:50','2014-05-21 15:48:26','2012-06-01 00:00:00',4,157870.53,9,0.05,180067.89),(8,'2014-04-22 09:28:50','2014-05-21 15:48:26','2012-07-01 00:00:00',4,138357.66,9,0.05,153063.07),(9,'2014-04-22 09:28:51','2014-05-21 15:48:26','2012-08-01 00:00:00',4,130635.59,9,0.05,138703.01),(10,'2014-04-22 09:28:51','2014-05-21 15:48:26','2012-09-01 00:00:00',4,127064.15,9,0.05,147973.75),(11,'2014-04-22 09:28:51','2014-05-21 15:48:26','2012-10-01 00:00:00',4,130424.87,8,0.05,140312.49),(12,'2014-04-22 09:28:51','2014-05-21 15:48:26','2012-11-01 00:00:00',4,128968.42,8,0.05,151021.57),(13,'2014-04-22 09:28:51','2014-05-21 15:48:26','2012-12-01 00:00:00',4,248764.81,10,0.05,310159.17),(14,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-01-01 00:00:00',4,307239.35,11,0.05,331516.43),(15,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-02-01 00:00:00',4,288086.12,11,0.05,231383.13),(16,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-03-01 00:00:00',4,151468.19,10,0.05,144018.39),(17,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-04-01 00:00:00',4,142105.16,9,0.05,146552.93),(18,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-05-01 00:00:00',4,178208.71,10,0.05,196560.39),(19,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-06-01 00:00:00',4,160676.06,10,0.05,157870.53),(20,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-07-01 00:00:00',4,175614.66,8,0.05,138357.66),(21,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-08-01 00:00:00',4,160944.95,9,0.05,130635.59),(22,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-09-01 00:00:00',4,133665.76,9,0.05,127064.15),(23,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-10-01 00:00:00',5,138198.09,9,0.05,130424.87),(24,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-11-01 00:00:00',4,137697.19,9,0.05,128968.42),(25,'2014-04-22 09:28:51','2014-05-21 15:48:26','2013-12-01 00:00:00',4,271555.86,10,0.05,248764.81),(26,'2014-04-22 09:28:51','2014-05-21 15:48:26','2014-01-01 00:00:00',4,389319.1,10,0.05,307239.35),(27,'2014-04-22 09:28:51','2014-05-21 15:48:26','2014-02-01 00:00:00',13,293786.89,10,0.05,288086.12),(28,'2014-04-22 09:28:51','2014-05-21 15:48:26','2014-03-01 00:00:00',12,139303.27,8,0.05,151468.19),(29,'2014-04-29 15:30:47','2014-05-21 15:48:26','2011-12-01 00:00:00',5,310159.17,10,0.05,273135.99),(30,'2014-05-02 09:38:59','2014-05-21 15:48:26','2014-04-01 00:00:00',9,140357.74000000005,9,0.05,142105.16),(32,'2014-05-02 11:55:12','2014-05-21 15:48:26','2011-11-01 00:00:00',5,151021.57,9,0.05,135991.79),(33,'2014-05-08 12:26:50','2014-05-21 17:45:31','2014-05-01 00:00:00',15,124765.96999999997,8,0.05,178208.71);
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
) ENGINE=InnoDB AUTO_INCREMENT=811 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rh_total_mes_vendedor`
--
-- ORDER BY:  `id`

LOCK TABLES `rh_total_mes_vendedor` WRITE;
/*!40000 ALTER TABLE `rh_total_mes_vendedor` DISABLE KEYS */;
INSERT INTO `rh_total_mes_vendedor` VALUES (271,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,30,41856.79,'',1),(272,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,37,41316.44,'',2),(273,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,24,34877.66,'',3),(274,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,38,33225.68,'',4),(275,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,42,32546.3,'',5),(276,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,12,32150.34,'',6),(277,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,35,28128.52,'',7),(278,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,16,27528.74,'',8),(279,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,31,26919.02,'',9),(280,'2014-04-22 12:08:57','2014-04-22 12:08:57',2,2,27,25812.89,'',10),(281,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,2,18,2798.99,'',11),(282,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,2,9,1641.84,'',12),(283,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,2,23,1314.93,'',13),(284,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,2,1,1255.64,'',14),(285,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,2,28,99.5,'',15),(286,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,2,25,43.15,'',16),(287,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,12,27604.54,'',1),(288,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,30,27570.06,'',2),(289,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,24,26348.52,'',3),(290,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,38,24114.01,'',4),(291,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,16,23271.9,'',5),(292,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,37,23237.34,'',6),(293,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,35,21588.18,'',7),(294,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,31,17103.74,'',8),(295,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,27,16993.29,'',9),(296,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,42,16726,'',10),(297,'2014-04-22 12:08:58','2014-04-22 12:08:58',2,3,23,1983.4,'',11),(298,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,3,1,1950.33,'',12),(299,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,3,18,1527.63,'',13),(300,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,3,9,1236.29,'',14),(301,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,3,25,127.9,'',15),(302,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,37,19840.11,'',1),(303,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,24,17851.04,'',2),(304,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,30,17752.35,'',3),(305,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,16,17352.43,'',4),(306,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,38,17235.8,'',5),(307,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,35,15854.09,'',6),(308,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,42,12571.38,'',7),(309,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,31,7671.6,'',8),(310,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,12,5712.38,'',9),(311,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,11,3062.21,'',10),(312,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,18,2841.59,'',11),(313,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,9,2362.5,'',12),(314,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,1,1842.64,'',13),(315,'2014-04-22 12:08:59','2014-04-22 12:08:59',2,4,23,1237.12,'',14),(316,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,4,27,831.15,'',15),(317,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,37,21727.95,'',1),(318,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,16,19227.72,'',2),(319,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,24,17876.58,'',3),(320,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,27,17157.58,'',4),(321,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,38,15607.77,'',5),(322,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,12,15597.15,'',6),(323,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,35,14627.38,'',7),(324,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,11,10935.45,'',8),(325,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,30,7539.87,'',9),(326,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,18,2128.69,'',10),(327,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,23,1652.22,'',11),(328,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,1,1450.24,'',12),(329,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,9,570.19,'',13),(330,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,25,348.4,'',14),(331,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,5,28,105.74,'',15),(332,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,6,24,26026.18,'',1),(333,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,6,37,24541.62,'',2),(334,'2014-04-22 12:09:00','2014-04-22 12:09:00',2,6,16,23433.24,'',3),(335,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,27,22405.93,'',4),(336,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,12,21647.93,'',5),(337,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,30,21497.06,'',6),(338,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,35,18244.8,'',7),(339,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,38,17006.27,'',8),(340,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,11,11350.76,'',9),(341,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,1,4176.19,'',10),(342,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,9,1871.95,'',11),(343,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,18,1606.07,'',12),(344,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,23,1528.92,'',13),(345,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,6,42,1223.47,'',14),(346,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,37,20099.94,'',1),(347,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,16,19343.07,'',2),(348,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,11,18844.66,'',3),(349,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,27,16028.4,'',4),(350,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,35,16019.25,'',5),(351,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,12,15874.97,'',6),(352,'2014-04-22 12:09:01','2014-04-22 12:09:01',2,7,30,15480.98,'',7),(353,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,24,15260.75,'',8),(354,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,38,12316.22,'',9),(355,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,1,4123.87,'',10),(356,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,23,1667.74,'',11),(357,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,9,1497.82,'',12),(358,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,18,1041.49,'',13),(359,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,25,231.38,'',14),(360,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,7,28,39.99,'',15),(361,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,27,19465.21,'',1),(362,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,24,18974.83,'',2),(363,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,12,18818.12,'',3),(364,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,38,17433.59,'',4),(365,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,11,16460.77,'',5),(366,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,16,16393.14,'',6),(367,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,30,14271.94,'',7),(368,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,37,6293.4,'',8),(369,'2014-04-22 12:09:02','2014-04-22 12:09:02',2,8,23,5124.77,'',9),(370,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,8,1,2400.84,'',10),(371,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,8,9,905.85,'',11),(372,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,8,28,701.02,'',12),(373,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,8,33,649.46,'',13),(374,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,8,18,252.65,'',14),(375,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,8,25,212.07,'',15),(376,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,37,18235.02,'',1),(377,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,24,17766.67,'',2),(378,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,12,17569.27,'',3),(379,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,33,16023.53,'',4),(380,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,16,14057.29,'',5),(381,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,11,13133.11,'',6),(382,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,27,12745.57,'',7),(383,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,9,9841.18,'',8),(384,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,34,6957.29,'',9),(385,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,1,1547.73,'',10),(386,'2014-04-22 12:09:03','2014-04-22 12:09:03',2,9,23,1371.95,'',11),(387,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,9,18,1072.47,'',12),(388,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,9,38,269.02,'',13),(389,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,9,28,29,'',14),(390,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,9,25,16.49,'',15),(391,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,37,16504.59,'',1),(392,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,33,14868.52,'',2),(393,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,34,14023.58,'',3),(394,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,11,13831.42,'',4),(395,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,12,12987.88,'',5),(396,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,42,12883.55,'',6),(397,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,38,11305.27,'',7),(398,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,27,10905.84,'',8),(399,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,24,10048.82,'',9),(400,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,1,4195.73,'',10),(401,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,18,2921.26,'',11),(402,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,9,1529.4,'',12),(403,'2014-04-22 12:09:04','2014-04-22 12:09:04',2,10,23,917.99,'',13),(404,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,10,28,140.3,'',14),(405,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,42,18788.5,'',1),(406,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,37,17754.44,'',2),(407,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,33,17721.09,'',3),(408,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,34,16427.62,'',4),(409,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,27,15378.11,'',5),(410,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,12,14217.26,'',6),(411,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,38,12638.22,'',7),(412,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,11,12196.44,'',8),(413,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,23,1832.55,'',9),(414,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,1,1748.11,'',10),(415,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,9,706.83,'',11),(416,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,18,623.87,'',12),(417,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,24,326.98,'',13),(418,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,11,25,64.85,'',14),(419,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,12,42,27170.01,'',1),(420,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,12,37,17722.28,'',2),(421,'2014-04-22 12:09:05','2014-04-22 12:09:05',2,12,33,16706.66,'',3),(422,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,34,14754.35,'',4),(423,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,27,14598.57,'',5),(424,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,12,11700.36,'',6),(425,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,38,11513.93,'',7),(426,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,24,10526.65,'',8),(427,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,22,1514.04,'',9),(428,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,1,1478.35,'',10),(429,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,23,690.58,'',11),(430,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,18,385.87,'',12),(431,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,12,9,206.77,'',13),(432,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,37,32611.73,'',1),(433,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,42,29892.13,'',2),(434,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,24,26340.11,'',3),(435,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,12,24459.02,'',4),(436,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,34,24439.64,'',5),(437,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,38,23669.91,'',6),(438,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,27,23475.13,'',7),(439,'2014-04-22 12:09:06','2014-04-22 12:09:06',2,13,30,20804.83,'',8),(440,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,33,15252.25,'',9),(441,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,22,14601.56,'',10),(442,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,1,4991.14,'',11),(443,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,9,3906.59,'',12),(444,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,23,3647.46,'',13),(445,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,18,458.52,'',14),(446,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,25,115.19,'',15),(447,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,13,11,99.6,'',16),(448,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,12,33128.72,'',1),(449,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,24,32883.53,'',2),(450,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,42,32272.97,'',3),(451,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,30,31220.77,'',4),(452,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,34,29802.24,'',5),(453,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,38,27556.39,'',6),(454,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,10,27252.12,'',7),(455,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,37,26231.03,'',8),(456,'2014-04-22 12:09:07','2014-04-22 12:09:07',2,14,19,25081.61,'',9),(457,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,14,27,25075.01,'',10),(458,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,14,33,9864.03,'',11),(459,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,14,1,2990.91,'',12),(460,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,14,18,1824.39,'',13),(461,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,14,23,1645.67,'',14),(462,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,14,9,409.96,'',15),(463,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,37,35565.23,'',1),(464,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,19,32565.82,'',2),(465,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,24,31144.04,'',3),(466,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,30,29784.21,'',4),(467,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,34,28942.21,'',5),(468,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,10,28710.62,'',6),(469,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,38,23400.22,'',7),(470,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,12,21215.1,'',8),(471,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,27,18411.86,'',9),(472,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,33,12126.88,'',10),(473,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,42,11565.18,'',11),(474,'2014-04-22 12:09:08','2014-04-22 12:09:08',2,15,28,7169,'',12),(475,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,15,23,4121.44,'',13),(476,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,15,1,2224.67,'',14),(477,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,15,9,595.88,'',15),(478,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,15,18,476.95,'',16),(479,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,15,25,66.81,'',17),(480,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,24,26472.98,'',1),(481,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,38,25701.06,'',2),(482,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,19,19611.31,'',3),(483,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,37,18614.44,'',4),(484,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,10,17144.16,'',5),(485,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,30,13416.01,'',6),(486,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,34,8684.73,'',7),(487,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,42,5122.16,'',8),(488,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,27,3454.32,'',9),(489,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,33,3386.17,'',10),(490,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,1,2655.21,'',11),(491,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,23,1894.11,'',12),(492,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,28,1508.71,'',13),(493,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,12,1288.16,'',14),(494,'2014-04-22 12:09:09','2014-04-22 12:09:09',2,16,9,1260.33,'',15),(495,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,16,18,1246.68,'',16),(496,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,16,25,7.65,'',17),(497,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,37,19073.31,'',1),(498,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,19,18970.14,'',2),(499,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,15,17685.24,'',3),(500,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,17,16375.18,'',4),(501,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,10,14648.47,'',5),(502,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,38,14395.53,'',6),(503,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,24,12827.28,'',7),(504,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,30,12299.58,'',8),(505,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,14,5750.83,'',9),(506,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,1,3117.66,'',10),(507,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,33,1871.24,'',11),(508,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,42,1735.96,'',12),(509,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,9,1225.52,'',13),(510,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,28,1006.21,'',14),(511,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,23,755.98,'',15),(512,'2014-04-22 12:09:10','2014-04-22 12:09:10',2,17,18,367.03,'',16),(513,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,37,27352.25,'',1),(514,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,15,19157.18,'',2),(515,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,14,19017.45,'',3),(516,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,17,17584.16,'',4),(517,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,19,17002.06,'',5),(518,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,24,16515.49,'',6),(519,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,30,15359.22,'',7),(520,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,38,13434.82,'',8),(521,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,10,12787.24,'',9),(522,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,42,11572.74,'',10),(523,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,1,3796.26,'',11),(524,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,33,1204.46,'',12),(525,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,25,1185,'',13),(526,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,23,801.2,'',14),(527,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,28,711.69,'',15),(528,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,18,444.85,'',16),(529,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,18,9,282.64,'',17),(530,'2014-04-22 12:09:11','2014-04-22 12:09:11',2,19,37,25428.15,'',1),(531,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,42,17076.6,'',2),(532,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,15,17001.28,'',3),(533,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,14,16233.99,'',4),(534,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,19,14067.06,'',5),(535,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,10,13693.73,'',6),(536,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,30,12976.12,'',7),(537,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,17,12794.68,'',8),(538,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,24,12248.62,'',9),(539,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,38,10878.03,'',10),(540,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,9,3731.02,'',11),(541,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,1,3351.41,'',12),(542,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,28,408.42,'',13),(543,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,33,378.29,'',14),(544,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,23,373.44,'',15),(545,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,18,23.22,'',16),(546,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,19,25,12,'',17),(547,'2014-04-22 12:09:12','2014-04-22 12:09:12',2,20,17,25688.23,'',1),(548,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,42,23465.84,'',2),(549,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,15,22352.82,'',3),(550,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,14,21673.49,'',4),(551,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,10,18120.84,'',5),(552,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,24,16912.82,'',6),(553,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,30,16694.68,'',7),(554,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,19,16613.05,'',8),(555,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,9,5988.87,'',9),(556,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,1,4902.2,'',10),(557,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,23,1923.27,'',11),(558,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,37,470.77,'',12),(559,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,18,323.5,'',13),(560,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,33,315.2,'',14),(561,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,38,123.34,'',15),(562,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,25,29.9,'',16),(563,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,20,28,15.84,'',17),(564,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,21,37,19827.97,'',1),(565,'2014-04-22 12:09:13','2014-04-22 12:09:13',2,21,14,18472.3,'',2),(566,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,30,17498.18,'',3),(567,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,19,15959.59,'',4),(568,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,38,15692.42,'',5),(569,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,24,15226.36,'',6),(570,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,17,15154.94,'',7),(571,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,15,14661.18,'',8),(572,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,10,12155.78,'',9),(573,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,9,9700.31,'',10),(574,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,1,2994.7,'',11),(575,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,18,2609.52,'',12),(576,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,26,567.19,'',13),(577,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,23,292.12,'',14),(578,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,21,42,132.39,'',15),(579,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,22,37,18396.77,'',1),(580,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,22,17,16961.5,'',2),(581,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,22,26,15587.53,'',3),(582,'2014-04-22 12:09:14','2014-04-22 12:09:14',2,22,15,15534.05,'',4),(583,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,24,13324.1,'',5),(584,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,38,12576.68,'',6),(585,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,14,11716.75,'',7),(586,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,30,11337.59,'',8),(587,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,19,9173.92,'',9),(588,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,18,3847.22,'',10),(589,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,1,2871.44,'',11),(590,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,9,1857.98,'',12),(591,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,23,384.83,'',13),(592,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,22,28,95.4,'',14),(593,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,38,19078.95,'',1),(594,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,17,15265.89,'',2),(595,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,30,14627.72,'',3),(596,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,19,14026.92,'',4),(597,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,26,13551.03,'',5),(598,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,15,13026.13,'',6),(599,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,25,11937.42,'',7),(600,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,36,9457.9,'',8),(601,'2014-04-22 12:09:15','2014-04-22 12:09:15',2,23,18,8109.04,'',9),(602,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,29,7988.23,'',10),(603,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,9,3189.06,'',11),(604,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,14,2904.87,'',12),(605,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,37,2280.74,'',13),(606,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,1,1895.2,'',14),(607,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,23,23,858.99,'',15),(608,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,38,20919.79,'',1),(609,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,24,18303.65,'',2),(610,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,26,15085.15,'',3),(611,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,36,14171.62,'',4),(612,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,15,13342.87,'',5),(613,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,19,12303.15,'',6),(614,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,30,11363.88,'',7),(615,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,17,10834.98,'',8),(616,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,29,8279.85,'',9),(617,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,25,6113.07,'',10),(618,'2014-04-22 12:09:16','2014-04-22 12:09:16',2,24,9,3877.14,'',11),(619,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,24,1,1674.38,'',12),(620,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,24,18,777.67,'',13),(621,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,24,23,649.99,'',14),(622,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,24,40092.77,'',1),(623,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,38,37938.47,'',2),(624,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,26,34863.9,'',3),(625,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,15,31090.16,'',4),(626,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,29,26165.09,'',5),(627,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,17,24527.4,'',6),(628,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,36,23961.84,'',7),(629,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,19,21887.29,'',8),(630,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,33,10294.71,'',9),(631,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,39,7714.86,'',10),(632,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,1,4191.49,'',11),(633,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,23,3283.75,'',12),(634,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,9,3131.38,'',13),(635,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,18,2348.09,'',14),(636,'2014-04-22 12:09:17','2014-04-22 12:09:17',2,25,28,42.62,'',15),(637,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,25,25,17,'',16),(638,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,25,42,5.04,'',17),(639,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,26,48648.06,'',1),(640,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,24,47124.82,'',2),(641,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,38,43674.46,'',3),(642,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,17,41376.74,'',4),(643,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,19,37244.97,'',5),(644,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,39,36950.05,'',6),(645,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,15,35148.13,'',7),(646,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,29,31323,'',8),(647,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,13,28252.31,'',9),(648,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,33,20665.62,'',10),(649,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,9,14170.89,'',11),(650,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,26,36,4740.05,'',12),(651,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,27,13,36269.75,'',1),(652,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,27,38,33913.97,'',2),(653,'2014-04-22 12:09:18','2014-04-22 12:09:18',2,27,26,32892.89,'',3),(654,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,27,19,29347.78,'',4),(655,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,27,15,26696.34,'',5),(656,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,27,39,26148.72,'',6),(657,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,27,24,25788.07,'',7),(658,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,27,17,24479.37,'',8),(659,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,27,29,23120.67,'',9),(660,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,27,33,20119.65,'',10),(662,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,28,26,20759.43,'',1),(663,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,28,13,20417.27,'',2),(664,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,28,15,16776.65,'',3),(665,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,28,38,16461.09,'',4),(666,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,28,17,15854.04,'',5),(667,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,28,19,14307.58,'',6),(668,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,28,39,13414.9,'',7),(670,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,28,29,9261.51,'',8),(671,'2014-04-22 12:09:19','2014-04-22 12:09:19',2,28,33,86.22,'',13),(672,'2014-04-22 17:34:45','2014-04-22 17:34:45',2,27,23,5023.36,'',11),(673,'2014-04-22 17:35:17','2014-04-22 17:35:17',2,27,9,3036.21,'',12),(674,'2014-04-22 17:35:42','2014-04-22 17:35:42',2,27,18,1905.58,'',14),(675,'2014-04-22 17:36:06','2014-04-22 17:36:06',2,27,25,258.04,'',17),(676,'2014-04-22 17:36:29','2014-04-22 17:36:29',2,27,42,1220.67,'',15),(677,'2014-04-22 17:36:49','2014-04-22 17:36:49',2,27,1,2930.33,'',13),(678,'2014-04-22 17:45:43','2014-04-22 17:45:43',2,27,28,635.49,'',16),(679,'2014-04-22 17:46:05','2014-04-22 17:46:05',2,28,9,5577,'',9),(680,'2014-04-22 17:46:20','2014-04-22 17:46:20',2,28,18,949.47,'',11),(681,'2014-04-22 17:46:32','2014-04-22 17:46:32',2,28,23,677.76,'',12),(682,'2014-04-22 17:46:45','2014-04-22 17:46:45',2,28,28,17.91,'',14),(683,'2014-04-22 17:47:11','2014-04-22 17:47:11',2,28,1,4742.44,'',10),(702,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,9,1798.01,'',13),(703,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,12,26806.07,'',6),(704,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,16,23862.14,'',9),(705,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,18,2132.04,'',12),(706,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,23,1300.58,'',14),(707,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,24,37878.93,'',2),(708,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,25,16.22,'',16),(709,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,27,16974.72,'',10),(710,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,28,157.72,'',15),(711,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,30,37979.82,'',1),(712,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,31,23890.54,'',8),(713,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,35,26006.3,'',7),(714,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,37,36147.37,'',3),(715,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,38,33625.38,'',4),(716,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,42,30361.09,'',5),(717,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,53,0,'',17),(718,'2014-05-02 11:01:35','2014-05-02 11:01:35',2,29,1,4241.91,'',11),(719,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,9,941.75,'',13),(720,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,12,16314.36,'',5),(721,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,16,15565.02,'',6),(722,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,18,1438.4,'',11),(723,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,23,996.66,'',12),(724,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,24,16755.96,'',4),(725,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,27,14669.53,'',8),(726,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,28,0,'',15),(727,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,30,18381.27,'',1),(728,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,31,13868.81,'',9),(729,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,35,15509.94,'',7),(730,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,38,16943.3,'',3),(731,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,42,17327.73,'',2),(732,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,53,0,'',14),(733,'2014-05-02 11:55:37','2014-05-02 11:55:37',2,32,1,2308.84,'',10),(777,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,42,30.06,'',16),(778,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,23,533.25,'',14),(779,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,53,0,'',18),(780,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,17,12240.15,'',7),(781,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,32,4001.57,'',12),(782,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,28,103.59,'',15),(783,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,44,7468,'',9),(784,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,1,4474.17,'',11),(785,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,18,4796.18,'',10),(786,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,39,12554.97,'',6),(787,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,25,0,'',17),(788,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,9,1921.5,'',13),(789,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,29,9020.2,'',8),(790,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,26,16696.33,'',4),(791,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,19,12716.54,'',5),(792,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,38,19094,'',1),(793,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,13,16983.8,'',3),(794,'2014-05-19 17:24:28','2014-05-19 17:24:28',2,30,15,17723.43,'',2),(795,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,42,1168.02,'',NULL),(796,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,23,1135.65,'',NULL),(797,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,17,15045.48,'',NULL),(798,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,32,2393.05,'',NULL),(799,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,44,19308.51,'',NULL),(800,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,1,4971.45,'',NULL),(801,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,18,681.19,'',NULL),(802,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,39,9395.05,'',NULL),(803,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,29,49.5,'',NULL),(804,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,25,300,'',NULL),(805,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,9,1763.74,'',NULL),(806,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,26,14486.04,'',NULL),(807,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,19,10733.72,'',NULL),(808,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,38,14623.05,'',NULL),(809,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,13,17051.2,'',NULL),(810,'2014-05-21 17:45:31','2014-05-21 17:45:31',0,33,15,11660.32,'',NULL);
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
INSERT INTO `sec_group` VALUES (1,'2012-08-03 00:16:44','2012-08-03 00:18:15','ADMINISTRADORES',0);
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
INSERT INTO `sec_group_role` VALUES (1,1);
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
INSERT INTO `sec_role` VALUES (1,'Administrador do Sistema','2012-02-13 00:51:42','2012-02-13 00:51:45','ROLE_ADMIN',0);
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
INSERT INTO `sec_user` VALUES (1,1,'carlospauluk@gmail.com','2012-02-13 00:50:39','2012-08-03 00:18:53','CARLOS EDUARDO PAULUK','754116f9f2d2144a7acafbff146e08bf44708db368248e0e66277c9a4f1dbc0f5a6fe367957e36f8','carlos',1,0);
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
INSERT INTO `sec_user_role` VALUES (1,1);
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

-- Dump completed on 2014-05-21 17:50:04
