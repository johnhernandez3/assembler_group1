package assembler;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
//import javax.swing.table.AbstractTableModel;
//import javax.swing.table.TableModel;

public class GUI extends javax.swing.JFrame {

	/****************************************************************************************************************************
	 * 	Declarations
	 *************************************************************************************************************************** */
	
	private JFrame f;
	private JTextPane textEditor;
	private JTextPane output;
	private JTextPane console;
	private JTextPane registers;
	private JPanel memoryPanel;
	private StyledDocument textEditorDoc;
	private StyledDocument outputDoc;
	private StyledDocument consoleDoc;
	private SimpleAttributeSet attrWHITE;
	private JMenuBar menu;
	private JMenu fileDropdown;
	private JButton genObjectFile;
	private JButton loadMemoryFile;
	private JButton executePrev;
	private JButton executeAll;
	private JButton executeNext;
	private JMenuItem openFileOpt;
	private JMenuItem saveFileOpt;
	private JTable registerTable;
	private JTable memoryTable;
	private DefaultTableModel tableModel;
	private JTable headerTable;
	private Runner runner;


	private static int columnNumber = 1;

	Register reg = new Register();
	HashMap<String,String> regs = reg.getregs();
	
	String[] columnNames = { "Direction", "Content" };
	Object[][] rowData = new Memory().memData();

	
	/****************************************************************************************************************************
	 * 	Getters and Setters
	 *************************************************************************************************************************** */

	public StyledDocument getTextEditorDoc() {
		return textEditorDoc;
	}

	public void setTextEditorDoc(StyledDocument textEditorDoc) {
	this.textEditorDoc = textEditorDoc;
	}

	public StyledDocument getOutputDoc() {
	return outputDoc;
	}

	public void setOutputDoc(StyledDocument outputDoc) {
	this.outputDoc = outputDoc;
	}

	public StyledDocument getConsoleDoc() {
	return consoleDoc;
	}

	public void buildGUI() {
	/****************************************************************************************************************************
	 * 	Initializations
	 *************************************************************************************************************************** */

	f = new JFrame("IDE TextBox");
	textEditor = new JTextPane();
	output = new JTextPane();
	console = new JTextPane();
	registers = new JTextPane();
	attrWHITE = new SimpleAttributeSet();
	menu = new JMenuBar();

	/****************************************************************************************************************************
	 * 	File Upload
	 *************************************************************************************************************************** */

	textEditor.setMargin(new Insets(10,10,10,10));
	output.setMargin(new Insets(10,10,10,10));
	console.setMargin(new Insets(10,10,10,10));

	StyleConstants.setForeground(attrWHITE, Color.WHITE);

	fileDropdown = new JMenu("File");
    genObjectFile = new JButton("Generate Object File");
    loadMemoryFile = new JButton("Upload Memory File");
    
    executePrev = new JButton("Prev Instruction");
    executeAll = new JButton("Execute All");
    executeNext = new JButton("Next Instruction");
    
    openFileOpt = new JMenuItem("Open");
    saveFileOpt = new JMenuItem("Save");
    fileDropdown.add(openFileOpt);
    fileDropdown.add(saveFileOpt);
    menu.add(fileDropdown);
    menu.add(genObjectFile);
    menu.add(loadMemoryFile);
    
    menu.add(executePrev);
    menu.add(executeAll);
    menu.add(executeNext);
    
    executePrev.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			log("Execute prev pressed.\n");
			if (textEditor.getStyledDocument().getLength() == 0) {
				log("ERROR: There are no instructions.\n");
			}
		}
    });
    executeAll.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			log("Execute all pressed.\n");
		}
    });
    executeNext.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			log("Execute next pressed.\n");
			if (textEditor.getStyledDocument().getLength() == 0) {
				log("ERROR: There are no instructions.\n");
			} else if (runner == null) {
				String fileContent = "";
				try {
					fileContent = textEditor.getStyledDocument().getText(0, textEditor.getStyledDocument().getLength());
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Parser p = new Parser(fileContent);
				runner = new Runner(p.getAllParsedTokens());
				runner.run();
			}
			if (runner.getNumOfInstructions() < 1) {
				log("ERROR: There are no instructions.\n");
			} else {
				log(runner.executeNext() + "\n");
			}
		}
    });
    
    genObjectFile.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			log("Generate Object File Pressed.\n");
			String fileContent = "";
			try {
				fileContent = textEditor.getStyledDocument().getText(0, textEditor.getStyledDocument().getLength());
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Parser p = new Parser(fileContent);
			runner = new Runner(p.parseLine(fileContent));
//			log(runner.executeLine());
		}

    });
    
    loadMemoryFile.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			switch (fileChooser.showOpenDialog(f)) {
				case JFileChooser.APPROVE_OPTION:
					File f = fileChooser.getSelectedFile();

					FileManager fm = new FileManager();
					String fileContent = null;
					try {
						fileContent = fm.read(f);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					// load memory with fileContent String here
					log("Upload Memory File Uploaded.\n");
					Memory mem = new Memory();
					mem.loadMemoryFromFile(fileContent);
					DefaultTableModel model = new DefaultTableModel(mem.memData(), columnNames);
					memoryTable.setModel(model);
					memoryTable.repaint();
					break;
			}	
		}
    	
    });

	openFileOpt.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			switch (fileChooser.showOpenDialog(f)) {
				case JFileChooser.APPROVE_OPTION:
					File f = fileChooser.getSelectedFile();

					FileManager fm = new FileManager();
					String fileContent = null;
					try {
						fileContent = fm.read(f);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					try {
						textEditorDoc.remove(0, textEditorDoc.getLength());
						textEditorDoc.insertString(0, fileContent, attrWHITE);
						Parser p = new Parser(fileContent);
						String parsed = p.parse();
						runner = new Runner(p.parseLine(fileContent));
						runner.run();
						log(parsed);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
			}	
		}

	});
	saveFileOpt.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser saver = new JFileChooser("./");
	        int returnVal = saver.showSaveDialog(getParent());
	        File file = saver.getSelectedFile();
	        BufferedWriter writer = null;
	        if (returnVal == JFileChooser.APPROVE_OPTION)
	        {
	            try
	            {
	            writer = new BufferedWriter( new FileWriter(file));
	            writer.write( textEditor.getText());
	            writer.close( );
	          
	            }
	            catch (IOException s)
	            {
	            
	            }
	        }
		}
		
	});
	
	/****************************************************************************************************************************
	 * 	TextEditor, Output, Console SetUp
	 *************************************************************************************************************************** */
	
	f.setJMenuBar(menu);

	textEditor.setBackground(Color.DARK_GRAY);
	textEditor.setForeground(Color.WHITE);
	textEditor.setCaretColor(Color.WHITE);
	textEditorDoc = textEditor.getStyledDocument();

	output.setBackground(Color.DARK_GRAY);
	output.setForeground(Color.WHITE);
	output.setCaretColor(Color.WHITE);
	output.setEditable(false);
	outputDoc = output.getStyledDocument();

	console.setBackground(Color.DARK_GRAY);
	console.setForeground(Color.WHITE);
	console.setCaretColor(Color.WHITE);
	console.setEditable(false);
	consoleDoc = console.getStyledDocument();


	/****************************************************************************************************************************
	 * 	Memory 
	 *************************************************************************************************************************** */
	
//	TableModel memoryDataModel = new AbstractTableModel() {
//		private static final long serialVersionUID = 1L;
//		String[] columnNames = { "Direction", "Content" };
//		Object[][] rowData;
//		public int getColumnCount() { return columnNames.length; }
//		public boolean isCellEditable(int row, int col) { return true; }
//		public String getColumnName(int index) { return columnNames[index]; }
//		public int getRowCount() { return 2048; }
//		public Object getValueAt(int row, int col) { return new Integer(row*col); }
//		public void setValueAt(Object value, int row, int col) {
//			rowData[row][col] = value;
//	        fireTableCellUpdated(row, col);
//	    }
//	};
	//Added Model to JTable & add JTable to ScrollPane
	memoryTable = new JTable(rowData, columnNames);
	JScrollPane memoryScrollPane2 = new JScrollPane(memoryTable);
	memoryScrollPane2.setBounds(800, 0, 480, 480);
	memoryScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	memoryScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	memoryScrollPane2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Memory"));

	/****************************************************************************************************************************
	 * 	TextEditor
	 **************************************************************************************************************************** */
	
	JScrollPane textEditorScrollPane = new JScrollPane(textEditor);
	textEditorScrollPane.setBounds(300, 0, 500, 480);
	textEditorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	textEditorScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	textEditorScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Text Editor"));
	
	/****************************************************************************************************************************
	 * 	Memory Scrollpane(Not Used)
	 *************************************************************************************************************************** */
	
	JScrollPane memoryScrollPane = new JScrollPane(output);
	memoryScrollPane.setBounds(800, 0, 480, 480);
	memoryScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	memoryScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	memoryScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Memory"));

	/****************************************************************************************************************************
	 * 	ConsoleScrollPane
	 *************************************************************************************************************************** */
	
	JScrollPane consoleScrollPane = new JScrollPane(console);
	consoleScrollPane.setBounds(0, 480,1275,400);
	consoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	consoleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	consoleScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Console"));

	/****************************************************************************************************************************
	 * 	GUI Register
	 *************************************************************************************************************************** */

	JPanel registerPanel = new JPanel(new GridBagLayout());
	//Table Declaration and adjustments
	JTable table=new JTable(regs.size(),2);
	table.setBackground(Color.gray);
	table.setBounds(5,20, 200, 150);
	table.setFont(new Font("Tahome",Font.ITALIC,14));
	// Adding Hashmaps key & Values to JTable
	//Still need to update values once GUI has functionality
	int row=0;
	for(Map.Entry<String,String> entry: regs.entrySet()){
	table.setValueAt(entry.getKey(),row,0);
	table.setValueAt(entry.getValue(),row,1);
	row++;
	}
	/****************************************************************************************************************************
	 * 	StackPointer & Instruction Register 
	 *************************************************************************************************************************** */
	JPanel stackPointerPanel = new JPanel(new GridBagLayout());
	stackPointerPanel.setBounds(table.getWidth()+7,0, table.getWidth(), 480);
	stackPointerPanel.setBackground(Color.gray);
	stackPointerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "PC & SP"));
	stackPointerPanel.setLayout(null);
	stackPointerPanel.setVisible(true);

	registerPanel.setBounds(0,0,210,480);
	registerPanel.setBackground(Color.gray);
	registerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Registers"));
	registerPanel.add(table);

	registerPanel.setLayout(null);
	registerPanel.setVisible(true);



	/****************************************************************************************************************************
	 * 	Adding to Frame
	 *************************************************************************************************************************** */
	f.add(textEditorScrollPane);
	f.add(consoleScrollPane);
	f.add(registerPanel);
	f.add(stackPointerPanel);
	f.add(memoryScrollPane2);
	f.setSize(1280, 1000);
	f.setLayout(null);
	f.setVisible(true);

	}
	
	public void log(String s) {
		try {
			console.getStyledDocument().insertString(console.getStyledDocument().getLength(), s, attrWHITE);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

