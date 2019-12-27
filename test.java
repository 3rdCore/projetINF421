// Test de la classe Image2d

import java.awt.*;

import javax.swing.JPanel;

public class test {
	public static void main(String[] args) throws Exception {
		System.out.println("Test de ColoredPolygon");
		try {
			ColoredPolygon CP1 = new ColoredPolygon("[(0,0), (0,4), (1,0), (1,1), (1,2), (1,3), (1,4), (2,0), (2,4)]",
					new Color(100, 100, 100));

		} catch (Exception e) {
			System.out.println("Erreur dans l'implémentation de ColoredPolygon");
		}
		System.out.println("Test de ColoredPolygon passé avec succés");

		System.out.println("Test de Image2d");
		//try {
			Image2d Image = new Image2d(500, 500);

			java.util.List<String> F = ColoredPolygons.readFile("polyominoesINF421.txt");

			for (String ligne : F) {
				ColoredPolygon CP = new ColoredPolygon(ligne, new Color(100, 100, 100));
				Image.addPolygon(CP);
			}
			
			Image2dViewer view = new Image2dViewer(Image);
			Image2dComponent viewc = new Image2dComponent (Image);
			Graphics g=view.getGraphics();
			viewc.paint(g);

		//} catch (Exception e) {
			// System.out.println("Erreur dans l'implémentation de ColoredPolygon");
		//}
	}
}
