package test;


import tools.FavorisTools;

public class TestMongo {
	public static void main (String[] args) {
		FavorisTools.createFavoris("bob");
		FavorisTools.getFavoris("bob");
	}
}
