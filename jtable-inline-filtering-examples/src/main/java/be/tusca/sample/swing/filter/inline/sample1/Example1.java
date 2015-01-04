package be.tusca.sample.swing.filter.inline.sample1;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
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

public class Example1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel container = createPanel();
		Dimension d = new Dimension(500, 500);
		container.setPreferredSize(d);
		container.setSize(d);
		frame.getContentPane().add(container);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	private static JPanel createPanel() {
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		{
			TextFilter containsFilter = new TextFilter() {
				@Override
				public boolean filter(Object tableValue, String filterValue) {
					return String.valueOf(tableValue).contains(filterValue);
				}
			};
			Filter equalsFilter = new Filter() {
				@Override
				public boolean filter(Object tableValue, Object filterValue) {
					return filterValue == null 
							|| (tableValue != null && tableValue.equals(filterValue));
				}
			};
			
			SampleTableModel tableModel = new SampleTableModel();
			tableModel.add(new Sample("Doctor","Who","Fiction"));
			tableModel.add(new Sample("Buffy","Summers","Fiction"));
			tableModel.add(new Sample("Hello","Suzie","Carmen"));
			tableModel.add(new Sample("San","Diego","Alabama"));

			JTable table = new JTable(tableModel);
			TableRowSorter<TableModel> tableSorter = new TableRowSorter<TableModel>(tableModel);
			
			InlineFilterHeader tableHeader = new InlineFilterHeader();
			table.setTableHeader(tableHeader);
			table.setRowSorter(tableSorter);
			
			tableHeader.addFilter(0, new SimpleTextFilter(containsFilter, FilterTrigger.ON_CHANGE));
			tableHeader.addFilter(1, new SimpleTextFilter(containsFilter, FilterTrigger.ON_ENTER));
			SimpleComboBoxFilter comboBoxFilter = new SimpleComboBoxFilter(equalsFilter);
//			comboBoxFilter.setComboBoxModel(new DefaultComboBoxModel(new String[] {"", "Fiction", "Carmen"}));
			tableHeader.addFilter(2, comboBoxFilter); 
			
			tableModel.add(new Sample("Test","Test","Test"));
			
			JScrollPane scrollPane = new JScrollPane(table);
			container.add(scrollPane, BorderLayout.CENTER);
			
		}

		return container;
	}

}
