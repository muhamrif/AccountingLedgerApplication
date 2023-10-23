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
        System.out.println("Welcome to TransactionApp");
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D", "P":
                    addTransaction(scanner);
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

    public static void loadTransactions(String fileName) {
        try {
            File myFile = new File(fileName);
            if (myFile.createNewFile()){
                System.out.println("You have no transaction(s) on record!");
            }else{
                System.out.println("Loading your transaction(s)!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
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
            System.err.println("Error reading file: " + fileName);
        }


    }

    private static void addTransaction(Scanner scanner) {

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
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
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
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    // Generate a report for all transactions within the current month,
                    // including the date, vendor, and amount for each transaction.
                case "2":
                    // Generate a report for all transactions within the previous month,
                    // including the date, vendor, and amount for each transaction.
                case "3":
                    // Generate a report for all transactions within the current year,
                    // including the date, vendor, and amount for each transaction.

                case "4":
                    // Generate a report for all transactions within the previous year,
                    // including the date, vendor, and amount for each transaction.
                case "5":
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
}