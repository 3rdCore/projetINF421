import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ColoredPolygon {
	public Polygon polygon;
	public Color color;

//Several constructors are implemented.

	public ColoredPolygon(int[] xcoords, int[] ycoords, Color color) {
		this.polygon = new Polygon(xcoords, ycoords, xcoords.length);
		this.color = color;
	}

	public ColoredPolygon(String input, Color color) {
		String[] couples = input.split(", ");
		int[] xcoords = new int[couples.length];
		int[] ycoords = new int[couples.length];
		int i = 0;
		for (String couple : couples) {
			String[] Couple = couple.replace("(", "").replace(")", "").replace("[", "").replace("]", "").split(",");
			xcoords[i] = Integer.parseInt(Couple[0]);
			ycoords[i] = Integer.parseInt(Couple[1]);
			i += 1;
		}
		this.polygon = new Polygon(xcoords, ycoords, xcoords.length);
		this.color = color;
	}

}
