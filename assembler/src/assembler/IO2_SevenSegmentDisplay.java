package assembler;

import javax.swing.*;
import java.awt.*;


public class IO2_SevenSegmentDisplay {
	
	private static Converter conv = new Converter();
	static String inst = "10101011";
	private static SevenSegments ss = new SevenSegments();
	private final JFrame f = new JFrame("Seven Segment Display");
	public static void main(String[] args) throws InterruptedException {
		
		JFrame frame = initFrame();
		SevenSegments ss = new SevenSegments();
		
		frame.add(ss);
		
		ss.turnOnSegment(bitOn(inst));
	}
	
	public SevenSegments getSevenSegments() {
		return ss;
	}
	
	public static void bitDivid(String s) throws InterruptedException {
		String str = conv.hextoBin(s);
		ss.turnOnSegment(bitOn(str));
	}
	
	private static boolean[] bitOn(String s) {
		boolean[] bool = new boolean[8];
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '1') {
				bool[i] = true;
			}
			else {
				bool[i] = false;
			}
		}
		return bool;
	}
	
	private static JFrame initFrame() {
		JFrame frame = new JFrame();
		frame.setTitle("Seven Segment Display");
		frame.setSize(250, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}

	 static class SevenSegments extends JPanel {
		
		private static final long serialVersionUID = 1L;
		private final JFrame f = new JFrame("Seven Segment Display");
		
		private int ten = 10;
		private int fifty = 50;
		private int apy = 10;
		private int bpx = 5;
		private int apx = 15 + bpx;
		private int bpy = 20;
		private int cpx = 70 + bpx;
		private int cpy = 20;
		// Segments color
		Color Greena;
		Color Greenb;
		Color Greenc;
		Color Greend;
		Color Greene;
		Color Greenf;
		Color Greeng;
		Color Greena2;
		Color Greenb2;
		Color Greenc2;
		Color Greend2;
		Color Greene2;
		Color Greenf2;
		Color Greeng2;
		
		SevenSegments() {
			this.Greena = Color.BLACK;
			this.Greenb = Color.BLACK;
			this.Greenc = Color.BLACK;
			this.Greend = Color.BLACK;
			this.Greene = Color.BLACK;
			this.Greenf = Color.BLACK;
			this.Greeng = Color.BLACK;
			this.Greena2 = Color.BLACK;
			this.Greenb2 = Color.BLACK;
			this.Greenc2 = Color.BLACK;
			this.Greend2 = Color.BLACK;
			this.Greene2 = Color.BLACK;
			this.Greenf2 = Color.BLACK;
			this.Greeng2 = Color.BLACK;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			segmentLights(g);
			segmentLights2(g);
		}

		public void turnOnSegment(boolean[] bool) throws InterruptedException {
			for(int i = 0; i < bool.length - 1; i++) {
				if(!bool[bool.length - 1]) {
					if(bool[i])
					this.segmentLight(i);
				}
				else {
					if(bool[i])
					this.segmentLight2(i);
				}
			}
			revalidate();
		}

		private void segmentLight(int i) {
			System.out.println("Turn on segment: " + i);
			switch(i) {
			case 0:
				this.Greena = Color.GREEN;
				break;
			case 1:
				this.Greenb = Color.GREEN;
				break;
			case 2:
				this.Greenc = Color.GREEN;
				break;
			case 3:
				this.Greend = Color.GREEN;
				break;
			case 4:
				this.Greene = Color.GREEN;
				break;
			case 5:
				this.Greenf = Color.GREEN;
				break;
			case 6:
				this.Greeng = Color.GREEN;
				break;
			default:
				break;
			}
		}
		
		private void segmentLight2(int i) {
			System.out.println("Turn on segment: " + i);
			switch(i) {
			case 0:
				this.Greena2 = Color.GREEN;
				break;
			case 1:
				this.Greenb2 = Color.GREEN;
				break;
			case 2:
				this.Greenc2 = Color.GREEN;
				break;
			case 3:
				this.Greend2 = Color.GREEN;
				break;
			case 4:
				this.Greene2 = Color.GREEN;
				break;
			case 5:
				this.Greenf2 = Color.GREEN;
				break;
			case 6:
				this.Greeng2 = Color.GREEN;
				break;
			default:
				break;
			}
		}

		public void reset() {
			this.Greena = Color.BLACK;
			this.Greenb = Color.BLACK;
			this.Greenc = Color.BLACK;
			this.Greend = Color.BLACK;
			this.Greene = Color.BLACK;
			this.Greenf = Color.BLACK;
			this.Greeng = Color.BLACK;
			this.Greena2 = Color.BLACK;
			this.Greenb2 = Color.BLACK;
			this.Greenc2 = Color.BLACK;
			this.Greend2 = Color.BLACK;
			this.Greene2 = Color.BLACK;
			this.Greenf2 = Color.BLACK;
			this.Greeng2 = Color.BLACK;
		}

		//Segments
		private void segmentLights(Graphics g) {
			
			
			g.setColor(this.Greena);
			g.fillRect(apx, apy, fifty, ten);
			
			g.setColor(this.Greenb);
			g.fillRect(bpx, bpy, ten, fifty);
			
			g.setColor(this.Greenc);
			g.fillRect(cpx, cpy, ten, fifty);
			
			g.setColor(this.Greend);
			g.fillRect(apx, apy + 60, fifty, ten);
			
			g.setColor(this.Greene);
			g.fillRect(bpx, bpy + 60, ten, fifty);
			
			g.setColor(this.Greenf);
			g.fillRect(cpx, cpy + 60, ten, fifty);
			
			g.setColor(this.Greeng);
			g.fillRect(apx, apy + (60 * 2), fifty, ten);
			
		}
		
		private void segmentLights2(Graphics g) {

			this.bpx += 100;
			this.apx += 100;
			this.cpx += 100;
			
			g.setColor(this.Greena2);
			g.fillRect(apx, apy, fifty, ten);
			
			g.setColor(this.Greenb2);
			g.fillRect(bpx, bpy, ten, fifty);
			
			g.setColor(this.Greenc2);
			g.fillRect(cpx, cpy, ten, fifty);
			
			g.setColor(this.Greend2);
			g.fillRect(apx, apy + 60, fifty, ten);
			
			g.setColor(this.Greene2);
			g.fillRect(bpx, bpy + 60, ten, fifty);
			
			g.setColor(this.Greenf2);
			g.fillRect(cpx, cpy + 60, ten, fifty);
			
			g.setColor(this.Greeng2);
			g.fillRect(apx, apy + (60 * 2), fifty, ten);
			
		}
		public void launch() {
			f.add(ss);
			f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			f.pack();
			f.setSize(250,300);
			f.setResizable(false);
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		}
		
	}
}
