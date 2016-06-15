/*
 * VerifyEmailResource.java
 *
 * This file contains the REST web service that extracts token
 * from url and verifies the account by updating the verified
 * field in users table.
 *
 */ 

package com.docschedule.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@Path("/v1/VerifyEmail/{verifyToken}")
public class VerifyEmailResource {
    private Connection connect = null;
    private PreparedStatement preparedStatement = null;
    private DataSource dataSource;

    public VerifyEmailResource() {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/docschedDB");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @GET
    public void verifyAndUpdate(@PathParam("verifyToken") String verifyToken) {

        String sqlString;

        try {
            
            connect = dataSource.getConnection();
    
            sqlString = "update users set verified = 1 where token = ?";

            preparedStatement = connect.prepareStatement(sqlString);
            preparedStatement.setString(1, verifyToken);
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("SQL Exception");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception-close");
                System.out.println("SQLException: " + e.getMessage());

            }
        }
    }
}
