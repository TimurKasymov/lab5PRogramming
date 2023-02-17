package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

public class RemoveCommand implements Command {
    private CollectionCustom<Product> productCollection;

    public RemoveCommand(CollectionCustom<Product> productCollection){
        this.productCollection = productCollection;
    }
    @Override
    public boolean execute(String[] args) {
        try {
            var id = Long.parseLong(args[0]);
            productCollection.get().remove(id);
            return true;
        }
        catch (NumberFormatException exception){
                System.out.println("ID must be an number. Try typing this command again");
                return false;
        }
    }

    @Override
    public String getInfo() {
        return "remove an element from the collection by its ID.";
    }
}
