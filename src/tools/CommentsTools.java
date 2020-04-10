package tools;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import database.Database;

public class CommentsTools {
	
	public static JSONObject addComment(String titre, String login, String comment) {
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Commentaires");

		Document filter = new Document();
		filter.append("_id", titre);
	
		Document commentaire = new Document("_id", login);
		commentaire.append("commentaire" , comment);
		Document update = new Document("$push", new Document("comments", commentaire ));
		coll.updateOne(filter, update); 
		
		Database.MongoClose();
		
		return ErrorJSON.serviceAccepted();
	}
	
	/**
	 * Verifie si le titre existe dans la base de donnée sinon le créer
	 * @param titre
	 */
	public static void hasTitre(String titre) {
		boolean here = false;
		try {
			MongoDatabase c = Database.getMongoConnection();
			MongoCollection <Document> coll = c.getCollection("Commentaires");
			Document query = new Document("_id",titre);
			
			MongoCursor<Document> cursor = coll.find(query).iterator();

			if(cursor.hasNext())
				here = true;
			
			if(!here) {
				Document n = new Document("_id", titre);
				n.append("comments", new ArrayList<Document>());
				coll.insertOne(n);
			}
			
		}finally {
			Database.MongoClose();
		}
	}

	
	public static boolean commentExist(String login, String titre) {
		try {
			MongoDatabase c = Database.getMongoConnection();
			MongoCollection <Document> coll = c.getCollection("Commentaires");
			Document query = new Document("_id",titre);
			query.append("comments._id", login);
			
			MongoCursor<Document> cursor = coll.find(query).iterator();

			if(cursor.hasNext())
				return true;
			return false;
		}finally {
			Database.MongoClose();
		}

	}
}
