package services;

import org.json.JSONObject;

import tools.CheckTools;
import tools.CommentsTools;
import tools.ErrorJSON;

public class Commentaires {
	
	public static JSONObject addComment(String user, String id_message,String commentaire) { 
		if(user.equals("") || id_message.equals("") || commentaire.equals(""))
			return ErrorJSON.serviceRefused("AddComment : Argument Null", -1);
		
		if(!CheckTools.checkUser(user)) {
			return ErrorJSON.serviceRefused("AddComment: Pseudo "+user+" do not exist", -2);
		}	

		return CommentsTools.addComment(id_message, user, commentaire);
	}
	
	public static JSONObject deleteComment(String idCom, String id_message) { 
		if(idCom.equals("") || id_message.equals(""))
			return ErrorJSON.serviceRefused("deleteComment : Argument Null", -1);
		if(CommentsTools.commentExist(id_message, idCom))
			return CommentsTools.deleteComment(id_message, idCom);
		return ErrorJSON.serviceRefused("DeleteComment: not exist", -2);
		
	}
	
	
}
