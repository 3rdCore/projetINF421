
import java.awt.*;
import java.util.LinkedList;
import java.util.HashSet;

public class testTask3 {

	public static LinkedList<Polyomino> allFixedPolyominos(int n) { // Renvoie la liste de tous les Polyominos "fixés"
																	// de taille n donnée.
																	// Par définition (puisqu'ils ne diffèrent que d'une
																	// translation),
																	// cela renverra donc une LinkedList de tous les
																	// Polyominos possible.
																	// On va progresser par récursion : on considère une
																	// liste de tous les Polyominos
																	// de taille n-1, et on va sur chacun d'entre eux
																	// augmenter sa taille de 1 en
																	// lui "collant" un carré supplémentaire. Une fois
																	// qu'on a fait cela pour tous
																	// les carrés voisins possibles, on a tous les
																	// polyominos de taille n. Il suffit
																	// donc juste de supprimer les doublons (ie les
																	// Polyominos égaux par translation)
																	// pour avoir la liste finale.

		if (n == 1) { // Condition d'arrêt : en taille 1, on renvoit le polyomino composé d'un seul
						// carré
			LinkedList<Polyomino> listeP = new LinkedList<Polyomino>();
			Polyomino P = new Polyomino("[(0,0)]", Color.red);
			listeP.add(P);
			return listeP;
		}

		LinkedList<Polyomino> listeP = allFixedPolyominos(n - 1); // Récursivité

		LinkedList<Polyomino> nouvListe = new LinkedList<Polyomino>();
		for (Polyomino P : listeP) {

			for (ColoredPolygon CP : P.data) { // On prend un carré dans P et on va considérer tous ses voisins
												// possibles.
				ColoredPolygon CPHaut = CP.VoisinHaut();
				ColoredPolygon CPBas = CP.VoisinBas();
				ColoredPolygon CPGauche = CP.VoisinGauche();
				ColoredPolygon CPDroite = CP.VoisinDroite();
				String Pecrit = P.ecrit(); // Si le polyomino P contient déjà le carré voisin, alors on l'ignore.
											// Sinon, on crée un nouveau polyomino identique à P, on lui ajoute ce carré
											// voisin,
											// et on l'ajoute à nouvListe. Pour des raisons de copies d'objets, on ne
											// peut pas
											// directement ajouter le voisin à P, puis P à nouvListe car lorsqu'on
											// testera pour les voisins
											// suivants, la liste P sera modifiée dans nouvListe.

				if (!P.data.contains(CPHaut)) {
					Polyomino nouvP = new Polyomino(Pecrit, P.color); // On copie notre Polyomino (c'est la méthode de
					nouvP.data.add(CPHaut);
															// copie la plus simple que j'ai trouvée)
					if (!nouvListe.contains(nouvP)) {
						nouvListe.add(nouvP);
					}
				}
				if (!P.data.contains(CPBas)) {
					Polyomino nouvP = new Polyomino(Pecrit, P.color);
					nouvP.data.add(CPBas);

					if (!nouvListe.contains(nouvP)) {

						nouvListe.add(nouvP);
					}
				}
				if (!P.data.contains(CPGauche)) {
					Polyomino nouvP = new Polyomino(Pecrit, P.color);
					nouvP.data.add(CPGauche);

					if (!nouvListe.contains(nouvP)) {

						nouvListe.add(nouvP);
					}
				}
				if (!P.data.contains(CPDroite)) {
					Polyomino nouvP = new Polyomino(Pecrit, P.color);
					nouvP.data.add(CPDroite);

					if (!nouvListe.contains(nouvP)) {

						nouvListe.add(nouvP);
					}
				}
			}
		} // On s'occupe maintenant de supprimer les Polyominos qui sont égaux par
			// translation/rotation ou symétrie.
			// Pour cela le plus simple est d'utiliser une table de hachage. Il faut donc
			// implémenter
			// les méthodes hashCode() et equals() dans la classe Polyomino.

																											// les
																											// doublons
																											// ne seront
																											// pas
																											// enregistrés

		return nouvListe;

	}

	public static void main(String[] args) { // test de la fonction allFixedPolyominos

		LinkedList<Polyomino> listeP = allFixedPolyominos(4);
		Image2d img = new Image2d(500, 500);
		LinkedList<ColoredPolygon> dataTotal = new LinkedList<ColoredPolygon>();

		int x = 0;
		int y = 0;
		int compteur = 0; // Sert juste à afficher proprement les polyominos, pour qu'ils soient tous
							// visibles à l'écran
		for (Polyomino P : listeP) {
			P.translation(-P.Min_x() + x, -P.Min_y() + y);
			x = P.Max_x() + 20;
			if (compteur > 20) { // On place 10 polyominos par ligne (dépend de la taille des carrés)
				compteur = 0;
				y = P.Max_y() + 60;
				x = 0;
			}

			compteur++;

			dataTotal.addAll(P.data);

			P.updateEdges(); // La méthode allFixedPolyominos ne gère pas les bords, on le fait donc à ce
								// niveau là (en pratique inutile car translation
			img.addEdges(P.edges); // et reflexion le font déjà, mais bon je le laisse on sait jamais...)
		}
		img.addListe(dataTotal);

		//new Image2dViewer(img);
	}

}