package BudgetTracker.logic;

import BudgetTracker.domain.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
public class BudgetManager implements FileProcessor
{
    private List<SpendingCategory> spendingCategoryList;
    private Account account;
    private LocalDateTime localDateTime;
    private File file;

    private Double income;

    public BudgetManager(Account account)
    {
        this.account = account;
        this.spendingCategoryList = new LinkedList<>();
        this.localDateTime = LocalDateTime.now();
        this.file = new File(account.getLastName()
                + " " + this.localDateTime.getYear() + " " + this.localDateTime.getMonth());
        this.income = 0.0;
    }

    public void spend(SpendingCategory spendingCategory)
    {
        spendingCategoryList.add(spendingCategory);
    }

    public void remove(String expenseToBeRemoved)
    {
        SpendingCategory spendingCategory = spendingCategoryList.stream()
                .filter(sc -> expenseToBeRemoved.equals(sc.getSpendingCategory()))
                .findAny()
                .orElse(null);
        spendingCategoryList.remove(spendingCategory);
    }

    public void clear()
    {
        spendingCategoryList.clear();
    }

    public String displayExpenses()
    {
        StringBuilder sb = new StringBuilder();
        for (SpendingCategory sc : spendingCategoryList)
        {
            sb
                    .append(sc.getSpendingCategory()).append(",")
                    .append(sc.getCost()).append(",")
                    .append(sc.getSpendingType()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void storeToFile()
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file)))
        {
            writer.println(displayExpenses());
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public Double sumOfExpenses(SpendingCategory.SpendingType spendingType)
    {
        double total = 0;
        try
        {
            total = Files.lines(Paths.get(file.getAbsolutePath()))
                    .map(data -> data.split(","))
                    .filter(parts -> parts.length > 2)
                    .filter(parts -> parts[2].equals(spendingType.toString()))
                    .mapToDouble(parts -> Double.parseDouble(parts[1]))
                    .sum();
        }
        catch (IOException ioe)
        {
            ioe.getStackTrace(); // ioe.printStackTrace() if you want to see the errors and exceptions thrown
        }
        return total;
    }

    public Double totalExpense()
    {
        double total = 0;
        try
        {
            total = Files.lines(Paths.get(file.getAbsolutePath()))
                    .map(data -> data.split(","))
                    .filter(parts -> parts.length > 2)
                    .mapToDouble(parts -> Double.parseDouble(parts[1]))
                    .sum();
        }
        catch (IOException ioe)
        {
            ioe.getStackTrace(); // ioe.printStackTrace() if you want to see the errors and exceptions thrown
        }
        return total;
    }

    public void checkTotalExpense()
    {
        double total = totalExpense();
        if (total != 0) System.out.println(account.getLastName() + "'s total expense as of "
                + localDateTime.getMonth() + " " + localDateTime.getYear() + " is " + total);
    }

    public void storeExpensesToList()
    {
        try
        {
            Files.lines(Paths.get(this.file.getAbsolutePath()))
                    .map(data -> data.split(","))
                    .filter(parts -> parts.length > 2)
                    .map(parts -> new SpendingCategory(parts[0], Double.parseDouble(parts[1]), parts[2]))
                    .forEach(data -> spendingCategoryList.add(data));
        }
        catch (IOException ioe)
        {
            ioe.getStackTrace(); // ioe.printStackTrace() if you want to see the errors and exceptions thrown
        }
    }
    public Map<String, String> storeDataToMap()
    {
        Map<String, String> expenseMap = new HashMap<>();
        try
        {
            Files.lines(Paths.get(file.getAbsolutePath()))
                    .map(data -> data.split(","))
                    .filter(parts -> parts.length > 2)
                    .forEach(parts -> expenseMap.put(parts[0], parts[1]));
        }
        catch (IOException ioe)
        {
            ioe.getStackTrace(); // ioe.printStackTrace() if you want to see the errors and exceptions thrown
        }

        return expenseMap;
    }
    public void displayMonthlyBudgetData()
    {
        Map<String, String> expenseMap = storeDataToMap();

        int[] maximumLength = new int[2];
        for (Map.Entry<String, String> entry : expenseMap.entrySet())
        {
            maximumLength[0] = Math.max(maximumLength[0], entry.getKey().length());
            maximumLength[1] = Math.max(maximumLength[1], entry.getKey().length());
        }

        String format = "%-" + (maximumLength[0] + 20) + "s%-20s%n";

        String[] parts = file.toString().split(" ");
        System.out.println(parts[0].toUpperCase() + " " + parts[2] + " " + parts[1]);
        System.out.println("".repeat(maximumLength[0] + 25));

        System.out.printf(format, "Spending Category", "Cost");
        for (Map.Entry<String, String> entry : expenseMap.entrySet())
            System.out.printf(format, entry.getKey(), entry.getValue());
        System.out.println("---------------------------------------------");
        System.out.println("Total Expenses");
        System.out.printf(format, "by wants", String.valueOf(sumOfExpenses(SpendingCategory.SpendingType.WANTS)));
        System.out.printf(format, "by needs", String.valueOf(sumOfExpenses(SpendingCategory.SpendingType.NEEDS)));

        Double totalSavingsByAccountBalance = account.getBalance() - totalExpense();
        System.out.println("Total Savings");
        System.out.printf(format, "by account balance", String.valueOf(totalSavingsByAccountBalance));
        Double totalSavingsByMonthlyIncome = getIncome() - totalExpense();
        System.out.printf(format, "by monthly income", String.valueOf(totalSavingsByMonthlyIncome));
    }


    public void save(Double amount)
    {
        account.save(amount);
    }

    public Double getIncome()
    {
        return income;
    }

    public void setIncome(Double income)
    {
        this.income = income;
    }

    public boolean fileExists()
    {
        return file.exists();
    }

    public File getFile()
    {
        return file;
    }
}
