import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import components.Card;
import components.InputContainer;
import components.PokerDeck;


public class PokerWindow extends JPanel implements ActionListener {
	private InputContainer hand1;
	private InputContainer hand2;
	private InputContainer flop1;
	private InputContainer flop2;
	private InputContainer flop3;
	private InputContainer river;
	private InputContainer turn;
	private JButton button;
	private JLabel outputLabel;
	
	
	public PokerWindow() {
		super();
	}
	
	
	public void paintComponent(Graphics g){
		//g.drawLine(10,10,150,150); // Draw a line from (10,10) to (150,150)
	}

	
	public static void main(String arg[]){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 400);
		frame.setVisible(true);
		
		PokerWindow pokerWindow = new PokerWindow();
		pokerWindow.setLayout(new BoxLayout(pokerWindow, BoxLayout.PAGE_AXIS));
		frame.setContentPane(pokerWindow);
		pokerWindow.initElements();
		frame.setContentPane(pokerWindow);
	}
	
	
	public void initElements() {
		hand1 = new InputContainer("Hand 1");
		hand1.setEnabled(true);
		hand1.initElements();
		add(hand1);
		
		hand2 = new InputContainer("Hand 2");
		hand2.setEnabled(true);
		hand2.initElements();
		add(hand2);
		
		flop1 = new InputContainer("Flop 1");
		flop1.setEnabled(true);
		flop1.initElements();
		add(flop1);
		
		flop2 = new InputContainer("Flop 2");
		flop2.setEnabled(true);
		flop2.initElements();
		add(flop2);
		
		flop3 = new InputContainer("Flop 3");
		flop3.setEnabled(true);
		flop3.initElements();
		add(flop3);
		
		river = new InputContainer("River");
		river.initElements();
		add(river);
		
		turn = new InputContainer("Turn");
		turn.initElements();
		add(turn);
		
		button = new JButton("Calculate");
		button.addActionListener(this);
		add(button);
		
		outputLabel = new JLabel("Probability of : 0.12%");
		add(outputLabel);
	}
	
	
	public void actionPerformed(ActionEvent evt) {
		double probability = 0.23;
		PokerDeck pokerDeck = new PokerDeck();
		if (hand1.getEnabled()) {
			pokerDeck.hand1 = new Card(Integer.parseInt(hand1.getNumber()), hand1.getColor());
		}
		if (hand2.getEnabled()) {
			pokerDeck.hand2 = new Card(Integer.parseInt(hand2.getNumber()), hand2.getColor());
		}
		if (flop1.getEnabled()) {
			pokerDeck.flop1 = new Card(Integer.parseInt(flop1.getNumber()), flop1.getColor());
		}
		if (flop2.getEnabled()) {
			pokerDeck.flop2 = new Card(Integer.parseInt(flop2.getNumber()), flop2.getColor());
		}
		if (flop3.getEnabled()) {
			pokerDeck.flop3 = new Card(Integer.parseInt(flop3.getNumber()), flop3.getColor());
		}
		if (river.getEnabled()) {
			pokerDeck.river = new Card(Integer.parseInt(river.getNumber()), river.getColor());
		}
		if (turn.getEnabled()) {
			pokerDeck.turn = new Card(Integer.parseInt(turn.getNumber()), turn.getColor());
		}
		pokerDeck.calculate();
		probability = pokerDeck.getPropability();
		outputLabel.setText("Probability of : " + probability + "%");
		repaint();
	}
}
