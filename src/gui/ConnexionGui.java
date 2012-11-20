package gui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import core.Voter;

public class ConnexionGui extends JFrame implements ActionListener, KeyListener {

	Vector<Voter> voters = new Vector<Voter>();

	JPanel mainPanel = new JPanel();
	JPanel pButton = new JPanel();

	JLabel lUser = new JLabel("Username: ");

	JTextField tUsername = new JTextField(12);

	JButton bSubmit = new JButton(new ImageIcon("button-sign-in.png"));
	JButton bCancel = new JButton(new ImageIcon("cancel.png"));

	public ConnexionGui() throws HeadlessException {

		setTitle("Login GUi");
		loadData();
		bCancel.addActionListener(this);
		bSubmit.addActionListener(this);
		bSubmit.setActionCommand("submit");
		tUsername.addKeyListener(this);
		tUsername.setFocusable(true);

		mainPanel.add(lUser);
		mainPanel.add(tUsername);

		pButton.add(bSubmit);
		pButton.add(bCancel);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		add(pButton, BorderLayout.SOUTH);
		setVisible(true);
		setSize(420, 150);
		setResizable(false);
		setLocation(400, 300);
		tUsername.requestFocusInWindow();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void loadData() {
		Vector<String> temp = new Vector<String>();
		try {

			InputStream ips = new FileInputStream("voters.txt");
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line;

			while ((line = br.readLine()) != null) {

				temp.add(line);
			}
			br.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		for (int i = 0; i < temp.size(); i++) {
			String s1 = temp.elementAt(i);
			String name = s1.substring(0, s1.indexOf(" "));
			String type = s1.substring(s1.indexOf(" ") + 1, s1.length());
			voters.add(new Voter(i, name, type));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(bCancel)) {
			new HomeGui();
			dispose();
			System.out.println(voters);
		}
		if (e.getSource().equals(bSubmit)) {
			Voter voter = new Voter();

			for (int i = 0; i < voters.size(); i++) {
				if (tUsername.getText().equals(voters.elementAt(i).getName())) {
					voter = voters.elementAt(i);
					break;
				}

			}
			System.out.println(voter);

			if (voter.getType().equals("voter")) {
				new VoteGui();
				dispose();
			} else if (voter.getType().equals("admin")) {
				new AdminGui();
				dispose();
			} else
				JOptionPane.showMessageDialog(this, "****Wrong login****",
						null, 0);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			bSubmit.doClick();
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
