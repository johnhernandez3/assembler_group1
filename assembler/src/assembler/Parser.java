package assembler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	
	InstructionSet instructionSet = new InstructionSet();
	Tokenizer tokenizer = new Tokenizer();
	ArrayList<Token> tokens = new ArrayList<>();
	private ArrayList<TokenRegex> tokenPatterns;
	private ArrayList<String> lines;
	private String sourceCode;
	
	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public ArrayList<String> getLines() {
		return lines;
	}

	public String printLines() {
		String result = "";
		int c = 1;
		for (String s : this.lines) {
			result += "Line " + c + ": " + s + "\n";
			c++;
		}
		return result;
	}
	
	public String printLine(int i) {
		return "Line " + i + ": " + this.lines.get(i) + "\n";
	}
	
	public String getLine(int currentLine) {
		return this.lines.get(currentLine);
	}
	
	public Parser(String sourceCode) {
		if (sourceCode == null) {
			throw new NullPointerException("File Content is empty.");
		}
		this.sourceCode = sourceCode;
		this.lines = new ArrayList<>();
		Scanner scanner = new Scanner(this.sourceCode);
//		int c = 1;
		while (scanner.hasNextLine()) {
			// WARNING: trimming every line might cause problems with syntax error handling
			String s = scanner.nextLine().trim();
			this.lines.add(s);
//			System.out.println("Line " + c + ": " + s);
//			c++;
		}
		scanner.close();
		
		this.tokenPatterns = new ArrayList<>();
		this.tokenPatterns.add(new TokenRegex(TokenType.NAME, "^[A-Za-z0-9]+:?$"));
		this.tokenPatterns.add(new TokenRegex(TokenType.LABEL, "^[A-Za-z0-9]+:$"));
		this.tokenPatterns.add(new TokenRegex(TokenType.VALUE, "^[0-9a-fA-F]{1,2}$"));
		this.tokenPatterns.add(new TokenRegex(TokenType.HEX, "^#[0-9a-fA-F]{1,2}$"));
		this.tokenPatterns.add(new TokenRegex(TokenType.CONST, "^const$"));
		this.tokenPatterns.add(new TokenRegex(TokenType.ORIGIN, "^org$"));
		this.tokenPatterns.add(new TokenRegex(TokenType.JMP, "^jmp$"));
		this.tokenPatterns.add(new TokenRegex(TokenType.DB, "^db$"));
		this.tokenPatterns.add(new TokenRegex(TokenType.OPCODE, "^loadim$|^loadrind$|^load$|^pop$|^storerind$|^push$|^store$|^addim$|^subim$|^add$|^sub$|^and$|^or$|^xor$|^not$|^neg$|^shiftr$|^shiftl$|^rotar$|^rotal$|^jmprind$|^jmpaddr$|^jcondrin$|^jcondaddr$|^loop$|^grteq$|^grt$|^eq$|^neq$|^nop$|^call$|^return$"));
		this.tokenPatterns.add(new TokenRegex(TokenType.REGISTER, "^r[0-7]$"));
	}
	
	public ArrayList<Token> getAllParsedTokens() {
		this.tokens = tokenizer.getTokens(this.sourceCode);
		this.classifyTokens(this.tokens);
		return this.tokens;
	}
	
	public ArrayList<Token> parseLine(int i) {
		String line = this.lines.get(i);
//		System.out.println(this.printLine(i));
		ArrayList<Token> tokens = tokenizer.getTokens(line);
		this.classifyTokens(tokens);
		return tokens;
	}
	
	public String parse() {
		String result = "";
		for (int i = 0; i < this.lines.size(); i++) {
			ArrayList<Token> lineTokens = this.parseLine(i);
			for (Token t : lineTokens) {
				result += t.toString() + " ";
			}
			result += "\n\n";
		}
		return result;
	}
	
	private void classifyTokens(ArrayList<Token> tokenList) {
		for (Token t : tokenList) {
			if (t.type != TokenType.COMMENT) {
				for (TokenRegex regex : tokenPatterns) {
					Matcher match = match(regex.pattern, t.getValue());
					if (match != null) {
						if (tokenList.size() > 1) {
							if (this.getPrevTokenType(t, tokenList) == TokenType.JMP || this.getPrevTokenType(t, tokenList) == TokenType.ORIGIN) {
								t.setType(TokenType.ADDRESS);
								return;
							}
						}
						t.setType(regex.tokenType);
					}
				}
			}
		}
	}
	
	private TokenType getPrevTokenType(Token t, ArrayList<Token> tokenList) {
		return tokenList.get(tokenList.size() - 2).type;
	}
	
	private Matcher match(Pattern pattern, String s) {
		Matcher m = pattern.matcher(s);
		if (!m.find()) {
			return null;
		} else {
			return m;
		}
	}

}