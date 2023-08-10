package BudgetTracker.domain;
public abstract class Person
{
    private String firstName;
    private String lastName;

    public enum Sex
    {
        MALE, FEMALE;
    }

    private String sex;

    public Person(String firstName, String lastName, String sex)
    {
        try
        {
            if (!firstName.isEmpty() && firstName.matches("[A-Za-z\\s]+$")
                    && !lastName.isEmpty() && lastName.matches("[A-Za-z\\s]+$"))
            {
                this.firstName = firstName;
                this.lastName = lastName;
            }
            else
            {
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException iae)
        {
            System.out.println("Name should be not be empty and it should contain alphabet characters only!");
        }


        if (sex.equals(Sex.MALE.toString()))
        {
            this.sex = Sex.MALE.toString();
        }
        if (sex.equals(Sex.FEMALE.toString()))
        {
            this.sex = Sex.FEMALE.toString();
        }
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getSex()
    {
        return sex;
    }

    @Override
    public String toString()
    {
        return this.firstName + " " + this.lastName;
    }
}
