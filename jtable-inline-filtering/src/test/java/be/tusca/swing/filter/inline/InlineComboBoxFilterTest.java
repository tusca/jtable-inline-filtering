package be.tusca.swing.filter.inline;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import junit.framework.Assert;

import org.junit.Test;

import be.tusca.swing.filter.inline.filters.impl.Filter;
import be.tusca.swing.filter.inline.filters.impl.SimpleComboBoxFilter;

/**
 * Unit test for SimpleComboBoxFilter
 * 
 * 
 * @author Guy Renard
 *
 */
public class InlineComboBoxFilterTest {

	
	@Test
	public void comboBoxTest() {
		Filter equalsFilter = new Filter() {
			@Override
			public boolean filter(Object tableValue, Object filterValue) {
				return filterValue == null 
						|| (tableValue != null && tableValue.equals(filterValue));
			}
		};
		
		DefaultTableModel tableModel = new DefaultTableModel(new String[][] { new String[] {"Value1"}, new String[] {"Value2"} }, new String[] { "Column1" } );
		SimpleComboBoxFilter comboBoxFilter = new SimpleComboBoxFilter(equalsFilter);
		comboBoxFilter.setTableModel(0, tableModel); 
		
		comboBoxFilter.getComboBox().setSelectedIndex(1);
		Assert.assertEquals("Combobox should contain the first value from the first column", "Value1", comboBoxFilter.getComboBox().getSelectedItem());
		Assert.assertTrue("Value1 should match",comboBoxFilter.filter("Value1"));
		Assert.assertFalse("Value2 should not match",comboBoxFilter.filter("Value2"));
		
		tableModel.addRow(new String[] {"Value3"});
		comboBoxFilter.getComboBox().setSelectedIndex(3);
		Assert.assertEquals("Combobox should contain the first value from the first column", "Value3", comboBoxFilter.getComboBox().getSelectedItem());
		Assert.assertFalse("Value1 should not match",comboBoxFilter.filter("Value1"));
		Assert.assertFalse("Value2 should not match",comboBoxFilter.filter("Value2"));
		Assert.assertTrue("Value3 should match",comboBoxFilter.filter("Value3"));
		
		comboBoxFilter.getComboBox().setSelectedIndex(0);
		Assert.assertNull("Combobox should contain the first value from the first column", comboBoxFilter.getComboBox().getSelectedItem());
		Assert.assertTrue("Value1 should match",comboBoxFilter.filter("Value1"));
		Assert.assertTrue("Value2 should match",comboBoxFilter.filter("Value2"));
		Assert.assertTrue("Value3 should match",comboBoxFilter.filter("Value3"));
	}
	
	@Test
	public void comboBoxTestManualModel() {
		Filter equalsFilter = new Filter() {
			@Override
			public boolean filter(Object tableValue, Object filterValue) {
				return filterValue == null 
						|| (tableValue != null && tableValue.equals(filterValue));
			}
		};
		
		SimpleComboBoxFilter comboBoxFilter = new SimpleComboBoxFilter(equalsFilter);
		comboBoxFilter.setComboBoxModel(new DefaultComboBoxModel(new String[] {null, "Value1", "Value2"}));
		
		comboBoxFilter.getComboBox().setSelectedIndex(1);
		Assert.assertEquals("Combobox should contain the first value from the first column", "Value1", comboBoxFilter.getComboBox().getSelectedItem());
		Assert.assertTrue("Value1 should match",comboBoxFilter.filter("Value1"));
		Assert.assertFalse("Value2 should not match",comboBoxFilter.filter("Value2"));
		
	}
	
	
}
