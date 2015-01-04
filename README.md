jtable-inline-filtering
=======================

Inline filtering for JTable in Java Swing

What ?
-------
The idea here is to add inline filtering to jtable through the means of a custom TableHeader and RowSorter, no subclassing of JTable.
The inline filter fields, either text field or combo box (or add your own), are inserted into the table header along with the caption of the column.
The filters can be triggered either by "on change" (for all fields) or "on enter" (for text fields), you can create your own very easily.

How to use ?
-------------

```java
InlineFilterHeader tableHeader = new InlineFilterHeader();
table.setTableHeader(tableHeader);

table.setRowSorter(new TableRowSorter<TableModel>(table.getModel())); // standard sorter to allow filtering

tableHeader.addFilter(COLUMN_INDEX, filterForThatColumn);
...
```

where the filter could be (taken from examples module)
```java
TextFilter containsFilter = new TextFilter() {
		@Override
		public boolean filter(Object tableValue, String filterValue) {
			return String.valueOf(tableValue).contains(filterValue);
		}
};
SimpleTextFilter myFilter = new SimpleTextFilter(containsFilter, FilterTrigger.ON_CHANGE);
```

or
```java
Filter equalsFilter = new EqualsFilter();
SimpleComboBoxFilter myFilter = new SimpleComboBoxFilter(equalsFilter);

private static class EqualsFilter implements Filter {
		@Override
		public boolean filter(Object tableValue, Object filterValue) {
			return filterValue == null || (tableValue != null && tableValue.equals(filterValue));
		}
}
```

Maven modules
--------------
* jtable-inline-filtering : main java implementation module
* jtable-inline-filtering-examples : some simple examples


How it works ?
--------------

The JTable headers are rendered in InlineFilterHeaderRenderer using a component provided by InlineFilterHeader which it turn has those from the added filters (allows thus for custom panels).

The panels are painted twice, once for simple rendition and once when editing which is done when clicking in there. Also to still allow draging columns the editing stops when dragged (DragHeaderMouseListener).

The editing of the header is a bit of a hack because JTables were not designed for this.



It was fun to make, Enjoy !
