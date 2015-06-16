import java.util.*;
import java.io.*;

public class Object {
   
   public String name;
   public String set;
   public String description;
   public ArrayList<String> supers = new ArrayList<String>();
   public ArrayList<String> subs = new ArrayList<String>();
   public ArrayList<String> links = new ArrayList<String>();
   
   public Object (String name, String set) {
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
   
   public void addSuper (String newSuper, boolean done) throws FileNotFoundException, IOException {
      addConnection (newSuper, supers, done);
   }
   
   public void addSub (String newSub, boolean done) throws FileNotFoundException, IOException {
      addConnection (newSub, subs, done);
   }
   
   public void addLink (String newLink, boolean done) throws FileNotFoundException, IOException {
      addConnection (newLink, links, done);
   }
   
   public void newDescription (String replacement) throws FileNotFoundException {
      description = replacement;
   }
   
   public boolean deleteSuper (String superToDelete, boolean selected) {
      return deleteConnection(superToDelete, supers, selected);
   }
   
   public boolean deleteSub (String subToDelete, boolean selected) {
      return deleteConnection(subToDelete, subs, selected);
   }
   
   public boolean deleteLink (String linkToDelete, boolean selected) {
      return deleteConnection(linkToDelete, links, selected);
   }
   
   public void deleteSelf () {
      for (String s : supers) {
         deleteSuper (s, true);
      }
      for (String s : subs) {
         deleteSub (s, true);
      }
      ArrayList<String> temp = links;
      for (String s : temp) {
         deleteLink (s, true);
      }
   }
   
   private boolean update () {
      try {
         PrintStream update = new PrintStream (new File ("data\\" + set + "\\" + name + ".txt"));
         for (String obj : supers) {
            update.println(obj);
         }
         update.println("*");
         for (String obj : subs) {
            update.println(obj);
         }
         update.println("*");
         for (String obj : links) {
            update.println(obj);
         }
         update.println("*");
         //update = printCategory(supers, update);
         //update = printCategory(subs, update);
         //update = printCategory(links, update);
         update.println(description);
         update.close();
      } catch (Exception e) {
      }
      return true;
   }
   
   private PrintStream printCategory (ArrayList<String> category, PrintStream writer) {
      for (String obj : category) {
         writer.println(obj);
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
   
   private boolean deleteConnection (String toDelete, ArrayList<String> list, boolean selected) {
      try {
         if (selected) {
            Object temp = new Object (toDelete, set);
            if (list.equals(supers)) {
               supers.remove(toDelete);
               temp.deleteSub(name, false);
            } else if (list.equals(subs)) {
               subs.remove(toDelete);
               temp.deleteSuper(name, false);
            } else if (list.equals(links)) {
               links.remove(toDelete);
               temp.deleteLink(name, false);
            }
         }
         update();
      } catch (Exception e) {
      }
      return true;
   }
   
   private boolean addConnection (String newConnection, ArrayList<String> list, boolean done) {
      try {
         list.add(newConnection);
         if (done == false) {
            File tempFile = new File("data\\" + set + "\\" + newConnection + ".txt");
            if (!tempFile.exists()) {
               tempFile.createNewFile();
               PrintStream addDots = new PrintStream (tempFile);
               addDots.println("*");
               addDots.println("*");
               addDots.println("*");
               addDots.close();
            }
            Object temp = new Object (newConnection, set);
            if (list.equals(supers)) {
               temp.addSub(name, true);
            } else if (list.equals(subs)) {
               temp.addSuper(name, true);
            } else if (list.equals(links)) {
               temp.addLink(name, true);
            }
         }
      } catch (Exception e) {
      }
      return update();
   }
}