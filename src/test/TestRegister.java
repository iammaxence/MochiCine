package test;


import services.Enregistrement;
import tools.CheckTools;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.Test;

public class TestRegister {

	
	
	@Test
	public void testCreateUser() {
		System.out.println("teste Create User");
		String login = "franck";
		String mdp = "mdp";
		if(CheckTools.checkUser(login)) {
			assertTrue(true);
		}else {
			JSONObject res = Enregistrement.createUser(login, mdp);
			assertEquals("{}", res.toString());
		}
		
		
	}
}

