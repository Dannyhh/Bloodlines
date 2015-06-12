import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DescUI extends UI {
   
   public DescUI (Object selected) {
      window = new JFrame ("Bloodlines: Object" + selected.name + "'s Description");
      window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   }
   
   public void renderAll () {
   }
   
   public void actionPerformed (ActionEvent clicked) {
   }
}