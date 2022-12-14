package be.walbert.frames;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Panel;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomePage extends JFrame {

	private JPanel contentPane;
	private Image img = new ImageIcon(this.getClass().getResource("/ressources/home_page_background.jpg")).getImage().getScaledInstance(644, 472, Image.SCALE_SMOOTH);
	JLabel lbl_register_succed;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage frame = new HomePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(30, 30, 1211, 545);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(40, 40, 40));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_image_HomePage_Consoles = new JLabel("");
		lbl_image_HomePage_Consoles.setBounds(0, 0, 644, 382); 
		lbl_image_HomePage_Consoles.setIcon(new ImageIcon(img));
		contentPane.add(lbl_image_HomePage_Consoles);
		
		Panel panel = new Panel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(645, 0, 618, 508);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btn_Signin = new JButton("Sign in");
		btn_Signin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignInPage signinpage = new SignInPage();
				signinpage.setVisible(true);
				dispose();
			}
		});
		btn_Signin.setForeground(new Color(255, 255, 255));
		btn_Signin.setBackground(new Color(92, 92, 92));
		btn_Signin.setFont(new Font("Sitka Small", Font.PLAIN, 20));
		btn_Signin.setBounds(315, 195, 185, 58);
		panel.add(btn_Signin);
		
		JLabel lbl_titleForm = new JLabel("What do you want to do?");
		lbl_titleForm.setBackground(new Color(234, 234, 234));
		lbl_titleForm.setFont(new Font("Sitka Small", Font.BOLD | Font.ITALIC, 35));
		lbl_titleForm.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_titleForm.setBounds(47, 31, 468, 71);
		panel.add(lbl_titleForm);
		lbl_titleForm.setOpaque(true);
		
		JButton btn_Login = new JButton("Log in");
		btn_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogInPage login_page = new LogInPage();
				login_page.setVisible(true);
				dispose();
			}
		});
		btn_Login.setForeground(Color.WHITE);
		btn_Login.setFont(new Font("Sitka Small", Font.PLAIN, 20));
		btn_Login.setBackground(new Color(92, 92, 92));
		btn_Login.setBounds(63, 195, 185, 58);
		panel.add(btn_Login);
		
		lbl_register_succed = new JLabel("");
		lbl_register_succed.setFont(new Font("Segoe UI Black", Font.ITALIC, 25));
		lbl_register_succed.setForeground(new Color(0, 128, 0));
		lbl_register_succed.setBounds(25, 541, 468, 71);
		panel.add(lbl_register_succed);
		
		JLabel lbl_home_page_title_Welcome2 = new JLabel("on");
		lbl_home_page_title_Welcome2.setVerticalAlignment(SwingConstants.TOP);
		lbl_home_page_title_Welcome2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_home_page_title_Welcome2.setFont(new Font("Sitka Small", Font.PLAIN, 30));
		lbl_home_page_title_Welcome2.setForeground(new Color(255, 255, 255));
		lbl_home_page_title_Welcome2.setBounds(0, 558, 644, 38);
		contentPane.add(lbl_home_page_title_Welcome2);
		
		JLabel lbl_home_page_title_Welcome3 = new JLabel("Video Games Loan Website");
		lbl_home_page_title_Welcome3.setVerticalAlignment(SwingConstants.TOP);
		lbl_home_page_title_Welcome3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_home_page_title_Welcome3.setForeground(Color.WHITE);
		lbl_home_page_title_Welcome3.setFont(new Font("Sitka Small", Font.BOLD | Font.ITALIC, 35));
		lbl_home_page_title_Welcome3.setBackground(SystemColor.menu);
		lbl_home_page_title_Welcome3.setBounds(0, 607, 644, 88);
		contentPane.add(lbl_home_page_title_Welcome3);
	}
}
