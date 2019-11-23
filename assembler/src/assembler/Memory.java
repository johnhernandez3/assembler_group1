package assembler;

import java.util.ArrayList;
import assembler.Converter;

public class Memory implements MemoryInterface {
	/***********************************************************************************************************************************************************************************************
	 * 																				Memory Location
	 *********************************************************************************************************************************************************************************************/
	public class MemoryLocation implements MemoryLocationInterface {

		public String direction;
		public String content;

		public MemoryLocation(String direction, String content) {
			this.direction = direction;
			this.content = content;
		}

		@Override
		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}

		@Override
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
		
		@Override
		public String toString() {
			return "Direction: " + this.direction + " Content: " + this.content;
		}
	}

	String address; 
	ArrayList<String> Mem = new ArrayList<String>();
	ArrayList<MemoryLocation> memory = new ArrayList<>();

/****************************************************************************************************************************************************************************************************
 * 																				New Memory
 ****************************************************************************************************************************************************************************************************/
	int next;
	MemoryLocation[] directions;
	MemoryLocation currDirection;

	//Memory Constructor
	public Memory() {
		directions = this.initializeMem();
		next = 0;
		currDirection = directions[next];
	}

	// Memory Initialization
	private MemoryLocation[] initializeMem() {
		String direction, content = "";
		MemoryLocation[] result = new MemoryLocation[2048];
		for (int i = 0; i < 2048; i++) {
			if (i < 10) {
				direction = "0" + i;
			} else {
				direction = i + "";
			}
			result[i] = new MemoryLocation(direction, content);
		}
		return result;
	}
	
	
/***********************************************************************************************************************************************************************************************
 * 																			Getter and Setter
 ********************************************************************************************************************************************************************************************/
	public MemoryLocation getMemoryDirection(int i) {
		return this.directions[i];
	}

	public void setNextDirection(int i) {
		this.validate(i);
		for (int j = next; j < i; j++) {
			this.directions[j].setContent("00");
		}
		next = i;
		currDirection = this.directions[i];
	}

	//Helper Method
	public Boolean validate(int i) {
		return i >= 0 && i < 2048;
	}
	
	/**********************************************************************************************************************************************************************************************
	 * 																	Code Before New Memory Implementation
	 * 																	Ask Cristian if usable 
	 */
	@Override
	public Object[][] memData() {
		Object[][] data = new Object[2048][2];

		int l = 0;
		for (MemoryLocation d : this.directions) {
			data[l][0] = d.getDirection();
			l++;
		}
		l = 0;
		for (MemoryLocation c : this.directions) {
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
		int c = 0;
		fileContent = fileContent.trim().replaceAll("\n||\t||\r", "");
		for (int i = 0; i < fileContent.length(); i+=2) {
			nextDirectionContent = fileContent.substring(i, i+2);
			if (!nextDirectionContent.matches("^[a-fA-F0-9]+$")) {
				// return Error
			}
			this.directions[c].setContent(nextDirectionContent.toUpperCase());
			c++;
			System.out.println("Added: " + nextDirectionContent.toUpperCase());
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

	public int getNextAvailableMemoryDirection() {
		int result = next;
		for (MemoryLocation d : this.directions) {
			if (d.getContent().isEmpty()) {
				return result;
			} else {
				result++;
			}
		}
		return result;
	}

//	public String GetLastEvenbit() { 
//		//Gets the last bit of the previous "even" entry in the memory.
//		//if mem size = 3(Index = 2) , itll return the last bit of the first entry (index = 0).
//
//		String result = "";
//		if (Mem.size() > 1 && Mem.size() %2 == 0) {
//			String s = Mem.get(Mem.size() -2);
//			result = String.valueOf(s.charAt(s.length()-1));
//		} else if (Mem.size() > 1 && Mem.size()%2 !=0) {
//			String s = Mem.get(Mem.size()-3);
//			result = String.valueOf(s.charAt(s.length()-1));
//		}
//		return result;
//	}
}



