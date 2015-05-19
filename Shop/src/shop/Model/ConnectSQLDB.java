/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Model;

import java.awt.List;
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
    private int indexID = 0;
    
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
    
    /*private void cleanUp() {
        try {
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException err) {
            err.printStackTrace();
        }  
    }*/
    
    private ArrayList<Score> getAllRatings() {
        ArrayList<Score> container = new ArrayList<>();
     
        try {
            rs = statement.executeQuery("SELECT * FROM Rating");
            
            while (rs.next()) {
                Score score = new Score();
                score.setID(rs.getInt("ID"));
                score.setRate(rs.getFloat("rate"));
                score.setSsn(rs.getString("personalnumber"));
                container.add(score);
            }
            
            for (int i = 0; i != container.size(); i++) {
                for (int k = container.size() -1; k != i; k--) {
                     if (container.get(i).getID() == container.get(k).getID()) {

                        container.get(i).setRate((container.get(i).getRate() + container.get(k).getRate()) / 2);
                        container.get(i).setSsn("null");

                        container.get(k).setID(-1);
                        container.get(k).setRate(0f);
                        container.get(k).setSsn("null");
                        
                    }
                }   
            }
            
            //Remove irregular numbers
            for (int i = container.size() -1; i >= 0; i--) {
                if (container.get(i).getID() == -1) {
                    container.remove(i);
                }
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return container;
    }
    
    private ArrayList<Item> matchItemWithRating(ArrayList<Item> items, ArrayList<Score> ratings) {
        ArrayList<Item> container = new ArrayList<>();
        
        for (int s = 0; s < ratings.size(); s++) {
            for (int k = 0; k < ratings.size(); k++) {
                if (ratings.get(s).getID() == items.get(k).getProductId()) {
                    
                    Item item = new Item();
                    item = items.get(k);
                    items.get(k).setScore(ratings.get(s).getRate());
                    container.add(item);
                } 
            }
        }
        
        //No score has been set.
        for (int i = 0; i != items.size(); i++) {
            if (!container.contains(items.get(i).getProductId())) {
                if (items.get(i).getScore() == 0) {
                    Item item = new Item();
                    item = items.get(i);
                    container.add(item);
                }
            }
        }
        
        return container;
    }
  
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> container = new ArrayList<>();
        try {
            rs = statement.executeQuery("SELECT Item.ID, Item.name, Item.categoryID,"
                    + " Item.amount, Item.price, Category.name, Category.ID "
                    + " FROM Item "
                    + " INNER JOIN Category ON Item.categoryID = Category.ID ");
            
            while (rs.next()) {
                    Item item = new Item();
                    item.setProductId(rs.getInt("Item.ID"));
                    item.setName(rs.getString("Item.name"));
                    item.setCategory(rs.getString("Category.name"));
                    item.setAmount(rs.getInt("Item.amount"));
                    item.setPrice(rs.getFloat("Item.price"));
                    item.setScore(0);

                    container.add(item);
            }  
            
            if (container.isEmpty()) return container;  

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matchItemWithRating(container, getAllRatings());
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
                rs = statement.executeQuery("SELECT " + columnName1 + ", " +
                        columnName2 +
                            " FROM " + table);
              
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
    
    public boolean deleteItem(Item item) {
        try {  
            rs = statement.executeQuery("SELECT * FROM Item");
            
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
    
    //Returns 1 if item existed, item.amount and rating was updated.
    //Returns 2 if item not existed and was succesfuly added to db.
    //Returns 3 if price not match existing price.
    //Returns 0 if adding item fails.
    public int insertItem(Item item) {
        try {
            //Check if item already exist in database.
                //verify with name and categoryID (categoryID has same value as ID)
                 rs = statement.executeQuery("SELECT Category.name, Category.ID, "
                    + "Item.name, Item.categoryID, Item.ID, Item.price "
                    + "FROM Item "
                    + "INNER JOIN Category ON Item.categoryID = Category.ID ");
               
               while (rs.next()) {
                   String category = rs.getString("Category.name");
                   String name = rs.getString("Item.name");
                   int price = rs.getInt("Item.price");
                   if (category.equals(item.getCategory()) && name.equals(item.getName())) {
                       if (price == item.getPrice()) {
                           //if Item exist, ++amount and calculate rating.
                            statement.executeUpdate("UPDATE Item " +
                                        " SET Item.amount = Item.amount + " + item.getAmount() +
                                        " WHERE Item.name = '" + item.getName() + "'" + 
                                        " AND '" + category + "' = '" + item.getCategory() + "'");
                            return 1;
                       }
                       else {
                           return 3;
                       }   
                   }
               }

                //If item not exist in database.
                //check if category name exist
                rs = statement.executeQuery("SELECT * FROM Category"
                        + " WHERE Category.name = '" + item.getCategory() + "'");
                
                 //if true set categoryID to the same categoryID
                int id = item.getProductId();
                if (rs.next()) {
                    id = rs.getInt("Category.ID");
                }
                //if false add category name and create a new categoryID
                else {
                    id = createNewID();
                    statement.executeUpdate("INSERT INTO Category "
                            + "VALUES( '" + id + "', '" + item.getCategory() + "')");
                }
                
                //Insert item.
                statement.executeUpdate("INSERT INTO Item " + 
                        "VALUES('" + item.getProductId() + "', '" + item.getName() + "', '" + id + "', '" + item.getAmount() +"', '" +
                        item.getPrice() + "')");
                
                return 2;
            
        } catch(SQLException err) {
            err.printStackTrace();
        }
        
        return 0;
    }
    
    public boolean updateItem(Item item) {
        return true;
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
    
    private ArrayList<Integer> getAllItemsID() {
        ArrayList<Integer> container = new ArrayList<Integer>();
        
        try {
            rs = statement.executeQuery("SELECT ID FROM Item");
            rs.first();
            do {
               int id = rs.getInt("ID");
                container.add(id); 
            } while(rs.next());
             
        } catch (SQLException err) {
            err.printStackTrace();
        }
        
        return container;
    }
    
    public int createNewID() {
         ArrayList<Integer> container = getAllItemsID();
       do {
           ++indexID;
       } while (container.contains(indexID));
       
        return indexID;
    }
    
    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) return false;
        if (rhs == this) return true;
        if (rhs != this) return false;
        return false;
    }
}
