package be.tusca.swing.filter.inline.support;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import be.tusca.swing.filter.inline.InlineFilterHeader;
import be.tusca.swing.filter.inline.filters.InlineFilter;


/**
 * Renderer wrapper to get the default or filter panel and render it.
 * 
 * 
 * @author Guy Renard
 *
 */
public class InlineFilterHeaderRenderer implements TableCellRenderer {
	private final TableCellRenderer delegate;
	private final InlineFilterHeader header;

	public InlineFilterHeaderRenderer(InlineFilterHeader header) {
		this.header = header;
		this.delegate = header.getDefaultRenderer();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JComponent filterPanel = this.header.getInlineFilterPanel(column);
		if (filterPanel != null) {
			return filterPanel;
		} else {
			return this.getDefaultTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}

	public Component getDefaultTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component result = this.delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		result.setPreferredSize(new Dimension(result.getSize().width, this.getMaxHeight(result.getSize().height)));
		return result;
	}

	protected int getMaxHeight(int height) {
		for (InlineFilter f : this.header.getFilterMap().values()) {
			height = Math.max(height, f.getViewComponent().getPreferredSize().height);
		}
		return height;
	}
}
