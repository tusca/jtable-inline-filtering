package be.tusca.swing.filter.inline;

import org.junit.Assert;
import org.junit.Test;

import be.tusca.swing.filter.inline.filters.InlineFilter;
import be.tusca.swing.filter.inline.filters.impl.SimpleTextFilter;
import be.tusca.swing.filter.inline.filters.impl.SimpleTextFilter.FilterTrigger;
import be.tusca.swing.filter.inline.filters.impl.TextFilter;
import be.tusca.swing.filter.inline.support.InlineFilterListener;


/**
 * Unit test for SimpleTextFilter
 * 
 * 
 * @author Guy Renard
 *
 */
public class InlineTextFilterTest {

	@Test
	public void textFilter() {
		TextFilter containsFilter = new TextFilter() {
			@Override
			public boolean filter(Object tableValue, String filterValue) {
				return String.valueOf(tableValue).contains(filterValue);
			}
		};
		SimpleTextFilter filter = new SimpleTextFilter(containsFilter, FilterTrigger.ON_CHANGE);
		
		filter.addInlineFilterListener(new InlineFilterListener() {
			@Override
			public void fireFilter() {
				
			}
		});
		
		filter.getTextField().setText("TEST");
		this.testFilter(filter, "My TEST", true);
		this.testFilter(filter, "My test", false);
	}

	private void testFilter(InlineFilter filter, Object value, boolean expects) {
		if (expects) {
			Assert.assertTrue("Failed filtering "+value+" with filter", filter.filter(value));
		} else {
			Assert.assertFalse("Failed filtering "+value+" with filter", filter.filter(value));
		}
	}
}
