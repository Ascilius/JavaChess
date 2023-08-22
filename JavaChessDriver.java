import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

/*
alpha v.0.1: Keeping Promises
 - Honor Code Dialog
alpha v.0.2: Efficiency
 - More Efficient Drawing
 - Coordinate System
2022-05-20: alpha v.0.3: Moving Along
 - Selection and Basic Moving
 - Capturing
 - Scores

To-Do
 - selection bug
 - text/image scaling

 - better honor code dialog
    - text wrapping
 - manage location by the panel or piece? both?
 - selection indicator
 - complete piece functionality
    - more efficient possible movement calculators
    - board bounds
    - intersecting pieces
    - special moves
    - pawn first move
 - past turn trackers
 - computer opponent

Chess Rules/Research
 - Checkmate King (King cannot escape capture)
 - Resignation or Draw (?)
 - Chess Notation
 - 8 x 8 Board
    - Light/White, Dark/Black
    - White is on the near-right corner
    - Horizontal Rows: Ranks
    - Vertical Rows: Files
 - "Queen on her own color"
 - "White on right"
 - White moves first
    - player must move before next turn
 - Time Control (?)
Moves
 - pieces cannot move past occupied squares
    - unless a knight and castling
    - piece is captured when attacking enemy piece replaces it on its square except for en passant
    - captured piece is permanently removed from the game
    - king can be put in check but cannot be captured
 - King moves one space is all directions
    - castling
       - only once per player, per game
       - king and rook have not previously moved ("the right to castle", "castling rights")
       - no pieces in between king and rook
       - king may not be in check, but rook can
       - king may not pass through or land on a square under attack
       - kingside (4 spaces), queenside(5 spaces)
          - just code specifically ig
 - rook moves horizontally or vertically
 - bishop moves diagonally
 - queen moves straight in all directions
 - knight moves to the nearest squares not on the same rank, file, or diagonal.
 - pawns
    - first move: move two spaces
    - after: move one space
    - capture diagonally
    - en passant
       - e.g. if a white pawn moves two spaces and lands next to a black pawn, the black pawn can capture and move as it the white pawn only moved one space.
    - promotion: pawn is promoted to queen, rook, bishop, or knight of the players choice
Checking
 - Check - when king is under attack by at least one enemy piece
 - piece is unable to move as it would put king in check
    - but it can move if it can deliver a check back
 - Getting out of a Check
    - Move the king
    - Capture the checking piece
    - Block the check with another piece
 - Checkmate - when the there is no escape from the check
Draw
 - player is not in check but has no legal moves
 - dead position (?)
 - both players agree
 - player states the following or declares to do the following
    - threefold repetition move (?)
    - fifty-move rule (?)
 - arbiter intervenes
    - fivefold repetition move (?)
    - seventy-five-move rule (?)
 - flag-fall (?) (for time control)
 */

public class JavaChessDriver {

	private static final boolean debug = true;

	public static void main(String[] args) {
		// main game window
		JFrame frame = new JFrame("Java Chess");
		// screen dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		JavaChessPanel panel = new JavaChessPanel(screenWidth, screenHeight);
		// further window setup
		frame.add(panel);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// honor code dialog
		JFrame dialog = new JFrame("Honor Code");
		// popup dimensions
		int dialogWidth = screenWidth / 4;
		int dialogHeight = screenHeight / 4;
		int x = screenWidth / 2 - dialogWidth / 2;
		int y = screenHeight / 2 - dialogHeight / 2;
		dialog.setBounds(x, y, dialogWidth, dialogHeight);
		// honor code statement
		if (debug == false) {
			String honorCode = "On my honor as a member of the Woodson HS Community, I, Jason Kim certify that I have neither given nor received unauthorized aid on this assignment, that I have cited my sources for authorized aid, and that this project was started on or after April 3, 2019.";
			dialog.add(new JLabel(honorCode)); // needs improvement
			dialog.setVisible(true);
			dialog.setAlwaysOnTop(true);
		}
	}
}
