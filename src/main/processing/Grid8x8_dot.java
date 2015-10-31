
import processing.core.*;

public class Grid8x8_dot extends PApplet {
	OPC opc;
	PImage dot;

	static String FC_SERVER_HOST = "raspberrypi.local";
	static int FC_SERVER_PORT = 7890;
	static int PIXELSTRIP_PIN = 0;

	public void setup() {
		size(640, 360);

		// Load a sample image
		dot = loadImage("dot.png");

		// Connect to the local instance of fcserver
		opc = new OPC(this, FC_SERVER_HOST, FC_SERVER_PORT);

		// Map an 8x8 grid of LEDs to the center of the window
		opc.ledGrid8x8(PIXELSTRIP_PIN, (float)(width / 2), (float)(height / 2), (float)(height / 12.0), 0f, false);
	}

	public void draw() {
		background(0);

		// Draw the image, centered at the mouse location
		float dotSize = (float)(height * 0.7);
		image(dot, mouseX - dotSize / 2, mouseY - dotSize / 2, dotSize, dotSize);
	}


	public static void main(String _args[]) {
		FC_SERVER_HOST = System.getProperty("fadecandy.server", "raspberrypi.local");
		FC_SERVER_PORT = Integer.parseInt(System.getProperty("fadecandy.port", "7890"));
		PIXELSTRIP_PIN = Integer.parseInt(System.getProperty("pixelStrip", "0"));
		PApplet.main(new String[] { Grid8x8_dot.class.getName() });
	}
}
