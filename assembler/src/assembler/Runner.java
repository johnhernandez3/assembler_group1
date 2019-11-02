package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Runner {
	// This will manage the runs through tokens we need to do to store constants and values and write the object code
	private InstructionSet opcodes = new InstructionSet();
	private Register registers = new Register();
	private Converter converter = new Converter();
	private ArrayList<Token> tokens;
	public Map<String, ArrayList<String>> values = new HashMap<>();
	public Map<String, String> constants = new HashMap<>();
	
	public Runner(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
	
	public void firstRun() {
		// store constants and values
		int tokenIndex = 0;
		for (Token t : tokens) {
			if (t.type == TokenType.CONST) {
				tokenIndex++;
				Token nameToken = tokens.get(tokenIndex);
				if (nameToken.type != TokenType.NAME) {
//					return "Error";
				}
				tokenIndex++;
				Token valueToken = tokens.get(tokenIndex);
				if (valueToken.type != TokenType.VALUE) {
//					return "Error";
				}
				constants.put(nameToken.value, valueToken.value);
			}
			// check for values adn store them respectively
		}
	}
	
	public String secondRun() {
		this.firstRun();
		// generate object code per instruction
		String objectCode = "";
		int tokenIndex = 0;
		for (Token t : tokens) {
			if (t.type == TokenType.OPCODE) {
				Instruction instruction = opcodes.getInstruction(t.value);
				String binaryCode = instruction.getOpcode();
				for (int i = 0; i < instruction.numOfNextTokens; i++) {
					tokenIndex++;
					Token nextToken = tokens.get(tokenIndex);
					TokenType nextTokenType = nextToken.type;
					if (nextTokenType != TokenType.NAME || nextTokenType != TokenType.HEX || nextTokenType != TokenType.REGISTER) {
						return "Error";
					}
					// create the binarycode depending on the opcode
//					binaryCode += ;
				}
				objectCode += converter.binToHex(binaryCode) + "\n";
			}
			tokenIndex++;
		}
		return objectCode;
	}
}