package radua.ui.controllers;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import radua.ui.models.IBasicModel;
import radua.ui.models.ISnapModel;
import radua.ui.models.SnapModel;
import radua.ui.models.snaps.ISnapPoint;
import radua.ui.models.snaps.SnapResult;
import radua.ui.models.snaps.SnapResultMove;
import radua.ui.observers.ObservableEvent;
import radua.ui.utils.Constants;
import radua.ui.views.BasicView;


public class MovingController implements MouseListener, MouseMotionListener
{
	private final WorldController worldCtrl;
	
    private /* volatile */ double initScreenX = 0;
    private /* volatile */ double initScreenY = 0;
    private /* volatile */ double initMovingX = 0;
    private /* volatile */ double initMovingY = 0;
    private /* volatile */ double[] initSelectionX = null;
    private /* volatile */ double[] initSelectionY = null;
    private /* volatile */ IBasicModel[] initSelectionModels = null;
    private /* volatile */ IBasicModel movingModel = null;
    private /* volatile */ SnapResult lastSnapResult = null;
    
    
    public MovingController(WorldController worldController) {
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
		startMovement(crtModel, e.getXOnScreen(), e.getYOnScreen());
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    	if (movingModel == null) {
    		return;
    	}
    	// continue moving 
    	updateMovement(e.getXOnScreen(), e.getYOnScreen());
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
    	// end moving
    	endMovement();
    }
	
    
    
    
	// ===================================================================
	// ===================================================================
    
    private void updateSelection(IBasicModel selectedModel, boolean add) {
		if (add) {
			worldCtrl.addSelection(selectedModel);
		}
		else {
			worldCtrl.setSelection(selectedModel);
		}
    }
    
    private void startMovement(IBasicModel newMovingModel, double x, double y) {
		movingModel = newMovingModel;
		
    	initScreenX = x;
        initScreenY = y;
        initMovingX = movingModel.x();
        initMovingY = movingModel.y();
        
        int selectionSize = worldCtrl.selectionSize() - 1;
        if (selectionSize > 0) {
        	int i = 0;
	        initSelectionModels = new IBasicModel[selectionSize];
	        for (IBasicModel model : worldCtrl.getSelection()) {
	        	if (model != movingModel) {
	        		initSelectionModels[i++] = model;
	        	}
	        }
        	initSelectionX = new double[selectionSize];
        	initSelectionY = new double[selectionSize];
        	for (i = 0; i < initSelectionModels.length; i++) {
        		initSelectionX[i] = initSelectionModels[i].x();
        		initSelectionY[i] = initSelectionModels[i].y();
        	}
        }
    }
    
    private void updateMovement(double x, double y) {
    	if (movingModel == null) {
    		return;
    	}
        
    	// move selection
    	double deltaScreenX = x - initScreenX;
    	double deltaScreenY = y - initScreenY;
        movingModel.moveTo(initMovingX + deltaScreenX, initMovingY + deltaScreenY);
        if (initSelectionX != null) {
        	for (int i = 0; i < initSelectionX.length; i++) {
        		if (initSelectionModels[i] != movingModel) {
        			initSelectionModels[i].moveTo(initSelectionX[i] + deltaScreenX, initSelectionY[i] + deltaScreenY);
        		}
        	}
        }
        
        lastSnapResult = handleSnap(movingModel, null);
    }
    
    private void endMovement() {
    	if (lastSnapResult != null && lastSnapResult.result) {
			SnapResultMove snapMove = lastSnapResult.getMove();
			movingModel.moveBy(snapMove.translation);
			movingModel.rotateBy(snapMove.rotation);
	        handleSnap(movingModel, lastSnapResult.localSnap);
    	}
    	
    	movingModel = null;
    	initSelectionX = null;
    	initSelectionY = null;
    	initSelectionModels = null;
    }
    
    private SnapResult handleSnap(IBasicModel localSnapModel, ISnapPoint ignoreSnap) {
    	if (!(localSnapModel instanceof SnapModel))
    		return SnapResult.FALSE();
    	ISnapModel localModel = (ISnapModel) localSnapModel;
    	
    	SnapResult snapResult = SnapResult.FALSE();
    	double resultStrength = Constants.MAX_INF;
    	
		for (ISnapPoint localSnap : localModel.snapPoints()) {
			// ignor give snap point
			if (localSnap == ignoreSnap)
				continue;
				
			// cache info about last snap
			ISnapPoint lastRemoteSnap = localSnap.getSnap();
			ISnapPoint newRemoteSnap = lastRemoteSnap;
			double newSnapStrength = Constants.MAX_INF;
			if (lastRemoteSnap != null) {
				// skip over already snaps to selection
				if (lastRemoteSnap.parent().isSelected()) {
					continue;
				}				
    			newSnapStrength = localSnap.getSnapStength(lastRemoteSnap);
			}
			
			// find model with best snap strength
	    	for (IBasicModel model : worldCtrl.getModels()) {
				// do not snap with these models
	    		if (localSnapModel == model) 
	    			continue;
	    		if (model.isSelected())
	    			continue;
	    		if (!(model instanceof SnapModel)) 
	    			continue;
	    		SnapModel remoteModel = (SnapModel) model;
	    		
				// find point with best snap strength
	    		for (ISnapPoint remoteSnap : remoteModel.snapPoints()) {
	    			// skip last remote snap point
	    			if (remoteSnap == lastRemoteSnap)
	    				continue;
	    			if (localSnap.canSnap(remoteSnap)) {
						double snapStrength = localSnap.getSnapStength(remoteSnap);
	    				if (snapStrength < newSnapStrength) {
	    					newSnapStrength = snapStrength;
	    					newRemoteSnap = remoteSnap;
	    				}
	    			}
	    		}
	    	}
	    	
			// update the snap point
			if (newRemoteSnap != lastRemoteSnap) {
				
				String str = "============ update SNAP POINT local:"+localModel.id()+"."+localSnap.id()+
						"  new:"+newRemoteSnap.parent().id()+"."+newRemoteSnap.id()+"  last:";
				
				if (lastRemoteSnap != null) {
    				lastRemoteSnap.setSnap(null);
    				lastRemoteSnap.parent().notifyObservers(ObservableEvent.SNAP_CHANGE, false);
    				
    				str += lastRemoteSnap.parent().id()+"."+lastRemoteSnap.id();
				}
				else {
					str += "null";
				}
				System.out.println(str);
				
				localSnap.setSnap(newRemoteSnap);
				newRemoteSnap.setSnap(localSnap);
				localModel.notifyObservers(ObservableEvent.SNAP_CHANGE, true);
				newRemoteSnap.parent().notifyObservers(ObservableEvent.SNAP_CHANGE, true);
				
				// update final snapResult
				if (newSnapStrength < resultStrength) {
					snapResult = SnapResult.TRUE(localSnap, newRemoteSnap);
					resultStrength = newSnapStrength;
				}
			}
			// test if last snap still snaps
			else if (lastRemoteSnap != null) {
				if (!localSnap.canSnap(lastRemoteSnap)) {
    				
					System.out.println("============ remove SNAP POINT local:"+localModel.id()+"."+localSnap.id()+
							"  last:"+lastRemoteSnap.parent().id()+"."+lastRemoteSnap.id());
					
					localSnap.setSnap(null);
    				lastRemoteSnap.setSnap(null);
    				localModel.notifyObservers(ObservableEvent.SNAP_CHANGE, false);
    				lastRemoteSnap.parent().notifyObservers(ObservableEvent.SNAP_CHANGE, false);
    			}
				else if (newSnapStrength < resultStrength) {
					System.out.println("============ keep on SNAP POINT local:"+localModel.id()+"."+localSnap.id()+
						"  last:"+lastRemoteSnap.parent().id()+"."+lastRemoteSnap.id());
					snapResult = SnapResult.TRUE(localSnap, lastRemoteSnap);
					resultStrength = newSnapStrength;
    			}
    			// remove last snap
			}
    		else {
    			// nothing to do, apparently even last remote snap was null
    		}
    	}
	    	
    	return snapResult;
    }
    
    
}
