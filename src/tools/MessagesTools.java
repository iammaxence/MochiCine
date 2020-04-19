package tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import database.Database;

public class MessagesTools {
	
	
	/**
	 * Retourne la date courante et l'heure
	 * @return "yyyy/MM/dd HH:mm:ss" (String)
	 */
	public static String getCurrentDate() {
		GregorianCalendar calendar = new GregorianCalendar();
		Date d = calendar.getTime();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String currentTime = sdf.format(d);
		return currentTime;
	}
	

	/**
	 * Ajoute un document "message" dans mongobase (login,text,date)
	 * @param login, text: String
	 * @param id: int
	 * @return JSONObject accepted
	 */
	public static JSONObject addMessage(String login, String text, String titre) {
		String d = getCurrentDate();
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Commentaires");
		Document q = new Document();
		q.append("_id", getCounterMessage());
		q.append("login", login);
		q.append("titre", titre);
		q.append("text", text);
		q.append("date", d);
		q.append("comments",new ArrayList<Document>());
		coll.insertOne(q);
		
		JSONObject res= new JSONObject();
		try {
			res.put("message", q);
		} catch (JSONException e) {
			e.printStackTrace();
			return ErrorJSON.serviceRefused("addMessage: erreur", 100);
		}
		return res;
	}

	/**
	 * Verifie si le message existe
	 * @param id_message: String (transform into ObjectId)
	 * @return true or false
	 */
	public static boolean messageExist(String id_message) {
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Commentaires");
		Document q = new Document("_id", id_message);
		MongoCursor<Document> cursor = coll.find(q).iterator();
		if(cursor.hasNext()) {
			System.out.println(cursor.next());
			return true;
		}
		return false;
	}

	/**
	 * Permet de supprimer un message
	 * @param id_message: String
	 * @return JSONObject accepted
	 */
	public static JSONObject deleteMessage(String id_message) {
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Commentaires");
		Document d = new Document("_id", id_message);
		coll.deleteOne(d);
		return ErrorJSON.serviceAccepted();
	}
	
	/**
	 * Compteur pour les id de message
	 * @return int
	 */
	private static String getCounterMessage() {
		boolean isCreate=false;
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Counter");
		
		//Verifie si le document existe dans la base de donnee:
		MongoCursor<Document> cursor = coll.find(new Document("_id","message")).iterator();
		if(cursor.hasNext())
			isCreate=true;
		
		Document d= new Document("_id","message");
		if(!isCreate){
			d.append("cpt", 0);
			coll.insertOne(d);
		}
		Document res = coll.findOneAndUpdate(d, new Document("$inc",new Document("cpt",1)));
		return Integer.toString(res.getInteger("cpt", -1));
	}
	
	public static JSONObject getMessages(String titre) {
		List<Document> messages = new ArrayList<Document>();
		JSONObject resultat = new JSONObject();
		
		MongoDatabase c = Database.getMongoConnection();
		MongoCollection <Document> coll = c.getCollection("Commentaires");
		Document q = new Document("titre", titre);
		MongoCursor<Document> cursor = coll.find(q).iterator();
		
		while(cursor.hasNext()) {
			messages.add(cursor.next());
		}
		
		try {
			resultat.append("data", messages);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return resultat;
	}
}
