package tools;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import database.Database;

public class FavorisTools {
	
	

	public static void createFavoris(String login) {
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Favoris");
		
		Document q = new Document();
		q.append("_id", login);
		q.append("series", new ArrayList<Integer>());
		q.append("movies", new ArrayList<Integer>());
		coll.insertOne(q);
		
		Database.MongoClose();
	}

	public static JSONObject addFavoris(String login, Integer id_favoris, boolean isSerie) {
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Favoris");
		
		Document filter = new Document();
		filter.append("_id", login);
	
		Document update = new Document();
		if(isSerie) {
			update.append("$push", new Document("series", id_favoris));
		}else {
			update.append("$push", new Document("movies", id_favoris));
		}
			
		coll.updateOne(filter, update); 
		
		Database.MongoClose();
		
		return ErrorJSON.serviceAccepted();
	}
	
	public static JSONObject getFavoris(String login) {
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Favoris");
		
		Document filter = new Document("_id", login);
		
        MongoCursor<Document> cursor = coll.find(filter).iterator();
        JSONObject res= new JSONObject();
        
        while(cursor.hasNext()) {
        	Document o=cursor.next();
        	try {
				res.put("series", o.get("series"));
				res.put("movies", o.get("movies"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        System.out.println(res);
		
		Database.MongoClose();
		
		return res;
	}
	
	
	public static JSONObject deleteFavoris(String login, Integer id_favoris, boolean isSerie) {   
		try {
	        MongoDatabase c = Database.getMongoConnection();
	        MongoCollection <Document> coll = c.getCollection("Favoris");
	        
	        Document filter = new Document("_id", login);
	        Document delete;
	        //Choix entre series ou movies
	        if(isSerie) {
	        	delete = new Document("series", id_favoris);
			}else {
				delete = new Document("movies", id_favoris);
			}
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
	
	public static boolean isFavoris(String login, Integer id_favoris, boolean isSerie) {
		boolean result = false;
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Favoris");
		
		Document filter = new Document("_id", login);
        MongoCursor<Document> cursor = coll.find(filter).iterator();        
        if(cursor.hasNext()) {
        	Document o = cursor.next();
        	if(!isSerie) {
    			ArrayList<Object> list = (ArrayList<Object>) o.get("movies");
    			for(Object obj : list) {
    				if(obj.toString().equals(id_favoris)){
    					result=true;
    				}
    			}
        	}else {
    			ArrayList<Object> list = (ArrayList<Object>) o.get("series");
    			for(Object obj : list) {
    				if(obj.toString().equals(id_favoris)){
    					result=true;
    				}
    			}
        	}
        }
        Database.MongoClose();
        return result;
	}
}
