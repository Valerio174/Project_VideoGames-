package be.walbert.frames;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import be.walbert.classes.Copy;
import be.walbert.classes.Player;
import be.walbert.classes.VideoGame;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.JButton;

public class LoanPage extends JFrame {

	private JPanel contentPane;
	private Image img = new ImageIcon(this.getClass().getResource("/ressources/icon_back.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private JDateChooser tf_startdate;
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
		setBounds(100, 100, 1247, 678);
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
				GamePage gamepage = new GamePage( player, current_copy.getGame());
				gamepage.setVisible(true);
				dispose();
			}
		});
		lbl_icon_back.setBounds(21, 572, 50, 50); 
		lbl_icon_back.setIcon(new ImageIcon(img));
		contentPane.add(lbl_icon_back);
		
		JLabel lbl_TitleLoan = new JLabel("Make a loan");
		lbl_TitleLoan.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TitleLoan.setFont(new Font("Segoe UI Black", Font.ITALIC, 35));
		lbl_TitleLoan.setBounds(31, 11, 989, 100);
		contentPane.add(lbl_TitleLoan);
		
		JLabel lblDescriptionOfThe = new JLabel("Description of the copy:");
		lblDescriptionOfThe.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescriptionOfThe.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 25));
		lblDescriptionOfThe.setBounds(31, 122, 295, 61);
		contentPane.add(lblDescriptionOfThe);
		
		JLabel lbl_IdCopy = new JLabel("Id copy:");
		lbl_IdCopy.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_IdCopy.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_IdCopy.setBounds(31, 194, 194, 61);
		contentPane.add(lbl_IdCopy);
		
		JLabel lbl_Game = new JLabel("Game:");
		lbl_Game.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Game.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_Game.setBounds(250, 194, 513, 61);
		contentPane.add(lbl_Game);
		
		JLabel lbl_Lender = new JLabel("Lender:");
		lbl_Lender.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Lender.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_Lender.setBounds(814, 194, 409, 61);
		contentPane.add(lbl_Lender);
		
		JLabel lbl_Credits = new JLabel("Credits:");
		lbl_Credits.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_Credits.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_Credits.setBounds(31, 262, 194, 61);
		contentPane.add(lbl_Credits);
		
		tf_startdate = new JDateChooser();
		tf_startdate.setBounds(208, 389, 161, 34);
		contentPane.add(tf_startdate);
		
		/*Attributions des valeurs de la copy choisie*/
		lbl_IdCopy.setText(lbl_IdCopy.getText() + " " + current_copy.getId_copy());
		lbl_Game.setText(lbl_Game.getText() + " " + current_copy.getGame().getName());
		lbl_Lender.setText(lbl_Lender.getText() + " " + current_copy.getOwner().getPseudo());
		lbl_Credits.setText(lbl_Credits.getText() + " " + current_copy.getGame().getCreditCost());
		JDateChooser tf_enddate = new JDateChooser();
		tf_enddate.setBounds(208, 451, 161, 34);
		contentPane.add(tf_enddate);
		
		JLabel lbl_TitleChooseDate = new JLabel("Choose the dates:");
		lbl_TitleChooseDate.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_TitleChooseDate.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 25));
		lbl_TitleChooseDate.setBounds(31, 317, 295, 61);
		contentPane.add(lbl_TitleChooseDate);
		
		JLabel lbl_StartDate = new JLabel("Start Date:");
		lbl_StartDate.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_StartDate.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_StartDate.setBounds(31, 387, 169, 36);
		contentPane.add(lbl_StartDate);
		
		JLabel lbl_EndDate = new JLabel("End Date:");
		lbl_EndDate.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_EndDate.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_EndDate.setBounds(31, 449, 169, 36);
		contentPane.add(lbl_EndDate);
		
		JButton btn_SendLoan = new JButton("Send Loan");
		btn_SendLoan.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_SendLoan.setBounds(208, 554, 137, 34);
		contentPane.add(btn_SendLoan);
		
	}
}
