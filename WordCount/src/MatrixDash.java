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
import java.util.ArrayList;

import javax.swing.JFileChooser;
import java.io.*;
public class MatrixDash extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MatrixDash frame = new MatrixDash();
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
	public MatrixDash() {
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
		
		JLabel lblWordCount = new JLabel("Calcul Matrice * Vecteur");
		lblWordCount.setForeground(Color.WHITE);
		lblWordCount.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblWordCount.setBounds(10, 114, 205, 22);
		panel.add(lblWordCount);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(20, 62, 24));
		panel_1.setBounds(0, 147, 755, 146);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Veuillez choisir les fichiers \u00E0 traiter : ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 28, 291, 25);
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(51, 153, 51));
		panel_2.setBounds(295, 21, 182, 42);
		panel_1.add(panel_2);
		
		ArrayList<File> MatrVect= new ArrayList<File>();
		
		JLabel lblUploder = new JLabel(" Uploader La Matrice");
		JFileChooser matrixChooser =new JFileChooser();
		lblUploder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (matrixChooser.showOpenDialog(null) == matrixChooser.APPROVE_OPTION) {
					MatrVect.add(matrixChooser.getSelectedFile());	
					System.out.println(MatrVect.get(0));
			}
			}
		});
		lblUploder.setForeground(Color.WHITE);
		lblUploder.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblUploder.setIcon(new ImageIcon("C:\\Users\\anasc\\eclipse-workspace_j\\WordCount\\icons8_upload_40px.png"));
		lblUploder.setBounds(0, 0, 196, 39);
		panel_2.add(lblUploder);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(51, 153, 51));
		panel_3.setBounds(497, 21, 195, 42);
		panel_1.add(panel_3);
		
		JLabel lblUploderLeVecteur = new JLabel("Uploder Le Vecteur");
		lblUploderLeVecteur.setBounds(0, 0, 195, 39);
		panel_3.add(lblUploderLeVecteur);
		
		JFileChooser vectorChooser =new JFileChooser();
		lblUploderLeVecteur.addMouseListener(new MouseAdapter() {
			@Override
			public 	void mouseClicked(MouseEvent e) {
				if (vectorChooser.showOpenDialog(null) == vectorChooser.APPROVE_OPTION) {
					MatrVect.add(vectorChooser.getSelectedFile());	
					System.out.println(MatrVect.get(1));	
			}
			}
		});
		
		lblUploderLeVecteur.setIcon(new ImageIcon("C:\\Users\\anasc\\eclipse-workspace_j\\WordCount\\icons8_upload_40px.png"));
		lblUploderLeVecteur.setForeground(Color.WHITE);
		lblUploderLeVecteur.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(new Color(51, 153, 51));
		panel_4.setBounds(385, 93, 195, 42);
		panel_1.add(panel_4);
		
		JLabel lblCalculer = new JLabel("          Calculer");
		lblCalculer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
					try {
						Matrix M1 =new Matrix(MatrVect.get(0),MatrVect.get(1));
						M1.split(1);
						File RESFile =M1.Reduce();
						Runtime.getRuntime().exec(new String[] {"C:\\Windows\\notepad.exe",RESFile.getAbsolutePath()});
					
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (MatrixException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
			}
		}
				
			
				
				
			
		);
		lblCalculer.setIcon(new ImageIcon("C:\\Users\\anasc\\eclipse-workspace_j\\WordCount\\icons8_services_40px.png"));
		lblCalculer.setForeground(Color.WHITE);
		lblCalculer.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblCalculer.setBounds(0, 0, 196, 39);
		panel_4.add(lblCalculer);
		
		
		JLabel lblNewLabel_1 = new JLabel("Fonctionnalit\u00E9s ");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new MatrixDash().setVisible(false);
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
