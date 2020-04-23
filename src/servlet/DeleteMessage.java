package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * servlet gerant la suppression des Messages
 * @author
 *
 */
@SuppressWarnings("serial")
public class DeleteMessage extends HttpServlet{
	
	/**
	 * appelle le Service Messages pour la suppression d'un message a partir
	 * des information de la requete http
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException {
		String id_message = request.getParameter("id_message");
		
		JSONObject res = services.Messages.deleteMessage(id_message);
		reponse.setContentType("text/json");
		PrintWriter out = reponse.getWriter();
		out.println(res);
	}
}