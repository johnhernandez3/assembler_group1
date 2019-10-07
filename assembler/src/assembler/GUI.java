package assembler;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.FocusEvent;
//import java.awt.event.FocusListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.util.HashMap;
//import java.util.Random;
//import java.awt.MenuBar;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
//import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
//import javax.swing.SwingUtilities;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.text.AttributeSet;
//import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
//import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
//import javax.swing.text.StyleContext;
//import javax.swing.text.StyledDocument;

public class GUI {
	
//	private JPanel panel;
	private JFrame f;
//	private static JPanel keywordPanel;
//	private static JLabel keyWordLabel;
//	private static JTextField keywordsInput;
//	private static JButton addButton;
//	private static JButton clearButton;
//	private static JButton viewButton;
//	private static JButton restart;
//	private static String kw;
//	private static HashMap<String, Color> map = new HashMap<String, Color>();
//	private static DocumentListener listener;
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
							p.parseLine(tokenizer);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                        break;
                }
            }

        });
	    
	    f.setJMenuBar(menu);
	    
//	    listener = new DocumentListener() {
//	    	
//	    	private void highlight(StyledDocument styledDocument) throws BadLocationException {
//	    		StyledDocument document = textBox.getStyledDocument();
//	            String text = document.getText(0, document.getLength());
//
//	            int startIndex;
//	            int start;
//
//	            StyleContext context = StyleContext.getDefaultStyleContext();
//
//	            Style styleDefault = context.getStyle(StyleContext.DEFAULT_STYLE);
//	            styledDocument.setCharacterAttributes(0, text.length(), styleDefault, true);
//
//	            for (String keyword : map.keySet()) {
//	                startIndex = 0;
//	                start = text.indexOf(keyword, startIndex);
//	                while (start >= 0) {
//	                	AttributeSet attr = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, map.get(keyword));
//	                    styledDocument.setCharacterAttributes(start, keyword.length(), attr, true);
//	                    startIndex += keyword.length();
//	                    start = text.indexOf(keyword, startIndex);
//	                }
//	            }
//	        }
//	    	
//	    	private void changeText() {
//             Runnable doChange = new Runnable() {
//                 @Override
//                 public void run() {
//                 	try {
//							highlight(textBox.getStyledDocument());
//						} catch (BadLocationException e) {
//							e.printStackTrace();
//						}
//                 }
//             };
//             SwingUtilities.invokeLater(doChange);
//         }
//	    	
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				changeText();
//			}
//
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				changeText();
//			}
//
//			@Override
//			public void changedUpdate(DocumentEvent e) {}
//	    };

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
	    
//	    textBox.getDocument().addDocumentListener(listener);
	    
//		panel = new JPanel();
//		keywordPanel = new JPanel();
		
//		panel.setBounds(805, 0, 600, 100);
//	    keywordPanel.setBounds(805, 100, 600, 675);
	    
//	    panel.setBackground(Color.DARK_GRAY);
//	    keywordPanel.setBackground(Color.DARK_GRAY);
	    
//		addButton = new JButton("Add");
//		clearButton = new JButton("Clear Keywords");
//		viewButton = new JButton("View Keywords");
//		restart = new JButton("New textbox");

//		keywordsInput = new JTextField(15);
		
//		panel.add(keywordsInput);
//		panel.add(addButton);
//		panel.add(clearButton);
//		panel.add(viewButton);
//		panel.add(restart);

//		keywordsInput.setText("Type keyword here");
		
//		keywordsInput.addFocusListener(new FocusListener() {
//
//			@Override
//			public void focusGained(FocusEvent e) {
//				JTextField source = (JTextField) e.getComponent();
//		        source.setText("");
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				
//		            
//					//JTextField source = (JTextField) e.getComponent();
//			       // source.setText("Type keyword here");
//			}
//		});
		
//		keywordsInput.addKeyListener(new KeyListener() {
//
//			@Override
//			public void keyTyped(KeyEvent e) {}
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					addButton.doClick();
//					keywordsInput.setText("");
//				}
//				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
//					clearButton.doClick();
//					keywordsInput.setText("");	
//				}}
//			@Override
//			public void keyReleased(KeyEvent e) {}
//			
//		});
		
//		ActionListener handler = new ActionListener() {
//			private Color genColor() {
//				Random rnd = new Random();
//				float r = rnd.nextFloat();
//				float g = rnd.nextFloat();
//				float b = rnd.nextFloat();
//				Color c = new Color(r, g, b).brighter();
//				if (map.containsValue(c)) {
//					r = rnd.nextFloat();
//					g = rnd.nextFloat();
//					b = rnd.nextFloat();
//					c = new Color(r, g, b).brighter();
//				}
//				if (r+g+b == 0 || r+g+b == (765) || r+g+b == (306)) {
//					r = rnd.nextFloat();
//					g = rnd.nextFloat();
//					b = rnd.nextFloat();
//					c = new Color(r, g, b).brighter();
//				}
//				return c;
//			}
//			 public void infoBox()
//			    {
//				 String title = "Error Message";
//				 String info = "Please type a keyword";
//			        JOptionPane.showMessageDialog(null, info, "Moron Alert: " + title, JOptionPane.INFORMATION_MESSAGE);
//			    }
//			
//			public void actionPerformed(ActionEvent event) {
//				if(event.getSource() == addButton) {
//					kw = keywordsInput.getText();
//					String Default1 = "Type keyword here";
//					String Default2 = "";
//
//					if(kw.equals(Default1) || kw.equals(Default2)) {
//						infoBox();
//						return;
//					}
//					System.out.println("Keyword Input: " + kw);
//					if (!kw.isEmpty()) {
//						if (!map.containsKey(kw)) {
//							keyWordLabel = new JLabel(kw);
//							keywordPanel.add(keyWordLabel);
//							Color color = genColor();
//							map.put(kw, color);
//							keyWordLabel.setForeground(color);
//							keyWordLabel.revalidate();
//							try {
//								textBox.getDocument().insertString(textBox.getDocument().getLength(), " ", attrWHITE);
//								textBox.getDocument().remove(textBox.getDocument().getLength()-1, 1);
//							} catch (BadLocationException e) {
//								e.printStackTrace();
//							
//							}
//						}
//					}
//					keywordsInput.setText("Type keyword here");
//				}
//				else if(event.getSource() == clearButton) {
//					textBox.getStyledDocument().setCharacterAttributes(0, textBox.getDocument().getLength(), attrWHITE, false);
//					if (!map.isEmpty()) {
//						map.clear();
//						keywordPanel.removeAll();
//						keywordPanel.repaint();
//					}
//				}
//				else if(event.getSource() == viewButton) {
//					JOptionPane.showMessageDialog(null, "All Keywords" + map.keySet());
//				}
//				else if(event.getSource() == restart) {
//                 textBox.setText("");
//				}
//			}
//		};
//		addButton.addActionListener(handler);
//		clearButton.addActionListener(handler);
//		viewButton.addActionListener(handler);
//		restart.addActionListener(handler);
		
//	    f.add(panel);
//	    f.add(keywordPanel);
	    
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
