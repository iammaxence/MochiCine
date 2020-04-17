package tools;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import database.Database;

public class Auth {
	
	public static JSONObject logout(String login) {
		try {
			
			//Set key_log à 0 pour une deconnexion et 1 pour une connexion
			Connection c = Database.getMySQLConnection();
			String query="UPDATE user SET key_log = 0 WHERE login='"+login+"'";
			Statement st = c.createStatement();
			int res= st.executeUpdate(query);
				
			st.close();
			c.close();
			
			if(res == 1 )
				return ErrorJSON.serviceAccepted();
			

		} catch ( SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("Logout : error sql", -3);
		}
		return ErrorJSON.serviceRefused("Logout : non trouver", -4);
	}
	
	public static JSONObject login(String login, String mdp) {
		try {
			
			//Set key_log à 0 pour une deconnexion et 1 pour une connexion
			Connection c = Database.getMySQLConnection();
			String query="UPDATE user SET key_log = 1 WHERE login='"+login+"'";
			Statement st = c.createStatement();
			int res= st.executeUpdate(query);
				
			st.close();
			c.close();
			
			if(res == 1)
				return ErrorJSON.serviceAccepted();
			

		} catch ( SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("Login : exception sql", -3);
		}
		return ErrorJSON.serviceRefused("Login : non trouver", -4);
	}
	
	
	
}
