package core;

public class Candidat {
	int id; //position in the array
	String name;
	int vows=0;
	//int percentage = 0;
	boolean isEliminated = false;
	
	/**
	 * First Constructor
	 * @param name
	 * @param id
	 */
	public Candidat(String name, int id) {
		super();
		this.name = name;
		this.id =id;
	}
	
	/**
	 * Second Constructor no parameter
	 */
	public Candidat() {
		super();
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
	public int getVows() {
		return vows;
	}
	public void setVows(int vows) {
		this.vows = vows;
	}
	
	
	
	@Override
	public String toString() {
		String s = "";
		if(!isEliminated)
			s = name + "==> " + " vows: " +vows /*+ " Eleminated: "+ isEliminated*/;
		return s;
	}
	
	

}
