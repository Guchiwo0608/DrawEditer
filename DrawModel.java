import javax.swing.*;
import java.awt.*;
import java.util.*;

class DrawModel extends Observable {
  protected ArrayList<Figure> fig;
  protected Figure drawingFigure;
  protected Color currentColor;
  protected int currentFigure;
  public DrawModel() {
    fig = new ArrayList<Figure>();
    drawingFigure = null;
    currentColor = JColorChooser.showDialog(null, "JColorChooser", Color.WHITE);
    currentFigure = 1;
  }
  public ArrayList<Figure> getFigures() {
    return fig;
  }
  public Figure getFigure(int idx) {
    return fig.get(idx);
  }
  public void setFigure(int i){
    currentFigure = i;
  }
  public void createFigure(int x, int y) {
    Figure f = new RectangleFigure(x, y, 0, 0, currentColor);
    if(currentFigure == 1){
      f = new RectangleFigure(x, y, 0, 0, currentColor);
    }else{
      if(currentFigure == 2){
        f = new CircleFigure(x, y, 0, 0, currentColor);
      }else{
        f = new LineFigure(x, y, x, y, currentColor);
      }
    }
    fig.add(f);
    drawingFigure = f;
    setChanged();
    notifyObservers();
  }
  public void reshapeFigure(int x1, int y1, int x2, int y2) {
    if (drawingFigure != null) {
      drawingFigure.reshape(x1, y1, x2, y2);
      setChanged();
      notifyObservers();
    }
  }
}