package assembler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class TrafficLight extends JPanel {
//	private static JFrame f = new JFrame("Traffic Light Display");
//	public Light l1 = new Light();
	//Test Intruction
	static String  inst = "11000011";
	private static final long serialVersionUID = 1L;
	private final JFrame f = new JFrame("Traffic Light IO");
	private final JPanel light = new JPanel();
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
	/*********************************************************************************************
	 * 								Main
	 * **************************************************************************************
	 * @param args
	 * @throws InterruptedException
	 */
//	public static void main(String[] args) throws InterruptedException {

//		JFrame frame = initializeFrame();
		//	Light l1 = new Light();

//		frame.add(l1);
//
//		if (l1.getLastTwoBin(inst).equals("00")) {
//			l1.lightUp(inst);
//			l1.intermitent(false,inst);
//		} else if (l1.getLastTwoBin(inst).equals("11")) {
//			l1.lightUp(inst);
//			l1.intermitent(true,inst);
//		}
//	}
	/*********************************************************************************************
	 * 									Frame
	 * ******************************************************************************************
	 * @return
	 */
//	private static JFrame initializeFrame() {
//		JFrame frame = new JFrame();
//		frame.setTitle("Traffic Light");
//		frame.setSize(400, 400);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//		return frame;
//	}


	/*********************************************************************************************
	 * 										Traffic Light
	 * *******************************************************************************************
	 *
	 */
	public TrafficLight() {
		f.add(this);
	}

		public void launch() {
//			JFrame f = new JFrame();
//			f.setTitle("Traffic Light");
//			f.setSize(400, 400);
//			f.add(this);
//			f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//			f.setVisible(true);
			System.out.println(" I am the correct frame Luis");	
			f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			f.pack();
			f.setResizable(false);
			f.setLocationRelativeTo(null);
			f.setVisible(true);
//			f.add(l);
//			f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//			//f.pack();
			f.setSize(350,300);
//			f.setResizable(false);
//			f.setLocationRelativeTo(null);
//			f.setVisible(true);
		}

		@Override
		public void paintComponent(Graphics g) {
			defineLight(g);
		}


		public void setLighDistance(int distance) {

			this.iDistance = distance;
		}

		public void lightUp (String inst) {
			//System.out.println(inst);
			for (int i = 0; i < inst.length()-2; i++) {
				int binary = Integer.parseInt(inst.charAt(i) + "");
				if (binary == 1) {
					this.turnOnLight(i);
				}
			}
//			revalidate();
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
		public void intermitent(String inst) {
			Timer timer = new Timer(500, new TimerListener(this, inst));
		    timer.setInitialDelay(0);
		    timer.start();
		}
		
		private class TimerListener implements ActionListener {
		    private TrafficLight light;
		    private String inst;
		    private boolean isOn = true;
		    public TimerListener(TrafficLight light, String inst) {
		      this.light = light;
		      this.inst = inst;
		    }
		    
		    @Override
		    public void actionPerformed(ActionEvent e) {
		      if (isOn) {
		    	  light.reset();
		    	  light.repaint();
		      } else {
		    	  light.lightUp(this.inst);
		    	  light.repaint();
		      }
		      isOn = !isOn;
		    }
		    
		  }

}