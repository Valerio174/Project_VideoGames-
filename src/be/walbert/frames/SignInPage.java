package be.walbert.frames;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import be.walbert.classes.Player;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;

import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import java.awt.TextField;
import java.awt.Button;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignInPage extends JFrame {

	private JPanel contentPane;
	private JTextField tf_Username;
	private JTextField tf_Pseudo;
	private JTextField tf_Password;
	private JDateChooser tf_date;

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
	public SignInPage() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1193, 711);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		     
		Image background_signin_page = new ImageIcon(this.getClass().getResource("/ressources/Signin_background.jpg")).getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
		
		JLabel lbl_Password = new JLabel("Password");
		lbl_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Password.setBounds(53, 378, 105, 24);
		contentPane.add(lbl_Password);
		
		JLabel lbl_Username = new JLabel("Username");
		lbl_Username.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lbl_Username.setBounds(53, 177, 105, 29);
		contentPane.add(lbl_Username);
		
		JLabel lbl_Pseudo = new JLabel("Pseudo");
		lbl_Pseudo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Pseudo.setBounds(53, 268, 105, 29);
		contentPane.add(lbl_Pseudo);
		
		JLabel lbl_dateOfBirth = new JLabel("Date of Birth");
		lbl_dateOfBirth.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_dateOfBirth.setBounds(53, 480, 148, 24);
		contentPane.add(lbl_dateOfBirth);
		
		JLabel lbl_TitleSignInForm = new JLabel("WELCOME NEW GAMER");
		lbl_TitleSignInForm.setForeground(new Color(0, 0, 0));
		lbl_TitleSignInForm.setFont(new Font("Segoe UI Black", Font.BOLD, 29));
		lbl_TitleSignInForm.setBounds(80, 61, 363, 53);
		contentPane.add(lbl_TitleSignInForm);
		
		tf_Username = new JTextField();
		tf_Username.setBounds(211, 185, 148, 24);
		contentPane.add(tf_Username);
		tf_Username.setColumns(10);
		
		tf_Pseudo = new JTextField();
		tf_Pseudo.setColumns(10);
		tf_Pseudo.setBounds(211, 276, 148, 24);
		contentPane.add(tf_Pseudo);
		
		tf_Password = new JTextField();
		tf_Password.setColumns(10);
		tf_Password.setBounds(211, 384, 148, 24);
		contentPane.add(tf_Password);
		
		tf_date = new JDateChooser();
		tf_date.setBounds(211, 480, 148, 24);
		contentPane.add(tf_date);
		
		JButton btnNewButton = new JButton("Sign In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Player p = new Player(tf_Username.getText(),tf_Password.getText(),10,tf_Pseudo.getText(),
						LocalDate.now(),tf_date.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				p.SignIn(p);
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(211, 578, 105, 35);
		contentPane.add(btnNewButton);

		JLabel lbl_background = new JLabel("");
		lbl_background.setIcon(new ImageIcon(background_signin_page));
		lbl_background.setBackground(new Color(255, 128, 128));
		lbl_background.setBounds(10, 11, this.getWidth(), this.getHeight());
		contentPane.add(lbl_background);
	}
}
