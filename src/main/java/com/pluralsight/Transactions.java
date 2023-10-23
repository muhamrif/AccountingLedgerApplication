package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

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
