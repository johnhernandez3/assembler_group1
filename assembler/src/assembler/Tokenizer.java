package assembler;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
	
	private ArrayList<TokenRegex> tokenPatterns;
	private String source;
	private int offset;
	
	public Tokenizer(String source) {
		if (source == null) {
			throw new NullPointerException();
		}
		this.source = source;
		this.offset = 0;
		this.tokenPatterns = new ArrayList<>();
		this.tokenPatterns.add(new TokenRegex(TokenType.COMMENT, "\\/\\/[^\\n\\r]*"));
//		this.tokenPatterns.add(new TokenRegex(TokenType.COMMA, ","));
//		this.tokenPatterns.add(new TokenRegex(TokenType.LABEL, "[A-Za-z][A-Za-z0-9]*:"));
		this.tokenPatterns.add(new TokenRegex(TokenType.INSTRUCTION, "#?[A-Za-z0-9]+:?"));
		this.tokenPatterns.add(new TokenRegex(TokenType.CONST, "(const)\\s+([A-Za-z0-9]+)\\s+([A-Fa-f0-9]+)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.ORIGIN, "(org)\\s+([0-9]+)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.JMP, "(jmp)\\s+([A-Za-z0-9]+)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.OPCODE, "(?:\\s)(loadim|loadrind|load|pop|storerind|push|store|addim|subim|add|sub|and|or|xor|not|neg|shiftr|shiftl|rotar|rotal|jmprind|jmpaddr|jcondrind|jcondaddr|loop|grteq|grt|eq|neq|nop|call|return)(?:\\s)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.REGISTER, "(?:,|\\s+)(?:,?)\\s*(r[0-7])"));
		
//		patterns.add(new TokenPattern("^[\n\r]+", TokenType.NEWLINE));
	}
	
	public Token next() {
		for (TokenRegex regex : tokenPatterns) {
			Matcher match = match(regex.pattern);
			if (match != null) {
				offset = match.end();
				if (regex.tokenType != null) {
					return new Token(regex.tokenType, match.group().trim());
				} else {
					return next();
				}
			}
		}
		throw new RuntimeException("No token pattern match");
	}
	
	
	private Matcher match(Pattern pattern) {
		Matcher m = pattern.matcher(source);
		m.region(offset, source.length());
		if (!m.find()) {
			return null;
		} else {
			return m;
		}
	}
	
}
