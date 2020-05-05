package test;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import services.Favoris;
import tools.ApiTools;
import tools.FavorisTools;


public class GetFavoris {

	public static void main(String[] args) {
		JSONObject res = FavorisTools.getFavoris("bob");
		
		ArrayList<Object> series;
		ArrayList<Object> movies;
		JSONObject resultat = null;
		
		try {
			series = (ArrayList<Object>) res.get("series");
			movies = (ArrayList<Object>) res.get("movies");
			ArrayList<Integer> ids = new ArrayList<Integer>();
			
			System.out.println(series);
			System.out.println(movies);
			
//			//AJout des series sous le nom de data
//			if(series.size() == 0) {
//				resultat = new JSONObject();
//				resultat.put("data", new ArrayList<>());
//			}else {
//				for(Object va : series) {
//					ids.add((Integer) va);
//				}
//				resultat = ApiTools.getIDs(ids, "tv");
//			}
//			
			//Ajout des movies sous le nom de movie
			if(movies.size() == 0) {
				resultat.put("movie", new ArrayList<>());
			}else {
				movies.remove(2);
				ids.clear();
				for(Object va : movies) {
					ids.add((Integer) va);
				}
				//[556678, 61864, 71712]
				JSONObject resultat2 = ApiTools.getIDs(ids, "movie");
				System.out.println(resultat2);
//				resultat.put("movie", resultat2.get("data"));
 			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
