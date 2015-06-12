import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class ObjUI extends UI {
   
   Object selected;
   
   public ObjUI (Object selected) {
      this.selected = selected;
      window = new JFrame ("Bloodlines: " + selected.name);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setLayout(new GridBagLayout());
      renderAll();
      window.pack();
      window.setVisible(true);
   }
   
   public void renderAll () {
      int width = 5;
      if (selected.supers.size() + 1 > width) {
         width = selected.supers.size() + 1;
      }
      if (selected.subs.size() + 1 > width) {
         width = selected.subs.size() + 1;
      }
      if (selected.links.size() + 4 >= width) {
         width = selected.links.size() + 4;
      }
      GridBagConstraints rules = new GridBagConstraints();
      // Name of Selected
      JLabel selectedLabel = new JLabel (selected.name);
      rules.gridx = width / 2 - 1;
      rules.gridy = 3;
      rules.gridwidth = 3;
      rules.ipady = 40;
      rules.fill = GridBagConstraints.BOTH;
      selectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
      window.getContentPane().add(selectedLabel, rules);
      rules.gridwidth = 1;
      // Open Description
      super.makeButton ("Open Description", "Open Description", (width / 2) - 1, 4, 0, rules);
      // Deletes Selected
      super.makeButton ("Delete", "Delete", (width / 2), 4, 0, rules);
      // Exit Selected Set
      super.makeButton ("Exit Set", "Exit Set", (width / 2) + 1, 4, 0, rules);
      // Supers
      int count = 0;
      for (String superObj : selected.supers) {
         makeButton (superObj, superObj, count, 0, 20, rules);
         // Delete connections
         makeButton ("Delete", "Remove " + superObj, count, 1, 0, rules);
         count++;
      }
      // Adds supers
      super.makeButton ("Add Upward Connection", "Add Upward Connection", width - 1, 0, 20, rules);
      // Subs
      count = 0;
      for (String subObj : selected.subs) {
         makeButton (subObj, subObj, count, 6, 20, rules);
         // Delete connections
         makeButton ("Delete", "Remove " + subObj, count, 7, 0, rules);
         count++;
      }
      // Adds subs
      super.makeButton ("Add Downward Connection", "Add Downward Connection", width - 1, 6, 20, rules);
      // Links
      count = 0;
      for (String linkedObj : selected.links) {
         super.makeButton (linkedObj, linkedObj, ((count % 2) * (width - 2)) + ((1 + ((count % 2) * -2)) * (count / 2)), 3, 20, rules);
         // Delete connections
         super.makeButton ("Delete", "Remove " + linkedObj, ((count % 2) * (width - 2)) + ((1 + ((count % 2) * -2)) * (count / 2)), 4, 0, rules);
         count++;
      }
      // Adds links
      super.makeButton ("Add Link", "Add Link", width - 1, 3, 20, rules);
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
      String command = clicked.getActionCommand();
      if (selected.supers.contains(command) || selected.subs.contains(command) || selected.links.contains(command)) {
         window.setVisible(false);
         window.dispose();
         new ObjUI (new Object (command, selected.set));
      } else if (command.contains("Remove")) {
         window.setVisible(false);
         window.dispose();
         command = command.substring(7, command.length());
         if (selected.supers.contains(command)) {
            selected.deleteSuper(command, true);
         } else if (selected.subs.contains(command)) {
            selected.deleteSub(command, true);
         } else if (selected.links.contains(command)) {
            selected.deleteLink(command, true);
         }
         new ObjUI (selected);
      } else if (command.contains("Exit")) {
         window.setVisible(false);
         window.dispose();
         new SetUI ();
      }
   }
}