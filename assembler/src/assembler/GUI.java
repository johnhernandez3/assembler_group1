package assembler;

import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

import assembler.IO2_SevenSegmentDisplay.SevenSegments;
import assembler.TrafficLight.Light;

public class GUI extends javax.swing.JFrame {

	/******************************************
	 * 	Declarations
	 ***************************************** */
	
	private JFrame f;
	private JTextPane textEditor;
	private JTextPane objectCodePane;
	private JTextPane output;
	private JTextPane console;
	private StyledDocument textEditorDoc;
	private StyledDocument objectCodeDoc;
	private StyledDocument outputDoc;
	private StyledDocument consoleDoc;
	private SimpleAttributeSet attrWHITE;
	private JMenuBar menu;
	private JMenu fileDropdown;
	private JMenu IODropdown;
	private JMenuItem segmentDisplay;
	private JMenuItem trafficLight;
	private JMenuItem keyboard;
	private JButton genObjectFile;
	private JButton loadMemoryFile;
	private JButton executePrev;
	private JButton executeAll;
	private JButton executeNext;
	private JMenuItem openFileOpt;
	private JMenuItem saveFileOpt;
	private Runner runner;
	public Register reg = new Register(this);
	public InstructionFormat instructions = new InstructionFormat(this);
	public HashMap<String,String> regs = reg.getregs();
	public Memory memory = new Memory();
	public Object[][] memoryData = memory.memData();
	public String[] columnNames = { "Direction", "Content" };
	public String[] registerColumns = { "Register", "Content" };
	public JTable memoryTable = new JTable(memoryData, columnNames);
	public JTable table = new JTable(this.reg.regsData(), registerColumns);
	
	public JPanel stackPointerPanel = new JPanel();
	public JLabel spLabel = new JLabel(this.reg.sp);
	public JPanel pcPointerPanel = new JPanel();
	public JLabel pcLabel = new JLabel(this.reg.pc);
	JPanel condPanel = new JPanel();
	JLabel condLabel = new JLabel(this.reg.cond ? "1" : "0");
	
	public Parser p;
	int currentLine;
	
	public GUI() { }
	
	public void updateMemoryTable() {
		Object[][] memoryData = memory.memData();
		DefaultTableModel model = new DefaultTableModel(memoryData, columnNames);
		memoryTable.setModel(model);
	}
	
	public void updateRegisterTable() {
		Object[][] registerData = reg.regsData();
		DefaultTableModel model = new DefaultTableModel(registerData, registerColumns);
		table.setModel(model);
		this.spLabel.setText(this.reg.sp);
		this.pcLabel.setText(this.reg.pc);
		this.condLabel.setText(this.reg.cond ? "1" : "0");
	}
	
	public Memory getMemory() {
		return this.memory;
	}
	
	/******************************************
	 * 	Getters and Setters
	 ***************************************** */

	public StyledDocument getTextEditorDoc() {
		return textEditorDoc;
	}

	public void setTextEditorDoc(StyledDocument textEditorDoc) {
		this.textEditorDoc = textEditorDoc;
	}
	public StyledDocument getObjectCodeDoc() {
		return objectCodeDoc;
	}

	public void setObjectCodeDoc(StyledDocument objectCodeDoc) {
		this.objectCodeDoc = objectCodeDoc;
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
		
		this.memoryTable.setAutoCreateColumnsFromModel(false);

		/******************************************
		 * 	Initializations
		 ***************************************** */
	
		f = new JFrame("Microprocessor Simulator");
		textEditor = new JTextPane();
		objectCodePane = new JTextPane();
		output = new JTextPane();
		console = new JTextPane();
		attrWHITE = new SimpleAttributeSet();
		menu = new JMenuBar();
		runner = new Runner(this);
		
		/******************************************
		 * 	File Upload
		 ***************************************** */
	
		textEditor.setMargin(new Insets(10,10,10,10));
		objectCodePane.setMargin(new Insets(10,10,10,10));
		output.setMargin(new Insets(10,10,10,10));
		console.setMargin(new Insets(10,10,10,10));
	
		StyleConstants.setForeground(attrWHITE, Color.WHITE);
	
		fileDropdown = new JMenu("File");
		IODropdown = new JMenu("I/Os");
	    genObjectFile = new JButton("Save Object Code to File");
	    loadMemoryFile = new JButton("Upload Memory File");
	    
	    String basePath = new File("").getAbsolutePath();
	    Icon prev = new ImageIcon(basePath + "/src/media/prev.png");
	    Icon next = new ImageIcon(basePath + "/src/media/next.png");
	    executePrev = new JButton(prev);
	    executeAll = new JButton("Execute All");
	    executeNext = new JButton(next);
	    
	    openFileOpt = new JMenuItem("Open");
	    saveFileOpt = new JMenuItem("Save");
	    
	    segmentDisplay = new JMenuItem("Segment Display");
	    trafficLight = new JMenuItem("Traffic Light");
	    keyboard = new JMenuItem("Hex Keyboard");
	    
	    IODropdown.add(segmentDisplay);
	    IODropdown.add(trafficLight);
	    IODropdown.add(keyboard);
	 
	    fileDropdown.add(openFileOpt);
	    fileDropdown.add(saveFileOpt);
	    
	    menu.add(fileDropdown);
	    menu.add(IODropdown);
	    menu.add(genObjectFile);
	    
	    menu.add(executePrev);
	    menu.add(executeAll);
	    menu.add(executeNext);
	    
	    menu.add(loadMemoryFile);
	    executePrev.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (textEditor.getStyledDocument().getLength() == 0) {
					log("WARNING: There is no source code.\n");
				} else {
					String fileContent = "";
					try {
						fileContent = textEditor.getStyledDocument().getText(0, textEditor.getStyledDocument().getLength());
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					p = new Parser(fileContent);
					currentLine = runner.getCurrentInstruction();
					if (currentLine == 0) {
						log("WARNING: There is no prev instruction.\n");
					} else if (currentLine > 0) {
						Converter converter = new Converter();
						runner.setCurrentInstruction(currentLine-1);
						currentLine = runner.getCurrentInstruction();
						String objectCode = runner.executeLine(runner.run(p.parseLine(currentLine))).toUpperCase();
						try {
							objectCodeDoc.insertString(objectCodeDoc.getLength(), objectCode + "\n", attrWHITE);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						log(converter.decimalToHex(currentLine).toUpperCase() + ": " + objectCode + ": " + p.getLine(currentLine) + "\n");
					}
				}
			}
	    });
	    
	    executeAll.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (textEditor.getStyledDocument().getLength() == 0) {
					log("WARNING: There is no source code.\n");
				} else if (p == null) {
					String fileContent = "";
					try {
						fileContent = textEditor.getStyledDocument().getText(0, textEditor.getStyledDocument().getLength());
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					p = new Parser(fileContent);
					for (String s : p.getLines()) {
						Converter converter = new Converter();
						String objectCode = runner.executeLine(runner.run(p.parseLine(currentLine))).toUpperCase();
						try {
							objectCodeDoc.insertString(objectCodeDoc.getLength(), objectCode + "\n", attrWHITE);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						log(converter.decimalToHex(currentLine).toUpperCase() + ": " + objectCode + ": " + p.getLine(currentLine) + "\n");
						runner.setCurrentInstruction(currentLine+1);
					}
				}
			}
	    });
	    
	    executeNext.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (textEditor.getStyledDocument().getLength() == 0) {
					log("WARNING: There is no source code.\n");
				} else {
					String fileContent = "";
					try {
						fileContent = textEditor.getStyledDocument().getText(0, textEditor.getStyledDocument().getLength());
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					p = new Parser(fileContent);
					currentLine = runner.getCurrentInstruction();
					if (currentLine > (p.getLines().size() - 1)) {
						log("WARNING: There is no next instruction.\n");
					} else {
						Converter converter = new Converter();
						String objectCode = runner.executeLine(runner.run(p.parseLine(currentLine))).toUpperCase();
						try {
							objectCodeDoc.insertString(objectCodeDoc.getLength(), objectCode + "\n", attrWHITE);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						log(converter.decimalToHex(currentLine).toUpperCase() + ": " + objectCode + ": " + p.getLine(currentLine) + "\n");
						runner.setCurrentInstruction(currentLine+1);
					}
				}
			}
	    });
	    
	    genObjectFile.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				FileManager fm = new FileManager();
				fm.save(objectCodePane.getText(), getParent());
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
						log("Memory File Uploaded.\n");
						memory.loadMemoryFromFile(fileContent);
						memoryData = memory.memData();
						DefaultTableModel model = new DefaultTableModel(memoryData, columnNames);
						memoryTable.setModel(model);
						System.out.println(memory.directions[0].getContent());
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
							p = null;
							currentLine = 0;
							console.getStyledDocument().remove(0, console.getStyledDocument().getLength());
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
				FileManager fm = new FileManager();
				fm.save(textEditor.getText(), getParent());
			}
			
		});
		
		trafficLight.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String memLocation = JOptionPane.showInputDialog(null,"Enter Memory Location:");
				Light l1 = new Light();
				l1.launch();

			}
		});
		
		segmentDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String memLocation = JOptionPane.showInputDialog(null,"Enter Memory Location:");
				SevenSegments ss = new SevenSegments();
				ss.launch();
			}
		});
		
		keyboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String memLocation = JOptionPane.showInputDialog(null,"Enter Memory Location:");
				Keyboard ui = new Keyboard();
				ui.launch();
			}
		});
		
		
		/******************************************
		 * 	TextEditor, Output, Console, Object Code SetUp
		 ***************************************** */
		
		f.setJMenuBar(menu);
	
		textEditor.setBackground(Color.DARK_GRAY);
		textEditor.setForeground(Color.WHITE);
		textEditor.setCaretColor(Color.WHITE);
		textEditorDoc = textEditor.getStyledDocument();
		
		objectCodePane.setBackground(Color.DARK_GRAY);
		objectCodePane.setForeground(Color.WHITE);
		objectCodePane.setCaretColor(Color.WHITE);
		objectCodeDoc = objectCodePane.getStyledDocument();
	
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
	
	
		/******************************************
		 * 	Memory 
		 ***************************************** */
		
		//Added Model to JTable & add JTable to ScrollPane
		JScrollPane memoryScrollPane2 = new JScrollPane(memoryTable);
		memoryScrollPane2.setBounds(800, 0, 480, 480);
		memoryScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		memoryScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		memoryScrollPane2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Memory"));
	
		/******************************************
		 * 	TextEditor
		 ****************************************** */
		
		JScrollPane textEditorScrollPane = new JScrollPane(textEditor);
		textEditorScrollPane.setBounds(300, 0, 500, 480);
		textEditorScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textEditorScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textEditorScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Text Editor"));
		
		/******************************************
		 * 	Object Code Console
		 ****************************************** */
		
		JScrollPane objectCodeScrollPane = new JScrollPane(objectCodePane);
		objectCodeScrollPane.setBounds(0, 160, 302, 456-136);
		objectCodeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		objectCodeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		objectCodeScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Object Code"));
	
		/******************************************
		 * 	ConsoleScrollPane
		 ***************************************** */
		
		JScrollPane consoleScrollPane = new JScrollPane(console);
		consoleScrollPane.setBounds(0, 480,1275,400);
		consoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		consoleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		consoleScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Console"));
	
		/******************************************
		 * 	Registers Table
		 ***************************************** */
	
		JPanel registerPanel = new JPanel(new GridBagLayout());
		table.setBackground(Color.WHITE);
		table.setBounds(5,20, 200, 136);
		table.setFont(new Font("Tahome",Font.ITALIC,14));
		int row = 0;
		for (Map.Entry<String,String> entry : regs.entrySet()) {
			table.setValueAt(entry.getKey(),row,0);
			table.setValueAt(entry.getValue(),row,1);
			row++;
		}
		registerPanel.setBounds(0,0,210,table.getHeight()+25);
		registerPanel.setBackground(Color.WHITE);
		registerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Registers"));
		registerPanel.add(table);
		registerPanel.setLayout(null);
		registerPanel.setVisible(true);
		
		/******************************************
		 * 	Stack Pointer (SP)
		 ***************************************** */

		stackPointerPanel.add(spLabel);
		stackPointerPanel.setBounds(table.getWidth()+9, 0, 92, 52);
		stackPointerPanel.setBackground(Color.WHITE);
		stackPointerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "SP"));
		stackPointerPanel.setLayout(new FlowLayout());
		stackPointerPanel.setVisible(true);
		
		/******************************************
		 * 	Program Counter (PC)
		 ***************************************** */
		
		pcPointerPanel.add(pcLabel);
		pcPointerPanel.setBounds(table.getWidth()+9, 52, 92, 52);
		pcPointerPanel.setBackground(Color.WHITE);
		pcPointerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "PC"));
		pcPointerPanel.setLayout(new FlowLayout());
		pcPointerPanel.setVisible(true);
		
		/******************************************
		 * 	Condition Bit (Cond)
		 ***************************************** */
		
		condPanel.add(condLabel);
		condPanel.setBounds(table.getWidth()+9, 104, 92, 57);
		condPanel.setBackground(Color.WHITE);
		condPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cond"));
		condPanel.setLayout(new FlowLayout());
		condPanel.setVisible(true);
		
		
		/******************************************
		 * 	Adding to Frame
		 ***************************************** */
		
		f.add(textEditorScrollPane);
		f.add(objectCodeScrollPane);
		f.add(consoleScrollPane);
		f.add(registerPanel);
		f.add(stackPointerPanel);
		f.add(pcPointerPanel);
		f.add(condPanel);
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
	private static JFrame initializeFrame() {
		JFrame frame = new JFrame();
		frame.setTitle("Traffic Light");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}
}