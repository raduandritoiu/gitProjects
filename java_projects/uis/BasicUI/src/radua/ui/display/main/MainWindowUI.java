package radua.ui.display.main;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import radua.ui.display.factory.ModelViewFactory;
import radua.ui.display.stage.StageView;
import radua.ui.display.stage.StageWrapper;
import radua.ui.logic.controllers.WorldController;
import radua.ui.logic.models.shapes.CircleSnapModel;
import radua.ui.logic.models.shapes.OvalSnapModel;
import radua.ui.logic.models.shapes.RectSnapModel;
import radua.ui.logic.models.shapes.SquareSnapModel;
import radua.ui.logic.models.tracks.AscendTrack;
import radua.ui.logic.models.tracks.CurvedTrack;
import radua.ui.logic.models.tracks.DescendTrack;
import radua.ui.logic.models.tracks.ReversTrack;
import radua.ui.logic.models.tracks.SplitTrack;
import radua.ui.logic.models.tracks.StraightTrack;


public class MainWindowUI extends JFrame
{
	private static final long serialVersionUID = -1870680521619193139L;
	
	
	private final String _name;
	private WorldController worldCtrl;
	
	
    public MainWindowUI(String name) 
    {
    	_name = name;
        initUI();
        setup();
    }
    

    private void initUI() {
        setTitle(_name);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }    
    
    void setup()
    {
    	StageView mainView = new StageView();
    	add(mainView);
        addOpsPanel(mainView);
    	
        
        
    	worldCtrl = new WorldController(new ModelViewFactory());
        
    	
        StageWrapper stageWrapper = new StageWrapper(worldCtrl, mainView);
        worldCtrl.setStageWrapper(stageWrapper);
        
        addModels_1();
    }
    
    private void addModels_1() {
        worldCtrl.addModel(new StraightTrack(100, 100));
        worldCtrl.addModel(new StraightTrack(300, 100));
        worldCtrl.addModel(new StraightTrack(500, 100));
        worldCtrl.addModel(new StraightTrack(300, 200));
        worldCtrl.addModel(new StraightTrack(100, 400));
        
        worldCtrl.addModel(new CurvedTrack(600, 100));
        worldCtrl.addModel(new CurvedTrack(700, 100));
        worldCtrl.addModel(new CurvedTrack(100, 300));
        worldCtrl.addModel(new CurvedTrack(200, 300));
        worldCtrl.addModel(new CurvedTrack(300, 300));
        worldCtrl.addModel(new CurvedTrack(400, 300));
        worldCtrl.addModel(new CurvedTrack(500, 300));
        worldCtrl.addModel(new CurvedTrack(600, 300));
        worldCtrl.addModel(new CurvedTrack(700, 300));
        
        worldCtrl.addModel(new CurvedTrack(100, 200));
        worldCtrl.addModel(new CurvedTrack(200, 300));
        
        worldCtrl.addModel(new SplitTrack(400, 200));
        worldCtrl.addModel(new ReversTrack(200, 200));
        
        worldCtrl.addModel(new AscendTrack(200, 400));
        worldCtrl.addModel(new DescendTrack(200, 500));
    }
    
    private void addModels_2() {
        worldCtrl.addModel(new StraightTrack(100, 100));
        worldCtrl.addModel(new StraightTrack(300, 100));
        worldCtrl.addModel(new StraightTrack(500, 100));
        worldCtrl.addModel(new StraightTrack(300, 300));
        worldCtrl.addModel(new StraightTrack(100, 400));
    }
    
    private void addModels_3() {
        worldCtrl.addModel(new OvalSnapModel(100, 100));
        worldCtrl.addModel(new OvalSnapModel(300, 100));
        worldCtrl.addModel(new OvalSnapModel(300, 300));
        
        worldCtrl.addModel(new RectSnapModel(100, 400));
        worldCtrl.addModel(new RectSnapModel(500, 100));
        
        worldCtrl.addModel(new SquareSnapModel(700, 100));
        worldCtrl.addModel(new SquareSnapModel(700, 300));
        worldCtrl.addModel(new SquareSnapModel(700, 500));
        
        worldCtrl.addModel(new CircleSnapModel(100, 500));
        worldCtrl.addModel(new CircleSnapModel(300, 500));
        worldCtrl.addModel(new CircleSnapModel(500, 500));
    }
    
    private void addOpsPanel(JComponent mainView) {
        JPanel opsPanel = new JPanel();
        opsPanel.setLayout(null);
        opsPanel.setSize(500, 50);
        opsPanel.setLocation(10, 10);
        mainView.add(opsPanel);
        
        JButton button1 = new JButton("add straight");
        button1.setLocation(0, 0);
        button1.setSize(110, 20);
        opsPanel.add(button1);
        
        JButton button2 = new JButton("add curved");
        button2.setLocation(120, 0);
        button2.setSize(110, 20);
        opsPanel.add(button2);
        
        JButton button3 = new JButton("add curved");
        button3.setLocation(240, 0);
        button3.setSize(110, 20);
        opsPanel.add(button3);
        
        JButton button4 = new JButton("remove");
        button4.setLocation(360, 0);
        button4.setSize(110, 20);
        opsPanel.add(button4);
    }
}
