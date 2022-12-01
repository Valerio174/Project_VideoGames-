package be.walbert.frames;

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

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyLoansPage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	private Image icon_back = new ImageIcon(this.getClass().getResource("/ressources/icon_back.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private ArrayList<Loan> loans;
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
	public MyLoansPage(Player player) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(30, 30, 756, 532);
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
		lbl_icon_back.setBounds(22, 416, 50, 50); 
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
		lbl_icon_logout.setBounds(597, 34, 70, 90); 
		lbl_icon_logout.setIcon(new ImageIcon(icon_logout));
		contentPane.add(lbl_icon_logout);
		
		JLabel lbl_LogOut = new JLabel("Log Out");
		lbl_LogOut.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LogOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_LogOut.setBounds(575, 0, 99, 50);
		contentPane.add(lbl_LogOut);
		
        JButton btn_EndLoan = new JButton("End Loan");
        btn_EndLoan.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        btn_EndLoan.setBounds(597, 336, 121, 29);
        btn_EndLoan.setVisible(false);
        contentPane.add(btn_EndLoan);
        
		/*Noms des colonnes du tableau*/
		String[] columnsnames = { "Start Date","End Date","Ongoing","Game","Version","Console", "Borrower"};
        
			/*Model du tableau*/
		DefaultTableModel tablemodel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		loans = player.getLender_list();
			/*Attribuer noms des colonnes au tablemodel*/
		tablemodel.setColumnIdentifiers(columnsnames);	
	       for(Loan l: loans){
	           Object[] datas = {l.getStartDate(), l.getEndDate(), l.isOngoing(), l.getCopy().getGame().getName(),l.getCopy().getGame().getVersion(), l.getCopy().getGame().getConsole(), l.getBorrower().getPseudo()};
	           tablemodel.addRow(datas);
	       }
	        
	       /*Creation du curseur du JTable*/
        contentPane.setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(543, 233);
        scrollPane.setLocation(22, 138);
        contentPane.add(scrollPane);
        
        	/*Creation du JTable*/
        table = new JTable(tablemodel);
        scrollPane.setViewportView(table);
        
        /*Ajouter evenement lors du clique sur une ligne*/
	    table.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e){
	    	      if (e.getClickCount() == 1){ 
	    	          btn_EndLoan.setVisible(true);
	    	      }
	    	}
	    });
	    
	    btn_EndLoan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Loan loan_selected = loans.get(table.getSelectedRow());
        		loan_selected = loan_selected.GetLoan();
        		if(loan_selected.isOngoing()!=false) {
        			if(LocalDate.now().isBefore(loan_selected.getEndDate())) {
         				JOptionPane.showMessageDialog(contentPane, "Not yet at the end date !" );
            		}
            		else {
         				if(loan_selected.EndLoan()) {	//Mise a jour de la location dans la base de donnees
         					Booking new_booking = loan_selected.getCopy().getGame().SelectBooking(); 	//Recuperer une reservation pour ce jeux
         					
         					if( new_booking!= null) {
                 				player.UpdateLoan(loan_selected);	//mise à jour de la location pour l'objet courant (pour l'affichage)
                 				Loan new_loan = new Loan(LocalDate.now(), LocalDate.now().plusDays(new_booking.getNumber_weeks()*7), false, new_booking.getPlayer(), player, loan_selected.getCopy());
                 				loan_selected.getCopy().Borrow(new_loan);
                 				
                 				if(new_booking.Delete()) {	//Supprimer la réservation de la DB
                 					player.AddLender(new_loan);//mise à jour de la liste des locations pour l'objet courant (pour l'affichage)
                 					JOptionPane.showMessageDialog(contentPane,  "Great, the loan is over. A booking has been chosen for you game for a new loan");
                     				CatalogVideoGames catalog = new CatalogVideoGames(player);
                        			catalog.setVisible(true);
                        			dispose(); 	
                 				}
         					}
         					else {	//Si il n'y a pas de reservation pour ce jeux
             					JOptionPane.showMessageDialog(contentPane,  "Great, the loan has been ended and there are no bookings for this game for the moment ");
                 				player.UpdateLoan(loan_selected);	//mise à jour de la location pour l'objet courant
                    			CatalogVideoGames catalog = new CatalogVideoGames(player);
                    			catalog.setVisible(true);
                    			dispose(); 	
         					}
                		}
                		else {
                			JOptionPane.showMessageDialog(contentPane, "An error has been occured ! " );
                		}
            		}
        		}
        		else {
     				JOptionPane.showMessageDialog(contentPane, "Sorry the loan is already done !" );

        		}
        	}
        });
	    
        JLabel lbl_Title_ListCopies = new JLabel("The list of your loans:");
        lbl_Title_ListCopies.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lbl_Title_ListCopies.setBounds(22, 77, 452, 47);
        contentPane.add(lbl_Title_ListCopies);
        
	}
}
