package assembler;

import java.util.HashMap;

public class Main {
	
	
	public static void main(String[] args) {
	   
	    GUI gui = new GUI();
	    gui.buildGUI();
    
	    Register reg = new Register();
		HashMap<String,String> regs = reg.getregs();
	
		for (String name: regs.keySet()) {
	        String key = name.toString();
	        String value = regs.get(name).toString();  
//	        System.out.println(key + " " + value);  
		} 
	    
//	    Converter c = new Converter("07");
//	    Converter c2 = new Converter("1A");
//	    Converter c3 = new Converter("1111111111111111");
//	    
//	    // System.out.println(c.binToHex(c.inst));
//	    System.out.println(c.hextoBin(c.inst));
//	    System.out.println(c2.hextoBin(c2.inst));
//	    System.out.println(c3.binToHex(c3.inst));
	}
}