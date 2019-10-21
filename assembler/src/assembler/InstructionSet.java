package assembler;

import java.util.HashMap;
import java.util.Map;

public class InstructionSet {
	
//	Initialize Map and map each code to its number in binary.	
	private Map<String, Instruction> map = new HashMap<>();
	
//	Possible Tests:
//	1. Test that every opcode's word representation returns the correct 5-bit binary code
	
	public InstructionSet() {
		map.put("load", new Instruction(2, "00000"));
		map.put("loadim", new Instruction(2, "00001"));
		map.put("pop", new Instruction(2, "00010"));
		map.put("store", new Instruction(2, "00011"));
		map.put("push", new Instruction(2, "00100"));
		map.put("loadrind", new Instruction(1, "00101"));
		map.put("storerind", new Instruction(1, "00110"));
		map.put("add", new Instruction(1, "00111"));
		map.put("sub", new Instruction(1, "01000"));
		map.put("addim", new Instruction(2, "01001"));
		map.put("subim", new Instruction(2, "01010"));
		map.put("and", new Instruction(1, "01011"));
		map.put("or", new Instruction(1, "01100"));
		map.put("xor", new Instruction(1, "01101"));
		map.put("not", new Instruction(1, "01110"));
		map.put("neg", new Instruction(1, "01111"));
		map.put("shiftr", new Instruction(1, "10000"));
		map.put("shiftl", new Instruction(1, "10001"));
		map.put("rotar", new Instruction(1, "10010"));
		map.put("rotal", new Instruction(1, "10011"));
		map.put("jmprind", new Instruction(1, "10100"));
		map.put("jmpaddr", new Instruction(3, "10101"));
		map.put("jcondrin", new Instruction(3, "10110"));
		map.put("jcondaddr", new Instruction(3, "10111"));
		map.put("loop", new Instruction(2, "11000"));
		map.put("grt", new Instruction(1, "11001"));
		map.put("grteq", new Instruction(1, "11010"));
		map.put("eq", new Instruction(1, "11011"));
		map.put("neq", new Instruction(1, "11100"));
		map.put("nop", new Instruction(1, "11101"));
		map.put("call", new Instruction(3, "11110"));
		map.put("return", new Instruction(0, "11111"));
	}
	
	/**
	 **For public use:
	 ** @return map to use
	 ** 
	 **/
	public Map<String, Instruction> getMap() {
		return this.map;
	}
	
	/**
	 **BinaryReturn:
	 **@param possible opcode 
	 **@return binary formatted bits of specific opcode
	 ** 
	 **/
	public String BinaryReturn(String str) {
		return map.get(str.toLowerCase()).getOpcode();
	}

}
