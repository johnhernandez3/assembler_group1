package assembler;

import java.util.HashMap;
import java.util.Map;

public class InstructionSet {
	
//	Initialize Map and map each code to its number in binary.	
	private Map<String, Instruction> map = new HashMap<>();
	
//	Possible Tests:
//	1. Test that every opcode's word representation returns the correct 5-bit binary code
	
	public InstructionSet() {
		map.put("load", new Instruction(2, "00000", 2));
		map.put("loadim", new Instruction(2, "00001", 2));
		map.put("pop", new Instruction(2, "00010", 1));
		map.put("store", new Instruction(2, "00011", 2));
		map.put("push", new Instruction(2, "00100", 1));
		map.put("loadrind", new Instruction(1, "00101", 2));
		map.put("storerind", new Instruction(1, "00110", 2));
		map.put("add", new Instruction(1, "00111", 3));
		map.put("sub", new Instruction(1, "01000", 3));
		map.put("addim", new Instruction(2, "01001", 2));
		map.put("subim", new Instruction(2, "01010", 2));
		map.put("and", new Instruction(1, "01011", 3));
		map.put("or", new Instruction(1, "01100", 3));
		map.put("xor", new Instruction(1, "01101", 3));
		map.put("not", new Instruction(1, "01110", 2));
		map.put("neg", new Instruction(1, "01111", 2));
		map.put("shiftr", new Instruction(1, "10000", 3));
		map.put("shiftl", new Instruction(1, "10001", 3));
		map.put("rotar", new Instruction(1, "10010", 3));
		map.put("rotal", new Instruction(1, "10011", 3));
		map.put("jmprind", new Instruction(1, "10100", 1));
		map.put("jmpaddr", new Instruction(3, "10101", 1));
		map.put("jcondrin", new Instruction(3, "10110", 1));
		map.put("jcondaddr", new Instruction(3, "10111", 1));
		map.put("loop", new Instruction(2, "11000", 2));
		map.put("grt", new Instruction(1, "11001", 2));
		map.put("grteq", new Instruction(1, "11010", 2));
		map.put("eq", new Instruction(1, "11011", 2));
		map.put("neq", new Instruction(1, "11100", 2));
		map.put("nop", new Instruction(1, "11101", 0));
		map.put("call", new Instruction(3, "11110", 1));
		map.put("return", new Instruction(0, "11111", 0));
	}
	
	/**
	 **For public use:
	 ** @return map to use
	 ** 
	 **/
	public Map<String, Instruction> getMap() {
		return this.map;
	}
	
	public Instruction getInstruction(String key) {
		return this.map.get(key);
	}
	
	/**
	 **BinaryReturn:
	 **@param possible opcode 
	 **@return binary formatted bits of specific opcode
	 ** 
	 **/
	public String BinaryReturn(String str) {
		if(str.equals("org")) return "";
		return map.get(str).getOpcode();
	}

}
