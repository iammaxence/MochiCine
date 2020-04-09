package services;

import org.json.JSONObject;

import tools.Auth;
import tools.CheckTools;

public class Authentification {
	
	public static JSONObject login(String login,String mdp) {
		return null;
		
	}
	
	public static JSONObject logout(String login) {
		
		if(CheckTools.checkUser(login)) {
			Auth.logout(login);
			return new JSONObject(); //JSON renvoyé -> ok
		}
		
		return new JSONObject(); //JSON renvoyé -> erreur avec numero d'erreur
	}
}
