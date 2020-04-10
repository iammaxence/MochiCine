package tools;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import database.Database;

public class FavorisTools {
	
	
	private static void getFavoris(String login) {
		
	}

	public static void createFavoris(String login) {
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Favoris");
		Document q = new Document();
		q.append("_id", login);
		q.append("favoris", new ArrayList<String>());
		coll.insertOne(q);
		

	}
}
