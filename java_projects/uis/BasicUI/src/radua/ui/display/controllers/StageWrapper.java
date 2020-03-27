package radua.ui.display.controllers;

import radua.ui.display.listeners.KeyListenerImpl;
import radua.ui.display.listeners.ModelMouseListenerImpl;
import radua.ui.display.listeners.WorldMouseListenerImpl;
import radua.ui.display.views.BasicView;
import radua.ui.display.views.WorldView;
import radua.ui.logic.controllers.IStageWrapper;
import radua.ui.logic.controllers.WorldController;
import radua.ui.logic.views.IBasicView;


public class StageWrapper implements IStageWrapper 
{
	public final WorldView mainView;
	private final WorldController worldCtrl;
	private final KeyListenerImpl keyListener;
	private final ModelMouseListenerImpl modelMouseListener;
	private final WorldMouseListenerImpl worldMouseListener;
	
	
	public StageWrapper(WorldController worldController, WorldView view) {
		worldCtrl = worldController;
		mainView = view;
		keyListener = new KeyListenerImpl(worldCtrl);
		modelMouseListener = new ModelMouseListenerImpl(worldCtrl);
		worldMouseListener = new WorldMouseListenerImpl(worldCtrl);
		mainView.setFocusable(true);
		mainView.addKeyListener(keyListener);
		mainView.addMouseListener(worldMouseListener);
		mainView.addMouseMotionListener(worldMouseListener);
	}


	public void addNewView(IBasicView<?> view) {
		BasicView<?> viewImpl = (BasicView<?>) view;
		mainView.add(viewImpl);
		viewImpl.addKeyListener(keyListener);
		viewImpl.addMouseListener(modelMouseListener);
		viewImpl.addMouseMotionListener(modelMouseListener);
		viewImpl.repaint();
	}
	
	public void removeView(IBasicView<?> view) {
		BasicView<?> viewImpl = (BasicView<?>) view;
		viewImpl.removeKeyListener(keyListener);
		viewImpl.removeMouseListener(modelMouseListener);
		viewImpl.removeMouseMotionListener(modelMouseListener);
		mainView.remove(viewImpl);
		mainView.repaint();
	}
}
