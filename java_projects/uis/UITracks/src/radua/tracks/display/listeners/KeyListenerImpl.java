package radua.tracks.display.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import radua.tracks.logic.controllers.TrainController;
import radua.tracks.logic.models.CurvedTrack;
import radua.tracks.logic.models.SplitTrack;
import radua.tracks.logic.models.StraightTrack;
import radua.tracks.logic.persistance.PersistTracks;
import radua.ui.logic.controllers.KeyLogic;
import radua.ui.logic.models.IBasicModel;


public class KeyListenerImpl implements KeyListener
{
	private final TrainController worldCtrl;
	private final KeyLogic keyLogic;
	
    public KeyListenerImpl(TrainController worldController) {
    	worldCtrl = worldController;
    	keyLogic = new KeyLogic();
	}
    
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 83) {
			PersistTracks persist = new PersistTracks(worldCtrl.getModels());
			persist.save();
			return;
		}
		
		// l - 76 for load
		if (e.getKeyCode() == 76) {
			PersistTracks persist = new PersistTracks();
			persist.load();
			for (IBasicModel model : persist.getModels()) {
				worldCtrl.addModel(model);
			}
			return;
		}
		
		
		if (keyLogic.handleKeyEvent(e, worldCtrl)) 
			return;
		
		
		if (e.getKeyCode() == 84) {
			worldCtrl.addModel(new SplitTrack(100, 100));
		}

		// ==== adding models =============
		// 1 - 49 add STRAIGHT track
		else if (e.getKeyCode() == 49) {
			worldCtrl.addModel(new StraightTrack(100, 100));
		}
		// 2 - 50 add CURVED track
		else if (e.getKeyCode() == 50) {
			worldCtrl.addModel(new CurvedTrack(100, 100));
		}
		// 3 - 51 add SPLIT track
		else if (e.getKeyCode() == 51) {
			worldCtrl.addModel(new SplitTrack(100, 100));
		}
		// 4 - 52 add SPLIT track
		else if (e.getKeyCode() == 52) {
			worldCtrl.addTrain();
		}
	}
}
