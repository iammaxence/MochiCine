package services;

import org.json.JSONObject;

import tools.CheckTools;
import tools.ErrorJSON;
import tools.FavorisTools;

public class Favoris {
	
	
	public static JSONObject addFavoris(String login, String titre) {
		if(login.equals("") || titre.equals("")) {
			return ErrorJSON.serviceRefused("deleteFavoris: Argument Null", -1);
		}
		
		if(!CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("deleteFavoris: Pseudo "+login+" do not exist", -2);
		}
		
		return FavorisTools.addFavoris(login, titre);
	}
	
	public static JSONObject deleteFavoris(String login, String titre) {
		if(login.equals("") || titre.equals("")) {
			return ErrorJSON.serviceRefused("deleteFavoris: Argument Null", -1);
		}
		
		if(!CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("deleteFavoris: Pseudo "+login+" do not exist", -2);
		}
		
		
		return FavorisTools.deleteFavoris(login, titre);
		
	}
	
	public static JSONObject ListFavoris(String login) {
		if(login.equals("")) {
			return ErrorJSON.serviceRefused("ListFavoris: Argument Null", -1);
		}
		
		if(!CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("ListFavoris: Pseudo "+login+" do not exist", -2);
		}
		
		return FavorisTools.getFavoris(login);
		
	}
	
}
