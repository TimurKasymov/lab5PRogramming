package models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType (XmlAccessType.NONE)
public class Products {

    @XmlElement(name = "product")
    private List<Product> products = null;

    /**
     * Method for get persons list
     * @return List<Product> products
     */
    public LinkedList<Product> getProducts() {
        return new LinkedList<Product>(products);
    }

    /** Method for passing a value to the persons list */
    public void setProducts(LinkedList<Product> persons) {
        this.products = persons;
    }

}


