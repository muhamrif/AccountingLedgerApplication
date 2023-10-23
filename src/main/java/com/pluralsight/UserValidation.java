package com.pluralsight;

import java.util.Scanner;

public class UserValidation {

    static Scanner scanner = new Scanner(System.in);
    public static String depositOrPayment (){

        boolean flag = true;
        String depositValidation="";
        while(flag) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+"Enter ( D ),  if this Transaction is a DEPOSIT (ADDING MONEY TO YOUR ACCOUNT)"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT+"Enter ( P ),  if this Transaction is a PAYMENT (REMOVING MONEY TO YOUR ACCOUNT)"+ConsoleColors.RESET);
            depositValidation = scanner.next();

            if(depositValidation.equalsIgnoreCase("d") || depositValidation.equalsIgnoreCase("p")){
                flag=false;
            }else{
                System.out.println("🚨⚠️INVALID entry, Please enter D for Deposit OR P for Payment⚠️🚨");
            }
        }
        return depositValidation;
    }

    public static String yearDate() {

        boolean flag = true;
        String year = "";
        while (flag) {
            System.out.println("📅📆Please enter the YEAR of your transaction in the YYYY format, you can enter any year from 1900 to 2023:📅📆");
            year = scanner.next();
            scanner.nextLine();

            if(year.chars().allMatch( Character::isDigit )) {
                if (Integer.parseInt(year) >= 1900 && Integer.parseInt(year) <= 2023) {
                    flag = false;
                }
            }else{
            System.out.println("🚨⚠️INVALID entry, Please enter a VALID Year to continue.⚠️🚨");
        }
        }
        return year;
    }

    public static String monthDate() {
        boolean flag = true; String month = "";
        while (flag) {
            System.out.println("📅📆Please enter the MONTH of your transaction in the MM format, you can enter any month from 01 to 12:📅📆");
            month = scanner.next();scanner.nextLine();
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

    public static String dayDate(String month) {
        boolean flag = true; String day = "";
        while (flag) {
            System.out.println("📅📆Please enter the DAY of your transaction in the DD format, you can enter any month from 01 to 31:📅📆");
            day = scanner.next();scanner.nextLine();
            if(day.chars().allMatch( Character::isDigit )) {
                if (Integer.parseInt(day) >= 01 && Integer.parseInt(day) <= 31) {
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


    public static String hourTime() {

        boolean flag = true;
        String hour = "";
        while (flag) {
            System.out.println("⏳⏳Please enter the Hour of your transaction in the HH format, you can enter any month from 01 to 24⏳⏳:");
            hour = scanner.next();
            scanner.nextLine();
            if((hour.chars().allMatch( Character::isDigit )) && (Integer.parseInt(hour) >= 01 && Integer.parseInt(hour) <= 24)) {
                    flag = false;
            }else{
                System.out.println("🚨⚠️INVALID entry, Please enter a VALID HOUR in HH format to continue.⚠️🚨");
            }
        }
        return hour;
    }

    public static String minuteTime() {

        boolean flag = true;
        String minute = "";
        while (flag) {
            System.out.println("⏳⏳Please enter the MINUTE of your transaction in the MM format, you can enter any month from 00 to 59⏳⏳:");
            minute = scanner.next();
            scanner.nextLine();
            if((minute.chars().allMatch( Character::isDigit )) && (Integer.parseInt(minute) >= 00 && Integer.parseInt(minute) <= 59)) {
                flag = false;
            }else{
                System.out.println("🚨⚠️INVALID entry, Please enter a VALID MINUTES in MM format to continue.⚠️🚨");
            }
        }
        return minute;
    }

    public static String secondTime() {

        boolean flag = true;
        String second = "";
        while (flag) {
            System.out.println("⏳⏳Please enter the SECONDS of your transaction in the SS format, you can enter any month from 00 to 59⏳⏳:");
            second = scanner.next();
            scanner.nextLine();
            if((second.chars().allMatch( Character::isDigit )) && (Integer.parseInt(second) >= 00 && Integer.parseInt(second) <= 59)) {
                flag = false;
            }else{
                System.out.println("🚨⚠️INVALID entry, Please enter a VALID SECONDS in SS format to continue.⚠️🚨");
            }
        }
        return second;
    }

    public static String transactionVendor(){
        System.out.println("Please enter the vendor for this Transaction🚙:");
        String vendor = scanner.next();
        scanner.nextLine();
        return vendor;
    }

    public static String transactionDescription(){
        System.out.println("Please enter the description or item name for this Transaction📝:");
        String description = scanner.next();
        scanner.nextLine();
        return description;
    }

    public static double transactionAmount(){
        System.out.println("Please enter the amount for this Transaction 💰:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        return amount;
    }


}
