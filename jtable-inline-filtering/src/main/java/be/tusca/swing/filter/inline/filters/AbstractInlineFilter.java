package be.tusca.swing.filter.inline.filters;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.table.TableModel;

import be.tusca.swing.filter.inline.support.InlineFilterListener;

/**
 * Abstract class that handles the InlineFilterListener and adds the fireFilter() method to call by subClasses to trigger the filtering.
 * 
 * @author Guy Renard
 *
 */
public abstract class AbstractInlineFilter implements InlineFilter {

	private final List<InlineFilterListener> listeners;
	
	public AbstractInlineFilter() {
		this.listeners = new ArrayList<InlineFilterListener>();
	}
	
	@Override
	public void addInlineFilterListener(InlineFilterListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeInlineFilterListener(InlineFilterListener l) {
		this.listeners.remove(l);
	}

	protected void fireFilter() {
		for (InlineFilterListener l : this.listeners) {
			l.fireFilter();
		}
	}

	protected void link(JTextField source, JTextField target) {
		target.setDocument(source.getDocument());
	}

	@Override
	public void setTableModel(int column, TableModel model) { 
		// empty by default, see the combobox filter 
	}
}
