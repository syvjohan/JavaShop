/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.View;

import shop.Model.Item;

/**
 *
 * @author Zerkish
 */
public interface ShopListener {
    
    // Add an item in a certain category etc.
    public abstract void addItem(Item item);
    
    // Remove 1 or more items.
    public abstract void removeItem(Item item);
    
    // Update the score of an item.
    public abstract void setItemScore(Item item, int score);
    
    // Return the user level
    // 0 : FAILED
    // 1 : Customer
    // 2 : Employee
    public abstract int login(String userName, String password);
    
    // Return true if registration is successful.
    public abstract boolean register(int level, String userName,
            String password, String name, String street, String zip, String ssn);
    
    // Return an item based on category and name.
    public abstract Item getItem(String category, String name);
    
    // Return all items.
    public abstract Item[] getItems();
    
    // Reset database
    public abstract void resetDataBaseDEBUG();
}
