package com.aqa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/* Class for use in AQA COMP1 exams for students using Java.
 *
 * class Console will be made available for students to use,
 * although they are not restricted to just using it for i/o to the console.
 * If they know how to use java.io.Console that is fine.
 * Also, System.out.println can be used for outputs.
 * This class should save time in the exam, especially if they
 * have been used by students beforehand.
 */
public class Console {

    public Console() {
    }// end of constructor CommandLineInput
    //returns the first character of a line typed in.

    public char readChar() {
        return readChar("");
    } // end method readChar

    public char readChar(String prompt) {
        return readLine(prompt).charAt(0);
    } // end method readChar

    public byte readByte(String prompt) {
        try {
            return Byte.parseByte(readLine(prompt).substring(0, 1));
        } catch (NumberFormatException nfe) {
            println(nfe.toString() + "Parsing a Byte");
        } catch (Exception e) {
            println("another exception" + e.toString());
        }
        return -1;
    } // end method readByte

    public byte readByte() {
        return readByte("");
    } // end method readByte

    public String readLine() {
        return readLine("");
    } // end method readLine with no parameter

    public String readLine(String prompt) {
        String input = "";
        System.out.print(prompt);
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        try {
            input = br.readLine();
        } catch (IOException ioe) {
            println("IO Error reading from command line.");
            System.exit(1);
        }
        return input;
    } // end method readLine

    public int readInteger(String prompt) {
        try {
            return (int) Integer.parseInt(readLine(prompt));
        } catch (NumberFormatException nfe) {
            System.out.println(" Number format excpetion.");
            System.out.println(nfe.toString());
        } // end try/catch
        return -1;
    } // end method readInteger

    public void print(Object o) {
        String output = String.valueOf(o);
        System.out.print(output);
    } // end method print

    public void println() {
        println("");
    } // end method println with no parameter

    public void println(Object o) {
        String output = String.valueOf(o);
        System.out.println(output);
    } // end method println

    public void write(Object o) {
        print(o);
    } // end method write

    public void writeLine() {
        println();
    } // end method writeLine with no parameter

    public void writeLine(Object o) {
        println(o);
    } // end method writeLine

    public void printf(String format, Object args) {
        System.out.printf(format, args);
    } // end method printf
} // end class Console