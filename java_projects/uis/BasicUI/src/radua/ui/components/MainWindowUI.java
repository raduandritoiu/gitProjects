package radua.ui.components;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import radua.ui.controllers.WorldController;
import radua.ui.models.tracks.CurvedTrack;
import radua.ui.models.tracks.ReversTrack;
import radua.ui.models.tracks.SplitTrack;
import radua.ui.models.tracks.StraightTrack;
import radua.ui.views.tracks.TrackView;
import radua.ui.world.WorldView;


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
    	JComponent mainView = new WorldView();
    	add(mainView);
        addOpsPanel(mainView);
    	
    	worldCtrl = new WorldController(mainView);
        worldCtrl.addView(new TrackView(new StraightTrack(100, 100)));
        worldCtrl.addView(new TrackView(new StraightTrack(300, 100)));
        worldCtrl.addView(new TrackView(new StraightTrack(500, 100)));
//        worldCtrl.addView(new TrackView(new StraightTrack(300, 200)));
//        worldCtrl.addView(new TrackView(new StraightTrack(100, 400)));
        
//        worldCtrl.addView(new TrackView(new CurvedTrack(500, 100)));
//        worldCtrl.addView(new TrackView(new CurvedTrack(600, 100)));
//        worldCtrl.addView(new TrackView(new CurvedTrack(700, 100)));
//        worldCtrl.addView(new TrackView(new CurvedTrack(100, 300)));
//        worldCtrl.addView(new TrackView(new CurvedTrack(200, 300)));
//        worldCtrl.addView(new TrackView(new CurvedTrack(300, 300)));
//        worldCtrl.addView(new TrackView(new CurvedTrack(400, 300)));
//        worldCtrl.addView(new TrackView(new CurvedTrack(500, 300)));
//        worldCtrl.addView(new TrackView(new CurvedTrack(600, 300)));
//        worldCtrl.addView(new TrackView(new CurvedTrack(700, 300)));
//        
//        worldCtrl.addView(new TrackView(new CurvedTrack(100, 500)));
//        worldCtrl.addView(new TrackView(new CurvedTrack(200, 300)));
        
//        worldCtrl.addView(new TrackView(new SplitTrack(400, 500)));
//        worldCtrl.addView(new TrackView(new ReversTrack(200, 200)));
        
        
        
        
//
//        wolrdCtrl.addView(new OvalSnapView(new TestingSnapModel(200, 200)));
//        wolrdCtrl.addView(new OvalSnapView(new TestingSnapModel(200, 100)));
//        wolrdCtrl.addView(new RectSnapView(new TestingSnapModel(100, 100)));
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
