package core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class RunOffVote {

	String filePath; // textFile Path

	private Vector<String> temp = new Vector<String>();// Contains all the lines
														// of the text file
	public Vector<Candidat> Candidats = new Vector<Candidat>();
	
	public Vector<Vector<Integer>> ballot = new Vector<Vector<Integer>>();
	public Vector<String>trace = new Vector<String>();

	Candidat winner = new Candidat();// the vote winner

	/**
	 * Constructor
	 * 
	 * @param file
	 */
	public RunOffVote(String file) {
		this.filePath = file;
		loadData();

	}

	/**
	 * This function puts all the data in the text file "run_off.txt" in the
	 * vector temp, and initialize the vector of candidats and the vector of
	 * voters
	 */
	public void loadData() {
		try {

			InputStream ips = new FileInputStream(filePath);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;

			while ((line = br.readLine()) != null) {

				temp.add(line);
			}
			br.close();

			setCandidats();
			setVoteOrder();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * This function puts the first line of the txt file "run_off.txt" in the
	 * vector "Candidats" by creating a new Candidat object for each name
	 */
	private void setCandidats() {

		String tempCandidats = temp.firstElement() + ",";
		int i = 0;
		Candidat candidat = new Candidat();
		while (tempCandidats.length() > 0) {

			// create name = position 0 to comma
			String name = tempCandidats
					.substring(0, tempCandidats.indexOf(","));
			candidat = new Candidat(name, i);
			i++;
			// add the new candidat in the vector "Candiats"
			Candidats.add(candidat);

			// delete the first of the vector "tempCandidats"
			tempCandidats = tempCandidats.replaceFirst(
					tempCandidats.substring(0, tempCandidats.indexOf(",") + 1),
					"");
		}

	}

	/**
	 * This function fill the vector "voteOrder" with the data in "run_off.txt"
	 * by skipping the first line, which is the name of the candidats.
	 */
	private void setVoteOrder() {
		for (int i = 1; i < temp.size(); i++) {
			// deleting the comma from the name
			String s = temp.elementAt(i).replaceAll(",", "");
			s = s.replaceAll(" ", "");
			if (s.length() > 0) {
				// creating a vector "tempVoteOrder" to store the preferences of
				// each voter
				Vector<Integer> tempVoteOrder = new Vector<Integer>();
				for (int j = 0; j < s.length(); j++)
					tempVoteOrder.add(Integer.parseInt(Character.toString(s
							.charAt(j))));
				// add the vector "tempVoteOrder" to the vector "voteOrder"
				ballot.add(tempVoteOrder);
			}
		}
	}

	/**
	 * This function does the initial vote (first choice for each voter)
	 * 
	 * @param
	 */
	public void initialVote() {

		for (int i = 0; i < ballot.size(); i++) {

			/*
			 * get the number voted and take 1 from this number; because the
			 * counting start from 0 while programming, but the voters start
			 * voting according to the candidat number 1.
			 */
			if(ballot.elementAt(i).elementAt(0)!=0){
			int count = ballot.elementAt(i).elementAt(0) - 1;

			// get the candidat at the position "count" from the vector
			// Candidats
			Candidat candidat = Candidats.elementAt(count);

			int vow = candidat.getVows() + 1;
			candidat.setVows(vow);}

		}

	}

	public void runOff(Candidat candidat) {
		for (int i = 0; i < ballot.size(); i++) {

			int round = 0;
			int count = ballot.elementAt(i).elementAt(round) - 1;

			if (count == candidat.id) {

				round++;
				count = ballot.elementAt(i).elementAt(round) - 1;

				Candidat c = Candidats.elementAt(count);
				for (int j = count; j < Candidats.size(); j++) {
					if (c.isEliminated) {
						round++;
						count = ballot.elementAt(i).elementAt(round) - 1;

						c = Candidats.elementAt(count);
					}
				}

				int vow = c.getVows() + 1;
				c.setVows(vow);

			}

		}
	}

	/**
	 * This function check the vector "Candidats" and returns the candidate with
	 * the lowest vows.
	 * 
	 * @return the candidate to eliminate.
	 */
	public Candidat getCandidatToEliminate() {

		Candidat candidat = new Candidat();
		Candidat toEliminate = new Candidat();

		for (int i = 0; i < Candidats.size(); i++) {
			if (!Candidats.elementAt(i).isEliminated)
				toEliminate = Candidats.elementAt(i);
		}

		// Find the candidat with the lowest vow depending on the round
		for (int i = 0; i < Candidats.size(); i++) {
			candidat = Candidats.elementAt(i);
			if (!candidat.isEliminated && candidat.vows < toEliminate.vows) {
				toEliminate = candidat;
			}
		}
		return toEliminate;
	}

	/**
	 * This function eliminate a candidate by putting its variable
	 * "isEleminated" to true.
	 * 
	 * @param Candidate
	 */
	public void eleminate(Candidat c) {
		c.isEliminated = true;
	}

	/**
	 * This function checks if there is only one candidate left
	 * 
	 * @return true if only one candidate is left and false if there is more
	 *         than one candidate left.
	 */
	public boolean onlyOneCandidateLeft() {
		Vector<Candidat> v = new Vector<Candidat>();
		boolean b = false;

		for (int i = 0; i < Candidats.size(); i++) {
			if (Candidats.elementAt(i).isEliminated)
				v.add(Candidats.elementAt(i));
		}
		if (v.size() == Candidats.size() - 1)
			b = true;

		return b;
	}

	/**
	 * This function check if there is a winner; if a candidat has more than 50%
	 * for example or if all the other candidats are eliminated except one.
	 * 
	 * @return true if there is a winner \n\t and false if no winner yet
	 */
	public boolean somebodyWon() {
		boolean b = false;
		Candidat c = new Candidat();

		for (int i = 0; i < Candidats.size(); i++) {
			c = Candidats.elementAt(i);
			if (c.vows > ballot.size() / 2) {
				winner = c;
				b = true;
			}
		}
		if (!b && onlyOneCandidateLeft()) {
			for (int i = 0; i < Candidats.size(); i++)
				if (!Candidats.elementAt(i).isEliminated)
					this.winner = Candidats.elementAt(i);
		}

		return b;
	}
	
	public void proceedVote(){
		
		initialVote();
		trace.add("********Initial Vote*********");
		trace.add(Candidats.toString());
		trace.add("_________________________________________________________________\n");

		int i =1;
		while (!somebodyWon()) {
			Candidat candidateToEliminate = getCandidatToEliminate();
			eleminate(candidateToEliminate);
			trace.add(candidateToEliminate.name+ "," + " with "
					+ candidateToEliminate.vows + " vows is eliminated");

			trace.add("=============After RunOff " + (i) + "=============");
			runOff(candidateToEliminate);
			trace.add(Candidats.toString());

			i++;

		}
		if (somebodyWon())
			trace.add("There is a winner : " + winner);
		else
			trace.add("Nobody has won yet !!");
		trace.add("Total Voters: " + ballot.size());
	}

}
