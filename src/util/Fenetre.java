package util;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//Les imports habituels
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
public class Fenetre extends JFrame {
  private JPanel container = new JPanel();
  private JTextField jtf = new JTextField("Valeur par défaut");
  private JLabel label = new JLabel("Un JTextField");

  public Fenetre(){
    this.setTitle("Animation");
    this.setSize(300, 300);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    container.setBackground(Color.white);
    container.setLayout(new BorderLayout());
    JPanel top = new JPanel();
    Font police = new Font("Arial", Font.BOLD, 14);
    jtf.setFont(police);
    jtf.setPreferredSize(new Dimension(150, 30));
    jtf.setForeground(Color.BLUE);
    top.add(label);
    top.add(jtf);
    container.add(top, BorderLayout.NORTH);
    this.setContentPane(container);
    this.setVisible(true);            
  }
}