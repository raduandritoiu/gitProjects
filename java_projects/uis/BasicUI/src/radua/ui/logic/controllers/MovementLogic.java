package radua.ui.logic.controllers;

import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.models.ISnapModel;
import radua.ui.logic.models.SnapModel;
import radua.ui.logic.models.snaps.ISnapPoint;
import radua.ui.logic.models.snaps.SnapResult;
import radua.ui.logic.models.snaps.SnapResultMove;
import radua.ui.logic.observers.ObservableProperty;
import radua.ui.logic.utils.Constants;


public class MovementLogic
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
    
    
    public MovementLogic(WorldController worldController) {
    	worldCtrl = worldController;
	}
    
    
    public void startMovement(IBasicModel newMovingModel, double x, double y) {
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
    
    public void updateMovement(double x, double y) {
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
    
    public void endMovement() {
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
			ISnapPoint lastRemoteSnap = localSnap.getSnapPeer();
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
				if (lastRemoteSnap != null) {
    				lastRemoteSnap.setSnapPeer(null);
    				lastRemoteSnap.parent().notifyObservers(ObservableProperty.SNAP, false);
				}
				
				localSnap.setSnapPeer(newRemoteSnap);
				newRemoteSnap.setSnapPeer(localSnap);
				localModel.notifyObservers(ObservableProperty.SNAP, true);
				newRemoteSnap.parent().notifyObservers(ObservableProperty.SNAP, true);
				
				// update final snapResult
				if (newSnapStrength < resultStrength) {
					snapResult = SnapResult.TRUE(localSnap, newRemoteSnap);
					resultStrength = newSnapStrength;
				}
			}
			// test if last snap still snaps
			else if (lastRemoteSnap != null) {
				if (!localSnap.canSnap(lastRemoteSnap)) {
					localSnap.setSnapPeer(null);
    				lastRemoteSnap.setSnapPeer(null);
    				localModel.notifyObservers(ObservableProperty.SNAP, false);
    				lastRemoteSnap.parent().notifyObservers(ObservableProperty.SNAP, false);
    			}
				else if (newSnapStrength < resultStrength) {
					snapResult = SnapResult.TRUE(localSnap, lastRemoteSnap);
					resultStrength = newSnapStrength;
    			}
			}
    		else {
    			// nothing to do, apparently even last remote snap was null
    		}
    	}
	    	
    	return snapResult;
    }
}
