package com.reneldev.chess;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ChessBoard extends JPanel implements MouseListener {

	private ChessPiece board[][];

	private ChessPiece selectedPiece = null;
	
	private int x_pos;
	private int y_pos;
	
	private final int SIZE_X = 58;
	private final int SIZE_Y = 58;
	
	private final int OFFSET_X = 5;
	private final int OFFSET_Y = 5;
	
	private boolean firstload = true;
	private boolean isChanged = false;
	
	/**
	 * Creates a classic Chess Board, with each autonomous piece added to the board.
	 * 
	 */
	public ChessBoard() {
		
		board = new ChessPiece[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = null;
			}
		}
		
		setLayout(new MigLayout("", "[464px]", "[500px]"));
		setBounds(0, 0, 464, 500);
		
		// Generate Chess Pieces for White
		// Rooks on both ends
		board[7][0] = new ChessPiece(this, Piece.ROOK, Team.WHITE, 'a', 1, true);
		board[7][7] = new ChessPiece(this, Piece.ROOK, Team.WHITE, 'h', 1, true);
		// Knights
		board[7][1] = new ChessPiece(this, Piece.KNIGHT, Team.WHITE, 'b', 1, true);
		board[7][6] = new ChessPiece(this, Piece.KNIGHT, Team.WHITE, 'g', 1, true);
		// Bishops
		board[7][2] = new ChessPiece(this, Piece.BISHOP, Team.WHITE, 'c', 1, true);
		board[7][5] = new ChessPiece(this, Piece.BISHOP, Team.WHITE, 'f', 1, true);
		// King and Queen
		board[7][3] = new ChessPiece(this, Piece.QUEEN, Team.WHITE, 'd', 1, true);
		board[7][4] = new ChessPiece(this, Piece.KING, Team.WHITE, 'e', 1, true);
		// Pawns
		for (int j = 0; j < 8; j++) {
			board[6][j] = new ChessPiece(this, Piece.PAWN, Team.WHITE, (char)(97 + j), 2, true);
		}
		
		// Generate Chess Pieces for Black
		// Rooks on both ends
		board[0][0] = new ChessPiece(this, Piece.ROOK, Team.BLACK, 'a', 8, true);
		board[0][7] = new ChessPiece(this, Piece.ROOK, Team.BLACK, 'h', 8, true);
		// Knights
		board[0][1] = new ChessPiece(this, Piece.KNIGHT, Team.BLACK, 'b', 8, true);
		board[0][6] = new ChessPiece(this, Piece.KNIGHT, Team.BLACK, 'g', 8, true);
		// Bishops
		board[0][2] = new ChessPiece(this, Piece.BISHOP, Team.BLACK, 'c', 8, true);
		board[0][5] = new ChessPiece(this, Piece.BISHOP, Team.BLACK, 'f', 8, true);
		// King and Queen
		board[0][3] = new ChessPiece(this, Piece.QUEEN, Team.BLACK, 'd', 8, true);
		board[0][4] = new ChessPiece(this, Piece.KING, Team.BLACK, 'e', 8, true);
		// Pawns
		for (int j = 0; j < 8; j++) {
			board[1][j] = new ChessPiece(this, Piece.PAWN, Team.BLACK, (char)(97 + j), 7, true);
		}
		addMouseListener(this);
	}
	
	// TODO PaintComponent seems to be the issue here
	// Also need to recalculate the row and columns for both pieces
	/**
	 * 
	 * @param cp Chess piece being moved
	 * @param x X location of destination
	 * @param y Y location of destination
	 */
	public void movePiece(ChessPiece cp, int x, int y) {
		if (selectedPiece == null) return;
		
		int row = getRow(y);
		int col = getColumn(x) % 97;
		
		if (!isValidMove(cp, col, row)) return;
		
		System.out.println(getColumn(x));
		System.out.println(x + " " + y);
		System.out.println(row + " " + col);
		
		// Remove old piece
		int old_row = 8 - cp.getRow();
		int old_col = cp.getColumn() % 97;
		
		cp.setVisible(false);
		this.remove(cp);
		board[old_row][old_col] = null;
		repaint();
		
		// Create new piece in the square
		board[row][col] = new ChessPiece(this, cp.getPiece(), cp.getTeam(), getColumn(x), row, false);
		
		repaint();
		
		selectedPiece = null;
		System.out.println("Moved piece from row: " + old_row + " col: " + old_col + " to row: " + row + " col: " + col);
	}
	
	/**
	 * Sets the selected piece field.
	 * @param cp Chess piece
	 */
	public void setSelectedPiece(ChessPiece cp) {
		selectedPiece = cp;
	}
	
	private ArrayList<ChessPiece> getPiecesInPath(int orig_col, int orig_row, int dest_col, int dest_row, int x_direction, int y_direction) {
		ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
		
		if (y_direction == 1 && x_direction == 0) {
			for (int i = orig_row + 1; i < dest_row; i++) {
				if (board[i][orig_col] != null) pieces.add(board[i][orig_col]);
			}
		} else if (y_direction == 0 && x_direction == 1) {
			for (int i = orig_col + 1; i < dest_col; i++) {
				if (board[orig_row][i] != null) pieces.add(board[orig_row][i]);
			}
		} else if (y_direction == -1 && x_direction == 0) {
			for (int i = orig_row - 1; i > dest_row; i--) {
				if (board[i][orig_col] != null) pieces.add(board[i][orig_col]);
			}
		} else if (y_direction == 0 && x_direction == -1) {
			for (int i = orig_col - 1; i > dest_col; i--) {
				if (board[orig_row][i] != null) pieces.add(board[orig_row][i]);
			}	
		}else if (y_direction == 1 && x_direction == 1) {
			int i, j;
			for (i = orig_row + 1, j = orig_col + 1; i < dest_row && j < dest_row; i++, j++) {	
				if (board[i][j] != null) pieces.add(board[i][j]);
			}
		} else if (y_direction == -1 && x_direction == 1) {
			int i, j;
			for (i = orig_row - 1, j = orig_col + 1; i > dest_row && j < dest_row; i--, j++) {	
				if (board[i][j] != null) pieces.add(board[i][j]);
			}
		} else if (y_direction == 1 && x_direction == -1) {
			int i, j;
			for (i = orig_row + 1, j = orig_col - 1; i < dest_row && j > dest_row; i++, j--) {	
				if (board[i][j] != null) pieces.add(board[i][j]);
			}
		} else if (y_direction == -1 && x_direction == -1) {
			int i, j;
			for (i = orig_row - 1, j = orig_col - 1; i > dest_row && j > dest_row; i--, j--) {	
				if (board[i][j] != null) pieces.add(board[i][j]);
			}
		} else if (y_direction == 0 && x_direction == 0) {
			// PASS
			// No pieces would be in path except itself
		}
		return pieces;
	}
	
	public boolean isValidMove(ChessPiece cp, int col, int row) {
		if (board[row][col] != null) {
			if (board[row][col].getTeam() == cp.getTeam()) return false;
		}
		
		ArrayList<ChessPiece> piecesInPath;
		int row_delta;
		int col_delta;
		
		switch (cp.getPiece()) {
		case KING:
			
			return (Math.abs((cp.getColumn() % 97) - col) == 1 || Math.abs(cp.getRow() - row - 1) == 1);
		case QUEEN:
			return true;
		case KNIGHT:
			return true;
		case BISHOP:
			return true;
		case ROOK:
			row_delta = Math.abs(row - cp.getRow());
			col_delta = Math.abs((cp.getNumericColumn()) - col);
			if (cp.getTeam() == Team.WHITE) {
				
			}
		case PAWN:
			col_delta = Math.abs((cp.getNumericColumn()) - col);
			System.out.println(col_delta);
			if (cp.isFirstMove()) {
				if (cp.getTeam() == Team.WHITE) {
					row_delta = row - cp.getRow();
					if (row_delta <= 2 && row_delta >= 1 && col_delta == 0 && board[row][col] == null) {
						return true;
					} else if (row_delta == 1 && col_delta == 1) {
						return board[row][col] != null; // capturable piece
					}
				} else {
					row_delta = cp.getRow() - row;
					if (row_delta <= 2 && row_delta >= 1 && col_delta == 0 && board[row][col] == null) {
						return true;
					} else if (row_delta == 1 && col_delta == 1) {
						return board[row][col] != null; // capturable piece
					}
				}
			} else {
				if (cp.getTeam() == Team.WHITE) {
					row_delta = row - cp.getRow();
					if (row_delta == 1 && col_delta == 0 && board[row][col] == null) {
						return true;
					} else if (row_delta == 1 && col_delta == 1) {
						return board[row][col] != null; // capturable piece
					}
				} else {
					row_delta = cp.getRow() - row;
					if (row_delta == 1 && col_delta == 0 && board[row][col] == null) {
						return true;
					} else if (row_delta == 1 && col_delta == 1) {
						return board[row][col] != null; // capturable piece
					}
				}
			}
		}
		return false;
	}
	/**
	 * Draws the actual board and corresponding pieces.
	 */
	@Override
	 protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      int x = 0;
	  int y = 0;
	  for (int i = 0; i < 8; i++) {
		  x = 0;
			for (int j = 0; j < 8; j++) {
				// Adding the board with altering colors
				Color color;
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						color = new Color(255,206,158);
					} else {
						color = new Color(209,139,71);
					}
				} else {
					if (j % 2 != 0) {
						color = new Color(255,206,158);
					} else {
						color = new Color(209,139,71);
					}
				}
				
				g2.setColor(color);
				g2.fillRect(x, y, 58, 58);
				x += 58;
				
				// Adding the pieces
				if (board[i][j] != null) {
					int x_pos = ChessBoard.getXFromColumn(board[i][j].getColumn()) - OFFSET_X;
					int y_pos = ChessBoard.getYFromRow(board[i][j].getRow()) - OFFSET_Y;
					ChessPiece boardPiece = board[i][j];
					if (!boardPiece.isDragging()) {
						boardPiece.setBounds(x_pos, y_pos, 68, 68);
						//repaint();
						//System.out.println("(Not Dragging) Chess Piece: " + board[i][j].getPiece().name() + " Color: " + board[i][j].getTeam().name() + " X: " + x_pos + " Y: " + y_pos);
					} else {
						System.out.println("(Dragging) Chess Piece: " + board[i][j].getPiece().name() + " Color: " + board[i][j].getTeam().name() + " X: " + x_pos + " Y: " + y_pos);
						//repaint();
					}
					this.add(board[i][j]);
					//this.repaint();
				} 
			}
			
			y += 58;
	  }
	}
	
	/**
	 * Converts Coordinates to a Square Number.
	 * @param x X-Coordinate
	 * @param y y-Coordinate
	 * @return Square Number
	 */
	private int convertCoordinateToSquare(int x, int y) {
		int square;
		
		int square_x = x/58;
		int square_y = (y/58)*8;
		
		square = square_x + square_y;
		
		return square + 1;
	}
	
	/**
	 * Gets row from Y-Coordinate.
	 * @param y Y-Coordinate
	 * @return Row
	 */
	public static int getRow(int y) {
		return 8 - (y/58);
	}
	
	/**
	 * Gets Y-Coordinate from Row.
	 * @param row Row
	 * @return Y-Coordinate
	 */
	public static int getYFromRow(int row) {
		return ((row - 8)*-58);
	}
	
	/**
	 * Gets column from X-Coordinate
	 * @param x X-Coordinate
	 * @return Column
	 */
	public static char getColumn(int x) {
		return (char) (97 + (int)Math.round(x/58));
	}
	
	/**
	 * Gets Column Index from X-Coordinate 
	 * @param x X-Coordinate
	 * @return Column Index
	 */
	public static int getColumnIndex(int x) {
		return 8 - (x/58);
	}
	
	/**
	 * Gets X-Coordinate from Column
	 * @param column Column
	 * @return X-Coordinate
	 */
	public static int getXFromColumn(char column) {
		return ((column - 97)*58);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		return;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		return;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		return;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();
		isChanged = true;
		System.out.println("x: " + x + " y: " + y);
		System.out.println("Square: " + convertCoordinateToSquare(x,y));
		System.out.println("Row: " + getRow(y));
		System.out.println("Column: " + getColumn(x));
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		return;
	}
	
}
