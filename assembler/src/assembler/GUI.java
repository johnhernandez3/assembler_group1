package assembler;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

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
	private JMenuItem openFileOpt;
	private JMenuItem saveFileOpt;
	private JTable registerTable;
	private JTable memoryTable;
	private DefaultTableModel tableModel;
	private JTable headerTable;


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
    openFileOpt = new JMenuItem("Open");
    saveFileOpt = new JMenuItem("Save");
    fileDropdown.add(openFileOpt);
    fileDropdown.add(saveFileOpt);
    menu.add(fileDropdown);
    menu.add(genObjectFile);
    menu.add(loadMemoryFile);
    
    genObjectFile.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				console.getStyledDocument().insertString(console.getStyledDocument().getLength(), "Generate Object File Pressed.", attrWHITE);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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

					try {
						// load memory with fileContent String here
						console.getStyledDocument().insertString(console.getStyledDocument().getLength(), "Upload Memory File Uploaded.", attrWHITE);
						Memory mem = new Memory();
						mem.loadMemoryFromFile(fileContent);
						DefaultTableModel model = new DefaultTableModel(mem.memData(), columnNames);
						memoryTable.setModel(model);
						memoryTable.repaint();
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
						Parser p = new Parser();
						Tokenizer tokenizer = new Tokenizer(fileContent);
						p.parse(tokenizer);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
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
	consoleScrollPane.setBounds(0, 480,1275,900);
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
}

