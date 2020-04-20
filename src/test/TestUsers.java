package test;


import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Test;

import services.Authentification;

public class TestUsers {
	
	
	@Test
	public void testLogin() {
		System.out.println("teste Login");
		JSONObject res = Authentification.login("bob", "mdp");
		assertEquals("{}", res.toString());
	}
	
	@Test
	public void testLogout() {
		System.out.println("teste Logout");
		JSONObject res = Authentification.logout("bob");
		assertEquals("{}", res.toString());
	}
}