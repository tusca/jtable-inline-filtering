package be.tusca.swing.filter.inline.filters.impl;

import be.tusca.swing.filter.inline.filters.AbstractComboBoxFilter;

public class SimpleComboBoxFilter extends AbstractComboBoxFilter {

	private final Filter delegate;

	public SimpleComboBoxFilter(Filter delegate) {
		this.delegate = delegate;
		this.getComboBox().setAction(new FilterAction());
	}
	
	@Override
	public boolean filter(Object value) {
		return this.delegate.filter(value, this.getComboBox().getSelectedItem());
	}

}
