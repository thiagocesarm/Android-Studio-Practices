/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CKAN;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Sinfo
 */
public class APIJsonArrayResponse extends APIGenericResponse{
    protected JSONArray result;
    
    public APIJsonArrayResponse(String response) {
        super(response);
        JSONObject my_obj = new JSONObject(response);
        this.result = my_obj.getJSONArray("result");
        
    }

    public JSONArray getResult() {
        return result;
    }
    
}
