package be.tusca.swing.filter.inline.support;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.table.TableCellEditor;

import be.tusca.swing.filter.inline.InlineFilterHeader;

/**
 * Mouse listener makes the switch back from editing mode to rendering mode when dragging the column.
 * also the dragDistance tolerance makes it easier to click on the edit fields by allowing a little drag.
 * 
 *  
 * @author Guy Renard
 *
 */
public class DragHeaderMouseListener extends MouseAdapter implements MouseMotionListener {
		private int x;
		private int y;
		private final InlineFilterHeader inlineFilterHeader;

		public DragHeaderMouseListener(InlineFilterHeader inlineFilterHeader) {
			this.inlineFilterHeader = inlineFilterHeader;
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
		}
		
		@Override // this avoid dragging the renderer while the editor stays, so hide editor
		public void mouseDragged(MouseEvent e) {
			TableCellEditor tableCellEditor = this.inlineFilterHeader.getTableCellEditor();
			int dist = Math.abs(this.x - e.getX())+Math.abs(this.y - e.getY());
			if (tableCellEditor != null && dist >= this.inlineFilterHeader.getStopEditingDragDistance()) {
				tableCellEditor.stopCellEditing();
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			this.x = e.getX();
			this.y = e.getY();
		}
	}
