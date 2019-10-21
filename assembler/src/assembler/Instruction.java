package assembler;

public class Instruction {
	
//	format = 1 = {F1}, format = 2 = {F2}, format = 3 = {F3}, format = 0 = NO FORMAT e.j. RETURN
	public int format;
	public String opcode;
	
	
	public Instruction(int format, String opcode) {
		this.format = format;
		this.opcode = opcode;
	}
	
	public int getFormat() {
		return format;
	}

	public String getOpcode() {
		return opcode;
	}
	
	@Override
	public String toString() {
		return String.format("Format: %s, Opcode: %s", format, opcode);
	}
}
