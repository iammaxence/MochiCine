package services;

import org.json.JSONObject;

import tools.Auth;
import tools.CheckTools;
import tools.ErrorJSON;

public class Authentification {
	
	public static JSONObject login(String login,String mdp) {
		if (login.equals("") || mdp.equals(""))
			return ErrorJSON.serviceRefused("Login: Argument Null", -1);
		
		if(CheckTools.checkPasswd(login, mdp)) 
			return Auth.login(login,mdp);
		else
			return ErrorJSON.serviceRefused("Login : "+login+" is not register", -2); 
	}
	
	public static JSONObject logout(String login) {
		if (login.equals("") )
			return ErrorJSON.serviceRefused("Logout: Argument Null", -1);
		
		if(CheckTools.checkUser(login)) 
			return Auth.logout(login);
			
		return ErrorJSON.serviceRefused("Logout : "+login+" is not register", -2); 
	}
}
