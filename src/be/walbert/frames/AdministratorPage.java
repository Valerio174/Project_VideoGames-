package be.walbert.frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.walbert.classes.User;

import javax.swing.JLabel;
import java.awt.Font;

public class AdministratorPage extends JFrame {

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
	}
}
