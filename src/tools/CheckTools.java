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
			Connection c = Database.getMySQLConnection();
			String query="SELECT login FROM user WHERE login='"+login+"'";
			Statement st = c.createStatement();
			ResultSet res= st.executeQuery(query);
			
			if(res.next())
				result=true;
			
			res.close();
			st.close();
			c.close();
			

		} catch ( SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean checkPasswd(String login, String psswd) {
		boolean res = false;
        try {
              
            Connection c = Database.getMySQLConnection();
            String query="SELECT * FROM user WHERE login='"+login+"' AND password='"+psswd+"'";
            
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);

            if(rs.next()) 
                res = true;
                 
            rs.close();
            st.close();
            c.close();
            
        }catch (SQLException e) {
        	e.printStackTrace();
            return res;
        }
        
        return res;
    }
}
