import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CommandManager {

    /** Collection manager for realising user`s commands */
    private final CollectionManager collectionManager;
    /** Field for receiving user`s command */
    private String userCommand;
    /** Field for separating user input into a command and an argument to it */

    private UsedCommandsContainer usedCommandsContainer;
    private String[] finalUserCommand;

    {
        userCommand = "";
    }

    /**
     * Constructor for making a commander
     * @param manager - CollectionManager class object which will realise user`s commands
     */
    public CommandManager(CollectionManager manager) {
        this.collectionManager = manager;
        this.usedCommandsContainer = manager.getUsedCommandsContainer();
    }

    /**
     * Method for starting interactive mood
     */
    public void interactiveMod() {
        try {
            try (Scanner commandReader = new Scanner(System.in)) {
                while (!userCommand.equals("exit")) {
                    System.out.println("Enter a command: ");
                    userCommand = commandReader.nextLine();
                    finalUserCommand = userCommand.trim().toLowerCase().split(" ", 2);
                    try {
                        switch (finalUserCommand[0]) {
                            case "":
                                break;
                            case "help":
                                collectionManager.help();
                                break;
                            case "info":
                                collectionManager.info();
                                break;
                            case "show":
                                collectionManager.show();
                                break;
                            case "add":
                                collectionManager.add();
                                break;
                            case "update_by_id":
                                collectionManager.update_by_id(finalUserCommand[1]);
                                break;
                            case "remove_by_id":
                                collectionManager.remove_by_id(finalUserCommand[1]);
                                break;
                            case "clear":
                                collectionManager.clear();
                                break;
                            case "save":
                                System.out.println("saved " + (collectionManager.save() ? "succesfully" : "unsuccesfully"));
                                break;
                            case "execute_script":
                                collectionManager.execute_script(finalUserCommand[1]);
                                break;
                            case "exit":
                                collectionManager.exit();
                                break;
                            case "print_unique_unit_of_measure":
                                collectionManager.print_unique_unit_of_measure();
                                break;
                            case "filter_greater_than_price":
                                collectionManager.filter_greater_than_price(finalUserCommand[1]);
                                break;
                            case "remove_first":
                                collectionManager.remove_first();
                                break;
                            case "reorder":
                                collectionManager.reorder();
                                break;
                            case "history":
                                collectionManager.history();
                                break;
                            default:
                                System.out.println("Unknown command. Write help for help.");
                                break;
                        }
                        if(finalUserCommand[0] != ""){
                            if(usedCommandsContainer.usedCommands.size() == 9)
                                usedCommandsContainer.usedCommands.removeFirst();
                            usedCommandsContainer.usedCommands.add(finalUserCommand[0]);
                        }


                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Argument of command is absent. Write help for help.");
                    }
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(1);
        }
    }

}
