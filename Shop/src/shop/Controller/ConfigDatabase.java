/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Controller;

import java.io.File;
import java.util.ArrayList;
import shop.Model.ConnectSQLDB;
import shop.Model.SQLHelper;

/**
 *
 * @author johan
 */
public class ConfigDatabase {
    private ConnectSQLDB connectSQLDB;
    
    public ConfigDatabase() {
        connectSQLDB = new ConnectSQLDB();
    }
    
    //Resets database to default.
    public void reset() {
        //Erase content in database.
        ArrayList tableNames = connectSQLDB.getTableNames();
        connectSQLDB.deleteTables(tableNames);
        
        //Setup database as default.
        ArrayList querys = getDefaultQuerys();
        connectSQLDB.insert(querys);
    }
    
    private ArrayList<String> getDefaultQuerys() {
//        
//        char[] readQuerys = sQLScript.readFile("query.txt");
//        char[] readKeywords = sQLScript.readFile("SQLKeywords.txt");
//        
//        
//        
//        //ArrayList<String> querystoStr = sQLScript.createStrings(readQuerys);
//        ArrayList<String> querystoStr = sQLScript.getSQLQueries(readQuerys);
//        ArrayList<String> keywordstoStr = sQLScript.createStrings(readKeywords);
//        
//        ArrayList querys = sQLScript.getQuery(querystoStr, keywordstoStr);
//        return querys;
//        
        ArrayList<String> queries = SQLHelper.getSQLQueries("query.txt");
        return queries;
    }
}
