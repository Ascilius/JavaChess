import java.awt.Image;
import java.awt.Point;

public class Queen extends ChessPiece {

	public Queen(Image piece, String color, Point location, int spaceWidth) {
		super(piece, color, location, spaceWidth);
	}

	public void select(ChessPiece[][] board, int spaceWidth) {

	}

	public String toString() {
		return "Queen";
	}

}