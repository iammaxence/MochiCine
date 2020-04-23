package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * servlet gerant la recherche d'un film ou d'une serie
 * @author
 *
 */
public class Search extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * appelle le Service ApiService pour realiser une recherche de film ou de serie a partir
	 * des information de la requete http
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws ServletException,IOException {
		String key = request.getParameter("key");
        String keyword = request.getParameter("keyword");
        
        JSONObject res = services.ApiService.recherche(key,keyword);
        reponse.setContentType("text/json");
        PrintWriter out = reponse.getWriter();
        out.println(res);
	}
}
