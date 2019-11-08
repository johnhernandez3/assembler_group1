package assembler;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class IO3 {
	
	public static char[] result = new char [8];
	public static ArrayList<String> ref = new ArrayList<>();
	private static Object[][] IO3 = new Object[9][2];
	private static String[] columnNames = { "Direction", "Content" };
	private static int DirRows = 0;
	private static int DirectionReference = 40;

	    
	    
	public static void main(String[] args) {
	 	ref.add("01000011");
		ref.add("01011111");
		ref.add("01110110");
		ref.add("01000100");
		ref.add("01110101");
		ref.add("01100011");
		ref.add("01110111");
		ref.add("01100000");
      
		
		JFrame frame = initializeFrame();
		JTable table1 = TableCreation();
		frame.add(table1);
	

	}
	
	
	private static JFrame initializeFrame() {
		JFrame frame = new JFrame();
		frame.setTitle("ASCII Convert");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}
	
	private static JTable TableCreation() {
		
        JTable table1 =new JTable(IO3, columnNames);
        DefaultTableModel model = new DefaultTableModel(IO3, columnNames);
        table1.setModel(model);
		table1.setBackground(Color.gray);
		table1.setBounds(5,20, 200, 150);
		table1.setFont(new Font("Tahome",Font.ITALIC,14));
		table1.setGridColor(Color.BLUE);
		
		for(int i = 0; i < 8 ; i++) {
			model.setValueAt(ref.get(i), DirRows, 1);
			 DirRows++;
		}
		DirRows = 0;
		for(int i = 0; i < 8 ; i++) {
			model.setValueAt(DirectionReference, DirRows, 0);
			 DirRows++;
			 DirectionReference++;
		}
		model.setValueAt("Ascii result:", 8, 0);
		model.setValueAt(AsciConversion(ref,result), 8, 1);
		return table1;

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
