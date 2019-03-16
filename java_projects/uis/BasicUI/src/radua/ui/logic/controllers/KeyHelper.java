package radua.ui.logic.controllers;

import java.awt.event.KeyEvent;

import radua.ui.logic.utils.Debug;


public class KeyHelper {
	private static final int DELTA_MOVE = 1;
	private static final double DELTA_ROTATION = Math.PI / 6;

	
	public boolean handleKeyEvent(KeyEvent e, WorldController worldCtrl) {
		// ==== rotates =============
		// UP - 38 move model
		if (e.getKeyCode() == 38) {
			worldCtrl.selectionMoveBy(0, -DELTA_MOVE);
			return true;
		}
		// DOWN - 40 move model
		if (e.getKeyCode() == 40) {
			worldCtrl.selectionMoveBy(0, DELTA_MOVE);
			return true;
		}
		// LEFT - 37 move model
		if (e.getKeyCode() == 37) {
			worldCtrl.selectionMoveBy(-DELTA_MOVE, 0);
			return true;
		}
		// RIGHT - 39 move model
		if (e.getKeyCode() == 39) {
			worldCtrl.selectionMoveBy(DELTA_MOVE, 0);
			return true;
		}
		
		// ==== rotates =============
		// q - 81 for rotate LEFT
		if (e.getKeyCode() == 81) {
			worldCtrl.selectionRotateBy(-DELTA_ROTATION);
			return true;
		}
		// w - 87 for rotate RIGHT
		if (e.getKeyCode() == 87) {
			worldCtrl.selectionRotateBy(DELTA_ROTATION);
			return true;
		}
		// r - 77 for rotate RESET
		if (e.getKeyCode() == 82) {
			worldCtrl.selectionResetRotation();
			return true;
		}
		
		// ==== debugs =============
		// p - 80 for debug print
		if (e.getKeyCode() == 80) {
			Debug.printWorldController(worldCtrl);
			return true;
		}
		
		return false;
	}
}
