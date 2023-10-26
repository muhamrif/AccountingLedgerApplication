package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The FilterAndSorts Class contains all the methods will be used to Filter And Sort transactions.
 */
public class FiltersAndSorts {

    /**
     * Displays a report of all transactions for the current month.
     */
    public static void getTransactionMonthToDate(){
        int counter = 0;
        System.out.println(ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+"DISPLAYING TRANSACTION REPORT OF ALL TRANSACTION(S) MONTH TO DATE: "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);

        LocalDate firstOfMonth= LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
        for (Transactions x:Screen.transactions){
            if (x.getDate().isAfter(firstOfMonth)&&(x.getDate().isBefore(LocalDate.now()))) {
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
        for (Transactions x:Screen.transactions){
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

        for (Transactions x:Screen.transactions){
            if ((x.getDate().getYear())==LocalDate.now().getYear() && x.getDate().isBefore(LocalDate.now())) {
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
        for (Transactions x:Screen.transactions){
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
        System.out.print("Please Enter the name of the VENDOR for transactions:👉🏽");
        String vendor = input.next();
        input.nextLine();

        int counter = 0;
        System.out.println(ConsoleColors.WHITE_UNDERLINED+ConsoleColors.WHITE_BOLD_BRIGHT+"DISPLAYING TRANSACTION REPORT OF ALL TRANSACTION(S) FOR "+vendor.toUpperCase()+": "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);
        for (Transactions x:Screen.transactions){
            if (x.getVendor().contains(vendor)){
                x.print();
                counter++;
            }
        }
        if (counter==0) System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🫤NO TRANSACTION REPORT AVAILABLE FOR:" +vendor+ConsoleColors.RESET);

    }


    /**
     * Allows the user to perform a custom search on a list of transactions based on specified criteria.
     * The user can input a date range, description, vendor name, and USD amount range for the search.
     * The user has the choice to not put a variable, and this method will set that variable to a default value.
     * Transactions that match the criteria are added to a list and then displayed.
     */
    public static void customSearch(){
        //https://salesforce.stackexchange.com/questions/8456/how-to-get-the-smallest-earliest-possible-date-value
        ArrayList<Transactions> ListToBeSorted = new ArrayList<Transactions>();
        Scanner scanner = new Scanner(System.in);

        String earliestDateInJava = "1700-01-01";
        String latestDateInJava = "4000-12-31";

        LocalDate startDate;
        while (true){
            try {
                System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Please Enter the start date for your search (yyyy-MM-dd):👉🏽 "+ConsoleColors.RESET);
                String startDateInput = scanner.nextLine();
                startDate = startDateInput.isEmpty() ? LocalDate.parse(earliestDateInJava, Screen.DATE_FORMATTER) : LocalDate.parse(startDateInput, Screen.DATE_FORMATTER);
                break;
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🚨⚠️INVALID entry, Please enter a VALID DATE to continue.⚠️🚨"+ConsoleColors.RESET);
            }
        }

        LocalDate endDate;
        while (true){
            try {
                System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Please Enter the end date for your search (yyyy-MM-dd):👉🏽 "+ConsoleColors.RESET);
                String endDateInput = scanner.nextLine();
                endDate = endDateInput.isEmpty()?LocalDate.parse(latestDateInJava, Screen.DATE_FORMATTER):LocalDate.parse(endDateInput, Screen.DATE_FORMATTER);
                break;
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🚨⚠️INVALID entry, Please enter a VALID DATE to continue.⚠️🚨"+ConsoleColors.RESET);
            }
        }

        System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Please Enter PART OR WHOLE of description/item name for your search:👉🏽 "+ConsoleColors.RESET);
        String description = scanner.nextLine().toLowerCase();
        description = description.isEmpty()?"":description;

        System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Please Enter PART OR WHOLE of the vendor name for your search:👉🏽 "+ConsoleColors.RESET);
        String vendor = scanner.nextLine().toLowerCase();
        vendor = vendor.isEmpty()?"":vendor;


        double minAmount;
        while (true){
            try {
                System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Please Enter MIN USD amount for your search in USD (NEGATIVE NUMBERS ALLOWED) :$"+ConsoleColors.RESET);
                String minAmountEntry = scanner.nextLine();
                minAmount = minAmountEntry.isEmpty()? Double.NEGATIVE_INFINITY: Double.parseDouble(minAmountEntry);
                break;
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🚨⚠️INVALID entry, Please enter a VALID AMOUNT (numbers only) to continue.⚠️🚨"+ConsoleColors.RESET);
            }
        }

        double maxAmount;
        while (true){
            try {
                System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT+"Please Enter MAX USD amount for your search in USD (NEGATIVE NUMBERS ALLOWED) :$"+ConsoleColors.RESET);
                String maxAmountEntry = scanner.nextLine();
                maxAmount = maxAmountEntry.isEmpty()? Double.POSITIVE_INFINITY: Double.parseDouble(maxAmountEntry);
                break;
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"🚨⚠️INVALID entry, Please enter a VALID AMOUNT (numbers only) to continue.⚠️🚨"+ConsoleColors.RESET);
            }
        }

        for (Transactions x:Screen.transactions){
            double xamount = x.getAmount();
            LocalDate xdate = x.getDate();
            String xdescription = x.getDescription().toLowerCase();
            String xvendor = x.getVendor().toLowerCase();
            if (    xdate.isAfter(startDate) &&
                    xdate.isBefore(endDate) &&
                    xamount>=minAmount &&
                    xamount<=maxAmount &&
                    xdescription.contains(description) &&
                    xvendor.contains(vendor)){
                ListToBeSorted.add(x);
            }
        }
        Menus.sortMenu(ListToBeSorted);
    }


    /**
     * Sorts a list of transactions by their absolute USD amount (price).
     * The sorted transactions are then printed to the console.
     * @param listToBeShorted list of transactions that fit the criteria that User entered.
     */
    public static void sortByPrice(ArrayList listToBeShorted){
        ArrayList<Transactions> sortedList = new ArrayList<>(listToBeShorted);
        Comparator<Transactions> amountComparator = Comparator.comparingDouble(obj -> Math.abs(obj.getAmount()));
        sortedList.sort(amountComparator);
        for (Transactions x : sortedList) {
            x.print();
        }
    }

    /**
     * Sorts a list of transactions by their date, displaying the latest transactions first.
     * The sorted transactions are then printed to the console.
     * @param listToBeSorted list of transactions that fit the criteria that User entered.
     */
    public static void sortByDate(ArrayList<Transactions> listToBeSorted) {
        ArrayList<Transactions> sortedList = new ArrayList<>(listToBeSorted);
        Comparator<Transactions> dateComparator = Comparator.comparing(Transactions::getDate);
        Collections.sort(sortedList, dateComparator.reversed());//reversing will show latest transaction first
        for (Transactions transaction : sortedList) {
            transaction.print();
        }
    }

    /**
     * Sorts a list of transactions alphabetically by vendor name.
     * The sorted transactions are then printed to the console.
     * @param listToBeSorted list of transactions that fit the criteria that User entered.
     */
    public static void sortByVendor(ArrayList<Transactions> listToBeSorted) {
        ArrayList<Transactions> sortedList = new ArrayList<>(listToBeSorted);
        Comparator<Transactions> vendorComparator = Comparator.comparing(Transactions::getVendor);
        sortedList.sort(vendorComparator);// sorting alphabetically
        for (Transactions transaction : sortedList) {
            transaction.print();
        }
    }

    public static void noSort(ArrayList<Transactions> listToBeSorted){
        for (Transactions transaction : listToBeSorted) {
            transaction.print();
        }
    }

}
