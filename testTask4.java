// Test de la classe Image2d

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;

public class testTask4 {
	public static void main(String[] args) {
		int taille = 3;
		
		int[][] map = new int[taille][taille];						// on créé une map carré de format : "taille"*"taille"
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				map[i][j] = 1;
			}
		}
		
		
		LinkedList<Polyomino> listeP = testTask3.allFixedPolyominos(3);
		//listeP.addAll(testTask3.allFixedPolyominos(4));
		//listeP.addAll(testTask3.allFixedPolyominos(2));
		//listeP.addAll(testTask3.allFixedPolyominos(1));

		for (Polyomino P : listeP) {
			P.Origine();
		}
	

		/// ZONE DE TEST DE FONCTIONS ////
		
		
		//map en rectangle :
		// String input = "[(0,0), (0,1), (1,0), (1,1), (1,2), (2,0), (2,1), (2,2)]";
		// map en étoile :
		// String input = "[(3,0), (3,1), (4,1), (2,1), (1,2), (2,2), (3,2), (4,2), (5,2), (0,3), (1,3), (2,3), (3,3), (4,3), (5,3), (6,3), (1,4), (2,4), (3,4), (4,4), (5,4), (2,5), (3,5), (4,5), (3,6)]";
		
		
		//covering CV = new covering(input, 7, 7);			// 2 différents constructeur de la classe covering
		
		covering CV = new covering(map);

								/// FIN DE LA ZONE DE TEST DE FONCTIONS ////

		CV.getCollection(listeP);
		System.out.println("On travaille avec une collection de : " + CV.Collection.size()
				+ " Positions possibles des Polyominoes");
		
		for (Polyomino P : CV.Collection) {
			// P.visualize();									// Si l'on souhaite voir tout les placements possibles de l'ensemble des pièces
		}

		
		System.out.println("Calcul du nombre de Pavages ...");
		System.out.println("");
		LinkedList<LinkedList<Polyomino>> Pavages = CV.cover();  				// Calcul 
		System.out.println("Nombre de Pavages : " + Pavages.size());
		
								/// ZONE D'AFFICHAGE ///
		
		for (LinkedList<Polyomino> Pliste : Pavages) {
			Image2d Image = new Image2d(150, 150);

			LinkedList<ColoredPolygon> dataTotal = new LinkedList<ColoredPolygon>();
			for (Polyomino P : Pliste) { // On ajoute à l'image les edges de tout les polyominoes
				dataTotal.addAll(P.data);
				Image.addEdges(P.edges);

			}
			Image.addListe(dataTotal);

			new Image2dViewer(Image);

		}
	}

}