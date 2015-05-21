/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Controller;

import shop.View.View;

/**
 *
 * @author johan
 */
public class Shop {

    public static void main(String[] args) {
        ConfigDatabase cf = new ConfigDatabase();
        
        // Send in your controller implementing the ShopListener interface.
        View view = new View(cf);        

    }
    
}
