package assembler;

import java.util.ArrayList;

/*************************************
 * 			Buffer
 *************************************/

public class Buffer {
	Converter conv = new Converter();
	private int front, rear , capacity;
	public ArrayList<String> buffer;
	
	public Buffer () {
		capacity = 4;
		front = rear = 0;
		buffer = new ArrayList<>();
		
	}
	
	public void enqueueBuffer(String memLoc){
		if(capacity == buffer.size()){
			System.out.println("Buffer is Full");
			return;
		}
		else{
			buffer.add(conv.hexToBin2(memLoc));
			System.out.println("Added to buffer: " + memLoc);
//			rear++;
		}
		return;
	}
	
	public String dequeueBuffer(){
//		if(front == rear){
//			System.out.println("Buffer is Empty");
//			return;
//		}
//		else{
//			for (int i = 0; i < rear -1; i++) {
//				buffer[i] = buffer[i+1];
//			}
//			if (rear < capacity) {
//				buffer[rear] = null;
//			}
//			rear--;
//		}
		String result = "";
		result = buffer.remove(0);
		String newResult = '1' + result.substring(1, 8);
//		System.out.println("dequeued: " + result);
		return newResult;
	}
	
	public void displayBuffer(){
		int i ;
		if (front == rear) {
			System.out.println("Buffer is Empty");
			return;
		}
		for (i = front; i < rear; i++) {
			System.out.println(buffer.get(i));
		}
	}
	
}
