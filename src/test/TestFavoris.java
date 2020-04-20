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
	
		Document update = new Document();
		update.append("$push", new Document("movies", 457335));

			
		coll.updateOne(filter, update); 
		
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
		
		//Recup series and movies
//		ArrayList<Object> series;
//		ArrayList<Object> movies;
//		JSONObject resultat = null;
//		
//		try {
//			series = (ArrayList<Object>) res.get("series");
//			movies = (ArrayList<Object>) res.get("movies");
//			ArrayList<Integer> ids = new ArrayList<Integer>();
//			resultat = new JSONObject();
//			//AJout des series sous le nom de data
//
//			
//			//Ajout des movies sous le nom de movie
//			if(movies.size() == 0) {
//				resultat.append("movie", new ArrayList<>());
//			}else {
//				ids.clear();
//				for(Object va : movies) {
//					ids.add((Integer) va);
//				}
//				
//				JSONObject resultat2 = ApiTools.getIDs(ids, "movie");
//				resultat.append("movie", resultat2);
//				System.out.println(resultat);
//			}
//			
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		
		System.out.println("ok");
	}

}
