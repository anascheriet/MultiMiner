import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ToolTipUI;
import javax.tools.Tool;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Dashboard extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard frame = new Dashboard();
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
	public Dashboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 781, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(23,69,25));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblMultiMiner = new JLabel("Multi Miner");
		lblMultiMiner.setBounds(10, 11, 143, 22);
		lblMultiMiner.setForeground(Color.WHITE);
		lblMultiMiner.setFont(new Font("Segoe UI Light", Font.BOLD, 24));
		panel.add(lblMultiMiner);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 121, 765, 182);
		panel_1.setBackground(new Color(20,62,24));
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			
			
			
			public void mouseClicked(MouseEvent e) {
				new Dashboard().setVisible(false);
				new wordcountDash().setVisible(true);
				
			 
			}
		});
		panel_2.setBounds(46, 32, 136, 86);
		panel_2.setBackground(new Color(51,153,51));
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Dashboard().setVisible(false);
				new wordcountDash().setVisible(true);
				
			}
		});
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\anasc\\eclipse-workspace_j\\WordCount\\icons8_outline_35px.png"));
		lblNewLabel.setBounds(51, 25, 38, 39);
		panel_2.add(lblNewLabel);
		
		JPanel panel_3 = new JPanel();
		
		panel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Dashboard().setVisible(false);
				new MatrixDash().setVisible(true);
			}
		});
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(51, 153, 51));
		panel_3.setBounds(227, 32, 136, 86);
		panel_1.add(panel_3);
		
		JLabel label = new JLabel("");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Dashboard().setVisible(false);
				new MatrixDash().setVisible(true);
			}
		});
		label.setIcon(new ImageIcon("C:\\Users\\anasc\\eclipse-workspace_j\\WordCount\\icons8_data_40px.png"));
		label.setBounds(51, 24, 38, 39);
		panel_3.add(label);
		
		JPanel panel_4 = new JPanel();
		panel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Dashboard().setVisible(false);
				new WeatherDash().setVisible(true);
			}
		});
		panel_4.setLayout(null);
		panel_4.setBackground(new Color(51, 153, 51));
		panel_4.setBounds(401, 32, 136, 86);
		panel_1.add(panel_4);
		
		JLabel label_1 = new JLabel("");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Dashboard().setVisible(false);
				new WeatherDash().setVisible(true);
			}
		});
		label_1.setIcon(new ImageIcon("C:\\Users\\anasc\\eclipse-workspace_j\\WordCount\\icons8_partly_cloudy_day_40px_1.png"));
		label_1.setBounds(47, 25, 38, 39);
		panel_4.add(label_1);
		
		JPanel panel_5 = new JPanel();
		panel_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Dashboard().setVisible(false);
				new CinemaDash().setVisible(true);
			}
		});
		panel_5.setLayout(null);
		panel_5.setBackground(new Color(51, 153, 51));
		panel_5.setBounds(580, 32, 136, 86);
		panel_1.add(panel_5);
		
		JLabel label_2 = new JLabel("");
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Dashboard().setVisible(false);
				new CinemaDash().setVisible(true);
			}
		});
		label_2.setIcon(new ImageIcon("C:\\Users\\anasc\\eclipse-workspace_j\\WordCount\\icons8_film_reel_40px_1.png"));
		label_2.setBounds(51, 22, 38, 53);
		panel_5.add(label_2);
		
		JLabel lblWordCount = new JLabel("Word Count");
		lblWordCount.setForeground(Color.WHITE);
		lblWordCount.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblWordCount.setBounds(46, 121, 108, 22);
		panel_1.add(lblWordCount);
		
		JLabel lblMatricevecteur = new JLabel("Matrice*Vecteur");
		lblMatricevecteur.setForeground(Color.WHITE);
		lblMatricevecteur.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblMatricevecteur.setBounds(227, 121, 136, 22);
		panel_1.add(lblMatricevecteur);
		
		JLabel lblDonnesMeteo = new JLabel("Donn\u00E9es Meteo");
		lblDonnesMeteo.setForeground(Color.WHITE);
		lblDonnesMeteo.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblDonnesMeteo.setBounds(401, 121, 136, 22);
		panel_1.add(lblDonnesMeteo);
		
		JLabel lblArchiveCinema = new JLabel("Archive Cinema");
		lblArchiveCinema.setForeground(Color.WHITE);
		lblArchiveCinema.setFont(new Font("Segoe UI Light", Font.BOLD, 18));
		lblArchiveCinema.setBounds(580, 121, 136, 22);
		panel_1.add(lblArchiveCinema);
	}
}
