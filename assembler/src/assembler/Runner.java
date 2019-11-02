package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Runner {
	
	// This will manage the runs through tokens we need to do to store constants and values and write the object code
	private InstructionSet opcodes = new InstructionSet();
	private InstructionFormat instructionFormat = new InstructionFormat();
	private Register registers = new Register();
	private Converter converter = new Converter();
	private ArrayList<Token> tokens;
	
	public Map<String, ArrayList<String>> values = new HashMap<>();
	public Map<String, String> constants = new HashMap<>();
	public ArrayList<Instruction> instructions = new ArrayList<>();
	public int currentInstruction = 0;
	
	public int getNumOfInstructions() {
		return this.instructions.size();
	}

	public Runner(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
	
	public ArrayList<Instruction> run() {
		// store constants and values
		Iterator<Token> iter = tokens.iterator();
		while (iter.hasNext()) {
			Token currentToken = iter.next();
			switch (currentToken.getType()) {
				case OPCODE:
					switch (currentToken.getValue()) {
						case "load":
							Instruction load = opcodes.getInstruction(currentToken.getValue());
							load.addToken(currentToken);
							Token registerToken = iter.next();
							if (registerToken.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								load.addToken(registerToken);
							}
							Token addressToken = iter.next();
							if (addressToken.getType() != TokenType.ADDRESS && addressToken.getType() != TokenType.NAME && addressToken.getType() != TokenType.VALUE) {
//								return Error
							} else {
								load.addToken(addressToken);
							}
							this.instructions.add(load);
							break;
						case "loadim":
							Instruction loadim = opcodes.getInstruction(currentToken.getValue());
							loadim.addToken(currentToken);
							Token registerToken1 = iter.next();
							if (registerToken1.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								loadim.addToken(registerToken1);
							}
							Token constantToken = iter.next();
							if (constantToken.getType() != TokenType.CONST && constantToken.getType() != TokenType.HEX) {
//								return Error
							} else {
								loadim.addToken(constantToken);
							}
							this.instructions.add(loadim);
							break;
						case "pop":
							Instruction pop = opcodes.getInstruction(currentToken.getValue());
							pop.addToken(currentToken);
							Token registerToken2 = iter.next();
							if (registerToken2.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								pop.addToken(registerToken2);
							}
							this.instructions.add(pop);
							break;
						case "store":
							Instruction store = opcodes.getInstruction(currentToken.getValue());
							store.addToken(currentToken);
							Token registerToken3 = iter.next();
							if (registerToken3.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								store.addToken(registerToken3);
							}
							Token addressToken1 = iter.next();
							if (addressToken1.getType() != TokenType.ADDRESS && addressToken1.getType() != TokenType.NAME && addressToken1.getType() != TokenType.VALUE) {
//								return Error
							} else {
								store.addToken(addressToken1);
							}
							this.instructions.add(store);
							break;
						case "push":
							Instruction push = opcodes.getInstruction(currentToken.getValue());
							push.addToken(currentToken);
							Token registerToken4 = iter.next();
							if (registerToken4.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								push.addToken(registerToken4);
							}
							this.instructions.add(push);
							break;
						case "loadrind":
							Instruction loadrind = opcodes.getInstruction(currentToken.getValue());
							loadrind.addToken(currentToken);
							Token registerToken5 = iter.next();
							if (registerToken5.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								loadrind.addToken(registerToken5);
							}
							Token registerToken6 = iter.next();
							if (registerToken6.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								loadrind.addToken(registerToken6);
							}
							this.instructions.add(loadrind);
							break;
						case "storerind":
							Instruction storerind = opcodes.getInstruction(currentToken.getValue());
							storerind.addToken(currentToken);
							Token registerToken7 = iter.next();
							if (registerToken7.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								storerind.addToken(registerToken7);
							}
							Token registerToken8 = iter.next();
							if (registerToken8.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								storerind.addToken(registerToken8);
							}
							this.instructions.add(storerind);
							break;
						case "add":
							Instruction add = opcodes.getInstruction(currentToken.getValue());
							add.addToken(currentToken);
							Token registerToken9 = iter.next();
							if (registerToken9.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								add.addToken(registerToken9);
							}
							Token registerToken10 = iter.next();
							if (registerToken10.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								add.addToken(registerToken10);
							}
							Token registerToken11 = iter.next();
							if (registerToken11.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								add.addToken(registerToken11);
							}
							this.instructions.add(add);
							break;
						case "sub":
							Instruction sub = opcodes.getInstruction(currentToken.getValue());
							sub.addToken(currentToken);
							Token registerToken12 = iter.next();
							if (registerToken12.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								sub.addToken(registerToken12);
							}
							Token registerToken13 = iter.next();
							if (registerToken13.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								sub.addToken(registerToken13);
							}
							Token registerToken14 = iter.next();
							if (registerToken14.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								sub.addToken(registerToken14);
							}
							this.instructions.add(sub);
							break;
						case "addim":
							Instruction addim = opcodes.getInstruction(currentToken.getValue());
							addim.addToken(currentToken);
							Token registerToken15 = iter.next();
							if (registerToken15.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								addim.addToken(registerToken15);
							}
							Token constantToken1 = iter.next();
							if (constantToken1.getType() != TokenType.CONST && constantToken1.getType() != TokenType.HEX) {
//								return Error
							} else {
								addim.addToken(constantToken1);
							}
							this.instructions.add(addim);
							break;
						case "subim":
							Instruction subim = opcodes.getInstruction(currentToken.getValue());
							subim.addToken(currentToken);
							Token registerToken16 = iter.next();
							if (registerToken16.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								subim.addToken(registerToken16);
							}
							Token constantToken2 = iter.next();
							if (constantToken2.getType() != TokenType.CONST && constantToken2.getType() != TokenType.HEX) {
//								return Error
							} else {
								subim.addToken(constantToken2);
							}
							this.instructions.add(subim);
							break;
						case "and":
							Instruction and = opcodes.getInstruction(currentToken.getValue());
							and.addToken(currentToken);
							Token registerToken17 = iter.next();
							if (registerToken17.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								and.addToken(registerToken17);
							}
							Token registerToken18 = iter.next();
							if (registerToken18.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								and.addToken(registerToken18);
							}
							Token registerToken19 = iter.next();
							if (registerToken19.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								and.addToken(registerToken19);
							}
							this.instructions.add(and);
							break;
						case "or":
							Instruction or = opcodes.getInstruction(currentToken.getValue());
							or.addToken(currentToken);
							Token registerToken20 = iter.next();
							if (registerToken20.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								or.addToken(registerToken20);
							}
							Token registerToken21 = iter.next();
							if (registerToken21.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								or.addToken(registerToken21);
							}
							Token registerToken22 = iter.next();
							if (registerToken22.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								or.addToken(registerToken22);
							}
							this.instructions.add(or);
							break;
						case "xor":
							Instruction xor = opcodes.getInstruction(currentToken.getValue());
							xor.addToken(currentToken);
							Token registerToken23 = iter.next();
							if (registerToken23.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								xor.addToken(registerToken23);
							}
							Token registerToken24 = iter.next();
							if (registerToken24.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								xor.addToken(registerToken24);
							}
							Token registerToken25 = iter.next();
							if (registerToken25.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								xor.addToken(registerToken25);
							}
							this.instructions.add(xor);
							break;
						case "not":
							Instruction not = opcodes.getInstruction(currentToken.getValue());
							not.addToken(currentToken);
							Token registerToken26 = iter.next();
							if (registerToken26.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								not.addToken(registerToken26);
							}
							Token registerToken27 = iter.next();
							if (registerToken27.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								not.addToken(registerToken27);
							}
							this.instructions.add(not);
							break;
						case "neg":
							Instruction neg = opcodes.getInstruction(currentToken.getValue());
							neg.addToken(currentToken);
							Token registerToken28 = iter.next();
							if (registerToken28.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								neg.addToken(registerToken28);
							}
							Token registerToken29 = iter.next();
							if (registerToken29.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								neg.addToken(registerToken29);
							}
							this.instructions.add(neg);
							break;
						case "shiftr":
							Instruction shiftr = opcodes.getInstruction(currentToken.getValue());
							shiftr.addToken(currentToken);
							Token registerToken30 = iter.next();
							if (registerToken30.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								shiftr.addToken(registerToken30);
							}
							Token registerToken31 = iter.next();
							if (registerToken31.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								shiftr.addToken(registerToken31);
							}
							Token registerToken32 = iter.next();
							if (registerToken32.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								shiftr.addToken(registerToken32);
							}
							this.instructions.add(shiftr);
							break;
						case "shiftl":
							Instruction shiftl = opcodes.getInstruction(currentToken.getValue());
							shiftl.addToken(currentToken);
							Token registerToken33 = iter.next();
							if (registerToken33.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								shiftl.addToken(registerToken33);
							}
							Token registerToken34 = iter.next();
							if (registerToken34.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								shiftl.addToken(registerToken34);
							}
							Token registerToken35 = iter.next();
							if (registerToken35.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								shiftl.addToken(registerToken35);
							}
							this.instructions.add(shiftl);
							break;
						case "rotar":
							Instruction rotar = opcodes.getInstruction(currentToken.getValue());
							rotar.addToken(currentToken);
							Token registerToken36 = iter.next();
							if (registerToken36.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								rotar.addToken(registerToken36);
							}
							Token registerToken37 = iter.next();
							if (registerToken37.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								rotar.addToken(registerToken37);
							}
							Token registerToken38 = iter.next();
							if (registerToken38.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								rotar.addToken(registerToken38);
							}
							this.instructions.add(rotar);
							break;
						case "rotal":
							Instruction rotal = opcodes.getInstruction(currentToken.getValue());
							rotal.addToken(currentToken);
							Token registerToken39 = iter.next();
							if (registerToken39.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								rotal.addToken(registerToken39);
							}
							Token registerToken40 = iter.next();
							if (registerToken40.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								rotal.addToken(registerToken40);
							}
							Token registerToken41 = iter.next();
							if (registerToken41.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								rotal.addToken(registerToken41);
							}
							this.instructions.add(rotal);
							break;
						case "jmprind":
							Instruction jmprind = opcodes.getInstruction(currentToken.getValue());
							jmprind.addToken(currentToken);
							Token registerToken42 = iter.next();
							if (registerToken42.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								jmprind.addToken(registerToken42);
							}
							this.instructions.add(jmprind);
							break;
						case "jmpaddr":
							Instruction jmpaddr = opcodes.getInstruction(currentToken.getValue());
							jmpaddr.addToken(currentToken);
							Token addressToken2 = iter.next();
							if (addressToken2.getType() != TokenType.ADDRESS && addressToken2.getType() != TokenType.NAME && addressToken2.getType() != TokenType.VALUE) {
//								return Error
							} else {
								jmpaddr.addToken(addressToken2);
							}
							this.instructions.add(jmpaddr);
							break;
						case "jcondrin":
							Instruction jcondrin = opcodes.getInstruction(currentToken.getValue());
							jcondrin.addToken(currentToken);
							Token registerToken43 = iter.next();
							if (registerToken43.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								jcondrin.addToken(registerToken43);
							}
							this.instructions.add(jcondrin);
							break;
						case "jcondaddr":
							Instruction jcondaddr = opcodes.getInstruction(currentToken.getValue());
							jcondaddr.addToken(currentToken);
							Token addressToken3 = iter.next();
							if (addressToken3.getType() != TokenType.ADDRESS && addressToken3.getType() != TokenType.NAME && addressToken3.getType() != TokenType.VALUE) {
//								return Error
							} else {
								jcondaddr.addToken(addressToken3);
							}
							this.instructions.add(jcondaddr);
							break;
						case "loop":
							Instruction loop = opcodes.getInstruction(currentToken.getValue());
							loop.addToken(currentToken);
							Token registerToken44 = iter.next();
							if (registerToken44.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								loop.addToken(registerToken44);
							}
							Token addressToken4 = iter.next();
							if (addressToken4.getType() != TokenType.ADDRESS && addressToken4.getType() != TokenType.NAME && addressToken4.getType() != TokenType.VALUE) {
//								return Error
							} else {
								loop.addToken(addressToken4);
							}
							this.instructions.add(loop);
							break;
						case "grt":
							Instruction grt = opcodes.getInstruction(currentToken.getValue());
							grt.addToken(currentToken);
							Token registerToken45 = iter.next();
							if (registerToken45.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								grt.addToken(registerToken45);
							}
							Token registerToken46 = iter.next();
							if (registerToken46.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								grt.addToken(registerToken46);
							}
							this.instructions.add(grt);
							break;
						case "grteq":
							Instruction grteq = opcodes.getInstruction(currentToken.getValue());
							grteq.addToken(currentToken);
							Token registerToken47 = iter.next();
							if (registerToken47.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								grteq.addToken(registerToken47);
							}
							Token registerToken48 = iter.next();
							if (registerToken48.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								grteq.addToken(registerToken48);
							}
							this.instructions.add(grteq);
							break;
						case "eq":
							Instruction eq = opcodes.getInstruction(currentToken.getValue());
							eq.addToken(currentToken);
							Token registerToken49 = iter.next();
							if (registerToken49.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								eq.addToken(registerToken49);
							}
							Token registerToken50 = iter.next();
							if (registerToken50.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								eq.addToken(registerToken50);
							}
							this.instructions.add(eq);
							break;
						case "neq":
							Instruction neq = opcodes.getInstruction(currentToken.getValue());
							neq.addToken(currentToken);
							Token registerToken51 = iter.next();
							if (registerToken51.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								neq.addToken(registerToken51);
							}
							Token registerToken52 = iter.next();
							if (registerToken52.getType() != TokenType.REGISTER) {
//								return Error
							} else {
								neq.addToken(registerToken52);
							}
							this.instructions.add(neq);
							break;
						case "nop":
							Instruction nop = opcodes.getInstruction(currentToken.getValue());
							nop.addToken(currentToken);
							this.instructions.add(nop);
							break;
						case "call":
							Instruction call = opcodes.getInstruction(currentToken.getValue());
							call.addToken(currentToken);
							Token addressToken5 = iter.next();
							if (addressToken5.getType() != TokenType.ADDRESS && addressToken5.getType() != TokenType.NAME && addressToken5.getType() != TokenType.VALUE) {
//								return Error
							} else {
								call.addToken(addressToken5);
							}
							this.instructions.add(call);
							break;
						case "return":
							Instruction r = opcodes.getInstruction(currentToken.getValue());
							r.addToken(currentToken);
							this.instructions.add(r);
							break;
						default:
//							return Error
							break;
					}
//				case CONST:
//					Token nameToken = iter.next();
//					if (nameToken.type != TokenType.NAME) {
////						return "Error";
//					}
//					Token valueToken = iter.next();
//					if (valueToken.type != TokenType.VALUE) {
////						return "Error";
//					}
//					constants.put(nameToken.value, valueToken.value);
//				case NAME:
					// store values and constants here
				default:
//					return Error
					break;
			}
		}
		return this.instructions;
	}
	
//	public String runnner() {
//		this.run();
//		// generate object code per instruction
//		String objectCode = "";
//		int tokenIndex = 0;
//		for (Token t : tokens) {
//			if (t.type == TokenType.OPCODE) {
//				Instruction instruction = opcodes.getInstruction(t.value);
//				String binaryCode = instruction.getOpcode();
//				for (int i = 0; i < instruction.numOfNextTokens; i++) {
//					tokenIndex++;
//					Token nextToken = tokens.get(tokenIndex);
//					TokenType nextTokenType = nextToken.type;
//					if (nextTokenType != TokenType.NAME || nextTokenType != TokenType.HEX || nextTokenType != TokenType.REGISTER) {
//						return "Error";
//					}
//					// create the binarycode depending on the opcode
////					binaryCode += ;
//				}
//				objectCode += converter.binToHex(binaryCode) + "\n";
//			}
//			tokenIndex++;
//		}
//		return objectCode;
//	}
	
	public String executePrev() {
		String result = "";
		if (currentInstruction == 1) {
			return "WARNING: No previous instruction.\n";
		} else {
			currentInstruction--;
			Instruction i = instructions.get(currentInstruction);
			if(i.getTokens().size() == 4) {
	 			result = instructionFormat.Fswitch(i.getTokens().get(0).getValue(), i.getTokens().get(1).getValue(), 
	 					i.getTokens().get(2).getValue(), i.getTokens().get(3).getValue(), 1);
	 		}
	 		else if(i.getTokens().size() == 3) {
	 			result = instructionFormat.Fswitch(i.getTokens().get(0).getValue(), i.getTokens().get(1).getValue(), 
	 					i.getTokens().get(2).getValue(), "", 2);
	 		}
	 		else if(i.getTokens().size() <= 2) {
	 			result = instructionFormat.Fswitch(i.getTokens().get(0).getValue(), i.getTokens().get(1).getValue(), 
	 					"", "", 3);
	 		}
		}
		return result;
	}
	
	public String executeAll() {
		String result = "";
		
		return result;
	}
	
	public String executeNext() {
		String result = "";
		if (this.currentInstruction == instructions.size()) {
			return "WARNING: No next instruction.\n";
		} else {
			Instruction i = instructions.get(this.currentInstruction);
			System.out.println(i.toString() + "\n" + "Num Of Tokens: " + i.getTokens().size());
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
			this.currentInstruction++;
		}
		return result;
	}
}
