package com.pluralsight;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

/**
 *
 * The Screen class is the main class of the Accounting Ledger application. It provides a command-line interface
 * for users to record financial transactions and generate various reports related to their financial activity.
 */
public class Screen {


    private static final ArrayList<Transactions> transactions = new ArrayList<Transactions>();
    private static final HashMap<String, String> userCredentials = new HashMap<>();

    public static String NAME = "";

    public static boolean running = true;
    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);




    /**
     * The main method is the entry point of the Accounting Ledger application. It initializes the user interface,
     * processes user inputs, and performs various actions related to recording transactions and generating reports.
     * @param args Command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        Progress.bar();
        System.out.println("\n");
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--------------------------------------------"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--"+ConsoleColors.RESET+ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+" WELCOME TO MUHAMRIF ACCOUNTING LEDGER! "+ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--------------------------------------------"+ConsoleColors.RESET);

        UserLogin.USER_LOGIN();

        //loads the transactions from a file, or creates a file if it does not exist
        loadTransactions(FILE_NAME.toLowerCase(), NAME.toLowerCase());



        while (running) {
            //Stream method to get running total of the ledger account (amounts of deposits and payments).
            double sum = transactions.stream().mapToDouble(x -> (x.getAmount())).reduce(0, Double::sum);
            System.out.printf("YOUR CURRENT TOTAL LEDGER VALUE: %.2f \n",sum);


            System.out.println("Choose an option:");
            System.out.println(ConsoleColors.GREEN_BRIGHT + "T) Add A Transaction 🤑" +ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE+"L) Ledger 📓"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"X) "+ "Exit 🛑"+ConsoleColors.RESET);


            System.out.println("Your Selection \uD83D\uDC49\uD83C\uDFFD");
            String input = scanner.next().trim();


            switch (input.toUpperCase()) {
                case "T":
                    System.out.println("\n");
                    addTransaction(scanner, NAME.toLowerCase());

                    System.out.println("\n" +"👈🏽GOING BACK TO HOME MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "L":

                    System.out.println("\n" + "GOING TO LEDGER!👉🏽"+"\n");
                    Progress.progressLong();
                    ledgerMenu(scanner);
                    break;
                case "X":
                    System.out.println(ConsoleColors.RED+ConsoleColors.RED_BACKGROUND+"-------------------------------------."+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.RED+ConsoleColors.RED_BACKGROUND+"--"+ConsoleColors.RESET+ConsoleColors.RED_BOLD_BRIGHT+"🚨🛑!YOU ARE NOW SIGNING OFF!🛑🚨"+ConsoleColors.RESET+ConsoleColors.RED+ConsoleColors.RED_BACKGROUND+"--"+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.RED+ConsoleColors.RED_BACKGROUND+"-------------------------------------."+ConsoleColors.RESET);
                    Progress.progressSmall();
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"THANK YOU FOR CHOOSING MUHAMRIF ACCOUNTING LEDGER"+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"HAVE A WONDERFUL DAY!☀️"+ConsoleColors.RESET);
                    running = false;
                    break;
                default:
//                    System.out.println("🚨🛑Invalid option🛑🚨");
                    break;
            }
        }

        scanner.close();
    }


    /**
     * This method allows users to register by providing a unique username and password. It checks if the
     * username already exists and, if not, stores the provided username and password in the HashMap.
     *
     * @param userCredentials A HashMap used to store username-password pairs.
     */
//    public static void registerUser(HashMap<String, String> userCredentials) {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println(ConsoleColors.WHITE_UNDERLINED+"Welcome! Let's register your username and password."+ ConsoleColors.RESET);
//        System.out.print("Enter your username:👉🏽 ");
//        String username = scanner.next();
//
//        // check if the username already exists
//        if (userCredentials.containsKey(username)) {
//            System.out.println(ConsoleColors.RED+"Username already exists. Please choose a different username."+ConsoleColors.RESET);
//            registerUser(userCredentials);
//            return;
//        }
//
//        System.out.print("Enter your password:👉🏽 ");
//        String password = scanner.next();
//
//        // stores the username and password in the hashmap
//        userCredentials.put(username, password);
//
//        // write the user data to the CSV file
//        writeUserDataToFile(username, password);
//
//        System.out.println("\n" + ConsoleColors.GREEN_BOLD_BRIGHT+"Registration successful!" +ConsoleColors.RESET+ "\n");
//        NAME = username;
//    }
//
//    /**
//     * This method writes user data (username and password) to an external CSV file for persistent storage.
//     *
//     * @param username The username to be written to the CSV file.
//     * @param password The password to be written to the CSV file.
//     */
//    public static void writeUserDataToFile(String username, String password) {
//        try (FileWriter writer = new FileWriter("AllTransactions/" +CSV_FILE, true)) {
//            writer.append(username).append("|").append(password).append("\n");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * This method allows users to log in by entering their username and password. It checks if the user
//     * exists in the userCredentials HashMap and if the provided password matches. If not, it provides
//     * the option to sign up or log in again.
//     *
//     * @param userCredentials A HashMap used to store username-password pairs.
//     * @param allowSignup   A boolean indicating whether user sign-up is allowed.
//     */
//    public static void login(HashMap<String, String> userCredentials, boolean allowSignup) {
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"\n💰Welcome! Let's log in.💰"+ConsoleColors.RESET+"\n");
//            System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Enter your username:👉🏽 "+ConsoleColors.RESET);
//            String username = scanner.nextLine();
//            NAME = username;
//
//            if (userCredentials.containsKey(username)) {
//                System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Enter your password:👉🏽 "+ConsoleColors.RESET);
//                String password = scanner.nextLine();
//
//                // Check if the entered password matches the stored password
//                if (userCredentials.get(username).equals(password)) {
//                    System.out.println("\n"+"Welcome, " +ConsoleColors.BLUE_BOLD_BRIGHT +username.toUpperCase() +ConsoleColors.RESET+ "!");
//                    break;
//                    // Successful login breaks the loop.
//                } else {
//                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"Wrong credentials. Please try again."+ ConsoleColors.RESET);
//
//                }
//            } else {
//                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"🫤This user does not exist.🫤"+ConsoleColors.RESET);
//
//
//                if (allowSignup) {
//                    System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Would you like to sign up? Enter ( Y ) for YES OR Enter ( N ) for NO: "+ConsoleColors.RESET);
//                    String signupChoice = scanner.nextLine().toLowerCase();
//                    if (signupChoice.equalsIgnoreCase("y")) {
//                        registerUser(userCredentials);
//                    } else {
//                        System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Would You Like To Login Again? Enter ( Y ) for YES OR Enter ( N ) for NO:: "+ConsoleColors.RESET);
//                        String loginChoice = scanner.nextLine().toLowerCase();
//                        if (!loginChoice.equalsIgnoreCase("y")) {
//                            Progress.progressSmall();
//                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"THANK YOU FOR CHOOSING MUHAMRIF ACCOUNTING LEDGER"+ConsoleColors.RESET);
//                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"HAVE A WONDERFUL DAY!☀️"+ConsoleColors.RESET);
//                            running=false;
//                            break; // Exit the loop if the user chooses not to log in.
//                        }
//                    }
//                } else {
//                    System.out.print("Login again? (yes/no): ");
//                    String loginChoice = scanner.nextLine().toLowerCase();
//                    if (!loginChoice.equals("yes")) {
//                        Progress.progressSmall();
//                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"THANK YOU FOR CHOOSING MUHAMRIF ACCOUNTING LEDGER"+ConsoleColors.RESET);
//                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"HAVE A WONDERFUL DAY!☀️"+ConsoleColors.RESET);
//                        running = false;
//                        break; // Exit the loop if the user chooses not to log in.
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * This method reads user data from an external CSV file and populates the userCredentials HashMap.
//     *
//     * @param userCredentials A HashMap used to store username-password pairs.
//     */
//    public static void readUserDataFromFile(HashMap<String, String> userCredentials) {
//        try (BufferedReader reader = new BufferedReader(new FileReader("AllTransactions/" +CSV_FILE))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] data = line.split("\\|");
//                if (data.length == 2) {
//                    userCredentials.put(data[0], data[1]);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * This method check for users.csv File, if it is created already, this program creates a new users.csv file.
//     *
//     */
//    public static void loadUserFile (){
//        //USER File
//        try {
//            File myFile = new File("AllTransactions/" + CSV_FILE);
//            myFile.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * Loads the user's transactions from a CSV file.
     * @param fileName The name of the CSV file where transactions are stored.
     * @param name The user's name, used to identify their ledger file.
     */



    public static void loadTransactions(String fileName, String name) {



        //Transaction File
        try {
            File myFile = new File("AllTransactions/"+(name+fileName).toLowerCase());
            if (myFile.createNewFile()){
                System.out.println("You have no transaction(s) on record!");
            }else{
                System.out.println("Loading your transaction(s)!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader( "AllTransactions/"+(name+fileName).toLowerCase()));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                LocalDate date = LocalDate.parse(tokens[0]);
                LocalTime time = LocalTime.parse(tokens[1]);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);
                Transactions transaction = new Transactions(description,vendor,date, time, amount);
                transactions.add(transaction);


            }


        } catch (IOException e) {
            System.err.println("Error reading file: " + name+fileName);
        }


    }

    /**
     * Adds a financial transaction to the ledger. It prompts the user for transaction details like date, time,
     * vendor, description, and amount, and records the transaction in a CSV file.
     * @param scanner The Scanner object to read user input.
     * @param name The user's name, used to identify their ledger file.
     */
    public static void addTransaction(Scanner scanner, String name) {

        boolean isDeposit = UserValidation.depositOrPayment().equalsIgnoreCase("D");

        LocalDate date = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        LocalTime time = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"⏳📅IF YOU WANT TO ENTER A TRANSACTION THAT HAPPENED NOW ENTER ( Y ) OR ENTER ( N ) FOR MANUAL DATE/TIME ENTRY: "+ConsoleColors.RESET);
        System.out.println("Your Selection 👉🏽");
        String dateNow = scanner.next();

        if (!dateNow.equalsIgnoreCase("Y")) {
            System.out.println("SORRY! UNKNOWN SELECTION, PLEASE ENTER THE DATE AND THE TIME FOR YOUR TRANSACTION:");
            // Building Date
            String year = UserValidation.yearDate();
            String month = UserValidation.monthDate();
            String day = UserValidation.dayDate(month);
            date = LocalDate.parse(year + "-" + month + "-" + day, DateTimeFormatter.ofPattern(DATE_FORMAT));
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Date of your Transaction(YYYY-MM-DD): " + date + ConsoleColors.RESET + "\n");

            // Building Time
            String hour = UserValidation.hourTime();
            String min = UserValidation.minuteTime();
            String sec = UserValidation.secondTime();
            time = LocalTime.parse(hour + ":" + min + ":" + sec, DateTimeFormatter.ofPattern(TIME_FORMAT));
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Time of your Transaction(HH:MM:SS): " + time + ConsoleColors.RESET + "\n");
        }else{
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Date of your Transaction(YYYY-MM-DD): " + date + ConsoleColors.RESET + "\n");
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Time of your Transaction(HH:MM:SS): " + time + ConsoleColors.RESET + "\n");
        }
        // Get Vendor
        String vendor = UserValidation.transactionVendor();

        // Get Description
        String description = UserValidation.transactionDescription();

        // Get Amount
        double amount = UserValidation.transactionAmount();
        if (isDeposit && amount<0) amount*=-1;
        try{
            Transactions transaction = new Transactions(description, vendor, date, time, isDeposit?amount:amount*-1);
            transactions.add(transaction);
            BufferedWriter writer = new BufferedWriter(new FileWriter("AllTransactions/"+(name+FILE_NAME).toLowerCase(), true));
            String outputLine = transaction.getDate()+ "|" + transaction.getTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount() + "\n";
                writer.write(outputLine);
            writer.close();
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"PLEASE WAIT! ADDING TRANSACTION TO THE LEDGER!"+ConsoleColors.RESET+"\n");
            Progress.dance();
            System.out.println("\n");
            transaction.print();
            System.out.println(transaction.getAmount()>=0?ConsoleColors.GREEN_BOLD_BRIGHT+"YOUR TRANSACTION WAS SECURELY RECORDED!" +ConsoleColors.RESET:ConsoleColors.RED_BOLD_BRIGHT+"YOUR TRANSACTION WAS SECURELY RECORDED!" +ConsoleColors.RESET);
        }
        catch(IOException e){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"TRANSACTION WAS NOT RECORDER, TRY AGAIN!" + ConsoleColors.RESET);
        }

    }



    public static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Here is your Account Ledger:");
            System.out.println("Choose an option to continue:");
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"A) All📝"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"D) Deposits💰"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"P) Payments💸"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT+"R) Reports📘"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"H) Home🏠"+ConsoleColors.RESET);

            System.out.println("Your Selection \uD83D\uDC49\uD83C\uDFFD");
            String input = scanner.next().trim();

            switch (input.toUpperCase()) {
                case "A":
                    System.out.println("\n" + "GETTING YOUR TRANSACTIONS!👉🏽"+"\n");
                    Progress.progressLong();
                    displayLedger();
                    System.out.println("\n" +"👈🏽GOING BACK TO LEDGER MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "D":
                    System.out.println("\n" + "GETTING YOUR DEPOSITS!👉🏽"+"\n");
                    Progress.progressLong();
                    displayDeposits();
                    System.out.println("\n" +"👈🏽GOING BACK TO LEDGER MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "P":
                    System.out.println("\n" + "GETTING YOUR PAYMENTS!👉🏽"+"\n");
                    Progress.progressLong();
                    displayPayments();
                    System.out.println("\n" +"👈🏽GOING BACK TO LEDGER MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "R":
                    System.out.println("\n" + "GOING TO REPORTS!👉🏽"+"\n");
                    Progress.progressLong();
                    reportsMenu(scanner);
                    System.out.println("\n" +"👈🏽GOING BACK TO LEDGER MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "H":
                    System.out.println("\n" + "👈🏽GOING BACK TO HOME!"+"\n");
                    Progress.progressLong();
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    /**
     * Displays all recorded transactions in the ledger.
     */
    public static void displayLedger() {
        int counter = 0;
        System.out.println(ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+"DISPLAYING ALL OF YOUR RECORDED TRANSACTIONS: "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);
        for (Transactions x:transactions){
            x.print();
            counter++;
        }
        if (counter==0) System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🫤NO RECORDED TRANSACTION(S) AVAILABLE🫤"+ConsoleColors.RESET);
    }


    /**
     * Displays all recorded deposit transactions in the ledger.
     */
    public static void displayDeposits() {
        int counter =0;
        System.out.println(ConsoleColors.GREEN_UNDERLINED+ConsoleColors.GREEN_BOLD_BRIGHT+"DISPLAYING ALL OF YOUR RECORDED DEPOSITS: "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);
        for (Transactions x:transactions){
            if(x.isDeposit()){
                x.print();
                counter++;
            }

        }
        if (counter==0) System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🫤NO RECORDED DEPOSIT TRANSACTION(S) AVAILABLE🫤"+ConsoleColors.RESET);
    }

    /**
     * Displays all recorded payment transactions in the ledger.
     */
    public static void displayPayments() {
        int counter = 0;
        System.out.println(ConsoleColors.RED_UNDERLINED+ConsoleColors.RED_BOLD_BRIGHT+"DISPLAYING ALL OF YOUR RECORDED PAYMENTS: "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);
        for (Transactions x:transactions){
            if(x.isPayment()){
                x.print();
                counter++;
            }

        }
        if (counter==0) System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🫤NO RECORDED PAYMENT TRANSACTION(S) AVAILABLE🫤"+ConsoleColors.RESET);


    }

    /**
     * Provides a menu for generating various financial reports based on user input. Users can select options
     * to view reports related to monthly, yearly, or vendor-specific transactions.
     * @param scanner The Scanner object to read user input.
     */
    public static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("📘Reports📓");
            System.out.println("Choose an option:");
            System.out.println("1) 🔎Month To Date📅");
            System.out.println("2) 🔎Previous Month📅");
            System.out.println("3) 🔎Year To Date⏳");
            System.out.println("4) 🔎Previous Year⏳");
            System.out.println("5) 🔎Search by Vendor🚙");
            System.out.println("6) 🔎Custom Search🔍");
            System.out.println("0) Back👈🏽");

            System.out.println("Your Selection \uD83D\uDC49\uD83C\uDFFD");
            String input = scanner.next().trim();

            switch (input) {
                case "1":
                    System.out.println("\n" + "GOING TO MONTH TO DATE REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    getTransactionMonthToDate();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();

                    break;
                case "2":
                    System.out.println("\n" + "GOING TO PREVIOUS MONTH REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    getTransactionPrevMonth();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "3":
                    System.out.println("\n" + "GOING TO YEAR TO DATE REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    getTransactionYearToDate();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();

                    break;
                case "4":
                    System.out.println("\n" + "GOING TO PREVIOUS YEAR REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    getTransactionPrevYear();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "5":
                    System.out.println("\n" + "GOING TO VENDOR REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    getTransactionByVendor();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "6":
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, vendor, and amount for each transaction.
                case "0":
                    running = false;
                default:
//                    System.out.println("Invalid option");
                    break;
            }
        }
    }



    /**
     * Displays a report of all transactions for the current month.
     */
    public static void getTransactionMonthToDate(){
        int counter = 0;
        System.out.println(ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+"DISPLAYING TRANSACTION REPORT OF ALL TRANSACTION(S) MONTH TO DATE: "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);

        for (Transactions x:transactions){
            if (((x.getDate().getMonthValue())==LocalDate.now().getMonthValue())&&((x.getDate().getYear())== LocalDate.now().getYear())) {

                x.print();
                counter++;

            }
        }
        if (counter==0) System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🫤NO TRANSACTION REPORT AVAILABLE🫤"+ConsoleColors.RESET);

    }

    /**
     * Displays a report of all transactions for the previous month.
     */
    public static void getTransactionPrevMonth(){
        int counter = 0;
        System.out.println(ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+"DISPLAYING TRANSACTION REPORT OF ALL TRANSACTION(S) MADE LAST MONTH: "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);
        LocalDate today = LocalDate.now();
        LocalDate prevMonth = today.minusMonths(1);
        for (Transactions x:transactions){
            if ((x.getDate().getMonthValue() == prevMonth.getMonthValue())&&(x.getDate().getYear() == prevMonth.getYear())) {
                x.print();
                counter++;
            }
        }
        if (counter==0) System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🫤NO TRANSACTION REPORT AVAILABLE🫤"+ConsoleColors.RESET);
    }

    /**
     * Displays a report of all transactions for the current year.
     */
    public static void getTransactionYearToDate(){
        int counter = 0;
        System.out.println(ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+"DISPLAYING TRANSACTION REPORT OF ALL TRANSACTION(S) MADE YEAR TO DATE: "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);

        for (Transactions x:transactions){
            if ((x.getDate().getYear())==LocalDate.now().getYear()) {
                x.print();
                counter++;
            }
        }
        if (counter==0) System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🫤NO TRANSACTION REPORT AVAILABLE🫤"+ConsoleColors.RESET);
    }

    /**
     * Displays a report of all transactions for the previous year.
     */
    public static void getTransactionPrevYear(){
        int counter = 0;
        System.out.println(ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+"DISPLAYING TRANSACTION REPORT OF ALL TRANSACTION(S) MADE PREVIOUS YEAR: "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);

        LocalDate today = LocalDate.now();
        LocalDate prevYear = today.minusMonths(12);
        for (Transactions x:transactions){
            if (x.getDate().getYear() == prevYear.getYear()) {
                x.print();
                counter++;
            }
        }
        if (counter==0) System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🫤NO TRANSACTION REPORT AVAILABLE🫤"+ConsoleColors.RESET);

    }

    /**
     * Displays a report of transactions associated with a specific vendor.
     */
    public static void getTransactionByVendor(){

        Scanner input = new Scanner(System.in);
        System.out.println("Please Enter the name of the VENDOR for transactions:");
        String vendor = input.next();
        input.nextLine();

        int counter = 0;
        System.out.println(ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+"DISPLAYING TRANSACTION REPORT OF ALL TRANSACTION(S) FOR "+vendor.toUpperCase()+": "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);
        for (Transactions x:transactions){
            if (x.getVendor().equalsIgnoreCase(vendor)){
                x.print();
                counter++;
            }
        }
        if (counter==0) System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🫤NO TRANSACTION REPORT AVAILABLE FOR:" +vendor+ConsoleColors.RESET);

    }


}