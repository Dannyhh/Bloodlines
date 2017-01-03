import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class InputUI extends UI {
   
   Item selected;
   String version;
   String defaultOf;
   String type;
   JTextField textField;
   JTextArea textArea;
   
   public InputUI () {
      renderAll();
      version = "Set";
   }
   
   public InputUI (String set) {
      defaultOf = set;
      renderAll();
      version = "Default";
   }
   
   public InputUI (Item selected, String type) {
      this.selected = selected;
      this.type = type;
      renderAll();
      version = "Item";
   }
   
   public InputUI (Item selected) {
      this.selected = selected;
      renderAll();
      version = "Description";
   }
   
   public void renderAll () {
      window = new JFrame ("Bloodlines");
      window.setLayout(new GridBagLayout());
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      GridBagConstraints rules = new GridBagConstraints();
      textArea = new JTextArea("Please Enter a Legal String", 1, 27);
      textArea.setEditable(false);
      rules.gridx = 0;
      rules.gridy = 0;
      rules.gridwidth = 2;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(textArea, rules);
      textField = new JTextField(27);
      textField.addActionListener(this);
      rules.gridwidth = 1;
      rules.gridy = 1;
      window.getContentPane().add(textField, rules);
      makeButton ("Enter", "Enter", 1, 1, 0, rules);
      window.pack();
      window.setVisible(true);
   }
   
   public void actionPerformed (ActionEvent entered) {
      try {
         String text = textField.getText();
         window.setVisible(false);
         window.dispose();
         if (!text.contains("\\") && !text.equals("*")) {
            if (version.equals("Set")) {
               Scanner setsFinder = new Scanner (new File("data\\Sets.txt"));
               ArrayList<String> sets = new ArrayList<String>();
               while (setsFinder.hasNextLine()) {
                  sets.add(setsFinder.nextLine());
               }
               if (!sets.contains(text)) {
                  PrintStream addSet = new PrintStream (new File ("data\\Sets.txt"));
                  for (String aSet : sets) {
                     addSet.println(aSet);
                  }
                  addSet.println(text);
                  File newDir = new File("data\\" + text);
                  newDir.mkdir();
                  File setOrigin = new File("data\\" + text + "\\" + text + ".txt");
                  setOrigin.createNewFile();
                  new InputUI (text);
               } else {
                  new InputUI ();
               }
            } else if (version.equals("Default")) {
               PrintStream addDefault = new PrintStream (new File ("data\\" + defaultOf + "\\" + defaultOf + ".txt"));
               addDefault.println(text);
               new ItemUI (new Item (text, defaultOf));
            } else if (version.equals("Item")) {
               if (type.equals("Super")) {
                  selected.addConnection(text, selected.supers, true);
               } else if (type.equals("Sub")) {
                  selected.addConnection(text, selected.subs, true);
               } else if (type.equals("Link")) {
                  selected.addConnection(text, selected.links, true);
               }
               new ItemUI (new Item (text, selected.set));
            } else if (version.equals("Description")) {
               selected.newDescription(text);
               new DescUI (selected);
            }
         } else {
            if (version.equals("Set")) {
               new InputUI ();
            } else if (version.equals("Default")) {
               new InputUI (defaultOf);
            } else if (version.equals("Item")) {
               new InputUI (selected, type);
            } else if (version.equals("Description")) {
               new InputUI (selected);
            }
         }
      } catch (Exception e) {
      }
   }
}