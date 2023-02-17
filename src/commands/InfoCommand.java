package commands;

import interfaces.CollectionCustom;
import interfaces.Command;
import models.Product;

import java.lang.reflect.ParameterizedType;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InfoCommand implements Command {
    private CollectionCustom<Product> collectionCustom;
    public InfoCommand(CollectionCustom<Product> collectionCustom){
        this.collectionCustom = collectionCustom;
    }

    @Override
    public boolean execute(String[] args) {
        var time = collectionCustom.getInitializationTime().atZone(ZoneId.of("Europe/Moscow"));
        var toPrint = String.format("type of colleciton: %s\ninitialization date: %s\nnumber of elements: %s", collectionCustom.getElementType().getName(),
                time.format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")), collectionCustom.get().size() );
        System.out.println(toPrint);
        return true;
    }

    @Override
    public String getInfo() {
        return "prints information about the collection to the standard output stream (type, initialization date, number of elements, etc.)";
    }
}
