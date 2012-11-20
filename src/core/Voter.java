package core;

public class Voter {

	int id;
	String name;
	String type; //admin or voter
	public Voter(int id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}
	public Voter() {
		this.id = -1;
		this.name = "";
		this.type = "";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Voter [id=" + id + ", name=" + name + ", type=" + type + "]";
	}
	
	
}
