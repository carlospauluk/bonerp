-- MySQL dump 10.13  Distrib 5.1.63, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: grafis_hom
-- ------------------------------------------------------
-- Server version	5.1.63-0ubuntu0.11.04.1

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cfg_config`
--
-- ORDER BY:  `config_id`

LOCK TABLES `cfg_config` WRITE;
/*!40000 ALTER TABLE `cfg_config` DISABLE KEYS */;
INSERT INTO `cfg_config` (`config_id`, `chave`, `inserted`, `updated`, `valor`, `version`) VALUES (1,'menu','2012-05-28 13:48:41','2012-05-28 13:48:43','<li class=\"top-level\" id=\"left_cap\">\r\n	<p class=\"homebutton\">\r\n		<a href=\"#\">\r\n			<span>Cadastros</span>\r\n		</a>\r\n	</p>\r\n	<div class=\"submenu two-columns\" style=\"margin-left: 0px;\">\r\n		<div class=\"left-column\">\r\n			<dl>\r\n				<dt>Clientes</dt>\r\n				<dd>\r\n					<a href=\"/Grafis/pages/cadastros/clienteList.jsf\">Listar Clientes</a>\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/Grafis/pages/cadastros/clienteForm.jsf\">Novo Cliente</a>\r\n				</dd>\r\n			</dl>\r\n			<dl>\r\n				<dt>Fornecedores</dt>\r\n				<dd>\r\n					<a href=\"/Grafis/pages/cadastros/fornecedorList.jsf\">Listar Fornecedores</a>\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/Grafis/pages/cadastros/fornecedorForm.jsf\">Novo Fornecedor</a>\r\n				</dd>\r\n			</dl>\r\n		</div>\r\n		<div class=\"right-column\">\r\n			<dl>\r\n				<dt>Funcionários</dt>\r\n				<dd>\r\n					<a href=\"/Grafis/pages/cadastros/funcionarioList.jsf\">Listar Funcionários</a>\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/Grafis/pages/cadastros/funcionarioForm.jsf\">Novo Funcionário</a>\r\n				</dd>\r\n			</dl>\r\n		</div>\r\n		<div class=\"bottomleft\">\r\n			<div class=\"bottomcenter\">\r\n				<div class=\"bottomright\"></div>\r\n			</div>\r\n		</div>\r\n	</div>\r\n</li>\r\n\r\n<!-- FINANCEIRO -->\r\n<li class=\"top-level\">\r\n	<p>\r\n		<a href=\"#\">\r\n			<span>Financeiro</span>\r\n		</a>\r\n	</p>\r\n	<div class=\"submenu three-columns\">\r\n		<div class=\"left-column\">\r\n			<dl>\r\n				<dt>Movimentações</dt>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/movimentacaoList.jsf?t=t\">Movimentações - Geral</a>\r\n				</dd>\r\n				<dd>\r\n					<a\r\n						href=\"/ocabitfinancweb/pages/financeiro/movimentacaoListMensal.jsf?t=m\">Movimentações - Mensal</a>\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/movimentacaoForm.jsf\">Nova Movimentação</a>\r\n				</dd>\r\n			</dl>\r\n			<dl>\r\n				<dt>Contas a Pagar/Receber</dt>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/contaPagRec_agenda.jsf\">Agenda</a>\r\n				</dd>\r\n				<dd>\r\n					<a\r\n						href=\"/ocabitfinancweb/pages/financeiro/contaPagRecListMensal.jsf?c=&amp;t=m\">Contas a Pagar/Receber - Mensal</a>\r\n				</dd>\r\n				<dd></dd>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/contaPagRecList.jsf?t=t\">Contas a Pagar/Receber - Geral</a>\r\n				</dd>\r\n				<dd></dd>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/contaPagRecForm.jsf\">Lançar Nova Conta</a>\r\n				</dd>\r\n			</dl>\r\n		</div>\r\n		<div class=\"right-colnoborder\">\r\n			<dl>\r\n				<dt>Previsões Financeiras</dt>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/orcamentoMensalForm.jsf\">Orçamento</a>\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/previsaoMensal.jsf\">Previsão Mensal</a>\r\n				</dd>\r\n				<dd>\r\n					<hr />\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/previsaoMensalItemList.jsf\">Últimas Previsões</a>\r\n				</dd>\r\n			</dl>\r\n			<dl>\r\n				<dt>Relatórios</dt>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/resultadoGeralRpView.jsf\">Resultado Geral</a>\r\n				</dd>\r\n			</dl>\r\n		</div>\r\n		<div class=\"last-column\">\r\n			<dl>\r\n				<dt>Cadastros</dt>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/modoMovtoForm.jsf\">Modos de Movimentação</a>\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/carteiraForm.jsf\">Carteiras</a>\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/categoriaTreeForm.jsf\">Categorias</a>\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/ocabitfinancweb/pages/financeiro/grupoMovimentForm.jsf\">Grupos de Movimentação</a>\r\n				</dd>\r\n			</dl>\r\n		</div>\r\n		<div class=\"bottomleft\">\r\n			<div class=\"threebottomcenter\">\r\n				<div class=\"bottomright\"></div>\r\n			</div>\r\n		</div>\r\n	</div>\r\n</li>\r\n\r\n<!-- OPERACIONAL -->\r\n<li class=\"top-level\">\r\n	<p>\r\n		<a href=\"#\">\r\n			<span>Operacional</span>\r\n		</a>\r\n	</p>\r\n\r\n	<div class=\"submenu two-columns\">\r\n		<div class=\"left-column\">\r\n			<dl>\r\n				<dt>Trabalhos</dt>\r\n				<dd>\r\n					<a\r\n						href=\"${facesContext.externalContext.requestContextPath}/pages/operacional/trabalhoList.jsf\">Listar Trabalhos</a>\r\n				</dd>\r\n				<dd>\r\n					<a\r\n						href=\"${facesContext.externalContext.requestContextPath}/pages/operacional/trabalhoForm.jsf\">Novo Trabalho</a>\r\n				</dd>\r\n				<br />\r\n				<dd>\r\n					<a\r\n						href=\"${facesContext.externalContext.requestContextPath}/pages/operacional/alocacaoTarefas.jsf\">Alocação de Tarefas</a>\r\n				</dd>\r\n			</dl>\r\n			<dl>\r\n\r\n			</dl>\r\n		</div>\r\n		<div class=\"right-column\">\r\n			<dl>\r\n\r\n			</dl>\r\n			<dl>\r\n\r\n			</dl>\r\n		</div>\r\n\r\n\r\n		<div class=\"bottomleft\">\r\n			<div class=\"bottomcenter\">\r\n				<div class=\"bottomright\"></div>\r\n			</div>\r\n		</div>\r\n	</div>\r\n</li>\r\n\r\n\r\n\r\n<!-- CONFIGURAÇÕES -->\r\n<li class=\"top-level\">\r\n	<p>\r\n		<a href=\"#\">\r\n			<span>Configurações</span>\r\n		</a>\r\n	</p>\r\n	<div class=\"submenu two-columns\">\r\n		<div class=\"left-column\">\r\n			<dl>\r\n				<dt>Geral</dt>\r\n				<dd>\r\n					<a href=\"/Grafis/pages/config/config.jsf\">Opções do Sistema</a>\r\n				</dd>\r\n			</dl>\r\n		</div>\r\n		<div class=\"right-column\">\r\n			<dl>\r\n				<dt>Segurança</dt>\r\n				<dd>\r\n					<a href=\"/Grafis/pages/security/groupForm.jsf\">Grupos de Acesso</a>\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/Grafis/pages/security/userForm.jsf\">Usuários do Sistema</a>\r\n				</dd>\r\n				<dd>\r\n					<a href=\"/Grafis/pages/security/logAcesso.jsf\">Logs de Acesso</a>\r\n				</dd>\r\n			</dl>\r\n		</div>\r\n		<div class=\"bottomleft\">\r\n			<div class=\"bottomcenter\">\r\n				<div class=\"bottomright\"></div>\r\n			</div>\r\n		</div>\r\n	</div>\r\n</li>\r\n<li class=\"special top-level last\">\r\n	<p>&#160;</p>\r\n</li>',0);
/*!40000 ALTER TABLE `cfg_config` ENABLE KEYS */;
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
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `groupname` (`groupname`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_group`
--
-- ORDER BY:  `group_id`

LOCK TABLES `sec_group` WRITE;
/*!40000 ALTER TABLE `sec_group` DISABLE KEYS */;
INSERT INTO `sec_group` (`group_id`, `inserted`, `updated`, `groupname`) VALUES (1,'2012-08-03 00:16:44','2012-08-03 00:18:15','ADMINISTRADORES');
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
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_role`
--
-- ORDER BY:  `role_id`

LOCK TABLES `sec_role` WRITE;
/*!40000 ALTER TABLE `sec_role` DISABLE KEYS */;
INSERT INTO `sec_role` (`role_id`, `descricao`, `inserted`, `updated`, `role`) VALUES (1,'Administrador do Sistema','2012-02-13 00:51:42','2012-02-13 00:51:45','ROLE_ADMIN');
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
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `FK375DF2F9D5F739E8` (`group_id`),
  CONSTRAINT `FK375DF2F9D5F739E8` FOREIGN KEY (`group_id`) REFERENCES `sec_group` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_user`
--
-- ORDER BY:  `user_id`

LOCK TABLES `sec_user` WRITE;
/*!40000 ALTER TABLE `sec_user` DISABLE KEYS */;
INSERT INTO `sec_user` (`user_id`, `ativo`, `email`, `inserted`, `updated`, `nome`, `senha`, `username`, `group_id`) VALUES (1,1,'carlospauluk@gmail.com','2012-02-13 00:50:39','2012-08-03 00:18:53','CARLOS EDUARDO PAULUK','74a3ce7e2e91ac70a901bdc841de8f036a6189ca566665e5693df9a6cad2b3bf','carlos',1);
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

-- Dump completed on 2012-09-17  9:00:01
