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
import java.util.Collections;
import java.util.Scanner;

public class Screen {

    private static ArrayList<Transactions> transactions = new ArrayList<Transactions>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"Please Enter Your Name To Open Your Accounting Ledger 📝:"+ConsoleColors.RESET );
        String name = scanner.next();
        scanner.nextLine();
        progress();
        System.out.println("Welcome "+ ConsoleColors.BLUE_BOLD_BRIGHT +name.toUpperCase()+ ConsoleColors.RESET  +" to your TransactionApp!");
        loadTransactions(FILE_NAME.toLowerCase(), name.toLowerCase());
        boolean running = true;


        while (running) {
            System.out.println("Choose an option:");
            System.out.println(ConsoleColors.GREEN_BRIGHT + "D) Add Deposit 🤑" +ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BRIGHT + "P) Make Payment (Debit) 💸" +ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE+"L) Ledger 📓"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"X) "+ "Exit 🛑"+ConsoleColors.RESET);

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D", "P":
                    addTransaction(scanner, name.toLowerCase());
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName, String name) {
        try {
            File myFile = new File((name+fileName).toLowerCase());
            if (myFile.createNewFile()){
                System.out.println("You have no transaction(s) on record!");
            }else{
                System.out.println("Loading your transaction(s)!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader((name+fileName).toLowerCase()));
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

    private static void addTransaction(Scanner scanner, String name) {

        boolean isDeposit = UserValidation.depositOrPayment().equalsIgnoreCase("D");

        // Building Date
        String year = UserValidation.yearDate();
        String month = UserValidation.monthDate();
        String day = UserValidation.dayDate(month);
        LocalDate date = LocalDate.parse(year+"-"+month+"-"+day, DateTimeFormatter.ofPattern(DATE_FORMAT));
        System.out.println("Date of your Transaction: " + date);

        // Building Time
        String hour = UserValidation.hourTime();
        String min = UserValidation.minuteTime();
        String sec = UserValidation.secondTime();
        LocalTime time = LocalTime.parse(hour+":"+min+":"+sec, DateTimeFormatter.ofPattern(TIME_FORMAT));
        System.out.println("Time of your Transaction: " + time);

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
            BufferedWriter writer = new BufferedWriter(new FileWriter((name+FILE_NAME).toLowerCase()));
            for (Transactions x:transactions){
                String outputLine = x.getDate() + "|" + x.getTime() + "|" + x.getDescription() + "|" + x.getVendor() + "|" + x.getAmount() + "\n";
                writer.write(outputLine);
            }
            writer.close();
            System.out.println("YOUR TRANSACTION WAS SECURELY RECORDED!");
        }
        catch(IOException e){
            System.out.println("TRANSACTION WAS NOT RECORDER, TRY AGAIN!");
        }
    }


    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        for (Transactions x:transactions){
            System.out.println(x.getDate() + "|" + x.getTime() + "|" + x.getDescription() + "|" + x.getVendor() + "|" + x.getAmount() + "\n");
        }
    }

    private static void displayDeposits() {
        for (Transactions x:transactions){
            if(x.isDeposit()){
                System.out.println(x.getDate() + "|" + x.getTime() + "|" + x.getDescription() + "|" + x.getVendor() + "|" + x.getAmount() + "\n");
            }

        }
    }

    private static void displayPayments() {
        for (Transactions x:transactions){
            if(x.isPayment()){
                System.out.println(x.getDate() + "|" + x.getTime() + "|" + x.getDescription() + "|" + x.getVendor() + "|" + x.getAmount() + "\n");
            }

        }


    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    getTransactionMonthToDate();
                    break;
                case "2":
                    getTransactionPrevMonth();
                    break;
                case "3":
                    getTransactionYearToDate();
                    break;
                case "4":
                    getTransactionPrevYear();
                    break;
                case "5":
                    getTransactionByVendor();
                    break;
                case "6":
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, vendor, and amount for each transaction.
                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }




    private static void getTransactionMonthToDate(){
        LocalDate today = LocalDate.now();
        LocalDate monthToDate = today.minusMonths(1);
        for (Transactions x:transactions){
            if (x.getDate().compareTo(monthToDate)>=0) {
                System.out.println(x);
            }
        }
    }
    private static void getTransactionPrevMonth(){
        LocalDate today = LocalDate.now();
        LocalDate prevMonth = today.minusMonths(1);
        for (Transactions x:transactions){
            if ((x.getDate().getMonthValue() == prevMonth.getMonthValue())&&(x.getDate().getYear() == prevMonth.getYear())) {
                System.out.println(x);
            }
        }
    }

    private static void getTransactionYearToDate(){
        LocalDate today = LocalDate.now();
        LocalDate yearToDate = today.minusMonths(12);
        for (Transactions x:transactions){
            if (x.getDate().compareTo(yearToDate)>=0) {
                System.out.println(x);
            }
        }
    }

    private static void getTransactionPrevYear(){
        LocalDate today = LocalDate.now();
        LocalDate prevYear = today.minusMonths(12);
        for (Transactions x:transactions){
            if (x.getDate().getYear() == prevYear.getYear()) {
                System.out.println(x);
            }
        }

    }

    private static void getTransactionByVendor(){

        Scanner input = new Scanner(System.in);

        System.out.println("Please Enter the name of the VENDOR for transactions:");
        String vendor = input.next();
        input.nextLine();
        int counter = 0;
        System.out.println("All available reports for " + vendor+": ");
        for (Transactions x:transactions){
            if (x.getVendor().equalsIgnoreCase(vendor)){
                System.out.println(x);
                counter++;
            }
        }

        if (counter == 0) System.out.println("No Reports Available for " + vendor + ":");

    }

    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.
    }

    private static void filterTransactionsByVendor(String vendor) {
        // This method filters the transactions by vendor and prints a report to the console.
        // It takes one parameter: vendor, which represents the name of the vendor to filter by.
        // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
        // Transactions with a matching vendor name are printed to the console.
        // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
    }


    private static void progress() {
        boolean showProgress = true;
            String anim = "=====================";

            int x = 0;
            while (showProgress) {
                System.out.print("\rProcessing "
                        + anim.substring(0, x++ % anim.length())
                        + " ");
                if (x == 40) {
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