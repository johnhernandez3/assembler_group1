//package assembler;
//
//import java.awt.Color;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.IOException;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.JScrollPane;
//import javax.swing.JTextPane;
//import javax.swing.ScrollPaneConstants;
//import javax.swing.text.BadLocationException;
//import javax.swing.text.SimpleAttributeSet;
//import javax.swing.text.StyleConstants;
//import javax.swing.text.StyledDocument;
//
//public class GUI {
//	
//	private JFrame f;
//	private JTextPane textEditor;
//	private JTextPane output;
//	private JTextPane console;
//	private StyledDocument textEditorDoc;
//	private StyledDocument outputDoc;
//	private StyledDocument consoleDoc;
//	private SimpleAttributeSet attrWHITE;
//	private JMenuBar menu;
//	private JMenu fileDropdown;
//	private JMenuItem openFileOpt;
//	private JMenuItem saveFileOpt;
//	private JMenuItem genObjectFileOpt;
//	
//	public StyledDocument getTextEditorDoc() {
//		return textEditorDoc;
//	}
//
//	public void setTextEditorDoc(StyledDocument textEditorDoc) {
//		this.textEditorDoc = textEditorDoc;
//	}
//	
//	public StyledDocument getOutputDoc() {
//		return outputDoc;
//	}
//
//	public void setOutputDoc(StyledDocument outputDoc) {
//		this.outputDoc = outputDoc;
//	}
//	
//	public StyledDocument getConsoleDoc() {
//		return consoleDoc;
//	}
//	
//	public void buildGUI() {
//		
//		f = new JFrame("IDE TextBox");
//		textEditor = new JTextPane();
//		output = new JTextPane();
//		console = new JTextPane();
//	    attrWHITE = new SimpleAttributeSet();
//	    menu = new JMenuBar();
//	    
//	    textEditor.setMargin(new Insets(10,10,10,10));
//	    output.setMargin(new Insets(10,10,10,10));
//	    console.setMargin(new Insets(10,10,10,10));
//	    
//	    StyleConstants.setForeground(attrWHITE, Color.WHITE);
//	    
//	    fileDropdown = new JMenu("File");
//	    openFileOpt = new JMenuItem("Open");
//	    saveFileOpt = new JMenuItem("Save");
//	    genObjectFileOpt = new JMenuItem("Generate Object File");
//	    fileDropdown.add(openFileOpt);
//	    fileDropdown.add(saveFileOpt);
//	    fileDropdown.add(genObjectFileOpt);
//	    menu.add(fileDropdown);
//	    
//	    
//	    
//	    openFileOpt.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fileChooser = new JFileChooser();
//                switch (fileChooser.showOpenDialog(f)) {
//                    case JFileChooser.APPROVE_OPTION:
//                    	File f = fileChooser.getSelectedFile();
//                    	
//                    	FileManager fm = new FileManager();
//						String fileContent = null;
//						try {
//							fileContent = fm.read(f);
//						} catch (IOException e2) {
//							// TODO Auto-generated catch block
//							e2.printStackTrace();
//						}
//                		
//						try {
//							textEditorDoc.remove(0, textEditorDoc.getLength());
//							textEditorDoc.insertString(0, fileContent, attrWHITE);
//							Parser p = new Parser();
//							Tokenizer tokenizer = new Tokenizer(fileContent);
//							p.parse(tokenizer);
//						} catch (BadLocationException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//                        break;
//                }
//            }
//
//        });
//	    
//	    f.setJMenuBar(menu);
//
//	    textEditor.setBackground(Color.DARK_GRAY);
//	    textEditor.setForeground(Color.WHITE);
//	    textEditor.setCaretColor(Color.WHITE);
//	    textEditorDoc = textEditor.getStyledDocument();
//	    
//	    output.setBackground(Color.DARK_GRAY);
//	    output.setForeground(Color.WHITE);
//	    output.setCaretColor(Color.WHITE);
//	    output.setEditable(false);
//	    outputDoc = output.getStyledDocument();
//	    
//	    console.setBackground(Color.DARK_GRAY);
//	    console.setForeground(Color.WHITE);
//	    console.setCaretColor(Color.WHITE);
//	    console.setEditable(false);
//	    consoleDoc = console.getStyledDocument();
//	    
//	    JScrollPane textEditorScrollPane = new JScrollPane(textEditor);
//	    textEditorScrollPane.setBounds(0, 0, 800, 930);
//	    textEditorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//	    textEditorScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//	    
//	    JScrollPane outputScrollPane = new JScrollPane(output);
//	    outputScrollPane.setBounds(800, 0, 480, 480);
//	    outputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//	    outputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//	    
//	    JScrollPane consoleScrollPane = new JScrollPane(console);
//	    consoleScrollPane.setBounds(800, 480, 480, 450);
//	    consoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//	    consoleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//	    
//	    
//	    
//	    
//	    f.add(textEditorScrollPane);
//	    f.add(outputScrollPane);
//	    f.add(consoleScrollPane);
//	    
//	    f.setSize(1280, 1000);
//	    
//	    f.setLayout(null);
//	    
//	    f.setVisible(true);
//
//	}
//}
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
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


import javafx.scene.layout.HBox;

public class GUI extends javax.swing.JFrame {
	
	private JFrame f;
	private JTextPane textEditor;
	private JTextPane output;
	private JTextPane console;
	private JTextPane registers;
	private StyledDocument textEditorDoc;
	private StyledDocument outputDoc;
	private StyledDocument consoleDoc;
	private SimpleAttributeSet attrWHITE;
	private JMenuBar menu;
	private JMenu fileDropdown;
	private JMenuItem openFileOpt;
	private JMenuItem saveFileOpt;
	private JMenuItem genObjectFileOpt;
	private JTable registerTable;
    private static DefaultTableModel tableModel;
	private static int columnNumber = 1;
	
	Register reg = new Register();
	HashMap<String,String> regs = reg.getregs();
	
	
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
	
	f = new JFrame("IDE TextBox");
	textEditor = new JTextPane();
	output = new JTextPane();
	console = new JTextPane();
	registers = new JTextPane();
	    attrWHITE = new SimpleAttributeSet();
	    menu = new JMenuBar();
	    
	    
	    
	    textEditor.setMargin(new Insets(10,10,10,10));
	    output.setMargin(new Insets(10,10,10,10));
	    console.setMargin(new Insets(10,10,10,10));
	    
	    StyleConstants.setForeground(attrWHITE, Color.WHITE);
	    
	    fileDropdown = new JMenu("File");
	    openFileOpt = new JMenuItem("Open");
	    saveFileOpt = new JMenuItem("Save");
	    genObjectFileOpt = new JMenuItem("Generate Object File");
	    fileDropdown.add(openFileOpt);
	    fileDropdown.add(saveFileOpt);
	    fileDropdown.add(genObjectFileOpt);
	    menu.add(fileDropdown);
	    


     

	    
	    
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
	    
	    JScrollPane textEditorScrollPane = new JScrollPane(textEditor);
	    textEditorScrollPane.setBounds(300, 0, 500, 480);
	    textEditorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    textEditorScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    textEditorScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Text Editor"));
	    
	    JScrollPane memoryScrollPane = new JScrollPane(output);
	    memoryScrollPane.setBounds(800, 0, 480, 480);
	    memoryScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    memoryScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    memoryScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Memory"));
	    
	    JScrollPane consoleScrollPane = new JScrollPane(console);
	    consoleScrollPane.setBounds(0, 480,1275,900);
	    consoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    consoleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    consoleScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Console"));
	    
//	    JScrollPane registersScrollPane = new JScrollPane(registers);
//	    registersScrollPane.setBounds(0,0, 300, 455);
//	    registersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//	    registersScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    //Registers Area
	  
	    
	    JPanel registerPanel = new JPanel(new GridBagLayout());
	    JTable table=new JTable(regs.size(),2);
	    table.setBackground(Color.gray);
	    table.setBounds(5,20, 200, 150);
	    table.setFont(new Font("Tahome",Font.ITALIC,14));
	   
	    int row=0;
	    for(Map.Entry<String,String> entry: regs.entrySet()){
	         table.setValueAt(entry.getKey(),row,0);
	         table.setValueAt(entry.getValue(),row,1);
	         row++;
	    }
	    
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
	    
	    
	    
	   
	    f.add(textEditorScrollPane);
	    f.add(memoryScrollPane);
	    f.add(consoleScrollPane);
	  
	    f.add(registerPanel);
	    f.add(stackPointerPanel);
	    
	    f.setSize(1280, 1000);
	    f.setLayout(null);
	    f.setVisible(true);

	}
}
