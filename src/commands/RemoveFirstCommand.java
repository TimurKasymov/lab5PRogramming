package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

public class RemoveFirstCommand implements Command {
    private CollectionCustom<Product> productCollection;

    public RemoveFirstCommand(CollectionCustom<Product> productCollection){
        this.productCollection = productCollection;
    }

    @Override
    public boolean execute(String[] args) {
        productCollection.get().removeFirst();
        return true;
    }

    @Override
    public String getInfo() {
        return "removes first element in the collection";
    }
}
