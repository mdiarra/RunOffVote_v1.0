package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeGui extends JFrame implements ActionListener{
 

	JPanel mainPanel = new JPanel();
	JPanel pButton = new JPanel();
	
	JLabel lWelcome = new JLabel(new ImageIcon("welcome.png"));
	JButton bSignIn = new JButton(new ImageIcon("button-sign-in.png"));


	public HomeGui() throws HeadlessException {

		setTitle("Home-EVoting");
		bSignIn.addActionListener(this);
		pButton.add(bSignIn);
		
		getContentPane().setBackground(Color.BLUE);
		setLayout(new BorderLayout());
		add(lWelcome, BorderLayout.CENTER);
		add(pButton, BorderLayout.SOUTH);
		setVisible(true);
		setSize(500, 550);
		setResizable(false);
		setLocation(250,50);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		new ConnexionGui();
		dispose();

	}    
 }