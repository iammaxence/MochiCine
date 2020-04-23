package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * servlet gerant l'acces aux messages
 * @author
 *
 */
public class ListMessages extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * appelle le Service Messages pour rendre la liste des messages a partir
	 * des information de la requete http
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
	        String titre= request.getParameter("titre");
	        
	        JSONObject res = services.Messages.getlistMessages(titre);
	        reponse.setContentType("text/json");
	        PrintWriter out = reponse.getWriter();
	        out.println(res);
		}
}
