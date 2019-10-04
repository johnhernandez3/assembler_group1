package assembler;

public class InstructionFormat {
	
	private InstructionSet opcodes = new InstructionSet();
	
	public InstructionFormat() {
		
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