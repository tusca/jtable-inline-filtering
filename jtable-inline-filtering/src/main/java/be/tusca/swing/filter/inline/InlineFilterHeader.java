package be.tusca.swing.filter.inline;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import be.tusca.swing.filter.inline.filters.InlineFilter;
import be.tusca.swing.filter.inline.support.DragHeaderMouseListener;
import be.tusca.swing.filter.inline.support.InlineFilterEditorComponentProvider;
import be.tusca.swing.filter.inline.support.InlineFilterHeaderRenderer;
import be.tusca.swing.filter.inline.support.InlineFilterListener;
import be.tusca.swing.filter.inline.support.InlineFilterRowFilter;
import be.tusca.swing.filter.inline.support.InlineFilterUtil;

/**
 * Header implementation for Inline Filtering<br/>
 * <br/>
 * Setting the header :
 * <code>
 * JTable table = ...
 * InlineFilterHeader tableHeader = new InlineFilterHeader();
 * table.setTableHeader(tableHeader);
 * </code>
 * Adding filters :
 * <code>
 * tableHeader.addFilter(COLUMN_INDEX, FILTER);
 * </code>
 * 
 * <br/><br/>More details in the InlineFilteringExample project.
 * 
 * @author Guy Renard
 *
 */
public class InlineFilterHeader extends JTableHeader implements CellEditorListener, InlineFilterEditorComponentProvider {
	protected int editingColumn;
	protected TableCellEditor tableCellEditor;
	protected Component editorComponent;
	protected final Map<Integer, JComponent> viewPanels;
	protected final Map<Integer, JComponent> editPanels;
	protected final Map<Integer, InlineFilter> filters;
	protected int stopEditingDragDistance = 3; // tolerance on clicking the editor field
	protected TableColumnModel tableColumnModel;
	private final DragHeaderMouseListener dragHeaderMouseListener;	
	
	/**
	 * Default constructor, will take the TableColumnModel from the table it's set on.
	 */
	public InlineFilterHeader() {
		this(null);
	}
	
	public InlineFilterHeader(TableColumnModel tableColumnModel) {
		super(tableColumnModel);
		this.tableColumnModel = tableColumnModel;
		this.viewPanels = new HashMap<Integer, JComponent>();
		this.editPanels = new HashMap<Integer, JComponent>();
		this.filters = new HashMap<Integer, InlineFilter>();
		if (tableColumnModel != null) {
			this.rebuildTableColumns(this.columnModel);
		}
		this.setReorderingAllowed(true);
		this.setDefaultRenderer(new InlineFilterHeaderRenderer(this));
		this.dragHeaderMouseListener = new DragHeaderMouseListener(this);
		this.addMouseMotionListener(this.dragHeaderMouseListener);
		this.addMouseListener(this.dragHeaderMouseListener);
		this.addComponentListener(new InternalComponentListener());
	}

	private class InternalComponentListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			if (InlineFilterHeader.this.tableCellEditor != null) {
				InlineFilterHeader.this.tableCellEditor.stopCellEditing();
			}
		}
	}
	
	@Override
	public void setTable(JTable table) {
		super.setTable(table);
		if (this.tableColumnModel == null) {
			this.setColumnModel(table.getColumnModel());
			this.rebuildTableColumns(this.columnModel);
		}
	}
	
	private void rebuildTableColumns(TableColumnModel columnModel) {
		List<TableColumn> columns = new ArrayList<TableColumn>();
		for (TableColumn tableColumn : InlineFilterUtil.getColumns(columnModel)) {
			columns.add(new InlineFilterHeaderTableColumn(this, tableColumn));
			columnModel.removeColumn(tableColumn);
		}
		for (TableColumn tableColumn : columns) {
			columnModel.addColumn(tableColumn);
		}
	}
	
	public JComponent getInlineFilterPanel(int index) {
		return this.viewPanels.get(this.columnModel.getColumn(index).getModelIndex());
	}

	@Override
	public JComponent getInlineFilterEditPanel(int index) {
		return this.editPanels.get(this.columnModel.getColumn(index).getModelIndex());
	}
	
	/**
	 * Add a filter on a given column
	 * @param column column index
	 * @param inlineFilter
	 */
	public void addFilter(int column, InlineFilter inlineFilter) {
		// TODO check index vs model index in case the filter is added after columns are moved around
		String columnName = this.table.getModel().getColumnName(column);
		inlineFilter.setColumnName(columnName);
		inlineFilter.setTableModel(column, this.table.getModel());
		this.editPanels.put(column, inlineFilter.getEditorComponent());
		this.viewPanels.put(column, inlineFilter.getViewComponent());
		this.filters.put(column, inlineFilter);
		inlineFilter.addInlineFilterListener(new InternalInlineFilterListener());
	}
	
	// TODO add a removeFilter Method

	private class InternalInlineFilterListener implements InlineFilterListener {
		@Override
		public void fireFilter() {
			InlineFilterHeader.this.filter();
		}
	}

	@SuppressWarnings("unchecked")
	private void filter() {
		TableRowSorter<TableModel> rowSorter = (TableRowSorter<TableModel>) this.table.getRowSorter();
		rowSorter.setRowFilter(new InlineFilterRowFilter(this));
	}
	
	/**
	 * @return list of filters set on the header
	 */
	public List<InlineFilter> getFilters() {
		List<InlineFilter> result = new ArrayList<InlineFilter>(this.filters.size());
		for (int i=0; i<this.filters.size(); i++) {
			result.add(this.filters.get(i));
		}
		return result;
	}

	@Override
	public void updateUI() {
		this.setUI(new InlineFilterHeaderUI());
		this.resizeAndRepaint();
		this.invalidate();
	}
	
	public boolean editCell(int index, EventObject e) {
		if (this.tableCellEditor != null && !this.tableCellEditor.stopCellEditing()) {
			return false;
		}
		if (!this.hasFilter(index)) {
			return false;
		}
		InlineFilterHeaderCellEditor editor = this.getCellEditor(index);
		if (editor != null && editor.isCellEditable(e)) {
			this.editorComponent = editor.getTableCellEditorComponent(index); 
			this.editorComponent.setBounds(this.getHeaderRect(index));
			this.add(this.editorComponent);
			this.editorComponent.validate();
			this.setTableCellEditor(editor);
			this.editingColumn = index;
			editor.addCellEditorListener(this);
			return true;
		}
		return false;
	}

	private boolean hasFilter(int index) {
		int columnIndex = this.columnModel.getColumn(index).getModelIndex();
		return this.filters.containsKey(columnIndex);
	}

	public InlineFilterHeaderCellEditor getCellEditor(int index) {
		int columnIndex = this.columnModel.getColumn(index).getModelIndex();
		InlineFilterHeaderTableColumn col = (InlineFilterHeaderTableColumn) this.columnModel.getColumn(columnIndex);
		return col.getHeaderEditor();
	}

	public TableCellEditor getTableCellEditor() {
		return this.tableCellEditor;
	}
	
	public void setTableCellEditor(TableCellEditor editor) {
		if (this.tableCellEditor != null) {
			this.tableCellEditor.removeCellEditorListener(this);
		}
		this.tableCellEditor = editor;
		if (this.tableCellEditor != null) {
			this.tableCellEditor.addCellEditorListener(this);
		}
	}

	public Component getEditorComponent() {
		return this.editorComponent;
	}

	public void stopEditing() {
		TableCellEditor editor = this.getTableCellEditor();
		if (editor != null) {
			editor.removeCellEditorListener(this);
			this.requestFocus();
			this.remove(this.editorComponent);
			Rectangle headerRectangle = this.getHeaderRect(this.editingColumn);
			this.editingColumn = -1;
			this.editorComponent = null;
			this.setTableCellEditor(null);
			this.repaint(headerRectangle);
		}
	}

	@Override
	public void editingStopped(ChangeEvent e) {
		this.stopEditing();
	}

	@Override
	public void editingCanceled(ChangeEvent e) {
		this.stopEditing();
	}

	public int getStopEditingDragDistance() {
		return this.stopEditingDragDistance;
	}

	public void setStopEditingDragDistance(int stopEditingDragDistance) {
		this.stopEditingDragDistance = stopEditingDragDistance;
	}

	public Map<Integer, InlineFilter> getFilterMap() {
		return Collections.unmodifiableMap(this.filters);
	}

}
