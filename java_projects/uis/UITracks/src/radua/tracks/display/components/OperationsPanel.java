package radua.tracks.display.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import radua.tracks.logic.controllers.TrainController;
import radua.ui.logic.models.example.CurvedShape;
import radua.ui.logic.models.example.SplitShape;
import radua.ui.logic.models.example.StraightShape;
import radua.ui.logic.utils.Constants;

public class OperationsPanel extends JPanel
{
	private static final long serialVersionUID = -5560926514062938949L;
	private static final int btn_w = 110;
	private static final int btn_h = 20;
	private static final int gap_w = 10;
	private static final int gap_h = 10;

	
	public OperationsPanel() {
        setLayout(null);
        setSize(500, 80);
        setLocation(10, 10);
//        setOpaque(false);
	}
	
	public void createButtons(TrainController worldCtrl) {
        JButton button1 = createButton("add straight", 0, 0);
        button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.addModel(new StraightShape(100, 100));
			}
		});
        
        JButton button2 = createButton("add curved", 1, 0);
        button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.addModel(new CurvedShape(100, 100));
			}
		});
        
        JButton button3 = createButton("add split", 2, 0);
        button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.addModel(new SplitShape(100, 100));
			}
		});
        
        JButton button4 = createButton("remove", 3, 0);
        button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.deleteSelection();
			}
		});
        
        
        JButton button5 = createButton("rotate Left", 0, 1);
        button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.selectionRotateBy(-Constants.RAD_30);
			}
		});
        
        JButton button6 = createButton("rotate right", 1, 1);
        button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.selectionRotateBy(Constants.RAD_30);
			}
		});
        
        JButton button7 = createButton("t. visible", 2, 1);
        button7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.selectionToggleVisible();
			}
		});
        
        JButton button8 = createButton("start", 0, 2);
        button8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.startTrain();
			}
		});
        
        JButton button9 = createButton("stop", 1, 2);
        button9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.stopTrain();
			}
		});

        JButton button10 = createButton("hide", 2, 2);
        button10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.hideTrain();
			}
		});

        JButton button11 = createButton("add train", 3, 2);
        button11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worldCtrl.addTrain();
			}
		});

	}
	
	
	private JButton createButton(String label, int pos_x, int pos_y) {
		JButton button = new JButton(label);
        button.setLocation(pos_x * (btn_w + gap_w), pos_y * (btn_h + gap_h));
        button.setSize(btn_w, btn_h);
        add(button);
		return button;
	}
}
