package assembler;

import java.util.Scanner;

public class Main {
	
	public static boolean incorrectAnswer;
	public static Scanner scanner;
	public static String input;
	
	public static void main(String[] args) {
	   
	    GUI gui = new GUI();
	    gui.buildGUI();
	    
//	    scanner = new Scanner(System.in);
//		
//		incorrectAnswer = true;
//		
//		while (incorrectAnswer) {
//			
//			System.out.println ("Please enter sourceCode, objectCode: e.j. load r1, 0a::010a. Type 'stop' to stop testing.");
//	        input = scanner.nextLine().trim();
//	        String[] inputs = input.split("::");
//	        
//	        String sourceCode = inputs[0];
//			String objectCode = inputs[1];
//			
//			Parser p = new Parser(sourceCode);
//			Runner runner = new Runner();
//			System.out.println("\nSource Code: " + sourceCode);
//			System.out.println("Object Code: " + objectCode);
//            System.out.println("Generated Object Code: " + runner.executeLine(runner.run(p.parseLine(0))));
//            if (input.equals("stop")) {
//            	System.out.println("Stopped.");
//            	incorrectAnswer = false;
//                scanner.close();
//            }
//	            
//        }
	}
}