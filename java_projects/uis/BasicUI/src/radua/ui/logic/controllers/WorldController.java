package radua.ui.logic.controllers;

import java.util.ArrayList;
import java.util.List;

import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.models.ISnapModel;
import radua.ui.logic.models.snaps.ISnapPoint;
import radua.ui.logic.utils.Constants;
import radua.ui.logic.views.IBasicView;

public class WorldController 
{
	private final List<IBasicModel> models;
	private final List<IBasicView<?>> views;
	private final List<IBasicModel> crtSelection;
	
	private IModelViewFactory factory;
	private IStageWrapper stageWrapper;

	
	public WorldController() {
		this(new EmptyViewFactory());
	}
	public WorldController(IModelViewFactory newFactory) {
		factory = newFactory;
		
		models = new ArrayList<>();
		views = new ArrayList<>();
		crtSelection = new ArrayList<>();
	}
	
	
	// ===================================================================
	// ====== Built-in sub components ====================================
	
	public void setFactory(IModelViewFactory newFactory) {
		factory = newFactory;
	}
	
	public IModelViewFactory getFactory() {
		return factory;
	}
	
	public void setStageWrapper(IStageWrapper newStageWrapper) {
		stageWrapper = newStageWrapper;
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
		if (selectedModel == null) return;
		if (crtSelection.contains(selectedModel)) {
			return;
		}
		crtSelection.add(selectedModel);
		selectedModel.select(true);
	}
	
	public void addSelection(List<IBasicModel> newSelection) {
		if (newSelection == null) return;
		IBasicModel[] updateSelection = new IBasicModel[newSelection.size()];
		int i = 0;
		for (IBasicModel model : newSelection) {
			if (!crtSelection.contains(model)) {
				updateSelection[i++] = model;
				crtSelection.add(model);
			}
		}
		for (i = 0; i < updateSelection.length; i++) {
			if (updateSelection[i] == null) break;
			updateSelection[i].select(true);
		}
	}
	
	public void setSelection(IBasicModel selectedModel) {
		// compute old and new
		IBasicModel[] oldSelection = new IBasicModel[crtSelection.size()];
		int i = 0;
		for (IBasicModel model : crtSelection) {
			if (model != selectedModel) {
				oldSelection[i++] = model;
			}
		}
		boolean changeSelection = !crtSelection.contains(selectedModel);
		
		// update selection
		crtSelection.clear();
		if (selectedModel != null) {
			crtSelection.add(selectedModel);
		}

		// update models
		for (i = 0; i < oldSelection.length; i++) {
			if (oldSelection[i] == null) break;
			oldSelection[i].select(false);
		}
		if (changeSelection) {
			selectedModel.select(true);
		}
	}
	
	public void setSelection(List<IBasicModel> newSelection) {
		// compute old and new
		IBasicModel[] oldSelection = new IBasicModel[crtSelection.size()];
		int i = 0;
		for (IBasicModel model : crtSelection) {
			if (newSelection == null || !newSelection.contains(model)) {
				oldSelection[i++] = model;
			}
		}
		IBasicModel[] updateSelection = null;
		if (newSelection != null) {
			updateSelection = new IBasicModel[newSelection.size()];
			i = 0;
			for (IBasicModel model : newSelection) {
				if (!crtSelection.contains(model)) {
					updateSelection[i++] = model;
				}
			}
		}
		
		// update selection
		crtSelection.clear();
		if (newSelection != null && newSelection.size() > 0) {
			crtSelection.addAll(newSelection);
		}
		
		// update models
		for (i = 0; i < oldSelection.length; i++) {
			if (oldSelection[i] == null) break;
			oldSelection[i].select(false);
		}
		if (updateSelection == null || updateSelection.length == 0) return;
		for (i = 0; i < updateSelection.length; i++) {
			if (updateSelection[i] == null) break;
			updateSelection[i].select(true);
		}
	}
	
	public void clearSelection() {
		// compute old and new
		IBasicModel[] oldSelection = new IBasicModel[crtSelection.size()];
		int i = 0;
		for (IBasicModel model : crtSelection) {
			oldSelection[i++] = model;
		}
		
		// update selection
		crtSelection.clear();
		
		// update models
		for (i = 0; i < oldSelection.length; i++) {
			if (oldSelection[i] == null) break;
			oldSelection[i].select(false);
		}
	}
	
	public List<IBasicModel> getSelection() {
		return crtSelection;
	}
	
	public int selectionSize() {
		return crtSelection.size();
	}
	
	
	// ===================================================================
	// ==== Proxy move/translation/change ================================
	
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
