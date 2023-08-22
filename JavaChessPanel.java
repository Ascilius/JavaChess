import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JavaChessPanel extends JPanel {

	private final boolean debug = true;

	// chessboard
	private int screenWidth, screenHeight;
	private ChessPiece[][] board = new ChessPiece[8][8]; // [0][0] is a7 (positive y is down)
	int spaceWidth, borderWidth;

	// chess pieces
	private ArrayList<ChessPiece> chessPieces = new ArrayList<ChessPiece>(); // chess pieces
	private BufferedImage whiteRook, whiteKnight, whiteBishop, whiteKing, whiteQueen, whitePawn; // white pieces
	private BufferedImage blackRook, blackKnight, blackBishop, blackKing, blackQueen, blackPawn; // black pieces

	// for moving/capturing pieces
	private boolean whitesTurn = true; // true - white, false - black
	private int moveCount = 0;

	// for mouse action listener
	private Point clickPoint;
	private ChessPiece selectedPiece = null;
	private ArrayList<Move> possibleMoves = new ArrayList<Move>();
	private ArrayList<Capture> possibleCaptures = new ArrayList<Capture>();

	// constructor
	public JavaChessPanel(int screenWidth, int screenHeight) {

		// screen dimensions
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		// board calculations
		spaceWidth = screenHeight / 10;
		borderWidth = spaceWidth / 16;

		// piece assests
		LoadImages();

		// white pieces
		chessPieces.add(new Rook(whiteRook, "White", new Point(0, 7), spaceWidth));
		chessPieces.add(new Knight(whiteKnight, "White", new Point(1, 7), spaceWidth));
		chessPieces.add(new Bishop(whiteBishop, "White", new Point(2, 7), spaceWidth));
		chessPieces.add(new Queen(whiteQueen, "White", new Point(3, 7), spaceWidth));
		chessPieces.add(new King(whiteKing, "White", new Point(4, 7), spaceWidth));
		chessPieces.add(new Bishop(whiteBishop, "White", new Point(5, 7), spaceWidth));
		chessPieces.add(new Knight(whiteKnight, "White", new Point(6, 7), spaceWidth));
		chessPieces.add(new Rook(whiteRook, "White", new Point(7, 7), spaceWidth));
		for (int i = 0; i < 8; i++) // pawns
			chessPieces.add(new Pawn(whitePawn, "White", new Point(i, 6), spaceWidth));

		// black pieces
		chessPieces.add(new Rook(blackRook, "Black", new Point(0, 0), spaceWidth));
		chessPieces.add(new Knight(blackKnight, "Black", new Point(1, 0), spaceWidth));
		chessPieces.add(new Bishop(blackBishop, "Black", new Point(2, 0), spaceWidth));
		chessPieces.add(new Queen(blackQueen, "Black", new Point(3, 0), spaceWidth));
		chessPieces.add(new King(blackKing, "Black", new Point(4, 0), spaceWidth));
		chessPieces.add(new Bishop(blackBishop, "Black", new Point(5, 0), spaceWidth));
		chessPieces.add(new Knight(blackKnight, "Black", new Point(6, 0), spaceWidth));
		chessPieces.add(new Rook(blackRook, "Black", new Point(7, 0), spaceWidth));
		for (int i = 0; i < 8; i++) // pawns
			chessPieces.add(new Pawn(blackPawn, "Black", new Point(i, 1), spaceWidth));

		// adding pieces to the board
		for (ChessPiece piece : chessPieces) {
			board[(int) (piece.getLocation().getX())][(int) (piece.getLocation().getY())] = piece;
		}

		// user inputs
		addKeyListener(new KeyHandler());
		addMouseListener(new MouseHandler());
		setFocusable(true);

	}

	// accessed images for each piece
	public void LoadImages() {
		try {
			// white pieces
			whiteRook = ImageIO.read(new File("images\\whiterook.png"));
			whiteKnight = ImageIO.read(new File("images\\whiteknight.png"));
			whiteBishop = ImageIO.read(new File("images\\whitebishop.png"));
			whiteKing = ImageIO.read(new File("images\\whiteking.png"));
			whiteQueen = ImageIO.read(new File("images\\whitequeen.png"));
			whitePawn = ImageIO.read(new File("images\\whitepawn.png"));
			// black pieces
			blackRook = ImageIO.read(new File("images\\blackrook.png"));
			blackKnight = ImageIO.read(new File("images\\blackknight.png"));
			blackBishop = ImageIO.read(new File("images\\blackbishop.png"));
			blackKing = ImageIO.read(new File("images\\blackking.png"));
			blackQueen = ImageIO.read(new File("images\\blackqueen.png"));
			blackPawn = ImageIO.read(new File("images\\blackpawn.png"));
		}

		catch (IOException ex) {
			System.out.println("ERROR: Files not found!");
			LoadImages();
		}
	}

	// paint method
	public void paintComponent(Graphics graphics) {

		// graphics
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // anti-aliasing

		// background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, screenWidth, screenHeight);

		// side bar info
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 50));
		g.drawString("Chess", 100, 100);
		// turn
		g.setFont(new Font("Arial", Font.PLAIN, 25));
		if (whitesTurn)
			g.drawString("Turn: White", 100, 150);
		else
			g.drawString("Turn: Black", 100, 150);
		// move count
		g.drawString("Moves: " + moveCount, 100, 200);

		// board (outer)
		g.translate(screenWidth / 2, screenHeight / 2);
		g.setColor(new Color(123, 63, 0));
		g.fillRect(screenHeight / -2, screenHeight / -2, screenHeight, screenHeight);
		// board (inner)
		g.setColor(new Color(245, 222, 179));
		g.setStroke(new BasicStroke(5));
		g.drawRect(spaceWidth * -4 - borderWidth, spaceWidth * -4 - borderWidth, spaceWidth * 8 + borderWidth * 2, spaceWidth * 8 + borderWidth * 2);
		// spaces
		for (int i = -4; i < 4; i++) {
			for (int j = -4; j < 4; j++) {
				if ((i + j) % 2 == 0) // checkerboard (chessboard?) pattern
					g.fillRect(spaceWidth * i, spaceWidth * j, spaceWidth, spaceWidth);
			}
		}

		// coordinate markers
		g.setFont(new Font("Monospaced", Font.BOLD, spaceWidth * 3 / 4));
		// letters
		int dec = 101;
		for (int i = -4; i < 4; i++) {
			g.drawString(Character.toString((char) (dec + i)), spaceWidth * i + spaceWidth / 4, spaceWidth * 5 - spaceWidth / 4);
			g.drawString(Character.toString((char) (dec + i)), spaceWidth * i + spaceWidth / 4, spaceWidth * -4 - spaceWidth / 4);
		}
		// numbers
		int num = -3;
		for (int i = 4; i > -4; i--) {
			g.drawString(Integer.toString(num - i + 8), spaceWidth * -5 + spaceWidth / 4, spaceWidth * i - spaceWidth / 4);
			g.drawString(Integer.toString(num - i + 8), spaceWidth * 4 + spaceWidth / 4, spaceWidth * i - spaceWidth / 4);

		}

		// chess pieces
		for (ChessPiece piece : chessPieces) {
			g.drawImage(piece.getPiece(), (spaceWidth * -4) + (int) (spaceWidth * piece.getLocation().getX()), (spaceWidth * -4) + (int) (spaceWidth * piece.getLocation().getY()), spaceWidth, spaceWidth, this);
		}

		// currently selected piece
		if (selectedPiece != null) {
			if (whitesTurn)
				g.setColor(Color.WHITE);
			else
				g.setColor(Color.BLACK);
			Rectangle box = selectedPiece.getPieceBounds();
			g.drawRect((int) box.getX(), (int) box.getY(), (int) box.getWidth(), (int) box.getWidth());
		}

		// possible spaces to move to
		for (Move move : possibleMoves) {
			g.drawOval((int) (move.getBounds().getX() + spaceWidth / 4), (int) (move.getBounds().getY() + spaceWidth / 4), spaceWidth / 2, spaceWidth / 2);
		}

		// possible pieces to capture
		g.setColor(Color.RED);
		for (Move capture : possibleCaptures) {
			g.drawOval((int) (capture.getBounds().getX() + spaceWidth / 4), (int) (capture.getBounds().getY() + spaceWidth / 4), spaceWidth / 2, spaceWidth / 2);
		}

	}

	// reset selection
	public void reset() {
		selectedPiece = null;
		possibleMoves.clear();
		possibleCaptures.clear();
	}

	// move piece
	public void movePiece(Move move) {
		// gathering data
		Point location = selectedPiece.getLocation();
		int x = (int) (location.getX());
		int y = (int) (location.getY());
		Point newLocation = move.getLocation();
		int newX = (int) (newLocation.getX());
		int newY = (int) (newLocation.getY());

		// updating board
		board[newX][newY] = board[x][y];
		board[x][y] = null;
		// debugging
		if (debug == true) {
			
		}

		// updating piece
		selectedPiece.updateLocation(newLocation, spaceWidth);

		// next turn
		whitesTurn = !whitesTurn;
	}

	// mouse inputs
	class MouseHandler extends MouseAdapter {

		// selecting chesspiece
		public void mouseClicked(MouseEvent e) {

			// new click
			clickPoint = new Point((int) (e.getPoint().getX() - screenWidth / 2), (int) (e.getPoint().getY() - screenHeight / 2)); // tracks where user clicked; adjusts for the origin

			// piece selected
			for (ChessPiece piece : chessPieces) {
				if (piece.getPieceBounds().contains(clickPoint)) {
					if ((whitesTurn == true && piece.getColor() == "White") || (whitesTurn == false && piece.getColor() == "Black")) { // checks if the piece clicked on is the right color
						// reset
						reset();
						// new piece
						selectedPiece = piece;
						piece.select(board, spaceWidth);
						possibleMoves.addAll(piece.getPossibleMoves());
						possibleCaptures.addAll(piece.getPossibleCaptures());
						// redraw board
						repaint();
						break;
					}
				}
			}

			// move selected
			for (Move move : possibleMoves) {
				if (move.getBounds().contains(clickPoint)) {
					// move piece
					movePiece(move);
					// reset selection and redraw board
					reset();
					repaint();
					break;
				}
			}

			// capture selected
			for (Capture capture : possibleCaptures) {
				if (capture.getBounds().contains(clickPoint)) {
					// move piece
					movePiece(capture);
					// capture piece
					ChessPiece other = capture.getOther();
					int otherX = (int) (other.getLocation().getX());
					int otherY = (int) (other.getLocation().getY());
					board[otherX][otherY] = null;
					chessPieces.remove(other);
					// reset selection and redraw board
					reset();
					repaint();
					break;
				}
			}

		}

	}

	// key inputs
	class KeyHandler extends KeyAdapter {

		public void keyReleased(KeyEvent e) {

			// exiting
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			// next turn (debugging)
			else if (e.getKeyCode() == KeyEvent.VK_SPACE && debug == true) {
				whitesTurn = !whitesTurn;
				reset();
				repaint();
			}

		}

	}
}
