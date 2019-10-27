package assembler;

import java.util.ArrayList;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

public class Tokenizer {
	
	public Tokenizer() { }
	
	public ArrayList<Token> getTokens(String source) {
		ArrayList<Token> allTokens = new ArrayList<>();
		int commentStart = source.indexOf("//");
		int commentEnd = source.length();
		if (-1 != commentStart) {
			allTokens.add(new Token(TokenType.COMMENT, source.substring(commentStart, commentEnd).trim()));
			source = source.substring(commentEnd);
		}
		String[] tokens = source.split(",|\\s");
		for (String token : tokens) {
			if (!token.equals("")) {
				allTokens.add(new Token(TokenType.TOKEN, token.trim()));
			}
		}
		return allTokens;
	}
	
//	public Token next() {
//		Pattern spaceOrComma = Pattern.compile("\\s | ,");
//		Matcher match = match(spaceOrComma, this.lines.get(0));
//		if (match != null) {
//			return new Token(TokenType.TOKEN, match.group().trim());
//		}
//		return null;
//	}
//	
//	
//	private Matcher match(Pattern pattern, String line) {
//		Matcher m = pattern.matcher(line);
//		if (!m.find()) {
//			return null;
//		} else {
//			return m;
//		}
//	}
	
}
