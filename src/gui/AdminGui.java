package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import core.RunOffVote;

public class AdminGui extends JFrame implements ActionListener{
	
	RunOffVote r = new RunOffVote("run_off.txt");
	
	JPanel bpanel = new JPanel();
	JButton bexit = new JButton(new ImageIcon("exit.png"));
	
	JTextArea tAtrace = new JTextArea(25, 65);
	
	JScrollPane tracePane = new JScrollPane(tAtrace);

	public AdminGui() throws HeadlessException {
		
		setTitle("Administrator GUI");
		setLayout(new BorderLayout());
		setLocation(200, 100);
		bexit.addActionListener(this);
		add(bexit, BorderLayout.SOUTH);
		add(tracePane, BorderLayout.CENTER);
		tAtrace.setFont(new Font(Font.SERIF, Font.BOLD, 12));

		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		r.proceedVote();
		
		for(int i=0; i<r.trace.size(); i++)
		tAtrace.setText(tAtrace.getText() + "\n" + r.trace.elementAt(i));
		tAtrace.setEditable(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
		if (confirm == JOptionPane.YES_OPTION){
		new HomeGui();
		dispose();
		}
		
	}
		
}
