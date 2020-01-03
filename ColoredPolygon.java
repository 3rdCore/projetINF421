import java.awt.*;
import java.util.LinkedList;

public class ColoredPolygon { // Un carr�, une unit� composant les polyominos

	public Polygon polygon;
	public Color color;
	public final static int taille = 20; // La taille d'un carr�, la longueur de son c�t� en pixels
	public final static int width = 5; // L'�paisseur des Edges
	public LinkedList<Edge> e; // Les bords de chaque carr� seront repr�sent�s par une liste cha�n�e de Edge

/////////////////////////////////////////////////////  CONSTRUCTEURS  ////////////////////////////////////////////////////////////////////////////////////////////////

	public ColoredPolygon(int[] xcoords, int[] ycoords, Color color) {
		this.polygon = new Polygon(xcoords, ycoords, xcoords.length);
		this.color = color;
		this.e = new LinkedList<Edge>();
		this.e.add(new Edge(xcoords[0], ycoords[0], xcoords[1], ycoords[1], width));
		this.e.add(new Edge(xcoords[1], ycoords[1], xcoords[2], ycoords[2], width));
		this.e.add(new Edge(xcoords[2], ycoords[2], xcoords[3], ycoords[3], width));
		this.e.add(new Edge(xcoords[3], ycoords[3], xcoords[0], ycoords[0], width));	
	}

	public ColoredPolygon(String[] Couple, Color color) { // Ici ColoredPolygon repr�sente un petit carr� � quatre cot�
															// relatif � un seul
															// couple [*,*] donn� en entr�e
															// ATTENTION : Le couple ne CORRESPOND PAS aux pixels
															// effectifs de l'�cran, ce sont
															// des coordonn�es normalis�es, et ce n'est qu'apr�s
															// "dilatation" du facteur "taille"
		// qu'on cr�e effectivement le polygon
		int[] xcoords = new int[4];
		int[] ycoords = new int[4];
		// On explicite les 4 points du carr� :
		xcoords[0] = taille * Integer.parseInt(Couple[0]);
		ycoords[0] = taille * Integer.parseInt(Couple[1]);
		xcoords[1] = taille * Integer.parseInt(Couple[0]);
		ycoords[1] = taille * Integer.parseInt(Couple[1]) + taille;
		xcoords[2] = taille * Integer.parseInt(Couple[0]) + taille;
		ycoords[2] = taille * Integer.parseInt(Couple[1]) + taille;
		xcoords[3] = taille * Integer.parseInt(Couple[0]) + taille;
		ycoords[3] = taille * Integer.parseInt(Couple[1]);
		// On cr�e les Edges
		this.e = new LinkedList<Edge>();
		this.e.add(new Edge(xcoords[0], ycoords[0], xcoords[1], ycoords[1], width));
		this.e.add(new Edge(xcoords[1], ycoords[1], xcoords[2], ycoords[2], width));
		this.e.add(new Edge(xcoords[2], ycoords[2], xcoords[3], ycoords[3], width));
		this.e.add(new Edge(xcoords[3], ycoords[3], xcoords[0], ycoords[0], width));


		this.polygon = new Polygon(xcoords, ycoords, xcoords.length);
		this.color = color;
	}

//////////////////////////////////////////////////////   FONCTIONS UTILES  ////////////////////////////////////////////////////////////////////////////////////////////////////	

	public void update() { // permet de mettre � jour la LinkedList de ses Edges.
		int[] xcoords = this.polygon.xpoints;
		int[] ycoords = this.polygon.ypoints;
		this.e=new LinkedList<Edge>();
		this.e.add(new Edge(xcoords[0], ycoords[0], xcoords[1], ycoords[1], width));
		this.e.add(new Edge(xcoords[1], ycoords[1], xcoords[2], ycoords[2], width));
		this.e.add(new Edge(xcoords[2], ycoords[2], xcoords[3], ycoords[3], width));
		this.e.add(new Edge(xcoords[3], ycoords[3], xcoords[0], ycoords[0], width));

	}

	public void translation(int x, int y) {
		for (int i = 0; i < this.polygon.npoints; i++) {
			this.polygon.xpoints[i] += x;
			this.polygon.ypoints[i] += y;
		}
		this.update();
	}

	public static int max(int[] coords) { // M�thode n�cessaire pour la m�thode equals ci-dessous, renvoie le max d'une
											// liste d'entiers
		int m = coords[0];
		for (int i = 1; i < coords.length; i++) {
			if (coords[i] > m)
				m = coords[i];
		}
		return m;
	}

	public boolean equals(Object o) { // N�cessaire pour utiliser la m�thode contains() de LinkedList (utilis�e dans
										// allFixedPolyominos)
										// J'ai tent� au d�but d'utiliser la m�thode equals de la classe Polygon, mais
										// �a ne marchait pas : elle
										// ne comparait pas les coordonn�es des carr�s.
										// Puisque les carr�s ont la m�me taille, il suffit de comparer leur coordonn�e
										// maximale.
		ColoredPolygon CP = (ColoredPolygon) o;
		return max(this.polygon.xpoints) == max(CP.polygon.xpoints)
				&& max(this.polygon.ypoints) == max(CP.polygon.ypoints);
	}

	public int hashCode() { // Apparemment, d�s qu'on cr�e une m�thode equals, on doit aussi faire une
							// m�thode hashCode...
							// De toute fa�on je m'en sers dans la m�thode hashCode de la classe Polyomino
		return max(this.polygon.xpoints) * 9 + max(this.polygon.ypoints) * 17; // calcul quelconque, je l'ai pris au
																				// pif. Si 2 ColoredPolygon sont �gaux
																				// (au sens
																				// de equals) alors ils ont le m�me
																				// hashCode
	}

	public ColoredPolygon VoisinHaut() { // Cr�e un nouveau ColoredPolygon, le voisin du haut de this
		int x0 = this.polygon.xpoints[0];
		int y0 = this.polygon.ypoints[0];
		int[] xcoords = new int[4];
		int[] ycoords = new int[4];
		xcoords[0] = x0;
		ycoords[0] = y0 - taille;
		xcoords[1] = x0;
		ycoords[1] = y0;
		xcoords[2] = x0 + taille;
		ycoords[2] = y0;
		xcoords[3] = x0 + taille;
		ycoords[3] = y0 - taille;
		return new ColoredPolygon(xcoords, ycoords, this.color);
	}

	public ColoredPolygon VoisinGauche() {
		int x0 = this.polygon.xpoints[0];
		int y0 = this.polygon.ypoints[0];
		int[] xcoords = new int[4];
		int[] ycoords = new int[4];
		xcoords[0] = x0 - taille;
		ycoords[0] = y0;
		xcoords[1] = x0 - taille;
		ycoords[1] = y0 + taille;
		xcoords[2] = x0;
		ycoords[2] = y0 + taille;
		xcoords[3] = x0;
		ycoords[3] = y0;
		return new ColoredPolygon(xcoords, ycoords, this.color);
	}

	public ColoredPolygon VoisinBas() {
		int x0 = this.polygon.xpoints[0];
		int y0 = this.polygon.ypoints[0];
		int[] xcoords = new int[4];
		int[] ycoords = new int[4];
		xcoords[0] = x0;
		ycoords[0] = y0 + taille;
		xcoords[1] = x0;
		ycoords[1] = y0 + 2 * taille;
		xcoords[2] = x0 + taille;
		ycoords[2] = y0 + 2 * taille;
		xcoords[3] = x0 + taille;
		ycoords[3] = y0 + taille;
		return new ColoredPolygon(xcoords, ycoords, this.color);
	}

	public ColoredPolygon VoisinDroite() {
		int x0 = this.polygon.xpoints[0];
		int y0 = this.polygon.ypoints[0];
		int[] xcoords = new int[4];
		int[] ycoords = new int[4];
		xcoords[0] = x0 + taille;
		ycoords[0] = y0;
		xcoords[1] = x0 + taille;
		ycoords[1] = y0 + taille;
		xcoords[2] = x0 + 2 * taille;
		ycoords[2] = y0 + taille;
		xcoords[3] = x0 + 2 * taille;
		ycoords[3] = y0;
		return new ColoredPolygon(xcoords, ycoords, this.color);
	}

	public ColoredPolygon changerCouleur(Color c) { // Il est plus pratique pour les tests de cr�er une fonction
													// renvoyant un objet,
		this.color = c; // plut�t qu'une fonction se contentant de le modifier
		return this;
	}

}