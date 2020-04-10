package services;

import org.json.JSONObject;

import tools.CheckTools;
import tools.ErrorJSON;

public class Favoris {
	
	
	public static JSONObject addFavoris(String login,String titre) {
		return null;
		
	}
	
	public static JSONObject deleteFavoris(String login, String titre) {
		if(login.equals("") || titre.equals("")) {
			return ErrorJSON.serviceRefused("deleteFavoris: Argument Null", -1);
		}
		
		if(!CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("deleteFavoris: Pseudo "+login+" do not exist", -2);
		}
		
		
		return null;
		
	}
	
	public static JSONObject ListFavoris(String login) {
		
		if(CheckTools.checkUser(login)) {
			
		}
		return new JSONObject();
		
	}
	
}
