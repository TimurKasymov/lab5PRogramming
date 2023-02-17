package interfaces;

import java.util.LinkedList;

public interface CollectionCustom<TEntity> {
    public boolean validateData();
    LinkedList<TEntity> get();
    void save();
}
