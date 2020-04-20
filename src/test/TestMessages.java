package test;

import static org.junit.Assert.assertTrue;


import org.json.JSONObject;
import org.junit.Test;


import tools.CommentsTools;
import tools.MessagesTools;

public class TestMessages {

	@Test
	public void testMessage() {
		System.out.println("teste Message");
		JSONObject res;
		res=MessagesTools.addMessage("bob", "ce film est trop bien", "La haut");
		res=MessagesTools.addMessage("alice", "super film", "La haut");
		res=MessagesTools.addMessage("alice", "j'adore", "jumanji");
		assertTrue(MessagesTools.messageExist("0"));
		res = MessagesTools.getMessages("La haut");
		System.out.println("1: "+res);
		MessagesTools.deleteMessage("1");
		res = MessagesTools.getMessages("La haut");
		System.out.println("2: "+res);
		
		
	}
	
	@Test
	public void testCommenteire() {
		System.out.println("teste Commentaire");

		JSONObject res;
		//TestCommentaires	
		CommentsTools.addComment("0", "alice", "totalement d'accord!");
		CommentsTools.addComment("2", "bob", "moi aussi!");
		CommentsTools.addComment("2", "bob", "moi aussi!");
		
		assertTrue(CommentsTools.commentExist("0","0"));
		res = MessagesTools.getMessages("La haut");
		System.out.println("1: "+res);
		
		CommentsTools.deleteComment("2", "1");
		res = MessagesTools.getMessages("La haut");
		System.out.println("2: "+res);
	}
}
/*
	public static void main(String[] args) {
		MessagesTools.addMessage("bob", "ce film est trop bien", "La haut");
		MessagesTools.addMessage("alice", "super film", "La haut");
		MessagesTools.addMessage("alice", "j'adore", "jumanji");
	
		System.out.println("Message d'id 0 existe? "+MessagesTools.messageExist("0"));
		
		JSONObject res = MessagesTools.getMessages("La haut");
		System.out.println("1: "+res);
		
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

}*/
