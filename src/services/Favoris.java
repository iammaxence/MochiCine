package services;

import org.json.JSONObject;

import tools.CheckTools;

public class Favoris {
	
	
	public static JSONObject addFavoris(String login,String titre) {
		return null;
		
	}
	
	public static JSONObject deleteFavoris(String login,String titre) {
		return null;
		
	}
	
	public static JSONObject ListFavoris(String login) {
		
		if(CheckTools.checkUser(login)) {
			
		}
		return new JSONObject();
		
	}
	
}
