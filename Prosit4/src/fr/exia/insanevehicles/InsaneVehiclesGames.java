package fr.exia.insanevehicles;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.SwingUtilities;

import fr.exia.insanevehicles.element.mobile.MyVehicle;
import fr.exia.showboard.BoardFrame;

/**
 * <h1>The InsaneVehiclesGames Class.</h1>
 *
 * @author Jade
 * @version 0.3
 */
public class InsaneVehiclesGames implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant roadView. */
	public static final int roadView = 10;

	/** The Constant roadQuota. */
	public static final int roadQuota = 20;

	/** The Constant startX. */
	private static final int startX = 5;

	/** The Constant startY. */
	private static final int startY = 0;

	/** The Constant squareSize. */
	private static final int squareSize = 30;

	/** The Constant keyRight. */
	private static final int keyRight = 37; // ils avaient mis 51, au cas où

	/** The Constant keyLeft. */
	private static final int keyLeft = 39; // ils avaient mis 49, au cas où

	/** The Constant closeView. */
	private Rectangle closeView;

	/** The road. */
	private Road road;

	/** My vehicle. */
	private MyVehicle myVehicle;

	/** The view. */
	private int view;
	
	/** la dirrection a prendre*/
	private static char dirrection;

	/**
	 * Instantiates a new insane vehicles games.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public InsaneVehiclesGames() throws IOException {
		this.setView(roadView);
		this.setRoad(new Road("road.txt", roadQuota));
		this.setMyVehicle(new MyVehicle(startX, startY, this.getRoad()));
		this.getMyVehicle().getSprite().loadImage();
		this.setCloseView(new Rectangle(0, this.getMyVehicle().getY(), this.getRoad().getWidth(), roadView));
		SwingUtilities.invokeLater(this);
	}

	/**
	 * Play.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public final void play() throws IOException {
		while (this.getMyVehicle().isAlive()) {
			
			System.out.println("dodo");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
System.out.println("plus dodo");

			// on délègue la détection

			switch (this.getDirrection()) {
			case 'd':
				this.getMyVehicle().moveRight();
				break;
			case 'g':
				this.getMyVehicle().moveLeft();
				break;
			default:
				break;
			}
			
			resetDirrection();

			/*
			 * final int key = System.in.read(); switch (key) { case keyRight:
			 * this.getMyVehicle().moveRight(); break; case keyLeft:
			 * this.getMyVehicle().moveLeft(); break; default: break; }
			 */

			this.getMyVehicle().moveDown();
			this.getCloseView().y = this.getMyVehicle().getY() - 1;
			this.getRoad().setMobileHasChanged();
		}
		System.out.println("CRASH !!!!!!!!!\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public final void run() {
		final BoardFrame boardFrame = new BoardFrame("Close view");
		boardFrame.setDimension(new Dimension(this.getRoad().getWidth(), this.getRoad().getHeight()));
		boardFrame.setDisplayFrame(this.closeView);
		boardFrame.setSize(this.closeView.width * squareSize, this.closeView.height * squareSize);
		boardFrame.setHeightLooped(true);

		/** added by me */
		boardFrame.addKeyListener(new MyKeyListener());
		/** */

		for (int x = 0; x < this.getRoad().getWidth(); x++) {
			for (int y = 0; y < this.getRoad().getHeight(); y++) {
				boardFrame.addSquare(this.road.getOnTheRoadXY(x, y), x, y);
			}
		}
		boardFrame.addPawn(this.getMyVehicle());

		this.getRoad().addObserver(boardFrame.getObserver());

		boardFrame.setVisible(true);
	}

	/**
	 * Prints the road and the player's vehicle in the console.
	 *
	 * @param yStart
	 *            the y start
	 */
	public final void show(final int yStart) {
		int y = yStart % this.getRoad().getHeight();
		for (int view = 0; view < this.getView(); view++) {
			for (int x = 0; x < this.getRoad().getWidth(); x++) {
				if ((x == this.getMyVehicle().getX()) && (y == yStart)) {
					System.out.print(this.getMyVehicle().getSprite().getConsoleImage());
				} else {
					System.out.print(this.getRoad().getOnTheRoadXY(x, y).getSprite().getConsoleImage());
				}
			}
			y = (y + 1) % this.getRoad().getHeight();
			System.out.print("\n");
		}
	}

	/**
	 * Gets the road.
	 *
	 * @return the road
	 */
	public final Road getRoad() {
		return this.road;
	}

	/**
	 * Sets the road.
	 *
	 * @param road
	 *            the new road
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void setRoad(final Road road) throws IOException {
		this.road = road;
		for (int x = 0; x < this.getRoad().getWidth(); x++) {
			for (int y = 0; y < this.getRoad().getHeight(); y++) {
				this.getRoad().getOnTheRoadXY(x, y).getSprite().loadImage();
			}
		}
	}

	/**
	 * Gets my vehicle.
	 *
	 * @return my vehicle
	 */
	public final MyVehicle getMyVehicle() {
		return this.myVehicle;
	}

	/**
	 * Sets my vehicle.
	 *
	 * @param myVehicle
	 *            my new vehicle
	 */
	public final void setMyVehicle(final MyVehicle myVehicle) {
		this.myVehicle = myVehicle;
	}

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public final int getView() {
		return this.view;
	}

	/**
	 * Sets the view.
	 *
	 * @param view
	 *            the new view
	 */
	private void setView(final int view) {
		this.view = view;
	}

	/**
	 * Gets the close view.
	 *
	 * @return the close view
	 */
	private Rectangle getCloseView() {
		return this.closeView;
	}

	/**
	 * Sets the close view.
	 *
	 * @param closeView
	 *            the new close view
	 */
	private void setCloseView(final Rectangle closeView) {
		this.closeView = closeView;
	}

	public char getDirrection() {
		return dirrection;
	}

	public static void setDirrection(char dirrection) {
		InsaneVehiclesGames.dirrection = dirrection;
	}
	
	public static void resetDirrection(){
		InsaneVehiclesGames.setDirrection('a');
	}

}
