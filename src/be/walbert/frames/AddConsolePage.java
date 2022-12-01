package be.walbert.frames;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.walbert.classes.Administrator;
import be.walbert.classes.VideoGame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class AddConsolePage extends JFrame {

	/*Variables*/
	private JPanel contentPane;
	private JTextField tf_NameNewConsole;
	private ArrayList<String> all_consoles;
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
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
	public AddConsolePage(Administrator admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(30, 30, 1145, 738);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		lbl_icon_back.setBounds(21, 620, 50, 50); 
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
		 
		JLabel lbl_TitleAddConsole= new JLabel("Add a new console");
		lbl_TitleAddConsole.setFont(new Font("Segoe UI Black", Font.ITALIC, 25));
		lbl_TitleAddConsole.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TitleAddConsole.setBounds(285, 38, 530, 64);
		contentPane.add(lbl_TitleAddConsole);
		
		JLabel lbl_TitleListConsoles = new JLabel("The list of consoles already on the application:");
		lbl_TitleListConsoles.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_TitleListConsoles.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_TitleListConsoles.setBounds(10, 127, 512, 38);
		contentPane.add(lbl_TitleListConsoles);
		
		JLabel lbl_TitleAddConsoleForm = new JLabel("Please enter the new console:");
		lbl_TitleAddConsoleForm.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_TitleAddConsoleForm.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_TitleAddConsoleForm.setBounds(10, 504, 512, 38);
		contentPane.add(lbl_TitleAddConsoleForm);
		
		JLabel lbl_Name = new JLabel("Name:");
		lbl_Name.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Name.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_Name.setBounds(10, 572, 104, 38);
		contentPane.add(lbl_Name);
		
		tf_NameNewConsole = new JTextField();
		tf_NameNewConsole.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		tf_NameNewConsole.setBounds(111, 572, 297, 38);
		contentPane.add(tf_NameNewConsole);
		tf_NameNewConsole.setColumns(10);
		
		JButton btn_AddNewConsole = new JButton("Add");
		btn_AddNewConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tf_NameNewConsole.getText().equals("")) {	/*Le TexField ne doit pas etre null*/
					JOptionPane.showMessageDialog(contentPane, "Field must be not null !");
				}
				else {
					if(VideoGame.CreateConsole(tf_NameNewConsole.getText())) {	/*Si la methode(static) de VideoGame renvoie true, la console s'est ajouté sans erreurs*/
						JOptionPane.showMessageDialog(contentPane, "Great, you have successfully added a new console !");
						AdministratorPage adminpage = new AdministratorPage(admin);
						adminpage.setVisible(true);
						dispose();
					}
				}
			}
		});
		btn_AddNewConsole.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		btn_AddNewConsole.setBounds(439, 572, 152, 38);
		contentPane.add(btn_AddNewConsole);
		
		 /*Creation du curseur du JList pour Consoles*/
	     JScrollPane scrollPane = new JScrollPane();
	     scrollPane.setSize(512, 100);
	     scrollPane.setLocation(10, 180);
	     contentPane.add(scrollPane);
	        
	     /*Récupérer TOUTES les consoles de la DB(avec methode static)*/
	     all_consoles = VideoGame.GetAllConsoles();
		 JList<String> list_consoles = new JList(all_consoles.toArray());
		 list_consoles.setBounds(10, 180, 512, 100);
		 contentPane.add(list_consoles);
			  
		 scrollPane.setViewportView(list_consoles);
        
	}
}
