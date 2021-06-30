import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

class DrawFrame extends JFrame implements ActionListener{
  DrawModel model;
  ViewPanel view1, view2, view3, view4;
  DrawController cont;
  JComboBox<String> comboBox = new JComboBox<>();
  public DrawFrame() {
    comboBox.addItem("四角形");
    comboBox.addItem("楕円形");
    comboBox.addItem("直線");
    comboBox.addActionListener(this);
    this.add(comboBox, BorderLayout.NORTH);
    model = new DrawModel();
    cont = new DrawController(model);
    view1 = new ViewPanel(model,cont);
    this.setBackground(Color.black);
    this.setTitle("Draw Editor");
    this.setSize(1000, 1000);
    this.add(view1);
    view1.setBorder(new LineBorder(Color.blue, 3));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }
  public static void main(String[] args) {
    new DrawFrame();
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    if(comboBox.getSelectedItem().equals("四角形")){
      model.setFigure(1);
    }else{
      if(comboBox.getSelectedItem().equals("楕円形")){
        model.setFigure(2);
      }else{
        model.setFigure(3);
      }
    }
  }
}
