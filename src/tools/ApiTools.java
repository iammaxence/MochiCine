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
	
	
	/**
	 * Renvoie la liste des séries de la semaine
	 * 
	 * @return JSONObject
	 * @throws IOException
	 */
	public static JSONObject weeklySeries() throws IOException {
		
		//On effectue un appel à l'API externe
		//On récupère seulement une partie pour l'affichage (Les nouvelles sorties se situes sur la première page, on verra mieux le dynamisme)
		URL url = new URL("https://api.themoviedb.org/3/tv/on_the_air?api_key=a3be1be132d237a0716cc27bdae1b2f0&language=en-US&page=1"); 
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");

		//On récupère la réponse, si le status est valide, on continue
		int status = con.getResponseCode();
		if(status != 200)
			return ErrorJSON.serviceRefused("Can't find tv show", -1);
		
		//On récupère la réponse envoyé par l'API
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		JSONObject listeoftheweek= new JSONObject();
		
		while ((inputLine = in.readLine()) != null) {
			try {
				listeoftheweek=new JSONObject(inputLine);
			} catch (JSONException e) {
				System.out.println("Error: Readline series (ApiTools.java)");
			}

		}
		in.close();

		// La réponse retourné est un String. On cast notre réponse String -> JSONArray (On obtiens un JSONArray contenant des objets)
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
				//On récupère chaque objet et on les cast en JSONObject (Pour les manipuler plus facilement dans le client)
				JSONObject j=(JSONObject) myarray.get(i);
				ids.add((Integer)j.get("id"));

			} catch (JSONException e) {
				System.out.println("Error : cast JSONObject (ApiTools.java)");
			}

		}

		// 2nd requête pour récuperer les informations de chaques series (On peut obtenir la date de sortie du dernier épisode sur la fiche technique de la série uniquement)

		return moreInfosSeries(ids);
	}
	
	/**
	 * Renvoie la liste des séries de la semaine
	 * 
	 * @param ids
	 * @return JSONObject
	 * @throws IOException
	 */

	private static JSONObject moreInfosSeries(ArrayList<Integer> ids) throws IOException {


		//Pour chaque id (represantant une serie), on récupère les informations qui nous intéressent
		JSONArray listeseries=new JSONArray();
		for(Integer id : ids) {

			//On effectue un appel à l'API externe

			URL url = new URL("https://api.themoviedb.org/3/tv/"+id+"?api_key=a3be1be132d237a0716cc27bdae1b2f0&language=en-US");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");

			//On récupère la réponse, si le status est valide, on continue
			int status = con.getResponseCode();
			if(status != 200)
				return ErrorJSON.serviceRefused("Can't find tv show with id", -2);
			
			//On récupère la réponse envoyé par l'API
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			JSONObject laserie= new JSONObject();
			
			while ((inputLine = in.readLine()) != null) {
				try {
					laserie=new JSONObject(inputLine);

					//Parsing de la date du dernier épisode sortie cette semaine
					String s = laserie.getString("last_air_date");
					//System.out.println("--- "+s+" ---");
					String[] sep = s.split("-");
					Integer[] date= {Integer.parseInt(sep[0]),Integer.parseInt(sep[1]),Integer.parseInt(sep[2])};
					
					//Parsing de la date du dernier épisode qui va sortir cette semaine
					//A faire peut-être

					if(isDayOfTheWeek(LocalDate.of(date[0], date[1], date[2]))) //Check si la date de sortie est compris dans la semaine
						listeseries.put(laserie);

				} catch (JSONException e) {
					System.out.println("Error: readline id series (ApiTools.java");
				}
			}
			in.close();
		}


		try {
			return new JSONObject().put("data", listeseries);
		} catch (JSONException e) {
			
			System.out.println("Error: Ajout listeseries à échoué (ApiTools.java)");
		}
		return ErrorJSON.serviceRefused("Problème requete ApiTools", -3);
	}
	
	/**
	 * Renvoie les films sortie depuis le début de l'année, jusqu'à aujourd'hui
	 * 
	 * @return JSONObject
	 * @throws IOException
	 */
	
	public static JSONObject filmsOnAir() throws IOException {
		
		JSONObject retour=new JSONObject();
		JSONArray listefilms=new JSONArray();
		
		for(int i=1;i<4;i++) { // On récupère les 3 première pages (On peux récupérer toutes les pages avec la valeur renvoyer par l'api "total_pages"
			
			//On effectue un appel à l'api externe (On récupère les pages françaises uniquement)
			URL url = new URL("https://api.themoviedb.org/3/movie/upcoming?api_key=a3be1be132d237a0716cc27bdae1b2f0&language=en-US&page="+i);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");

			//On récupère la réponse, si le status est valide, on continue
			int status = con.getResponseCode();
			if(status != 200)
				return ErrorJSON.serviceRefused("Can't find movies ", -1);
			
			//On récupère la réponse envoyé par l'API
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			JSONObject listeofthemonth= new JSONObject();
			
			while ((inputLine = in.readLine()) != null) {
				try {
					listeofthemonth=new JSONObject(inputLine); //Recupère tout le champ results
				} catch (JSONException e) {
					System.out.println("Error: readline movies (ApiTools.java");
				}

			}
			in.close();

			// La réponse retourné est un String. On cast notre réponse String -> JSONArray (On obtiens un JSONArray contenant des objets)
			JSONArray myarray = null;
			try {
				myarray = (JSONArray) listeofthemonth.get("results");
			} catch (JSONException e) {
				System.out.println("Error: cast JSONArray films (ApiTools.java");
			}

			//Pour chaque films, on récupère ceux qui sont compris entre le début de l'année et aujourdhui.
			
			for(int k=0;k<myarray.length();k++) {
				try {

					JSONObject lefilm=(JSONObject) myarray.get(k);
					
					//Parsing de la date du dernier épisode sortie cette semaine
					String s = lefilm.getString("release_date");
					
					
					String[] sep = s.split("-");
					Integer[] date= {Integer.parseInt(sep[0]),Integer.parseInt(sep[1]),Integer.parseInt(sep[2])};
					
					//Si la date est compris entre le début de l'année et aujourd'hui inclus
					if (isDayUntilToday(LocalDate.of(date[0], date[1], date[2]))) {
						listefilms.put(lefilm);
					}

				} catch (JSONException e) {
					System.out.println("Error : cast JSONObject (ApiTools.java");
				}

			}
		}
		try {
			retour.put("data", listefilms);
		} catch (JSONException e) {
			System.out.println("Error: Ajout listefilms à échoué (ApiTools.java)");
		}
		return retour;
	}
	
	/**
	 * Renvoie le resultat de la recherche pour le mot "Keyword"
	 * 
	 * @param key
	 * @param keyword
	 * @return JSONObject
	 * @throws IOException
	 */
	public static JSONObject recherche(String key,String keyword) throws IOException {
		JSONObject retour= new JSONObject();
		JSONArray myarray=new JSONArray();
		
		//On effectue un appel à l'API externe
		URL url = new URL("https://api.themoviedb.org/3/search/multi?api_key="+key+"&language=fr-FR&query="+keyword+"&page=1&include_adult=false"); 
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");

		//On récupère la réponse, si le status est valide, on continue
		int status = con.getResponseCode();
		if(status != 200)
			return ErrorJSON.serviceRefused("Can't find search associate to "+keyword, -1);
		
		//On récupère la réponse envoyé par l'API
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		JSONObject listedelarecherche= new JSONObject();
		
		while ((inputLine = in.readLine()) != null) {
			try {
				listedelarecherche=new JSONObject(inputLine);
			} catch (JSONException e) {
				System.out.println("Error: Readline recherche (ApiTools.java)");
			}

		}
		in.close();
		
		// La réponse retourné est un String. On cast notre réponse String -> JSONArray (On obtiens un JSONArray contenant des objets)
		try {
			myarray=(JSONArray) listedelarecherche.get("results");
		} catch (JSONException e1) {
			System.out.println("Error: Cast JSONArray listedelarecherche (ApiTools.java)");
		}
		
		JSONArray newArray=new JSONArray();
		for(int i=0;i<myarray.length();i++) {
			try {
				JSONObject jo=(JSONObject)myarray.get(i); //String -> JSONObject
				newArray.put(jo);
			} catch (JSONException e) {
				System.out.println("Error: cast JSONObject recherche (ApiTools.java)");
			}
			
		}
		
		try {
			retour.put("data",newArray);
		} catch (JSONException e) {
			System.out.println("Error: Ajout resultat de recherche (ApiTools.java)");
		}
		return retour;
	}
	
	/**
	 * Renvoie true si la date est comprise entre le début et la fin de la semaine sinon false
	 * 
	 * @param d
	 * @return boolean 
	 */
	private static boolean isDayOfTheWeek(LocalDate d) {
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

		if(d.compareTo(monday)>=0 && d.compareTo(sunday)<=0)
			return true;
		return false;
	}
	
	/**
	 * Verifie si la date est comprise entre le début de l'année et aujourd'hui
	 *
	 * @param d
	 * @return boolean
	 */
	private static boolean isDayUntilToday(LocalDate d) {
		LocalDate today = LocalDate.now(); //date d'aujourd'hui
		LocalDate begin = LocalDate.of(today.getYear(), 01, 01); //debut de l'année
		
		
		if(d.compareTo(begin)>=0 && d.compareTo(today)<=0)
			return true;
		return false;
	}
}
