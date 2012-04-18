package com.bakes.aqacomp4.exporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.aqa.AQAWriteTextFile;
import com.bakes.aqacomp4.imagetools.ImageNotTestedException;
import com.bakes.aqacomp4.imagetools.ImageRecord;

public class PDFExporter {
	static final String TEMPFOLDER = "temp";
	static final String LATEXBIN = "latex\\miktex\\bin";

	static final String NEWLINE = System.getProperty("line.separator");
	public void export(LinkedList<ImageRecord> table, String outputPath) {
		LinkedList<String> fileNames = new LinkedList<String>();
		LinkedList<ImageRecord> stego = new LinkedList<ImageRecord>();
		int numScanned = 0;
		int numTests = 0;
		int numSteganography = 0;
		for (ImageRecord r : table)
		{
			if (!fileNames.contains(r.getImagePath()))
			{
				fileNames.add(r.getImagePath());
				numScanned++;
			}
			numTests++;
			try {
				if (r.getResult() >= 0.5)
				{
					numSteganography++;
					stego.add(r);
				}
			} catch (ImageNotTestedException e) {
			}
		}

		
		String s = "";
		s += "\\documentclass[a4paper, 12pt]{article}";
		s += NEWLINE + "\\newlength{\\mywidth}";
		s += NEWLINE + "\\setlength{\\mywidth}{5cm}";
		s += NEWLINE + "\\usepackage{datetime}";
		s += NEWLINE + "\\usepackage{graphicx}";
		s += NEWLINE + "\\usepackage{url}";
		s += NEWLINE + "\\usepackage{booktabs}";
		s += NEWLINE + "\\usepackage{float}";
		s += NEWLINE + "\\usepackage{longtable}";
		s += NEWLINE + "\\makeatletter";
		s += NEWLINE + "\\def\\ScaleIfNeeded{";
		s += NEWLINE + "\\ifdim\\Gin@nat@width>\\mywidth";
		s += NEWLINE + "\\mywidth";
		s += NEWLINE + "\\else";
		s += NEWLINE + "\\Gin@nat@width";
		s += NEWLINE + "\\fi";
		s += NEWLINE + "}";
		s += NEWLINE + "\\makeatother";
		s += NEWLINE + "\\begin{document}";
		s += NEWLINE + "\\section{Results}";
		s += NEWLINE + "StegFinder Report generated on {\\today} at {\\xxivtime}.\\\\";
		s += NEWLINE + "As part of the testing process, "+ numScanned + " files were scanned, in " + numTests + " individual tests. The full list of files can be found in the accompanying table. Steganography was found in " + numSteganography + " of the files.\\\\";
		if (stego.size() == 0)
		{
			s += "\\end{document}";
		}
		else
		{
			s += NEWLINE +	"The following is a report of the files that were found to likely contain steganography.";
			s += NEWLINE + NEWLINE + "\\begin{center}";
			s += NEWLINE + "\\begin{longtable}{p{4cm}p{7cm}l}";
			s += NEWLINE + "\\toprule";
			s += NEWLINE + "File & Path & Method \\\\";
			s += NEWLINE + "\\midrule";
			s += NEWLINE + "\\endhead";
			String lastFileName = "";
			for (ImageRecord r : stego)
			{

				String path = r.getImagePath();
				String fileName = path.substring(path.lastIndexOf('\\')+1,path.length());
				String justPath = path.substring(0, path.lastIndexOf('\\'));
				if (!lastFileName.equals(path))
				{
					s += NEWLINE + "{\\tt \\path{" + fileName+"}} & {\\tt \\path{"+(justPath)+"}} & "+r.getStegMethod().toString()+"\\\\";
				}
				else
				{
					s += NEWLINE + "& &" + r.getStegMethod().toString() + "\\\\";
				}
				lastFileName = path;
			}
			// RESULTS
			s += NEWLINE + "\\bottomrule";
			s += NEWLINE + "\\end{longtable}";
			s += NEWLINE + "\\end{center}";
			s += NEWLINE + NEWLINE + "It should be noted that no steganalytical process can be perfect. The above results are merely best guesses. For this reason, it may be useful to consider how many pictures have been found to contain steganography out of \\emph{the total number of images scanned}.";
			s += NEWLINE + "\\section{Explanations of Steganalytical Methods}";
			s += NEWLINE + "Steganalysis was detected using the following methods.";
			s += NEWLINE + "\\subsection{SPAM}";
			s += NEWLINE + "Images are made up of pixels, arranged in a grid. Now, it is entirely reasonable that we might expect white pixels to be located near pixels that are a light grey colour. SPAM steganalysis works by assuming that this is the case across all images. The process of steganography will often change these statistics (meaning that white pixels might be more or less likely to be located near pixels that are a light grey colour), which SPAM uses to differentiate between images that have embedded steganography and images that do not.";
			s += NEWLINE + "\\subsection{RS}";
			s += NEWLINE + "RS steganalysis works by measuring an image's `noise'. If you look at a photograph taken in a dark room, you may see speckles of light colours in dark patches. This is referred to as noise. Least significant bit replacement steganography increases the noise in an image on a minute scale. RS steganalysis measures this increase, and uses it to determine whether a given image contains embedded data.";
			s += NEWLINE + "\\subsection{Chi-Square}";
			s += NEWLINE + "Whilst we view images as blocks of colour, computers view them as numbers. In general, simply knowing the number of pixels in an image with value 220 does not allow us to accurately estimate the number of pixels in an image with value 221. If Least Signficant Bit Replacement steganography has taken place, we can sometimes make an accurate estimate. The Chi-Square test makes a large number of estimates about the numbers of specific pixels with specific properties, and sees how well they fit the actual image. If the fit is good, Steganography has almost certainly taken place.";
			s += NEWLINE + "\\end{document}";
		}

		makeTempDirectory();
		
		stringToFile(s, TEMPFOLDER+"\\document.tex");
		String path = TEMPFOLDER+"\\document";
		
		latexProcess("document");
		
		File latex = new File(path+".pdf");
		File output = new File(outputPath);
		try {
			FileUtils.copyFile(latex, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//deleteTempDirectory();
		
		
	}
	
	private void stringToFile(String s, String path)
	{
		try {
			PrintWriter out = new PrintWriter(path, "UTF-8");
			out.print(s);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void latexProcess(String path)
	{
		Runtime runtime = Runtime.getRuntime();
		try {
			//runtime.exec("/usr/texbin/pdflatex -output-directory "+(new File(".").getAbsolutePath())+"/"+TEMPFOLDER+" "+path).waitFor();
			//JOptionPane.showMessageDialog(null, "\""+System.getProperty("user.dir")+"\\"+LATEXBIN+"\\pdflatex.exe\" -output-directory "+"\""+System.getProperty("user.dir")+"\\"+TEMPFOLDER+"\" "+path);

			String line;
			Process p = runtime.exec("\""+System.getProperty("user.dir")+"\\"+LATEXBIN+"\\pdflatex.exe\" -output-directory "+"\""+System.getProperty("user.dir")+"\\"+TEMPFOLDER+"\" "+path);
		     BufferedReader bri = new BufferedReader
		    	        (new InputStreamReader(p.getInputStream()));
		    	      BufferedReader bre = new BufferedReader
		    	        (new InputStreamReader(p.getErrorStream()));
		    	      while ((line = bri.readLine()) != null) {
		    	        //JOptionPane.showMessageDialog(null, line);
		    	    	  System.out.println(line);
		    	      }
		    	      bri.close();
		    	      while ((line = bre.readLine()) != null) {
		    	        //JOptionPane.showMessageDialog(null, line);
		    	    	  System.out.println(line);
		    	      }
		    	      bre.close();
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void makeTempDirectory()
	{
		new File(TEMPFOLDER).mkdir();
	}
	
	private void deleteTempDirectory()
	{
		File d = new File(TEMPFOLDER);
		deleteDirectory(d);
	}
	
	public static boolean deleteDirectory(File dir) {
	    if (dir.isDirectory()) {
	        String[] contents = dir.list();
	        for (int i = 0; i < contents.length; i++) {
	            boolean success = deleteDirectory(new File(dir, contents[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    return dir.delete();
	}

}
