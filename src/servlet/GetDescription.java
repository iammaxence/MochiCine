package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * servlet permettant de recupere la description d'un film ou d'une série
 * @author
 *
 */
public class GetDescription extends HttpServlet{

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
			
			String id = request.getParameter("id");
			String isMovie = request.getParameter("isMovie");
	        
	        
	        JSONObject res = services.ApiService.getDescription(id, isMovie);
	        reponse.setContentType("text/json");
	        PrintWriter out = reponse.getWriter();
	        out.println(res);
		}
}