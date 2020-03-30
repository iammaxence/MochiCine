package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class AddComment extends HttpServlet {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPut(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
			
			String user = request.getParameter("user");
			String titre = request.getParameter("titre");
	        String commentaire = request.getParameter("comment"); //comment : {id, comment}
	        
	        JSONObject res = services.Commentaires.addComment(user, titre, commentaire);
	        reponse.setContentType("text/json");
	        PrintWriter out = reponse.getWriter();
	        out.println(res);
		}
}
