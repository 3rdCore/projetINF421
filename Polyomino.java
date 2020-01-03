import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Polyomino { // Un polyomino est repr�sent� par une liste cha�n�e de plusieurs carr�s (=
							// ColoredPolygon)

	public LinkedList<ColoredPolygon> data;
	public Color color;
	public LinkedList<Edge> edges = new LinkedList<Edge>(); // L� encore, les bords du polyomino seront repr�sent� par
															// une liste cha�n�e de Edges. Cependant, il va nous falloir
															// ne garder que les Edges des ColoredPolygon qui seront
															// effectivement sur le c�t�

/////////////////////////////////////////////////////  CONSTRUCTEURS  ////////////////////////////////////////////////////////////////////////////////////////////////

	public Polyomino(String ligne, Color color) { // constructeur � partir d'une ligne
													// ATTENTION : Les couples ne CORRESPONDENT PAS aux pixels effectifs
													// de l'�cran, ce sont
													// des coordonn�es normalis�es, et ce n'est qu'apr�s "dilatation" du
													// facteur "taille"
													// qu'on cr�e effectivement les polygon composant le polyomino
		this.data = new LinkedList<ColoredPolygon>();
		String[] couples = ligne.split(", ");
		for (String couple : couples) {
			String[] Couple = (couple.replace("(", "").replace(")", "").replace("[", "").replace("]", "")).split(",");

			ColoredPolygon CP = new ColoredPolygon(Couple, color);
			this.data.add(CP);

			for (Edge edge : CP.e) {
				this.edges.add(edge);
			}
		}
		this.color = color;
		this.updateEdges();

	}

	public Polyomino(LinkedList<int[]> couples, Color color) {
		{ // constructeur � partir d'un tableau contenant des couples de coordoonn�es de
			// chacun des carr�s composant le Polyomino
			this.data = new LinkedList<ColoredPolygon>();
			for (int[] couple : couples) { // On parcourt chaque carr�
				String[] Couple = new String[2];
				Couple[0] = String.valueOf(couple[0]); // coordonn�e suivant x
				Couple[1] = String.valueOf(couple[1]); // coordonn�e suivant x
				ColoredPolygon CP = new ColoredPolygon(Couple, color);
				this.data.add(CP);

				for (Edge edge : CP.e) {
					this.edges.add(edge);
				}
			}
			this.color = color;
			this.updateEdges();
		}
	}

	public String ecrit() { // On va utiliser cette m�thode pour copier un Polyomino : on le traduit en
							// String, puis on cr�e un nouveau Polyomino
		String s = "["; // correspondant � ce String. ATTENTION, il est possible que cette m�thode ne
						// marche pas apr�s des rotations ou autres,
						// car on ne consid�re que la coordonn�e initale de CP. A v�rifier.

		// ATTENTION : Les couples de sortie ne CORRESPONDENT PAS aux pixels effectifs
		// de l'�cran, ce sont
		// des coordonn�es normalis�es apr�s "dilatation" du facteur "1/taille"
		for (ColoredPolygon CP : this.data) {
			int x0 = (int) CP.polygon.xpoints[0] / ColoredPolygon.taille;
			int y0 = (int) CP.polygon.ypoints[0] / ColoredPolygon.taille;
			s += "(" + x0 + "," + y0 + "), ";
		}
		s = s.substring(0, s.length() - 2);
		return s + "]";
	}

/////////////////////////////////////////////////////  FONCTIONS UTILES  ////////////////////////////////////////////////////////////////////////////////////////////////

	public void updateEdges() { // Cette fonction cr�e la liste edges des bords du polyomino, en supprimant tous
								// les edges des ColoredPolygon qui sont "� l'int�rieur" du polyomino.
								// Pour cela, on supprime tous les Edges en doublons, de cette mani�re il ne
								// restera que les Edges pr�sents une seule fois, ce sont les bords du
								// polyomino.

		this.edges = new LinkedList<Edge>(); // ATTENTION ! Il faut appeler updateEdges() apr�s chaque transformation
		// (rotation, reflexion...) du polyomino car sinon les bords ne seront pas
		// modifi�s. Cette
		// ligne clear() permet d'effacer la liste edges apr�s une modification, pour
		// pouvoir impl�menter les nouveaux bords sans garder les anciens

		for (ColoredPolygon CP : this.data) {
			CP.update();

			for (Edge e : CP.e) {
				if (this.edges.contains(e)) {
					this.edges.remove(e);
				} else
					this.edges.add(e);
			}
		}
	}

	public void visualize() {
		Image2d Image = new Image2d(150, 150);
		Image.addListe(this.data);
		new Image2dViewer(Image);
	}

	public void print() {
		for (ColoredPolygon CP : this.data)
			System.out.print("[" + CP.polygon.xpoints[0] / ColoredPolygon.taille + ","
					+ CP.polygon.ypoints[0] / ColoredPolygon.taille + "] ");

		System.out.println(" ");
	}

	public int Max_x() { // Renvoie la coordonn�e maximale du polyomino selon l'axe X (utile
							// entre autre pour les placer � bonne distance les uns des autres)
		LinkedList<Integer> rechMax = new LinkedList<Integer>();
		for (ColoredPolygon CP : this.data) {
			for (int i = 0; i < CP.polygon.npoints; i++) {
				rechMax.add(CP.polygon.xpoints[i]);
			}
		}
		return (int) Collections.max(rechMax);
	}

	public int Max_y() { // Pareil mais selon l'axe Y
		LinkedList<Integer> rechMax = new LinkedList<Integer>();
		for (ColoredPolygon CP : this.data) {
			for (int i = 0; i < CP.polygon.npoints; i++) {
				rechMax.add(CP.polygon.ypoints[i]);
			}
		}
		return (int) Collections.max(rechMax);
	}

	public int Min_x() { // Renvoie la coordonn�e minimale du polyomino selon l'axe X (utile
							// uniquement pour les placer � bonne distance les uns des autres dans
							// allFixedPolyominos)
		LinkedList<Integer> rechMin = new LinkedList<Integer>();
		for (ColoredPolygon CP : this.data) {
			for (int i = 0; i < CP.polygon.npoints; i++) {
				rechMin.add(CP.polygon.xpoints[i]);
			}
		}
		return (int) Collections.min(rechMin);
	}

	public int Min_y() { // Pareil mais selon l'axe Y
		LinkedList<Integer> rechMin = new LinkedList<Integer>();
		for (ColoredPolygon CP : this.data) {
			for (int i = 0; i < CP.polygon.npoints; i++) {
				rechMin.add(CP.polygon.ypoints[i]);
			}
		}
		return (int) Collections.min(rechMin);
	}

	public boolean egaux(Polyomino P) { // Teste si deux polyominos sont �gaux (ie s'ils sont constitu�s des m�mes
										// carr�s unitaires).
		if (this.data.size() != P.data.size())
			return false; // Cette fonction devrait normalement s'appeler equals, mais on a besoin pour la
							// table de
		for (ColoredPolygon CP : this.data) { // hachage de allFixedPolyominos d'une relation d'�galit� qui consid�re
												// aussi les translations
			if (!P.data.contains(CP))
				return false;
		}

		return true;
	}

	public boolean egauxTranslation(Polyomino P) { // Teste si deux polyominos sont �gaux � une translation pr�s. Pour
													// cela, on les translate en
		int x = this.Max_x(); // consid�rant leurs Max_x et Max_y de fa�on � superposer leur carr� extr�mal.
								// De l�, on peut
		int y = this.Max_y(); // facilement les comparer avec la m�thode pr�c�dente. Il ne faut juste pas
								// oublier de re-translater
		int px = P.Max_x(); // le polyomino qu'on a d�plac�
		int py = P.Max_y();

		P.translation(x - px, y - py);

		if (this.egaux(P)) {
			P.translation(px - x, py - y);
			return true;
		}
		P.translation(px - x, py - y);
		return false;
	}

	public boolean egauxTransRot(Polyomino P) {
		int x = this.Max_x(); // consid�rant leurs Max_x et Max_y de fa�on � superposer leur carr� extr�mal.
		// De l�, on peut
		int y = this.Max_y(); // facilement les comparer avec la m�thode pr�c�dente. Il ne faut juste pas
		// oublier de re-translater

		for (int i = 0; i < 4; i++) {
			P.rotation(); // l'astuce ici est de tourner puis APRES seulement d'effectuer l'action de
							// egauxtranslation
			int px = P.Max_x();
			int py = P.Max_y();
			P.translation(x - px, y - py);
			if (this.egaux(P)) { // ces trois lignes servent � assurer que apr�s avoir tester l'une des 3
				P.translation(px - x, py - y); // rotations du polyominoe, on le remette bien � sa place originelle

				for (int j = 3 - i; j > 0; j--) {
					P.rotation();
				}
				return true;
			}
		}

		return false;

	}

	public boolean equals(Object o) {

		Polyomino P = (Polyomino) o;

		return this.egauxTransRot(P);
	}

	public int hashCode() { // N�cessaire pour impl�menter la table de hachage de allFixedPolyominos
		int x = this.Max_x();
		int y = this.Max_y();
		this.translation(-x, -y); // Pour que deux Polyominos �gaux PAR TRANSLATION (!!!) aient le m�me hashCode,
									// on les ram�ne � l'origine

		int somme = 0; // pour pouvoir utiliser la m�thode ColoredPolygon.hashCode
		for (int i = 0; i < 4; i++) {
			this.rotation();
			this.translation(-this.Max_x(), -this.Max_y());// on fait faire les 3 rotations possibles au Polyomino

			for (ColoredPolygon CP : this.data) {
				somme += CP.hashCode(); // calcul quelconque, je l'ai pris au pif
			}
			this.translation(this.Max_x(), this.Max_y());// on fait faire les 3 rotations possibles au Polyomino

		}
		this.translation(x, y);
		return somme;
	}

	public boolean contains(int[] l2) {
		LinkedList<int[]> L = this.toList();
		for (int[] l1 : L) {
			if (l1[0] == l2[0] && l1[1] == l2[1]) {
				return true;
			}
		}
		return false;
	}

/////////////////////////////////////////////////////  FONCTIONS DE TRANSFORMATIONS  ////////////////////////////////////////////////////////////////////////////////////////////////

	public void translation(int x, int y) {
		for (ColoredPolygon CP : this.data) {
			CP.translation(x, y);
		}
		this.updateEdges();
	}

	public void reflexionVert() {
		LinkedList<Integer> rechMilieu = new LinkedList<Integer>();
		for (ColoredPolygon CP : this.data) {
			for (int i = 0; i < CP.polygon.npoints; i++) {
				rechMilieu.add(CP.polygon.ypoints[i]);
			}
		}
		int milieu = (int) (Collections.max(rechMilieu) + Collections.min(rechMilieu)) / 2;

		for (ColoredPolygon CP : this.data) {
			for (int i = 0; i < CP.polygon.npoints; i++) {
				CP.polygon.ypoints[i] = 2 * milieu - CP.polygon.ypoints[i];
			}
		}
		this.updateEdges();
	}

	public void reflexionHori() {
		LinkedList<Integer> rechMilieu = new LinkedList<Integer>();
		for (ColoredPolygon CP : this.data) {
			for (int i = 0; i < CP.polygon.npoints; i++) {
				rechMilieu.add(CP.polygon.xpoints[i]);
			}
		}
		int milieu = (int) (Collections.max(rechMilieu) + Collections.min(rechMilieu)) / 2;

		for (ColoredPolygon CP : this.data) {
			for (int i = 0; i < CP.polygon.npoints; i++) {
				CP.polygon.xpoints[i] = 2 * milieu - CP.polygon.xpoints[i];
			}
		}
		this.updateEdges();

	}

	public void rotation() { // Fait subir au polyomino un quart de tour dans le sens trigo, � partir du
								// point inital d'un carr� (un ColoredPolygon) quelconque le composant
		int xrot = this.data.getFirst().polygon.xpoints[0];
		int yrot = this.data.getFirst().polygon.ypoints[0];
		// this.translation(-xrot, -yrot); // On commence par ramener le centre de
		// rotation du polyomino � l'origine, de
		// cette mani�re la rotation sera plus pratique � �crire

		for (ColoredPolygon CP : this.data) {
			int xi = CP.polygon.xpoints[0];
			int yi = CP.polygon.ypoints[0];
			int x = xrot + yi - yrot;
			int y = yrot - xi + xrot;
			CP.translation(x - xi, y - yi);

		}
		this.updateEdges();
		// this.translation(xrot, yrot); // On commence par ramener le centre de
		// rotation du polyomino � l'origine, de
		this.Origine(); // On ram�ne le polyomino � l'origine

	}

	public void Origine() {
		int xorigine = this.Min_x();
		int yorigine = this.Min_y();
		this.translation(-xorigine, -yorigine);
	}

	public void dilatation(int coeff) { // On dilate le polyomino d'un coefficient entier
		int xorigine = this.data.getFirst().polygon.xpoints[0];
		int yorigine = this.data.getFirst().polygon.ypoints[0];
		this.translation(-xorigine, -yorigine); // Pour simplifier les calculs, on commence par ramener le point de
												// r�f�rence � l'origine

		for (ColoredPolygon CP : this.data) {
			for (int i = 0; i < CP.polygon.npoints; i++) {
				CP.polygon.xpoints[i] = coeff * CP.polygon.xpoints[i];
				CP.polygon.ypoints[i] = coeff * CP.polygon.ypoints[i];
			}
		}
		this.translation(coeff * xorigine, coeff * yorigine);
		this.updateEdges();
	}

//////////////////////////////////////////////////////  LECTURE D'UN FICHIER .txt  ////////////////////////////////////////////////////////////////////////////////////////////////////	

	public static java.util.List<String> readFile(String filename) { // r�cup�ration sous la forme d'une list de String
																		// d'un fichier .txt
		java.util.List<String> records = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				records.add(line);
			}
			reader.close();
			return records;
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
			return null;
		}
	}

	public LinkedList<int[]> toList() {
		LinkedList<int[]> data = new LinkedList<int[]>();
		String[] texte = this.ecrit().replace("(", "").replace(")", "").replace("[", "").replace("]", "").split(", ");
		for (String Coordonnee : texte) {
			int[] couple = new int[2];
			couple[0] = Integer.parseInt(Coordonnee.split(",")[0]);
			couple[1] = Integer.parseInt(Coordonnee.split(",")[1]);
			data.addLast(couple);
		}
		return data;
	}
}
