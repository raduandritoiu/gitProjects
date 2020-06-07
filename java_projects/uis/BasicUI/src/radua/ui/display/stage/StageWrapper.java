package radua.ui.display.stage;

import radua.ui.display.listeners.KeyListenerImpl;
import radua.ui.display.listeners.ModelMouseListenerImpl;
import radua.ui.display.listeners.StageMouseListenerImpl;
import radua.ui.logic.controllers.IStageWrapper;
import radua.ui.logic.controllers.WorldController;
import radua.ui.logic.view.IModelView;
import radua.ui.views.BasicView;


public class StageWrapper implements IStageWrapper 
{
	public final StageView stageView;
	private final WorldController worldCtrl;
	private final KeyListenerImpl keyListener;
	private final ModelMouseListenerImpl modelMouseListener;
	private final StageMouseListenerImpl worldMouseListener;
	
	
	public StageWrapper(WorldController worldController, StageView view) {
		worldCtrl = worldController;
		stageView = view;
		keyListener = new KeyListenerImpl(worldCtrl);
		modelMouseListener = new ModelMouseListenerImpl(worldCtrl);
		worldMouseListener = new StageMouseListenerImpl(worldCtrl, stageView);
		stageView.setFocusable(true);
		stageView.addKeyListener(keyListener);
		stageView.addMouseListener(worldMouseListener);
		stageView.addMouseMotionListener(worldMouseListener);
	}


	public void addNewView(IModelView<?> view) {
		BasicView<?> viewImpl = (BasicView<?>) view;
		stageView.add(viewImpl);
		viewImpl.addKeyListener(keyListener);
		viewImpl.addMouseListener(modelMouseListener);
		viewImpl.addMouseMotionListener(modelMouseListener);
		viewImpl.repaint();
	}
	
	public void removeView(IModelView<?> view) {
		BasicView<?> viewImpl = (BasicView<?>) view;
		viewImpl.removeKeyListener(keyListener);
		viewImpl.removeMouseListener(modelMouseListener);
		viewImpl.removeMouseMotionListener(modelMouseListener);
		stageView.remove(viewImpl);
		stageView.repaint();
	}
}
