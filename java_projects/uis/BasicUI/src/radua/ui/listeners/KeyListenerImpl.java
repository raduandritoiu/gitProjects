package radua.ui.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import radua.ui.controllers.KeyHelper;
import radua.ui.controllers.WorldController;
import radua.ui.models.IBasicModel;
import radua.ui.models.tracks.CurvedTrack;
import radua.ui.models.tracks.SplitTrack;
import radua.ui.models.tracks.StraightTrack;
import radua.ui.views.tracks.TrackView;


public class KeyListenerImpl implements KeyListener
{
	private final WorldController worldCtrl;
	private final KeyHelper keyHelper;
	
	
    public KeyListenerImpl(WorldController worldController) {
    	worldCtrl = worldController;
    	keyHelper = new KeyHelper();
	}

	@Override
	public void keyTyped(KeyEvent e) {
//		System.out.println("Key typed: " + e.getKeyCode() + " : " + e.getKeyChar() + " : " + e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (keyHelper.handleKeyEvent(e, worldCtrl)) {
			return;
		}
		
		List<IBasicModel> selection = worldCtrl.getSelection();
		IBasicModel model = null;
		if (selection.size() == 1) {
			model = selection.get(0);
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
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("Key release: " + e.getKeyCode() + " : " + e.getKeyChar() + " : " + e);
	}
}
