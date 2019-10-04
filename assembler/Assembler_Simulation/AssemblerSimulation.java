
import java.awt.Color; 
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


public class Main {
	private static JPanel panel;
	private static JPanel keywordPanel;
	private static JPanel keyboardPanel;
	private static JLabel keyWordLabel;
	private static JTextField keywordsInput;
	private static JButton addButton;
	private static JButton clearButton;
	private static JButton viewButton;
	
	private static JButton zeroButton;
	private static JButton oneButton;
	private static JButton twoButton;
	private static JButton threeButton;
	private static JButton fourButton;
	private static JButton fiveButton;
	private static JButton sixButton;
	private static JButton sevenButton;
	private static JButton eightButton;
	private static JButton nineButton;
	private static JButton AButton;
	private static JButton BButton;
	private static JButton CButton;
	private static JButton DButton;
	private static JButton EButton;
	private static JButton FButton;
	
	private static String kw;
	private static HashMap<String, Color> map = new HashMap<String, Color>();
	private static DocumentListener listener;
	private static JTextPane textBox;
	private static SimpleAttributeSet attrWHITE;

	public static void main(String[] args) {
		
		JFrame f = new JFrame("IDE TextBox");
	    
	    textBox = new JTextPane();
	    textBox.setMargin(new Insets(10,10,10,10));
	    
	    attrWHITE = new SimpleAttributeSet();
	    StyleConstants.setForeground(attrWHITE, Color.WHITE);
	    
	    listener = new DocumentListener() {
	    	
	    	private void highlight(StyledDocument styledDocument) throws BadLocationException {
	    		StyledDocument document = textBox.getStyledDocument();
	            String text = document.getText(0, document.getLength());

	            int startIndex;
	            int start;

	            StyleContext context = StyleContext.getDefaultStyleContext();

	            Style styleDefault = context.getStyle(StyleContext.DEFAULT_STYLE);
	            styledDocument.setCharacterAttributes(0, text.length(), styleDefault, true);

	            for (String keyword : map.keySet()) {
	                startIndex = 0;
	                start = text.indexOf(keyword, startIndex);
	                while (start >= 0) {
	                	AttributeSet attr = context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, map.get(keyword));
	                    styledDocument.setCharacterAttributes(start, keyword.length(), attr, true);
	                    startIndex += keyword.length();
	                    start = text.indexOf(keyword, startIndex);
	                }
	            }
	        }
	    	
	    	private void changeText() {
                Runnable doChange = new Runnable() {
                    @Override
                    public void run() {
                    	try {
							highlight(textBox.getStyledDocument());
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
                    }
                };
                SwingUtilities.invokeLater(doChange);
            }
	    	
			@Override
			public void insertUpdate(DocumentEvent e) {
				changeText();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changeText();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {}
	    };
	    textBox.setBackground(Color.DARK_GRAY);
	    textBox.setForeground(Color.WHITE);
	    textBox.setCaretColor(Color.WHITE);
	    
	    textBox.getDocument().addDocumentListener(listener);
	    
	    textBox.setBounds(0, 0, 725, 725);
	    
	    buildPanel();
	    
	    f.add(panel);
	    f.add(keywordPanel);
	    f.add(keyboardPanel);
	    
	    JScrollPane scrollPane = new JScrollPane(textBox);
	    scrollPane.setBounds(0, 0, 800, 775);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    f.add(scrollPane);
	    
	    f.setSize(1400, 800);
	    
	    f.setLayout(null);
	    
	    f.setVisible(true);
	    
	    
	}
	
	public static void buildPanel() {
		
		panel = new JPanel();
		keywordPanel = new JPanel();
		keyboardPanel = new JPanel();
		
		panel.setBounds(805, 0, 600, 100);
	    keywordPanel.setBounds(400, 50, 300, 300);
	    keyboardPanel.setBounds(805, 100, 600, 675);
	    
	    panel.setBackground(Color.DARK_GRAY);
	    keywordPanel.setBackground(Color.DARK_GRAY);
	    keyboardPanel.setBackground(Color.DARK_GRAY);
	    
		addButton = new JButton("Add");
		clearButton = new JButton("Clear Keywords");
		viewButton = new JButton("View Keywords");
		keywordsInput = new JTextField(15);
		
		zeroButton = new JButton("0");
		oneButton = new JButton("1");
		twoButton = new JButton("2");
		threeButton = new JButton("3");
		fourButton = new JButton("4");
		fiveButton = new JButton("5");
		sixButton = new JButton("6");
		sevenButton = new JButton("7");
		eightButton = new JButton("8");
		nineButton = new JButton("9");
		AButton = new JButton("A");
		BButton = new JButton("B");
		CButton = new JButton("C");
		DButton = new JButton("D");
		EButton = new JButton("E");
		FButton = new JButton("F");
		
		panel.add(keywordsInput);
		panel.add(addButton);
		panel.add(clearButton);
		panel.add(viewButton);
		
		keyboardPanel.add(zeroButton);
		keyboardPanel.add(oneButton);
		keyboardPanel.add(twoButton);
		keyboardPanel.add(threeButton);
		keyboardPanel.add(fourButton);
		keyboardPanel.add(fiveButton);
		keyboardPanel.add(sixButton);
		keyboardPanel.add(sevenButton);
		keyboardPanel.add(eightButton);
		keyboardPanel.add(nineButton);
		keyboardPanel.add(AButton);
		keyboardPanel.add(BButton);
		keyboardPanel.add(CButton);
		keyboardPanel.add(DButton);
		keyboardPanel.add(EButton);
		keyboardPanel.add(FButton);
		
		keywordsInput.setText("Type keyword here");
		
		keywordsInput.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				JTextField source = (JTextField) e.getComponent();
		        source.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				JTextField source = (JTextField) e.getComponent();
		        source.setText("Type keyword here");
			}
		});
		
		keywordsInput.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addButton.doClick();
					keywordsInput.setText("");
				}
				
			}

			@Override 
			public void keyReleased(KeyEvent e) {}
			
		});
		
		ActionListener handler = new ActionListener() {
			private Color genColor() {
				Random rnd = new Random();
				float r = rnd.nextFloat();
				float g = rnd.nextFloat();
				float b = rnd.nextFloat();
				Color c = new Color(r, g, b);
				if (map.containsValue(c)) {
					r = rnd.nextFloat();
					g = rnd.nextFloat();
					b = rnd.nextFloat();
					c = new Color(r, g, b);
				}
				if (r+g+b == 0 || r+g+b == (255*3) || r+g+b == (102*3)) {
					r = rnd.nextFloat();
					g = rnd.nextFloat();
					b = rnd.nextFloat();
				}
				return c;
			}
			public void actionPerformed(ActionEvent event) {
				
				if(event.getSource() == addButton) {
					kw = keywordsInput.getText();
					System.out.println("Keyword Input: " + kw);
					if (!kw.isEmpty()) {
						if (!map.containsKey(kw)) {
							keyWordLabel = new JLabel(kw);
							keywordPanel.add(keyWordLabel);
							Color color = genColor();
							map.put(kw, color);
							keyWordLabel.setForeground(color);
							keyWordLabel.revalidate();
							try {
								textBox.getDocument().insertString(textBox.getDocument().getLength(), " ", attrWHITE);
								textBox.getDocument().remove(textBox.getDocument().getLength()-1, 1);
							} catch (BadLocationException e) {
								e.printStackTrace();
							}
						}
					}
					keywordsInput.setText("Type keyword here");
				}
				else if(event.getSource() == clearButton) {
					textBox.getStyledDocument().setCharacterAttributes(0, textBox.getDocument().getLength(), attrWHITE, false);
					if (!map.isEmpty()) {
						map.clear();
						keywordPanel.removeAll();
						keywordPanel.repaint();
					}
				}
				else if(event.getSource() == viewButton) {
					JOptionPane.showMessageDialog(null, "All Keywords" + map.keySet());
				}
			}
		};
		ActionListener keyboardhandler = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				if(event.getSource() == zeroButton) {
					System.out.println("0");
				}
				else if(event.getSource() == oneButton) {
					System.out.println("1");
				}
				else if(event.getSource() == twoButton) {
					System.out.println("2");
				}
				else if(event.getSource() == threeButton) {
					System.out.println("3");
				}
				else if(event.getSource() == fourButton) {
					System.out.println("4");					
				}
				else if(event.getSource() == fiveButton) {
					System.out.println("5");
				}
				else if(event.getSource() == sixButton) {
					System.out.println("6");
				}
				else if(event.getSource() == sevenButton) {
					System.out.println("7");
				}
				else if(event.getSource() == eightButton) {
					System.out.println("8");
				}
				else if(event.getSource() == nineButton) {
					System.out.println("9");
				}
				else if(event.getSource() == AButton) {
					System.out.println("A");
				}
				else if(event.getSource() == BButton) {
					System.out.println("B");
				}
				else if(event.getSource() == CButton) {
					System.out.println("C");
				}
				else if(event.getSource() == DButton) {
					System.out.println("D");
				}
				else if(event.getSource() == EButton) {
					System.out.println("E");
				}
				else if(event.getSource() == FButton) {
					System.out.println("F");
				}
			}
		};
		addButton.addActionListener(handler);
		clearButton.addActionListener(handler);
		viewButton.addActionListener(handler);
		
		zeroButton.addActionListener(keyboardhandler);
		oneButton.addActionListener(keyboardhandler);
		twoButton.addActionListener(keyboardhandler);
		threeButton.addActionListener(keyboardhandler);
		fourButton.addActionListener(keyboardhandler);
		fiveButton.addActionListener(keyboardhandler);
		sixButton.addActionListener(keyboardhandler);
		sevenButton.addActionListener(keyboardhandler);
		eightButton.addActionListener(keyboardhandler);
		nineButton.addActionListener(keyboardhandler);
		AButton.addActionListener(keyboardhandler);
		BButton.addActionListener(keyboardhandler);
		CButton.addActionListener(keyboardhandler);
		DButton.addActionListener(keyboardhandler);
		EButton.addActionListener(keyboardhandler);
		FButton.addActionListener(keyboardhandler);
	}

}
