
package com.docschedule.model.dao;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;

import java.util.ArrayList;

import com.docschedule.model.domain.Physician;

public class PhysicianDAO {

    public int getPhysiciansForSide(int sideId) throws DAOException {

        Connection connection = null;
        DataSource ds = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int numPhysiciansPerSide = -1;

        try {
            ds = AppDataSource.getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new DAOException("NamingException encountered", e);
        }

        try {
            connection = ds.getConnection();

            String sqlString = "select count(1) from physicians where side_id = ?";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setInt(1, sideId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            numPhysiciansPerSide = resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("SQLException during data access", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception-close");
                System.out.println("SQLException: " + e.getMessage());
                throw new DAOException("SQL Exception on attempt to close connection", e);
            }
        }

        return numPhysiciansPerSide;

    }

    public ArrayList<Physician> getPhysiciansBySide(int sideId) throws DAOException {

        Connection connection = null;
        DataSource ds = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Physician> arr = new ArrayList<Physician>();

        try {
            ds = AppDataSource.getDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new DAOException("NamingException encountered", e);
        }

        try {
            connection = ds.getConnection();

            String sqlString = "select physician_id, night_order from physicians where side_id = ?";

            preparedStatement = connection.prepareStatement(sqlString);
            preparedStatement.setInt(1, sideId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Physician row = new Physician();
                row.setPhysicianId(resultSet.getInt(1));
                row.setNightOrder(resultSet.getInt(2));
                arr.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("SQLException during data access", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception-close");
                System.out.println("SQLException: " + e.getMessage());
                throw new DAOException("SQL Exception on attempt to close connection", e);
            }
        }

        return arr;
    }
}
