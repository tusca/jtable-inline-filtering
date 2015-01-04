package be.tusca.swing.filter.inline.filters.impl;

/**
 * Filter used in the simple text filter to implement the filtering logic itself.
 * 
 * 
 * @author Guy Renard
 *
 */
public interface TextFilter {

	boolean filter(Object tableValue, String filterValue);
}
