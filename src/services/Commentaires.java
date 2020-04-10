package services;

import org.json.JSONObject;

import tools.CheckTools;
import tools.CommentsTools;
import tools.ErrorJSON;

public class Commentaires {
	
	public static JSONObject addComment(String user, String titre,String commentaire) { 
		if(user.equals("") || titre.equals("") || commentaire.equals(""))
			return ErrorJSON.serviceRefused("AddComment : Argument Null", -1);
		
		if(!CheckTools.checkUser(user)) {
			return ErrorJSON.serviceRefused("AddComment: Pseudo "+user+" do not exist", -2);
		}
		
		CommentsTools.hasTitre(titre);
		return CommentsTools.addComment(titre, user, commentaire);
	}
	
	public static JSONObject deleteComment(String user,String titre,int idCom) { 
		return null;
		
	}
	
	public static JSONObject listeComment(String titre) {
		return null;
		
	}
	
}
