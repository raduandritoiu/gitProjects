package radua.ui.display.components;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import radua.ui.display.controllers.ModelViewFactory;
import radua.ui.display.controllers.StageWrapper;
import radua.ui.display.views.WorldView;
import radua.ui.logic.controllers.WorldController;
import radua.ui.logic.models.tracks.CurvedTrack;
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
    	WorldView mainView = new WorldView();
    	add(mainView);
        addOpsPanel(mainView);
    	
        
        
    	worldCtrl = new WorldController(new ModelViewFactory());
        
    	
        StageWrapper stageWrapper = new StageWrapper(worldCtrl, mainView);
        worldCtrl.setStageWrapper(stageWrapper);
        
        
        
        worldCtrl.addModel(new StraightTrack(100, 100));
        worldCtrl.addModel(new StraightTrack(300, 100));
        worldCtrl.addModel(new StraightTrack(500, 100));
        worldCtrl.addModel(new StraightTrack(300, 200));
        worldCtrl.addModel(new StraightTrack(100, 400));
        
        worldCtrl.addModel(new CurvedTrack(500, 100));
        worldCtrl.addModel(new CurvedTrack(600, 100));
        worldCtrl.addModel(new CurvedTrack(700, 100));
        worldCtrl.addModel(new CurvedTrack(100, 300));
        worldCtrl.addModel(new CurvedTrack(200, 300));
        worldCtrl.addModel(new CurvedTrack(300, 300));
        worldCtrl.addModel(new CurvedTrack(400, 300));
        worldCtrl.addModel(new CurvedTrack(500, 300));
        worldCtrl.addModel(new CurvedTrack(600, 300));
        worldCtrl.addModel(new CurvedTrack(700, 300));
        
        worldCtrl.addModel(new CurvedTrack(100, 500));
        worldCtrl.addModel(new CurvedTrack(200, 300));
        
        worldCtrl.addModel(new SplitTrack(400, 500));
        worldCtrl.addModel(new ReversTrack(200, 200));
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
