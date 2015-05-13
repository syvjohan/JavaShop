/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Model;

import com.sun.corba.se.spi.ior.IdentifiableContainerBase;
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
              System.out.println("Database-driver could not be found");
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
  
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> container = new ArrayList<>();
        try {
            rs = statement.executeQuery("SELECT Item.ID, Item.name, Item.categoryID, "
                    + "Item.amount, Item.price, Category.name, Category.ID, Rating.rate "
                    + "FROM Item "
                    + "INNER JOIN CATEGORY ON Item.categoryID = Category.ID "
                    + "INNER JOIN Rating ON Item.ID = Rating.ID");
            
            rs.first();
            while (rs.next()) {
                Item item = new Item();
                item.setProductId(rs.getInt("Item.ID"));
                item.setName(rs.getString("Item.name"));
                item.setCategory(rs.getString("Category.name"));
                item.setAmount(rs.getInt("Item.amount"));
                item.setPrice(rs.getFloat("Item.price"));
                item.setScore(rs.getInt("Rating.rate"));
                
                container.add(item);
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
    
    public Item getItem(String objToFind) {
        Item item = new Item();
        Integer obj = Integer.parseInt(objToFind);
        
        try {           
            rs = statement.executeQuery("SELECT Item.ID, Item.name, Item.categoryID, "
                    + "Item.amount, Item.price, Category.name, Category.ID, Rating.rate "
                    + "FROM Item "
                    + "INNER JOIN Category ON Item.categoryID = Category.ID "
                    + "INNER JOIN Rating ON Item.ID= Rating.ID");
            
            rs.first();
            while (rs.next()) {
                if (obj.equals(rs.getInt("Item.ID"))) {
                    item.setProductId(rs.getInt("Item.ID"));
                    item.setName(rs.getString("Item.name"));
                    item.setCategory(rs.getString("Category.name"));
                    item.setAmount(rs.getInt("Item.amount"));
                    item.setPrice(rs.getFloat("Item.price"));
                    item.setScore(rs.getInt("Rating.rate"));
                    return item;
                }  
            }
            
        } catch (SQLException err) {
             err.printStackTrace();
        }      

        return item;
    }
    
    public boolean deleteItem(Item item) {
        try {  
            rs = statement.executeQuery("SELECT * FROM Item");
            
            rs.first();
            while(rs.next()) {
                String name = rs.getString("name");
                int amount = rs.getInt("amount");
                int art = rs.getInt("ID");
                
                if (name.equals(item.getName()) && art == item.getProductId()) {
                    if (amount >= item.getAmount()) {
                        statement.executeUpdate("UPDATE Item " +
                                " SET Item.amount = Item.amount - " + item.getAmount() +
                                " WHERE Item.name = '" + item.getName() + "'" + 
                                " AND Item.ID = '" + item.getProductId() + "'");
                        
                        return true;
                    }
                }
            }
            
        } catch (SQLException err) {
            err.printStackTrace();
        }
                   
        return false;
    }

    public boolean updateItemScore(Item item, int rating, String ssn) {
       try {  
            rs = statement.executeQuery("SELECT Item.name, Item.ID FROM Item");
            
            rs.first();
            while(rs.next()) {
                String name = rs.getString("Item.name");
                int artItem = rs.getInt("Item.ID");
                
                if (name.equals(item.getName()) && artItem == item.getProductId()) {
                        statement.executeUpdate("INSERT INTO Rating " +
                                "VALUES(" + item.getProductId() + ", '" + ssn + "'" +
                                ", " + rating + ")"); 

                        return true;
                }
            }
            
        } catch (SQLException err) {
            err.printStackTrace();
        }
                   
        return false;
    }
    
    public boolean find(String objToFind, ArrayList<String> tables, String columnName) {
        ArrayList<String> container = new ArrayList<String>();
        try {
            for (int i = 0; i != tables.size(); i++) {

                rs = statement.executeQuery("SELECT " + columnName +
                        " FROM " + tables.get(i));

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
    
    //Returns 1 if item existed, item.amount was updated.
    //Returns 2 if item not existed and was succesfuly added to db.
    //Returns 0 if adding item fails.
    public int insertItem(Item item) {      
        try {
               //Check if item already exist in database.
               rs = statement.executeQuery("SELECT Category.name, Category.ID, "
                    + "Item.name, Item.categoryID, Item.ID, Rating.ID, Rating.rate "
                    + "FROM Item "
                    + "INNER JOIN Category ON Item.categoryID = Category.ID "
                    + "INNER JOIN Rating ON Item.ID = Rating.ID");
               
               rs.first();
               while (rs.next()) {
                   String cat = rs.getString("Category.name");
                   String name = rs.getString("Item.name");
                   String art = rs.getString("Item.ID");
                   if (cat.equals(item.getCategory()) && name.equals(item.getName())) {
                        statement.executeUpdate("UPDATE Item " +
                                " SET Item.amount = Item.amount + " + item.getAmount() +
                                " WHERE Item.name = '" + item.getName() + "'" + 
                                " AND Item.ID = '" + item.getProductId() + "'");
                       
                        return 1;
                   }
               }
               
                //Check if category exist.
                Map<Integer, String> category = new HashMap();
                int id = 0;
                String name = "";
                rs = statement.executeQuery("SELECT * FROM Category ");
                rs.first();
                while (rs.next()) {
                    id = rs.getInt("ID");
                    name = rs.getString("name");
                    category.put(id, name);
                }
                
                int categoryID = -1;
                Iterator it = category.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if (pair.getValue().equals(item.getCategory())) {
                        categoryID = Integer.parseInt(pair.getKey().toString());
                        break;
                    }
                }      
                //If name dont exist.
                if (categoryID == -1) {
                    statement.executeUpdate("INSERT INTO Category (name) "
                            + "VALUES ('" + item.getCategory() + "')");

                    //Get category id.
                    rs = statement.executeQuery("SELECT * FROM Category ");  

                    while (rs.next()) {
                        int tmpID = rs.getInt("ID");
                        String tmpName = rs.getString("name");
                        if (item.getCategory().equals(tmpName)) {
                            categoryID = tmpID;
                        }
                    }
                }
                
                statement.executeUpdate("INSERT INTO Item (name, categoryID, amount, price) "
                    + "VALUES ('" + item.getName() + "', '"
                    + categoryID + "', " + item.getAmount() + ", " + item.getPrice() 
                    + ")");
                
                return 2;
                
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
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
    
    public ArrayList<Integer> getAllItemsID() {
        ArrayList<Integer> container = new ArrayList<Integer>();
        
        try {
            rs = statement.executeQuery("SELECT ID FROM Item");
            rs.first();
            while(rs.next()) {
                int id = rs.getInt("ID");
                container.add(id);
            }
             
        } catch (SQLException err) {
            err.printStackTrace();
        }
        
        return container;
    }
    
    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) return false;
        if (rhs == this) return true;
        if (rhs != this) return false;
        return false;
    }
}
