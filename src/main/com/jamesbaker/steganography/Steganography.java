/**
 * Snippets used:
 * 	Reading binary files into byte arrays: http://goo.gl/Fbxvj
 */
package com.jamesbaker.steganography;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.aqa.Console;

/**
 * @author James Baker
 *
 */
public class Steganography {
	int imageDataOffset = 0; // The offset at which the image data array starts.
	byte[] fileData; // File data, including the assorted headers
	boolean[] usedBytes; // Used to determine which bytes of the image data have data stored in them.
	Console console = new Console();
	final int BMP_OFFSET_LOCATION = 0xA;
	final int BYTESIZE = 8;
	final byte LSB_BITMASK = (byte) 0xFE;
	int byteCounter = 0;
	int dataLength = 0;
	int bitsEncoded=0;
	Random randomOffsets;
	Random randomIncrement = new Random(); // Used for bit matching.
	double perc = 0;
	

	/**
	 * Just starts the application. Boilerplate code.
	 */
	public static void main(String[] args) {
		Steganography steg = new Steganography();
		steg.encodeMessageCLI();
	}
	
	/**
	 * Provides a command line interface to the process of
	 * encoding text in images (Steganography).
	 */
	public void encodeMessageCLI()
	{
		boolean isRunning = true;
		do
		{
			console.println(" encode [f]ile");
			console.println(" encode [w]hitenoise)");
			console.println(" encode [s]tring");
			console.println(" de[c]ode file");
			console.println("[d]ecode string");
			console.println("[q]uit");
			
			char option = console.readChar(">");
			byte[] message;
			/* Switch case revision */
			switch (option)
			{
			case 'f':
				loadFile(console.readLine("Please enter the path to the image file you want encoded: "));
				message = loadFileToByteArray(console.readLine("Please enter the path to the file you want encoded: "));
				encodeByteArray(message, console.readInteger("Please enter the integer key you want the message to be distributed with: "), 100);
				writeFile(console.readLine("Please enter the path to the newly encoded file: "));
				isRunning=false;
				break;
			case 'w':
				loadFile(console.readLine("Please enter the path to the image file you want encoded: "));
				message = loadFileToByteArray("geysir.7z");
				encodeByteArray(message, console.readInteger("Please enter the integer key you want the message to be distributed with: "), console.readInteger("What percentage of the file would you like data to be embedded in? "));
				writeFile(console.readLine("Please enter the path to the newly encoded file: "));
				isRunning=false;
				break;
			case 's':
				loadFile(console.readLine("Please enter the path to the image file you want encoded: "));
				encodeMessage(console.readLine("Please enter the path to the text you want encoded: "), console.readInteger("Please enter the integer key you want the message to be encoded with: "));
				writeFile(console.readLine("Please enter the path to the newly encoded file: "));
				isRunning=false;
				break;
			case 'd':
				loadFile(console.readLine("Please enter the path to the image file you want decoded: "));
				console.println("The decoded message is: "+decodeImage(console.readInteger("Please enter the integer key you want the message to be decoded with: ")));
				break;
			case 'c':
				loadFile(console.readLine("Please enter the path to the image file you want decoded: "));
				writeByteArrayToFile(console.readLine("Please enter the path to the newly decoded file: "), decodeByteArray(console.readInteger("Please enter the integer key you want the message to be decoded with: ")));
				break;
			case 'q':
				isRunning = false;
				break;
			case 't':
				loadFile("kosmo_cropped.bmp");
				encodeByteArray(loadFileToByteArray("geysir.7z"), 1337, 100);
				writeFile("test_output.bmp");
				isRunning=false;
				break;
			default:
				break;
			}
		} while (isRunning);		
	}

	/**
	 * Writes a byte array to the hard disk.
	 */
	private void writeByteArrayToFile(String fileName, byte[] data) {
        FileOutputStream stream;
        BufferedOutputStream buffered;
		try {
			stream = new FileOutputStream(fileName);
			buffered = new BufferedOutputStream(stream);
			buffered.write(data);
			buffered.close();
	        stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the contents of fileData to a file on the hard disk.
	 */
	public void writeFile(String fileName)
	{
		writeByteArrayToFile(fileName, fileData);
	}
    
	/**
	 * Changes the last bit of a byte according to parameter bit.
	 */
    public byte changeLastBit(int bit, byte i)
	{
    	int j = i;
		j = j & 0xFE;
		j = j + bit;
		return (byte) j;
	}
	
    /**
     * Spreads the character across 8 bytes, using the least significant bit of each byte to store the data.
     */
	public void encodeByte(byte c)
	{
		/*for (int i = 0; i< BYTESIZE; i++)
		{
			int randomOffset = imageDataOffset+getNextImageOffset();
			if ((fileData[randomOffset] & ((byte) 0x1)) != (c >> (BYTESIZE-1-i)))
			{
				if ((fileData[randomOffset] == 255 || fileData[randomOffset]==0))
				{
					
				}
				if (randomIncrement.nextBoolean())
				{
					fileData[randomOffset]++;
				}
				else
				{
					fileData[randomOffset]--;
				}
			}
			
		}*/
		/*for (int i = 0; i < BYTESIZE; i++)
		{
			int randomOffset = imageDataOffset + getNextImageOffset();
			if ((fileData[randomOffset] & ((byte) 0x1)) != (c >> (BYTESIZE-1-i)))
			{
				if ((fileData[randomOffset] != 255 || fileData[randomOffset]!=0))
				{
					fileData[randomOffset] = changeLastBit(((c >> (BYTESIZE-1-i)) & 0x1), fileData[randomOffset]);	
				}
			}
		}*/
		
		for (int i = 0; i < BYTESIZE; i++)
		{
			int randomOffset = imageDataOffset + getNextImageLinearOffset();
			if ((fileData[randomOffset] & ((byte) 0x1)) != (c >> (BYTESIZE-1-i)))
			{
				if ((fileData[randomOffset] != 255 || fileData[randomOffset]!=0))
				{
					fileData[randomOffset] = changeLastBit(((c >> (BYTESIZE-1-i)) & 0x1), fileData[randomOffset]);	
				}
			}
			
			/*if ((fileData[randomOffset] & ((byte) 0x1)) != (c >> (BYTESIZE-1-i)))
			{
				if ((fileData[randomOffset] == 255 || fileData[randomOffset]==0))
				{
					
				}
				if (randomIncrement.nextBoolean())
				{
					fileData[randomOffset]++;
				}
				else
				{
					fileData[randomOffset]--;
				}
			}*/
		}
			
	}
	
	/**
	 * Encodes the byte array into the imageData.
	 */
	public void encodeByteArray(byte[] array, int seed, double percentage) {
		perc = percentage;
		dataLength=array.length;
		randomOffsets = new Random(seed);
		int imageDataSize = fileData.length-imageDataOffset;
		if (array.length > (imageDataSize/8))
		{
			//throw new Error("File too large.");
		}
		setHiddenDataLength(array.length);
		for (int i = 0; i<(imageDataSize*percentage)/8 -10/*array.length*/; i++)
		{
			logPercentage(i, imageDataSize/8);
			encodeByte(array[i]);
		}
	}
	
	public int getNextImageLinearOffset()
	{
		int size = (fileData.length - imageDataOffset)/8;
		int dataSize = (int) (perc*size);
		int dataSplit = size/dataSize;
		int offset = dataSplit*bitsEncoded;
		bitsEncoded++;
		offset+=randomOffsets.nextInt(dataSplit);
		
		
		return offset;
	}
	
	/**
	 * Assembles a bit from the LSBs of 8 bytes, as stored in fileData.
	 * @param offset
	 * @return
	 */
	public char decodeChar()
	{
		return (char) decodeByte();
	}
	
	/**
	 * Decodes the hidden data into a byte array.
	 * @param seed
	 * @return
	 */
	private byte[] decodeByteArray(int seed) {
		randomOffsets = new Random(seed);
		int fileSize = getHiddenDataLength();
		byte[] array = new byte[fileSize];
		for (int i = 0; i < fileSize; i++)
		{
			array[i] = decodeByte();
		}
		return array;
	}
	
	/**
	 * The next byte in the sequence is decoded.
	 * @return
	 */
	private byte decodeByte() {
		byte b = 0;
		for (int i = 0; i<BYTESIZE; i++)
		{
			b = (byte) (b | ( (fileData[imageDataOffset+getNextImageOffset()] & 0x1) << (BYTESIZE-1-i)));
		}
		return b;
	}

	/**
	 * Decodes the embedded image data into a human-readable format.
	 * @return
	 */
	private String decodeImage(int seed) {
		randomOffsets = new Random(seed);
		String s = "";
		for (int i = 0; i < 50 /*usedBytes.length/BYTESIZE A reasonable character limit - we don't want this running all day until I embed the message length in the first few bytes.*/; i++)
		{
			s = s+decodeChar();
		}
		return s;
	}
	
	/**
	 * Encodes a string into the imageData.
	 */
	public void encodeMessage(String message, int seed) {
		randomOffsets = new Random(seed);
		
		for (char c : message.toCharArray())
		{
			encodeByte((byte) c);
		}
		
	}
	

	
	/** 
	 * Logs the progress based on the number of samples done / numble of samples remaining.
	 * @param done
	 * @param length
	 */
	public void logPercentage(int done, int length)
	{
			double j = ((double) done/(double) length)*100;
			//console.println(j+"%");
	}
	
	/**
	 * Gets the next image offset in the file.
	 * A random number is generated with the maximum size being the size of the image data.
	 * If the random number has been used, repeat till it is no longer in use.
	 * This can lead to a significant slowdown if the file is close to saturated with data
	 * - but speed is still good.
	 */
	private int getNextImageOffset()
	{
		if (byteCounter > usedBytes.length)
		{
			throw new Error("Out of room!");
		}
		boolean goodValue = false;
		int i;
		do
		{
			goodValue = false;
			i = randomOffsets.nextInt(fileData.length - imageDataOffset);
			if (usedBytes[i] == false)
			{
				usedBytes[i] = true;
				goodValue = true;
			}
		} while (!goodValue);
		return i;
	}

	/**
	 * Loads a binary file into a byte array.
	 * @param fileName
	 */
	public byte[] loadFileToByteArray(String fileName)
	{
		byte[] fileData;
	    try {
	    	File file = new File(fileName);
	    	InputStream is = new FileInputStream(file);
	    	long length = file.length();
	    	fileData = new byte[(int)length];
	    	
	    	int offset = 0;
	    	int numRead = 0;
	    	while (offset < fileData.length && (numRead =is.read(fileData, offset, fileData.length-offset)) >= 0)
	    	{
	    		offset += numRead;
	    	}
	    	is.close();
	      }
	    catch (IOException ex){
	    	throw new Error(ex);
	    }
	    
	    return fileData;
	   
	}
	
	/**
	 * Loads a file object into the byte array fileData, ready for manipulation.
	 */
	public void loadFile(String fileName)
	{
		fileData = loadFileToByteArray(fileName);
	    imageDataOffset = fourBytesToInt(fileData[BMP_OFFSET_LOCATION], fileData[BMP_OFFSET_LOCATION+1], fileData[BMP_OFFSET_LOCATION+2], fileData[BMP_OFFSET_LOCATION+3]);
	    usedBytes = new boolean[fileData.length-imageDataOffset];
	}
	
	/**
	 * Converts four supplied bytes into an int.
	 * Uses little-endian structure (ie least significant byte first).
	 */
	private int fourBytesToInt(byte a, byte b, byte c, byte d)
	{
		int i = 0;
		i += a;
		i += b << 8;
		i += c << 16;
		i += d << 24;
		return i;
	}
	
	/**
	 * Sets the next four bytes in fileData to the int length.
	 */
	private void setHiddenDataLength(int length)
	{
		encodeByte((byte) (length & 0xFF));
		encodeByte((byte) ((length & (0xFF << 8)) >> 8));
		encodeByte((byte) ((length & (0xFF << 16)) >> 16));
		encodeByte((byte) ((length & (0xFF << 24)) >> 24));
	}
	
	/**
	 * Finds the length of the encoded data that is to be decoded.
	 */
	private int getHiddenDataLength()
	{
		int i = fourBytesToInt(decodeByte(), decodeByte(), decodeByte(), decodeByte());
		return i;
	}

}