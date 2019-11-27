package assembler;

import java.util.HashMap;

public class Register {
	
//	Initialize opcode, register map and map register values, initializes r0 with 00(bytes)
//	private InstructionSet opcodes = new InstructionSet();
	private Converter conv = new Converter();
	private GUI gui;
	
	private HashMap<String, String> regs = new HashMap<String, String>();
	private HashMap<String, String> regloc = new HashMap<String, String>();

	public String sp = "00"; 
	public String pc = "00";
	public boolean cond = false;

//	public Register() {
//		sp = "00"; pc = "00";
//		regs.put("r0", "00");
//		regs.put("r1", "");
//		regs.put("r2", "");
//		regs.put("r3", "");
//		regs.put("r4", "");
//		regs.put("r5", "");
//		regs.put("r6", "");
//		regs.put("r7", "");
//		regloc.put("r0", "000");
//		regloc.put("r1", "001");
//		regloc.put("r2", "010");
//		regloc.put("r3", "011");
//		regloc.put("r4", "100");
//		regloc.put("r5", "101");
//		regloc.put("r6", "110");
//		regloc.put("r7", "111");
//	}
	
	public Register(GUI gui) {
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
		this.gui = gui;
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
	public void load(String reg, String addr) { //F2 y loadim
		if(zeroerr(reg))
			// log error
		regs.put(reg, addr.replace("#", ""));
		checkr7(reg);
	}
	
	public String pop(String a) { //F2
		if(zeroerr(a)) return null;
		regs.put(a, gui.memory.getMemoryDirection(Integer.parseInt(sp, 16)).getContent());
		sp = Integer.toString(Integer.parseInt(sp) + 1);
		checkr7(a);
		return a;
	}
	
	public String store(String a, String b) { //F2
		if(zeroerr(b)) return null;
		regs.put(gui.memory.getMemoryDirection(Integer.parseInt(sp, 16)).getContent(), b);
		checkr7(a);
		return b;
	}
	
	public String push(String a) {
		if(zeroerr(a)) return null;
		sp = Integer.toString(Integer.parseInt(sp) - 1);
		regs.put(gui.memory.getMemoryDirection(Integer.parseInt(sp, 16)).getContent(), a);
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
//		if(triplezerr(a, b, c))	return null;
		String tok2 = "", tok3 = "";
		if (c.contains("#")) {
			tok2 = regs.get(b);
			tok3 = c.replaceAll("#", "");
		} else {
			tok2 = regs.get(b);
			tok3 = regs.get(c);
		}
		int add1 = Integer.parseInt(tok2, 16);
		int add2 = Integer.parseInt(tok3, 16);
		int result = add1 + add2;
		regs.put(a, String.format("%02X", (0xFF & result)));
		checkr7(a);
		return regs.get(a);
	}
	
	public String sub(String a, String b, String c) {
		if(triplezerr(a, b, c))	return null;
		String tok2 = "", tok3 = "";
		if (c.contains("#")) {
			tok2 = regs.get(b);
			tok3 = c.replaceAll("#", "");
		} else {
			tok2 = regs.get(b);
			tok3 = regs.get(c);
		}
		int add1 = Integer.parseInt(tok2, 16);
		int add2 = Integer.parseInt(tok3, 16);
		int result = add1 - add2;
		regs.put(a, String.format("%02X", (0xFF & result)));
		checkr7(a);
		return regs.get(a);
	}
	
//	End Arithmetic Logic
	
//	Logic Operators
	public String and(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		int B = Integer.parseInt(regs.get(b), 16);
		int C = Integer.parseInt(regs.get(c), 16);
		int result = (B & C);
		regs.put(a, String.format("%02X", (0xFF & result)));
		checkr7(a);
		return regs.get(a);
	}
	
	public String or(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		int B = Integer.parseInt(regs.get(b), 16);
		int C = Integer.parseInt(regs.get(c), 16);
		int result = (B | C);
		regs.put(a, String.format("%02X", (0xFF & result)));
		checkr7(a);
		return regs.get(a);
	}
	
	public String xor(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		int B = Integer.parseInt(regs.get(b), 16);
		int C = Integer.parseInt(regs.get(c), 16);
		int result = (B ^ C);
		regs.put(a, String.format("%02X", (0xFF & result)));
		checkr7(a);
		return regs.get(a);
	}
	
	public String not(String a, String b) {
		if(doublezerr(a, b)) return null;
		int B = Integer.parseInt(regs.get(b), 16);
		int result = (~B);
		regs.put(a, String.format("%02X", (0xFF & result)));
		checkr7(a);
		return regs.get(a);
	}
	
	public String neg(String a, String b) {
		if(doublezerr(a, b)) return null;
		int B = Integer.parseInt(regs.get(b), 16);
		int result = (-1 * B);
		regs.put(a, String.format("%02X", (0xFF & result)));
		checkr7(a);
		return regs.get(a);
	}
	
	public String shiftr(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, String.format("%02X", (0xFF & (Integer.parseInt(regs.get(b), 16) >>> Integer.parseInt(regs.get(c), 16)))));
		checkr7(a);
		return a;
	}
	
	public String shiftl(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		regs.put(a, String.format("%02X", (0xFF & (Integer.parseInt(regs.get(b), 16) << Integer.parseInt(regs.get(c), 16)))));
		checkr7(a);
		return a;
	}
	
	public String rotar(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		int B = Integer.parseInt(regs.get(b), 16);
		int C = Integer.parseInt(regs.get(c), 16);
		int result = Integer.rotateRight(B, C);
		regs.put(a, String.format("%02X", (0xFF & result)));
		checkr7(a);
		return a;
	}
	
	public String rotal(String a, String b, String c) {
		if(triplezerr(a, b, c)) return null;
		int B = Integer.parseInt(regs.get(b), 16);
		int C = Integer.parseInt(regs.get(c), 16);
		int result = Integer.rotateLeft(B, C);
		regs.put(a, String.format("%02X", (0xFF & result)));
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
	
	public String jcondrin(boolean b, String a) {
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
		gui.memory.getMemoryDirection(Integer.parseInt(sp, 16)).setContent(pc);
		pc = a;
	}
	
	public void ret() {
		int stackPointer = Integer.parseInt(sp, 16);
		pc = gui.memory.getMemoryDirection(stackPointer).getContent();
		sp = String.format("%02X", (stackPointer + 2));
	}
	
//	End Flow
	
//	r0 Error Handler and helpers
	public boolean zeroerr(String a) {
		boolean t = false;
		if(a.equals("r0")) {
			gui.log("Error can't reference r0");
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