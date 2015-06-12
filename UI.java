import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public abstract class UI implements ActionListener {
   
   JFrame window;
   
   abstract void renderAll ();
   
   public void makeButton (String name, String command, int column, int row, int height, GridBagConstraints rules) {
      JButton newButton = new JButton (name);
      newButton.setActionCommand (command);
      newButton.addActionListener(this);
      rules.gridx = column;
      rules.gridy = row;
      rules.ipady = height;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(newButton, rules);
   }
}