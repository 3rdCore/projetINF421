import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class covering {
	public LinkedList<LinkedList<Polyomino>> Pavages;
	public LinkedList<Polyomino> Collection; // Linked list de Polyominoe sous forme de Tableau sur (x,y) : la
												// coordonnée
	// (i,j) indique la présence d'un carré du Polyominoe aux coordonnées (i,j).
	public int[][] map;
	public int x_max;
	public int y_max;
	public final Color color = new Color(150, 150, 150);

/////////////////////////////////////////////////////  CONSTRUCTEURS  ////////////////////////////////////////////////////////////////////////////////////////////////

	public covering(LinkedList<Polyomino> collection, int[][] map, int x_max, int y_max) { // Constructeur de base : Pas
																							// utlise
		super();
		this.Pavages = new LinkedList<LinkedList<Polyomino>>();
		Collection = collection;
		this.map = map;
		this.x_max = x_max;
		this.y_max = y_max;
	}

	public covering(int[][] map) { // On construit un objet uniquement à partir de la map sur laquelle on veut
									// paver
		this.Pavages = new LinkedList<LinkedList<Polyomino>>(); // Il faut donc dans un 2nd temps ajouter des pavés dans
																// sa collection.
		Collection = new LinkedList<Polyomino>();
		this.map = map;
		this.x_max = map[0].length;
		this.y_max = map.length;
	}

	public covering(String input, int x_max, int y_max) { // Cette fois ci, la map est donnée sous forme de String (
															// voir testTask4 pour le formalisme )
		// On prend un rectangle de taille (x_max*y_max) qui contient la map ( qui n'est pas forcément rectangulaire )
		// de cette facon on peut paver n'importe quelle figure géométrique ( et pas juste des rectangle )
		
		String[] Couples = (input.replace("(", "").replace(")", "").replace("[", "").replace("]", "")).split(", ");
		int[][] map = new int[y_max][x_max];
		for (int i = 0; i < y_max; i++) {
			for (int j = 0; j < x_max; j++) {
				map[i][j] = 0;				// On met initialement tout les points du rectangle (x_max*y_max) à 0
			}
		}
		for (String S : Couples) {
			map[Integer.parseInt(S.split(",")[1])][Integer.parseInt(S.split(",")[0])] = 1;   // Puis on passe à un tout les points du rectangle étant contenu dans la map
		}
		this.Pavages = new LinkedList<LinkedList<Polyomino>>();
		Collection = new LinkedList<Polyomino>();
		this.map = map;
		this.x_max = x_max;
		this.y_max = y_max;

	}

/////////////////////////////////////////////////////  FONCTIONS UTILES  ////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean appartientMAP(Polyomino P) {     //Test si le Polyomino appartient effectivement à la map
		// ie : test si pour chaque case du Polyomino correspond une case de la map contenant "1"
		for (ColoredPolygon CP : P.data) {
			if (CP.polygon.xpoints[0] < 0 || CP.polygon.xpoints[0] > this.x_max * ColoredPolygon.taille - 1				// On test ici si le Polyomino est bien dans le rectangle 
					|| CP.polygon.ypoints[0] < 0 || CP.polygon.ypoints[0] > this.y_max * ColoredPolygon.taille - 1) {
				return false;
			}
			if (!(map[CP.polygon.ypoints[0] / ColoredPolygon.taille][(CP.polygon.xpoints[0]			 //puis si pour chaque case du Polyomino correspond une case de la map contenant "1"
					/ ColoredPolygon.taille)] == 1)) {
				return false;
			}
		}
		return true;
	}

	public void addPoly(Polyomino P) { // on ajoute le polyominoe à la Collection si il est bien compris dans la map
										// sur laquelle on veut paver.
		if (this.appartientMAP(P)) {
			this.Collection.add(new Polyomino(P.toList(), color));
		}

	}

	public static boolean IsMapVide(int[][] map) { // Me permet de savoir si la map sur laquelle on doit caller des
													// Polyominoes est
		// vide. ( si elle ne contient aucun 1 )
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (map[i][j] == 1) {
					return false;
				}
			}
		}
		return true;
	}

	public int[] getIndice(int[][] map) { // fonction renvoyant les coordonnées du prochain carré de la map que l'on souhaite combler
		// cela peut être intéressant de modifier cette fonction juste pour voir l'effet sur la manière dont il progresse dans le pavage.
		for (int i = 0; i < this.x_max; i++) {
			for (int j = 0; j < this.y_max; j++) {
				if (map[i][j] == 1) {
					int[] couple = new int[2];
					couple[0] = j;
					couple[1] = i;
					return (couple);
				}

			}
		}
		return null;
	}
	// Une fois que l'on à la map et la Collection, cover() remplit la liste de tout les pavages possibles.
	public LinkedList<LinkedList<Polyomino>> cover() {
		return this.reccursifcover(this.Collection, this.map);
	}

	//brace yourself, la fonction est un beau bordel, si tu veux un peu mieux comprendre regarde l'énoncé
	
	public LinkedList<LinkedList<Polyomino>> reccursifcover(LinkedList<Polyomino> Collection, int[][] map) {
		LinkedList<LinkedList<Polyomino>> PAVAGES = new LinkedList<LinkedList<Polyomino>>();
		if (IsMapVide(map)) {
			return new LinkedList<LinkedList<Polyomino>>();
		}
		int[] x = this.getIndice(map);  //x est la case que l'on souhaite paver --> on va chercher tout les Polyomino de la collection qui pavent au moins cette case
		for (Polyomino LP : Collection) {  //On parcours la collection, et on selectionne un Polyomino LP positionné à une certaine place qui pave x.
			if (LP.contains(x)) {
				int[][] MAP = new int[map.length][]; 		// On copie map proprement
				for (int i = 0; i < map.length; i++) {
					MAP[i] = new int[map[i].length];
					for (int j = 0; j < map[i].length; j++) {
						MAP[i][j] = map[i][j];
					}
				}
				LinkedList<Polyomino> COLLECTION = new LinkedList<Polyomino>(); // on copie Collection proprement
				for (Polyomino config : Collection) {
					Polyomino CONFIG = new Polyomino(config.toList(), color);
					COLLECTION.add(CONFIG);
				}
				
				for (int[] couple : LP.toList()) {  //On parcours maintenant chaque carré composant le Polyomino LP choisi.
					MAP[couple[1]][couple[0]] = 0;	// On passe à 0 tout les cases de la map qui sont pavées par ce Polyomino
					for (Polyomino Poly : Collection) { 	 //On vire tout les polyominos de la collection qui ont une intersection nous nulle avec LP.
						if (Poly.contains(couple)) {
							for (int i = 0; i < COLLECTION.size(); ++i) {
								if (COLLECTION.get(i).egaux(Poly)) {
									COLLECTION.remove(i);

								}

							} 			// cela semble laborieux car il faut faire des copies d'objets dans tout les sens, je pense que y'a moyen de rassifier le code dans tout les sens 
						}				// y'a des updates qui trainent un peu partout y'a moyen de voir lesquels ont peut virer
					}

				}

				LinkedList<LinkedList<Polyomino>> Pavages = reccursifcover(COLLECTION, MAP);     //On appel réccursivement le pavages avec la nouvelle Collection s
				if (Pavages.size() == 0 && this.IsMapVide(MAP)) {								//à laquelle on a enlevé des élèments et avec la nouvelle map
					LinkedList<Polyomino> PAVAGE = new LinkedList<Polyomino>();	// ces 3 lignes servent au cas ou la liste des pavages renvoyés et vide.
					PAVAGE.add(LP);												// cela arrive lorsque la condition d'arret est atteinte, ie: lorsque aucune 
					PAVAGES.add(PAVAGE);										// pièce n'a encore été placé
				} else {
					for (LinkedList<Polyomino> P : Pavages) {
						P.add(LP);										//On ajoute alors LP au pavages trouvés réccursivement.
						PAVAGES.add(P);									// important de ne pas confondre PAVAGE et pavage car il y a une itération sur pavage.
					}
				}
			}
		}
		return PAVAGES;
	}

	public static void printmap(int[][] map) {					//permet d'affichier rapidement une map
		for (int[] ligne : map) {
			System.out.print("[ ");

			for (int i = 0; i < ligne.length; i++) {
				System.out.print(ligne[i] + " ");
			}
			System.out.println("]");
		}
	}
	//getCollection prend en entrée une famille de Polyomino qui sont non one-sided equivalement.
	
	public void getCollection(LinkedList<Polyomino> listeP) {				//permet d'optenir la collection associé à une famille de Polyomino donnée
		this.Collection = new LinkedList<Polyomino>();						//En gros, la fonction prend un polyomino, crée ses 3 rotations associées puis
		for (Polyomino P : listeP) {										//balaye tout la surface de la map et regarde à quels endroits il est placable.
			Polyomino P1 = new Polyomino(P.ecrit(), P.color);				//Toute pièce placable est ajoutée à la collection avec sa position de "placabilité". 
			Polyomino P2 = new Polyomino(P1.ecrit(), P.color);				
			P2.rotation();
			Polyomino P3 = new Polyomino(P2.ecrit(), P.color);
			P3.rotation();
			Polyomino P4 = new Polyomino(P3.ecrit(), P.color);
			P4.rotation();
			LinkedList<Polyomino> Ps = new LinkedList<Polyomino>();
			Ps.add(P1);
			Ps.add(P2);
			Ps.add(P3);
			Ps.add(P4);
			if (P1.egaux(P2))			
				Ps.remove(P2);			// c'est un beau bordel y'a peut être moyen de nettoyer tout ca.
			if (P1.egaux(P3))				
				Ps.remove(P3);
			if (P1.egaux(P4))
				Ps.remove(P4);
			if (P2.egaux(P3))
				Ps.remove(P3);
			if (P2.egaux(P4))
				Ps.remove(P4);
			if (P3.egaux(P4))
				Ps.remove(P4);
			for (Polyomino Pol : Ps) {
				int X = Pol.Max_x();
				int Y = Pol.Max_y();
				for (int i = 0; i <= this.x_max - X / ColoredPolygon.taille; i++) {
					for (int j = 0; j <= this.y_max - Y / ColoredPolygon.taille; j++) {
						this.addPoly(Pol);
						Pol.translation(0, ColoredPolygon.taille);
					}
					Pol.translation(ColoredPolygon.taille, Y - Pol.Max_y());
				}
			}

		}
	}
}
