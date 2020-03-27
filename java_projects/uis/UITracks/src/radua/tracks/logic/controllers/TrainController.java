package radua.tracks.logic.controllers;

import radua.tracks.display.controllers.TrackViewFactory;
import radua.tracks.logic.models.TrackModel;
import radua.tracks.logic.models.Train;
import radua.ui.logic.basics.IReadablePoint;
import radua.ui.logic.controllers.WorldController;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.models.snaps.DirectionalSnapPoint;
import radua.ui.logic.utils.Calculus;


public class TrainController extends WorldController
{
	private Train train;
	private TrainMover mover;
	
	private DirectionalSnapPoint lastPoint;
	private DirectionalSnapPoint nextPoint;
	private double delta_x;
	private double delta_y;
	private int totalSteps;
	private int crtStreps;

	
	
	public TrainController() {
		super(new TrackViewFactory());
	}
	
	public void addTrain() {
		if (train == null) {
			train = new Train(200, 200);
			mover = new TrainMover();
			resetTrain();
			addModel(train);
		}
		train.visible(true);
	}
	
	public void hideTrain() {
		mover.stopMove();
		train.visible(false);
	}
	
	public void startTrain() {
		addTrain();
		mover.startMove();
//		print();
	}
	
	public void stopTrain() {
		mover.stopMove();
	}
	
	
	private void resetTrain() {
		DirectionalSnapPoint startPoint = getStartingPoint();
		moveTrain(startPoint.absoluteA());
	}
	
	
	private DirectionalSnapPoint getStartingPoint() {
		for (IBasicModel model : getModels()) {
			if (model instanceof TrackModel) {
				return (DirectionalSnapPoint) ((TrackModel) model).snapPoints().get(0);
			}
		}
		return null;
	}
	
	
	/*package*/ void stepMove() {
		train.moveBy(1, 0);
	}
	
	
	private void moveTrain(IReadablePoint point) {
		train.moveTo(point.x()-train.width()/2, point.y()-train.height()/2);
	}

	
	private void moveTheTrain() {
		boolean validPoints = resetPointValues();
		computeStepValues();
		while (validPoints) {
			for (int i = 0; i < totalSteps; i++) {
				
				train.moveTo(lastPoint.absoluteA().x() + delta_x * i - train.width() / 2, 
						lastPoint.absoluteA().y() + delta_y * i - train.height() / 2);
				
			
				//print point
	//			printPoint(lastPoint);
				try { Thread.sleep(50); } 
				catch (InterruptedException e) { e.printStackTrace(); }
	//			printPoint(nextPoint);
				
			}
			
			
			
			validPoints = nextPointValues();
			computeStepValues();
		}
	}
	
	private boolean resetPointValues() {
		lastPoint = getStartingPoint();
		if (lastPoint == null)
			return false;
		nextPoint = ((TrackModel) lastPoint.parent()).otherEnd(lastPoint);
		return true;
	}
	
	private boolean nextPointValues() {
		lastPoint = (DirectionalSnapPoint) nextPoint.getSnap();
		if (lastPoint == null)
			return false;
		nextPoint = ((TrackModel) lastPoint.parent()).otherEnd(lastPoint);
		if (nextPoint == null)
			return false;
		return true;
	}
	
	private void computeStepValues() {
		if (lastPoint == null || nextPoint == null) 
			return;
		double m = Calculus.computeM(lastPoint.absoluteA(), nextPoint.absoluteA());
		delta_x = 1;
		if (lastPoint.absoluteA().x() > nextPoint.absoluteA().x())
			delta_x = -delta_x;
		delta_y = m * delta_x;
		totalSteps = (int) Math.floor(Math.abs(lastPoint.absoluteA().x() - nextPoint.absoluteA().x()));
		crtStreps = 0;
	}
	
	
	
	
	private void printPoint(DirectionalSnapPoint point) {
		System.out.println("Point "+point.parent().id().val+"."+point.id().val+" - "+point.absoluteA().toString());
	}
	
	
	// ==============================================================	
	// ==============================================================	
	
	private class TrainMover extends Thread
	{
		private boolean running;
		private boolean paused;
		private final Object pauseLock;
		
		public TrainMover() {
			running = true;
			paused = true;
			pauseLock = new Object();
			start();
		}
		
		public void startMove() {
			paused = false;
			synchronized (pauseLock) {
				pauseLock.notify();
			}
		}
		
		public void stopMove() {
			paused = true;
		}
		
		public void terminate() {
			running = false;
			paused = false;
			synchronized (pauseLock) {
				pauseLock.notify();
			}
		}
		
		public void run() {
			// test pause
			testPause();
			
			moveTheTrain();
			
			while (running) {
				// do stuff
				stepMove();
				// wait
				try { sleep(50); }
				catch (InterruptedException ex) {
					System.out.println("SLEEP intrerupted!");
				}
				
				// test pause
				testPause();
			}
		}
		
		
		private void testPause() {
			if (paused) {
				synchronized (pauseLock) {
					while (paused) {
						try {
							pauseLock.wait();
						}
						catch (InterruptedException ex) {
							System.out.println("PAUSE LOCK intrerupted!");
						}
					}
				}
			}
		}
	}
}
