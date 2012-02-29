package com.bakes.aqacomp4.gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

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
	private JProgressBar progress;
	
	private HashMap<StegMethods, JCheckBox> stegMethods = new HashMap<StegMethods, JCheckBox>();


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
	
	public StegTableModel getTable()
	{
		return tableModel;
	}
	
	public void setProgress(int progress)
	{
		this.progress.setValue(progress);
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
		
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // The application needs to exit when this is closed.
		application.setTitle("Stegfinder"); //TODO Redo with constant of some kind.
		java.awt.Image icon = Toolkit.getDefaultToolkit().getImage("Logo1");
		application.setIconImage(icon);
		
		//Initialize the 'Gridbaglayout' used to lay the components.
		GridBagLayout layout = new GridBagLayout();
		
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
		GridBagConstraints sourcePathConstraints = new GridBagConstraints();
		sourcePathConstraints.insets = new Insets(5, 5, 5, 5);
		sourcePathConstraints.gridwidth = 5;
		sourcePathConstraints.fill = GridBagConstraints.HORIZONTAL;
		sourcePathConstraints.gridx = 1;
		sourcePathConstraints.gridy = 0;
		application.getContentPane().add(sourcePath, sourcePathConstraints);
		
		// TODO Method specific buttons. To be made procedural in a later stage.
		
		int gridYOffset = 0;
		int gridXOffset = 0;
		for (StegMethods s : StegMethods.values())
		{
			System.out.println(s.toString());
			JCheckBox chckBx = new JCheckBox(s.toString());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.insets = new Insets(0, 0, 5, 5);
			constraints.gridx = gridXOffset;
			constraints.gridy = gridYOffset+1;
			gridXOffset++;
			if (gridXOffset > 4)
			{
				gridXOffset = 0;
				gridYOffset++;
			}
			application.getContentPane().add(chckBx, constraints);
			stegMethods.put(s, chckBx);
		}
		
		// TODO The scrollpane is necessary for the table - so that there can be an 'unlimited' number of items in the queue.
		
		int tableHeight = 8;
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints scrollPaneConstraints = new GridBagConstraints();
		scrollPaneConstraints.insets = new Insets(0, 5, 5, 5);
		scrollPaneConstraints.gridheight = tableHeight;
		scrollPaneConstraints.gridwidth = 6;
		scrollPaneConstraints.fill = GridBagConstraints.BOTH;
		scrollPaneConstraints.gridx = 0;
		scrollPaneConstraints.gridy = gridYOffset+2;
		scrollPaneConstraints.weighty = 1;
		application.getContentPane().add(scrollPane, scrollPaneConstraints);

		
	    // Add the table to the scrollpane.
	    tableModel = new StegTableModel();
	    JTable table = new JTable(tableModel);
	    table.getTableHeader().setReorderingAllowed(false); 
		scrollPane.setViewportView(table);
		
		queueAdd = new JButton("Add to Queue");
		GridBagConstraints queueAddConstraints = new GridBagConstraints();
		queueAddConstraints.insets = new Insets(0, 0, 5, 0);
		queueAddConstraints.gridx = 5;
		queueAddConstraints.gridy = 1;
		queueAddConstraints.gridheight = 1 + gridYOffset;
		queueAdd.addActionListener(this);
		application.getContentPane().add(queueAdd, queueAddConstraints);
		
		gridYOffset++;
		
		selectOutput = new JButton("Output");
		GridBagConstraints selectOutputConstraints = new GridBagConstraints();
		selectOutputConstraints.insets = new Insets(0, 0, 5, 5);
		selectOutputConstraints.gridx = 0;
		selectOutputConstraints.gridy = 2+gridYOffset+tableHeight;
		selectOutput.addActionListener(this);
		application.getContentPane().add(selectOutput, selectOutputConstraints);
		
		outputPath = new JTextField();
		outputPath.setColumns(10);
		GridBagConstraints outputPathConstraints = new GridBagConstraints();
		outputPathConstraints.gridwidth = 2;
		outputPathConstraints.insets = new Insets(0, 0, 5, 5);
		outputPathConstraints.fill = GridBagConstraints.HORIZONTAL;
		outputPathConstraints.gridx = 1;
		outputPathConstraints.gridy = 2+gridYOffset+tableHeight;
		application.getContentPane().add(outputPath, outputPathConstraints);
		
		JCheckBox csvReportRequired = new JCheckBox("CSV Report");
		GridBagConstraints csvReportConstraints = new GridBagConstraints();
		csvReportConstraints.insets = new Insets(0, 0, 5, 5);
		csvReportConstraints.gridx = 3;
		csvReportConstraints.gridy = 2+gridYOffset+tableHeight;
		application.getContentPane().add(csvReportRequired, csvReportConstraints);
		
		JCheckBox pdfReportRequired = new JCheckBox("PDF Report");
		GridBagConstraints pdfReportConstraints = new GridBagConstraints();
		pdfReportConstraints.insets = new Insets(0, 0, 5, 5);
		pdfReportConstraints.gridx = 4;
		pdfReportConstraints.gridy = 2+gridYOffset+tableHeight;
		application.getContentPane().add(pdfReportRequired, pdfReportConstraints);
		
		startStop = new JButton("Start");
		GridBagConstraints startStopConstraints = new GridBagConstraints();
		startStopConstraints.insets = new Insets(0, 0, 5, 5);
		startStopConstraints.gridx = 5;
		startStopConstraints.gridy = 2+gridYOffset+tableHeight;
		startStop.addActionListener(this);
		application.getContentPane().add(startStop, startStopConstraints);
		
		progress = new JProgressBar(0, 100); // 0-100%
		progress.setValue(0);
		progress.setStringPainted(true);
		GridBagConstraints progressConstraints = new GridBagConstraints();
		progressConstraints.insets = new Insets(0, 5, 5, 5);
		progressConstraints.gridx = 0;
		progressConstraints.gridy = 3+gridYOffset+tableHeight;
		progressConstraints.gridwidth = 6;
		progressConstraints.fill = GridBagConstraints.HORIZONTAL;
		application.getContentPane().add(progress, progressConstraints);
		
		
		application.setSize(application.getPreferredSize());
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
			String path = sourcePath.getText();
			boolean changed = false;
			File file = new File(path);
			if (file.isDirectory())
			{
				
			}
			else if (file.exists() && (path.endsWith(".bmp") || path.endsWith(".png")))
			{
				for (StegMethods s : StegMethods.values())
				{
					if (stegMethods.get(s).isSelected())
					{
						ImageQueueItem i = new ImageQueueItem(path, s, ImageTypes.BITMAP);
						tableModel.addQueueItem(i);
						changed = true;
					}
				}
				if (changed)
				{
					progress.setValue(0);
					sourcePath.setText("");
				}			
			}
			else
			{
				JOptionPane.showMessageDialog(application, "The path provided is not a path to a .bmp or .png file.");
			}

		}
		else if (source == startStop)
		{
			startStop.setText("Stop");
			(new ProcessImageQueue(this)).execute();
			
			startStop.setText("Start");
		}
		
		
		
	}

	class ImageFileFilter extends javax.swing.filechooser.FileFilter {
		@Override
	    public boolean accept(File f) {
	        return (f.isDirectory() || f.getName().toLowerCase().endsWith(".bmp") || f.getName().toLowerCase().endsWith(".png")) && !f.getName().toLowerCase().endsWith(".app");
	    }
	    
	    @Override
	    public String getDescription() {
	        return "Supported files";
	    }
	}

}
