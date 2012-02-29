package com.bakes.aqacomp4.gui;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import com.bakes.aqacomp4.imagetools.ImageNotTestedException;
import com.bakes.aqacomp4.imagetools.ImageQueueItem;

public class StegTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = -3445152052420230606L;
	
	private String[] columns = {"Filename", "Image Type", "Steganalysis Method", "Result"};
	LinkedList<ImageQueueItem> items = new LinkedList<ImageQueueItem>();
	
	@Override
	public int getColumnCount() {
		return columns.length;
	}
	
	@Override
	public String getColumnName(int col) {
		  return columns[col];
		}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return items.size();
	}
	
	public void addQueueItem(ImageQueueItem i) {
		items.add(i);
		fireTableDataChanged();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ImageQueueItem item = items.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return item.getImagePath();
		case 1:
			return item.getImageType().toString();
		case 2:
			return item.getStegMethod().toString();
		case 3:
			try {
				return ""+item.getResult();
			} catch (ImageNotTestedException e) {
				return "";
			}
		default:
			return null;
		}
	}

}
