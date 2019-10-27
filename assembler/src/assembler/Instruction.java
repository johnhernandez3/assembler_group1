package assembler;

public class Instruction {
	
//	format = 1 = {F1}, format = 2 = {F2}, format = 3 = {F3}, format = 0 = NO FORMAT e.j. RETURN
	public int format;
	public String opcode;
	public int numOfNextTokens;
	
	
	public Instruction(int format, String opcode, int numOfNextTokens) {
		this.format = format;
		this.opcode = opcode;
		this.numOfNextTokens = numOfNextTokens;
	}
	
	public int getFormat() {
		return format;
	}

	public String getOpcode() {
		return opcode;
	}
	
	public int getNumOfNextTokens() {
		return numOfNextTokens;
	}
	
	@Override
	public String toString() {
		return String.format("Format: %s, Opcode: %s, NumOfNextTokens: %s", format, opcode, numOfNextTokens);
	}
}
