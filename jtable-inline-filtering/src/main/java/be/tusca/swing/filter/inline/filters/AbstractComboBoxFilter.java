package be.tusca.swing.filter.inline.filters;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Abstract class that implements a simple panel with a label (for column name)
 * and a ComboBox for filtering. An innerclass FilterAction provides a simple
 * way to trigger the filter if set on the combobox in a subclass. Also if no
 * ComboBox model is explicitly set then there will be one created with all
 * different values from the table.
 * 
 * @author Guy Renard
 * 
 */
public abstract class AbstractComboBoxFilter extends AbstractInlineFilter {

	protected final ComboBoxFilterPanel editPanel;
	protected final ComboBoxFilterPanel viewPanel;
	protected ComboBoxModel comboBoxModel;
	private TableModel tableModel;
	private int column;
	private boolean comboBoxModelProvided;

	public AbstractComboBoxFilter() {
		super();
		this.editPanel = new ComboBoxFilterPanel();
		this.viewPanel = new ComboBoxFilterPanel();
		this.comboBoxModelProvided = false;
	}

	public class FilterAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			AbstractComboBoxFilter.this.fireFilter();
		}
	}

	public void setComboBoxModel(ComboBoxModel model) {
		this.comboBoxModel = model;
		this.editPanel.comboBox.setModel(model);
		this.viewPanel.comboBox.setModel(model);
		this.comboBoxModelProvided = true;
	}

	@Override
	public void setTableModel(int column, TableModel tableModel) {
		this.column = column;
		if (!this.comboBoxModelProvided) {
			this.tableModel = tableModel;
			if (tableModel instanceof AbstractTableModel) {
				((AbstractTableModel) tableModel).addTableModelListener(new InternalTableModelListener());
			}
			this.comboBoxModel = new DefaultComboBoxModel();
			this.editPanel.comboBox.setModel(this.comboBoxModel);
			this.viewPanel.comboBox.setModel(this.comboBoxModel);
			this.reload();
		}
	}

	private void reload() {
		Set<Object> set = new HashSet<Object>();
		List<Object> list = new ArrayList<Object>();
		list.add(null);
		for (int row = 0; row < this.tableModel.getRowCount(); row++) {
			Object object = this.tableModel.getValueAt(row, this.column);
			if (!set.contains(object)) {
				set.add(object);
				list.add(object);
			}
		}
		DefaultComboBoxModel defaultComboBoxModel = (DefaultComboBoxModel)this.comboBoxModel; 
		defaultComboBoxModel.removeAllElements();
		for (Object object : list) {
			defaultComboBoxModel.addElement(object);
		}
	}

	private class InternalTableModelListener implements TableModelListener {
		@Override
		public void tableChanged(TableModelEvent e) {
			AbstractComboBoxFilter.this.reload();
		}
	}

	public JComboBox getComboBox() {
		return this.editPanel.comboBox;
	}

	@Override
	public JComponent getEditorComponent() {
		return this.editPanel;
	}

	@Override
	public JComponent getViewComponent() {
		return this.viewPanel;
	}

	@Override
	public void setColumnName(String name) {
		this.editPanel.textLabel.setText(name);
		this.viewPanel.textLabel.setText(name);
	}

	protected class ComboBoxFilterPanel extends JPanel {
		protected JLabel textLabel;
		protected JComboBox comboBox;

		public ComboBoxFilterPanel() {
			super();
			this.initGui();
		}

		private void initGui() {
			this.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			JPanel container = this;
			container.setLayout(new GridBagLayout());
			{
				JLabel label = new JLabel();
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.anchor = GridBagConstraints.NORTH;
				constraints.weighty = 1.0;
				container.add(label, constraints);
				this.textLabel = label;
			}
			{
				JComboBox comboBox = new JComboBox();
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.gridy = 1;
				constraints.weightx = 1.0;
				constraints.fill = GridBagConstraints.HORIZONTAL;
				constraints.insets = new Insets(2, 2, 2, 2);
				container.add(comboBox, constraints);
				this.comboBox = comboBox;
			}
		}
	}

}
