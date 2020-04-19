package test;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import database.Database;
import tools.ApiTools;
import tools.ErrorJSON;

public class TestFavoris {

	public static void main(String[] args) {
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Favoris");
		
		//Creation de la liste des favoris pour Tester
//		Document q = new Document();
//		q.append("_id", "bob");
//		q.append("series", new ArrayList<Integer>());
//		q.append("movies", new ArrayList<Integer>());
//		coll.insertOne(q);
		
		
		
		Document filter = new Document();
		filter.append("_id", "bob");
		
		//Ajout d'un id serie
//	
//		Document update = new Document();
//		update.append("$push", new Document("series", 63247));
//
//			
//		coll.updateOne(filter, update); 
		
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
//		
//		ArrayList<Object> series;
//		ArrayList<Object> movies;
//		JSONObject resultat = null;
//		
//		try {
//			series = (ArrayList<Object>) res.get("series");
//			movies = (ArrayList<Object>) res.get("movies");
//			
//			ArrayList<Integer> ids = new ArrayList<Integer>();
//			for(Object va : series) {
//				ids.add((Integer) va);
//			}
//			resultat = ApiTools.getIDs(ids, "tv");
//			 
//			ids.clear();
//			for(Object va : movies) {
//				ids.add((Integer) va);
//			}
//			JSONObject resultat2 = ApiTools.getIDs(ids, "movie");
//			
//			resultat.append("movie", resultat2.get("data"));
//			System.out.println(resultat);
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		
		System.out.println("ok");
	}

}
