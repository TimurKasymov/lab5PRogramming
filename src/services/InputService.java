package services;

import models.*;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputService {

    public String inputName() {
        for ( ; ; ) {
            try {
                System.out.println("Do not enter a very long name, some parts of it may get lost");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a name: ");
                String name = scanner.nextLine().trim();
                if (name.equals("")) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }
                return name;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public OrganizationType inputOrganizationType(){
        for ( ; ; ) {
            try {
                System.out.println("Choose OrganizationType. Enter the number corresponding to the desired option. ");
                System.out.println(" 1 - COMMERCIAL, 2 - GOVERNMENT, 3 - TRUST, 4 - PRIVATE LIMITED COMPANY, 5 - OPEN JOINT STOCK COMPAN");
                Scanner scanner = new Scanner(System.in);
                int nationalityChoice = scanner.nextInt();
                switch (nationalityChoice) {
                    case 1:
                        return OrganizationType.COMMERCIAL;
                    case 2:
                        return OrganizationType.GOVERNMENT;
                    case 3:
                        return OrganizationType.TRUST;
                    case 4:
                        return OrganizationType.PRIVATE_LIMITED_COMPANY;
                    case 5:
                        return OrganizationType.OPEN_JOINT_STOCK_COMPANY;
                    default:
                        break;
                }
                System.out.println("You should enter a number from 1 to 5. Try again. ");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /** method for combining X and Y inputs */
    public Coordinates inputCoordinates(){
        System.out.println("adding coordinates..");
        var coor = new Coordinates(inputXLocation(), inputYLocation());
        System.out.println("done with coordinates..");
        return coor;
    }

    /**
     * Method for receiving x-coordinate of location of element
     * @return Double xLocation
     */
    public Double inputXLocation() {
        for ( ; ; ) {
            try {

                System.out.print("Enter X coordinate of location: ");
                Scanner scanner = new Scanner(System.in);
                return scanner.nextDouble();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a double-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving y-coordinate of element
     * @return float yLocation
     */
    public float inputYLocation() {
        for ( ; ; ) {
            try {
                System.out.print("Enter Y coordinate of location: ");
                Scanner scanner = new Scanner(System.in);
                return scanner.nextFloat();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a float-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /** method for taking price input */
    public float inputPrice(){
        for ( ; ; ) {
            try {
                System.out.print("Enter the price of the product: ");
                Scanner scanner = new Scanner(System.in);
                return scanner.nextFloat();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a float-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /** method for taking price input */
    public Double inputManufactureCost(){
        for ( ; ; ) {
            try {
                System.out.print("Enter manufacture cost: ");
                Scanner scanner = new Scanner(System.in);
                return scanner.nextDouble();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a Double-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }


    public UnitOfMeasure inputUnitOfMeasure(){
        for ( ; ; ) {
            try {
                System.out.println("Choose UnitOfMeasure. Enter the number corresponding to the desired option. ");
                System.out.println(" 1 - KILOGRAMS, 2 - METERS, 3 - LITERS, 4 - MILLIGRAMS");
                Scanner scanner = new Scanner(System.in);
                var unitOfMeasure = scanner.nextLine();
                if(unitOfMeasure.isEmpty()){
                    System.out.println("Unit Of Measure is not defined");
                    return null;
                }
                var unitOfMeasureEnumNumber = Integer.valueOf(unitOfMeasure);
                switch (unitOfMeasureEnumNumber) {
                    case 1:
                        return UnitOfMeasure.KILOGRAMS;
                    case 2:
                        return UnitOfMeasure.METERS;
                    case 3:
                        return UnitOfMeasure.LITERS;
                    case 4:
                        return UnitOfMeasure.MILLIGRAMS;
                    default:
                        break;
                }
                System.out.println("You should enter a number from 1 to 4. Try again. ");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    public Organization inputOrganization(Collection<Product> products){
        var maxId = Long.MIN_VALUE;
        for (var prod: products
        ) {
            if(prod.getManufacturer() !=  null){
                var organization = prod.getManufacturer();
                maxId = Long.max(organization.getId(), maxId);
            }
        }
        System.out.println("adding organization..");
        var org = new Organization(maxId < 0 ? 1 : maxId + 1, inputName(), inputAnnualTurnover(), inputOrganizationType() );
        System.out.println("done with organization..");
        return org;
    }

    public Integer inputAnnualTurnover() {
        for ( ; ; ) {
            try {
                System.out.printf("Enter annual turnover. Note that value can only be from %s to %s: ", 1, Integer.MAX_VALUE);
                Scanner scanner = new Scanner(System.in);
                return scanner.nextInt();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a double-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

}
