package assembler;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import assembler.Memory.MemoryLocation;
import sun.misc.Queue;

public class Keyboard extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//JFrame && JPanel Declared
	private final JFrame f = new JFrame("Keyboard IO");
	private final JPanel keyboard = new JPanel();
	private JButton b;
	Converter c = new Converter();
	

	//Declare Two Dim Array to hold keys
	private static final String[][] key = {
			{"0","1", "2", "3"},
			{"4","5","6","7"},
			{"8","9","A","B"},
			{"C","D","E","F"},
	};
	/********************************************************************************************************
	 * 										Keyboard
	 *******************************************************************************************************/
	//Focuses on Keyboard appearance
	
	public Keyboard(GUI gui, int memLocation, Buffer buffer) {

		keyboard.setLayout(new GridBagLayout());
		Insets zeroInset = new Insets(0, 0, 0, 0);
		Font monospace = new Font(Font.MONOSPACED, Font.PLAIN, 12);
		
		JPanel pRow;
		//JButton b;

		GridBagConstraints cRow = new GridBagConstraints(),
				cButton = new GridBagConstraints();
		cRow.anchor = GridBagConstraints.WEST;
		cButton.ipady = 21;

		for(int row = 0, i = 0; row < key.length; ++row){
			pRow = new JPanel(new GridBagLayout());
			cRow.gridy = row;

			for (int col = 0; col < key[row].length; ++col, ++i) {
				switch (key[row][col]) {
				case " ":
					cButton.ipadx = 247;
					cButton.insets = new Insets(0, 192, 0, 72);
					break;

				default:
					cButton.ipadx = 9;
					cButton.insets = zeroInset;
			}
				b = new JButton(key[row][col]);
				b.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JButton btn = (JButton) e.getSource();
						ArrayList<String> s = new ArrayList<String>();
						s.add(btn.getText());
						String hex = s.get(0);
						String memContent = gui.memory.getMemoryDirection(memLocation).getContent();
						if (buffer.buffer.size() == 4) {
							gui.log("Keyboard buffer is full!\n");
						} else if (memContent.length() < 2) {
							gui.memory.getMemoryDirection(memLocation).setContent(memContent + hex);
						} else {
							buffer.enqueueBuffer(hex);
						}
						gui.updateMemoryTable();
						onButtonPress(hex);
						s.remove(0);
					}
					
				});
				b.setFont(monospace);
				b.setFocusable(false);
				pRow.add(b, cButton);			
			}
			keyboard.add(pRow, cRow);
			
		
		}
		
		f.add(keyboard);
	}
	/***********************************************************************************************************
	 * 											Event Handler
	 * @param text 
	 ***********************************************************************************************************/
	

	
	private void onButtonPress(String text){
//		String text = b.getText();
//		System.out.println(text);
		System.out.println(text + ": " + c.hextoBin(text.toLowerCase()));
		
	}
	
	
	/******************************************************************************************************************
	 * 										Launch JFrame
	 ******************************************************************************************************************/
	public void launch() {
		f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		f.pack();
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	/**********************************************************************************************************************
	 * 										Main
	 ********************************************************************************************************************/
//	public static void main(String[] args) {
//		Keyboard ui = new Keyboard();
//		ui.launch();
//		
//	}
}
