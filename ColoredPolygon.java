import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

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

	public ColoredPolygon translation(int x, int y) {
		int[] xcoords = new int[this.polygon.npoints];
		int[] ycoords = new int[this.polygon.npoints];

		for (int i = 0; i < this.polygon.npoints; i++) {
			xcoords[i] = this.polygon.xpoints[i] + x;
			ycoords[i] = this.polygon.ypoints[i] + y;

		}
		return (new ColoredPolygon(xcoords, ycoords, this.color));
	}

	public ColoredPolygon rotation(boolean sens) {
		int[] xcoords = this.polygon.xpoints;
		int[] ycoords = this.polygon.ypoints;

		int xrot = xcoords[0];
		int yrot = ycoords[0];
		if (sens) { // sens trigo
			for (int i = 1; i < this.polygon.npoints; i++) {
				xcoords[i] = xrot - (yrot - ycoords[i]);
				ycoords[i] = yrot - (xrot - xcoords[i]);

			}
		} else { // sens horaire
			for (int i = 1; i < this.polygon.npoints; i++) {
				xcoords[i] = xrot + (yrot - ycoords[i]);
				ycoords[i] = yrot + (xrot - xcoords[i]);

			}
		}
		return (new ColoredPolygon(xcoords, ycoords, this.color));
	}

	public ColoredPolygon reflectionsVert() { //reflection par rapport à un plan vertical
		int[] xcoords = this.polygon.xpoints;
		int[] ycoords = this.polygon.ypoints;
		LinkedList<Integer> Xcoords = new LinkedList<Integer>();
		for (int i = 0; i < this.polygon.npoints; i++) {
			Xcoords.add(xcoords[i]);
		}
		int milieu = Collections.max(Xcoords) - Collections.min(Xcoords);
		if (milieu % 2 == 0) {
			for (int i = 0; i < this.polygon.npoints; i++) {
				xcoords[i] = 2 * milieu - xcoords[i];

			}
		} else {
			milieu = (int) Math.ceil(milieu % 2);
			for (int i = 0; i < this.polygon.npoints; i++) {
				xcoords[i] = 2 * milieu + 1 - xcoords[i];
			}
		}
		return (new ColoredPolygon(xcoords, ycoords, this.color));
	}

	public ColoredPolygon reflectionsHori() { //reflection par rapport à un plan horizontal
		int[] xcoords = this.polygon.xpoints;
		int[] ycoords = this.polygon.ypoints;
		LinkedList<Integer> Ycoords = new LinkedList<Integer>();
		for (int i = 0; i < this.polygon.npoints; i++) {
			Ycoords.add(ycoords[i]);
		}
		int milieu = Collections.max(Ycoords) - Collections.min(Ycoords);
		if (milieu % 2 == 0) {
			for (int i = 0; i < this.polygon.npoints; i++) {
				ycoords[i] = 2 * milieu - ycoords[i];

			}
		} else {
			milieu = (int) Math.ceil(milieu % 2);
			for (int i = 0; i < this.polygon.npoints; i++) {
				ycoords[i] = 2 * milieu + 1 - ycoords[i];
			}
		}
		return (new ColoredPolygon(xcoords, ycoords, this.color));

	}

}