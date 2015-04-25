/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author johan
 */
public class ConnectSQLDB {
    String host = "jdbc:mysql://195.178.232.7:4040/ab5785";
    String uName = "ab5785";
    String uPass = "worshipous";
    
    Connection connection;
    Statement statement; 
    boolean connectionStatus;
    ResultSet rs;
    Query query;
    
    public ConnectSQLDB() {
        EstablishConnection();
    }
    
    private void EstablishConnection() {
        try {            
            Class.forName("com.mysql.jdbc.Driver");   
            
            try {
                 connection = DriverManager.getConnection(host, uName, uPass);
                 statement = connection.createStatement();
                 
            } catch (SQLException err) {
               err.printStackTrace();              
            }
           
        } catch (ClassNotFoundException err) {
            err.printStackTrace();
              System.out.println("Databas-driver could not found");
        }
    }

    private boolean testConnection() {
        try {
            connection.isValid(2000);
            return true;
        } catch (SQLException err) {
             err.printStackTrace();
        }
        return false;
    }
    
    private void cleanUp() {
        try {
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }  
    }
  
    //Querys...
    public String[] getItem(String tableName) {
        String[] arr = null;
        try {
            rs = statement.executeQuery("SELECT id, name FROM " + tableName);
            rs.first();
            int count = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String id = String.valueOf(rs.getInt("id"));
                arr[count] = name + id;
                count++;      
                System.out.println(name + id);
            }
        } catch (SQLException err) {
             err.printStackTrace();
        }
        
        cleanUp();
        return arr;      
    }
    
    public void insert(String[] querys) {
        try {
                for (int i = 0; i != querys.length; i++) {
                    statement.executeUpdate(querys[i]);
                }
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
    
    private void insert(String table, String[] values) {      
         try {
                statement.executeUpdate("INSERT INTO " +
                        table + "VALUES (" + values + ")"
                );
        } catch (SQLException err) {
            err.printStackTrace();
        }  
    }
    
    public void insertItem() {
        try {
                statement.executeUpdate("INSERT INTO test " +
                    "VALUES (10, 'johan')"
                );
        } catch (SQLException err) {
            err.printStackTrace();
        }  
        
        //cleanUp();
    }
           
    public String[] getTableNames() {
        String[] tableNames = null;
        
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, null, new String[] {"TABLE"});
            int count = 0;
            while(rs.next()) { 
                tableNames[count] = rs.getString("TABLE_NAME");
                count++;
            }
        } catch(SQLException err) {
            err.printStackTrace();
        }
        
        return tableNames;
    }
            
    public void deleteTables(String[] tableNames) {
        try {
           for ( int i = 0; i != tableNames.length; i++) {
                statement.executeUpdate("DROP TABLE " + tableNames[i]);
           }
        } catch(SQLException err) {
            err.printStackTrace();
        } 
    }
    
    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) return false;
        if (rhs == this) return true;
        if (rhs != this) return false;
        return false;
    }
}
