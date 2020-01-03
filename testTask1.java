// Test de la classe Image2d

import java.awt.*;
import java.util.LinkedList;

public class testTask1 {
	public static void main(String[] args) {

		Image2d Image = new Image2d(800, 200);
		LinkedList<Polyomino> Pliste = new LinkedList<Polyomino>(); // On construit une LinkedList avec tous nos
																	// polyominos, à partir du fichier .txt

		java.util.List<String> F = Polyomino.readFile("polyominoesINF421_complet.txt");
		int x = 0;
		for (String input : F) {
			Polyomino P = new Polyomino(input, new Color(150, 150, 150));

		// Du fait de la définition des axes de Image (qui sont inversés par rapport à
								// notre définition
								// de l'implémentation des polyomino, cf exemple INF421 du pdf), les polyominos
								// apparaissent à l'envers,
								// on doit donc leur faire subir une réflexion verticale pour les voir à
								// l'endroit.

			/// ZONE DE TEST DE FONCTIONS ////
			
			System.out.println("["+P.data.getFirst().polygon.xpoints[0]+","+P.data.getFirst().polygon.ypoints[0]+"]");

			for ( ColoredPolygon CP :P.data) {
				System.out.println(CP.polygon.xpoints[0]+","+CP.polygon.ypoints[0]);

			}
			P.rotation();
			P.visualize();

			

			// P.dilatation();

			/// FIN DE LA ZONE DE TEST ////

			x = P.Max_x() + 30; // On s'assure de cette manière que tous les polyominos seront espacés de 30
								// pixels (valeur arbitraire) de leur voisin de gauche

		}

		//LinkedList<ColoredPolygon> dataTotal = new LinkedList<ColoredPolygon>(); // On concatène toutes nos listes de
																					// carrés dans une seule pour que
																					// Image les prennent tous d'un coup
																					// (sinon Image rafraîchit sa
																					// mémoire)

		//for (Polyomino P : Pliste) { // On ajoute à l'image les edges de tout les polyominoes
			// dataTotal.addAll(P.data);
			//Image.addEdges(P.edges);

		//}
		//Image.addListe(dataTotal);

		//ColoredPolygon CP = new ColoredPolygon(new String[] { "5", "5" }, Color.red); // Test des fonctions voisins (je
																						// ne la met pas dans la ZONE DE
																						// TEST car
		// Image.addPolygon(CP); // sinon Image se rafraîchit et ça ne les affiche pas.
		// Image.addPolygon(CP.VoisinBas().changerCouleur(Color.blue));

		// new Image2dViewer(Image);

	}

}
