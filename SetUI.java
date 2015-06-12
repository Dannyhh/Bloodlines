import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class SetUI extends UI {
   
   public SetUI () {
      window = new JFrame ("Bloodlines: Sets");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setLayout(new GridBagLayout());
      renderAll();
      window.pack();
      window.setVisible(true);
   }
   
   public void renderAll () {
      try {
         Scanner setsFinder = new Scanner (new File("data\\Sets.txt"));
         ArrayList<String> sets = new ArrayList<String>();
         while (setsFinder.hasNextLine()) {
            sets.add(setsFinder.nextLine());
         }
         GridBagConstraints rules = new GridBagConstraints();
         int count = 0;
         for (String set : sets) {
            super.makeButton (set, set, count, 0, 20, rules);
            count++;
         }
      } catch (Exception e) {
      }
   }
   
   /*private void makeButton (String name, String command, int column, int row, int height, GridBagConstraints rules) {
      JButton newButton = new JButton (name);
      newButton.setActionCommand (command);
      newButton.addActionListener(this);
      rules.gridx = column;
      rules.gridy = row;
      rules.ipady = height;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(newButton, rules);
   }*/
   
   public void actionPerformed (ActionEvent clicked) {
      try {
         String command = clicked.getActionCommand();
         Scanner defaultFile = new Scanner(new File("data\\" + command + "\\" + command + ".txt"));
         new ObjUI (new Object (defaultFile.next(), command));
      } catch (Exception e) {
      }
   }
}