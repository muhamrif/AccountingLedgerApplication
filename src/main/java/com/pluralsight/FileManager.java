package com.pluralsight;
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
import java.util.Scanner;
import java.util.*;
/**
 * The FileManager Class contains all the methods will be used Read AND Write from/to files.
 */
public class FileManager {

    /**
     * Loads transaction data from a file and populates the list of transactions.
     *
     * This method reads transaction data from a file with the specified file name and user's name, located
     * in the "AllTransactions" directory. It parses each line of the file to create Transaction objects and
     * adds them to the list of transactions in the "Screen" class.
     *
     * If the file does not exist, it creates an empty file and informs the user.
     *
     * @param fileName The name of the file containing transaction data.
     * @param name The user's name to identify the user's transaction file.
     */
    public static void loadTransactions(String fileName, String name) {
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

        try (BufferedReader reader = new BufferedReader(new FileReader( "AllTransactions/"+((name+fileName).toLowerCase())))){

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                LocalDate date = LocalDate.parse(tokens[0]);
                LocalTime time = LocalTime.parse(tokens[1]);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);
                Transactions transaction = new Transactions(description,vendor,date, time, amount);
                Screen.transactions.add(transaction);

            }

        } catch (IOException e) {
            System.err.print(ConsoleColors.RED_BOLD_BRIGHT+"Something went wrong while loading your transactions! please try again"+ConsoleColors.RESET);
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

        LocalDate date = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern(Screen.DATE_FORMAT)));
        LocalTime time = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern(Screen.TIME_FORMAT)));

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"⏳📅IF YOU WANT TO ENTER A TRANSACTION THAT HAPPENED NOW ENTER ( Y ) OR ENTER ( N ) FOR MANUAL DATE/TIME ENTRY: "+ConsoleColors.RESET);
        System.out.print("Your Selection 👉🏽");
        String dateNow = scanner.next();

        if (!dateNow.equalsIgnoreCase("Y")) {
            System.out.println("SORRY! UNKNOWN SELECTION, PLEASE ENTER THE DATE AND THE TIME FOR YOUR TRANSACTION:");
            // Building Date
            String year = UserValidation.yearDate();
            String month = UserValidation.monthDate();
            String day = UserValidation.dayDate(month);
            date = LocalDate.parse(year + "-" + month + "-" + day, DateTimeFormatter.ofPattern(Screen.DATE_FORMAT));
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Date of your Transaction(YYYY-MM-DD): " + date + ConsoleColors.RESET + "\n");

            // Building Time
            String hour = UserValidation.hourTime();
            String min = UserValidation.minuteTime();
            String sec = UserValidation.secondTime();
            time = LocalTime.parse(hour + ":" + min + ":" + sec, DateTimeFormatter.ofPattern(Screen.TIME_FORMAT));
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
            Screen.transactions.add(transaction);
            BufferedWriter writer = new BufferedWriter(new FileWriter("AllTransactions/"+(name+Screen.FILE_NAME).toLowerCase(), true));
            String outputLine = transaction.getDate()+ "|" + transaction.getTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount() + "\n";
            writer.write(outputLine);
            writer.close();
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"PLEASE WAIT! ADDING TRANSACTION TO THE LEDGER!"+ConsoleColors.RESET+"\n");
            Progress.dance();
            System.out.println("\n");
            transaction.printSlow();
            System.out.println(transaction.getAmount()>=0?ConsoleColors.GREEN_BOLD_BRIGHT+"YOUR TRANSACTION WAS SECURELY RECORDED!" +ConsoleColors.RESET:ConsoleColors.RED_BOLD_BRIGHT+"YOUR TRANSACTION WAS SECURELY RECORDED!" +ConsoleColors.RESET);
        }
        catch(IOException e){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"TRANSACTION WAS NOT RECORDER, TRY AGAIN!" + ConsoleColors.RESET);
        }

    }

    public static void printToReportFile(String reportName){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("Reports/Report.txt",true));
            String extraLine = "\n";
            String outputLine = LocalDate.now() +" @ "+LocalTime.now()+" " +reportName+" For "+Screen.NAME.toUpperCase() +": "+ "\n";
            String headings="""
                    +-----------+--------+-------------------------+--------------------+---------+
                    |   DATE    |  TIME  │       DESCRIPTION       |       VENDOR       |  PRICE  |
                    +-----------+--------+-------------------------+--------------------+---------+""";
            writer.write(extraLine);
            writer.write(extraLine);
            writer.write(extraLine);
            writer.write(outputLine);
            writer.write(headings);
            writer.close();
        }
        catch(IOException e){
            System.out.print(true);
        }

    }

    public static void printToReportFile(Transactions transaction){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("Reports/Report.txt",true));
            String formattedDate = String.format("%-10.10s", transaction.getDate());
            String formattedDesc = String.format("%-25.25s",transaction.getDescription());
            String formattedVendor = String.format("%-20.20s",transaction.getVendor());
            String formattedTime = String.format("%-8.8s", transaction.getTime());
            String formattedPrice = String.format("%6.2f", transaction.getAmount());
            formattedPrice = String.format("$%8.8s", formattedPrice);
            String output = String.format(" \n |%s|%s|%s|%s|%s| \n", formattedDate,formattedTime,formattedDesc,formattedVendor,formattedPrice);
            writer.write(output);
            writer.close();
        }
        catch(IOException e){
            System.out.print(true);
        }

    }

    public static void concludingReport(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("Reports/Report.txt", true));
            String extraLine = "\n";
            String outputLine ="******************************---"+ "END OF SESSION FOR " +Screen.NAME.toUpperCase()+"---******************************";
            writer.write(extraLine);
            writer.write(extraLine);
            writer.write(extraLine);
            writer.write(outputLine);
            writer.close();
        }
        catch(IOException e){
            System.out.print(true);
        }

    }

    public static void clearReportFile(){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("Reports/Report.txt"));
            String outputLine = "";
            writer.write(outputLine);
            writer.close();
        }
        catch(IOException e){
            System.out.print(true);
        }

    }


}
