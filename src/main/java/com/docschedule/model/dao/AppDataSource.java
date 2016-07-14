
package com.docschedule.model.dao;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppDataSource {

    public static DataSource getDataSource() throws NamingException {

        Logger logger = LoggerFactory.getLogger("com.docschedule.model.dao.AppDataSource");
        DataSource dataSource = null;
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/docschedDB");
        } catch (NamingException e) {
            logger.error("getDataSource.", e);
            throw new NamingException("AppDataSource");
        }

        return dataSource;
    }
}
