import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DescUI {
   
   JFrame window;
   
   public DescUI (Object selected) {
      window = new JFrame ("Bloodlines: Object" + selected.name + "'s Description");
      window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   }
}