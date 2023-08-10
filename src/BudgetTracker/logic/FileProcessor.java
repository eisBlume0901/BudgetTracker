package BudgetTracker.logic;

import java.io.*;
import static java.lang.System.*;

public interface FileProcessor
{
    default void createNewFile(String fileName)
    {
        try (FileWriter file = new FileWriter(fileName))
        {
            boolean success = new File(fileName).createNewFile();

            if (success)
                out.println("File created successfully!");
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    void storeToFile() throws IOException;

}
