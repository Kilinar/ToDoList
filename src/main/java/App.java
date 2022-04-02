import java.sql.Date;
import java.util.Scanner;

public class App {

	// ToDo: think about deprecating Data class and using just formatted String;
	// ToDo: Name can include spaces, but input is splitted by space, so there is a problem here. 
	// Maybe, change split method to something, that will split only first word (command) from all other input
	
	
	
	// Main class and method
	public static void main(String[] args) {
				
		// For now, all UI logic and controller are in a main method.
		// Yes, it's a bad practice (at least, as I know), but
		// I'll move this project to Swing or JavaFX, so I'll definitely 
		// rewrite it all, so... I think it's more simple than adding more classes
		
		Records records = new Records();
		Scanner sc = new Scanner(System.in);
		boolean isActive = true;
		
		while(isActive) {
			System.out.println("\n\n");
			//In the beginning of the cycle the full list of records is written in console.
			var recordsList = records.getRecords();
			for(Record rec : recordsList) {
				String tempOutput = rec.toString();
				if(tempOutput.length() > 50)
					System.out.println(tempOutput.substring(0, 50) + "...");
				else
					System.out.println(tempOutput);
			}
			System.out.print("\n");
			
			// Then the console asks user to input operation
			System.out.println("Please, write command.\nWrite help to see list of all commands.");
			// And process it
			var input = sc.nextLine().trim().split(" ", 2);
			try {
				switch( input[0].toLowerCase() ) {
					case "help":
						System.out.println("add_record - used to create new records or modify existing ones." 
								+ " After entering this command, a dialog will be launched; if you enter the name" 
								+ " of an already existing entry, you will edit it");
						System.out.println("full *** - writes a full description of the record with a specific title,"
								+ " which should be written instead of '***'");
						System.out.println("remove_record *** - removes the record with a specific title,"
								+ " which should be written instead of '***'");
						System.out.println("exit - closes the application");
						break;
					case "add_record":
						//Process record's name
						String name;
						while(true) {
							System.out.println("Please, enter name of your record (maximum 25 letters)");
							name = sc.nextLine();
							if(name.length() > 25) {
								System.out.println("Entered name exceeds the maximum length");
								continue;
							}
							break;
						}
						//Process deadline
						Date deadline;
						System.out.println("Please, enter deadline in yyyy-[m]m-[d]d format. Example: 2022-7-11");
						while(true) {	
							try {
								deadline = Date.valueOf(sc.nextLine());
								break;
							} catch(IllegalArgumentException exc) {
								System.out.println("Something is wrong with the entered date. Please, try again.");
								continue;
							}
						}
						//Process description
						String description;
						while(true) {
							System.out.println("Please, enter description of your record (maximum 255 letters)");
							description = sc.nextLine();
							if(description.length() > 255) {
								System.out.println("Entered description exceeds the maximum length");
								continue;
							}
							break;
						}
						//Call the add or update method
						records.addOrUpdateRecord(name, deadline, description);
						break;
					case "full":
						System.out.println(records.getRecordByName(input[1]));
						break;
					case "remove_record":
						records.deleteRecord(input[1]);
						break;
					case "exit":
						isActive = false;
					break;
					default:
						System.out.println("An unknown command was written.");
						break;
				}
			} catch(ArrayIndexOutOfBoundsException err) {
				System.out.println("You didn't provide a record name.");
				continue;
			}
			
		}
		
		
		sc.close();
	}
	
	
	
}
