import java.sql.Date;

public class Record {
	
	//name has to be unique
	private String name;
	private java.sql.Date deadline;
	private String description;
	
	public Record(String name, Date deadline, String description) {
		this.name = name;
		this.deadline = deadline;
		this.description = description;
	}

	//Getters
	public String getName() {
		return name;
	}
	public Date getDeadline() {
		return deadline;
	}
	public String getDescription() {
		return description;
	}
	
	//Setters
	public void setName(String name) {
		this.name = name;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		
		return String.format("%s %s %s", name, deadline.toString(), description);
	}
}
