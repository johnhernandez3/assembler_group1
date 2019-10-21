package assembler;

public class Parser {
	
	InstructionSet instructionSet = new InstructionSet();
	
	public String parse(Tokenizer tokenizer) {
		
		String result = "";
		Token prevToken = null;
		Token currentToken = tokenizer.next();
		while (currentToken != null) {
			if (instructionSet.getMap().containsKey(currentToken.value)) {
				currentToken.setType(TokenType.OPCODE);
			} else if (currentToken.value.contains(":")) {
				currentToken.setType(TokenType.LABEL);
			} else if (currentToken.value.contains("#")) {
				currentToken.setType(TokenType.HEX);
			} else if (currentToken.value.contains("r") && currentToken.value.length() == 2) {
				currentToken.setType(TokenType.REGISTER);
			} else if (currentToken.value.equals("const")) {
				currentToken.setType(TokenType.CONST);
			} else if (currentToken.value.equals("jmp")) {
				currentToken.setType(TokenType.JMP);
			} else if (currentToken.value.equals("db")) {
				currentToken.setType(TokenType.DB);
			} else if (prevToken != null && prevToken.value.equals("jmpaddr")) {
				currentToken.setType(TokenType.ADDRESS);
			} else if (prevToken != null && prevToken.value.equals("db")) {
				currentToken.setType(TokenType.HEX);
			} else if (prevToken != null && prevToken.value.equals("const")) {
				currentToken.setType(TokenType.ADDRESS);
			} else if (prevToken != null && prevToken.value.equals("org")) {
				currentToken.setType(TokenType.ORIGINADDRESS);
			} else if (prevToken != null && prevToken.value.equals("jmp")) {
				currentToken.setType(TokenType.ADDRESS);
			} else if (currentToken.value.equals("org")) {
				currentToken.setType(TokenType.ORIGIN);
			}
			System.out.println(currentToken.toString());
			result += currentToken.toString() + "\n";
			prevToken = currentToken;
			currentToken = tokenizer.next();
		}
		return result;
	}
}
