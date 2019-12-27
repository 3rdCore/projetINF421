
import java.io.*;
import java.util.*;

public class ColoredPolygon {
	private int[] xcoords;
	private int[] ycoords;
	private String color;

//Several constructors are implemented.

	public ColoredPolygon(int[] xcoords, int[] ycoords, String color) {
		this.xcoords = xcoords;
		this.ycoords = ycoords;
		this.color = color;
	}

	public ColoredPolygon(String input, String color) {
		String[] couples = input.split(",");
		int[] xcoords = new int[couples.length];
		int[] ycoords = new int[couples.length];
		int i = 0;
		for (String couple : couples) {
			String[] Couple = couple.replace("(", "").replace(")", "").split(",");
			xcoords[i] = Integer.parseInt(Couple[0]);
			ycoords[i] = Integer.parseInt(Couple[1]);
		}
		this.xcoords = xcoords;
		this.ycoords = ycoords;
		this.color = color;
	}

}
