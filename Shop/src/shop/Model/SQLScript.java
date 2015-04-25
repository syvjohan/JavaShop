/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author johan
 */
public class SQLScript {

     public char[] readFile(String path) {
        char[] content = null;
        if (path != null) {
            try {
                File file = new File(path);
                FileReader filereader = new FileReader(file);
                content = new char[(int) file.length()];
                filereader.read(content);
                filereader.close();
                return content;
            } catch (IOException err) {
                err.printStackTrace();
            }  
        }

        return content;
    }
     
    public String[] createStrings(char[] file) {
        int size = file.length;
        int len = 0;
        String[] content = new String[size];
        String str = "";
        int endPos = 0;
        
        for (int startPos = 0; startPos != file.length; startPos++) {
            //Search for end of string
            do {
                str += file[endPos];
                endPos++;
            } while(file[endPos] != ' ');
            
            //Reached end of string
            content[len] = str;
            startPos = endPos +1; 
            len++;
        }
        
        return content;
    }

    public String[] getQuery(String[] file, String[] keywords) {
        String[] querys = new String[file.length];
        String q = "";
        int startPos = -1;
        int endPos = -1;
        int countQuerys = 0;
        
        if (file != null && keywords != null) {
            for (int i = 0; !file[i].equals(file.length); i++) {
                for (int j = 0; !keywords[j].equals(keywords.length); j++) {
                    
                    if (file[i].equals(keywords)) {
                        startPos = i; 
                    } 

                    if (file[i].equals(");")) {
                        endPos = i;
                        if (startPos != -1){
                            for (int k = startPos; k != endPos +1; k++) {
                                   q += file[k];
                            }
                            querys[countQuerys] = q;
                            countQuerys++;
                            
                            //Reset counts.
                            startPos = -1;
                            endPos = -1;
                        }
                    }  
                }              
            }
        }
        return querys; 
    }
    
    public void doTableExist(String table) {
        String query = "SELECT 1 FROM" + table +
                "LIMIT 1";
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null) return false;
        if (rhs == this) return true;
        if (rhs != this) return false;
        return false;
    }
}
