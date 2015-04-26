/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Controller;

import java.io.File;
import java.util.ArrayList;
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
    
    public ConfigDatabase() {
        connectSQLDB = new ConnectSQLDB();
    }
    
    //Resets database to default.
    public void reset() {
        //Erase content in database.
        ArrayList tableNames = connectSQLDB.getTableNames();
        connectSQLDB.deleteTables(tableNames);
        
        //Setup database as default.
        ArrayList<String> queries = SQLHelper.getSQLQueries("query.txt");
        connectSQLDB.insert(queries);
    }
    
    private ArrayList<String> getDefaultQuerys() {

        ArrayList<String> queries = SQLHelper.getSQLQueries("query.txt");
        return queries;
    }

    @Override
    public void addItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setItemScore(Item item, int score) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int login(String userName, String password) {
        // TODO: Check staff and customer tables for userName
        //       match it with password and return the correct level
        //       (1 for customer, 2 for staff) return 0 on failure.        
        return 0;
    }

    @Override
    public boolean register(int level, String userName,
            String password, String name, String street, String zip, String ssn) {
        if (level < 1 || level > 2)
            return false;
        
        // TODO: Check if the user already exists?
        
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

    @Override
    public Item getItem(String category, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Item[] getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void resetDataBaseDEBUG()
    {
        reset();
    }
}
