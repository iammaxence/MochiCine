package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class AddMessage extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse reponse) throws IOException, ServletException {
		String text = request.getParameter("text");
		String login = request.getParameter("login");
		String titre = request.getParameter("titre");
		
		JSONObject res = services.Messages.addMessage(login, text, titre);
		reponse.setContentType("text/json");
		PrintWriter out = reponse.getWriter();
		out.println(res);
	}
}