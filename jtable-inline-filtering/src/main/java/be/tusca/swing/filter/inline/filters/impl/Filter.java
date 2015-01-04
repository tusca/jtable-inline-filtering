package be.tusca.swing.filter.inline.filters.impl;

/**
 * Filter interface used in the simple combobox filter
 * 
 * @author Guy Renard
 *
 */
public interface Filter {
	
	boolean filter(Object tableValue, Object filterValue);
}
