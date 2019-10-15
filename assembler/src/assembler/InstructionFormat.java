package assembler;

import java.util.ArrayList;

public class InstructionFormat {
	
	private InstructionSet opcodes = new InstructionSet();
	private ArrayList<Instruction> instructions;
	
	public InstructionFormat() {
		this.instructions = new ArrayList<>();
	}
	
	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<Instruction> instructions) {
		this.instructions = instructions;
	}
	
	public void addInstruction(ArrayList<Token> t) {
		// Create method to verify each token's type and classify the instruction (f1,f2,f3)
		
		// Inside that method also verify the token order to
		// throw the necessary warnings and/or error messages
	}

	public String Formatted1(String op, String rega, String regb, String regc) {
		return opcodes.BinaryReturn(op) + rega + "," + regb + "," + regc;
	}
	
	public String Formatted2(String op, String reg, String addr) {
		return opcodes.BinaryReturn(op) + reg + "," + addr;
	}
	
	public String Formatted3(String op, String addr) {
		return opcodes.BinaryReturn(op) + addr;
	}
}