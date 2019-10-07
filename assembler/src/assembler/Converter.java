package assembler;

public class Converter {

	String inst = "";
	String hex = "0123456789ABCDEF";

	public Converter(String inst){
		this.inst = inst;
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
		int dec1 = 0;
		int dec2 = hex.indexOf(str.charAt(1));

		// If instruction is purely numerical, look up in hex
		// Else, first element of the element must be multiplied by 16. 
		if(Character.isDigit(str.charAt(0)) && Character.isDigit(str.charAt(1)))
			dec1 = hex.indexOf(str.charAt(0));
		else
			dec1 = hex.indexOf(str.charAt(0))*16;

		int resultNumber = dec1 + dec2;
		
		// If number has less than 4 bits, format to 4 bits
		if(resultNumber < 8)
			return String.format("%04d",(Integer.parseInt(Integer.toBinaryString(resultNumber))));

		return Integer.toBinaryString(resultNumber);
	}
}
