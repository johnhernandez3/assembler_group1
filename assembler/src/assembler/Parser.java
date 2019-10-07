package assembler;

public class Parser {
	
	InstructionSet instructionSet = new InstructionSet();
	
	public void parseLine(Tokenizer tokenizer) {
		
//		while (tokenizer.next().type == TokenType.LABEL) {
//			String name = tokenizer.next().value;
//			name = name.substring(0, name.length() - 1);
//			program.addStatement(new LabelStatement(name));
//		}
		
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
			} else if (currentToken.value.equals("org")) {
				currentToken.setType(TokenType.ORIGIN);
			} 
//			if (nextToken.type == TokenType.INSTRUCTION) {
//				System.out.println(nextToken.toString());
//			} else if (nextToken.type == TokenType.COMMENT) {
//				System.out.println(nextToken.toString());
//			}
			System.out.println(currentToken.toString());
			prevToken = currentToken;
			currentToken = tokenizer.next();
		}
		
//		if (tokenizer.check(TokenType.NEWLINE))
//			tokenizer.next();
//		else
//			throw new RuntimeException("Expected newline");
	}
}
