package assembler;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	
	InstructionSet instructionSet = new InstructionSet();
	InstructionFormat instructionFormat = new InstructionFormat();
	Tokenizer tokenizer = new Tokenizer();
	ArrayList<Token> tokens = new ArrayList<>();
	private ArrayList<TokenRegex> tokenPatterns;
	private ArrayList<String> lines;
	private String sourceCode;
	
	public Parser(String sourceCode) {
		if (sourceCode == null) {
			throw new NullPointerException("File Content is empty.");
		}
		this.sourceCode = sourceCode;
		this.lines = new ArrayList<>();
		Scanner scanner = new Scanner(this.sourceCode);
		int c = 1;
		while (scanner.hasNextLine()) {
			// WARNING: trimming every line might cause problems with syntax error handling
			String s = scanner.nextLine().trim();
			this.lines.add(s);
			System.out.println("Line " + c + ": " + s);
			c++;
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
		return this.tokens;
	}
	
	public String parseLine(String line) {
		String result = "";
		tokens = tokenizer.getTokens(line);
		if(tokens.size() == 4) {
			result = instructionFormat.Fswitch(tokens.get(0).toString(), tokens.get(1).toString(), 
					tokens.get(2).toString(), tokens.get(3).toString(), 1);
		}
		else if(tokens.size() == 3) {
			result = instructionFormat.Fswitch(tokens.get(0).toString(), tokens.get(1).toString(), 
					tokens.get(2).toString(), "", 2);
		}
		else if(tokens.size() <= 2) {
			result = instructionFormat.Fswitch(tokens.get(0).toString(), tokens.get(1).toString(), 
					"", "", 3);
		}
		for (Token t : tokens) {
			
			this.classifyToken(t);
			result += t.toString() + " ";
		}
		return result += "\n";
	}
	
	public String parse() {
		String result = "";
		for (String line : this.lines) {
			result += this.parseLine(line);
		}
		return result;
	}
	
	private void classifyToken(Token token) {
		if (token.type == TokenType.COMMENT) return;
		for (TokenRegex regex : tokenPatterns) {
			Matcher match = match(regex.pattern, token.getValue());
			if (match != null) {
				if (this.tokens.size() > 1) {
					if (this.getPrevTokenType(token) == TokenType.JMP || this.getPrevTokenType(token) == TokenType.ORIGIN) {
						token.setType(TokenType.ADDRESS);
						return;
					}
				}
				token.setType(regex.tokenType);
			}
		}
	}
	
	private TokenType getPrevTokenType(Token t) {
		return this.tokens.get(this.tokens.size() - 2).type;
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
