
import java.awt.*;
import java.util.LinkedList;
import java.util.HashSet;

public class testTask3 {

	public static LinkedList<Polyomino> allFixedPolyominos(int n) { // Renvoie la liste de tous les Polyominos "fix�s"
																	// de taille n donn�e.
																	// Par d�finition (puisqu'ils ne diff�rent que d'une
																	// translation),
																	// cela renverra donc une LinkedList de tous les
																	// Polyominos possible.
																	// On va progresser par r�cursion : on consid�re une
																	// liste de tous les Polyominos
																	// de taille n-1, et on va sur chacun d'entre eux
																	// augmenter sa taille de 1 en
																	// lui "collant" un carr� suppl�mentaire. Une fois
																	// qu'on a fait cela pour tous
																	// les carr�s voisins possibles, on a tous les
																	// polyominos de taille n. Il suffit
																	// donc juste de supprimer les doublons (ie les
																	// Polyominos �gaux par translation)
																	// pour avoir la liste finale.

		if (n == 1) { // Condition d'arr�t : en taille 1, on renvoit le polyomino compos� d'un seul
						// carr�
			LinkedList<Polyomino> listeP = new LinkedList<Polyomino>();
			Polyomino P = new Polyomino("[(0,0)]", Color.red);
			listeP.add(P);
			return listeP;
		}

		LinkedList<Polyomino> listeP = allFixedPolyominos(n - 1); // R�cursivit�

		LinkedList<Polyomino> nouvListe = new LinkedList<Polyomino>();
		for (Polyomino P : listeP) {

			for (ColoredPolygon CP : P.data) { // On prend un carr� dans P et on va consid�rer tous ses voisins
												// possibles.
				ColoredPolygon CPHaut = CP.VoisinHaut();
				ColoredPolygon CPBas = CP.VoisinBas();
				ColoredPolygon CPGauche = CP.VoisinGauche();
				ColoredPolygon CPDroite = CP.VoisinDroite();
				String Pecrit = P.ecrit(); // Si le polyomino P contient d�j� le carr� voisin, alors on l'ignore.
											// Sinon, on cr�e un nouveau polyomino identique � P, on lui ajoute ce carr�
											// voisin,
											// et on l'ajoute � nouvListe. Pour des raisons de copies d'objets, on ne
											// peut pas
											// directement ajouter le voisin � P, puis P � nouvListe car lorsqu'on
											// testera pour les voisins
											// suivants, la liste P sera modifi�e dans nouvListe.

				if (!P.data.contains(CPHaut)) {
					Polyomino nouvP = new Polyomino(Pecrit, P.color); // On copie notre Polyomino (c'est la m�thode de
					nouvP.data.add(CPHaut);
															// copie la plus simple que j'ai trouv�e)
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
		} // On s'occupe maintenant de supprimer les Polyominos qui sont �gaux par
			// translation/rotation ou sym�trie.
			// Pour cela le plus simple est d'utiliser une table de hachage. Il faut donc
			// impl�menter
			// les m�thodes hashCode() et equals() dans la classe Polyomino.

																											// les
																											// doublons
																											// ne seront
																											// pas
																											// enregistr�s

		return nouvListe;

	}

	public static void main(String[] args) { // test de la fonction allFixedPolyominos

		LinkedList<Polyomino> listeP = allFixedPolyominos(4);
		Image2d img = new Image2d(500, 500);
		LinkedList<ColoredPolygon> dataTotal = new LinkedList<ColoredPolygon>();

		int x = 0;
		int y = 0;
		int compteur = 0; // Sert juste � afficher proprement les polyominos, pour qu'ils soient tous
							// visibles � l'�cran
		for (Polyomino P : listeP) {
			P.translation(-P.Min_x() + x, -P.Min_y() + y);
			x = P.Max_x() + 20;
			if (compteur > 20) { // On place 10 polyominos par ligne (d�pend de la taille des carr�s)
				compteur = 0;
				y = P.Max_y() + 60;
				x = 0;
			}

			compteur++;

			dataTotal.addAll(P.data);

			P.updateEdges(); // La m�thode allFixedPolyominos ne g�re pas les bords, on le fait donc � ce
								// niveau l� (en pratique inutile car translation
			img.addEdges(P.edges); // et reflexion le font d�j�, mais bon je le laisse on sait jamais...)
		}
		img.addListe(dataTotal);

		//new Image2dViewer(img);
	}

}