package examples;

import opc.*;

public class Blink extends Animation {

	long changeTime;
	boolean lightOn;

	public void reset(PixelStrip strip) {
		strip.clear();
		changeTime = millis();
		lightOn = false;
	}

	public boolean draw(PixelStrip strip) {
		if (millis() > changeTime) {
			if (lightOn) {
				strip.setPixelColor(0, 0xFF0000);
				lightOn = false;
			} else {
				strip.setPixelColor(0, 0x000000);
				lightOn = true;
			}
			changeTime = millis() + 1000;
		}
		return true;
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

		strip1.setAnimation(new Blink());
		for (int i=0; i<1000; i++) {
			server.animate();
			Thread.sleep(100);
		}
		server.close();
	}

}
