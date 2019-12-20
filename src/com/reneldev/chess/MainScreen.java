package com.reneldev.chess;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

public class MainScreen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/QuattrocentoSans-Regular.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/QuattrocentoSans-Bold.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/QuattrocentoSans-Italic.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/QuattrocentoSans-BoldItalic.ttf")));

		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
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
	public MainScreen() {
		setResizable(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setTitle("JChess");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		// Set Background Image
		BufferedImage windowBackgroundImage = null;
		try {
			windowBackgroundImage = ImageIO.read(new File("images/chess2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel backgroundLabel = new JLabel(new ImageIcon(windowBackgroundImage.getScaledInstance(750, 550, Image.SCALE_FAST)));
		backgroundLabel.setBounds(0, 0, 686, 463);
		contentPane.add(backgroundLabel);
		
		BufferedImage pieceImage = null;
		try {
			pieceImage = ImageIO.read(new File("images/chess_piece_selector.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel pieceLabel = new JLabel(new ImageIcon(pieceImage.getScaledInstance(75, 75, Image.SCALE_FAST)));
		pieceLabel.setVisible(false);
		backgroundLabel.add(pieceLabel);
		
		JLabel jchessLabel = new JLabel("J");
		jchessLabel.setFont(new Font("QuattrocentoSans", Font.BOLD, 100));
		jchessLabel.setBounds(150, 0, 200, 150);
		jchessLabel.setForeground(Color.WHITE);
		backgroundLabel.add(jchessLabel);
		JLabel jchessLabel2 = new JLabel("Chess");
		jchessLabel2.setFont(new Font("QuattrocentoSans", Font.BOLD, 100));
		jchessLabel2.setBounds(200, 0, 400, 150);
		jchessLabel2.setForeground(new Color(240, 162, 58));
		backgroundLabel.add(jchessLabel2);

		setContentPane(contentPane);
		
		JLabel playLabel = new JLabel("Play");
		playLabel.setFont(new Font("QuattrocentoSans", Font.BOLD, 60));
		playLabel.setBounds(496, 130, 150, 100);
		playLabel.setForeground(new Color(240, 162, 58));
		playLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				backgroundLabel.setVisible(false);
				add(new ChessBoard());
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				playLabel.setFont(playLabel.getFont().deriveFont(72f));
				pieceLabel.setBounds(426, 130, 100, 100);
				pieceLabel.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				playLabel.setFont(playLabel.getFont().deriveFont(60f));
				pieceLabel.setVisible(false);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		backgroundLabel.add(playLabel);
		
		JLabel configLabel = new JLabel("Config");
		configLabel.setFont(new Font("QuattrocentoSans", Font.BOLD, 60));
		configLabel.setBounds(450, 230, 250, 100);
		configLabel.setForeground(new Color(240, 162, 58));
		configLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				configLabel.setFont(configLabel.getFont().deriveFont(72f));
				pieceLabel.setBounds(380, 230, 100, 100);
				pieceLabel.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				configLabel.setFont(configLabel.getFont().deriveFont(60f));
				pieceLabel.setVisible(false);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		backgroundLabel.add(configLabel);
		
		JLabel exitLabel = new JLabel("Exit");
		exitLabel.setFont(new Font("QuattrocentoSans", Font.BOLD, 60));
		exitLabel.setBounds(496, 330, 150, 100);
		exitLabel.setForeground(new Color(240, 162, 58));
		exitLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				exitLabel.setFont(exitLabel.getFont().deriveFont(72f));
				pieceLabel.setBounds(426, 330, 100, 100);
				pieceLabel.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				exitLabel.setFont(exitLabel.getFont().deriveFont(60f));
				pieceLabel.setVisible(false);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				 dispose();
				 System.exit(0);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		backgroundLabel.add(exitLabel);
	}

}
