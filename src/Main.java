import BudgetTracker.domain.Account;
import BudgetTracker.domain.Person;
import BudgetTracker.domain.SpendingCategory;
import BudgetTracker.logic.BudgetManager;
import BudgetTracker.userinterface.UI;

public class Main
{
    public static void main(String[] args)
    {
//        UI ui = new UI();
//        ui.start();
        BudgetManager bm = new BudgetManager(new Account("Mary Kate", "Anecito", Person.Sex.FEMALE.toString(), 10000.0, 0.0));
        bm.createBudgetFile();
        bm.spend(new SpendingCategory("Utilities", 900.0, SpendingCategory.SpendingType.NEEDS.toString()));
        bm.spend(new SpendingCategory("Grocery", 500.0, SpendingCategory.SpendingType.NEEDS.toString()));
        bm.storeToFile();
        Double total = bm.sumOfExpenses(SpendingCategory.SpendingType.NEEDS);
        System.out.println(total);
        bm.remove("Grocery");
        System.out.println(bm.displayExpenses());
        bm.storeToFile();
        bm.storeExpensesToList();
        bm.displayMonthlyBudgetData();


    }
}