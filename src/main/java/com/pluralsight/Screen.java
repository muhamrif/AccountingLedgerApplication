package com.pluralsight;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.reader.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.*;

/**
 *
 * The Screen class is the main class of the Accounting Ledger application. It provides a command-line interface
 * for users to record financial transactions and generate various reports related to their financial activity.
 */
public class Screen {


    private static ArrayList<Transactions> transactions = new ArrayList<Transactions>();
    private static HashMap<String, String> userCredentials = new HashMap<>();

    private static String NAME = "";

    private static boolean running = true;
    private static final String FILE_NAME = "transactions.csv";
    private static final String CSV_FILE = "users.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    private static final Terminal terminal;

    static {
        try {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final LineReader reader = LineReaderBuilder.builder()
            .terminal(terminal)
            .build();


    /**
     * The main method is the entry point of the Accounting Ledger application. It initializes the user interface,
     * processes user inputs, and performs various actions related to recording transactions and generating reports.
     * @param args Command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        Progress.bar();
        System.out.println("\n");
        terminal.flush();
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--------------------------------------------"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--"+ConsoleColors.RESET+ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+" WELCOME TO MUHAMRIF ACCOUNTING LEDGER! "+ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN+ConsoleColors.GREEN_BACKGROUND+"--------------------------------------------"+ConsoleColors.RESET);
        loadUserFile();
        readUserDataFromFile(userCredentials);
        login(userCredentials, true);
        loadTransactions(FILE_NAME.toLowerCase(), NAME.toLowerCase());



        while (running) {
//            loadTransactions(FILE_NAME.toLowerCase(), NAME.toLowerCase());
            double sum = transactions.stream().mapToDouble(x -> (x.getAmount())).reduce(0, Double::sum);
            System.out.printf("YOUR CURRENT TOTAL LEDGER VALUE: %.2f \n",sum);
            System.out.println("Choose an option:");
            System.out.println(ConsoleColors.GREEN_BRIGHT + "D) Add Deposit 🤑" +ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BRIGHT + "P) Make Payment (Debit) 💸" +ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE+"L) Ledger 📓"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"X) "+ "Exit 🛑"+ConsoleColors.RESET);


            terminal.writer().write("Your Selection \uD83D\uDC49\uD83C\uDFFD");
            terminal.flush();
            String input = scanner.next().trim();


            switch (input.toUpperCase()) {
                case "D", "P":
                    System.out.println("\n");
                    addTransaction(scanner, NAME.toLowerCase());

                    System.out.println("\n" +"👈🏽GOING BACK TO HOME MENU!"+"\n");
                    progressSmall();
                    break;
                case "L":

                    System.out.println("\n" + "GOING TO LEDGER!👉🏽"+"\n");
                    progress();
                    ledgerMenu(scanner);
                    break;
                case "X":
                    System.out.println(ConsoleColors.RED+ConsoleColors.RED_BACKGROUND+"-------------------------------------."+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.RED+ConsoleColors.RED_BACKGROUND+"--"+ConsoleColors.RESET+ConsoleColors.RED_BOLD_BRIGHT+"🚨🛑!YOU ARE NOW SIGNING OFF!🛑🚨"+ConsoleColors.RESET+ConsoleColors.RED+ConsoleColors.RED_BACKGROUND+"--"+ConsoleColors.RESET);
                    System.out.println(ConsoleColors.RED+ConsoleColors.RED_BACKGROUND+"-------------------------------------."+ConsoleColors.RESET);
                    progressSmall();
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

    public static void registerUser(HashMap<String, String> userCredentials) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ConsoleColors.WHITE_UNDERLINED+"Welcome! Let's register your username and password."+ ConsoleColors.RESET);
        System.out.print("Enter your username:👉🏽 ");
        String username = scanner.next();

        // Check if the username already exists.
        if (userCredentials.containsKey(username)) {
            System.out.println(ConsoleColors.RED+"Username already exists. Please choose a different username."+ConsoleColors.RESET);
            registerUser(userCredentials);
            return;
        }

        System.out.print("Enter your password:👉🏽 ");
        String password = scanner.next();

        // Store the username and password in the HashMap.
        userCredentials.put(username, password);

        // Write the user data to the CSV file.
        writeUserDataToFile(username, password);

        System.out.println("\n" + ConsoleColors.GREEN_BOLD_BRIGHT+"Registration successful!" +ConsoleColors.RESET+ "\n");
        NAME = username;
    }

    public static void writeUserDataToFile(String username, String password) {
        try (FileWriter writer = new FileWriter("AllTransactions/" +CSV_FILE, true)) {
            writer.append(username).append("|").append(password).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void login(HashMap<String, String> userCredentials, boolean allowSignup) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"\n💰Welcome! Let's log in.💰"+ConsoleColors.RESET+"\n");
            System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Enter your username:👉🏽 "+ConsoleColors.RESET);
            String username = scanner.nextLine();
            NAME = username;

            if (userCredentials.containsKey(username)) {
                System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Enter your password:👉🏽 "+ConsoleColors.RESET);
                String password = scanner.nextLine();

                // Check if the entered password matches the stored password.
                if (userCredentials.get(username).equals(password)) {
                    System.out.println("\n"+"Welcome, " +ConsoleColors.BLUE_BOLD_BRIGHT +username.toUpperCase() +ConsoleColors.RESET+ "!");
                    break; // Successful login, exit the loop.
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
                            progressSmall();
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"THANK YOU FOR CHOOSING MUHAMRIF ACCOUNTING LEDGER"+ConsoleColors.RESET);
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"HAVE A WONDERFUL DAY!☀️"+ConsoleColors.RESET);
                            running=false;
                            break; // Exit the loop if the user chooses not to log in.
                        }
                    }
                } else {
                    System.out.print("Login again? (yes/no): ");
                    String loginChoice = scanner.nextLine().toLowerCase();
                    if (!loginChoice.equals("yes")) {
                        progressSmall();
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"THANK YOU FOR CHOOSING MUHAMRIF ACCOUNTING LEDGER"+ConsoleColors.RESET);
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"HAVE A WONDERFUL DAY!☀️"+ConsoleColors.RESET);
                        running = false;
                        break; // Exit the loop if the user chooses not to log in.
                    }
                }
            }
        }
    }
//    public static void login(HashMap<String, String> userCredentials, boolean allowSignup) {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"\n💰Welcome! Let's log in.💰"+ConsoleColors.RESET+"\n");
//        System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Enter your username:👉🏽 "+ConsoleColors.RESET);
//        String username = scanner.nextLine();
//        NAME = username;
//
//        if (userCredentials.containsKey(username)) {
//            System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Enter your password:👉🏽 "+ConsoleColors.RESET);
//            String password = scanner.nextLine();
//
//            if (userCredentials.get(username).equals(password)) {
//                System.out.println("\n"+"Welcome, " +ConsoleColors.BLUE_BOLD_BRIGHT +username.toUpperCase() +ConsoleColors.RESET+ "!");
//            } else {
//                System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"Wrong credentials. Please try again."+ ConsoleColors.RESET);
//            }
//        } else {
//            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"🫤This user does not exist.🫤"+ConsoleColors.RESET);
//
//            if (allowSignup) {
//                System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Would you like to sign up? Enter ( Y ) for YES OR Enter ( N ) for NO: "+ConsoleColors.RESET);
//                String signupChoice = scanner.nextLine().toLowerCase();
//                if (signupChoice.equalsIgnoreCase("yes")) {
//                    registerUser(userCredentials);
//                }else if(signupChoice.equalsIgnoreCase("no")){
//                    running = false;
//                }
//            }
//        }
//    }

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

    public static void loadUserFile (){
        //USER File
        try {
            File myFile = new File("AllTransactions/" + CSV_FILE);
            myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
        terminal.writer().write("Your Selection 👉🏽");
        terminal.flush();
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

            terminal.writer().write("Your Selection \uD83D\uDC49\uD83C\uDFFD");
            terminal.flush();
            String input = scanner.next().trim();

            switch (input.toUpperCase()) {
                case "A":
                    System.out.println("\n" + "GETTING YOUR TRANSACTIONS!👉🏽"+"\n");
                    progress();
                    displayLedger();
                    System.out.println("\n" +"👈🏽GOING BACK TO LEDGER MENU!"+"\n");
                    progressSmall();
                    break;
                case "D":
                    System.out.println("\n" + "GETTING YOUR DEPOSITS!👉🏽"+"\n");
                    progress();
                    displayDeposits();
                    System.out.println("\n" +"👈🏽GOING BACK TO LEDGER MENU!"+"\n");
                    progressSmall();
                    break;
                case "P":
                    System.out.println("\n" + "GETTING YOUR PAYMENTS!👉🏽"+"\n");
                    progress();
                    displayPayments();
                    System.out.println("\n" +"👈🏽GOING BACK TO LEDGER MENU!"+"\n");
                    progressSmall();
                    break;
                case "R":
                    System.out.println("\n" + "GOING TO REPORTS!👉🏽"+"\n");
                    progress();
                    reportsMenu(scanner);
                    System.out.println("\n" +"👈🏽GOING BACK TO LEDGER MENU!"+"\n");
                    progressSmall();
                    break;
                case "H":
                    System.out.println("\n" + "👈🏽GOING BACK TO HOME!"+"\n");
                    progress();
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

            terminal.writer().write("Your Selection \uD83D\uDC49\uD83C\uDFFD");
            terminal.flush();
            String input = scanner.next().trim();

            switch (input) {
                case "1":
                    System.out.println("\n" + "GOING TO MONTH TO DATE REPORT!👉🏽"+"\n");
                    progress();
                    getTransactionMonthToDate();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    progressSmall();

                    break;
                case "2":
                    System.out.println("\n" + "GOING TO PREVIOUS MONTH REPORT!👉🏽"+"\n");
                    progress();
                    getTransactionPrevMonth();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    progressSmall();
                    break;
                case "3":
                    System.out.println("\n" + "GOING TO YEAR TO DATE REPORT!👉🏽"+"\n");
                    progress();
                    getTransactionYearToDate();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    progressSmall();

                    break;
                case "4":
                    System.out.println("\n" + "GOING TO PREVIOUS YEAR REPORT!👉🏽"+"\n");
                    progress();
                    getTransactionPrevYear();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    progressSmall();
                    break;
                case "5":
                    System.out.println("\n" + "GOING TO VENDOR REPORT!👉🏽"+"\n");
                    progress();
                    getTransactionByVendor();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    progressSmall();
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

        LocalDate today = LocalDate.now();
        LocalDate monthToDate = today.minusMonths(1);
        for (Transactions x:transactions){
            if (x.getDate().compareTo(monthToDate)>=0) {
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
        LocalDate today = LocalDate.now();
        LocalDate yearToDate = today.minusMonths(12);
        for (Transactions x:transactions){
            if (x.getDate().compareTo(yearToDate)>=0) {
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


    /**
     * A helper method to display a progress indicator while processing.
     */
    public static void progress() {
        boolean showProgress = true;
            String anim = "=====================";

            int x = 0;
            while (showProgress) {
                System.out.print("\rProcessing "
                        + anim.substring(0, x++ % anim.length())
                        + " ");
                if (x == 20) {
                    showProgress = false;
                    System.out.println("\n");
                }
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
            }

    /**
     * A smaller version of the progress indicator for shorter processes.
     */
    public static void progressSmall() {
        boolean showProgress = true;
        String anim = "=====================";

        int x = 0;
        while (showProgress) {
            System.out.print("\rProcessing "
                    + anim.substring(0, x++ % anim.length())
                    + " ");
            if (x == 10) {
                showProgress = false;
                System.out.println("\n");
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }
}