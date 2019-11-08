package assembler;

import java.awt.*;
import java.util.Scanner;

import javax.swing.*;


public class TrafficLight {
	//Test Intruction
	static String  inst = "11000011";
	/*********************************************************************************************
	 * 								Main
	 * **************************************************************************************
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		JFrame frame = initializeFrame();
		Light l1 = new Light();

		frame.add(l1);

		if (l1.getLastTwoBin(inst).equals("00")) {
			l1.lightUp(inst);
			l1.intermitent(false);
		} else if (l1.getLastTwoBin(inst).equals("11")) {
			l1.lightUp(inst);
			l1.intermitent(true);
		}
	}
	/*********************************************************************************************
	 * 									Frame
	 * ******************************************************************************************
	 * @return
	 */
	private static JFrame initializeFrame() {
		JFrame frame = new JFrame();
		frame.setTitle("Traffic Light");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}
	/*********************************************************************************************
	 * 										Traffic Light
	 * *******************************************************************************************
	 *
	 */
	static class Light extends JPanel {

		private static final long serialVersionUID = 1L;

		private int iDistance=90;
		private int topLightHeight=10;
		//First Traffic Light
		private Color iRed = Color.black;
		private Color iYellow = Color.black;
		private Color iGreen = Color.black;
		//Second Traffic Light
		private Color iRed2 = Color.black;
		private Color iYellow2 = Color.black;
		private Color iGreen2 = Color.black;

		Light() {}


		public void paintComponent(Graphics g) {
			defineLight(g);

		}


		public void setLighDistance(int distance) {

			this.iDistance = distance;
		}

		public void lightUp (String inst) throws InterruptedException {
			//System.out.println(inst);
			for (int i = 0; i < inst.length()-2; i++) {
				int binary = Integer.parseInt(inst.charAt(i) + "");
				if (binary == 1) {
					this.turnOnLight(i);
				}
			}
			revalidate();
		}
		//Gets LastTwoBin from Intruction
		public String getLastTwoBin(String inst){
			return inst.substring(6,8);
		}
		//Sets all Traffic Lights to Black
		public void reset(){
			this.iRed = Color.black;
			this.iYellow = Color.black;
			this.iGreen = Color.black;
			this.iRed2 = Color.black;
			this.iYellow2 = Color.black;
			this.iGreen2 = Color.black;
		}
		//Traffic Light Structure
		public void defineLight(Graphics g) {

			g.setColor(this.iRed);
			g.fillRoundRect(80, this.topLightHeight, 80, 80, 70, 70);


			g.setColor(this.iYellow);
			g.fillRoundRect(80, this.topLightHeight+this.iDistance, 80, 80, 70, 70);


			g.setColor(this.iGreen);
			g.fillRoundRect(80, this.topLightHeight+2*this.iDistance, 80, 80, 70, 70);

			//Second Traffic Light
			g.setColor(this.iRed2);
			g.fillRoundRect(200, this.topLightHeight, 80, 80, 70, 70);

			g.setColor(this.iYellow2);
			g.fillRoundRect(200, this.topLightHeight+this.iDistance, 80, 80, 70, 70);

			g.setColor(this.iGreen2);
			g.fillRoundRect(200, this.topLightHeight+2*this.iDistance, 80, 80, 70, 70);
		}
		//Switch Case to decide which light turns on
		public void turnOnLight(int light){
//			System.out.println("Turn on light #" + light);
			switch(light){
			case 0:
				this.iRed = Color.red;
				break;
			case 1:
				this.iYellow = Color.yellow;
				break;
			case 2:
				this.iGreen = Color.green;
				break;
			case 3: 
				this.iRed2 = Color.red;
				break;
			case 4:
				this.iYellow2 = Color.yellow;
				break;
			case 5:
				this.iGreen2 = Color.green;
				break;
				default:
				this.iRed = Color.black;
				this.iYellow = Color.black;
				this.iGreen = Color.black;
				this.iRed2 = Color.black;
				this.iYellow2 = Color.black;
				this.iGreen2 = Color.black;
			}	
		}
		//Method puts lights in intermittent state
		public void intermitent(boolean a) throws InterruptedException{
			while(a){
				//				repaint();
				Thread.sleep(500);
				reset();
				repaint();
				revalidate();
				Thread.sleep(500);
				lightUp(inst);
				repaint();
				revalidate();
			}
		}

	}
}