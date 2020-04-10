package test;

import tools.CommentsTools;
import tools.FavorisTools;

public class TestMongo {
	public static void main (String[] args) {
		//FavorisTools.createFavoris("bob");
		//FavorisTools.deleteFavoris("bob", "1111111");
		CommentsTools.hasTitre("alice au pays des merveilles");
		CommentsTools.addComment("alice au pays des merveilles", "bob", "j'adore!");
	}
}
