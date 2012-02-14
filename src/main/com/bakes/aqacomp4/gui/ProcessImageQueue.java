package com.bakes.aqacomp4.gui;
import javax.swing.SwingWorker;

import com.bakes.aqacomp4.imagetools.ImageQueueItem;

public class ProcessImageQueue extends SwingWorker<Integer, Integer> {
	StegTableModel table;

	public ProcessImageQueue(StegTableModel s)
	{
		this.table = s;
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub
		for (ImageQueueItem item : table.q)
		{
			item.runMethod();
		}
		return null;
	}

}
