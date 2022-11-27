package be.walbert.frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import be.walbert.classes.Booking;
import be.walbert.classes.Copy;
import be.walbert.classes.Loan;
import be.walbert.classes.Player;
import be.walbert.classes.VideoGame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddCopyPage extends JFrame {

	private JPanel contentPane;
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	private Image icon_back = new ImageIcon(this.getClass().getResource("/ressources/icon_back.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private ArrayList<VideoGame> videogames;
	private JTable table;
	
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
	public AddCopyPage(Player player) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1176, 690);
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
				MyCopiesPage copiespage = new MyCopiesPage(player);
				copiespage.setVisible(true);
				dispose();
			}
		});
		lbl_icon_back.setBounds(20, 592, 50, 50); 
		lbl_icon_back.setIcon(new ImageIcon(icon_back));
		contentPane.add(lbl_icon_back);
		
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
        scrollPane.setSize(780, 332);
        scrollPane.setLocation(47, 180);
        contentPane.add(scrollPane);
        
        	/*Creation du JTable*/
        table = new JTable(tablemodel);
        scrollPane.setViewportView(table);
        
        JLabel lbl_TitleAddCopy = new JLabel("Please choose a game to add a copy:");
        lbl_TitleAddCopy.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 18));
        lbl_TitleAddCopy.setBounds(47, 139, 463, 30);
        contentPane.add(lbl_TitleAddCopy);
        
        JButton btn_AddCopy = new JButton("Add a copy");
        
        btn_AddCopy.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        btn_AddCopy.setBounds(859, 482, 147, 30);
        btn_AddCopy.setVisible(false);
        contentPane.add(btn_AddCopy);
        
        /*Ajouter evenement lors du clique sur une ligne*/
	    table.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e){
	    	      if (e.getClickCount() == 1){ 
	    	    	  btn_AddCopy.setVisible(true);
	    	    	  VideoGame game_selected = videogames.get(table.getSelectedRow());
	    	    	  Copy new_copy = new Copy(player,game_selected);
	    	    	  
	    	    	  btn_AddCopy.addActionListener(new ActionListener() {
	    	    		 public void actionPerformed(ActionEvent e) {
	    	          		if(new_copy.CreateCopy()) {	//Ajout de la Copy en DB
	    	          			player.AddCopy(new_copy);		//Mettre à jour la liste des Copy du palyer (pour affichage)
	    	          			Booking new_booking = new_copy.getGame().SelectBooking(); 	//Recuperer une reservation pour ce jeux
	    	          			
	    	          			if(new_booking!= null) {			//Si il y a une ou plusieurs reservation en attente pour ce jeux
		                 			  Loan new_loan = new Loan(LocalDate.now(), LocalDate.now().plusDays(new_booking.getNumber_weeks()*7), false, new_booking.getPlayer(), player, new_copy);	//Creer une location avec cette copy du jeux, le player et les infos de la reservation	
		    	          			  new_copy.Borrow(new_loan);		//Ajouter cette location créée dans la DB
		    	          			
		    	          			  if(new_booking.Delete()) {	//Supprimer la réservation de la DB
	                 					player.AddLender(new_loan);//mise à jour de la liste des locations pour l'objet courant (pour l'affichage)
	                        			JOptionPane.showMessageDialog(contentPane, "Great you have been added a new copy of the this game and a booking has been already choosen for you copy!");
	      	  	    	    		  	MyCopiesPage copiespage = new MyCopiesPage(player);
	      	  	    	    		  	copiespage.setVisible(true);
	      	  	    	    		  	dispose();
		    	          			}  
		    	          			  else {
		    	  	    	    		  JOptionPane.showMessageDialog(contentPane, "Sorry, an error has been occured during the deletion of the booking!");

		    	          			  }
	    	          			}
	    	          			else{
		         					  JOptionPane.showMessageDialog(contentPane, "Great you have been added a new copy of the this game !");
		  	  	    	    		  MyCopiesPage copiespage = new MyCopiesPage(player);
		  	  	    	    		  copiespage.setVisible(true);
		  	  	    	    		  dispose();
		         				}
	    	          		}
	    	          		else {
	  	    	    		  JOptionPane.showMessageDialog(contentPane, "Sorry, an error has been occured during the creation!");

	    	          		}
	    	          	}
	    	          });
	    	      }
	    	}
	    }); 
		
	    
	    
	    
	    
	    
	}
}
