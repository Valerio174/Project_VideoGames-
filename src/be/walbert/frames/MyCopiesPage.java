package be.walbert.frames;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import be.walbert.classes.Copy;
import be.walbert.classes.Player;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyCopiesPage extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Image icon_logout = new ImageIcon(this.getClass().getResource("/ressources/icon_logout.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
	private Image icon_back = new ImageIcon(this.getClass().getResource("/ressources/icon_back.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

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
	public MyCopiesPage(Player player) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(30, 30, 714, 566);
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
		lbl_icon_back.setBounds(22, 452, 50, 50); 
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
		lbl_icon_logout.setBounds(545, 34, 70, 90); 
		lbl_icon_logout.setIcon(new ImageIcon(icon_logout));
		contentPane.add(lbl_icon_logout);
		
		JLabel lbl_LogOut = new JLabel("Log Out");
		lbl_LogOut.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LogOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_LogOut.setBounds(502, 0, 99, 50);
		contentPane.add(lbl_LogOut);
		
			/*Noms des colonnes du tableau*/
		String[] columnsnames = { "Name", "Console","Version","Credits"};
        
			/*Model du tableau*/
		DefaultTableModel tablemodel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
			/*Attribuer noms des colonnes au tablemodel*/
		tablemodel.setColumnIdentifiers(columnsnames);	
	       for(Copy c: player.getCopy_list()){
	           Object[] datas = {c.getGame().getName(), c.getGame().getConsole(), c.getGame().getVersion(), c.getGame().getCreditCost()};
	           tablemodel.addRow(datas);
	       }
	        
	       /*Creation du curseur du JTable*/
        contentPane.setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(452, 269);
        scrollPane.setLocation(22, 138);
        contentPane.add(scrollPane);
        
        	/*Creation du JTable*/
        table = new JTable(tablemodel);
        scrollPane.setViewportView(table);
        
        JLabel lbl_Title_ListCopies = new JLabel("The list of your copies:");
        lbl_Title_ListCopies.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lbl_Title_ListCopies.setBounds(22, 77, 452, 47);
        contentPane.add(lbl_Title_ListCopies);
        
        JButton btn_AddCopy = new JButton("Add New Copy");
        btn_AddCopy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AddCopyPage copypage = new AddCopyPage(player);
        		copypage.setVisible(true);
        		dispose();
        	}
        });
        btn_AddCopy.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        btn_AddCopy.setBounds(502, 378, 153, 29);
        contentPane.add(btn_AddCopy);
        
	}
}
