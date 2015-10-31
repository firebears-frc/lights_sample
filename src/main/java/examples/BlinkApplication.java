package examples;

import opc.*;

public class BlinkApplication {

	static final String FC_SERVER_HOST = System.getProperty("fadecandy.server", "raspberrypi.local");
	static int FC_SERVER_PORT = Integer.parseInt(System.getProperty("fadecandy.port", "7890"));
	static int PIXELSTRIP_PIN = Integer.parseInt(System.getProperty("pixelStrip", "0"));

    public static void main(String[] args) throws Exception {

        OpcClient server = new OpcClient(FC_SERVER_HOST, FC_SERVER_PORT);
        OpcDevice fadecandy = server.addDevice();
        PixelStrip strip = fadecandy.addPixelStrip(PIXELSTRIP_PIN, 8);

        for (int i = 0; i<100; i++) {
	        strip.setPixelColor(0, 0xFF0000);
	        server.show();
	        Thread.sleep(1000);
	        server.clear();
	        server.show();
	        Thread.sleep(1000);
        }

        server.close();
    }
}
