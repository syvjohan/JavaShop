/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Model;

/**
 *
 * @author johan
 */
public class Person {
    private String ssn;
    private String name;
    private String street;
    private String zip;
    private String username;
    private int userLvl;
    
    public Person() {
        ssn = "";
        name = "";
        street = "";
        zip = "";
        username = "";
    }
    
    public Person(String ssn, String name, String street, String zip, String username, int userLvl) {
        this.ssn = ssn;
        this.name = name;
        this.street = street;
        this.zip = zip;
        this.username = username;
        this.userLvl = userLvl;
    }
    
    public String getSsn() {
        return this.ssn;
    }
    
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getStreet() {
        return this.street;
    }
    
    public void seStreet(String street) {
        this.street = street;
    }
    
    public String getZip() {
        return this.zip;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public int getUserLvl() {
        return this.userLvl;
    }
    
    public void setUserLvl(int userLvl) {
        this.userLvl = userLvl;
    }
    
    public Person makeGenericCopy() {
        Person p = new Person();
        p.name = name;
        p.ssn = ssn;
        p.street = street;
        p.username = username;
        p.zip = zip;
        p.userLvl = userLvl;
        return p;
    }
}
