package assembler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
	
	private ArrayList<TokenRegex> tokenPatterns;
	private ArrayList<String> lines;
	private String source;
	private int offset;
	
	public Tokenizer(String source) {
		if (source == null) {
			throw new NullPointerException();
		}
		this.source = source;
		this.offset = 0;
		this.tokenPatterns = new ArrayList<>();
		this.lines = new ArrayList<>();
		Scanner scanner = new Scanner(this.source);
		int c = 1;
		while (scanner.hasNextLine()) {
			String s = scanner.nextLine().trim();
			this.lines.add(s);
			System.out.println("Line " + c + ": " + s);
			c++;
		}
		scanner.close();
		
		
		this.tokenPatterns.add(new TokenRegex(TokenType.COMMENT, "\\/\\/[^\\n\\r]*"));
		this.tokenPatterns.add(new TokenRegex(TokenType.ADDRESS, "[A-Za-z0-9]+:?"));
		this.tokenPatterns.add(new TokenRegex(TokenType.CONST, "(const)\\s+([A-Za-z0-9]+)\\s+([A-Fa-f0-9]+)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.ORIGIN, "(org)\\s+([0-9]+)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.JMP, "(jmp)\\s+([A-Za-z0-9]+)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.OPCODE, "(?:\\s)(loadim|loadrind|load|pop|storerind|push|store|addim|subim|add|sub|and|or|xor|not|neg|shiftr|shiftl|rotar|rotal|jmprind|jmpaddr|jcondrind|jcondaddr|loop|grteq|grt|eq|neq|nop|call|return)(?:\\s)"));
		this.tokenPatterns.add(new TokenRegex(TokenType.REGISTER, "(?:,|\\s+)(?:,?)\\s*(r[0-7])"));
		
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
		return null;
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
