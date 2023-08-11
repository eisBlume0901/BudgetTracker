package BudgetTracker.domain;

import java.util.*;

public class SpendingCategory
{
    private String spendingCategory;
    private Double cost;
    public enum SpendingType
    {
        WANTS, NEEDS
    }

    private String spendingType;

    public SpendingCategory(String spendingCategory, Double cost, String spendingType)
    {
        try
        {
            if (!spendingCategory.isEmpty() && spendingCategory.matches("[A-Za-z\\s]+$"))
                this.spendingCategory = spendingCategory;
            else throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException iae)
        {
            System.err.println("Note: Spending Category should only contain alphabets only");
        }
        this.cost = cost;
        this.spendingType = spendingType;
    }

    public String getSpendingCategory()
    {
        return spendingCategory;
    }

    public Double getCost()
    {
        return cost;
    }

    public String getSpendingType()
    {
        return spendingType;
    }

    public void setSpendingCategory(String spendingCategory)
    {
        this.spendingCategory = spendingCategory;
    }

    public void setCost(Double cost)
    {
        this.cost = cost;
    }

    public void setSpendingType(String spendingType)
    {
        this.spendingType = spendingType;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof SpendingCategory compared)) return false;

        return Objects.equals(this.getSpendingCategory(), compared.getSpendingCategory());
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(spendingCategory);
        /*
        If your hashCode and equals override is based on a single member of your class,
        use Objects.hashCode(member)

        If your hashCode and equals override is based on multiple attribute of your class,
        use Objects.hash(MemberA, MemberB, MemberC)
         */
    }
}
