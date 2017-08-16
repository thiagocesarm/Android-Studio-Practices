/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CKAN;

import java.util.Iterator;
import org.json.JSONArray;

/**
 *
 * @author Sinfo
 */
public abstract class ListResult {
    protected String[] results;
    
    public ListResult(JSONArray res){
        results = new String[res.length()];
        int i = 0;
        for (Iterator iterator = res.iterator(); iterator.hasNext();) {
            String next = (String)iterator.next();
            if(next instanceof String){
                this.results[i++] = next;
            }
        }
    }

    public String[] getResults() {
        return results;
    }
    
}
