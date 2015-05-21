/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.View;

import java.util.Map;
import shop.Model.Item;
import shop.Model.Person;

/**
 *
 * @author Zerkish
 */
public interface ShopListener {
    
    // Add an item in a certain category etc.
    //Observe that addItem calls same function in model as updateItem (ConnectSQLDB.insertItem).
    public abstract int addItem(Item item);
    
    //Remove Item from the catalog (if necessary removes catalog).
    //For doing changes in the item objects stored in db please use updateItem(Item) instead.
    public abstract boolean removeItem(Item item);
    
    // Update the score of an item.
    public abstract boolean setItemScore(Item item, int score, String ssn);
    
    // Return the user level
    // 0 : FAILED
    // 1 : Customer
    // 2 : Employee
    public abstract int login(String userName, String password);
    
    // Return true if registration is successful.
    public abstract boolean register(int level, String userName,
            String password, String name, String street, String zip, String ssn);
    
    // Return all items.
    public abstract Item[] getItems();
    
    // Reset database
    public abstract void resetDataBaseDEBUG();
    
    // Update an item, the item id should already exist.
    public abstract boolean updateItem(Item item);
    
    //Creates a unique productId
    public abstract int getNewID();
    
    //Get specific user personalnumber.
    public abstract String getUserSSN(String username);
    
    //Return all person (staff and customer).
    public abstract Person[] getAllPersons();
    
    //Updates the content on an already inserted person in db.
    public abstract boolean updatePerson(Person person, String oldUsername);
    
    //Delete a specific person from database.
    public abstract boolean deletePerson(String username);
    
    //Delete level for person.
    public abstract boolean deleteUser(String username);
}
