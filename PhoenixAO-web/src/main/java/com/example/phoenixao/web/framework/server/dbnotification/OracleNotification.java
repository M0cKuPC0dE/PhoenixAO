/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.framework.server.dbnotification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

/**
 *
 * @author wjirawong
 */
public class OracleNotification {
    
    OracleConnection oracleConnection;
    
    public OracleNotification() throws NamingException, SQLException{
        InitialContext context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup("jdbc/sti_common");
        oracleConnection =  (OracleConnection)dataSource.getConnection().unwrap(OracleConnection.class);
        
        System.out.println("init oracle");
    }

    public DatabaseChangeRegistration registerDatabaseNotification(String table, DatabaseChangeListener databaseChangeListener) {
        List<String> tables = new ArrayList<String>();
        tables.add(table);
        return this.registerDatabaseNotification(tables, databaseChangeListener);
    }

    public DatabaseChangeRegistration registerDatabaseNotification(List<String> tables, DatabaseChangeListener databaseChangeListener) {
        Properties properties = new Properties();
        properties.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
        // need to fix port enable it
        //properties.setProperty(OracleConnection.NTF_LOCAL_TCP_PORT, "8095");
        
        Statement stmt;
        DatabaseChangeRegistration databaseChangeRegistration = null;
        try {
            databaseChangeRegistration = oracleConnection.registerDatabaseChangeNotification(properties);

            databaseChangeRegistration.addListener(databaseChangeListener);

            stmt = oracleConnection.createStatement();
            ((OracleStatement) stmt).setDatabaseChangeRegistration(databaseChangeRegistration);

            for (String table : tables) {
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + " WHERE rownum < 1");
                while (rs.next()) {
                }
                rs.close();
            }
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return databaseChangeRegistration;
    }

    public void terminate(DatabaseChangeRegistration databaseChangeRegistration) {
        try {
            oracleConnection.unregisterDatabaseChangeNotification(databaseChangeRegistration);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
