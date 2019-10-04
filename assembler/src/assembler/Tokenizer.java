package assembler;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Tokenizer {

	enum TokenType {
		COMMENT, CONST, ORIGIN, JMP, LABEL, NAME, OPCODE, REGISTER, COMMA, ADDRESS, HEX
	}
	
	class Token {
		
		public final TokenType type;
		public final String value;
		
		
		Token(TokenType type, String value) {
			if (type == null || value == null) {
				throw new NullPointerException();
			}
			this.type = type;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return String.format("Token: (%s, %s)", type, value);
		}
	}
	
	class TokenRegex {
		
		public final Pattern pattern;
		public final TokenType tokenType;
		
		public TokenRegex(TokenType tokenType, String pattern) {
			if (pattern == null) {
				throw new NullPointerException();
			}
			this.pattern = Pattern.compile(pattern);
			this.tokenType = tokenType;
		}
		
		
		@Override
		public String toString() {
			return String.format("TokenRegex: (%s, pattern: %s)", tokenType, pattern);
		}
	}
	
	private ArrayList<TokenRegex> tokenPatterns;
	
	public Tokenizer() {
		this.tokenPatterns.add(new TokenRegex(TokenType.COMMENT, "\\/\\/[^\\n\\r]*"));
		this.tokenPatterns.add(new TokenRegex(TokenType.CONST, "(const)\\s+([A-Za-z0-9]+)\\s+([A-Fa-f0-9]+)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.ORIGIN, "(org)\\s+([0-9]+)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.JMP, "(jmp)\\s+([A-Za-z0-9]+)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.LABEL, "[A-Za-z][A-Za-z0-9]*:"));
		this.tokenPatterns.add(new TokenRegex(TokenType.NAME, "([A-Za-z0-9]+)\\s+(db)\\s+([A-Fa-f0-9]+)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.OPCODE, "(?:\\s)(loadim|loadrind|load|pop|storerind|push|store|addim|subim|add|sub|and|or|xor|not|neg|shiftr|shiftl|rotar|rotal|jmprind|jmpaddr|jcondrind|jcondaddr|loop|grteq|grt|eq|neq|nop|call|return)(?:\\s)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.REGISTER, "(?:,|\\s+)(?:,?)\\s*(r[0-7])"));
		this.tokenPatterns.add(new TokenRegex(TokenType.COMMA, ","));
	}
	
	
	
	
	
	
	
	
	
}
