package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * servlet gerant la connextion d'un utilisateur
 * @author
 *
 */
public class Login extends HttpServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * appelle le Service Authentification pour connecter un utilisateur a partir
	 * des information de la requete http
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
		
		String login = request.getParameter("login");
        String mdp = request.getParameter("mdp");
        
        JSONObject res = services.Authentification.login(login, mdp);
        reponse.setContentType("text/json");
        PrintWriter out = reponse.getWriter();
        out.println(res);
	}
}
