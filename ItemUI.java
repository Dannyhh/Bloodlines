import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class ItemUI extends UI {
   
   Item selected;
   
   public ItemUI (Item selected) {
      this.selected = selected;
      window = new JFrame ("Bloodlines: " + selected.name);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setLayout(new GridBagLayout());
      renderAll();
      window.pack();
      window.setVisible(true);
   }
   
   public void renderAll () {
      int width = 4;
      while ((selected.supers.size() + 1 > width)
          || (selected.subs.size() + 1 > width)
          || (selected.links.size() + 4 > width)) {
         width++;
      }
      GridBagConstraints rules = new GridBagConstraints();
      // Name of Selected
      JLabel selectedLabel = new JLabel (selected.name);
      rules.gridx = width / 2 - 2 + (width % 2);
      rules.gridy = 3;
      rules.gridwidth = 3;
      rules.ipady = 40;
      rules.fill = GridBagConstraints.BOTH;
      selectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
      window.getContentPane().add(selectedLabel, rules);
      rules.gridwidth = 1;
      // Open Description
      super.makeButton ("Open Description", "Open Description", (width / 2) - 2  + (width % 2), 4, 0, rules);
      // Deletes Selected
      super.makeButton ("Delete", "Delete", (width / 2) - 1  + (width % 2), 4, 0, rules);
      // Exit Selected Set
      super.makeButton ("Exit Set", "Exit", (width / 2)  + (width % 2), 4, 0, rules);
      // Supers
      int count = 0;
      for (String superItem : selected.supers) {
         makeButton (superItem, superItem, count, 0, 20, rules);
         // Delete connections
         makeButton ("Delete", "Remove " + superItem, count, 1, 0, rules);
         count++;
      }
      // Adds supers
      super.makeButton ("Add Upward Connection", "Add Super", width - 1, 0, 20, rules);
      // Subs
      count = 0;
      for (String subItem : selected.subs) {
         super.makeButton (subItem, subItem, count, 6, 20, rules);
         // Delete connections
         super.makeButton ("Delete", "Remove " + subItem, count, 7, 0, rules);
         count++;
      }
      // Adds subs
      super.makeButton("Add Downward Connection", "Add Sub", width - 1, 6, 20, rules);
      // Links
      count = 0;
      for (String linkedItem : selected.links) {
         super.makeButton (linkedItem, linkedItem, ((count % 2) * (width - 2)) + ((1 + ((count % 2) * -2)) * (count / 2)), 3, 20, rules);
         // Delete connections
         super.makeButton("Delete", "Remove " + linkedItem, ((count % 2) * (width - 2)) + ((1 + ((count % 2) * -2)) * (count / 2)), 4, 0, rules);
         count++;
      }
      // Adds links
      super.makeButton("Add Link", "Add Link", width - 1, 3, 20, rules);
   }
   
   public void actionPerformed (ActionEvent clicked) {
      window.setVisible(false);
      window.dispose();
      String command = clicked.getActionCommand();
      if (command.equals("Exit")) {
         new SetUI ();
      } else if (command.equals("Open Description")) {
         new DescUI (selected);
      } else if (command.equals("Delete")) {
         try {
         new ItemUI (new Item((new Scanner(new File("data\\" + selected.set + "\\" + selected.set + ".txt"))).next(), selected.set));
         } catch (Exception e) {
         }
         selected.deleteSelf();
      } else if (selected.supers.contains(command) || selected.subs.contains(command) || selected.links.contains(command)) {
         new ItemUI (new Item (command, selected.set));
      } else if (command.substring(0, 6).equals("Remove")) {
         command = command.substring(7, command.length());
         if (selected.supers.contains(command)) {
            selected.deleteConnection(command, selected.supers, true);
         } else if (selected.subs.contains(command)) {
            selected.deleteConnection(command, selected.subs, true);
         } else if (selected.links.contains(command)) {
            selected.deleteConnection(command, selected.links, true);
         }
         new ItemUI (selected);
      } else if (command.substring(0,3).equals("Add")) {
         command = command.substring(4, command.length());
         new InputUI (selected, command);
      }
   }
}