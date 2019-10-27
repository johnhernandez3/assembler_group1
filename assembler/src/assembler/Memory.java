package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import assembler.Converter;

public class Memory implements MemoryInterface {
	
//	Try to make it into a doubly linked list to traverse to the next and prev with ease
//	private class MemoryNode<T> {
//		private MemoryNode<T> curr;
//		private MemoryNode<T> prev;
//		private MemoryNode<T> next;
//		private MemoryLocation here;
//		
//		public MemoryNode(MemoryNode<T> prev, MemoryNode<T> curr, MemoryNode<T> next, MemoryLocation here) {
//			this.curr = curr;
//			this.next = next;
//			this.prev = prev;
//			this.here = here;
//		}
//
//		public MemoryNode<T> getCurr() {
//			return curr;
//		}
//
//		public void setCurr(MemoryNode<T> curr) {
//			this.curr = curr;
//		}
//
//		public MemoryNode<T> getPrev() {
//			return prev;
//		}
//
//		public void setPrev(MemoryNode<T> prev) {
//			this.prev = prev;
//		}
//
//		public MemoryNode<T> getNext() {
//			return next;
//		}
//
//		public void setNext(MemoryNode<T> next) {
//			this.next = next;
//		}
//
//		public MemoryLocation getHere() {
//			return here;
//		}
//
//		public void setHere(MemoryLocation here) {
//			this.here = here;
//		}
//		
//		
//	}
	
	
	public class MemoryLocation implements MemoryLocationInterface {
		
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
	ArrayList<String> Mem = new ArrayList<String>();
	ArrayList<MemoryLocation> memory = new ArrayList<>();
	
	// Create new memory location object which will act as the storage and control unit of the bits 
	// each location has. This idea was to implement the 'const' functionality.
	
	public Memory() {
//		for (int i = 0; i < 2048; i++) {
//	    	if (i < 10) {
//	    		memory.add(new MemoryLocation("0" + i, "00"));
//	    	} else {
//	    		memory.add(new MemoryLocation("" + i, "00"));
//	    	}
//	    }
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
	
	public MemoryLocation currAddr(int i) {
		return memory.get(i);
	}
	
	public String instrBefore(MemoryLocation addr) {
		return memory.get(Integer.parseInt(addr.getDirection()) - 2).getContent() + memory.get(Integer.parseInt(addr.getDirection()) - 1).getContent();
	}
	
	public void loadMemoryFromFile(String fileContent) {
		String nextDirectionContent = "";
		fileContent = fileContent.trim().replaceAll("\n||\t||\r", "");
		for (int i = 0; i < (fileContent.length() - 2); i+=2) {
			nextDirectionContent = fileContent.substring(i, i+2);
			if (!this.memory.isEmpty()) {
				if (this.memory.size() < 10) {
		    		memory.add(new MemoryLocation("0" + (this.memory.size() + ""), nextDirectionContent));
		    		System.out.println("Added: " + nextDirectionContent);
		    	} else {
		    		memory.add(new MemoryLocation("" + (this.memory.size() + ""), nextDirectionContent));
		    		System.out.println("Added: " + nextDirectionContent);
		    	}
			} else {
				memory.add(new MemoryLocation("00", nextDirectionContent));
	    		System.out.println("Added: " + nextDirectionContent);
			}
		}
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