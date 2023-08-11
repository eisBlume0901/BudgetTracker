# Simple-Budget-Tracker

[Account](#moneywithwings-features-available-for-account-section)
[Budget](#ledger-features-available-for-budget-section)

This is my second Java project that allows user to create an account and plan for their budget.
The account section allows user to create or login to their account using their first name and last name.
After signing an account, the user is allowed to create his or her budget plan. I created this project to
apply what I have learned from University of Helsinki's MOOC.fi Java II Programming. This project gave me
deeper understanding of object-oriented principles in Java and the use of packages and imports (such as PrintWriter
and File API).

## Getting Started
To launch the program, follow these simple steps
1. Go to src folder
2. Go to the `Main` class.
3. Make sure that the following code is present
    ```java
    import BudgetTracker.userinterface.*;
    
    public class Main
    {
        public static void main(String[] args)
        {
            UI ui = new UI();
            ui.start();
        }
    }
    ```
4. Run the `Main` class to execute the program.
5. The program must start with the following
   ```
   Please choose a number to continue
   [1] Open account
   [2] Plan your budget
   [3] Exit
   ```

## Functionality
Once the program is executed, users can either create or login an account which allows them to
explore the functionalities available for account and budget section. 

### :money_with_wings: Features available for Account Section
1. Sign-in account
   1. Create Account - creates a new account and store it to `List of Accounts` file text
   2. Login Account - retrieves the matched account to the `List of Accounts` file text. 
2. Deposit money - can only add amount to an account's balance if there is an existing account
3. Check balance - displays account's balance at current time
4. Withdraw money - can only subtract the amount to an account's balance if there is an existing account
5. Check savings - displays account's savings at current time

### :ledger: Features available for Budget Section
1. Create a list of expenses 
   1. Add - add new spending category to list and store it to an account's budget file
   2. Remove - remove existing spending category to list and store it to an account's budget file
   3. Clear - delete all spending category to list and store it to an account's budget file
2. Display total expenses - display existing total expenses retrieved from the file or from the newly created
list of expenses
3. Display monthly budget data - display budget data statistics by showing the total expenses and the total
savings 
4. Save money - save money to the account and store the updated value to `List of Accounts` file

### List of Accounts file
1. Format Requirements:
   The file should be in Comma-Separated Values (CSV) format so that it can be read and processed.
   The following format as exemplified as shown below:

   ```
   File name:
   List of Accounts
   
   Format:
   FirstName,LastName,Sex,InitialBalance,Savings

   Contents:
   Melina,Huffman,FEMALE,100000.0,0.0
   Larissa,Hopkins,FEMALE,90000.0,7500.0
   Orion,Jacobson,MALE,85000.0,2500.0
   Agustin,Warren,MALE,45703.0,15000.0
   Scarlet,Hayden,FEMALE,65478.0,5100.0
   ```
   You can change the contents of the file as long as you follow the CSV format.
2. Location:
   There is a ready-file named `List of Accounts` inside the BudgetTracker folder. Please do not change the file name or 
else the code would not be read and processed. You can create a new file as long as you revise the file name inside the 
`AccountManager` class.
   ```java
   package BudgetTracker.logic;
   
   import BudgetTracker.domain.Account;
   import java.io.*;
   import java.nio.file.*;
   import java.util.*;
   
   public class AccountManager 
   {
      private List<Account> accountList;
      private File file;
      public AccountManager()
      {
          this.accountList = new ArrayList<>();
          this.file = new File("Lists of Accounts"); // Change the file name. Example: this.file = new File("Account Database")
      }
   }
   ```
   
### Budget File
1. Format Requirements:
   Everytime an account has been signed-in, it automatically creates a budget file created which can be used as a storage of expenses. 
The file format is always SURNAME YEAR MONTH and its content is always empty. This budget file is processed and used to calculate the 
expenses (total, by wants, by needs). You can input the expenses inside the budget file as long as you follow the CSV-format as shown below:
   ```
   File name:
   Huffman 2023 AUGUST
   
   Format:
   SpendingCategory,Cost,WANTS/NEEDS
   
   Contents:
   Utilities,9000.0,NEEDS
   Rent,15000.0,NEEDS
   Clothes,9700.0,WANTS
   Grocery,5500.0,NEEDS
   ```
2. Location:
   The location of a newly or existing budget file will be always be inside the BudgetTracker folder and not inside the src
folder. 