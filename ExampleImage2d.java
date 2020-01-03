import java.awt.Color;

public class ExampleImage2d {

	public static void main(String[] args) {
		Image2d img = new Image2d(500, 500);
		// adding some colored polygons
		int[] xcoords1 = {250, 350, 300, 400, 350, 450, 270, 270, 230, 230, 50, 150, 100, 200, 150};
		int[] ycoords1 = {100, 200, 200, 300, 300, 400, 400, 450, 450, 400, 400, 300, 300, 200, 200};
		img.addPolygon(xcoords1, ycoords1, Color.BLUE);
		int[] xcoords2 = {100, 200, 200, 100};
		int[] ycoords2 = {410, 410, 450, 450};
		img.addPolygon(xcoords2, ycoords2, Color.RED);
		// adding white edges (this is convenient when working with tilings to separate the polyominoes) 
		img.addEdge(100, 430, 200, 430, 5);
		img.addEdge(150, 410, 150, 450, 5);
		new Image2dViewer(img);
	}
}