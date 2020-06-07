package radua.ui.logic.controllers;

import java.util.ArrayList;
import java.util.List;

import radua.ui.logic.models.IBasicModel;
import radua.ui.logic.models.ISnapModel;
import radua.ui.logic.models.snaps.ISnapPoint;
import radua.ui.logic.utils.Constants;
import radua.ui.logic.view.IModelView;

public class WorldController 
{
	private final List<IBasicModel> _models;
	private final List<IModelView<?>> _views;
	private final List<IBasicModel> _crtSelection;
	
	private IModelViewFactory _factory;
	private IStageWrapper _stageWrapper;

	
	public WorldController() {
		this(new EmptyViewFactory());
	}
	public WorldController(IModelViewFactory newFactory) {
		_factory = newFactory;
		
		_models = new ArrayList<>();
		_views = new ArrayList<>();
		_crtSelection = new ArrayList<>();
	}
	
	
	// ===================================================================
	// ====== Built-in sub components ====================================
	
	public void setFactory(IModelViewFactory newFactory) {
		_factory = newFactory;
	}
	
	public IModelViewFactory getFactory() {
		return _factory;
	}
	
	public void setStageWrapper(IStageWrapper newStageWrapper) {
		_stageWrapper = newStageWrapper;
	}
	
	
	// ===================================================================
	// ====== Models and Views ===========================================
	
	public List<IBasicModel> getModels() {
		return _models;
	} 
	
	public IModelView<?> getView(IBasicModel model) {
		for (IModelView<?> view : _views) {
			if (view.model() == model)
				return view;
		}
		return null;
	}

	
	// ===================================================================
	// ====== Add and Remove =============================================
	
	public final void addModel(IBasicModel model) {
		IModelView<?> view = createView(model);
		addModelView(model, view);
	}
	
	public final void addView(IModelView<?> view) {
		IBasicModel model = view.model();
		addModelView(model, view);
	}
	
	private void addModelView(IBasicModel model, IModelView<?> view) {
		if (_stageWrapper != null) {
			_stageWrapper.addNewView(view);
		}
		_models.add(model);
        _views.add(view);
	}
	
	protected IModelView<?> createView(IBasicModel model) {
		return _factory.createView(model); 
	}
	
	public void removeModel(IBasicModel model) {
		IModelView<?> view = getView(model);
		removeView(view);
	}
	
	public void removeView(IModelView<?> view) {
		IBasicModel model = view.model();
		_crtSelection.remove(model);
		_models.remove(model);
		_views.remove(view);
		if (_stageWrapper != null) {
			_stageWrapper.removeView(view);
		}
	}
	
	
	// ===================================================================
	// ==== Selection ====================================================
	
	public void addSelection(IBasicModel selectedModel) {
		if (selectedModel == null) return;
		if (_crtSelection.contains(selectedModel)) {
			return;
		}
		_crtSelection.add(selectedModel);
		selectedModel.select(true);
	}
	
	public void addSelection(List<IBasicModel> newSelection) {
		if (newSelection == null) return;
		IBasicModel[] updateSelection = new IBasicModel[newSelection.size()];
		int i = 0;
		for (IBasicModel model : newSelection) {
			if (!_crtSelection.contains(model)) {
				updateSelection[i++] = model;
				_crtSelection.add(model);
			}
		}
		for (i = 0; i < updateSelection.length; i++) {
			if (updateSelection[i] == null) break;
			updateSelection[i].select(true);
		}
	}
	
	public void setSelection(IBasicModel selectedModel) {
		// compute old and new
		IBasicModel[] oldSelection = new IBasicModel[_crtSelection.size()];
		int i = 0;
		for (IBasicModel model : _crtSelection) {
			if (model != selectedModel) {
				oldSelection[i++] = model;
			}
		}
		boolean changeSelection = !_crtSelection.contains(selectedModel);
		
		// update selection
		_crtSelection.clear();
		if (selectedModel != null) {
			_crtSelection.add(selectedModel);
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
		IBasicModel[] oldSelection = new IBasicModel[_crtSelection.size()];
		int i = 0;
		for (IBasicModel model : _crtSelection) {
			if (newSelection == null || !newSelection.contains(model)) {
				oldSelection[i++] = model;
			}
		}
		IBasicModel[] updateSelection = null;
		if (newSelection != null) {
			updateSelection = new IBasicModel[newSelection.size()];
			i = 0;
			for (IBasicModel model : newSelection) {
				if (!_crtSelection.contains(model)) {
					updateSelection[i++] = model;
				}
			}
		}
		
		// update selection
		_crtSelection.clear();
		if (newSelection != null && newSelection.size() > 0) {
			_crtSelection.addAll(newSelection);
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
	
	public void setSelection(int x1, int y1, int x2, int y2) {
		if (x1 == x2 || y1 == y2) {
			clearSelection();
			return;
		}

		int xm = x1, xM = x2;
		int ym = y1, yM = y2;
		if (x1 > x2) { xm = x2; xM = x1; }
		if (y1 > y2) { ym = y2; yM = y1; }
		
		List<IBasicModel> newSelection = new ArrayList<>();
		for (IBasicModel model : _models) {
			if (model.x() > xm &&  model.y() > ym && 
				model.x() + model.width() < xM &&  model.y() + model.height() < yM) {
				newSelection.add(model);
			}
		}
		setSelection(newSelection);
	}

	
	public void clearSelection() {
		// compute old and new
		IBasicModel[] oldSelection = new IBasicModel[_crtSelection.size()];
		int i = 0;
		for (IBasicModel model : _crtSelection) {
			oldSelection[i++] = model;
		}
		
		// update selection
		_crtSelection.clear();
		
		// update models
		for (i = 0; i < oldSelection.length; i++) {
			if (oldSelection[i] == null) break;
			oldSelection[i].select(false);
		}
	}
	
	public List<IBasicModel> getSelection() {
		return _crtSelection;
	}
	
	public int selectionSize() {
		return _crtSelection.size();
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
		for (IBasicModel model : _crtSelection) {
			// move only unsnapped models
			if (modelNotSnapped(model)) {
				model.moveBy(x, y);
			}
		}
	}
	
	public void selectionRotateBy(double rotation) {
		for (IBasicModel model : _crtSelection) {
			// move only unsnapped models
			if (modelNotSnapped(model)) {
				model.rotateBy(rotation);
			}
		}
	}
	
	public void selectionRotateTo(double rotation) {
		for (IBasicModel model : _crtSelection) {
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
