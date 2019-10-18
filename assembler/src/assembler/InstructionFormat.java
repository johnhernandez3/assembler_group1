package assembler;

import java.util.ArrayList;

public class InstructionFormat {
	
	private InstructionSet opcodes = new InstructionSet();
	private Register regloc = new Register();
	private ArrayList<Instruction> instructions;
	private Converter conv = new Converter("");
	
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

	private String Formatted1(String op, String rega, String regb, String regc) {
		return opcodes.BinaryReturn(op) + regloc.getregloc().get(rega) + regloc.getregloc().get(regb) + regloc.getregloc().get(regc) + "00";
	}
	
	private String Formatted2(String op, String reg, String addr) {
		String str = conv.hextoBin(addr);
		if(str.length() > 8) {
			str = str.substring(str.charAt(str.length() - 7), str.charAt(str.length()));
		}
		else {
			while(str.length() < 8) {
				str = "0" + str;
			}
		}
		return opcodes.BinaryReturn(op) + regloc.getregloc().get(reg) + str;
	}
	
	private String Formatted3(String op, String addr) {
		String str = conv.hextoBin(addr);
		if(str.length() > 11) {
			str = str.substring(str.charAt(str.length() - 10), str.charAt(str.length()));
		}
		else {
			while(str.length() < 11) {
				str = "0" + str;
			}
		}
		return opcodes.BinaryReturn(op) + str;
	}
	
	public String Fswitch(String a, String b, String c, String d, int i) {
		String str = "";
		switch(i) {
			case 4:
				F1switch(a, b, c, d);
				str = Formatted1(a, b, c, d);
				break;
			case 3:
				F2switch(a, b, c);
				str = Formatted2(a, b, c);
				break;
			case 2:
				F3switch(a, b);
				str = Formatted3(a, b);
				break;
			default:
				break;
		}
		return str;
	}

	private void F1switch(String a, String b, String c, String d) {
		
	}

	private void F2switch(String a, String b, String c) {
		
	}
	
	private void F3switch(String a, String b) {
		
	}

	
}