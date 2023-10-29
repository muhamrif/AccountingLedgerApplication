package com.pluralsight;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * The Screen class is the main class of the Accounting Ledger application. It provides a command-line interface
 * for users to record financial transactions and generate various reports related to their financial activity.
 */
public class Screen {
    public static final ArrayList<Transactions> transactions = new ArrayList<Transactions>();
    public static String NAME = "";
    public static boolean running = true;
    public static boolean runningLedger = true;
    public static final String FILE_NAME = "transactions.csv";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    /**
     * The main method is the entry point of the Accounting Ledger application. It initializes the user interface,
     * processes user inputs, and performs various actions related to recording transactions and generating reports.
     * @param args Command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Loading bar, and a welcome message
        Progress.onLoadUpWelcome();
        //gets user info and logs in if correct creds are entered, or asks the user to signup.
        UserLogin.USER_LOGIN();
        //clears the reports file for the new login session
        FileManager.clearReportFile();
        //loads the transactions from a file, or creates a file if it does not exist
        FileManager.loadTransactions(FILE_NAME.toLowerCase(), NAME.toLowerCase());
        //gets and displays the home menu from Menus class
        Menus.homeMenu(scanner);
        scanner.close();
    }

}
