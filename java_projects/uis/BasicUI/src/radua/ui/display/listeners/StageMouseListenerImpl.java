package radua.ui.display.listeners;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import radua.ui.display.stage.StageView;
import radua.ui.logic.controllers.WorldController;


public class StageMouseListenerImpl implements MouseListener, MouseMotionListener
{
	private final WorldController worldCtrl;
	private final StageView stageView;
	
    private /* volatile */ int initX = 0;
    private /* volatile */ int initY = 0;

	
	
    public StageMouseListenerImpl(WorldController worldController, StageView mainStageView) {
    	worldCtrl = worldController;
    	stageView = mainStageView;
	}

    
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}

	
	// ===================================================================
	// ==== Mouse Listener ===============================================
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	Component comp = e.getComponent();
    	if (!(comp instanceof StageView)) {
    		return;
    	}
    	// clear selection
    	worldCtrl.clearSelection();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    	Component comp = e.getComponent();
    	if (!(comp instanceof StageView)) {
    		return;
    	}
    	// clear selection
    	worldCtrl.clearSelection();
    	
    	initX = e.getX();
    	initY = e.getY();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    	stageView.updateSelection(initX, initY, e.getX(), e.getY());
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
    	worldCtrl.setSelection(initX, initY, e.getX(), e.getY());
    	stageView.updateSelection(0, 0, 0, 0);
    	initX = 0;
    	initY = 0;
    }
}
