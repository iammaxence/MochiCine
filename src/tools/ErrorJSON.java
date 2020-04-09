package tools;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorJSON {
	/**
	 * @param msg: String
	 * @param code:int
	 * @return JSONObject refused
	 */
	public static JSONObject serviceRefused(String msg, int code) {
		JSONObject o = new JSONObject();
		try {
			o.put("message",msg);
			o.put("code", code);
		}catch (JSONException e) {
			System.out.println("Erreur ServiceRefused ");
		}
		return o;
	}
	
	/**
	 * @return JSONObject accepted
	 */
	public static JSONObject serviceAccepted() {
		JSONObject o = new JSONObject();
		return o;
	}
}
