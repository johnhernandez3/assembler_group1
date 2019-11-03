package assembler;

import java.util.ArrayList;

public class Instruction {
	
//	format = 1 = {F1}, format = 2 = {F2}, format = 3 = {F3}, format = 0 = NO FORMAT e.j. RETURN
	public int format;
	public String opcode;
	public int numOfNextTokens;
	public ArrayList<Token> tokens;
	
	public Instruction(int format, String opcode, int numOfNextTokens, ArrayList<Token> tokens) {
		this.format = format;
		this.opcode = opcode;
		this.numOfNextTokens = numOfNextTokens;
		this.tokens = tokens;
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
	
	public ArrayList<Token> getTokens() {
		return tokens;
	}
	
	public void addToken(Token t) {
		this.tokens.add(t);
	}
	
	@Override
	public String toString() {
		return String.format("Format: %s, Opcode: %s, NumOfNextTokens: %s", format, opcode, numOfNextTokens);
	}
}
