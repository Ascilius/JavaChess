import java.awt.Point;
import java.awt.Rectangle;

public class Move {

	private Point location;
	private Rectangle bounds;

	public Move(Point location, int spaceWidth) {
		this.location = location;
		updateBounds(spaceWidth);
	}

	public void updateLocation(Point newLocation, int spaceWidth) {
		location = newLocation;
		updateBounds(spaceWidth);
	}

	public void updateBounds(int spaceWidth) { // (0, 0) is at the center of the screen
		bounds = new Rectangle((int) (spaceWidth * -4 + spaceWidth * location.getX()), (int) (spaceWidth * -4 + spaceWidth * location.getY()), spaceWidth, spaceWidth);
	}

	public Point getLocation() {
		return location;
	}

	public Rectangle getBounds() {
		return bounds;
	}

}
