/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.View;

import java.util.Map;
import shop.Model.Item;

/**
 *
 * @author Zerkish
 */
public interface ShopListener {
    
    // Add an item in a certain category etc.
    public abstract void addItem(Item item, String ssn);
    
    // Remove 1 or more items.
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
    
    // Return an item based on ArticleNumber.
    public abstract Item getItem(String articleNumber);
    
    // Return all items.
    public abstract Item[] getItems();
    
    // Reset database
    public abstract void resetDataBaseDEBUG();
}
