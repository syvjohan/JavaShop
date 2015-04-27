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
              System.out.println("Databas-driver could not be found");
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
  
    public Map<Integer, Item> getAllItems() {
        Map<Integer, Item> container = new HashMap();       
        try {
            rs = statement.executeQuery("SELECT Item.ARTICLENUMBER, Item.name, Item.categoryID, "
                    + " Item.amount, Item.price, Category.name, Category.ID"
                    + " FROM Item "
                    + "INNER JOIN CATEGORY ON Item.categoryID = Category.ID");
            
            int count = 0;
            rs.first();
            while (rs.next()) {
                Item item = new Item();
                item.setProductId(rs.getInt("Item.ARTICLENUMBER"));
                item.setName(rs.getString("Item.name"));
                item.setCategory(rs.getString("Category.name"));
                item.setAmount(rs.getInt("Item.amount"));
                item.setPrice(rs.getFloat("Item.price"));
                
                container.put(count, item);
                count++;
            }                   

        } catch (SQLException e) {
            e.printStackTrace();
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
                while(rs.next()) {
                    String user = rs.getString(columnName1); 
                    String pwd = rs.getString(columnName2);
                    container.put(user, pwd);

                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return container;
    }
    
    public Item findItem(String objToFind) {
        Item item = new Item();
        Integer obj = Integer.parseInt(objToFind);
        
        try {           
            rs = statement.executeQuery("SELECT Item.ARTICLENUMBER, Item.name, Item.categoryID, "
                    + " Item.amount, Item.price, Category.name, Category.ID"
                    + " FROM Item "
                    + "INNER JOIN CATEGORY ON Item.categoryID = Category.ID");
            
            rs.first();
            while (rs.next()) {
                if (obj.equals(rs.getInt("Item.ARTICLENUMBER"))) {
                    item.setProductId(rs.getInt("Item.ARTICLENUMBER"));
                    item.setName(rs.getString("Item.name"));
                    item.setCategory(rs.getString("Category.name"));
                    item.setAmount(rs.getInt("Item.amount"));
                    item.setPrice(rs.getFloat("Item.price"));
                    return item;
                }  
            }
            
        } catch (SQLException err) {
             err.printStackTrace();
        }      

        return item;
    }
    
    public boolean find(String objToFind, ArrayList<String> table, String columnName) {
        ArrayList<String> container = new ArrayList<String>();
        try {
            for (int i = 0; i != table.size(); i++) {
                //statement = connection.createStatement();
                rs = statement.executeQuery("SELECT " + columnName +
                        " FROM " + table.get(i));

                rs.first();
                while (rs.next()) {
                    String value = rs.getString(columnName);
                    container.add(value);
                } ;
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
