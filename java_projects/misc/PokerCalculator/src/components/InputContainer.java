package components;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;


public class InputContainer extends Panel implements ActionListener {
	private String _text = "none";
	private boolean _enabled = false;
	private String[] _numberList = Card.NUMBERS_STR;
	private String[] _colorList = Card.COLORS;
	
	
	private JLabel lbl;
	private JComboBox numberCombo;
	private JComboBox colorCombo;
	private JCheckBox enableCheck;
	
	public InputContainer() {
		super();
	}
	
	
	public InputContainer(String newText) {
		super();
		_text = newText;
	}
	
	
	public void setText(String value) {
		_text = value;
		if (lbl != null) {
			lbl.setText(_text);
		}
	}
	
	public String getText() {
		return _text;
	}
	
	
	public void setEnabled(boolean value) {
		_enabled = value;
		if (enableCheck != null) {
			enableCheck.setSelected(value);
			numberCombo.setEnabled(_enabled);
			colorCombo.setEnabled(_enabled);
		}
	}
	
	public boolean getEnabled() {
		return _enabled;
	}
	
	
	public void setNumberList(String[] value) {
		_numberList = value;
		if (numberCombo != null) {
			numberCombo.removeAllItems();
			for (int i=0; i < value.length; i++) {
				numberCombo.addItem(value[i]);
			}
			numberCombo.setSelectedIndex(0);
		}
	}
	
	public String[] getNumberList() {
		return _numberList;
	}

	public String getNumber() {
		return numberCombo.getSelectedItem().toString();
	}
	
	
	public void setColorList(String[] value) {
		_colorList = value;
		if (lbl != null) {
			colorCombo.removeAllItems();
			for (int i=0; i < value.length; i++) {
				colorCombo.addItem(value[i]);
			}
			colorCombo.setSelectedIndex(0);
		}
	}
	
	public String[] getColorList() {
		return _colorList;
	}
	
	public String getColor() {
		return colorCombo.getSelectedItem().toString();
	}

	
	
	public void initElements() {
		lbl = new JLabel(_text);
		add(lbl);
		
		numberCombo = new JComboBox(_numberList);
		numberCombo.setSelectedIndex(0);
		numberCombo.setEnabled(_enabled);
		add(numberCombo);

		colorCombo = new JComboBox(_colorList);
		colorCombo.setSelectedIndex(0);
		colorCombo.setEnabled(_enabled);
		add(colorCombo);
		
		enableCheck = new JCheckBox();
		enableCheck.setSelected(_enabled);
		enableCheck.addActionListener(this);
		add(enableCheck);
	}
	
	
	public void actionPerformed(ActionEvent evt) {
		_enabled = enableCheck.isSelected();;
		numberCombo.setEnabled(_enabled);
		colorCombo.setEnabled(_enabled);
	}
}
