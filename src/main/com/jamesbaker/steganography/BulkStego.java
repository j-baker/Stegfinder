package com.jamesbaker.steganography;

import com.aqa.Console;

public class BulkStego {
	Console console = new Console();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BulkStego b = new BulkStego();
		b.run("res/bows2/training/", 0, 0.05);
		b.run("res/bows2/testing/", 1, 0.05);
		//b.runNew();
		// TODO Auto-generated method stub

	}
	
	private void runNew() {
		String prefix = "res/kosmo";
		String suffix = ".bmp";
		String splitter = "_";
		int interval = 5;
		for (int i = 5; i <= 100; i+=interval)
		{
			String fileName = prefix + splitter + i + suffix;
			Steganography s = new Steganography();
			s.loadFile(prefix+suffix);
			byte[] message = s.loadFileToByteArray("res/stego");
			s.encodeByteArray(message, i+100, i*0.01);
			s.writeFile(fileName);
			System.out.println(i);
		}
	}

	private void run(String prefix, int offset, double content) {
		final String suffix = ".bmp";

		for (int i = 0; i < 10000; i++)
		{
			String fileName = prefix+i+suffix;
			double perc =0;
			if (i % 2 == offset)
			{
				perc = content;
			}
			else
			{
				perc = 0;
			}
			if (perc != 0)
			{
				Steganography s = new Steganography();
				s.loadFile(fileName);
				byte[] message = s.loadFileToByteArray("res/stego");
				s.encodeByteArray(message, i, perc);
				s.writeFile(fileName);
				System.out.println(i);
			}
		}
		
		// TODO Auto-generated method stub
		
	}
}
