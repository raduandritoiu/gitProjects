package radua.ui.display.listeners;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import radua.ui.logic.controllers.MovementLogic;
import radua.ui.logic.controllers.WorldController;
import radua.ui.logic.models.IBasicModel;
import radua.ui.views.BasicView;

public class ModelMouseListenerImpl implements MouseListener, MouseMotionListener
{
	private final WorldController worldCtrl;
	private final MovementLogic movementLogic;
	
	
    public ModelMouseListenerImpl(WorldController worldController) {
    	worldCtrl = worldController;
		movementLogic = new MovementLogic(worldCtrl);
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
		updateSelection(((BasicView<?>) comp).model(), e.isControlDown());
    }
    
    
    @Override
    public void mousePressed(MouseEvent e) {
    	Component comp = e.getComponent();
    	if (!(comp instanceof BasicView)) {
    		return;
    	}
    	IBasicModel crtModel = ((BasicView<?>) comp).model();
    	// update selection
		updateSelection(crtModel, e.isControlDown());
		
		// start moving
		movementLogic.startMovement(crtModel, e.getXOnScreen(), e.getYOnScreen());
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    	// continue moving
    	movementLogic.updateMovement(e.getXOnScreen(), e.getYOnScreen());
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
    	// end moving
    	movementLogic.endMovement();
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
