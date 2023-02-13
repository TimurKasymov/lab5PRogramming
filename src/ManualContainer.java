import Interfaces.Manual;

import java.util.HashMap;

public class ManualContainer implements Manual {
    private final HashMap<String, String> commInf;

    {
    commInf = new HashMap<>();
        commInf.put("help", " - display help for available commands");
        commInf.put("info", " - print all elements in string representation to standard output");
        commInf.put("add", " - add new element to the collection");
        commInf.put("update_by_id id", " - update the element`s value, whose ID is equal to the given." +
                " You should enter ID after entering a command.");
        commInf.put("remove_by_id id", " - remove an element from the collection by its ID." +
                " You should enter ID after entering a command.");
        commInf.put("clear", " - clear the collection");
        commInf.put("save", " - save the collection to file");
        commInf.put("execute_script filename", " - read and execute a script from specified file." +
                " You should enter path to file after entering a command.");
        commInf.put("exit", " - end the program (without saving to file)");
        commInf.put("remove_first ", " - remove the first element in the collection");
        commInf.put("reorder", " - reorder the collection");
        commInf.put("history", " - output the last 9 used commands");
        commInf.put("filter_by_manufacture_cost", "display items whose manufactureCost field value is equal to the given one" +
                "enter the manufacture cost right after the command name");
        commInf.put("filter_greater_than_price", "display elements whose price field value is greater than the specified one" +
                "enter the price right after the command name");
        commInf.put("print_unique_unit_of_measure", "display unique values of the unitOfMeasure field of all elements in the collection");
    }

    @Override
    public HashMap<String, String> getCommandInfo() {
        return commInf;
    }
}
