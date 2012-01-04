package com.aqa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for use in AQA COMP1 exams for students using Java.
 *
 * class AQAWriteTextFile will be made available for students to use from
 * summer 2011, although they are not restricted just to using it for
 * writing text to files.
 *
 * There are 3 constructors
 * 1. no parameters - creates an instance of AQAWriteTextFile
 *
 * 2. one parameter - filename, this will open the file for writing
 *                              to the beginning of the file.
 * 3. two parameters - filename and
 *                     a boolean  false - write to the beginning
 *                                true - append to end of file.
 *
 * There are two openFile methods, which match 2 and 3 above.
 *
 * There are two writeToTextFile methods, which write a string to the
 * open file.
 * 1. one parameter - the string to be written. The system line separator
 *                    is automatically added to it before writing.
 * 2. two parameters - the string and another
 *                     string as a line separator.
 *
 * There is a closeFile method.
 *
 */
public class AQAWriteTextFile {

  //The global variable link to the open file
  private BufferedWriter outputFile;

  /**
   * Empty constructor. No file is opened.
   * Attempting a write before opening the file will cause an error
   */
  AQAWriteTextFile() {
  } // end constructor AQAWriteTextFile

  /**
   * Constructor - filename is opened to write at the start of the file
   * @param filename  name of file to be opened
   */
  public AQAWriteTextFile(String filename) {

    openFile(filename, false);

  } // end constructor AQAWriteTextFile

  /**
   * Constructor - filename is opened to write at the start of the file
   * @param filename  name of file
   * @param append    false write to start of file - true append to file
   */
  AQAWriteTextFile(String filename, boolean append) {

    openFile(filename, append);

  } // end constructor AQAWriteTextFile

  /**
   * Open file for writing from the start of the file
   * @param filename name of file to be written to
   */
  public void openFile(String filename) {

    openFile(filename, false);

  } // end openFileString

  /**
   * Open filename
   * @param filename  name of file to be written to
   * @param append    false, write to start of file - true, append to file
   */
  public void openFile(String filename, boolean append) {
    try {

      outputFile = new BufferedWriter(new FileWriter(filename, append));

    } catch (IOException e) {
      e.printStackTrace();
    } // end try/catch open file
  } // end openFileString

  /**
   * Write content to output file from where current pointer is
   * each line separated with the system line separator
   * @param content the string to be written to the file
   */
  public void writeToTextFile(String content) {
    writeToTextFile(content, System.getProperty("line.separator"));
  } // end openTextFile

  /**
   * Write content to output file from where current pointer is
   * a line separator is added to each line to the file
   * @param content the string to be written
   * @param lineSeparator a line separator
   */
  public void writeToTextFile(String content, String lineSeparator) {
    try {

      outputFile.write(content + lineSeparator);

    } catch (IOException e) {
      e.printStackTrace();
      closeFile();
    } // end writeToTextFile
  } // end openTextFile

  /**
   * closes the output file
   */
  public void closeFile() {
    try {

      if (outputFile != null) {
        outputFile.flush();
        outputFile.close();
      } // end if

    } catch (IOException ioe) {
      ioe.printStackTrace();
    } // end catch close exception
  } // end closeFile
} // end AQAWriteTextFile