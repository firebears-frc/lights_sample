package examples;

import opc.*;

public class Blink extends Animation {

	boolean lightOn = false;

	public void reset(PixelStrip strip) {
		strip.clear();
		lightOn = false;
		setTimeout(0.1);
	}

	public boolean draw(PixelStrip strip) {
		if (isTimedOut()) {
			if (lightOn) {
				strip.setPixelColor(0, 0x000000);
				lightOn = false;
			} else {
				strip.setPixelColor(0, 0xFF0000);
				lightOn = true;
			}
			setTimeout(1.0);
		}
		return true;
	}




	public static void main(String[] args) throws Exception {
		final String FC_SERVER_HOST = System.getProperty("fadecandy.server", "raspberrypi.local");
		final int FC_SERVER_PORT = Integer.parseInt(System.getProperty("fadecandy.port", "7890"));
		final int STRIP1_COUNT = Integer.parseInt(System.getProperty("fadecandy.strip1.count", "8"));
		final int PIXELSTRIP_PIN = Integer.parseInt(System.getProperty("pixelStrip", "0"));
		final boolean VERBOSE = "true".equalsIgnoreCase(System.getProperty("verbose", "false"));

		OpcClient server = new OpcClient(FC_SERVER_HOST, FC_SERVER_PORT);
		server.setVerbose(VERBOSE);
		server.setSingleStripNum(PIXELSTRIP_PIN);
		OpcDevice fadeCandy = server.addDevice();

		PixelStrip strip1 = fadeCandy.addPixelStrip(PIXELSTRIP_PIN, STRIP1_COUNT);
		System.out.println(server.getConfig());

		strip1.setAnimation(new Blink());
		for (int i=0; i<10000; i++) {
			server.animate();
			Thread.sleep(100);
		}
		server.close();
	}

}
