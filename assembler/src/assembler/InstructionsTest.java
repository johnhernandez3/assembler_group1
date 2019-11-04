package assembler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InstructionsTest {
	
	@Test
	void testLoad() {
		String sourceCode = "load r1, 0a";
		String objectCode = "010a";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testLoadim() {
		String sourceCode = "loadim r2, #15";
		String objectCode = "0a15";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testPop() {
		String sourceCode = "pop r3";
		String objectCode = "1300";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testStore() {
		String sourceCode = "store r4, 12";
		String objectCode = "1c12";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testPush() {
		String sourceCode = "push r5";
		String objectCode = "2500";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testLoadrind() {
		String sourceCode = "loadrind r6, r7";
		String objectCode = "2ee0";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testStorerind() {
		String sourceCode = "storerind r6, r7";
		String objectCode = "36e0";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testAdd() {
		String sourceCode = "add r1, r2, r3";
		String objectCode = "394c";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testSub() {
		String sourceCode = "sub r1, r2, r3";
		String objectCode = "414c";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testAddim() {
		String sourceCode = "addim r2, #15";
		String objectCode = "4a15";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testSubim() {
		String sourceCode = "subim r3, #15";
		String objectCode = "5315";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testAnd() {
		String sourceCode = "and r2, r3, r4";
		String objectCode = "5a70";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testOr() {
		String sourceCode = "or r4, r5, r6";
		String objectCode = "64b8";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testXor() {
		String sourceCode = "xor r1, r2, r3";
		String objectCode = "694c";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testNot() {
		String sourceCode = "not r5, r6";
		String objectCode = "75c0";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testNeg() {
		String sourceCode = "neg r2, r3";
		String objectCode = "7a60";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testShiftr() {
		String sourceCode = "shiftr r5, r3, r1";
		String objectCode = "8564";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testShiftl() {
		String sourceCode = "shiftl r4, r2, r3";
		String objectCode = "8c4c";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testRotar() {
		String sourceCode = "rotar r5, r3, r6";
		String objectCode = "9578";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testRotal() {
		String sourceCode = "rotal r6, r3, r5";
		String objectCode = "9e74";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testJmprind() {
		String sourceCode = "jmprind r3";
		String objectCode = "a300";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testJmpaddr() {
		String sourceCode = "jmpaddr 97";
		String objectCode = "a897";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testJcondrin() {
		String sourceCode = "jcondrin r5";
		String objectCode = "b500";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testJcondaddr() {
		String sourceCode = "jcondaddr 76";
		String objectCode = "b876";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testLoop() {
		String sourceCode = "loop r4, 65";
		String objectCode = "c465";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testGrt() {
		String sourceCode = "grt r1, r2";
		String objectCode = "c940";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testGrteq() {
		String sourceCode = "grteq r3, r6";
		String objectCode = "d3c0";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testEq() {
		String sourceCode = "eq r1, r4";
		String objectCode = "d980";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testNeq() {
		String sourceCode = "neq r5, r6";
		String objectCode = "e5c0";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testNop() {
		String sourceCode = "nop";
		String objectCode = "e800";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testCall() {
		String sourceCode = "call af";
		String objectCode = "f0af";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}
	
	@Test
	void testReturn() {
		String sourceCode = "return";
		String objectCode = "f800";
		Parser p = new Parser(sourceCode);
		Runner runner = new Runner();
		assertEquals(runner.executeLine(runner.run(p.parseLine(0))), objectCode);
	}


}
