package BudgetTracker.userinterface;

import java.util.Scanner;
import static java.lang.System.*;

public interface UserInputFunctionalities
{
    default int validIntegerChoice(int min, int max)
    {
        int validChoice = 0;
        Scanner scanner = new Scanner(in);
        try
        {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice >= min && choice <= max) validChoice = choice;
            else throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException iae)
        {
            out.println("Please choose only from " + min + " to " + max);
        }

        return validChoice;
    }

    default String validStringInput()
    {
        String validStringInput = "";
        Scanner scanner = new Scanner(in);
        boolean valid = false;
        while (!valid)
        {
            try
            {
                String stringInput = scanner.nextLine();
                if (!stringInput.isEmpty() && stringInput.matches("[A-Za-z\\s]+$"))
                {
                    validStringInput = stringInput.trim();
                    valid = true;
                }
                else throw new IllegalArgumentException();
            }
            catch (IllegalArgumentException iae)
            {
                out.println("Wrong input. Please try again!");
            }
        }
        return validStringInput;
    }

    default Double validDoubleInput()
    {
        double validDoubleInput = 0;
        boolean valid = false;
        while (!valid)
        {
            Scanner scanner = new Scanner(in);
            try
            {
                validDoubleInput = Double.parseDouble(scanner.nextLine());
                valid = true;

            } catch (NumberFormatException nme)
            {
                out.println("Please input numbers only (e.g. 1.0)");
            }
        }
        return validDoubleInput;
    }
}
