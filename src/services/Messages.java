package services;

import org.json.JSONObject;

import tools.CheckTools;
import tools.ErrorJSON;
import tools.MessagesTools;

/**
 * Service assurant l'ajout, la suppression et l'acces aux messages.
 * @author
 *
 */
public class Messages {

	/**
	 * Appelle l'outil MessagesTools pour realiser l'ajout d'un message.
	 * @param login
	 * @param text
	 * @param titre
	 * @return
	 */
	public static JSONObject addMessage(String login, String text, String titre) {
		if (login.equals("") || text.equals("") || titre.equals("")) {
			return ErrorJSON.serviceRefused("AddMessage: Argument null", -1);
		}
		
		if(!CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("AddMessage: Pseudo "+login+" do not exist", -2);
		}	
		
		return MessagesTools.addMessage(login, text, titre);
	}

	/**
	 * Appelle l'outil MessagesTools pour realiser la supression d'un message.
	 * @param id_message
	 * @return
	 */
	public static JSONObject deleteMessage(String id_message) {
		if (id_message.equals(""))
			return ErrorJSON.serviceRefused("DeleteMessage: Argument null", -1);
		if(MessagesTools.messageExist(id_message))
			return MessagesTools.deleteMessage(id_message);
		return ErrorJSON.serviceRefused("DeleteMessage: wronf id_message", -2);
	}

	/**
	 * Appelle l'outil FavorisTools pour liste des messages d'un film ou d'une serie.
	 * @param titre
	 * @return
	 */
	public static JSONObject getlistMessages(String titre) {
		if (titre.equals(""))
			return ErrorJSON.serviceRefused("ListMessages: Argument null", -1);
		
		return MessagesTools.getMessages(titre);
	}

}
