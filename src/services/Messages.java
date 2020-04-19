package services;

import org.json.JSONObject;

import tools.CheckTools;
import tools.ErrorJSON;
import tools.MessagesTools;

public class Messages {

	public static JSONObject addMessage(String login, String text, String titre) {
		if (login.equals("") || text.equals("") || titre.equals("")) {
			return ErrorJSON.serviceRefused("AddMessage: Argument null", -1);
		}
		
		if(!CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("AddMessage: Pseudo "+login+" do not exist", -2);
		}	
		
		return MessagesTools.addMessage(login, text, titre);
	}

	public static JSONObject deleteMessage(String id_message) {
		if (id_message.equals(""))
			return ErrorJSON.serviceRefused("DeleteMessage: Argument null", -1);
		if(MessagesTools.messageExist(id_message))
			return MessagesTools.deleteMessage(id_message);
		return ErrorJSON.serviceRefused("DeleteMessage: wronf id_message", -2);
	}

	public static JSONObject getlistMessages(String titre) {
		if (titre.equals(""))
			return ErrorJSON.serviceRefused("ListMessages: Argument null", -1);
		
		return MessagesTools.getMessages(titre);
	}

}
