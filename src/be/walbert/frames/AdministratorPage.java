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
import be.walbert.classes.User;
import be.walbert.classes.VideoGame;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class AdministratorPage extends JFrame {

	private JPanel contentPane;
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	private ArrayList<VideoGame> videogames;
	private JTable table;
	JLabel lbl_SuccessUpdate;

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
	public AdministratorPage(Administrator admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1145, 738);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_WelcomeAdmin = new JLabel("New label");
		lbl_WelcomeAdmin.setFont(new Font("Segoe UI Black", Font.PLAIN, 20));
		lbl_WelcomeAdmin.setBounds(45, 48, 888, 81);
		contentPane.add(lbl_WelcomeAdmin);
		
		lbl_WelcomeAdmin.setText("Welcome admin " + admin.getUsername());
		
		/*Liste des jeux videos*/
		videogames = VideoGame.getAll();
		
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
	    	    	  int selectedRow = table.getSelectedRow();
	    	    	  	VideoGame videogame_selected = VideoGame.GetVideoGame((String) table.getValueAt(selectedRow, 0), (String)table.getValueAt(selectedRow, 2));
	  					SetCreditsPage creditspage = new SetCreditsPage(admin, videogame_selected);
	  					creditspage.setVisible(true);
	  					dispose();
	    	         }
	    	      }
	    });
	    
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
		lbl_icon_logout.setBounds(1051, 48, 70, 90); 
		lbl_icon_logout.setIcon(new ImageIcon(icon_logout));
		contentPane.add(lbl_icon_logout);
		
		JLabel lbl_LogOut = new JLabel("Log Out");
		lbl_LogOut.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LogOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_LogOut.setBounds(1022, 11, 99, 50);
		contentPane.add(lbl_LogOut);
		
		JLabel lbl_WelcomeAdmin_1 = new JLabel("Please choose game to modify his credits:");
		lbl_WelcomeAdmin_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		lbl_WelcomeAdmin_1.setBounds(45, 140, 780, 81);
		contentPane.add(lbl_WelcomeAdmin_1);
		
		JButton btnNewButton = new JButton("Add Game");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(913, 270, 130, 33);
		contentPane.add(btnNewButton);
		
		lbl_SuccessUpdate = new JLabel("");
		lbl_SuccessUpdate.setFont(new Font("Segoe UI Black", Font.ITALIC, 20));
		lbl_SuccessUpdate.setForeground(new Color(0, 128, 0));
		lbl_SuccessUpdate.setBounds(45, 619, 580, 71);
		contentPane.add(lbl_SuccessUpdate);
	}
}
