package com.bakes.aqacomp4.gui;

import javax.swing.SwingWorker;

import com.bakes.aqacomp4.imagetools.ImageQueueItem;

public class ProcessImageQueue extends SwingWorker<Integer, Integer> {
	ApplicationWindow application;
	StegTableModel tableModel;

	public ProcessImageQueue(ApplicationWindow window)
	{
		this.application = window;
		this.tableModel = window.getTable();
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		int length = tableModel.getResults().size();
		int i = 0;
		for (ImageQueueItem item : tableModel.getResults())
		{
			if (item.runMethod())
			{
				this.tableModel.fireTableDataChanged();
			}
			i+=100;
			application.setProgress(i/length);
		}

		return null;
	}

}
