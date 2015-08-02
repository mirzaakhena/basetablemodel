package com.mirzaakhena.ui.basetablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author mirzaakhena@gmail.com
 *
 */
@SuppressWarnings("serial")
public abstract class BaseTableModel<T> extends AbstractTableModel {

	private List<T> list;

	private final String[] COLUMN_HEADER;

	/**
	 * You SHOULD call this super constructor to defined your header table
	 * @param columnHeader
	 */
	public BaseTableModel(String... columnHeader) {
		COLUMN_HEADER = columnHeader;
		list = new ArrayList<T>();
	}

	/**
	 * by overiding this method you can decide what column/row editable or not
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_HEADER[column];
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_HEADER.length;
	}

	/**
	 * get an item by index
	 * @param index
	 * @return
	 */
	public T getItem(int index) {
		return list.get(index);
	}

	public List<T> getAllItem() {
		return list;
	}

	/**
	 * reload all the item in the table
	 * @param x
	 */
	public void setItem(List<T> x) {
		if (x == null) {
			list.clear();
		} else {
			list = x;
		}
		fireTableDataChanged();
	}

	public void insert(T x) {
		list.add(x);
		int n = list.size() - 1;
		fireTableRowsInserted(n, n);
	}

	public void insertOrder(T x, Comparable<T> comparator) {
		int size = list.size();
		boolean found = false;
		if (size != 0) {
			int n = size - 1;
			for (int i = 0; i < n && !found; i++) {
				if (comparator.compareTo(list.get(i)) < 0) {
					insert(x, i);
					found = true;
				}
			}
		}
		if (!found) {
			insert(x);
		}
	}

	public void insert(T x, int index) {
		list.add(index, x);
		int n = list.size() - 1;
		fireTableRowsInserted(n, n);
	}

	public void insert(List<T> list) {
		list.addAll(list);
		fireTableDataChanged();
	}

	public void update(T x, int index) {
		list.set(index, x);
		fireTableRowsUpdated(index, index);
	}

	public void update(int index) {
		fireTableRowsUpdated(index, index);
	}

	public void update(T x) {
		int index = list.indexOf(x);
		if (index != -1) {
			fireTableRowsUpdated(index, index);
		}
	}

	public void update() {
		fireTableDataChanged();
	}

	public void delete(int index) {
		list.remove(index);
		fireTableRowsDeleted(index, index);
	}

	public void delete(T x) {
		int index = list.indexOf(x);
		list.remove(x);
		fireTableRowsDeleted(index, index);

	}

	public void deleteAll() {
		list.clear();
		fireTableDataChanged();
	}

}
