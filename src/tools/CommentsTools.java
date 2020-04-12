package tools;

import java.util.ArrayList;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
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
	
		Document commentaire = new Document("login", login); 
		
		//commentaire: {id:0,message:"hola"}
		//id generator (note) : https://alexmarquardt.com/2017/01/30/how-to-generate-unique-identifiers-for-use-with-mongodb/
		Document other =new Document("id",0); 
		other.append("message", comment);
		commentaire.append("commentaire" , other);
		
		Document update = new Document("$push", new Document("comments", commentaire ));
		coll.updateOne(filter, update); 
		
		Database.MongoClose();
		
		return ErrorJSON.serviceAccepted();
	}
	
	public static JSONObject deleteComment(String titre, String login, int idCom) {   
	        MongoDatabase c = Database.getMongoConnection();
	        MongoCollection <Document> coll = c.getCollection("Commentaires");
	        
			Document filter = new Document("_id", titre);
			Document other =new Document("id",0); 
			Document commentaire = new Document("login", login); 
			commentaire.append("commentaire" , other);
	        Document delete = new Document("comments", commentaire);
	        Document update = new Document("$pull",  delete);
	        
			System.out.println(update.toString());
			coll.updateOne(filter, update); 

			return ErrorJSON.serviceAccepted();
        
    }
	
	//A modifier : Si idcom =nul -> on renvoie tous les commentaires d'un user. Si login=null et idcom =null -> on renvoie tous les commentaires d'un titre
	public static JSONObject getComment(String titre, String login) {   // Recupère tous les commentaires d'un titre pour un utilisateur donnée
        MongoDatabase c = Database.getMongoConnection();
        MongoCollection <Document> coll = c.getCollection("Commentaires");
        
		Document filter = new Document("_id", titre);
        
        MongoCursor<Document> list=coll.find(filter).iterator();
        
        JSONObject res=new JSONObject();
        JSONArray listeMessages=new JSONArray();
        
        try {
			res.put("login", login);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //Je récupère les messages de l'utilisateur
        while(list.hasNext()) {
        	Document doc=list.next();
        	//System.out.println(doc); //Pour le debug
        	
        	ArrayList<Document> comments= (ArrayList<Document>) doc.get("comments"); //Tous les commentaires 
        	for(Document j : comments) { //Pour chaque users
        		if(j.get("login").equals(login)) { //Si c'est le user==login
	        		Document mess=(Document) j.get("commentaire");
	        		listeMessages.put(mess.get("message"));
        		}
        	}
        	
        }
        try {
			res.put("messages", listeMessages);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(res);
		return res;
    
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

	// A changer
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
