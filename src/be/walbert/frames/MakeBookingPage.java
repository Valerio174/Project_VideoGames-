package be.walbert.frames;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.walbert.classes.Booking;
import be.walbert.classes.Player;
import be.walbert.classes.VideoGame;

import javax.swing.JButton;

public class MakeBookingPage extends JFrame {

	private JPanel contentPane;
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	private Image img = new ImageIcon(this.getClass().getResource("/ressources/icon_back.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private JTextField tf_NumberofWeeks;

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
	public MakeBookingPage(VideoGame videogame_selected, Player player) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(30, 30, 716, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		/*Icon de retour (vers la page Admin)*/
		JLabel lbl_icon_back = new JLabel("");
		
		/*Ajout de l'evenement lors du click*/
		lbl_icon_back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CatalogVideoGames catalog = new CatalogVideoGames(player);
				catalog.setVisible(true);
				dispose();
			}
		});
		lbl_icon_back.setBounds(21, 339, 50, 50); 
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
		lbl_icon_logout.setBounds(615, 42, 64, 64); 
		lbl_icon_logout.setIcon(new ImageIcon(icon_logout));
		contentPane.add(lbl_icon_logout);
		
		JLabel lbl_LogOut = new JLabel("Log Out");
		lbl_LogOut.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LogOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_LogOut.setBounds(609, 11, 70, 25);
		contentPane.add(lbl_LogOut);
		
		JLabel lbl_TitleMakeBooking = new JLabel("Please indicate the number of weeks you want to loan:\r\n\r\n");
		lbl_TitleMakeBooking.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		lbl_TitleMakeBooking.setBounds(21, 69, 532, 64);
		contentPane.add(lbl_TitleMakeBooking);
		
		JLabel lbl_NumberofWeeks = new JLabel("Number of weeks (max 5 weeks):");
		lbl_NumberofWeeks.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		lbl_NumberofWeeks.setBounds(21, 197, 280, 50);
		contentPane.add(lbl_NumberofWeeks);
		
		tf_NumberofWeeks = new JTextField();
		tf_NumberofWeeks.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//Ajout de l'evenement lors de la saisie des semaines
		tf_NumberofWeeks.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();	/*Attribuer le caractere saisit a une variable de type char*/
				
				/*Si le caractère choisit est différent d'un nombre OU non compris entre 1 et 4 OU un nombre à 2 chiffres=>IMPOSSIBLE d'ecrire */
				if(!Character.isDigit(c) || c==KeyEvent.VK_DELETE || c=='0' || c> '4' || tf_NumberofWeeks.getText().length() > 0) {
					e.consume();
				}
			}
		});
		tf_NumberofWeeks.setBounds(332, 203, 112, 41);
		contentPane.add(tf_NumberofWeeks);
		tf_NumberofWeeks.setColumns(10);
		
		JButton btn_Booking = new JButton("Booking");
		btn_Booking.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btn_Booking.setBounds(356, 293, 112, 41);
		btn_Booking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tf_NumberofWeeks.getText().equals("")) {
					 JOptionPane.showMessageDialog(contentPane, "Sorry, the field is empty. Enter a number of weeks !");
				}
				else {
					Booking new_booking = new Booking(LocalDate.now(), videogame_selected, player, Integer.valueOf(tf_NumberofWeeks.getText()));
					if(new_booking.CreateBooking()) {
	  	    		  	JOptionPane.showMessageDialog(contentPane, "Great, your booking has been added !");
	  	    		  	CatalogVideoGames catalog = new CatalogVideoGames(player);
	  	    		  	catalog.setVisible(true);
	  	    		  	dispose();
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Sorry, an error has been occured!");
					}
				}
				
			}
		});
		contentPane.add(btn_Booking);
	}
}