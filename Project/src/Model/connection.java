package Model;
import java.sql.*;

public class connection {
	private static Statement stmt;
	private static Connection con;
	
	public static Connection getConnection() {
		if(con==null) {
		     try{
		    	 Class.forName("com.mysql.cj.jdbc.Driver");
		    	  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafe","root","Pr@vin24"); 	  
		     }
		     catch(Exception c){
		    	 System.out.println(c);
		     }
		}
		return con;            
	}
   public static Statement getStatement() {
	   return stmt;
   }
}
