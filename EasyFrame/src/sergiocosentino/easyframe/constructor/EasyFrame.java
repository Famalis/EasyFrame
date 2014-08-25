/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sergiocosentino.easyframe.constructor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Sergio
 */
public class EasyFrame {

	private static final Logger LOG = Logger.getLogger(EasyFrame.class.getName());

	private JFrame frame;
	private ArrayList<JPanel> columns;
	private HashMap<JPanel, ArrayList<Component>> columnContent;
	private HashMap<String, Component> components;
	private HashMap<String, Integer> componentColumnsIndexes;
	private String title = "EasyFrame";

	private EasyFrame() {
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		columns = new ArrayList<>();
		columnContent = new HashMap<>();
		components = new HashMap<>();
		componentColumnsIndexes = new HashMap<>();
	}

	public ArrayList<JPanel> getColumns() {
		return columns;
	}

	public HashMap<JPanel, ArrayList<Component>> getColumnContent() {
		return columnContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int addColumn() {
		columns.add(new JPanel());
		return columns.size();
	}

	public void setSize(int width, int height) {
		frame.setSize(width, height);
	}

	/**
	 * Sets the given look and feel. Returns true if the look and feel was
	 * found.
	 *
	 * @param laf
	 * @return
	 */
	public boolean setLookAndFeel(LookAndFeel laf) {
		try {
			UIManager.setLookAndFeel(laf);
			return true;
		} catch (UnsupportedLookAndFeelException ex) {
			LOG.warning(ex.getLocalizedMessage());
			return false;
		}
	}

	/**
	 * Sets the given look and feel. Returns true if the look and feel was
	 * found.
	 *
	 * @param className
	 * @return
	 */
	public boolean setLookAndFeel(String className) {
		try {
			UIManager.setLookAndFeel(className);
			return true;
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
			LOG.warning(ex.getLocalizedMessage());
			return false;
		}
	}

	/**
	 * Returns an instance of the amazing easy frame.
	 *
	 * @return
	 */
	public static EasyFrame getInstanceOf() {
		return new EasyFrame();
	}

	/**
	 * Makes the frame visible if it has been initaliazed. If the frame can be
	 * shown return true, if not - false.
	 *
	 * @return
	 */
	public boolean show() {
		if (frame != null) {
			frame.setVisible(true);
			return true;
		}
		return false;
	}

	/**
	 * Makes the frame invisible if it has been initaliazed. If the frame can be
	 * shown return true, if not - false.
	 *
	 * @return
	 */
	public boolean hide() {
		if (frame != null) {
			frame.setVisible(false);
			return true;
		}
		return false;
	}

	/**
	 * If set to true acts like <code>JFrame.EXIT_ON_CLOSE</code>, that is once
	 * the frame is closed the application end. If false acts as
	 * <code>JFrame.DISPOSE_ON_CLOSE</code>, that is simply closes the frame.
	 *
	 * @param b
	 */
	public void setEndProgramOnClose(boolean b) {
		if (b) {
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}

	/**
	 * Packs the easy frame, which means it configures all the added components.
	 * This method has to be called in order to see the added components. When
	 * packing the EasyFrame's frame is re-created.
	 */
	public void pack() {
		Dimension maxDim = new Dimension(0,0);
		if (frame != null) {
			frame.dispose();
		}
		frame = new JFrame(title);
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		for (JPanel panel : columns) {
			frame.add(panel);
		}
		frame.pack();
		for (JPanel panel : columns) {
			if(maxDim.getHeight()<panel.getHeight()) {
				maxDim = panel.getSize();
			}
			//System.out.println(panel.getSize());
			if(panel.getHeight()<maxDim.getHeight()) {
				panel.setMinimumSize(maxDim);
				panel.setPreferredSize(maxDim);
			}
			//System.out.println(panel.getSize());
		}
		frame.repaint();
		this.show();
	}

	/**
	 * This method is meant for adding action listener to <code>JButton</code>
	 * objects. It adds the action listener to the component to which the
	 * reference points. Returns true if such a button has been found.
	 *
	 * @param reference
	 * @param action
	 * @return
	 */
	public boolean addButtonActionListener(String reference, ActionListener action) {
		try {
			JButton button = (JButton) components.get(reference);
			button.addActionListener(action);
			return true;
		} catch (Exception ex) {
			LOG.warning(ex.getLocalizedMessage());
			return false;
		}
	}

	/**
	 * Adds the component to the first empty row of a column. The
	 * <code>reference</code> String is supposed to be a unique name for the
	 * component wich can be used for easy access to the component.
	 *
	 * @param comp
	 * @param reference
	 * @param column
	 */
	public void addNextComponent(Component comp, String reference, int column) {
		int cIndex = column - 1;
		int row = 0;
		JPanel panel = columns.get(cIndex);
		if (!columnContent.get(panel).isEmpty()) {
			row = columnContent.get(panel).size();
		}
		addComponent(column, row, comp, reference);
	}

	/**
	 * Adds the component to the first empty row of a column. Also adds scroll
	 * bars to the component. The <code>reference</code> String is supposed to
	 * be a unique name for the component wich can be used for easy access to
	 * the component.
	 *
	 * @param comp
	 * @param reference
	 * @param column
	 */
	public void addNextScrolledComponent(Component comp, String reference, int column) {
		int cIndex = column - 1;
		int row = 0;
		JPanel panel = columns.get(cIndex);
		if (!columnContent.get(panel).isEmpty()) {
			row = columnContent.get(panel).size();
		}
		addScrolledComponent(column, row, comp, reference);
	}

	/**
	 * Adds a text area to the easy frame. The area have the number of columns
	 * and rows as specified. Also the text area will have line wrapping and
	 * scroll bars. The <code>reference</code> String is supposed to be a unique
	 * name for the component wich can be used for easy access to the component.
	 * If there is a component at the given coordinates it will be overwritten.
	 *
	 * @param column
	 * @param row
	 * @param reference
	 * @param textColumns
	 * @param textRows
	 */
	public void addTextArea(int column, int row, String reference, int textColumns, int textRows) {
		if (column > columns.size()) {
			for (int i = 0; i < column; i++) {
				JPanel newPanel = new JPanel();
				newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
				columns.add(newPanel);
			}
		}
		int cIndex = column - 1;
		int rIndex = row - 1;
		JPanel columnPanel = columns.get(cIndex);
		if (!columnContent.containsKey(columnPanel)) {
			columnContent.put(columnPanel, new ArrayList<Component>());
		}
		ArrayList<Component> columnComps = columnContent.get(columnPanel);
		if (columnComps.size() < row) {
			for (int i = 0; i < row; i++) {
				columnComps.add(null);
			}
		}
		JTextArea area = new JTextArea();
		area.setColumns(textColumns);
		area.setRows(textRows);
		area.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(area);
		columnComps.set(rIndex, scroll);
		components.put(reference, area);
		componentColumnsIndexes.put(reference, cIndex);
		columnPanel.add(scroll);
	}

	/**
	 * Adds the component to the easy frame and attaches a scroll pane to that
	 * component. This method allows to set the width and height of the scroll
	 * pane. The <code>reference</code> String is supposed to be a unique name
	 * for the component wich can be used for easy access to the component. If
	 * there is a component at the given coordinates it will be overwritten.
	 *
	 * @param column
	 * @param row
	 * @param comp
	 * @param reference
	 * @param width
	 * @param height
	 */
	public void addLimitedComponent(int column, int row, Component comp, String reference, int width, int height) {
		if (column > columns.size()) {
			for (int i = 0; i < column; i++) {
				JPanel newPanel = new JPanel();
				newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
				columns.add(newPanel);
			}
		}
		int cIndex = column - 1;
		int rIndex = row - 1;
		JPanel columnPanel = columns.get(cIndex);
		if (!columnContent.containsKey(columnPanel)) {
			columnContent.put(columnPanel, new ArrayList<Component>());
		}
		ArrayList<Component> columnComps = columnContent.get(columnPanel);
		if (columnComps.size() < row) {
			for (int i = 0; i < row; i++) {
				columnComps.add(null);
			}
		}
		JScrollPane scroll = new JScrollPane(comp);
		scroll.setSize(width, height);
		columnComps.set(rIndex, scroll);
		components.put(reference, comp);
		componentColumnsIndexes.put(reference, cIndex);
		columnPanel.add(scroll);
	}

	/**
	 * Adds the component to the easy frame and attaches a scroll pane to that
	 * component. The <code>reference</code> String is supposed to be a unique
	 * name for the component wich can be used for easy access to the component.
	 * If there is a component at the given coordinates it will be overwritten.
	 *
	 * @param column
	 * @param row
	 * @param comp
	 * @param reference
	 */
	public void addScrolledComponent(int column, int row, Component comp, String reference) {
		if (column > columns.size()) {
			for (int i = 0; i < column; i++) {
				JPanel newPanel = new JPanel();
				newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
				columns.add(newPanel);
			}
		}
		int cIndex = column - 1;
		int rIndex = row - 1;
		JPanel columnPanel = columns.get(cIndex);
		if (!columnContent.containsKey(columnPanel)) {
			columnContent.put(columnPanel, new ArrayList<Component>());
		}
		ArrayList<Component> columnComps = columnContent.get(columnPanel);
		if (columnComps.size() < row) {
			for (int i = 0; i < row; i++) {
				columnComps.add(null);
			}
		}
		JScrollPane scroll = new JScrollPane(comp);
		columnComps.set(rIndex, scroll);
		components.put(reference, comp);
		componentColumnsIndexes.put(reference, cIndex);
		columnPanel.add(scroll);
	}

	/**
	 * Adds a component to the specified column and row. The
	 * <code>reference</code> String is supposed to be a unique name for the
	 * component wich can be used for easy access to the component. If there is
	 * a component at the given coordinates it will be overwritten.
	 *
	 * @param column
	 * @param row
	 * @param comp
	 * @param reference - unique name for the component
	 */
	public void addComponent(int column, int row, Component comp, String reference) {
		if (column > columns.size()) {
			for (int i = 0; i < column; i++) {
				JPanel newPanel = new JPanel();
				newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
				columns.add(newPanel);
			}
		}
		int cIndex = column - 1;
		int rIndex = row - 1;
		JPanel columnPanel = columns.get(cIndex);
		if (!columnContent.containsKey(columnPanel)) {
			columnContent.put(columnPanel, new ArrayList<Component>());
		}
		ArrayList<Component> columnComps = columnContent.get(columnPanel);
		if (columnComps.size() < row) {
			for (int i = 0; i < row; i++) {
				columnComps.add(null);
			}
		}
		columnComps.set(rIndex, comp);
		components.put(reference, comp);
		componentColumnsIndexes.put(reference, cIndex);
		columnPanel.add(comp);
	}

	public Component getComponent(String reference) {
		return components.get(reference);
	}

	/**
	 * Remove the component with the specified String identifier from the easy
	 * frame. Returns true if such a reference was found.
	 *
	 * @param reference
	 * @return
	 */
	public boolean removeComponent(String reference) {
		if (components.containsKey(reference)) {
			Component comp = components.get(reference);
			int columnNum = componentColumnsIndexes.get(reference);
			JPanel columnPanel = columns.get(columnNum);
			columnContent.get(columnPanel).remove(comp);
			columnPanel.remove(comp);
			components.remove(reference);
			componentColumnsIndexes.remove(reference);
			int notNullCount = 0;
			for (int i = 0; i < columnContent.get(columnPanel).size(); i++) {
				if (columnContent.get(columnPanel).get(i) != null) {
					notNullCount++;
				}
			}
			if (notNullCount == 0) {
				columns.remove(columnPanel);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns an array of Strings containing the reference names of the
	 * components of the easy frame.
	 *
	 * @return
	 */
	public String[] getReferenceList() {
		Iterator<String> iter = components.keySet().iterator();
		String[] sarr = new String[components.keySet().size()];
		int index = 0;
		while (iter.hasNext()) {
			sarr[index] = iter.next();
			index++;
		}
		return sarr;
	}
	
	public void close() {
		frame.dispose();
	}
}
