import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class ObjUI implements ActionListener {
   
   boolean open;
   Object selected;
   JFrame window;
   Object next;
   
   public ObjUI (Object selected) {
      open = true;
      next = null;
      this.selected = selected;
      window = new JFrame ();
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setLayout(new GridBagLayout());
      renderAll();
      window.pack();
      window.setVisible(true);
   }
   
   public Object waitForNext () {
      while (open) {
      }
      return next;
   }
   
   private void renderAll () {
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
      // Open Description
      JButton openDesc = new JButton ("Open Description");
      openDesc.setActionCommand ("Open Description");
      openDesc.addActionListener(this);
      rules.gridx = width / 2 - 1;
      rules.gridy = 4;
      rules.gridwidth = 1;
      rules.ipady = 0;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(openDesc, rules);
      // Deletes Selected
      JButton delete = new JButton ("Delete");
      delete.setActionCommand ("Delete");
      openDesc.addActionListener(this);
      rules.gridx = (width / 2);
      rules.gridy = 4;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(delete, rules);
      // Exit Selected Set
      JButton exit = new JButton ("Exit Set");
      exit.setActionCommand ("Exit Set");
      openDesc.addActionListener(this);
      rules.gridx = (width / 2) + 1;
      rules.gridy = 4;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(exit, rules);
      // Supers
      int count = 0;
      for (String superObj : selected.supers) {
         JButton newButton = new JButton (superObj);
         newButton.setActionCommand (superObj);
         newButton.addActionListener(this);
         rules.gridx = count;
         rules.gridy = 0;
         rules.ipady = 20;
         rules.fill = GridBagConstraints.BOTH;
         window.getContentPane().add(newButton, rules);
         // Delete connections
         makeButton ("Delete", "Delete " + superObj, count, 1, 0, rules);
         /*JButton newDelButton = new JButton ("Delete");
         newDelButton.setActionCommand ("Delete " + superObj);
         newDelButton.addActionListener(this);
         rules.gridx = count;
         rules.gridy = 1;
         rules.ipady = 0;
         rules.fill = GridBagConstraints.BOTH;
         window.getContentPane().add(newDelButton, rules);*/
         count++;
      }
      // Adds supers
      JButton addSuper = new JButton ("Add Upward Connection");
      addSuper.setActionCommand ("Add Upward Connection");
      openDesc.addActionListener(this);
      rules.gridx = width - 1;
      rules.gridy = 0;
      rules.ipady = 20;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(addSuper, rules);
      // Subs
      count = 0;
      for (String subObj : selected.subs) {
         JButton newButton = new JButton (subObj);
         newButton.setActionCommand (subObj);
         newButton.addActionListener(this);
         rules.gridx = count;
         rules.gridy = 6;
         rules.ipady = 20;
         rules.fill = GridBagConstraints.BOTH;
         window.getContentPane().add(newButton, rules);
         // Delete connections
         makeButton ("Delete", "Delete " + subObj, count, 7, 0, rules);
         /*JButton newDelButton = new JButton ("Delete");
         newDelButton.setActionCommand ("Delete " + subObj);
         newDelButton.addActionListener(this);
         rules.gridx = count;
         rules.gridy = 7;
         rules.ipady = 0;
         rules.fill = GridBagConstraints.BOTH;
         window.getContentPane().add(newDelButton, rules);*/
         count++;
      }
      // Adds subs
      makeButton ("Add Sub", "Add Sub", width - 1, 6, 20, rules);
      JButton addSub = new JButton ("Add Downward Connection");
      addSub.setActionCommand ("Add Downward Connection");
      openDesc.addActionListener(this);
      rules.gridx = width - 1;
      rules.gridy = 6;
      rules.ipady = 20;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(addSub, rules);
      // Links
      count = 0;
      for (String linkedObj : selected.links) {
         JButton newButton = new JButton (linkedObj);
         newButton.setActionCommand (linkedObj);
         newButton.addActionListener(this);
         rules.gridx = ((count % 2) * (width - 2)) + ((1 + ((count % 2) * -2)) * (count / 2));
         rules.gridy = 3;
         rules.ipady = 20;
         rules.fill = GridBagConstraints.BOTH;
         window.getContentPane().add(newButton, rules);
         // Delete connections
         makeButton ("Delete", "Delete " + linkedObj, ((count % 2) * (width - 2)) + ((1 + ((count % 2) * -2)) * (count / 2)), 4, 0, rules);
         /*JButton newDelButton = new JButton ("Delete");
         newDelButton.setActionCommand ("Delete " + linkedObj);
         newDelButton.addActionListener(this);
         rules.gridx = count;
         rules.gridy = 4;
         rules.ipady = 0;
         rules.fill = GridBagConstraints.BOTH;
         window.getContentPane().add(newDelButton, rules);*/
         count++;
      }
      // Adds links
      makeButton ("Add Link", "Add Link", width - 1, 3, 20, rules);
      /*JButton addLink = new JButton ("Add Link");
      addLink.setActionCommand ("Add Link");
      openDesc.addActionListener(this);
      rules.gridx = width - 1;
      rules.gridy = 3;
      rules.ipady = 20;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(addLink, rules);*/
   }
   
   private void makeButton (String name, String command, int column, int row, int height, GridBagConstraints rules) {
      JButton newButton = new JButton (name);
      newButton.setActionCommand (command);
      newButton.addActionListener(this);
      rules.gridx = column;
      rules.gridy = row;
      rules.ipady = height;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(newButton, rules);
   }
   
   public void actionPerformed (ActionEvent clicked) {
      String command = clicked.getActionCommand();
      if (selected.supers.contains(command) || selected.subs.contains(command) || selected.links.contains(command)) {
         window.setVisible(false);
         window.dispose();
         try {
            next = new Object (command, selected.set);
         } catch (FileNotFoundException e) {
         } catch (IOException e) {
         }
         open = false;
      }
   }
}