package com.coderapids.docsched.security;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class MyRestRealm extends JdbcRealm {
	String dataSourceName;
	public MyRestRealm() {
		super();
		setName("MyRestRealm");
		MyRestCredentialsMatcher myCredentialsMatcher = new MyRestCredentialsMatcher();
		setCredentialsMatcher(myCredentialsMatcher);

		try {
			InitialContext ic = new InitialContext();
			DataSource dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/docschedDB");
			this.setDataSource(dataSource);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void setDataSourceName(String dataSourceName) {   
		this.dataSourceName = dataSourceName;
	}

}