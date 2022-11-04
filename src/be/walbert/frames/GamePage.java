package be.walbert.frames;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import be.walbert.classes.Copy;
import be.walbert.classes.Player;
import be.walbert.classes.User;
import be.walbert.classes.VideoGame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;

public class GamePage extends JFrame {

	private JPanel contentPane;
	private Image img = new ImageIcon(this.getClass().getResource("/ressources/icon_back.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private ArrayList<Copy> listcopy;
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
	 * @param id_videogame 
	 */
	public GamePage(Player player, VideoGame videogame_selected) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1241, 694); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*Icon de retour (vers catalogue des jeux videos)*/
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
		lbl_icon_back.setBounds(21, 572, 50, 50); 
		lbl_icon_back.setIcon(new ImageIcon(img));
		contentPane.add(lbl_icon_back);
		
		/*Déclaration des elements de la JFrame*/
		JLabel lbl_TitleDescription = new JLabel("Description of the game");
		lbl_TitleDescription.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TitleDescription.setFont(new Font("Segoe UI Black", Font.ITALIC, 35));
		lbl_TitleDescription.setBounds(31, 11, 989, 100);
		contentPane.add(lbl_TitleDescription);
		
		JLabel lbl_TitleName = new JLabel("Name:");
		lbl_TitleName.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
		lbl_TitleName.setBounds(21, 109, 106, 32);
		contentPane.add(lbl_TitleName);
		
		JLabel lbl_TitleConsole = new JLabel("Console:");
		lbl_TitleConsole.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
		lbl_TitleConsole.setBounds(21, 169, 106, 32);
		contentPane.add(lbl_TitleConsole);
		
		JLabel lbl_TitleVersion = new JLabel("Version:");
		lbl_TitleVersion.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
		lbl_TitleVersion.setBounds(21, 228, 106, 32);
		contentPane.add(lbl_TitleVersion);
		
		JLabel lbl_GameName = new JLabel("");
		lbl_GameName.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
		lbl_GameName.setBounds(148, 113, 361, 32);
		contentPane.add(lbl_GameName);
		
		JLabel lbl_GameConsole = new JLabel("");
		lbl_GameConsole.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
		lbl_GameConsole.setBounds(148, 173, 361, 32);
		contentPane.add(lbl_GameConsole);
		
		JLabel lbl_GameVersion = new JLabel("");
		lbl_GameVersion.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
		lbl_GameVersion.setBounds(148, 232, 361, 32);
		contentPane.add(lbl_GameVersion);
		
		JButton btn_LoanGame = new JButton("Loan game");
		btn_LoanGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_LoanGame.setBounds(341, 572, 136, 32);
		contentPane.add(btn_LoanGame);
		btn_LoanGame.setVisible(false);
		
		JButton btn_BookingGame = new JButton("Book game");
		btn_BookingGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_BookingGame.setBounds(341, 572, 136, 32);
		contentPane.add(btn_BookingGame);
		btn_BookingGame.setVisible(false);
		
		JLabel lbl_TitleCredits = new JLabel("Cost:");
		lbl_TitleCredits.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
		lbl_TitleCredits.setBounds(21, 288, 106, 32);
		contentPane.add(lbl_TitleCredits);
		
		JLabel lbl_GameCredits = new JLabel((String) null);
		lbl_GameCredits.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
		lbl_GameCredits.setBounds(148, 288, 361, 32);
		contentPane.add(lbl_GameCredits);
        
		
		JLabel lbl_Loan_Or_Book = new JLabel("");
		lbl_Loan_Or_Book.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		lbl_Loan_Or_Book.setBounds(21, 396, 633, 38);
		contentPane.add(lbl_Loan_Or_Book);
		
		Copy copy = new Copy(player,videogame_selected);	
    	   if(copy.IsAvailable()) {
    		   lbl_Loan_Or_Book.setText("Great, you can loan a copy");
    		   btn_LoanGame.setVisible(true);
    	   	}
    	 	else {
    	 		lbl_Loan_Or_Book.setText("Sorry there are no more copies. You can make a booking");
    	   		btn_BookingGame.setVisible(true);
    	    }		
    
		/*Attribuer les infos du jeux aux différents labels*/
		lbl_GameName.setText(videogame_selected.getName());
		lbl_GameConsole.setText(videogame_selected.getConsole());
		lbl_GameVersion.setText(videogame_selected.getVersion());
		lbl_GameCredits.setText(videogame_selected.getCreditCost()+ " Credits");

		
	}
}
