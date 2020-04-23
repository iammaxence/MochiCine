package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * servlet gerant la creation d'un compte
 * @author
 *
 */
public class Register extends HttpServlet{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * appelle le Service Enregistrement pour cree un compte utilisateur a partir
	 * des information de la requete http
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
			
			String login = request.getParameter("login");
	        String mdp = request.getParameter("mdp");
	        
	        JSONObject res = services.Enregistrement.createUser(login, mdp);
	        reponse.setContentType("text/json");
	        reponse.addHeader("Content-Security-Policy", "script-src"); // Important pour les méthodes post sinon navigateur bloque (Question de sécurité)
	        PrintWriter out = reponse.getWriter();
	        out.println(res);
		}
}
