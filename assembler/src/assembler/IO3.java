package assembler;
import java.util.ArrayList;
import java.util.List;

public class IO3 {
	
	public static char[] result = new char [8];
	public static List<String> ref = new ArrayList<>();
    
	

	public static void main(String[] args) {
		ref.add("01010011");
		ref.add("01101111");
		ref.add("01100110");
		ref.add("01110100");
		ref.add("01110111");
		ref.add("01100001");
		ref.add("01110010");
		ref.add("00100100");
		result = AsciConversion(ref,result);
		System.out.println(result);

	}
	
	public static char[] AsciConversion(List<String> data , char[] data2) {
		int bintoDec;
		String bin;
		for(int i = 0; i < data.size(); i++) {
			bin = data.get(i);
			bintoDec =  Integer.parseInt(bin,2);
			data2[i] = (char)bintoDec;
			
			
		}
		return data2;
	}
}
