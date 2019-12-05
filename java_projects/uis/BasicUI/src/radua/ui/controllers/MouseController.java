package radua.ui.controllers;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import radua.ui.models.IBasicModel;
import radua.ui.views.BasicView;

public class MouseController implements MouseListener, MouseMotionListener
{
	private final WorldController worldCtrl;
	
    public MouseController(WorldController worldController) {
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
    	if (!(comp instanceof BasicView)) {
    		return;
    	}
    	// update selection
		updateSelection(((BasicView<?>) comp).getModel(), e.isControlDown());
    }
    
    
    @Override
    public void mousePressed(MouseEvent e) {
    	Component comp = e.getComponent();
    	if (!(comp instanceof BasicView)) {
    		return;
    	}
    	IBasicModel  crtModel = ((BasicView<?>) comp).getModel();
    	// update selection
		updateSelection(crtModel, e.isControlDown());
		
		// start moving
		worldCtrl.movingController().startMovement(crtModel, e.getXOnScreen(), e.getYOnScreen());
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    	// continue moving
    	worldCtrl.movingController().updateMovement(e.getXOnScreen(), e.getYOnScreen());
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
    	// end moving
    	worldCtrl.movingController().endMovement();
    }
    
    
    private void updateSelection(IBasicModel selectedModel, boolean add) {
		if (add) {
			worldCtrl.addSelection(selectedModel);
		}
		else {
			worldCtrl.setSelection(selectedModel);
		}
    }
}
