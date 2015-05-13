/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Controller;

import shop.Model.ConnectSQLDB;
import shop.Model.Item;
import shop.View.View;

/**
 *
 * @author johan
 */
public class Shop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnectSQLDB db = new ConnectSQLDB();
        ConfigDatabase cf = new ConfigDatabase();
        cf.reset();
        /*Item item = new Item();
        item.setProductId(3);
        item.setName("reglar");
        item.setAmount(10);
        item.setPrice((float) 100.10);
        item.setCategory("trä");
        item.setScore(8);
        
        
        Item item2 = new Item();
        item2.setProductId(2);
        item2.setName("fotboll");
        item2.setAmount(1);
        item2.setPrice((float) 490.00);
        item2.setCategory("bollar");
        item2.setScore(8);*/
        
        System.out.println(cf.getNewID());

        // Send in your controller implementing the ShopListener interface.
        View view = new View(cf);        
    }
    
}
