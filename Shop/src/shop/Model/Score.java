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
public class Score {
    private int ID;
    private String ssn;
    private float rate;
    
    public Score() {
        ID = 0;
        ssn = "null";
        rate = 0f;
    }
    
    public Score(int ID, String ssn, float rate) {
        this.ID = ID;
        this.ssn = ssn;
        this.rate = rate;
    }
    
    public float getRate() {
        return this.rate;
    }
    
    public void setRate(float rate) {
        this.rate = rate;
    }
    
    public int getID() {
        return this.ID;
    }
    
    public void setID(int id) {
        this.ID = id;
    }
    
    public String getSsn() {
        return this.ssn;
    }
    
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
    public Score makeGenericCopy() {
        Score s = new Score();
        s.ID = ID;
        s.rate = rate;
        s.ssn = ssn;
        return s;
    }
}
