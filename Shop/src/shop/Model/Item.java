/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Zerkish
 */
public class Item {
    
    private int amount;
    private float price;
    private String name;
    private String category;
    private float score;
    private int productId;
    
    public Item()
    {
        amount = 0;
        price = 0;
        name = "null";
        category = "null";
    }
    
    public Item(int id, String name, String category, float price, int amount)
    {
        productId = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.amount = amount;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the score
     */
    public float getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public Item makeGenericCopy()
    {
        Item i = new Item();
        i.amount = 0;
        i.category = category;
        i.price = price;
        i.productId = productId;
        i.score = score;
        i.name = name;
        return i;        
    }
    
    
}
