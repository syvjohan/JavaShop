/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Controller;

import shop.Model.ConnectSQLDB;
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
        //db.insertItem();
        //db.getItem("test");
        
        // Send in your controller implementing the ShopListener interface.
        View view = new View(cf);        
    }
    
}
