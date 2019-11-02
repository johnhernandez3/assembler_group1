package assembler;

import java.util.regex.Pattern;

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
