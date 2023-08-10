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
        this.income = 0.0;
    }

    public void createBudgetFile()
    {
        try
        {
            if (this.file == null || !this.file.exists())
            {
                this.file = new File(account.getLastName()
                        + " " + this.localDateTime.getYear() + " " + this.localDateTime.getMonth());
                createNewFile(file.getName());
            }
            else throw new FileAlreadyExistsException("File already exists!");
        }
        catch (FileAlreadyExistsException faee)
        {
            faee.printStackTrace();
        }
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
            ioe.printStackTrace();
        }
        return total;
    }

    public Double totalExpense()
    {
        double wantsTotal = sumOfExpenses(SpendingCategory.SpendingType.WANTS);
        double needsTotal = sumOfExpenses(SpendingCategory.SpendingType.NEEDS);
        double total = wantsTotal + needsTotal;
        return total;
    }

    public void checkTotalExpense()
    {
        double total = totalExpense();
        if (total == 0) System.out.println("File is empty. Please create an spending category list and add expenses");
        else System.out.println(account.getLastName() + "'s total expense as of "
                + localDateTime.getMonth() + " " + localDateTime.getYear() + " is " + total);
    }

    public void storeExpensesToList()
    {
        try
        {
            Files.lines(Paths.get(file.getAbsolutePath()))
                    .map(data -> data.split(","))
                    .filter(parts -> parts.length > 2)
                    .map(parts -> new SpendingCategory(parts[0], Double.parseDouble(parts[1]), parts[2]))
                    .forEach(data -> spendingCategoryList.add(data));
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    public Map<String, String> storeDataToMap()
    {
        Map<String, String> expenseMap = new HashMap<>();
        try
        {
            expenseMap.put("Spending Category", "Cost");
            Files.lines(Paths.get(file.getAbsolutePath()))
                    .map(data -> data.split(","))
                    .filter(parts -> parts.length > 2)
                    .map(parts -> expenseMap.put(parts[0], parts[1]));
            expenseMap.put("Total Expenses :", String.valueOf(totalExpense()));
            expenseMap.put("Total Savings  :", String.valueOf(account.getSavings()));
            expenseMap.put("Monthly Income :", String.valueOf(getIncome()));
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
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

        String format = "%-" + (maximumLength[0] + 5) + "s%-20s%n";

        String[] parts = file.toString().split(" ");
        System.out.println(parts[0].toUpperCase() + " " + parts[2] + " " + parts[1]);
        System.out.println("".repeat(maximumLength[0] + 25));

        for (Map.Entry<String, String> entry : expenseMap.entrySet())
            System.out.printf(format, entry.getKey(), entry.getValue());
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
}