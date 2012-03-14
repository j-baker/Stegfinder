package com.bakes.aqacomp4.exporter;

import java.util.LinkedList;

import com.aqa.AQAWriteTextFile;
import com.bakes.aqacomp4.gui.StegTableModel;
import com.bakes.aqacomp4.imagetools.ImageNotTestedException;
import com.bakes.aqacomp4.imagetools.ImageQueueItem;

public class CSVExporter {
	private static final String SEPERATOR = "\",\"";
	private static final String SEPERATOR_ENDLINE = "\"";
	private static final String NEWLINE = "\n";
	
	
	public void export(StegTableModel table, String fileName) {
		String exportData = "";
		LinkedList<ImageQueueItem> image = table.getResults();
		for (ImageQueueItem i : image)
		{
			try {
				String line = SEPERATOR_ENDLINE+i.getImagePath()+SEPERATOR+i.getStegMethod().toString()+SEPERATOR+i.getResult()+SEPERATOR_ENDLINE;
				exportData += line + NEWLINE;
			} catch (ImageNotTestedException e) {
			}
		}
		writeFile(fileName, exportData);
	}

	private void writeFile(String fileName, String exportData) {
		AQAWriteTextFile writer = new AQAWriteTextFile(fileName);
		writer.writeToTextFile(exportData);
		writer.closeFile();		
	}

}
