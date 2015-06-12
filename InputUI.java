import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class InputUI extends UI {
   
   Object selected;
   String version;
   
   public InputUI () {
      this.selected = selected;
      window = new JFrame ("Bloodlines");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      renderAll();
      version = "Set";
   }
   
   public InputUI (Object selected) {
      this.selected = selected;
      window = new JFrame ("Bloodlines");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      renderAll();
      version = "Object";
   }
   
   public InputUI (Object selected, String description) {
      this.selected = selected;
      window = new JFrame ("Bloodlines");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      renderAll();
      version = "Description";
   }
   
   public void renderAll () {
      GridBagConstraints rules = new GridBagConstraints();
      JTextArea textArea = new JTextArea("Please Enter a Legal String", 1, 27);
      textArea.setEditable(false);
      rules.gridx = 0;
      rules.gridy = 0;
      rules.gridwidth = 1;
      rules.ipady = 40;
      rules.fill = GridBagConstraints.BOTH;
      textField = new JTextField(27);
      textField.addActionListener(this);
      rules.gridy = 0;
      add(textField, rules);
   }
   
   public void actionPerformed (ActionEvent entered) {
      String text = textField.getText();
      if (version.equals("Set")) {
         
      } else if (version.equals("Object")) {
         
      } else if (version.equals("Description")) {
         
      }
   }
}