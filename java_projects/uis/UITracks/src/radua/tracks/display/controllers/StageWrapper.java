package radua.tracks.display.controllers;

import radua.tracks.display.listeners.KeyListenerImpl;
import radua.tracks.display.views.TrackView;
import radua.tracks.display.views.WorldView;
import radua.tracks.logic.controllers.TrainController;
import radua.ui.display.listeners.MouseListenerImpl;
import radua.ui.display.views.BasicView;
import radua.ui.logic.controllers.IStageWrapper;
import radua.ui.logic.views.IBasicView;


public class StageWrapper implements IStageWrapper 
{
	public final WorldView mainView;
	private final TrainController worldCtrl;
	private final KeyListenerImpl keyListener;
	private final MouseListenerImpl mouseListener;
	
	public StageWrapper(TrainController worldController, WorldView view) {
		worldCtrl = worldController;
		mainView = view;
		keyListener = new KeyListenerImpl(worldCtrl);
		mouseListener = new MouseListenerImpl(worldCtrl);
		mainView.setFocusable(true);
		mainView.addKeyListener(keyListener);
	}

	public void addNewView(IBasicView<?> view) {
		BasicView<?> viewImpl = (BasicView<?>) view;
		if (view instanceof TrackView)
			mainView.getLowerLayer().add(viewImpl);
		else
			mainView.getUpperLayer().add(viewImpl);
		viewImpl.addKeyListener(keyListener);
		viewImpl.addMouseListener(mouseListener);
		viewImpl.addMouseMotionListener(mouseListener);
		viewImpl.repaint();
	}
	
	public void removeView(IBasicView<?> view) {
		BasicView<?> viewImpl = (BasicView<?>) view;
		viewImpl.removeKeyListener(keyListener);
		viewImpl.removeMouseListener(mouseListener);
		viewImpl.removeMouseMotionListener(mouseListener);
		if (view instanceof TrackView)
			mainView.getLowerLayer().remove(viewImpl);
		else
			mainView.getUpperLayer().remove(viewImpl);
		mainView.repaint();
	}
}
