package services;

import org.json.JSONObject;

import tools.Auth;
import tools.CheckTools;
import tools.ErrorJSON;

public class Authentification {
	
	public static JSONObject login(String login,String mdp) {
		return null;
		
	}
	
	public static JSONObject logout(String login) {
		if (login.equals(""))
			return ErrorJSON.serviceRefused("Logout: Argument Null", -1);
		
		if(CheckTools.checkUser(login)) 
			return Auth.logout(login);
			
		return ErrorJSON.serviceRefused("Logout : "+login+" is not a register", -2); 
	}
}
