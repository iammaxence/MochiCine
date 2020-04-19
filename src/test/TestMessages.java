package test;

import org.json.JSONObject;

import tools.CommentsTools;
import tools.MessagesTools;

public class TestMessages {

	public static void main(String[] args) {
//		MessagesTools.addMessage("bob", "ce film est trop bien", "La haut");
//		MessagesTools.addMessage("alice", "super film", "La haut");
//		MessagesTools.addMessage("alice", "j'adore", "jumanji");
//		
//		System.out.println("Message d'id 0 existe? "+MessagesTools.messageExist("0"));
//		
//		JSONObject res = MessagesTools.getMessages("La haut");
//		System.out.println("1: "+res);
		
//		MessagesTools.deleteMessage("1");
//		res = MessagesTools.getMessages("La haut");
//		System.out.println("2: "+res);
		
		//TestCommentaires	
//		CommentsTools.addComment("0", "alice", "totalement d'accord!");
//		CommentsTools.addComment("2", "bob", "moi aussi!");
//		CommentsTools.addComment("2", "bob", "moi aussi!");
		
		System.out.println("Commentaire d'id 0 existe? "+CommentsTools.commentExist("0","0"));
		JSONObject res = MessagesTools.getMessages("La haut");
		System.out.println("1: "+res);
		
		CommentsTools.deleteComment("2", "1");
		res = MessagesTools.getMessages("La haut");
		System.out.println("2: "+res);
		
		
		
		
		
	}

}
