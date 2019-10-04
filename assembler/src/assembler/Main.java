package assembler;

//Here we have all the imports from java libraries needed for this project
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
	// Next off, we add the following global variables
	private static JPanel panel;
	private static JPanel keywordPanel;
	private static JLabel keyWordLabel;
	private static JTextField keywordsInput;
	private static JButton addButton;
	private static JButton clearButton;
	private static JButton viewButton;
	private static JButton restart;
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
		
		panel.setBounds(805, 0, 600, 100);
	    keywordPanel.setBounds(805, 100, 600, 675);
	    
	    panel.setBackground(Color.DARK_GRAY);
	    keywordPanel.setBackground(Color.DARK_GRAY);
	    
		addButton = new JButton("Add");
		clearButton = new JButton("Clear Keywords");
		viewButton = new JButton("View Keywords");
		restart = new JButton("New textbox");

		keywordsInput = new JTextField(15);
		
		panel.add(keywordsInput);
		panel.add(addButton);
		panel.add(clearButton);
		panel.add(viewButton);
		panel.add(restart);

		keywordsInput.setText("Type keyword here");
		
		keywordsInput.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				JTextField source = (JTextField) e.getComponent();
		        source.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				
		            
					//JTextField source = (JTextField) e.getComponent();
			       // source.setText("Type keyword here");
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
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearButton.doClick();
					keywordsInput.setText("");	
				}}
			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		ActionListener handler = new ActionListener() {
			private Color genColor() {
				Random rnd = new Random();
				float r = rnd.nextFloat();
				float g = rnd.nextFloat();
				float b = rnd.nextFloat();
				Color c = new Color(r, g, b).brighter();
				if (map.containsValue(c)) {
					r = rnd.nextFloat();
					g = rnd.nextFloat();
					b = rnd.nextFloat();
					c = new Color(r, g, b).brighter();
				}
				if (r+g+b == 0 || r+g+b == (765) || r+g+b == (306)) {
					r = rnd.nextFloat();
					g = rnd.nextFloat();
					b = rnd.nextFloat();
					c = new Color(r, g, b).brighter();
				}
				return c;
			}
			 public void infoBox()
			    {
				 String title = "Error Message";
				 String info = "Please type a keyword";
			        JOptionPane.showMessageDialog(null, info, "Moron Alert: " + title, JOptionPane.INFORMATION_MESSAGE);
			    }
			
			public void actionPerformed(ActionEvent event) {
				if(event.getSource() == addButton) {
					kw = keywordsInput.getText();
					String Default1 = "Type keyword here";
					String Default2 = "";

					if(kw.equals(Default1) || kw.equals(Default2)) {
						infoBox();
						return;
					}
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
				else if(event.getSource() == restart) {
                 textBox.setText("");
				}
			}
		};
		addButton.addActionListener(handler);
		clearButton.addActionListener(handler);
		viewButton.addActionListener(handler);
		restart.addActionListener(handler);

	}
}
