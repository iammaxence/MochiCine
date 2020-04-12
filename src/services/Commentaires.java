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
		if(user.equals("") || titre.equals(""))
			return ErrorJSON.serviceRefused("deleteComment : Argument Null", -1);
		
		if(!CheckTools.checkUser(user)) {
			return ErrorJSON.serviceRefused("deleteComment: Pseudo "+user+" do not exist", -2);
		}
		
		CommentsTools.hasTitre(titre);
		return CommentsTools.deleteComment(titre, user, idCom);
		
	}
	
	public static JSONObject listeComment(String titre,String login) {
		
		if(titre.equals("")) {
			return ErrorJSON.serviceRefused("listComment : Argument Null", -1);
		}
		
		return CommentsTools.getComment(titre, login);
		
	}
	
}
