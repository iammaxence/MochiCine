package services;

import java.io.IOException;

import org.json.JSONObject;

import tools.ErrorJSON;

public class ApiService {
	
	
	public static JSONObject weeklySeries() throws IOException{
		
		return tools.ApiTools.weeklySeries();
	}
	
	
	public static JSONObject filmsOnAir() throws IOException{

		return tools.ApiTools.filmsOnAir();
	}
	
	
	public static JSONObject recherche(String key,String keyword) throws IOException{
		if(keyword.equals("")) 
			return ErrorJSON.serviceRefused("Keyword is empty", -1);
		
		if(key.equals(""))
			return ErrorJSON.serviceRefused("Invalid key", -2);
		return tools.ApiTools.recherche(key,keyword);
	}

	
	public static JSONObject tendanceFilms(String key,int count) throws IOException {
		if(key.equals("")) 
			return ErrorJSON.serviceRefused("Invalid key", -3);
		return tools.ApiTools.tendanceFilms(key,count);
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
		
		boolean movie = new Boolean(isMovie);
		if(movie) 
			return tools.ApiTools.getDescription(id, "movie");
		else
			return tools.ApiTools.getDescription(id, "tv");
	}
}
