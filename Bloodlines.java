import java.util.*;
import java.io.*;

public class Bloodlines {
   
   public static void main(String[] args) {
      new ObjUI(new Object ("Test1", "TestSet"));
   }
   
   public static void chooseSet() throws FileNotFoundException, IOException {
      Scanner input = new Scanner (System.in);
      Scanner setsFinder = new Scanner (new File("data\\Sets.txt"));
      ArrayList<String> sets = new ArrayList<String>();
      while (setsFinder.hasNextLine()) {
         sets.add(setsFinder.nextLine());
      }
      System.out.println("Choose one of the following sets:");
      for (String set : sets) {
         System.out.println(set);
      }
      System.out.println("OR create a new set");
      String set = input.next();
      if (sets.indexOf(set) == -1) {
         File newDir = new File("data\\" + set);
         newDir.mkdir();
         File setOrigin = new File("data\\" + set + "\\" + set + ".txt");
         setOrigin.createNewFile();
         System.out.println("What is the starting point for this new set: ");
         PrintStream addDefault = new PrintStream (new File ("data\\" + set + "\\" + set + ".txt"));
         addDefault.println(input.next());
         PrintStream addSet = new PrintStream (new File ("data\\Sets.txt"));
         for (String aSet : sets) {
            addSet.println(aSet);
         }
         addSet.println(set);
         chooseSet();
      }
      else {
         Scanner defaultFile = new Scanner(new File("data\\" + set + "\\" + set + ".txt"));
         String firstSelect = defaultFile.next();
         Object firstObject = new Object (firstSelect, set);
         showObject(firstObject);
      }
   }
   
   public static void showObject(Object selected) throws FileNotFoundException, IOException {
      boolean continueOn = true;
      Scanner input = new Scanner (System.in);
      System.out.print("Please choose one of the following objects or options:\nObjects:\nSupers:\n");
      for (String superObj : selected.supers) {
         System.out.print(superObj + "\n");
      }
      System.out.print("Subs:\n");
      for (String subObj : selected.subs) {
         System.out.print(subObj + "\n");
      }
      System.out.print("Linked:\n");
      for (String linkedObj : selected.links) {
         System.out.print(linkedObj + "\n"); 
      }
      System.out.print("Options:\nAddSuper*\nAddSub*\nAddLink*\nDeleteSuper*\nDeleteSub*\nDeleteLink*\nDeleteSelf*\nOpenDescription*\nExit*\n");
      String choice = input.next();
      if (choice.contains("*")) {
         if (choice.contains("Add")) {
            if (choice.contains("Super")) {
               System.out.println("What is the name of the object you want to connect to: ");
               String addChoice = input.next();
               input.close();
               selected.addSuper(addChoice, false);
            } 
            else if (choice.contains("Sub")) {
               System.out.println("What is the name of the object you want to connect to: ");
               String addChoice = input.next();
               input.close();
               selected.addSub(addChoice, false);
            } 
            else if (choice.contains("Link")) {
               System.out.println("What is the name of the object you want to connect to: ");
               String addChoice = input.next();
               input.close();
               selected.addLink(addChoice, false);
            } 
            else {
               System.out.println("That option does not exist");
            }
         } 
         else if (choice.contains("Delete")) {
            if (choice.contains("Super")) {
               System.out.println("What is the name of the object you want to disconnect from: ");
               String deleteChoice = input.next();
               input.close();
               selected.deleteSuper(deleteChoice, false);
            } 
            else if (choice.contains("Sub")) {
               System.out.println("What is the name of the object you want to disconnect from: ");
               String deleteChoice = input.next();
               input.close();
               selected.deleteSub(deleteChoice, false);
            } 
            else if (choice.contains("Link")) {
               System.out.println("What is the name of the object you want to disconnect from: ");
               String deleteChoice = input.next();
               input.close();
               selected.deleteLink(deleteChoice, false);
            } 
            else if (choice.contains("Self")) {
               input.close();
               selected.deleteSelf();
               continueOn = false;
            } 
            else {
               System.out.println("That option does not exist");
            }
         } 
         else if (choice.contains("OpenDescription")) {
            System.out.println(selected.description + "\nWhould you like to Change* or Exit* the description");
            String descChoice = input.next();
            if (descChoice.equals("Change*")) {
               System.out.println("What would you like the new description to be?");
               String newDesc = input.next();
               while (input.hasNext()) {
                  newDesc += " " + input.next();
               }
               input.close();
               selected.newDescription(newDesc);
            } 
            else if (descChoice.equals("Exit")) {
            } 
            else {
               System.out.println("That option does not exist");
            }
         } 
         else if (choice.contains("Exit")) {
            continueOn = false;
         }
         else {
            System.out.println("That option does not exist");
         }
      } 
      else if ( selected.supers.contains(choice)
               || selected.subs.contains(choice)
               || selected.links.contains(choice) ) {
         showObject(new Object (choice, selected.set));
         continueOn = false;
      } 
      else {
         System.out.println("That option does not exist");
      }
      if (continueOn) {
         showObject (selected);
      }
   }
}