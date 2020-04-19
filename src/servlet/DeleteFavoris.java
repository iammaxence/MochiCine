package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class DeleteFavoris extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
		
		String login = request.getParameter("login");
		String id_favoris = request.getParameter("titre");
        String isSerie = request.getParameter("isSerie");
        
        JSONObject res = services.Favoris.deleteFavoris(login, Integer.parseInt(id_favoris) ,isSerie);
        reponse.setContentType("text/json");
        PrintWriter out = reponse.getWriter();
        out.println(res);
	}
}
