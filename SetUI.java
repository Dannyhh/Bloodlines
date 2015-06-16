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
            makeButton (set, set, count, 0, 20, rules);
            count++;
         }
         makeButton ("New Set", "New", count, 0, 20, rules);
      } catch (Exception e) {
      }
   }
   
   public void actionPerformed (ActionEvent clicked) {
      try {
         String command = clicked.getActionCommand();
         if (command.equals("New")) {
            new InputUI();
         } else {
            Scanner defaultFile = new Scanner(new File("data\\" + command + "\\" + command + ".txt"));
            new ObjUI (new Object (defaultFile.next(), command));
         }
      } catch (Exception e) {
      }
   }
}