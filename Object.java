import java.util.*;
import java.io.*;

public class Object {
   
   private Scanner source;
   public String name;
   public String set;
   public String description;
   public ArrayList<String> supers = new ArrayList<String>();
   public ArrayList<String> subs = new ArrayList<String>();
   public ArrayList<String> links = new ArrayList<String>();
   
   public Object (String name, String set) throws FileNotFoundException, IOException {
      this.name = name;
      this.set = set;
      boolean found = true;
      File sourceFile = new File ("data\\" + set + "\\" + name + ".txt");
      if (!sourceFile.exists()) {
         sourceFile.createNewFile();
      }
      source = new Scanner (sourceFile);
      description = "";
      try {
         getSection(supers);
         getSection(subs);
         getSection(links);
         if (source.hasNextLine()) {
            description = source.nextLine();
         }
      } catch (NoSuchElementException e) {
         update();
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
   
   public void deleteSuper (String superToDelete, boolean selected) throws FileNotFoundException, IOException {
      deleteConnection(superToDelete, supers, selected);
   }
   
   public void deleteSub (String subToDelete, boolean selected) throws FileNotFoundException, IOException {
      deleteConnection(subToDelete, subs, selected);
   }
   
   public void deleteLink (String linkToDelete, boolean selected) throws FileNotFoundException, IOException {
      deleteConnection(linkToDelete, links, selected);
   }
   
   public void deleteSelf () throws FileNotFoundException, IOException {
      for (String s : supers) {
         deleteSuper (s, true);
      }
      for (String s : subs) {
         deleteSub (s, true);
      }
      for (String s : links) {
         deleteLink (s, true);
      }
   }
   
   private void update () throws FileNotFoundException {
      PrintStream update = new PrintStream (new File ("data\\" + set + "\\" + name + ".txt"));
      printCategory(supers, update);
      printCategory(subs, update);
      printCategory(links, update);
      update.println(description);
   }
   
   private void printCategory (ArrayList<String> category, PrintStream writer) {
      for (String obj : category) {
         writer.println(obj);
      }
      writer.println("*");
   }
   
   private void getSection (ArrayList<String> section) {
      String temp = source.nextLine();
      while (temp.equals("*") == false) {
         section.add(temp);
         temp = source.nextLine();
      }
   }
   
   private void deleteConnection (String toDelete, ArrayList<String> list, boolean selected) throws FileNotFoundException, IOException {
      list.remove(toDelete);
      if (selected == true) {
         Object temp = new Object (toDelete, set);
         if (list.equals(supers)) {
            temp.deleteSub(name, false);
         } else if (list.equals(subs)) {
               temp.deleteSuper(name, false);
         } else if (list.equals(links)) {
               temp.deleteLink(name, false);
         }
      }
      update();
   }
   
   private void addConnection (String newConnection, ArrayList<String> list, boolean done) throws FileNotFoundException, IOException {
      if (newConnection.contains("*") == false
         && supers.indexOf(newConnection) == -1
         && subs.indexOf(newConnection) == -1
         && links.indexOf(newConnection) == -1
         && newConnection.contains("\\") == false) {
         list.add(newConnection);
         if (done == false) {
            File tempFile = new File("data\\" + set + "\\" + newConnection + ".txt");
            if (!tempFile.exists()) {
               tempFile.createNewFile();
               PrintStream addDots = new PrintStream (tempFile);
               addDots.println("*");
               addDots.println("*");
               addDots.println("*");
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
      } else {
         System.out.println("Please do not use the string name \"*\", a taken name, or one containing a backslash");
      }
      update();
   }
   
}