
import java.io.*;


public class coloredPolygons {
  private java.util.List<ColoredPolygon> data;
  private String color;

  public ColoredPolygons(String filename,String color){        //constructeur à partir d'un .txt
      List<String> F = readFile(filename);
      for (String ligne:F){
        this.data.add(new coloredPolygon(ligne,color));

      }
      this.color=color;
}

  private List<String> readFile(String filename)  //récupération sous la forme d'une list de String d'un fichier .txt
  {
    List<String> records = new ArrayList<String>();
    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = reader.readLine()) != null)
      {
        records.add(line);
      }
      reader.close();
      return records;
    }
    catch (Exception e)
    {
      System.err.format("Exception occurred trying to read '%s'.", filename);
      e.printStackTrace();
      return null;
    }
  }
