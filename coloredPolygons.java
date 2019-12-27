Import Image2d;
import java.io.*;

public class input {
  private int[] xcoords;
  private int[] ycoords;
  private String color;


  public ColoredPolygon(xcoords, ycoords, color){
    this.xcoords=xcoords;
    this.ycoords=ycoords;
    this.color=color;
  }

  public ColoredPolygon(String input,color){
    String[] couples =input.split(",");

    int[]xcoords=new int[couples.size()];
    int[]ycoords=new int[];

    for (String couple: couples) {
      couple=couple.replace('(', '').replace(')','').split(",");
      xcoords.
    }
  }

  public ColoredPolygon(String filename,color){
    List<String> data=readFile(filename);

  }


  private List<String> readFile(String filename)  #récupération sous la forme d'une list de String d'un fichier .txt
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
