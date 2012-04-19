package com.bakes.aqacomp4.exporter;

import java.util.LinkedList;

import com.bakes.aqacomp4.imagetools.ImageRecord;

/**
 * Exports the table data. Depending on provided options, will export provided data to csv or pdf formats.
 * @author bakes
 *
 */

public class Export {
	private String fileName;
	private boolean csv;
	private boolean pdf;
	
	/**
	 * Initialize a new export object, ready to export to file.
	 * @param table The table the data is to be taken from.
	 * @param fileName The base filename. Should be without extension, though extensions should not be filtered.
	 * @param csv Output CSV file
	 * @param pdf Output PDF file (Windows only).
	 */
	public Export(String fileName, boolean csv, boolean pdf)
	{
		this.fileName = fileName;
		this.csv = csv;
		this.pdf = pdf;
	}
	
	/**
	 * Exports the data using the information given above.
	 */
	public void exportToFiles(LinkedList<ImageRecord> tableData)
	{
		if (csv)
		{
			CSVExporter c = new CSVExporter();
			c.export(tableData, fileName+".csv");
		}
		if (pdf)
		{
			PDFExporter p = new PDFExporter();
			p.export(tableData, fileName+".pdf");
		}
	}
	
	/**
	 * To reduce unintentional bugs, this software is bundled with a distribution of LaTeX.
	 * This distribution supports only Windows. Thus, we can only export as pdf files if
	 * the system is running on Windows
	 * @return true if we are allowed to export as a PDF
	 */
	public static boolean PDFExportAllowed()
	{
		// We can only export as a PDF if we're using Windows.
		return isWindows();
	}
	
	private static boolean isWindows() {
		 
		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);
 
	}
	
}
