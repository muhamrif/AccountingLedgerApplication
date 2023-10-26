package com.pluralsight;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class UserLogin {
    private static final HashMap<String, String> userCredentials = new HashMap<>();
    private static final String CSV_FILE = "users.csv";

    /**
     * Handles user login functionality. This method loads user credentials from a file,
     * checks if the user provided correct credentials, and logs the user in.
     * If the user credentials file does not exist, it will be created.
     */
    public static void USER_LOGIN (){
        //makes a user creds file if it does not exist
        loadUserFile();
        //Loads Username and Passwords to a hashmap
        readUserDataFromFile(userCredentials);
        // checks if user put in right creds
        login(userCredentials, true);
    }


    /**
     * Allows a user to register by providing a username and password.
     * If the provided username is already in use, the method prompts the user to choose a different username.
     * After successful registration, the user's data is stored in the userCredentials HashMap,
     * and the user data is written to the users.csv file.
     *
     * @param userCredentials
     */
    public static void registerUser(HashMap<String, String> userCredentials) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ConsoleColors.WHITE_UNDERLINED+"Welcome! Let's register your username and password."+ ConsoleColors.RESET);
        System.out.print("Enter your username:👉🏽 ");
        String username = scanner.next();

        // check if the username already exists
        if (userCredentials.containsKey(username)) {
            System.out.println(ConsoleColors.RED+"Username already exists. Please choose a different username."+ConsoleColors.RESET);
            registerUser(userCredentials);
            return;
        }

        System.out.print("Enter your password:👉🏽 ");
        String password = scanner.next();

        // stores the username and password in the hashmap
        userCredentials.put(username, PasswordHashing.passwordHashPigLatin(password));

        // write the user data to the CSV file
        writeUserDataToFile(username, PasswordHashing.passwordHashPigLatin(password));

        System.out.println("\n" + ConsoleColors.GREEN_BOLD_BRIGHT+"Registration successful!" +ConsoleColors.RESET+ "\n");
        Screen.NAME = username;
    }

    /**
     * This method writes user data (username and password) to an external CSV file for persistent storage.
     *
     * @param username The username to be written to the CSV file.
     * @param password The password to be written to the CSV file.
     */
    public static void writeUserDataToFile(String username, String password) {
        try (FileWriter writer = new FileWriter("AllTransactions/" +CSV_FILE, true)) {
            writer.append(username).append("|").append(password).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method allows users to log in by entering their username and password. It checks if the user
     * exists in the userCredentials HashMap and if the provided password matches. If not, it provides
     * the option to sign up or log in again.
     *
     * @param userCredentials A HashMap used to store username-password pairs.
     * @param allowSignup   A boolean indicating whether user sign-up is allowed.
     */
    public static void login(HashMap<String, String> userCredentials, boolean allowSignup) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"\n💰Welcome! Let's log in.💰"+ConsoleColors.RESET+"\n");
            System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Enter your username:👉🏽 "+ConsoleColors.RESET);
            String username = scanner.nextLine();
            Screen.NAME = username;

            if (userCredentials.containsKey(username)) {
                System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Enter your password:👉🏽 "+ConsoleColors.RESET);
                String password = scanner.nextLine();

                // Check if the entered password matches the stored password
                if (userCredentials.get(username).equals(PasswordHashing.passwordHashPigLatin(password))) {
                    System.out.println("\n"+"Welcome, " +ConsoleColors.BLUE_BOLD_BRIGHT +username.toUpperCase() +ConsoleColors.RESET+ "!");
                    break;
                    // Successful login breaks the loop.
                } else {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"Wrong credentials. Please try again."+ ConsoleColors.RESET);

                }
            } else {
                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"🫤This user does not exist.🫤"+ConsoleColors.RESET);


                if (allowSignup) {
                    System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Would you like to sign up? Enter ( Y ) for YES OR Enter ( N ) for NO: "+ConsoleColors.RESET);
                    String signupChoice = scanner.nextLine().toLowerCase();
                    if (signupChoice.equalsIgnoreCase("y")) {
                        registerUser(userCredentials);
                    } else {
                        System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Would You Like To Login Again? Enter ( Y ) for YES OR Enter ( N ) for NO:: "+ConsoleColors.RESET);
                        String loginChoice = scanner.nextLine().toLowerCase();
                        if (!loginChoice.equalsIgnoreCase("y")) {
                            Progress.progressSmall();
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"THANK YOU FOR CHOOSING MUHAMRIF ACCOUNTING LEDGER"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"HAVE A WONDERFUL DAY!☀️"+ConsoleColors.RESET);
                            Screen.running=false;
                            break; // Exit the loop if the user chooses not to log in.
                        }
                    }
                } else {
                    System.out.print("Login again? (yes/no): ");
                    String loginChoice = scanner.nextLine().toLowerCase();
                    if (!loginChoice.equals("yes")) {
                        Progress.progressSmall();
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"THANK YOU FOR CHOOSING MUHAMRIF ACCOUNTING LEDGER"+ConsoleColors.RESET);
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"HAVE A WONDERFUL DAY!☀️"+ConsoleColors.RESET);
                        Screen.running = false;
                        break; // Exit the loop if the user chooses not to log in.
                    }
                }
            }
        }
    }

    /**
     * This method reads user data from an external CSV file and populates the userCredentials HashMap.
     *
     * @param userCredentials A HashMap used to store username-password pairs.
     */
    public static void readUserDataFromFile(HashMap<String, String> userCredentials) {
        try (BufferedReader reader = new BufferedReader(new FileReader("AllTransactions/" +CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length == 2) {
                    userCredentials.put(data[0], data[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method check for users.csv File, if it is created already, this program creates a new users.csv file.
     *
     */
    public static void loadUserFile (){
        //USER File
        try {
            File myFile = new File("AllTransactions/" + CSV_FILE);
            myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
