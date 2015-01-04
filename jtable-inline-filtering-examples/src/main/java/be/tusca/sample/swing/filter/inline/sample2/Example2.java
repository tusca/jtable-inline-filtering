package be.tusca.sample.swing.filter.inline.sample2;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import be.tusca.swing.filter.inline.InlineFilterHeader;
import be.tusca.swing.filter.inline.filters.impl.Filter;
import be.tusca.swing.filter.inline.filters.impl.SimpleComboBoxFilter;
import be.tusca.swing.filter.inline.filters.impl.SimpleTextFilter;
import be.tusca.swing.filter.inline.filters.impl.SimpleTextFilter.FilterTrigger;
import be.tusca.swing.filter.inline.filters.impl.TextFilter;

/**
 * Simple example on how to use inline filtering with a default table model
 * 
 * @author Guy Renard
 *
 */
public class Example2 {
	
	private static final String[] header = {
		"Country", "City Name", "Population", "Last Update"
	};
	private static final Object[][] data = {
		{ "Belgium", "Brussels", 1089538 , "Jan 2010" },
		{ "France" , "Paris"   , 12089098, "Jan 2008" },
		{ "Belgium", "Bruges"  , 116741  , "Jan 2010" },
		{ "Belgium", "Ghent"   , 243366  , "Jan 2010" },
		{ "France" , "Toulouse", 439553  , "Jan 2008" },
		{ "England", "Cardiff" , 341054  , "Jan 2001" }
	};
	
	public static void main(String[] args) {
		// Create table with default table model using data from arrays
		JTable table = new JTable(data, header);
		
		// Set the row sorter
		table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()));
		
		// Create the new table header
		InlineFilterHeader tableHeader = new InlineFilterHeader();
		table.setTableHeader(tableHeader);

		// text filter
		TextFilter containsFilter = new ContainsFilter();
		SimpleTextFilter cityFilter = new SimpleTextFilter(containsFilter, FilterTrigger.ON_CHANGE);
		SimpleTextFilter dateFilter = new SimpleTextFilter(containsFilter, FilterTrigger.ON_CHANGE);
		
		// combo box filter
		Filter equalsFilter = new EqualsFilter();
		SimpleComboBoxFilter comboBoxFilter = new SimpleComboBoxFilter(equalsFilter);
		
		// configure the filters
		tableHeader.addFilter(0, comboBoxFilter);
		tableHeader.addFilter(1, cityFilter);
		tableHeader.addFilter(3, dateFilter);
		
		// show the table
		show(table);
	}
	
	private static void show(JTable table) {
		JFrame frame = new JFrame();
		JScrollPane scrollPane = new JScrollPane(table);
		Dimension d = new Dimension(500, 500);
		scrollPane.setPreferredSize(d);
		frame.getContentPane().add(scrollPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private static class ContainsFilter implements TextFilter {
		@Override
		public boolean filter(Object tableValue, String filterValue) {
			return String.valueOf(tableValue).contains(filterValue);
		}	
	}
	
	private static class EqualsFilter implements Filter {
		@Override
		public boolean filter(Object tableValue, Object filterValue) {
			return filterValue == null || (tableValue != null && tableValue.equals(filterValue));
		}
	}
}
