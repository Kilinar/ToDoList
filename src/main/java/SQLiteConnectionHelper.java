import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//This class is Singleton, which will handle with all DB connections
public final class SQLiteConnectionHelper {
	
	private static Connection con;
	private static SQLiteConnectionHelper INSTANCE = null;
	
	// We create a connection in private Constructor
	private SQLiteConnectionHelper() throws SQLException {
		try{
			//ToDo change DB name
			con = DriverManager.getConnection("jdbc:sqlite:src//main//db//test_db.db"); 
    	} catch(SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	//This method returns connection.
	public static Connection getConnection() {
		if (INSTANCE == null) {
	    	try{
	    		INSTANCE = new SQLiteConnectionHelper();
	    	} catch(SQLException exc) {
				exc.printStackTrace();
			}
	    }
		return con;
	}
	
	
	
}
