package assembler;

import java.util.ArrayList;
import java.util.Iterator;

public class Runner {
	
	// This will manage the runs through tokens we need to do to store constants and values and write the object code
	private InstructionSet opcodes = new InstructionSet();
	private InstructionFormat instructionFormat;
	private Register register;
	private GUI gui;
	private Memory mem;
	
	public class Label {
		
		public String name;
		public int direction;
		public int line;
		
		public Label(String name, int direction, int line) {
			this.name = name;
			this.direction = direction;
			this.line = line;
		}
		
		public String getName() {
			return name;
		}
		
		public int getDirection() {
			return direction;
		}
		
		public int getLine() {
			return line;
		}
		
		@Override
		public String toString() {
			return String.format("Name: %s, Direction: %s, In Line: %s", name, direction, line);
		}
	}
	
	public class Value {
		
		public String name;
		public int direction;
		
		public Value(String name, int direction) {
			this.name = name;
			this.direction = direction;
		}
		
		public String getName() {
			return name;
		}
		
		public int getDirection() {
			return direction;
		}
		
		@Override
		public String toString() {
			return String.format("Name: %s, Direction: %s", name, direction);
		}
	}
	
	public class Constant {
		
		public String name;
		public String content;
		
		public Constant(String name, String content) {
			this.name = name;
			this.content = content;
		}
		
		public String getName() {
			return name;
		}

		public String getContent() {
			return content;
		}
		
		@Override
		public String toString() {
			return String.format("Name: %s, Content: %s", name, content);
		}
	}
	
	public ArrayList<Value> values = new ArrayList<>();
	public ArrayList<Constant> constants = new ArrayList<>();
	public ArrayList<Label> labels = new ArrayList<>();
	
	public int getLabelLine(String name) {
		for (Label l : this.labels) {
			if (l.getName().equals(name)) return l.getLine();
		}
		return -1;
	}
	
	public int getLabelDirection(String name) {
		for (Label l : this.labels) {
			if (l.getName().equals(name)) return l.getDirection();
		}
		return -1;
	}
	
	public boolean containsValueDirection(int direction) {
		for (Value v : this.values) {
			if (v.getDirection() == direction) return true;
		}
		return false;
	}
	
	public int getValueDirection(String name) {
		for (Value v : this.values) {
			if (v.getName().equals(name)) return v.getDirection();
		}
		return -1;
	}
	
	public String getConstantContent(String name) {
		for (Constant c : this.constants) {
			if (c.getName().equals(name)) return c.getContent();
		}
		return "";
	}
	
	public ArrayList<Constant> getConstants() {
		return constants;
	}
	
	public Object[][] valuesData() {
		Object[][] data = new Object[2048][2];
		
		int l = 0;
		for (Value d : this.values) {
			data[l][0] = d.name;
			l++;
		}
		l = 0;
		for (Value c : this.values) {
			data[l][1] = c.direction;
			l++;
		}
		return data;
	}
	
	public Object[][] constantsData() {
		Object[][] data = new Object[2048][2];
		
		int l = 0;
		for (Constant d : this.constants) {
			data[l][0] = d.name;
			l++;
		}
		l = 0;
		for (Constant c : this.constants) {
			data[l][1] = c.content;
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
	
	public Instruction initRun(ArrayList<Token> tokens, int currentLine) {
		Iterator<Token> iter = tokens.iterator();
		while (iter.hasNext()) {
			if (!tokens.isEmpty()) {
				Token currentToken = iter.next();
//				System.out.println(tokens);
				switch (currentToken.getType()) {
					case LABEL:
						Label l = new Label(currentToken.getValue().replaceAll(":", ""), gui.memory.getNextAvailableMemoryDirection(), currentLine);
//						System.out.println(l);
						if (this.getLabelDirection(currentToken.getValue().replaceAll(":", "")) == -1) {
//							gui.log("LABEL ERROR: Label already exists.");
							labels.add(l);
						}
						return null;
					case COMMENT:
//						gui.log("Comment: " + currentToken.getValue());
						return null;
					case ORIGIN:
						Token originAddressToken = iter.next();
//						System.out.println(originAddressToken.getType());
						if (originAddressToken.getType() != TokenType.ADDRESS) {
							gui.log("Origin address error");
							return null;
						} else if (originAddressToken.getType() == TokenType.NAME) {
							int memLocation = this.getValueDirection(originAddressToken.getValue());
							gui.memory.setNextDirection(memLocation);
							gui.updateMemoryTable();
//							gui.log("Origin set at " + memLocation + ".");
							return null;
						} else {
							int memLocation = Integer.parseInt(originAddressToken.getValue());
							gui.memory.setNextDirection(memLocation);
							gui.updateMemoryTable();
//							gui.log("Origin set at " + memLocation + ".");
							return null;
						}
					case CONST:
						// store constants here
						Token nameToken = iter.next();
						String keywordRegex = "^org$|^jmp$|^const$|^db$|^loadim$|^loadrind$|^load$|^pop$|^storerind$|^push$|^store$|^addim$|^subim$|^add$|^sub$|^and$|^or$|^xor$|^not$|^neg$|^shiftr$|^shiftl$|^rotar$|^rotal$|^jmprind$|^jmpaddr$|^jcondrin$|^jcondaddr$|^loop$|^grteq$|^grt$|^eq$|^neq$|^nop$|^call$|^return$";
						if (nameToken.getType() != TokenType.NAME) {
//							gui.log("Not a viable name!");
						} else if (nameToken.getValue().matches(keywordRegex)) {
							gui.log("Not a viable keyword!");
						} else {
							Token valueToken = iter.next();
							if (valueToken.type != TokenType.VALUE) {
//								gui.log("Not a valid value!");
							} else {
								Constant c = new Constant(nameToken.value, valueToken.value.toUpperCase());
								if (this.getConstantContent(nameToken.value).equals("")) {
									constants.add(c);
								}
								gui.updateConstantsTable();
								return null;
							}
						}
					case NAME:
						 // store values here
						keywordRegex = "^org$|^jmp$|^const$|^db$|^loadim$|^loadrind$|^load$|^pop$|^storerind$|^push$|^store$|^addim$|^subim$|^add$|^sub$|^and$|^or$|^xor$|^not$|^neg$|^shiftr$|^shiftl$|^rotar$|^rotal$|^jmprind$|^jmpaddr$|^jcondrin$|^jcondaddr$|^loop$|^grteq$|^grt$|^eq$|^neq$|^nop$|^call$|^return$";
						if (currentToken.getType() != TokenType.NAME) {
//							gui.log("Not a name token!" + currentToken.getType());
						} else if (currentToken.getValue().matches(keywordRegex)) {
//							gui.log("Name is a keyword!");
						} else if (tokens.size() == 1) {
//							gui.log("SYNTAX ERROR IN LINE " + (currentLine+1));
							return null;
						}
						Token dbToken = iter.next();
						if (dbToken.type != TokenType.DB) {
							gui.log("Not a db token!");
							return null;
						}
						Token valueToken = iter.next();
						if (valueToken.type != TokenType.VALUE) {
							gui.log("Not a valid value!");
							return null;
						} else {
							int nextMemDirection = gui.memory.getNextAvailableMemoryDirection();
//							System.out.println(nextMemDirection);
							if (!this.containsValueDirection(nextMemDirection)) {
								values.add(new Value(currentToken.value, nextMemDirection));
//								gui.memory.getMemoryDirection(nextMemDirection).setContent(valueToken.value.toUpperCase());
//								gui.memory.next = nextMemDirection+1;
							}
							for (int i = 3; i < tokens.size(); i++) {
								valueToken = iter.next();
								nextMemDirection = gui.memory.getNextAvailableMemoryDirection();
								if (!this.containsValueDirection(nextMemDirection)) {
									values.add(new Value(currentToken.value, nextMemDirection));
//									gui.memory.getMemoryDirection(nextMemDirection).setContent(valueToken.value.toUpperCase());
//									gui.memory.next = nextMemDirection+1;
								}
							}
							gui.updateValuesTable();
							gui.updateMemoryTable();
							return null;
						}
					case DB:
						Token valueToken1 = iter.next();
						if (valueToken1.type != TokenType.VALUE) {
//							gui.log("Not a valid value!");
						} else {
							int nextMemDirection = gui.memory.getNextAvailableMemoryDirection();
//							System.out.println(nextMemDirection);
							values.add(new Value("", nextMemDirection));
//							gui.memory.getMemoryDirection(nextMemDirection).setContent(valueToken1.value.toUpperCase());
							gui.memory.next = nextMemDirection+1;
							for (int i = 2; i < tokens.size(); i++) {
								valueToken1 = iter.next();
								nextMemDirection = gui.memory.getNextAvailableMemoryDirection();
								values.add(new Value("", nextMemDirection));
//								gui.memory.getMemoryDirection(nextMemDirection).setContent(valueToken1.value.toUpperCase());
//								gui.memory.next = nextMemDirection+1;
							}
							gui.updateValuesTable();
							gui.updateMemoryTable();
							return null;
						}
					default:
						return null;
				}
			}
			
		}
		return null;
	}
	
	public Instruction firstRun(ArrayList<Token> tokens, int currentLine) {
		Iterator<Token> iter = tokens.iterator();
		while (iter.hasNext()) {
			if (!tokens.isEmpty()) {
				Token currentToken = iter.next();
				System.out.println(tokens);
				switch (currentToken.getType()) {
					case LABEL:
						return null;
					case COMMENT:
						return null;
					case ORIGIN:
						return null;
					case CONST:
						return null;
					case NAME:
						 // store values here
						String keywordRegex = "^org$|^jmp$|^const$|^db$|^loadim$|^loadrind$|^load$|^pop$|^storerind$|^push$|^store$|^addim$|^subim$|^add$|^sub$|^and$|^or$|^xor$|^not$|^neg$|^shiftr$|^shiftl$|^rotar$|^rotal$|^jmprind$|^jmpaddr$|^jcondrin$|^jcondaddr$|^loop$|^grteq$|^grt$|^eq$|^neq$|^nop$|^call$|^return$";
						if (currentToken.getType() != TokenType.NAME) {
	//						gui.log("Not a name token!" + currentToken.getType());
						} else if (currentToken.getValue().matches(keywordRegex)) {
	//						gui.log("Name is a keyword!");
						} else if (tokens.size() == 1) {
	//						gui.log("SYNTAX ERROR IN LINE " + (currentLine+1));
							return null;
						}
						Token dbToken = iter.next();
						if (dbToken.type != TokenType.DB) {
	//						gui.log("Not a db token!");
							// STOP ERROR HERE
						}
						Token valueToken = iter.next();
						if (valueToken.type != TokenType.VALUE) {
	//						gui.log("Not a valid value!");
						} else {
							int nextMemDirection = gui.memory.getNextAvailableMemoryDirection();
	//						System.out.println(nextMemDirection);
							if (!this.containsValueDirection(nextMemDirection)) {
								values.add(new Value(currentToken.value, nextMemDirection));
								gui.memory.getMemoryDirection(nextMemDirection).setContent(valueToken.value.toUpperCase());
//								gui.memory.next = nextMemDirection+1;
							}
							for (int i = 3; i < tokens.size(); i++) {
								valueToken = iter.next();
								nextMemDirection = gui.memory.getNextAvailableMemoryDirection();
								if (!this.containsValueDirection(nextMemDirection)) {
									values.add(new Value(currentToken.value, nextMemDirection));
									gui.memory.getMemoryDirection(nextMemDirection).setContent(valueToken.value.toUpperCase());
//									gui.memory.next = nextMemDirection+1;
								}
							}
							gui.updateValuesTable();
							gui.updateMemoryTable();
							return null;
						}
					case DB:
						Token valueToken1 = iter.next();
						if (valueToken1.type != TokenType.VALUE) {
	//						gui.log("Not a valid value!");
						} else {
							int nextMemDirection = gui.memory.getNextAvailableMemoryDirection();
	//						System.out.println(nextMemDirection);
							values.add(new Value("", nextMemDirection));
							gui.memory.getMemoryDirection(nextMemDirection).setContent(valueToken1.value.toUpperCase());
//							gui.memory.next = nextMemDirection+1;
							for (int i = 2; i < tokens.size(); i++) {
								valueToken1 = iter.next();
								nextMemDirection = gui.memory.getNextAvailableMemoryDirection();
								values.add(new Value("", nextMemDirection));
								gui.memory.getMemoryDirection(nextMemDirection).setContent(valueToken1.value.toUpperCase());
//								gui.memory.next = nextMemDirection+1;
							}
							gui.updateValuesTable();
							gui.updateMemoryTable();
							return null;
						}
					case OPCODE:
						switch (currentToken.getValue().toLowerCase()) {
							case "load":
								opcodes = new InstructionSet();
								Instruction load = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								load.addToken(currentToken);
								Token registerToken = iter.next();
								if (registerToken.getType() != TokenType.REGISTER) {
									gui.log("SYNTAX ERROR IN LINE " + (gui.currentLine + 1));
									return null;
								} else {
									load.addToken(registerToken);
								}
								Token addressToken = iter.next();
								if (addressToken.getType() != TokenType.ADDRESS && addressToken.getType() != TokenType.NAME && addressToken.getType() != TokenType.VALUE) {
									gui.log("SYNTAX ERROR IN LINE " + (gui.currentLine + 1));
									return null;
								} else {
									load.addToken(addressToken);
								}
								instructions.add(load);
								return load;
							case "loadim":
								opcodes = new InstructionSet();
								Instruction loadim = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								if (registerToken1.getValue().toLowerCase().contains("0")) {
									gui.log("REGISTER 0 CANNOT BE MODIFIED.");
									return null;
								}
								instructions.add(loadim);
								return loadim;
							case "pop":
								opcodes = new InstructionSet();
								Instruction pop = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								pop.addToken(currentToken);
								Token registerToken2 = iter.next();
								if (registerToken2.getType() != TokenType.REGISTER) {
									reglog();
								} else {
									pop.addToken(registerToken2);
								}
								instructions.add(pop);
								return pop;
							case "store":
								opcodes = new InstructionSet();
								Instruction store = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								store.addToken(currentToken);
								Token addressToken1 = iter.next();
								if (addressToken1.getType() != TokenType.ADDRESS && addressToken1.getType() != TokenType.NAME && addressToken1.getType() != TokenType.VALUE) {
									addresslog();
								} else {
									store.addToken(addressToken1);
								}
								Token registerToken3 = iter.next();
								if (registerToken3.getType() != TokenType.REGISTER) {
									reglog();
								} else {
									store.addToken(registerToken3);
								}
								// TODO: check what type is address to get the correct content for all cases
								if (addressToken1.getType() == TokenType.NAME) {
									int direction = this.getValueDirection(addressToken1.getValue());
									store.getTokens().get(1).setValue(direction + "");
									System.out.println("direction: " + direction);
								}
	 							instructions.add(store);
	 							return store;
							case "push":
								opcodes = new InstructionSet();
								Instruction push = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								push.addToken(currentToken);
								Token registerToken4 = iter.next();
								if (registerToken4.getType() != TokenType.REGISTER) {
									reglog();
								} else {
									push.addToken(registerToken4);
								}
								// TODO: decrement SP by 1 (SP = SP - 1)
								// TODO: update SP in GUI
								instructions.add(push);
								return push;
							case "loadrind":
								opcodes = new InstructionSet();
								Instruction loadrind = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(loadrind);
								return loadrind;
							case "storerind":
								opcodes = new InstructionSet();
								Instruction storerind = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(storerind);
								return storerind;
							case "add":
								opcodes = new InstructionSet();
								Instruction add = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(add);
								return add;
							case "sub":
								opcodes = new InstructionSet();
								Instruction sub = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(sub);
								return sub;
							case "addim":
								opcodes = new InstructionSet();
								Instruction addim = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(addim);
								return addim;
							case "subim":
								opcodes = new InstructionSet();
								Instruction subim = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(subim);
								return subim;
							case "and":
								opcodes = new InstructionSet();
								Instruction and = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(and);
								return and;
							case "or":
								opcodes = new InstructionSet();
								Instruction or = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(or);
								return or;
							case "xor":
								opcodes = new InstructionSet();
								Instruction xor = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(xor);
								return xor;
							case "not":
								opcodes = new InstructionSet();
								Instruction not = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(not);
								return not;
							case "neg":
								opcodes = new InstructionSet();
								Instruction neg = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(neg);
								return neg;
							case "shiftr":
								opcodes = new InstructionSet();
								Instruction shiftr = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(shiftr);
								return shiftr;
							case "shiftl":
								opcodes = new InstructionSet();
								Instruction shiftl = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(shiftl);
								return shiftl;
							case "rotar":
								opcodes = new InstructionSet();
								Instruction rotar = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(rotar);
								return rotar;
							case "rotal":
								opcodes = new InstructionSet();
								Instruction rotal = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(rotal);
								return rotal;
							case "jmprind":
								opcodes = new InstructionSet();
								Instruction jmprind = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								jmprind.addToken(currentToken);
								Token registerToken42 = iter.next();
								if (registerToken42.getType() != TokenType.REGISTER) {
									reglog();
								} else {
									jmprind.addToken(registerToken42);
								}
								instructions.add(jmprind);
								return jmprind;
							case "jmpaddr":
								opcodes = new InstructionSet();
								Instruction jmpaddr = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								jmpaddr.addToken(currentToken);
								Token addressToken2 = iter.next();
								if (addressToken2.getType() != TokenType.NAME && addressToken2.getType() != TokenType.VALUE) {
									gui.log("SYNTAX ERROR IN LINE " + (gui.currentLine));
								} else {
									jmpaddr.addToken(addressToken2);
								}
								// TODO: check address type
								if (addressToken2.getType() == TokenType.NAME) {
									String address = this.getLabelDirection(addressToken2.getValue()) + "";
//									this.register.jmpaddr(address);
									gui.currentLine = this.getLabelLine(addressToken2.getValue());
									if (address.equals("-1")) {
										gui.log("NO ADDRESS ERROR IN LINE " + (gui.currentLine));
										return null;
									}
									addressToken2.setValue(address);
								}
								instructions.add(jmpaddr);
								return jmpaddr;
							case "jcondrin":
								opcodes = new InstructionSet();
								Instruction jcondrin = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								jcondrin.addToken(currentToken);
								Token registerToken43 = iter.next();
								if (registerToken43.getType() != TokenType.REGISTER) {
									reglog();
								} else {
									jcondrin.addToken(registerToken43);
								}
								instructions.add(jcondrin);
								return jcondrin;
							case "jcondaddr":
								opcodes = new InstructionSet();
								Instruction jcondaddr = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								jcondaddr.addToken(currentToken);
								Token addressToken3 = iter.next();
								if (addressToken3.getType() != TokenType.ADDRESS && addressToken3.getType() != TokenType.NAME && addressToken3.getType() != TokenType.VALUE) {
									gui.log("NO ADDRESS ERROR IN LINE " + (gui.currentLine + 1));
								} else {
									jcondaddr.addToken(addressToken3);
								}
								if (addressToken3.getType() == TokenType.NAME) {
									String address = this.getLabelDirection(addressToken3.getValue()) + "";
									if (address.equals("-1")) {
										gui.log("NO ADDRESS ERROR IN LINE " + (gui.currentLine + 1));
										return null;
									}
									addressToken3.setValue(address);
								}
								instructions.add(jcondaddr);
								return jcondaddr;
							case "loop":
								opcodes = new InstructionSet();
								Instruction loop = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								if (addressToken4.getType() == TokenType.NAME) {
									int direction = this.getValueDirection(addressToken4.getValue());
									loop.getTokens().get(2).setValue(direction + "");
								}
								instructions.add(loop);
								return loop;
							case "grt":
								opcodes = new InstructionSet();
								Instruction grt = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(grt);
								return grt;
							case "grteq":
								opcodes = new InstructionSet();
								Instruction grteq = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(grteq);
								return grteq;
							case "eq":
								opcodes = new InstructionSet();
								Instruction eq = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(eq);
								return eq;
							case "neq":
								opcodes = new InstructionSet();
								Instruction neq = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
								instructions.add(neq);
								return neq;
							case "nop":
								opcodes = new InstructionSet();
								Instruction nop = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								nop.addToken(currentToken);
								instructions.add(nop);
								return nop;
							case "call":
								opcodes = new InstructionSet();
								Instruction call = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								call.addToken(currentToken);
								Token addressToken5 = iter.next();
								if (addressToken5.getType() != TokenType.ADDRESS && addressToken5.getType() != TokenType.NAME && addressToken5.getType() != TokenType.VALUE) {
									addresslog();
								} else {
									call.addToken(addressToken5);
								}
								instructions.add(call);
								return call;
							case "return":
								opcodes = new InstructionSet();
								Instruction r = opcodes.getInstruction(currentToken.getValue().toLowerCase());
								r.addToken(currentToken);
								instructions.add(r);
								return r;
							default:
								break;
						}
					default:
						break;
				}
			}
			
		}
		return null;
	}
	
	public Instruction run(ArrayList<Token> tokens) {
		Iterator<Token> iter = tokens.iterator();
		while (iter.hasNext()) {
			Token currentToken = iter.next();
			System.out.println(tokens);
			switch (currentToken.getType()) {
				case NAME:
					gui.log("ERROR: Instruction not found or incorrect label format.");
					return null;
				case OPCODE:
					switch (currentToken.getValue().toLowerCase()) {
						case "load":
							opcodes = new InstructionSet();
							Instruction load = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							load.addToken(currentToken);
							Token registerToken = iter.next();
							if (registerToken.getType() != TokenType.REGISTER) {
								gui.log("SYNTAX ERROR IN LINE " + (gui.currentLine + 1));
								return null;
							} else {
								load.addToken(registerToken);
							}
							Token addressToken = iter.next();
							if (addressToken.getType() != TokenType.ADDRESS && addressToken.getType() != TokenType.NAME && addressToken.getType() != TokenType.VALUE) {
								gui.log("SYNTAX ERROR IN LINE " + (gui.currentLine + 1));
								return null;
							} else {
								load.addToken(addressToken);
							}
							int memDirection = -1;
							if (addressToken.getType() == TokenType.NAME) {
								memDirection = this.getValueDirection(addressToken.getValue());
							} else {
								memDirection = Integer.parseInt(addressToken.getValue());
							}
							// TODO: check what type is address to get the correct content for all cases
							String content = gui.memory.getMemoryDirection(memDirection).getContent();
							this.register.getregs().put(registerToken.getValue().toLowerCase(), content);
							this.gui.updateRegisterTable();
							if (gui.buffer.buffer.size() >= 2) {
								gui.memory.getMemoryDirection(memDirection).setContent(gui.buffer.dequeueBuffer() + gui.buffer.dequeueBuffer());
								gui.updateMemoryTable();
							}
							instructions.add(load);
							return load;
						case "loadim":
							opcodes = new InstructionSet();
							Instruction loadim = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							if (registerToken1.getValue().toLowerCase().contains("0")) {
								gui.log("REGISTER 0 CANNOT BE MODIFIED.");
								return null;
							}
							if (registerToken1.getValue().toLowerCase().contains("7")) {
								this.register.sp = constantToken.getValue().replaceAll("#", "");
							}
							this.register.getregs().put(registerToken1.getValue().toLowerCase(), constantToken.getValue().replaceAll("#", ""));
							this.gui.updateRegisterTable();
							instructions.add(loadim);
							return loadim;
						case "pop":
							opcodes = new InstructionSet();
							Instruction pop = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							pop.addToken(currentToken);
							Token registerToken2 = iter.next();
							if (registerToken2.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								pop.addToken(registerToken2);
							}
							// TODO: check if the register is r0 to throw error if it is
							this.register.getregs().put(registerToken2.getValue().toLowerCase(), gui.memory.getMemoryDirection(Integer.parseInt(this.register.sp, 16)).getContent());
							// TODO: increment SP by 1 (SP = SP + 1)
							// TODO: update SP in GUI
							this.gui.updateRegisterTable();
							instructions.add(pop);
							return pop;
						case "store":
							opcodes = new InstructionSet();
							Instruction store = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							store.addToken(currentToken);
							Token addressToken1 = iter.next();
							if (addressToken1.getType() != TokenType.ADDRESS && addressToken1.getType() != TokenType.NAME && addressToken1.getType() != TokenType.VALUE) {
								addresslog();
							} else {
								store.addToken(addressToken1);
							}
							Token registerToken3 = iter.next();
							if (registerToken3.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								store.addToken(registerToken3);
							}
							// TODO: check what type is address to get the correct content for all cases
							if (addressToken1.getType() == TokenType.NAME) {
								int direction = this.getValueDirection(addressToken1.getValue());
								store.getTokens().get(1).setValue(direction + "");
								gui.memory.getMemoryDirection(direction).setContent(this.register.getregs().get(registerToken3.getValue().toLowerCase()));
	 							gui.updateMemoryTable();
	 							instructions.add(store);
	 							return null;
	 							
							}
 							gui.memory.getMemoryDirection(Integer.parseInt(addressToken1.getValue())).setContent(this.register.getregs().get(registerToken3.getValue().toLowerCase()));
 							gui.updateMemoryTable();
 							instructions.add(store);
 							return store;
						case "push":
							opcodes = new InstructionSet();
							Instruction push = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							push.addToken(currentToken);
							Token registerToken4 = iter.next();
							if (registerToken4.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								push.addToken(registerToken4);
							}
							// TODO: decrement SP by 1 (SP = SP - 1)
							// TODO: update SP in GUI
							this.register.push(registerToken4.getValue().toLowerCase());
//							gui.memory.getMemoryDirection(Integer.parseInt(this.register.sp, 16)).setContent(this.register.getregs().get(registerToken4.getValue().toLowerCase()));
							gui.updateMemoryTable();
							gui.updateRegisterTable();
							instructions.add(push);
							return push;
						case "loadrind":
							opcodes = new InstructionSet();
							Instruction loadrind = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							int regAddress = Integer.parseInt(this.register.getregs().get(registerToken6.getValue().toLowerCase()), 16);
							this.register.getregs().put(registerToken5.getValue().toLowerCase(), gui.memory.getMemoryDirection(regAddress).getContent());
							gui.updateRegisterTable();
							instructions.add(loadrind);
							return loadrind;
						case "storerind":
							opcodes = new InstructionSet();
							Instruction storerind = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.getregs().put(registerToken8.getValue().toLowerCase(), gui.memory.getMemoryDirection(Integer.parseInt(this.register.getregs().get(registerToken7.getValue().toLowerCase()), 16)).getContent());
							gui.updateRegisterTable();
							instructions.add(storerind);
							return storerind;
						case "add":
							opcodes = new InstructionSet();
							Instruction add = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.add(registerToken9.getValue().toLowerCase(), registerToken10.getValue().toLowerCase(), registerToken11.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(add);
							return add;
						case "sub":
							opcodes = new InstructionSet();
							Instruction sub = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.sub(registerToken12.getValue().toLowerCase(), registerToken13.getValue().toLowerCase(), registerToken14.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(sub);
							return sub;
						case "addim":
							opcodes = new InstructionSet();
							Instruction addim = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.add(registerToken15.getValue().toLowerCase(), registerToken15.getValue().toLowerCase(), constantToken1.getValue());
							gui.updateRegisterTable();
							instructions.add(addim);
							return addim;
						case "subim":
							opcodes = new InstructionSet();
							Instruction subim = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.sub(registerToken16.getValue().toLowerCase(), registerToken16.getValue().toLowerCase(), constantToken2.getValue());
							gui.updateRegisterTable();
							instructions.add(subim);
							return subim;
						case "and":
							opcodes = new InstructionSet();
							Instruction and = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.and(registerToken17.getValue().toLowerCase(), registerToken18.getValue().toLowerCase(), registerToken19.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(and);
							return and;
						case "or":
							opcodes = new InstructionSet();
							Instruction or = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.or(registerToken20.getValue().toLowerCase(), registerToken21.getValue().toLowerCase(), registerToken22.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(or);
							return or;
						case "xor":
							opcodes = new InstructionSet();
							Instruction xor = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.xor(registerToken23.getValue().toLowerCase(), registerToken24.getValue().toLowerCase(), registerToken25.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(xor);
							return xor;
						case "not":
							opcodes = new InstructionSet();
							Instruction not = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.not(registerToken26.getValue().toLowerCase(), registerToken27.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(not);
							return not;
						case "neg":
							opcodes = new InstructionSet();
							Instruction neg = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.neg(registerToken28.getValue().toLowerCase(), registerToken29.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(neg);
							return neg;
						case "shiftr":
							opcodes = new InstructionSet();
							Instruction shiftr = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.shiftr(registerToken30.getValue().toLowerCase(), registerToken31.getValue().toLowerCase(), registerToken32.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(shiftr);
							return shiftr;
						case "shiftl":
							opcodes = new InstructionSet();
							Instruction shiftl = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.shiftl(registerToken33.getValue().toLowerCase(), registerToken34.getValue().toLowerCase(), registerToken35.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(shiftl);
							return shiftl;
						case "rotar":
							opcodes = new InstructionSet();
							Instruction rotar = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.rotar(registerToken36.getValue().toLowerCase(), registerToken37.getValue().toLowerCase(), registerToken38.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(rotar);
							return rotar;
						case "rotal":
							opcodes = new InstructionSet();
							Instruction rotal = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.rotal(registerToken39.getValue().toLowerCase(), registerToken40.getValue().toLowerCase(), registerToken41.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(rotal);
							return rotal;
						case "jmprind":
							opcodes = new InstructionSet();
							Instruction jmprind = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							jmprind.addToken(currentToken);
							Token registerToken42 = iter.next();
							if (registerToken42.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								jmprind.addToken(registerToken42);
							}
							this.register.jmprind(registerToken42.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(jmprind);
							return jmprind;
						case "jmpaddr":
							opcodes = new InstructionSet();
							Instruction jmpaddr = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							jmpaddr.addToken(currentToken);
							Token addressToken2 = iter.next();
							if (addressToken2.getType() != TokenType.NAME && addressToken2.getType() != TokenType.VALUE) {
								gui.log("SYNTAX ERROR IN LINE " + (gui.currentLine));
							} else {
								jmpaddr.addToken(addressToken2);
							}
							// TODO: check address type
							if (addressToken2.getType() == TokenType.NAME) {
								String address = this.getLabelDirection(addressToken2.getValue()) + "";
								this.register.jmpaddr(address);
								gui.currentLine = this.getLabelLine(addressToken2.getValue());
								if (address.equals("-1")) {
									gui.log("NO ADDRESS ERROR IN LINE " + (gui.currentLine));
									return null;
								}
								addressToken2.setValue(address);
							}
//							System.out.println(addressToken2.getValue() + " GUI CurrentLine: " + gui.currentLine);
							gui.updateRegisterTable();
							instructions.add(jmpaddr);
							return jmpaddr;
						case "jcondrin":
							opcodes = new InstructionSet();
							Instruction jcondrin = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							jcondrin.addToken(currentToken);
							Token registerToken43 = iter.next();
							if (registerToken43.getType() != TokenType.REGISTER) {
								reglog();
							} else {
								jcondrin.addToken(registerToken43);
							}
							this.register.jcondrin(this.register.cond, registerToken43.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(jcondrin);
							return jcondrin;
						case "jcondaddr":
							opcodes = new InstructionSet();
							Instruction jcondaddr = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							jcondaddr.addToken(currentToken);
							Token addressToken3 = iter.next();
							if (addressToken3.getType() != TokenType.ADDRESS && addressToken3.getType() != TokenType.NAME && addressToken3.getType() != TokenType.VALUE) {
								gui.log("NO ADDRESS ERROR IN LINE " + (gui.currentLine + 1));
							} else {
								jcondaddr.addToken(addressToken3);
							}
							// TODO: check address type
							if (!this.register.cond) {
								return null;
							}
							if (addressToken3.getType() == TokenType.NAME) {
								String address = this.getLabelDirection(addressToken3.getValue()) + "";
								this.register.jcondaddr(this.register.cond, address);
								gui.currentLine = this.getLabelLine(addressToken3.getValue());
								if (address.equals("-1")) {
									gui.log("NO ADDRESS ERROR IN LINE " + (gui.currentLine));
									return null;
								}
								addressToken3.setValue(address);
							}
							gui.updateRegisterTable();
							instructions.add(jcondaddr);
							return jcondaddr;
						case "loop":
							opcodes = new InstructionSet();
							Instruction loop = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							if (addressToken4.getType() == TokenType.NAME) {
								int direction = this.getValueDirection(addressToken4.getValue());
								loop.getTokens().get(2).setValue(direction + "");
								this.register.loop(registerToken44.getValue().toLowerCase(), direction+"");
							} else {
								this.register.loop(registerToken44.getValue().toLowerCase(), addressToken4.getValue());
							}
							gui.updateRegisterTable();
							instructions.add(loop);
							return loop;
						case "grt":
							opcodes = new InstructionSet();
							Instruction grt = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.cond = this.register.grt(registerToken45.getValue().toLowerCase(), registerToken46.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(grt);
							return grt;
						case "grteq":
							opcodes = new InstructionSet();
							Instruction grteq = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.cond = this.register.grteq(registerToken47.getValue().toLowerCase(), registerToken48.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(grteq);
							return grteq;
						case "eq":
							opcodes = new InstructionSet();
							Instruction eq = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.cond = this.register.eq(registerToken49.getValue().toLowerCase(), registerToken50.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(eq);
							return eq;
						case "neq":
							opcodes = new InstructionSet();
							Instruction neq = opcodes.getInstruction(currentToken.getValue().toLowerCase());
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
							this.register.cond = this.register.neq(registerToken51.getValue().toLowerCase(), registerToken52.getValue().toLowerCase());
							gui.updateRegisterTable();
							instructions.add(neq);
							return neq;
						case "nop":
							opcodes = new InstructionSet();
							Instruction nop = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							nop.addToken(currentToken);
							instructions.add(nop);
							return nop;
						case "call":
							opcodes = new InstructionSet();
							Instruction call = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							call.addToken(currentToken);
							Token addressToken5 = iter.next();
							if (addressToken5.getType() != TokenType.ADDRESS && addressToken5.getType() != TokenType.NAME && addressToken5.getType() != TokenType.VALUE) {
								addresslog();
							} else {
								call.addToken(addressToken5);
							}
							// TODO: check address type
							if (addressToken5.getType() == TokenType.NAME) {
								int valueDirection = this.getValueDirection(addressToken5.getValue());
								int labelDirection = this.getLabelDirection(addressToken5.getValue());
								if (valueDirection == -1 && labelDirection == -1) {
									gui.log("ADDRESS ERROR");
									return null;
								} else if (valueDirection != -1) {
									call.getTokens().get(1).setValue(valueDirection + "");
								} else if (labelDirection != -1) {
									call.getTokens().get(1).setValue(labelDirection + "");
								}
							}
							this.register.call(addressToken5.getValue());
							gui.updateMemoryTable();
							gui.updateRegisterTable();
							instructions.add(call);
							return call;
						case "return":
							opcodes = new InstructionSet();
							Instruction r = opcodes.getInstruction(currentToken.getValue().toLowerCase());
							r.addToken(currentToken);
							this.register.ret();
							gui.updateRegisterTable();
							instructions.add(r);
							return r;
						default:
							break;
					}
				default:
					this.register.pc = Integer.toHexString(Integer.parseInt(this.register.pc, 16) + 4);
					break;
			}
		}
		return null;
	}
	
	public String executeLine(Instruction instruction) {
		String result = "";
		if (instruction == null) return "";
		Instruction i = instruction;
		if(i.getTokens().size() == 4) {
 			result = instructionFormat.Fswitch(i.getTokens().get(0).getValue(), i.getTokens().get(1).getValue(), 
 					i.getTokens().get(2).getValue(), i.getTokens().get(3).getValue(), i.getFormat());
 		}
		if(i.getTokens().size() == 3 && i.getTokens().get(0).getValue().toLowerCase().equals("store")) {
 			result = instructionFormat.Fswitch(i.getTokens().get(0).getValue(), i.getTokens().get(2).getValue(), 
 					i.getTokens().get(1).getValue(), "", i.getFormat());
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
		gui.log("Line " + gui.currentLine + ": " + "Not a valid register!");
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