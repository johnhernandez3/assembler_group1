package assembler;

import java.util.HashMap;

public class InstructionSet {
	
//	Initialize Map and map each code to its number in binary.	
	private HashMap<String, String> map = new HashMap<String, String>();
	
	public InstructionSet() {
		map.put("load", "00000"); // new Instruction(1, "load", null)
		map.put("loadim", "00001");
		map.put("pop", "00010");
		map.put("store", "00011");
		map.put("push", "00100");
		map.put("loadrind", "00101");
		map.put("storerind", "00110");
		map.put("add", "00111");
		map.put("sub", "01000");
		map.put("addim", "01001");
		map.put("subim", "01010");
		map.put("and", "01011");
		map.put("or", "01100");
		map.put("xor", "01101");
		map.put("not", "01110");
		map.put("neg", "01111");
		map.put("shiftr", "10000");
		map.put("shiftl", "10001");
		map.put("rotar", "10010");
		map.put("rotal", "10011");
		map.put("jmprind", "10100");
		map.put("jmpaddr", "10101");
		map.put("jcondrin", "10110");
		map.put("jcondaddr", "10111");
		map.put("loop", "11000");
		map.put("grt", "11001");
		map.put("grteq", "11010");
		map.put("eq", "11011");
		map.put("neq", "11100");
		map.put("nop", "11101");
		map.put("call", "11110");
		map.put("return", "11111");
	}
	
	/**
	 **For public use:
	 ** @return map to use
	 ** 
	 **/
	public HashMap<String, String> getMap() {
		return this.map;
	}
	
	/**
	 **BinaryReturn:
	 **@param possible opcode 
	 **@return binary formatted bits of specific opcode
	 ** 
	 **/
	public String BinaryReturn(String str) {
		return map.get(str.toLowerCase());
	}

}
