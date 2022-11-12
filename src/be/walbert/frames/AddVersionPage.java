package be.walbert.frames;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.walbert.classes.Administrator;
import be.walbert.classes.VideoGame;

public class AddVersionPage extends JFrame {

	private JPanel contentPane;
	private JTextField tf_NameNewVersion;
	private ArrayList<String> all_consoles;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage homepage = new HomePage();
					homepage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddVersionPage(Administrator admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1171, 712);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_TitleAddVersion= new JLabel("Add a new version");
		lbl_TitleAddVersion.setFont(new Font("Segoe UI Black", Font.ITALIC, 25));
		lbl_TitleAddVersion.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TitleAddVersion.setBounds(285, 38, 530, 64);
		contentPane.add(lbl_TitleAddVersion);
		
		JLabel lbl_TitleListVersions = new JLabel("The list of versions already on the application:");
		lbl_TitleListVersions.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_TitleListVersions.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_TitleListVersions.setBounds(10, 127, 512, 38);
		contentPane.add(lbl_TitleListVersions);
		
		JLabel lbl_TitleAddVersionForm = new JLabel("Please enter the new version:");
		lbl_TitleAddVersionForm.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_TitleAddVersionForm.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_TitleAddVersionForm.setBounds(10, 504, 512, 38);
		contentPane.add(lbl_TitleAddVersionForm);
		
		JLabel lbl_Name = new JLabel("Name:");
		lbl_Name.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Name.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_Name.setBounds(10, 572, 104, 38);
		contentPane.add(lbl_Name);
		
		tf_NameNewVersion = new JTextField();
		tf_NameNewVersion.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		tf_NameNewVersion.setBounds(111, 572, 297, 38);
		contentPane.add(tf_NameNewVersion);
		tf_NameNewVersion.setColumns(10);
		
		/*Creation du curseur du JList pour Consoles*/
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(244, 250);
        scrollPane.setLocation(10, 200);
        contentPane.add(scrollPane);
        
		all_consoles = VideoGame.GetAllConsoles();
		JList<String> list_consoles = new JList(all_consoles.toArray());
		list_consoles.setBounds(10, 200, 512, 250);
		contentPane.add(list_consoles);
		  
		scrollPane.setViewportView(list_consoles);
		
		JButton btn_AddNewConsole = new JButton("Add");
		btn_AddNewConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tf_NameNewVersion.getText().equals("")) {
					 JOptionPane.showMessageDialog(contentPane, "Sorry, the field is empty. Enter a new version !");
				}
				else {
					 if(list_consoles.getSelectedValue() == null) {
						 JOptionPane.showMessageDialog(contentPane, "Please, choose a console to add a version to it !");
					 }
					 else {
						 VideoGame.CreateVersion(list_consoles.getSelectedValue(), tf_NameNewVersion.getText());
						 JOptionPane.showMessageDialog(contentPane, "Great, your new version has been added !");
						 AdministratorPage 	adminpage = new AdministratorPage(admin);
						 adminpage.setVisible(true);
						 dispose();
					 }
				}
			}
		});
		btn_AddNewConsole.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		btn_AddNewConsole.setBounds(439, 572, 152, 38);
		contentPane.add(btn_AddNewConsole);
		
		
		
	}

}
