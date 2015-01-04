package be.tusca.swing.filter.inline;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

import be.tusca.swing.filter.inline.support.InlineFilterEditorComponentProvider;

/**
 * Cell Editor overriding the default to render the editor panel instead.
 * 
 * 
 * @author Guy Renard
 *
 */
public class InlineFilterHeaderCellEditor extends DefaultCellEditor {
	private final InlineFilterEditorComponentProvider panelProvider;

	public InlineFilterHeaderCellEditor(InlineFilterEditorComponentProvider panelProvider) {
		super(new JTextField());
		this.panelProvider = panelProvider;
		this.setClickCountToStart(1);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return this.getTableCellEditorComponent(column);
	}
	public Component getTableCellEditorComponent(int column) {
		return this.panelProvider.getInlineFilterEditPanel(column);
	}

}
