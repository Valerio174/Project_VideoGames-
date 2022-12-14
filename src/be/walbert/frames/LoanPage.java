package be.walbert.frames;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import be.walbert.classes.Copy;
import be.walbert.classes.Loan;
import be.walbert.classes.Player;
import be.walbert.classes.VideoGame;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoanPage extends JFrame {

	private JPanel contentPane;
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
	public LoanPage(Player player, Copy current_copy) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(30, 30, 592, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lbl_TitleLoan = new JLabel("Make a loan");
		lbl_TitleLoan.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TitleLoan.setFont(new Font("Segoe UI Black", Font.ITALIC, 35));
		lbl_TitleLoan.setBounds(30, 30, 602, 100);
		contentPane.add(lbl_TitleLoan);
		
		JLabel lblDescriptionOfThe = new JLabel("Description of the copy:");
		lblDescriptionOfThe.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescriptionOfThe.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 25));
		lblDescriptionOfThe.setBounds(31, 122, 295, 61);
		contentPane.add(lblDescriptionOfThe);
		
		JLabel lbl_Game = new JLabel("Game:");
		lbl_Game.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Game.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_Game.setBounds(31, 189, 513, 61);
		contentPane.add(lbl_Game);
		
		JLabel lbl_Lender = new JLabel("Lender:");
		lbl_Lender.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Lender.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_Lender.setBounds(30, 310, 409, 61);
		contentPane.add(lbl_Lender);
		
		JLabel lbl_Credits = new JLabel("Credits:");
		lbl_Credits.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Credits.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_Credits.setBounds(31, 249, 194, 61);
		contentPane.add(lbl_Credits);
		
		/*Attributions des valeurs de la copy choisie*/
		lbl_Game.setText(lbl_Game.getText() + " " + current_copy.getGame().getName());
		lbl_Lender.setText(lbl_Lender.getText() + " " + current_copy.getOwner().getPseudo());
		lbl_Credits.setText(lbl_Credits.getText() + " " + current_copy.getGame().getCreditCost());
		
		JDateChooser tf_enddate = new JDateChooser();
		tf_enddate.setBounds(174, 436, 161, 34);
		contentPane.add(tf_enddate);
		
		JLabel lbl_TitleChooseDate = new JLabel("Choose the dates:");
		lbl_TitleChooseDate.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_TitleChooseDate.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 25));
		lbl_TitleChooseDate.setBounds(31, 362, 295, 61);
		contentPane.add(lbl_TitleChooseDate);
		
		JLabel lbl_EndDate = new JLabel("End Date:");
		lbl_EndDate.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_EndDate.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_EndDate.setBounds(31, 434, 137, 36);
		contentPane.add(lbl_EndDate);
		
		JLabel lbl_ErrorEmpty = new JLabel("");
		lbl_ErrorEmpty.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_ErrorEmpty.setBounds(30, 499, 418, 29);
		contentPane.add(lbl_ErrorEmpty);
		
		JButton btn_SendLoan = new JButton("Send Loan");
		btn_SendLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(tf_enddate.getDate() == null) {
					lbl_ErrorEmpty.setText("End date is empty !");
				}
				else {
					LocalDate end_date = tf_enddate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					
					if(end_date.isEqual(LocalDate.now())) {
						lbl_ErrorEmpty.setText("Date must be different of today!");
					}
					else if(end_date.isBefore(LocalDate.now())){
						lbl_ErrorEmpty.setText("Date must be after today!");
					}
					else {
						Loan new_loan = new Loan(LocalDate.now(), tf_enddate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), true, player, current_copy.getOwner(), current_copy );
						 
						current_copy.Borrow(new_loan);
						player.AddBorrow(new_loan);	/*Ajout de la location pour l'afficher dans sa liste*/
						CatalogVideoGames catalog = new CatalogVideoGames(player);
						catalog.setVisible(true);
						catalog.lbl_register_succed.setText("Good, you have successfully completed a loan !");
						dispose();
					}
				}
			}
		});
		btn_SendLoan.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_SendLoan.setBounds(393, 436, 137, 34);
		contentPane.add(btn_SendLoan); 
		
	}
}
