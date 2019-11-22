package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Runner {
	
	// This will manage the runs through tokens we need to do to store constants and values and write the object code
	private InstructionSet opcodes = new InstructionSet();
	private InstructionFormat instructionFormat;
	private Register register;
	private Converter converter = new Converter();
	private ArrayList<Token> tokens;
	private GUI gui;
	private Memory mem;
	
	public Map<String, String> values = new HashMap<>();
	public Map<String, String> constants = new HashMap<>();
	
	public Map<String, String> getConstants() {
		return constants;
	}
	
	public Object[][] valuesData() {
		Object[][] data = new Object[8][2];
		
		int l = 0;
		for (String d : this.values.keySet()) {
			data[l][0] = d;
			l++;
		}
		l = 0;
		for (String c : this.values.values()) {
			data[l][1] = c;
			l++;
		}
		return data;
	}
	
	public Object[][] constantsData() {
		Object[][] data = new Object[8][2];
		
		int l = 0;
		for (String d : this.constants.keySet()) {
			data[l][0] = d;
			l++;
		}
		l = 0;
		for (String c : this.constants.values()) {
			data[l][1] = c;
			l++;
		}
		return data;
	}

	public ArrayList<Instruction> instructions = new ArrayList<>();
	public int currentInstruction = 0;
	
	public int getNumOfInstructions() {
		return this.instructions.size();
	}
	
	public int getCurrentInstruction() {
		return this.currentInstruction;
	}
	public void setCurrentInstruction(int i) {
		this.currentInstruction = i;
	}

	public Runner() {
		this.gui = new GUI();
		this.register = gui.reg;
		this.mem = gui.memory;
		this.instructionFormat = new InstructionFormat(gui);
	}

	public Runner(GUI gui) {
		this.gui = gui;
		this.register = gui.reg;
		this.mem = gui.memory;
		this.instructionFormat = new InstructionFormat(gui);
	}
	
	public Instruction run(ArrayList<Token> tokens) {
		// store constants and values
		Iterator<Token> iter = tokens.iterator();
		while (iter.hasNext()) {
			Token currentToken = iter.next();
			switch (currentToken.getType()) {
				case CONST:
					// store constants here
					Token nameToken = iter.next();
					String keywordRegex = "^org$|^jmp$|^const$|^db$|^loadim$|^loadrind$|^load$|^pop$|^storerind$|^push$|^store$|^addim$|^subim$|^add$|^sub$|^and$|^or$|^xor$|^not$|^neg$|^shiftr$|^shiftl$|^rotar$|^rotal$|^jmprind$|^jmpaddr$|^jcondrin$|^jcondaddr$|^loop$|^grteq$|^grt$|^eq$|^neq$|^nop$|^call$|^return$";
					if (nameToken.getType() != TokenType.NAME) {
						gui.log("Not a viable name!");
					} else if (nameToken.getValue().matches(keywordRegex)) {
						gui.log("Not a viable keyword!");
					} else {
						Token valueToken = iter.next();
						if (valueToken.type != TokenType.VALUE) {
							gui.log("Not a valid value!");
						} else {
							System.out.println("running put in constants");
							constants.put(nameToken.value, valueToken.value);
							gui.updateConstantsTable();
						}
					}
				case NAME:
					 // store values here
					keywordRegex = "^org$|^jmp$|^const$|^db$|^loadim$|^loadrind$|^load$|^pop$|^storerind$|^push$|^store$|^addim$|^subim$|^add$|^sub$|^and$|^or$|^xor$|^not$|^neg$|^shiftr$|^shiftl$|^rotar$|^rotal$|^jmprind$|^jmpaddr$|^jcondrin$|^jcondaddr$|^loop$|^grteq$|^grt$|^eq$|^neq$|^nop$|^call$|^return$";
					if (currentToken.getType() != TokenType.NAME) {
						gui.log("Not a viable name!");
					}else if (currentToken.getValue().matches(keywordRegex)) {
						gui.log("Name is a keyword!");
					}
					Token dbToken = iter.next();
					if (dbToken.type != TokenType.DB) {
						gui.log("Not a db token!");
					}
					Token valueToken = iter.next();
					if (valueToken.type != TokenType.VALUE) {
						gui.log("Not a valid value!");
					} else {
						System.out.println("running put in constants");
						values.put(currentToken.value, valueToken.value);
						gui.updateValuesTable();
					}
				case OPCODE:
					switch (currentToken.getValue()) {
						case "load":
							opcodes = new InstructionSet();
							Instruction load = opcodes.getInstruction(currentToken.getValue());
							load.addToken(currentToken);
							Token registerToken = iter.next();
							if (registerToken.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								load.addToken(registerToken);
							}
							Token addressToken = iter.next();
							if (addressToken.getType() != TokenType.ADDRESS && addressToken.getType() != TokenType.NAME && addressToken.getType() != TokenType.VALUE) {
								addresslog();
							} else {
								load.addToken(addressToken);
							}
							// TODO: check what type is address to get the correct content for all cases
							String content = this.mem.getMemoryDirection(Integer.parseInt(addressToken.getValue())).getContent();
							this.register.getregs().put(registerToken.getValue(), content);
							this.gui.updateRegisterTable();
							instructions.add(load);
							return load;
						case "loadim":
							opcodes = new InstructionSet();
							Instruction loadim = opcodes.getInstruction(currentToken.getValue());
							loadim.addToken(currentToken);
							Token registerToken1 = iter.next();
							if (registerToken1.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								loadim.addToken(registerToken1);
							}
							Token constantToken = iter.next();
							if (constantToken.getType() != TokenType.CONST && constantToken.getType() != TokenType.HEX) {
								constlog();
							} else {
								loadim.addToken(constantToken);
							}
							// TODO: check constant type to get the correct content for all cases
							this.register.getregs().put(registerToken1.getValue(), constantToken.getValue());
							this.gui.updateRegisterTable();
							instructions.add(loadim);
							return loadim;
						case "pop":
							opcodes = new InstructionSet();
							Instruction pop = opcodes.getInstruction(currentToken.getValue());
							pop.addToken(currentToken);
							Token registerToken2 = iter.next();
							if (registerToken2.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								pop.addToken(registerToken2);
							}
							// TODO: check if the register is r0 to throw error if it is
							this.register.getregs().put(registerToken2.getValue(), this.mem.getMemoryDirection(Integer.parseInt(this.register.sp, 16)).getContent());
							// TODO: increment SP by 1 (SP = SP + 1)
							// TODO: update SP in GUI
							this.gui.updateRegisterTable();
							instructions.add(pop);
							return pop;
						case "store":
							opcodes = new InstructionSet();
							Instruction store = opcodes.getInstruction(currentToken.getValue());
							store.addToken(currentToken);
							Token registerToken3 = iter.next();
							if (registerToken3.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								store.addToken(registerToken3);
							}
							Token addressToken1 = iter.next();
							if (addressToken1.getType() != TokenType.ADDRESS && addressToken1.getType() != TokenType.NAME && addressToken1.getType() != TokenType.VALUE) {
								addresslog();
							} else {
								store.addToken(addressToken1);
							}
							// TODO: check what type is address to get the correct content for all cases
 							this.mem.getMemoryDirection(Integer.parseInt(addressToken1.getValue(), 16)).setContent(this.register.getregs().get(registerToken3.getValue()));
 							gui.updateMemoryTable();
 							instructions.add(store);
 							return store;
						case "push":
							opcodes = new InstructionSet();
							Instruction push = opcodes.getInstruction(currentToken.getValue());
							push.addToken(currentToken);
							Token registerToken4 = iter.next();
							if (registerToken4.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								push.addToken(registerToken4);
							}
							// TODO: decrement SP by 1 (SP = SP - 1)
							// TODO: update SP in GUI
							this.mem.getMemoryDirection(Integer.parseInt(this.register.sp, 16)).setContent(this.register.getregs().get(registerToken4.getValue()));
							gui.updateMemoryTable();
							instructions.add(push);
							return push;
						case "loadrind":
							opcodes = new InstructionSet();
							Instruction loadrind = opcodes.getInstruction(currentToken.getValue());
							loadrind.addToken(currentToken);
							Token registerToken5 = iter.next();
							if (registerToken5.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								loadrind.addToken(registerToken5);
							}
							Token registerToken6 = iter.next();
							if (registerToken6.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								loadrind.addToken(registerToken6);
							}
							this.register.getregs().put(registerToken5.getValue(), this.mem.getMemoryDirection(Integer.parseInt(this.register.getregs().get(registerToken6.getValue()), 16)).getContent());
							gui.updateRegisterTable();
							instructions.add(loadrind);
							return loadrind;
						case "storerind":
							opcodes = new InstructionSet();
							Instruction storerind = opcodes.getInstruction(currentToken.getValue());
							storerind.addToken(currentToken);
							Token registerToken7 = iter.next();
							if (registerToken7.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								storerind.addToken(registerToken7);
							}
							Token registerToken8 = iter.next();
							if (registerToken8.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								storerind.addToken(registerToken8);
							}
							this.register.getregs().put(registerToken8.getValue(), this.mem.getMemoryDirection(Integer.parseInt(this.register.getregs().get(registerToken7.getValue()), 16)).getContent());
							gui.updateRegisterTable();
							instructions.add(storerind);
							return storerind;
						case "add":
							opcodes = new InstructionSet();
							Instruction add = opcodes.getInstruction(currentToken.getValue());
							add.addToken(currentToken);
							Token registerToken9 = iter.next();
							if (registerToken9.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								add.addToken(registerToken9);
							}
							Token registerToken10 = iter.next();
							if (registerToken10.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								add.addToken(registerToken10);
							}
							Token registerToken11 = iter.next();
							if (registerToken11.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								add.addToken(registerToken11);
							}
							this.register.add(registerToken9.getValue(), registerToken10.getValue(), registerToken11.getValue());
							gui.updateRegisterTable();
							instructions.add(add);
							return add;
						case "sub":
							opcodes = new InstructionSet();
							Instruction sub = opcodes.getInstruction(currentToken.getValue());
							sub.addToken(currentToken);
							Token registerToken12 = iter.next();
							if (registerToken12.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								sub.addToken(registerToken12);
							}
							Token registerToken13 = iter.next();
							if (registerToken13.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								sub.addToken(registerToken13);
							}
							Token registerToken14 = iter.next();
							if (registerToken14.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								sub.addToken(registerToken14);
							}
							this.register.sub(registerToken12.getValue(), registerToken13.getValue(), registerToken14.getValue());
							gui.updateRegisterTable();
							instructions.add(sub);
							return sub;
						case "addim":
							opcodes = new InstructionSet();
							Instruction addim = opcodes.getInstruction(currentToken.getValue());
							addim.addToken(currentToken);
							Token registerToken15 = iter.next();
							if (registerToken15.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								addim.addToken(registerToken15);
							}
							Token constantToken1 = iter.next();
							if (constantToken1.getType() != TokenType.CONST && constantToken1.getType() != TokenType.HEX) {
								constlog();
							} else {
								addim.addToken(constantToken1);
							}
							this.register.add(registerToken15.getValue(), registerToken15.getValue(), constantToken1.getValue());
							gui.updateRegisterTable();
							instructions.add(addim);
							return addim;
						case "subim":
							opcodes = new InstructionSet();
							Instruction subim = opcodes.getInstruction(currentToken.getValue());
							subim.addToken(currentToken);
							Token registerToken16 = iter.next();
							if (registerToken16.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								subim.addToken(registerToken16);
							}
							Token constantToken2 = iter.next();
							if (constantToken2.getType() != TokenType.CONST && constantToken2.getType() != TokenType.HEX) {
								constlog();
							} else {
								subim.addToken(constantToken2);
							}
							this.register.sub(registerToken16.getValue(), registerToken16.getValue(), constantToken2.getValue());
							gui.updateRegisterTable();
							instructions.add(subim);
							return subim;
						case "and":
							opcodes = new InstructionSet();
							Instruction and = opcodes.getInstruction(currentToken.getValue());
							and.addToken(currentToken);
							Token registerToken17 = iter.next();
							if (registerToken17.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								and.addToken(registerToken17);
							}
							Token registerToken18 = iter.next();
							if (registerToken18.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								and.addToken(registerToken18);
							}
							Token registerToken19 = iter.next();
							if (registerToken19.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								and.addToken(registerToken19);
							}
							this.register.and(registerToken17.getValue(), registerToken18.getValue(), registerToken19.getValue());
							gui.updateRegisterTable();
							instructions.add(and);
							return and;
						case "or":
							opcodes = new InstructionSet();
							Instruction or = opcodes.getInstruction(currentToken.getValue());
							or.addToken(currentToken);
							Token registerToken20 = iter.next();
							if (registerToken20.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								or.addToken(registerToken20);
							}
							Token registerToken21 = iter.next();
							if (registerToken21.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								or.addToken(registerToken21);
							}
							Token registerToken22 = iter.next();
							if (registerToken22.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								or.addToken(registerToken22);
							}
							this.register.or(registerToken20.getValue(), registerToken21.getValue(), registerToken22.getValue());
							gui.updateRegisterTable();
							instructions.add(or);
							return or;
						case "xor":
							opcodes = new InstructionSet();
							Instruction xor = opcodes.getInstruction(currentToken.getValue());
							xor.addToken(currentToken);
							Token registerToken23 = iter.next();
							if (registerToken23.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								xor.addToken(registerToken23);
							}
							Token registerToken24 = iter.next();
							if (registerToken24.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								xor.addToken(registerToken24);
							}
							Token registerToken25 = iter.next();
							if (registerToken25.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								xor.addToken(registerToken25);
							}
							this.register.xor(registerToken23.getValue(), registerToken24.getValue(), registerToken25.getValue());
							gui.updateRegisterTable();
							instructions.add(xor);
							return xor;
						case "not":
							opcodes = new InstructionSet();
							Instruction not = opcodes.getInstruction(currentToken.getValue());
							not.addToken(currentToken);
							Token registerToken26 = iter.next();
							if (registerToken26.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								not.addToken(registerToken26);
							}
							Token registerToken27 = iter.next();
							if (registerToken27.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								not.addToken(registerToken27);
							}
							this.register.not(registerToken26.getValue(), registerToken27.getValue());
							gui.updateRegisterTable();
							instructions.add(not);
							return not;
						case "neg":
							opcodes = new InstructionSet();
							Instruction neg = opcodes.getInstruction(currentToken.getValue());
							neg.addToken(currentToken);
							Token registerToken28 = iter.next();
							if (registerToken28.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								neg.addToken(registerToken28);
							}
							Token registerToken29 = iter.next();
							if (registerToken29.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								neg.addToken(registerToken29);
							}
							this.register.neg(registerToken28.getValue(), registerToken29.getValue());
							gui.updateRegisterTable();
							instructions.add(neg);
							return neg;
						case "shiftr":
							opcodes = new InstructionSet();
							Instruction shiftr = opcodes.getInstruction(currentToken.getValue());
							shiftr.addToken(currentToken);
							Token registerToken30 = iter.next();
							if (registerToken30.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								shiftr.addToken(registerToken30);
							}
							Token registerToken31 = iter.next();
							if (registerToken31.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								shiftr.addToken(registerToken31);
							}
							Token registerToken32 = iter.next();
							if (registerToken32.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								shiftr.addToken(registerToken32);
							}
							this.register.shiftr(registerToken30.getValue(), registerToken31.getValue(), registerToken32.getValue());
							gui.updateRegisterTable();
							instructions.add(shiftr);
							return shiftr;
						case "shiftl":
							opcodes = new InstructionSet();
							Instruction shiftl = opcodes.getInstruction(currentToken.getValue());
							shiftl.addToken(currentToken);
							Token registerToken33 = iter.next();
							if (registerToken33.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								shiftl.addToken(registerToken33);
							}
							Token registerToken34 = iter.next();
							if (registerToken34.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								shiftl.addToken(registerToken34);
							}
							Token registerToken35 = iter.next();
							if (registerToken35.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								shiftl.addToken(registerToken35);
							}
							this.register.shiftl(registerToken33.getValue(), registerToken34.getValue(), registerToken35.getValue());
							gui.updateRegisterTable();
							instructions.add(shiftl);
							return shiftl;
						case "rotar":
							opcodes = new InstructionSet();
							Instruction rotar = opcodes.getInstruction(currentToken.getValue());
							rotar.addToken(currentToken);
							Token registerToken36 = iter.next();
							if (registerToken36.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								rotar.addToken(registerToken36);
							}
							Token registerToken37 = iter.next();
							if (registerToken37.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								rotar.addToken(registerToken37);
							}
							Token registerToken38 = iter.next();
							if (registerToken38.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								rotar.addToken(registerToken38);
							}
							this.register.rotar(registerToken36.getValue(), registerToken37.getValue(), registerToken38.getValue());
							gui.updateRegisterTable();
							instructions.add(rotar);
							return rotar;
						case "rotal":
							opcodes = new InstructionSet();
							Instruction rotal = opcodes.getInstruction(currentToken.getValue());
							rotal.addToken(currentToken);
							Token registerToken39 = iter.next();
							if (registerToken39.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								rotal.addToken(registerToken39);
							}
							Token registerToken40 = iter.next();
							if (registerToken40.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								rotal.addToken(registerToken40);
							}
							Token registerToken41 = iter.next();
							if (registerToken41.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								rotal.addToken(registerToken41);
							}
							this.register.rotal(registerToken39.getValue(), registerToken40.getValue(), registerToken41.getValue());
							gui.updateRegisterTable();
							instructions.add(rotal);
							return rotal;
						case "jmprind":
							opcodes = new InstructionSet();
							Instruction jmprind = opcodes.getInstruction(currentToken.getValue());
							jmprind.addToken(currentToken);
							Token registerToken42 = iter.next();
							if (registerToken42.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								jmprind.addToken(registerToken42);
							}
							this.register.jmprind(registerToken42.getValue());
							gui.updateRegisterTable();
							instructions.add(jmprind);
							return jmprind;
						case "jmpaddr":
							opcodes = new InstructionSet();
							Instruction jmpaddr = opcodes.getInstruction(currentToken.getValue());
							jmpaddr.addToken(currentToken);
							Token addressToken2 = iter.next();
							if (addressToken2.getType() != TokenType.ADDRESS && addressToken2.getType() != TokenType.NAME && addressToken2.getType() != TokenType.VALUE) {
								addresslog();
							} else {
								jmpaddr.addToken(addressToken2);
							}
							// TODO: check address type
							this.register.jmpaddr(addressToken2.getValue());
							gui.updateRegisterTable();
							instructions.add(jmpaddr);
							return jmpaddr;
						case "jcondrin":
							opcodes = new InstructionSet();
							Instruction jcondrin = opcodes.getInstruction(currentToken.getValue());
							jcondrin.addToken(currentToken);
							Token registerToken43 = iter.next();
							if (registerToken43.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								jcondrin.addToken(registerToken43);
							}
							this.register.jcondrin(this.register.cond, registerToken43.getValue());
							gui.updateRegisterTable();
							instructions.add(jcondrin);
							return jcondrin;
						case "jcondaddr":
							opcodes = new InstructionSet();
							Instruction jcondaddr = opcodes.getInstruction(currentToken.getValue());
							jcondaddr.addToken(currentToken);
							Token addressToken3 = iter.next();
							if (addressToken3.getType() != TokenType.ADDRESS && addressToken3.getType() != TokenType.NAME && addressToken3.getType() != TokenType.VALUE) {
								addresslog();
							} else {
								jcondaddr.addToken(addressToken3);
							}
							// TODO: check address type
							this.register.jcondaddr(this.register.cond, addressToken3.getValue());
							gui.updateRegisterTable();
							instructions.add(jcondaddr);
							return jcondaddr;
						case "loop":
							opcodes = new InstructionSet();
							Instruction loop = opcodes.getInstruction(currentToken.getValue());
							loop.addToken(currentToken);
							Token registerToken44 = iter.next();
							if (registerToken44.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								loop.addToken(registerToken44);
							}
							Token addressToken4 = iter.next();
							if (addressToken4.getType() != TokenType.ADDRESS && addressToken4.getType() != TokenType.NAME && addressToken4.getType() != TokenType.VALUE) {
								addresslog();
							} else {
								loop.addToken(addressToken4);
							}
							// TODO: check address type
							this.register.loop(registerToken44.getValue(), addressToken4.getValue());
							gui.updateRegisterTable();
							instructions.add(loop);
							return loop;
						case "grt":
							opcodes = new InstructionSet();
							Instruction grt = opcodes.getInstruction(currentToken.getValue());
							grt.addToken(currentToken);
							Token registerToken45 = iter.next();
							if (registerToken45.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								grt.addToken(registerToken45);
							}
							Token registerToken46 = iter.next();
							if (registerToken46.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								grt.addToken(registerToken46);
							}
							this.register.cond = this.register.grt(registerToken45.getValue(), registerToken46.getValue());
							gui.updateRegisterTable();
							instructions.add(grt);
							return grt;
						case "grteq":
							opcodes = new InstructionSet();
							Instruction grteq = opcodes.getInstruction(currentToken.getValue());
							grteq.addToken(currentToken);
							Token registerToken47 = iter.next();
							if (registerToken47.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								grteq.addToken(registerToken47);
							}
							Token registerToken48 = iter.next();
							if (registerToken48.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								grteq.addToken(registerToken48);
							}
							this.register.cond = this.register.grteq(registerToken47.getValue(), registerToken48.getValue());
							gui.updateRegisterTable();
							instructions.add(grteq);
							return grteq;
						case "eq":
							opcodes = new InstructionSet();
							Instruction eq = opcodes.getInstruction(currentToken.getValue());
							eq.addToken(currentToken);
							Token registerToken49 = iter.next();
							if (registerToken49.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								eq.addToken(registerToken49);
							}
							Token registerToken50 = iter.next();
							if (registerToken50.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								eq.addToken(registerToken50);
							}
							this.register.cond = this.register.eq(registerToken49.getValue(), registerToken50.getValue());
							gui.updateRegisterTable();
							instructions.add(eq);
							return eq;
						case "neq":
							opcodes = new InstructionSet();
							Instruction neq = opcodes.getInstruction(currentToken.getValue());
							neq.addToken(currentToken);
							Token registerToken51 = iter.next();
							if (registerToken51.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								neq.addToken(registerToken51);
							}
							Token registerToken52 = iter.next();
							if (registerToken52.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								neq.addToken(registerToken52);
							}
							this.register.cond = this.register.neq(registerToken51.getValue(), registerToken52.getValue());
							gui.updateRegisterTable();
							instructions.add(neq);
							return neq;
						case "nop":
							opcodes = new InstructionSet();
							Instruction nop = opcodes.getInstruction(currentToken.getValue());
							nop.addToken(currentToken);
							instructions.add(nop);
							return nop;
						case "call":
							opcodes = new InstructionSet();
							Instruction call = opcodes.getInstruction(currentToken.getValue());
							call.addToken(currentToken);
							Token addressToken5 = iter.next();
							if (addressToken5.getType() != TokenType.ADDRESS && addressToken5.getType() != TokenType.NAME && addressToken5.getType() != TokenType.VALUE) {
								addresslog();
							} else {
								call.addToken(addressToken5);
							}
							// TODO: check address type
							this.register.call(addressToken5.getValue());
							gui.updateMemoryTable();
							gui.updateRegisterTable();
							instructions.add(call);
							return call;
						case "return":
							opcodes = new InstructionSet();
							Instruction r = opcodes.getInstruction(currentToken.getValue());
							r.addToken(currentToken);
							this.register.ret();
							gui.updateRegisterTable();
							instructions.add(r);
							return r;
						default:
							oplog();
							break;
					}
				default:
					gui.log("Not valid tokens!");
					break;
			}
		}
		return null;
	}
	
	public String executeLine(Instruction instruction) {
		String result = "";
		if (instruction == null) return "No OPCODE. Not an instruction";
		Instruction i = instruction;
//		System.out.println(i.toString() + "\n" + "Num Of Tokens: " + i.getTokens().size());
		if(i.getTokens().size() == 4) {
 			result = instructionFormat.Fswitch(i.getTokens().get(0).getValue(), i.getTokens().get(1).getValue(), 
 					i.getTokens().get(2).getValue(), i.getTokens().get(3).getValue(), i.getFormat());
 		}
 		else if(i.getTokens().size() == 3) {
 			result = instructionFormat.Fswitch(i.getTokens().get(0).getValue(), i.getTokens().get(1).getValue(), 
 					i.getTokens().get(2).getValue(), "", i.getFormat());
 		}
 		else if(i.getTokens().size() == 2) {
 			result = instructionFormat.Fswitch(i.getTokens().get(0).getValue(), i.getTokens().get(1).getValue(), 
 					"", "", i.getFormat());
 		} else if(i.getTokens().size() == 1) {
 			result = instructionFormat.Fswitch(i.getTokens().get(0).getValue(), "", "", "", i.getFormat());
 		}
		return result;
	}
	
	public String executeAll() {
		String result = "";
		
		return result;
	}
	
//	Small helper methods
	public void reglog() {
		gui.log("Not a valid register!");
	}
	
	public void addresslog() {
		gui.log("Not a valid address, name, or value!");
	}
	
	public void oplog() {
		gui.log("Not a valid opcode!");
	}
	
	public void constlog() {
		gui.log("Not a valid const or hex!");
	}
}