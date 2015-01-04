package be.tusca.swing.filter.inline;

import javax.swing.table.TableColumn;

import be.tusca.swing.filter.inline.support.InlineFilterEditorComponentProvider;

/**
 * Extension on the default TableColumn with editable attribute and editor override.
 * 
 * 
 * @author Guy Renard
 *
 */
public class InlineFilterHeaderTableColumn extends TableColumn {

	protected InlineFilterHeaderCellEditor headerEditor;
	protected boolean isHeaderEditable;

	public InlineFilterHeaderTableColumn(InlineFilterEditorComponentProvider panelProvider, TableColumn existing) {
		this.setHeaderEditor(new InlineFilterHeaderCellEditor(panelProvider));
		this.isHeaderEditable = true;
		this.modelIndex = existing.getModelIndex();
		this.identifier = existing.getIdentifier();
		this.width = existing.getWidth();
		this.minWidth = existing.getMinWidth();
		this.setPreferredWidth(existing.getPreferredWidth());
		this.maxWidth = existing.getMaxWidth();
		this.headerRenderer = existing.getHeaderRenderer();
		this.headerValue = existing.getHeaderValue();
		this.cellRenderer = existing.getCellRenderer();
		this.cellEditor = existing.getCellEditor();
		this.isResizable = existing.getResizable();
	}

	public void setHeaderEditor(InlineFilterHeaderCellEditor headerEditor) {
		this.headerEditor = headerEditor;
	}

	public InlineFilterHeaderCellEditor getHeaderEditor() {
		return this.headerEditor;
	}

	public void setHeaderEditable(boolean isEditable) {
		this.isHeaderEditable = isEditable;
	}

	public boolean isHeaderEditable() {
		return this.isHeaderEditable;
	}

}
