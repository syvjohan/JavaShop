/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Controller;

import shop.Model.ConnectSQLDB;
import shop.Model.Item;
import shop.View.View;
import shop.Model.Person;

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
        cf.reset();
        
        Item item = new Item();
        item.setProductId(cf.getNewID());
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
        item2.setCategory("runda grejer");
        item2.setScore(8);
        
        Person person = new Person();
        person.setName("John Doe");
        person.setSsn("19880306-4568");
        person.setUsername("testcustomer");
        person.setUserLvl(2);
        person.setZip("12345");
        person.seStreet("bondageRoad");
        person.setUserLvl(1);
        
        //cf.updatePerson(person, "testcustomer");
        
        // Send in your controller implementing the ShopListener interface.
        View view = new View(cf);        

    }
    
}
