/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CKAN;

import org.json.*;

/**
 *
 * @author Sinfo
 */
public abstract class APIGenericResponse {
    protected String help;
    protected Boolean success;

    public APIGenericResponse(String response) throws JSONException {
        JSONObject my_obj = new JSONObject(response);
        this.help = my_obj.getString("help");
        this.success = my_obj.getBoolean("success");
    }

    public String getHelp() {
        return help;
    }

    public Boolean getSuccess() {
        return success;
    }
    
}
