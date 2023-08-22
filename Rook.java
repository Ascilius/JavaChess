import java.awt.Image;
import java.awt.Point;

public class Rook extends ChessPiece {

	public Rook(Image piece, String color, Point location, int spaceWidth) {
		super(piece, color, location, spaceWidth);
	}

	public void select(ChessPiece[][] board, int spaceWidth) {

		// piece location
		int x = (int) (location.getX());
		int y = (int) (location.getY()); // positive y is down

		// up
		for (int i = y - 1; i >= 0; i--) {
			if (board[x][i] == null) // space is empty
				possibleMoves.add(new Move(new Point(x, i), spaceWidth));
			else if (board[x][i] != null) { // space has a piece
				if (board[x][i].getColor() != color) // piece is opposing color
					possibleCaptures.add(new Capture(new Point(x, i), board[x][i], spaceWidth));
				break; // rook cannot move past an occupied space
			}
		}

		// left
		for (int i = x - 1; i >= 0; i--) {
			if (board[i][y] == null) // space is empty
				possibleMoves.add(new Move(new Point(i, y), spaceWidth));
			else if (board[i][y] != null) { // space has a piece
				if (board[i][y].getColor() != color) // piece is opposing color
					possibleCaptures.add(new Capture(new Point(i, y), board[i][y], spaceWidth));
				break; // rook cannot move past an occupied space
			}
		}

		// down
		for (int i = y + 1; i <= 7; i++) {
			if (board[x][i] == null) // space is empty
				possibleMoves.add(new Move(new Point(x, i), spaceWidth));
			else if (board[x][i] != null) { // space has a piece
				if (board[x][i].getColor() != color) // piece is opposing color
					possibleCaptures.add(new Capture(new Point(x, i), board[x][i], spaceWidth));
				break; // rook cannot move past an occupied space
			}
		}

		// right
		for (int i = x + 1; i <= 7; i++) {
			if (board[i][y] == null) // space is empty
				possibleMoves.add(new Move(new Point(i, y), spaceWidth));
			else if (board[i][y] != null) { // space has a piece
				if (board[i][y].getColor() != color) // piece is opposing color
					possibleCaptures.add(new Capture(new Point(i, y), board[i][y], spaceWidth));
				break; // rook cannot move past an occupied space
			}
		}

	}

	public String toString() {
		return "Rook";
	}

}
