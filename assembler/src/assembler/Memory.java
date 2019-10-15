package assembler;

import java.util.ArrayList;
import assembler.Converter;

public class Memory extends Converter {
	
	String address; 
	String setter = "0000000000000000";
	ArrayList<String> Mem = new ArrayList<String>();
	
	// Create new memory location object which will act as the storage and control unit of the bits 
	// each location has. This idea was to implement the 'const' functionality.
	
	
	public Memory(String inst) {
		super(inst);
//		Address (String name, int id) { 
//			this.address = address; 
//		}


//    if(Instruction || data) {
    // Instruction or data refers to when something is added to the memory
    //since code was not finished , I left this part of the logic as pseudocode to change it afterwards.
    // When something is added , itll set every empty/null value on the memory to 0's.
    
    for(String s : Mem) {
    	if(s.isEmpty()) {
    		s = setter;
    	}
    }
//    if((Instruction || data) && Mem.size() == 2048) {
//    	throw new IndexOutOfBoundsException();
//    }
}

	public String MemtoHex() {
		//Prints the content in the memory as separate lines of Hexadecimals values.
		 String str = "";
		 String hexChange = "";
		 for (String s : Mem) {
		   	
		   	hexChange = binToHex(s);
		     
		    str+= System.lineSeparator() + hexChange;
		       
		}
		return str;
	}

	public String GetLastEvenbit() { 
		//Gets the last bit of the previous "even" entry in the memory.
		//if mem size = 3(Index = 2) , itll return the last bit of the first entry (index = 0).
		
		String result = "";
		if (Mem.size() > 1 && Mem.size() %2 == 0) {
			String s = Mem.get(Mem.size() -2);
			result = String.valueOf(s.charAt(s.length()-1));
		} else if (Mem.size() > 1 && Mem.size()%2 !=0) {
			String s = Mem.get(Mem.size()-3);
			result = String.valueOf(s.charAt(s.length()-1));
		}
		return result;
	}
}