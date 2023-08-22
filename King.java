import java.awt.Image;
import java.awt.Point;

public class King extends ChessPiece {

	public King(Image piece, String color, Point location, int spaceWidth) {
		super(piece, color, location, spaceWidth);
	}

	public void select(ChessPiece[][] board, int spaceWidth) {
		
	}

	public String toString() {
		return "King";
	}

}