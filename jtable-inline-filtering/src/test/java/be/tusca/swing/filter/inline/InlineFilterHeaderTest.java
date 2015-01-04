package be.tusca.swing.filter.inline;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.junit.Test;

/**
 * Unit test adding the table header to the table, check initializations.
 * 
 * @author Guy Renard
 *
 */
public class InlineFilterHeaderTest {

	@Test
	public void addToTable() {
		JTable table = new JTable();
		
		InlineFilterHeader tableHeader = new InlineFilterHeader();
		table.setTableHeader(tableHeader);
	}
	
	@Test
	public void addWithSorter() {
		JTable table = new JTable();
		TableRowSorter<TableModel> tableSorter = new TableRowSorter<TableModel>();
		InlineFilterHeader tableHeader = new InlineFilterHeader();
		table.setTableHeader(tableHeader);
		table.setRowSorter(tableSorter);
	}
	
}
