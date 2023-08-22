import java.awt.Point;

public class Capture extends Move {

	private ChessPiece other;

	public Capture(Point location, ChessPiece piece, int spaceWidth) {
		super(location, spaceWidth);
		this.other = piece;
	}

	public ChessPiece getOther() {
		return other;
	}

}
