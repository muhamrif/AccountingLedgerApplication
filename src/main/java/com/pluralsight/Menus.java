package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Menus Class contains all the methods will be used to display Menus.
 */
public class Menus {


    /**
     * Displays the main menu for the Accounting Ledger and handles user interactions.
     * The menu shows the current total ledger value and provides options to add a transaction,
     * view the ledger, or exit the application.
     *
     * @param scanner The Scanner object for user input.
     */
   public static void homeMenu(Scanner scanner) {
        while (Screen.running) {
            //Stream method to get running total of the ledger account (amounts of deposits and payments).
            double sum = Screen.transactions.stream().mapToDouble(x -> (x.getAmount())).reduce(0, Double::sum);
            System.out.printf("YOUR CURRENT TOTAL LEDGER VALUE: %.2f \n", sum);


            System.out.println("Choose an option:");
            System.out.println(ConsoleColors.GREEN_BRIGHT + "T) Add A Transaction 🤑" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "L) Ledger 📓" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "X) " + "Exit 🛑" + ConsoleColors.RESET);


            System.out.print("Your Selection \uD83D\uDC49\uD83C\uDFFD");
            String input = scanner.next().trim();


            switch (input.toUpperCase()) {
                case "T":
                    System.out.println("\n");
                   FileManager.addTransaction(scanner, Screen.NAME.toLowerCase());

                    System.out.println("\n" + "👈🏽GOING BACK TO HOME MENU!" + "\n");
                    Progress.progressSmall();
                    break;
                case "L":

                    System.out.println("\n" + "GOING TO LEDGER!👉🏽" + "\n");
                    Progress.progressLong();
                    ledgerMenu(scanner);
                    break;
                case "X":
                    System.out.println(ConsoleColors.RED + ConsoleColors.RED_BACKGROUND + "-------------------------------------." + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.RED + ConsoleColors.RED_BACKGROUND + "--" + ConsoleColors.RESET + ConsoleColors.RED_BOLD_BRIGHT + "🚨🛑!YOU ARE NOW SIGNING OFF!🛑🚨" + ConsoleColors.RESET + ConsoleColors.RED + ConsoleColors.RED_BACKGROUND + "--" + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.RED + ConsoleColors.RED_BACKGROUND + "-------------------------------------." + ConsoleColors.RESET);
                    Progress.progressSmall();
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "THANK YOU FOR CHOOSING MUHAMRIF ACCOUNTING LEDGER" + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "HAVE A WONDERFUL DAY!☀️" + ConsoleColors.RESET);
                    Screen.running = false;
                    break;
                default:
//                    System.out.println("🚨🛑Invalid option🛑🚨");
                    break;
            }
        }
    }

    /**
     * Displays the ledger menu for the Accounting Ledger and handles ledger-related user interactions.
     * This menu allows users to view different types of transactions (All, Deposits, Payments), access reports,
     * and return to the home menu.
     *
     * @param scanner The Scanner object for user input.
     */
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

            System.out.print("Your Selection \uD83D\uDC49\uD83C\uDFFD");
            String input = scanner.next().trim();

            switch (input.toUpperCase()) {
                case "A":
                    System.out.println("\n" + "GETTING YOUR TRANSACTIONS!👉🏽"+"\n");
                    Progress.progressLong();
                    DisplayMethods.displayLedger();
                    System.out.println("\n" +"👈🏽GOING BACK TO LEDGER MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "D":
                    System.out.println("\n" + "GETTING YOUR DEPOSITS!👉🏽"+"\n");
                    Progress.progressLong();
                    DisplayMethods.displayDeposits();
                    System.out.println("\n" +"👈🏽GOING BACK TO LEDGER MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "P":
                    System.out.println("\n" + "GETTING YOUR PAYMENTS!👉🏽"+"\n");
                    Progress.progressLong();
                    DisplayMethods.displayPayments();
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

            System.out.print("Your Selection \uD83D\uDC49\uD83C\uDFFD");
            String input = scanner.next().trim();

            switch (input) {
                case "1":
                    System.out.println("\n" + "GOING TO MONTH TO DATE REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    FiltersAndSorts.getTransactionMonthToDate();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();

                    break;
                case "2":
                    System.out.println("\n" + "GOING TO PREVIOUS MONTH REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    FiltersAndSorts.getTransactionPrevMonth();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "3":
                    System.out.println("\n" + "GOING TO YEAR TO DATE REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    FiltersAndSorts.getTransactionYearToDate();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();

                    break;
                case "4":
                    System.out.println("\n" + "GOING TO PREVIOUS YEAR REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    FiltersAndSorts.getTransactionPrevYear();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "5":
                    System.out.println("\n" + "GOING TO VENDOR REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    FiltersAndSorts.getTransactionByVendor();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();
                    break;
                case "6":
                    System.out.println("\n" + "GOING TO CUSTOM REPORT!👉🏽"+"\n");
                    Progress.progressLong();
                    FiltersAndSorts.customSearch();
                    System.out.println("\n" +"👈🏽GOING BACK TO REPORTS MENU!"+"\n");
                    Progress.progressSmall();
                case "0":
                    running = false;
                default:
//                    System.out.println("Invalid option");
                    break;
            }
        }
    }


    /**
     * Displays a menu for sorting a list of transactions based on user preferences.
     * Users can choose to sort the list by date, amount, or vendor alphabetically, or choose not to sort.
     * After sorting, the sorted transactions are displayed.
     *
     * @param listToBeSorted The list of transactions to be sorted based on user selection.
     */
    public static void sortMenu(ArrayList listToBeSorted){
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("WOULD YOU LIKE TO SORT YOUR SEARCH?");
            System.out.println("Choose an option:");
            System.out.println("1) Sort By Date(Chronologically)");
            System.out.println("2) Sort By Amount");
            System.out.println("3) Sort By Vendor(Alphabetically)");
            System.out.println("0) NO SORT NEEDED RIGHT NOW");


            System.out.print("Your Selection \uD83D\uDC49\uD83C\uDFFD");
            String input = scanner.next().trim();

            switch (input) {
                case "1":
                    FiltersAndSorts.sortByDate(listToBeSorted);
                    running=false;
                    break;
                case "2":
                    FiltersAndSorts.sortByPrice(listToBeSorted);
                    running=false;
                    break;
                case "3":
                    FiltersAndSorts.sortByVendor(listToBeSorted);
                    running=false;
                    break;

                case "0":
                    System.out.println("PRINTING YOUR SEARCH REPORT NOW!");
                    FiltersAndSorts.noSort(listToBeSorted);
                    running=false;
                    break;
            }
        }
    }
}
