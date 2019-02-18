package radua.ui.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import radua.ui.models.BasicModel;
import radua.ui.models.IBasicModel;
import radua.ui.views.BasicView;
import radua.ui.world.WorldModel;

public class WorldController 
{
	private final ArrayList<IBasicModel> models;
	public final WorldModel world;
	public final JComponent mainView;
	
	private final MovingController movingCtrl;
	private final KeyController keyCtrl;
	
	private final ArrayList<BasicView<?>> views;
	private final ArrayList<IBasicModel> selection;
	
	
	public WorldController(JComponent view) {
		this(view, new WorldModel());
	}

	public WorldController(JComponent view, WorldModel model) {
		world = model;
		mainView = view;
		
		keyCtrl = new KeyController(this);
		movingCtrl = new MovingController(this);
		
		models = new ArrayList<>();
		views = new ArrayList<>();
		selection = new ArrayList<>();
		
		mainView.setFocusable(true);
		mainView.addKeyListener(keyCtrl);
	}
	
	
	public JComponent mainView() { return mainView; }
	public WorldModel world() { return world; }
	public List<IBasicModel>getModels() { return models; } 
	
	
	public final void addModel(BasicModel model) {
		BasicView<?> view = createView(model);
		addModelView(model, view);
	}
	public final void addView(BasicView<?> view) {
		IBasicModel model = view.getModel();
		addModelView(model, view);
	}
	private void addModelView(IBasicModel model, BasicView<?> view) {
		models.add(model);
		
		view.addKeyListener(keyCtrl);
		view.addMouseListener(movingCtrl);
        view.addMouseMotionListener(movingCtrl);
		views.add(view);
		mainView.add(view);
		view.repaint();
	}
	protected BasicView<?> createView(BasicModel model) { return null; }
	
	public void removeModel(IBasicModel model) {
		BasicView<?> view = getView(model);
		removeView(view);
	}
	
	public void removeView(BasicView<?> view) {
		IBasicModel model = view.getModel();
		selection.remove(model);
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
	
	
	public void selection(List<IBasicModel> newSelection) {
		for (IBasicModel model : selection) {
			BasicView<?> view = getView(model);
			view.setFocusable(false);
			model.setSelected(false);
		}
		selection.clear();
		for (IBasicModel model : newSelection) {
			selection.add(model);
			model.setSelected(true);
			BasicView<?> view = getView(model);
			view.setFocusable(true);
		}
	}
	public BasicModel[] getSelection() {
		return selection.toArray(new BasicModel[0]);
	}
}
