-- ROLES:

-- ROLE_ADMIN

-- ROLE_ESTOQUE
-- ROLE_ESTOQUE_ADMIN
-- ROLE_CORTINAS
-- ROLE_CORTINAS_ADMIN
-- ROLE_RH
-- ROLE_RH_ADMIN



insert into sec_group values(1,now(),now(),'ADMINISTRADORES',0);
insert into sec_group values(2,now(),now(),'USUÁRIO',0);

insert into sec_role values(1,'ROLE_ADMIN',now(),now(),'ROLE_ADMIN',0);
insert into sec_role values(NULL,'ROLE_ESTOQUE',now(),now(),'ROLE_ESTOQUE',0);
insert into sec_role values(NULL,'ROLE_ESTOQUE_ADMIN',now(),now(),'ROLE_ESTOQUE_ADMIN',0);
insert into sec_role values(NULL,'ROLE_CORTINAS',now(),now(),'ROLE_CORTINAS',0);
insert into sec_role values(NULL,'ROLE_CORTINAS_ADMIN',now(),now(),'ROLE_CORTINAS_ADMIN',0);
insert into sec_role values(NULL,'ROLE_RH',now(),now(),'ROLE_RH',0);
insert into sec_role values(NULL,'ROLE_RH_ADMIN',now(),now(),'ROLE_RH_ADMIN',0);

insert into sec_group_role(1,1);


INSERT INTO `sec_user` 
	(`user_id`, `ativo`, `email`, `inserted`, `updated`, `nome`, `senha`, `username`, `group_id`, `version`) 
	VALUES 
	(1,1,'carlospauluk@gmail.com','2012-02-13 00:50:39','2014-12-19 16:24:34','CARLOS EDUARDO PAULUK','A10867BB0001DBFD694FD3AB51DE282DEA103F08737650E5B605806F649AD06288D48814BFAB0F38','carlos',1,5),
	(2,1,'marizapauluk@gmail.com','2014-07-28 16:45:37','2014-07-28 16:56:22','MARIZA PAULUK','3A08CDD46220C0BBFB6CE07DBAEEB65D5D879D05457864BDAB512C7AB42DC00351DF133DEE665C7A','mariza',2,1),
	(3,1,'none@none.com','2014-07-31 11:05:39','2015-01-12 14:45:15','MAKELLY','FAACAC6EF539F1A465F0E8F84DABBC90A798BA01F76137D4926647E54E5651F03B1A02028AE77E27','makelly',2,3),
	(4,1,'flaviopauluk@gmail.com','2014-10-13 09:13:37','2014-11-17 16:58:24','FLÁVIO ANTONIO PAULUK','F92183215FFC426F983516F600FCD30D132C0A07409CAA5C7AD7841463E66D3F4E85CB524D1D1A44','toninho',2,2),
	(5,1,'luiz.pauluk@gmail.com','2014-12-09 17:39:52','2014-12-09 17:40:15','LUIZ RICARDO PAULUK','77318BC89414174260E54CE9DD3E382E92A8C37F23661ADCF7320047AF8DB1ABE7F3E43D0E0DB38E','luiz.pauluk',2,2);



insert into sec_user_role values (1,1);