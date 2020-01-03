import java.awt.*;
import java.util.LinkedList;

public class ColoredPolygon { // Un carré, une unité composant les polyominos

	public Polygon polygon;
	public Color color;
	public final static int taille = 20; // La taille d'un carré, la longueur de son côté en pixels
	public final static int width = 5; // L'épaisseur des Edges
	public LinkedList<Edge> e; // Les bords de chaque carré seront représentés par une liste chaînée de Edge

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

	public ColoredPolygon(String[] Couple, Color color) { // Ici ColoredPolygon représente un petit carré à quatre coté
															// relatif à un seul
															// couple [*,*] donné en entrée
															// ATTENTION : Le couple ne CORRESPOND PAS aux pixels
															// effectifs de l'écran, ce sont
															// des coordonnées normalisées, et ce n'est qu'après
															// "dilatation" du facteur "taille"
		// qu'on crée effectivement le polygon
		int[] xcoords = new int[4];
		int[] ycoords = new int[4];
		// On explicite les 4 points du carré :
		xcoords[0] = taille * Integer.parseInt(Couple[0]);
		ycoords[0] = taille * Integer.parseInt(Couple[1]);
		xcoords[1] = taille * Integer.parseInt(Couple[0]);
		ycoords[1] = taille * Integer.parseInt(Couple[1]) + taille;
		xcoords[2] = taille * Integer.parseInt(Couple[0]) + taille;
		ycoords[2] = taille * Integer.parseInt(Couple[1]) + taille;
		xcoords[3] = taille * Integer.parseInt(Couple[0]) + taille;
		ycoords[3] = taille * Integer.parseInt(Couple[1]);
		// On crée les Edges
		this.e = new LinkedList<Edge>();
		this.e.add(new Edge(xcoords[0], ycoords[0], xcoords[1], ycoords[1], width));
		this.e.add(new Edge(xcoords[1], ycoords[1], xcoords[2], ycoords[2], width));
		this.e.add(new Edge(xcoords[2], ycoords[2], xcoords[3], ycoords[3], width));
		this.e.add(new Edge(xcoords[3], ycoords[3], xcoords[0], ycoords[0], width));


		this.polygon = new Polygon(xcoords, ycoords, xcoords.length);
		this.color = color;
	}

//////////////////////////////////////////////////////   FONCTIONS UTILES  ////////////////////////////////////////////////////////////////////////////////////////////////////	

	public void update() { // permet de mettre à jour la LinkedList de ses Edges.
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

	public static int max(int[] coords) { // Méthode nécessaire pour la méthode equals ci-dessous, renvoie le max d'une
											// liste d'entiers
		int m = coords[0];
		for (int i = 1; i < coords.length; i++) {
			if (coords[i] > m)
				m = coords[i];
		}
		return m;
	}

	public boolean equals(Object o) { // Nécessaire pour utiliser la méthode contains() de LinkedList (utilisée dans
										// allFixedPolyominos)
										// J'ai tenté au début d'utiliser la méthode equals de la classe Polygon, mais
										// ça ne marchait pas : elle
										// ne comparait pas les coordonnées des carrés.
										// Puisque les carrés ont la même taille, il suffit de comparer leur coordonnée
										// maximale.
		ColoredPolygon CP = (ColoredPolygon) o;
		return max(this.polygon.xpoints) == max(CP.polygon.xpoints)
				&& max(this.polygon.ypoints) == max(CP.polygon.ypoints);
	}

	public int hashCode() { // Apparemment, dès qu'on crée une méthode equals, on doit aussi faire une
							// méthode hashCode...
							// De toute façon je m'en sers dans la méthode hashCode de la classe Polyomino
		return max(this.polygon.xpoints) * 9 + max(this.polygon.ypoints) * 17; // calcul quelconque, je l'ai pris au
																				// pif. Si 2 ColoredPolygon sont égaux
																				// (au sens
																				// de equals) alors ils ont le même
																				// hashCode
	}

	public ColoredPolygon VoisinHaut() { // Crée un nouveau ColoredPolygon, le voisin du haut de this
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

	public ColoredPolygon changerCouleur(Color c) { // Il est plus pratique pour les tests de créer une fonction
													// renvoyant un objet,
		this.color = c; // plutôt qu'une fonction se contentant de le modifier
		return this;
	}

}