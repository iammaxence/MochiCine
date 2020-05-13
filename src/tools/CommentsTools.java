package tools;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import database.Database;

public class CommentsTools {
	
	public static JSONObject addComment(String id_message, String login, String comment) {
		String d = MessagesTools.getCurrentDate();
		
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Commentaires");

		Document filter = new Document();
		filter.append("_id", id_message);
	
		
		Document commentaire = new Document("_id", getCounterComment()); 
		commentaire.append("login", login);
		commentaire.append("commentaire" , comment);
		commentaire.append("date", d);
		
		
		Document update = new Document("$push", new Document("comments", commentaire ));
		coll.updateOne(filter, update); 
		
		Database.MongoClose();
		
		JSONObject res= new JSONObject();
		try {
			res.put("comment", commentaire);
		} catch (JSONException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("addComment: erreur", 100);
		}
		return res;
	}
	
	public static JSONObject deleteComment(String id_message, String idCom) {   
	        MongoDatabase c = Database.getMongoConnection();
	        MongoCollection <Document> coll = c.getCollection("Commentaires");
	        
			Document filter = new Document("_id", id_message);
			Document delete = new Document("comments", new Document("_id", idCom)); 
	        Document update = new Document("$pull",  delete);
	        
			coll.updateOne(filter, update); 

			return ErrorJSON.serviceAccepted();
    }
	
	
	/**
	 * Verifie si le commentaire existe
	 * @param id_commentaire
	 * @return true or false
	 */
	public static boolean commentExist(String id_message, String id_commentaire) {
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Commentaires");
		Document query = new Document("comments._id",id_commentaire);
		query.append("_id", id_message);
		MongoCursor<Document> cursor = coll.find(query).iterator();
		if(cursor.hasNext())
			return true;
		return false;
	}
	
	/**
	 * Compteur pour les id de commentaires
	 * @return int
	 */
	private static String getCounterComment() {
		boolean isCreate=false;
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Counter");
		
		//Verifie si le document existe dans la base de donnee:
		MongoCursor<Document> cursor = coll.find(new Document("_id","comment")).iterator();
		if(cursor.hasNext())
			isCreate=true;
		
		Document d= new Document("_id","comment");
		if(!isCreate){
			d.append("cpt", 0);
			coll.insertOne(d);
		}
		Document res = coll.findOneAndUpdate(d, new Document("$inc",new Document("cpt",1)));
		return Integer.toString(res.getInteger("cpt", -1));
	}
}
