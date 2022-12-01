package be.walbert.frames;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import be.walbert.classes.Administrator;
import be.walbert.classes.VideoGame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdministratorPage extends JFrame {

	/*Variables*/
	private JPanel contentPane;
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	private ArrayList<VideoGame> videogames;
	private JTable table;
	JLabel lbl_Success;

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
	//Passer l'objet Administrator en parametre de la frame pour récupérer les infos (ici afficher le pseudo de l'admin)
	public AdministratorPage(Administrator admin) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1145, 738);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*Image de fond*/
		Image background_admin_page = new ImageIcon(this.getClass().getResource("/ressources/admin_background.jpg")).getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
		
		/*Definir attributs du fond d'ecran*/
		
		JLabel lbl_WelcomeAdmin = new JLabel("New label");
		lbl_WelcomeAdmin.setForeground(new Color(192, 192, 192));
		lbl_WelcomeAdmin.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lbl_WelcomeAdmin.setBounds(45, 48, 888, 81);
		contentPane.add(lbl_WelcomeAdmin);
		
		lbl_WelcomeAdmin.setText("Welcome admin " + admin.getUsername());
		
		/*Liste des jeux videos*/
		videogames = VideoGame.GetAll();
		
			/*Noms des colonnes du tableau*/
		String[] columnsnames = { "Name", "Console","Version","Credits"};
        
			/*Model du tableau*/
		DefaultTableModel tablemodel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
			/*Attribuer noms des colonnes au tablemodel*/
		tablemodel.setColumnIdentifiers(columnsnames);	
	       for(VideoGame v: videogames){
	           Object[] datas = {v.getName(), v.getConsole(),v.getVersion(),v.getCreditCost()};
	           tablemodel.addRow(datas);
	       }
	        
	       /*Creation du curseur du JTable*/
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(780, 284);
        scrollPane.setLocation(45, 230);
        contentPane.add(scrollPane);
        
        	/*Creation du JTable*/
        table = new JTable(tablemodel);
        scrollPane.setViewportView(table);
        
        
        /*Ajouter evenement lors du clique sur une ligne*/
	    table.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e){
	    	      if (e.getClickCount() == 2){ 

	  					VideoGame game_selected = videogames.get(table.getSelectedRow());
	  					game_selected = game_selected.GetVideoGame();
	  					SetCreditsPage creditspage = new SetCreditsPage(admin, game_selected);
	  					creditspage.setVisible(true);
	  					dispose();
	    	         }
	    	      }
	    });
	    
	    /*Bouton deconnexion(Log Out)*/
		JLabel lbl_icon_logout = new JLabel("");
		
		/*Ajout de l'evenement sur le bouton deconnexion*/
		lbl_icon_logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HomePage homepage = new HomePage();	/*Redirection vers la HomePage*/
				homepage.setVisible(true);
				dispose();
			}
		});
		lbl_icon_logout.setBounds(1051, 48, 70, 90); 
		lbl_icon_logout.setIcon(new ImageIcon(icon_logout));
		contentPane.add(lbl_icon_logout);
		
		JLabel lbl_LogOut = new JLabel("Log Out");
		lbl_LogOut.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LogOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_LogOut.setBounds(1022, 11, 99, 50);
		contentPane.add(lbl_LogOut);
		
		JLabel lbl_WelcomeAdmin_1 = new JLabel("Please choose game to modify his credits:");
		lbl_WelcomeAdmin_1.setForeground(new Color(192, 192, 192));
		lbl_WelcomeAdmin_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		lbl_WelcomeAdmin_1.setBounds(45, 140, 780, 81);
		contentPane.add(lbl_WelcomeAdmin_1);
		
		JButton btnNewButton = new JButton("Add Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddGamePage addgame_page = new AddGamePage(admin);
				addgame_page.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(913, 270, 160, 33);
		contentPane.add(btnNewButton);
		
		lbl_Success = new JLabel("");
		lbl_Success.setFont(new Font("Segoe UI Black", Font.ITALIC, 20));
		lbl_Success.setForeground(new Color(0, 128, 0));
		lbl_Success.setBounds(45, 601, 780, 71);
		contentPane.add(lbl_Success);
		
		JButton btn_AddConsole = new JButton("Add Console");
		btn_AddConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddConsolePage add_consolepage = new AddConsolePage(admin);
				add_consolepage.setVisible(true);
				dispose();
			}
		});
		btn_AddConsole.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_AddConsole.setBounds(913, 344, 160, 33);
		contentPane.add(btn_AddConsole);
		
		JButton btn_AddVersion = new JButton("Add Version");
		btn_AddVersion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddVersionPage add_versionpage = new AddVersionPage(admin);
				add_versionpage.setVisible(true);
				dispose();
			}
		});
		btn_AddVersion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_AddVersion.setBounds(913, 419, 160, 33);
		contentPane.add(btn_AddVersion);
		
		JLabel lbl_background = new JLabel("");
		lbl_background.setIcon(new ImageIcon(background_admin_page));
		lbl_background.setBackground(new Color(255, 128, 128));
		lbl_background.setBounds(10, 11, this.getWidth(), this.getHeight());
		contentPane.add(lbl_background);
	}
}
