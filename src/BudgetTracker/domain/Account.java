package BudgetTracker.domain;

import java.time.*;
import java.time.format.*;
import static java.lang.System.*;

public class Account extends Person
{
    private Double balance;
    private Person person;
    private String firstName;
    private String lastName;
    private Double savings;

    public Account(String firstName, String lastName, String sex, Double balance, Double savings)
    {
        super(firstName, lastName, sex);
        try
        {
            if (balance > 0 && balance < 101_000) this.balance = balance;
            else throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException iae)
        {
            out.println("Initial balance should not be less than 0 and greater than 100,000");
        }

        try
        {
            if (savings >= 0 && savings < 101_000) this.savings = savings;
            else throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException iae)
        {
            out.println("Savings should not less than 0 and greater than 100,000");
        }
    }


    public Double getBalance()
    {
        return balance;
    }

    public Double getSavings()
    {
        return savings;
    }
    public void deposit(double amount)
    {
        try
        {
            if (amount > 0) this.balance += amount;
            else throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException iae)
        {
            out.println("Deposit should not be 0");
        }
    }

    public void withdraw(double amount)
    {
        try
        {
            if (this.balance > amount) this.balance -= amount;
            else throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException iae)
        {
            out.println("Not enough balance to withdraw");
        }
    }
    public void checkBalance()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        out.println(getLastName() + "'s current balance as of " + formatter.format(localDateTime));
        out.println(balance);
    }

    public void save(double amount)
    {
        savings += amount;
    }

    public void checkSavings()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        out.println(getLastName() + "'s current savings as of " + formatter.format(localDateTime));
        out.println(savings);
    }

}
