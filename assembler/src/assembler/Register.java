
package assembler;

import java.util.ArrayList;
import java.util.HashMap;

public class Register {
	
//	Initialize opcode, register map and map register values, initializes r0 with 00(bytes)
	private InstructionSet opcodes = new InstructionSet();
	private Converter conv = new Converter("");
	private String[] mem = new String[2048];
	
	private HashMap<String, String> regs = new HashMap<String, String>();

	public String sp, pc;

	public Register() {
		sp = "0"; pc = "0";
		regs.put("r0", "00");
		regs.put("r1", "");
		regs.put("r2", "");
		regs.put("r3", "");
		regs.put("r4", "");
		regs.put("r5", "");
		regs.put("r6", "");
		regs.put("r7", "");
	}
	
//	Set Regs
	public String load(String a, String b) { //F2
		if(zeroerr(a)) return null;
		regs.put(a, b);
		return a;
	}
	
	public String pop(String a) { //F2
		if(zeroerr(a)) return null;
		regs.put(a, mem[Integer.parseInt(sp)]);
		sp = Integer.toString(Integer.parseInt(sp) + 1);
		return a;
	}
	
	public String store(String a, String b) { //F2
		if(zeroerr(b)) return null;
		regs.put(mem[Integer.parseInt(sp)], b);
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
		regs.replace(b, regs.get(a));
		return a;
	}
	
	public String storerind(String a, String b) {
		if(doublezerr(a, b)) return null;
		regs.replace(a, regs.get(b));
		return a;
	}
	
//	End Set Regs
	
//	Arithmetic Logic
	public String add(String a, String b, String c) {
		if(triplezerr(a, b, c))	return null;
		String tok2 = regs.get(b), tok3 = regs.get(c);
		int add1 = Integer.parseInt(tok2, 16);
		int add2 = Integer.parseInt(tok3, 16);
		regs.put(a, Integer.toHexString(add1 + add2));
		return regs.get(a);
	}
	
	public String sub(String a, String b, String c) {
		if(triplezerr(a, b, c))	return null;
		regs.put(a, add(a, b, neg(c, c)));
		return regs.get(a);
	}
	
//	Needs error handler for addr
	public String addim(String a, String b) {
		if(zeroerr(a)) return null;
		regs.put(a, Integer.toHexString(Integer.parseInt(a, 16) + Integer.parseInt(b)));
		return a;
	}
	
	public String subim(String a, String b) {
		if(zeroerr(a)) return null;
		regs.put(a, Integer.toHexString(Integer.parseInt(a, 16) - Integer.parseInt(b)));
		return a;
	}
	
//	End Arithmetic Logic
	
//	Logic Operators
	public String and(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, Integer.toHexString(Integer.parseInt(regs.get(b), 16) &
				Integer.parseInt(regs.get(c), 16)));
		return a;
	}
	
	public String or(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, Integer.toHexString(Integer.parseInt(regs.get(b), 16) |
				Integer.parseInt(regs.get(c), 16)));
		return regs.get(a);
	}
	
	public String xor(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, or(a, and(b, b, not(c, c)), and(c, c, not(b, b))));
		return regs.get(a);
	}
	
	public String not(String a, String b) {
		if(doublezerr(a, b)) return null;
		char[] ch = regs.get(b).toCharArray();
		String s = new String();
		for(int i = 0; i < ch.length; i++) {
			if(ch[i] == '0') {
				s += '1';
			}
			else if(ch[i] == '1') {
				s += '0';
			}
		}
		regs.put(a, s);
		return a;
	}
	
	public String neg(String a, String b) {
		if(doublezerr(a, b)) return null;
		regs.put(a, Integer.toHexString(~Integer.parseInt(regs.get(b), 16)));
		return a;
	}
	
	public String shiftr(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, Integer.toHexString(Integer.parseInt(regs.get(b) + 
														regs.get(c), 16)>>> 1));
		return a;
	}
	
	public String shiftl(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, Integer.toHexString(Integer.parseInt(regs.get(b) + 
													regs.get(c), 16)<<1));

		return a;
	}
	
	public String rotar(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, Integer.toHexString(Integer.rotateRight(Integer.parseInt(regs.get(b)
				 + regs.get(c)), 1)));
		return a;
	}
	
	public String rotal(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, Integer.toHexString(Integer.rotateLeft(Integer.parseInt(regs.get(b)
				 + regs.get(c)), 1)));
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
	
	public String loop(String a, String b) {
		if(zeroerr(a)) return null;
		regs.put(a, Integer.toString(Integer.parseInt(regs.get(a), 16) - 1));
		if(Integer.parseInt(regs.get(a), 16) != 0) {
			pc = b;
		}
		return a;
	}
	
	public boolean grt(String a, String b) {
		return Integer.parseInt(regs.get(a)) > Integer.parseInt(regs.get(b));
	}
	
	public boolean grteq(String a, String b) {
		return Integer.parseInt(regs.get(a)) >= Integer.parseInt(regs.get(b));
	}
	
	public boolean eq(String a, String b) {
		return Integer.parseInt(regs.get(a)) == Integer.parseInt(regs.get(b));
	}
	
	public boolean neq(String a, String b) {
		return Integer.parseInt(regs.get(a)) != Integer.parseInt(regs.get(b));
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
			return t;
		}
		return !t;
	}
	
	public boolean triplezerr(String a, String b, String c) {
		return zeroerr(a) || zeroerr(b) || zeroerr(c);
	}
	
	public boolean doublezerr(String a, String b) {
		return zeroerr(a) || zeroerr(b);
	}
//	End r0
	
}