package be.tusca.swing.filter.inline.filters.impl;

import be.tusca.swing.filter.inline.filters.AbstractTextFilter;
import be.tusca.swing.filter.inline.filters.AbstractTextFilter.FilterOnDocumentChangeListener;
import be.tusca.swing.filter.inline.filters.AbstractTextFilter.FilterOnEnterKeyListener;




public class SimpleTextFilter extends AbstractTextFilter {

	private final TextFilter delegate;

	public SimpleTextFilter(TextFilter delegate, FilterTrigger trigger) {
		super();
		this.delegate = delegate;
		if (trigger == FilterTrigger.ON_CHANGE) {
			this.getTextField().getDocument().addDocumentListener(new FilterOnDocumentChangeListener());
		} else if (trigger == FilterTrigger.ON_ENTER) {
			this.getTextField().addKeyListener(new FilterOnEnterKeyListener());
		} else {
			throw new IllegalArgumentException("Invalid trigger "+trigger);
		}
	}
	
	@Override
	public boolean filter(Object value) {
		return this.delegate.filter(value,this.getTextField().getText());
	}

	public static enum FilterTrigger {
		ON_CHANGE, ON_ENTER
	}
}
