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
public class ValidateInput {
     
    public boolean onlyAlpha(String value) {
        String regex = "[a-zA-Z]+";
        if (value.matches(regex)) {
            return true;
        }
        return false;
    }
    
    public boolean onlyNumbers(String value) {
        String regex = "[0-9]+";
        if (value.matches(regex)) {
            return true;
        }
        return false;
    }
}
