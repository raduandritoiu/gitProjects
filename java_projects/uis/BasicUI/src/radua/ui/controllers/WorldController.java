package radua.ui.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import radua.ui.models.IBasicModel;
import radua.ui.views.BasicView;
import radua.ui.world.WorldModel;

public class WorldController 
{
	public final WorldModel world;
	private final ArrayList<IBasicModel> models;
	private final ArrayList<BasicView<?>> views;
	private final ArrayList<IBasicModel> crtSelection;
	
	public final JComponent mainView;
	private final MovingController movingCtrl;
	private final KeyController keyCtrl;
	
	private ModelViewFactory factory;
	
	
	public WorldController(JComponent view) {
		this(view, new ModelViewFactory());
	}

	public WorldController(JComponent view, ModelViewFactory fa) {
		this(view, new WorldModel(), fa);
	}

	public WorldController(JComponent view, WorldModel model, ModelViewFactory newFactory) {
		world = model;
		mainView = view;
		factory = newFactory;
		
		keyCtrl = new KeyController(this);
		movingCtrl = new MovingController(this);
		
		models = new ArrayList<>();
		views = new ArrayList<>();
		crtSelection = new ArrayList<>();
		
		mainView.setFocusable(true);
		mainView.addKeyListener(keyCtrl);
	}
	
	
	public JComponent mainView() {
		return mainView;
	}
	
	public WorldModel world() {
		return world;
	}
	
	public List<IBasicModel> getModels() {
		return models;
	} 
	
	public void setFactory(ModelViewFactory newFactory) {
		factory = newFactory;
	}
	
	// ===================================================================
	// ====== Add and Remove =============================================
	
	public final void addModel(IBasicModel model) {
		BasicView<?> view = createView(model);
		addModelView(model, view);
	}
	
	public final void addView(BasicView<?> view) {
		IBasicModel model = view.getModel();
		addModelView(model, view);
	}
	
	private void addModelView(IBasicModel model, BasicView<?> view) {
		models.add(model);
		mainView.add(view);
		
		view.addKeyListener(keyCtrl);
		view.addMouseListener(movingCtrl);
        view.addMouseMotionListener(movingCtrl);
		views.add(view);
		view.repaint();
	}
	
	protected BasicView<?> createView(IBasicModel model) {
		return factory.createView(model); 
	}
	
	public void removeModel(IBasicModel model) {
		BasicView<?> view = getView(model);
		removeView(view);
	}
	
	public void removeView(BasicView<?> view) {
		IBasicModel model = view.getModel();
		crtSelection.remove(model);
		models.remove(model);
		views.remove(view);
		view.removeKeyListener(keyCtrl);
		view.removeMouseListener(movingCtrl);
		view.removeMouseMotionListener(movingCtrl);
		mainView.remove(view);
		mainView.repaint();
	}
	
	public BasicView<?> getView(IBasicModel model) {
		for (BasicView<?> view : views) {
			if (view.getModel() == model)
				return view;
		}
		return null;
	}
	
	// ===================================================================
	// ==== Selection ====================================================
	
	public void addSelection(IBasicModel selectedModel) {
		if (crtSelection.contains(selectedModel)) {
			return;
		}
		crtSelection.add(selectedModel);
		selectedModel.select(true);
		BasicView<?> view = getView(selectedModel);
		view.setFocusable(true);
	}
	
	public void addSelection(List<IBasicModel> newSelection) {
		for (IBasicModel model : newSelection) {
			if (crtSelection.contains(model)) {
				continue;
			}
			crtSelection.add(model);
			model.select(true);
			BasicView<?> view = getView(model);
			view.setFocusable(true);
		}
	}
	
	public void setSelection(IBasicModel selectedModel) {
		for (IBasicModel model : crtSelection) {
			if (selectedModel == model) {
				continue;
			}
			BasicView<?> view = getView(model);
			view.setFocusable(false);
			model.select(false);
		}
		crtSelection.clear();
		if (crtSelection.contains(selectedModel)) {
			return;
		}
		crtSelection.add(selectedModel);
		selectedModel.select(true);
		BasicView<?> view = getView(selectedModel);
		view.setFocusable(true);
	}
	
	public void setSelection(List<IBasicModel> newSelection) {
		for (IBasicModel model : crtSelection) {
			if (newSelection.contains(model)) {
				continue;
			}
			BasicView<?> view = getView(model);
			view.setFocusable(false);
			model.select(false);
		}
		crtSelection.clear();
		for (IBasicModel model : newSelection) {
			if (crtSelection.contains(model)) {
				continue;
			}
			crtSelection.add(model);
			model.select(true);
			BasicView<?> view = getView(model);
			view.setFocusable(true);
		}
	}
	
	public List<IBasicModel> getSelection() {
		return crtSelection;
	}
	
	public int selectionSize() {
		return crtSelection.size();
	}
	
	// ===================================================================
	// ==== Selection ====================================================
	
	
}
