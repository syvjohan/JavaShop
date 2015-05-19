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

    public static void compareStrings(String s1, String s2) {
        if ( s1.length() != s2.length() ) {
            System.out.println("Length does not match...");
        }
        
        for ( int i = 0; i < s1.length(); ++i ) {
            if ( s1.charAt(i) != s2.charAt(i) ) {
                System.out.println(String.format("Compare failed at index %d, (%c != %c)",
                        i, s1.charAt(i), s2.charAt(i))); 
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnectSQLDB db = new ConnectSQLDB();
        ConfigDatabase cf = new ConfigDatabase();
        //cf.reset();
        
        // Send in your controller implementing the ShopListener interface.
        View view = new View(cf);        

    }
    
}
