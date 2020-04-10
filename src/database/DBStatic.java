package database;


public class DBStatic {
	public static String mysql_host="localhost";
	public static String mysql_db="mochicine"; //changer ici
	public static String mysql_username="root";
	public static String mysql_password="root";
	public static boolean mysql_pooling=false; 
//	public static boolean mysql_pooling=false; 
	//EXPORT -> mettre a true
	//pooling true -> on utilise Datasource qui fait la connection automatique donc pas local
	//pooling false -> connection locale avec DBStatic
	
	public static String mongodb_host="mongodb://localhost:27017";
	public static String mongodb_port="27017"; //changer ici maybe
	public static String mongodb_db="Brunet_Lin"; //changer ici
}