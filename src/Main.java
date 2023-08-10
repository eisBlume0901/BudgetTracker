import BudgetTracker.domain.*;
import BudgetTracker.logic.*;
import BudgetTracker.userinterface.UI;

public class Main
{
    public static void main(String[] args)
    {
        UI ui = new UI();
        ui.start();

        // For debugging purposes
//        AccountManager accountManager = new AccountManager();
//        accountManager.storeAccountsToList();
//        Account account = accountManager.retrieveAccount("Emerald", "Greenleaf");
//        System.out.println(accountManager.exists("Emerald", "Greenleaf"));
//        BudgetManager budgetManager = new BudgetManager(account);
//        budgetManager.storeExpensesToList();
//        budgetManager.displayMonthlyBudgetData();

    }
}