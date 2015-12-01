package examples;

import opc.Animation;
import opc.OpcClient;
import opc.OpcDevice;
import opc.PixelStrip;

/**
 * Example animation that pulses all pixels through an array of colors.
 */
public class Pulsing extends Animation {


	/** Milliseconds for each pulse cycle. */
	long timeCycle = 2000;
	int color[] = {
			0x00FF00, // Green
			0xCCCC00, // Yellow
//			0x888888, // White
	};
	int colorLen = color.length;




	@Override
	public void reset(PixelStrip strip) {
		// Nothing
	}




	@Override
	public boolean draw(PixelStrip strip) {
		long currentTime = millis() % timeCycle;
		for (int p = 0; p < strip.getPixelCount(); p++) {
			int color_num = p % colorLen;
			int timeShift = (int)(color_num * (timeCycle / colorLen));
			int brightness = pulseOverTime((currentTime + timeShift) % timeCycle);
			int c1 = color[color_num];
			int c2 = fadeColor(c1, brightness);
			strip.setPixelColor(p, c2);
		}
		return true;
	}




	/**
	 * Return a brightness value as a function of time. The input value is the
	 * number of milliseconds into the cycle, from zero to timeCycle.
	 * Cycle over a sine function, so it's nice and smooth.
	 *
	 * @param timeNow time within the cycle.
	 * @return brightness value from 0 to 255
	 */
	private int pulseOverTime(long timeNow) {
	  double theta = 6.283 * timeNow / timeCycle;   // Angle in radians
	  double s = (Math.sin(theta) + 1.0) / 2.0;     // Value from 0.0 to 1.0
	  return (int)Math.round(s * 256);
	}




	public static void main(String[] args) throws Exception {
		String FC_SERVER_HOST = System.getProperty("fadecandy.server", "raspberrypi.local");
		int FC_SERVER_PORT = Integer.parseInt(System.getProperty("fadecandy.port", "7890"));
		int PIXELSTRIP_PIN = Integer.parseInt(System.getProperty("pixelStrip", "0"));
		boolean verbose = "true".equalsIgnoreCase(System.getProperty("verbose", "false"));
		
		OpcClient server = new OpcClient(FC_SERVER_HOST, FC_SERVER_PORT);
		server.setVerbose(verbose);
		server.setSingleStripNum(PIXELSTRIP_PIN);
		OpcDevice fadeCandy = server.addDevice();

		PixelStrip strip1 = fadeCandy.addPixelStrip(PIXELSTRIP_PIN, 8);
		System.out.println(server.getConfig());

		strip1.setAnimation(new Pulsing());
		for (int i=0; i<10000; i++) {
			server.animate();
			Thread.sleep(100);
		}
		server.close();
	}
}
