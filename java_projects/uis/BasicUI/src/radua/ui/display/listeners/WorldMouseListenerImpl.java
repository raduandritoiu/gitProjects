package radua.ui.display.listeners;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import radua.ui.display.views.WorldView;
import radua.ui.logic.controllers.WorldController;


public class WorldMouseListenerImpl implements MouseListener, MouseMotionListener
{
	private final WorldController worldCtrl;
	
	
    public WorldMouseListenerImpl(WorldController worldController) {
    	worldCtrl = worldController;
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
    	if (!(comp instanceof WorldView)) {
    		return;
    	}
    	// clear selection
    	worldCtrl.clearSelection();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    	Component comp = e.getComponent();
    	if (!(comp instanceof WorldView)) {
    		return;
    	}
    	// clear selection
    	worldCtrl.clearSelection();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {}
 
    @Override
    public void mouseReleased(MouseEvent e) {}
}
