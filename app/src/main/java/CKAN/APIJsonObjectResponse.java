/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CKAN;

import org.json.JSONObject;

/**
 *
 * @author Sinfo
 */
public class APIJsonObjectResponse extends APIGenericResponse{
    protected JSONObject result;
    
    public APIJsonObjectResponse(String response) {
        super(response);
        JSONObject my_obj = new JSONObject(response);
        this.result = my_obj.getJSONObject("result");
    }

    public JSONObject getResult() {
        return result;
    }
    
}
