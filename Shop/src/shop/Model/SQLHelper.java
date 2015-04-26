/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author johan
 */
public class SQLHelper {
    
    private SQLHelper() {}
    
    private static boolean isWhiteSpace(char c)
    {
        return c == '\r' || c == '\n' || c == '\r' || c == '\t';
    }

    public static ArrayList<String> getSQLQueries(String path) {
        ArrayList<String> queries = new ArrayList();
        
        File file = new File(path);
        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException ex) {
            return null;
        }
        
        try{
            char c;
            StringBuilder builder = new StringBuilder();
            while (reader.ready())
            {
                c = (char)reader.read();
                if (isWhiteSpace(c))
                    continue;

                if (builder.length() == 0 && c == ' ')
                    continue;

                builder.append(c);

                if (c == ';')
                {
                    queries.add(builder.toString());
                    builder = new StringBuilder();
                }
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }

        return queries;
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
