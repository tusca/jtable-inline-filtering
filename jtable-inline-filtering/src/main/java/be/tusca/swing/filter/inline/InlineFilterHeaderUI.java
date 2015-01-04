package be.tusca.swing.filter.inline;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableColumnModel;

/**
 * Header UI redefining the mouse input listener.
 * 
 * 
 * @author Guy Renard
 *
 */
public class InlineFilterHeaderUI extends BasicTableHeaderUI {

	@Override
	protected MouseInputListener createMouseInputListener() {
		return new MouseInputHandler((InlineFilterHeader) this.header);
	}

	public class MouseInputHandler extends BasicTableHeaderUI.MouseInputHandler {
		private Component dispatchComponent;
		protected InlineFilterHeader header;

		public MouseInputHandler(InlineFilterHeader header) {
			this.header = header;
		}

		private void setDispatchComponent(MouseEvent e) {
			Component editorComponent = this.header.getEditorComponent();
			Point p = e.getPoint();
			Point p2 = SwingUtilities.convertPoint(this.header, p, editorComponent);
			this.dispatchComponent = SwingUtilities.getDeepestComponentAt(editorComponent, p2.x, p2.y);
		}

		private boolean repostEvent(MouseEvent e) {
			if (this.dispatchComponent == null) {
				return false;
			}
			MouseEvent e2 = SwingUtilities.convertMouseEvent(this.header, e, this.dispatchComponent);
			this.dispatchComponent.dispatchEvent(e2);
			return true;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (this.header.getTableCellEditor() != null) {
				((InlineFilterHeaderCellEditor) this.header.getTableCellEditor()).stopCellEditing();
			}
			if (!SwingUtilities.isLeftMouseButton(e)) {
				return;
			}
			super.mousePressed(e);
			if (this.header.getResizingColumn() == null) {
				Point p = e.getPoint();
				TableColumnModel tableColumnModel = this.header.getColumnModel();
				int index = tableColumnModel.getColumnIndexAtX(p.x);
				if (index != -1) {
					if (this.header.editCell(index, e)) {
						this.setDispatchComponent(e);
						this.repostEvent(e);
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			if (!SwingUtilities.isLeftMouseButton(e)) {
				return;
			}
			this.repostEvent(e);
			this.dispatchComponent = null;
		}

	}
}
