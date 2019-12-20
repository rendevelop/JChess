package com.reneldev.chess;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

enum Piece {
	KING, QUEEN, KNIGHT, BISHOP, ROOK, PAWN
}

enum Team {
	WHITE, BLACK
}

public class ChessPiece extends JLabel implements MouseListener, MouseMotionListener {
	
	private char column;
	private int row;
	private boolean captured;
	private Piece piece;
	private Team team;
	private boolean dragging = false;
	private boolean firstMove;
	private ChessBoard board;
	
	private int origX;
	private int origY;
	
	private int x_pos;
	private int y_pos;
	
	private final int OFFSET_X = 34;
	private final int OFFSET_Y = 34;
	
	public ChessPiece(ChessBoard board, Piece piece, Team team, char column, int row, boolean firstMove) {
		this.piece = piece;
		this.team = team;
		this.column = column;
		this.row = row;
		this.captured = false;
		this.firstMove = firstMove;
		this.board = board;
		
		this.origX = ChessBoard.getXFromColumn(column);
		this.origY = ChessBoard.getYFromRow(row);
		this.x_pos = 0;
		this.y_pos = 0;
		
		BufferedImage pieceImage = null;
		try {
			pieceImage = ImageIO.read(new File("images/chess pieces/" + team.name().toLowerCase() + " pieces/" 
					+ piece.name().toLowerCase() + "_" + team.name().toLowerCase() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setIcon(new ImageIcon(pieceImage.getScaledInstance(68, 68, Image.SCALE_DEFAULT)));
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public char getColumn() {
		return column;
	}
	
	public int getNumericColumn() {
		return column % 97;
	}
	
	public int getRow() {
		return row;
	}
	
	public void captured() {
		captured = true;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public Team getTeam() {
		return team;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		setDragging(true);
		board.setSelectedPiece(this);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (isDragging()) {
				setDragging(false);
				board.movePiece(this, getX() + arg0.getX(), getY() + arg0.getY());
				super.repaint();
		}
		//repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (isDragging()) {
			setLocation(getX() + arg0.getX() - OFFSET_X, getY() + arg0.getY() - OFFSET_Y);
			super.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean isDragging() {
		return dragging;
	}
	
	public boolean isFirstMove() {
		return firstMove;
	}

	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}
	
}
