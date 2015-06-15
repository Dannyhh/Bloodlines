import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class InputUI extends UI {
   
   Object selected;
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
   
   public InputUI (Object selected, String type) {
      this.selected = selected;
      this.type = type;
      renderAll();
      version = "Object";
   }
   
   public InputUI (Object selected) {
      this.selected = selected;
      renderAll();
      version = "Description";
   }
   
   public void renderAll () {
      window = new JFrame ("Bloodlines");
      window.setLayout(new GridBagLayout());
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      GridBagConstraints rules = new GridBagConstraints();
      JTextArea textArea = new JTextArea("Please Enter a Legal String", 1, 27);
      textArea.setEditable(false);
      rules.gridx = 0;
      rules.gridy = 0;
      rules.gridwidth = 1;
      rules.ipady = 40;
      rules.fill = GridBagConstraints.BOTH;
      window.getContentPane().add(textArea, rules);
      JTextField textField = new JTextField(27);
      textField.addActionListener(this);
      rules.gridy = 1;
      window.getContentPane().add(textField, rules);
      window.pack();
      window.setVisible(true);
   }
   
   public void actionPerformed (ActionEvent entered) {
      try {
         String text = textField.getText();
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
               new ObjUI (new Object (text, defaultOf));
            } else if (version.equals("Object")) {
               if (type.equals("Super")) {
                  selected.addSuper(text, false);
               } else if (type.equals("Sub")) {
                  selected.addSub(text, false);
               } else if (type.equals("Link")) {
                  selected.addLink(text, false);
               }
               new ObjUI (new Object (text, selected.set));
            } else if (version.equals("Description")) {
               selected.newDescription(text);
               new DescUI (selected);
            }
         } else {
            if (version.equals("Set")) {
               new InputUI ();
            } else if (version.equals("Default")) {
               new InputUI (defaultOf);
            } else if (version.equals("Object")) {
               new InputUI (selected, type);
            } else if (version.equals("Description")) {
               new InputUI (selected);
            }
         }
      } catch (Exception e) {
      }
   }
}