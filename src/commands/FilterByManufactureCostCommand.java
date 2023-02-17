package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class FilterByManufactureCostCommand implements Command {
    private CollectionCustom<Product> collectionCustom;
    public FilterByManufactureCostCommand(CollectionCustom<Product> collectionCustom){
        this.collectionCustom = collectionCustom;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            var manufactureCost = Double.valueOf(args[0]);
            var products = collectionCustom.get();
            for (Product product : products) {
                if(product.getManufactureCost().doubleValue() == manufactureCost)
                    System.out.println(product.toString() + "\n");
            }
        }
        catch (NumberFormatException exception){
            System.out.printf("Manufacture cost must be from %s to %s. Try typing this command again", Double.MIN_VALUE, Double.MAX_VALUE);
            return false;
        }
        return true;
    }

    @Override
    public String getInfo() {
        return "display items whose manufactureCost field value is equal to the given on enter the manufacture cost right after the command name";
    }
}
