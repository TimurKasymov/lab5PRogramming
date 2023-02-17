package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

public class ClearCommand implements Command {
    private CollectionCustom<Product> productCollection;

    public ClearCommand(CollectionCustom<Product> productCollection){
        this.productCollection = productCollection;
    }

    @Override
    public boolean execute(String[] args) {
        productCollection.get().clear();
        return true;
    }

    @Override
    public String getInfo() {
        return "clear the collection";
    }
}
