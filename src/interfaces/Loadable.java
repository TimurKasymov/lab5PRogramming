package interfaces;

import models.Product;

import java.time.ZonedDateTime;
import java.util.LinkedList;

public interface Loadable {

    LinkedList<Product> load();
    boolean save(LinkedList<Product> products);
    ZonedDateTime getInitializationTime();
}
