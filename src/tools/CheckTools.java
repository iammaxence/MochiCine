package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.Database;

public class CheckTools {
	
	
	public static boolean checkUser(String login) {
		boolean result = false;
		try {
			
			//Class.forName("com.mysql.jdbc.Driver"); 
			Connection c = Database.getMySQLConnection();
			String query="SELECT login FROM user WHERE login='"+login+"'";
			Statement st = c.createStatement();
			ResultSet res= st.executeQuery(query);
			
			if(res.next())
				result=true;
			
			st.close();
			c.close();
			

		} catch ( SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
