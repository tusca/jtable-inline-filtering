package be.tusca.swing.filter.inline.support;

import javax.swing.RowFilter;
import javax.swing.table.TableModel;

import be.tusca.swing.filter.inline.InlineFilterHeader;
import be.tusca.swing.filter.inline.filters.InlineFilter;

/**
 * Implementation of the filtering extending the java6 row filter.
 * 
 * 
 * @author Guy Renard
 *
 */
public class InlineFilterRowFilter extends RowFilter<TableModel, Integer> {
	private final InlineFilterHeader header;

	public InlineFilterRowFilter(InlineFilterHeader header) {
		this.header = header;
	}

	@Override
	public boolean include(javax.swing.RowFilter.Entry<? extends TableModel, ? extends Integer> rowEntry) {
		for (java.util.Map.Entry<Integer, InlineFilter> filterEntry : this.header.getFilterMap().entrySet()) {
			Object value = rowEntry.getValue(filterEntry.getKey());
			if (!filterEntry.getValue().filter(value)) {
				return false;
			}
		}
		return true;
	}
}
