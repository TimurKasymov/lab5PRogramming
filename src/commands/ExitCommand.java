package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

import java.util.concurrent.TimeUnit;

public class ExitCommand implements Command {
    private CollectionCustom<Product> collectionCustom;
    public ExitCommand(CollectionCustom<Product> collectionCustom){
        this.collectionCustom = collectionCustom;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            System.out.println("Program will be finished now. ");
            TimeUnit.SECONDS.sleep(3);
            System.exit(0);
        } catch (InterruptedException interruptedException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
        return true;
    }

    @Override
    public String getInfo() {
        return "end the program (without saving to file)";
    }
}
