package BudgetTracker.userinterface;

import BudgetTracker.domain.*;
import BudgetTracker.logic.*;

import java.nio.file.NoSuchFileException;
import java.util.*;
import static java.lang.System.*;

public class UI implements UserInputFunctionalities
{
    private Scanner scanner;
    private Account account;
    private AccountManager accountManager;
    private BudgetManager budgetManager;

    private SpendingCategory spendingCategory;
    public UI()
    {
        this.scanner = new Scanner(in);
        this.accountManager = new AccountManager();
        accountManager.storeAccountsToList();
    }

    public void start()
    {

        while (true)
        {
            out.println("""
                    \nPlease choose a number to continue
                    [1] Open account
                    [2] Plan your budget
                    [3] Exit
                    """);

            int userChoice = validIntegerChoice(1, 3);

            if (userChoice == 1) accountOptions();
            else if (userChoice == 2) budgetOptions();
            else if (userChoice == 3) break;
        }
    }

    // Account
    public void accountOptions()
    {
        while (true)
        {
            out.println("""
                \nAccount
                [1] Sign in account
                [2] Deposit money
                [3] Check balance
                [4] Withdraw money
                [5] Check savings
                [6] Exit
                """);

            int userChoice = validIntegerChoice(1, 6);
            if (userChoice == 1) signInAccount();
            else if (userChoice == 2) depositMoney();
            else if (userChoice == 3) checkBalance();
            else if (userChoice == 4) withdrawMoney();
            else if (userChoice == 5) checkSavings();
            else if (userChoice == 6)
            {
                out.println("Exiting...");
                break;
            }
        }
    }

    public void signInAccount()
    {
        while (true)
        {
            out.println("""
                Sign-in Account
                [1] Create Account
                [2] Login Account
                [3] Exit
                """);

            int userChoice = validIntegerChoice(1, 3);

            if (userChoice == 1)
            {
                createAccount();
                break;
            }
            else if (userChoice == 2)
            {
                loginAccount();
                break;
            }
            else if (userChoice == 3)
            {
                out.println("Exiting...");
                break;
            }
        }
    }
    public void createAccount()
    {
        out.println("Please enter the following information: ");
        out.println("First name: ");
        String firstName = validStringInput();
        out.println("Last name: ");
        String lastName = validStringInput();

        if (accountManager.exists(firstName, lastName))
        {
            out.println("Account is already existing!");
        }
        else
        {
            String sex = chooseSex();
            out.println("Initial balance: ");
            double initialBalance = validDoubleInput();

            account = new Account(firstName, lastName, sex, initialBalance, 0.0);

            accountManager.add(account);
            accountManager.storeToFile();
            budgetManager = new BudgetManager(account);
        }
    }

    public String chooseSex()
    {
        String sex = "";
        while (true)
        {
            out.println("""
                    Sex
                    [1] Male
                    [2] Female
                    """);
            int choice = validIntegerChoice(1, 2);
            if (choice == 1)
            {
                sex = Person.Sex.MALE.toString();
                break;
            }
            else if (choice == 2)
            {
                sex = Person.Sex.FEMALE.toString();
                break;
            }
        }
        return sex;
    }


    public void loginAccount()
    {
        out.println("Please enter the following information: ");
        out.println("First name: ");
        String firstName = validStringInput();
        out.println("Last name: ");
        String lastName = validStringInput();

        account = accountManager.retrieveAccount(firstName, lastName);

        if (account == null)
        {
            out.println("Account not found in the database");
        }
        else
        {
            budgetManager = new BudgetManager(account);

            if (budgetManager.fileExists()) budgetManager.storeExpensesToList();
        }
    }
    public void depositMoney()
    {
        if (account == null)
        {
            out.println("Please create or login to your account first");
        }
        else
        {
            out.println("Amount to deposit: ");
            double depositMoney = validDoubleInput();
            account.deposit(depositMoney);
            account.checkBalance();
        }

    }

    public void withdrawMoney()
    {
        if (account == null)
        {
            out.println("Please create or login to your account first");
        }
        else
        {
            out.println("Amount to withdraw: ");
            double withdrawMoney = validDoubleInput();
            account.withdraw(withdrawMoney);
            account.checkBalance();
        }
    }

    public void checkBalance()
    {
        if (account == null)
        {
            out.println("Please create or login to your account first");
        }
        else
        {
            account.checkBalance();
        }
    }

    public void checkSavings()
    {
        if (account == null)
        {
            out.println("Please create or login to your account first");
        }
        else
        {
            account.checkSavings();
        }
    }

    // Budget
    public void budgetOptions()
    {
        if (account == null)
        {
            out.println("Please sign in to your account first!");
        }
        else
        {
            while (true)
            {
                out.println("""
                    \nBudget
                    [1] Create a list of expenses
                    [2] Display total expense
                    [3] Display monthly budget data
                    [4] Save money
                    [5] Exit
                    """);

                int userChoice = validIntegerChoice(1, 5);
                if (userChoice == 1) createAListOfExpenses();
                else if (userChoice == 2) displayTotalExpenses();
                else if (userChoice == 3) displayMonthlyBudgetData();
                else if (userChoice == 4) saveMoney();
                else if (userChoice == 5)
                {
                    out.println("Exiting...");
                    break;
                }
            }
        }

    }

    public void createAListOfExpenses()
    {
        while (true)
        {
            out.println("""
                    Create list of expenses
                    [1] Add
                    [2] Remove
                    [3] Clear  
                    [4] Exit
                    """);

            int userChoice = validIntegerChoice(1, 4);

            if (userChoice == 1) addExpense();
            else if (userChoice == 2) removeExpense();
            else if (userChoice == 3) clearAllExpenses();
            else if (userChoice == 4)
            {
                budgetManager.storeToFile();
                out.println("Exiting...");
                break;
            }
        }
    }

    public void addExpense()
    {
        while (true)
        {
            out.println("""
                    Add expense
                    [1] Add
                    [2] Exit
                    """);

            int userChoice = validIntegerChoice(1, 2);

            if (userChoice == 1) storeExpenses();
            else if (userChoice == 2)
            {
                out.println("Exiting...");
                break;
            }
        }

    }

    public void storeExpenses()
    {
        spendingCategory = new SpendingCategory("", 0.0, SpendingCategory.SpendingType.NEEDS.toString());

        out.println("Spending Category: ");
        String expense = validStringInput();
        spendingCategory.setSpendingCategory(expense);
        out.println("Cost: ");
        Double cost = validDoubleInput();
        spendingCategory.setCost(cost);

        while (true)
        {
            out.println("""
                    Spending Type
                    [1] Wants
                    [2] Needs
                    """);

            int spendingTypeChoice = validIntegerChoice(1, 2);

            if (spendingTypeChoice == 1)
            {
                spendingCategory.setSpendingType(SpendingCategory.SpendingType.WANTS.toString());
                break;
            }
            else
            {
                spendingCategory.setSpendingType(SpendingCategory.SpendingType.NEEDS.toString());
                break;
            }
        }
        budgetManager.spend(spendingCategory);
    }

    public void removeExpense()
    {
        while (true)
        {
            out.println("""
                    Remove expense
                    [1] Remove
                    [2] Exit
                    """);

            int userChoice = validIntegerChoice(1, 2);
            if (userChoice == 1)
            {
                out.println("Remove an expense: ");
                String expenseToBeRemoved = validStringInput();
                budgetManager.remove(expenseToBeRemoved);
                out.println(expenseToBeRemoved + " removed from the expense list successfully.");
            }
            else if (userChoice == 2)
            {
                out.println("Exiting...");
                break;
            }
        }
    }


    public void clearAllExpenses()
    {
        while (true)
        {
            out.println("""
                    Clear all expenses from the list
                    [1] Yes
                    [2] No
                    """);

            int userChoice = validIntegerChoice(1, 2);

            if (userChoice == 1)
            {
                budgetManager.clear();
                out.println("Deleted successfully");
            }
            else if (userChoice == 2)
            {
                out.println("Exiting...");
                break;
            }
        }
    }

    public void displayTotalExpenses()
    {
        try
        {
            if (budgetManager.fileExists()) budgetManager.checkTotalExpense();
            else throw new Exception();
        }
        catch (Exception e)
        {
            err.println("Please create an expense list first");
        }
    }

    public void displayMonthlyBudgetData()
    {
        try
        {
            if (budgetManager.fileExists())
            {
                setIncome();
                budgetManager.displayMonthlyBudgetData();
            }
            else throw new Exception();
        }
        catch (Exception e)
        {
            err.println("Please create an expense list first");
        }
    }

    public void saveMoney()
    {
        out.println("Save: ");
        budgetManager.save(validDoubleInput());
        account.checkSavings();
        accountManager.storeToFile();
    }

    public void setIncome()
    {
        out.println("Expected Monthly Income: ");
        budgetManager.setIncome(validDoubleInput());
    }
}
