package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.Database;

public class CheckTools {
	
	
	public static boolean checkUser(String login) {

		try {
			
			//Class.forName("com.mysql.jdbc.Driver"); 
			Connection c = Database.getMySQLConnection();
			String query="SELECT login FROM user WHERE login='"+login+"'";
			Statement st = c.createStatement();
			ResultSet res= st.executeQuery(query);
			
			st.close();
			c.close();
			
			if(res.next())
				return true;
			

		} catch ( SQLException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION");
		}
		
		return false;
	}
}
