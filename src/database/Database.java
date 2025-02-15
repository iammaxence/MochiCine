package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; 

import javax.naming.InitialContext; 
import javax.naming.NamingException; 
import javax.sql.DataSource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Database {
	
	private DataSource dataSource; 
	private static Database database;
	private static MongoClient mongo;
	
	public Database(String jndiname) throws SQLException { 
		try { 
				dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname); 
			} catch (NamingException e) { 
				// Handle error that it’s not configured in JNDI. 
				throw new SQLException(jndiname + " is missing in JNDI! : "+e.getMessage()); 
			} 
		} 
	
	public Connection getConnection() throws SQLException { 
		return dataSource.getConnection(); 
	} 
	
	public static Connection getMySQLConnection() throws SQLException { 
        
        if (DBStatic.mysql_pooling==false) { 
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return(DriverManager.getConnection("jdbc:mysql://"+DBStatic.mysql_host+"/"+DBStatic.mysql_db, DBStatic.mysql_username, DBStatic.mysql_password));
        } 
        else { 
            
            if (database==null) { 
                database=new Database("jdbc/db"); 
        } 
        return(database.getConnection()); 
        } 
    }
	
	
	
	/**
	 * Connection à Mongo
	 * @return MongoDatabase
	 */
	public static MongoDatabase getMongoConnection() {
		mongo = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase db = mongo.getDatabase(DBStatic.mongodb_db);
		return db;
	}
	
	
	public static void MongoClose() {
		mongo.close();
	}
	


}
