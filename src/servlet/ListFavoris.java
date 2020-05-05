package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * servlet gerant l'acces aux favoris
 * @author
 *
 */
public class ListFavoris extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * appelle le Service Favoris pour rendre la liste des favoris a partir
	 * des information de la requete http
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
			
			String login = request.getParameter("login");
	        
	        
	        JSONObject res = services.Favoris.listFavoris(login);
	        reponse.setContentType("text/json");
	        PrintWriter out = reponse.getWriter();
	        out.println(res);
		}
}
