package be.walbert.frames;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import be.walbert.classes.Player;
import be.walbert.classes.User;
import be.walbert.classes.VideoGame;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTable;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CatalogVideoGames extends JFrame {

	private JPanel contentPane;  
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	private ArrayList<VideoGame> videogames;
	private JTable table;
	JLabel lbl_register_succed;
	
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
	public CatalogVideoGames(Player player) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1169, 765); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
	 
		JLabel lbl_Username = new JLabel("");
		lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Username.setBounds(47, 33, 1074, 49);
		contentPane.add(lbl_Username);
		lbl_Username.setText("Welcome on the catalog of VideoGames " + player.getUsername());
		
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
        scrollPane.setLocation(47, 180);
        contentPane.add(scrollPane);
        
        	/*Creation du JTable*/
        table = new JTable(tablemodel);
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setFont(new Font("Segoe UI Black", Font.ITALIC, 15));
        lblNewLabel.setBounds(47, 612, 603, 40);
        contentPane.add(lblNewLabel);
        
        /*Ajouter evenement lors du clique sur une ligne*/
	    table.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e){
	    	      if (e.getClickCount() == 2){ 
	    	    	  if(player.LoanAllowed()) {
	 	  				VideoGame game_selected = videogames.get(table.getSelectedRow());
		    	      	GamePage gamepage= new GamePage( player,game_selected);
		  				gamepage.setVisible(true);
		  				dispose();
	    	    	  }
	    	    	  else {
	    	    		  JOptionPane.showMessageDialog(contentPane, "Sorry, you can't make loan because you have a balance of credits less than 0");
	    	    	  }
	    	      }
	    	}
	    });
        
	    lbl_register_succed = new JLabel("");
		lbl_register_succed.setFont(new Font("Segoe UI Black", Font.ITALIC, 25));
		lbl_register_succed.setForeground(new Color(0, 128, 0));
		lbl_register_succed.setBounds(25, 541, 468, 71);
		contentPane.add(lbl_register_succed);
		
		JButton btn_MyCopies = new JButton("My Copies");
		btn_MyCopies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyCopiesPage copies_page = new MyCopiesPage(player);
				copies_page.setVisible(true);
				dispose();
			}
		});
		btn_MyCopies.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_MyCopies.setBounds(860, 209, 127, 40);
		contentPane.add(btn_MyCopies);
		
		JButton btn_MyBorrows = new JButton("My Borrows");
		btn_MyBorrows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyBorrowsPage borrowspage = new MyBorrowsPage(player);
				borrowspage.setVisible(true);
				dispose();
			}
		});
		btn_MyBorrows.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_MyBorrows.setBounds(1010, 209, 135, 40);
		contentPane.add(btn_MyBorrows);
		
		JButton btn_MyLoans = new JButton("My Loans");
		btn_MyLoans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyLoansPage loans_page = new MyLoansPage(player);
				loans_page.setVisible(true);
				dispose();
			}
		});
		btn_MyLoans.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_MyLoans.setBounds(860, 302, 135, 40);
		contentPane.add(btn_MyLoans);
	}
}
