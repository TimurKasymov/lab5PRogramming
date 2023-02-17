package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;
import services.InputService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AddCommand implements Command {
    private CollectionCustom<Product> productCollection;
    private InputService inputService;
    {
        inputService = new InputService();
    }
    public AddCommand(CollectionCustom<Product> productCollection){
        this.productCollection = productCollection;
    }

    @Override
    public boolean execute(String[] args) {
        System.out.println("adding product..");
        var maxId = Long.MIN_VALUE;
        var products = productCollection.get();
        for (var product : products) {
            maxId = Long.max(maxId, product.getId());
        }
        var id = products.size() == 0 ? 1 : maxId + 1;
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
        if (products.size() == 0) {
            products.add(prod);
            return true;
        }
        if (products.peekLast().getId() == maxId)
            products.add(prod);
        else
            products.addFirst(prod);
        return true;
    }

    @Override
    public String getInfo() {
        return "add new element to the collection";
    }
}
