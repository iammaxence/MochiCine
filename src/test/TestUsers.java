package test;

import org.json.JSONObject;

import services.Authentification;
import tools.Auth;

public class TestUsers {
	
	public static void main(String[] args) {
		Auth.logout("bob");
		//JSONObject res = Authentification.login("bob", "mdp");
		//System.out.println(res.toString());
	}
}
