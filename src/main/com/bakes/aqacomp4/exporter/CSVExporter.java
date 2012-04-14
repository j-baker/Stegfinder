package com.bakes.aqacomp4.exporter;

import java.util.LinkedList;

import com.aqa.AQAWriteTextFile;
import com.bakes.aqacomp4.gui.StegTableModel;
import com.bakes.aqacomp4.imagetools.ImageNotTestedException;
import com.bakes.aqacomp4.imagetools.ImageRecord;

/**
 * Exports the supplied data as a Comma Separated Values file.
 * Exported are the path to the image file, the method of steganalysis used, and the result.
 * @author bakes
 *
 */
public class CSVExporter {
	private static final String SEPERATOR = "\",\"";
	private static final String SEPERATOR_ENDLINE = "\"";
	private static final String NEWLINE = "\n";
	
	
	/**
	 * Exports the provided data to a CSV file. Only images that have been tested are processed.
	 * @param table The StegTableModel the data is to be taken from.
	 * @param fileName the path the CSV file is to be written to..
	 */
	public void export(StegTableModel table, String fileName) {
		String exportData = "";
		LinkedList<ImageRecord> image = table.getResults();
		for (ImageRecord i : image)
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
