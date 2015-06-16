import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DescUI extends UI {
   
   Object selected;
   JTextArea textArea;
   
   public DescUI (Object selected) {
      this.selected = selected;
      renderAll();
   }
   
   public void renderAll () {
      window = new JFrame ("Bloodlines: Object" + selected.name + "'s Description");
      window.setLayout(new GridBagLayout());
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      GridBagConstraints rules = new GridBagConstraints();
      textArea = new JTextArea(selected.description, 5, 27);
      textArea.setEditable(false);
      rules.gridx = 0;
      rules.gridy = 0;
      rules.gridwidth = 2;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(textArea, rules);
      rules.gridwidth = 1;
      makeButton("Edit", "Edit", 0, 1, 0, rules);
      makeButton("Exit", "Exit", 1, 1, 0, rules);
      window.pack();
      window.setVisible(true);
   }
   
   public void actionPerformed (ActionEvent clicked) {
      String command = clicked.getActionCommand();
      window.setVisible(false);
      window.dispose();
      if (command.equals("Edit")) {
         new InputUI (selected);
      } else if (command.equals("Exit")) {
         new ObjUI (selected);
      }
   }
}