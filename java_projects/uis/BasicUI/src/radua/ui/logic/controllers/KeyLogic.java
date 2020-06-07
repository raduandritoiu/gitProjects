package radua.ui.logic.controllers;

import radua.ui.logic.basics.MKeyPress;
import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.utils.Debug;


public class KeyLogic {
	private static final int DELTA_MOVE = 1;
	private static final double DELTA_ROTATION = Math.PI / 6;

	
	public boolean handleKeyEvent(MKeyPress key, WorldController worldCtrl) {
		// ==== rotates =============
		// UP - 38 move model
		if (key.keyCode == 38) {
			worldCtrl.selectionMoveBy(0, -DELTA_MOVE);
			return true;
		}
		// DOWN - 40 move model
		if (key.keyCode == 40) {
			worldCtrl.selectionMoveBy(0, DELTA_MOVE);
			return true;
		}
		// LEFT - 37 move model
		if (key.keyCode == 37) {
			worldCtrl.selectionMoveBy(-DELTA_MOVE, 0);
			return true;
		}
		// RIGHT - 39 move model
		if (key.keyCode == 39) {
			worldCtrl.selectionMoveBy(DELTA_MOVE, 0);
			return true;
		}
		
		// ==== rotates =============
		// q - 81 for rotate LEFT
		if (key.keyCode == 81) {
			worldCtrl.selectionRotateBy(-DELTA_ROTATION);
			return true;
		}
		// w - 87 for rotate RIGHT
		if (key.keyCode == 87) {
			worldCtrl.selectionRotateBy(DELTA_ROTATION);
			return true;
		}
		// r - 77 for rotate RESET
		if (key.keyCode == 82) {
			worldCtrl.selectionResetRotation();
			return true;
		}
		
		// ==== visible =============
		// v - 86 for tobble visible
		if (key.keyCode == 86) {
			for (IBasicModel model : worldCtrl.getSelection()) {
				model.visible(!model.isVisible());
			}
			return true;
		}
		
		// ==== debugs =============
		// p - 80 for debug print
		if (key.keyCode == 80) {
			Debug.printWorldController(worldCtrl);
			return true;
		}
		
		return false;
	}
}
