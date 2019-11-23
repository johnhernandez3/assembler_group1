package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructionSet {
	
//	Initialize Map and map each code to its number in binary.	
	private Map<String, Instruction> map = new HashMap<>();
	
//	Possible Tests:
//	1. Test that every opcode's word representation returns the correct 5-bit binary code
	
	public InstructionSet() {
		map.put("load", new Instruction(2, "00000", 2, new ArrayList<>()));
		map.put("loadim", new Instruction(2, "00001", 2, new ArrayList<>()));
		map.put("pop", new Instruction(2, "00010", 1, new ArrayList<>()));
		map.put("store", new Instruction(2, "00011", 2, new ArrayList<>()));
		map.put("push", new Instruction(2, "00100", 1, new ArrayList<>()));
		map.put("loadrind", new Instruction(1, "00101", 2, new ArrayList<>()));
		map.put("storerind", new Instruction(1, "00110", 2, new ArrayList<>()));
		map.put("add", new Instruction(1, "00111", 3, new ArrayList<>()));
		map.put("sub", new Instruction(1, "01000", 3, new ArrayList<>()));
		map.put("addim", new Instruction(2, "01001", 2, new ArrayList<>()));
		map.put("subim", new Instruction(2, "01010", 2, new ArrayList<>()));
		map.put("and", new Instruction(1, "01011", 3, new ArrayList<>()));
		map.put("or", new Instruction(1, "01100", 3, new ArrayList<>()));
		map.put("xor", new Instruction(1, "01101", 3, new ArrayList<>()));
		map.put("not", new Instruction(1, "01110", 2, new ArrayList<>()));
		map.put("neg", new Instruction(1, "01111", 2, new ArrayList<>()));
		map.put("shiftr", new Instruction(1, "10000", 3, new ArrayList<>()));
		map.put("shiftl", new Instruction(1, "10001", 3, new ArrayList<>()));
		map.put("rotar", new Instruction(1, "10010", 3, new ArrayList<>()));
		map.put("rotal", new Instruction(1, "10011", 3, new ArrayList<>()));
		map.put("jmprind", new Instruction(1, "10100", 1, new ArrayList<>()));
		map.put("jmpaddr", new Instruction(3, "10101", 1, new ArrayList<>()));
		map.put("jcondrin", new Instruction(3, "10110", 1, new ArrayList<>()));
		map.put("jcondaddr", new Instruction(3, "10111", 1, new ArrayList<>()));
		map.put("loop", new Instruction(2, "11000", 2, new ArrayList<>()));
		map.put("grt", new Instruction(1, "11001", 2, new ArrayList<>()));
		map.put("grteq", new Instruction(1, "11010", 2, new ArrayList<>()));
		map.put("eq", new Instruction(1, "11011", 2, new ArrayList<>()));
		map.put("neq", new Instruction(1, "11100", 2, new ArrayList<>()));
		map.put("nop", new Instruction(1, "11101", 0, new ArrayList<>()));
		map.put("call", new Instruction(3, "11110", 1, new ArrayList<>()));
		map.put("return", new Instruction(0, "11111", 0, new ArrayList<>()));
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
		return map.get(str).getOpcode();
	}

}
