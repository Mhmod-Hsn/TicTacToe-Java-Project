/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.utils;

import java.util.Vector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import playerinfo.Player;

/**
 *
 * @author Hossam
 */
public abstract class JSONHandeling {
    
    private static JSONObject jsonObj;
    private static JSONParser parser ;
    
    public static JSONObject constructJsonResponse(JSONObject jsonObj, String type)
    {   
        //add the relavent fields
        jsonObj = addToJsonObject(jsonObj, "type", type);
        jsonObj = addToJsonObject(jsonObj, "responseStatus", true);
        jsonObj = addToJsonObject(jsonObj, "errorMsg", "");
        
        return jsonObj;
    }
    
    public static JSONObject errorToJson(String type, String errorMsg)
    {
        //Construct the json object
        jsonObj = new JSONObject();
        
        //add the relavent fields
        jsonObj = addToJsonObject(jsonObj, "type", type);
        jsonObj = addToJsonObject(jsonObj, "responseStatus", false);
        jsonObj = addToJsonObject(jsonObj, "errorMsg", errorMsg);
        
        return jsonObj;
    }
    
    public static JSONObject addToJsonObject (JSONObject jsonObj , String key , Object value)
    {
        jsonObj.put(key,value);
        return jsonObj;
    }

    public static JSONObject parseStringToJson(String jsonStr) throws ParseException
    {
        parser = new JSONParser();
        jsonObj = new JSONObject();
        
        jsonObj = (JSONObject)parser.parse(jsonStr);
        
        return jsonObj;
    }
    
    public static JSONObject playerToJson(Player player)
    {   
        jsonObj = new JSONObject();

        //add the player attributes fields
        jsonObj = addToJsonObject(jsonObj,"username", player.getUsername());
        jsonObj = addToJsonObject(jsonObj,"status", player.getStatus().toString());
        jsonObj = addToJsonObject(jsonObj,"score",  player.getScore());
        jsonObj = addToJsonObject(jsonObj,"avatar", player.getAvatar());

        return jsonObj;
    }
    
    public static JSONArray playerListToJSONArray(Vector <Player> playerList)
    {
        //Construct json array
        JSONArray jsonPlayersList = new JSONArray();
        
        playerList.forEach((playerInfo) -> {
            jsonPlayersList.add(playerToJson(playerInfo));
        });
        
        return jsonPlayersList;
    }
    
}
