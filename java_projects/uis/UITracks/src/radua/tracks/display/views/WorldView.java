package radua.tracks.display.views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import radua.ui.logic.views.IWorldView;


public class WorldView extends JComponent implements IWorldView
{
	private static final long serialVersionUID = 1072138064506103976L;
	
	private JComponent _lowerLayer;
	private SelectionLayer _upperLayer;
	
	public WorldView() {
		_upperLayer = new SelectionLayer();
		_upperLayer.setSize(2000, 2000);
		_lowerLayer = new LowerLayer();
		_lowerLayer.setSize(2000, 2000);
		add(_upperLayer);
		add(_lowerLayer);
	}
	
	public JComponent getLowerLayer() {
		return _lowerLayer;
	}
	
	public JComponent getUpperLayer() {
		return _upperLayer;
	}
	
	public void drawSelection(double selectionX, double selectionY, double selectionWidth, double selectionHeight) {
		_upperLayer.drawSelection(selectionX, selectionY, selectionWidth, selectionHeight);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	
	// ===================================================
	// ===================================================
	
	
	private static class LowerLayer extends JComponent
	{
		private static final long serialVersionUID = -2846378436243649415L;
		public LowerLayer() {}
	}
	
	
	private static class SelectionLayer extends JComponent
	{
		private static final long serialVersionUID = -7271016666724075442L;
		
		private double _selectionX, _selectionY, _selectionWidth, _selectionHeight;
		
		public SelectionLayer () {}
		
		public void drawSelection(double selectionX, double selectionY, double selectionWidth, double selectionHeight) {
			_selectionX = selectionX;
			_selectionY = selectionY;
			_selectionWidth = selectionWidth;
			_selectionHeight = selectionHeight;
			repaint();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLUE);
	        g.drawRect((int)_selectionX, (int)_selectionY, (int)_selectionWidth, (int)_selectionHeight);
		}
	}
}
