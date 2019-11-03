package assembler;

public class Converter {

	String inst = "";
	String hex = "0123456789abcdef";
	
	public Converter() {
	}
	
	public String binToHex(String str){
		//Divide instruction into 4 bits & convert from Binary to Decimal
		  int dec1 = Integer.parseInt(str.substring(0,4),2);
		  int dec2 = Integer.parseInt(str.substring(4,8),2);
		  int dec3 = Integer.parseInt(str.substring(8,12),2);
		  int dec4 = Integer.parseInt(str.substring(12,16),2);
		//Convert from Decimal to Hex  
		 String hex1 = Integer.toHexString(dec1);
		 String hex2 = Integer.toHexString(dec2);
		 String hex3 = Integer.toHexString(dec3);
		 String hex4 = Integer.toHexString(dec4);
		 
		 return hex1 + hex2 + hex3 + hex4;
	}
	
	public String hextoBin(String str){
		//Declare String binary numbers
//		String bin1= "";
//		String bin2= "";
		
		String result = "";
//		System.out.println("str: " + str);
		for(int i = 0; i < str.length(); i++) {
			int h = hex.indexOf(str.charAt(i));
			if (h < 10) {
				String binary09 = ("0000" + Integer.toBinaryString(h)).substring(Integer.toBinaryString(h).length());
//				System.out.println("binary09: " + binary09 + "hex: " + h);
				result += binary09;
			} else {
				String binaryAF = Integer.toBinaryString(h);
//				System.out.println("binaryAF: " + binaryAF);
				result += binaryAF;
			}
		}
		
		
//		if(str.length() == 2) {
//			dec2 = hex.indexOf(str.charAt(1));
//		}
//		//Return Decimal number of Hex Number based on index
//		int dec1 = hex.indexOf(str.charAt(0));
//		dec2 = hex.indexOf(str.charAt(1));
//		
//		//If binary number has less than 4 digits it returns new binary number with 4 digit format
//		if (dec1 < 4) {
//		 bin1 = String.format("%04d", dec1);
//		}
//		else if(dec2 < 4){
//	 	bin2 = String.format("%04d", dec2);
//		}
		
		// str = 0a return 00001010
		
		return result;
//		String s = "";
//		for(int i = 0; i < str.length(); i++) {
//			int dec1 = hex.indexOf(str.charAt(i));
//			s += String.format("04d", dec1);
//		}
//		return s;
		}
}
