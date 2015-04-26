/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
                content = new char[(int) file.length() +1];
                filereader.read(content);
                filereader.close();
                return content;
            } catch (IOException err) {
                err.printStackTrace();
            }  
        }

        return content;
    }
     
    public ArrayList<String> createStrings(char[] file) {
        ArrayList content = new ArrayList();
        String str = "";
        int endPos = 0;
        int startPos = 0;
        
        for (startPos = endPos; startPos < (file.length -1); startPos++) {
            //Search for end of string                                     
            do {
                str += file[endPos];
                endPos++;
            } while(file[endPos] != ' ' /* || (file[endPos] != '/' && file[endPos+1] != 'r') || 
                    (file[endPos] != '/' && file[endPos+1] != 'n') ||
                    (file[endPos] != '/' && file[endPos+1] != 't')*/);
            
            //Reached end of string
            content.add(str); 
            str = "";
        }

        return content;
    }

    public ArrayList getQuery(ArrayList file, ArrayList keywords) {
        ArrayList querys = new ArrayList();
        String q = "";  
        int startPos = 0;
        int endPos = 0;
        if (file != null && keywords != null) {
            for (int i = 0; !file.get(i).equals(file.size()); i++) {
                for (int j = 0; !keywords.get(j).equals(keywords.size()); j++) {
                    
                    if (file.get(i).equals(keywords)) {
                        startPos = i; 
                    } 
                    
                    //Identifie end of query.
                    if (file.get(i).equals(");")) {
                        endPos = i;
                        if (startPos != -1){
                            for (int k = startPos; k != endPos +1; k++) {
                                   q += file.indexOf(k);
                            }
                            querys.add(q);
                            
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
