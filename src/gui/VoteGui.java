package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import core.RunOffVote;

public class VoteGui extends JFrame implements ActionListener {
	JPanel mainpanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JButton bsubmit = new JButton(new ImageIcon("submit.png"));
	JButton bexit = new JButton(new ImageIcon("exit.png"));
	RunOffVote r = new RunOffVote("run_off.txt");

	VoteGui() {

		setTitle("Vote GUI");
		setLayout(new BorderLayout());
		mainpanel.setLayout(new GridLayout(0, 2, 0, 10));
		buttonPanel.add(bexit);
		buttonPanel.add(bsubmit);

		bexit.addActionListener(this);
		bsubmit.addActionListener(this);

		for (int i = 0; i < r.Candidats.size(); i++) {
			String[] s = new String[r.Candidats.size()];
			for (int j = 0; j < r.Candidats.size(); j++) {
				s[j] = "Choice " + (j + 1);
			}
			JLabel l = new JLabel(r.Candidats.elementAt(i).getName());
			JComboBox cb = new JComboBox(s);
			cb.addItem("Rank this candidate");
			cb.setSelectedItem("Rank this candidate");
			mainpanel.add(l);
			mainpanel.add(cb);

		}

		add(mainpanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);
		setLocation(500, 300);

		this.pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	public void addVote(String votes) {

		PrintWriter output;
		try {

			output = new PrintWriter(new FileWriter("run_off.txt", true));
			output.printf("%s\r\n", votes);

			output.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(bsubmit)) {
			String votes = "";
			for (int i = 0; i < mainpanel.getComponentCount(); i++) {
				if (mainpanel.getComponent(i).getClass().getName()
						.equals("javax.swing.JComboBox")) {

					JComboBox cb = (JComboBox) mainpanel.getComponent(i);
					int x = cb.getSelectedIndex() + 1;
					if (x == 7)
						x = 0;
					votes = votes + "," + Integer.toString(x);

				}
			}
			votes = votes.replaceFirst(
					votes.substring(0, votes.indexOf(",") + 1), "");

			// Make the confirmation messge
			String confirmMessage = "==Do you confirm== \n\n";
			String temp = votes.replaceAll(",", "");
			for (int i = 0; i < temp.length(); i++) {

				int choice = Integer
						.parseInt(Character.toString(temp.charAt(i)));
				if (choice == 0)
					confirmMessage = confirmMessage + " "
							+ r.Candidats.elementAt(i).getName()
							+ " (Not ranked)\n";
				else
					confirmMessage = confirmMessage + " "
							+ r.Candidats.elementAt(i).getName() + " choice "
							+ choice + "\n";

			}

			// Show the confirmation dialog box
			int confirm = JOptionPane.showConfirmDialog(null, confirmMessage);
			if (confirm == JOptionPane.YES_OPTION) {
				addVote(votes);
				JOptionPane.showMessageDialog(null,
						"Thank you for using the E-voting System\n "
								+ "Your vote has been succesfuly saved !");
				dispose();
				
				new ConnexionGui();
				System.out.println(votes + " saved !");
			}
		}

		else {// exit button
			int confirm = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to exit?");
			if (confirm == JOptionPane.YES_OPTION) {
				new HomeGui();
				dispose();
			}
		}

	}

}
