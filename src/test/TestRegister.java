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
//		
//		try {
//			//Class.forName("com.mysql.jdbc.Driver"); 
//			Connection c = Database.getMySQLConnection();
//			String query="INSERT INTO user values('"+login+"','"+mdp+"','"+0+"')";
//			Statement st = c.createStatement();
//			int rs= st.executeUpdate(query);
//			
//			st.close();
//			c.close();
//			
//			if(rs==0)
//				System.out.println("ERROR");
//			else
//				System.out.println("OK");
//			
//		} catch ( SQLException e) {
//			e.printStackTrace();
//			System.out.println("EXCEPTION");
//		}
//		boolean res = false;
//		try {		
//			Connection c = Database.getMySQLConnection();
//			String query="select * from user where login='bob'";
//			Statement st = c.createStatement();
//			ResultSet rs= st.executeQuery(query);
//			if(rs.next())
//				res=true;
//		
//			rs.close();
//			st.close();
//			c.close();
//			
//		} catch ( SQLException e) {
//			e.printStackTrace();
//		}
//		System.out.println(res);
		
		
		
//		JSONObject reponse = services.Register.createUser("bob", "mdp");
//		System.out.println(reponse);
