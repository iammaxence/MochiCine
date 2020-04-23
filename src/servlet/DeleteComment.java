package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
/**
 * servlet gerant la suppression de commentaire
 * @author
 *
 */
public class DeleteComment extends HttpServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * appelle le Service Commentaire pour la suppression d'un commentaire a partir
	 * des information de la requete http
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
			String id_message = request.getParameter("id_message");
	        String idCom = request.getParameter("idCom"); 
	        
	        
	        JSONObject res = services.Commentaires.deleteComment(idCom, id_message);
	        reponse.setContentType("text/json");
	        PrintWriter out = reponse.getWriter();
	        out.println(res);
		}
}
