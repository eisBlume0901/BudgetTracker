import BudgetTracker.domain.Account;
import BudgetTracker.domain.Person;
import BudgetTracker.domain.SpendingCategory;
import BudgetTracker.logic.AccountManager;
import BudgetTracker.logic.BudgetManager;
import BudgetTracker.userinterface.UI;

public class Main
{
    public static void main(String[] args)
    {
//        UI ui = new UI();
//        ui.start();


        AccountManager accountManager = new AccountManager();
        accountManager.storeAccountsToList();
        Account account = accountManager.retrieveAccount("Emerald", "Greenleaf");
        System.out.println(accountManager.exists("Emerald", "Greenleaf"));
        BudgetManager budgetManager = new BudgetManager(account);
        budgetManager.storeExpensesToList();
        budgetManager.displayMonthlyBudgetData();

    }
}