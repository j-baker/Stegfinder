package com.bakes.aqacomp4.exporter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.aqa.AQAWriteTextFile;
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
	public void export(LinkedList<ImageRecord> image, String fileName) {
		String exportData = "";
		for (ImageRecord i : image)
		{
			try {
				String line = SEPERATOR_ENDLINE+i.getImagePath()+SEPERATOR+i.getStegMethod().toString()+SEPERATOR+i.getResult()+SEPERATOR_ENDLINE;
				exportData += line + NEWLINE;
			} catch (ImageNotTestedException e) {
			}
		}
		stringToFile(exportData, fileName);
	}
	
	private void stringToFile(String s, String path)
	{
		try {
			PrintWriter out = new PrintWriter(path, "UTF-8");
			out.print(s);
			out.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The output path provided is not valid.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
