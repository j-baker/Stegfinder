package com.bakes.aqacomp4.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.bakes.aqacomp4.imagetools.ImageQueueItem;
import com.bakes.aqacomp4.imagetools.ImageTypes;
import com.bakes.aqacomp4.stegmethods.StegMethods;

public class ApplicationWindow implements ActionListener {
	private JFrame application;
	private JTextField sourcePath;
	private JTextField outputPath;
	
	private StegTableModel tableModel;
	private JButton inputSelector;
	private JButton queueAdd;
	private JButton selectOutput;
	private JButton startStop;
	
	private JCheckBox rsCheckBox;
	private JCheckBox chiSquareCheckBox;

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
		java.awt.Image icon = Toolkit.getDefaultToolkit().getImage("Logo1");
		application.setIconImage(icon);
		
		//Initialize the 'Gridbaglayout' used to lay the components.
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		layout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		application.getContentPane().setLayout(layout);
		
		// The button that selects different input files.
		inputSelector = new JButton("Source");
		GridBagConstraints inputSelectorConstraints = new GridBagConstraints();
		inputSelectorConstraints.insets = new Insets(5, 0, 5, 0);
		inputSelectorConstraints.gridx = 0;
		inputSelectorConstraints.gridy = 0;
		inputSelector.addActionListener(this);
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
		
		chiSquareCheckBox = new JCheckBox("Chi Square");
		GridBagConstraints gbc_chckbxChiSquare = new GridBagConstraints();
		gbc_chckbxChiSquare.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxChiSquare.gridx = 2;
		gbc_chckbxChiSquare.gridy = 1;
		application.getContentPane().add(chiSquareCheckBox, gbc_chckbxChiSquare);
		
		rsCheckBox = new JCheckBox("RS");
		GridBagConstraints gbc_chckbxRs = new GridBagConstraints();
		gbc_chckbxRs.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxRs.gridx = 3;
		gbc_chckbxRs.gridy = 1;
		application.getContentPane().add(rsCheckBox, gbc_chckbxRs);
		
		JCheckBox chckbxPairs = new JCheckBox("Pairs");
		GridBagConstraints gbc_chckbxPairs = new GridBagConstraints();
		gbc_chckbxPairs.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPairs.gridx = 4;
		gbc_chckbxPairs.gridy = 1;
		application.getContentPane().add(chckbxPairs, gbc_chckbxPairs);
		
		queueAdd = new JButton("Add to Queue");
		GridBagConstraints queueAddConstraints = new GridBagConstraints();
		queueAddConstraints.insets = new Insets(0, 0, 5, 0);
		queueAddConstraints.gridx = 5;
		queueAddConstraints.gridy = 1;
		queueAdd.addActionListener(this);
		application.getContentPane().add(queueAdd, queueAddConstraints);
		
		selectOutput = new JButton("Output");
		GridBagConstraints selectOutputConstraints = new GridBagConstraints();
		selectOutputConstraints.insets = new Insets(0, 0, 5, 5);
		selectOutputConstraints.gridx = 0;
		selectOutputConstraints.gridy = 2;
		selectOutput.addActionListener(this);
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
		
		startStop = new JButton("Start");
		GridBagConstraints startStopConstraints = new GridBagConstraints();
		startStopConstraints.insets = new Insets(0, 0, 5, 0);
		startStopConstraints.gridx = 5;
		startStopConstraints.gridy = 2;
		startStop.addActionListener(this);
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
		
	    // Add the table to the scrollpane.
	    tableModel = new StegTableModel();
	    JTable table = new JTable(tableModel);
	    table.getTableHeader().setReorderingAllowed(false); 
		scrollPane.setViewportView(table);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source == inputSelector)
		{
			FileFilter f = new ImageFileFilter();
			JFileChooser chooser = new JFileChooser(); 
		    chooser.setDialogTitle("Choose a file or directory");
		    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // For the batch mode, we want to be able to select both files and directories.
		    chooser.addChoosableFileFilter(f);
		    chooser.setAcceptAllFileFilterUsed(false); // Disallow the 'all files selection'.
		    chooser.setFileFilter(f);
		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
		    	File file = chooser.getSelectedFile();
		    	sourcePath.setText(file.getAbsolutePath());
		    }
		    else {
		    	  
		    }
		}
		else if (source == queueAdd)
		{
			if (rsCheckBox.isSelected())
			{
				ImageQueueItem i = new ImageQueueItem(sourcePath.getText(), StegMethods.RS, ImageTypes.BITMAP, tableModel);
				tableModel.addQueueItem(i);
			}
			if (chiSquareCheckBox.isSelected())
			{
				ImageQueueItem i = new ImageQueueItem(sourcePath.getText(), StegMethods.CHI_SQUARE, ImageTypes.BITMAP, tableModel);
				tableModel.addQueueItem(i);
			}
			sourcePath.setText("");
		}
		else if (source == startStop)
		{
			startStop.setText("Stop");
			(new ProcessImageQueue(tableModel)).execute();
			
			startStop.setText("Start");
		}
		
		// TODO Auto-generated method stub
		
	}
	
	class ImageFileFilter extends javax.swing.filechooser.FileFilter {
	    public boolean accept(File f) {
	        return (f.isDirectory() || f.getName().toLowerCase().endsWith(".bmp") || f.getName().toLowerCase().endsWith(".png")) && !f.getName().toLowerCase().endsWith(".app");
	    }
	    
	    public String getDescription() {
	        return "Supported files";
	    }
	}

}
