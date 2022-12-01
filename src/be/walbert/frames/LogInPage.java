package be.walbert.frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.walbert.DAO.DAO;
import be.walbert.DAO.UserDAO;
import be.walbert.classes.Administrator;
import be.walbert.classes.Player;
import be.walbert.classes.User;

import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class LogInPage extends JFrame {

	private JPanel contentPane;
	private Image img = new ImageIcon(this.getClass().getResource("/ressources/home_page_background.jpg")).getImage().getScaledInstance(644, 472, Image.SCALE_SMOOTH);
	private JTextField tf_username;
	private JTextField tf_password;

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
	public LogInPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(30, 30, 1199, 562);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(40, 40, 40));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		 
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*Image de couverture (3 consoles)*/
		JLabel lbl_image_HomePage_Consoles = new JLabel("");
		lbl_image_HomePage_Consoles.setBounds(0, 0, 644, 340); 
		lbl_image_HomePage_Consoles.setIcon(new ImageIcon(img));
		contentPane.add(lbl_image_HomePage_Consoles);
		
		/*Titre Welcome*/
		JLabel lbl_home_page_title_Welcome = new JLabel("Welcome");
		lbl_home_page_title_Welcome.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_home_page_title_Welcome.setBackground(new Color(240, 240, 240));
		lbl_home_page_title_Welcome.setForeground(new Color(255, 255, 255));
		lbl_home_page_title_Welcome.setFont(new Font("Sitka Small", Font.BOLD, 45));
		lbl_home_page_title_Welcome.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_home_page_title_Welcome.setBounds(0, 349, 644, 88);
		contentPane.add(lbl_home_page_title_Welcome);
		
		/*Ajout du panel de couleur*/
		Panel panel = new Panel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(645, 0, 581, 721);
		contentPane.add(panel);
		panel.setLayout(null);
		
		/*Label de username*/
		JLabel lbl_username = new JLabel("Username :");
		lbl_username.setFont(new Font("Sitka Small", Font.BOLD, 25));
		lbl_username.setBounds(26, 150, 156, 34);
		panel.add(lbl_username);
		
		/*Label de password*/
		JLabel lbl_password = new JLabel("Password :");
		lbl_password.setToolTipText("");
		lbl_password.setFont(new Font("Sitka Small", Font.BOLD, 25));
		lbl_password.setBounds(26, 244, 156, 34);
		panel.add(lbl_password);
		
		/*Saisie du username*/
		tf_username = new JTextField();
		tf_username.setFont(new Font("Sitka Small", Font.PLAIN, 25));
		tf_username.setBounds(256, 150, 246, 34);
		panel.add(tf_username);
		tf_username.setColumns(10);
		
		/*Saisie du password*/
		tf_password = new JPasswordField();
		tf_password.setFont(new Font("Sitka Small", Font.PLAIN, 25));
		tf_password.setColumns(10);
		tf_password.setBounds(256, 244, 246, 34);
		panel.add(tf_password);
		
		/*Zone d'affichage*/
		JLabel lbl_error_users = new JLabel("");
		lbl_error_users.setForeground(new Color(185, 0, 0));
		lbl_error_users.setFont(new Font("Sitka Small", Font.BOLD | Font.ITALIC, 25));
		lbl_error_users.setBounds(26, 449, 446, 48);
		panel.add(lbl_error_users);
		
		/*Bouton Login pour se connecter*/
		JButton btn_Login = new JButton("Log In");
		btn_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = User.GetUser(tf_username.getText(), tf_password.getText());
				
				if(user != null) {
					if(user instanceof Player) {
							Player new_player = (Player)user;
								CatalogVideoGames catalog = new CatalogVideoGames(new_player);
								catalog.setVisible(true);
								dispose(); //Efface 1ere fenêtre 
 					}
 				
					else {
						if(user instanceof Administrator) {
								Administrator new_administrator = (Administrator)user;
								AdministratorPage administratorPage = new AdministratorPage(new_administrator);
								administratorPage.setVisible(true);
								dispose(); //Efface 1ere fenêtre 
						}
					}
				}
				else {
					lbl_error_users.setText("Not users found ! ");
				}
				
			}
		});
		btn_Login.setForeground(new Color(255, 255, 255));
		btn_Login.setBackground(new Color(92, 92, 92));
		btn_Login.setFont(new Font("Sitka Small", Font.PLAIN, 25));
		btn_Login.setBounds(256, 339, 246, 48);
		panel.add(btn_Login);
		
		/*Bouton Back pour retourner vers la Home Page*/
		JButton btn_BackHome = new JButton("Back");
		btn_BackHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomePage homepage = new HomePage();
				homepage.setVisible(true);
				dispose();
			}
		});
		btn_BackHome.setForeground(Color.WHITE);
		btn_BackHome.setFont(new Font("Sitka Small", Font.PLAIN, 25));
		btn_BackHome.setBackground(new Color(147, 0, 0));
		btn_BackHome.setBounds(26, 368, 142, 48);
		panel.add(btn_BackHome);
		
		/*Label sous-titre "on"*/
		JLabel lbl_home_page_title_Welcome2 = new JLabel("on");
		lbl_home_page_title_Welcome2.setVerticalAlignment(SwingConstants.TOP);
		lbl_home_page_title_Welcome2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_home_page_title_Welcome2.setFont(new Font("Sitka Small", Font.PLAIN, 30));
		lbl_home_page_title_Welcome2.setForeground(new Color(255, 255, 255));
		lbl_home_page_title_Welcome2.setBounds(0, 427, 644, 38);
		contentPane.add(lbl_home_page_title_Welcome2);
		
		/*Label Video Games Loan Website*/
		JLabel lbl_home_page_title_Welcome3 = new JLabel("Video Games Loan Website");
		lbl_home_page_title_Welcome3.setVerticalAlignment(SwingConstants.TOP);
		lbl_home_page_title_Welcome3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_home_page_title_Welcome3.setForeground(Color.WHITE);
		lbl_home_page_title_Welcome3.setFont(new Font("Sitka Small", Font.BOLD | Font.ITALIC, 35));
		lbl_home_page_title_Welcome3.setBackground(SystemColor.menu);
		lbl_home_page_title_Welcome3.setBounds(0, 458, 644, 88);
		contentPane.add(lbl_home_page_title_Welcome3);
		
	}
}
