package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

import java.util.Collections;

public class ReorderCommand implements Command {

    private CollectionCustom<Product> productCollection;

    public ReorderCommand(CollectionCustom<Product> productCollection){
        this.productCollection = productCollection;
    }

    @Override
    public boolean execute(String[] args) {
        Collections.reverse(productCollection.get());
        return true;
    }

    @Override
    public String getInfo() {
        return "reorder the collection";
    }
}
