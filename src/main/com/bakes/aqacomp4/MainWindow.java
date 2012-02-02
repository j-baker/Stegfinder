/**
 * 
 */
package com.bakes.aqacomp4;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author bakes
 *
 */
public class MainWindow implements ActionListener {
	String path = null;

	JButton btnBrowse;
	JButton btnAdd;
	JComboBox comboBox1;
	JComboBox comboBox2;
	JLabel label;
	JLabel lblNewLabel;
	int numInQueue = 0;
	Queue<ImageQueueItem> queue = new LinkedList<ImageQueueItem>();
	String boxText = "";

	private JFrame frame;

	private JButton btnRun;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    //Handle open button action.
	    if (e.getSource() == btnBrowse) {
	    	final JFileChooser fc = new JFileChooser();
	        int returnVal = fc.showOpenDialog(null);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            path = file.getAbsolutePath();
	            lblNewLabel.setText(path);
	            //This is where a real application would open the file.
	        }
	    }
	    if (e.getSource() == btnAdd)
	    {
	    	if (path != null)
	    	{
	    		StegMethods s = (StegMethods) comboBox1.getSelectedItem();
	    		ImageTypes i = (ImageTypes) comboBox2.getSelectedItem();
	    		ImageQueueItem q = new ImageQueueItem(path, s, i);
	    		numInQueue++;
	    		label.setText(""+numInQueue);
	    		queue.add(q);
	    		path = null;
	    		lblNewLabel.setText("/");
	    	}
	    }
	    if (e.getSource() == btnRun)
	    {
	    	while (!queue.isEmpty())
	    	{
	    		ImageQueueItem q = queue.remove();
	    		q.runMethod();
	    		try {
					String result = q.getResultAsString();
					boxText += result + "\n";
					txtpnHi.setText(boxText);
				} catch (ImageNotTestedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		numInQueue--;
	    		label.setText(""+numInQueue);
	    	}
	    }
	   }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 579, 302);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow][176.00,grow][27.00,grow][]", "[][][63.00,grow][]"));
		
		btnBrowse = new JButton("Browse");
		frame.getContentPane().add(btnBrowse, "cell 0 0");
		btnBrowse.addActionListener(this);
		comboBox1 = new JComboBox();
		comboBox1.setModel(new DefaultComboBoxModel(StegMethods.values()));
		frame.getContentPane().add(comboBox1, "cell 1 0,growx");
		
		comboBox2 = new JComboBox();
		comboBox2.setModel(new DefaultComboBoxModel(ImageTypes.values()));
		frame.getContentPane().add(comboBox2, "cell 2 0,growx");
		
		btnAdd = new JButton("Add");
		frame.getContentPane().add(btnAdd, "cell 3 0");
		btnAdd.addActionListener(this);
		
		lblNewLabel = new JLabel("/");
		frame.getContentPane().add(lblNewLabel, "cell 0 1");
		
		JLabel lblTotalNumberOf = new JLabel("Total Number of Images:");
		frame.getContentPane().add(lblTotalNumberOf, "cell 1 1,alignx right");
		
		label = new JLabel("0");
		frame.getContentPane().add(label, "cell 2 1");
		
		btnRun = new JButton("Run");
		frame.getContentPane().add(btnRun, "cell 3 1");
		btnRun.addActionListener(this);
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 3 4 1,grow");
		
		String sampleData[][] =
			{
				{"1.bmp", "RS_METHOD", "BITMAP", "0.93", "DONE"},
				{"2.bmp", "RS_METHOD", "BITMAP", "", "2 seconds"},
				{"3.bmp", "CHISQUARE_METHOD", "BITMAP", "", "0.1 seconds"},
			};
		
		String col[] = {"File name","Steganography Method","Image Type","Result","ETA"};
		
		
		
		table = new JTable(sampleData, col);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
	}

}
