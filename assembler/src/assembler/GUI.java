package assembler;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class GUI {
	
	private JFrame f;
	private JTextPane textEditor;
	private JTextPane output;
	private JTextPane console;
	private StyledDocument textEditorDoc;
	private StyledDocument outputDoc;
	private StyledDocument consoleDoc;
	private SimpleAttributeSet attrWHITE;
	private JMenuBar menu;
	private JMenu fileDropdown;
	private JMenuItem openFileOpt;
	private JMenuItem saveFileOpt;
	private JMenuItem genObjectFileOpt;
	
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
	    textEditorScrollPane.setBounds(0, 0, 800, 930);
	    textEditorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    textEditorScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    JScrollPane outputScrollPane = new JScrollPane(output);
	    outputScrollPane.setBounds(800, 0, 480, 480);
	    outputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    outputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    JScrollPane consoleScrollPane = new JScrollPane(console);
	    consoleScrollPane.setBounds(800, 480, 480, 450);
	    consoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    consoleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    
	    
	    
	    f.add(textEditorScrollPane);
	    f.add(outputScrollPane);
	    f.add(consoleScrollPane);
	    
	    f.setSize(1280, 1000);
	    
	    f.setLayout(null);
	    
	    f.setVisible(true);

	}
}
