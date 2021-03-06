package com.bakes.aqacomp4.gui;

import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.bakes.aqacomp4.imagetools.ImageNotTestedException;
import com.bakes.aqacomp4.imagetools.ImageRecord;

@SuppressWarnings("serial")
public class StegTableModel extends AbstractTableModel {
	
	private String[] columns = {"Filename", "Steganalysis Method", "Result"};
	private LinkedList<ImageRecord> items = new LinkedList<ImageRecord>();
	
	/**
	 * Get the number of columns in the table.
	 */
	@Override
	public int getColumnCount() {
		return columns.length;
	}
	
	/**
	 * For continuity, should only be used for read-only tasks.
	 * @return The contents of the queue.
	 */
	public LinkedList<ImageRecord> getResults()
	{
		return items;
	}
	
	/**
	 * Get the name of a column.
	 */
	@Override
	public String getColumnName(int col) {
		  return columns[col];
		}

	/**
	 * Get the number of rows in the table/items in the queue.
	 */
	@Override
	public int getRowCount() {
		return items.size();
	}
	
	/**
	 * Add an item to the queue.
	 * @param i The item that is to be added.
	 */
	public void addQueueItem(ImageRecord i) {
		items.add(i);
		fireTableDataChanged();
	}
	
	/**
	 * Remove all items from the queue.
	 */
	public void clearQueue()
	{
		items.clear();
		fireTableDataChanged();
	}
	
	/**
	 * Remove a single item from the queue.
	 * @param i The index of the item that should be removed.
	 */
	public void removeQueueItem(int i)
	{
		items.remove(i);
	}

	/**
	 * Get the contents of the selected cell.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ImageRecord item = items.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return item.getImagePath();
		case 1:
			return item.getStegMethod().toString();
		case 2:
			try {
				return ""+item.getResult();
			} catch (ImageNotTestedException e) {
				return "";
			}
		default:
			return null;
		}
	}

	/**
	 * Remove all items from the table that have been selected in the user interface.
	 * @param table The table that the items were selected in.
	 */
	public void removeSelected(JTable table) {
		int numSelectedRows = table.getSelectedRows().length;
		for (int i = 0; i < numSelectedRows; i++)
		{
			removeQueueItem(table.getSelectedRow());
		}
		fireTableDataChanged();
		
	}

}
