package com.bakes.aqacomp4.gui;

import javax.swing.SwingWorker;

import com.bakes.aqacomp4.imagetools.ImageRecord;

/**
 * Processes the image queue. The processing code is placed in this class
 * so that the user interface does not lock up during processing.
 * @author bakes
 *
 */
public class ProcessImageQueue extends SwingWorker<Integer, Integer> {
	ApplicationWindow application;
	StegTableModel tableModel;

	public ProcessImageQueue(ApplicationWindow window)
	{
		this.application = window;
		this.tableModel = window.getTable();
	}
	
	/**
	 * Process the queue.
	 */
	@Override
	protected Integer doInBackground() throws Exception {
		application.setProgress(null);
		application.setStartStopText("Stop");
		int length = tableModel.getResults().size();
		int i = 0;
		for (ImageRecord item : tableModel.getResults())
		{
			if (!Thread.currentThread().isInterrupted())
			{
				if (item.runMethod())
				{
					this.tableModel.fireTableDataChanged();
				}
				i+=100;
				application.setProgress(i/length);				
			}
		}
		application.setProgress("Exporting Results");
		application.getExporter().exportToFiles(tableModel.getResults());
		application.setProgress("Done");
		application.setStartStopText("Start");

		return null;
	}

}
