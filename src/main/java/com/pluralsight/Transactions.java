package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * The Transactions class represents a financial transaction with attributes including
 * description, vendor, date, time, amount, and transaction type (deposit or payment).
 */

public class Transactions {


//_________________________//
//    Attributes
//________________________//
    private String description;
    private String vendor;
    private LocalDate date;
    private LocalTime time;
    private double amount;
    private boolean isDeposit;
    private boolean isPayment;

/*
________________________
    Constructors
________________________
*/
    /**
     * Constructs a new Transactions object with the specified attributes.
     *
     * @param description The description or item name for the transaction.
     * @param vendor The name of the vendor associated with the transaction.
     * @param date The date of the transaction in the form of LocalDate.
     * @param time The time of the transaction in the form of LocalTime.
     * @param amount The transaction amount in USD. A positive amount indicates a deposit, while a negative amount indicates a payment.
     */
    public Transactions(String description, String vendor, LocalDate date, LocalTime time, double amount) {
        this.description = description;
        this.vendor = vendor;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.isDeposit = amount>=0?true:false;
        this.isPayment = amount<0?true:false;
    }


//_________________________//
//     Getters & Setters
//________________________//


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isDeposit() {
        return isDeposit;
    }

    public void setDeposit(boolean deposit) {
        isDeposit = deposit;
    }

    public boolean isPayment() {
        return isPayment;
    }

    public void setPayment(boolean payment) {
        isPayment = payment;
    }


//_________________________//
//     additional Methods
//________________________//

    /**
     * Prints the details of the transaction, including date, time, vendor, description, and amount.
     */
    public void print(){
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+"Date @ Time :" + date + " @ " +time);
        System.out.println("Vendor: "+ vendor);
        System.out.println("Description: " + description);
        System.out.println("Amount: " +ConsoleColors.RESET + (amount>=0? ConsoleColors.GREEN_BOLD_BRIGHT+amount+ConsoleColors.RESET:ConsoleColors.RED_BOLD_BRIGHT+amount+ConsoleColors.RESET));
        System.out.println(ConsoleColors.WHITE_UNDERLINED+"                    "+ConsoleColors.RESET);
    }

    /**
     * Returns a string representation of the Transactions object.
     *
     * @return A string containing details of the transaction, including description, vendor, date, time, amount, and transaction type.
     */
    @Override
    public String toString() {
        return "Transactions{" +
                "description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", amount=" + amount +
                ", isDeposit=" + isDeposit +
                ", isPayment=" + isPayment +
                '}';
    }
}
