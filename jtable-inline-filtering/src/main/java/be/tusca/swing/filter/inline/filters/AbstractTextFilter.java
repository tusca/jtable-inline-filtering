package be.tusca.swing.filter.inline.filters;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Abstract filter that implements a panel with a label (for the column name) and a text field for the filter.
 * Also provides a listener to filter on the fly (FilterOnDocumentChangeListener) or on pressing enter (FilterOnEnterKeyListener)
 * which can be set from subclasses on the document or on the text field.
 * 
 * 
 * @author Guy Renard
 *
 */
public abstract class AbstractTextFilter extends AbstractInlineFilter {

	protected final TextFilterPanel editPanel;
	protected final TextFilterPanel viewPanel;

	public AbstractTextFilter() {
		super();
		this.editPanel = new TextFilterPanel();
		this.viewPanel = new TextFilterPanel();
		this.link(this.editPanel.textField, this.viewPanel.textField);
	}
	
	public class FilterOnDocumentChangeListener implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			AbstractTextFilter.this.fireFilter();
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
			AbstractTextFilter.this.fireFilter();
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			AbstractTextFilter.this.fireFilter();
		}
	}
	
	public class FilterOnEnterKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				AbstractTextFilter.this.fireFilter();
			}
		};
	}
	
	public JTextField getTextField() {
		return this.editPanel.textField;
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
	
	protected class TextFilterPanel extends JPanel {
		protected JLabel textLabel;
		protected JTextField textField;

		public TextFilterPanel() {
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
				JTextField textField = new JTextField();
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.gridy = 1;
				constraints.weightx = 1.0;
				constraints.fill = GridBagConstraints.HORIZONTAL;
				constraints.insets = new Insets(2, 2, 2, 2);
				container.add(textField, constraints);
				this.textField = textField;
			}			
		}
	}
}
