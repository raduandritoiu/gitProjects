package radua.ui.controllers;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import radua.ui.models.IBasicModel;
import radua.ui.models.SnapModel;
import radua.ui.models.snaps.SnapResult;
import radua.ui.models.snaps.SnapResultMove;
import radua.ui.views.BasicView;


public class MovingController implements MouseListener, MouseMotionListener
{
	private final WorldController worldCtrl;
	
    private volatile int initScreenX = 0;
    private volatile int initScreenY = 0;
    private volatile int initCompX = 0;
    private volatile int initCompY = 0;
    private volatile BasicView<?> moveView;
    private volatile IBasicModel moveModel;
    private volatile SnapResult lastSnapResult;
    
    
    public MovingController(WorldController worldController) {
    	worldCtrl = worldController;
	}
    
    
	// ========== Mouse Listener ==========
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	Component comp = e.getComponent();
    	if (!(comp instanceof BasicView)) {
    		return;
    	}
		IBasicModel tmpModel = ((BasicView<?>) comp).getModel();
		List<IBasicModel> newSelection = new ArrayList<>();
		newSelection.add(tmpModel);
		worldCtrl.selection(newSelection);
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
    	Component comp = e.getComponent();
    	if (!(comp instanceof BasicView)) {
    		return;
    	}
    	
    	moveView = (BasicView<?>) comp;
		moveModel = moveView.getModel();
		List<IBasicModel> newSelection = new ArrayList<>();
		newSelection.add(moveModel);
		worldCtrl.selection(newSelection);

    	initScreenX = e.getXOnScreen();
        initScreenY = e.getYOnScreen();
        initCompX = moveModel.getX();
        initCompY = moveModel.getY();
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
    	if (lastSnapResult != null && lastSnapResult.result) {
			SnapResultMove snapMove = lastSnapResult.getMove();
			moveModel.translate(snapMove.translation);
			moveModel.rotateDelta(snapMove.rotation);
    	}
    	
    	moveView = null;
    	moveModel = null;
    }
	
    @Override
    public void mouseEntered(MouseEvent e) {
    	
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
    }
 
    
	// ========== Mouse Motion Listener ==========
    
	@Override
	public void mouseMoved(MouseEvent e) {
	}
  
    @Override
    public void mouseDragged(MouseEvent e) {
    	if (moveModel == null) {
    		return;
    	}
        int deltaScreenX = e.getXOnScreen() - initScreenX;
        int deltaScreenY = e.getYOnScreen() - initScreenY;
        moveModel.move(initCompX + deltaScreenX, initCompY + deltaScreenY);
        lastSnapResult = handleSnap(false);
    }
    
    
	// ===========================================
    
    private SnapResult handleSnap(boolean move) {
    	if (!(moveModel instanceof SnapModel))
    		return SnapResult.FALSE();
    	
    	for (IBasicModel model : worldCtrl.getModels()) {
    		if (moveModel == model) 
    			continue;
    		if (!(moveModel instanceof SnapModel)) 
    			continue;
    		
    		// try to see if it snaps with model
    		SnapResult snapResult = ((SnapModel) moveModel).snaps((SnapModel) model);
    		if (snapResult.result) {
    			return snapResult;
    		}
    	}
    	return SnapResult.FALSE();
    }
}
