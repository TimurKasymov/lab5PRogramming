package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

public class ShowCommand implements Command {
    private CollectionCustom<Product> collectionCustom;
    public ShowCommand(CollectionCustom<Product> collectionCustom){
        this.collectionCustom = collectionCustom;
    }

    @Override
    public boolean execute(String[] args) {
        var products = collectionCustom.get();
        for (Product product : products) {
            System.out.println(product.toString() + "\n");
        }
        if(products.size() == 0)
            System.out.println("there is no products yet.. add a new one");
        return true;
    }

    @Override
    public String getInfo() {
        return "printing collection elements into the string representation";
    }
}
