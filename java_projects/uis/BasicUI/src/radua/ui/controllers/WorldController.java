package radua.ui.controllers;

import java.util.ArrayList;
import java.util.List;

import radua.ui.models.IBasicModel;
import radua.ui.models.ISnapModel;
import radua.ui.models.snaps.ISnapPoint;
import radua.ui.utils.Constants;
import radua.ui.views.IBasicView;

public class WorldController 
{
	private final List<IBasicModel> models;
	private final List<IBasicView<?>> views;
	private final List<IBasicModel> crtSelection;
	
	private ModelViewFactory factory;
	private final MovementHelper movingHelper;
	private IStageWrapper stageWrapper;

	
	public WorldController() {
		this(new ModelViewFactory());
	}
	public WorldController(ModelViewFactory newFactory) {
		factory = newFactory;
		movingHelper = new MovementHelper(this);
		
		models = new ArrayList<>();
		views = new ArrayList<>();
		crtSelection = new ArrayList<>();
	}
	
	
	// ===================================================================
	// ====== Built-in sub components ====================================
	
	public void setStageWrapper(IStageWrapper newStageWrapper) {
		stageWrapper = newStageWrapper;
	}
	
	public void setFactory(ModelViewFactory newFactory) {
		factory = newFactory;
	}
	
	public ModelViewFactory getFactory() {
		return factory;
	}
	
	public MovementHelper movingHelper() {
		return movingHelper;
	}
	
	
	// ===================================================================
	// ====== Models and Views ===========================================
	
	public List<IBasicModel> getModels() {
		return models;
	} 
	
	public IBasicView<?> getView(IBasicModel model) {
		for (IBasicView<?> view : views) {
			if (view.model() == model)
				return view;
		}
		return null;
	}

	
	// ===================================================================
	// ====== Add and Remove =============================================
	
	public final void addModel(IBasicModel model) {
		IBasicView<?> view = createView(model);
		addModelView(model, view);
	}
	
	public final void addView(IBasicView<?> view) {
		IBasicModel model = view.model();
		addModelView(model, view);
	}
	
	private void addModelView(IBasicModel model, IBasicView<?> view) {
		if (stageWrapper != null) {
			stageWrapper.addNewView(view);
		}

		models.add(model);
        views.add(view);
	}
	
	protected IBasicView<?> createView(IBasicModel model) {
		return factory.createView(model); 
	}
	
	public void removeModel(IBasicModel model) {
		IBasicView<?> view = getView(model);
		removeView(view);
	}
	
	public void removeView(IBasicView<?> view) {
		IBasicModel model = view.model();
		crtSelection.remove(model);
		models.remove(model);
		views.remove(view);
		
		if (stageWrapper != null) {
			stageWrapper.removeView(view);
		}
	}
	
	
	// ===================================================================
	// ==== Selection ====================================================
	
	public void addSelection(IBasicModel selectedModel) {
		if (crtSelection.contains(selectedModel)) {
			return;
		}
		crtSelection.add(selectedModel);
		selectedModel.select(true);
	}
	
	public void addSelection(List<IBasicModel> newSelection) {
		for (IBasicModel model : newSelection) {
			if (crtSelection.contains(model)) {
				continue;
			}
			crtSelection.add(model);
			model.select(true);
		}
	}
	
	public void setSelection(IBasicModel selectedModel) {
		for (IBasicModel model : crtSelection) {
			if (selectedModel == model) {
				continue;
			}
			model.select(false);
		}
		crtSelection.clear();
		if (crtSelection.contains(selectedModel)) {
			return;
		}
		crtSelection.add(selectedModel);
		selectedModel.select(true);
	}
	
	public void setSelection(List<IBasicModel> newSelection) {
		for (IBasicModel model : crtSelection) {
			if (newSelection.contains(model)) {
				continue;
			}
			model.select(false);
		}
		crtSelection.clear();
		for (IBasicModel model : newSelection) {
			if (crtSelection.contains(model)) {
				continue;
			}
			crtSelection.add(model);
			model.select(true);
		}
	}
	
	public List<IBasicModel> getSelection() {
		return crtSelection;
	}
	
	public int selectionSize() {
		return crtSelection.size();
	}
	
	
	// ===================================================================
	// ==== Proxy move/translation =======================================
	
	private boolean modelNotSnapped(IBasicModel model) {
		if (model instanceof ISnapModel) {
			ISnapModel snapModel = (ISnapModel) model;
			for (ISnapPoint snapPoint : snapModel.snapPoints()) {
				if (snapPoint.isSnapped()) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public void selectionMoveBy(double x, double y) {
		for (IBasicModel model : crtSelection) {
			// move only unsnapped models
			if (modelNotSnapped(model)) {
				model.moveBy(x, y);
			}
		}
	}
	
	public void selectionRotateBy(double rotation) {
		for (IBasicModel model : crtSelection) {
			// move only unsnapped models
			if (modelNotSnapped(model)) {
				model.rotateBy(rotation);
			}
		}
	}
	
	public void selectionRotateTo(double rotation) {
		for (IBasicModel model : crtSelection) {
			// move only unsnapped models
			if (modelNotSnapped(model)) {
				model.rotateTo(rotation);
			}
		}
	}
	
	public void selectionResetRotation() {
		selectionRotateTo(Constants.DEG_0);
	}
}
