package assembler;

import java.util.HashMap;

public class Register {
	
//	Initialize opcode, register map and map register values, initializes r0 with 00(bytes)
//	private InstructionSet opcodes = new InstructionSet();
	private Converter conv = new Converter();
	private String[] mem = new String[2048];
	
	private HashMap<String, String> regs = new HashMap<String, String>();
	private HashMap<String, String> regloc = new HashMap<String, String>();

	public String sp, pc;

	public Register() {
		sp = "00"; pc = "00";
		regs.put("r0", "00");
		regs.put("r1", "");
		regs.put("r2", "");
		regs.put("r3", "");
		regs.put("r4", "");
		regs.put("r5", "");
		regs.put("r6", "");
		regs.put("r7", "");
		regloc.put("r0", "000");
		regloc.put("r1", "001");
		regloc.put("r2", "010");
		regloc.put("r3", "011");
		regloc.put("r4", "100");
		regloc.put("r5", "101");
		regloc.put("r6", "110");
		regloc.put("r7", "111");
	}
	
	public Object[][] regsData() {
		Object[][] data = new Object[8][2];
		
		int l = 0;
		for (String d : this.regs.keySet()) {
			data[l][0] = d;
			l++;
		}
		l = 0;
		for (String c : this.regs.values()) {
			data[l][1] = c;
			l++;
		}
		return data;
	}
	
//	Allows access to regs
	public HashMap<String, String> getregs() {
		return regs;
	}
	
//	Allows access to reg location(for memory use)
	public HashMap<String, String> getregloc() {
		return regloc;
	}
	
//	Access to sp and pc
	public String getSP() {
		return sp;
	}
	
	public String getPC() {
		return pc;
	}
	
//	Set Regs
	public String load(String a, String b) { //F2 y loadim
		if(zeroerr(a)) return null;
		regs.put(a, b);
		checkr7(a);
		return a;
	}
	
	public String pop(String a) { //F2
		if(zeroerr(a)) return null;
		regs.put(a, mem[Integer.parseInt(sp)]);
		sp = Integer.toString(Integer.parseInt(sp) + 1);
		checkr7(a);
		return a;
	}
	
	public String store(String a, String b) { //F2
		if(zeroerr(b)) return null;
		regs.put(mem[Integer.parseInt(sp)], b);
		checkr7(a);
		return b;
	}
	
	public String push(String a) {
		if(zeroerr(a)) return null;
		sp = Integer.toString(Integer.parseInt(sp) - 1);
		regs.put(mem[Integer.parseInt(sp)], a);
		return a;
	}
	
	public String loadrind(String a, String b) {
		if(doublezerr(a, b)) return null;
		regs.put(b, regs.get(a));
		checkr7(a);
		return a;
	}
	
	public String storerind(String a, String b) {
		if(doublezerr(a, b)) return null;
		regs.put(a, regs.get(b));
		checkr7(a);
		return a;
	}
	
//	End Set Regs
	
//	Arithmetic Logic
	public String add(String a, String b, String c) {
		if(triplezerr(a, b, c))	return null;
		String tok2 = regs.get(b), tok3 = regs.get(c);
		int add1 = Integer.parseInt(tok2, 16);
		int add2 = Integer.parseInt(tok3, 16);
		regs.put(a, String.format("%02x", add1 + add2));
		checkr7(a);
		return regs.get(a);
	}
	
	public String sub(String a, String b, String c) {
		if(triplezerr(a, b, c))	return null;
		String tok2 = regs.get(b), tok3 = regs.get(c);
		int add1 = Integer.parseInt(tok2, 16);
		int add2 = Integer.parseInt(tok3, 16);
		regs.put(a, String.format("%02x", add1 - add2));
		checkr7(a);
		return regs.get(a);
	}
	
//	Needs error handler for addr
	public String addim(String a, String b) {
		if(zeroerr(a)) return null;
		regs.put(a, Integer.toHexString(Integer.parseInt(regs.get(a), 16) + Integer.parseInt(b, 16)));
		checkr7(a);
		return a;
	}
	
	public String subim(String a, String b) {
		if(zeroerr(a)) return null;
		regs.put(a, Integer.toHexString(Integer.parseInt(regs.get(a), 16) - Integer.parseInt(b, 16)));
		checkr7(a);
		return a;
	}
	
//	End Arithmetic Logic
	
//	Logic Operators
	public String and(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null; String str = "";
		String d = String.format("%04d", (Integer.parseInt(regs.get(c), 16)));
		String e = String.format("%04d", (Integer.parseInt(regs.get(b), 16)));
		for(int i = 0; i < d.length(); i++) {
			if(d.substring(i, i + 1).equals("1") && 
					e.substring(i, i + 1).equals("1")) {
				str += "1";
			}
			else {
				str += "0";
			}
		}
		regs.put(a, String.format("%02x", Integer.parseInt(str, 2)));
		checkr7(a);
		return a;
	}
	
	public String or(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null; String str = "";
		String d = String.format("%04d", (Integer.parseInt(regs.get(c), 16)));
		String e = String.format("%04d", (Integer.parseInt(regs.get(b), 16)));
		for(int i = 0; i < d.length(); i++) {
			if(d.substring(i, i + 1).equals("0") && 
					e.substring(i, i + 1).equals("0")) {
				str += "1";
			}
			else {
				str += "0";
			}
		}
		regs.put(a, String.format("%02x", Integer.parseInt(str, 2)));
		checkr7(a);
		return regs.get(a);
	}
	
	public String xor(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null; String str = "";
		String d = String.format("%04d", (Integer.parseInt(regs.get(c), 16)));
		String e = String.format("%04d", (Integer.parseInt(regs.get(b), 16)));
		for(int i = 0; i < d.length(); i++) {
			if((d.substring(i, i + 1).equals("1") && e.substring(i, i + 1).equals("1")) ||
			(d.substring(i, i + 1).equals("0") && e.substring(i, i + 1).equals("0"))) {
				str += "0";
			}
			else {
				str += "1";
			}
		}
		regs.put(a, String.format("%02x", Integer.parseInt(str, 2)));
		checkr7(a);
		return regs.get(a);
	}
	
	public String not(String a, String b) {
		if(doublezerr(a, b)) return null;
		regs.put(b, conv.hextoBin(regs.get(b)));
		char[] ch = regs.get(b).toCharArray();
		String s = new String("");
		for(int i = 0; i < ch.length; i++) {
			if(ch[i] == '0') {
				s += '1';
			}
			else if(ch[i] == '1') {
				s += '0';
			}
		}
		regs.put(a, String.format("%02x", Integer.parseInt(s, 2)));
		checkr7(a);
		return regs.get(a);
	}
	
	public String neg(String a, String b) {
		if(doublezerr(a, b)) return null;
		String s = String.format("%x", ~(Integer.parseInt(regs.get(b), 16)));
		regs.put(a, s.substring(s.length() - 2, s.length()));
		checkr7(a);
		return a;
	}
	
	public String shiftr(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, String.format("%04x", Integer.parseInt(regs.get(b), 16)>>> Integer.parseInt(regs.get(c), 16)));
		checkr7(a);
		return a;
	}
	
	public String shiftl(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, String.format("%04x", Integer.parseInt(regs.get(b), 16)<< Integer.parseInt(regs.get(c), 16)));
		checkr7(a);
		return a;
	}
	
	public String rotar(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, Integer.toHexString(Integer.rotateRight(Integer.parseInt(regs.get(b), 16), Integer.parseInt(regs.get(c), 16))));
		checkr7(a);
		return a;
	}
	
	public String rotal(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, Integer.toHexString(Integer.rotateLeft(Integer.parseInt(regs.get(b), 16), Integer.parseInt(regs.get(c), 16))));
		checkr7(a);
		return a;
	}
	
//	End Logic Operators
	
//	Flow Control
	public String jmprind(String a) {
		if(zeroerr(a)) return null;
		pc = regs.get(a);
		return a;
	}
	
	public String jmpaddr(String a) {
		pc = a;
		return a;
	}
	
	public String jcondrind(boolean b, String a) {
		if(zeroerr(a)) return null;
		if(b) pc = regs.get(a);
		return a;
	}
	
	public String jcondaddr(boolean b, String a) {
		if(b) pc = a;
		return a;
	}
	
	public String loop(String a, String b) {//needs work
		if(zeroerr(a)) return null;
		int bool = Integer.parseInt(regs.get(a), 16);
		regs.put(a, Integer.toString(Integer.parseInt(regs.get(a), 16) - 1));
		if(bool != 0) {
			pc = b;
			bool--;
		}
		return a;
	}
	
	public boolean grt(String a, String b) {
		return Integer.parseInt(regs.get(a), 16) > Integer.parseInt(regs.get(b), 16);
	}
	
	public boolean grteq(String a, String b) {
		return Integer.parseInt(regs.get(a), 16) >= Integer.parseInt(regs.get(b), 16);
	}
	
	public boolean eq(String a, String b) {
		return Integer.parseInt(regs.get(a), 16) == Integer.parseInt(regs.get(b), 16);
	}
	
	public boolean neq(String a, String b) {
		return Integer.parseInt(regs.get(a), 16) != Integer.parseInt(regs.get(b), 16);
	}
	
	public void call(String a) {
		sp = Integer.toString(Integer.parseInt(sp) - 2);
		mem[Integer.parseInt(sp)] = pc;
		pc = a;
	}
	
	public void ret() {
		pc = mem[Integer.parseInt(sp)];
		sp = Integer.toString(Integer.parseInt(sp) + 2);
	}
	
//	End Flow
	
//	r0 Error Handler and helpers
	public boolean zeroerr(String a) {
		boolean t = false;
		if(a.equals("r0")) {
			System.out.println("Error can't reference r0");
			return !t;
		}
		return t;
	}
	
	public boolean triplezerr(String a, String b, String c) {
		return zeroerr(a) || zeroerr(b) || zeroerr(c);
	}
	
	public boolean doublezerr(String a, String b) {
		return zeroerr(a) || zeroerr(b);
	}
//	End r0
	
//	Check r7
	public void checkr7(String a) {
		if(a.equals("r7")) {
			sp = regs.get(a);
		}
	}
	
//	End r7
	
}