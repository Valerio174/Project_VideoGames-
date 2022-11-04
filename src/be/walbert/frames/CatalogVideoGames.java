package be.walbert.frames;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import be.walbert.classes.Player;
import be.walbert.classes.User;
import be.walbert.classes.VideoGame;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTable;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;

public class CatalogVideoGames extends JFrame {

	private JPanel contentPane;  
	private ArrayList<VideoGame> videogames;
	private JTable table;
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
	public CatalogVideoGames(Player player) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1169, 765); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
	 
		JLabel lbl_Username = new JLabel("");
		lbl_Username.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lbl_Username.setBounds(47, 33, 1074, 49);
		contentPane.add(lbl_Username);
		lbl_Username.setText("Welcome on the catalog of VideoGames " + player.getUsername());
		
		/*Label de test*/
		JLabel lbl_TEST = new JLabel("");
		lbl_TEST.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_TEST.setBounds(96, 663, 252, 54);
		contentPane.add(lbl_TEST);
		
		
		/*Liste des jeux videos*/
		videogames = VideoGame.getAll();
		
			/*Noms des colonnes du tableau*/
		String[] columnsnames = {"Id", "Name", "Console","Version","Credits"};
        
			/*Model du tableau*/
		DefaultTableModel tablemodel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
			/*Attribuer noms des colonnes au tablemodel*/
		tablemodel.setColumnIdentifiers(columnsnames);	
	       for(VideoGame v: videogames){
	           Object[] datas = {v.getId_videogame(), v.getName(), v.getConsole(),v.getVersion(),v.getCreditCost()};
	           tablemodel.addRow(datas);
	       }
	        
	       /*Creation du curseur du JTable*/
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(780, 284);
        scrollPane.setLocation(47, 180);
        contentPane.add(scrollPane);
        
        	/*Creation du JTable*/
        table = new JTable(tablemodel);
        scrollPane.setViewportView(table);
        
        /*Ajouter evenement lors du clique sur une ligne*/
	    table.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent e){
	    	      if (e.getClickCount() == 2){
	    	    	  int selectedRow = table.getSelectedRow();
	  					 VideoGame videogame_selected = new VideoGame((int) table.getValueAt(selectedRow, 0),(String) table.getValueAt(selectedRow, 1),
	  							(int) table.getValueAt(selectedRow, 4), (String)table.getValueAt(selectedRow, 3),(String) table.getValueAt(selectedRow, 2));
	  					GamePage gamepage= new GamePage( player,videogame_selected);
	  					gamepage.setVisible(true);
	  					dispose();
	    	         }
	    	      }
	    });
        
	}
}
