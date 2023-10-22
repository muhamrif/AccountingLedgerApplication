package com.pluralsight;

import java.util.Scanner;

public class UserValidation {

    static Scanner scanner = new Scanner(System.in);
    public static String depositOrPayment (){

        boolean flag = true;
        String depositValidation="";
        while(flag) {
            System.out.println("Enter ( D ),  if this Transaction is a DEPOSIT (ADDING MONEY TO YOUR ACCOUNT)");
            System.out.println("Enter ( P ),  if this Transaction is a PAYMENT (REMOVING MONEY TO YOUR ACCOUNT)");
            depositValidation = scanner.next();

            if(depositValidation.equalsIgnoreCase("d") || depositValidation.equalsIgnoreCase("p")){
                flag=false;
            }else{
                System.out.println("INVALID entry, Please enter D for Deposit OR P for Payment:");
            }
        }
        return depositValidation;
    }

    public static String yearDate() {

        boolean flag = true;
        String year = "";
        while (flag) {
            System.out.println("Please enter the Year of your transaction in the YYYY format, you can enter any year from 1900 to 2023:");
            year = scanner.next();
            scanner.nextLine();

            if(year.chars().allMatch( Character::isDigit )) {
                if (Integer.parseInt(year) >= 1900 && Integer.parseInt(year) <= 2023) {
                    flag = false;
                }
            }else{
            System.out.println("INVALID entry, Please enter a VALID Year to continue.");
        }
        }
        return year;
    }

    public static String monthDate() {
        boolean flag = true; String month = "";
        while (flag) {
            System.out.println("Please enter the Month of your transaction in the MM format, you can enter any month from 01 to 12:");
            month = scanner.next();scanner.nextLine();
            if(month.chars().allMatch( Character::isDigit )) {
                if (Integer.parseInt(month) >= 01 && Integer.parseInt(month) <= 12) {
                    flag = false;
                }else{
                    System.out.println("INVALID entry, Please enter a VALID Month to continue.");
                }
            }else{
                System.out.println("INVALID entry, Please enter a VALID Month to continue.");
            }
        }return month;
    }

    public static String dayDate(String month) {
        boolean flag = true; String day = "";
        while (flag) {
            System.out.println("Please enter the DAY of your transaction in the DD format, you can enter any month from 01 to 31:");
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
                        System.out.println("INVALID entry, Please enter a VALID DAY to continue.");
                    }
                }
            }else{
                System.out.println("INVALID entry, Please enter a VALID DAY to continue.");
            }
        }return day;
    }


}
