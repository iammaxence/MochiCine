package services;

import java.io.IOException;

import org.json.JSONObject;

import tools.ErrorJSON;

public class ApiService {
	
	/**
	 * Retourne un json contenant toutes les series des 7 derniers jours
	 * @param key : La clée d'identification (fournis par l'API externe)
	 * @return JSONObject
	 * @throws IOException
	 */
	public static JSONObject weeklySeries(String key) throws IOException{
		
		return tools.ApiTools.weeklySeries(key);
	}
	
	/**
	 * Retourne un json contenant tous les films sortie de 2020 à aujourd"hui (Ceux présent dans l'API)
	 * note: Seulement une 10ène sont récupéré pour l'affichage de l'accueil
	 * @param key : La clée d'identification (fournis par l'API externe)
	 * @return JSONObject
	 * @throws IOException
	 */
	
	public static JSONObject filmsOnAir(String key) throws IOException{

		return tools.ApiTools.filmsOnAir(key);
	}
	
	/**
	 * 
	 * @param key : La clée d'identification (fournis par l'API externe)
	 * @param keyword : Le mot clée de la recherche (ex: Avengers)
	 * @return JSONObject
	 * @throws IOException
	 */
	
	public static JSONObject recherche(String key,String keyword) throws IOException{
		if(keyword.equals("")) 
			return ErrorJSON.serviceRefused("Keyword is empty in recherche", -1);
		
		if(key.equals(""))
			return ErrorJSON.serviceRefused("Invalid key in recherche", -2);
		return tools.ApiTools.recherche(key,keyword);
	}

	/**
	 * 
	 * @param key :  La clée d'identification (fournis par l'API externe)
	 * @param count : Le nombres d'élements à récuperer ( On en récupère quelques uns pour les tendances pour ne pas polluer l'accueil du site)
	 * @param type : Correspond au string "tv" ou "movie"
	 * @return JSONObject
	 * @throws IOException
	 */
	public static JSONObject tendances(String key,int count,String type) throws IOException {
		if(key.equals("")) 
			return ErrorJSON.serviceRefused("Invalid key in tendances", -3);
		if(count<0)
			return ErrorJSON.serviceRefused("Invalid counter in tendances", -4);
		if(type.equals("tv") || type.equals("movie")) {
			return tools.ApiTools.tendances(key,count,type);
		}
		return ErrorJSON.serviceRefused("Invalid type in tendances", -4);
		
	}

	
	/**
	 * Permet de recuperer les informations d'un id via l'api externe
	 * @param id du film ou de la serie
	 * @param isMovie est "true" si c'est un film ou "false" si c'est une serie
	 * @return JSONObject du resultat dans "data" ou un ErrorJSON
	 */
	public static JSONObject getDescription(String id, String isMovie) {
		if(isMovie.equals("") || id.equals("")) {
			return ErrorJSON.serviceRefused("getDescription: Argument Null", -1);
		}
		
		boolean movie = Boolean.parseBoolean(isMovie);
		if(movie) 
			return tools.ApiTools.getDescription(id, "movie");
		else
			return tools.ApiTools.getDescription(id, "tv");
	}
}
