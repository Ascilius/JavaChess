import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class ChessPiece {

	private final boolean debug = true;

	// attributes
	protected Image piece;
	protected String color;
	protected Point location;

	// possible
	protected Rectangle pieceBounds;
	protected ArrayList<Move> possibleMoves = new ArrayList<Move>();
	protected ArrayList<Capture> possibleCaptures = new ArrayList<Capture>();

	// constructor
	public ChessPiece(Image piece, String color, Point location, int spaceWidth) {
		this.piece = piece;
		this.color = color;
		this.location = location;
		newPieceBounds(location, spaceWidth);
	}

	// generates new selection box
	public void newPieceBounds(Point location, int spaceWidth) {
		pieceBounds = new Rectangle((int) (spaceWidth * -4 + spaceWidth * location.getX()), (int) (spaceWidth * -4 + spaceWidth * location.getY()), spaceWidth, spaceWidth);
	}

	// piece is selected
	public abstract void select(ChessPiece[][] board, int spaceWidth);
	
	// piece is moved
	public void updateLocation(Point newLocation, int spaceWidth) {
		this.location = newLocation;
		newPieceBounds(location, spaceWidth);
		possibleMoves.clear();
		possibleCaptures.clear();
	}
	
	public Image getPiece() {
		return piece;
	}

	public String getColor() {
		return color;
	}

	public Point getLocation() {
		return location;
	}

	public Rectangle getPieceBounds() {
		return pieceBounds;
	}

	public ArrayList<Move> getPossibleMoves() {
		return possibleMoves;
	}

	public ArrayList<Capture> getPossibleCaptures() {
		return possibleCaptures;
	}

}