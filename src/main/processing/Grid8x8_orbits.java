
import processing.core.*;

public class Grid8x8_orbits extends PApplet {

	static String FC_SERVER_HOST = "raspberrypi.local";
	static int FC_SERVER_PORT = 7890;
	static int PIXELSTRIP_PIN = 0;

	OPC opc;
	PImage dot1, dot2;

	public void setup() {
		size(640, 360);
		frameRate(20);

		dot1 = loadImage("greenDot.png");
		dot2 = loadImage("purpleDot.png");

		// Connect to the local instance of fcserver. You can change this line
		// to connect to another computer's fcserver
		opc = new OPC(this, FC_SERVER_HOST, FC_SERVER_PORT);

		// Map an 8x8 grid of LEDs to the center of the window, scaled to take
		// up most of the space
		float spacing = (float) (height / 14.0f);
		opc.ledGrid8x8(PIXELSTRIP_PIN, width / 2, height / 2, spacing, HALF_PI, false);
	}

	float px, py;

	public void draw() {
		background(0);
		blendMode(ADD);

		// Smooth out the mouse location
		px += (mouseX - px) * 0.1;
		py += (mouseY - py) * 0.1;

		float a = (float) (millis() * 0.001f);
		float r = (float) (py * 0.5f);
		float dotSize = r * 4;

		float dx = width / 2 + cos(a) * r;
		float dy = height / 2 + sin(a) * r;

		// Draw it centered at the mouse location
		image(dot1, dx - dotSize / 2, dy - dotSize / 2, dotSize, dotSize);

		// Another dot, mirrored around the center
		image(dot2, width - dx - dotSize / 2, height - dy - dotSize / 2, dotSize, dotSize);
	}

	public static void main(String _args[]) {
		FC_SERVER_HOST = System.getProperty("fadecandy.server", "raspberrypi.local");
		FC_SERVER_PORT = Integer.parseInt(System.getProperty("fadecandy.port", "7890"));
		PIXELSTRIP_PIN = Integer.parseInt(System.getProperty("pixelStrip", "0"));
		PApplet.main(new String[] { Grid8x8_orbits.class.getName() });
	}
}
