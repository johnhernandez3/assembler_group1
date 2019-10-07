package assembler;

public class Main {
	
	
	public static void main(String[] args) {
	   
//	    GUI gui = new GUI();
//	    gui.buildGUI();
	    
	    Converter c = new Converter("07");
	    // System.out.println(c.binToHex(c.inst));
	    System.out.println(c.hextoBin(c.inst));
	    
	    
	    
	}
}
