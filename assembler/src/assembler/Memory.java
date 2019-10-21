package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import assembler.Converter;

public class Memory {
	
	public class MemoryLocation {
		
		public String direction;
		public String content;
		
		public MemoryLocation(String direction, String content) {
			this.direction = direction;
			this.content = content;
		}
		
		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
	
	String address; 
	String setter = "0000000000000000";
	ArrayList<String> Mem = new ArrayList<String>();
	ArrayList<MemoryLocation> memory = new ArrayList<>();
	
	// Create new memory location object which will act as the storage and control unit of the bits 
	// each location has. This idea was to implement the 'const' functionality.
	
	public Memory() {
		for (int i = 0; i < 2048; i++) {
	    	if (i < 10) {
	    		memory.add(new MemoryLocation("0" + i, "00"));
	    	} else {
	    		memory.add(new MemoryLocation("" + i, "00"));
	    	}
	    }
	}
	
	public Memory(String inst) {
    
	    for(String s : Mem) {
	    	if(s.isEmpty()) {
	    		s = setter;
	    	}
	    }
	}
	
	public Object[][] memData() {
		Object[][] data = new Object[2048][2];
		int l = 0;
		for (MemoryLocation d : this.memory) {
			data[l][0] = d.getDirection();
			l++;
		}
		l = 0;
		for (MemoryLocation c : this.memory) {
			data[l][1] = c.getContent();
			l++;
		}
		
		return data;
	}

	public String MemtoHex() {
		//Prints the content in the memory as separate lines of Hexadecimals values.
		 String str = "";
		 String hexChange = "";
		 Converter c = new Converter();
		 for (String s : Mem) {
		   	
		   	hexChange = c.binToHex(s);
		     
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