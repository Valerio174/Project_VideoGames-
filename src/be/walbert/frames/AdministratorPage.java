package be.walbert.frames;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.walbert.classes.User;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdministratorPage extends JFrame {

	private JPanel contentPane;
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	

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
	public AdministratorPage(User admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1145, 738);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_WelcomeAdmin = new JLabel("New label");
		lbl_WelcomeAdmin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_WelcomeAdmin.setBounds(45, 48, 888, 81);
		contentPane.add(lbl_WelcomeAdmin);
		
		lbl_WelcomeAdmin.setText("Welcome admin " + admin.getUsername());
		
		JLabel lbl_icon_logout = new JLabel("");
		lbl_icon_logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HomePage homepage = new HomePage();
				homepage.setVisible(true);
				dispose();
			}
		});
		lbl_icon_logout.setBounds(31, 576, 70, 90); 
		lbl_icon_logout.setIcon(new ImageIcon(icon_logout));
		contentPane.add(lbl_icon_logout);
	}
}
