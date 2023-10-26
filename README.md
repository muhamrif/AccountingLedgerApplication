
# Accounting Ledger Application

A Java based CLI application, where users can signup and login to securely record their transactions,including Deposits and Payments, in a Ledger. Along with the keeping the record, users can filter by specific dates and by vendors. After recording their transactions, users can view and filter the transactions by Deposits and/or Payments.


## Tech Stack

For this Project I used:
- OS: Mac
- CLI: Mac Terminal
- IDE: Intellij IDEA
- Language: Java
- JAVADOC: documentation
- JDK: 17SE
- Git/GitHub: for version control and to manage source code
- Maven: to manage Java project building process



## Features

- [Login](#first-time-signing-up-and-logging-in)
- [Sign up](#first-time-signing-up-and-logging-in)
- [Ability to view the Total of the Ledger account in $USD](#adding-transaction-datetime-manually)
- [Ability to record a Deposit Transaction](#adding-a-transaction)
- [Ability to record a Payment Transaction](#adding-a-transaction)
- [Ability to view Ledger and all Transactions](#in-ledger-showing-deposits-only)
- [Ability to view Deposits Only](#in-ledger-showing-deposits-only)
- [Ability to view Payments](#in-ledger-showing-payments-only)
- [Ability to filter Transactions BY Month to Date](#in-reports-all-reports-for-month-to-date-)
- [Ability to filter Transactions BY Previous Month](#in-reports-all-reports-for-month-to-date-)
- [Ability to filter Transactions BY Year to Date](#in-reports-all-reports-for-year-to-date)
- [Ability to filter Transactions BY Previous Year](#in-reports-all-reports-for-year-to-date)
- [Ability to filter Transactions BY Vendor](#in-reports-all-reports-for-a-user-selected-vendor)
- Ability to filter Transactions BY a custom user provided values.

## Flow Of Order
![Legder Flow Chart.png](graphics%2FLegder%20Flow%20Chart.png)
## Demo

#### Initial Loading
![InitialLoading.gif](graphics%2FInitialLoading.gif)

#### First Time Signing up And Logging In
![FirstTimeSigningUpAndLogginIn.gif](graphics%2FFirstTimeSigningUpAndLogginIn.gif)

#### Adding A Transaction
![AddingATransaction.gif](graphics%2FAddingATransaction.gif)

#### Viewing Ledger
![ViewingLedger.gif](graphics%2FViewingLedger.gif)

#### Viewing Reports
![CheckingReports.gif](graphics%2FCheckingReports.gif)

#### Signing Off
![SigningOff.gif](graphics%2FSigningOff.gif)

## Screenshots

## In Ledger: Showing Deposits Only
![OnlyDeposits.png](graphics%2FOnlyDeposits.png)

### In Ledger: Showing Payments Only
![OnlyPayments.png](graphics%2FOnlyPayments.png)

### In Reports: All Reports For Month To Date 
![ReportsMonthToDate.png](graphics%2FReportsMonthToDate.png)

### In Reports: All Reports For Year To Date
![ReportsYearToDate.png](graphics%2FReportsYearToDate.png)

### In Reports: All Reports For A User Selected Vendor
![ReportsByVendor.png](graphics%2FReportsByVendor.png)

## Special Feature
[CodeWars:Pig Latin Hash](https://www.codewars.com/kata/520b9d2ad5c005041100000f/java)
![PIgLatin CodeWars.png](graphics%2FPIgLatin%20CodeWars.png)
```  
//https://www.codewars.com/kata/520b9d2ad5c005041100000f/java
    //Pig Latin Hash
    public static String passwordHashPigLatin(String password){
        // Split the input sentence into words array
        String[] words = password.split(" ");
        // Process each word and convert it to PigLatin.
        for (int i = 0; i < words.length; i++) {
            // If the word contains non-alphabet characters, leave it untouched.
            if (!words[i].matches("^[a-zA-Z]+$")) {
                continue;
            }
            // Move the first letter of the word to the end and add "ay" to the end.
            words[i] = words[i].substring(1) + words[i].charAt(0) + "ay";
        }
        // Reconstruct the transformed sentence.
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word).append(" ");
        }

        return sb.toString().trim();
    };
    
 ```
### User Registers Using Text:
![UserRegiter.png](graphics%2FUserRegiter.png)
### Application Strores The Password in File After Hashing (Pig Latin)
![Hash.png](graphics%2FHash.png)



## Roadmap-Coming Months

- More SECURED storage of user data.

- User ability to remove a transaction.

- User ability to edit a transaction.


## Run Locally

Clone the project

```bash
  git clone https://github.com/muhamrif/AccountingLegderApplication
```

Go to the src Directory

```bash
  cd src/main/java/com/pluralsight
```

Complile the code

```bash
 javac Screen.java
```

Start the CLI program

```bash
  java Screen.Class
```
## JavaDoc
[JavaDoc for AccountingLedgerApplication](https://muhamrif.github.io/JavaDocAccountingLedgerApplication/com/pluralsight/package-summary.html)
![JavaDoc1.png](graphics%2FJavaDoc1.png)
![JavaDoc2.png](graphics%2FJavaDoc2.png)

## Authors

- [@muhmarif](https://www.github.com/muhamrif)
- [@RayMaroun](https://github.com/RayMaroun) (Project Skeleton)

## Feedback

If you have any feedback, please reach out to at muhammad.hamza6415@gmail.com

