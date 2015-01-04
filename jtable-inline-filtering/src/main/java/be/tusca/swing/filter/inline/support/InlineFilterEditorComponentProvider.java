package be.tusca.swing.filter.inline.support;

import javax.swing.JComponent;

/**
 * Interface to access the Editor component from the editor renderer.
 * 
 * 
 * @author Guy Renard
 *
 */
public interface InlineFilterEditorComponentProvider {
	JComponent getInlineFilterEditPanel(int column);
}
