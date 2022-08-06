package ser322;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * The main class textui starts the console ui. Queries user for database credentials until connection
 * is made. Displays and querys user to choose among menu options for manipulating the database. 
 * 
 * @author maaniksingh
 * @version 1
 */
public class Textui {
	static Jdbc db; //jdbc instance
	static Scanner scanner = new Scanner(System.in);
	
	/**
	 * Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		db = new Jdbc();
		
		connect();
		queryMenu();

	}
	
	/**
	 * Queries url, username, and password to the database until connection is successful 
	 * 
	 */
	public static void connect() {
		String url = "jdbc:mysql://localhost:3306/musiclibrary?autoReconnect=true&useSSL=true"; 
		String usr = "root";
		String pass = "";
		
		while(true) {
			try {
				//enter with the autoReconnect, useSSL value set 
				System.out.println("Enter database URL:\t");
				url = scanner.next();
				System.out.println("Enter username:\t");
				usr = scanner.next();
				System.out.println("Enter password:\t");
				pass = scanner.next();
				db.attemptConnect(url, usr, pass);
				break;
			}catch(SQLException e){
				System.out.println("FAILED. Try again.");
			}
		}
		System.out.println("Connected!");	
	}
	
	/**
	 * Presents the db manipulation menu to user until option is selected and calls appropriate method
	 *
	 */
	public static void queryMenu() {
		String input;
		while (true) {
			System.out.println("\nMAIN MENU");
			System.out.println("Please select an option:");
			System.out.println("||Insert[I]||Delete[D]||Update[U]||Query[Q]||Close Connection[CC]");
			input = scanner.next();
			
			//handling user selection of Insert option
			if(input.toLowerCase().equals("insert")||input.toLowerCase().equals("i")) queryInsert();
			
			//TODO handling user selection of Delete, Update, Query options
			else if(input.toLowerCase().equals("cc")||input.toLowerCase().equals("close connection")) {
				close();
				break;
			}
		}
	}
	
	/**
	 * If the option to Insert is selected from the menu, this method is called and queries
	 * the appropriate information to insert
	 */
	public static void queryInsert() {
		boolean insert = false; //were values inserted successfully?
		boolean valid = false; //is input vali?
		String input;
		String[] values;
		
		//query user to select object to insert
		while(!valid){
			System.out.println("What would you like to insert?\n");
			System.out.println("USER[U]\nARTIST[A]\nLABEL[L]\nTRACKLIST[T]");
			input = scanner.next();
			
			//handles the case to insert a User.
			if(input.toLowerCase().equals("user")||input.toLowerCase().equals("u")) {
				valid = true;
				values = new String[3];
				
				//query user for the values to be inserted
				while(!insert) {
					System.out.println("Enter new USER details:");
					System.out.println("UserID:\t");
					values[0]=scanner.next();
					System.out.println("Name:\t");
					values[1]=scanner.next();
					System.out.println("Date Of Birth:\t");
					values[2]=scanner.next();
					try{
						db.insert("USERS", values);
						insert = true;
					}catch(SQLException e) {
						System.out.println("FAILED. Please check values.");
					}		
				}
			}
			
			//TODO handle user selection to insert Artist, Label, Tracklist... Song, Playlist, etc to be added
			else if(input.toLowerCase().equals("artist")||input.toLowerCase().equals("a")) {valid = true;}
			else if(input.toLowerCase().equals("label")||input.toLowerCase().equals("l")) {valid = true;}
			else if(input.toLowerCase().equals("tracklist")||input.toLowerCase().equals("t")) {valid = true;}
			else System.out.println("Try again. Please select an option from the list below.");
		}	
	}
	
	/**
	 * Close the connection and resources by calling the corresponding method closeConnection() 
	 * in the jdbc class to close thet resultset, statment, and connection.  
	 * 
	 */
	public static void close() {
		db.closeResources();
		System.out.print("Connection Terminated.");
	}
	
	
}
