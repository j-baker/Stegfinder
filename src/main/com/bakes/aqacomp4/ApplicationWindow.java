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

public class ApplicationWindow {

	private JFrame frmStegfinder;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationWindow window = new ApplicationWindow();
					window.frmStegfinder.setVisible(true);
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
		frmStegfinder = new JFrame();
		frmStegfinder.setTitle("Stegfinder");
		frmStegfinder.setBounds(100, 100, 766, 493);
		frmStegfinder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmStegfinder.getContentPane().setLayout(gridBagLayout);
		
		JButton btnSource = new JButton("Source");
		GridBagConstraints gbc_btnSource = new GridBagConstraints();
		gbc_btnSource.insets = new Insets(0, 0, 5, 5);
		gbc_btnSource.gridx = 0;
		gbc_btnSource.gridy = 0;
		frmStegfinder.getContentPane().add(btnSource, gbc_btnSource);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 5;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		frmStegfinder.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JCheckBox chckbxSpam = new JCheckBox("SPAM");
		GridBagConstraints gbc_chckbxSpam = new GridBagConstraints();
		gbc_chckbxSpam.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSpam.gridx = 0;
		gbc_chckbxSpam.gridy = 1;
		frmStegfinder.getContentPane().add(chckbxSpam, gbc_chckbxSpam);
		
		JButton btnSpamSettings = new JButton("SPAM Settings");
		GridBagConstraints gbc_btnSpamSettings = new GridBagConstraints();
		gbc_btnSpamSettings.insets = new Insets(0, 0, 5, 5);
		gbc_btnSpamSettings.gridx = 1;
		gbc_btnSpamSettings.gridy = 1;
		frmStegfinder.getContentPane().add(btnSpamSettings, gbc_btnSpamSettings);
		
		JCheckBox chckbxChiSquare = new JCheckBox("Chi Square");
		GridBagConstraints gbc_chckbxChiSquare = new GridBagConstraints();
		gbc_chckbxChiSquare.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxChiSquare.gridx = 2;
		gbc_chckbxChiSquare.gridy = 1;
		frmStegfinder.getContentPane().add(chckbxChiSquare, gbc_chckbxChiSquare);
		
		JCheckBox chckbxRs = new JCheckBox("RS");
		GridBagConstraints gbc_chckbxRs = new GridBagConstraints();
		gbc_chckbxRs.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxRs.gridx = 3;
		gbc_chckbxRs.gridy = 1;
		frmStegfinder.getContentPane().add(chckbxRs, gbc_chckbxRs);
		
		JCheckBox chckbxPairs = new JCheckBox("Pairs");
		GridBagConstraints gbc_chckbxPairs = new GridBagConstraints();
		gbc_chckbxPairs.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPairs.gridx = 4;
		gbc_chckbxPairs.gridy = 1;
		frmStegfinder.getContentPane().add(chckbxPairs, gbc_chckbxPairs);
		
		JButton btnAddToQueue = new JButton("Add to Queue");
		GridBagConstraints gbc_btnAddToQueue = new GridBagConstraints();
		gbc_btnAddToQueue.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddToQueue.gridx = 5;
		gbc_btnAddToQueue.gridy = 1;
		frmStegfinder.getContentPane().add(btnAddToQueue, gbc_btnAddToQueue);
		
		JButton btnOutput = new JButton("Output");
		GridBagConstraints gbc_btnOutput = new GridBagConstraints();
		gbc_btnOutput.insets = new Insets(0, 0, 5, 5);
		gbc_btnOutput.gridx = 0;
		gbc_btnOutput.gridy = 2;
		frmStegfinder.getContentPane().add(btnOutput, gbc_btnOutput);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		frmStegfinder.getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JCheckBox chckbxCsvReport = new JCheckBox("CSV Report");
		GridBagConstraints gbc_chckbxCsvReport = new GridBagConstraints();
		gbc_chckbxCsvReport.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxCsvReport.gridx = 3;
		gbc_chckbxCsvReport.gridy = 2;
		frmStegfinder.getContentPane().add(chckbxCsvReport, gbc_chckbxCsvReport);
		
		JCheckBox chckbxPdfReport = new JCheckBox("PDF Report");
		GridBagConstraints gbc_chckbxPdfReport = new GridBagConstraints();
		gbc_chckbxPdfReport.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxPdfReport.gridx = 4;
		gbc_chckbxPdfReport.gridy = 2;
		frmStegfinder.getContentPane().add(chckbxPdfReport, gbc_chckbxPdfReport);
		
		JButton btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 5;
		gbc_btnStart.gridy = 2;
		frmStegfinder.getContentPane().add(btnStart, gbc_btnStart);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		frmStegfinder.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

}
