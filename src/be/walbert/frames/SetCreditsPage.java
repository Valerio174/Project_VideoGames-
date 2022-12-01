package be.walbert.frames;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.walbert.classes.Administrator;
import be.walbert.classes.HistoryCredits;
import be.walbert.classes.VideoGame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class SetCreditsPage extends JFrame {

	/*Variables*/
	private JPanel contentPane;
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	private JTextField tf_NewCredits;
	private Image img = new ImageIcon(this.getClass().getResource("/ressources/icon_back.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

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
	public SetCreditsPage(Administrator admin, VideoGame videogame_selected) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(30, 30, 676, 609);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*Icon de retour (vers la page Admin)*/
		JLabel lbl_icon_back = new JLabel("");
		
		/*Ajout de l'evenement lors du click*/
		lbl_icon_back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdministratorPage adminpage = new AdministratorPage(admin);
				adminpage.setVisible(true);
				dispose();
			}
		});
		lbl_icon_back.setBounds(21, 507, 50, 50); 
		lbl_icon_back.setIcon(new ImageIcon(img));
		contentPane.add(lbl_icon_back);
		
		
		JLabel lbl_TitleModify = new JLabel("Modify credits of a game");
		lbl_TitleModify.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TitleModify.setFont(new Font("Segoe UI Black", Font.PLAIN, 25));
		lbl_TitleModify.setBounds(86, 11, 395, 97);
		contentPane.add(lbl_TitleModify);
		
		JLabel lbl_LogOut = new JLabel("Log Out");
		lbl_LogOut.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LogOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_LogOut.setBounds(502, 0, 99, 50);
		contentPane.add(lbl_LogOut);
		
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
		lbl_icon_logout.setBounds(528, 40, 70, 90); 
		lbl_icon_logout.setIcon(new ImageIcon(icon_logout));
		contentPane.add(lbl_icon_logout);
		
		JLabel lbl_Name = new JLabel("Name: ");
		lbl_Name.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		lbl_Name.setBounds(21, 141, 593, 41);
		lbl_Name.setText(lbl_Name.getText() + videogame_selected.getName());
		contentPane.add(lbl_Name);
		
		JLabel lbl_Console = new JLabel("Console: ");
		lbl_Console.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		lbl_Console.setBounds(21, 217, 593, 41);
		lbl_Console.setText(lbl_Console.getText() + videogame_selected.getConsole());
		contentPane.add(lbl_Console);
		
		JLabel lbl_Version = new JLabel("Version: ");
		lbl_Version.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		lbl_Version.setBounds(21, 299, 593, 41);
		lbl_Version.setText(lbl_Version.getText() + videogame_selected.getVersion());
		contentPane.add(lbl_Version);
		
		JLabel lbl_CurrentCredits = new JLabel("Current credits: ");
		lbl_CurrentCredits.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		lbl_CurrentCredits.setBounds(21, 383, 216, 41);
		lbl_CurrentCredits.setText(lbl_CurrentCredits.getText() + videogame_selected.getCreditCost());
		contentPane.add(lbl_CurrentCredits);
		
		JLabel lbl_CurrentCredits_1 = new JLabel("New credits  (min 1/max 5):");
		lbl_CurrentCredits_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		lbl_CurrentCredits_1.setBounds(251, 383, 267, 41);
		contentPane.add(lbl_CurrentCredits_1);
		
		JLabel lbl_FieldEmpty = new JLabel("");
		lbl_FieldEmpty.setForeground(new Color(166, 0, 0));
		lbl_FieldEmpty.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_FieldEmpty.setBounds(373, 299, 225, 41);
		contentPane.add(lbl_FieldEmpty);
		
		JLabel lbl_ErrorUpdate = new JLabel("");
		lbl_ErrorUpdate.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 20));
		lbl_ErrorUpdate.setForeground(new Color(166, 0, 0));
		lbl_ErrorUpdate.setBounds(21, 452, 371, 41);
		contentPane.add(lbl_ErrorUpdate);
		
		tf_NewCredits = new JTextField();
		tf_NewCredits.setFont(new Font("Tahoma", Font.PLAIN, 18));
		//Ajout de l'evenement lors de la saisie des nouveau credits
		tf_NewCredits.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();	/*Attribuer le caractere saisit a une variable de type char*/
				
				/*Si le caractère choisit est différent d'un nombre OU non compris entre 1 et 5 OU un nombre à 2 chiffres=>IMPOSSIBLE d'ecrire */
				if(!Character.isDigit(c) || c==KeyEvent.VK_DELETE || c=='0' || c> '5' || tf_NewCredits.getText().length() > 0) {
					e.consume();
				}
			}
		});
		tf_NewCredits.setBounds(528, 385, 112, 41);
		contentPane.add(tf_NewCredits);
		tf_NewCredits.setColumns(10);
		
		/*Ajout de l'evenement lors du click sur le bouton Set Credits*/
		JButton btn_SetCredits = new JButton("Set Credits");
		btn_SetCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tf_NewCredits.getText().equals("")) {
					lbl_FieldEmpty.setText("Field is empty !");
				}
				else {
					HistoryCredits history = new HistoryCredits(LocalDate.now(), videogame_selected.getCreditCost(),Integer.parseInt(tf_NewCredits.getText()) ,
							videogame_selected);
					if(history.ModifyCredits()) {	/*Si la mise à jour des credits s'est faite correctement*/
						videogame_selected.setCreditCost(history.getNew_creditCost());
						videogame_selected.UpdateGame();
						AdministratorPage adminpage = new AdministratorPage(admin);
						adminpage.setVisible(true);
						adminpage.lbl_Success.setText("Great you have updated the credits !");
						dispose();
					}
					else {	/*Si une erreur a eu lieu lors de la maj(si la methode renvoie false)*/
						lbl_ErrorUpdate.setText("Sorry an error occurred during the update");
					}
				}
				
			}
		});
		btn_SetCredits.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		btn_SetCredits.setBounds(502, 452, 138, 41);
		contentPane.add(btn_SetCredits);
		
	}
}
