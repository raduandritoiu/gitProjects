package radua.tracks.display.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import radua.tracks.display.controllers.StageWrapper;
import radua.tracks.display.views.WorldView;
import radua.tracks.logic.controllers.TrainController;
import radua.tracks.logic.models.CurvedTrack;
import radua.tracks.logic.models.StraightTrack;
import radua.tracks.logic.persistance.PersistTracks;
import radua.ui.display.listeners.DragSelectionListener;
import radua.ui.logic.models.IBasicModel;


public class MainWindowUI extends JFrame
{
	private static final long serialVersionUID = -1870680521619193139L;
	
	
	private final String _name;
	TrainController worldCtrl;
	
	
	public MainWindowUI(String name) {
		
		// sa 
		
		
		_name = name;
		initUI();
		setup();
	}
	
	private void initUI() {
		setTitle(_name);
		setSize(800, 800);
		setLocationRelativeTo(null);
//		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}	
	
	
	
	void setup() {
		JPanel content = (JPanel) getContentPane();
		
		worldCtrl = new TrainController();
		addOpsPanel(content);
		
		JComponent c = new MyComp("MainBackground", new Color(255, 0, 0, 20));
		c.setSize(600, 600);
		c.setLocation(100, 100);
		content.add(c);

		
		WorldView mainView = new WorldView();
		DragSelectionListener dragSelection = new DragSelectionListener(worldCtrl);
		mainView.addMouseListener(dragSelection);
		mainView.addMouseMotionListener(dragSelection);
		StageWrapper stageWrapper = new StageWrapper(worldCtrl, mainView);
		worldCtrl.setStageWrapper(stageWrapper);
		content.add(mainView);

		
		
//		createModels();
//		loadModels();
	}		
	 
	private void loadModels() {
		
		PersistTracks persist = new PersistTracks();
		persist.load();
		for (IBasicModel model : persist.getModels()) {
			worldCtrl.addModel(model);
		}
	}
	
	
	
	private void createModels() {
		worldCtrl.addModel(new StraightTrack(100, 100));
		worldCtrl.addModel(new StraightTrack(300, 100));
//		worldCtrl.addModel(new StraightTrack(300, 200));
//		worldCtrl.addModel(new StraightTrack(100, 400));
		
		worldCtrl.addModel(new CurvedTrack(500, 100));
		worldCtrl.addModel(new CurvedTrack(600, 100));
		worldCtrl.addModel(new CurvedTrack(700, 100));
		worldCtrl.addModel(new CurvedTrack(100, 300));
		worldCtrl.addModel(new CurvedTrack(200, 300));
	}
	
	
	private void addOpsPanel(JComponent mainView) {
		OperationsPanel opsPanel = new OperationsPanel();
		opsPanel.createButtons(worldCtrl);
		mainView.add(opsPanel);
	}
}
