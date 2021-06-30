import java.awt.*;

class RectangleFigure extends Figure {
	public RectangleFigure(int x, int y, int w, int h, Color c) {
	  super(x, y, w, h, c);
	}
	public void draw(Graphics g) {
	  g.setColor(color);
	  g.drawRect(x, y, width, height);
	}
      }