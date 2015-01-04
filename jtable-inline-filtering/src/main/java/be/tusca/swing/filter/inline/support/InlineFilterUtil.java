package be.tusca.swing.filter.inline.support;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Utility methods.
 * 
 * 
 * @author Guy Renard
 *
 */
public class InlineFilterUtil {
	
	public static List<TableColumn> getColumns(TableColumnModel columnModel) {
		List<TableColumn> tablecolumns = new ArrayList<TableColumn>(columnModel.getColumnCount());
		for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
			tablecolumns.add(columnModel.getColumn(columnIndex));
		}
		
		return tablecolumns;
	}
}
