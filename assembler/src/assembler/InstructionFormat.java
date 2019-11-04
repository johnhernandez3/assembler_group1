package assembler;

import java.util.ArrayList;

public class InstructionFormat {
	
	private InstructionSet opcodes = new InstructionSet();
	private Register regloc = new Register();
	private ArrayList<Instruction> instructions;
	private Converter conv = new Converter();
	
	public InstructionFormat() {
		this.instructions = new ArrayList<>();
	}
	
	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<Instruction> instructions) {
		this.instructions = instructions;
	}
	
	public void addInstruction(ArrayList<Token> t) {
//		If token = origin, next token = originaddress if not error
//		If token = jmp, next token = address if not error store address variable in table
	}

	private String Formatted1(String op, String rega, String regb, String regc) {
//		System.out.println("op: " + op + "rega: " + rega + "regb: " + regb + "regc: " + regc);
		if (regc.equals("")) {
			regc = "r0";
		}
		if (regb.equals("")) {
			regb = "r0";
		}
		if (rega.equals("")) {
			rega = "r0";
		}
		return opcodes.BinaryReturn(op) + regloc.getregloc().get(rega) + regloc.getregloc().get(regb) + regloc.getregloc().get(regc) + "00";
	}
	
	private String Formatted2(String op, String reg, String addr) {
//		System.out.println("Address: " + addr);
		if (addr.contains("#")) {
			addr = addr.replace("#", "");
		}
		if (addr.equals("")) {
			addr = "00";
		}
		String str = conv.hextoBin(addr);
//		if(str.length() > 8) {
//			str = str.substring(str.charAt(str.length() - 7), str.charAt(str.length()));
//		}
//		else {
//			while(str.length() < 8) {
//				str += "0";
//			}
//		}
		str = ("00000000" + str).substring(str.length());
		return opcodes.BinaryReturn(op) + regloc.getregloc().get(reg) + str;
	}
	
	private String Formatted3(String op, String addr) {
		if (addr.contains("r")) {
			String str = conv.hextoBin("00");
			str = ("00000000000" + str).substring(str.length());
			return opcodes.BinaryReturn(op) + regloc.getregloc().get(addr) + str;
		}
		String str = conv.hextoBin(addr);
		str = ("00000000000" + str).substring(str.length());
		return opcodes.BinaryReturn(op) + str;
	}
	
	public String Fswitch(String a, String b, String c, String d, int i) {
		String str = "";
		switch(i) {
			case 0:
				str = "1111100000000000";
			case 1:
//				F1switch(a, b, c, d);
				str = Formatted1(a, b, c, d);
				break;
			case 2:
//				F2switch(a, b, c);
				str = Formatted2(a, b, c);
				break;
			case 3:
//				F3switch(a, b);
				str = Formatted3(a, b);
				break;
			default:
				break;
		}
//		System.out.println(str);
		return conv.binToHex(str);
	}

	private void F1switch(String a, String b, String c, String d) {
		switch(a) {
			case "add":
				regloc.add(b, c, d);
				break;
			case "sub":
				regloc.sub(b, c, d);
				break;
			case "and":
				regloc.and(b, c, d);
				break;
			case "or":
				regloc.or(b, c, d);
				break;
			case "xor":
				regloc.xor(b, c, d);
				break;
			case "shiftr":
				regloc.shiftr(b, c, d);
				break;
			case "shiftl":
				regloc.shiftl(b, c, d);
				break;
			case "rotar":
				regloc.rotar(b, c, d);
				break;
			case "rotal":
				regloc.rotal(b, c, d);
				break;
			default:
				break;
		}
	}

	private void F2switch(String a, String b, String c) {
		switch(a) {
			case "load":
				regloc.load(b, c);
				break;
			case "loadim":// needs work
				regloc.load(b, c);
			case "pop":
				regloc.pop(b);// work needed
				break;
			case "store":
				regloc.store(b, c);
				break;
			case "loadrind":
				regloc.loadrind(b, c);
				break;
			case "storerind":
				regloc.storerind(b, c);
				break;
			case "not":
				regloc.not(b, c);
				break;
			case "addim":
				regloc.addim(b, c);
				break;
			case "subim":
				regloc.subim(b, c);
				break;
			case "neg":
				regloc.neg(b, c);
				break;
			case "grt":
				regloc.grt(b, c);
				break;
			case "grteq":
				regloc.grteq(b, c);
				break;
			case "eq":
				regloc.eq(b, c);
				break;
			case "neq":
				regloc.neq(b, c);
				break;
			default:
				break;
		}
	}
	
	private void F3switch(String a, String b) {
		switch(a) {
			case "pop":
				regloc.pop(b);
				break;
			case "push":
				regloc.push(b);
				break;
			case "jmprind":
				regloc.jmprind(b);
				break;
			case "jmpaddr":
				regloc.jmpaddr(b);
				break;
			case "jcondrind":
				//needs work
				regloc.jcondrind(true, b);
				break;
			case "jcondaddr":
				//needs work
				regloc.jcondaddr(true, b);
				break;
			case "loop":
				//needs work
				regloc.loop(b, b);
				break;
			case "call":
				regloc.call(b);
			case "ret":
				regloc.ret();
				break;
			default:
				break;
		}
	}

	
}