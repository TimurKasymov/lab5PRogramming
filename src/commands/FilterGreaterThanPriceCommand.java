package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

public class FilterGreaterThanPriceCommand implements Command {
    private CollectionCustom<Product> collectionCustom;
    public FilterGreaterThanPriceCommand(CollectionCustom<Product> collectionCustom){
        this.collectionCustom = collectionCustom;
    }

    @Override
    public boolean execute(String[] args) {
        var products = collectionCustom.get();
        try{
            var flag = false;
            var price = Float.parseFloat(args[0]);
            for (var prod : products){
                if(prod.getPrice() > price){
                    System.out.println(prod.toString() + "\n");
                    flag = true;
                }
            }
            if(!flag)
                System.out.println("no such elements found");
            return true;
        }
        catch (NumberFormatException | IndexOutOfBoundsException exception){
            System.out.println("ID must be provided and it must be a number. Try typing this command again");
            return false;
        }
    }

    @Override
    public String getInfo() {
        return "display elements whose price field value is greater than the specified one";
    }
}
