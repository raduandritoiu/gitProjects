package radua.ui.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import radua.ui.models.BasicModel;
import radua.ui.models.tracks.CurvedTrack;
import radua.ui.models.tracks.SplitTrack;
import radua.ui.models.tracks.StraightTrack;
import radua.ui.utils.Debug;
import radua.ui.views.tracks.TrackView;


public class KeyController implements KeyListener
{
	private static final int DELTA_MOVE = 1;
	private static final double DELTA_ROTATION = Math.PI / 6;
	
	
	private final WorldController worldCtrl;
	
    public KeyController(WorldController worldController) {
    	worldCtrl = worldController;
	}

	@Override
	public void keyTyped(KeyEvent e) {
//		System.out.println("Key typed: " + e.getKeyCode() + " : " + e.getKeyChar() + " : " + e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		BasicModel[] selection = worldCtrl.getSelection();
		BasicModel model = null;
		if (selection.length == 1) {
			model = selection[0];
		}

		// ==== adding models =============
		// 1 - 49 add STRAIGHT track
		if (e.getKeyCode() == 49) {
			worldCtrl.addView(new TrackView(new StraightTrack(100, 100)));
		}
		// 2 - 50 add CURVED track
		else if (e.getKeyCode() == 50) {
			worldCtrl.addView(new TrackView(new CurvedTrack(100, 100)));
		}
		// 3 - 51 add SPLIT track
		else if (e.getKeyCode() == 51) {
			worldCtrl.addView(new TrackView(new SplitTrack(100, 100)));
		}
		// d - 68 add SPLIT track
		else if (e.getKeyCode() == 68) {
			if (model == null) return;
			worldCtrl.removeModel(model);
		}
		
		
		// ==== rotates =============
		// UP - 38 move model
		else if (e.getKeyCode() == 38) {
			if (model == null) return;
			model.translate(0, -DELTA_MOVE);
		}
		// DOWN - 40 move model
		else if (e.getKeyCode() == 40) {
			if (model == null) return;
			model.translate(0, DELTA_MOVE);
		}
		// LEFT - 37 move model
		else if (e.getKeyCode() == 37) {
			if (model == null) return;
			model.translate(-DELTA_MOVE, 0);
		}
		// RIGHT - 39 move model
		else if (e.getKeyCode() == 39) {
			if (model == null) return;
			model.translate(DELTA_MOVE, 0);
		}
		
		// ==== rotates =============
		// q - 81 for rotate LEFT
		if (e.getKeyCode() == 81) {
			if (model == null) return;
			model.rotateDelta(-DELTA_ROTATION);
		}
		// w - 87 for rotate RIGHT
		else if (e.getKeyCode() == 87) {
			if (model == null) return;
			model.rotateDelta(DELTA_ROTATION);
		}
		// r - 77 for rotate RESET
		else if (e.getKeyCode() == 82) {
			if (model == null) return;
			model.rotate(0);
		}
		
		// ==== split track =======
		// a - 65 move switch for Split track to go RIGHT
		else if (e.getKeyCode() == 65) {
			if (model == null) return;
			if (model instanceof SplitTrack) {
				((SplitTrack) model).goLeft();
			}
		}
		// s - 83 move switch for Split track to go RIGHT
		else if (e.getKeyCode() == 83) {
			if (model == null) return;
			if (model instanceof SplitTrack) {
				((SplitTrack) model).goRight();
			}
		}
		
		// ==== debugs =============
		// p - 80 for debug print
		else if (e.getKeyCode() == 80) {
			Debug.printWorldController(worldCtrl);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("Key release: " + e.getKeyCode() + " : " + e.getKeyChar() + " : " + e);
	}
}
