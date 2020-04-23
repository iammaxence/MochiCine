package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
/**
 * servlet gerant l'ajout de favoris
 * @author
 *
 */
public class AddFavoris extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * appelle le Service Favoris pour l'ajout d'un favoris a partir
	 * des information de la requete http
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
			
			String login = request.getParameter("login");
	        String id_favoris = request.getParameter("titre");
	        String isSerie = request.getParameter("isSerie");
	        
	        JSONObject res = services.Favoris.addFavoris(login, Integer.parseInt(id_favoris) ,isSerie);
	        reponse.setContentType("text/json");
	        PrintWriter out = reponse.getWriter();
	        out.println(res);
		}
}
