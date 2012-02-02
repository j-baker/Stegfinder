package com.bakes.aqacomp4;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class ApplicationWindow {
	private JFrame application;
	private JTextField sourcePath;
	private JTextField outputPath;
	
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationWindow window = new ApplicationWindow();
					window.application.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApplicationWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Make the application window.
		application = new JFrame();
		application.setResizable(false); // TODO Resizing disabled to make laying out easier. May investigate minimum sizes.
		application.setBounds(100, 100, 677, 390); // Place the application in its default position.
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // The application needs to exit when this is closed.
		application.setTitle("Stegfinder"); //TODO Redo with constant of some kind.
		
		//Initialize the 'Gridbaglayout' used to lay the components.
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		layout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		application.getContentPane().setLayout(layout);
		
		// The button that selects different input files.
		JButton inputSelector = new JButton("Source");
		GridBagConstraints inputSelectorConstraints = new GridBagConstraints();
		inputSelectorConstraints.insets = new Insets(5, 0, 5, 0);
		inputSelectorConstraints.gridx = 0;
		inputSelectorConstraints.gridy = 0;
		application.getContentPane().add(inputSelector, inputSelectorConstraints);
		
		// The text field that stores file paths.
		sourcePath = new JTextField();
		sourcePath.setEditable(false);
		GridBagConstraints sourcePathConstraints = new GridBagConstraints();
		sourcePathConstraints.insets = new Insets(5, 5, 5, 5);
		sourcePathConstraints.gridwidth = 5;
		sourcePathConstraints.fill = GridBagConstraints.HORIZONTAL;
		sourcePathConstraints.gridx = 1;
		sourcePathConstraints.gridy = 0;
		application.getContentPane().add(sourcePath, sourcePathConstraints);
		
		// TODO Method specific buttons. To be made procedural in a later stage.
		
		JCheckBox chckbxSpam = new JCheckBox("SPAM");
		GridBagConstraints gbc_chckbxSpam = new GridBagConstraints();
		gbc_chckbxSpam.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSpam.gridx = 0;
		gbc_chckbxSpam.gridy = 1;
		application.getContentPane().add(chckbxSpam, gbc_chckbxSpam);
		
		JButton btnSpamSettings = new JButton("SPAM Settings");
		GridBagConstraints gbc_btnSpamSettings = new GridBagConstraints();
		gbc_btnSpamSettings.insets = new Insets(0, 0, 5, 5);
		gbc_btnSpamSettings.gridx = 1;
		gbc_btnSpamSettings.gridy = 1;
		application.getContentPane().add(btnSpamSettings, gbc_btnSpamSettings);
		
		JCheckBox chckbxChiSquare = new JCheckBox("Chi Square");
		GridBagConstraints gbc_chckbxChiSquare = new GridBagConstraints();
		gbc_chckbxChiSquare.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxChiSquare.gridx = 2;
		gbc_chckbxChiSquare.gridy = 1;
		application.getContentPane().add(chckbxChiSquare, gbc_chckbxChiSquare);
		
		JCheckBox chckbxRs = new JCheckBox("RS");
		GridBagConstraints gbc_chckbxRs = new GridBagConstraints();
		gbc_chckbxRs.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxRs.gridx = 3;
		gbc_chckbxRs.gridy = 1;
		application.getContentPane().add(chckbxRs, gbc_chckbxRs);
		
		JCheckBox chckbxPairs = new JCheckBox("Pairs");
		GridBagConstraints gbc_chckbxPairs = new GridBagConstraints();
		gbc_chckbxPairs.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPairs.gridx = 4;
		gbc_chckbxPairs.gridy = 1;
		application.getContentPane().add(chckbxPairs, gbc_chckbxPairs);
		
		JButton queueAdd = new JButton("Add to Queue");
		GridBagConstraints queueAddConstraints = new GridBagConstraints();
		queueAddConstraints.insets = new Insets(0, 0, 5, 0);
		queueAddConstraints.gridx = 5;
		queueAddConstraints.gridy = 1;
		application.getContentPane().add(queueAdd, queueAddConstraints);
		
		JButton selectOutput = new JButton("Output");
		GridBagConstraints selectOutputConstraints = new GridBagConstraints();
		selectOutputConstraints.insets = new Insets(0, 0, 5, 5);
		selectOutputConstraints.gridx = 0;
		selectOutputConstraints.gridy = 2;
		application.getContentPane().add(selectOutput, selectOutputConstraints);
		
		outputPath = new JTextField();
		outputPath.setEditable(false);
		outputPath.setColumns(10);
		GridBagConstraints outputPathConstraints = new GridBagConstraints();
		outputPathConstraints.gridwidth = 2;
		outputPathConstraints.insets = new Insets(0, 0, 5, 5);
		outputPathConstraints.fill = GridBagConstraints.HORIZONTAL;
		outputPathConstraints.gridx = 1;
		outputPathConstraints.gridy = 2;
		application.getContentPane().add(outputPath, outputPathConstraints);
		
		JCheckBox csvReportRequired = new JCheckBox("CSV Report");
		GridBagConstraints csvReportConstraints = new GridBagConstraints();
		csvReportConstraints.insets = new Insets(0, 0, 5, 5);
		csvReportConstraints.gridx = 3;
		csvReportConstraints.gridy = 2;
		application.getContentPane().add(csvReportRequired, csvReportConstraints);
		
		JCheckBox pdfReportRequired = new JCheckBox("PDF Report");
		GridBagConstraints pdfReportConstraints = new GridBagConstraints();
		pdfReportConstraints.insets = new Insets(0, 0, 5, 5);
		pdfReportConstraints.gridx = 4;
		pdfReportConstraints.gridy = 2;
		application.getContentPane().add(pdfReportRequired, pdfReportConstraints);
		
		JButton startStop = new JButton("Start");
		GridBagConstraints startStopConstraints = new GridBagConstraints();
		startStopConstraints.insets = new Insets(0, 0, 5, 0);
		startStopConstraints.gridx = 5;
		startStopConstraints.gridy = 2;
		application.getContentPane().add(startStop, startStopConstraints);
		
		// TODO The scrollpane is necessary for the table - so that there can be an 'unlimited' number of items in the queue.
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints scrollPaneConstraints = new GridBagConstraints();
		scrollPaneConstraints.insets = new Insets(0, 5, 5, 5);
		scrollPaneConstraints.gridheight = 8;
		scrollPaneConstraints.gridwidth = 6;
		scrollPaneConstraints.fill = GridBagConstraints.BOTH;
		scrollPaneConstraints.gridx = 0;
		scrollPaneConstraints.gridy = 3;
		application.getContentPane().add(scrollPane, scrollPaneConstraints);
		
	    TableModel dataModel = new AbstractTableModel() {
	          public int getColumnCount() { return 10; }
	          public int getRowCount() { return 40;}
	          public Object getValueAt(int row, int col) { return new Integer(row*col); }
	      };
	   
	    // Add the table to the scrollpane.
	      
	    table = new JTable(dataModel);
		scrollPane.setViewportView(table);
	}

}
