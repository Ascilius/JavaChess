import java.awt.Image;
import java.awt.Point;

public class Pawn extends ChessPiece {

	private boolean firstMove = true;

	public Pawn(Image piece, String color, Point location, int spaceWidth) {
		super(piece, color, location, spaceWidth);
	}

	public void select(ChessPiece[][] board, int spaceWidth) {

		// piece location
		int x = (int) (location.getX());
		int y = (int) (location.getY()); // positive y is down

		// white piece
		if (getColor() == "White") {

			// captures
			if (x > 0) {
				// left
				ChessPiece other = board[x - 1][y - 1];
				if (other != null && other.getColor() == "Black")
					possibleCaptures.add(new Capture(new Point(x - 1, y - 1), board[x - 1][y - 1], spaceWidth));
				// left en passant
				other = board[x - 1][y];
				if (other != null && other.getColor() == "Black")
					possibleCaptures.add(new Capture(new Point(x - 1, y - 1), board[x - 1][y], spaceWidth));
			}
			if (x < 7) {
				// right
				ChessPiece other = board[x + 1][y - 1];
				if (other != null && other.getColor() == "Black")
					possibleCaptures.add(new Capture(new Point(x + 1, y - 1), board[x + 1][y - 1], spaceWidth));
				// right en passant
				other = board[x + 1][y];
				if (other != null && other.getColor() == "Black")
					possibleCaptures.add(new Capture(new Point(x + 1, y - 1), board[x + 1][y], spaceWidth));
			}

			// moves
			if (board[x][y - 1] == null) { // checks if space in front is open
				if (firstMove && board[x][y - 2] == null) // checks if its the pawns first move and if the space two spaces in front is open
					possibleMoves.add(new Move(new Point(x, y - 2), spaceWidth));
				possibleMoves.add(new Move(new Point(x, y - 1), spaceWidth));
			}

		}

		// black piece
		else if (getColor() == "Black") {

			// captures
			if (x > 0) {
				// right
				ChessPiece other = board[x - 1][y + 1];
				if (other != null && other.getColor() == "White")
					possibleCaptures.add(new Capture(new Point(x - 1, y + 1), board[x - 1][y + 1], spaceWidth));
				// right en passant
				other = board[x - 1][y];
				if (other != null && other.getColor() == "White")
					possibleCaptures.add(new Capture(new Point(x - 1, y + 1), board[x - 1][y], spaceWidth));
			}
			if (x < 7) {
				// left
				ChessPiece other = board[x + 1][y + 1];
				if (other != null && other.getColor() == "White")
					possibleCaptures.add(new Capture(new Point(x + 1, y + 1), board[x + 1][y + 1], spaceWidth));
				// left en passant
				other = board[x + 1][y];
				if (other != null && other.getColor() == "White")
					possibleCaptures.add(new Capture(new Point(x + 1, y + 1), board[x + 1][y], spaceWidth));
			}

			// moves
			if (board[x][y + 1] == null) { // checks if space in front is open
				if (firstMove && board[x][y + 2] == null) // checks if its the pawns first move and if the space two spaces in front is open
					possibleMoves.add(new Move(new Point(x, y + 2), spaceWidth));
				possibleMoves.add(new Move(new Point(x, y + 1), spaceWidth));
			}

		}
	}

	// piece is moved
	public void updateLocation(Point newLocation, int spaceWidth) {
		super.updateLocation(newLocation, spaceWidth);
		firstMove = false;
	}

	public String toString() {
		return color + " Pawn";
	}

}
