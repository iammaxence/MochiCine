package services;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ErrorJSON;

public class ApiService {
	
	public static JSONObject weeklySeries() throws IOException{
		
		return tools.ApiTools.weeklySeries();
	}
	
	public static JSONObject filmsOnAir() throws IOException{

		return tools.ApiTools.filmsOnAir();
	}
	
	public static JSONObject recherche(String key,String keyword) throws IOException{
		if(keyword.equals("")) 
			return ErrorJSON.serviceRefused("Keyword is empty", -1);
		
		if(key.equals(""))
			return ErrorJSON.serviceRefused("Invalid key", -2);
		return tools.ApiTools.recherche(key,keyword);
	}
}
