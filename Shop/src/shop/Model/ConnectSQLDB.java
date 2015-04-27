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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


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
    public Map getItem(String tableName) {
        Map container = new HashMap();
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT id, name FROM " + "test");
            rs.first();
            int count = 0;

            while (rs.next()) {
                String name = rs.getString("name");
                String id = String.valueOf(rs.getInt("id"));
                container.put(count, id + name);
                count++;      
                System.out.println(name + id);
            }
        } catch (SQLException err) {
             err.printStackTrace();
        }
        
        return container;      
    }
    
    public boolean matchDBAndValues(Map<String, String> container, String value1, String value2) {
        Iterator it = container.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (pair.getKey().equals(value1) && pair.getValue().equals(value2)) {
                return true;
            }
        }
        
        return false;
    }
    
    public Map<String, String> getValues(String columnName1, String columnName2, String table) {
        Map<String, String> container = new HashMap<String, String>();
        
        try {
                rs = statement.executeQuery("SELECT " + columnName1 + "," +
                        columnName2 +
                            " FROM " + table);
              
                rs.first();
                do {
                    String user = rs.getString(columnName1); 
                    String pwd = rs.getString(columnName2);
                    container.put(user, pwd);

                } while(rs.next());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return container;
    }
    
    public boolean find(String objToFind, ArrayList<String> table, String columnName) {
        ArrayList<String> container = new ArrayList<String>();
        try {
            for (int i = 0; i != table.size(); i++) {
                //statement = connection.createStatement();
                rs = statement.executeQuery("SELECT " + columnName +
                        " FROM " + table.get(i));

                rs.first();
                do {
                    String value = rs.getString(columnName);
                    container.add(value);
                    
                } while (rs.next());
            }
            
            if (container.contains(objToFind)) {
                  return true;
            }
            
        } catch (SQLException err) {
             err.printStackTrace();
        }      
        
        return false;
    }
    
    public void insert(ArrayList<String> querys) {
        try {
                for (int i = 0; i != querys.size(); i++) {
                    statement.executeUpdate(querys.get(i));
                }
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
    
    public void insert(String table, String[] values) {
        
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(table);
        builder.append(" VALUES (");
        for ( int i = 0; i < values.length; ++i)
        {
            builder.append("'" + values[i] + "'");
            if (i < values.length - 1)
                builder.append(", ");
        }
        builder.append(");");
        
         try {
            statement.executeUpdate(builder.toString());
        } catch (SQLException err) {
            err.printStackTrace();
        }  
    }
           
    public ArrayList<String> getTableNames() {
        ArrayList<String> tableNames = new ArrayList();
        
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, null, new String[]{"TABLE"});
            rs.first();
            while(rs.next()) { 
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        } catch(SQLException err) {
            err.printStackTrace();
        }
        
        return tableNames;
    }
            
    public void deleteTables(ArrayList<String> tableNames) {
        try {
           for ( int i = 0; i != tableNames.size(); i++) {
                statement.executeUpdate("DROP TABLE " + tableNames.get(i));
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
