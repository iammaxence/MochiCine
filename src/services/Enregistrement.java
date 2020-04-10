package services;

import org.json.JSONObject;

import tools.ErrorJSON;
import tools.CheckTools;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.Database;


public class Enregistrement {
	
	
	public static JSONObject createUser(String login, String mdp) {
		if(login.equals("") || mdp.equals("")) {
			return ErrorJSON.serviceRefused("Register: Argument Null", -1);
		}
		
		if(CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("Register: Pseudo "+login+" already existe", -2);
		}
		
		return insertUser(login, mdp);
	}
	
	private static JSONObject insertUser(String login, String mdp) {
		try {
			Connection c = Database.getMySQLConnection();
			String query="INSERT INTO user values('"+login+"','"+mdp+"','"+1+"')";
			Statement st = c.createStatement();
			int rs= st.executeUpdate(query);
			
			st.close();
			c.close();
			
			if(rs==0)
				return ErrorJSON.serviceRefused("Failed Register", -3);
			return ErrorJSON.serviceAccepted();
			
		} catch ( SQLException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("Failed Register", 4);
		}
	}
}
