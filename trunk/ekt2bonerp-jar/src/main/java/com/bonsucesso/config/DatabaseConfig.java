package com.bonsucesso.config;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Classe de configuração do Spring. Lê os dados de conexão ao banco de dados a partir de um arquivo no HD. Substitui a configuração
 * estática do applicationContext.xml.
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
@Configuration
public class DatabaseConfig {

	// definido no pom.xml
	@Value("${dbconfig.file}")
	private String dbConfigFile;

	public String getDbConfigFile() {
		return dbConfigFile;
	}

	public void setDbConfigFile(String dbConfigFile) {
		this.dbConfigFile = dbConfigFile;
	}

	@Bean
	public BasicDataSource dataSource() {
		String dbConfigFile = System.getProperty("dbconfig.file");
		if (dbConfigFile == null) {
			dbConfigFile = getDbConfigFile();
		}
		if (dbConfigFile == null || "".equals(dbConfigFile.trim())) {
			throw new IllegalStateException("dbconfig.file não informado. Impossível inicializar conexão ao banco de dados.");
		}

		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(dbConfigFile);
			prop.load(input);
		} catch (IOException io) {
			throw new IllegalStateException("Impossível inicializar conexão ao banco de dados (dados inválidos).");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (prop.isEmpty()) {
			throw new IllegalStateException("Impossível inicializar conexão ao banco de dados (dados inválidos).");
		}

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(prop.getProperty("database.driver"));
		ds.setUrl(prop.getProperty("database.url"));
		ds.setUsername(prop.getProperty("database.username"));
		ds.setPassword(prop.getProperty("database.password"));
		ds.setInitialSize(15);
		ds.setMaxActive(30);
		ds.setValidationQuery("SELECT 1");

		return ds;
	}

}
