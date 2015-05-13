/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import shop.Model.ConnectSQLDB;
import shop.Model.Item;
import shop.Model.SQLHelper;
import shop.View.ShopListener;

/**
 *
 * @author johan
 */
public class ConfigDatabase implements ShopListener {
    private ConnectSQLDB connectSQLDB;
    private int indexID = 0;
    
    public ConfigDatabase() {
        connectSQLDB = new ConnectSQLDB();
    }
    
    //Resets database to default.
    public void reset() {
        //Erase content in database.
        ArrayList tableNames = connectSQLDB.getTableNames();
        connectSQLDB.deleteTables(tableNames);
        
        //Setup database as default.
        ArrayList<String> queries = SQLHelper.setDefaultSQLQueries("query.txt");
        connectSQLDB.insert(queries);
    }
    
    @Override
    public int getNewID() {
       ArrayList<Integer> container = connectSQLDB.getAllItemsID();
       do {
           ++indexID;
       } while (container.contains(indexID));
       
        return indexID;
    }

    @Override
    public int addItem(Item item) {
        return connectSQLDB.insertItem(item);
    }

    @Override
    public boolean removeItem(Item item) {
       return connectSQLDB.deleteItem(item);
    }

    @Override
    public boolean setItemScore(Item item, int rating, String ssn) {
        return connectSQLDB.updateItemScore(item, rating, ssn);
    }

    @Override
    public int login(String userName, String password) {
        Map<String, String> container = new HashMap<String, String>();
        container = connectSQLDB.getValues("username", "password", "Staff");
        boolean match = connectSQLDB.matchDBAndValues(container, userName, password);
        //Staff.
        if (match) {
            return 2;
        }
        
        container.clear();
        container = connectSQLDB.getValues("username", "password", "Customer");
        match = connectSQLDB.matchDBAndValues(container, userName, password);
        //Customer.
        if (match) {
            return 1;
        }
        
        //wrong username and password.     
        return 0;
    }

    @Override
    public boolean register(int level, String userName,
            String password, String name, String street, String zip, String ssn) {
        if (level < 1 || level > 2)
            return false;
        
        ArrayList<String> tables = new ArrayList<>();
        tables.add("Customer");
        tables.add("staff");
        //Check if the user already exists.
        boolean isFound = connectSQLDB.find(ssn, tables, "PERSONALNUMBER");
        if (!isFound) {
            StringBuilder builder = new StringBuilder();

            String table;
            String userValues[] = new String[]
            {
                ssn, userName, password,
            };

            String personValues[] = new String[]
            {
                ssn, name, street, zip
            };

            if (level == 1) {
                table = "Customer";
            } else {
                table = "Staff";
            }

            connectSQLDB.insert(table, userValues);
            connectSQLDB.insert("Person", personValues);
            
            return true;
        }
        
        return false;
    }

    @Override
    public Item getItem(String articleNumber) {
        Item item = new Item();
        item = connectSQLDB.getItem(articleNumber);
        return item;
    }

    @Override
    public Item[] getItems() {
        ArrayList<Item> receive = new ArrayList<>();
        receive = connectSQLDB.getAllItems();
        final Item[] container = receive.toArray(new Item[receive.size()]);
        return container;
    }
    
    @Override
    public void resetDataBaseDEBUG()
    {
        reset();
    }
}
