
package assembler;

import java.util.HashMap;

public class Register {
	
//	Initialize opcode, register map and map register values, initializes r0 with 00(bytes)
	private InstructionSet opcodes = new InstructionSet();
	
	private HashMap<String, String> regs = new HashMap<String, String>();

	public String sp = "";
	public String pc = "";

	public Register() {
		regs.put("r0", "00");
		regs.put("r1", "");
		regs.put("r2", "");
		regs.put("r3", "");
		regs.put("r4", "");
		regs.put("r5", "");
		regs.put("r6", "");
		regs.put("r7", "");
	}
	
//	Arithmetic Logic
	public String add(String a, String b) {
		return a + b;
	}
	
//	End Arithmetic Logic
	
//	Logic Operators
	public String and(String a, String b) {
		return a;
	}
	
//	End Logic Operators
	
}