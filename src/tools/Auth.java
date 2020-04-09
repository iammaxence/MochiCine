package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.Database;

public class Auth {
	
	public static void logout(String login) {
		try {
			
			//Set keylog à 0 pour une deconnexion et 1 pour une connexion
			Connection c = Database.getMySQLConnection();
			String query="UPDATE user SET keylog = 0 WHERE login='"+login+"'";
			Statement st = c.createStatement();
			int res= st.executeUpdate(query);
				
			st.close();
			c.close();

			if(res == 0 )
				System.out.println("Error");
			else
				System.out.println("ok");

		} catch ( SQLException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION");
		}
	}
}
