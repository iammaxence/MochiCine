package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.Database;

public class TestRegister {

	public static void main(String[] args) {
		
		
		
		String login = "max";
		String mdp = "bg2";
		
		try {
			//Class.forName("com.mysql.jdbc.Driver"); 
			Connection c = Database.getMySQLConnection();
			String query="INSERT INTO user values('"+login+"','"+mdp+"')";
			Statement st = c.createStatement();
			int rs= st.executeUpdate(query);
			
			st.close();
			c.close();
			
			if(rs==0)
				System.out.println("ERROR");
			else
				System.out.println("OK");
			
		} catch ( SQLException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION");
		}
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
	}

}
