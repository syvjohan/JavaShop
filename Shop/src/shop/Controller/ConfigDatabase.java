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
import shop.Model.Person;
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
        ArrayList<String> queries = SQLHelper.setDefaultSQLQueries("query.txt");
        connectSQLDB.insert(queries);
    }
    
    @Override
    public int getNewID() {
       return connectSQLDB.createNewID();
    }

    @Override
    public int addItem(Item item) {
        int i = connectSQLDB.insertItem(item);
        System.out.println(i);
        return i;
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
        boolean isFound = connectSQLDB.find(userName, tables, "username");
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
    
    @Override
    public boolean updateItem(Item item) {
        int succesful = connectSQLDB.insertItem(item);
        if (succesful == 1) {
            return true;
        }
        return false;
    }
    
    @Override
     public String getUserSSN(String username) {
         return username;
     }
     
     @Override
    public Person[] getAllPersons() {
        ArrayList<Person> receive = new ArrayList<>();
        receive = connectSQLDB.getAllPersons();
        final Person[] container = receive.toArray(new Person[receive.size()]);
        return container;
    }
    
    @Override
    public boolean deleteUser(String username) {
        return connectSQLDB.deletePerson(username);
    }
    
    @Override
    public boolean deleteUserLvl(String username, int lvl) {
        //Kan korrumpera databasen om användaren endast har en userlvl.
        //I detta fall kommer getAllPersons() inte retunera personen 
        //eftersom den inte innehar något username
       //return connectSQLDB.removeUserLvl(username, lvl);
        return false;
    }
    
}

//TODO UpdateItemScore.
//Ta bort en vara ur sortimentet
//Välja vilka varor som ska visas efter kategori
//Lägga in en ny person.
//SQL för att hämta all personal
//Logga ut


//Peter behöver:
//En klass som hetter person (get set metoder) inkl username. KLAR!
//En funktion som returnerar alla personer (anställda och kunder) KLAR!
//En funktion för att ta bort en användar (arg username)  KLAR!
//En funktion för att ta bort ett item (arg item) bägge retunerar bool.
//Updaterar en person (arg person).
//Uppdaterar ett item (arg item) KLAR!