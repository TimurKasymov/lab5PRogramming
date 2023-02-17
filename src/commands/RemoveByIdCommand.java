package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

public class RemoveByIdCommand implements Command {
    private CollectionCustom<Product> collectionCustom;
    public RemoveByIdCommand(CollectionCustom<Product> collectionCustom){
        this.collectionCustom = collectionCustom;
    }

    @Override
    public boolean execute(String[] args) {
        try{
            long id = Long.parseLong(args[0]);
            var products = collectionCustom.get();
            if(id == 0){
                System.out.println("ID must be an number greater than 0. Try typing this command again");
                return false;
            }
            for (Product product : products) {
                Long intId = product.getId();
                if (intId == id) {
                    products.remove(product);
                    System.out.println("Element was successfully removed.");
                    return true;
                }
            }
        }
        catch (NumberFormatException exception){
            System.out.println("ID must be an number. Try typing this command again");
            return false;
        }

        System.out.println("Element with this ID is not defined. Try again.");
        return true;
    }

    @Override
    public String getInfo() {
        return "You should enter ID after entering a command.";
    }
}
