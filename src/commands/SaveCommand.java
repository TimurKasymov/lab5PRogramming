package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

public class SaveCommand implements Command {

    private CollectionCustom<Product> productCollection;

    public SaveCommand(CollectionCustom<Product> productCollection){
        this.productCollection = productCollection;
    }

    @Override
    public boolean execute(String[] args) {
        productCollection.save();
        return true;
    }

    @Override
    public String getInfo() {
        return "save the collection to file";
    }
}
