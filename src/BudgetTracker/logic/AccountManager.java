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
        this.file = new File("Lists of Accounts");
    }

    public void add(Account account)
    {
        accountList.add(account);
    }

    public void remove(Account account)
    {
        accountList.remove(account);
    }
    public String displayAccounts()
    {
        StringBuilder sb = new StringBuilder();
        for (Account account : this.accountList)
            sb.
                    append(account.getFirstName()).append(",").
                    append(account.getLastName()).append(",").
                    append(account.getSex()).append(",").
                    append(account.getBalance()).append(",").
                    append(account.getSavings()).append("\n");
        return sb.toString();
    }

    public void storeToFile()
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file)))
        {
            writer.println(displayAccounts());
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public boolean exists(String firstName, String lastName)
    {
        boolean exists = false;
        for (Account acc : accountList)
            if (acc.getFirstName().equals(firstName)
            && acc.getLastName().equals(lastName))
            {
                exists = true;
                break;
            }
        return exists;
    }

    public void storeAccountsToList()
    {
        try
        {
            Files.lines(Paths.get(file.getAbsolutePath()))
                    .map(data -> data.split(","))
                    .filter(parts -> parts.length > 4)
                    .map(parts ->
                            new Account(parts[0], parts[1], parts[2],
                                    Double.parseDouble(parts[3]), Double.parseDouble(parts[4])))
                    .forEach(this::add);
        }
        catch (NoSuchFileException nsfe)
        {
            System.err.println("There is no such file to store the accounts to a list");
        }
        catch (FileNotFoundException fnfe)
        {
            System.err.println("File not found. Please create a new file and update the AccountManager Class");
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public Account retrieveAccount(String firstName, String lastName)
    {
        Account account = accountList.stream()
                .filter(acc -> firstName.equals(acc.getFirstName()) && lastName.equals(acc.getLastName()))
                .findAny()
                .orElse(null);
        return account;
    }
}
