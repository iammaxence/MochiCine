package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * servlet gerant l'acces aux serie de la semaine
 * @author
 *
 */
public class SerieOfTheWeek extends HttpServlet{
	private static final long serialVersionUID = 1L;

	/**
	 * appelle le Service ApiService pour recuperer les serie de la semaine a partir
	 * des information de la requete http
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
			
			String key = request.getParameter("key");
			
	        JSONObject res = services.ApiService.weeklySeries(key);
	        reponse.setContentType("text/json");
	        //reponse.addHeader("Access-Control-Allow-Origin", "*"); // Permet de faire des appel via les naviagteur qui bloque l'accès à une requete du client
	        PrintWriter out = reponse.getWriter();
	        out.println(res);
		}
}
