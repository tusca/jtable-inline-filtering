package be.tusca.swing.filter.inline.filters;

import javax.swing.JComponent;
import javax.swing.table.TableModel;

import be.tusca.swing.filter.inline.support.InlineFilterListener;

/**
 * Interface to implement to make a filter. Some existing abstract implementation can help.
 * 
 * 
 * @author Guy Renard
 *
 */
public interface InlineFilter {
	JComponent getEditorComponent();
	JComponent getViewComponent();
	void addInlineFilterListener(InlineFilterListener l);
	void removeInlineFilterListener(InlineFilterListener l);
	boolean filter(Object value);
	void setColumnName(String name);
	void setTableModel(int column, TableModel model);
}
