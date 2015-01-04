package be.tusca.sample.swing.filter.inline.sample1;


import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class SampleTableModel extends AbstractTableModel implements TableModel {
	private static final long serialVersionUID = -4265908063949641959L;
	private static final String[] names = { "sample1", "sample2", "sample3" };
	private final List<Sample> list;
	
	public SampleTableModel() {
		this.list = new ArrayList<Sample>();
	}
	
	@Override
	public String getColumnName(int column) {
		return names[column];
	}
	
	@Override
	public int getRowCount() {
		return this.list.size();
	}

	@Override
	public int getColumnCount() {
		return names.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Sample sample = this.list.get(rowIndex);
		switch (columnIndex) {
		case 0: return sample.getSample1();
		case 1: return sample.getSample2();
		case 2: return sample.getSample3();
		default: throw new IllegalArgumentException("wrong column index "+columnIndex);
		}
	}

	public void add(Sample sample) {
		this.list.add(sample);
		this.fireTableDataChanged();
	}
}
