package services;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiService {
	
	public static JSONObject weeklySeries() throws IOException{
		
		return tools.ApiTools.weeklySeries();
	}
	
	public static JSONObject filmsOnAir() throws IOException{

		return tools.ApiTools.filmsOnAir();
	}
	
	public static JSONObject recherche(String key,String keyword) throws IOException{
		return tools.ApiTools.recherche(key,keyword);
	}
}
