/**
 * 
 */
package com.bakes.aqacomp4;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;

/**
 * @author bakes
 *
 */
public class OptionsWindow {

	private JFrame frmSpamSettings;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptionsWindow window = new OptionsWindow();
					window.frmSpamSettings.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OptionsWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSpamSettings = new JFrame();
		frmSpamSettings.setTitle("SPAM Settings");
		frmSpamSettings.setResizable(false);
		frmSpamSettings.setBounds(100, 100, 449, 353);
		frmSpamSettings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSpamSettings.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "SVM Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmSpamSettings.getContentPane().add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[][][]", "[][][]"));
		
		JCheckBox chckbxUseSvm = new JCheckBox("Use SVM 1");
		panel.add(chckbxUseSvm, "cell 0 0");
		
		JCheckBox chckbxUseSvm_3 = new JCheckBox("Use SVM 4");
		panel.add(chckbxUseSvm_3, "cell 1 0");
		
		JCheckBox chckbxYou = new JCheckBox("You");
		panel.add(chckbxYou, "cell 2 0");
		
		JCheckBox chckbxUseSvm_1 = new JCheckBox("Use SVM 2");
		panel.add(chckbxUseSvm_1, "cell 0 1");
		
		JCheckBox chckbxUseSvm_4 = new JCheckBox("Use SVM 5");
		panel.add(chckbxUseSvm_4, "cell 1 1");
		
		JCheckBox chckbxGet = new JCheckBox("Get");
		panel.add(chckbxGet, "cell 2 1");
		
		JCheckBox chckbxUseSvm_2 = new JCheckBox("Use SVM 3");
		panel.add(chckbxUseSvm_2, "cell 0 2");
		
		JCheckBox chckbxUseSvm_5 = new JCheckBox("Use SVM 6");
		panel.add(chckbxUseSvm_5, "cell 1 2");
		
		JCheckBox chckbxThePicture = new JCheckBox("The picture");
		panel.add(chckbxThePicture, "cell 2 2");
		
		JCheckBox chckbxThePicture3 = new JCheckBox("Bakes' Custom one.");
		panel.add(chckbxThePicture3, "cell 2 3");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmSpamSettings.getContentPane().add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[]", "[][][]"));
		
		JLabel lblItIsEstimated = new JLabel("<html>It is estimated that a standard (6MP) Bitmap image will take (0:02) seconds to process with the current settings.</html>");
		panel_1.add(lblItIsEstimated, "cell 0 0,grow");
		
		JButton btnRerunBenchmark = new JButton("Re-run benchmark");
		panel_1.add(btnRerunBenchmark, "cell 0 1,alignx center");
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setToolTipText("");
		progressBar.setValue(40);
		panel_1.add(progressBar, "cell 0 2,growx");
	}

}
