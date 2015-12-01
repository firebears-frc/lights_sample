package examples;

import opc.*;

public class BlinkApplication {

    public static void main(String[] args) throws Exception {
		String FC_SERVER_HOST = System.getProperty("fadecandy.server", "raspberrypi.local");
		int FC_SERVER_PORT = Integer.parseInt(System.getProperty("fadecandy.port", "7890"));
		int PIXELSTRIP_PIN = Integer.parseInt(System.getProperty("pixelStrip", "0"));
		
        OpcClient server = new OpcClient(FC_SERVER_HOST, FC_SERVER_PORT);
		server.setSingleStripNum(PIXELSTRIP_PIN);
        OpcDevice fadecandy = server.addDevice();
        
        PixelStrip strip = fadecandy.addPixelStrip(PIXELSTRIP_PIN, 8);

        for (int i = 0; i<100; i++) {
	        strip.setPixelColor(0, 0x00FF00);
	        server.show();
	        Thread.sleep(1000);
	        server.clear();
	        server.show();
	        Thread.sleep(1000);
        }

        server.close();
    }
}
