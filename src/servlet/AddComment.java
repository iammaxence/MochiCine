package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * servlet gerant l'ajout de commentaire
 * @author
 *
 */
public class AddComment extends HttpServlet {
	

	private static final long serialVersionUID = 1L;

	/**
	 * appelle le Service Commentaire pour l'ajout d'un commentaire a partir
	 * des information de la requete http
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
			
			String user = request.getParameter("user");
			String id_message = request.getParameter("id_message");
	        String commentaire = request.getParameter("comment"); 
	        
	        JSONObject res = services.Commentaires.addComment(user, id_message, commentaire);
	        reponse.setContentType("text/json");
	        PrintWriter out = reponse.getWriter();
	        out.println(res);
		}
}
