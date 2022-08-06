package ser322;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Helper class Jdbc. Instance created by the Textui class to create a connection to the database
 * and execute all the logic based on the input from the user as processed by the Textui class.
 * @author maaniksingh
 *
 */
public class Jdbc {

	private ResultSet rs = null;
	private Statement stmt = null;
	private PreparedStatement ps = null;
	private Connection conn = null;
	
	public void attemptConnect(String url, String usr, String pass) throws SQLException {
		
		// Load the JDBC driver
		//Class.forName();
		
		// attempt connection
		conn = DriverManager.getConnection(url, usr, pass);
   		
		
	}
	/**
	 * Receives the table and the values to insert. Will execute insertion and print accordingly.
	 * 
	 * @param table		the table to insert into in the form of String
	 * @param values	the values to be inserted
	 * @throws SQLException
	 */
	public void insert(String table, String[] values) throws SQLException {
		ps = conn.prepareStatement("INSERT INTO musiclibrary.USERS VALUES (?,?,?);");
		ps.setString(1, values[0]);
		ps.setString(2, values[1]);
		ps.setString(3, values[2]);
		if(ps.executeUpdate()>0) {
			System.out.println("SUCCESSFUL INSERTION!");
		}		
	}
	
	/**
	 * Closes all resources
	 */
	public void closeResources() {
		try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (ps !=null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
	}
	

}
