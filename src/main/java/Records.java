import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Records {
	private ArrayList<Record> records;
	private Statement statement;
	
	//Constructor
	public Records() {
		records = new ArrayList<Record>();	
		final var con = SQLiteConnectionHelper.getConnection();
		
		try {
			//We initialize statement
			statement = con.createStatement();
			//And load records from db into records list 
			updateRecords();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Method, which updates records list according DB
	private void updateRecords() throws SQLException {
		ResultSet rs = statement.executeQuery( "SELECT * FROM records" );
		records = new ArrayList<Record>();
		//Reading values from DB into the records list
		while(rs.next()){
			records.add(new Record(rs.getString("name"), rs.getString("date"), rs.getString("description"))); 
		}
	}
	
	// Add or update record by name with date in String format  
	public void addOrUpdateRecord(String name, String deadline, String description) {
		Date newDeadline = Date.valueOf(deadline);
		addOrUpdateRecord(name, newDeadline, description);
	}
	
	//Add or update record
	public void addOrUpdateRecord(Record rec) {
		addOrUpdateRecord(rec.getName(), rec.getDeadline(), rec.getDescription());
	}
	
	//Add or update record by name with date in sql.Date format 
	public void addOrUpdateRecord( String name, Date deadline, String description ) {
		Record newRecord = new Record( name, deadline, description );
				
		//ToDo delete or change output to logging after debugging
		//System.out.println(newRecord);
		
		//Check if there is a record with the same name as the new record.
		boolean exists = existsInRecordsDB( newRecord.getName() );
		
		//Create query String, then initialize it with UPDATE or INSERT query depending on whether there is a record with the name
		String query;
		if( exists ) {
			query = String.format("UPDATE records SET name = '%s', date = '%s', description = '%s' WHERE name = '%s';", newRecord.getName(),
					newRecord.getDeadline(), newRecord.getDescription(), newRecord.getName());
		} else {
			query = String.format( "INSERT INTO records (name, date, description) VALUES('%s', '%s', '%s');", 
					newRecord.getName(), newRecord.getDeadline().toString(), newRecord.getDescription() );	
		}
		//Execute UPDATE or INSERT query and update records list 
		try {
			statement.executeUpdate(query);
			updateRecords();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Delete record by name; If there is not record with the name, it will return -1;
	public int deleteRecord( String recordName ) {
		String query;
		if( existsInRecordsDB( recordName ) ) {
			query = "DELETE FROM records WHERE name = '" + recordName + "';";
			try {
				statement.executeUpdate( query );
				updateRecords();
			} catch ( SQLException e ) {
				e.printStackTrace();
				return 0;
			} 
			return 1;
		} 	
		return -1;
	}
	
	//Check if there is a record with this name.
	private boolean existsInRecordsDB( String name ) {
		boolean exists;
		int numOfMatchingNames = records.stream()
				.filter( 
						p -> p.getName()
						.equals( ( name ) ) 
						)
				.collect( Collectors.toList() )
				.size();
		exists = numOfMatchingNames > 0;
		
		return exists;
	}
	
	public ArrayList<Record> getRecords() {
		return records;
	}
	
	public Record getRecordByName(String name) {
		return records.stream().filter(record -> name.equals(record.getName())).findFirst().orElse(null);
	}
	
}
