
# Accounting Ledger Application

A Java based CLI application, where users can signup and login to securely record their transactions,including Deposits and Payments, in a Ledger. Along with the keeping the record, users can filter by specific dates and by vendors. After recording their transactions, users can view and filter the transactions by Deposits and/or Payments.

## Technology Used
<div align="center">

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Maven](https://img.shields.io/badge/maven-%23F24E1E.svg?style=for-the-badge&logo=maven&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![Markdown](https://img.shields.io/badge/markdown-%23F24E1E.svg?style=for-the-badge&logo=markdown&logoColor=white)
![JUnit](https://img.shields.io/badge/junit-%23F24E1E.svg?style=for-the-badge&logo=junit&logoColor=white)
![JavaDoc](https://img.shields.io/badge/javadoc-%23F24E1E.svg?style=for-the-badge&logo=javadoc&logoColor=white)
![Stack Overflow](https://img.shields.io/badge/-Stackoverflow-FE7A16?style=for-the-badge&logo=stack-overflow&logoColor=white)
![Google](https://img.shields.io/badge/google-4285F4?style=for-the-badge&logo=google&logoColor=white)
![YouTube](https://img.shields.io/badge/YouTube-%23FF0000.svg?style=for-the-badge&logo=YouTube&logoColor=white)
![Zoom](https://img.shields.io/badge/Zoom-2D8CFF?style=for-the-badge&logo=zoom&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)
![GeeksForGeeks](https://img.shields.io/badge/GeeksforGeeks-gray?style=for-the-badge&logo=geeksforgeeks&logoColor=35914c)
![Pluralsight](https://img.shields.io/badge/Pluralsight-EE3057?style=for-the-badge&logo=pluralsight&logoColor=white)
</div>



## Features

- [Login](#first-time-signing-up-and-logging-in)
- [Sign up](#first-time-signing-up-and-logging-in)
- [Ability to view the Total of the Ledger account in $USD](#adding-transaction-datetime-manually)
- [Ability to record a Deposit Transaction](#adding-a-transaction)
- [Ability to record a Payment Transaction](#adding-a-transaction)
- [Ability to view Ledger and all Transactions](#in-ledger-showing-deposits-only)
- [Ability to view Deposits Only](#in-ledger-showing-deposits-only)
- [Ability to view Payments](#in-ledger-showing-deposits-only)
- [Ability to filter Transactions BY Month to Date](#in-reports-all-reports-for-month-to-date-)
- [Ability to filter Transactions BY Previous Month](#in-reports-all-reports-for-month-to-date-)
- [Ability to filter Transactions BY Year to Date](#in-reports-all-reports-for-month-to-date-)
- [Ability to filter Transactions BY Previous Year](#in-reports-all-reports-for-month-to-date-)
- [Ability to filter Transactions BY Vendor](#in-reports-all-reports-for-a-user-selected-vendor)
- [Ability to filter Transactions BY a custom user provided values.](#in-reports-custom-search)
- [Ability to Sort By: Date, Price, Vendor(Alphabetically)](#in-reports-custom-search)

## Flow Of Order
![Legder Flow Chart.png](graphics%2FLegder%20Flow%20Chart.png)

## Code BreakDown
![CodeBreakdowndarkbg.png](graphics%2FCodeBreakdowndarkbg.png)

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

### In Reports: All Reports For Month To Date 
![ReportsMonthToDate.png](graphics%2FReportsMonthToDate.png)

### In Reports: All Reports For A User Selected Vendor
![ReportsByVendor.png](graphics%2FReportsByVendor.png)

### In Reports: Custom Search
![Custom Search1.png](graphics%2FCustom%20Search1.png)

### In Reports: Sorts
![SortSearch.png](graphics%2FSortSearch.png)

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
<!-- ## JavaDoc
[👉🏽JavaDoc for Muhamrif AccountingLedgerApplication👈🏽](https://muhamrif.github.io/JavaDocAccountingLedgerApplication/com/pluralsight/package-summary.html)
-->

## Authors

- [@muhmarif](https://www.github.com/muhamrif)
- [@RayMaroun](https://github.com/RayMaroun) (Project Skeleton)

## Feedback

If you have any feedback, please reach out to at muhammad.hamza6415@gmail.com

