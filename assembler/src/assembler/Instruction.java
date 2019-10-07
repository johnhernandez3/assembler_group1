package assembler;

import java.util.ArrayList;

public class Instruction {
	
	public int format;
	public String name;
	public String opcode;
	public ArrayList<Token> tokens;
	
	
	public Instruction(int format, ArrayList<Token> tokens) {
		if (tokens == null) {
			throw new NullPointerException();
		}
		this.format = format;
		this.tokens = tokens;
	}
	
	@Override
	public String toString() {
		String result = String.format("Instruction: \nFormat: %s", format);
		for (Token t : tokens) {
			result += " " + t.toString();
		}
		return result;
	}
}
