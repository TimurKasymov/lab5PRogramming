import interfaces.CollectionCustom;
import interfaces.Loadable;
import models.*;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CollectionManager implements CollectionCustom<Product> {

    /** HashSet collection for keeping a collection as java-object */
    private LinkedList<Product> products;
    /** Field used for saving collection into xml file */
    private File xmlfile;

    /** for keeping Loadable instance*/
    private Loadable fileManager;

    /** Field for checking the program was started */
    private boolean wasStart;
    /** Container for storing used commands */


    // Constructor for checking a path to file existence and file readiness to work
    public CollectionManager(Loadable fileManager) {
        this.fileManager = fileManager;
        products = fileManager.load();
    }

    @Override
    public boolean validateData(){
        if(products.isEmpty())
            return true;

        var organizations = new LinkedList<Organization>();
        var productIds = new HashSet<Long>();
        var organizationIds = new HashSet<Long>();

        for (var prod: products
             ) {
            if(prod.getManufacturer() !=  null){
                var organization = prod.getManufacturer();
                organizationIds.add(organization.getId());
                organizations.add(prod.getManufacturer());
                if(organization.getName() == null || organization.getName().isEmpty() || organization.getAnnualTurnover() == null ||
                        organization.getAnnualTurnover() < 1 || organization.getOrganizationType() == null){
                    return false;
                }
            }
            if(prod.getPrice() < 1 || prod.getCreationDate() == null || prod.getCoordinates() == null ||
            prod.getName() == null || prod.getManufactureCost() == null || prod.getCoordinates().getX() == null)
                return false;

            productIds.add(prod.getId());
        }
        var minProductId = productIds.stream().reduce(Long.MAX_VALUE, (m,i)-> {if (i< m){m = i;} return m; });
        var minOrganizationId = organizationIds.stream().reduce(Long.MAX_VALUE, (m,i)-> {if (i< m){m = i;} return m; });

        if(productIds.stream().count() < products.size() || minProductId < 1
                || minOrganizationId < 1 || organizationIds.stream().count() < organizationIds.size()){
            return false;
        }
        return true;

    }

    @Override
    public LinkedList<Product> get() {
        return products;
    }

    @Override
    public void save() {
        if (!validateData()) {
            System.out.println("the product collection does not meet the validation criteria");
            return;
        }
        fileManager.save(products);
    }
}
