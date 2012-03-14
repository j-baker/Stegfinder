package com.bakes.aqacomp4.exporter;

import com.bakes.aqacomp4.gui.StegTableModel;

public class Export {
	private StegTableModel table;
	private String fileName;
	private boolean csv;
	private boolean pdf;
	
	public Export(StegTableModel table, String fileName, boolean csv, boolean pdf)
	{
		this.table = table;
		this.fileName = fileName;
		this.csv = csv;
		this.pdf = pdf;
	}
	
	public void exportToFiles()
	{
		if (csv)
		{
			CSVExporter c = new CSVExporter();
			c.export(table, fileName+".csv");
		}
		if (pdf)
		{
			if (PDFExportAllowed())
			{
				PDFExporter p = new PDFExporter();
				p.export(table, fileName+".pdf");
			}
		}
	}
	
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
