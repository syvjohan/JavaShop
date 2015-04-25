/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Controller;

import shop.Model.ConnectSQLDB;
import shop.Model.SQLScript;

/**
 *
 * @author johan
 */
public class ConfigDatabase {
    ConnectSQLDB connectSQLDB;
    SQLScript sQLScript;
    
    public ConfigDatabase() {
        connectSQLDB = new ConnectSQLDB();
        sQLScript = new SQLScript();
    }
    
    //Resets database to default.
    public void reset() {
        //Erase content in database.
        String[] tableNames = connectSQLDB.getTableNames();
        connectSQLDB.deleteTables(tableNames);
        
        //Setup database as default.
        String[] querys = getDefaultQuerys();
        connectSQLDB.insert(querys);
    }
    
    private String[] getDefaultQuerys() {
        char[] readQuerys = sQLScript.readFile(getClass().getResource("query.sql").getPath());
        char[] readKeywords = sQLScript.readFile(getClass().getResource("SQLKeywords.txt").getPath());
        
        String[] querystoStr = sQLScript.createStrings(readQuerys);
        String[] keywordstoStr = sQLScript.createStrings(readKeywords);
        
        String[] querys = sQLScript.getQuery(querystoStr, keywordstoStr);
        return querys;
    }
}
