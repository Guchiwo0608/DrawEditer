import java.awt.*;

class LineFigure extends Figure {
  public LineFigure(int x, int y, int w, int h, Color c){
    super(x, y, w, h, c);
  }
  public void draw(Graphics g){
    g.setColor(color);
    g.drawLine(x, y, x + width, y + height);
  }
}
