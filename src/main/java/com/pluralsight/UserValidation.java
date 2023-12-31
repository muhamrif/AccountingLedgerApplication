package com.pluralsight;
import java.io.IOException;
import java.util.Scanner;



/**
 *
 * The UserValidation class provides methods for validating user input related to transactions.
 * It includes methods for validating deposit or payment type, year, month, day, hour, minute, second,
 * vendor, description, and transaction amount.
 */
public class UserValidation {

    static Scanner scanner = new Scanner(System.in);


    /**
     * Validates the user's selection for deposit or payment type.
     * @return "d" for deposit or "p" for payment
     */
    public static String depositOrPayment (){

        boolean flag = true;
        String depositValidation="";
        while(flag) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"Enter ( D ),  if this Transaction is a DEPOSIT (ADDING MONEY TO YOUR ACCOUNT)"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"Enter ( P ),  if this Transaction is a PAYMENT (REMOVING MONEY TO YOUR ACCOUNT)"+ConsoleColors.RESET);
            System.out.print("Your Selection 👉🏽");
            depositValidation = scanner.next();
            System.out.println("\n");
            if(depositValidation.equalsIgnoreCase("d") || depositValidation.equalsIgnoreCase("p")){
                flag=false;
            }else{
                System.out.println("🚨⚠️INVALID entry, Please enter D for Deposit OR P for Payment⚠️🚨");
            }
        }
        return depositValidation;
    }


    /**
     * Validates the user's input for the transaction's year.
     * @return A valid year in the format "yyyy"
     */
    public static String yearDate() {

        boolean flag = true;
        String year = "";
        while (flag) {
            System.out.println("📅📆Please enter the YEAR of your transaction in the YYYY format, you can enter any year from 1900 to 2023:📅📆");
            System.out.print("Your Transaction's YEAR (yyyy) 👉🏽");
            year = scanner.next();
            System.out.println("\n");

            if(year.chars().allMatch( Character::isDigit )) {
                if (Integer.parseInt(year) >= 1900 && Integer.parseInt(year) <= 2023) {
                    flag = false;
                }else{
                    System.out.println("🚨⚠️INVALID entry, Please enter a VALID Year to continue.⚠️🚨");
                }
            }else{
            System.out.println("🚨⚠️INVALID entry, Please enter a VALID Year to continue.⚠️🚨");
        }
        }
        return year;
    }

    /**
     * Validates the user's input for the transaction's month.
     * @return A valid month in the format "MM"
     */
    public static String monthDate() {
        boolean flag = true; String month = "";
        while (flag) {
            System.out.println("📅📆Please enter the MONTH of your transaction in the MM format, you can enter any month from 01 to 12:📅📆");
            System.out.print("Your Transaction's Month (MM) 👉🏽");
            month = scanner.next();
            System.out.println("\n");
            if(month.chars().allMatch( Character::isDigit )) {
                if (Integer.parseInt(month) >= 01 && Integer.parseInt(month) <= 12) {
                    flag = false;
                }else{
                    System.out.println("🚨⚠️INVALID entry, Please enter a VALID Month to continue.⚠️🚨");
                }
            }else{
                System.out.println("🚨⚠️INVALID entry, Please enter a VALID Month to continue.⚠️🚨");
            }
        }return month;
    }

    /**
     * Validates the user's input for the transaction's day based on the selected month.
     * @param month The transaction's month in the format "MM"
     * @return A valid day in the format "dd"
     */
    public static String dayDate(String month) {
        boolean flag = true; String day = "";
        while (flag) {
            System.out.println("📅📆Please enter the DAY of your transaction in the DD format, you can enter any month from 01 to 31:📅📆");
            System.out.print("Your Transaction's Day (dd) 👉🏽");
            day = scanner.next();
            System.out.println("\n");
            if(day.chars().allMatch( Character::isDigit )) {
                //checks for if day is between 1 and 31
                if (Integer.parseInt(day) >= 01 && Integer.parseInt(day) <= 31) {
                    //checks the cross-reference for months and the number of their correct days
                    if((Integer.parseInt(month) == 01 ||Integer.parseInt(month) == 03 ||Integer.parseInt(month) == 05 ||Integer.parseInt(month) == 07 ||Integer.parseInt(month) == 8 ||Integer.parseInt(month) == 10 ||Integer.parseInt(month) == 12)&& Integer.parseInt(day) <= 31) {
                        flag = false;
                    }else if ((Integer.parseInt(month) == 04 ||Integer.parseInt(month) == 06 ||Integer.parseInt(month) == 9 ||Integer.parseInt(month) == 11 )&& Integer.parseInt(day) < 31) {
                        flag = false;
                    }else if ((Integer.parseInt(month) == 02) && Integer.parseInt(day) <= 29){
                        flag = false;
                    }else{
                        System.out.println("🚨⚠️INVALID entry, Please enter a VALID DAY to continue.⚠️🚨");
                    }
                }
            }else{
                System.out.println("🚨⚠️INVALID entry, Please enter a VALID DAY to continue.⚠️🚨");
            }
        }return day;
    }


    /**
     * Validates the user's input for the transaction's hour.
     * @return A valid hour in the format "HH"
     */
    public static String hourTime() {

        boolean flag = true;
        String hour = "";
        while (flag) {
            System.out.println("⏳⏳Please enter the Hour of your transaction in the HH format, you can enter any month from 01 to 24⏳⏳:");
            System.out.print("Your Transaction's Hour (HH) 👉🏽");
            hour = scanner.next();
            System.out.println("\n");
            //checks if user entered numbers only and its between 01 and 24
            if((hour.chars().allMatch( Character::isDigit )) && (Integer.parseInt(hour) >= 01 && Integer.parseInt(hour) <= 24)) {
                    flag = false;
            }else{
                System.out.println("🚨⚠️INVALID entry, Please enter a VALID HOUR in HH format to continue.⚠️🚨");
            }
        }
        return hour;
    }

    /**
     * Validates the user's input for the transaction's minute.
     * @return A valid minute in the format "MM"
     */
    public static String minuteTime() {

        boolean flag = true;
        String minute = "";
        while (flag) {
            System.out.println("⏳⏳Please enter the MINUTE of your transaction in the MM format, you can enter any month from 00 to 59⏳⏳:");
            System.out.print("Your Transaction's Minutes (MM) 👉🏽");
            minute = scanner.next();
            System.out.println("\n");
            if((minute.chars().allMatch( Character::isDigit )) && (Integer.parseInt(minute) >= 00 && Integer.parseInt(minute) <= 59)) {
                flag = false;
            }else{
                System.out.println("🚨⚠️INVALID entry, Please enter a VALID MINUTES in MM format to continue.⚠️🚨");
            }
        }
        return minute;
    }

    /**
     * Validates the user's input for the transaction's second.
     * @return A valid second in the format "SS"
     */
    public static String secondTime() {

        boolean flag = true;
        String second = "";
        while (flag) {
            System.out.println("⏳⏳Please enter the SECONDS of your transaction in the SS format, you can enter any month from 00 to 59⏳⏳:");
            System.out.print("Your Transaction's Seconds (SS) 👉🏽");
            second = scanner.next();
            System.out.println("\n");
            if((second.chars().allMatch( Character::isDigit )) && (Integer.parseInt(second) >= 00 && Integer.parseInt(second) <= 59)) {
                flag = false;
            }else{
                System.out.println("🚨⚠️INVALID entry, Please enter a VALID SECONDS in SS format to continue.⚠️🚨");
            }
        }
        return second;
    }

    /**
     * Validates the user's input for the transaction's vendor.
     * @return The vendor for the transaction
     */
    public static String transactionVendor(){
        System.out.println("Please enter the vendor for this Transaction🚙:");
        System.out.print("Vendor For Your Transaction 👉🏽");
        String vendor = scanner.next();
        System.out.println("\n");
        return vendor;
    }

    /**
     * Validates the user's input for the transaction's description or item name.
     * @return The description or item name for the transaction
     */
    public static String transactionDescription(){
        System.out.println("Please enter the description or item name for this Transaction📝:");
        System.out.print("Description Or Item Name For Your Transaction 👉🏽");
        String description = scanner.next();
        System.out.println("\n");
        return description;
    }

    /**
     * Validates the user's input for the transaction amount.
     * @return The transaction amount in USD
     */
    public static double transactionAmount(){

        boolean flag = true;
        double amount= 0;
        while (flag) {
            System.out.println("Please enter the amount for this Transaction 💰:");
            System.out.print("Amount in USD For Your Transaction 👉🏽$");
            String amountStr = scanner.next();
            System.out.println("\n");
            if((amountStr.chars().allMatch( Character::isDigit ))) {
                amount = Double.parseDouble(amountStr);
                flag = false;
            }else{
                System.out.println("🚨⚠️INVALID entry, Please ONLY enter DIGITS to continue.⚠️🚨");
            }
        }
        return amount;
    }


}
