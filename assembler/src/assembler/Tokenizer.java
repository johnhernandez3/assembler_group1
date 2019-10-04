package assembler;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Tokenizer {

	enum TokenType {
		COMMENT, CONST, ORIGIN, OPCODE, NAME, LABEL, REGISTER, COMMA, ADDRESS, HEX
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
	
	
//	patterns.add(new TokenRegex("^[ \t]+", null));  // Whitespace
//	patterns.add(new TokenRegex("^[A-Za-z_][A-Za-z0-9_]*:"));
//	patterns.add(new TokenRegex("^[A-Za-z_][A-Za-z0-9_]*"));
//	patterns.add(new TokenRegex("^%[A-Za-z][A-Za-z0-9_]*"));
//	patterns.add(new TokenRegex("^0[xX][0-9a-fA-F]+"));
//	patterns.add(new TokenRegex("^-?[0-9]+"));
//	patterns.add(new TokenRegex("^\\$"));
//	patterns.add(new TokenRegex("^,"));
//	patterns.add(new TokenRegex("^\\+"));
//	patterns.add(new TokenRegex("^-"));
//	patterns.add(new TokenRegex("^\\("));
//	patterns.add(new TokenRegex("^\\)"));
//	patterns.add(new TokenRegex("^[\n\r]+"));
//	patterns.add(new TokenRegex(TokenType.COMMENT, "^//[^\n\r]*"));  // Comment
	public Tokenizer() {
		this.tokenPatterns.add(new TokenRegex(TokenType.COMMENT, "\\/\\/[^\\n\\r]*"));
		this.tokenPatterns.add(new TokenRegex(TokenType.CONST, "const"));
		this.tokenPatterns.add(new TokenRegex(TokenType.ORIGIN, "(org) ([0-9]+)")); 
		this.tokenPatterns.add(new TokenRegex(TokenType.OPCODE, "(load)|(loadim)|(pop)|(store)|(push)|(loadrind)|(storerind)|(add)|(sub)|(addim)|(subim)|(and)|(or)|(xor)|(not)|(neg)|(shiftr)|(shiftl)|(rotar)|(rotal)|(jmprind)|(jmpaddr)|(jcondrind)|(jcondaddr)|(loop)|(grt)|(grteq)|(eq)|(neq)|(nop)|(call)|(return)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.LABEL, "[A-Za-z][A-Za-z0-9]*:"));
		
	}
	
	
	
	
	
	
	
	
	
}
