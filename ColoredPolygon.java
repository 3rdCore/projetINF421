Import Image2d;
import java.io.*;

public class ColoredPolygon {
  private int[] xcoords;
  private int[] ycoords;
  private String color;

//Several constructors are implemented.

  public ColoredPolygon(int[] xcoords, int[] ycoords, String color){
    this.xcoords=xcoords;
    this.ycoords=ycoords;
    this.color=color;
  }

  public ColoredPolygon(String input,String color){
    String[] couples =input.split(",");
    int[]xcoords=new int[couples.size()];
    int[]ycoords=new int[couples.size()];
    int i=0;
    for (String couple: couples) {
      couple=couple.replace('(', '').replace(')','').split(",");
      xcoords[i]=couple[0];
      ycoords[i]=couple[1];
    }
    this.xcoords=xcoords;
    this.ycoords=ycoords;
    this.color=color;
  }



}
