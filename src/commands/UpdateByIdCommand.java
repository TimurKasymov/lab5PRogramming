package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;
import services.InputService;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/** Class for updating the element by it`s ID */
public class UpdateByIdCommand implements Command {
    private CollectionCustom<Product> CollectionCustom;
    private InputService inputService;
    {
        inputService = new InputService();
    }
    public UpdateByIdCommand(CollectionCustom<Product> collectionCustom){
        this.CollectionCustom = collectionCustom;
    }
    @Override
    public boolean execute(String[] args) {
        try{
            long id = Long.parseLong(args[0]);
            var products = CollectionCustom.get();
            if(id <= 0){
                System.out.println("ID must be an number greater than 0. Try typing this command again");
                return false;
            }
            System.out.println("updateing product with id: " + id);
            for (Product product : products) {
                Long intId = product.getId();
                if (intId == id) {
                    products.remove(product);
                    var name = inputService.inputName();
                    var coord = inputService.inputCoordinates();
                    var price = inputService.inputPrice();
                    var manufCost = inputService.inputManufactureCost();
                    var unit = inputService.inputUnitOfMeasure();

                    int yesOrNo = 0;
                    for( ; ; ) {
                        try {
                            System.out.println("enter a number: ");
                            System.out.println("should we add organization? enter the number: 1 - Yes or 2 - No");
                            Scanner scanner = new Scanner(System.in);
                            yesOrNo = scanner.nextInt();
                            if (yesOrNo == 1 || yesOrNo == 2)
                                if(yesOrNo == 2)
                                    System.out.println("organization will not be defined");
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("enter a number: ");
                        }
                    }
                    var prod = new Product(id, name, coord, price, manufCost,
                            unit, yesOrNo == 1 ? inputService.inputOrganization(products) : null);
                    products.add(prod);
                    Collections.sort(products);
                    System.out.println("Element was updated and sorted successfully.");
                    return true;
                }
            }
        }
        catch (NumberFormatException exception){
            System.out.println("ID must be an number. Try typing this command again");
        }
        System.out.println("Element with this ID is not defined. Try again.");
        return true;
    }

    @Override
    public String getInfo() {
        return "update the element`s value, whose ID is equal to the given." +
                " You should enter ID after entering a command.";
    }
}
