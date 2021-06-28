import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


class Figure {
  protected int x, y, width, height;
  protected Color color;
  public Figure(int x, int y, int w, int h, Color c) {
    this.x = x; this.y = y; 
    width = w; height = h; 
    color = c;          
  }
  public void setSize(int w, int h) {
    width = w; height = h;
  }
  public void setLocation(int x, int y) {
    this.x = x; this.y = y;
  }
  public void reshape(int x1, int y1, int x2, int y2) {
    int newx = Math.min(x1, x2);
    int newy = Math.min(y1, y2);
    int neww = Math.abs(x1 - x2);
    int newh = Math.abs(y1 - y2);
    setLocation(newx, newy);
    setSize(neww, newh);
  }
  public void draw(Graphics g) {}
}

class RectangleFigure extends Figure {
  public RectangleFigure(int x, int y, int w, int h, Color c) {
    super(x, y, w, h, c);
  }
  public void draw(Graphics g) {
    g.setColor(color);
    g.drawRect(x, y, width, height);
  }
}

class CircleFigure extends Figure {
  public CircleFigure(int x, int y, int w, int h, Color c){
    super(x, y, w, h, c);
  }
  public void draw(Graphics g){
    g.setColor(color);
    g.drawOval(x, y, width, height);
  }
}

class LineFigure extends Figure {
  public LineFigure(int x, int y, int w, int h, Color c){
    super(x, y, w, h, c);
  }
  public void draw(Graphics g){
    g.setColor(color);
    g.drawLine(x, y, x + width, y + height);
  }
}

class DrawModel extends Observable {
  protected ArrayList<Figure> fig;
  protected Figure drawingFigure;
  protected Color currentColor;
  protected Figure currentFigure;
  public DrawModel() {
    fig = new ArrayList<Figure>();
    drawingFigure = null;
    currentColor = JColorChooser.showDialog(null, "JColorChooser", Color.WHITE);
    currentFigure = null;
  }
  public ArrayList<Figure> getFigures() {
    return fig;
  }
  public Figure getFigure(int idx) {
    return fig.get(idx);
  }
  public void createFigure(int x, int y) {
    currentFigure = new RectangleFigure(x, y, 0, 0, currentColor);
    Figure f = currentFigure;
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

class ViewPanel extends JPanel implements Observer {
  protected DrawModel model;
  public ViewPanel(DrawModel m, DrawController c) {
    this.setBackground(Color.white);
    this.addMouseListener(c);
    this.addMouseMotionListener(c);
    model = m;
    model.addObserver(this);
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    ArrayList<Figure> fig = model.getFigures();
    for(int i = 0; i < fig.size(); i++) {
      Figure f = fig.get(i);
      f.draw(g);
    }
  }
  public void update(Observable o, Object arg) {
    repaint();
  }
}

class DrawFrame extends JFrame {
  DrawModel model;
  ViewPanel view1, view2, view3, view4;
  DrawController cont;
  JComboBox<String> comboBox = new JComboBox<>();
  public DrawFrame() {
    comboBox.addItem("四角形");
    comboBox.addItem("楕円形");
    comboBox.addItem("直線");
    model = new DrawModel();
    cont = new DrawController(model);
    view1 = new ViewPanel(model,cont);
    view2 = new ViewPanel(model,cont);
    view3 = new ViewPanel(model,cont);
    view4 = new ViewPanel(model,cont);
    this.setBackground(Color.black);
    this.setTitle("Draw Editor");
    this.setSize(1000, 1000);
    this.setLayout(new GridLayout(2, 2));
    this.add(view1);
    this.add(view2);
    this.add(view3);
    this.add(view4);
    this.add(comboBox, BorderLayout.NORTH);
    view1.setBorder(new LineBorder(Color.blue, 3));
    view2.setBorder(new LineBorder(Color.blue, 3));
    view3.setBorder(new LineBorder(Color.blue, 3));
    view4.setBorder(new LineBorder(Color.blue, 3));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
  public static void main(String[] args) {
    new DrawFrame();
  }
}

class DrawController implements MouseListener, MouseMotionListener {
  protected DrawModel model;
  protected int dragStartX, dragStartY;
  public DrawController(DrawModel a) {
    model = a;
  }
  public void mouseClicked(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {
    dragStartX = e.getX(); dragStartY = e.getY();
    model.createFigure(dragStartX, dragStartY);
  }
  public void mouseDragged(MouseEvent e) {
    model.reshapeFigure(dragStartX, dragStartY, e.getX(), e.getY());
  }
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseMoved(MouseEvent e) {}
}