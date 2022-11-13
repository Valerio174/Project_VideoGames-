package be.walbert.frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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
import javax.swing.JButton;

public class AddGamePage extends JFrame {

	/*Variables*/
	private JPanel contentPane;
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	private JTextField tf_Name; 
	private ArrayList<String> all_consoles;
	private ArrayList<String> all_versions;
	private JList<String> list_version; 
	private Image img = new ImageIcon(this.getClass().getResource("/ressources/icon_back.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

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
	public AddGamePage(Administrator admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1136, 723);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		/*Icon de retour (vers la page Admin)*/
		JLabel lbl_icon_back = new JLabel("");
		
		/*Ajout de l'evenement lors du click*/
		lbl_icon_back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdministratorPage adminpage = new AdministratorPage(admin);
				adminpage.setVisible(true);
				dispose();
			}
		});
		lbl_icon_back.setBounds(21, 589, 50, 50); 
		lbl_icon_back.setIcon(new ImageIcon(img));
		contentPane.add(lbl_icon_back);
		
		 /*Bouton deconnexion(Log Out)*/
		JLabel lbl_icon_logout = new JLabel("");
		
		/*Ajout de l'eveneptn sur le bouton deconnexion*/
		lbl_icon_logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HomePage homepage = new HomePage();	/*Redirection vers la HomePage*/
				homepage.setVisible(true);
				dispose();
			}
		});
		contentPane.setLayout(null);
		lbl_icon_logout.setBounds(1031, 38, 64, 64); 
		lbl_icon_logout.setIcon(new ImageIcon(icon_logout));
		contentPane.add(lbl_icon_logout);
		
		JLabel lbl_LogOut = new JLabel("Log Out");
		lbl_LogOut.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LogOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_LogOut.setBounds(1025, 11, 70, 25);
		contentPane.add(lbl_LogOut);
		
		JLabel lbl_TitlteNewGame = new JLabel("Add a new game");
		lbl_TitlteNewGame.setFont(new Font("Segoe UI Black", Font.ITALIC, 25));
		lbl_TitlteNewGame.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TitlteNewGame.setBounds(237, 38, 530, 64);
		contentPane.add(lbl_TitlteNewGame);
		
		JLabel lblAddAEnter = new JLabel("Enter informations about the new game:");
		lblAddAEnter.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddAEnter.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lblAddAEnter.setBounds(10, 126, 458, 40);
		contentPane.add(lblAddAEnter);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
		lblName.setBounds(10, 200, 204, 40);
		contentPane.add(lblName);
		
		JLabel lblConsole = new JLabel("Console:");
		lblConsole.setHorizontalAlignment(SwingConstants.LEFT);
		lblConsole.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
		lblConsole.setBounds(10, 280, 204, 40);
		contentPane.add(lblConsole);
		
		JLabel lblVersion = new JLabel("Version:");
		lblVersion.setHorizontalAlignment(SwingConstants.LEFT);
		lblVersion.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
		lblVersion.setBounds(10, 358, 204, 40);
		contentPane.add(lblVersion);
		
		JLabel lblCredits = new JLabel("Credits(min 1/max 5):");
		lblCredits.setHorizontalAlignment(SwingConstants.LEFT);
		lblCredits.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
		lblCredits.setBounds(10, 427, 204, 40);
		contentPane.add(lblCredits);
		
		/*TextFields*/
		JTextField tf_Credits = new JTextField();
		tf_Credits.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tf_Credits.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				/*Si le caractère choisit est différent d'un nombre OU non compris entre 1 et 5 OU un nombre à 2 chiffres */
				if(!Character.isDigit(c) || c==KeyEvent.VK_DELETE || c=='0' || c> '5' || tf_Credits.getText().length() > 0) {
					e.consume();
				}
			}
		});
		tf_Credits.setBounds(224, 429, 85, 41);
		contentPane.add(tf_Credits);
		tf_Credits.setColumns(10);
		
		tf_Name = new JTextField();
		tf_Name.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		tf_Name.setBounds(224, 200, 243, 35);
		contentPane.add(tf_Name);
		tf_Name.setColumns(10);
		
		/*Creation du curseur du JList pour Consoles*/
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(244, 40);
        scrollPane.setLocation(224, 280);
        contentPane.add(scrollPane);
        
		all_consoles = VideoGame.GetAllConsoles();
		JList<String> list_consoles = new JList(all_consoles.toArray());
		list_consoles.setBounds(224, 280, 244, 40);
		contentPane.add(list_consoles);
		  
		scrollPane.setViewportView(list_consoles);
		
		/*Creation du curseur du JList pour Version*/
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setSize(244, 40);
        scrollPane2.setLocation(224, 360);
        contentPane.add(scrollPane2);
        
		/*Ajout d'un evenement lors du clic sur la JList des Consoles*/
		list_consoles.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e){
	    	      if (e.getClickCount() == 1){
	    	    	String console_name = list_consoles.getSelectedValue();	/*Récupérer la console choisie*/
	    	    	all_versions = VideoGame.GetAllVersions(console_name);	/*Récupérer toutes les versions correspondantes*/
	    	    	list_version = new JList(all_versions.toArray());	/*Attribuer les versions à la liste pour affichage*/
	    	  		list_version.setBounds(224, 360, 244, 40);
	    			contentPane.add(list_version);
	    			  
	    			scrollPane2.setViewportView(list_version);
	    	      }
	    	}
	    });
   
		/*JLabel pour afficher si un champ est manquant*/
		JLabel lbl_ErrorEmpty = new JLabel("");
		lbl_ErrorEmpty.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_ErrorEmpty.setForeground(new Color(166, 0, 0));
		lbl_ErrorEmpty.setBounds(60, 625, 371, 41);
		contentPane.add(lbl_ErrorEmpty);
		
		/*Button*/
		JButton btn_Add = new JButton("Add");
		btn_Add.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		btn_Add.setBounds(237, 589, 106, 40);
		btn_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(tf_Name.getText().equals("") || list_consoles.getSelectedValue() == null || list_version.getSelectedValue() == null || tf_Credits.getText().equals("")) { 
					lbl_ErrorEmpty.setText("A field is missing");
				}
				else {
					VideoGame game = new VideoGame(tf_Name.getText(), Integer.valueOf(tf_Credits.getText()), list_version.getSelectedValue() , list_consoles.getSelectedValue());
					if(game.CreateVideoGame()) {
						 AdministratorPage adminpage = new AdministratorPage(admin);
						 adminpage.setVisible(true);
						 adminpage.lbl_Success.setText("Great, you have successfully added a video game");
						 dispose();
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Sorry an error");
					}
				}
			}
		});
		contentPane.add(btn_Add);

	}
}
