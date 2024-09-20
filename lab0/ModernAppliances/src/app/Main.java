/**
 * Written by Joshua LaRue
 * September 19, 2024
 * CPRG304-E Fall 2024
 * 
 * The main functions runs the menu loop. 
 * Each appliance type inherits the appliance superclass.
 * 
 */

package app;

import classes.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
  public static void main(String[] args) {
    ArrayList<Appliance> appliances = Appliance.loadAppliances("src/res/appliances.txt");

    // menu loop
    while (true) {
      System.out.println('\n' + "Welcome to Modern Appliances!");
      System.out.println("How may we assist you?");
      System.out.println("1. Check out appliance");
      System.out.println("2. Find appliances by brand");
      System.out.println("3. Display appliances by type");
      System.out.println("4. Produce random appliance list");
      System.out.println("5. Save and exit");
      System.out.println("\nEnter option:\n");

      String choice = System.console().readLine();

      switch (choice) {
        // checking out appliance
        case "1":
          System.out.println("Enter the item number of an appliance:\n");
          String itemNumber = System.console().readLine().strip();

          // loop to check if appliance exists and checks out appliance if it does
          boolean applianceExists = false;
          for (int i = 0; i < appliances.size(); i++) {
            Appliance appliance = appliances.get(i);

            if (appliance.getItemNumber().equals(itemNumber)) {
              applianceExists = true;
              if (appliance.getQuantity() > 0) {
                appliance.checkOut();
                System.out.printf("You checked out:\n%s", appliance);
              } else {
                System.out.println("We're all out of that one!");
              }
            }
          }

          if (!applianceExists) {
            System.out.println("We're sorry, the item number you entered is invalid.");
          }
          break;

        // find appliances by brand
        case "2":
          System.out.println("Enter the brand you are looking for:\n");
          String brand = System.console().readLine();
          System.out.println("Here are matching appliances:\n");

          // prints out each appliance that matches the input brand
          boolean brandExists = false;
          for (int i = 0; i < appliances.size(); i++) {
            Appliance appliance = appliances.get(i);

            if (appliance.getBrand().toLowerCase().equals(brand.toLowerCase())) {
              brandExists = true;
              System.out.println(appliance.toString());
            }
          }

          if (!brandExists) {
            System.out.println("Uh oh! We don't seem to carry that brand.");
          }

          break;

        // find appliance by type
        case "3":
          System.out.println("Enter the type of appliance you are looking for:\n");
          System.out.println("1 - Refrigerators\n2 - Vacuums\n3 - Microwaves\n4 - Dishwashers");
          String applianceType = System.console().readLine().toLowerCase();

          // if appliance type is Refrigerator
          if (applianceType.equals("1")) {
            System.out.println(
                "Enter number of doors: 2 (double door), 3 (three doors) or 4 (four doors):\n");
            String numDoors = System.console().readLine().toLowerCase();
            System.out.println("Matching refrigerators:");
            
            // breaks if not 2 <= numDoors <= 4
            String[] doorList = {"2", "3", "4"};
            boolean exists = Arrays.asList(doorList).contains(numDoors);
            if (!exists) {
            	System.out.println("We don't have refrigerators with that number of doors.");
            	break;
            }
            
            // loop prints out every refrigerator that matches the number of doors given
            for (Appliance appliance : appliances) {
              if (appliance instanceof Refrigerator) {
                Refrigerator refrigerator = (Refrigerator) appliance;
                if (refrigerator.numDoorsIs(numDoors)) {
                  System.out.println(appliance.toString());
                }
              }
            }

            // if appliance type is Vacuum
          } else if (applianceType.equals("2")) {
            System.out.println("Enter battery voltage value. 18 V (low) or 24 V (high)");

            String voltage = System.console().readLine().toLowerCase();
            System.out.println("Matching vacuums");

            // prints out Vacuums with matching voltages
            for (Appliance appliance : appliances) {
              if (appliance instanceof Vacuum) {
                Vacuum vacuum = (Vacuum) appliance;
                if (vacuum.isVoltage(voltage)) {
                  System.out.println(vacuum);
                }
              }
            }

            // if appliance type is Microwave
          } else if (applianceType.equals("3")) {
            System.out.println(
                "Room where the microwave will be installed: K (kitchen) or W (work site):");
            String room = System.console().readLine().toLowerCase();

            String[] roomList = {"k", "w"};
            if (!Arrays.asList(roomList).contains(room.toLowerCase())) {
            	System.out.println("Invalid room type.");
            	break;
            }

            // prints out Microwaves with matching room type
            for (Appliance appliance : appliances) {
              if (appliance instanceof Microwave) {
                Microwave microwave = (Microwave) appliance;
                if (microwave.isRoomType(room)) {
                  System.out.println(microwave.toString());
                }
              }
            }

            // if appliance type is Dishwasher
          } else if (applianceType.equals("4")) {
            System.out.println("Enter a sound rating: Qt (Quietest), Qr (Quieter), Qu (Quiet) or M (Moderate)\n");
            String rating = System.console().readLine();
            System.out.println("List of appliances:");
            
            String[] ratingList = {"qt", "qr", "qu", "m"};
            if (!Arrays.asList(ratingList).contains(rating)) {
            	System.out.println("Invalid sound rating.");
            	break;
            }
            
            // displays dishwashers with matching ratings
            for (int i = 0; i < appliances.size(); i++) {
              Appliance appliance = appliances.get(i);
              if (appliance instanceof Dishwasher) {
                Dishwasher dishwasher = (Dishwasher) appliance;
                if (dishwasher.isRating(rating)) {
                  System.out.println(dishwasher.toString());
                }
              }
            }
          }
          break;

        // produce random appliance list
        case "4":
          System.out.println("Enter number of appliances:\n");

          try {
            int numAppliances = Integer.parseInt(System.console().readLine());
            System.out.println("Random appliances: ");
            
            if (numAppliances > appliances.size()) {
            	System.out.println("We only have " + appliances.size() + " appliances.");
            	break;
            }

            // generates random number, and for each appliance under numAppliances prints appliance
            Random rand = new Random();
            for (int i = 0; i < numAppliances; i++) {
              int index = rand.nextInt(appliances.size() - 1);
              System.out.println(appliances.get(index).toString() + "\n");
            }
          } catch (NumberFormatException e) {
            System.out.println("Invalid integer. Please try again.");
          }
          break;

        // write appliance file and exit program
        case "5":
          String applianceStringToWrite = "";
          
          // casts each appliance to appropriate type and appends appliance's formatted file string to applianceStringToWrite
          for (Appliance appliance : appliances) {
            if (appliance instanceof Dishwasher) {
              Dishwasher dishwasher = (Dishwasher) appliance;
              applianceStringToWrite += dishwasher.toFileString();
            } else if (appliance instanceof Microwave) {
              Microwave microwave = (Microwave) appliance;
              applianceStringToWrite += microwave.toFileString();
            } else if (appliance instanceof Refrigerator) {
              Refrigerator refrigerator = (Refrigerator) appliance;
              applianceStringToWrite += refrigerator.toFileString();
            } else if (appliance instanceof Vacuum) {
              Vacuum vacuum = (Vacuum) appliance;
              applianceStringToWrite += vacuum.toFileString();
            }
          }

          File applianceFile = new File("./src/res/appliances.txt");

          if (!applianceFile.exists()) {
            System.out.println("Uh oh... your appliance file is missing!");
            break;
          }

          try {
            FileWriter writer = new FileWriter(applianceFile.getCanonicalFile(), false);
            writer.write(applianceStringToWrite);
            writer.close();
            System.out.println("File successfully written! Goodbye!");
          } catch (IOException e) {
            System.out.println("Error writing to appliance file:\n" + e.getMessage());
          }
          System.exit(0);

        default:
          System.out.println("You didn't select a valid option. Please try again!");
          break;
      }
    }
  }
}
