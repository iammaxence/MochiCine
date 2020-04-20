package services;

import org.json.JSONObject;

import tools.CheckTools;
import tools.CommentsTools;
import tools.ErrorJSON;

/**
 * Service assurant l'ajout et la supression de commentaire
 * @author 
 *
 */
public class Commentaires {
	
	/**
	 * Appelle l'outil CommentsTools pour realiser l'ajout d'un commentaire par un utilisateur.
	 * @param user
	 * @param id_message
	 * @param commentaire
	 * @return
	 */
	public static JSONObject addComment(String user, String id_message,String commentaire) { 
		if(user.equals("") || id_message.equals("") || commentaire.equals(""))
			return ErrorJSON.serviceRefused("AddComment : Argument Null", -1);
		
		if(!CheckTools.checkUser(user)) {
			return ErrorJSON.serviceRefused("AddComment: Pseudo "+user+" do not exist", -2);
		}	

		return CommentsTools.addComment(id_message, user, commentaire);
	}
	
	/**
	 * Appelle l'outil CommentsTools pour realiser la supression d'un commentaire par un utilisateur.
	 * @param idCom
	 * @param id_message
	 * @return
	 */
	public static JSONObject deleteComment(String idCom, String id_message) { 
		if(idCom.equals("") || id_message.equals(""))
			return ErrorJSON.serviceRefused("deleteComment : Argument Null", -1);
		if(CommentsTools.commentExist(id_message, idCom))
			return CommentsTools.deleteComment(id_message, idCom);
		return ErrorJSON.serviceRefused("DeleteComment: not exist", -2);
		
	}
	
	
}
