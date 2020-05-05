package services;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ApiTools;
import tools.CheckTools;
import tools.ErrorJSON;
import tools.FavorisTools;

/**
 * Service assurant l'ajout, la suppression et l'acces au favoris d'un utilisateur
 * @author 
 *
 */
public class Favoris {
	
	/**
	 * Appelle l'outil FavorisTools pour realiser l'ajout d'un film ou d'une serie aux favoris d'un utilisateur.
	 * @param login
	 * @param id_favoris
	 * @param isSerie
	 * @return
	 */
	public static JSONObject addFavoris(String login, Integer id_favoris, Boolean isSerie) {
		if(login.equals("") || id_favoris.equals("")) {
			return ErrorJSON.serviceRefused("deleteFavoris: Argument Null", -1);
		}
		
		if(!CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("deleteFavoris: Pseudo "+login+" do not exist", -2);
		}
		
		if(FavorisTools.isFavoris(login, id_favoris, isSerie)) {
			return ErrorJSON.serviceAccepted(); //Deja ajouter
		}
		
		return FavorisTools.addFavoris(login, id_favoris, isSerie);
	}
	
	/**
	 * Appelle l'outil FavorisTools pour realiser la suppression d'un film ou d'une serie des favoris d'un utilisateur.
	 * @param login
	 * @param id_favoris
	 * @param isSerie
	 * @return
	 */
	public static JSONObject deleteFavoris(String login, Integer id_favoris, String isSerie) {
		if(login.equals("") || id_favoris < 0) {
			return ErrorJSON.serviceRefused("deleteFavoris: Argument Null", -1);
		}
		
		if(!CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("deleteFavoris: Pseudo "+login+" do not exist", -2);
		}
		
		
		return FavorisTools.deleteFavoris(login, id_favoris, new Boolean(isSerie));
		
	}
	
	/**
	 * Appelle l'outil FavorisTools pour recuperer la liste des favoris d'un utilisateur.
	 * @param login
	 * @return
	 */
	public static JSONObject ListFavoris(String login) {
		if(login.equals("")) {
			return ErrorJSON.serviceRefused("ListFavoris: Argument Null", -1);
		}
		
		if(!CheckTools.checkUser(login)) {
			return ErrorJSON.serviceRefused("ListFavoris: Pseudo "+login+" do not exist", -2);
		}
		
		JSONObject res = FavorisTools.getFavoris(login);
		
		
		return getFavoris(res);
		
	}
	
	/**
	 * Recupere tout les informations de la liste des favoris de l'utilisateur.
	 * @param fav
	 * @return JSONObject final
	 */
	@SuppressWarnings("unchecked")
	private static JSONObject getFavoris(JSONObject fav) {
		ArrayList<Object> series;
		ArrayList<Object> movies;
		JSONObject resultat = null;
		
		try {
			series = (ArrayList<Object>) fav.get("series");
			movies = (ArrayList<Object>) fav.get("movies");
			ArrayList<Integer> ids = new ArrayList<Integer>();
			
			//AJout des series sous le nom de data
			if(series.size() == 0) {
				resultat = new JSONObject();
				resultat.append("data", new ArrayList<>());
			}else {
				for(Object va : series) {
					ids.add((Integer) va);
				}
				resultat = ApiTools.getIDs(ids, "tv");
			}
			
			//Ajout des movies sous le nom de movie
			if(movies.size() == 0) {
				resultat.append("movie", new ArrayList<>());
			}else {
				ids.clear();
				for(Object va : movies) {
					ids.add((Integer) va);
				}
				
				JSONObject resultat2 = ApiTools.getIDs(ids, "movie");
				resultat.append("movie", resultat2.get("data"));
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return resultat;
	}
	
}
