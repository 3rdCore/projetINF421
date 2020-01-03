
public class Edge {

	public int x1, y1, x2, y2, width;

	public Edge(int x1, int y1, int x2, int y2, int width) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = width;
	}

	public boolean equals(Object o) { // Fonction nécessaire pour l'implémentation des bords du polyomino
		Edge e = (Edge) o;
		if ((this.x1 == e.x1 && this.x2 == e.x2 && this.y1 == e.y1 && this.y2 == e.y2)
				|| (this.x1 == e.x2 && this.x2 == e.x1 && this.y1 == e.y2 && this.y2 == e.y1)) {
			return true;
		}
		return false;
	}

}
