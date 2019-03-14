package radua.ui.controllers;

import javax.swing.JComponent;

import radua.ui.listeners.KeyListenerImpl;
import radua.ui.listeners.MouseListenerImpl;
import radua.ui.views.BasicView;
import radua.ui.views.IBasicView;

public class StageWrapper implements IStageWrapper 
{
	public final JComponent mainView;
	private final WorldController worldCtrl;
	private final KeyListenerImpl keyListener;
	private final MouseListenerImpl mouseListener;
	
	
	public StageWrapper(WorldController worldController, JComponent view) {
		worldCtrl = worldController;
		mainView = view;
		keyListener = new KeyListenerImpl(worldCtrl);
		mouseListener = new MouseListenerImpl(worldCtrl);
		mainView.setFocusable(true);
		mainView.addKeyListener(keyListener);
	}


	public void addNewView(IBasicView<?> view) {
		BasicView<?> viewImpl = (BasicView<?>) view;
		mainView.add(viewImpl);
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
		mainView.remove(viewImpl);
		mainView.repaint();
	}
}
