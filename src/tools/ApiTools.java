package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiTools {
	
	
	public static JSONObject weeklySeries() throws IOException {
		
		//On effectue un appel à l'api externe

		URL url = new URL("https://api.themoviedb.org/3/tv/on_the_air?api_key=a3be1be132d237a0716cc27bdae1b2f0&language=en-US&page=1"); //On récupère seulement une partie pour l'affichage
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");

		//On récupère la réponse, si le status est valide, on continue
		int status = con.getResponseCode();

		//On récupère la réponse envoyé par l'api
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		JSONObject listeoftheweek= new JSONObject();
		while ((inputLine = in.readLine()) != null) {
			try {
				listeoftheweek=new JSONObject(inputLine);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		in.close();

		// On cast notre réponse String -> JSONArray (On obtiens notre liste de JSONObject)
		JSONArray myarray = null;
		try {
			myarray = (JSONArray) listeoftheweek.get("results");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Pour chaque série, on récupère son id (L'api nous fournis plus d'information si on récupère la fiche technique de chaque series)
		ArrayList<Integer> ids=new ArrayList<Integer>();
		for(int i=0;i<myarray.length();i++) {
			try {

				JSONObject j=(JSONObject) myarray.get(i);
				ids.add((Integer)j.get("id"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// 2nd requête pour récuperer les informations de chaques series

		return moreInfosSeries(ids);
	}

	private static JSONObject moreInfosSeries(ArrayList<Integer> ids) throws IOException {


		//Pour chaque id on récupère les informations qui nous intéressent
		JSONArray listeseries=new JSONArray();
		for(Integer id : ids) {

			//On effectue un appel à l'api externe

			URL url = new URL("https://api.themoviedb.org/3/tv/"+id+"?api_key=a3be1be132d237a0716cc27bdae1b2f0&language=en-US"); //On récupère seulement une partie pour l'affichage
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");

			//On récupère la réponse, si le status est valide, on continue
			int status = con.getResponseCode();

			//On récupère la réponse envoyé par l'api
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			JSONObject laserie= new JSONObject();
			while ((inputLine = in.readLine()) != null) {
				try {
					laserie=new JSONObject(inputLine); //On récupère le json

					//Parsing de la date du dernier épisode sortie cette semaine
					String s = laserie.getString("last_air_date");
					//System.out.println("--- "+s+" ---");
					String[] sep = s.split("-");
					Integer[] date= {Integer.parseInt(sep[0]),Integer.parseInt(sep[1]),Integer.parseInt(sep[2])};
					
					//Parsing de la date du dernier épisode qui va sortir cette semaine
					//A faire

					if(isDayOfTheWeek(LocalDate.of(date[0], date[1], date[2]))) //Check si la date de sortie est compris dans la semaine
						listeseries.put(laserie);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			in.close();
		}


//		for(int i=0;i<listeseries.length();i++) {
//			try {
//
//				JSONObject j=(JSONObject) listeseries.get(i);
//				System.out.println(j.get("name"));
//
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}

		try {
			return new JSONObject().put("data", listeseries);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ErrorJSON.serviceRefused("Problème requete ApiTools", -1);
	}
	
	public static JSONObject filmsOnAir() throws IOException {
		
		JSONObject retour=new JSONObject();
		JSONArray listefilms=new JSONArray();
		
		for(int i=1;i<4;i++) { // On récupère les 3 première pages (On peux récupérer toutes les pages avec la valeur renvoyer par l'api "total_pages"
			
			//On effectue un appel à l'api externe
			URL url = new URL("https://api.themoviedb.org/3/movie/upcoming?api_key=a3be1be132d237a0716cc27bdae1b2f0&language=en-US&page="+i); //On récupère seulement une partie pour l'affichage
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");

			//On récupère la réponse, si le status est valide, on continue
			int status = con.getResponseCode();

			//On récupère la réponse envoyé par l'api
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			JSONObject listeofthemonth= new JSONObject();
			while ((inputLine = in.readLine()) != null) {
				try {
					listeofthemonth=new JSONObject(inputLine); //Recupère tout le champ results
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			in.close();

			// On cast notre réponse String -> JSONArray (On obtiens notre liste de JSONObject)
			JSONArray myarray = null;
			try {
				myarray = (JSONArray) listeofthemonth.get("results");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Pour chaque série, on récupère son id (L'api nous fournis plus d'information si on récupère la fiche technique de chaque films)
			
			for(int k=0;k<myarray.length();k++) {
				try {

					JSONObject lefilm=(JSONObject) myarray.get(k);
					
					//Parsing de la date du dernier épisode sortie cette semaine
					String s = lefilm.getString("release_date");
					//System.out.println("--- "+s+" ---");
					String[] sep = s.split("-");
					Integer[] date= {Integer.parseInt(sep[0]),Integer.parseInt(sep[1]),Integer.parseInt(sep[2])};
					
					if (isDayUntilToday(LocalDate.of(date[0], date[1], date[2]))) { //Si la date est compris entre le début de l'année et aujourd'hui inclus
						listefilms.put(lefilm);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		try {
			retour.put("data", listefilms);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retour;
	}
	
	public static JSONObject recherche(String key,String keyword) throws IOException {
		JSONObject retour= new JSONObject();
		JSONArray myarray=new JSONArray();
		
		//On effectue un appel à l'api externe
		URL url = new URL("https://api.themoviedb.org/3/search/multi?api_key="+key+"&language=fr-FR&query="+keyword+"&page=1&include_adult=false"); //On récupère seulement une partie pour l'affichage
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");

		//On récupère la réponse, si le status est valide (200), on continue
		int status = con.getResponseCode();

		//On récupère la réponse envoyé par l'api
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		JSONObject listedelarecherche= new JSONObject();
		while ((inputLine = in.readLine()) != null) {
			try {
				listedelarecherche=new JSONObject(inputLine); //Recupère tout le champ results
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		in.close();
		// On cast notre réponse String -> JSONArray 
		
		try {
			myarray=(JSONArray) listedelarecherche.get("results");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONArray newArray=new JSONArray();
		for(int i=0;i<myarray.length();i++) {
			try {
				JSONObject jo=(JSONObject)myarray.get(i); //String -> JSONObject
				newArray.put(jo);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		try {
			retour.put("data",newArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retour;
	}

	private static boolean isDayOfTheWeek(LocalDate d) { //https://stackoverflow.com/questions/22890644/get-current-week-start-and-end-date-in-java-monday-to-sunday/22890763
		LocalDate today = LocalDate.now();

		// Go backward to get Monday
		LocalDate monday = today;
		while (monday.getDayOfWeek() != DayOfWeek.MONDAY)
		{
			monday = monday.minusDays(1);
		}

		// Go forward to get Sunday
		LocalDate sunday = today;
		while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY)
		{
			sunday = sunday.plusDays(1);
		}

		//	    System.out.println("Today: " + today.toString());
		//	    System.out.println("Monday of the Week: " + monday);
		//	    System.out.println("Sunday of the Week: " + sunday);

		if(d.compareTo(monday)>=0 && d.compareTo(sunday)<=0)
			return true;
		return false;
	}
	
	private static boolean isDayUntilToday(LocalDate d) { //Verifie si la date est comprise entre le début de l'année et aujourd'hui
		LocalDate today = LocalDate.now(); //date d'aujourd'hui
		LocalDate begin = LocalDate.of(today.getYear(), 01, 01); //debut de l'année
		
		
		if(d.compareTo(begin)>=0 && d.compareTo(today)<=0)
			return true;
		return false;
	}
}
