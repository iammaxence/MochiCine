package services;

import org.json.JSONObject;

import tools.ErrorJSON;
import tools.CheckTools;



public class Enregistrement {
	
	
	public static JSONObject createUser(String login, String mdp) {
		if(login.equals("") || mdp.equals("")) {
			return ErrorJSON.serviceRefused("Register: Argument Null", -1);
		}
		
		if(CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("Register: Pseudo "+login+" already existe", -2);
		}
		
		return CheckTools.insertUser(login, mdp);
		
	}
}
