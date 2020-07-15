import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JFileChooser;

public class wordcountDash extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					wordcountDash frame = new wordcountDash();
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
	public wordcountDash() {
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
		
		JLabel lblWordCount = new JLabel("Calcul de nombre d'occurences des mots dans un fichier :");
		lblWordCount.setForeground(Color.WHITE);
		lblWordCount.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblWordCount.setBounds(10, 114, 473, 22);
		panel.add(lblWordCount);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(20, 62, 24));
		panel_1.setBounds(0, 147, 755, 91);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Veuillez choisir le fichier \u00E0 traiter : ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 28, 277, 25);
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(51, 153, 51));
		panel_2.setBounds(282, 21, 376, 42);
		panel_1.add(panel_2);
		
		JFileChooser chosenFile =new JFileChooser();
		
		JLabel lblUploder = new JLabel("Uploder");
		lblUploder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (chosenFile.showOpenDialog(null) == chosenFile.APPROVE_OPTION) {
					java.io.File  file = chosenFile.getSelectedFile();
					
					 try {
						WordCounter w1 = new WordCounter(file);
						try {
							w1.split(500);
							try {
								File RESFile =w1.Reduce();
								Runtime.getRuntime().exec(new String[] {"C:\\Windows\\notepad.exe",RESFile.getAbsolutePath()});
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					} catch (FileNotFoundException | MatrixException e1) {
						// TODO Auto-generated catch block
						
					}
					 
				}
				
			}
		});
		lblUploder.setForeground(Color.WHITE);
		lblUploder.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblUploder.setIcon(new ImageIcon("C:\\Users\\anasc\\eclipse-workspace_j\\WordCount\\icons8_upload_40px.png"));
		lblUploder.setBounds(159, 0, 119, 39);
		panel_2.add(lblUploder);
		
		JLabel lblNewLabel_1 = new JLabel("Fonctionnalit\u00E9s ");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new wordcountDash().setVisible(false);
				new Dashboard().setVisible(true);
				
			}
		});
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\anasc\\eclipse-workspace_j\\WordCount\\icons8_back_40px.png"));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
		lblNewLabel_1.setBounds(10, 11, 189, 40);
		panel.add(lblNewLabel_1);
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBounds(128, 149, 397, 178);
		//panel.add(fileChooser);
	}
}
