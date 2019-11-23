package assembler;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import assembler.IO2_SevenSegmentDisplay.SevenSegments;

public class IO3 {
	
	public static char[] result = new char [8];
	private static Object[][] IO3 = new Object[9][2];
	private static String[] columnNames = { "Direction", "Content" };
	private static JFrame frame = new JFrame();
	private static ASCII convert = new ASCII();
    private static ArrayList<String> ref = new ArrayList<>();

	    
	public static void main(String[] args) throws InterruptedException {
	
      
		ref.add("01000011");
		ref.add("01011111");
		ref.add("01110110");
		ref.add("01000100");
		ref.add("01110101");
		ref.add("01100011");
		ref.add("01110111");
		ref.add("01100000");
		JFrame frame = initializeFrame();
		ASCII convert = new ASCII();
		frame.add(ASCII.table1);
	
       convert.FillTable(ref);
	}
	public ASCII getAscii() {
		return convert;
	}
	
	
	private static JFrame initializeFrame() {
		JFrame frame = new JFrame();
		frame.setTitle("ASCII Convert");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}
	
     static class ASCII extends JTable {
    	 
 		private static final long serialVersionUID = 1L;
        private static JTable table1 = new JTable(IO3, columnNames);
        private static DefaultTableModel model;
        private static int DirRows = 0;
    	private static int DirectionReference = 40;
  
        
        private static void main(String[] args) {
        	
           }
        
        public ASCII() {
        	this.model = new DefaultTableModel(IO3, columnNames);
        	table1.setModel(model);
        	this.table1.setBackground(Color.gray);
        	this.table1.setBounds(5,20, 200, 150);
      		this.table1.setFont(new Font("Tahome",Font.ITALIC,14));
      		this.table1.setGridColor(Color.BLUE);
        }
    
        public static void FillTable(ArrayList<String> data) throws InterruptedException {
        	
    		for(int i = 0; i < 8 ; i++) {
    			table1.setValueAt(ref.get(i), DirRows, 1);
    			 DirRows++;
    		}
    		DirRows = 0;
    		for(int i = 0; i < 8 ; i++) {
    			table1.setValueAt(DirectionReference, DirRows, 0);
    			 DirRows++;
    			 DirectionReference++;
    		}
    		table1.setValueAt("Ascii result:", 8, 0);
    		table1.setValueAt(AsciConversion(ref,result), 8, 1);
        }
        
        
        public void launch() {
			frame.add(table1);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.setSize(250,300);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
		
     }

	public static String AsciConversion(ArrayList<String> data , char[] data2) {
		int bintoDec;
		String bin;
		for(int i = 0; i < data.size(); i++) {
			bin = data.get(i);
			bintoDec =  Integer.parseInt(bin,2);
			data2[i] = (char)bintoDec;
		}
	    String result = String.valueOf(data2);
			
		
		return result;
	}
}
