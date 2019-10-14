package assembler;

import java.util.ArrayList;

public class InstructionFormat {
	
	private InstructionSet opcodes = new InstructionSet();
	private Register regloc = new Register();
	
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
		
	}
	
	
	
	
//	private Instruction checkIfInstruction(ArrayList<Token> t) {
//		
//	}
	
	
	
	
	
	
	

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