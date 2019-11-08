package assembler;
import java.util.ArrayList;
import java.util.List;

public class IO3 {
	
	public static char[] result = new char [8];
	public static ArrayList<String> ref = new ArrayList<>();
    
	
	public void setList(ArrayList<String> ref) {
	       this.ref = ref;
	    }

	    public ArrayList<String> getList() {
	    	ref.add("01000011");
			ref.add("01011111");
			ref.add("01110110");
			ref.add("01000100");
			ref.add("01110101");
			ref.add("01100011");
			ref.add("01110111");
			ref.add("01100000");
	        return this.ref;
	    }
	    
	public static void main(String[] args) {
	
		result = AsciConversion(ref,result);
//		System.out.println("ASCII: " + result);

	}
	
	public static char[] AsciConversion(ArrayList<String> data , char[] data2) {
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

