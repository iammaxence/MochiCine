package tools;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
		
		Database.MongoClose();
	}

	public static JSONObject addFavoris(String login, String titre) {
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Favoris");
		
		Document filter = new Document();
		filter.append("_id", login);
	
		Document update = new Document();
		update.append("$push", new Document("favoris", titre));
		coll.updateOne(filter, update); 
		
		Database.MongoClose();
		
		return ErrorJSON.serviceAccepted();
	}
	
	
	public static JSONObject deleteFavoris(String login, String titre) {   
		try {
	        MongoDatabase c = Database.getMongoConnection();
	        MongoCollection <Document> coll = c.getCollection("Favoris");
	        
	        Document filter = new Document("_id", login);
	        Document delete = new Document("favoris", titre);
	        Document update = new Document("$pull",  delete);
	        
	        coll.updateOne(filter,update);
	        
	        MongoCursor<Document> cursor = coll.find(filter).iterator();
	         
	        if(cursor.hasNext()) 
	            return ErrorJSON.serviceAccepted();
	        return ErrorJSON.serviceRefused("deleteComment: all the message has been deleted", 2000);
		}finally {
			 Database.MongoClose();
		}
        
    }
}
