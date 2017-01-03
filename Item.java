import java.util.*;
import java.io.*;

public class Item {
   
   public String name;
   public String set;
   public String description;
   public ArrayList<String> supers = new ArrayList<String>();
   public ArrayList<String> subs = new ArrayList<String>();
   public ArrayList<String> links = new ArrayList<String>();
   
   public Item (String name, String set) {
      this.name = name;
      this.set = set;
      boolean found = true;
      try {
         File sourceFile = new File ("data\\" + set + "\\" + name + ".txt");
         if (!sourceFile.exists()) {
            sourceFile.createNewFile();
         }
         Scanner source = new Scanner (sourceFile);
         description = "";
         try {
            source = getSection(supers, source);
            source = getSection(subs, source);
            source = getSection(links, source);
            if (source.hasNextLine()) {
               description = source.nextLine();
            }
            source.close();
         } catch (NoSuchElementException e) {
            update();
         }
      } catch (Exception e) {
      }
   }
   
   public void newDescription (String replacement) throws FileNotFoundException {
      description = replacement;
   }
   
   public void deleteSelf () {
      while (supers.size() > 0) {
         deleteConnection (supers.get(0), supers, true);
      }
      while (subs.size() > 0) {
         deleteConnection (subs.get(0), subs, true);
      }
      while (links.size() > 0) {
         deleteConnection (links.get(0), links, true);
      }
      (new File("data\\" + set + "\\" + name + ".txt")).delete();
   }
   
   private void update () {
      try {
         PrintStream update = new PrintStream (new File ("data\\" + set + "\\" + name + ".txt"));
         update = printCategory(supers, update);
         update = printCategory(subs, update);
         update = printCategory(links, update);
         update.println(description);
         update.close();
      } catch (Exception e) {
      }
   }
   
   private PrintStream printCategory (ArrayList<String> category, PrintStream writer) {
      for (String item : category) {
         writer.println(item);
      }
      writer.println("*");
      return writer;
   }
   
   private Scanner getSection (ArrayList<String> section, Scanner source) {
      String temp = source.nextLine();
      while (temp.equals("*") == false) {
         section.add(temp);
         temp = source.nextLine();
      }
      return source;
   }
   
   public void deleteConnection (String toDelete, ArrayList<String> list, boolean selected) {
      Item temp = new Item (toDelete, set);
      if (list.equals(supers)) {
         supers.remove(toDelete);
         if (selected) {
            temp.deleteConnection(name, temp.subs, false);
         }
      } else if (list.equals(subs)) {
         subs.remove(toDelete);
         if (selected) {
            temp.deleteConnection(name, temp.supers, false);
         }
      } else if (list.equals(links)) {
         links.remove(toDelete);
         if (selected) {
            temp.deleteConnection(name, temp.links, false);
         }
      }
      update();
   }
   
   public void addConnection (String newConnection, ArrayList<String> list, boolean selected) {
      try {
         list.add(newConnection);
         if (selected == true) {
            File tempFile = new File("data\\" + set + "\\" + newConnection + ".txt");
            if (!tempFile.exists()) {
               tempFile.createNewFile();
               PrintStream addDots = new PrintStream (tempFile);
               addDots.println("*");
               addDots.println("*");
               addDots.println("*");
               addDots.close();
            }
            Item temp = new Item (newConnection, set);
            if (list.equals(supers)) {
               temp.addConnection(name, temp.subs, false);
            } else if (list.equals(subs)) {
               temp.addConnection(name, temp.supers, false);
            } else if (list.equals(links)) {
               temp.addConnection(name, temp.links, false);
            }
         }
      } catch (Exception e) {
      }
      update();
   }
}