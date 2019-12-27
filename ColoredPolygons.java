import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;


public class ColoredPolygons {
	public LinkedList<ColoredPolygon> data;
	public Color color;

	public ColoredPolygons(String filename, Color color) { // constructeur à partir d'un .txt
		java.util.List<String> F = readFile(filename);

		for (String ligne : F) {
			System.out.println(ligne);
			ColoredPolygon CP= new ColoredPolygon(ligne, color);
			System.out.println(CP);
			this.data.add(CP);


		}
		this.color = color;
	}
	public int lenght() {
		return data.size();
	}

	public static java.util.List<String> readFile(String filename) // récupération sous la forme d'une list de String d'un fichier
													// .txt ( copié d'Internet )
	{
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
}
